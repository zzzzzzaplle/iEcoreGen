import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents an airline that manages a collection of flights.
 */
 class Airline {

    private List<Flight> flights = new ArrayList<>();

    /** No‑arg constructor */
    public Airline() {
    }

    /** Get the list of flights (read‑only) */
    public List<Flight> getFlights() {
        return Collections.unmodifiableList(flights);
    }

    /** Set the list of flights (used mainly for testing) */
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    /**
     * Adds a flight to the airline portfolio.
     *
     * @param f the flight to add
     */
    public void addFlight(Flight f) {
        if (f != null && !flights.contains(f)) {
            flights.add(f);
        }
    }

    /**
     * Removes a flight from the airline portfolio.
     *
     * @param f the flight to remove
     */
    public void removeFlight(Flight f) {
        flights.remove(f);
    }

    /**
     * Publishes a newly created flight, making it available for customer bookings.
     * <p>
     * The method validates:
     * <ul>
     *   <li>departure and arrival timestamps are in the pattern {@code yyyy-MM-dd HH:mm:ss}</li>
     *   <li>current time {@code now} is before departure, and departure is before arrival</li>
     *   <li>departure and arrival airports are different</li>
     *   <li>the flight has not been published before</li>
     * </ul>
     *
     * @param f   the flight to publish
     * @param now the current time
     * @return {@code true} if the flight was successfully published; {@code false} otherwise
     */
    public boolean publishFlight(Flight f, LocalDateTime now) {
        if (f == null || now == null) {
            return false;
        }
        // Ensure timestamps are present
        if (f.getDepartureTime() == null || f.getArrivalTime() == null) {
            return false;
        }

        // Temporal consistency
        if (!now.isBefore(f.getDepartureTime())) {
            return false;
        }
        if (!f.getDepartureTime().isBefore(f.getArrivalTime())) {
            return false;
        }

        // Route integrity
        if (f.getDepartureAirport() == null || f.getArrivalAirport() == null) {
            return false;
        }
        if (Objects.equals(f.getDepartureAirport().getId(),
                f.getArrivalAirport().getId())) {
            return false;
        }

        // Flight must not already be open for booking
        if (f.isOpenForBooking()) {
            return false;
        }

        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an open flight that has not yet departed. All confirmed reservations are cancelled.
     *
     * @param flightId the unique identifier of the flight to close
     * @param now      the current time
     * @return {@code true} if the flight was successfully closed; {@code false} otherwise
     */
    public boolean closeFlight(String flightId, LocalDateTime now) {
        Flight f = findFlightById(flightId);
        if (f == null) {
            return false;
        }
        if (!f.isOpenForBooking()) {
            return false; // already closed
        }
        if (!now.isBefore(f.getDepartureTime())) {
            return false; // flight already departed
        }

        // Cancel every confirmed reservation
        for (Reservation r : f.getReservations()) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELED);
            }
        }
        f.setOpenForBooking(false);
        return true;
    }

    /**
     * Searches for flights that depart from {@code origin} airport, arrive at {@code dest} airport,
     * and have a departure date equal to {@code date} (date part only, time ignored).
     *
     * @param origin the IATA/id of the origin airport
     * @param dest   the IATA/id of the destination airport
     * @param date   the desired departure date (time part ignored)
     * @return list of matching flights (may be empty)
     */
    public List<Flight> searchFlights(String origin, String dest, LocalDateTime date) {
        List<Flight> result = new ArrayList<>();
        if (origin == null || dest == null || date == null) {
            return result;
        }
        for (Flight f : flights) {
            if (!f.isOpenForBooking()) {
                continue;
            }
            if (f.getDepartureAirport() == null || f.getArrivalAirport() == null) {
                continue;
            }
            if (origin.equals(f.getDepartureAirport().getId())
                    && dest.equals(f.getArrivalAirport().getId())
                    && f.getDepartureTime().toLocalDate().equals(date.toLocalDate())) {
                result.add(f);
            }
        }
        return result;
    }

    /** Helper method to locate a flight by its identifier */
    private Flight findFlightById(String flightId) {
        for (Flight f : flights) {
            if (flightId.equals(f.getId())) {
                return f;
            }
        }
        return null;
    }
}

/**
 * Represents a flight with departure/arrival information, stopovers and reservations.
 */
class Flight {

