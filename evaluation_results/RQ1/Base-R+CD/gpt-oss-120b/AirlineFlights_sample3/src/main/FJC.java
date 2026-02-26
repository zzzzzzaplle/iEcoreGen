import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Represents an airline that manages a collection of flights.
 */
 class Airline {

    private List<Flight> flights = new ArrayList<>();

    /** No‑arg constructor */
    public Airline() {
    }

    /** Get the list of flights */
    public List<Flight> getFlights() {
        return flights;
    }

    /** Set the list of flights */
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
     * Publishes a newly created flight so it becomes available for booking.
     *
     * @param f   the flight to publish
     * @param now the current time
     * @return {@code true} if the flight was successfully published; {@code false} otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || now == null) {
            return false;
        }

        // Verify timestamps are not null
        Date dep = f.getDepartureTime();
        Date arr = f.getArrivalTime();
        if (dep == null || arr == null) {
            return false;
        }

        // Temporal consistency: now < departure < arrival
        if (!now.before(dep) || !dep.before(arr)) {
            return false;
        }

        // Route integrity: departure airport must differ from arrival airport
        if (f.getDepartureAirport() == null || f.getArrialAirport() == null) {
            return false;
        }
        if (f.getDepartureAirport().getId().equals(f.getArrialAirport().getId())) {
            return false;
        }

        // Flight may be published only once and must be open for booking
        if (f.isOpenForBooking()) {
            return false; // already published
        }

        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an open flight that has not yet departed.
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
        // Flight must be open and not yet departed
        if (!flight.isOpenForBooking() || now.after(flight.getDepartureTime())) {
            return false;
        }

        // Close the flight
        flight.setOpenForBooking(false);

        // Cancel every confirmed reservation
        for (Reservation r : flight.getReservations()) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELLED);
            }
        }
        return true;
    }

    /**
     * Searches for flights that match the given origin, destination and departure date.
     *
     * @param origin      the departure airport id
     * @param dest        the arrival airport id
     * @param date        the desired departure date (time component is ignored)
     * @return list of matching flights; empty list if none found
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        if (origin == null || dest == null || date == null) {
            return result;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        for (Flight f : flights) {
            if (!f.isOpenForBooking()) {
                continue; // only open flights are searchable
            }
            Airport depA = f.getDepartureAirport();
            Airport arrA = f.getArrialAirport();
            if (depA == null || arrA == null) {
                continue;
            }
            if (!origin.equals(depA.getId()) || !dest.equals(arrA.getId())) {
                continue;
            }
            // compare only date part of departure time
            Calendar depCal = Calendar.getInstance();
            depCal.setTime(f.getDepartureTime());
            if (depCal.get(Calendar.YEAR) == year &&
                depCal.get(Calendar.MONTH) == month &&
                depCal.get(Calendar.DAY_OF_MONTH) == day) {
                result.add(f);
            }
        }
        return result;
    }

    /** Helper to locate a flight by its identifier */
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
    private boolean openForBooking = false;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrialAirport; // typo kept from design model
    private List<Stopover> stopovers = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    /** No‑arg constructor */
    public Flight() {
    }

    /** Getters and setters */
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
     * Adds a stopover to the flight before it departs.
     *
     * @param stop the stopover to add
     * @param now  the current time
     * @return {@code true} if the stopover was added successfully; {@code false} otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null || now == null) {
            return false;
        }
        // Flight must not have departed
        if (now.after(this.departureTime)) {
            return false;
        }
        // Stopover times must fit within overall schedule
        if (stop.getArrivalTime().before(this.departureTime) ||
            stop.getDepartureTime().after(this.arrivalTime) ||
            !stop.getArrivalTime().before(stop.getDepartureTime())) {
            return false;
        }
        // Ensure no duplicate stopover at same airport and times
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
     * Removes a stopover from the flight before it departs.
     *
     * @param stop the stopover to remove
     * @param now  the current time
     * @return {@code true} if the stopover was removed; {@code false} otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null || now == null) {
            return false;
        }
        // Flight must not have departed
        if (now.after(this.departureTime)) {
            return false;
        }
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
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
}

