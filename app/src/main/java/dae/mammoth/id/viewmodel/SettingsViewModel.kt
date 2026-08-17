package dae.mammoth.id.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dae.mammoth.id.data.local.AppPreferences
import dae.mammoth.id.data.repository.BotRepository
import dae.mammoth.id.util.BatteryUtils
import dae.mammoth.id.util.ExportUtils
import dae.mammoth.id.util.FileUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SettingsUiState(
    val foregroundService: Boolean = true,
    val wakeLock: Boolean = true,
    val autoRestart: Boolean = true,
    val batteryExclusion: Boolean = false,
    val isIgnoringBattery: Boolean = false,
    val versionName: String = "0.1.0",
    val packageId: String = "dae.mammoth.id",
    val lastBackupPath: String? = null,
    val accentKey: dae.mammoth.id.ui.theme.AccentKey = dae.mammoth.id.ui.theme.AccentKey.Cyan,
)

class SettingsViewModel(
    private val context: Context,
    private val prefs: AppPreferences,
    private val botRepository: BotRepository,
    private val dataStore: dae.mammoth.id.data.store.SettingsDataStore,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SettingsUiState(
            foregroundService = prefs.foregroundServiceEnabled,
            wakeLock = prefs.wakeLockEnabled,
            autoRestart = prefs.autoRestart,
            batteryExclusion = prefs.batteryExclusion,
            isIgnoringBattery = BatteryUtils.isIgnoringBatteryOptimizations(context),
            accentKey = dataStore.accentKey?.let { k ->
                dae.mammoth.id.ui.theme.AccentKey.entries.firstOrNull { it.name == k }
            } ?: dae.mammoth.id.ui.theme.AccentKey.Cyan,
        )
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun setAccent(key: dae.mammoth.id.ui.theme.AccentKey) {
        dataStore.accentKey = key.name
        update { it.copy(accentKey = key) }
    }

    fun setForegroundService(v: Boolean) { prefs.foregroundServiceEnabled = v; update { it.copy(foregroundService = v) } }
    fun setWakeLock(v: Boolean) { prefs.wakeLockEnabled = v; update { it.copy(wakeLock = v) } }
    fun setAutoRestart(v: Boolean) { prefs.autoRestart = v; update { it.copy(autoRestart = v) } }
    fun setBatteryExclusion(v: Boolean) {
        prefs.batteryExclusion = v
        update { it.copy(batteryExclusion = v) }
        if (v) BatteryUtils.requestIgnoringBatteryOptimizations(context)
    }

    fun refreshBatteryStatus() {
        update { it.copy(isIgnoringBattery = BatteryUtils.isIgnoringBatteryOptimizations(context)) }
    }

    fun backup() {
        viewModelScope.launch {
            val dir = FileUtils.workspaceDir(context)
            val file = ExportUtils.writeBackup(dir, botRepository.bots.value)
            update { it.copy(lastBackupPath = file.absolutePath) }
        }
    }

    private fun update(transform: (SettingsUiState) -> SettingsUiState) {
        viewModelScope.launch { _uiState.value = transform(_uiState.value) }
    }
}
