package dae.mammoth.id.process

import java.io.File
import java.util.concurrent.ConcurrentHashMap

/**
 * Owns the lifecycle of bot child processes. Each bot id maps to a live
 * [Process]. In production this would persist across the foreground service and
 * feed output back into the console; for the scaffold it demonstrates the full
 * spawn / stop / status lifecycle.
 */
class RuntimeProcessManager {

    private val processes = ConcurrentHashMap<String, Process>()
    private val startTimes = ConcurrentHashMap<String, Long>()

    val activeIds: Set<String> get() = processes.keys

    fun isRunning(id: String): Boolean =
        processes[id]?.isAlive ?: false

    fun uptimeSeconds(id: String): Long {
        val start = startTimes[id] ?: return 0L
        return (System.currentTimeMillis() - start) / 1000
    }

    /**
     * Spawn a process running [command] inside [workingDir].
     * Returns true when the process was successfully started.
     */
    fun start(id: String, command: List<String>, workingDir: File?): Boolean {
        if (isRunning(id)) return false
        return runCatching {
            val pb = ProcessBuilder(command)
                .directory(workingDir)
                .redirectErrorStream(true)
            val p = pb.start()
            processes[id] = p
            startTimes[id] = System.currentTimeMillis()
            true
        }.getOrDefault(false)
    }

    fun stop(id: String) {
        processes.remove(id)?.let { p ->
            p.destroy()
            startTimes.remove(id)
        }
    }

    fun stopAll() {
        processes.keys.forEach { stop(it) }
    }

    fun destroy() {
        processes.keys.forEach { id ->
            processes.remove(id)?.destroy()
        }
        startTimes.clear()
    }
}
