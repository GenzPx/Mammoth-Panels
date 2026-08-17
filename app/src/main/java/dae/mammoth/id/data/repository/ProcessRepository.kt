package dae.mammoth.id.data.repository

import android.content.Context
import dae.mammoth.id.model.Bot
import dae.mammoth.id.process.ProcessFactory
import dae.mammoth.id.process.ProcessStatus
import dae.mammoth.id.process.RuntimeProcessManager
import dae.mammoth.id.util.FileUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Repository that exposes the process manager's state as a [StateFlow]
 * so the UI can react to start/stop without polling.
 */
class ProcessRepository(context: Context) {

    private val manager = RuntimeProcessManager()
    private val workspace = FileUtils.workspaceDir(context)

    private val _statuses = MutableStateFlow<List<ProcessStatus>>(emptyList())
    val statuses: StateFlow<List<ProcessStatus>> = _statuses.asStateFlow()

    fun start(bot: Bot): Boolean {
        val ok = manager.start(bot.id, ProcessFactory.buildCommand(bot), ProcessFactory.workingDirFor(workspace, bot))
        refresh()
        return ok
    }

    fun stop(bot: Bot): Boolean {
        if (!manager.isRunning(bot.id)) return false
        manager.stop(bot.id)
        refresh()
        return true
    }

    fun status(id: String): ProcessStatus? =
        _statuses.value.firstOrNull { it.botId == id }

    private fun refresh() {
        _statuses.update {
            manager.activeIds.map { id ->
                ProcessStatus(
                    botId = id,
                    running = manager.isRunning(id),
                    uptimeSeconds = manager.uptimeSeconds(id),
                )
            }
        }
    }
}
