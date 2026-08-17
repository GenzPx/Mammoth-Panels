package dae.mammoth.id.ui.navigation

/** Central destination list for the NavHost. */
object Routes {
    const val SERVERS = "servers"
    const val SERVER_DETAIL = "server/{botId}"
    const val NEW_BOT = "new-bot"
    const val CREDITS = "credits"
    const val ABOUT = "about"
    const val SETTINGS = "settings"
    const val FILES = "files"
    const val DASHBOARD = "dashboard"
    const val CONSOLE = "console"
    const val LOGS = "logs"
    const val HELP = "help"
    const val CHANGELOG = "changelog"
    const val EDITOR = "editor?path={path}"
    const val DATABASES = "databases"
    const val BOT_INFO = "bot-info/{botId}"
    const val NETWORK = "network"
    const val PROCESSES = "processes"

    fun editor(path: String) = "editor?path=${android.net.Uri.encode(path)}"
    fun botInfo(botId: String) = "bot-info/$botId"

    fun serverDetail(botId: String) = "server/$botId"
}
