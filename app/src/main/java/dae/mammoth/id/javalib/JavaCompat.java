package dae.mammoth.id.javalib;

import java.util.Locale;

/**
 * A small Java helper kept in the project to demonstrate that Mammoth is a
 * mixed-language Android app (Kotlin UI + Java helper + C/NDK native lib).
 *
 * These are deliberately simple, dependency-free utilities written in Java and
 * called from the Kotlin UI.
 */
public final class JavaCompat {

    private JavaCompat() {
    }

    /** Java runtime reported by the ART/Dalvik VM. */
    public static String javaVersion() {
        return System.getProperty("java.version", "unknown");
    }

    /** Java VM vendor (e.g. the ART runtime string). */
    public static String javaVendor() {
        return System.getProperty("java.vendor", "unknown");
    }

    /** Runtime available processors as seen by the VM. */
    public static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /** Total heap memory currently allocated to the app, human-readable-ish MB. */
    public static long heapMegabytes() {
        long max = Runtime.getRuntime().maxMemory();
        return max / (1024L * 1024L);
    }

    /** Formats a host/port into an allocation string, e.g. "127.0.0.1:8080". */
    public static String formatAllocation(String host, int port) {
        return String.format(Locale.US, "%s:%d", host, port);
    }
}
