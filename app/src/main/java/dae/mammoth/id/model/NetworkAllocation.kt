package dae.mammoth.id.model

/** A network allocation (address/port), mirroring Pterodactyl's Network tab. */
data class NetworkAllocation(
    val address: String,
    val port: Int,
    val primary: Boolean = false,
)

object SampleAllocations {
    val list = listOf(
        NetworkAllocation("wa.session.local", 8080, true),
        NetworkAllocation("wa.session.local", 8443, false),
        NetworkAllocation("tg.webhook.local", 9000, false),
    )
}
