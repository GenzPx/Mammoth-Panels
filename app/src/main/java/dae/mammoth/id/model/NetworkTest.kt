package dae.mammoth.id.model

/** Result of a single connectivity probe. */
enum class ProbeStatus { Pending, Running, Ok, Fail }

/** A connectivity / endpoint test shown in the diagnostics screen. */
data class NetworkTest(
    val id: String,
    val label: String,
    val host: String,
    val port: Int? = null,
    val status: ProbeStatus = ProbeStatus.Pending,
    val latencyMs: Long = -1L,
    val detail: String = "",
)
