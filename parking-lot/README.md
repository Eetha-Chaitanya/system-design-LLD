# Parking Lot
Low Level Design implementation in Java.

# Functional Requirements
Core flow

1. A vehicle enters the parking lot at an entry gate, is issued a ticket, and is directed to (or finds) an available spot
2. A vehicle exits at an exit gate, presents the ticket, pays, and the spot is freed
3. Support multiple vehicle types: Motorcycle, Car, Truck/Bus — each requiring different spot sizes
4. Support multiple spot types: Small (motorcycle only), Medium (car), Large (truck/bus) — with reasonable size compatibility (e.g. a car can also park in a Large spot if no Medium is free, but a truck cannot fit in Small/Medium)
5. Multi-level parking lot — several floors, each with its own set of spots
6. Spot allocation strategy: find nearest available compatible spot (start from nearest floor/entry point outward) — pluggable, so allocation logic could later change (e.g. "fill floor 1 completely before floor 2")
7. Pricing: time-based, and it should differ by vehicle type (motorcycle cheaper per hour than truck) — pluggable, so a flat-rate or weekend-rate strategy could be swapped in later without touching the rest of the system
8. Display/query: number of available spots per floor per type, in real time

Edge cases to explicitly handle

1. Lot is full for a given vehicle type → entry should be rejected with a clear reason, not silently fail

# Non-Functional Requirements

1. Concurrency correctness: two vehicles should never be assigned the same spot, even if they "arrive" at the same instant — this is the headline correctness concern for this problem
2. Extensibility: adding a new vehicle type, a new spot type, a new pricing model, or a new allocation strategy should mean adding a class, not editing existing ones (Open/Closed Principle)

Explicit Assumptions (say these out loud in the actual interview)

1. Single parking lot instance (not modeling a multi-lot enterprise system) — but the design should make multi-lot a plausible future extension
2. Ticket is a simple in-memory object (entry timestamp, vehicle, assigned spot) — not modeling barcode/RFID hardware
3. Payment is not handled
4. If ticket is lost or invalid, admin will collect the fee manually and periodic reconcilation happens to free up the spots