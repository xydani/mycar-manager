# MyCar Manager

An Android prototype for monitoring and controlling your cars remotely.

The app simulates pairing with an **OBD-II** dongle and, once connected, lets you browse a vehicle's
specifications, operate its main functions, locate it on a map and review maintenance alerts. The
whole interface also ships in three alternative colour schemes designed for colour-blind users.

> **Note:** this is a UI prototype. There is no backend — data is generated locally and the
> connection to the vehicle is simulated. See [Known limitations](#known-limitations).

---

## Table of contents

- [Features](#features)
- [App flow](#app-flow)
- [Accessibility](#accessibility)
- [Tech stack](#tech-stack)
- [Project structure](#project-structure)
- [Requirements](#requirements)
- [Getting started](#getting-started)
- [Demo credentials](#demo-credentials)
- [Known limitations](#known-limitations)

---

## Features

### Authentication
- Login with credential validation.
- Sign-up with real-time validation of username, email and password
  (at least 8 characters, one uppercase letter, one lowercase letter, one digit and one special
  character).

### Vehicle pairing
- Guided tutorial for connecting the OBD-II device, including a Bluetooth status check.
- Device selection screen with two possible outcomes: successful or failed connection.
- On the first successful connection a car is added to the user's garage.

### Garage
- Browse the connected vehicles (up to 6) using the side arrows.
- Summary card for each car: model, licence plate, make, fuel type and body style.

### Manage — remote control
Six functions cycled through one at a time, each with its own state stored per vehicle:

| Function         | Interaction                                    |
|------------------|------------------------------------------------|
| Power            | On/off toggle                                  |
| Windows          | Dialog with an opening slider (0–100%)         |
| Air conditioning | Dialog with temperature and fan power          |
| Door locks       | Lock/unlock toggle                             |
| Radio            | Dialog with station frequency and volume       |
| Headlights       | On/off toggle                                  |

### Features — technical specs
Detailed vehicle sheet: horsepower, acceleration, fuel consumption, emissions, weight, number of
doors and seats, gears, ABS and remaining fuel or charge level.

### Map
Location of the selected car on Google Maps, with a marker and automatic camera centring.

### Alerts
Per-vehicle maintenance warnings — car key, engine coolant, oil level and washer fluid — with a
dedicated empty state when nothing needs attention.

### Account
Edit username, email and password, pick a profile picture, manage notifications and log out.

---

## App flow

```
Login ──────────────► Garage (if the user already owns at least one car)
  │                     │
  │ sign up            ├──► Manage      (remote control)
  ▼                     ├──► Map         (GPS location)
Registration            ├──► Features    (technical specs)
  │                     ├──► Alerts      (maintenance warnings)
  ▼                     ├──► Account     (user profile)
Connection Tutorial     └──► Color Blind (colour correction)
  │
  ▼
Device Selection ──┬──► Successful Connection ──► Garage
                   └──► Failed Connection
```

Navigation between sections happens through a bottom navigation bar and a side drawer, both
available on every main screen. The garage is the base screen: opening a section closes the current
one, so **Back** from any section returns to the garage, and Back from the garage closes the app.

---

## Accessibility

The project includes a dedicated **colour blindness** module, reachable from the side drawer.
Users can pick between four themes:

- `BASE_THEME` — standard palette
- `DEUTERAN_THEME` — deuteranopia
- `PROTAN_THEME` — protanopia
- `TRITAN_THEME` — tritanopia

The choice is persisted in `SharedPreferences` and applied to every activity on startup; screens that
are already open are recreated with the new theme when you return to them.
The `ColorBlindFilter` class additionally applies colour-correction matrices
(`ColorMatrixColorFilter`) to images, so the effect of a filter can be previewed before confirming.

---

## Tech stack

| Component              | Version / detail                            |
|------------------------|---------------------------------------------|
| Language               | Java 17                                     |
| Build                  | Gradle 9.7.1, Android Gradle Plugin 9.4.1   |
| compileSdk / targetSdk | 36 / 36 (edge-to-edge handled)              |
| minSdk                 | 21 (Android 5.0 Lollipop)                   |
| UI                     | XML layouts + Material Components 1.7.0     |
| Layout                 | ConstraintLayout 2.1.4, DrawerLayout        |
| Maps                   | Google Play Services Maps 18.1.0            |
| Third-party libraries  | CircleImageView 3.1.0                       |
| Tests                  | JUnit 4 (validation rules and car catalogue)|

Roughly 2,650 lines of Java across 20 classes, 19 XML layouts and about 100 drawable resources.

---

## Project structure

```
MyCar-Manager/
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/mycarmanager/
│       │   ├── ThemedActivity.java                  # base: colour-blind theme + filtered images
│       │   ├── NavigationActivity.java              # base: side drawer, bottom bar, car selection
│       │   ├── SystemBars.java                      # edge-to-edge insets (Android 15+)
│       │   ├── InputValidation.java                 # username, email and password rules
│       │   ├── LoginActivity.java                   # authentication
│       │   ├── RegistrationActivity.java            # sign-up
│       │   ├── ConnectionTutorialActivity.java      # OBD-II pairing tutorial
│       │   ├── DeviceSelectionActivity.java         # device picker
│       │   ├── SuccessfullConnectionActivity.java   # success screen
│       │   ├── FailedConnectionActivity.java        # failure screen
│       │   ├── CarGarageActivity.java               # home / vehicle list
│       │   ├── CarManageActivity.java               # remote control
│       │   ├── CarMapActivity.java                  # location on map
│       │   ├── CarFeaturesActivity.java             # technical specs
│       │   ├── CarAlertsActivity.java               # maintenance alerts
│       │   ├── AccountActivity.java                 # user profile
│       │   ├── ColorBlindActivity.java              # accessible theme picker
│       │   ├── ColorBlindFilter.java                # colour-correction matrices
│       │   ├── User.java                            # user model + garage
│       │   └── Car.java                             # car model + catalogue (+ AirConditioning, Radio)
│       └── res/
│           ├── layout/    # activities and dialogs
│           ├── drawable/  # icons, illustrations, vehicle images
│           ├── values/    # themes.xml (4 themes), colors, strings, attr
│           ├── anim/      # dialog zoom in/out animations
│           └── font/      # Staatliches, Manjari
│   └── src/test/          # JUnit tests
├── gradle/wrapper/        # Gradle wrapper
├── build.gradle
└── settings.gradle
```

---

## Requirements

- A recent Android Studio release that supports Android Gradle Plugin 9.4
- JDK 17 or newer (the project is built with JDK 21)
- A device or emulator running Android 5.0 (API 21) or above
- A Google Maps API key

---

## Getting started

```bash
git clone https://github.com/xydani/mycar-manager.git
cd mycar-manager
```

Open the folder in Android Studio and wait for the Gradle sync, or build from the command line:

```bash
./gradlew assembleDebug
```

Run the unit tests with:

```bash
./gradlew test
```

### Configuring the Google Maps API key

The *Map* screen needs a valid key for the Google Maps SDK for Android.
Put it in `local.properties` (which is not tracked by git):

```properties
MAPS_API_KEY=your_key_here
```

`app/build.gradle` reads it and passes it to the manifest through the `${MAPS_API_KEY}` placeholder.

Without a valid key the app still runs, but the map renders empty.

---

## Demo credentials

A demo user is created automatically at startup and already has six cars in the garage:

| Username | Password |
|----------|----------|
| `user`   | `user`   |

You can also register a new account — in that case the garage starts empty and you are taken to the
pairing tutorial.

---

## Known limitations

This is a UI-focused prototype, so a few parts are deliberately simulated:

- **No data persistence.** Users and vehicles live in static in-memory lists and are wiped when the
  app closes. Only the selected theme is stored in `SharedPreferences`.
- **No backend and no real communication with the car.** The app only checks that Bluetooth is on;
  no OBD-II connection is actually established: the outcome depends on which device you pick on the
  selection screen.
- **Plaintext credentials.** Passwords are compared as strings, with no hashing — fine for a demo,
  but it would need rewriting for real use.
- **Static dataset.** The six vehicles (`Car.getCatalog()`), their GPS positions and the maintenance
  alerts are hardcoded.
