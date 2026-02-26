import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents an airline that manages a collection of flights.
 */
 class Airline {

    private List<Flight> flights = new ArrayList<>();

    /** No‑arg constructor */
    public Airline() {
    }

    /* ---------- Getters & Setters ---------- */
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
     * Publishes a newly created flight for booking.
     * <p>
     * The method validates that the flight has non‑null departure and arrival
     * timestamps, that they are in the correct chronological order,
     * that the departure and arrival airports differ, that the current time is
     * before the departure, and that the flight has not been published before.
     *
     * @param f   the flight to publish
     * @param now the current time
     * @return {@code true} if the flight was successfully published; {@code false}
     * otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || now == null) {
            return false;
        }
        // flight must not be already open
        if (f.isOpenForBooking()) {
            return false;
        }
        // timestamps must be present
        if (f.getDepartureTime() == null || f.getArrivalTime() == null) {
            return false;
        }
        // temporal consistency
        if (now.after(f.getDepartureTime()) || !f.getDepartureTime().before(f.getArrivalTime())) {
            return false;
        }
        // route integrity
        if (f.getDepartureAirport() == null || f.getArrialAirport() == null) {
            return false;
        }
        if (f.getDepartureAirport().getId().equals(f.getArrialAirport().getId())) {
            return false;
        }
        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an open flight that has not yet departed.
     *
     * @param flightId the unique identifier of the flight to close
     * @param now      the current time
     * @return {@code true} if the flight was closed successfully; {@code false}
     * otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight flight = findFlightById(flightId);
        if (flight == null) {
            return false;
        }
        if (!flight.isOpenForBooking()) {
            return false;
        }
        // flight must not have departed yet
        if (now.after(flight.getDepartureTime())) {
            return false;
        }
        // cancel every confirmed reservation
        for (Reservation r : flight.getReservations()) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELLED);
            }
        }
        flight.setOpenForBooking(false);
        return true;
    }

    /**
     * Searches for flights that match the given origin, destination and date.
     *
     * @param origin      the IATA code (or id) of the departure airport
     * @param dest        the IATA code (or id) of the arrival airport
     * @param date        the date (ignoring time) to match the departure day
     * @return list of matching flights (empty list if none)
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        if (origin == null || dest == null || date == null) {
            return Collections.emptyList();
        }
        List<Flight> result = new ArrayList<>();
        SimpleDateFormat dayFmt = new SimpleDateFormat("yyyy-MM-dd");
        String targetDay = dayFmt.format(date);

        for (Flight f : flights) {
            if (!f.isOpenForBooking()) {
                continue;
            }
            if (f.getDepartureAirport() == null || f.getArrialAirport() == null) {
                continue;
            }
            if (!origin.equals(f.getDepartureAirport().getId())) {
                continue;
            }
            if (!dest.equals(f.getArrialAirport().getId())) {
                continue;
            }
            String flightDay = dayFmt.format(f.getDepartureTime());
            if (targetDay.equals(flightDay)) {
                result.add(f);
            }
        }
        return result;
    }

    /* ---------- Helper ---------- */
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

    private String id;
    private boolean openForBooking;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrialAirport; // typo kept from design model
    private List<Stopover> stopovers = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    /** No‑arg constructor */
    public Flight() {
    }

    /* ---------- Getters & Setters ---------- */
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
        // flight must not have departed
        if (now.after(this.departureTime)) {
            return false;
        }
        // stopover times must be within overall schedule
        if (stop.getArrivalTime().before(this.departureTime) ||
                stop.getDepartureTime().after(this.arrivalTime) ||
                !stop.getArrivalTime().before(stop.getDepartureTime())) {
            return false;
        }
        // airport must serve at least one city (simple check)
        if (stop.getAirport() == null || stop.getAirport().getServesForCities().isEmpty()) {
            return false;
        }
        // avoid duplicate stopovers at same airport and time
        for (Stopover s : stopovers) {
            if (s.getAirport().getId().equals(stop.getAirport().getId()) &&
                    s.getArrivalTime().equals(stop.getArrivalTime()) &&
                    s.getDepartureTime().equals(stop.getDepartureTime())) {
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
        // cannot modify after departure
        if (now.after(this.departureTime)) {
            return false;
        }
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight while it is still open.
     *
     * @return list of confirmed reservations (empty list if none or flight closed)
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
     * Finds a reservation belonging to this flight by its identifier.
     *
     * @param reservationId the reservation identifier
     * @return the reservation if found; {@code null} otherwise
     */
    public Reservation findReservationById(String reservationId) {
        for (Reservation r : reservations) {
            if (reservationId.equals(r.getId())) {
                return r;
            }
        }
        return null;
    }
}

/**
 * Represents a stopover point within a flight.
 */
class Stopover {

    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    /** No‑arg constructor */
    public Stopover() {
    }

    /* ---------- Getters & Setters ---------- */
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
 * Represents an airport identified by a unique code and serving one or more cities.
 */
class Airport {

    private String id;
    private List<City> servesForCities = new ArrayList<>();

    /** No‑arg constructor */
    public Airport() {
    }

    /* ---------- Getters & Setters ---------- */
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
 * Simple representation of a city.
 */
class City {

    private String name;

    /** No‑arg constructor */
    public City() {
    }

    /* ---------- Getters & Setters ---------- */
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

    /* ---------- Getters & Setters ---------- */
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Creates a booking for a given flight with a list of passenger names.
     *
     * @param f                     the flight to book
     * @param now                   the current time
     * @param listOfPassengerNames  list of passenger names
     * @return {@code true} if the booking was created successfully; {@code false}
     * otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || now == null || listOfPassengerNames == null) {
            return false;
        }
        // flight must be open for booking and not departed
        if (!f.isOpenForBooking() || now.after(f.getDepartureTime())) {
            return false;
        }
        // ensure no duplicate passenger names in the request
        List<String> uniqueNames = new ArrayList<>();
        for (String name : listOfPassengerNames) {
            if (name == null) {
                return false;
            }
            if (uniqueNames.contains(name)) {
                return false; // duplicate in request
            }
            uniqueNames.add(name);
        }
        // ensure no duplicate passenger on the same flight (across existing reservations)
        for (String name : uniqueNames) {
            for (Reservation r : f.getReservations()) {
                if (r.getPassenger().getName().equals(name)) {
                    return false; // passenger already booked on this flight
                }
            }
        }
        Booking booking = new Booking();
        booking.setCustomer(this);
        // create a reservation for each passenger (status = PENDING)
        for (String name : uniqueNames) {
            boolean created = booking.createReservation(f, name, now);
            if (!created) {
                return false; // should not happen, but abort if any failure
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
     * @return {@code true} if the reservation was confirmed; {@code false}
     * otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        Reservation reservation = findReservationById(reservationID);
        if (reservation == null) {
            return false;
        }
        Flight flight = reservation.getFlight();
        if (!flight.isOpenForBooking() || now.after(flight.getDepartureTime())) {
            return false;
        }
        if (reservation.getStatus() != ReservationStatus.PENDING) {
            return false;
        }
        reservation.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels a reservation identified by its ID.
     *
     * @param reservationID the reservation identifier
     * @param now           the current time
     * @return {@code true} if the reservation was cancelled; {@code false}
     * otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        Reservation reservation = findReservationById(reservationID);
        if (reservation == null) {
            return false;
        }
        Flight flight = reservation.getFlight();
        if (!flight.isOpenForBooking() || now.after(flight.getDepartureTime())) {
            return false;
        }
        // can cancel pending or confirmed reservations
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            return false;
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        return true;
    }

    /* ---------- Helper ---------- */
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
 * Represents a passenger identified by a name.
 */
class Passenger {

    private String name;

    /** No‑arg constructor */
    public Passenger() {
    }

    public Passenger(String name) {
        this.name = name;
    }

    /* ---------- Getters & Setters ---------- */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

/**
 * Represents a booking made by a customer. It holds a set of reservations.
 */
class Booking {

    private Customer customer;
    private List<Reservation> reservations = new ArrayList<>();

    /** No‑arg constructor */
    public Booking() {
    }

    /* ---------- Getters & Setters ---------- */
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
     * Creates a reservation for a given flight and passenger.
     *
     * @param f          the flight on which to reserve a seat
     * @param passengerName the name of the passenger
     * @param now        the current time
     * @return {@code true} if the reservation was created; {@code false} otherwise
     */
    public boolean createReservation(Flight f, String passengerName, Date now) {
        if (f == null || passengerName == null || now == null) {
            return false;
        }
        // flight must be open and not departed
        if (!f.isOpenForBooking() || now.after(f.getDepartureTime())) {
            return false;
        }
        // create passenger and reservation objects
        Passenger passenger = new Passenger(passengerName);
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());
        reservation.setPassenger(passenger);
        reservation.setFlight(f);
        reservation.setStatus(ReservationStatus.PENDING);
        // link reservation to flight and booking
        f.getReservations().add(reservation);
        reservations.add(reservation);
        return true;
    }
}

/**
 * Enumeration of possible reservation statuses.
 */
enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELLED
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

    /* ---------- Getters & Setters ---------- */
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

    /**
     * Updates the status of this reservation.
     *
     * @param s the new status
     */
    public void setStatus(ReservationStatus s) {
        this.status = s;
    }
}