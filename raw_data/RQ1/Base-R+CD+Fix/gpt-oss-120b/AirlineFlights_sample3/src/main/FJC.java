import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Represents an airline that manages flights.
 */
 class Airline {

    private List<Flight> flights = new ArrayList<>();

    /** No‑arg constructor */
    public Airline() {
    }

    /** Getters and Setters */
    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

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
     * Publishes a newly created flight to make it available for booking.
     * <p>
     * The method validates:
     * <ul>
     *   <li>departure and arrival timestamps are non‑null and follow the pattern yyyy‑MM‑dd HH:mm:ss</li>
     *   <li>temporal consistency: now &lt; departure &lt; arrival</li>
     *   <li>route integrity: departure airport differs from arrival airport</li>
     *   <li>the flight has not been published before</li>
     * </ul>
     *
     * @param f   the flight to publish
     * @param now the current time
     * @return {@code true} if the flight was successfully published, otherwise {@code false}
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || now == null) {
            return false;
        }

        // Validate date format (the Date objects are assumed to be already parsed correctly)
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fmt.setLenient(false);
        try {
            fmt.format(f.getDepartureTime());
            fmt.format(f.getArrivalTime());
        } catch (Exception e) {
            return false;
        }

        // Temporal consistency
        if (!now.before(f.getDepartureTime()) || !f.getDepartureTime().before(f.getArrivalTime())) {
            return false;
        }

        // Route integrity
        if (f.getDepartureAirport() == null || f.getArrialAirport() == null
                || f.getDepartureAirport().equals(f.getArrialAirport())) {
            return false;
        }

        // Flight may be published only once and must be currently closed
        if (f.isOpenForBooking()) {
            return false;
        }

        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an open flight that has not yet departed.
     *
     * @param flightId the identifier of the flight to close
     * @param now      the current time
     * @return {@code true} if the flight was successfully closed, otherwise {@code false}
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

        // Change status
        flight.setOpenForBooking(false);

        // Cancel every confirmed reservation
        for (Reservation r : flight.getReservations()) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELED);
            }
        }
        return true;
    }

    /**
     * Searches for flights matching the given origin, destination and date.
     *
     * @param origin the departure airport id
     * @param dest   the arrival airport id
     * @param date   the date of departure (time part is ignored)
     * @return list of matching flights (may be empty)
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        Calendar target = Calendar.getInstance();
        target.setTime(date);
        for (Flight f : flights) {
            if (!f.isOpenForBooking()) {
                continue;
            }
            if (f.getDepartureAirport() == null || f.getArrialAirport() == null) {
                continue;
            }
            if (!f.getDepartureAirport().getId().equals(origin) ||
                !f.getArrialAirport().getId().equals(dest)) {
                continue;
            }
            Calendar dep = Calendar.getInstance();
            dep.setTime(f.getDepartureTime());
            if (dep.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
                dep.get(Calendar.MONTH) == target.get(Calendar.MONTH) &&
                dep.get(Calendar.DAY_OF_MONTH) == target.get(Calendar.DAY_OF_MONTH)) {
                result.add(f);
            }
        }
        return result;
    }

    /* Helper method */
    private Flight findFlightById(String id) {
        for (Flight f : flights) {
            if (f.getId() != null && f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }
}

/**
 * Represents a flight.
 */
class Flight {

    private String id;
    private boolean openForBooking = false;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrialAirport;
    private List<Stopover> stopovers = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    /** No‑arg constructor */
    public Flight() {
    }

    /** Getters and Setters */
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

    public Airport getArrialAirport() {
        return arrialAirport;
    }

    public void setArrialAirport(Airport arrialAirport) {
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

    /**
     * Adds a stopover to the flight.
     *
     * @param stop the stopover to add
     * @param now  the current time
     * @return {@code true} if the stopover was added, otherwise {@code false}
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null || now == null) {
            return false;
        }
        if (!now.before(departureTime)) {
            return false; // cannot modify after departure
        }

        // Ensure stopover times are within flight schedule
        if (stop.getArrivalTime().before(departureTime) ||
            stop.getDepartureTime().after(arrivalTime) ||
            !stop.getArrivalTime().before(stop.getDepartureTime())) {
            return false;
        }

        // Simple overlap check with existing stopovers
        for (Stopover s : stopovers) {
            if (timesOverlap(s.getArrivalTime(), s.getDepartureTime(),
                    stop.getArrivalTime(), stop.getDepartureTime())) {
                return false;
            }
        }

        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     *
     * @param stop the stopover to remove
     * @param now  the current time
     * @return {@code true} if the stopover was removed, otherwise {@code false}
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null || now == null) {
            return false;
        }
        if (!now.before(departureTime)) {
            return false; // cannot modify after departure
        }
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     *
     * @return list of confirmed reservations (empty list if none)
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmed = new ArrayList<>();
        if (!openForBooking) {
            return confirmed;
        }
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                confirmed.add(r);
            }
        }
        return confirmed;
    }

    /* Helper method to detect overlapping intervals */
    private boolean timesOverlap(Date start1, Date end1, Date start2, Date end2) {
        return !end1.before(start2) && !end2.before(start1);
    }
}

/**
 * Represents a stopover within a flight.
 */
class Stopover {

    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    /** No‑arg constructor */
    public Stopover() {
    }

    /** Getters and Setters */
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
 * Represents an airport.
 */
class Airport {

    private String id;
    private List<City> servesForCities = new ArrayList<>();

    /** No‑arg constructor */
    public Airport() {
    }

    /** Getters and Setters */
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
 * Represents a city.
 */
class City {

    private String name;

    /** No‑arg constructor */
    public City() {
    }

    /** Constructor with name */
    public City(String name) {
        this.name = name;
    }

    /** Getters and Setters */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

/**
 * Represents a customer who can make bookings.
 */
class Customer {

    private List<Booking> bookings = new ArrayList<>();

    /** No‑arg constructor */
    public Customer() {
    }

    /** Getters and Setters */
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Creates a booking for a flight with a list of passenger names.
     *
     * @param f                     the flight to book
     * @param now                   the current time
     * @param listOfPassengerNames  list of passenger names
     * @return {@code true} if the booking was successfully created, otherwise {@code false}
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || now == null || listOfPassengerNames == null || listOfPassengerNames.isEmpty()) {
            return false;
        }
        if (!f.isOpenForBooking()) {
            return false;
        }
        if (!now.before(f.getDepartureTime())) {
            return false; // cannot book after departure
        }

        // Check for duplicate passenger names on the same flight (across all bookings of this customer)
        Set<String> existingNames = new HashSet<>();
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getFlight().equals(f)) {
                    existingNames.add(r.getPassenger().getName().toLowerCase());
                }
            }
        }
        for (String name : listOfPassengerNames) {
            if (existingNames.contains(name.toLowerCase())) {
                return false; // duplicate passenger on this flight
            }
        }

        Booking booking = new Booking(this);
        for (String name : listOfPassengerNames) {
            boolean ok = booking.createReservation(f, name, now);
            if (!ok) {
                return false; // abort if any reservation fails
            }
        }
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms an existing reservation.
     *
     * @param reservationID the reservation identifier
     * @param now           the current time
     * @return {@code true} if the reservation was confirmed, otherwise {@code false}
     */
    public boolean confirm(String reservationID, Date now) {
        Reservation r = findReservationById(reservationID);
        if (r == null) {
            return false;
        }
        Flight flight = r.getFlight();
        if (!flight.isOpenForBooking() || !now.before(flight.getDepartureTime())) {
            return false;
        }
        r.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels an existing reservation.
     *
     * @param reservationID the reservation identifier
     * @param now           the current time
     * @return {@code true} if the reservation was cancelled, otherwise {@code false}
     */
    public boolean cancel(String reservationID, Date now) {
        Reservation r = findReservationById(reservationID);
        if (r == null) {
            return false;
        }
        Flight flight = r.getFlight();
        if (!flight.isOpenForBooking() || !now.before(flight.getDepartureTime())) {
            return false;
        }
        r.setStatus(ReservationStatus.CANCELED);
        return true;
    }

    /* Helper to locate a reservation across all bookings */
    private Reservation findReservationById(String id) {
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId() != null && r.getId().equals(id)) {
                    return r;
                }
            }
        }
        return null;
    }
}

