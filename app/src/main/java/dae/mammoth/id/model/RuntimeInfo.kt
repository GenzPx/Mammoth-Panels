package dae.mammoth.id.model

/** Metadata about an installed runtime. */
data class RuntimeInfo(
    val name: String,
    val version: String,
    val installed: Boolean,
    val defaultCommand: String,
)

object SampleRuntimes {
    val list = listOf(
        RuntimeInfo("Node.js", "v22.12.0", true, "node"),
        RuntimeInfo("Python", "3.12.1", true, "python3"),
        RuntimeInfo("Bun", "1.1.4", false, "bun"),
        RuntimeInfo("Shell", "sh", true, "sh"),
    )
}
