package dae.mammoth.id.model

/** Per-server tab pages, mirroring Pterodactyl's server navigation. */
enum class ServerTab(val title: String) {
    Console("Console"),
    FileManager("File Manager"),
    Databases("Databases"),
    Schedules("Schedules"),
    Network("Network"),
    Startup("Startup"),
    Settings("Settings")
}
