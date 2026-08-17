package dae.mammoth.id.ui.screens.databases

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
import androidx.compose.material.icons.outlined.Add
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
import dae.mammoth.id.model.DatabaseEntry
import dae.mammoth.id.model.SampleDatabases
import dae.mammoth.id.ui.components.table.Badge
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

/** Full-screen database manager for a bot (shared across bots via sample data). */
@Composable
fun DatabasesScreen(onBack: () -> Unit) {
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
            Text("Databases", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .size(32.dp)
                    .background(Green, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.Add, contentDescription = "Add", tint = Color(0xFF04121C), modifier = Modifier.size(18.dp))
            }
        }

        LazyColumn(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            items(SampleDatabases.list) { db ->
                DatabaseCard(db)
            }
            item { Spacer(Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun DatabaseCard(db: DatabaseEntry) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Panel)
            .border(1.dp, Border, RoundedCornerShape(0.dp))
            .padding(horizontal = 14.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(db.name, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text("${db.host} · user: ${db.user}", color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
        }
        Badge(text = if (db.healthy) "OK" else "—", active = db.healthy)
    }
}
