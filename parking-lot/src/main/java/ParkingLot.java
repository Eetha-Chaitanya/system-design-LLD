import domain.*;
import domain.vehicle.Vehicle;
import exception.InvalidTicketException;
import exception.ParkingFullException;
import observer.AvailabilityObserver;
import strategy.fee.PricingStrategy;
import strategy.parking.SpotAllocationStrategy;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParkingLot {
    private final List<ParkingFloor>  parkingFloors;
    private SpotAllocationStrategy spotAllocationStrategy;
    private PricingStrategy pricingStrategy;
    private final ConcurrentHashMap<String, ParkingTicket> activeTickets = new ConcurrentHashMap<>();
    private final List<AvailabilityObserver> observers = new CopyOnWriteArrayList<>();

    public ParkingLot(List<ParkingFloor> parkingFloors, SpotAllocationStrategy spotAllocationStrategy, PricingStrategy pricingStrategy) {
        this.parkingFloors = parkingFloors;
        this.spotAllocationStrategy = spotAllocationStrategy;
        this.pricingStrategy = pricingStrategy;
    }

    public void addFloor(ParkingFloor parkingFloor) {
        this.parkingFloors.add(parkingFloor);
    }

    public void setSpotAllocationStrategy(SpotAllocationStrategy spotAllocationStrategy) {
        this.spotAllocationStrategy = spotAllocationStrategy;
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public void addObserver(AvailabilityObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(AvailabilityObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        Map<SpotSize, Long> availability = getAvailability();
        for (AvailabilityObserver observer : observers) {
            observer.onAvailabilityChange(availability);
        }
    }

    public Map<SpotSize, Long> getAvailability() {
        Map<SpotSize, Long> availabilityMap = new HashMap<>();
        for (ParkingFloor parkingFloor : parkingFloors) {
            parkingFloor.getAvailableCountBySize().forEach((size, count) -> availabilityMap.put(size, availabilityMap.getOrDefault(size, 0L) + count));
        }
        return availabilityMap;
    }

    public ParkingTicket parkVehicle(Vehicle vehicle) throws Exception{
        Optional<ParkingSpot> spot = spotAllocationStrategy.allocateSpot(parkingFloors, vehicle);
        if(spot.isEmpty()){
            throw new ParkingFullException("No available spot for "+vehicle.getVehicleType()+" ("+vehicle.getVehicleId()+")");
        }
        ParkingTicket ticket = new ParkingTicket(vehicle, spot.get());
        activeTickets.put(ticket.getTicketId(), ticket);
        notifyObservers();
        return ticket;
    }

    public double unparkVehicle(String ticketId) throws Exception{
        ParkingTicket ticket = activeTickets.get(ticketId);
        if(ticket == null){
            throw new InvalidTicketException("Invalid ticket id "+ticketId);
        }
        if(!ticket.getStatus().equals(TicketStatus.ACTIVE)){
            throw new InvalidTicketException("Ticket "+ticketId+"is not active (status: "+ticket.getStatus()+" )");
        }
        Instant exitTime = Instant.now();
        ticket.setExitTime(exitTime);
        double fee = pricingStrategy.calculateFee(ticket.getVehicle(), ticket.getEntryTime(), exitTime);
        ticket.getParkingSpot().vacateSpot();
        ticket.setStatus(TicketStatus.INACTIVE);
        activeTickets.remove(ticket.getTicketId());
        notifyObservers();
        return fee;
    }

}
