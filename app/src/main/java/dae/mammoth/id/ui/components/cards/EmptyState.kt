package dae.mammoth.id.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.TextMuted

/** Empty-state placeholder for lists that have no entries yet. */
@Composable
fun EmptyState(title: String, hint: String, modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxWidth()
            .background(Panel)
            .border(1.dp, Border, RoundedCornerShape(0.dp))
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, color = TextMuted, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(hint, color = TextMuted, fontSize = 11.5.sp)
        }
    }
}
