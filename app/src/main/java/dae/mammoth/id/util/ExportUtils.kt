package dae.mammoth.id.util

import dae.mammoth.id.model.Bot
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/** Backup/export helpers for the bot configuration. */
object ExportUtils {

    fun toJson(bots: List<Bot>): String {
        val arr = JSONArray()
        bots.forEach { b ->
            arr.put(
                JSONObject()
                    .put("id", b.id)
                    .put("name", b.name)
                    .put("meta", b.meta)
                    .put("runtime", b.runtime.name)
                    .put("entryPoint", b.entryPoint)
                    .put("running", b.running)
            )
        }
        val root = JSONObject().put("app", "Mammoth").put("version", 1).put("bots", arr)
        return root.toString(2)
    }

    /** Write backup JSON into the given directory and return the created file. */
    fun writeBackup(dir: File, bots: List<Bot>): File {
        val stamp = java.text.SimpleDateFormat("yyyyMMdd-HHmmss", java.util.Locale.US).format(java.util.Date())
        val file = File(dir, "mammoth-backup-$stamp.json")
        file.writeText(toJson(bots))
        return file
    }
}
