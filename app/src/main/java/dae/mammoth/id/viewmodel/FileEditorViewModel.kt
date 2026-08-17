package dae.mammoth.id.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

data class FileEditorUiState(
    val path: String = "",
    val content: String = "",
    val saved: Boolean = true,
    val loaded: Boolean = false,
    val error: String? = null,
)

class FileEditorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FileEditorUiState())
    val uiState: StateFlow<FileEditorUiState> = _uiState.asStateFlow()

    fun open(path: String) {
        viewModelScope.launch {
            val content = withContext(Dispatchers.IO) {
                runCatching { File(path).readText() }.getOrDefault("")
            }
            _uiState.update {
                it.copy(path = path, content = content, loaded = true, saved = true, error = null)
            }
        }
    }

    fun edit(text: String) {
        _uiState.update { it.copy(content = text, saved = false) }
    }

    fun save() {
        val s = _uiState.value
        viewModelScope.launch {
            val ok = withContext(Dispatchers.IO) {
                runCatching { File(s.path).writeText(s.content) }.isSuccess
            }
            _uiState.update { it.copy(saved = ok, error = if (ok) null else "Gagal menyimpan") }
        }
    }
}
