// ==version1==
```
+ "Publish a flight for booking". An airline can publish a newly created flight to make it available for customer bookings. The system first confirms that the flight has valid departure and arrival timestamps in yyyy-MM-dd HH:mm:ss format, then checks temporal consistency — currentTime < departureTime < arrivalTime — and route integrity — departureAirport ≠ arrivalAirport. A flight may be published only once, and its status must still be open for booking. The call returns true on success; otherwise, false.

+ "Create a booking for passengers on a flight". A customer selects a specific open flight and supplies a list of passengers names. The system checks that there are no duplicate passengers on the flight and the current time is before the flight departure time. It then generates a booking for the customer and creates a reservation with a unique ID for each passenger in that booking. Each reservation is initially placed in “pending” status until the customer confirms it. The call returns true on success; otherwise, false.

+ "Confirm or cancel an existing reservation in a booking". The system checks that the flight has not yet departed and is still open for booking. The customer can then confirm or cancel a reservation by providing its reservation ID. The call returns true on success; otherwise, false.

+ "Close an open flight". An airline can close an existing flight that has not yet departed. The system verifies that the flight is currently open; if so, it changes the status to closed and cancels every confirmed reservation. It returns true on success; otherwise, false.

+ "Retrieve all confirmed reservations for a specific flight". Given a flight ID, the system fetches every confirmed reservation on that flight, provided the flight is currently open for booking. It returns a list of reservations, or empty list if none exist.

+ "Add or delete a stopover on a flight". Before the flight departs, the system can add or delete stopovers on a flight, specifying each stop’s airport along with its arrival and departure times. The system ensures that the stopover times fit within the overall flight schedule and that the airports serve valid cities. The call returns true on success; otherwise, false.
```
// ==end== 