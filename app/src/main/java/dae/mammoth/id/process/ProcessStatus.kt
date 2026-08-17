package dae.mammoth.id.process

/** Snapshot of a managed bot process. */
data class ProcessStatus(
    val botId: String,
    val running: Boolean,
    val uptimeSeconds: Long,
    val exitCode: Int? = null,
)
