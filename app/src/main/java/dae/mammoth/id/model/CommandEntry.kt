package dae.mammoth.id.model

/** A single console command issued by the user. */
data class CommandEntry(
    val command: String,
    val timestamp: Long = System.currentTimeMillis(),
)
