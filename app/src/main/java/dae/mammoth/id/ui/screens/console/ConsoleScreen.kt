package dae.mammoth.id.ui.screens.console

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material.icons.outlined.Send
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
import dae.mammoth.id.ui.theme.Accent
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.model.SamplePresets
import dae.mammoth.id.ui.theme.TermBg

import dae.mammoth.id.ui.theme.TermText
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary
import dae.mammoth.id.viewmodel.ConsoleViewModel

@Composable
fun ConsoleScreen(onBack: () -> Unit) {
    val vm: ConsoleViewModel = viewModel()
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
            Text("Console", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .size(32.dp)
                    .background(Panel2)
                    .border(1.dp, Border, RoundedCornerShape(4.dp))
                    .clickable { vm.clear() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.DeleteSweep, contentDescription = "Clear", tint = TextMuted, modifier = Modifier.size(16.dp))
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(TermBg)
                .border(1.dp, Border, RoundedCornerShape(0.dp))
                .padding(12.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            state.lines.forEach { l ->
                Text(l.text, color = l.color, fontSize = 11.sp, fontFamily = FontFamily.Monospace, lineHeight = 18.sp)
            }
            Text("  root@wa-main:~$ █", color = TermText, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
        }

        // command presets (chips)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            SamplePresets.list.take(4).forEach { preset ->
                Box(
                    Modifier
                        .background(Panel2)
                        .border(1.dp, Border, RoundedCornerShape(4.dp))
                        .clickable { vm.sendPreset(preset.command) }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                ) {
                    Text(preset.label, color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                }
            }
        }

        // command input row
        Row(
            Modifier
                .fillMaxWidth()
                .background(Panel2)
                .border(1.dp, Border, RoundedCornerShape(0.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("root@wa-main:~$", color = Green, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
            androidx.compose.foundation.text.BasicTextField(
                value = state.input,
                onValueChange = { vm.setInput(it) },
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(color = TextPrimary, fontSize = 12.sp, fontFamily = FontFamily.Monospace),
                modifier = Modifier.weight(1f),
            )
            Box(
                Modifier
                    .size(30.dp)
                    .background(Accent, RoundedCornerShape(4.dp))
                    .clickable { vm.send() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.Send, contentDescription = "Send", tint = Color(0xFF04121C), modifier = Modifier.size(15.dp))
            }
        }
    }
}
