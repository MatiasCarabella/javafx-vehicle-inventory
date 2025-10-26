package com.vehicleapp.view;

import com.vehicleapp.controller.VehicleController;
import com.vehicleapp.model.Vehicle;
import com.vehicleapp.service.VehicleService;
import com.vehicleapp.util.InjectorHolder;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VehicleFXView extends Application {
  private Stage primaryStage;
  private TextArea outputArea;
  private VehicleController controller;

  @Override
  public void start(Stage stage) {
    this.primaryStage = stage;
    primaryStage.setTitle("Vehicle Management System");

    // Initialize application components via Guice
    VehicleService service = InjectorHolder.getInjector().getInstance(VehicleService.class);
    this.controller = new VehicleController(service, this);

    // Create main layout
    final BorderPane mainLayout = new BorderPane();

    // Create menu buttons
    final VBox menuButtons = new VBox(10);
    menuButtons.setPadding(new Insets(10));

    // Add vehicle button
    final Button addButton = new Button("Add new vehicle");
    addButton.setMaxWidth(Double.MAX_VALUE);
    addButton.setOnAction(e -> controller.addVehicle());

    // List vehicles button
    final Button listButton = new Button("List all vehicles");
    listButton.setMaxWidth(Double.MAX_VALUE);
    listButton.setOnAction(e -> controller.listVehicles());

    // Find vehicle button
    final Button findButton = new Button("Find vehicle by ID");
    findButton.setMaxWidth(Double.MAX_VALUE);
    findButton.setOnAction(e -> controller.findVehicle());

    // Update vehicle button
    final Button updateButton = new Button("Update vehicle");
    updateButton.setMaxWidth(Double.MAX_VALUE);
    updateButton.setOnAction(e -> controller.updateVehicle());

    // Delete vehicle button
    final Button deleteButton = new Button("Delete vehicle");
    deleteButton.setMaxWidth(Double.MAX_VALUE);
    deleteButton.setOnAction(e -> controller.deleteVehicle());

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
    Dialog<Vehicle> dialog = new Dialog<>();
    dialog.setTitle("Add Vehicle");
    dialog.setHeaderText("Enter vehicle details");

    // Create fields
    TextField makeField = new TextField();
    TextField modelField = new TextField();
    TextField yearField = new TextField();
    TextField colorField = new TextField();
    TextField priceField = new TextField();

    // Create layout
    GridPane grid = new GridPane();
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
    Dialog<Vehicle> dialog = new Dialog<>();
    dialog.setTitle("Update Vehicle");
    dialog.setHeaderText("Update vehicle details");

    // Create fields
    TextField makeField = new TextField(existing.getMake());
    TextField modelField = new TextField(existing.getModel());
    TextField yearField = new TextField(String.valueOf(existing.getYear()));
    TextField colorField = new TextField(existing.getColor());
    TextField priceField = new TextField(String.valueOf(existing.getPrice()));

    // Create layout
    GridPane grid = new GridPane();
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

  public static void launchApp() {
    Application.launch(VehicleFXView.class);
  }
}
