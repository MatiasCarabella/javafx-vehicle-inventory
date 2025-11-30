package com.vehicleapp.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.vehicleapp.controller.VehicleController;
import com.vehicleapp.model.Vehicle;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;

/**
 * Modern web-based view using JavaFX WebView. Provides a sleek HTML/CSS/JS interface for vehicle
 * management with smooth animations and professional design.
 */
@NoArgsConstructor
public class VehicleWebView {
  private Stage primaryStage;
  private WebEngine webEngine;
  private ObjectMapper objectMapper = new ObjectMapper();

  @Inject private VehicleController controller;

  public void initializeUI(Stage stage) {
    this.primaryStage = stage;
    primaryStage.setTitle("Vehicle Inventory Management System");

    // Create WebView
    WebView webView = new WebView();
    webEngine = webView.getEngine();

    // Enable JavaScript
    webEngine.setJavaScriptEnabled(true);

    // Set up status change listener to intercept commands from JavaScript
    webEngine.setOnStatusChanged(
        event -> {
          String status = event.getData();
          if (status != null && status.contains(":")) {
            String[] parts = status.split(":", 2);
            String action = parts[0];
            String params = parts.length > 1 ? parts[1] : "";

            javafx.application.Platform.runLater(
                () -> {
                  try {
                    switch (action) {
                      case "addVehicle":
                        handleAddVehicle(java.net.URLDecoder.decode(params, "UTF-8"));
                        break;
                      case "updateVehicle":
                        handleUpdateVehicle(java.net.URLDecoder.decode(params, "UTF-8"));
                        break;
                      case "deleteVehicle":
                        handleDeleteVehicle(Long.parseLong(params));
                        break;
                    }
                  } catch (Exception e) {
                    showToast("Error: " + e.getMessage(), "error");
                  }
                });
          }
        });

    // Load the HTML content
    webEngine
        .getLoadWorker()
        .stateProperty()
        .addListener(
            (obs, oldState, newState) -> {
              if (newState == Worker.State.SUCCEEDED) {
                refreshVehicles();
              }
            });

    // Load HTML from resources
    String htmlContent = loadResource("/ui/index.html");
    String cssContent = loadResource("/ui/styles.css");
    String jsContent = loadResource("/ui/app.js");

    // Inject CSS and JS into HTML
    htmlContent =
        htmlContent.replace(
            "<link rel=\"stylesheet\" href=\"styles.css\">", "<style>" + cssContent + "</style>");
    htmlContent =
        htmlContent.replace(
            "<script src=\"app.js\"></script>", "<script>" + jsContent + "</script>");

    webEngine.loadContent(htmlContent);

    // Create scene
    StackPane root = new StackPane(webView);
    Scene scene = new Scene(root, 1200, 700);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Loads a resource file from the classpath.
   *
   * @param path The resource path
   * @return The resource content as a string
   * @throws RuntimeException if the resource cannot be loaded
   */
  private String loadResource(String path) {
    try (InputStream is = getClass().getResourceAsStream(path)) {
      if (is == null) {
        throw new RuntimeException("Resource not found: " + path);
      }
      return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
          .lines()
          .collect(Collectors.joining("\n"));
    } catch (Exception e) {
      throw new RuntimeException("Failed to load resource: " + path, e);
    }
  }

  /** Refreshes the vehicle list in the web UI by fetching current data from the controller. */
  private void refreshVehicles() {
    try {
      List<Vehicle> vehicles = controller.listVehicles();
      String json = objectMapper.writeValueAsString(vehicles);
      webEngine.executeScript("updateVehicles('" + escapeForJavaScript(json) + "')");
    } catch (JsonProcessingException e) {
      showToast("Error loading vehicles: " + e.getMessage(), "error");
    }
  }

  /**
   * Displays a toast notification in the web UI.
   *
   * @param message The message to display
   * @param type The type of toast (success, error, etc.)
   */
  private void showToast(String message, String type) {
    webEngine.executeScript(
        "showToast('" + escapeForJavaScript(message) + "', '" + escapeForJavaScript(type) + "')");
  }

  /**
   * Escapes a string for safe use in JavaScript code.
   *
   * @param text The text to escape
   * @return Escaped text safe for JavaScript
   */
  private String escapeForJavaScript(String text) {
    return text.replace("\\", "\\\\").replace("'", "\\'").replace("\n", "\\n").replace("\r", "");
  }

  /**
   * Handles adding a new vehicle from the web UI.
   *
   * @param vehicleJson JSON string containing vehicle data
   */
  private void handleAddVehicle(String vehicleJson) {
    try {
      Vehicle vehicle = objectMapper.readValue(vehicleJson, Vehicle.class);
      controller.addVehicle(vehicle);
      refreshVehicles();
      showToast("Vehicle added successfully!", "success");
    } catch (Exception e) {
      showToast("Error adding vehicle: " + e.getMessage(), "error");
    }
  }

  /**
   * Handles updating an existing vehicle from the web UI.
   *
   * @param vehicleJson JSON string containing updated vehicle data
   */
  private void handleUpdateVehicle(String vehicleJson) {
    try {
      Vehicle vehicle = objectMapper.readValue(vehicleJson, Vehicle.class);
      controller.updateVehicle(vehicle);
      refreshVehicles();
      showToast("Vehicle updated successfully!", "success");
    } catch (Exception e) {
      showToast("Error updating vehicle: " + e.getMessage(), "error");
    }
  }

  /**
   * Handles deleting a vehicle from the web UI.
   *
   * @param id ID of the vehicle to delete
   */
  private void handleDeleteVehicle(long id) {
    try {
      controller.deleteVehicle(id);
      refreshVehicles();
      showToast("Vehicle deleted successfully!", "success");
    } catch (Exception e) {
      showToast("Error deleting vehicle: " + e.getMessage(), "error");
    }
  }
}
