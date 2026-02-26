import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents an airline that manages flights.
 */
class Airline {

    /** List of flights owned by the airline. */
    private List<Flight> flights;

    /** Unparameterized constructor. */
    public Airline() {
        this.flights = new ArrayList<>();
    }

    /** @return the list of flights */
    public List<Flight> getFlights() {
        return flights;
    }

    /** @param flights the flights to set */
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
     * Publishes a newly created flight so that it becomes available for booking.
     * The method validates timestamp format, temporal consistency and route integrity.
     *
     * @param f   the flight to publish
     * @param now the current time (used for validation)
     * @return true if the flight was successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || f.isOpenForBooking()) {
            return false; // already published or null
        }
        // Validate timestamps are present
        if (f.getDepartureTime() == null || f.getArrivalTime() == null) {
            return false;
        }
        // Temporal consistency: now < departure < arrival
        if (!now.before(f.getDepartureTime()) || !f.getDepartureTime().before(f.getArrivalTime())) {
            return false;
        }
        // Route integrity: departure airport must differ from arrival airport
        if (f.getDepartureAirport() == null || f.getArrialAirport() == null) {
            return false;
        }
        if (f.getDepartureAirport().getId().equals(f.getArrialAirport().getId())) {
            return false;
        }
        // Publish
        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an open flight that has not yet departed. All confirmed reservations are cancelled.
     *
     * @param flightId the unique identifier of the flight to close
     * @param now      the current time (used for validation)
     * @return true if the flight was successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight flight = findFlightById(flightId);
        if (flight == null || !flight.isOpenForBooking()) {
            return false;
        }
        // Flight must not have departed yet
        if (!now.before(flight.getDepartureTime())) {
            return false;
        }
        // Close the flight
        flight.setOpenForBooking(false);
        // Cancel all confirmed reservations
        for (Reservation r : flight.getReservations()) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELED);
            }
        }
        return true;
    }

    /**
     * Searches for flights that match the given origin, destination and departure date.
     *
     * @param origin the airport id of the origin
     * @param dest   the airport id of the destination
     * @param date   the departure date (date part only, time ignored)
     * @return list of matching flights; empty list if none match
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
        String targetDay = dayFormat.format(date);
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
            String flightDay = dayFormat.format(f.getDepartureTime());
            if (flightDay.equals(targetDay)) {
                result.add(f);
            }
        }
        return result;
    }

    /** Helper method to locate a flight by its id. */
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
 * Represents a flight with schedule, airports, stopovers and reservations.
 */
class Flight {

    private String id;
    private boolean openForBooking;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrialAirport; // note: typo kept from design model
    private List<Stopover> stopovers;
    private List<Reservation> reservations;

    /** Unparameterized constructor. */
    public Flight() {
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.openForBooking = false;
    }

    // ---------- getters & setters ----------
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

    // ---------- functional methods ----------
    /**
     * Adds a stopover to the flight after validating temporal constraints.
     *
     * @param stop the stopover to add
     * @param now  the current time (used for validation)
     * @return true if the stopover was added, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null || stop.getAirport() == null) {
            return false;
        }
        // Flight must not have departed yet
        if (!now.before(this.departureTime)) {
            return false;
        }
        // Stopover times must be within flight schedule
        if (!stop.getArrivalTime().after(this.departureTime) ||
            !stop.getDepartureTime().before(this.arrivalTime) ||
            !stop.getArrivalTime().before(stop.getDepartureTime())) {
            return false;
        }
        // Ensure no overlapping with existing stopovers
        for (Stopover existing : stopovers) {
            if (timesOverlap(existing.getArrivalTime(), existing.getDepartureTime(),
                    stop.getArrivalTime(), stop.getDepartureTime())) {
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
     * @param now  the current time (used for validation)
     * @return true if the stopover was removed, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null) {
            return false;
        }
        // Flight must not have departed yet
        if (!now.before(this.departureTime)) {
            return false;
        }
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all reservations that are in CONFIRMED status.
     *
     * @return list of confirmed reservations; empty list if none
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

    /** Helper to detect overlapping time intervals. */
    private boolean timesOverlap(Date start1, Date end1, Date start2, Date end2) {
        return !start1.after(end2) && !start2.after(end1);
    }
}

