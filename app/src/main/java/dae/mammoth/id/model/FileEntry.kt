package dae.mammoth.id.model

/** A file or directory inside the local bot workspace. */
data class FileEntry(
    val name: String,
    val isDirectory: Boolean,
    val sizeBytes: Long = 0L,
    val path: String = "",
) {
    val isFile: Boolean get() = !isDirectory
}
