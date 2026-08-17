package dae.mammoth.id.data.local

import android.content.Context
import androidx.core.content.edit

/**
 * Thin wrapper over SharedPreferences for app-wide settings.
 * Later we may migrate to DataStore; keeping the surface tiny makes that trivial.
 */
class AppPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("mammoth_settings", Context.MODE_PRIVATE)

    var foregroundServiceEnabled: Boolean
        get() = prefs.getBoolean(KEY_FG, true)
        set(v) = prefs.edit { putBoolean(KEY_FG, v) }

    var wakeLockEnabled: Boolean
        get() = prefs.getBoolean(KEY_WAKELOCK, true)
        set(v) = prefs.edit { putBoolean(KEY_WAKELOCK, v) }

    var autoRestart: Boolean
        get() = prefs.getBoolean(KEY_AUTO, true)
        set(v) = prefs.edit { putBoolean(KEY_AUTO, v) }

    var batteryExclusion: Boolean
        get() = prefs.getBoolean(KEY_BATT, false)
        set(v) = prefs.edit { putBoolean(KEY_BATT, v) }

    var uptimeSeconds: Long
        get() = prefs.getLong(KEY_UPTIME, 0L)
        set(v) = prefs.edit { putLong(KEY_UPTIME, v) }

    companion object {
        private const val KEY_FG = "fg_service"
        private const val KEY_WAKELOCK = "wake_lock"
        private const val KEY_AUTO = "auto_restart"
        private const val KEY_BATT = "battery_exclusion"
        private const val KEY_UPTIME = "uptime_seconds"
    }
}
