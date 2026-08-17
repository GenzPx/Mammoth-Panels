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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dae.mammoth.id.data.repository.ProcessRepository
import dae.mammoth.id.model.ResourceGenerator
import dae.mammoth.id.model.SampleBots
import dae.mammoth.id.ui.components.PanelBox
import dae.mammoth.id.ui.components.PanelRow
import dae.mammoth.id.ui.components.SectionLabel

import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.Red
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary
import dae.mammoth.id.util.Formatters

/**
 * Read-only detail screen for a single bot: resource usage, process status,
 * and configuration summary. Opened from the server detail's header.
 */
@Composable
fun BotInfoScreen(
    botId: String,
    context: android.content.Context,
    onBack: () -> Unit,
) {
    val bot = SampleBots.list.firstOrNull { it.id == botId } ?: SampleBots.list.first()
    val resources = ResourceGenerator.forBot(botId)
    val processRepo = ProcessRepository(context)
    val status = processRepo.status(botId)

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
            Text("Info Bot", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .size(32.dp)
                    .background(Panel2)
                    .border(1.dp, Border, RoundedCornerShape(4.dp))
                    .clickable { processRepo.start(bot) },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.Refresh, contentDescription = "Start", tint = TextMuted, modifier = Modifier.size(16.dp))
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Text(bot.name, color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Text(bot.meta, color = TextMuted, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
            Spacer(Modifier.height(4.dp))
            Text(
                if (bot.running) "Berjalan" else "Berhenti",
                color = if (bot.running) Green else Red,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )

            SectionLabel("Resource")
            PanelBox {
                PanelRow(key = "CPU", value = "${resources.cpuPercent}%")
                PanelRow(key = "Memory", value = "${resources.memMB} MB")
                PanelRow(key = "Disk", value = Formatters.bytes((resources.diskGB * 1024f).toLong()))
            }

            SectionLabel("Proses")
            PanelBox {
                PanelRow(key = "Status", value = if (status?.running == true) "aktif" else "idle", valueColor = if (status?.running == true) Green else TextMuted)
                PanelRow(key = "Uptime", value = Formatters.uptime(status?.uptimeSeconds ?: 0L))
            }

            SectionLabel("Konfigurasi")
            PanelBox {
                PanelRow(key = "Runtime", value = bot.runtime.displayName)
                PanelRow(key = "Entry point", value = bot.entryPoint)
                PanelRow(key = "ID", value = bot.id)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}
