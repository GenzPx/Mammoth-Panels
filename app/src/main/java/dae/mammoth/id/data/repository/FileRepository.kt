package dae.mammoth.id.data.repository

import android.content.Context
import dae.mammoth.id.model.FileEntry
import dae.mammoth.id.util.FileUtils
import java.io.File

/** Provides directory listing and file operations over the workspace. */
class FileRepository(private val context: Context) {

    val root: File get() = FileUtils.workspaceDir(context)

    fun list(path: String): List<FileEntry> =
        FileUtils.listDirectory(resolve(path))

    fun resolve(path: String): File {
        val rootAbs = root.absolutePath
        val candidate = File(path).takeIf { it.absolutePath.startsWith(rootAbs) } ?: root
        return candidate
    }

    fun createDirectory(parentPath: String, name: String): Boolean {
        val dir = File(resolve(parentPath), name)
        return dir.mkdirs()
    }

    fun rename(fromPath: String, newName: String): Boolean {
        val f = File(fromPath)
        val target = File(f.parentFile, newName)
        return f.renameTo(target)
    }

    fun delete(path: String): Boolean {
        val f = File(path)
        return f.deleteRecursively()
    }
}
