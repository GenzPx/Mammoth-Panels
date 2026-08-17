package dae.mammoth.id.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dae.mammoth.id.model.NetworkTest
import dae.mammoth.id.model.ProbeStatus
import dae.mammoth.id.util.NetworkProbe
import dae.mammoth.id.util.NetworkUtils
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NetworkUiState(
    val connected: Boolean = false,
    val transport: String = "none",
    val tests: List<NetworkTest> = emptyList(),
    val running: Boolean = false,
)

class NetworkViewModel(private val context: Context) : ViewModel() {

    private val _uiState = MutableStateFlow(
        NetworkUiState(
            connected = NetworkUtils.hasInternet(context),
            transport = NetworkUtils.transportLabel(context),
        )
    )
    val uiState: StateFlow<NetworkUiState> = _uiState.asStateFlow()

    fun refreshTransport() {
        _uiState.update {
            it.copy(
                connected = NetworkUtils.hasInternet(context),
                transport = NetworkUtils.transportLabel(context),
            )
        }
    }

    fun runTests() {
        viewModelScope.launch {
            _uiState.update { s ->
                s.copy(
                    running = true,
                    tests = defaultTests().map { it.copy(status = ProbeStatus.Running) },
                )
            }
            val results = NetworkProbe.probeDefaults()
            _uiState.update { s ->
                val updated = s.tests.mapIndexed { i, test ->
                    val latency = if (i < results.size) results[i].second else -1L
                    test.copy(
                        status = if (latency >= 0) ProbeStatus.Ok else ProbeStatus.Fail,
                        latencyMs = latency,
                        detail = if (latency >= 0) "${latency} ms" else "unreachable",
                    )
                }
                s.copy(tests = updated, running = false)
            }
        }
    }

    private fun defaultTests(): List<NetworkTest> = listOf(
        NetworkTest("wa", "WhatsApp", "web.whatsapp.com", 443),
        NetworkTest("tg", "Telegram", "api.telegram.org", 443),
        NetworkTest("dc", "Discord", "discord.com", 443),
        NetworkTest("gh", "GitHub", "api.github.com", 443),
    )
}
