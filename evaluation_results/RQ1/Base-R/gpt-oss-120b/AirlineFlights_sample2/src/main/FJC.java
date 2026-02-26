import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Represents a city served by an airport.
 */
class City {
    private String name;

    public City() {}

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
}

/**
 * Represents an airport that can serve several cities.
 */
class Airport {
    private String id;
    private String name;
    private List<City> cities = new ArrayList<>();

    public Airport() {}

    public Airport(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /** Unique identifier of the airport. */
    public String getId() {
        return id;
    }

    /** Sets the unique identifier of the airport. */
    public void setId(String id) {
        this.id = id;
    }

    /** Human‑readable name of the airport. */
    public String getName() {
        return name;
    }

    /** Sets the human‑readable name of the airport. */
    public void setName(String name) {
        this.name = name;
    }

    /** List of cities served by this airport. */
    public List<City> getCities() {
        return cities;
    }

    /** Sets the list of cities served by this airport. */
    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    /** Adds a city to the list of served cities. */
    public void addCity(City city) {
        this.cities.add(city);
    }
}

/**
 * Flight status enumeration.
 */
enum FlightStatus {
    OPEN, CLOSED
}

/**
 * Represents a stopover on a flight.
 */
class Stopover {
    private Airport airport;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public Stopover() {}

    public Stopover(Airport airport, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.airport = airport;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    /** Airport where the stopover occurs. */
    public Airport getAirport() {
        return airport;
    }

    /** Sets the airport for the stopover. */
    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    /** Arrival time at the stopover airport. */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /** Sets the arrival time at the stopover airport. */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /** Departure time from the stopover airport. */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /** Sets the departure time from the stopover airport. */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
}

/**
 * Represents a flight.
 */
class Flight {
    private String id;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightStatus status = FlightStatus.OPEN;
    private List<Stopover> stopovers = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public Flight() {}

    public Flight(String id, Airport departureAirport, Airport arrivalAirport,
                  LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    /** Unique identifier of the flight. */
    public String getId() {
        return id;
    }

    /** Sets the unique identifier of the flight. */
    public void setId(String id) {
        this.id = id;
    }

    /** Airport from which the flight departs. */
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    /** Sets the departure airport. */
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    /** Airport at which the flight arrives. */
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    /** Sets the arrival airport. */
    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    /** Scheduled departure timestamp. */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /** Sets the scheduled departure timestamp. */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /** Scheduled arrival timestamp. */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /** Sets the scheduled arrival timestamp. */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /** Current status of the flight (OPEN / CLOSED). */
    public FlightStatus getStatus() {
        return status;
    }

    /** Sets the current status of the flight. */
    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    /** List of stopovers for this flight. */
    public List<Stopover> getStopovers() {
        return stopovers;
    }

    /** Sets the list of stopovers. */
    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    /** Adds a stopover to the flight. */
    public void addStopover(Stopover stopover) {
        this.stopovers.add(stopover);
    }

    /** Removes a stopover that belongs to the specified airport. */
    public boolean removeStopoverByAirportId(String airportId) {
        return this.stopovers.removeIf(s -> s.getAirport().getId().equals(airportId));
    }

    /** List of all reservations (pending, confirmed, cancelled) made for this flight. */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /** Sets the reservations list. */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /** Adds a reservation to the flight. */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
}

/**
 * Reservation status enumeration.
 */
enum ReservationStatus {
    PENDING, CONFIRMED, CANCELLED
}

/**
 * Represents a reservation for a single passenger on a flight.
 */
class Reservation {
    private String id;
    private String passengerName;
    private ReservationStatus status = ReservationStatus.PENDING;
    private Flight flight;               // back‑reference (optional)

    public Reservation() {}

    public Reservation(String id, String passengerName, Flight flight) {
        this.id = id;
        this.passengerName = passengerName;
        this.flight = flight;
    }

    /** Unique identifier of the reservation. */
    public String getId() {
        return id;
    }

