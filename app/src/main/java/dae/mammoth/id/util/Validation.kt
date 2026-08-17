package dae.mammoth.id.util

/** Input validation helpers for forms. */
object Validation {

    fun isValidBotName(name: String): Boolean =
        name.isNotBlank() && name.length in 3..40

    fun isValidBotId(id: String): Boolean =
        id.isNotBlank() && id.length in 2..30 && id.matches(Regex("[a-z0-9_-]+"))

    fun isValidPort(port: String): Boolean {
        val p = port.toIntOrNull() ?: return false
        return p in 1..65535
    }

    fun errorForBotName(name: String): String? = when {
        name.isBlank() -> "Nama tidak boleh kosong"
        name.length < 3 -> "Minimal 3 karakter"
        name.length > 40 -> "Maksimal 40 karakter"
        else -> null
    }

    fun errorForPort(port: String): String? = when {
        port.isBlank() -> "Port wajib diisi"
        !isValidPort(port) -> "Port tidak valid (1-65535)"
        else -> null
    }
}
