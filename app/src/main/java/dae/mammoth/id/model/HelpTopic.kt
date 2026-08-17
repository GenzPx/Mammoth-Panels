package dae.mammoth.id.model

/** A help / FAQ entry. */
data class HelpTopic(
    val title: String,
    val body: String,
)

object SampleHelp {
    val list = listOf(
        HelpTopic(
            "Apa itu Mammoth?",
            "Mammoth adalah bot runner yang berjalan sepenuhnya di HP Anda. Tidak ada server eksternal — selama HP tidak dimatikan dan ada koneksi, bot tetap aktif.",
        ),
        HelpTopic(
            "Kenapa bot tidak mati walau layar mati?",
            "Mammoth memakai foreground service, wake lock, dan (opsional) eksklusi baterai sehingga proses tetap hidup di latar belakang.",
        ),
        HelpTopic(
            "Bagaimana cara menambah bot?",
            "Gunakan menu 'Buat Bot Baru', pilih runtime (Node.js/Python/Bun), dan tentukan entry point script Anda di File Manager.",
        ),
        HelpTopic(
            "Apakah data saya dikirim keluar?",
            "Tidak. Semua script dan konfigurasi tersimpan lokal di penyimpanan HP Anda.",
        ),
    )
}
