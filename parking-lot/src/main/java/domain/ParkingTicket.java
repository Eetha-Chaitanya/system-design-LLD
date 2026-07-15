package domain;

import domain.vehicle.Vehicle;

import java.time.Instant;
import java.util.UUID;

public class ParkingTicket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot parkingSpot;
    private final Instant entryTime;
    private Instant exitTime;
    private TicketStatus status;

    public ParkingTicket(Vehicle vehicle, ParkingSpot parkingSpot) {
        this.ticketId = UUID.randomUUID().toString();
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = Instant.now();
        this.status = TicketStatus.ACTIVE;
    }
    public String getTicketId() {
        return ticketId;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }
    public Instant getEntryTime() {
        return entryTime;
    }
    public Instant getExitTime() {
        return exitTime;
    }
    public void setExitTime(Instant exitTime) {
        this.exitTime = exitTime;
    }
    public TicketStatus getStatus() {
        return status;
    }
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
