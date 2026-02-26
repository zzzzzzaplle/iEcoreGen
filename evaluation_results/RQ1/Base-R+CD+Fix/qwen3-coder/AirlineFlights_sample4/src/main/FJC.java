import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Represents an airline that manages flights.
 */
 class Airline {
    private List<Flight> flights;

    public Airline() {
        this.flights = new ArrayList<>();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void addFlight(Flight f) {
        flights.add(f);
    }

    public void removeFlight(Flight f) {
        flights.remove(f);
    }

    /**
     * Publishes a flight for booking after validating various conditions.
     *
     * @param f   The flight to be published
     * @param now The current date and time
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        // Check if flight is already published (not open for booking)
        if (!f.isOpenForBooking()) {
            return false;
        }

        // Validate departure and arrival timestamps format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String departureStr = sdf.format(f.getDepartureTime());
            String arrivalStr = sdf.format(f.getArrivalTime());
            Date parsedDeparture = sdf.parse(departureStr);
            Date parsedArrival = sdf.parse(arrivalStr);

            if (!parsedDeparture.equals(f.getDepartureTime()) || !parsedArrival.equals(f.getArrivalTime())) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        // Check temporal consistency: currentTime < departureTime < arrivalTime
        if (!(now.before(f.getDepartureTime()) && f.getDepartureTime().before(f.getArrivalTime()))) {
            return false;
        }

        // Check route integrity: departureAirport ≠ arrivalAirport
        if (f.getDepartureAirport().equals(f.getArrivalAirport())) {
            return false;
        }

        // All validations passed, flight is now published and open for booking
        return true;
    }

    /**
     * Closes an open flight and cancels all confirmed reservations.
     *
     * @param flightId The ID of the flight to close
     * @param now      The current date and time
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        for (Flight flight : flights) {
            if (flight.getId().equals(flightId)) {
                // Verify that the flight is currently open
                if (!flight.isOpenForBooking()) {
                    return false;
                }

                // Verify that the flight has not yet departed
                if (now.after(flight.getDepartureTime())) {
                    return false;
                }

                // Change status to closed
                flight.setOpenForBooking(false);

                // Cancel every confirmed reservation
                for (Reservation reservation : flight.getReservations()) {
                    if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                        reservation.setStatus(ReservationStatus.CANCELED);
                    }
                }

                return true;
            }
        }
        return false;
    }

    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getDepartureAirport().getId().equals(origin) &&
                flight.getArrivalAirport().getId().equals(dest) &&
                isSameDay(flight.getDepartureTime(), date)) {
                result.add(flight);
            }
        }
        return result;
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}

/**
 * Represents a flight with departure and arrival information.
 */
class Flight {
    private String id;
    private boolean openForBooking;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private List<Stopover> stopovers;
    private List<Reservation> reservations;

    public Flight() {
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.openForBooking = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOpenForBooking() {
        return openForBooking;
    }

    public void setOpenForBooking(boolean openForBooking) {
        this.openForBooking = openForBooking;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public List<Stopover> getStopovers() {
        return stopovers;
    }

    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds a stopover to the flight if it fits within the schedule.
     *
     * @param stop The stopover to add
     * @param now  The current date and time
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        // Check that the flight has not yet departed
        if (now.after(departureTime)) {
            return false;
        }

        // Check that the airport serves valid cities
        if (stop.getAirport().getServesForCities().isEmpty()) {
            return false;
        }

        // Check that stopover times fit within the overall flight schedule
        if (stop.getArrivalTime().before(departureTime) || stop.getDepartureTime().after(arrivalTime)) {
            return false;
        }

        // Check that arrival time is before departure time at the stopover
        if (!stop.getArrivalTime().before(stop.getDepartureTime())) {
            return false;
        }

        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     *
     * @param stop The stopover to remove
     * @param now  The current date and time
     * @return true if the stopover is successfully removed, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        // Check that the flight has not yet departed
        if (now.after(departureTime)) {
            return false;
        }

        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     *
     * @return A list of confirmed reservations
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmedReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                confirmedReservations.add(reservation);
            }
        }
        return confirmedReservations;
    }
}

/**
 * Represents a stopover during a flight.
 */
class Stopover {
    private Date arrivalTime;
    private Date departureTime;
    private Airport airport;

    public Stopover() {}

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}

/**
 * Represents an airport that serves one or more cities.
 */
class Airport {
    private String id;
    private List<City> servesForCities;

    public Airport() {
        this.servesForCities = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<City> getServesForCities() {
        return servesForCities;
    }

    public void setServesForCities(List<City> servesForCities) {
        this.servesForCities = servesForCities;
    }

    public void addCity(City c) {
        servesForCities.add(c);
    }
}

/**
 * Represents a city.
 */
class City {
    public City() {}
}

/**
 * Represents a customer who makes bookings.
 */
class Customer {
    private List<Booking> bookings;

    public Customer() {
        this.bookings = new ArrayList<>();
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Creates a booking for passengers on a flight.
     *
     * @param f                     The flight to book
     * @param now                   The current date and time
     * @param listOfPassengerNames  The list of passenger names
     * @return true if the booking is successfully created, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        // Check that the flight is open for booking
        if (!f.isOpenForBooking()) {
            return false;
        }

        // Check that the current time is before the flight departure time
        if (!now.before(f.getDepartureTime())) {
            return false;
        }

        // Check for duplicate passengers
        Set<String> uniquePassengers = new HashSet<>(listOfPassengerNames);
        if (uniquePassengers.size() != listOfPassengerNames.size()) {
            return false;
        }

        // Create a new booking
        Booking booking = new Booking();
        booking.setCustomer(this);

        // Create reservations for each passenger
        for (String passengerName : listOfPassengerNames) {
            booking.createReservation(f, passengerName, now);
        }

        bookings.add(booking);
        return true;
    }

    /**
     * Confirms an existing reservation in a booking.
     *
     * @param reservationID The ID of the reservation to confirm
     * @param now           The current date and time
     * @return true if the reservation is successfully confirmed, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    // Check that the flight has not yet departed and is still open for booking
                    if (now.after(reservation.getFlight().getDepartureTime()) || 
                        !reservation.getFlight().isOpenForBooking()) {
                        return false;
                    }

                    reservation.setStatus(ReservationStatus.CONFIRMED);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cancels an existing reservation in a booking.
     *
     * @param reservationID The ID of the reservation to cancel
     * @param now           The current date and time
     * @return true if the reservation is successfully canceled, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    // Check that the flight has not yet departed and is still open for booking
                    if (now.after(reservation.getFlight().getDepartureTime()) || 
                        !reservation.getFlight().isOpenForBooking()) {
                        return false;
                    }

                    reservation.setStatus(ReservationStatus.CANCELED);
                    return true;
                }
            }
        }
        return false;
    }
}

/**
 * Represents a passenger.
 */
class Passenger {
    private String name;

    public Passenger() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a booking made by a customer.
 */
class Booking {
    private Customer customer;
    private List<Reservation> reservations;

    public Booking() {
        this.reservations = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Creates a reservation for a passenger on a flight.
     *
     * @param f         The flight for the reservation
     * @param passengerName The name of the passenger
     * @param now       The current date and time
     * @return true if the reservation is successfully created, false otherwise
     */
    public boolean createReservation(Flight f, String passengerName, Date now) {
        // Check that the flight is open for booking
        if (!f.isOpenForBooking()) {
            return false;
        }

        // Check that the current time is before the flight departure time
        if (!now.before(f.getDepartureTime())) {
            return false;
        }

        // Create passenger
        Passenger passenger = new Passenger();
        passenger.setName(passengerName);

        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString()); // Generate unique ID
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(f);

        reservations.add(reservation);
        
        // Also add to flight's reservations
        f.getReservations().add(reservation);
        
        return true;
    }
}

/**
 * Enumeration of possible reservation statuses.
 */
enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELED
}

/**
 * Represents a reservation for a passenger on a flight.
 */
class Reservation {
    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    public Reservation() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}