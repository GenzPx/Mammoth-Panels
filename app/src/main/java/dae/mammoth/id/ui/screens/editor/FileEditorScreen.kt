package dae.mammoth.id.ui.screens.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dae.mammoth.id.ui.theme.Accent
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border

import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TermBg
import dae.mammoth.id.ui.theme.TermText
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary
import dae.mammoth.id.viewmodel.FileEditorViewModel

/** A full-screen text editor for bot scripts. */
@Composable
fun FileEditorScreen(
    filePath: String,
    onBack: () -> Unit,
) {
    val vm: FileEditorViewModel = viewModel()
    LaunchedEffect(filePath) { vm.open(filePath) }
    val state by vm.uiState.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                Modifier
                    .size(32.dp)
                    .background(Panel2)
                    .border(1.dp, Border, RoundedCornerShape(4.dp))
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back", tint = TextMuted, modifier = Modifier.size(16.dp))
            }
            Column(Modifier.weight(1f)) {
                Text("Edit File", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                Text(state.path.substringAfterLast('/'), color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            }
            if (!state.saved) {
                Box(
                    Modifier
                        .size(32.dp)
                        .background(Accent, RoundedCornerShape(4.dp))
                        .clickable { vm.save() },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Outlined.Save, contentDescription = "Save", tint = Color(0xFF04121C), modifier = Modifier.size(16.dp))
                }
            }
        }

        BasicTextField(
            value = state.content,
            onValueChange = { vm.edit(it) },
            textStyle = TextStyle(color = TermText, fontSize = 13.sp, fontFamily = FontFamily.Monospace),
            cursorBrush = androidx.compose.ui.graphics.SolidColor(Accent),
            modifier = Modifier
                .fillMaxSize()
                .background(TermBg)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()),
        )

        if (state.error != null) {
            Text(state.error!!, color = Color(0xFFE05555), fontSize = 12.sp, modifier = Modifier.padding(16.dp))
        }
    }
}
