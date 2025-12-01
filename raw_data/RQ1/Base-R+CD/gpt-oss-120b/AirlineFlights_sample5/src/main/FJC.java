import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents an airline that manages a collection of flights.
 */
 class Airline {

    /** List of flights owned by the airline */
    private List<Flight> flights = new ArrayList<>();

    /** No‑argument constructor */
    public Airline() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    // -------------------------------------------------------------------------
    // Business operations
    // -------------------------------------------------------------------------

    /**
     * Adds a flight to the airline.
     *
     * @param f the flight to add
     */
    public void addFlight(Flight f) {
        if (f != null && !flights.contains(f)) {
            flights.add(f);
        }
    }

    /**
     * Removes a flight from the airline.
     *
     * @param f the flight to remove
     */
    public void removeFlight(Flight f) {
        flights.remove(f);
    }

    /**
     * Publishes a newly created flight for booking.
     *
     * <p>The method validates the date‑time format, checks that the current time
     * is before the departure, that the departure time is before the arrival
     * time, and that departure and arrival airports differ. A flight can be
     * published only once and must be open for booking.</p>
     *
     * @param f   the flight to publish
     * @param now the current time (used for validation)
     * @return {@code true} if the flight was successfully published,
     *         {@code false} otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || now == null) {
            return false;
        }

        // flight must not have been published before
        if (f.isOpenForBooking()) {
            return false;
        }

        // basic null checks
        if (f.getDepartureTime() == null || f.getArrivalTime() == null ||
                f.getDepartureAirport() == null || f.getArrivalAirport() == null) {
            return false;
        }

        // temporal consistency
        if (!now.before(f.getDepartureTime())) {
            return false;
        }
        if (!f.getDepartureTime().before(f.getArrivalTime())) {
            return false;
        }

        // route integrity
        if (f.getDepartureAirport().getId().equals(f.getArrivalAirport().getId())) {
            return false;
        }

        // all checks passed – open the flight for booking
        f.setOpenForBooking(true);
        // add flight to airline collection if not already there
        if (!flights.contains(f)) {
            flights.add(f);
        }
        return true;
    }

    /**
     * Closes an open flight that has not yet departed.
     *
     * <p>The method verifies that the flight exists, is currently open for
     * booking and that the current time is before the scheduled departure.
     * All confirmed reservations are cancelled.</p>
     *
     * @param flightId the unique identifier of the flight to close
     * @param now      the current time
     * @return {@code true} if the flight was successfully closed,
     *         {@code false} otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight flight = findFlightById(flightId);
        if (flight == null) {
            return false;
        }
        if (!flight.isOpenForBooking()) {
            return false;
        }
        if (!now.before(flight.getDepartureTime())) {
            return false; // flight already departed
        }

        // Cancel every confirmed reservation
        for (Reservation r : flight.getReservations()) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELLED);
            }
        }

        flight.setOpenForBooking(false);
        return true;
    }

    /**
     * Searches for flights that match the given origin airport id,
     * destination airport id and departure date (ignoring time of day).
     *
     * @param origin airport id of departure
     * @param dest   airport id of arrival
     * @param date   the day to search for (time part is ignored)
     * @return list of matching flights; empty list if none match
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        if (origin == null || dest == null || date == null) {
            return result;
        }

        Calendar targetCal = Calendar.getInstance();
        targetCal.setTime(date);
        int targetYear = targetCal.get(Calendar.YEAR);
        int targetMonth = targetCal.get(Calendar.MONTH);
        int targetDay = targetCal.get(Calendar.DAY_OF_MONTH);

        for (Flight f : flights) {
            if (!f.isOpenForBooking()) {
                continue; // only consider flights that are open
            }
            Airport dep = f.getDepartureAirport();
            Airport arr = f.getArrivalAirport();
            if (dep == null || arr == null) {
                continue;
            }
            if (!origin.equals(dep.getId()) || !dest.equals(arr.getId())) {
                continue;
            }

            Calendar depCal = Calendar.getInstance();
            depCal.setTime(f.getDepartureTime());
            if (depCal.get(Calendar.YEAR) == targetYear &&
                    depCal.get(Calendar.MONTH) == targetMonth &&
                    depCal.get(Calendar.DAY_OF_MONTH) == targetDay) {
                result.add(f);
            }
        }
        return result;
    }

    // -------------------------------------------------------------------------
    // Helper methods
    // -------------------------------------------------------------------------

    /**
     * Finds a flight in the airline's collection by its identifier.
     *
     * @param flightId the id to search for
     * @return the matching {@link Flight} or {@code null} if not found
     */
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
 * Represents a flight, its schedule, airports, stop‑overs and reservations.
 */
class Flight {

    private String id;
    private boolean openForBooking = false;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrialAirport; // typo kept from design (arrivalAirport)
    private List<Stopover> stopovers = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    /** No‑argument constructor */
    public Flight() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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
        return arrialAirport;
    }

    public void setArrivalAirport(Airport arrialAirport) {
        this.arrialAirport = arrialAirport;
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

    // -------------------------------------------------------------------------
    // Business operations
    // -------------------------------------------------------------------------

    /**
     * Adds a stopover to the flight before departure.
     *
     * @param stop the stopover to add
     * @param now  the current time (must be before flight departure)
     * @return {@code true} if the stopover was added successfully,
     *         {@code false} otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null || now == null) {
            return false;
        }
        if (!now.before(this.departureTime)) {
            return false; // cannot modify after departure
        }

        // Ensure stopover times are inside the overall flight schedule
        if (stop.getArrivalTime().before(this.departureTime) ||
                stop.getDepartureTime().after(this.arrivalTime) ||
                !stop.getArrivalTime().before(stop.getDepartureTime())) {
            return false;
        }

        // Avoid duplicate stopovers at the same airport and time
        for (Stopover s : stopovers) {
            if (s.getAirport().getId().equals(stop.getAirport().getId())
                    && s.getArrivalTime().equals(stop.getArrivalTime())
                    && s.getDepartureTime().equals(stop.getDepartureTime())) {
                return false;
            }
        }

        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight before departure.
     *
     * @param stop the stopover to remove
     * @param now  the current time (must be before flight departure)
     * @return {@code true} if the stopover was removed,
     *         {@code false} otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null || now == null) {
            return false;
        }
        if (!now.before(this.departureTime)) {
            return false; // cannot modify after departure
        }
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     *
     * @return list of {@link Reservation} objects whose status is {@code CONFIRMED}
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmed = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                confirmed.add(r);
            }
        }
        return confirmed;
    }

    /**
     * Adds a reservation to the flight.
     *
     * @param reservation the reservation to add
     */
    public void addReservation(Reservation reservation) {
        if (reservation != null && !reservations.contains(reservation)) {
            reservations.add(reservation);
        }
    }
}

/**
 * Represents a stopover point in a flight.
 */
class Stopover {

    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    /** No‑argument constructor */
    public Stopover() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}

/**
 * Represents an airport that can serve several cities.
 */
class Airport {

    private String id;
    private List<City> servesForCities = new ArrayList<>();

    /** No‑argument constructor */
    public Airport() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    /**
     * Adds a city to the list of cities served by this airport.
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
 * Simple POJO representing a city.
 */
class City {

    private String name;

    /** No‑argument constructor */
    public City() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a passenger (identified by name).
 */
class Passenger {

    private String name;

    /** No‑argument constructor */
    public Passenger() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a reservation for a passenger on a specific flight.
 */
class Reservation {

    private String id;
    private ReservationStatus status = ReservationStatus.PENDING;
    private Passenger passenger;
    private Flight flight;

    /** No‑argument constructor */
    public Reservation() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

/**
 * Enum describing possible reservation states.
 */
enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELLED
}

/**
 * Represents a booking made by a customer. A booking aggregates many reservations.
 */
class Booking {

    private Customer customer;
    private List<Reservation> reservations = new ArrayList<>();

    /** No‑argument constructor */
    public Booking() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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
     * Creates a reservation for a specific passenger on a given flight.
     *
     * @param f          the flight for which the reservation is created
     * @param passenger  passenger name
     * @param now        current time (must be before flight departure)
     * @return {@code true} if the reservation was successfully created,
     *         {@code false} otherwise
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (f == null || passenger == null || now == null) {
            return false;
        }
        if (!f.isOpenForBooking()) {
            return false;
        }
        if (!now.before(f.getDepartureTime())) {
            return false; // cannot reserve after departure
        }

        // Check for duplicate passenger names on the same flight
        for (Reservation r : f.getReservations()) {
            if (r.getPassenger().getName().equals(passenger)) {
                return false;
            }
        }

        // Build passenger and reservation objects
        Passenger p = new Passenger();
        p.setName(passenger);

        Reservation r = new Reservation();
        r.setId(UUID.randomUUID().toString());
        r.setPassenger(p);
        r.setFlight(f);
        r.setStatus(ReservationStatus.PENDING);

        // Link objects
        this.reservations.add(r);
        f.addReservation(r);
        return true;
    }
}

/**
 * Represents a customer that can create bookings and manage reservations.
 */
class Customer {

    private List<Booking> bookings = new ArrayList<>();

    /** No‑argument constructor */
    public Customer() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    // -------------------------------------------------------------------------
    // Business operations
    // -------------------------------------------------------------------------

    /**
     * Creates a new booking for a flight with a list of passenger names.
     *
     * @param f                     the flight to book
     * @param now                   current time (must be before flight departure)
     * @param listOfPassengerNames  list of passenger names
     * @return {@code true} if the booking was created successfully,
     *         {@code false} otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || now == null || listOfPassengerNames == null) {
            return false;
        }
        if (!f.isOpenForBooking()) {
            return false;
        }
        if (!now.before(f.getDepartureTime())) {
            return false;
        }

        // Ensure no duplicate passenger names in the request
        Set<String> uniqueNames = new HashSet<>(listOfPassengerNames);
        if (uniqueNames.size() != listOfPassengerNames.size()) {
            return false; // duplicates supplied
        }

        // Ensure none of the passengers already have a reservation on this flight
        for (Reservation existing : f.getReservations()) {
            if (uniqueNames.contains(existing.getPassenger().getName())) {
                return false; // passenger already booked on this flight
            }
        }

        Booking booking = new Booking();
        booking.setCustomer(this);

        // Create a reservation for each passenger
        for (String name : listOfPassengerNames) {
            boolean created = booking.createReservation(f, name, now);
            if (!created) {
                // rollback any reservations already added
                for (Reservation r : booking.getReservations()) {
                    f.getReservations().remove(r);
                }
                return false;
            }
        }

        // Add booking to the customer's list
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms an existing reservation.
     *
     * @param reservationID the identifier of the reservation to confirm
     * @param now           current time (must be before flight departure)
     * @return {@code true} if the reservation was confirmed,
     *         {@code false} otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        Reservation r = findReservationById(reservationID);
        if (r == null) {
            return false;
        }
        Flight f = r.getFlight();
        if (!f.isOpenForBooking()) {
            return false;
        }
        if (!now.before(f.getDepartureTime())) {
            return false;
        }
        if (r.getStatus() != ReservationStatus.PENDING) {
            return false; // only pending reservations can be confirmed
        }
        r.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels an existing reservation.
     *
     * @param reservationID the identifier of the reservation to cancel
     * @param now           current time (must be before flight departure)
     * @return {@code true} if the reservation was cancelled,
     *         {@code false} otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        Reservation r = findReservationById(reservationID);
        if (r == null) {
            return false;
        }
        Flight f = r.getFlight();
        if (!f.isOpenForBooking()) {
            return false;
        }
        if (!now.before(f.getDepartureTime())) {
            return false;
        }
        if (r.getStatus() == ReservationStatus.CANCELLED) {
            return false; // already cancelled
        }
        r.setStatus(ReservationStatus.CANCELLED);
        return true;
    }

    // -------------------------------------------------------------------------
    // Helper methods
    // -------------------------------------------------------------------------

    /**
     * Searches all bookings of this customer for a reservation with the given id.
     *
     * @param reservationID the reservation identifier
     * @return the matching {@link Reservation} or {@code null} if not found
     */
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