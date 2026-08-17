package dae.mammoth.id.util

import java.util.Locale

object Formatters {

    private val UNITS = arrayOf("B", "KB", "MB", "GB", "TB")

    /** Format a byte count into a human-readable string. */
    fun bytes(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        var value = bytes.toDouble()
        var unit = 0
        while (value >= 1024 && unit < UNITS.size - 1) {
            value /= 1024
            unit++
        }
        return if (unit == 0) {
            "${bytes} B"
        } else {
            String.format(Locale.US, "%.1f %s", value, UNITS[unit])
        }
    }

    /** Format seconds into a human-readable uptime like "12h 34m". */
    fun uptime(seconds: Long): String {
        val d = seconds / 86_400
        val h = (seconds % 86_400) / 3_600
        val m = (seconds % 3_600) / 60
        return when {
            d > 0 -> "${d}d ${h}h ${m}m"
            h > 0 -> "${h}h ${m}m"
            else -> "${m}m"
        }
    }

    /** Format a timestamp into a locale date string. */
    fun timestamp(epochMillis: Long): String {
        if (epochMillis <= 0L) return "—"
        val fmt = java.text.SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        return fmt.format(java.util.Date(epochMillis))
    }

    /** Simple id sanitizer for bot ids. */
    fun slug(input: String): String =
        input.lowercase(Locale.US)
            .trim()
            .replace(Regex("[^a-z0-9]+"), "-")
            .trim('-')
            .ifEmpty { "bot" }
}
