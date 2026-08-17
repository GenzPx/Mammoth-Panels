package dae.mammoth.id.model

/** A help / FAQ entry. */
data class HelpTopic(
    val title: String,
    val body: String,
)

object SampleHelp {
    val list = listOf(
        HelpTopic(
            "Mulai cepat (Quick Start)",
            "1) Buka Dashboard lalu tekan 'Buat Bot Baru'.\n2) Isi nama & pilih runtime (Node.js / Python / Bun).\n3) Bot muncul di daftar Server — tekan untuk masuk ke panel.\n4) Masukkan script Anda lewat File Manager, lalu tekan Start.",
        ),
        HelpTopic(
            "Cara menjalankan bot",
            "Buka detail server, lalu gunakan tombol Start / Restart / Stop di bagian atas. Tab Console menampilkan output dan kolom untuk mengetik perintah.",
        ),
        HelpTopic(
            "Menjaga bot tetap aktif",
            "Mammoth memakai foreground service + wake lock. Untuk daya tahan maksimal, di Pengaturan aktifkan 'Eksklusi baterai (Doze)' dan izinkan notifikasi.",
        ),
        HelpTopic(
            "Menambah script / file",
            "Buka File Manager dari dashboard, lalu upload atau buat file baru di folder bot. File teks bisa diedit langsung lewat editor bawaan.",
        ),
        HelpTopic(
            "Cek koneksi",
            "Gunakan 'Network Diagnostics' untuk memastikan HP bisa menjangkau WhatsApp, Telegram, Discord, dan GitHub.",
        ),
        HelpTopic(
            "Apakah data saya dikirim keluar?",
            "Tidak. Semua script dan konfigurasi tersimpan lokal di penyimpanan HP Anda, tanpa server eksternal.",
        ),
    )
}
