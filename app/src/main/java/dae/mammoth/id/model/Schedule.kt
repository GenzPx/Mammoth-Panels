package dae.mammoth.id.model

/** A cron-like scheduled task, mirroring Pterodactyl's Schedules tab. */
data class Schedule(
    val name: String,
    val cron: String,
    val active: Boolean = true,
)

object SampleSchedules {
    val list = listOf(
        Schedule("Daily Restart", "0 5 * * *", true),
        Schedule("Backup Session", "*/30 * * * *", true),
        Schedule("Banner Update", "0 */6 * * *", false),
    )
}
