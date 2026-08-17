package dae.mammoth.id.ui.screens.help

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dae.mammoth.id.model.HelpTopic
import dae.mammoth.id.model.SampleHelp
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

@Composable
fun HelpScreen(onBack: () -> Unit) {
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
            Text("Bantuan", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        }

        LazyColumn(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            items(SampleHelp.list) { topic ->
                HelpCard(topic)
            }
            item { Spacer(Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun HelpCard(topic: HelpTopic) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Panel)
            .border(1.dp, Border, RoundedCornerShape(0.dp))
            .padding(14.dp)
            .padding(bottom = 4.dp),
    ) {
        Text(topic.title, color = TextPrimary, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text(topic.body, color = TextMuted, fontSize = 12.sp, lineHeight = 17.sp)
    }
    Spacer(Modifier.height(10.dp))
}
