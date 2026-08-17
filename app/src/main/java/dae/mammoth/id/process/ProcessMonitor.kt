package dae.mammoth.id.process

import java.io.File
import java.util.concurrent.ConcurrentHashMap

/**
 * Tracks managed bot processes and exposes their ids, uptime and live output.
 * This is the live counterpart of the simpler [RuntimeProcessManager] — it keeps
 * the [ManagedBotProcess] (process + output reader) so the console can stream
 * real stdout.
 */
class ProcessMonitor {

    private val managed = ConcurrentHashMap<String, ManagedBotProcess>()
    private val startTimes = ConcurrentHashMap<String, Long>()

    val runningIds: Set<String> get() = managed.keys.toSet()

    fun isRunning(id: String): Boolean = managed[id]?.isAlive ?: false

    fun uptime(id: String): Long {
        val s = startTimes[id] ?: return 0L
        return (System.currentTimeMillis() - s) / 1000
    }

    fun start(id: String, command: List<String>, dir: File?): Boolean {
        if (isRunning(id)) return false
        val mbp = ManagedBotProcess.launch(command, dir) ?: return false
        mbp.startReading()
        managed[id] = mbp
        startTimes[id] = System.currentTimeMillis()
        return true
    }

    fun drain(id: String): List<String> = managed[id]?.drainOutput() ?: emptyList()

    fun stop(id: String) {
        managed.remove(id)?.stop()
        startTimes.remove(id)
    }

    fun stopAll() {
        managed.keys.forEach { stop(it) }
    }
}
