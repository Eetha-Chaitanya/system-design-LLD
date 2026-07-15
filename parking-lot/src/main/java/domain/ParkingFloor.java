package domain;

import domain.vehicle.Vehicle;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingFloor {
    private final int floorNumber;
    private List<ParkingSpot> parkingSpots;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
    }
    public ParkingFloor(int floorNumber, int numOfSmallSpots, int numOfMediumSpots, int numOfLargeSpots) {
        this.floorNumber = floorNumber;
        this.parkingSpots = new ArrayList<>();
        for (int i = 0; i < numOfSmallSpots; i++) {
            parkingSpots.add(new ParkingSpot("F"+floorNumber+"-S"+i, SpotSize.SMALL, floorNumber));
        }
        for(int i = 0; i < numOfMediumSpots; i++) {
            parkingSpots.add(new ParkingSpot("F"+floorNumber+"-M"+i, SpotSize.MEDIUM, floorNumber));
        }
        for(int i = 0; i < numOfLargeSpots; i++) {
            parkingSpots.add(new ParkingSpot("F"+floorNumber+"-L"+i, SpotSize.LARGE, floorNumber));
        }
    }
    public void addParkingSpot(ParkingSpot parkingSpot) {
        parkingSpots.add(parkingSpot);
    }
    public int getFloorNumber() {
        return floorNumber;
    }

    // Sorted by size ascending so a motorcycle prefers a SMALL spot over "wasting" a LARGE one.
    public List<ParkingSpot> getCompatibleParkingSpotsSorted(Vehicle vehicle) {
        return parkingSpots.stream()
                            .filter(spot -> spot.isAvailable() && spot.canFitVehicle(vehicle))
                            .sorted(Comparator.comparingInt(spot -> spot.getSpotSize().ordinal()))
                            .collect(Collectors.toList());
    }

    public List<ParkingSpot> getMatchingParkingSpots(Vehicle vehicle) {
        return parkingSpots.stream()
                .filter(spot -> spot.isAvailable() && spot.getSpotSize().equals(vehicle.getMinimumSpotSize()))
                .collect(Collectors.toList());
    }

    public Map<SpotSize, Long> getAvailableCountBySize(){
        Map<SpotSize, Long> countMap = new HashMap<>();
        for(ParkingSpot spot : parkingSpots){
            if(spot.isAvailable()) {
                countMap.put(spot.getSpotSize(), countMap.getOrDefault(spot.getSpotSize(), 0L) + 1);
            }
        }
        return countMap;
    }

}
