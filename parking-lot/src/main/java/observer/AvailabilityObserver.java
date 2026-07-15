package observer;

import domain.SpotSize;

import java.util.Map;

public interface AvailabilityObserver {
    void onAvailabilityChange(Map<SpotSize, Long> availability);
}
