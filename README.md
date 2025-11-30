
<div align="center">

# JavaFX Vehicle Inventory
  
  <a href="https://www.oracle.com/java/" target="_blank"><img src="https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white" alt="Java 21" /></a>
  <a href="https://openjfx.io/" target="_blank"><img src="https://img.shields.io/badge/JavaFX-21-007396?logo=java&logoColor=white" alt="JavaFX 21" /></a>
  <a href="https://gradle.org/" target="_blank"><img src="https://img.shields.io/badge/Gradle-9.x-02303A?logo=gradle&logoColor=white" alt="Gradle" /></a>
  <a href="https://www.h2database.com/" target="_blank"><img src="https://img.shields.io/badge/H2-2.4.240-blue?logo=database&logoColor=white" alt="H2 Database" /></a>


A modern vehicle inventory management system built with Java 21 and JavaFX 21 WebView. Features a web-based interface using HTML, CSS, and JavaScript, backed by a robust Java architecture with Guice dependency injection and H2 database persistence.

</div>

<img width="1489" height="898" alt="image" src="https://github.com/user-attachments/assets/0d9a8802-c348-4718-ab2e-c2bbb48990ad" />

## Features

- 🎨 **Modern Web UI** - Beautiful, responsive interface with smooth animations and dark theme
- 🚗 **Vehicle Management** - Add, edit, delete, and search vehicles with intuitive controls
- 💾 **Persistent Storage** - H2 database with file-based persistence between sessions
- 🔍 **Real-time Search** - Filter vehicles instantly by make, model, color, or year
- 📊 **Dashboard Stats** - Live statistics showing total vehicles and inventory value
- 🎯 **Clean Architecture** - SOLID principles with dependency injection via Guice
- 🌉 **Java-JavaScript Bridge** - Seamless communication using window.status protocol

## Prerequisites

- Java 21
- Gradle

## Quick Start

Run the application using the Gradle wrapper (no Gradle installation required):

```bash
./gradlew run
```

The application uses H2 database in file mode, storing data in `vehicledb.mv.db`. Your data persists between runs.

## Project Structure

```
src/main/
├── java/com/vehicleapp/
│   ├── Main.java                    # Application entry point
│   ├── config/
│   │   └── AppModule.java          # Guice dependency injection config
│   ├── controller/
│   │   └── VehicleController.java  # Business logic coordinator
│   ├── model/
│   │   └── Vehicle.java            # Vehicle entity
│   ├── repository/
│   │   └── VehicleRepository.java  # Data persistence layer
│   ├── service/
│   │   └── VehicleService.java     # Business logic
│   └── view/
│       └── VehicleWebView.java     # Web-based UI with JavaFX WebView
└── resources/ui/
    ├── index.html                   # Web UI structure
    ├── styles.css                   # Modern styling with animations
    └── app.js                       # Frontend logic and interactivity
```

### Tech Stack

- **Backend**: Java 21, JavaFX 21 WebView, Guice 7.0, H2 Database 2.4
- **Frontend**: HTML5, CSS3, Vanilla JavaScript
- **Communication**: window.status protocol with JSON (Jackson 2.18)

## Development

### Code Formatting

The project uses Google Java Format via Spotless:

```bash
./gradlew spotlessApply    # Apply formatting
```

### Building Distribution

Create a standalone executable:

```bash
./gradlew assembleDist
```

Distributions are in `build/distributions/` as `.zip` and `.tar` files.

Run the extracted distribution:
- Windows: `bin/inventory-app.bat`
- Linux/Mac: `bin/inventory-app`

## License

This project is licensed under the [MIT License](LICENSE).
