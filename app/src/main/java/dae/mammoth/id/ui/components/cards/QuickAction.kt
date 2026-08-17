package dae.mammoth.id.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TextPrimary

/** A quick action button used on the dashboard. */
@Composable
fun QuickAction(label: String, icon: @Composable () -> Unit, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Panel2)
            .border(1.dp, Border, RoundedCornerShape(0.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()
        Text(label, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 12.dp))
    }
}