/**
 * Represents a passenger.
 */
class Passenger {

    private String name;

    /** No‑arg constructor */
    public Passenger() {
    }

    /** Constructor with name */
    public Passenger(String name) {
        this.name = name;
    }

    /** Getters and Setters */
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
    private List<Reservation> reservations = new ArrayList<>();

    /** No‑arg constructor */
    public Booking() {
    }

    /** Constructor linking to a customer */
    public Booking(Customer customer) {
        this.customer = customer;
    }

    /** Getters and Setters */
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
     * Creates a reservation for a given flight and passenger name.
     *
     * @param f         the flight for which the reservation is made
     * @param passenger the passenger name
     * @param now       the current time
     * @return {@code true} if the reservation was successfully created, otherwise {@code false}
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (f == null || passenger == null || passenger.isEmpty() || now == null) {
            return false;
        }
        if (!f.isOpenForBooking() || !now.before(f.getDepartureTime())) {
            return false;
        }

        Passenger p = new Passenger(passenger);
        String reservationId = UUID.randomUUID().toString();

        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(p);
        reservation.setFlight(f);

        // Add reservation to flight and booking
        f.getReservations().add(reservation);
        reservations.add(reservation);
        return true;
    }
}

/**
 * Enumeration of reservation statuses.
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

    /** No‑arg constructor */
    public Reservation() {
    }

    /** Getters and Setters */
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