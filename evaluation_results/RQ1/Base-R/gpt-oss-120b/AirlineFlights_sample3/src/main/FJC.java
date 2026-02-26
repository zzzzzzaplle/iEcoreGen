import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

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

    /** getters / setters **/
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
 * Represents an airport. An airport may serve one or more cities.
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

    /** getters / setters **/
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

    public List<City> getServedCities() {
        return servedCities;
    }

    public void setServedCities(List<City> servedCities) {
        this.servedCities = servedCities;
    }

    public void addCity(City city) {
        if (!servedCities.contains(city)) {
            servedCities.add(city);
        }
    }
}

/**
 * Flight status – a flight can be OPEN for booking or CLOSED.
 */
enum FlightStatus {
    OPEN,
    CLOSED
}

/**
 * Represents a stopover within a flight.
 */
class Stopover {
    private Airport airport;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public Stopover() {
    }

    public Stopover(Airport airport, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.airport = airport;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    /** getters / setters **/
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
 * Represents a flight.
 */
class Flight {
    private String id;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private List<Stopover> stopovers = new ArrayList<>();
    private FlightStatus status = FlightStatus.OPEN; // default when created
    private boolean published = false;               // true once published

    public Flight() {
    }

    public Flight(String id) {
        this.id = id;
    }

    /** getters / setters **/
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

    public List<Stopover> getStopovers() {
        return stopovers;
    }

    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public void addStopover(Stopover stopover) {
        this.stopovers.add(stopover);
    }

    public void removeStopover(Stopover stopover) {
        this.stopovers.remove(stopover);
    }
}

/**
 * Reservation status.
 */
enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELED
}

/**
 * Represents a reservation for a single passenger on a single flight.
 */
class Reservation {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private String id;
    private Flight flight;
    private String passengerName;
    private ReservationStatus status = ReservationStatus.PENDING;

    public Reservation() {
        this.id = "R" + ID_GENERATOR.getAndIncrement();
    }

    public Reservation(Flight flight, String passengerName) {
        this.id = "R" + ID_GENERATOR.getAndIncrement();
        this.flight = flight;
        this.passengerName = passengerName;
    }

    /** getters / setters **/
    public String getId() {
        return id;
    }

    // No setter for id – it is generated automatically

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
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
}

/**
 * Represents a customer.
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

    /** getters / setters **/
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
 * Represents a booking made by a customer. A booking contains one reservation per passenger.
 */
class Booking {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private String id;
    private Customer customer;
    private Flight flight;
    private List<Reservation> reservations = new ArrayList<>();

    public Booking() {
        this.id = "B" + ID_GENERATOR.getAndIncrement();
    }

    public Booking(Customer customer, Flight flight) {
        this.id = "B" + ID_GENERATOR.getAndIncrement();
        this.customer = customer;
        this.flight = flight;
    }

    /** getters / setters **/
    public String getId() {
        return id;
    }

    // No setter for id – generated automatically

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

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
}

/**
 * Represents an airline that owns flights.
 */
class Airline {
    private String id;
    private String name;
    private List<Flight> flights = new ArrayList<>();

    public Airline() {
    }

    public Airline(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /** getters / setters **/
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

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    public Flight findFlightById(String flightId) {
        for (Flight f : flights) {
            if (Objects.equals(f.getId(), flightId)) {
                return f;
            }
        }
        return null;
    }
}

/**
 * Central repository for bookings – a very simple in‑memory store.
 */
class BookingRepository {
    private static final List<Booking> BOOKINGS = new ArrayList<>();

    public static void addBooking(Booking booking) {
        BOOKINGS.add(booking);
    }

    public static List<Booking> getAllBookings() {
        return Collections.unmodifiableList(BOOKINGS);
    }

    public static List<Reservation> getReservationsByFlightId(String flightId) {
        List<Reservation> result = new ArrayList<>();
        for (Booking b : BOOKINGS) {
            if (b.getFlight() != null && Objects.equals(b.getFlight().getId(), flightId)) {
                result.addAll(b.getReservations());
            }
        }
        return result;
    }

