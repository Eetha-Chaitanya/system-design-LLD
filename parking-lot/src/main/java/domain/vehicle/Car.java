package domain.vehicle;

import domain.SpotSize;

public class Car extends Vehicle {

    public Car(String vehicleId, VehicleType vehicleType){
        super(vehicleId, vehicleType);
    }

    @Override
    public SpotSize getMinimumSpotSize() {
        return SpotSize.MEDIUM;
    }
}
