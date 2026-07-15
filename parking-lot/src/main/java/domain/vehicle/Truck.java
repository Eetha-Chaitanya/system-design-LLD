package domain.vehicle;

import domain.SpotSize;

public class Truck extends Vehicle {

    public Truck(String vehicleId, VehicleType vehicleType){
        super(vehicleId, vehicleType);
    }

    @Override
    public SpotSize getMinimumSpotSize() {
        return SpotSize.LARGE;
    }
}
