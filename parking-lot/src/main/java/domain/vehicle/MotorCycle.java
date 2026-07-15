package domain.vehicle;

import domain.SpotSize;

public class MotorCycle extends Vehicle {
    public MotorCycle(String vehicleId, VehicleType vehicleType) {
        super(vehicleId, vehicleType);
    }

    @Override
    public SpotSize getMinimumSpotSize() {
        return SpotSize.SMALL;
    }
}
