import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;

/**
 * Represents an airline that manages flights.
 */
class Airline {

    private List<Flight> flights = new ArrayList<>();

    /** Unparameterized constructor */
    public Airline() {
    }

    /*-------------------- Getters & Setters --------------------*/

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    /*-------------------- Business Methods --------------------*/

    /**
     * Adds a flight to the airline's catalogue.
     *
     * @param f the flight to add
     */
    public void addFlight(Flight f) {
        if (f != null && !flights.contains(f)) {
            flights.add(f);
        }
    }

    /**
     * Removes a flight from the airline's catalogue.
     *
     * @param f the flight to remove
     */
    public void removeFlight(Flight f) {
        flights.remove(f);
    }

    /**
     * Publishes a flight for booking.
     * <p>
     * The method validates:
     * <ul>
     *   <li>departure and arrival timestamps are non‑null</li>
     *   <li>current time &lt; departure &lt; arrival</li>
     *   <li>departure airport differs from arrival airport</li>
     *   <li>the flight has not been published before</li>
     * </ul>
     *
     * @param f   the flight to publish
     * @param now the current time
     * @return {@code true} if publishing succeeds, {@code false} otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || now == null) {
            return false;
        }
        if (f.isOpenForBooking()) {
            // already published
            return false;
        }
        Date dep = f.getDepartureTime();
        Date arr = f.getArrivalTime();
        if (dep == null || arr == null) {
            return false;
        }
        if (!now.before(dep) || !dep.before(arr)) {
            return false;
        }
        if (f.getDepartureAirport() == null || f.getArrivalAirport() == null) {
            return false;
        }
        if (f.getDepartureAirport().equals(f.getArrivalAirport())) {
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
     * @return {@code true} if the flight was successfully closed, {@code false} otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight f = findFlightById(flightId);
        if (f == null || !f.isOpenForBooking()) {
            return false;
        }
        if (!now.before(f.getDepartureTime())) {
            return false; // already departed
        }
        // cancel every confirmed reservation
        for (Reservation r : f.getReservations()) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELED);
            }
        }
        f.setOpenForBooking(false);
        return true;
    }

    /**
     * Searches flights that match the given origin, destination and departure date.
     *
     * @param origin   the departure airport id
     * @param dest     the arrival airport id
     * @param date     the date (day) of departure (time part is ignored)
     * @return a list of matching flights (may be empty)
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        SimpleDateFormat dayFmt = new SimpleDateFormat("yyyy-MM-dd");
        String targetDay = dayFmt.format(date);
        for (Flight f : flights) {
            if (f.getDepartureAirport() == null || f.getArrivalAirport() == null) {
                continue;
            }
            if (!f.getDepartureAirport().getId().equals(origin)) {
                continue;
            }
            if (!f.getArrivalAirport().getId().equals(dest)) {
                continue;
            }
            if (dayFmt.format(f.getDepartureTime()).equals(targetDay)) {
                result.add(f);
            }
        }
        return result;
    }

    /*-------------------- Helper --------------------*/

