package dae.mammoth.id.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dae.mammoth.id.data.repository.BotRepository

/** Simple manual DI for ViewModels until we adopt Hilt. */
class AppViewModelFactory(
    private val botRepository: BotRepository,
    private val context: android.content.Context,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BotListViewModel::class.java) ->
                BotListViewModel(botRepository) as T
            modelClass.isAssignableFrom(BotDetailViewModel::class.java) ->
                BotDetailViewModel(botRepository) as T
            modelClass.isAssignableFrom(FileManagerViewModel::class.java) ->
                FileManagerViewModel(context) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) ->
                SettingsViewModel(
                    context,
                    dae.mammoth.id.data.local.AppPreferences(context),
                    botRepository,
                    dae.mammoth.id.data.store.SettingsDataStore(context),
                ) as T
            modelClass.isAssignableFrom(DashboardViewModel::class.java) ->
                DashboardViewModel(context, botRepository) as T
            modelClass.isAssignableFrom(LogViewModel::class.java) ->
                LogViewModel(context) as T
            modelClass.isAssignableFrom(NetworkViewModel::class.java) ->
                NetworkViewModel(context) as T
            modelClass.isAssignableFrom(ProcessMonitorViewModel::class.java) ->
                ProcessMonitorViewModel(context, botRepository) as T
            else -> error("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
