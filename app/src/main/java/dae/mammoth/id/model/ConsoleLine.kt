package dae.mammoth.id.model

import androidx.compose.ui.graphics.Color
import dae.mammoth.id.ui.theme.TermDim
import dae.mammoth.id.ui.theme.TermErr
import dae.mammoth.id.ui.theme.TermInfo
import dae.mammoth.id.ui.theme.TermOk
import dae.mammoth.id.ui.theme.TermText

/** Severity of a console output line. */
enum class LogLevel { Normal, Info, Ok, Error, Dim }

/** A single line of console output. */
data class ConsoleLine(
    val text: String,
    val level: LogLevel = LogLevel.Normal,
) {
    val color: Color
        get() = when (level) {
            LogLevel.Normal -> TermText
            LogLevel.Info -> TermInfo
            LogLevel.Ok -> TermOk
            LogLevel.Error -> TermErr
            LogLevel.Dim -> TermDim
        }
}

/** A canned boot log used until real process output is wired in. */
object SampleConsole {
    val bootLog = listOf(
        ConsoleLine("— Mammoth Console (connected) —", LogLevel.Dim),
        ConsoleLine("[boot]", LogLevel.Info),
        ConsoleLine("  memuat runtime node v22.12.0 ...", LogLevel.Normal),
        ConsoleLine("  menyalakan loop ...", LogLevel.Normal),
        ConsoleLine("  OK  WhatsApp session connected", LogLevel.Ok),
        ConsoleLine("—", LogLevel.Dim),
        ConsoleLine("[12:04:11]", LogLevel.Info),
        ConsoleLine("  pesan masuk dari +6281xxx", LogLevel.Normal),
        ConsoleLine("  balas otomatis -> \"Halo, lagi ada di HP ya\"", LogLevel.Normal),
        ConsoleLine("[12:04:12]", LogLevel.Info),
        ConsoleLine("  pesan terkirim", LogLevel.Normal),
        ConsoleLine("[12:05:00] warn: rate limit 2s", LogLevel.Error),
        ConsoleLine("—", LogLevel.Dim),
        ConsoleLine("  menjaga tetap aktif (foreground service) ...", LogLevel.Normal),
        ConsoleLine("  OK", LogLevel.Ok),
    )
}
