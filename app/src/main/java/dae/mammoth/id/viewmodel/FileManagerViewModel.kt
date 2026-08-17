package dae.mammoth.id.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dae.mammoth.id.data.repository.FileRepository
import dae.mammoth.id.model.FileEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

data class FileManagerUiState(
    val currentPath: String = "",
    val breadcrumbs: List<String> = emptyList(),
    val entries: List<FileEntry> = emptyList(),
    val isRoot: Boolean = true,
)

class FileManagerViewModel(context: Context) : ViewModel() {

    private val repository = FileRepository(context)

    private val _uiState = MutableStateFlow(FileManagerUiState())
    val uiState: StateFlow<FileManagerUiState> = _uiState.asStateFlow()

    fun openRoot() {
        val root = repository.root
        refresh(root.absolutePath)
    }

    fun openDir(path: String) {
        refresh(path)
    }

    fun goUp() {
        val current = File(_uiState.value.currentPath)
        val root = repository.root
        val parent = current.parentFile
        if (parent != null && parent.absolutePath.startsWith(root.absolutePath)) {
            refresh(parent.absolutePath)
        } else {
            refresh(root.absolutePath)
        }
    }

    fun createFolder(name: String) {
        viewModelScope.launch {
            repository.createDirectory(_uiState.value.currentPath, name)
            refresh(_uiState.value.currentPath)
        }
    }

    fun renameFile(path: String, newName: String) {
        viewModelScope.launch {
            repository.rename(path, newName)
            refresh(_uiState.value.currentPath)
        }
    }

    fun deleteFile(path: String) {
        viewModelScope.launch {
            repository.delete(path)
            refresh(_uiState.value.currentPath)
        }
    }

    /** Imports a file picked from the system picker into the current folder. */
    fun upload(uri: android.net.Uri) {
        viewModelScope.launch {
            repository.upload(uri, File(_uiState.value.currentPath))
            refresh(_uiState.value.currentPath)
        }
    }

    private fun refresh(path: String) {
        val entries = repository.list(path)
        val rootAbs = repository.root.absolutePath
        val current = File(path)
        // Build a clean breadcrumb relative to the workspace root: show only the
        // folders *inside* Mammoth (e.g. ["Mammoth", "dc"]) — never the noisy
        // Android/storage path segments that confused users.
        val crumbs = if (current.absolutePath.startsWith(rootAbs)) {
            val sub = current.absolutePath.removePrefix(rootAbs).trim('/')
            buildList {
                add("Mammoth")
                if (sub.isNotEmpty()) addAll(sub.split("/"))
            }
        } else {
            listOf(current.name.ifEmpty { "Mammoth" })
        }
        val isRoot = current.absolutePath == repository.root.absolutePath
        _uiState.update {
            it.copy(currentPath = current.absolutePath, breadcrumbs = crumbs, entries = entries, isRoot = isRoot)
        }
    }
}
