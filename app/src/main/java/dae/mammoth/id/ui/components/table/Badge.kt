package dae.mammoth.id.ui.components.table


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.TextMuted

/** A small flat status badge. */
@Composable
fun Badge(text: String, active: Boolean = true, modifier: Modifier = Modifier) {
    val color = if (active) Green else TextMuted
    val border = if (active) Green else Border
    Box(
        modifier
            .border(1.dp, border, RoundedCornerShape(4.dp))
            .padding(horizontal = 7.dp, vertical = 2.dp),
    ) {
        Text(text, color = color, fontSize = 9.5.sp, fontWeight = FontWeight.Bold)
    }
}
