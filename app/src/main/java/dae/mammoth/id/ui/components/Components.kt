package dae.mammoth.id.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack

import androidx.compose.material3.Icon
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

import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

@Composable
fun MammothTopBar(
    onBack: (() -> Unit)? = null,
    logo: @Composable () -> Unit,
    title: @Composable () -> Unit,
    actions: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Panel)
            .border(0.dp, Border, RoundedCornerShape(0.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        if (onBack != null) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Panel2)
                    .border(1.dp, Border, RoundedCornerShape(4.dp))
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back", tint = TextMuted, modifier = Modifier.size(16.dp))
            }
        }
        logo()
        Spacer(Modifier.weight(1f))
        actions?.invoke()
    }
}

@Composable
fun StatusPill(
    text: String,
    color: Color = Green,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .border(1.dp, color, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(Modifier.size(7.dp).background(color))
        Text(text, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.4.sp)
    }
}

@Composable
fun RunningBadge() {
    Row(
        modifier = Modifier
            .background(Panel2)
            .border(1.dp, Border, RoundedCornerShape(4.dp))
            .padding(horizontal = 9.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(Modifier.size(7.dp).background(Green))
        Text("RUNNING", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.6.sp)
    }
}

@Composable
fun ResourceBar(
    label: String,
    valueText: String,
    pct: Int,
    fillColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(9.dp),
    ) {
        Text(label, color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.width(32.dp))
        Box(
            Modifier
                .weight(1f)
                .height(8.dp)
                .background(Bg)
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .width((8f * pct.coerceIn(0, 100) / 100f).dp)
                    .background(fillColor)
            )
        }
        Text(valueText, color = TextPrimary, fontSize = 10.sp, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace, modifier = Modifier.width(56.dp))
    }
}

@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        color = TextMuted,
        fontSize = 10.5.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.1.sp,
        modifier = modifier.padding(top = 20.dp, bottom = 8.dp),
    )
}

@Composable
fun PanelBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier
            .fillMaxWidth()
            .background(Panel)
            .border(1.dp, Border, RoundedCornerShape(0.dp)),
    ) { content() }
}

@Composable
fun PanelHeader(
    title: String,
    action: String? = null,
    onAction: (() -> Unit)? = null,
    titleStyle: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Panel2)
            .border(0.dp, Border, RoundedCornerShape(0.dp))
            .padding(horizontal = 13.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (titleStyle != null) titleStyle() else Text(title, color = TextPrimary, fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
        if (action != null) {
            Text(
                action,
                color = Accent,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(enabled = onAction != null, onClick = { onAction?.invoke() }),
            )
        }
    }
}

@Composable
fun PanelRow(
    modifier: Modifier = Modifier,
    leading: (@Composable () -> Unit)? = null,
    key: String,
    value: String,
    valueColor: Color = TextMuted,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(0.dp, Border, RoundedCornerShape(0.dp))
            .padding(horizontal = 13.dp, vertical = 11.dp)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        leading?.invoke()
        Text(key, color = TextPrimary, fontSize = 11.5.sp, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace, modifier = Modifier.weight(1f))
        Text(value, color = valueColor, fontSize = 11.sp, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
    }
}
