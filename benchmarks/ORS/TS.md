### CR 1:  "Validate Booking Eligibility": Check if a customer can book seats on a trip.

```
Computational Requirement: "Validate Booking Eligibility": Check if a customer can book seats on a trip. Eligibility: Trip must exist and have available seats. The number of available seats on the trip ≥ the number of booking seats. There are no overlapping time periods between existing bookings booked by customer and the new booking time on the same day. The current booking time must be at least 2 hours earlier than the departure time of the trip(excluding the case where it is exactly 2 hours). If a customer can book seats on a trip, return true and update the number of seats of the trip. 

+ Test Case 1: "Valid booking with available seats and no overlap" 
    Input: Check booking eligibility for Trip T123
    Setup: 1. Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
           2. Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00 (3 hours before departure)
    Expected Output: true, updated Trip T123 seats = 2
---
+ Test Case 2: "Booking denied due to seat shortage"  
    Input: Check booking eligibility for Trip T456
    Setup:1. Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
          2. Create Customer C2 with existing booking for T456 (3 seats)
          3. Current time: 2023-12-25 07:30 (2.5 hours before departure)
    Expected Output: false, Trip T123 seats = 2
---
+ Test Case 3: "Booking denied due to time cutoff (exactly 2 hours before)"
    Input: Check booking eligibility for Trip T100
    Setup:
    Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
    Current time: 2023-12-25 12:00 (exactly 2 hours before)
    Create Customer C3 with booking for T100 (3 seats)
    Expected Output: False, Trip T123 seats = 50
---
Test Case 4: "Booking allowed with multiple non-overlapping trips"
    Input: Check booking eligibility for Trip T200
    Setup:
    Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 ( 2023-12-25 08:00-10:00, 50 seats)
    Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 ( 2023-12-25 12:00-14:00 (no overlap), 40 seats)
    Expected Output: true, Trip T200 seats = 36
---
Test Case 5: "Booking denied when customer has overlapping booking"
    Input: Check booking eligibility for Trip T300
    Setup:
    Customer C10 has existing booking(2023-12-23 12:00, 2 seats)  for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
    Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00 (1-hour overlap), 40 seats)
    Expected Output: false, Trip T300 seats = 40
```

***

### CR 2: "Calculate Discounted Trip Price"

```
Computational Requirement: "Calculate Discounted Trip Price": Compute the final price for a booking after applying reward discounts (the discount is always 20%off). Discount applies only if the booking is made by customer with membership and the booking time is ≥24 hours before departure. Return the final price if conditions are met, otherwise, return original price.

+ Test Case 1: "20% discount applied for early booking" 
    Input: Calculate price for Trip T789
    Setup:1. Create Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
          2. Customer C3 has membership with 20% DISCOUNTS award
          3. Booking made at 2023-12-24 07:00 (25 hours before)
    Expected Output: 80.0
---
+ Test Case 2: "Discount denied for late booking" 
    Input: Calculate price for Trip T999
    Setup:1. Create Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
          2. Customer C4 has membership with 20% DISCOUNTS award
          3. Booking made at 2023-12-25 10:30 (1.5 hours before)
    Expected Output: 200.0
---
+ Test Case 3: "Exact 24-hour boundary for discount"
    Input: Calculate price for Trip T800
    Setup:
    Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
    Customer has membership with 20% DISCOUNTS award
    Booking made at 2023-12-24 08:00 (exactly 24 hours before)
    Expected Output: 80.0
    Tests the boundary condition
---
+ Test Case 4: "No discount without membership"
    Input: Calculate price for Trip T900
    Setup:
    Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
    Customer has no membership
    Booking made 48 hours before (2023-12-24 12:00)
    Expected Output: 200.0
    Verifies membership requirement
---
+ Test Case 5: "Discount applies only to eligible membership type"
    Input: Calculate price for Trip T950
    Setup:
    Trip T950 (price: 150.0)
    Customer has membership with CASHBACK only (no DISCOUNTS)
    Booking made 30 hours before
    Expected Output: 150.0
    Tests award type validation
```


***


### CR 3:  "Check Stop Overlap for Indirect Trips"

```
Computational Requirement: "Check Stop Overlap for Indirect Trips": Determine if two indirect trips (have ≥1 stop) by the same driver share any common stops. Return true if any stop station is shared.

+ Test Case 1: "Shared stop in indirect trips" 
    Input: Check stop overlap between Trip A1 and A2
    Setup:1. Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
          2. Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
    Expected Output: true, common stop: "CityY".
---
+ Test Case 2: "No common stops in indirect trips" 
    Input: Check stop overlap between Trip B1 and B2
    Setup:1. Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
          2. Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
    Expected Output: false, no common stop.
---
+ Test Case 3: "Empty stop lists comparison"
    Input: Check stop overlap between Trip C1 and C2
    Setup:
    Trip C1 has no stops
    Trip C2 has no stops
    Expected Output: false. Tests empty stop handling
---
+ Test Case 4: "Multiple shared stops detection"
    Input: Check stop overlap between Trip D1 and D2
    Setup:
    Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
    Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
    Expected Output: true, common stop: Stop("B"), Stop("C").
---
+ Test Case 5: "Case-sensitive stop comparison"
    Input: Check stop overlap between Trip E1 and E2
    Setup:
    Trip E1 stops: [Stop("Boston")]
    Trip E2 stops: [Stop("boston")]
    Expected Output: false, no common stop.
```

