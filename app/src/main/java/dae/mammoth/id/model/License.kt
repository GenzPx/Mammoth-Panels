package dae.mammoth.id.model

/** Open-source license metadata shown on the About/License screen. */
data class LicenseInfo(
    val name: String,
    val summary: String,
)

object SampleLicense {
    val info = LicenseInfo(
        name = "MIT License",
        summary = "Izin diberikan secara gratis kepada siapa pun untuk menggunakan, menyalin, mengubah, dan membagikan perangkat lunak ini, asalkan menyertakan pemberitahuan hak cipta asli.",
    )
}
