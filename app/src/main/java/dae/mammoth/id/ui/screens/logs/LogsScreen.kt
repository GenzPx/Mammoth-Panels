package dae.mammoth.id.ui.screens.logs

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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dae.mammoth.id.model.LogEntry
import dae.mammoth.id.viewmodel.AppViewModelFactory
import dae.mammoth.id.viewmodel.LogViewModel
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border

import dae.mammoth.id.ui.theme.Panel2

import dae.mammoth.id.ui.theme.TermBg
import dae.mammoth.id.ui.theme.TermErr
import dae.mammoth.id.ui.theme.TermInfo
import dae.mammoth.id.ui.theme.TermOk
import dae.mammoth.id.ui.theme.TermText
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

@Composable
fun LogsScreen(
    factory: AppViewModelFactory,
    onBack: () -> Unit,
) {
    val vm: LogViewModel = viewModel(factory = factory)
    LaunchedEffect(Unit) { vm.collect() }
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
            Text("Log / Diagnostics", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .size(32.dp)
                    .background(Panel2)
                    .border(1.dp, Border, RoundedCornerShape(4.dp))
                    .clickable { vm.collect() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.DeleteSweep, contentDescription = "Refresh", tint = TextMuted, modifier = Modifier.size(16.dp))
            }
        }

        LazyColumn(
            Modifier.fillMaxSize().padding(horizontal = 16.dp),
        ) {
            items(state.entries) { entry ->
                LogRow(entry)
            }
        }
    }
}

@Composable
private fun LogRow(entry: LogEntry) {
    val color = when (entry.level) {
        "OK" -> TermOk
        "ERROR" -> TermErr
        "INFO" -> TermInfo
        else -> TermText
    }
    Row(
        Modifier
            .fillMaxWidth()
            .background(TermBg)
            .border(1.dp, Border, RoundedCornerShape(0.dp))
            .padding(horizontal = 12.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(entry.time, color = TextMuted, fontSize = 10.sp, fontFamily = FontFamily.Monospace, modifier = Modifier.width(60.dp))
        Text(entry.level, color = color, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, modifier = Modifier.width(48.dp))
        Text(entry.message, color = TermText, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
    }
}
