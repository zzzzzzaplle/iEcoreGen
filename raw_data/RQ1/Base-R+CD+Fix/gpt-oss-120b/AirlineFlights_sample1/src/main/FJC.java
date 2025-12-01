import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.UUID;

/**
 * Represents an airline that manages a collection of flights.
 */
class Airline {

    private List<Flight> flights = new ArrayList<>();

    /** Default constructor */
    public Airline() {
    }

    /** Getter for flights */
    public List<Flight> getFlights() {
        return flights;
    }

    /** Setter for flights */
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
     * Publishes a newly created flight for booking.
     *
     * <p>The method checks:
     * <ul>
     *   <li>that departure and arrival timestamps are non‑null and follow the
     *       {@code yyyy-MM-dd HH:mm:ss} pattern,</li>
     *   <li>temporal consistency: {@code now < departure < arrival},</li>
     *   <li>route integrity: departure airport differs from arrival airport,</li>
     *   <li>the flight has not been published before, and it is still open for booking.</li>
     * </ul>
     *
     * @param f   the flight to publish
     * @param now the current time
     * @return {@code true} if the flight was successfully published; {@code false} otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || now == null) {
            return false;
        }
        // Validate date format (the flight already stores Date objects; we only ensure they are non‑null)
        if (f.getDepartureTime() == null || f.getArrivalTime() == null) {
            return false;
        }
        // Temporal consistency
        if (!now.before(f.getDepartureTime()) || !f.getDepartureTime().before(f.getArrivalTime())) {
            return false;
        }
        // Route integrity
        if (f.getDepartureAirport() == null || f.getArrivalAirport() == null) {
            return false;
        }
        if (f.getDepartureAirport().getId().equals(f.getArrivalAirport().getId())) {
            return false;
        }
        // Publish only once
        if (f.isOpenForBooking()) {
            return false; // already published
        }
        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an open flight that has not yet departed.
     *
     * @param flightId the unique identifier of the flight to close
     * @param now      the current time
     * @return {@code true} if the flight was successfully closed; {@code false} otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight f = findFlightById(flightId);
        if (f == null) {
            return false;
        }
        if (!f.isOpenForBooking()) {
            return false; // already closed
        }
        // Ensure flight has not departed yet
        if (!now.before(f.getDepartureTime())) {
            return false;
        }
        // Change status
        f.setOpenForBooking(false);
        // Cancel every confirmed reservation
        for (Reservation r : f.getReservations()) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELED);
            }
        }
        return true;
    }

    /**
     * Searches for flights matching the given origin airport id, destination airport id,
     * and departure date (day part only). Only flights that are currently open for booking
     * are returned.
     *
     * @param origin the id of the departure airport
     * @param dest   the id of the arrival airport
     * @param date   the date (day) of departure (time component is ignored)
     * @return a list of matching flights; possibly empty
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        if (origin == null || dest == null || date == null) {
            return result;
        }
        Calendar calTarget = Calendar.getInstance();
        calTarget.setTime(date);
        for (Flight f : flights) {
            if (!f.isOpenForBooking()) {
                continue;
            }
            if (!origin.equals(f.getDepartureAirport().getId())) {
                continue;
            }
            if (!dest.equals(f.getArrivalAirport().getId())) {
                continue;
            }
            Calendar calDep = Calendar.getInstance();
            calDep.setTime(f.getDepartureTime());
            if (calDep.get(Calendar.YEAR) == calTarget.get(Calendar.YEAR)
                    && calDep.get(Calendar.MONTH) == calTarget.get(Calendar.MONTH)
                    && calDep.get(Calendar.DAY_OF_MONTH) == calTarget.get(Calendar.DAY_OF_MONTH)) {
                result.add(f);
            }
        }
        return result;
    }

    /**
     * Helper method to locate a flight by its unique identifier.
     *
     * @param flightId the flight identifier
     * @return the matching {@code Flight} or {@code null} if not found
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
 * Represents a flight with schedule, airports, stopovers and reservations.
 */
class Flight {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    private String id;
    private boolean openForBooking = false;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private List<Stopover> stopovers = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    /** Default constructor – generates a unique flight id. */
    public Flight() {
        this.id = "FL-" + ID_GENERATOR.getAndIncrement();
    }

