package com.vehicleapp.view;

import com.google.inject.Inject;
import com.vehicleapp.controller.VehicleController;
import com.vehicleapp.model.Vehicle;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;

/**
 * JavaFX view component providing the user interface for vehicle management. Handles user input,
 * displays data, and coordinates with the controller.
 */
@NoArgsConstructor
public class VehicleFXView {
  private Stage primaryStage;
  private TextArea outputArea;
  @Inject private VehicleController controller;

  public void initializeUI(Stage stage) {
    this.primaryStage = stage;
    primaryStage.setTitle("JavaFX Vehicle Inventory Management System");

    // Create main layout
    final BorderPane mainLayout = new BorderPane();

    // Create menu buttons
    final VBox menuButtons = new VBox(10);
    menuButtons.setPadding(new Insets(10));

    // Add vehicle button
    final Button addButton = new Button("Add new vehicle");
    addButton.setMaxWidth(Double.MAX_VALUE);
    addButton.setOnAction(
        e -> {
          try {
            Vehicle vehicle = getVehicleInput();
            if (vehicle != null) {
              Vehicle saved = controller.addVehicle(vehicle);
              displayMessage("Vehicle added successfully with ID: " + saved.getId());
            }
          } catch (Exception ex) {
            displayMessage(ex.getMessage());
          }
        });

    // List vehicles button
    final Button listButton = new Button("List all vehicles");
    listButton.setMaxWidth(Double.MAX_VALUE);
    listButton.setOnAction(
        e -> {
          try {
            displayVehicles(controller.listVehicles());
          } catch (Exception ex) {
            displayMessage(ex.getMessage());
          }
        });

    // Find vehicle button
    final Button findButton = new Button("Find vehicle by ID");
    findButton.setMaxWidth(Double.MAX_VALUE);
    findButton.setOnAction(
        e -> {
          try {
            Long id = getVehicleId();
            if (id != null) {
              controller
                  .findVehicle(id)
                  .ifPresentOrElse(
                      this::displayVehicle, () -> displayMessage("Vehicle not found."));
            }
          } catch (Exception ex) {
            displayMessage(ex.getMessage());
          }
        });

    // Update vehicle button
    final Button updateButton = new Button("Update vehicle");
    updateButton.setMaxWidth(Double.MAX_VALUE);
    updateButton.setOnAction(
        e -> {
          try {
            Long id = getVehicleId();
            if (id != null) {
              controller
                  .findVehicle(id)
                  .ifPresentOrElse(
                      existing -> {
                        Vehicle updated = getUpdateVehicleInput(existing);
                        if (updated != null) {
                          controller.updateVehicle(updated);
                          displayMessage("Vehicle updated successfully.");
                        }
                      },
                      () -> displayMessage("Vehicle not found."));
            }
          } catch (Exception ex) {
            displayMessage(ex.getMessage());
          }
        });

    // Delete vehicle button
    final Button deleteButton = new Button("Delete vehicle");
    deleteButton.setMaxWidth(Double.MAX_VALUE);
    deleteButton.setOnAction(
        e -> {
          try {
            Long id = getVehicleId();
            if (id != null) {
              controller
                  .findVehicle(id)
                  .ifPresentOrElse(
                      v -> {
                        controller.deleteVehicle(id);
                        displayMessage("Vehicle deleted successfully.");
                      },
                      () -> displayMessage("Vehicle not found."));
            }
          } catch (Exception ex) {
            displayMessage(ex.getMessage());
          }
        });

    // Exit button
    final Button exitButton = new Button("Exit");
    exitButton.setMaxWidth(Double.MAX_VALUE);
    exitButton.setOnAction(e -> Platform.exit());

    menuButtons
        .getChildren()
        .addAll(addButton, listButton, findButton, updateButton, deleteButton, exitButton);

    // Create output area
    outputArea = new TextArea();
    outputArea.setEditable(false);
    outputArea.setWrapText(true);

    // Add components to layout
    mainLayout.setLeft(menuButtons);
    mainLayout.setCenter(outputArea);

    // Create scene
    final Scene scene = new Scene(mainLayout, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private <T> Optional<T> showDialog(String title, String content, Dialog<T> dialog) {
    return dialog.showAndWait();
  }

  public Vehicle getVehicleInput() {
    final Dialog<Vehicle> dialog = new Dialog<>();
    dialog.setTitle("Add Vehicle");
    dialog.setHeaderText("Enter vehicle details");

    // Create fields
    final TextField makeField = new TextField();
    final TextField modelField = new TextField();
    final TextField yearField = new TextField();
    final TextField colorField = new TextField();
    final TextField priceField = new TextField();

    // Create layout
    final GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    grid.add(new Label("Make:"), 0, 0);
    grid.add(makeField, 1, 0);
    grid.add(new Label("Model:"), 0, 1);
    grid.add(modelField, 1, 1);
    grid.add(new Label("Year:"), 0, 2);
    grid.add(yearField, 1, 2);
    grid.add(new Label("Color:"), 0, 3);
    grid.add(colorField, 1, 3);
    grid.add(new Label("Price:"), 0, 4);
    grid.add(priceField, 1, 4);

    dialog.getDialogPane().setContent(grid);

    // Add buttons
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    // Convert result
    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == ButtonType.OK) {
            try {
              return Vehicle.builder()
                  .make(makeField.getText())
                  .model(modelField.getText())
                  .year(Integer.parseInt(yearField.getText()))
                  .color(colorField.getText())
                  .price(Double.parseDouble(priceField.getText()))
                  .build();
            } catch (NumberFormatException e) {
              displayMessage("Invalid number format");
              return null;
            }
          }
          return null;
        });