    private String id;
    private boolean openForBooking = false;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private List<Stopover> stopovers = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    /** No‑arg constructor */
    public Flight() {
        this.id = UUID.randomUUID().toString();
    }

    /** Constructor used in tests to set a predefined id */
    public Flight(String id) {
        this.id = (id != null) ? id : UUID.randomUUID().toString();
    }

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public boolean isOpenForBooking() {
        return openForBooking;
    }

    public void setOpenForBooking(boolean openForBooking) {
        this.openForBooking = openForBooking;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
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
        return Collections.unmodifiableList(stopovers);
    }

    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    // -------------------------------------------------------------------------
    // Business methods
    // -------------------------------------------------------------------------

    /**
     * Adds a stopover to the flight schedule.
     *
     * @param stop the stopover to add
     * @param now  the current time
     * @return {@code true} if the stopover was added; {@code false} otherwise
     */
    public boolean addStopover(Stopover stop, LocalDateTime now) {
        if (stop == null || now == null) {
            return false;
        }
        // Must be before flight departure
        if (!now.isBefore(this.departureTime)) {
            return false;
        }
        // Stopover times must be within overall flight window
        if (stop.getArrivalTime().isBefore(this.departureTime)
                || stop.getDepartureTime().isAfter(this.arrivalTime)
                || !stop.getArrivalTime().isBefore(stop.getDepartureTime())) {
            return false;
        }
        // Airport must serve at least one city (simple validation)
        if (stop.getAirport() == null || stop.getAirport().getServesForCities().isEmpty()) {
            return false;
        }
        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight schedule.
     *
     * @param stop the stopover to remove
     * @param now  the current time
     * @return {@code true} if the stopover was removed; {@code false} otherwise
     */
    public boolean removeStopover(Stopover stop, LocalDateTime now) {
        if (stop == null || now == null) {
            return false;
        }
        // Can only remove before departure
        if (!now.isBefore(this.departureTime)) {
            return false;
        }
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     * The method returns an empty list if the flight is closed for booking.
     *
     * @return list of confirmed reservations (may be empty)
     */
    public List<Reservation> getConfirmedReservations() {
        if (!openForBooking) {
            return Collections.emptyList();
        }
        List<Reservation> confirmed = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                confirmed.add(r);
            }
        }
        return confirmed;
    }

    /**
     * Adds a reservation to the flight (used internally by Booking).
     *
     * @param reservation the reservation to add
     */
    protected void addReservation(Reservation reservation) {
        if (reservation != null && !reservations.contains(reservation)) {
            reservations.add(reservation);
        }
    }
}

/**
 * Represents a stopover within a flight, containing an airport and arrival/departure times.
 */
class Stopover {

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Airport airport;

    /** No‑arg constructor */
    public Stopover() {
    }

