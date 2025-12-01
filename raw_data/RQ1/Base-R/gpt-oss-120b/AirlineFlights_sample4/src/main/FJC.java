import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Represents a city served by an airport.
 */
class City {
    private String id;
    private String name;

    public City() {
    }

    public City(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /* Getters and Setters */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an airport. An airport can serve one or more cities.
 */
class Airport {
    private String id;
    private String name;
    private List<City> cities = new ArrayList<>();

    public Airport() {
    }

    public Airport(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /* Getters and Setters */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
}

/**
 * Flight status enumeration.
 */
enum FlightStatus {
    DRAFT,   // created but not yet published
    OPEN,    // open for booking
    CLOSED   // closed for booking
}

/**
 * Represents a flight. A flight has departure/arrival airports,
 * departure/arrival timestamps and optional stopovers.
 */
class Flight {
    private String id;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightStatus status = FlightStatus.DRAFT;
    private List<Stopover> stopovers = new ArrayList<>();

    public Flight() {
    }

    public Flight(String id, Airport departureAirport, Airport arrivalAirport,
                  LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    /* Getters and Setters */
    public String getId() {
        return id;
    }

    public void setId(String id) {
		this.id = id;
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

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public List<Stopover> getStopovers() {
        return stopovers;
    }

    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    /**
     * Adds a stopover to this flight after validating temporal consistency.
     *
     * @param stopover the stopover to add
     * @return true if the stopover was added, false otherwise
     */
    public boolean addStopover(Stopover stopover) {
        // stopover times must be inside the overall flight schedule
        if (stopover.getArrivalTime().isBefore(this.departureTime) ||
            stopover.getDepartureTime().isAfter(this.arrivalTime) ||
            !stopover.getArrivalTime().isBefore(stopover.getDepartureTime())) {
            return false;
        }
        // airport must serve at least one city
        if (stopover.getAirport() == null || stopover.getAirport().getCities().isEmpty()) {
            return false;
        }
        this.stopovers.add(stopover);
        return true;
    }

    /**
     * Deletes a stopover from this flight.
     *
     * @param stopoverId the identifier of the stopover to delete
     * @return true if a stopover was removed, false otherwise
     */
    public boolean deleteStopover(String stopoverId) {
        return this.stopovers.removeIf(s -> s.getId().equals(stopoverId));
    }
}

/**
 * Represents a stopover on a flight.
 */
class Stopover {
    private static final AtomicLong COUNTER = new AtomicLong(1);
    private String id;
    private Airport airport;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public Stopover() {
        this.id = "STO-" + COUNTER.getAndIncrement();
    }

    public Stopover(Airport airport, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.id = "STO-" + COUNTER.getAndIncrement();
        this.airport = airport;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    /* Getters and Setters */
    public String getId() {
        return id;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
}

/**
 * Reservation status enumeration.
 */
enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELED
}

/**
 * Represents a reservation for a single passenger on a flight.
 */
class Reservation {
    private static final AtomicLong COUNTER = new AtomicLong(1);
    private String id;
    private String passengerName;
    private ReservationStatus status = ReservationStatus.PENDING;
    private Booking booking; // back reference (optional)

    public Reservation() {
        this.id = "RES-" + COUNTER.getAndIncrement();
    }

    public Reservation(String passengerName) {
        this.id = "RES-" + COUNTER.getAndIncrement();
        this.passengerName = passengerName;
    }

    /* Getters and Setters */
    public String getId() {
        return id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}

/**
 * Represents a booking made by a customer; a booking contains multiple reservations.
 */
class Booking {
    private static final AtomicLong COUNTER = new AtomicLong(1);
    private String id;
    private Customer customer;
    private Flight flight;
    private List<Reservation> reservations = new ArrayList<>();

    public Booking() {
        this.id = "BK-" + COUNTER.getAndIncrement();
    }

    public Booking(Customer customer, Flight flight) {
        this.id = "BK-" + COUNTER.getAndIncrement();
        this.customer = customer;
        this.flight = flight;
    }

    /* Getters and Setters */
    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}

/**
 * Represents a customer of the airline.
 */
class Customer {
    private String id;
    private String name;
    private List<Booking> bookings = new ArrayList<>();

    public Customer() {
    }

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /* Getters and Setters */
    public String getId() {
        return id;
    }

    public void setId(String id) {
		this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
}

/**
 * Core system managing flights, airports, customers, bookings and reservations.
 */
class AirlineSystem {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Map<String, Flight> flights = new HashMap<>();
    private Map<String, Airport> airports = new HashMap<>();
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, Reservation> reservations = new HashMap<>();

    /* --------------------------------------------------------------------- */
    /* Helper methods to parse timestamps and generate IDs                    */
    /* --------------------------------------------------------------------- */

    /**
     * Parses a date‑time string according to the required format.
     *
     * @param dateTimeStr the string to parse
     * @return a {@link LocalDateTime} instance
     * @throws DateTimeParseException if parsing fails
     */
    private LocalDateTime parseDateTime(String dateTimeStr) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeStr, FORMATTER);
    }

    /* --------------------------------------------------------------------- */
    /* Functional Requirement Implementations                               */
    /* --------------------------------------------------------------------- */

    /**
     * Publishes a flight for booking.
     *
     * The system checks:
     * <ul>
     *   <li>departure and arrival timestamps are in the required format</li>
     *   <li>current time &lt; departure &lt; arrival</li>
     *   <li>departure and arrival airports differ</li>
     *   <li>flight has not been published before and is still in DRAFT state</li>
     * </ul>
     *
     * @param flightId the identifier of the flight to publish
     * @return true if the flight was successfully published, false otherwise
     */
    public boolean publishFlight(String flightId) {
        Flight flight = flights.get(flightId);
        if (flight == null) {
            return false;
        }

        // Must be in DRAFT state and not already published
        if (flight.getStatus() != FlightStatus.DRAFT) {
            return false;
        }

        // Validate timestamps are present
        LocalDateTime dep = flight.getDepartureTime();
        LocalDateTime arr = flight.getArrivalTime();
        if (dep == null || arr == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();

        // Temporal consistency
        if (!now.isBefore(dep) || !dep.isBefore(arr)) {
            return false;
        }

        // Route integrity
        if (flight.getDepartureAirport() == null ||
            flight.getArrivalAirport() == null ||
            flight.getDepartureAirport().getId().equals(flight.getArrivalAirport().getId())) {
            return false;
        }

        // All checks passed – publish
        flight.setStatus(FlightStatus.OPEN);
        return true;
    }

    /**
     * Creates a booking for a list of passenger names on a specific open flight.
     *
     * The system checks:
     * <ul>
     *   <li>the flight exists and is OPEN</li>
     *   <li>current time is before the flight's departure time</li>
     *   <li>no duplicate passenger names already exist on the same flight</li>
     * </ul>
     *
     * For each passenger a {@link Reservation} with status PENDING is created.
     *
     * @param flightId        the flight to book
     * @param customerId      the customer making the booking
     * @param passengerNames list of passenger names
     * @return true if the booking was successfully created, false otherwise
     */
    public boolean createBooking(String flightId, String customerId, List<String> passengerNames) {
        Flight flight = flights.get(flightId);
        Customer customer = customers.get(customerId);
        if (flight == null || customer == null) {
            return false;
        }

        if (flight.getStatus() != FlightStatus.OPEN) {
            return false;
        }

        if (LocalDateTime.now().isAfter(flight.getDepartureTime())) {
            return false;
        }

        // Gather existing passenger names on this flight
        Set<String> existingNames = new HashSet<>();
        for (Reservation r : reservations.values()) {
            if (r.getBooking() != null && r.getBooking().getFlight().getId().equals(flightId)
                    && r.getStatus() != ReservationStatus.CANCELED) {
                existingNames.add(r.getPassengerName().toLowerCase());
            }
        }

        // Verify no duplicate passengers in the request and against existing ones
        for (String name : passengerNames) {
            if (existingNames.contains(name.toLowerCase())) {
                return false; // duplicate passenger on same flight
            }
        }

        // Create booking and reservations
        Booking booking = new Booking(customer, flight);
        for (String name : passengerNames) {
            Reservation reservation = new Reservation(name);
            reservation.setBooking(booking);
            booking.getReservations().add(reservation);
            reservations.put(reservation.getId(), reservation);
        }

        // Store booking
        customer.getBookings().add(booking);
        return true;
    }

    /**
     * Confirms or cancels an existing reservation.
     *
     * The system checks:
     * <ul>
     *   <li>the reservation exists</li>
     *   <li>the related flight has not yet departed and is still OPEN</li>
     * </ul>
     *
     * @param reservationId the identifier of the reservation
     * @param confirm       true to confirm, false to cancel
     * @return true if the operation succeeded, false otherwise
     */
    public boolean updateReservationStatus(String reservationId, boolean confirm) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) {
            return false;
        }

        Booking booking = reservation.getBooking();
        if (booking == null) {
            return false;
        }

        Flight flight = booking.getFlight();
        if (flight == null || flight.getStatus() != FlightStatus.OPEN) {
            return false;
        }

        if (LocalDateTime.now().isAfter(flight.getDepartureTime())) {
            return false; // flight already departed
        }

        if (confirm) {
            reservation.setStatus(ReservationStatus.CONFIRMED);
        } else {
            reservation.setStatus(ReservationStatus.CANCELED);
        }
        return true;
    }

    /**
     * Closes an open flight that has not yet departed.
     *
     * The system verifies that the flight is currently OPEN.
     * Once closed, every confirmed reservation on the flight is cancelled.
     *
     * @param flightId the flight to close
     * @return true if the flight was closed successfully, false otherwise
     */
    public boolean closeFlight(String flightId) {
        Flight flight = flights.get(flightId);
        if (flight == null) {
            return false;
        }

        if (flight.getStatus() != FlightStatus.OPEN) {
            return false;
        }

        if (LocalDateTime.now().isAfter(flight.getDepartureTime())) {
            return false; // cannot close after departure
        }

        // Cancel all confirmed reservations
        for (Reservation r : reservations.values()) {
            Booking b = r.getBooking();
            if (b != null && b.getFlight().getId().equals(flightId)
                    && r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELED);
            }
        }

        flight.setStatus(FlightStatus.CLOSED);
        return true;
    }

    /**
     * Retrieves all confirmed reservations for a specific flight,
     * provided the flight is currently OPEN.
     *
     * @param flightId the identifier of the flight
     * @return a list of confirmed reservations; empty list if none or if flight is not open
     */
    public List<Reservation> getConfirmedReservations(String flightId) {
        Flight flight = flights.get(flightId);
        if (flight == null || flight.getStatus() != FlightStatus.OPEN) {
            return Collections.emptyList();
        }

        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations.values()) {
            Booking b = r.getBooking();
            if (b != null && b.getFlight().getId().equals(flightId)
                    && r.getStatus() == ReservationStatus.CONFIRMED) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Adds a stopover to a flight before it departs.
     *
     * The system ensures:
     * <ul>
     *   <li>the flight exists and has not yet departed</li>
     *   <li>stopover times fit inside the overall flight schedule</li>
     *   <li>the stopover airport serves at least one city</li>
     * </ul>
     *
     * @param flightId      the flight to modify
     * @param airportId     the airport of the stopover
     * @param arrivalStr    arrival timestamp in {@code yyyy-MM-dd HH:mm:ss}
     * @param departureStr  departure timestamp in {@code yyyy-MM-dd HH:mm:ss}
     * @return true if the stopover was added, false otherwise
     */
    public boolean addStopover(String flightId, String airportId,
                               String arrivalStr, String departureStr) {
        Flight flight = flights.get(flightId);
        Airport airport = airports.get(airportId);
        if (flight == null || airport == null) {
            return false;
        }

        if (LocalDateTime.now().isAfter(flight.getDepartureTime())) {
            return false; // cannot modify after departure
        }

        try {
            LocalDateTime arrival = parseDateTime(arrivalStr);
            LocalDateTime departure = parseDateTime(departureStr);
            Stopover stopover = new Stopover(airport, arrival, departure);
            return flight.addStopover(stopover);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Deletes a stopover from a flight before it departs.
     *
     * @param flightId   the flight to modify
     * @param stopoverId the identifier of the stopover to delete
     * @return true if the stopover was removed, false otherwise
     */
    public boolean deleteStopover(String flightId, String stopoverId) {
        Flight flight = flights.get(flightId);
        if (flight == null) {
            return false;
        }

        if (LocalDateTime.now().isAfter(flight.getDepartureTime())) {
            return false; // cannot modify after departure
        }

        return flight.deleteStopover(stopoverId);
    }

    /* --------------------------------------------------------------------- */
    /* Additional utility methods (creation of airports, flights, etc.)      */
    /* --------------------------------------------------------------------- */

    public Airport createAirport(String id, String name, List<City> cities) {
        Airport airport = new Airport(id, name);
        airport.setCities(cities);
        airports.put(id, airport);
        return airport;
    }

    public City createCity(String id, String name) {
        return new City(id, name);
    }

    public Flight createFlight(String id, String departureAirportId, String arrivalAirportId,
                               String departureTimeStr, String arrivalTimeStr) {
        Airport dep = airports.get(departureAirportId);
        Airport arr = airports.get(arrivalAirportId);
        if (dep == null || arr == null) {
            return null;
        }

        try {
            LocalDateTime depTime = parseDateTime(departureTimeStr);
            LocalDateTime arrTime = parseDateTime(arrivalTimeStr);
            Flight flight = new Flight(id, dep, arr, depTime, arrTime);
            flights.put(id, flight);
            return flight;
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public Customer createCustomer(String id, String name) {
        Customer customer = new Customer(id, name);
        customers.put(id, customer);
        return customer;
    }

    /* Getters for internal collections (useful for tests) */
    public Map<String, Flight> getFlights() {
        return flights;
    }

    public Map<String, Airport> getAirports() {
        return airports;
    }

    public Map<String, Customer> getCustomers() {
        return customers;
    }

    public Map<String, Reservation> getReservations() {
        return reservations;
    }
}