    return showDialog("Add Vehicle", "Enter vehicle details", dialog).orElse(null);
  }

  public Long getVehicleId() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Vehicle ID");
    dialog.setHeaderText("Enter vehicle ID");
    dialog.setContentText("ID:");

    return showDialog("Vehicle ID", "Enter ID", dialog).map(Long::parseLong).orElse(null);
  }

  public void displayVehicle(Vehicle vehicle) {
    Platform.runLater(
        () -> {
          if (vehicle == null) {
            outputArea.appendText("\nNo vehicle found.\n");
            return;
          }
          outputArea.appendText("\nVehicle Details:\n" + vehicle.toString() + "\n");
        });
  }

  public void displayVehicles(List<Vehicle> vehicles) {
    Platform.runLater(
        () -> {
          if (vehicles.isEmpty()) {
            outputArea.appendText("\nNo vehicles found.\n");
            return;
          }
          outputArea.appendText("\nAll Vehicles:\n");
          vehicles.stream()
              .map(vehicle -> vehicle.toString() + "\n")
              .forEach(outputArea::appendText);
        });
  }

  public void displayMessage(String message) {
    Platform.runLater(
        () -> {
          outputArea.appendText("\n" + message + "\n");
        });
  }

  public Vehicle getUpdateVehicleInput(Vehicle existing) {
    final Dialog<Vehicle> dialog = new Dialog<>();
    dialog.setTitle("Update Vehicle");
    dialog.setHeaderText("Update vehicle details");

    // Create fields
    final TextField makeField = new TextField(existing.getMake());
    final TextField modelField = new TextField(existing.getModel());
    final TextField yearField = new TextField(String.valueOf(existing.getYear()));
    final TextField colorField = new TextField(existing.getColor());
    final TextField priceField = new TextField(String.valueOf(existing.getPrice()));

    // Create layout
    final GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    grid.add(new Label("Make:"), 0, 0);
    grid.add(makeField, 1, 0);
    grid.add(new Label("Model:"), 0, 1);
    grid.add(modelField, 1, 1);
    grid.add(new Label("Year:"), 0, 2);
    grid.add(yearField, 1, 2);
    grid.add(new Label("Color:"), 0, 3);
    grid.add(colorField, 1, 3);
    grid.add(new Label("Price:"), 0, 4);
    grid.add(priceField, 1, 4);

    dialog.getDialogPane().setContent(grid);
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == ButtonType.OK) {
            try {
              return Vehicle.builder()
                  .id(existing.getId())
                  .make(makeField.getText().isEmpty() ? existing.getMake() : makeField.getText())
                  .model(
                      modelField.getText().isEmpty() ? existing.getModel() : modelField.getText())
                  .year(
                      yearField.getText().isEmpty()
                          ? existing.getYear()
                          : Integer.parseInt(yearField.getText()))
                  .color(
                      colorField.getText().isEmpty() ? existing.getColor() : colorField.getText())
                  .price(
                      priceField.getText().isEmpty()
                          ? existing.getPrice()
                          : Double.parseDouble(priceField.getText()))
                  .build();
            } catch (NumberFormatException e) {
              displayMessage("Invalid number format");
              return existing;
            }
          }
          return existing;
        });

    return showDialog("Update Vehicle", "Update vehicle details", dialog).orElse(existing);
  }
}
