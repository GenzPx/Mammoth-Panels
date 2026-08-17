package dae.mammoth.id.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dae.mammoth.id.data.repository.BotRepository
import dae.mammoth.id.model.Bot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class BotListUiState(
    val total: Int = 0,
    val running: Int = 0,
    val errors: Int = 0,
    val uptime: String = "—",
    val bots: List<Bot> = emptyList(),
    val query: String = "",
)

class BotListViewModel(
    private val repository: BotRepository,
) : ViewModel() {

    private val query = MutableStateFlow("")

    val uiState: StateFlow<BotListUiState> =
        combine(repository.bots, query) { bots, q ->
            val filtered = if (q.isBlank()) bots else bots.filter {
                it.name.contains(q, ignoreCase = true) || it.id.contains(q, ignoreCase = true)
            }
            BotListUiState(
                total = bots.size,
                running = bots.count { it.running },
                errors = 0,
                uptime = "12h 34m",
                bots = filtered,
                query = q,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BotListUiState())

    fun setQuery(value: String) {
        query.update { value }
    }
}
