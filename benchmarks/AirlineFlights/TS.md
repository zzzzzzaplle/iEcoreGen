### CR 1 – Publish a flight for booking

```
Computational requirement: "Publish a flight for booking". An airline can publish a newly created flight to make it available for customer bookings. The system first confirms that the flight has valid departure and arrival timestamps in yyyy-MM-dd HH:mm:ss format, then checks temporal consistency — currentTime < departureTime < arrivalTime — and route integrity — departureAirport ≠ arrivalAirport. A flight may be published only once, and its status must still be open for booking. The call returns true on success; otherwise, false.

+ Test Case 1: "Correct schedule and route"
    Input: Publish flight F100 for airline AL1.
    Setup:
    1. Create airline AL1.
    2. Create airports AP01 (serves CityA) and AP02 (serves CityB).
    3. Create flight F100: departureAirport = AP01, arrivalAirport = AP02, departureTime = 2025-01-10 10:00:00, arrivalTime = 2025-01-10 14:00:00.
    4. Current system time = 2024-12-01 09:00:00.
    Expected Output: True
---
+ Test Case 2: "Departure after arrival"
    Input: Publish flight F101 for airline AL2.
    Setup:
    1. Airline AL2; airports AP03 (CityC) and AP04 (CityD).
    2. Flight F101: AP03 ➜ AP04, departure 2025-02-05 20:00:00,
       arrival 2025-02-05 18:00:00.
    3. Current time = 2024-12-15 10:00:00.
    Expected Output: False
---
+ Test Case 3: "Same departure and arrival airport"
    Input: Publish flight F102 for airline AL3.
    Setup:
    1. Airline AL3; airport AP05 (CityE).
    2. Flight F102: AP05 ➜ AP05, departure 2025-03-01 08:00:00,
       arrival 2025-03-01 12:00:00.
    3. Current time = 2025-02-01 09:00:00.
    Expected Output: False
---
+ Test Case 4: "Departure time in the past"
    Input: Publish flight F103 for airline AL4.
    Setup:
    1. Airline AL4; airports AP06 (CityF) and AP07 (CityG).
    2. Flight F103: departure 2025-03-30 10:00:00, arrival 2025-03-30 12:00:00.
    3. Current time = 2025-04-01 09:00:00.
    Expected Output: False
---
+ Test Case 5: "Second publish attempt"
    Input: Publish flight F104 again for airline AL5.
    Setup:
    1. Airline AL5; airports AP08 (CityH) and AP09 (CityI).
    2. Flight F104 already published successfully with
       departure 2025-05-05 07:00:00, arrival 2025-05-05 10:00:00.
    3. Current time = 2025-04-01 10:00:00.
    Expected Output: False
```
***

