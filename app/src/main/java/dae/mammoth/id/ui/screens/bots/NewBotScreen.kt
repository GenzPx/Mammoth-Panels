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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dae.mammoth.id.model.BotRuntime
import dae.mammoth.id.ui.components.SectionLabel
import dae.mammoth.id.ui.theme.Accent
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border

import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary
import dae.mammoth.id.util.Formatters

@Composable
fun NewBotScreen(
    onBack: () -> Unit,
    onCreate: (String, String, BotRuntime) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var runtime by remember { mutableStateOf(BotRuntime.NodeJs) }

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
            Text("Buat Bot Baru", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            SectionLabel("Nama Bot")
            androidx.compose.material3.OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("contoh: WhatsApp Auto-Reply", color = TextMuted) },
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(color = TextPrimary, fontSize = 13.sp),
                modifier = Modifier.fillMaxWidth(),
            )

            SectionLabel("Runtime")
            dae.mammoth.id.ui.components.PanelBox {
                BotRuntime.entries.forEach { r ->
                    val selected = runtime == r
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(if (selected) Panel2 else Panel)
                            .border(1.dp, if (selected) Accent else Border, RoundedCornerShape(0.dp))
                            .clickable { runtime = r }
                            .padding(horizontal = 14.dp, vertical = 13.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Box(
                            Modifier
                                .size(14.dp)
                                .background(if (selected) Accent else Color.Transparent)
                                .border(1.dp, if (selected) Accent else TextMuted, RoundedCornerShape(2.dp)),
                        )
                        Text(r.displayName, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.weight(1f))
                        Text(r.defaultCmd, color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Accent)
                    .clickable {
                        val id = Formatters.slug(name).ifEmpty { "bot-${System.currentTimeMillis() % 10000}" }
                        onCreate(id, name, runtime)
                    }
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text("Buat Bot", color = Color(0xFF04121C), fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


