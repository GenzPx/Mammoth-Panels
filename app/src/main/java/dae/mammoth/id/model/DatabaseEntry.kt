package dae.mammoth.id.model

/** A database allocation for a bot, mirroring Pterodactyl's Databases tab. */
data class DatabaseEntry(
    val name: String,
    val host: String,
    val user: String,
    val healthy: Boolean = true,
)

object SampleDatabases {
    val list = listOf(
        DatabaseEntry("wa-main", "127.0.0.1", "wa", true),
        DatabaseEntry("tg-notif", "127.0.0.1", "tg", true),
        DatabaseEntry("session_store", "127.0.0.1", "sess", false),
    )
}
