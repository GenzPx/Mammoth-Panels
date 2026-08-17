package dae.mammoth.id.data.store

import android.content.Context
import androidx.core.content.edit
import dae.mammoth.id.data.local.AppPreferences

/**
 * Migration-aware settings accessor.
 *
 * Currently delegates to [AppPreferences] (SharedPreferences). This class exists
 * so that we can transparently move to a proper Proto/DataStore later without
 * touching call sites.
 */
class SettingsDataStore(context: Context) {

    private val prefs = context.getSharedPreferences("mammoth_settings", Context.MODE_PRIVATE)

    /** Accent color key selected by the user; null = default cyan. */
    var accentKey: String?
        get() = prefs.getString(KEY_ACCENT, null)
        set(v) = prefs.edit { putString(KEY_ACCENT, v) }

    /** Whether to show the persistent foreground notification. */
    var showNotification: Boolean
        get() = prefs.getBoolean(KEY_NOTIF, true)
        set(v) = prefs.edit { putBoolean(KEY_NOTIF, v) }

    companion object {
        private const val KEY_ACCENT = "accent_key"
        private const val KEY_NOTIF = "show_notification"
    }
}
