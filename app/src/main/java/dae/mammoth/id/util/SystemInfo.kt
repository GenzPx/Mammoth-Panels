package dae.mammoth.id.util

import android.content.Context
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs

/** Gathers read-only diagnostics about the device and storage. */
object SystemInfo {

    data class Device(
        val model: String,
        val androidVersion: String,
        val apiLevel: Int,
        val manufacturer: String,
    )

    data class Storage(
        val totalBytes: Long,
        val freeBytes: Long,
    )

    fun device(): Device = Device(
        model = Build.MODEL,
        androidVersion = Build.VERSION.RELEASE,
        apiLevel = Build.VERSION.SDK_INT,
        manufacturer = Build.MANUFACTURER,
    )

    fun storage(): Storage {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.absolutePath)
        return Storage(
            totalBytes = stat.totalBytes,
            freeBytes = stat.availableBytes,
        )
    }

    fun batteryPercent(context: Context): Int {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as? BatteryManager ?: return -1
        return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

    fun workspaceUsedBytes(context: Context): Long {
        val dir = FileUtils.workspaceDir(context)
        return dir.walkTopDown().filter { it.isFile }.sumOf { it.length() }
    }
}
