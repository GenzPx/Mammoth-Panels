package dae.mammoth.id.ui.screens.about

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
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dae.mammoth.id.AppInfo
import dae.mammoth.id.ui.components.PanelBox
import dae.mammoth.id.ui.components.PanelRow
import dae.mammoth.id.ui.components.SectionLabel
import dae.mammoth.id.javalib.JavaCompat
import dae.mammoth.id.nativelib.NativeHelper
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.Red
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

@Composable
fun AboutScreen(
    onBack: () -> Unit,
    onOpenChangeLog: () -> Unit = {},
) {
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
            Text("About", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Text(AppInfo.NAME, color = TextPrimary, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
            Text(AppInfo.TAGLINE, color = TextMuted, fontSize = 13.sp)
            Spacer(Modifier.height(14.dp))

            SectionLabel("Arsitektur")
            PanelBox {
                PanelRow(key = "Model", value = "Panel-style, lokal di HP")
                PanelRow(key = "Daemon", value = "Foreground service")
                PanelRow(key = "Keep-alive", value = "Wake lock + notif permanen")
                PanelRow(key = "Data", value = "Lokal, tanpa server eksternal")
            }

            SectionLabel("Runtime")
            PanelBox {
                PanelRow(key = "Node.js", value = "v22.12.0")
                PanelRow(key = "Python", value = "3.12.1")
                PanelRow(key = "Bun", value = "opsional")
            }

            SectionLabel("Native (C / NDK)")
            PanelBox {
                PanelRow(key = "Library", value = "libmammoth_native.so")
                PanelRow(key = "Version", value = NativeHelper.version)
                PanelRow(key = "Arch", value = NativeHelper.arch)
                PanelRow(key = "Status", value = if (NativeHelper.loaded) "termuat" else "tidak termuat", valueColor = if (NativeHelper.loaded) Green else Red)
            }

            SectionLabel("Java Runtime")
            PanelBox {
                PanelRow(key = "Java", value = JavaCompat.javaVersion())
                PanelRow(key = "Vendor", value = JavaCompat.javaVendor())
                PanelRow(key = "Prosesor", value = "${JavaCompat.availableProcessors()} core")
                PanelRow(key = "Heap", value = "${JavaCompat.heapMegabytes()} MB")
            }

            SectionLabel("Lisensi")
            PanelBox {
                PanelRow(key = dae.mammoth.id.model.SampleLicense.info.name, value = dae.mammoth.id.model.SampleLicense.info.summary)
            }

            Spacer(Modifier.height(12.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(dae.mammoth.id.ui.theme.Accent)
                    .clickable { onOpenChangeLog() }
                    .padding(vertical = 11.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text("Lihat Riwayat Versi", color = Color(0xFF04121C), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(dae.mammoth.id.ui.theme.Panel)
                    .border(1.dp, Border, RoundedCornerShape(0.dp))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text("v${AppInfo.VERSION_NAME} · code ${AppInfo.VERSION_CODE}", color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}
