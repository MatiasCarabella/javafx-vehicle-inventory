package com.vehicleapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a vehicle entity with its basic attributes. Uses Lombok for getter/setter/builder
 * generation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
  private Long id;
  private String make;
  private String model;
  private int year;
  private String color;
  private double price;
}
