package dae.mammoth.id.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager

/** Helpers around battery optimization / Doze exclusion. */
object BatteryUtils {

    /** Whether the app has been exempted from battery optimization (Doze). */
    fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return pm.isIgnoringBatteryOptimizations(context.packageName)
    }

    /** Intent to let the user grant the battery optimization exclusion. */
    fun requestIgnoringBatteryOptimizations(context: Context) {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(context.packageName)) {
            val intent = Intent(
                android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                Uri.parse("package:${context.packageName}"),
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    /** Whether the device supports Doze mode at all. */
    fun supportsDoze(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}
