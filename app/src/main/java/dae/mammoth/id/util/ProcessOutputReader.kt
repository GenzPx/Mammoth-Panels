package dae.mammoth.id.util

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.thread

/**
 * Reads the stdout stream of a child process and queues the lines so the UI can
 * render them as console output. Call [start] after a process is spawned and
 * [close] when the bot is stopped.
 */
class ProcessOutputReader {

    private val lines = ConcurrentLinkedQueue<String>()
    private var worker: Thread? = null

    val available: Boolean get() = lines.isNotEmpty()

    /** Returns and clears the current buffered lines. */
    fun drain(): List<String> {
        val out = ArrayList<String>()
        while (true) {
            val l = lines.poll() ?: break
            out.add(l)
        }
        return out
    }

    fun start(process: java.lang.Process) {
        if (worker?.isAlive == true) return
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        worker = thread(isDaemon = true) {
            try {
                reader.forEachLine { lines.add(it) }
            } catch (_: Exception) {
                // process ended; ignore
            }
        }
    }

    fun close() {
        worker?.interrupt()
        worker = null
    }
}
