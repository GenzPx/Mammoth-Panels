package dae.mammoth.id.ui.screens.bots

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Description


import androidx.compose.material.icons.outlined.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dae.mammoth.id.model.ConsoleLine
import dae.mammoth.id.model.SampleAllocations
import dae.mammoth.id.model.SampleDatabases
import dae.mammoth.id.model.SampleSchedules
import dae.mammoth.id.model.SampleVariables
import dae.mammoth.id.model.ServerTab
import dae.mammoth.id.ui.components.PanelBox
import dae.mammoth.id.ui.components.PanelHeader
import dae.mammoth.id.ui.components.PanelRow
import dae.mammoth.id.ui.components.table.DataTable
import dae.mammoth.id.ui.components.StatusPill
import dae.mammoth.id.viewmodel.AppViewModelFactory
import dae.mammoth.id.viewmodel.BotDetailViewModel
import dae.mammoth.id.ui.theme.Accent
import dae.mammoth.id.ui.theme.Accent2
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.Red
import dae.mammoth.id.ui.theme.TermBg
import dae.mammoth.id.ui.theme.TermDim
import dae.mammoth.id.ui.theme.TermText
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary
import dae.mammoth.id.ui.theme.Yellow

@Composable
fun ServerDetailScreen(
    botId: String,
    factory: AppViewModelFactory,
    onBack: () -> Unit,
    onOpenFiles: (String) -> Unit,
    onDeleted: () -> Unit = {},
) {
    val vm: BotDetailViewModel = viewModel(factory = factory)
    LaunchedEffect(botId) { vm.load(botId) }
    val state by vm.uiState.collectAsState()
    val bot = state.bot ?: return

    Column(
        Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        // header
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                Modifier
                    .size(32.dp)
                    .background(Panel2)
                    .border(1.dp, Border, RoundedCornerShape(4.dp))
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back", tint = TextMuted, modifier = Modifier.size(16.dp))
            }
            Column(Modifier.weight(1f)) {
                Text(
                    bot.name,
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                )
                Text(
                    bot.meta,
                    color = TextMuted,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                )
            }
            StatusPill(text = if (bot.running) "RUN" else "OFF", color = if (bot.running) Green else TextMuted)
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            // control bar
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Panel2)
                    .border(1.dp, Border, RoundedCornerShape(0.dp))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                PowerButton("Start", Accent, Color(0xFF04121C), Modifier.weight(1f)) { vm.start() }
                PowerButton("Restart", Accent2, Color.White, Modifier.weight(1f)) { vm.restart() }
                PowerButton("Stop", Red, Color.White, Modifier.weight(1f)) { vm.stop() }
            }

            Spacer(Modifier.height(12.dp))

            // gauges
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Gauge("CPU", "${bot.cpu}%", bot.cpu, Accent, Modifier.weight(1f))
                Gauge("MEM", "${bot.memMB} MB", bot.memPct, Green, Modifier.weight(1f))
                Gauge("DSK", "${bot.dskGB} GB", bot.dskPct, Yellow, Modifier.weight(1f))
            }

            Spacer(Modifier.height(12.dp))

            // per-server tabs
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Panel2)
                    .border(1.dp, Border, RoundedCornerShape(0.dp))
                    .padding(6.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                ServerTab.entries.forEach { tab ->
                    val active = state.selectedTab == tab
                    Box(
                        Modifier
                            .background(if (active) Accent else Color.Transparent)
                            .border(1.dp, if (active) Accent else Color.Transparent, RoundedCornerShape(4.dp))
                            .clickable { vm.selectTab(tab) }
                            .padding(horizontal = 12.dp, vertical = 9.dp),
                    ) {
                        Text(
                            tab.title,
                            color = if (active) Color(0xFF04121C) else TextMuted,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            when (state.selectedTab) {
                ServerTab.Console -> ConsolePane(state.consoleLines)
                ServerTab.FileManager -> FilesPane(bot.id, onOpenFiles)
                ServerTab.Databases -> DatabasesPane()
                ServerTab.Schedules -> SchedulesPane()
                ServerTab.Network -> NetworkPane()
                ServerTab.Startup -> StartupPane(bot.entryPoint)
                ServerTab.Settings -> SettingsPane(
                    onRename = { /* rename flow could open a dialog; scaffold keeps it as a toast-like no-op */ },
                    onReinstall = { /* reinstall would reset the bot folder */ },
                    onDelete = {
                        vm.remove()
                        onDeleted()
                    },
                )
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun PowerButton(text: String, bg: Color, colorOn: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier
            .background(bg)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Text(text, color = colorOn, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun Gauge(label: String, value: String, pct: Int, fill: Color, modifier: Modifier = Modifier) {
    Column(
        modifier
            .background(Panel)
            .border(1.dp, Border, RoundedCornerShape(0.dp))
            .padding(10.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                label.uppercase(),
                color = TextMuted,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false),
            )
            Text(
                value,
                color = TextPrimary,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(value, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily.Monospace)
        Spacer(Modifier.height(8.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Bg)
        ) {
            Box(Modifier.fillMaxWidth(fraction = pct.coerceIn(0, 100) / 100f).height(8.dp).background(fill))
        }
    }
}

// ---------------- TABS ----------------

@Composable
private fun ConsolePane(lines: List<ConsoleLine>) {
    Column {
        PanelBox {
            Column(
                Modifier
                    .background(TermBg)
                    .padding(12.dp)
            ) {
                lines.forEach { l ->
                    Text(l.text, color = l.color, fontSize = 11.sp, fontFamily = FontFamily.Monospace, lineHeight = 18.sp)
                }
                Text("  root@wa-main:~$ █", color = TermText, fontSize = 11.sp, fontFamily = FontFamily.Monospace, lineHeight = 18.sp)
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .background(Panel)
                .border(1.dp, Border, RoundedCornerShape(0.dp))
                .padding(horizontal = 12.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("root@wa-main:~$", color = Green, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
            Text("ketik perintah ke bot...", color = TermDim, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
        }
    }
}

@Composable
private fun FilesPane(botId: String, onOpenFiles: (String) -> Unit) {
    PanelBox {
        PanelHeader(title = "", titleStyle = {
            Text("/storage/emulated/0/Mammoth/$botId", color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
        }, action = "Buka File Manager", onAction = { onOpenFiles(botId) })
        PanelRow(leading = { Icon(Icons.Outlined.Description, contentDescription = null, tint = Accent, modifier = Modifier.size(16.dp)) }, key = "src", value = "—")
        PanelRow(leading = { Icon(Icons.Outlined.Description, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp)) }, key = "index.js", value = "3.2 KB")
        PanelRow(leading = { Icon(Icons.Outlined.Description, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp)) }, key = "package.json", value = "412 B")
        PanelRow(leading = { Icon(Icons.Outlined.Description, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp)) }, key = "config.json", value = "224 B")
    }
}

@Composable
private fun DatabasesPane() {
    PanelBox {
        PanelHeader("Databases", action = "+ Buat Database")
        DataTable(
            headers = listOf("Nama", "Host", "User", "Status"),
            rows = SampleDatabases.list.map { db ->
                listOf(
                    db.name to null,
                    db.host to TextMuted,
                    db.user to TextMuted,
                    (if (db.healthy) "OK" else "—") to (if (db.healthy) Green else TextMuted),
                )
            },
        )
    }
}

@Composable
private fun SchedulesPane() {
    PanelBox {
        PanelHeader("Schedules", action = "+ Jadwal Baru")
        DataTable(
            headers = listOf("Nama", "Jadwal (cron)", "Status"),
            rows = SampleSchedules.list.map { s ->
                listOf(
                    s.name to null,
                    s.cron to TextMuted,
                    (if (s.active) "Active" else "Paused") to (if (s.active) Green else TextMuted),
                )
            },
        )
    }
}

@Composable
private fun NetworkPane() {
    PanelBox {
        PanelHeader("Allocations", action = "+ Tambah")
        DataTable(
            headers = listOf("Alamat", "Port", "Role"),
            rows = SampleAllocations.list.map { a ->
                listOf(
                    a.address to null,
                    a.port.toString() to TextMuted,
                    (if (a.primary) "Primary" else "Alias") to (if (a.primary) Green else TextMuted),
                )
            },
        )
    }
}

@Composable
private fun StartupPane(entryPoint: String) {
    Column {
        PanelBox {
            PanelHeader("Startup Command", action = "Simpan")
            Text(
                "node \${HOME}/$entryPoint --session=wa-main",
                color = TermText,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.background(TermBg).fillMaxWidth().padding(12.dp),
            )
        }
        Spacer(Modifier.height(10.dp))
        PanelBox {
            PanelHeader("Variables", action = "+")
            SampleVariables.list.forEach { v ->
                PanelRow(key = v.key, value = v.value, valueColor = TextMuted)
            }
        }
    }
}

@Composable
private fun SettingsPane(
    onRename: () -> Unit,
    onReinstall: () -> Unit,
    onDelete: () -> Unit,
) {
    Column {
        PanelBox {
            Column {
                SettingToggle("Foreground service", "Notif permanen supaya gak gampang di-kill", true)
                SettingToggle("Wake lock", "Jaga CPU saat layar mati", true)
                SettingToggle("Auto-restart saat crash", "Nyala ulang otomatis kalau error", true)
                SettingToggle("Eksklusi baterai (Doze)", "Jangan biarkan Android menidurkan daemon", false)
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ActionButton("Rename", Accent, Color(0xFF04121C), Modifier.weight(1f), onClick = onRename)
            ActionButton("Reinstall", Accent2, Color.White, Modifier.weight(1f), onClick = onReinstall)
            ActionButton("Hapus", Red, Color.White, Modifier.weight(1f), onClick = onDelete) {
                Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
            }
        }
    }
}

@Composable
private fun SettingToggle(label: String, desc: String, on: Boolean) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(label, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Text(desc, color = TextMuted, fontSize = 11.sp)
        }
        Box(
            Modifier
                .width(40.dp)
                .height(22.dp)
                .background(if (on) Accent else Bg)
                .border(1.dp, if (on) Accent else Border, RoundedCornerShape(0.dp))
                .padding(horizontal = 3.dp, vertical = 4.dp),
        ) {
            Box(
                Modifier
                    .size(14.dp)
                    .background(Color.White)
                    .align(if (on) Alignment.CenterEnd else Alignment.CenterStart)
            )
        }
    }
}

@Composable
private fun ActionButton(text: String, bg: Color, fg: Color, modifier: Modifier = Modifier, onClick: () -> Unit, icon: (@Composable () -> Unit)? = null) {
    Row(
        modifier
            .background(bg)
            .clickable(onClick = onClick)
            .padding(vertical = 9.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) { icon(); Spacer(Modifier.width(5.dp)) }
        Text(text, color = fg, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}
