package dae.mammoth.id.util

import dae.mammoth.id.model.FileEntry
import java.io.File

object FileUtils {

    const val ROOT_NAME = "Mammoth"

    /** The workspace directory on external storage. */
    fun workspaceDir(context: android.content.Context): File {
        val base = context.getExternalFilesDir(null) ?: context.filesDir
        return File(base, ROOT_NAME).apply { mkdirs() }
    }

    /** List directory contents sorted: folders first, then files alphabetically. */
    fun listDirectory(dir: File): List<FileEntry> {
        if (!dir.exists() || !dir.isDirectory) return emptyList()
        val children = dir.listFiles()?.filterNot { it.isHidden } ?: emptyList()
        return children
            .sortedWith(compareBy({ !it.isDirectory }, { it.name.lowercase() }))
            .map { f ->
                FileEntry(
                    name = f.name,
                    isDirectory = f.isDirectory,
                    sizeBytes = if (f.isFile) f.length() else 0L,
                    path = f.absolutePath,
                )
            }
    }
}
