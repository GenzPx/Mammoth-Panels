package dae.mammoth.id.util

import java.util.concurrent.atomic.AtomicInteger

/** Generates short, human-friendly unique ids for bots and resources. */
object IdGenerator {
    private val counter = AtomicInteger(0)

    /** e.g. "wa-0342" */
    fun botId(prefix: String = "bot"): String =
        "$prefix-${(System.currentTimeMillis() % 10000).toString().padStart(4, '0')}"

    /** monotonically increasing id for list keys */
    fun next(): Int = counter.incrementAndGet()
}
