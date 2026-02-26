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

    /** @return the city identifier */
    public String getId() {
        return id;
    }

    /** @param id the city identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the city name */
    public String getName() {
        return name;
    }

    /** @param name the city name to set */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an airport that serves one or more cities.
 */
class Airport {
    private String id;
    private String name;
    private List<City> servedCities = new ArrayList<>();

    public Airport() {
    }

    public Airport(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /** @return the airport identifier */
    public String getId() {
        return id;
    }

    /** @param id the airport identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the airport name */
    public String getName() {
        return name;
    }

    /** @param name the airport name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the list of cities served by this airport */
    public List<City> getServedCities() {
        return servedCities;
    }

    /** @param servedCities the list of cities served by this airport */
    public void setServedCities(List<City> servedCities) {
        this.servedCities = servedCities;
    }

    /** Adds a city to the list of served cities. */
    public void addCity(City city) {
        this.servedCities.add(city);
    }
}

/**
 * Flight status enumeration.
 */
enum FlightStatus {
    OPEN, CLOSED, PUBLISHED
}

/**
 * Represents a stopover in a flight.
 */
class Stopover {
    private static final AtomicLong COUNTER = new AtomicLong(0);
    private String id;
    private Airport airport;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public Stopover() {
        this.id = "SO-" + COUNTER.incrementAndGet();
    }

    public Stopover(Airport airport, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this();
        this.airport = airport;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    /** @return the stopover identifier */
    public String getId() {
        return id;
    }

    /** @return the airport of the stopover */
    public Airport getAirport() {
        return airport;
    }

    /** @param airport the airport to set */
    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    /** @return the arrival time of the stopover */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /** @param arrivalTime the arrival time to set */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /** @return the departure time of the stopover */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /** @param departureTime the departure time to set */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
}

/**
 * Represents a flight.
 */
class Flight {
    private static final AtomicLong COUNTER = new AtomicLong(0);
    private String id;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightStatus status = FlightStatus.OPEN;
    private List<Stopover> stopovers = new ArrayList<>();

    public Flight() {
        this.id = "FL-" + COUNTER.incrementAndGet();
    }

    public Flight(Airport departureAirport, Airport arrivalAirport,
                  LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this();
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    /** @return the flight identifier */
    public String getId() {
        return id;
    }

    /** @return the departure airport */
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    /** @param departureAirport the departure airport to set */
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    /** @return the arrival airport */
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    /** @param arrivalAirport the arrival airport to set */
    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    /** @return the departure timestamp */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /** @param departureTime the departure timestamp to set */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /** @return the arrival timestamp */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /** @param arrivalTime the arrival timestamp to set */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /** @return the flight status */
    public FlightStatus getStatus() {
        return status;
    }

    /** @param status the flight status to set */
    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    /** @return the list of stopovers */
    public List<Stopover> getStopovers() {
        return stopovers;
    }

    /** @param stopovers the list of stopovers to set */
    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    /** Adds a stopover to the flight. */
    public void addStopover(Stopover stopover) {
        this.stopovers.add(stopover);
    }

    /** Removes a stopover from the flight by its identifier. */
    public boolean removeStopover(String stopoverId) {
        return this.stopovers.removeIf(s -> s.getId().equals(stopoverId));
    }
}

/**
 * Reservation status enumeration.
 */
enum ReservationStatus {
    PENDING, CONFIRMED, CANCELED
}

/**
 * Represents a reservation for a single passenger on a single flight.
 */
class Reservation {
    private static final AtomicLong COUNTER = new AtomicLong(0);
    private String id;
    private Flight flight;
    private String passengerName;
    private ReservationStatus status = ReservationStatus.PENDING;

    public Reservation() {
        this.id = "RS-" + COUNTER.incrementAndGet();
    }

    public Reservation(Flight flight, String passengerName) {
        this();
        this.flight = flight;
        this.passengerName = passengerName;
    }

    /** @return the reservation identifier */
    public String getId() {
        return id;
    }

    /** @return the flight associated with the reservation */
    public Flight getFlight() {
        return flight;
    }

    /** @param flight the flight to set */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /** @return the passenger name */
    public String getPassengerName() {
        return passengerName;
    }

    /** @param passengerName the passenger name to set */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /** @return the reservation status */
    public ReservationStatus getStatus() {
        return status;
    }

