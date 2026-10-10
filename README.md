# Calculator App

A minimal Android app (Kotlin + XML) used to practice a CI/CD commit → push → build pipeline.

Currently supports a single operation: **addition (+)**. More operations will be added one at a time.

## Project structure

- `app/src/main/java/com/example/calculator/Calculator.kt` — pure calculation logic (unit-testable).
- `app/src/main/java/com/example/calculator/MainActivity.kt` — UI wiring (View Binding).
- `app/src/main/res/layout/activity_main.xml` — screen layout.
- `app/src/test/java/com/example/calculator/CalculatorTest.kt` — unit tests.
- `.github/workflows/android-ci.yml` — GitHub Actions CI (runs tests + builds a debug APK on every push/PR).

## Build & test locally

```bash
./gradlew test            # run unit tests
./gradlew assembleDebug   # build a debug APK
```

## Adding a new operation (the workflow)

1. Add the function in `Calculator.kt` (e.g. `fun subtract(...)`).
2. Add a test in `CalculatorTest.kt`.
3. Wire a button in `activity_main.xml` + `MainActivity.kt`.
4. Commit & push — CI runs tests and builds automatically.
