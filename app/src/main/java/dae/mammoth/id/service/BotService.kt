package dae.mammoth.id.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import dae.mammoth.id.MainActivity
import dae.mammoth.id.R
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Foreground service that keeps the bot daemon alive.
 *
 * Resilience strategy:
 *  1. Foreground service + persistent notification -> hard for the system to kill.
 *  2. Partial wake lock -> keeps CPU alive while the screen is off.
 *  3. Optional battery-optimization / Doze exclusion (user grant in Settings).
 *
 * It also acts as the process supervisor: each running bot is spawned as a child
 * Process via [ProcessBuilder] and watched on an executor. This is the seam where
 * the actual Node/Python runtimes get executed.
 */
class BotService : Service() {

    private val executor = Executors.newSingleThreadExecutor()
    private val wakeLock: PowerManager.WakeLock? by lazy {
        (getSystemService(PowerManager::class.java))
            .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Mammoth::BotDaemon")
    }

    // botId -> running process (live map; would be persisted in production)
    private val processes = ConcurrentHashMap<String, Process>()

    companion object {
        const val CHANNEL_ID = "mammoth_bot"
        const val CHANNEL_NAME = "Mammoth Bot"
        const val NOTIFICATION_ID = 101
    }

    override fun onCreate() {
        super.onCreate()
        createChannel()
        startForeground(NOTIFICATION_ID, buildNotification())
        wakeLock?.apply { setReferenceCounted(false); acquire() }
        executor.execute { superviseLoop() }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY

    override fun onDestroy() {
        wakeLock?.let { if (it.isHeld) it.release() }
        processes.values.forEach { it.destroy() }
        executor.shutdownNow()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    /** Spawn a bot process for the given id. Returns true on success. */
    fun startBot(id: String, command: List<String>, workingDir: java.io.File?): Boolean {
        if (processes.containsKey(id)) return false
        return runCatching {
            val pb = ProcessBuilder(command)
                .directory(workingDir)
                .redirectErrorStream(true)
            val p = pb.start()
            processes[id] = p
            true
        }.getOrDefault(false)
    }

    fun stopBot(id: String) {
        processes.remove(id)?.destroy()
    }

    private fun superviseLoop() {
        // Poll for dead processes; in production this would also respawn per
        // auto-restart policy. Placeholder keeps the executor alive.
        while (!Thread.currentThread().isInterrupted) {
            runCatching { TimeUnit.SECONDS.sleep(30) }
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW,
            ).apply {
                setShowBadge(false)
                description = "Menjaga bot tetap aktif"
            }
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pending = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Mammoth — Bot aktif")
            .setContentText("Bot tetap berjalan di latar belakang")
            .setSmallIcon(R.drawable.ic_stat_bot)
            .setOngoing(true)
            .setContentIntent(pending)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}
