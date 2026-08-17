package dae.mammoth.id.ui.screens.dashboard

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

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.NetworkCheck
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dae.mammoth.id.ui.components.SectionLabel
import dae.mammoth.id.ui.components.cards.QuickAction
import dae.mammoth.id.ui.components.cards.StatCard
import dae.mammoth.id.viewmodel.AppViewModelFactory
import dae.mammoth.id.viewmodel.DashboardViewModel
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

@Composable
fun DashboardScreen(
    factory: AppViewModelFactory,
    onBack: () -> Unit,
    onNewBot: () -> Unit,
    onOpenServers: () -> Unit,
    onOpenFiles: () -> Unit,
    onOpenNetwork: () -> Unit,
    onOpenHelp: () -> Unit = {},
) {
    val vm: DashboardViewModel = viewModel(factory = factory)
    LaunchedEffect(Unit) { vm.refresh() }
    val state by vm.uiState.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
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
                    .clickable { onBack() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.List, contentDescription = "Menu", tint = TextMuted, modifier = Modifier.size(16.dp))
            }
            Text("Dashboard", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .size(32.dp)
                    .background(Panel2)
                    .border(1.dp, Border, RoundedCornerShape(4.dp))
                    .clickable { vm.refresh() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.Refresh, contentDescription = "Refresh", tint = TextMuted, modifier = Modifier.size(16.dp))
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Text("Overview", color = TextPrimary, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold)
            Text("Ringkasan daemon · baterai ${state.battery}% · ${state.network}", color = TextMuted, fontSize = 12.5.sp)

            Spacer(Modifier.height(14.dp))
            state.metrics.chunked(2).forEach { rowMetrics ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    rowMetrics.forEach { m ->
                        StatCard(m, Modifier.weight(1f))
                    }
                    if (rowMetrics.size == 1) Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(10.dp))
            }

            SectionLabel("Aksi Cepat")
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                QuickAction("Buat Bot Baru", { Icon(Icons.Outlined.Add, contentDescription = null, tint = TextPrimary) }, onNewBot)
                QuickAction("Kelola Servers", { Icon(Icons.Outlined.List, contentDescription = null, tint = TextPrimary) }, onOpenServers)
                QuickAction("File Manager", { Icon(Icons.Outlined.Folder, contentDescription = null, tint = TextPrimary) }, onOpenFiles)
                QuickAction("Network Diagnostics", { Icon(Icons.Outlined.NetworkCheck, contentDescription = null, tint = TextPrimary) }, onOpenNetwork)
                QuickAction("Bantuan / Panduan", { Icon(Icons.Outlined.HelpOutline, contentDescription = null, tint = TextPrimary) }, onOpenHelp)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}