    /** @param status the reservation status to set */
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}

/**
 * Represents a booking made by a customer.
 */
class Booking {
    private static final AtomicLong COUNTER = new AtomicLong(0);
    private String id;
    private String customerId;
    private Flight flight;
    private List<Reservation> reservations = new ArrayList<>();

    public Booking() {
        this.id = "BK-" + COUNTER.incrementAndGet();
    }

    public Booking(String customerId, Flight flight) {
        this();
        this.customerId = customerId;
        this.flight = flight;
    }

    /** @return the booking identifier */
    public String getId() {
        return id;
    }

    /** @return the customer identifier */
    public String getCustomerId() {
        return customerId;
    }

    /** @param customerId the customer identifier to set */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /** @return the flight associated with the booking */
    public Flight getFlight() {
        return flight;
    }

    /** @param flight the flight to set */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /** @return the list of reservations belonging to this booking */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /** @param reservations the list of reservations to set */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /** Adds a reservation to the booking. */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
}

/**
 * Represents a customer of the airline system.
 */
class Customer {
    private String id;
    private String name;

    public Customer() {
    }

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /** @return the customer identifier */
    public String getId() {
        return id;
    }

    /** @param id the customer identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the customer name */
    public String getName() {
        return name;
    }

    /** @param name the customer name to set */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Core system managing flights, bookings and reservations.
 */
class AirlineSystem {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // In‑memory stores
    private Map<String, Flight> flights = new HashMap<>();
    private Map<String, Airport> airports = new HashMap<>();
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, Booking> bookings = new HashMap<>();
    private Map<String, Reservation> reservations = new HashMap<>();

    /** Adds a new airport to the system. */
    public void addAirport(Airport airport) {
        airports.put(airport.getId(), airport);
    }

    /** Adds a new flight to the system (not yet published). */
    public void addFlight(Flight flight) {
        flights.put(flight.getId(), flight);
    }

    /** Adds a new customer to the system. */
    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    /**
     * Publishes a flight for booking.
     *
     * @param flightId the identifier of the flight to publish
     * @return true if the flight was successfully published; false otherwise
     */
    public boolean publishFlight(String flightId) {
        Flight flight = flights.get(flightId);
        if (flight == null) {
            return false;
        }

        // Ensure flight has not been published yet
        if (flight.getStatus() != FlightStatus.OPEN) {
            return false;
        }

        // Validate timestamps format and temporal consistency
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dep = flight.getDepartureTime();
        LocalDateTime arr = flight.getArrivalTime();

        if (dep == null || arr == null) {
            return false;
        }

        if (!now.isBefore(dep) || !dep.isBefore(arr)) {
            return false;
        }

        // Route integrity
        if (flight.getDepartureAirport() == null || flight.getArrivalAirport() == null) {
            return false;
        }
        if (flight.getDepartureAirport().getId().equals(flight.getArrivalAirport().getId())) {
            return false;
        }

        flight.setStatus(FlightStatus.PUBLISHED);
        return true;
    }

    /**
     * Creates a booking for a list of passengers on a specific open flight.
     *
     * @param flightId       the flight identifier
     * @param customerId     the customer identifier
     * @param passengerNames list of passenger names
     * @return true if the booking and reservations were created successfully; false otherwise
     */
    public boolean createBooking(String flightId, String customerId, List<String> passengerNames) {
        Flight flight = flights.get(flightId);
        Customer customer = customers.get(customerId);

        if (flight == null || customer == null) {
            return false;
        }

        // Flight must be published (open for booking)
        if (flight.getStatus() != FlightStatus.PUBLISHED) {
            return false;
        }

        // Time check
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Check duplicate passenger names within this booking
        Set<String> uniqueNames = new HashSet<>(passengerNames);
        if (uniqueNames.size() != passengerNames.size()) {
            return false;
        }

        // Create booking
        Booking booking = new Booking(customerId, flight);
        bookings.put(booking.getId(), booking);

        // Create a reservation per passenger
        for (String name : passengerNames) {
            Reservation reservation = new Reservation(flight, name);
            reservations.put(reservation.getId(), reservation);
            booking.addReservation(reservation);
        }
        return true;
    }

    /**
     * Confirms a reservation.
     *
     * @param reservationId the reservation identifier to confirm
     * @return true if the reservation was confirmed; false otherwise
     */
    public boolean confirmReservation(String reservationId) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) {
            return false;
        }

        Flight flight = reservation.getFlight();
        if (flight == null) {
            return false;
        }

