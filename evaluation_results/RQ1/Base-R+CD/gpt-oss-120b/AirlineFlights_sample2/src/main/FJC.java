import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Calendar;

/**
 * Represents an airline that manages a collection of flights.
 */
 class Airline {

    /** List of flights owned by the airline. */
    private List<Flight> flights;

    /** No‑argument constructor. */
    public Airline() {
        this.flights = new ArrayList<>();
    }

    /** @return the list of flights (modifiable). */
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
     *
     * @param f   the flight to publish
     * @param now the current time
     * @return {@code true} if the flight was successfully published; {@code false} otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || now == null) {
            return false;
        }
        // flight must not already be open for booking
        if (f.isOpenForBooking()) {
            return false;
        }
        Date dep = f.getDepartureTime();
        Date arr = f.getArrivalTime();
        if (dep == null || arr == null) {
            return false;
        }
        // temporal consistency: now < departure < arrival
        if (!now.before(dep) || !dep.before(arr)) {
            return false;
        }
        // route integrity: departure airport must differ from arrival airport
        Airport depA = f.getDepartureAirport();
        Airport arrA = f.getArrivalAirport();
        if (depA == null || arrA == null || depA.getId().equals(arrA.getId())) {
            return false;
        }
        // publish
        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an open flight that has not yet departed. All confirmed reservations are cancelled.
     *
     * @param flightId the identifier of the flight to close
     * @param now      the current time
     * @return {@code true} if the flight was successfully closed; {@code false} otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        if (flightId == null || now == null) {
            return false;
        }
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
        // close flight
        flight.setOpenForBooking(false);
        // cancel confirmed reservations
        for (Reservation r : flight.getReservations()) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELLED);
            }
        }
        return true;
    }

    /**
     * Searches for flights that match a given origin airport, destination airport and departure date.
     *
     * @param origin the id of the departure airport
     * @param dest   the id of the arrival airport
     * @param date   the date (day) of departure (time component is ignored)
     * @return a list of matching flights; empty list if none match
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        if (origin == null || dest == null || date == null) {
            return Collections.emptyList();
        }
        List<Flight> result = new ArrayList<>();
        Calendar targetCal = Calendar.getInstance();
        targetCal.setTime(date);
        for (Flight f : flights) {
            Airport depA = f.getDepartureAirport();
            Airport arrA = f.getArrivalAirport();
            if (depA == null || arrA == null) {
                continue;
            }
            if (!origin.equals(depA.getId()) || !dest.equals(arrA.getId())) {
                continue;
            }
            Calendar flightCal = Calendar.getInstance();
            flightCal.setTime(f.getDepartureTime());
            if (flightCal.get(Calendar.YEAR) == targetCal.get(Calendar.YEAR)
                    && flightCal.get(Calendar.MONTH) == targetCal.get(Calendar.MONTH)
                    && flightCal.get(Calendar.DAY_OF_MONTH) == targetCal.get(Calendar.DAY_OF_MONTH)) {
                result.add(f);
            }
        }
        return result;
    }

    /** Helper method to locate a flight by its id. */
    private Flight findFlightById(String id) {
        for (Flight f : flights) {
            if (id.equals(f.getId())) {
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
    private Airport arrivalAirport;
    private List<Stopover> stopovers;
    private List<Reservation> reservations;

    /** No‑argument constructor. */
    public Flight() {
        this.id = UUID.randomUUID().toString();
        this.openForBooking = false;
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    /** @return the unique identifier */
    public String getId() {
        return id;
    }

    /** @param id the id to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return true if the flight is currently open for booking */
    public boolean isOpenForBooking() {
        return openForBooking;
    }

    /** @param openForBooking the open flag to set */
    public void setOpenForBooking(boolean openForBooking) {
        this.openForBooking = openForBooking;
    }

    /** @return the scheduled departure time */
    public Date getDepartureTime() {
        return departureTime;
    }

    /** @param departureTime the departure time to set */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /** @return the scheduled arrival time */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /** @param arrivalTime the arrival time to set */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    /** @return the list of stopovers */
    public List<Stopover> getStopovers() {
        return stopovers;
    }

    /** @param stopovers the stopovers to set */
    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    /** @return the list of reservations */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /** @param reservations the reservations to set */
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
        if (!now.before(this.departureTime)) {
            return false; // cannot modify after departure
        }
        // stopover must be within the overall schedule
        if (stop.getArrivalTime().before(this.departureTime) ||
                stop.getDepartureTime().after(this.arrivalTime) ||
                !stop.getArrivalTime().before(stop.getDepartureTime())) {
            return false;
        }
        // ensure no overlapping stopover times
        for (Stopover existing : stopovers) {
            if (timesOverlap(existing, stop)) {
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
     * Retrieves all confirmed reservations for this flight while it is still open for booking.
     *
     * @return list of confirmed reservations; empty list if none
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

    /** Helper to detect overlapping stopover periods. */
    private boolean timesOverlap(Stopover a, Stopover b) {
        return !(a.getDepartureTime().before(b.getArrivalTime()) ||
                 a.getArrivalTime().after(b.getDepartureTime()));
    }
}

/**
 * Represents a stopover during a flight.
 */
class Stopover {

    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    /** No‑argument constructor. */
    public Stopover() {
    }

    /** @return the stopover departure time */
    public Date getDepartureTime() {
        return departureTime;
    }

    /** @param departureTime the departure time to set */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /** @return the stopover arrival time */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /** @param arrivalTime the arrival time to set */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /** @return the airport of the stopover */
    public Airport getAirport() {
        return airport;
    }

    /** @param airport the airport to set */
    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}

/**
 * Represents an airport that serves one or more cities.
 */
class Airport {

    private String id;
    private List<City> servesForCities;

    /** No‑argument constructor. */
    public Airport() {
        this.servesForCities = new ArrayList<>();
    }

    /** @return the unique identifier of the airport */
    public String getId() {
        return id;
    }

    /** @param id the id to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the list of cities served */
    public List<City> getServesForCities() {
        return servesForCities;
    }

    /** @param servesForCities the cities list to set */
    public void setServesForCities(List<City> servesForCities) {
        this.servesForCities = servesForCities;
    }

    /**
     * Adds a city to the list of served cities.
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

    /** No‑argument constructor. */
    public City() {
    }

    /** @return the city name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a customer who can create bookings.
 */
class Customer {

    private List<Booking> bookings;

    /** No‑argument constructor. */
    public Customer() {
        this.bookings = new ArrayList<>();
    }

    /** @return the list of bookings */
    public List<Booking> getBookings() {
        return bookings;
    }

    /** @param bookings the bookings to set */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Creates a booking for a flight with a list of passenger names.
     *
     * @param f                 the flight to book
     * @param now               the current time
     * @param passengerNames    list of passenger names
     * @return {@code true} if the booking was successfully created; {@code false} otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> passengerNames) {
        if (f == null || now == null || passengerNames == null || passengerNames.isEmpty()) {
            return false;
        }
        if (!f.isOpenForBooking()) {
            return false;
        }
        if (!now.before(f.getDepartureTime())) {
            return false; // flight already departed
        }
        // ensure no duplicate passenger names in this booking request
        List<String> lowerNames = new ArrayList<>();
        for (String n : passengerNames) {
            if (n == null) continue;
            String low = n.toLowerCase();
            if (lowerNames.contains(low)) {
                return false; // duplicate in request
            }
            lowerNames.add(low);
        }
        // ensure passengers are not already booked on this flight (across all bookings)
        for (String name : passengerNames) {
            for (Reservation r : f.getReservations()) {
                if (r.getPassenger().getName().equalsIgnoreCase(name)) {
                    return false; // duplicate passenger on same flight
                }
            }
        }
        // create booking and reservations
        Booking booking = new Booking(this);
        for (String name : passengerNames) {
            boolean ok = booking.createReservation(f, name, now);
            if (!ok) {
                return false; // should not happen, but abort if any reservation fails
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
     * @return {@code true} if the reservation was confirmed; {@code false} otherwise
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
        if (r.getStatus() != ReservationStatus.PENDING) {
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
     * @return {@code true} if the reservation was cancelled; {@code false} otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        Reservation r = findReservationById(reservationID);
        if (r == null) {
            return false;
        }
        Flight f = r.getFlight();
        if (f == null || !f.isOpenForBooking() || now.after(f.getDepartureTime())) {
            return false;
        }
        if (r.getStatus() == ReservationStatus.CANCELLED) {
            return false;
        }
        r.setStatus(ReservationStatus.CANCELLED);
        return true;
    }

    /** Helper to locate a reservation belonging to this customer. */
    private Reservation findReservationById(String id) {
        if (id == null) {
            return null;
        }
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (id.equals(r.getId())) {
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

    /** No‑argument constructor. */
    public Passenger() {
    }

    /** @return the passenger's name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
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

    /** Constructor that links the booking to a customer. */
    public Booking(Customer customer) {
        this.customer = customer;
        this.reservations = new ArrayList<>();
    }

    /** @return the customer who made the booking */
    public Customer getCustomer() {
        return customer;
    }

    /** @param customer the customer to set */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /** @return the list of reservations */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /** @param reservations the reservations to set */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Creates a reservation for a passenger on a given flight.
     *
     * @param f          the flight to reserve
     * @param passengerName the passenger name
     * @param now        the current time
     * @return {@code true} if the reservation was created; {@code false} otherwise
     */
    public boolean createReservation(Flight f, String passengerName, Date now) {
        if (f == null || passengerName == null || now == null) {
            return false;
        }
        if (!f.isOpenForBooking() || now.after(f.getDepartureTime())) {
            return false;
        }
        Passenger passenger = new Passenger();
        passenger.setName(passengerName);
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(f);
        // add to flight and booking
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

    /** No‑argument constructor. */
    public Reservation() {
    }

    /** @return the reservation identifier */
    public String getId() {
        return id;
    }

    /** @param id the id to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the current status */
    public ReservationStatus getStatus() {
        return status;
    }

    /** @param status the status to set */
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    /** @return the passenger */
    public Passenger getPassenger() {
        return passenger;
    }

    /** @param passenger the passenger to set */
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    /** @return the flight */
    public Flight getFlight() {
        return flight;
    }

    /** @param flight the flight to set */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}