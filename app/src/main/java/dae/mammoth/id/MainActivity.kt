package dae.mammoth.id

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dae.mammoth.id.data.repository.BotRepository
import dae.mammoth.id.model.Bot
import dae.mammoth.id.service.BotService
import dae.mammoth.id.ui.navigation.Routes
import dae.mammoth.id.ui.screens.about.AboutScreen
import dae.mammoth.id.ui.screens.bots.NewBotScreen
import dae.mammoth.id.ui.screens.bots.ServerDetailScreen
import dae.mammoth.id.ui.screens.bots.ServerListScreen
import dae.mammoth.id.ui.screens.changelog.ChangeLogScreen
import dae.mammoth.id.ui.screens.console.ConsoleScreen
import dae.mammoth.id.ui.screens.credits.CreditsScreen
import dae.mammoth.id.ui.screens.databases.DatabasesScreen
import dae.mammoth.id.ui.screens.editor.FileEditorScreen
import dae.mammoth.id.ui.screens.bots.BotInfoScreen
import dae.mammoth.id.ui.screens.dashboard.DashboardScreen
import dae.mammoth.id.ui.screens.files.FileManagerScreen
import dae.mammoth.id.ui.screens.help.HelpScreen
import dae.mammoth.id.ui.screens.logs.LogsScreen
import dae.mammoth.id.ui.screens.network.NetworkScreen
import dae.mammoth.id.ui.screens.processes.ProcessesScreen
import dae.mammoth.id.ui.screens.settings.SettingsScreen
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.viewmodel.AppViewModelFactory

class MainActivity : ComponentActivity() {

    private val botRepository = BotRepository()

    private val factory: AppViewModelFactory by lazy {
        AppViewModelFactory(botRepository, applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestNotificationPermissionIfNeeded()
        startBotService()
        setContent {
            MammothNavHost()
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 42)
            }
        }
    }

    private fun startBotService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(this, BotService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
    }

    @Composable
    private fun MammothNavHost() {
        val nav = rememberNavController()
        val accentState = dae.mammoth.id.data.store.SettingsDataStore(applicationContext).accentKey
            ?.let { dae.mammoth.id.ui.theme.AccentKey.entries.firstOrNull { a -> a.name == it } }
            ?: dae.mammoth.id.ui.theme.AccentKey.Cyan
        dae.mammoth.id.ui.theme.MammothTheme(accent = accentState) {
            NavHost(navController = nav, startDestination = Routes.DASHBOARD) {
                composable(Routes.DASHBOARD) {
                    DashboardScreen(
                        factory = factory,
                        onBack = { nav.navigate(Routes.SERVERS) },
                        onNewBot = { nav.navigate(Routes.NEW_BOT) },
                        onOpenServers = { nav.navigate(Routes.SERVERS) },
                    onOpenFiles = { nav.navigate(Routes.FILES) },
                    onOpenNetwork = { nav.navigate(Routes.NETWORK) },
                )
            }
            composable(Routes.SERVERS) {
                    ServerListScreen(
                        factory = factory,
                        onOpenBot = { id -> nav.navigate(Routes.serverDetail(id)) },
                        onOpenCredits = { nav.navigate(Routes.CREDITS) },
                        onNewBot = { nav.navigate(Routes.NEW_BOT) },
                        onOpenConsole = { nav.navigate(Routes.CONSOLE) },
                        onOpenLogs = { nav.navigate(Routes.LOGS) },
                        onOpenSettings = { nav.navigate(Routes.SETTINGS) },
                    )
                }
                composable(Routes.SERVER_DETAIL) { entry ->
                    val botId = entry.arguments?.getString("botId") ?: "wa"
                    ServerDetailScreen(
                        botId = botId,
                        factory = factory,
                        onBack = { nav.popBackStack() },
                        onOpenFiles = { nav.navigate(Routes.FILES) },
                    )
                }
                composable(Routes.FILES) {
                    FileManagerScreen(
                        factory = factory,
                        onBack = { nav.popBackStack() },
                        onOpenFile = { path -> nav.navigate(Routes.editor(path)) },
                    )
                }
                composable(Routes.NEW_BOT) {
                    NewBotScreen(
                        onBack = { nav.popBackStack() },
                        onCreate = { id, name, runtime ->
                            botRepository.add(
                                Bot(
                                    id = id,
                                    name = name.ifEmpty { "Bot $id" },
                                    meta = "$id · ${runtime.displayName.lowercase()}",
                                    avatarColor = Green,
                                    runtime = runtime,
                                )
                            )
                            nav.popBackStack()
                        },
                    )
                }
                composable(Routes.CREDITS) {
                    CreditsScreen(onBack = { nav.popBackStack() })
                }
                composable(Routes.ABOUT) {
                    AboutScreen(onBack = { nav.popBackStack() }, onOpenChangeLog = { nav.navigate(Routes.CHANGELOG) })
                }
                composable(Routes.SETTINGS) {
                    SettingsScreen(factory = factory, onBack = { nav.popBackStack() })
                }
                composable(Routes.CONSOLE) {
                    ConsoleScreen(onBack = { nav.popBackStack() })
                }
                composable(Routes.LOGS) {
                    LogsScreen(factory = factory, onBack = { nav.popBackStack() })
                }
                composable(Routes.HELP) {
                    HelpScreen(onBack = { nav.popBackStack() })
                }
                composable(Routes.CHANGELOG) {
                    ChangeLogScreen(onBack = { nav.popBackStack() })
                }
                composable(
                    Routes.EDITOR,
                    arguments = listOf(androidx.navigation.navArgument("path") { type = androidx.navigation.NavType.StringType }),
                ) { entry ->
                    val path = entry.arguments?.getString("path") ?: ""
                    FileEditorScreen(filePath = path, onBack = { nav.popBackStack() })
                }
                composable(Routes.DATABASES) {
                    DatabasesScreen(onBack = { nav.popBackStack() })
                }
                composable(Routes.BOT_INFO) { entry ->
                    val botId = entry.arguments?.getString("botId") ?: "wa"
                    BotInfoScreen(botId = botId, context = applicationContext, onBack = { nav.popBackStack() })
                }
                composable(Routes.NETWORK) {
                    NetworkScreen(factory = factory, onBack = { nav.popBackStack() })
                }
                composable(Routes.PROCESSES) {
                    ProcessesScreen(factory = factory, onBack = { nav.popBackStack() })
                }
            }
        }
    }
}
