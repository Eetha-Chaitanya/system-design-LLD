package domain;

import domain.vehicle.Vehicle;

import java.util.concurrent.atomic.AtomicReference;

public class ParkingSpot {
    private final String spotId;
    private final SpotSize spotSize;
    private final int floorNumber;
    private final AtomicReference<Vehicle> currentVehicle = new AtomicReference<>(null);

    public ParkingSpot(String spotId, SpotSize spotSize, int floorNumber){
        this.spotId = spotId;
        this.spotSize = spotSize;
        this.floorNumber = floorNumber;
    }

    public String getSpotId() {
        return spotId;
    }
    public SpotSize getSpotSize() {
        return spotSize;
    }
    public int getFloorNumber() {
        return floorNumber;
    }
    public Vehicle getCurrentVehicle() {
        return currentVehicle.get();
    }

    public boolean canFitVehicle(Vehicle vehicle) {
        return this.spotSize.ordinal() >= vehicle.getMinimumSpotSize().ordinal();
    }

    public boolean isAvailable() {
        return currentVehicle.get() == null;
    }

    //this will atomically sets the current vehicle,
    // hence only ONE thread can win this even if two vehicles "arrive" simultaneously
    public boolean allocateSpot(Vehicle vehicle) {
        return currentVehicle.compareAndSet(null, vehicle);
    }

    public void vacateSpot() {
        currentVehicle.set(null);
    }

    @Override
    public String toString() {
        return spotId+" ("+spotSize+")";
    }
}
