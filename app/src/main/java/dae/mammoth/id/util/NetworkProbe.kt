package dae.mammoth.id.util

import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Runs simple TCP connectivity probes. This is the diagnostic counterpart to
 * the bot runner's dependency on "having a data connection" — it tells the user
 * whether the phone can actually reach the hosts a bot talks to.
 */
object NetworkProbe {

    /**
     * Attempts a TCP connection to [host]:[port] and returns latency in
     * milliseconds, or -1 on failure.
     */
    suspend fun tcp(host: String, port: Int, timeoutMs: Int = 3000): Long =
        withContext(Dispatchers.IO) {
            val start = System.currentTimeMillis()
            try {
                Socket().use { socket ->
                    socket.connect(InetSocketAddress(host, port), timeoutMs)
                    System.currentTimeMillis() - start
                }
            } catch (e: IOException) {
                -1L
            }
        }

    /** Probes a list of well-known endpoints used by social/bot platforms. */
    suspend fun probeDefaults(): List<Pair<String, Long>> {
        val targets = listOf(
            "web.whatsapp.com" to 443,
            "api.telegram.org" to 443,
            "discord.com" to 443,
            "api.github.com" to 443,
        )
        return targets.map { (h, p) -> (h + ":$p") to tcp(h, p) }
    }
}
