package dae.mammoth.id.model

/** A dashboard statistic tile. */
data class SystemMetric(
    val label: String,
    val value: String,
    val sub: String = "",
    val accent: Boolean = false,
)
