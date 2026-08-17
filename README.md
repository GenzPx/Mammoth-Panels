# Mammoth

**Mammoth** — a Pterodactyl-style bot runner that keeps your scripts alive **on your own device**. No external server needed: as long as the phone isn't killed and you have a data connection, your bots stay active.

- **Author:** GenzPX
- **Version:** 1.0.0
- **Package ID:** `dae.mammoth.id`
- **UI style:** flat, Pterodactyl-inspired (server list → server detail with Console / File Manager / Databases / Schedules / Network / Startup / Settings tabs)
- **Keep-alive:** Android foreground service + partial wake lock
- **Languages:** Kotlin (UI) · Java (helpers) · C via NDK/JNI (native lib)

## Download

Grab the signed release APK from the **Releases** tab:
- `https://github.com/GenzPx/Mammoth-Panels/releases`

## Features

- Server/bot list grid with live resource bars and status badges
- Server detail with 7 Pterodactyl-style tabs
- Foreground service + wake lock keep-alive
- Local file manager with breadcrumbs + text editor
- Process monitor (spawns real processes via `ProcessBuilder`, streams stdout)
- Network diagnostics (TCP latency to WhatsApp/Telegram/Discord/GitHub)
- Native C/NDK helper for cheap hashing and `/proc` process checks
- Backup config to JSON, battery-optimization shortcut, accent theme picker
- Dashboard, settings, credits, changelog, help screens

## Build

```bash
chmod +x ./gradlew
./gradlew assembleDebug      # debug APK
./gradlew assembleRelease    # signed release APK
```

Requires JDK 17 + Android SDK + NDK (for the C library).

## CI / Releases

- `.github/workflows/build-apk.yml` — builds debug APK on every push.
- `.github/workflows/release.yml` — on tag `v*`, builds the **signed** release APK and publishes it to **GitHub Releases**.

Signing: the build reads a keystore (`mammoth-release.keystore` by default) and credentials from `keystore.properties` or env/secrets. For real releases, replace the demo keystore and put credentials in **GitHub Secrets** (`KEYSTORE_BASE64`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD`).

## Structure

```
app/src/main/java/dae/mammoth/id/
├── MainActivity.kt            # nav + service bootstrap
├── data/                      # repositories, preferences, data store
├── model/                     # domain models + sample data
├── nativelib/                 # JNI bridge to C library
├── javalib/                   # Java helper (runtime/VM info)
├── process/                   # runtime process manager + monitor
├── service/BotService.kt      # foreground service + wake lock
├── util/                      # formatters, network probe, system info, etc.
└── ui/
    ├── theme/                 # Mammoth flat dark palette + accent keys
    ├── components/            # shared cards, bars, tables, form widgets
    └── screens/               # dashboard, bots, console, files, network, ...
app/src/main/cpp/              # C source (NDK) + CMakeLists.txt
```
