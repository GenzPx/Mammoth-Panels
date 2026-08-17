package dae.mammoth.id.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/** Small utility for producing ticker flows (e.g. uptime, resource polling). */
object Clock {

    /** Emits an increasing value every [intervalMillis]. */
    fun ticker(intervalMillis: Long = 1_000): Flow<Long> = flow {
        var v = 0L
        while (true) {
            emit(v)
            delay(intervalMillis)
            v++
        }
    }

    /** Current wall-clock timestamp in millis. */
    fun now(): Long = System.currentTimeMillis()
}