### CR 2 – Create a booking for passengers on a flight
```
Computational requirement: "Create a booking for passengers on a flight". A customer selects a specific open flight and supplies a list of passengers names. The system checks that there are no duplicate passengers on the flight and the current time is before the flight departure time. It then generates a booking for the customer and creates a reservation with a unique ID for each passenger in that booking. Each reservation is initially placed in “pending” status until the customer confirms it. The call returns true on success; otherwise, false.

+ Test Case 1: "Two new passengers succeed"
    Input: Request booking on flight F300 for passenger list P1:"Peter", P2:"Beck" by customer CUA, current time = 2025-10-01 09:00:00
    Setup:
    1. Create airline AL11.
    2. Create airports AP100 (departure) and AP101 (arrival).
    3. Create flight F300 under AL11  
       - departureAirport = AP100, arrivalAirport = AP101  
       - departureTime = 2025-10-05 08:00:00, arrivalTime = 2025-10-05 12:00:00  
       - it is open for booking.
    4. Instantiate customer CUA.
    5. Instantiate passengers P1:"Peter", P2:"Beck".
    Expected Output: True. There are two reservations for passengers P1 and P2.
---
+ Test Case 2: "Duplicate passenger in same request"
    Input: Request booking on flight F301 for passenger P3: "Alice" P3: "Alice" by customer CUB, current time = 2025-10-01 09:00:00
    Setup:
    1. Create airline AL12.
    2. Create airports AP102 (dep) and AP103 (arr).
    3. Create flight F301 under AL12:
        - departureAirport = AP102, arrivalAirport = AP103  
        - departureTime = 2025-10-05 08:00:00, arrivalTime = 2025-10-05 10:00:00  
        - it is open for booking.
    4. Customer CUB.
    5. Passenger P3 "Alice" 
    Expected Output: False. (Duplicate passenger)
---
+ Test Case 3: "Passenger already booked earlier"
    Input: Request booking on flight F302 for passenger "Jucy" by customer CUC, current time = 2025-10-01 09:00:00
    Setup:
    1. Create airline AL13 with airports AP104, AP105.
    2. Create flight F302 :
        - departureAirport = AP104, arrivalAirport = AP105  
        - departureTime = 2025-10-05 18:00:00, arrivalTime = 2025-10-06 02:00:00  
        - it is open for booking.
    3. Passenger P4 "Jucy".
    4. Customer CUC.
    5. Customer CUC : Pre-existing booking BK-OLD containing reservation R302-A for passenger P4 "Jucy" on F302 (status = PENDING).
    Expected Output: False
---
+ Test Case 4: "Flight closed blocks booking"
    Input: Request booking on flight F303 for passenger list "Ruby" by customer CUD, current time = 2025-10-01 09:00:00
    Setup:
    1. Create airline AL14 with airports AP106, AP107.
    2. Create flight F303 under AL14:
        - departureAirport = AP106, arrivalAirport = AP107
        - departureTime = 2025-10-05 18:00:00, arrivalTime = 2025-10-06 02:00:00  
        - it is closed for booking.
    3. Customer CUD.
    4. Passenger P5 "Ruby".
    Expected Output: False
---
+ Test Case 5: "The time is after the departure time"
    Input: Request booking on flight F303 for passenger list "Ruby" by customer CUD, current time = 2025-10-06 09:00:00
    Setup:
    1. Create airline AL14 with airports AP106, AP107.
    2. Create flight F303 under AL14:
        - departureAirport = AP104, arrivalAirport = AP105  
        - departureTime = 2025-10-05 18:00:00, arrivalTime = 2025-10-06 02:00:00  
        - it is open for booking.
    3. Customer CUD.
    4. Passenger P5 "Ruby".
    Expected Output: False
```


***
### CR 3 – Confirm or cancel an existing reservation

```
Computational requirement: "Confirm or cancel an existing reservation in a booking". The system checks that the flight has not yet departed and is still open for booking. The customer can then confirm or cancel a reservation by providing its reservation ID. The call returns true on success; otherwise, false.

+ Test Case 1: "Confirm pending reservation"
    Input: CU16 confirm reservation R401.
    Setup:
    1. Airline AL16.
    2. Airports AP160 (CityAA) and AP161 (CityAB).
    3. Flight F401  
       · departureAirport = AP160 arrivalAirport = AP161  
       · departureTime = 2025-12-10 11:00:00 arrivalTime = 2025-12-10 15:00:00  
       · openForBooking = True.
    4. Customer CU16, passenger P9.
    5. Booking BK401 contains reservation R401 (status =PENDING) for P9 on F401.
    6. Current time = 2025-11-01 09:00:00.
    Expected Output: True
---
+ Test Case 2: "Cancel confirmed reservation"
    Input: CU17 cancel reservation R402.
    Setup:
    1. Airline AL17.
    2. Airports AP170 (CityAC) and AP171 (CityAD).
    3. Flight F402  
       · departureTime = 2025-12-15 15:00:00 openForBooking = True.
    4. Customer CU17, passenger P10.
    5. Booking BK402 contains reservation R402 (status =CONFIRMED) for P10 on F402.
    6. Current time = 2025-12-01 12:00:00.
    Expected Output: True
---
+ Test Case 3: "Flight departed blocks confirmation"
    Input: Confirm reservation R403.
    Setup:
    1. Airline AL18.
    2. Airports AP180 (CityAE) and AP181 (CityAF).
    3. Flight F403  
       · departureTime = 2025-01-05 06:00:00 openForBooking = True.
    4. Reservation R403 status = PENDING (passenger P11).
    5. Current time = 2025-01-05 07:00:00 (flight already left).
    Expected Output: False
---
+ Test Case 4: "Closed flight blocks cancellation"
    Input: Cancel reservation R404.
    Setup:
    1. Airline AL19.
    2. Airports AP190 (CityAG) and AP191 (CityAH).
    3. Flight F404  
       · departureTime = 2025-02-01 09:00:00 openForBooking = False.
    4. Reservation R404 status = CONFIRMED (passenger P12).
    5. Current time = 2025-01-20 08:00:00.
    Expected Output: False
---
+ Test Case 5: "Unknown reservation identifier"
    Input: Customer CU20 confirm reservation R406.
    Setup:
    1. Airline AL20.
    2. Airports AP200 (CityAI) and AP201 (CityAJ).
    3. Flight F405  
       · departureTime = 2025-03-10 10:00:00 openForBooking = True.
    4. Customer CU20 with one existing reservation R405 (status =PENDING) for passenger P13.  
    5. Customer CU21 with one existing reservation R406 (status =PENDING) for passenger P14.  
    5. Current time = 2025-02-15 09:00:00.
    Expected Output: False
```