    public static Reservation findReservationById(String reservationId) {
        for (Booking b : BOOKINGS) {
            for (Reservation r : b.getReservations()) {
                if (Objects.equals(r.getId(), reservationId)) {
                    return r;
                }
            }
        }
        return null;
    }

    public static Booking findBookingByReservationId(String reservationId) {
        for (Booking b : BOOKINGS) {
            for (Reservation r : b.getReservations()) {
                if (Objects.equals(r.getId(), reservationId)) {
                    return b;
                }
            }
        }
        return null;
    }
}

/**
 * Service class that implements the functional requirements.
 */
class FlightService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Publishes a flight for booking.
     *
     * @param flight the flight to publish
     * @return {@code true} if the flight was successfully published; {@code false} otherwise
     */
    public static boolean publishFlight(Flight flight) {
        // Validate timestamps format – they are already stored as LocalDateTime,
        // so format validation is only required if they were strings.
        if (flight == null ||
                flight.getDepartureTime() == null ||
                flight.getArrivalTime() == null ||
                flight.getDepartureAirport() == null ||
                flight.getArrivalAirport() == null) {
            return false;
        }

        // Temporal consistency: now < departure < arrival
        LocalDateTime now = LocalDateTime.now();
        if (!now.isBefore(flight.getDepartureTime()) ||
                !flight.getDepartureTime().isBefore(flight.getArrivalTime())) {
            return false;
        }

        // Route integrity
        if (Objects.equals(flight.getDepartureAirport().getId(),
                flight.getArrivalAirport().getId())) {
            return false;
        }

        // Flight may be published only once and must be open
        if (flight.isPublished() || flight.getStatus() != FlightStatus.OPEN) {
            return false;
        }

        flight.setPublished(true);
        return true;
    }

    /**
     * Creates a booking for a list of passenger names on a specific open flight.
     *
     * @param customer        the customer making the booking
     * @param flight          the flight to be booked (must be open)
     * @param passengerNames  list of passenger names
     * @return {@code true} if the booking was successfully created; {@code false} otherwise
     */
    public static boolean createBooking(Customer customer, Flight flight, List<String> passengerNames) {
        if (customer == null || flight == null || passengerNames == null || passengerNames.isEmpty()) {
            return false;
        }

        // Flight must be open and not departed yet
        if (flight.getStatus() != FlightStatus.OPEN) {
            return false;
        }
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // No duplicate passenger names in the request
        List<String> lowered = new ArrayList<>();
        for (String name : passengerNames) {
            String l = name.toLowerCase();
            if (lowered.contains(l)) {
                return false; // duplicate in request
            }
            lowered.add(l);
        }

        // Ensure none of the passengers already have a reservation on this flight
        List<Reservation> existing = BookingRepository.getReservationsByFlightId(flight.getId());
        for (Reservation r : existing) {
            if (lowered.contains(r.getPassengerName().toLowerCase())) {
                return false; // duplicate passenger already booked
            }
        }

        // Create booking and reservations
        Booking booking = new Booking(customer, flight);
        for (String name : passengerNames) {
            Reservation res = new Reservation(flight, name);
            // status is PENDING by default
            booking.addReservation(res);
        }

        BookingRepository.addBooking(booking);
        return true;
    }

    /**
     * Confirms or cancels an existing reservation.
     *
     * @param reservationId the unique identifier of the reservation
     * @param confirm       {@code true} to confirm, {@code false} to cancel
     * @return {@code true} if the operation succeeded; {@code false} otherwise
     */
    public static boolean updateReservationStatus(String reservationId, boolean confirm) {
        Reservation reservation = BookingRepository.findReservationById(reservationId);
        if (reservation == null) {
            return false;
        }

        Flight flight = reservation.getFlight();
        // Flight must still be open and not departed
        if (flight.getStatus() != FlightStatus.OPEN ||
                !LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        if (confirm) {
            reservation.setStatus(ReservationStatus.CONFIRMED);
        } else {
            reservation.setStatus(ReservationStatus.CANCELED);
        }
        return true;
    }

    /**
     * Closes an open flight that has not yet departed. All confirmed reservations are cancelled.
     *
     * @param flight the flight to close
     * @return {@code true} if the flight was successfully closed; {@code false} otherwise
     */
    public static boolean closeFlight(Flight flight) {
        if (flight == null) {
            return false;
        }

        // Flight must be open and not yet departed
        if (flight.getStatus() != FlightStatus.OPEN ||
                !LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Change status to CLOSED
        flight.setStatus(FlightStatus.CLOSED);

        // Cancel every confirmed reservation
        List<Reservation> allRes = BookingRepository.getReservationsByFlightId(flight.getId());
        for (Reservation r : allRes) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELED);
            }
        }
        return true;
    }

