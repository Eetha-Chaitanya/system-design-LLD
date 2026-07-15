package strategy.parking;

import domain.ParkingFloor;
import domain.ParkingSpot;
import domain.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;

public interface SpotAllocationStrategy {
    Optional<ParkingSpot> allocateSpot(List<ParkingFloor> floors, Vehicle vehicle);
}
