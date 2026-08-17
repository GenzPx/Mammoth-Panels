package dae.mammoth.id.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/** App typography. Monospace is used for terminal and data displays. */
object MammothType {

    val Mono = FontFamily.Monospace

    val Typography = Typography(
        titleLarge = TextStyle(fontSize = 19.sp, fontWeight = FontWeight.ExtraBold),
        titleMedium = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.ExtraBold),
        bodyMedium = TextStyle(fontSize = 13.sp),
        bodySmall = TextStyle(fontSize = 11.sp),
        labelMedium = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
    )
}
