package dae.mammoth.id.ui.screens.bots

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import dae.mammoth.id.model.Bot
import dae.mammoth.id.ui.components.PanelBox
import dae.mammoth.id.ui.components.RunningBadge
import dae.mammoth.id.ui.components.cards.EmptyState
import dae.mammoth.id.ui.components.SectionLabel
import dae.mammoth.id.ui.components.StatusPill
import dae.mammoth.id.viewmodel.AppViewModelFactory
import dae.mammoth.id.viewmodel.BotListViewModel
import dae.mammoth.id.ui.theme.Accent
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Border2
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

@Composable
fun ServerListScreen(
    factory: AppViewModelFactory,
    onOpenBot: (String) -> Unit,
    onOpenCredits: () -> Unit,
    onNewBot: () -> Unit,
    onOpenConsole: () -> Unit,
    onOpenLogs: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val vm: BotListViewModel = viewModel(factory = factory)
    val state by vm.uiState.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(16.dp)
    ) {
        // top bar
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                Modifier
                    .size(30.dp)
                    .background(Accent, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.PlayArrow, contentDescription = null, tint = Color(0xFF04121C))
            }
            Column {
                Text("Mammoth", color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text("Bot Runner", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Medium)
            }
            Spacer(Modifier.weight(1f))
            RunningBadge()
            Box(
                Modifier
                    .size(32.dp)
                    .background(Panel2)
                    .border(1.dp, Border, RoundedCornerShape(4.dp))
                    .clickable(onClick = onOpenSettings),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.Settings, contentDescription = "Settings", tint = TextMuted, modifier = Modifier.size(16.dp))
            }
        }

        Spacer(Modifier.height(18.dp))
        Text("Servers", color = TextPrimary, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold)
        Text(
            "${state.running} bot aktif · ${state.errors} error · uptime ${state.uptime}",
            color = TextMuted,
            fontSize = 12.5.sp,
        )

        Spacer(Modifier.height(12.dp))
        // search
        androidx.compose.material3.OutlinedTextField(
            value = state.query,
            onValueChange = { vm.setQuery(it) },
            placeholder = { Text("Cari server...", color = TextMuted) },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp)) },
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(color = TextPrimary, fontSize = 13.sp),
            modifier = Modifier.fillMaxWidth(),
        )

        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                Spacer(Modifier.height(14.dp))
                if (state.bots.isEmpty()) {
                    EmptyState("Tidak ada server", "Buat bot baru atau ubah pencarian")
                } else {
                    ServerGrid(bots = state.bots, onOpenBot = onOpenBot, onNewBot = onNewBot)
                }
                SectionLabel("Runtimes Terpasang")
                PanelBox {
                    dae.mammoth.id.model.SampleRuntimes.list.forEach { rt ->
                        RuntimeRow(rt.name, rt.version)
                    }
                }
            }
        }
    }
}

@Composable
private fun ServerGrid(bots: List<Bot>, onOpenBot: (String) -> Unit, onNewBot: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        bots.chunked(2).forEach { rowBots ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                rowBots.forEach { bot ->
                    ServerCard(bot = bot, modifier = Modifier.weight(1f), onClick = { onOpenBot(bot.id) })
                }
                if (rowBots.size == 1) {
                    NewServerCard(modifier = Modifier.weight(1f), onClick = onNewBot)
                }
            }
        }
        if (bots.isNotEmpty() && bots.size % 2 == 0) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                NewServerCard(modifier = Modifier.weight(1f), onClick = onNewBot)
            }
        }
    }
}

@Composable
private fun ServerCard(bot: Bot, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .background(Panel)
            .border(1.dp, Border, RoundedCornerShape(0.dp))
            .clickable(onClick = onClick)
            .padding(13.dp)
    ) {
        Box(
            Modifier
                .size(40.dp)
                .background(bot.avatarColor, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(bot.id.uppercase(), color = Color(0xFF04121C), fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
        }
        Spacer(Modifier.height(10.dp))
        Text(bot.name, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        Text(bot.meta, color = TextMuted, fontSize = 10.5.sp, fontFamily = FontFamily.Monospace)
        Spacer(Modifier.height(9.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .weight(1f)
                    .height(6.dp)
                    .background(Bg)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(fraction = bot.memPct.coerceIn(0, 100) / 100f)
                        .height(6.dp)
                        .background(Green)
                )
            }
            StatusPill(text = if (bot.running) "RUN" else "OFF", color = if (bot.running) Green else TextMuted)
        }
    }
}

@Composable
private fun NewServerCard(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .background(Panel)
            .border(1.dp, Border2, RoundedCornerShape(0.dp))
            .clickable(onClick = onClick)
            .padding(13.dp)
    ) {
        Box(
            Modifier
                .size(40.dp)
                .background(Panel2, RoundedCornerShape(4.dp))
                .border(1.dp, Border2, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Outlined.Add, contentDescription = "New", tint = TextMuted)
        }
        Spacer(Modifier.height(10.dp))
        Text("New Server", color = TextMuted, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Text("buat bot baru", color = TextMuted, fontSize = 10.5.sp, fontFamily = FontFamily.Monospace)
    }
}

@Composable
private fun RuntimeRow(name: String, version: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(name, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Text("terpasang", color = TextMuted, fontSize = 11.sp)
        }
        StatusPill(text = "OK", color = Green)
    }
}
