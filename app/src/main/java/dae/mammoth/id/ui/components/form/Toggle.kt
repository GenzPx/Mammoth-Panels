package dae.mammoth.id.ui.components.form

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dae.mammoth.id.ui.theme.Accent
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

/** A reusable flat toggle row (label + description + switch). */
@Composable
fun SettingToggleRow(
    label: String,
    desc: String,
    on: Boolean,
    onChange: (Boolean) -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.weight(1f)) {
            Text(label, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Text(desc, color = TextMuted, fontSize = 11.sp)
        }
        Box(
            Modifier
                .width(40.dp)
                .height(22.dp)
                .background(if (on) Accent else Bg)
                .border(1.dp, if (on) Accent else Border, RoundedCornerShape(0.dp))
                .clickable { onChange(!on) }
                .padding(horizontal = 3.dp, vertical = 4.dp),
        ) {
            Box(
                Modifier
                    .size(14.dp)
                    .background(Color.White)
                    .align(if (on) Alignment.CenterEnd else Alignment.CenterStart)
            )
        }
    }
}
