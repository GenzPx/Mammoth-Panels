package dae.mammoth.id.process

import dae.mammoth.id.util.ProcessOutputReader
import java.io.File

/**
 * A running bot process together with its output reader. Bundling the reader
 * with the process makes it easy for the console to stream real output.
 */
class ManagedBotProcess(
    val process: Process,
    val output: ProcessOutputReader = ProcessOutputReader(),
) {
    val isAlive: Boolean get() = process.isAlive

    fun drainOutput(): List<String> = output.drain()

    fun startReading() = output.start(process)

    fun stop() {
        output.close()
        process.destroy()
    }

    companion object {
        /** Spawns a managed process, or null on failure. */
        fun launch(command: List<String>, workingDir: File?): ManagedBotProcess? =
            runCatching {
                val pb = ProcessBuilder(command).directory(workingDir).redirectErrorStream(true)
                val p = pb.start()
                ManagedBotProcess(p)
            }.getOrNull()
    }
}
