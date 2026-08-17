package dae.mammoth.id.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dae.mammoth.id.model.LogEntry
import dae.mammoth.id.util.NetworkUtils
import dae.mammoth.id.util.SystemInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LogUiState(
    val entries: List<LogEntry> = emptyList(),
)

class LogViewModel(private val context: Context) : ViewModel() {

    private val _uiState = MutableStateFlow(LogUiState())
    val uiState: StateFlow<LogUiState> = _uiState.asStateFlow()

    fun collect() {
        viewModelScope.launch {
            val device = SystemInfo.device()
            val storage = SystemInfo.storage()
            val entries = buildList {
                add(LogEntry.now("Mammoth daemon started", "INFO"))
                add(LogEntry.now("Device: ${device.model} · API ${device.apiLevel}", "INFO"))
                add(LogEntry.now("Android ${device.androidVersion} by ${device.manufacturer}", "INFO"))
                add(LogEntry.now("Network: ${NetworkUtils.transportLabel(context)}", "INFO"))
                add(LogEntry.now("Disk free: ${android.text.format.Formatter.formatFileSize(context, storage.freeBytes)}", "INFO"))
                add(LogEntry.now("Foreground service running", "OK"))
                add(LogEntry.now("Wake lock acquired", "OK"))
            }
            _uiState.update { it.copy(entries = entries) }
        }
    }

    fun clear() {
        _uiState.update { it.copy(entries = emptyList()) }
    }
}
