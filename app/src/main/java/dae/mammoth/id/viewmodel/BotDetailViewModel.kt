package dae.mammoth.id.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dae.mammoth.id.data.repository.BotRepository
import dae.mammoth.id.model.Bot
import dae.mammoth.id.model.ConsoleLine
import dae.mammoth.id.model.SampleConsole
import dae.mammoth.id.model.ServerTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BotDetailUiState(
    val bot: Bot? = null,
    val selectedTab: ServerTab = ServerTab.Console,
    val consoleLines: List<ConsoleLine> = SampleConsole.bootLog,
)

class BotDetailViewModel(
    private val repository: BotRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BotDetailUiState())
    val uiState: StateFlow<BotDetailUiState> = _uiState.asStateFlow()

    fun load(botId: String) {
        _uiState.update { it.copy(bot = repository.getById(botId) ?: it.bot) }
    }

    fun selectTab(tab: ServerTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun start() {
        _uiState.value.bot?.let { b -> repository.setRunning(b.id, true); _uiState.update { it.copy(bot = it.bot?.copy(running = true)) } }
    }

    fun stop() {
        _uiState.value.bot?.let { b -> repository.setRunning(b.id, false); _uiState.update { it.copy(bot = it.bot?.copy(running = false)) } }
    }

    fun restart() {
        viewModelScope.launch {
            stop()
            kotlinx.coroutines.delay(400)
            start()
        }
    }
}
