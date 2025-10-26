package com.vehicleapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Vehicle {
  private Long id;
  private String make;
  private String model;
  private int year;
  private String color;
  private double price;
}
