package com.vehicleapp.service;

import com.google.inject.Inject;
import com.vehicleapp.model.Vehicle;
import com.vehicleapp.repository.VehicleRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 * Service layer handling business logic for vehicle operations. Provides data validation and
 * coordinates with the repository layer.
 */
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VehicleService {
  private final VehicleRepository repository;

  public Vehicle createVehicle(Vehicle vehicle) {
    return repository.save(vehicle);
  }

  public Optional<Vehicle> getVehicle(Long id) {
    return repository.findById(id);
  }

  public List<Vehicle> getAllVehicles() {
    return repository.findAll();
  }

  public Vehicle updateVehicle(Vehicle vehicle) {
    if (vehicle.getId() == null || repository.findById(vehicle.getId()).isEmpty()) {
      throw new IllegalArgumentException("Vehicle not found");
    }
    return repository.save(vehicle);
  }

  public void deleteVehicle(Long id) {
    repository.deleteById(id);
  }
}