    /** Sets the unique identifier of the reservation. */
    public void setId(String id) {
        this.id = id;
    }

    /** Name of the passenger for this reservation. */
    public String getPassengerName() {
        return passengerName;
    }

    /** Sets the passenger name. */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /** Current status of the reservation. */
    public ReservationStatus getStatus() {
        return status;
    }

    /** Sets the current status of the reservation. */
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    /** Flight associated with this reservation. */
    public Flight getFlight() {
        return flight;
    }

    /** Sets the flight associated with this reservation. */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}

/**
 * Represents a booking made by a customer (a group of reservations for a single flight).
 */
class Booking {
    private String id;
    private String customerId;
    private String flightId;
    private List<Reservation> reservations = new ArrayList<>();

    public Booking() {}

    public Booking(String id, String customerId, String flightId) {
        this.id = id;
        this.customerId = customerId;
        this.flightId = flightId;
    }

    /** Unique identifier of the booking. */
    public String getId() {
        return id;
    }

    /** Sets the unique identifier of the booking. */
    public void setId(String id) {
        this.id = id;
    }

    /** Identifier of the customer who made the booking. */
    public String getCustomerId() {
        return customerId;
    }

    /** Sets the identifier of the customer. */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /** Identifier of the flight for which the booking was made. */
    public String getFlightId() {
        return flightId;
    }

    /** Sets the flight identifier. */
    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    /** List of reservations belonging to this booking. */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /** Sets the list of reservations. */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /** Adds a reservation to the booking. */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
}

/**
 * Represents a customer of the airline.
 */
class Customer {
    private String id;
    private String name;
    private List<Booking> bookings = new ArrayList<>();

    public Customer() {}

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /** Unique identifier of the customer. */
    public String getId() {
        return id;
    }

    /** Sets the unique identifier of the customer. */
    public void setId(String id) {
        this.id = id;
    }

    /** Full name of the customer. */
    public String getName() {
        return name;
    }

    /** Sets the full name of the customer. */
    public void setName(String name) {
        this.name = name;
    }

    /** Bookings made by the customer. */
    public List<Booking> getBookings() {
        return bookings;
    }

    /** Sets the bookings list. */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /** Adds a booking to the customer. */
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}

/**
 * Service class that holds the core business logic for flights, bookings and reservations.
 */
class AirlineService {

    /* ---------- In‑memory repositories ---------- */
    private final Map<String, Flight> flights = new HashMap<>();
    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, Reservation> reservations = new HashMap<>();

    /* ---------- ID generators ---------- */
    private final AtomicLong flightIdSeq = new AtomicLong(1);
    private final AtomicLong reservationIdSeq = new AtomicLong(1);
    private final AtomicLong bookingIdSeq = new AtomicLong(1);
    private final AtomicLong customerIdSeq = new AtomicLong(1);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /* ---------- Utility methods ---------- */

    /**
     * Generates a new unique flight identifier.
     *
     * @return a unique flight id as a string.
     */
    private String nextFlightId() {
        return "FL-" + flightIdSeq.getAndIncrement();
    }

    /**
     * Generates a new unique reservation identifier.
     *
     * @return a unique reservation id as a string.
     */
    private String nextReservationId() {
        return "RS-" + reservationIdSeq.getAndIncrement();
    }

    /**
     * Generates a new unique booking identifier.
     *
     * @return a unique booking id as a string.
     */
    private String nextBookingId() {
        return "BK-" + bookingIdSeq.getAndIncrement();
    }

    /**
     * Generates a new unique customer identifier.
     *
     * @return a unique customer id as a string.
     */
    private String nextCustomerId() {
        return "CU-" + customerIdSeq.getAndIncrement();
    }

    /* ---------- Core Functionalities ---------- */