/**
 * Represents a stopover point during a flight.
 */
class Stopover {

    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    /** No‑arg constructor */
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

    /** No‑arg constructor */
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

    /** No‑arg constructor */
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
 * Represents a passenger.
 */
class Passenger {

    private String name;

    /** No‑arg constructor */
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
 * Represents a reservation made for a passenger on a specific flight.
 */
class Reservation {

    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    /** No‑arg constructor */
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

/**
 * Status values for a reservation.
 */
enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELLED
}

/**
 * Represents a booking made by a customer, containing multiple reservations.
 */
class Booking {

    private Customer customer;
    private List<Reservation> reservations = new ArrayList<>();

    /** No‑arg constructor */
    public Booking() {
    }

    /** Getters and setters */
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
     * Creates a reservation for a given passenger on a flight.
     *
     * @param f          the flight
     * @param passenger  the passenger name
     * @param now        the current time
     * @return {@code true} if reservation was created; {@code false} otherwise
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (f == null || passenger == null || now == null) {
            return false;
        }
        // Ensure flight is still open for booking and not departed
        if (!f.isOpenForBooking() || now.after(f.getDepartureTime())) {
            return false;
        }

        // Create passenger and reservation objects
        Passenger p = new Passenger();
        p.setName(passenger);

        Reservation r = new Reservation();
        r.setId(UUID.randomUUID().toString());
        r.setStatus(ReservationStatus.PENDING);
        r.setPassenger(p);
        r.setFlight(f);

        // Add reservation to flight and booking
        f.getReservations().add(r);
        this.reservations.add(r);
        return true;
    }
}

/**
 * Represents a customer that can make bookings.
 */
class Customer {

    private List<Booking> bookings = new ArrayList<>();

    /** No‑arg constructor */
    public Customer() {
    }

    /** Getter and setter */
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Adds a new booking for a flight with the given passenger names.
     *
     * @param f                     the flight to book
     * @param now                   the current time
     * @param listOfPassengerNames  list of passenger names
     * @return {@code true} if booking succeeded; {@code false} otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || now == null || listOfPassengerNames == null) {
            return false;
        }
        // Flight must be open and not yet departed
        if (!f.isOpenForBooking() || now.after(f.getDepartureTime())) {
            return false;
        }

        // Ensure no duplicate passenger names in this booking request
        Set<String> uniqueNames = new HashSet<>(listOfPassengerNames);
        if (uniqueNames.size() != listOfPassengerNames.size()) {
            return false; // duplicates present
        }

        // Ensure none of the passengers already have a reservation on the same flight
        for (String name : listOfPassengerNames) {
            for (Reservation existing : f.getReservations()) {
                if (existing.getPassenger().getName().equals(name)) {
                    return false; // duplicate passenger on same flight
                }
            }
        }

        // Create a new booking
        Booking booking = new Booking();
        booking.setCustomer(this);

        // Create reservations for each passenger
        for (String name : listOfPassengerNames) {
            boolean created = booking.createReservation(f, name, now);
            if (!created) {
                return false; // abort if any reservation fails
            }
        }

        // Add booking to customer's list
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms a reservation belonging to this customer.
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
        // Flight must be open and not departed
        Flight f = r.getFlight();
        if (!f.isOpenForBooking() || now.after(f.getDepartureTime())) {
            return false;
        }
        r.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels a reservation belonging to this customer.
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
        // Flight must be open and not departed
        Flight f = r.getFlight();
        if (!f.isOpenForBooking() || now.after(f.getDepartureTime())) {
            return false;
        }
        r.setStatus(ReservationStatus.CANCELLED);
        return true;
    }

    /** Helper method to locate a reservation belonging to this customer */
    private Reservation findReservationById(String id) {
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