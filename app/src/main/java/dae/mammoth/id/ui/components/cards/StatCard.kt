package dae.mammoth.id.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dae.mammoth.id.model.SystemMetric
import dae.mammoth.id.ui.theme.Accent
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

/** A dashboard stat tile. */
@Composable
fun StatCard(metric: SystemMetric, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .background(Panel)
            .border(1.dp, if (metric.accent) Accent else Border, RoundedCornerShape(0.dp))
            .padding(12.dp)
    ) {
        Text(metric.label.uppercase(), color = TextMuted, fontSize = 9.5.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.8.sp)
        Spacer(Modifier.height(6.dp))
        Text(metric.value, color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily.Monospace)
        if (metric.sub.isNotEmpty()) {
            Text(metric.sub, color = TextMuted, fontSize = 10.5.sp)
        }
    }
}
