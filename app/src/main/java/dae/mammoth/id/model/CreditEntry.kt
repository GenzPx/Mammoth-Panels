package dae.mammoth.id.model

/** A row of credit information. */
data class CreditEntry(
    val section: String,
    val items: List<Pair<String, String>>,
)

object SampleCredits {
    val list = listOf(
        CreditEntry(
            "Pengembang / Developer",
            listOf("Author" to "GenzPX", "GitHub" to "@GenzPX", "Role" to "Pengembang"),
        ),
        CreditEntry(
            "Terinspirasi Dari",
            listOf("Pterodactyl Panel" to "UI/UX reference", "Wings" to "daemon concept", "Termux" to "local runtime idea"),
        ),
        CreditEntry(
            "Teknologi / Libraries",
            listOf("Kotlin" to "lang", "Jetpack Compose" to "UI", "Material 3" to "design", "Navigation Compose" to "nav"),
        ),
        CreditEntry(
            "Terima Kasih",
            listOf("Kontributor" to "—", "Komunitas" to "—", "Support" to "—"),
        ),
    )
}
