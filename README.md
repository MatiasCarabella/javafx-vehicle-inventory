# Vehicle Management System

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