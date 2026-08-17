package dae.mammoth.id.model

import java.util.Date

/** A diagnostic log entry with a timestamp and level. */
data class LogEntry(
    val time: String,
    val level: String,
    val message: String,
) {
    companion object {
        fun now(message: String, level: String = "INFO"): LogEntry =
            LogEntry(java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(Date()), level, message)
    }
}
