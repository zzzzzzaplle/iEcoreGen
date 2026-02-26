import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Simple domain model for an airline reservation system.
 * All classes contain a no‑argument constructor, getters and setters,
 * and the service layer implements the functional requirements described
 * in the task specification.
 */
 class AirlineReservationSystem {

    /* ====================== ENUMS ====================== */

    /** Flight status – either open for booking or closed. */
 enum FlightStatus {
        OPEN, CLOSED
    }

    /** Reservation status – pending, confirmed, or cancelled. */
 enum ReservationStatus {
        PENDING, CONFIRMED, CANCELED
    }

    /* ====================== DOMAIN CLASSES ====================== */

    /** City served by airports. */
    public static class City {
        private String id;
        private String name;

        public City() {}

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    /** Airport belonging to a city. */
    public static class Airport {
        private String id;
        private String name;
        private City city;

        public Airport() {}

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public City getCity() { return city; }
        public void setCity(City city) { this.city = city; }
    }

    /** Stopover on a flight – includes arrival and departure times. */
    public static class Stopover {
        private String id;
        private Airport airport;
        private LocalDateTime arrivalTime;
        private LocalDateTime departureTime;

        public Stopover() {}

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public Airport getAirport() { return airport; }
        public void setAirport(Airport airport) { this.airport = airport; }

        public LocalDateTime getArrivalTime() { return arrivalTime; }
        public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

        public LocalDateTime getDepartureTime() { return departureTime; }
        public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    }

    /** Flight entity. */
    public static class Flight {
        private String id;
        private Airport departureAirport;
        private Airport arrivalAirport;
        private LocalDateTime departureTime;
        private LocalDateTime arrivalTime;
        private FlightStatus status = FlightStatus.OPEN;
        private List<Stopover> stopovers = new ArrayList<>();

        public Flight() {}

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public Airport getDepartureAirport() { return departureAirport; }
        public void setDepartureAirport(Airport departureAirport) { this.departureAirport = departureAirport; }

        public Airport getArrivalAirport() { return arrivalAirport; }
        public void setArrivalAirport(Airport arrivalAirport) { this.arrivalAirport = arrivalAirport; }

        public LocalDateTime getDepartureTime() { return departureTime; }
        public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

        public LocalDateTime getArrivalTime() { return arrivalTime; }
        public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

        public FlightStatus getStatus() { return status; }
        public void setStatus(FlightStatus status) { this.status = status; }

        public List<Stopover> getStopovers() { return stopovers; }
        public void setStopovers(List<Stopover> stopovers) { this.stopovers = stopovers; }
    }

    /** Customer who makes bookings. */
    public static class Customer {
        private String id;
        private String name;

        public Customer() {}

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    /** Booking – a collection of reservations for a single flight. */
    public static class Booking {
        private String id;
        private Customer customer;
        private Flight flight;
        private List<Reservation> reservations = new ArrayList<>();

        public Booking() {}

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public Customer getCustomer() { return customer; }
        public void setCustomer(Customer customer) { this.customer = customer; }

        public Flight getFlight() { return flight; }
        public void setFlight(Flight flight) { this.flight = flight; }

        public List<Reservation> getReservations() { return reservations; }
        public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }
    }

    /** Reservation – a seat for a single passenger on a flight. */
    public static class Reservation {
        private String id;
        private String passengerName;
        private Flight flight;
        private ReservationStatus status = ReservationStatus.PENDING;

        public Reservation() {}

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getPassengerName() { return passengerName; }
        public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

        public Flight getFlight() { return flight; }
        public void setFlight(Flight flight) { this.flight = flight; }

        public ReservationStatus getStatus() { return status; }
        public void setStatus(ReservationStatus status) { this.status = status; }
    }

    /* ====================== SERVICE LAYER ====================== */

    /**
     * Core service implementing all functional requirements.
     * It keeps in‑memory collections of flights, bookings and reservations.
     */
    public static class AirlineService {

        private static final DateTimeFormatter DATE_TIME_FORMATTER =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        private final Map<String, Flight> flights = new HashMap<>();
        private final Map<String, Booking> bookings = new HashMap<>();
        private final Map<String, Reservation> reservations = new HashMap<>();

        private final AtomicLong flightIdSeq = new AtomicLong(1);
        private final AtomicLong bookingIdSeq = new AtomicLong(1);
        private final AtomicLong reservationIdSeq = new AtomicLong(1);
        private final AtomicLong stopoverIdSeq = new AtomicLong(1);

        /** Creates a new service instance. */
        public AirlineService() {}

        /* ----------------- Helper methods ----------------- */

        private String nextFlightId() { return "F" + flightIdSeq.getAndIncrement(); }
        private String nextBookingId() { return "B" + bookingIdSeq.getAndIncrement(); }
        private String nextReservationId() { return "R" + reservationIdSeq.getAndIncrement(); }
        private String nextStopoverId() { return "S" + stopoverIdSeq.getAndIncrement(); }

        private LocalDateTime parseDateTime(String text) {
            try {
                return LocalDateTime.parse(text, DATE_TIME_FORMATTER);
            } catch (DateTimeParseException ex) {
                return null;
            }
        }

        /* ----------------- Functional Requirements ----------------- */

        /**
         * Publish a flight for booking.
         * <p>
         * The method validates timestamp format, temporal consistency,
         * route integrity and ensures the flight has not been published before.
         *
         * @param flight the flight to be published (must have departure/arrival airports and timestamps)
         * @return {@code true} if the flight was successfully published; {@code false} otherwise
         */
        public boolean publishFlight(Flight flight) {
            // Validate timestamps format (already LocalDateTime, but ensure non‑null)
            if (flight.getDepartureTime() == null || flight.getArrivalTime() == null) {
                return false;
            }

            LocalDateTime now = LocalDateTime.now();
            // Temporal consistency
            if (!now.isBefore(flight.getDepartureTime()) ||
                !flight.getDepartureTime().isBefore(flight.getArrivalTime())) {
                return false;
            }

            // Route integrity
            if (flight.getDepartureAirport() == null ||
                flight.getArrivalAirport() == null ||
                flight.getDepartureAirport().getId().equals(flight.getArrivalAirport().getId())) {
                return false;
            }

            // Flight may be published only once
            if (flight.getId() != null && flights.containsKey(flight.getId())) {
                return false;
            }

            // Assign an ID and store
            flight.setId(nextFlightId());
            flight.setStatus(FlightStatus.OPEN);
            flights.put(flight.getId(), flight);
            return true;
        }

        /**
         * Create a booking for passengers on an open flight.
         * <p>
         * The method checks that the flight is open, that the current time
         * is before the flight's departure, and that passenger names are unique.
         *
         * @param customer        the customer making the booking
         * @param flightId        the identifier of the flight to book
         * @param passengerNames  list of passenger names (must be unique)
         * @return {@code true} if the booking and its reservations were created; {@code false} otherwise
         */
        public boolean createBooking(Customer customer, String flightId, List<String> passengerNames) {
            Flight flight = flights.get(flightId);
            if (flight == null || flight.getStatus() != FlightStatus.OPEN) {
                return false;
            }

            // Current time before departure
            if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
                return false;
            }

            // Ensure passenger names are unique within the request
            Set<String> distinct = new HashSet<>(passengerNames);
            if (distinct.size() != passengerNames.size()) {
                return false;
            }

            // Create booking
            Booking booking = new Booking();
            booking.setId(nextBookingId());
            booking.setCustomer(customer);
            booking.setFlight(flight);

            // Create reservations (pending)
            for (String name : passengerNames) {
                Reservation reservation = new Reservation();
                reservation.setId(nextReservationId());
                reservation.setPassengerName(name);
                reservation.setFlight(flight);
                reservation.setStatus(ReservationStatus.PENDING);
                reservations.put(reservation.getId(), reservation);
                booking.getReservations().add(reservation);
            }

            bookings.put(booking.getId(), booking);
            return true;
        }

        /**
         * Confirm an existing reservation.
         *
         * @param reservationId the identifier of the reservation to confirm
         * @return {@code true} if the reservation was confirmed; {@code false} otherwise
         */
        public boolean confirmReservation(String reservationId) {
            Reservation reservation = reservations.get(reservationId);
            if (reservation == null) {
                return false;
            }

            Flight flight = reservation.getFlight();
            // Flight must be open and not yet departed
            if (flight.getStatus() != FlightStatus.OPEN ||
                !LocalDateTime.now().isBefore(flight.getDepartureTime())) {
                return false;
            }

            // Only pending reservations can be confirmed
            if (reservation.getStatus() != ReservationStatus.PENDING) {
                return false;
            }

            reservation.setStatus(ReservationStatus.CONFIRMED);
            return true;
        }

        /**
         * Cancel an existing reservation.
         *
         * @param reservationId the identifier of the reservation to cancel
         * @return {@code true} if the reservation was cancelled; {@code false} otherwise
         */
        public boolean cancelReservation(String reservationId) {
            Reservation reservation = reservations.get(reservationId);
            if (reservation == null) {
                return false;
            }

            Flight flight = reservation.getFlight();
            // Flight must be open and not yet departed
            if (flight.getStatus() != FlightStatus.OPEN ||
                !LocalDateTime.now().isBefore(flight.getDepartureTime())) {
                return false;
            }

            // Can cancel pending or confirmed reservations
            if (reservation.getStatus() == ReservationStatus.CANCELED) {
                return false;
            }

            reservation.setStatus(ReservationStatus.CANCELED);
            return true;
        }

        /**
         * Close an open flight.
         * <p>
         * The method sets the flight status to CLOSED and automatically cancels
         * every confirmed reservation attached to that flight.
         *
         * @param flightId the identifier of the flight to close
         * @return {@code true} if the flight was successfully closed; {@code false} otherwise
         */
        public boolean closeFlight(String flightId) {
            Flight flight = flights.get(flightId);
            if (flight == null || flight.getStatus() != FlightStatus.OPEN) {
                return false;
            }

            // Flight must not have departed yet
            if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
                return false;
            }

            flight.setStatus(FlightStatus.CLOSED);

            // Cancel every confirmed reservation on this flight
            reservations.values().stream()
                    .filter(r -> r.getFlight().getId().equals(flightId))
                    .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED)
                    .forEach(r -> r.setStatus(ReservationStatus.CANCELED));

            return true;
        }

        /**
         * Retrieve all confirmed reservations for a specific flight.
         *
         * @param flightId the identifier of the flight
         * @return list of confirmed reservations; empty list if none or flight not open
         */
        public List<Reservation> getConfirmedReservations(String flightId) {
            Flight flight = flights.get(flightId);
            if (flight == null || flight.getStatus() != FlightStatus.OPEN) {
                return Collections.emptyList();
            }

            return reservations.values().stream()
                    .filter(r -> r.getFlight().getId().equals(flightId))
                    .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED)
                    .collect(Collectors.toList());
        }

        /**
         * Add a stopover to a flight before departure.
         *
         * @param flightId   the identifier of the flight
         * @param airport    the airport where the stopover occurs
         * @param arrivalStr arrival timestamp in {@code yyyy-MM-dd HH:mm:ss} format
         * @param departStr  departure timestamp in {@code yyyy-MM-dd HH:mm:ss} format
         * @return {@code true} if the stopover was added; {@code false} otherwise
         */
        public boolean addStopover(String flightId, Airport airport,
                                   String arrivalStr, String departStr) {
            Flight flight = flights.get(flightId);
            if (flight == null || flight.getStatus() != FlightStatus.OPEN) {
                return false;
            }

            // Flight must not have departed
            if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
                return false;
            }

            LocalDateTime arrival = parseDateTime(arrivalStr);
            LocalDateTime departure = parseDateTime(departStr);
            if (arrival == null || departure == null) {
                return false;
            }

            // Stopover times must be within overall flight schedule
            if (arrival.isBefore(flight.getDepartureTime()) ||
                departure.isAfter(flight.getArrivalTime()) ||
                !arrival.isBefore(departure)) {
                return false;
            }

            // Airport must serve a city (city not null)
            if (airport == null || airport.getCity() == null) {
                return false;
            }

            Stopover stopover = new Stopover();
            stopover.setId(nextStopoverId());
            stopover.setAirport(airport);
            stopover.setArrivalTime(arrival);
            stopover.setDepartureTime(departure);
            flight.getStopovers().add(stopover);
            return true;
        }

        /**
         * Delete a stopover from a flight before departure.
         *
         * @param flightId   the identifier of the flight
         * @param stopoverId the identifier of the stopover to delete
         * @return {@code true} if the stopover was removed; {@code false} otherwise
         */
        public boolean deleteStopover(String flightId, String stopoverId) {
            Flight flight = flights.get(flightId);
            if (flight == null || flight.getStatus() != FlightStatus.OPEN) {
                return false;
            }

            // Flight must not have departed
            if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
                return false;
            }

            List<Stopover> list = flight.getStopovers();
            boolean removed = list.removeIf(s -> s.getId().equals(stopoverId));
            return removed;
        }

        /* ----------------- Additional Accessors (useful for tests) ----------------- */

        public Flight getFlight(String flightId) { return flights.get(flightId); }

        public Booking getBooking(String bookingId) { return bookings.get(bookingId); }

        public Reservation getReservation(String reservationId) { return reservations.get(reservationId); }

        public Collection<Flight> getAllFlights() { return flights.values(); }

        public Collection<Booking> getAllBookings() { return bookings.values(); }

        public Collection<Reservation> getAllReservations() { return reservations.values(); }
    }

    /* ====================== USAGE EXAMPLE (for reference) ====================== */

    public static void main(String[] args) {
        // The main method demonstrates a tiny workflow; it is *not* part of the required API.
        AirlineService service = new AirlineService();

        // Create cities and airports
        City cityA = new City(); cityA.setId("C1"); cityA.setName("Metropolis");
        City cityB = new City(); cityB.setId("C2"); cityB.setName("Gotham");

        Airport airportA = new Airport(); airportA.setId("AP1"); airportA.setName("Metro Intl"); airportA.setCity(cityA);
        Airport airportB = new Airport(); airportB.setId("AP2"); airportB.setName("Gotham Central"); airportB.setCity(cityB);

        // Create a flight (not yet published)
        Flight flight = new Flight();
        flight.setDepartureAirport(airportA);
        flight.setArrivalAirport(airportB);
        flight.setDepartureTime(LocalDateTime.now().plusDays(2));
        flight.setArrivalTime(LocalDateTime.now().plusDays(2).plusHours(3));

        // Publish the flight
        boolean published = service.publishFlight(flight);
        System.out.println("Flight published: " + published + " (id=" + flight.getId() + ")");

        // Create a customer and a booking
        Customer cust = new Customer(); cust.setId("CU1"); cust.setName("John Doe");
        List<String> passengers = Arrays.asList("Alice", "Bob");
        boolean booked = service.createBooking(cust, flight.getId(), passengers);
        System.out.println("Booking created: " + booked);

        // Confirm a reservation
        Reservation anyReservation = service.getAllReservations().iterator().next();
        boolean confirmed = service.confirmReservation(anyReservation.getId());
        System.out.println("Reservation " + anyReservation.getId() + " confirmed: " + confirmed);
    }
}