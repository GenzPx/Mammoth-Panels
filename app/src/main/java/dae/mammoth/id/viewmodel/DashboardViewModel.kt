package dae.mammoth.id.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dae.mammoth.id.data.repository.BotRepository
import dae.mammoth.id.model.SystemMetric
import dae.mammoth.id.util.Formatters
import dae.mammoth.id.util.NetworkUtils
import dae.mammoth.id.util.SystemInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DashboardUiState(
    val metrics: List<SystemMetric> = emptyList(),
    val network: String = "—",
    val battery: Int = -1,
)

class DashboardViewModel(
    private val context: Context,
    private val botRepository: BotRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            botRepository.bots.collect { bots ->
                val running = bots.count { it.running }
                val storage = SystemInfo.storage()
                _uiState.update {
                    it.copy(
                        metrics = listOf(
                            SystemMetric("Bots", bots.size.toString(), "$running aktif"),
                            SystemMetric("Aktif", running.toString(), "sedang jalan"),
                            SystemMetric("Disk", Formatters.bytes(storage.freeBytes), "sisa"),
                            SystemMetric("Uptime", "12h 34m", "daemon"),
                        ),
                        network = NetworkUtils.transportLabel(context),
                        battery = SystemInfo.batteryPercent(context),
                    )
                }
            }
        }
    }
}
