package dae.mammoth.id.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dae.mammoth.id.ui.theme.Accent
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.TextMuted

/** A bottom navigation destination shown on the scaffold. */
enum class AppSection(val label: String, val icon: ImageVector) {
    Dashboard("Home", Icons.Outlined.Dashboard),
    Servers("Servers", Icons.Outlined.Bolt),
    Files("Files", Icons.Outlined.Folder),
    Settings("Setelan", Icons.Outlined.Settings),
}

/** Wraps content with a bottom navigation bar. */
@Composable
fun AppScaffold(
    current: AppSection,
    onSelect: (AppSection) -> Unit,
    content: @Composable () -> Unit,
) {
    Column(Modifier.fillMaxSize().background(Bg)) {
        Box(Modifier.weight(1f)) { content() }
        Row(
            Modifier
                .fillMaxWidth()
                .background(Panel)
                .padding(horizontal = 4.dp, vertical = 6.dp),
        ) {
            AppSection.entries.forEach { section ->
                val active = section == current
                Column(
                    Modifier
                        .weight(1f)
                        .clickable { onSelect(section) }
                        .padding(vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Icon(
                        section.icon,
                        contentDescription = section.label,
                        tint = if (active) Accent else TextMuted,
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        section.label,
                        color = if (active) Accent else TextMuted,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}
