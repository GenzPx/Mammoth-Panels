package dae.mammoth.id.ui.screens.processes

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Stop
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

import dae.mammoth.id.ui.components.SectionLabel
import dae.mammoth.id.viewmodel.AppViewModelFactory
import dae.mammoth.id.viewmodel.ProcessMonitorViewModel
import dae.mammoth.id.viewmodel.ProcessUiEntry

import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.Red
import dae.mammoth.id.ui.theme.TermBg
import dae.mammoth.id.ui.theme.TermText
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary
import dae.mammoth.id.util.Formatters

/** Live view of the spawned bot processes and their output. */
@Composable
fun ProcessesScreen(
    factory: AppViewModelFactory,
    onBack: () -> Unit,
) {
    val vm: ProcessMonitorViewModel = viewModel(factory = factory)
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
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back", tint = TextMuted, modifier = Modifier.size(16.dp))
            }
            Text("Proses", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .size(32.dp)
                    .background(Green, RoundedCornerShape(4.dp))
                    .clickable { vm.startAll() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.PlayArrow, contentDescription = "Start all", tint = Color(0xFF04121C), modifier = Modifier.size(16.dp))
            }
            Box(
                Modifier
                    .size(32.dp)
                    .background(Red, RoundedCornerShape(4.dp))
                    .clickable { vm.stopAll() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.Stop, contentDescription = "Stop all", tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }

        LazyColumn(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            item {
                SectionLabel("Proses Berjalan")
                Text("${state.entries.count { it.running }} aktif", color = TextMuted, fontSize = 11.sp)
            }
            items(state.entries) { entry ->
                ProcessCard(entry, onStop = { vm.stop(entry.id) })
            }
            item { Spacer(Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun ProcessCard(entry: ProcessUiEntry, onStop: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Panel)
            .border(1.dp, Border, RoundedCornerShape(0.dp))
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(8.dp)
                    .background(if (entry.running) Green else Red),
            )
            Spacer(Modifier.padding(horizontal = 4.dp))
            Text(entry.name, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            if (entry.running) {
                Box(
                    Modifier
                        .size(28.dp)
                        .background(Red, RoundedCornerShape(4.dp))
                        .clickable(onClick = onStop),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Outlined.Stop, contentDescription = "Stop", tint = Color.White, modifier = Modifier.size(14.dp))
                }
            }
        }
        Text(entry.command, color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
        Text("uptime ${Formatters.uptime(entry.uptimeSeconds)}", color = TextMuted, fontSize = 10.5.sp, fontFamily = FontFamily.Monospace)
        Spacer(Modifier.height(8.dp))
        Text(
            "> ${entry.lastOutput}",
            color = TermText,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth().background(TermBg).padding(8.dp),
        )
    }
}