/**
 * Represents a stopover (layover) during a flight.
 */
class Stopover {

    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    /** Unparameterized constructor. */
    public Stopover() {
    }

    // getters & setters
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
    private List<City> servesForCities;

    /** Unparameterized constructor. */
    public Airport() {
        this.servesForCities = new ArrayList<>();
    }

    // getters & setters
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
 * Simple placeholder class for a city.
 */
class City {

    private String name;

    /** Unparameterized constructor. */
    public City() {
    }

    // getters & setters
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

    private List<Booking> bookings;

    /** Unparameterized constructor. */
    public Customer() {
        this.bookings = new ArrayList<>();
    }

    // getters & setters
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Adds a new booking for a flight with the supplied passenger names.
     *
     * @param f                     the flight to book
     * @param now                   the current time (used for validation)
     * @param listOfPassengerNames  list of passenger names
     * @return true if the booking was successfully created, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || listOfPassengerNames == null || listOfPassengerNames.isEmpty()) {
            return false;
        }
        // Flight must be open for booking and not departed
        if (!f.isOpenForBooking() || !now.before(f.getDepartureTime())) {
            return false;
        }
        // Check for duplicate passenger names on this flight
        Set<String> existingNames = new HashSet<>();
        for (Reservation r : f.getReservations()) {
            existingNames.add(r.getPassenger().getName().toLowerCase());
        }
        for (String name : listOfPassengerNames) {
            if (existingNames.contains(name.toLowerCase())) {
                return false; // duplicate passenger on same flight
            }
        }
        // Create booking and reservations
        Booking booking = new Booking(this);
        for (String name : listOfPassengerNames) {
            boolean ok = booking.createReservation(f, name, now);
            if (!ok) {
                // rollback partially created reservation
                return false;
            }
        }
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms a reservation identified by its id.
     *
     * @param reservationID the reservation identifier
     * @param now           the current time (used for validation)
     * @return true if the reservation was confirmed, false otherwise
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
     * Cancels a reservation identified by its id.
     *
     * @param reservationID the reservation identifier
     * @param now           the current time (used for validation)
     * @return true if the reservation was cancelled, false otherwise
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

    /** Helper to locate a reservation inside the customer's bookings. */
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

    /** Unparameterized constructor. */
    public Passenger() {
    }

    // getters & setters
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
    private List<Reservation> reservations;

    /**
     * Constructs a booking for the given customer.
     *
     * @param customer the owner of the booking
     */
    public Booking(Customer customer) {
        this.customer = customer;
        this.reservations = new ArrayList<>();
    }

    // getters & setters
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
     * Creates a reservation for a passenger on a specific flight.
     *
     * @param f             the flight to reserve a seat on
     * @param passengerName the name of the passenger
     * @param now           the current time (used for validation)
     * @return true if the reservation was created and added, false otherwise
     */
    public boolean createReservation(Flight f, String passengerName, Date now) {
        if (f == null || passengerName == null || passengerName.isEmpty()) {
            return false;
        }
        // Ensure flight is still open for booking
        if (!f.isOpenForBooking() || !now.before(f.getDepartureTime())) {
            return false;
        }
        // Create passenger and reservation objects
        Passenger p = new Passenger();
        p.setName(passengerName);
        Reservation r = new Reservation();
        r.setId(UUID.randomUUID().toString());
        r.setStatus(ReservationStatus.PENDING);
        r.setPassenger(p);
        r.setFlight(f);

        // Add to flight and booking collections
        f.getReservations().add(r);
        reservations.add(r);
        return true;
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

    /** Unparameterized constructor. */
    public Reservation() {
    }

    // getters & setters
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