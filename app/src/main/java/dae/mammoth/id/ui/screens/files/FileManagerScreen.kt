package dae.mammoth.id.ui.screens.files

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CreateNewFolder
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dae.mammoth.id.model.FileEntry

import dae.mammoth.id.viewmodel.AppViewModelFactory
import dae.mammoth.id.viewmodel.FileManagerViewModel
import dae.mammoth.id.ui.theme.Accent
import dae.mammoth.id.ui.theme.Bg
import dae.mammoth.id.ui.theme.Border
import dae.mammoth.id.ui.theme.Green
import dae.mammoth.id.ui.theme.Panel
import dae.mammoth.id.ui.theme.Panel2
import dae.mammoth.id.ui.theme.TextMuted
import dae.mammoth.id.ui.theme.TextPrimary
import dae.mammoth.id.util.Formatters

@Composable
fun FileManagerScreen(
    factory: AppViewModelFactory,
    onBack: () -> Unit,
    onOpenFile: (String) -> Unit = {},
) {
    val vm: FileManagerViewModel = viewModel(factory = factory)
    LaunchedEffect(Unit) { vm.openRoot() }
    val state by vm.uiState.collectAsState()

    // System file picker for uploading a script into the current folder.
    val context = LocalContext.current
    val uploadLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument(),
    ) { uri ->
        if (uri != null) vm.upload(uri)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        // header
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
            Text("File Manager", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
            Box(
                Modifier
                    .size(32.dp)
                    .background(Green, RoundedCornerShape(4.dp))
                    .clickable {
                        uploadLauncher.launch(arrayOf("*/*"))
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.FileUpload, contentDescription = "Upload", tint = Color(0xFF04121C), modifier = Modifier.size(16.dp))
            }
            Box(
                Modifier
                    .size(32.dp)
                    .background(Panel2)
                    .border(1.dp, Border, RoundedCornerShape(4.dp))
                    .clickable { vm.createFolder("new-folder") },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.CreateNewFolder, contentDescription = "New folder", tint = TextMuted, modifier = Modifier.size(16.dp))
            }
        }

        // breadcrumbs
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            state.breadcrumbs.forEachIndexed { i, crumb ->
                if (i > 0) Text(" / ", color = TextMuted, fontSize = 12.sp)
                Text(crumb, color = if (i == state.breadcrumbs.lastIndex) Accent else TextMuted, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(Modifier.height(4.dp))

        LazyColumn(
            Modifier.fillMaxSize().padding(horizontal = 16.dp),
        ) {
            // up navigation
            if (!state.isRoot) {
                item {
                    FileRow(
                        entry = FileEntry("..", isDirectory = true),
                        onClick = { vm.goUp() },
                    )
                }
            }
            items(state.entries) { entry ->
                FileRow(
                    entry = entry,
                    onClick = { if (entry.isDirectory) vm.openDir(entry.path) else onOpenFile(entry.path) },
                )
            }
            item { Spacer(Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun FileRow(entry: FileEntry, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Panel)
            .border(1.dp, Border, RoundedCornerShape(0.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 13.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            if (entry.isDirectory) Icons.Outlined.Folder else Icons.Outlined.InsertDriveFile,
            contentDescription = null,
            tint = if (entry.isDirectory) Accent else TextMuted,
            modifier = Modifier.size(18.dp),
        )
        Text(entry.name, color = TextPrimary, fontSize = 13.sp, fontFamily = FontFamily.Monospace, modifier = Modifier.weight(1f))
        Text(
            if (entry.isDirectory) "—" else Formatters.bytes(entry.sizeBytes),
            color = TextMuted,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
        )
    }
}
