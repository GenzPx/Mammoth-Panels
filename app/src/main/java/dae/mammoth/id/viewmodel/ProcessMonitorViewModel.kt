package dae.mammoth.id.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dae.mammoth.id.data.repository.BotRepository
import dae.mammoth.id.process.ProcessFactory
import dae.mammoth.id.process.ProcessMonitor
import dae.mammoth.id.util.FileUtils
import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProcessUiEntry(
    val id: String,
    val name: String,
    val command: String,
    val running: Boolean,
    val uptimeSeconds: Long,
    val lastOutput: String,
)

data class ProcessMonitorUiState(
    val entries: List<ProcessUiEntry> = emptyList(),
)

class ProcessMonitorViewModel(
    context: Context,
    private val botRepository: BotRepository,
) : ViewModel() {

    private val monitor = ProcessMonitor()
    private val workspace = FileUtils.workspaceDir(context)

    private val _uiState = MutableStateFlow(ProcessMonitorUiState())
    val uiState: StateFlow<ProcessMonitorUiState> = _uiState.asStateFlow()

    init {
        // poll every 1s to keep the process list / output fresh
        viewModelScope.launch {
            while (true) {
                refresh()
                delay(1000)
            }
        }
    }

    fun startAll() {
        botRepository.bots.value.forEach { bot ->
            if (!monitor.isRunning(bot.id)) {
                monitor.start(bot.id, ProcessFactory.buildCommand(bot), ProcessFactory.workingDirFor(workspace, bot))
            }
        }
    }

    fun stopAll() {
        monitor.stopAll()
        refresh()
    }

    fun stop(id: String) {
        monitor.stop(id)
        refresh()
    }

    private fun refresh() {
        _uiState.update {
            val entries = botRepository.bots.value.map { bot ->
                val running = monitor.isRunning(bot.id)
                val out = monitor.drain(bot.id)
                ProcessUiEntry(
                    id = bot.id,
                    name = bot.name,
                    command = ProcessFactory.describe(bot),
                    running = running,
                    uptimeSeconds = monitor.uptime(bot.id),
                    lastOutput = out.lastOrNull() ?: (if (running) "berjalan..." else "berhenti"),
                )
            }
            it.copy(entries = entries)
        }
    }
}
