# Vehicle Management System

A simple CRUD application for managing vehicles using Java with H2 database.

## Features

- Add new vehicles
- List all vehicles
- Find vehicle by ID
- Update vehicle details
- Delete vehicles
- H2 in-memory database for storage

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
├── Main.java
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

## Usage

The application provides a modern JavaFX graphical interface with the following features:

1. Add new vehicle - Opens a form to input vehicle details
2. List all vehicles - Displays all vehicles in the system
3. Find vehicle by ID - Search for a specific vehicle
4. Update vehicle - Modify existing vehicle details
5. Delete vehicle - Remove a vehicle from the system
6. Exit - Close the application

All operations are performed through an intuitive graphical interface with forms and dialogs.