***


### CR 4:  "Compute Monthly Reward Points"
```
Computational Requirement: "Compute Monthly Reward Points": Calculate total reward points earned by a customer in the given current month. Preconditions: The customer must have a membership with a points reward. Calculation rules: Only calculate the bookings whose booking dates are within the target month. 5 points per seat per booking. Return totalPoints = sum(seats * 5) for eligible bookings.


+ Test Case 1: "Points calculation with multiple bookings" 
    Input: Compute reward points for Customer C5 (current Month: 2023-12)
    Setup:1. Customer C5 has membership with POINTS award
          2. C5 create Booking1 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
          3. C5 create Booking2 (seats:3) for trip (price: 100.0, departure: 2023-12-26 12:00)
          
    Expected Output: 25. (2+3)*5=25
---
+ Test Case 2: "Zero points with expired bookings" 
    Input: Compute reward points for Customer C6 (current Month: 2023-12)
    Setup:1. Customer C6 has membership with POINTS award
          2. C6 create Booking3 (seats:4) for trip (price: 100.0, departure: 2024-12-26 12:00)
    Expected Output: 0
---
+ Test Case 3: "Partial month inclusion"
    Input: Compute reward points for Customer C7 (current Month: 2023-12) 
    Setup:
    Customer C7 has membership with POINTS award
    C7 create Booking1: 2023-11-30 10:00 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
    C7 create Booking2: 2023-12-01 10:00 (seats:3) for trip (price: 200.0, departure: 2023-12-25 12:00)
    Expected Output: 15. 3*5=15.
---
+ Test Case 4: "Multiple seats edge case"
    Input: Compute reward points for Customer C8 (current Month: 2023-12)
    Setup:
    Customer C8 has membership with POINTS award
    C8 create Booking: 2023-12-10 10:00 (seats:2) for trip (price: 200.0, departure: 2024-03-25 12:00)
    Expected Output: 10. 2*5=10
---
+ Test Case 5: "Large seat quantity"
    Input: Compute reward points for Customer C8, C9 (current Month: 2024-01)
    Setup:
    Customer C8 has membership with POINTS award
    C8 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 150.0, departure: 2024-05-25 12:00)
    C8 create Booking: 2024-01-15 10:00 (seats:50) for trip (price: 200.0, departure: 2024-06-25 12:00)
    C9 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 200.0, departure: 2024-07-25 12:00)
    Expected Output: C8 reward points: 500. C9 reward points: 250.
```

***
### CR 5:  "Validate Trip Posting Feasibility"

```
Computational Requirement: "Validate Trip Posting Feasibility": Preconditions: The driver must exist and be valid; The new trip time must be valid (departure < arrival). Check if there are time conflicts between the new trip and the existed trips (The time period of the new trip intersects with that of any existing trip. Completely identical time periods are considered a conflict. Adjacent boundaries (e.g., A ends = B starts) are not considered a conflict.) Return true if no time conflict.

+ Test Case 1: "Valid trip posting with time gap" 
    Input: Validate new trip posting for Driver D5
    Setup:1. Driver D5 has existing trip at 2023-12-25 09:00-11:00
          2. New trip proposed by D5: 2023-12-25 13:00-15:00
    Expected Output: true
---
+ Test Case 2: "Posting denied due to time conflict" 
    Input: Validate new trip posting for Driver D6
    Setup:1. Driver D6 has existing trip at 2023-12-25 14:00-16:00
          2. New trip proposed by D6: 2023-12-25 14:30-17:30
    Expected Output: false
---
+ Test Case 3: "Back-to-back trips allowed"
    Input: Validate new trip ending at existing trip's start for Driver D7
    Setup:
    Driver D7 has existing trip at 2023-12-25 09:00-11:00
    New trip proposed by D7: 2023-12-25 11:00-13:00
    Expected Output: true
    Tests exact boundary handling
---
+ Test Case 4: "Complete time enclosure rejection"
    Input: Validate new trip within existing trip for Driver D8
    Setup:
    Driver D8 has existing trip at Existing trip: 10:00-16:00
   New trip proposed by D8: 12:00-14:00
    Expected Output: false
    Tests complete overlap
---
+ Test Case 5: "Multiple existing trip comparison"
    Input: Validate new trip against 3 existing trips for Driver D9
    Setup:
    Driver D9 has Existing trips at 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
    New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
    Expected Output: false
    Tests multiple conflict detection
```
***
