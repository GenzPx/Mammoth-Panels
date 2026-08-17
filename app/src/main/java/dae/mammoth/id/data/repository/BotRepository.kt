package dae.mammoth.id.data.repository

import dae.mammoth.id.model.Bot
import dae.mammoth.id.model.SampleBots
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In-memory repository for managed bots.
 * Eventually backed by the file system + preferences; for now it exposes
 * sample data and a clean mutation API the ViewModels call into.
 */
class BotRepository {

    private val _bots = MutableStateFlow(SampleBots.list)
    val bots: StateFlow<List<Bot>> = _bots.asStateFlow()

    fun getById(id: String): Bot? = _bots.value.firstOrNull { it.id == id }

    fun add(bot: Bot) {
        _bots.value = _bots.value + bot
    }

    fun remove(id: String) {
        _bots.value = _bots.value.filterNot { it.id == id }
    }

    fun setRunning(id: String, running: Boolean) {
        _bots.value = _bots.value.map {
            if (it.id == id) it.copy(running = running) else it
        }
    }
}
