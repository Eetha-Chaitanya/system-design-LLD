package strategy.fee;

import domain.vehicle.Vehicle;
import domain.vehicle.VehicleType;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class VehicleBasedHourlyPricingStrategy implements PricingStrategy {
    private final Map<VehicleType, Double> hourlyRates;
    public  VehicleBasedHourlyPricingStrategy(Map<VehicleType, Double> hourlyRates) {
        this.hourlyRates = hourlyRates;
    }

    @Override
    public double calculateFee(Vehicle vehicle, Instant entryTime, Instant exitTime) {
        Duration duration = Duration.between(entryTime, exitTime);
        long minutes = duration.toMinutes();
        long billableHours = (long)Math.ceil(minutes/60.0);
        double fee = billableHours * hourlyRates.getOrDefault(vehicle.getVehicleType(), 0.0);
        return fee;
    }
}
