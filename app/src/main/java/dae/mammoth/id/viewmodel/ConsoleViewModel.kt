package dae.mammoth.id.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dae.mammoth.id.model.CommandEntry
import dae.mammoth.id.model.ConsoleLine
import dae.mammoth.id.model.LogLevel
import dae.mammoth.id.model.SampleConsole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ConsoleUiState(
    val lines: List<ConsoleLine> = SampleConsole.bootLog,
    val history: List<CommandEntry> = emptyList(),
    val input: String = "",
    val connected: Boolean = true,
)

class ConsoleViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ConsoleUiState())
    val uiState: StateFlow<ConsoleUiState> = _uiState.asStateFlow()

    fun setInput(value: String) {
        _uiState.update { it.copy(input = value) }
    }

    fun send() {
        val cmd = _uiState.value.input.trim()
        if (cmd.isEmpty()) return
        execute(cmd)
    }

    fun sendPreset(command: String) {
        execute(command)
    }

    private fun execute(cmd: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    lines = it.lines + ConsoleLine("  > $cmd", LogLevel.Info),
                    history = it.history + CommandEntry(cmd),
                    input = "",
                )
            }
        }
    }

    fun clear() {
        _uiState.update { it.copy(lines = emptyList()) }
    }
}
