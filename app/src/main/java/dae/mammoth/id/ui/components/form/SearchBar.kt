package dae.mammoth.id.ui.components.form

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary

/** A flat search input row. (Full text field added by host screen.) */
@Composable
fun SearchBarHeader(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(Panel2)
            .border(1.dp, Border, RoundedCornerShape(0.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Icons.Outlined.Search, contentDescription = "Search", tint = TextMuted, modifier = Modifier.size(18.dp))
        Box(Modifier.padding(horizontal = 8.dp)) {
            if (query.isEmpty()) {
                Text("Cari server...", color = TextMuted, fontSize = 13.sp, modifier = Modifier.padding(vertical = 10.dp))
            } else {
                Text(query, color = TextPrimary, fontSize = 13.sp, modifier = Modifier.padding(vertical = 10.dp))
            }
        }
    }
}
