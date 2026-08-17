package dae.mammoth.id.nativelib

import android.util.Log

/**
 * JNI bridge to the C library `libmammoth_native.so` (built from
 * src/main/cpp/native_helper.c via CMake + NDK).
 *
 * The heavy/scratchy jobs — cheap string hashing and reading /proc — are done in
 * C so they never touch the garbage collector and work directly on the Linux
 * proc filesystem that Android exposes.
 */
object NativeHelper {

    private const val TAG = "NativeHelper"

    init {
        runCatching {
            System.loadLibrary("mammoth_native")
            loaded = true
        }.onFailure {
            Log.e(TAG, "Gagal memuat libmammoth_native.so", it)
            loaded = false
        }
    }

    @Volatile
    var loaded: Boolean = false
        private set

    /** Version string reported by the native library. */
    val version: String get() = nativeVersion()

    /** CPU architecture the native lib was built for. */
    val arch: String get() = nativeArch()

    /** Cheap djb2 hash of [input], computed in C. */
    fun hash(input: String): Long = if (loaded) nativeHash(input) else input.hashCode().toLong()

    /** Whether a process with [pid] exists, checked natively against /proc. */
    fun isProcessAlive(pid: Int): Boolean = if (loaded) nativeProcessAlive(pid) == 1 else pid > 0

    private external fun nativeVersion(): String
    private external fun nativeArch(): String
    private external fun nativeHash(input: String): Long
    private external fun nativeProcessAlive(pid: Int): Int
}