    /**
     * Publishes a flight for booking after validating timestamps, temporal consistency and route integrity.
     *
     * @param flight the flight to be published; its departure/arrival timestamps must be parsable
     *               and satisfy currentTime &lt; departure &lt; arrival, and departureAirport ≠ arrivalAirport.
     * @return {@code true} if the flight was successfully published; {@code false} otherwise.
     */
    public boolean publishFlight(Flight flight) {
        // Validate timestamp format (they are already LocalDateTime, so format is guaranteed)
        LocalDateTime now = LocalDateTime.now();

        if (flight.getDepartureTime() == null || flight.getArrivalTime() == null) {
            return false;
        }

        // Temporal consistency
        if (!now.isBefore(flight.getDepartureTime()) ||
            !flight.getDepartureTime().isBefore(flight.getArrivalTime())) {
            return false;
        }

        // Route integrity
        if (flight.getDepartureAirport() == null || flight.getArrivalAirport() == null ||
            Objects.equals(flight.getDepartureAirport().getId(),
                          flight.getArrivalAirport().getId())) {
            return false;
        }

        // Flight may be published only once and must be open
        if (flight.getStatus() != FlightStatus.OPEN) {
            return false;
        }

        // Assign a unique identifier if not already set
        if (flight.getId() == null || flight.getId().isEmpty()) {
            flight.setId(nextFlightId());
        }

        flights.put(flight.getId(), flight);
        return true;
    }

    /**
     * Creates a booking for the given customer on an open flight with the supplied passenger names.
     *
     * @param customerId      identifier of the customer creating the booking.
     * @param flightId        identifier of the flight to be booked.
     * @param passengerNames  list of passenger names; must contain no duplicates on the same flight.
     * @return {@code true} if the booking and associated reservations were successfully created; {@code false} otherwise.
     */
    public boolean createBooking(String customerId, String flightId, List<String> passengerNames) {
        Customer customer = customers.get(customerId);
        Flight flight = flights.get(flightId);
        LocalDateTime now = LocalDateTime.now();

        if (customer == null || flight == null) {
            return false;
        }

        // Flight must be open and not departed yet
        if (flight.getStatus() != FlightStatus.OPEN || !now.isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Check duplicate passenger names on the same flight (including existing reservations)
        for (String name : passengerNames) {
            boolean duplicate = flight.getReservations().stream()
                    .anyMatch(r -> r.getPassengerName().equalsIgnoreCase(name));
            if (duplicate) {
                return false;
            }
        }

        // Create booking
        Booking booking = new Booking(nextBookingId(), customerId, flightId);
        for (String name : passengerNames) {
            Reservation reservation = new Reservation(nextReservationId(), name, flight);
            reservation.setStatus(ReservationStatus.PENDING);
            // link both ways
            booking.addReservation(reservation);
            flight.addReservation(reservation);
            reservations.put(reservation.getId(), reservation);
        }

        // Persist booking
        customer.addBooking(booking);
        return true;
    }

    /**
     * Confirms or cancels an existing reservation, provided the flight has not yet departed and is still open.
     *
     * @param reservationId identifier of the reservation to be updated.
     * @param confirm        {@code true} to confirm the reservation; {@code false} to cancel it.
     * @return {@code true} if the operation succeeded; {@code false} otherwise.
     */
    public boolean updateReservationStatus(String reservationId, boolean confirm) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) {
            return false;
        }

        Flight flight = reservation.getFlight();
        LocalDateTime now = LocalDateTime.now();

        // Flight must be open and not departed
        if (flight == null ||
            flight.getStatus() != FlightStatus.OPEN ||
            !now.isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Update status
        reservation.setStatus(confirm ? ReservationStatus.CONFIRMED : ReservationStatus.CANCELLED);
        return true;
    }