***
### CR 4 – Close an opening flight

```
Computational requirement: "Close an open flight". An airline can close an existing flight that has not yet departed. The system verifies that the flight is currently open; if so, it changes the status to closed and cancels every confirmed reservation. It returns true if on success; otherwise, false.

+ Test Case 1: "No reservations to cancel"
    Input: Close flight F200 for airline AL6.
    Setup:
    1. Airline AL6; airports AP10 (CityJ) and AP11 (CityK).
    2. Flight F200: depart 2025-06-20 09:00:00, arrive 2025-06-20 13:00:00, 0 reservations. The flight is open for booking for customers.
    3. Current time = 2025-05-01 08:00:00.
    Expected Output: 
    - True. 
    - No canceled reservation.
---
+ Test Case 2: "Three confirmed reservations canceled"
    Input: Close flight F201 for airline AL7.
    Setup:
    1. Airline AL7; airports AP12 (CityL) and AP13 (CityM).
    2. Flight F201: depart 2025-07-15 14:00:00, arrive 2025-07-15 18:00:00. The flight is open for booking for customers.
    3. Customer make a booking with three reservations R201-1, R201-2, R201-3. Cstomer has confirmed these resercation.
    4. Current time = 2025-06-10 12:00:00.
    Expected Output: 
    - True. 
    - The three reservation are canceled.
---
+ Test Case 3: "Flight already closed"
    Input: Close flight F202 for airline AL8.
    Setup:
    1. Airline AL8; flight F202 openForBooking = False,
       depart 2025-08-10 11:00:00, arrive 2025-08-10 13:30:00.
    2. Current time = 2025-07-01 09:00:00.
    Expected Output: 
    - False
---
+ Test Case 4: "Close on departure day, after departure time"
    Input: Close flight F203 for airline AL9.
    Setup:
    1. Airline AL9; flight F203 openForBooking = True,
       depart 2025-09-10 09:00:00, arrive 2025-09-10 15:30:00.
    2. Two confirmed reservations R203-1, R203-2.
    3. Current time = 2025-09-10 09:10:00.
    Expected Output: 
    - False
---
+ Test Case 5: "Attempt to close after departure"
    Input: Close flight F204 for airline AL10.
    Setup:
    1. Airline AL10; flight F204 openForBooking = True,
       depart 2025-04-01 22:00:00, arrive 2025-04-1 01:30:00.
    2. Current time = 2025-04-01 22:05:00 (flight already left).
    Expected Output: 
    - False
```

***

