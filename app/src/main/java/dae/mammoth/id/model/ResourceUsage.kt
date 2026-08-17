package dae.mammoth.id.model

/** Point-in-time resource usage snapshot for a bot. */
data class ResourceUsage(
    val cpuPercent: Int,
    val memMB: Int,
    val memPercent: Int,
    val diskGB: Float,
    val diskPercent: Int,
)

/** Generates the canned resource values used by the sample bots. */
object ResourceGenerator {

    fun forBot(id: String): ResourceUsage = when (id) {
        "wa" -> ResourceUsage(34, 412, 48, 1.2f, 21)
        "tg" -> ResourceUsage(12, 186, 19, 0.8f, 8)
        else -> ResourceUsage(0, 0, 0, 0.4f, 14)
    }
}