        // Flight must still be open for booking and not yet departed
        if (flight.getStatus() != FlightStatus.PUBLISHED) {
            return false;
        }
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            return false;
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels a reservation.
     *
     * @param reservationId the reservation identifier to cancel
     * @return true if the reservation was cancelled; false otherwise
     */
    public boolean cancelReservation(String reservationId) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) {
            return false;
        }

        Flight flight = reservation.getFlight();
        if (flight == null) {
            return false;
        }

        // Flight must still be open for booking and not yet departed
        if (flight.getStatus() != FlightStatus.PUBLISHED) {
            return false;
        }
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        if (reservation.getStatus() == ReservationStatus.CANCELED) {
            return false;
        }

        reservation.setStatus(ReservationStatus.CANCELED);
        return true;
    }

    /**
     * Closes an open flight.
     *
     * @param flightId the identifier of the flight to close
     * @return true if the flight was successfully closed; false otherwise
     */
    public boolean closeFlight(String flightId) {
        Flight flight = flights.get(flightId);
        if (flight == null) {
            return false;
        }

        // Must be published and not yet departed
        if (flight.getStatus() != FlightStatus.PUBLISHED) {
            return false;
        }
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Change status
        flight.setStatus(FlightStatus.CLOSED);

        // Cancel all confirmed reservations for this flight
        for (Reservation r : reservations.values()) {
            if (r.getFlight().getId().equals(flightId) &&
                r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELED);
            }
        }
        return true;
    }

    /**
     * Retrieves all confirmed reservations for a specific flight while it is still open.
     *
     * @param flightId the identifier of the flight
     * @return list of confirmed reservations; empty list if none or flight not open
     */
    public List<Reservation> getConfirmedReservations(String flightId) {
        Flight flight = flights.get(flightId);
        if (flight == null) {
            return Collections.emptyList();
        }

        if (flight.getStatus() != FlightStatus.PUBLISHED) {
            return Collections.emptyList();
        }

        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations.values()) {
            if (r.getFlight().getId().equals(flightId) &&
                r.getStatus() == ReservationStatus.CONFIRMED) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Adds a stopover to a flight before it departs.
     *
     * @param flightId   the identifier of the flight
     * @param airportId  the identifier of the stopover airport
     * @param arrivalStr arrival time in "yyyy-MM-dd HH:mm:ss" format
     * @param departureStr departure time in "yyyy-MM-dd HH:mm:ss" format
     * @return true if the stopover was added successfully; false otherwise
     */
    public boolean addStopover(String flightId, String airportId,
                               String arrivalStr, String departureStr) {
        Flight flight = flights.get(flightId);
        Airport airport = airports.get(airportId);
        if (flight == null || airport == null) {
            return false;
        }

        // Flight must not have departed yet
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        try {
            LocalDateTime arrival = LocalDateTime.parse(arrivalStr, FORMATTER);
            LocalDateTime departure = LocalDateTime.parse(departureStr, FORMATTER);

            // Times must be within overall flight schedule
            if (arrival.isBefore(flight.getDepartureTime()) ||
                departure.isAfter(flight.getArrivalTime()) ||
                !arrival.isBefore(departure)) {
                return false;
            }

            // Airport must serve at least one city (basic validation)
            if (airport.getServedCities() == null || airport.getServedCities().isEmpty()) {
                return false;
            }

            Stopover stopover = new Stopover(airport, arrival, departure);
            flight.addStopover(stopover);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Deletes a stopover from a flight before it departs.
     *
     * @param flightId   the identifier of the flight
     * @param stopoverId the identifier of the stopover to delete
     * @return true if the stopover was removed; false otherwise
     */
    public boolean deleteStopover(String flightId, String stopoverId) {
        Flight flight = flights.get(flightId);
        if (flight == null) {
            return false;
        }

        // Flight must not have departed yet
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        return flight.removeStopover(stopoverId);
    }

    // -------------------------------------------------------------------------
    // Helper getters for unit tests or external usage (optional)
    // -------------------------------------------------------------------------

    public Map<String, Flight> getFlights() {
        return flights;
    }

    public Map<String, Airport> getAirports() {
        return airports;
    }

    public Map<String, Customer> getCustomers() {
        return customers;
    }

    public Map<String, Booking> getBookings() {
        return bookings;
    }

    public Map<String, Reservation> getReservations() {
        return reservations;
    }
}