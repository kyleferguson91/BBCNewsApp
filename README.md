# BBC News Reader

An Android app I built in Java to browse BBC news articles and save favourites.

## What it does

- Reads article data from an XML feed.
- Shows story details and a link to the original article.
- Saves favourites locally with SharedPreferences so they remain after reopening the app.
- Lets the user change the displayed username.

## Development

1. Open the repository in Android Studio. Use its bundled JDK (17 or newer); the project includes Gradle 8.7.
2. Install Android SDK Platform 34 and Build Tools 34.0.0 in SDK Manager, then let Gradle sync. For a command-line build, set `ANDROID_HOME` to your SDK folder.
3. Run the `app` module on an emulator or device with Android 6.0 / API 23 or newer. Loading articles needs internet access and an available news feed.

On Windows, `.\gradlew.bat assembleDebug` builds the debug APK at `app/build/outputs/apk/debug/app-debug.apk`. The APK builds successfully; device behaviour still needs an emulator or phone check.
