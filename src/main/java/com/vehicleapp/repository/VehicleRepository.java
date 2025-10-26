package com.vehicleapp.repository;

import com.vehicleapp.model.Vehicle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository layer handling data persistence for vehicles using H2 database. Provides CRUD
 * operations and manages database connections.
 */
public class VehicleRepository {
  private static final String DB_URL = "jdbc:h2:./vehicledb;DB_CLOSE_DELAY=-1";
  private static final String DB_USER = "sa";
  private static final String DB_PASSWORD = "";

  public VehicleRepository() {
    try {
      Class.forName("org.h2.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Failed to load H2 driver", e);
    }
    initializeDatabase();
  }

  private void initializeDatabase() {
    try (Connection conn = getConnection()) {
      final String createTable =
          """
                CREATE TABLE IF NOT EXISTS vehicles (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    make VARCHAR(50) NOT NULL,
                    model VARCHAR(50) NOT NULL,
                    manufacture_year INT NOT NULL,
                    color VARCHAR(30) NOT NULL,
                    price DOUBLE NOT NULL
                )
            """;
      try (Statement stmt = conn.createStatement()) {
        stmt.execute(createTable);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to initialize database", e);
    }
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
  }

  public Vehicle save(Vehicle vehicle) {
    final String sql =
        vehicle.getId() == null
            ? "INSERT INTO vehicles (make, model, manufacture_year, color, price) VALUES (?, ?, ?, ?, ?)"
            : "UPDATE vehicles SET make=?, model=?, manufacture_year=?, color=?, price=? WHERE id=?";

    try (final Connection conn = getConnection();
        final PreparedStatement stmt =
            conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      stmt.setString(1, vehicle.getMake());
      stmt.setString(2, vehicle.getModel());
      stmt.setInt(3, vehicle.getYear());
      stmt.setString(4, vehicle.getColor());
      stmt.setDouble(5, vehicle.getPrice());

      if (vehicle.getId() != null) {
        stmt.setLong(6, vehicle.getId());
      }

      stmt.executeUpdate();

      if (vehicle.getId() == null) {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
          if (rs.next()) {
            vehicle.setId(rs.getLong(1));
          }
        }
      }

      return vehicle;
    } catch (SQLException e) {
      throw new RuntimeException("Failed to save vehicle", e);
    }
  }

  public Optional<Vehicle> findById(Long id) {
    try (final Connection conn = getConnection();
        final PreparedStatement stmt =
            conn.prepareStatement("SELECT * FROM vehicles WHERE id = ?")) {

      stmt.setLong(1, id);
      final ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        return Optional.of(mapResultSetToVehicle(rs));
      }
      return Optional.empty();
    } catch (SQLException e) {
      throw new RuntimeException("Failed to find vehicle by id", e);
    }
  }

  public List<Vehicle> findAll() {
    final List<Vehicle> vehicles = new ArrayList<>();
    try (Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM vehicles")) {

      while (rs.next()) {
        vehicles.add(mapResultSetToVehicle(rs));
      }
      return vehicles;
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch all vehicles", e);
    }
  }

  public void deleteById(Long id) {
    try (final Connection conn = getConnection();
        final PreparedStatement stmt = conn.prepareStatement("DELETE FROM vehicles WHERE id = ?")) {

      stmt.setLong(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Failed to delete vehicle", e);
    }
  }

  private Vehicle mapResultSetToVehicle(ResultSet rs) throws SQLException {
    return new Vehicle(
        rs.getLong("id"),
        rs.getString("make"),
        rs.getString("model"),
        rs.getInt("manufacture_year"),
        rs.getString("color"),
        rs.getDouble("price"));
  }
}