### CR 5 – Retrieve all confirmed reservations for a specific flight
```
Computational requirement: "Retrieve all confirmed reservations for a specific flight". Given a flight ID, the system fetches every confirmed reservation on that flight, provided the flight is currently open for booking. It returns a list of reservations, or empty list if none exist.

+ Test Case 1: "Flight with three confirmations"
    Input: Retrieve confirmed list for flight F501.
    Setup:
    1. Airline AL21; flight F501 openForBooking = True.
    2. Reservations R501-1, R501-2, R501-3 status = CONFIRMED.
    Expected Output: R501-1, R501-2, R501-3
---
+ Test Case 2: "No confirmed reservations"
    Input: Retrieve confirmed list for flight F502.
    Setup:
    1. Airline AL22; flight F502 openForBooking = True.
    2. Two reservations status = PENDING.
    Expected Output: []
---
+ Test Case 3: "Flight closed returns zero"
    Input: Retrieve confirmed list for flight F503.
    Setup:
    1. Airline AL23; flight F503 openForBooking = False.
    2. One reservation status = CONFIRMED.
    Expected Output: []
---
+ Test Case 4: "Unknown flight id"
    Input: Retrieve confirmed list for flight FX999.
    Setup:
    1. Airline AL24 holds flights F504, F505 only.
    Expected Output: []
---
+ Test Case 5: "Mixed reservation statuses"
    Input: Retrieve confirmed list for flight F504.
    Setup:
    1. Airline AL25; flight F504 openForBooking = True.
    2. Reservations: R504-A, R504-B status = CONFIRMED;
       R504-C status = CANCELED; R504-D status = PENDING.
    Expected Output: R504-A, R504-B
```

***
### CR 6 – Add or delete a stopover on a flight
```
Computational requirement: "Add or delete a stopover on a flight". Before the flight departs, the system can add or delete stopovers on a flight, specifying each stop’s airport along with its arrival and departure times. The system ensures that the stopover times fit within the overall flight schedule and that the airports serve valid cities. The call returns true on success; otherwise, false.

+ Test Case 1: "Add first stopover inside journey window"
    Input: Add stopover AP30 (2025-04-20 12:00:00 → 2025-04-20 12:40:00) to flight F601.
    Setup:
    1. Airline AL26.
    2. Airports:  
       · AP28 (CityN) · AP29 (CityO) · AP30 (CityP).
    3. Flight F601  
       · AP28 ➜ AP29  
       · depart 2025-04-20 10:00:00 arrive 2025-04-20 15:00:00  
       · openForBooking = True, 0 stopovers.
    4. Current time = 2025-04-19 09:00:00.
    Expected Output: true
---
+ Test Case 2: "Stopover time outside window"
    Input: Add stopover AP31 (2025-05-10 16:00:00 → 17:00:00) to flight F602.
    Setup:
    1. Airline AL27.
    2. Airports AP32 (CityQ), AP33 (CityR), AP31 (CityS).
    3. Flight F602  
       · depart 2025-05-10 09:00:00 arrive 2025-05-10 14:00:00  
       · openForBooking = True, 0 stopovers.
    4. Current time = 2025-05-09 11:00:00.
    Expected Output: False
---
+ Test Case 3: "Delete existing stopover"
    Input: Delete stopover AP34 from flight F603.
    Setup:
    1. Airline AL28.
    2. Airports AP34 (CityT), AP35 (CityU), AP36 (CityV).
    3. Flight F603  
       · depart 2025-06-15 11:00:00 arrive 2025-06-15 18:00:00  
       · openForBooking = True, one stopover  
         AP34 (2025-06-15 13:00:00 → 2025-06-15 13:45:00).
    4. Current time = 2025-06-14 10:00:00.
    Expected Output: True
---
+ Test Case 4: "Flight closed prevents modification"
    Input: Add stopover AP37 (2025-07-20 13:30:00 → 14:00:00) to flight F604.
    Setup:
    1. Airline AL29.
    2. Airports AP37 (CityW), AP38 (CityX), AP39 (CityY).
    3. Flight F604  
       · depart 2025-07-20 12:00:00 arrive 2025-07-20 17:00:00  
       · openForBooking = False.
    4. Current time = 2025-07-10 09:00:00.
    Expected Output: False
---
+ Test Case 5: "Attempt removal after departure"
    Input: Delete stopover AP42 from flight F606.
    Setup:
    1. Airline AL31.
    2. Airports AP42 (CityBB), AP43 (CityCC), AP44 (CityDD).
    3. Flight F606  
       · depart 2025-12-09 18:00:00 arrive 2025-12-10 00:00:00  
       · openForBooking = True, one stopover  
         AP42 (2025-12-09 20:00:00 → 2025-12-09 20:45:00).
    4. Current time = 2025-12-09 20:50:00 (after stopover’s exit, flight mid-air).
    Expected Output: False

```

***