    /**
     * Retrieves all confirmed reservations for a specific flight while the flight is still open.
     *
     * @param flightId the identifier of the flight
     * @return a list of confirmed reservations; empty list if none exist or the flight is not open
     */
    public static List<Reservation> getConfirmedReservations(String flightId) {
        if (flightId == null) {
            return Collections.emptyList();
        }

        // Find flight (could be managed elsewhere – here we search all bookings)
        Flight flight = null;
        for (Booking b : BookingRepository.getAllBookings()) {
            if (b.getFlight() != null && Objects.equals(b.getFlight().getId(), flightId)) {
                flight = b.getFlight();
                break;
            }
        }
        if (flight == null || flight.getStatus() != FlightStatus.OPEN) {
            return Collections.emptyList();
        }

        List<Reservation> result = new ArrayList<>();
        for (Reservation r : BookingRepository.getReservationsByFlightId(flightId)) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Adds a stopover to a flight before it departs.
     *
     * @param flight   the flight to modify
     * @param stopover the stopover to add
     * @return {@code true} if the stopover was successfully added; {@code false} otherwise
     */
    public static boolean addStopover(Flight flight, Stopover stopover) {
        if (flight == null || stopover == null || stopover.getAirport() == null) {
            return false;
        }

        // Flight must not have departed yet
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Stopover times must be within the overall flight schedule
        if (stopover.getArrivalTime() == null || stopover.getDepartureTime() == null) {
            return false;
        }
        if (stopover.getArrivalTime().isBefore(flight.getDepartureTime()) ||
                stopover.getDepartureTime().isAfter(flight.getArrivalTime())) {
            return false;
        }
        if (!stopover.getArrivalTime().isBefore(stopover.getDepartureTime())) {
            return false;
        }

        // Ensure airport serves at least one city (simple validation)
        if (stopover.getAirport().getServedCities() == null ||
                stopover.getAirport().getServedCities().isEmpty()) {
            return false;
        }

        // No overlapping with existing stopovers
        for (Stopover existing : flight.getStopovers()) {
            boolean overlap = !(stopover.getDepartureTime().isBefore(existing.getArrivalTime()) ||
                    stopover.getArrivalTime().isAfter(existing.getDepartureTime()));
            if (overlap) {
                return false;
            }
        }

        flight.addStopover(stopover);
        return true;
    }

    /**
     * Deletes a stopover from a flight before the flight departs.
     *
     * @param flight   the flight to modify
     * @param airportId identifier of the airport whose stopover should be removed
     * @return {@code true} if a matching stopover was found and removed; {@code false} otherwise
     */
    public static boolean deleteStopover(Flight flight, String airportId) {
        if (flight == null || airportId == null) {
            return false;
        }

        // Flight must not have departed yet
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        Stopover toRemove = null;
        for (Stopover s : flight.getStopovers()) {
            if (Objects.equals(s.getAirport().getId(), airportId)) {
                toRemove = s;
                break;
            }
        }

        if (toRemove != null) {
            flight.removeStopover(toRemove);
            return true;
        }
        return false;
    }

    /**
     * Utility method to parse a date‑time string in the required format.
     *
     * @param dateTimeStr date‑time string in "yyyy-MM-dd HH:mm:ss" format
     * @return {@link LocalDateTime} instance or {@code null} if parsing fails
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, FORMATTER);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}