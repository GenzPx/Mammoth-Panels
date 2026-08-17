package dae.mammoth.id.ui.screens.credits

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

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dae.mammoth.id.AppInfo
import dae.mammoth.id.model.CreditEntry
import dae.mammoth.id.model.SampleCredits
import dae.mammoth.id.ui.components.PanelBox
import dae.mammoth.id.ui.components.PanelRow
import dae.mammoth.id.ui.components.SectionLabel
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

@Composable
fun CreditsScreen(onBack: () -> Unit) {
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
            Text("Credits", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Text(AppInfo.NAME, color = TextPrimary, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            Text(AppInfo.TAGLINE, color = TextMuted, fontSize = 13.sp)

            Spacer(Modifier.height(8.dp))
            Text(
                "Mammoth is a local bot runner that keeps your scripts alive on your own device. " +
                    "No external server needed — as long as the phone isn't killed and you have data, your bots stay active.",
                color = TextMuted,
                fontSize = 12.5.sp,
                lineHeight = 18.sp,
            )

            SampleCredits.list.forEach { section ->
                CreditSection(section)
            }

            Spacer(Modifier.height(12.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Panel)
                    .border(1.dp, Border, RoundedCornerShape(0.dp))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text("Mammoth v${AppInfo.VERSION_NAME} · ${AppInfo.PACKAGE_ID}", color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun CreditSection(section: CreditEntry) {
    SectionLabel(section.section)
    PanelBox {
        section.items.forEach { (k, v) ->
            PanelRow(key = k, value = v, valueColor = if (k == "Status") Green else TextMuted)
        }
    }
}