    private Flight findFlightById(String flightId) {
        for (Flight f : flights) {
            if (flightId != null && flightId.equals(f.getId())) {
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

    private static final AtomicInteger RES_ID_GEN = new AtomicInteger(0);

    private String id;
    private boolean openForBooking = false;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private List<Stopover> stopovers = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    /** Unparameterized constructor */
    public Flight() {
    }

    /*-------------------- Getters & Setters --------------------*/

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

    /*-------------------- Business Methods --------------------*/

    /**
     * Adds a stopover to this flight.
     *
     * @param stop the stopover to add
     * @param now  the current time
     * @return {@code true} if the stopover was added, {@code false} otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null || now == null) {
            return false;
        }
        if (!now.before(departureTime)) {
            return false; // flight already departed
        }
        // stop times must be within flight schedule
        if (stop.getArrivalTime().before(departureTime) ||
                stop.getDepartureTime().after(arrivalTime) ||
                !stop.getArrivalTime().before(stop.getDepartureTime())) {
            return false;
        }
        // airport must serve at least one city
        if (stop.getAirport() == null || stop.getAirport().getServesForCities().isEmpty()) {
            return false;
        }
        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from this flight.
     *
     * @param stop the stopover to remove
     * @param now  the current time
     * @return {@code true} if the stopover was removed, {@code false} otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null || now == null) {
            return false;
        }
        if (!now.before(departureTime)) {
            return false; // flight already departed
        }
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     * The method returns an empty list if the flight is closed for booking.
     *
     * @return a list of confirmed reservations (may be empty)
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

    /**
     * Creates a reservation for a passenger on this flight.
     *
     * @param passengerName name of the passenger
     * @return the newly created reservation, or {@code null} if creation failed
     */
    public Reservation createReservation(String passengerName) {
        if (passengerName == null || passengerName.isEmpty()) {
            return null;
        }
        Passenger p = new Passenger();
        p.setName(passengerName);
        Reservation r = new Reservation();
        r.setId("R" + RES_ID_GEN.incrementAndGet());
        r.setPassenger(p);
        r.setFlight(this);
        r.setStatus(ReservationStatus.PENDING);
        reservations.add(r);
        return r;
    }

    /**
     * Checks whether a passenger (by name) already has a reservation on this flight.
     *
     * @param passengerName name of the passenger
     * @return {@code true} if a reservation exists, {@code false} otherwise
     */
    public boolean hasReservationForPassenger(String passengerName) {
        for (Reservation r : reservations) {
            if (r.getPassenger() != null && passengerName.equals(r.getPassenger().getName())) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Represents a stopover.
 */
class Stopover {

    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    /** Unparameterized constructor */
    public Stopover() {
    }

    /*-------------------- Getters & Setters --------------------*/

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

    /** Unparameterized constructor */
    public Airport() {
    }

    /*-------------------- Getters & Setters --------------------*/

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

    /** Unparameterized constructor */
    public City() {
    }

    /*-------------------- Getters & Setters --------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a customer that can make bookings.
 */
class Customer {

    private List<Booking> bookings = new ArrayList<>();

    /** Unparameterized constructor */
    public Customer() {
    }

    /*-------------------- Getters & Setters --------------------*/

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Creates a booking for a flight with the given passenger names.
     *
     * @param flight               the flight to book
     * @param now                  the current time
     * @param listOfPassengerNames list of passenger names
     * @return {@code true} if the booking was created, {@code false} otherwise
     */
    public boolean addBooking(Flight flight, Date now, List<String> listOfPassengerNames) {
        if (flight == null || now == null || listOfPassengerNames == null) {
            return false;
        }
        if (!flight.isOpenForBooking()) {
            return false;
        }
        if (!now.before(flight.getDepartureTime())) {
            return false; // flight already departed
        }
        // Check duplicate passenger names within the request
        Set<String> uniqueNames = new HashSet<>(listOfPassengerNames);
        if (uniqueNames.size() != listOfPassengerNames.size()) {
            return false; // duplicates in request
        }
        // Check for existing reservations for any of these passengers on the same flight
        for (String name : listOfPassengerNames) {
            if (flight.hasReservationForPassenger(name)) {
                return false;
            }
        }
        Booking booking = new Booking();
        booking.setCustomer(this);
        for (String name : listOfPassengerNames) {
            boolean created = booking.createReservation(flight, name, now);
            if (!created) {
                return false; // should not happen, but abort on failure
            }
        }
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms a reservation identified by its ID.
     *
     * @param reservationID the reservation identifier
     * @param now           the current time
     * @return {@code true} if the reservation was confirmed, {@code false} otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        Reservation r = findReservationById(reservationID);
        if (r == null) {
            return false;
        }
        Flight f = r.getFlight();
        if (f == null || !f.isOpenForBooking() || now.after(f.getDepartureTime())) {
            return false;
        }
        r.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels a reservation identified by its ID.
     *
     * @param reservationID the reservation identifier
     * @param now           the current time
     * @return {@code true} if the reservation was cancelled, {@code false} otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        Reservation r = findReservationById(reservationID);
        if (r == null) {
            return false;
        }
        Flight f = r.getFlight();
        if (f == null || now.after(f.getDepartureTime())) {
            return false;
        }
        r.setStatus(ReservationStatus.CANCELED);
        return true;
    }

    /*-------------------- Helper --------------------*/

    private Reservation findReservationById(String reservationID) {
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (reservationID != null && reservationID.equals(r.getId())) {
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

    /** Unparameterized constructor */
    public Passenger() {
    }

    /*-------------------- Getters & Setters --------------------*/

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

    private static final AtomicInteger RES_ID_GEN = new AtomicInteger(0);

    private Customer customer;
    private List<Reservation> reservations = new ArrayList<>();

    /** Unparameterized constructor */
    public Booking() {
    }

    /*-------------------- Getters & Setters --------------------*/

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
     * Creates a reservation for a passenger on a given flight.
     *
     * @param f          the flight
     * @param passenger  passenger name
     * @param now        current time (used for possible future checks)
     * @return {@code true} if the reservation was created, {@code false} otherwise
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (f == null || passenger == null || now == null) {
            return false;
        }
        Reservation reservation = f.createReservation(passenger);
        if (reservation == null) {
            return false;
        }
        reservations.add(reservation);
        return true;
    }
}

/**
 * Represents a reservation.
 */
class Reservation {

    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    /** Unparameterized constructor */
    public Reservation() {
    }

    /*-------------------- Getters & Setters --------------------*/

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
 * Enumeration of reservation statuses.
 */
enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELED
}