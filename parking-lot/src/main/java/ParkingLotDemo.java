import domain.ParkingFloor;
import domain.ParkingTicket;
import domain.SpotSize;
import domain.vehicle.Car;
import domain.vehicle.MotorCycle;
import domain.vehicle.Truck;
import domain.vehicle.VehicleType;
import observer.ConsoleAvailabilityDisplay;
import strategy.fee.VehicleBasedHourlyPricingStrategy;
import strategy.parking.NearestBestSpotAllocationStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotDemo {
    public static void main(String[] args) {
        List<ParkingFloor> parkingFloors = new ArrayList<>();
        parkingFloors.add(new ParkingFloor(0, 1, 1, 1)); // ground floor: 2 small, 2 medium, 1 large
        parkingFloors.add(new ParkingFloor(1, 1, 2, 0)); // floor 1: 2 small, 2 medium, 2 large

        Map<VehicleType, Double> rates = new HashMap<>();
        rates.put(VehicleType.MOTORCYCLE, 10.0);
        rates.put(VehicleType.CAR, 30.0);
        rates.put(VehicleType.TRUCK, 60.0);

        ParkingLot parkingLot = new ParkingLot(
                parkingFloors,
                new NearestBestSpotAllocationStrategy(),
                new VehicleBasedHourlyPricingStrategy(rates)
        );
        parkingLot.addObserver(new ConsoleAvailabilityDisplay());

        //stores vehicle number, ticket for testing purpose
        Map<String, ParkingTicket> issuedTickets = new HashMap<>();

        System.out.println("===== Initial availability =====");
        Map<SpotSize, Long> availabilityMap = parkingLot.getAvailability();
        System.out.println("[Display] Available Spots: "+availabilityMap);

        System.out.println("===== Scenario 1: Park a motorcycle, car, and truck =====");
        try{
            ParkingTicket ticket1 = parkingLot.parkVehicle(new MotorCycle("TG-BIKE-1", VehicleType.MOTORCYCLE));
            issuedTickets.put("TG-BIKE-1", ticket1);
            System.out.println("Issued ticket "+ticket1.getTicketId()+" for the vehicle TG-BIKE-1 with spot "+ticket1.getParkingSpot());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            ParkingTicket ticket2 = parkingLot.parkVehicle(new Car("TG-CAR-1", VehicleType.CAR));
            issuedTickets.put("TG-CAR-1", ticket2);
            System.out.println("Issued ticket "+ticket2.getTicketId()+" for the vehicle TG-CAR-1 with spot "+ticket2.getParkingSpot());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            ParkingTicket ticket3 = parkingLot.parkVehicle(new Truck("TG-TRUCK-1", VehicleType.TRUCK));
            issuedTickets.put("TG-TRUCK-1", ticket3);
            System.out.println("Issued ticket "+ticket3.getTicketId()+" for the vehicle TG-TRUCK-1 spot "+ticket3.getParkingSpot());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n===== Scenario 2: try to park truck and no large spot available =====");
        try{
            ParkingTicket ticket4 = parkingLot.parkVehicle(new Truck("TG-TRUCK-2", VehicleType.TRUCK));
            issuedTickets.put("TG-TRUCK-2", ticket4);
            System.out.println("Issued ticket "+ticket4.getTicketId()+" for the vehicle TG-TRUCK-2 with spot "+ticket4.getParkingSpot());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n===== Scenario 3: try to park 2 bikes and only 1 small spot available but a medium spot is available and allocated for 2nd bike  =====");
        try{
            ParkingTicket ticket5 = parkingLot.parkVehicle(new MotorCycle("TG-BIKE-2", VehicleType.MOTORCYCLE));
            issuedTickets.put("TG-BIKE-2", ticket5);
            System.out.println("Issued ticket "+ticket5.getTicketId()+" for the vehicle TG-BIKE-2 with spot "+ticket5.getParkingSpot());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            ParkingTicket ticket6 = parkingLot.parkVehicle(new MotorCycle("TG-BIKE-3", VehicleType.MOTORCYCLE));
            issuedTickets.put("TG-BIKE-3", ticket6);
            System.out.println("Issued ticket "+ticket6.getTicketId()+" for the vehicle TG-BIKE-3 with spot "+ticket6.getParkingSpot());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("\n===== Scenario 4: try to unpark truck =====");
        try{
            Thread.sleep(60000);
            double fee = parkingLot.unparkVehicle(issuedTickets.get("TG-TRUCK-1").getTicketId());
            System.out.println("unparked vehicle TG-TRUCK1 and the calculated fee is: "+fee);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("\n===== Scenario 5: try to park truck2 again and this time large spot became available =====");
        try{
            ParkingTicket ticket7 = parkingLot.parkVehicle(new Truck("TG-TRUCK-2", VehicleType.TRUCK));
            issuedTickets.put("TG-TRUCK-2", ticket7);
            System.out.println("Issued ticket "+ticket7.getTicketId()+" for the vehicle TG-TRUCK-2 with spot "+ticket7.getParkingSpot());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
