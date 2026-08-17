package dae.mammoth.id.ui.screens.settings

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
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
import dae.mammoth.id.ui.components.PanelBox
import dae.mammoth.id.ui.components.SectionLabel
import dae.mammoth.id.viewmodel.AppViewModelFactory
import dae.mammoth.id.viewmodel.SettingsViewModel
import dae.mammoth.id.ui.theme.Accent
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

@Composable
fun SettingsScreen(
    factory: AppViewModelFactory,
    onBack: () -> Unit,
) {
    val vm: SettingsViewModel = viewModel(factory = factory)
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
            Text("Pengaturan", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            SectionLabel("Perilaku Daemon")
            PanelBox {
                SettingToggle("Foreground service", "Notif permanen supaya gak gampang di-kill", state.foregroundService) { vm.setForegroundService(it) }
                SettingToggle("Wake lock", "Jaga CPU saat layar mati", state.wakeLock) { vm.setWakeLock(it) }
                SettingToggle("Auto-restart saat crash", "Nyala ulang otomatis kalau error", state.autoRestart) { vm.setAutoRestart(it) }
                SettingToggle("Eksklusi baterai (Doze)", "Jangan biarkan Android menidurkan daemon", state.batteryExclusion) { vm.setBatteryExclusion(it) }
            }

            SectionLabel("Tema / Aksen")
            PanelBox {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Warna aksen", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    Text(state.accentKey.label, color = TextMuted, fontSize = 12.sp)
                }
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    dae.mammoth.id.ui.theme.AccentKey.entries.forEach { key ->
                        val selected = key == state.accentKey
                        Box(
                            Modifier
                                .weight(1f)
                                .size(30.dp)
                                .background(key.color, RoundedCornerShape(4.dp))
                                .border(if (selected) 2.dp else 1.dp, if (selected) Color.White else Border, RoundedCornerShape(4.dp))
                                .clickable { vm.setAccent(key) },
                        )
                    }
                }
            }

            SectionLabel("Baterai & Backup")
            PanelBox {
                Row(
                    Modifier.fillMaxWidth().clickable { vm.refreshBatteryStatus() }.padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    Text("Optimasi baterai", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    Text(if (state.isIgnoringBattery) "Dikecualikan" else "Aktif", color = if (state.isIgnoringBattery) Color(0xFF3FBF6F) else TextMuted, fontSize = 12.sp)
                }
                Row(
                    Modifier.fillMaxWidth().clickable { vm.backup() }.padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    Text("Backup config", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    Text("Ekspor JSON", color = TextMuted, fontSize = 12.sp)
                }
                val backupPath = state.lastBackupPath
                if (backupPath != null) {
                    Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 6.dp)) {
                        Text("Tersimpan:", color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                    }
                    Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 6.dp)) {
                        Text(backupPath, color = TextMuted, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                    }
                }
            }

            SectionLabel("Info")
            PanelBox {
                Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp)) {
                    Text("Versi", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    Text(state.versionName, color = TextMuted, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                }
                Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp)) {
                    Text("Package", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    Text(state.packageId, color = TextMuted, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                }
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun SettingToggle(label: String, desc: String, on: Boolean, onChange: (Boolean) -> Unit) {
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
                .clickable { onChange(!on) }
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
