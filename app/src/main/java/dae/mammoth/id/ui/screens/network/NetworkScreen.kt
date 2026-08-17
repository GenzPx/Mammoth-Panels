package dae.mammoth.id.ui.screens.network

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

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dae.mammoth.id.model.NetworkTest
import dae.mammoth.id.model.ProbeStatus
import dae.mammoth.id.ui.components.cards.InfoCard
import dae.mammoth.id.ui.components.SectionLabel
import dae.mammoth.id.viewmodel.AppViewModelFactory
import dae.mammoth.id.viewmodel.NetworkViewModel
import dae.mammoth.id.ui.theme.Accent
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.Red
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

/** Connectivity diagnostics: transport status + endpoint latency tests. */
@Composable
fun NetworkScreen(
    factory: AppViewModelFactory,
    onBack: () -> Unit,
) {
    val vm: NetworkViewModel = viewModel(factory = factory)
    LaunchedEffect(Unit) { vm.runTests() }
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
            Text("Network", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .size(32.dp)
                    .background(Panel2)
                    .border(1.dp, Border, RoundedCornerShape(4.dp))
                    .clickable { vm.runTests() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.Refresh, contentDescription = "Run tests", tint = TextMuted, modifier = Modifier.size(16.dp))
            }
        }

        LazyColumn(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            item {
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Outlined.NetworkCheck, contentDescription = null, tint = if (state.connected) Green else Red, modifier = Modifier.size(20.dp))
                    Column {
                        Text(if (state.connected) "Terhubung" else "Tidak terhubung", color = if (state.connected) Green else Red, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Text("Transport: ${state.transport}", color = TextMuted, fontSize = 11.sp)
                    }
                }
                Spacer(Modifier.height(8.dp))
                InfoCard("Catatan", "Bot membutuhkan koneksi aktif. Tes di bawah mengecek jangkauan ke server platform populer.")
                SectionLabel("Endpoint Latency")
            }
            items(state.tests) { test ->
                TestRow(test)
            }
            item { Spacer(Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun TestRow(test: NetworkTest) {
    val statusColor = when (test.status) {
        ProbeStatus.Pending, ProbeStatus.Running -> Accent
        ProbeStatus.Ok -> Green
        ProbeStatus.Fail -> Red
    }
    Row(
        Modifier
            .fillMaxWidth()
            .background(Panel)
            .border(1.dp, Border, RoundedCornerShape(0.dp))
            .padding(horizontal = 14.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(test.label, color = TextPrimary, fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold)
            Text("${test.host}:${test.port}", color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
        }
        Text(
            when (test.status) {
                ProbeStatus.Running -> "menguji..."
                ProbeStatus.Ok -> test.detail
                ProbeStatus.Fail -> "gagal"
                ProbeStatus.Pending -> "menunggu"
            },
            color = statusColor,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
        )
    }
}
