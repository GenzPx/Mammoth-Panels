package dae.mammoth.id.model

/** Common console command presets shown to the user. */
data class CommandPreset(
    val label: String,
    val command: String,
)

object SamplePresets {
    val list = listOf(
        CommandPreset("Status", "status"),
        CommandPreset("Restart bot", "restart"),
        CommandPreset("Hentikan loop", "stop"),
        CommandPreset("Tampilkan config", "config"),
        CommandPreset("Jeda 5 detik", "sleep 5"),
    )
}
