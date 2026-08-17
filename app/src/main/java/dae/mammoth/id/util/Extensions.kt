package dae.mammoth.id.util




/** Common extension functions shared across the app. */

/** Returns the given Int as a zero-padded 2-digit string. */
fun Int.twoDigits(): String = toString().padStart(2, '0')

/** Returns the given Long as a simple file-friendly slug. */
fun Long.slug(): String = toString(36)

/** Whether a string is blank after trimming. */
fun String?.isBlankish(): Boolean = this == null || isBlank()

/** Clamp an Int into [min]..[max]. */
fun Int.clamp(min: Int, max: Int): Int = if (this < min) min else if (this > max) max else this
