# Mammoth

**Mammoth** — a Pterodactyl-style bot runner that keeps your scripts alive **on your own device**. No external server needed: as long as the phone isn't killed and you have a data connection, your bots stay active.

- **Author:** GenzPX
- **Package ID:** `dae.mammoth.id`
- **UI style:** flat, Pterodactyl-inspired (server list → server detail with Console / File Manager / Databases / Schedules / Network / Startup / Settings tabs)
- **Keep-alive:** Android foreground service + partial wake lock
- **Stack:** Kotlin, Jetpack Compose, Material 3, Navigation Compose

## Build

```bash
chmod +x ./gradlew
./gradlew assembleDebug
```

Output APK: `app/build/outputs/apk/debug/app-debug.apk`

You need JDK 17 and the Android SDK (a `local.properties` with `sdk.dir` or `ANDROID_HOME`).

## CI

`.github/workflows/build-apk.yml` builds a debug APK automatically on every push to `main` and uploads it as a workflow artifact.

## Structure

```
app/src/main/java/dae/mammoth/id/
├── MainActivity.kt            # nav + service bootstrap
├── data/Bot.kt                # server/bot model + sample data
├── service/BotService.kt      # foreground service + wake lock
└── ui/
    ├── theme/                 # Mammoth flat dark palette
    ├── components/            # shared cards, bars, buttons
    └── screens/               # ServerList, ServerDetail, Credits
```
