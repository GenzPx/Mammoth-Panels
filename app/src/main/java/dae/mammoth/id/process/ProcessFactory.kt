package dae.mammoth.id.process

import dae.mammoth.id.model.Bot
import java.io.File

/**
 * Builds the actual OS command for a bot based on its runtime and entry point.
 * This is the layer that adapts a "server config" into an executable command,
 * mirroring how Wings translates a Pterodactyl egg into a container command.
 */
object ProcessFactory {

    fun buildCommand(bot: Bot): List<String> {
        val runtime = bot.runtime
        val script = bot.entryPoint
        return when (runtime) {
            BotRuntimeAlias.NodeJs -> listOf("node", script)
            BotRuntimeAlias.Python -> listOf("python3", script)
            BotRuntimeAlias.Bun -> listOf("bun", script)
            BotRuntimeAlias.Shell -> listOf("sh", script)
        }
    }

    /** Resolve the working directory for a bot (its folder under the workspace). */
    fun workingDirFor(workspaceRoot: File, bot: Bot): File =
        File(workspaceRoot, bot.id).apply { mkdirs() }

    /** Human-friendly description of the command. */
    fun describe(bot: Bot): String = buildCommand(bot).joinToString(" ")
}

/** Local alias to avoid leaking the enum into the process package. */
private typealias BotRuntimeAlias = dae.mammoth.id.model.BotRuntime
