package observer;

import domain.SpotSize;

import java.util.Map;

public class ConsoleAvailabilityDisplay implements AvailabilityObserver {
    @Override
    public void onAvailabilityChange(Map<SpotSize, Long> availability) {
        System.out.println("[Display] Available Spots: "+availability);
    }
}