    /** Full constructor for convenience */
    public Stopover(Airport airport, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.airport = airport;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    // Getters and setters

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
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
    private List<City> servesForCities = new ArrayList<>();

    /** No‑arg constructor */
    public Airport() {
        this.id = UUID.randomUUID().toString();
    }

    /** Constructor with explicit id */
    public Airport(String id) {
        this.id = (id != null) ? id : UUID.randomUUID().toString();
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public List<City> getServesForCities() {
        return Collections.unmodifiableList(servesForCities);
    }

    public void setServesForCities(List<City> cities) {
        this.servesForCities = cities;
    }

    /**
     * Associates a city with this airport.
     *
     * @param c the city to add
     */
    public void addCity(City c) {
        if (c != null && !servesForCities.contains(c)) {
            servesForCities.add(c);
        }
    }
}

/**
 * Simple placeholder for a city.
 */
class City {

    private String name;

    /** No‑arg constructor */
    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    // Getter / setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

/**
 * Represents a customer that can create bookings and manage reservations.
 */
class Customer {

    private List<Booking> bookings = new ArrayList<>();

    /** No‑arg constructor */
    public Customer() {
    }

    // Getters and setters

    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Creates a new booking for a flight with a list of passenger names.
     *
     * @param flight               the flight to be booked (must be open for booking)
     * @param now                  the current time
     * @param listOfPassengerNames list of passenger names (no duplicates)
     * @return {@code true} if the booking (and all reservations) were created successfully; {@code false} otherwise
     */
    public boolean addBooking(Flight flight, LocalDateTime now, List<String> listOfPassengerNames) {
        if (flight == null || now == null || listOfPassengerNames == null) {
            return false;
        }
        if (!flight.isOpenForBooking()) {
            return false;
        }
        if (!now.isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Check duplicate passenger names within the request
        List<String> distinct = new ArrayList<>();
        for (String name : listOfPassengerNames) {
            if (name == null || distinct.contains(name)) {
                return false; // duplicate or null name
            }
            distinct.add(name);
        }

        Booking booking = new Booking(this);
        for (String passengerName : distinct) {
            boolean ok = booking.createReservation(flight, passengerName, now);
            if (!ok) {
                return false; // abort if any reservation fails
            }
        }
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms a reservation belonging to any of this customer's bookings.
     *
     * @param reservationID the unique identifier of the reservation
     * @param now           the current time
     * @return {@code true} if the reservation was successfully confirmed; {@code false} otherwise
     */
    public boolean confirm(String reservationID, LocalDateTime now) {
        Reservation r = findReservationById(reservationID);
        if (r == null) {
            return false;
        }
        Flight f = r.getFlight();
        if (f == null || !f.isOpenForBooking() || !now.isBefore(f.getDepartureTime())) {
            return false;
        }
        if (r.getStatus() != ReservationStatus.PENDING) {
            return false;
        }
        r.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels a reservation belonging to any of this customer's bookings.
     *
     * @param reservationID the unique identifier of the reservation
     * @param now           the current time
     * @return {@code true} if the reservation was successfully cancelled; {@code false} otherwise
     */
    public boolean cancel(String reservationID, LocalDateTime now) {
        Reservation r = findReservationById(reservationID);
        if (r == null) {
            return false;
        }
        Flight f = r.getFlight();
        if (f == null || !f.isOpenForBooking() || !now.isBefore(f.getDepartureTime())) {
            return false;
        }
        if (r.getStatus() == ReservationStatus.CANCELED) {
            return false;
        }
        r.setStatus(ReservationStatus.CANCELED);
        return true;
    }

    /** Helper to locate a reservation across all bookings */
    private Reservation findReservationById(String reservationID) {
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (reservationID.equals(r.getId())) {
                    return r;
                }
            }
        }
        return null;
    }
}

/**
 * Represents a passenger (identified by name).
 */
class Passenger {

    private String name;

    /** No‑arg constructor */
    public Passenger() {
    }

    public Passenger(String name) {
        this.name = name;
    }

    // Getter / setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

/**
 * Represents a booking made by a customer. It contains a collection of reservations.
 */
class Booking {

    private Customer customer;
    private List<Reservation> reservations = new ArrayList<>();

    /** No‑arg constructor (used only by frameworks) */
    public Booking() {
    }

    /** Constructor linking the booking to its customer */
    public Booking(Customer customer) {
        this.customer = customer;
    }

    // Getters and setters

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Creates a reservation for a given flight and passenger name.
     * The reservation is added to the flight's reservation list.
     *
     * @param flight    the flight to reserve a seat on
     * @param passengerName the name of the passenger
     * @param now       the current time
     * @return {@code true} if the reservation was successfully created; {@code false} otherwise
     */
    public boolean createReservation(Flight flight, String passengerName, LocalDateTime now) {
        if (flight == null || passengerName == null || now == null) {
            return false;
        }
        if (!flight.isOpenForBooking() || !now.isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Ensure the passenger does not already have a reservation on this flight
        for (Reservation existing : flight.getReservations()) {
            if (existing.getPassenger().getName().equals(passengerName)) {
                return false; // duplicate passenger on the same flight
            }
        }

        Passenger passenger = new Passenger(passengerName);
        Reservation reservation = new Reservation(UUID.randomUUID().toString(),
                ReservationStatus.PENDING,
                passenger,
                flight);
        reservations.add(reservation);
        flight.addReservation(reservation);
        return true;
    }
}

/**
 * Enumeration for reservation status.
 */
enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELED
}

/**
 * Represents a reservation linking a passenger to a flight.
 */
class Reservation {

    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    /** No‑arg constructor */
    public Reservation() {
    }

    public Reservation(String id, ReservationStatus status, Passenger passenger, Flight flight) {
        this.id = id;
        this.status = status;
        this.passenger = passenger;
        this.flight = flight;
    }

    // Getters and setters

    public String getId() {
        return id;
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