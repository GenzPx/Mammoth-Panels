package dae.mammoth.id.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/** Available accent colors the user can pick from. */
enum class AccentKey(val label: String, val color: Color) {
    Cyan("Cyan", Accent),
    Blue("Blue", Accent2),
    Green("Hijau", Green),
    Yellow("Kuning", Yellow),
    Orange("Orange", Color(0xFFF97316)),
    Purple("Ungu", Color(0xFF9B5CF5)),
}

private fun baseColors() = darkColorScheme(
    primary = Accent,
    onPrimary = Color(0xFF04121C),
    secondary = Accent2,
    onSecondary = Color.White,
    background = Bg,
    onBackground = TextPrimary,
    surface = Panel,
    onSurface = TextPrimary,
    surfaceVariant = Panel2,
    onSurfaceVariant = TextMuted,
    error = Red,
    onError = Color.White,
)

@Composable
fun MammothTheme(accent: AccentKey = AccentKey.Cyan, content: @Composable () -> Unit) {
    val colors = baseColors().copy(primary = accent.color)
    MaterialTheme(
        colorScheme = colors,
        typography = MammothType.Typography,
        content = content
    )
}
