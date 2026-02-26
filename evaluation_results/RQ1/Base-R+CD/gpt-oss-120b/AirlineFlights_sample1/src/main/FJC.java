import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Represents an airline that manages flights.
 */
 class Airline {

    private List<Flight> flights = new ArrayList<>();

    /** Default constructor */
    public Airline() {
    }

    /** @return the list of flights (modifiable) */
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
     * Publishes a flight for booking.
     *
     * @param f   the flight to publish
     * @param now the current time
     * @return true if the flight was successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || now == null) {
            return false;
        }
        // Validate timestamps
        Date dep = f.getDepartureTime();
        Date arr = f.getArrivalTime();
        if (dep == null || arr == null) {
            return false;
        }
        // Temporal consistency
        if (!now.before(dep) || !dep.before(arr)) {
            return false;
        }
        // Route integrity
        Airport depA = f.getDepartureAirport();
        Airport arrA = f.getArrialAirport();
        if (depA == null || arrA == null || depA.getId().equals(arrA.getId())) {
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
     * @return true if the flight was successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        if (flightId == null || now == null) {
            return false;
        }
        Flight target = null;
        for (Flight f : flights) {
            if (flightId.equals(f.getId())) {
                target = f;
                break;
            }
        }
        if (target == null) {
            return false;
        }
        if (!target.isOpenForBooking()) {
            return false; // already closed
        }
        if (!now.before(target.getDepartureTime())) {
            return false; // already departed
        }
        // Close flight
        target.setOpenForBooking(false);
        // Cancel every confirmed reservation
        for (Reservation r : target.getReservations()) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELLED);
            }
        }
        return true;
    }

    /**
     * Searches flights by origin, destination and departure date (day precision).
     *
     * @param origin      airport id of origin
     * @param dest        airport id of destination
     * @param date        date (day) to match departure
     * @return list of matching flights (may be empty)
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        if (origin == null || dest == null || date == null) {
            return Collections.emptyList();
        }
        List<Flight> result = new ArrayList<>();
        Calendar targetCal = Calendar.getInstance();
        targetCal.setTime(date);
        for (Flight f : flights) {
            if (!origin.equals(f.getDepartureAirport().getId())) {
                continue;
            }
            if (!dest.equals(f.getArrialAirport().getId())) {
                continue;
            }
            Calendar depCal = Calendar.getInstance();
            depCal.setTime(f.getDepartureTime());
            if (depCal.get(Calendar.YEAR) == targetCal.get(Calendar.YEAR)
                    && depCal.get(Calendar.MONTH) == targetCal.get(Calendar.MONTH)
                    && depCal.get(Calendar.DAY_OF_MONTH) == targetCal.get(Calendar.DAY_OF_MONTH)) {
                result.add(f);
            }
        }
        return result;
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

    /** Default constructor */
    public Flight() {
    }

    /** @return the flight identifier */
    public String getId() {
        return id;
    }

    /** @param id the identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return true if the flight is open for booking */
    public boolean isOpenForBooking() {
        return openForBooking;
    }

    /** @param openForBooking the open flag to set */
    public void setOpenForBooking(boolean openForBooking) {
        this.openForBooking = openForBooking;
    }

    /** @return departure timestamp */
    public Date getDepartureTime() {
        return departureTime;
    }

    /** @param departureTime the departure timestamp to set */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /** @return arrival timestamp */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /** @param arrivalTime the arrival timestamp to set */
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
    public Airport getArrialAirport() {
        return arrialAirport;
    }

    /** @param arrialAirport the arrival airport to set */
    public void setArrialAirport(Airport arrialAirport) {
        this.arrialAirport = arrialAirport;
    }

    /** @return list of stopovers */
    public List<Stopover> getStopovers() {
        return stopovers;
    }

    /** @param stopovers the stopovers list to set */
    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    /** @return list of reservations */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /** @param reservations the reservations list to set */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds a stopover to the flight.
     *
     * @param stop the stopover to add
     * @param now  the current time
     * @return true if the stopover was added, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null || now == null) {
            return false;
        }
        if (!now.before(this.departureTime)) {
            return false; // cannot modify after departure
        }
        // Times must fit within flight schedule
        if (stop.getArrivalTime().before(this.departureTime)
                || stop.getDepartureTime().after(this.arrivalTime)
                || !stop.getArrivalTime().before(stop.getDepartureTime())) {
            return false;
        }
        // No duplicate stopover at same airport at same time
        for (Stopover s : stopovers) {
            if (s.getAirport().getId().equals(stop.getAirport().getId())) {
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
     * @return true if the stopover was removed, false otherwise
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
     * @return list of confirmed reservations (empty if none)
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
}

/**
 * Represents a stopover during a flight.
 */
class Stopover {

    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    /** Default constructor */
    public Stopover() {
    }

    /** @return arrival timestamp */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /** @param arrivalTime the arrival timestamp to set */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /** @return departure timestamp */
    public Date getDepartureTime() {
        return departureTime;
    }

    /** @param departureTime the departure timestamp to set */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /** @return associated airport */
    public Airport getAirport() {
        return airport;
    }

    /** @param airport the airport to set */
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

    /** Default constructor */
    public Airport() {
    }

    /** @return airport identifier */
    public String getId() {
        return id;
    }

    /** @param id the identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return list of cities served */
    public List<City> getServesForCities() {
        return servesForCities;
    }

    /** @param servesForCities the list of cities to set */
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
 * Represents a city.
 */
class City {

    private String name;

    /** Default constructor */
    public City() {
    }

    /** @return city name */
    public String getName() {
        return name;
    }

    /** @param name city name to set */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a customer who can make bookings.
 */
class Customer {

    private List<Booking> bookings = new ArrayList<>();

    /** Default constructor */
    public Customer() {
    }

    /** @return list of bookings */
    public List<Booking> getBookings() {
        return bookings;
    }

    /** @param bookings the bookings list to set */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Creates a booking for a flight with a list of passenger names.
     *
     * @param f                     the flight to book
     * @param now                   the current time
     * @param listOfPassengerNames  list of passenger names
     * @return true if the booking succeeded, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || now == null || listOfPassengerNames == null || listOfPassengerNames.isEmpty()) {
            return false;
        }
        if (!f.isOpenForBooking()) {
            return false;
        }
        if (!now.before(f.getDepartureTime())) {
            return false;
        }
        // Ensure no duplicate passenger names on this flight (across all reservations)
        for (String name : listOfPassengerNames) {
            for (Reservation r : f.getReservations()) {
                if (r.getPassenger().getName().equalsIgnoreCase(name)) {
                    return false; // duplicate passenger on same flight
                }
            }
        }
        Booking booking = new Booking();
        booking.setCustomer(this);
        for (String passengerName : listOfPassengerNames) {
            boolean created = booking.createReservation(f, passengerName, now);
            if (!created) {
                return false; // if any reservation fails, abort
            }
        }
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms a reservation.
     *
     * @param reservationID the reservation identifier
     * @param now           the current time
     * @return true if the reservation was confirmed, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        if (reservationID == null || now == null) {
            return false;
        }
        Reservation target = findReservationById(reservationID);
        if (target == null) {
            return false;
        }
        Flight f = target.getFlight();
        if (!f.isOpenForBooking() || !now.before(f.getDepartureTime())) {
            return false;
        }
        if (target.getStatus() != ReservationStatus.PENDING) {
            return false;
        }
        target.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels a reservation.
     *
     * @param reservationID the reservation identifier
     * @param now           the current time
     * @return true if the reservation was cancelled, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        if (reservationID == null || now == null) {
            return false;
        }
        Reservation target = findReservationById(reservationID);
        if (target == null) {
            return false;
        }
        Flight f = target.getFlight();
        if (!f.isOpenForBooking() || !now.before(f.getDepartureTime())) {
            return false;
        }
        if (target.getStatus() == ReservationStatus.CANCELLED) {
            return false;
        }
        target.setStatus(ReservationStatus.CANCELLED);
        return true;
    }

    /** Helper method to locate a reservation belonging to this customer */
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

    /** @return passenger name */
    public String getName() {
        return name;
    }

    /** @param name passenger name to set */
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

    /** Default constructor */
    public Booking() {
    }

    /** @return the owning customer */
    public Customer getCustomer() {
        return customer;
    }

    /** @param customer the customer to set */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /** @return list of reservations */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /** @param reservations the reservations list to set */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Creates a reservation for a passenger on a flight.
     *
     * @param f          the flight
     * @param passenger  passenger name
     * @param now        current time
     * @return true if reservation was created, false otherwise
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (f == null || passenger == null || now == null) {
            return false;
        }
        // No duplicate passenger name within this booking (already ensured by Customer)
        // Create passenger
        Passenger p = new Passenger();
        p.setName(passenger);
        // Create reservation
        Reservation r = new Reservation();
        r.setId(UUID.randomUUID().toString());
        r.setStatus(ReservationStatus.PENDING);
        r.setPassenger(p);
        r.setFlight(f);
        // Add to flight and booking
        f.getReservations().add(r);
        reservations.add(r);
        return true;
    }
}

/**
 * Enumeration of reservation statuses.
 */
enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELLED
}

/**
 * Represents a reservation for a passenger on a flight.
 */
class Reservation {

    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    /** Default constructor */
    public Reservation() {
    }

    /** @return reservation identifier */
    public String getId() {
        return id;
    }

    /** @param id the identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return current reservation status */
    public ReservationStatus getStatus() {
        return status;
    }

    /** @param status the status to set */
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    /** @return associated passenger */
    public Passenger getPassenger() {
        return passenger;
    }

    /** @param passenger the passenger to set */
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    /** @return associated flight */
    public Flight getFlight() {
        return flight;
    }

    /** @param flight the flight to set */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}