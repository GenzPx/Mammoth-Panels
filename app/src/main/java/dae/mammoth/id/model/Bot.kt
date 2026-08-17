package dae.mammoth.id.model

import androidx.compose.ui.graphics.Color
import dae.mammoth.id.ui.theme.Accent2
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.Yellow

/** Runtime environments the runner can target. */
enum class BotRuntime(val displayName: String, val defaultCmd: String) {
    NodeJs("Node.js", "node"),
    Python("Python", "python3"),
    Bun("Bun", "bun"),
    Shell("Shell", "sh"),
}

/** A single managed bot process, analogous to a Pterodactyl "server". */
data class Bot(
    val id: String,
    val name: String,
    val meta: String,
    val avatarColor: Color,
    val runtime: BotRuntime = BotRuntime.NodeJs,
    val running: Boolean = true,
    val cpu: Int = 0,
    val memMB: Int = 0,
    val memPct: Int = 0,
    val dskGB: Float = 0f,
    val dskPct: Int = 0,
    val entryPoint: String = "index.js",
    val createdAt: Long = 0L,
)

/** Sample data used while the repository/preferences are still scaffolded. */
object SampleBots {
    val list = listOf(
        Bot("wa", "WhatsApp Auto-Reply", "wa-main · nodejs", Green, BotRuntime.NodeJs, true, 34, 412, 48, 1.2f, 21, "index.js"),
        Bot("tg", "Telegram Notifier", "tg-notif · python", Accent2, BotRuntime.Python, true, 12, 186, 19, 0.8f, 8, "bot.py"),
        Bot("dc", "Discord Music", "dc-music · nodejs", Yellow, BotRuntime.NodeJs, false, 0, 0, 0, 0.4f, 14, "index.js"),
    )
}
