package com.vehicleapp.controller;

import com.google.inject.Inject;
import com.vehicleapp.model.Vehicle;
import com.vehicleapp.service.VehicleService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 * Controller component handling vehicle-related operations. Acts as a bridge between the view and
 * service layer, providing error handling and data transformation.
 */
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VehicleController {
  private final VehicleService service;

  public Vehicle addVehicle(final Vehicle vehicle) {
    try {
      return service.createVehicle(vehicle);
    } catch (Exception e) {
      throw new RuntimeException("Error adding vehicle: " + e.getMessage(), e);
    }
  }

  public List<Vehicle> listVehicles() {
    try {
      return service.getAllVehicles();
    } catch (Exception e) {
      throw new RuntimeException("Error fetching vehicles: " + e.getMessage(), e);
    }
  }

  public Optional<Vehicle> findVehicle(final Long id) {
    try {
      return service.getVehicle(id);
    } catch (Exception e) {
      throw new RuntimeException("Error finding vehicle: " + e.getMessage(), e);
    }
  }

  public Vehicle updateVehicle(final Vehicle vehicle) {
    try {
      service.updateVehicle(vehicle);
      return vehicle;
    } catch (Exception e) {
      throw new RuntimeException("Error updating vehicle: " + e.getMessage(), e);
    }
  }

  public void deleteVehicle(final Long id) {
    try {
      service.deleteVehicle(id);
    } catch (Exception e) {
      throw new RuntimeException("Error deleting vehicle: " + e.getMessage(), e);
    }
  }
}
