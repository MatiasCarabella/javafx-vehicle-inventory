<div id="user-content-toc">
  <ul align="center" style="list-style: none;">
    <summary>
      <h1 align="center">JavaFX Vehicle Inventory</h1>
    </summary>
  </ul>
</div>
<div align="center">
  <a href="https://www.oracle.com/java/" target="_blank"><img src="https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white" alt="Java 21" /></a>
  <a href="https://openjfx.io/" target="_blank"><img src="https://img.shields.io/badge/JavaFX-21-007396?logo=java&logoColor=white" alt="JavaFX 21" /></a>
  <a href="https://gradle.org/" target="_blank"><img src="https://img.shields.io/badge/Gradle-8.x-02303A?logo=gradle&logoColor=white" alt="Gradle" /></a>
  <a href="https://www.h2database.com/" target="_blank"><img src="https://img.shields.io/badge/H2-2.4.240-blue?logo=database&logoColor=white" alt="H2 Database" /></a>
  <a href="https://projectlombok.org/" target="_blank"><img src="https://img.shields.io/badge/Lombok-1.18.42-BC4521?logo=lombok&logoColor=white" alt="Project Lombok" /></a>
  <a href="https://github.com/google/guice" target="_blank"><img src="https://img.shields.io/badge/Guice-7.0.0-4285F4?logo=google&logoColor=white" alt="Google Guice" /></a>
  <a href="https://junit.org/junit5/" target="_blank"><img src="https://img.shields.io/badge/JUnit-6.0.0-25A162?logo=junit5&logoColor=white" alt="JUnit Jupiter" /></a>
</div>
<h1></h1>

A simple CRUD application for managing vehicles using JavaFX, Guice, and H2 database.

## Features

- Add new vehicles
- List all vehicles
- Find vehicle by ID
- Update vehicle details
- Delete vehicles
- Persistent H2 database storage
- Dependency injection with Guice
- Clean architecture with separation of concerns

## Prerequisites

- Java 21
- Gradle

## Building and Running the Project

You can run the application using the Gradle wrapper (no Gradle installation required):

```bash
./gradlew run
```

Or if you have Gradle installed:

```bash
gradle run
```

The application uses H2 database in file mode, storing data in `vehicledb.mv.db` in the project directory. This means your data persists between application runs.

## Project Structure

```
src/main/java/com/vehicleapp/
├── Main.java                 # Application entry point
├── config/
│   └── AppModule.java       # Guice dependency configuration
├── controller/
│   └── VehicleController.java
├── model/
│   └── Vehicle.java
├── repository/
│   └── VehicleRepository.java
├── service/
│   └── VehicleService.java
└── view/
    └── VehicleFXView.java
```

## Architecture

The application follows a clean architecture pattern:

- **View Layer**: JavaFX UI components handling user interaction
- **Controller Layer**: Coordinates between view and service, handles errors
- **Service Layer**: Business logic and data validation
- **Repository Layer**: Data persistence using H2 database
- **Dependency Injection**: Guice manages component lifecycle and dependencies

## Development

### Code Style

The project uses Google Java Format through the Spotless plugin. To maintain consistent code style:

```bash
# Check code formatting
./gradlew spotlessCheck

# Apply code formatting
./gradlew spotlessApply
```

### Creating an Executable

You can create a standalone executable distribution of the application using Gradle:

```bash
# Create distribution packages
./gradlew assembleDist

# The distributions will be available in:
# build/distributions/inventory-app.zip
# build/distributions/inventory-app.tar
```

To run the application after extracting the distribution:
- Windows: `bin/inventory-app.bat`
- Linux/Mac: `bin/inventory-app`

The executable includes all required dependencies and can be distributed to users who don't have Gradle or the source code installed.

### Project Setup

The application uses Gradle for build management and includes several plugins:

- **JavaFX Plugin**: Manages JavaFX dependencies
- **Spotless**: Code formatting with Google Java Format
- **Versions Plugin**: Dependency version management

## Usage

The application provides a modern JavaFX graphical interface with the following features:

1. Add new vehicle - Opens a form to input vehicle details
2. List all vehicles - Displays all vehicles in the system
3. Find vehicle by ID - Search for a specific vehicle
4. Update vehicle - Modify existing vehicle details
5. Delete vehicle - Remove a vehicle from the system
6. Exit - Close the application

All operations are performed through an intuitive graphical interface with forms and dialogs.
