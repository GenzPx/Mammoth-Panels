package dae.mammoth.id.ui.components.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

/** A simple flat data table (header row + body rows) used across server tabs. */
@Composable
fun DataTable(
    headers: List<String>,
    rows: List<List<Pair<String, Color?>>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(Panel2)
            .border(1.dp, Border, RoundedCornerShape(0.dp))
    ) {
        Row(Modifier.fillMaxWidth().background(Panel2).padding(horizontal = 12.dp, vertical = 8.dp)) {
            headers.forEach { h ->
                Text(h, color = TextMuted, fontSize = 9.5.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.6.sp, modifier = Modifier.weight(1f))
            }
        }
        rows.forEachIndexed { i, row ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(if (i % 2 == 0) Color.Transparent else Color(0x08000000))
                    .padding(horizontal = 12.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                row.forEach { (cell, tint) ->
                    Text(
                        cell,
                        color = tint ?: TextPrimary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}
