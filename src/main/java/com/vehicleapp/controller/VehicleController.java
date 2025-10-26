package com.vehicleapp.controller;

import com.vehicleapp.model.Vehicle;
import com.vehicleapp.service.VehicleService;
import com.vehicleapp.view.VehicleFXView;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VehicleController {
  private final VehicleService service;
  private final VehicleFXView view;

  public void addVehicle() {
    try {
      final Vehicle vehicle = view.getVehicleInput();
      final Vehicle saved = service.createVehicle(vehicle);
      view.displayMessage("Vehicle added successfully with ID: " + saved.getId());
    } catch (Exception e) {
      view.displayMessage("Error adding vehicle: " + e.getMessage());
    }
  }

  public void listVehicles() {
    try {
      view.displayVehicles(service.getAllVehicles());
    } catch (Exception e) {
      view.displayMessage("Error fetching vehicles: " + e.getMessage());
    }
  }

  public void findVehicle() {
    try {
      final Long id = view.getVehicleId();
      final Optional<Vehicle> vehicle = service.getVehicle(id);
      if (vehicle.isPresent()) {
        view.displayVehicle(vehicle.get());
      } else {
        view.displayMessage("Vehicle not found.");
      }
    } catch (Exception e) {
      view.displayMessage("Error finding vehicle: " + e.getMessage());
    }
  }

  public void updateVehicle() {
    try {
      final Long id = view.getVehicleId();
      final Optional<Vehicle> existingVehicle = service.getVehicle(id);
      if (existingVehicle.isPresent()) {
        final Vehicle updated = view.getUpdateVehicleInput(existingVehicle.get());
        service.updateVehicle(updated);
        view.displayMessage("Vehicle updated successfully.");
      } else {
        view.displayMessage("Vehicle not found.");
      }
    } catch (Exception e) {
      view.displayMessage("Error updating vehicle: " + e.getMessage());
    }
  }

  public void deleteVehicle() {
    try {
      final Long id = view.getVehicleId();
      if (service.getVehicle(id).isPresent()) {
        service.deleteVehicle(id);
        view.displayMessage("Vehicle deleted successfully.");
      } else {
        view.displayMessage("Vehicle not found.");
      }
    } catch (Exception e) {
      view.displayMessage("Error deleting vehicle: " + e.getMessage());
    }
  }
}
