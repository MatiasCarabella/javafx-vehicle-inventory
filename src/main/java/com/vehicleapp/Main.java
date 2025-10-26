package com.vehicleapp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vehicleapp.config.AppModule;
import com.vehicleapp.util.InjectorHolder;
import com.vehicleapp.view.VehicleFXView;
import javafx.application.Application;

public class Main {
  public static void main(String[] args) {
    // Create Guice injector and store it so the JavaFX Application can access it
    final Injector injector = Guice.createInjector(new AppModule());
    InjectorHolder.setInjector(injector);

    // Launch JavaFX application
    Application.launch(VehicleFXView.class, args);
  }
}