    /** Getters and setters */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        // id is normally generated; allow setting only for deserialization
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
     * Adds a stopover to the flight before departure.
     *
     * @param stop the stopover to add
     * @param now  the current time
     * @return {@code true} if the stopover was added; {@code false} otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null || now == null) {
            return false;
        }
        // Flight must not have departed yet
        if (!now.before(this.departureTime)) {
            return false;
        }
        // Stopover times must be within flight schedule
        if (stop.getArrivalTime().before(this.departureTime)
                || stop.getDepartureTime().after(this.arrivalTime)
                || !stop.getArrivalTime().before(stop.getDepartureTime())) {
            return false;
        }
        // Ensure stopover airport serves at least one city
        if (stop.getAirport() == null || stop.getAirport().getServesForCities().isEmpty()) {
            return false;
        }
        // Avoid duplicate stopover at same airport and time
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
     * @param now  the current time
     * @return {@code true} if the stopover was removed; {@code false} otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null || now == null) {
            return false;
        }
        if (!now.before(this.departureTime)) {
            return false;
        }
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all reservations that are in {@link ReservationStatus#CONFIRMED}
     * for this flight while it is still open for booking.
     *
     * @return list of confirmed reservations; may be empty
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmed = new ArrayList<>();
        if (!this.openForBooking) {
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
     * Checks whether the flight has already departed with respect to a given time.
     *
     * @param now the current time
     * @return {@code true} if the flight has departed; {@code false} otherwise
     */
    public boolean hasDeparted(Date now) {
        return now.after(this.departureTime);
    }
}

/**
 * Represents a stopover (layover) on a flight.
 */
class Stopover {

    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    /** Default constructor */
    public Stopover() {
    }

    /** Getters and setters */
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
 * Represents an airport serving one or more cities.
 */
class Airport {

    private String id;
    private List<City> servesForCities = new ArrayList<>();

    /** Default constructor */
    public Airport() {
    }

    /** Getters and setters */
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

    /** Default constructor */
    public City() {
    }

    /** Getter and setter */
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

    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    private String id;
    private List<Booking> bookings = new ArrayList<>();

    /** Default constructor – generates a unique customer id. */
    public Customer() {
        this.id = "CU-" + ID_GENERATOR.getAndIncrement();
    }

    /** Getters and setters */
    public String getId() {
        return id;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Creates a booking for a flight with a list of passenger names.
     *
     * <p>The method validates:
     * <ul>
     *   <li>the flight is open for booking,</li>
     *   <li>the current time is before the flight's departure,</li>
     *   <li>no duplicate passenger names exist on the same flight.</li>
     * </ul>
     *
     * @param f                     the flight to book
     * @param now                   the current time
     * @param listOfPassengerNames  list of passenger names
     * @return {@code true} if the booking was created successfully; {@code false} otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || now == null || listOfPassengerNames == null || listOfPassengerNames.isEmpty()) {
            return false;
        }
        if (!f.isOpenForBooking() || !now.before(f.getDepartureTime())) {
            return false;
        }
        // Check for duplicate passenger names on this flight (across all existing reservations)
        Set<String> existingNames = new HashSet<>();
        for (Reservation r : f.getReservations()) {
            existingNames.add(r.getPassenger().getName());
        }
        for (String name : listOfPassengerNames) {
            if (existingNames.contains(name)) {
                return false; // duplicate passenger on same flight
            }
        }

        Booking booking = new Booking(this);
        for (String name : listOfPassengerNames) {
            boolean ok = booking.createReservation(f, name, now);
            if (!ok) {
                // rollback partially created reservations
                for (Reservation r : booking.getReservations()) {
                    f.getReservations().remove(r);
                }
                return false;
            }
        }
        this.bookings.add(booking);
        return true;
    }

    /**
     * Confirms a reservation identified by its id.
     *
     * @param reservationID the reservation identifier
     * @param now           the current time
     * @return {@code true} if the reservation was successfully confirmed; {@code false} otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        Reservation r = findReservationById(reservationID);
        if (r == null) {
            return false;
        }
        // Flight must not have departed and must be open for booking
        Flight f = r.getFlight();
        if (!f.isOpenForBooking() || f.hasDeparted(now)) {
            return false;
        }
        r.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels a reservation identified by its id.
     *
     * @param reservationID the reservation identifier
     * @param now           the current time
     * @return {@code true} if the reservation was successfully cancelled; {@code false} otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        Reservation r = findReservationById(reservationID);
        if (r == null) {
            return false;
        }
        // Flight must not have departed and must be open for booking
        Flight f = r.getFlight();
        if (!f.isOpenForBooking() || f.hasDeparted(now)) {
            return false;
        }
        r.setStatus(ReservationStatus.CANCELED);
        return true;
    }

    /**
     * Helper method to locate a reservation across all bookings of this customer.
     *
     * @param reservationID the reservation identifier
     * @return the matching {@code Reservation} or {@code null}
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

/**
 * Represents a passenger.
 */
class Passenger {

    private String name;

    /** Default constructor */
    public Passenger() {
    }

    /** Getter and setter */
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

    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    private String id;
    private Customer customer;
    private List<Reservation> reservations = new ArrayList<>();

    /** Default constructor – generates a unique booking id. */
    public Booking() {
        this.id = "BK-" + ID_GENERATOR.getAndIncrement();
    }

    /** Constructor linking booking to a customer. */
    public Booking(Customer customer) {
        this();
        this.customer = customer;
    }

    /** Getters and setters */
    public String getId() {
        return id;
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
     * Creates a reservation for a given flight and passenger name.
     *
     * @param f         the flight to reserve a seat on
     * @param passenger the passenger name
     * @param now       the current time
     * @return {@code true} if the reservation was successfully created; {@code false} otherwise
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (f == null || passenger == null || now == null) {
            return false;
        }
        // Ensure flight is still open and not departed
        if (!f.isOpenForBooking() || f.hasDeparted(now)) {
            return false;
        }
        // Create passenger and reservation objects
        Passenger p = new Passenger();
        p.setName(passenger);

        Reservation r = new Reservation();
        r.setId(generateReservationId());
        r.setPassenger(p);
        r.setFlight(f);
        r.setStatus(ReservationStatus.PENDING);

        // Add reservation to flight and booking
        f.getReservations().add(r);
        this.reservations.add(r);
        return true;
    }

    /**
     * Generates a unique reservation identifier.
     *
     * @return a unique reservation id string
     */
    private String generateReservationId() {
        return "RS-" + UUID.randomUUID().toString();
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
 * Represents a reservation linking a passenger to a flight.
 */
class Reservation {

    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    /** Default constructor */
    public Reservation() {
    }

    /** Getters and setters */
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