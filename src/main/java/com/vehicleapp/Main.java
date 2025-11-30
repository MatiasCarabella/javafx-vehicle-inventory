package com.vehicleapp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vehicleapp.config.AppModule;
import com.vehicleapp.view.VehicleWebView;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;

/**
 * Main entry point for the Vehicle Management System application. Initializes the Guice dependency
 * injection container and launches the modern web-based JavaFX UI.
 */
@NoArgsConstructor
public class Main extends Application {
  private static Injector injector;
  private VehicleWebView view;

  @Override
  public void init() {
    // Initialize Guice and create view
    injector = Guice.createInjector(new AppModule());
    view = injector.getInstance(VehicleWebView.class);
  }

  @Override
  public void start(Stage primaryStage) {
    view.initializeUI(primaryStage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
