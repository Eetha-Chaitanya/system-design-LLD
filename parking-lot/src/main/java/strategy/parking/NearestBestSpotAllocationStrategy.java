package strategy.parking;

import domain.ParkingFloor;
import domain.ParkingSpot;
import domain.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;

public class NearestBestSpotAllocationStrategy implements SpotAllocationStrategy {
    @Override
    public Optional<ParkingSpot> allocateSpot(List<ParkingFloor> floors, Vehicle vehicle) {
        for(ParkingFloor floor : floors){
            for(ParkingSpot spot : floor.getCompatibleParkingSpotsSorted(vehicle)){
                if(spot.allocateSpot(vehicle)){
                    return Optional.of(spot);
                }
            }
        }
        return Optional.empty();
    }
}
