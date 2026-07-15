package strategy.fee;

import domain.vehicle.Vehicle;

import java.time.Instant;

public interface PricingStrategy {
    /*instant is passed for entry and exit times so that
       a weekend pricing strategy can be implemented in future where the date information is reqquired.
       Ticket is not passed since a future requirement of getting the price quote
       before parking or buying ticket can be achieved*/
    double calculateFee(Vehicle vehicle, Instant entryTime, Instant exitTime);
}
