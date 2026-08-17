package dae.mammoth.id.model

/** A startup environment variable, mirroring Pterodactyl's Startup tab. */
data class StartupVariable(
    val key: String,
    val value: String,
    val sensitive: Boolean = false,
)

object SampleVariables {
    val list = listOf(
        StartupVariable("NODE_ENV", "production"),
        StartupVariable("BOT_TOKEN", "••••••••", sensitive = true),
        StartupVariable("RATE_LIMIT", "2000ms"),
    )
}