    /**
     * Closes an open flight that has not yet departed. All confirmed reservations are cancelled.
     *
     * @param flightId identifier of the flight to close.
     * @return {@code true} if the flight was successfully closed; {@code false} otherwise.
     */
    public boolean closeFlight(String flightId) {
        Flight flight = flights.get(flightId);
        if (flight == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();

        // Flight must be open and not departed
        if (flight.getStatus() != FlightStatus.OPEN || !now.isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Change status to CLOSED
        flight.setStatus(FlightStatus.CLOSED);

        // Cancel every confirmed reservation
        for (Reservation reservation : flight.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                reservation.setStatus(ReservationStatus.CANCELLED);
            }
        }

        return true;
    }

    /**
     * Retrieves all confirmed reservations for a specific flight, provided the flight is still open.
     *
     * @param flightId identifier of the flight.
     * @return an unmodifiable list of confirmed reservations; empty list if none exist or flight is not open.
     */
    public List<Reservation> getConfirmedReservations(String flightId) {
        Flight flight = flights.get(flightId);
        if (flight == null || flight.getStatus() != FlightStatus.OPEN) {
            return Collections.emptyList();
        }

        List<Reservation> confirmed = new ArrayList<>();
        for (Reservation reservation : flight.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                confirmed.add(reservation);
            }
        }
        return Collections.unmodifiableList(confirmed);
    }

    /**
     * Adds a stopover to a flight before its departure time. Validates that the stopover fits within the
     * overall flight schedule and that the airport serves at least one city.
     *
     * @param flightId   identifier of the flight.
     * @param stopover   stopover to be added.
     * @return {@code true} if the stopover was added; {@code false} otherwise.
     */
    public boolean addStopover(String flightId, Stopover stopover) {
        Flight flight = flights.get(flightId);
        if (flight == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();

        // Flight must not have departed yet
        if (!now.isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Airport must serve at least one city
        if (stopover.getAirport() == null ||
            stopover.getAirport().getCities() == null ||
            stopover.getAirport().getCities().isEmpty()) {
            return false;
        }

        // Times must be within flight schedule
        if (stopover.getArrivalTime() == null || stopover.getDepartureTime() == null) {
            return false;
        }
        if (!flight.getDepartureTime().isBefore(stopover.getArrivalTime())) {
            return false;
        }
        if (!stopover.getDepartureTime().isBefore(flight.getArrivalTime())) {
            return false;
        }
        if (!stopover.getArrivalTime().isBefore(stopover.getDepartureTime())) {
            return false;
        }

        flight.addStopover(stopover);
        return true;
    }

    /**
     * Deletes a stopover from a flight before departure.
     *
     * @param flightId    identifier of the flight.
     * @param airportId   identifier of the airport whose stopover should be removed.
     * @return {@code true} if the stopover existed and was removed; {@code false} otherwise.
     */
    public boolean deleteStopover(String flightId, String airportId) {
        Flight flight = flights.get(flightId);
        if (flight == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();

        // Flight must not have departed yet
        if (!now.isBefore(flight.getDepartureTime())) {
            return false;
        }

        return flight.removeStopoverByAirportId(airportId);
    }

    /* ---------- Convenience Methods for Tests ---------- */

    /**
     * Registers a new customer in the system.
     *
     * @param name the name of the customer.
     * @return the created {@link Customer} instance.
     */
    public Customer registerCustomer(String name) {
        Customer customer = new Customer(nextCustomerId(), name);
        customers.put(customer.getId(), customer);
        return customer;
    }

    /**
     * Retrieves a flight by its identifier.
     *
     * @param flightId identifier of the flight.
     * @return the {@link Flight} instance, or {@code null} if not found.
     */
    public Flight getFlight(String flightId) {
        return flights.get(flightId);
    }

    /**
     * Retrieves a reservation by its identifier.
     *
     * @param reservationId identifier of the reservation.
     * @return the {@link Reservation} instance, or {@code null} if not found.
     */
    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    /**
     * Retrieves a customer by its identifier.
     *
     * @param customerId identifier of the customer.
     * @return the {@link Customer} instance, or {@code null} if not found.
     */
    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }
}