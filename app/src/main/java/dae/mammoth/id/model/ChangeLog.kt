package dae.mammoth.id.model

/** A changelog entry with a version tag and list of changes. */
data class ChangeLogEntry(
    val version: String,
    val date: String,
    val changes: List<String>,
)

object SampleChangeLog {
    val list = listOf(
        ChangeLogEntry(
            version = "1.0.0",
            date = "2026-08-17",
            changes = listOf(
                "Rilis stabil pertama",
                "Icon launcher resmi (logo Mammoth)",
                "Native C/NDK helper + Java runtime info",
                "Network diagnostics & process monitor",
                "Sign release APK via GitHub Releases",
                "Pembersihan kode & perbaikan bug",
            ),
        ),
        ChangeLogEntry(
            version = "0.1.0",
            date = "2026-08-17",
            changes = listOf(
                "Rilis awal Mammoth",
                "Server list bergaya panel + 7 tab detail",
                "Foreground service & wake lock (keep-alive)",
                "File manager lokal",
                "Buat bot baru dengan pilihan runtime",
                "Backup konfigurasi ke JSON",
            ),
        ),
        ChangeLogEntry(
            version = "0.0.9",
            date = "2026-08-10",
            changes = listOf(
                "Prototipe UI internal",
                "Eksplorasi tema flat Pterodactyl",
            ),
        ),
    )
}
