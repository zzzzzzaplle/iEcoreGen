import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an airline with a list of flights.
 */
class Airline {
    private List<Flight> flights;

    /**
     * Default constructor.
     */
    public Airline() {
        this.flights = new ArrayList<>();
    }

    /**
     * Adds a flight to the airline's list of flights.
     * @param f the flight to be added
     */
    public void addFlight(Flight f) {
        this.flights.add(f);
    }

    /**
     * Removes a flight from the airline's list of flights.
     * @param f the flight to be removed
     */
    public void removeFlight(Flight f) {
        this.flights.remove(f);
    }

    /**
     * Publishes a flight for booking.
     * @param f the flight to be published
     * @param now the current date and time
     * @return true if the flight is published successfully, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        // Check if the flight has valid departure and arrival timestamps
        if (!f.isValidTimestamp()) {
            return false;
        }
        // Check temporal consistency
        if (!f.isTemporalConsistent(now)) {
            return false;
        }
        // Check route integrity
        if (!f.isRouteIntegrity()) {
            return false;
        }
        // Check if the flight is already published
        if (!f.isOpenForBooking()) {
            return false;
        }
        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an existing flight.
     * @param flightId the ID of the flight to be closed
     * @param now the current date and time
     * @return true if the flight is closed successfully, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        for (Flight f : flights) {
            if (f.getId().equals(flightId)) {
                if (f.isOpenForBooking() && f.getDepartureTime().after(now)) {
                    f.setOpenForBooking(false);
                    f.cancelConfirmedReservations();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Searches for flights based on origin, destination, and date.
     * @param origin the origin airport
     * @param dest the destination airport
     * @param date the date of travel
     * @return a list of flights matching the search criteria
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        // TO DO: implement search logic
        return new ArrayList<>();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}

/**
 * Represents a flight with its details and reservations.
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

    /**
     * Default constructor.
     */
    public Flight() {
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    /**
     * Adds a stopover to the flight.
     * @param stop the stopover to be added
     * @param now the current date and time
     * @return true if the stopover is added successfully, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (this.departureTime.after(now) && stop.isValid(this.departureTime, this.arrivalTime)) {
            this.stopovers.add(stop);
            return true;
        }
        return false;
    }

    /**
     * Removes a stopover from the flight.
     * @param stop the stopover to be removed
     * @param now the current date and time
     * @return true if the stopover is removed successfully, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (this.departureTime.after(now)) {
            this.stopovers.remove(stop);
            return true;
        }
        return false;
    }

    /**
     * Gets the confirmed reservations for the flight.
     * @return a list of confirmed reservations
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmedReservations = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                confirmedReservations.add(r);
            }
        }
        return confirmedReservations;
    }

    /**
     * Checks if the flight has valid departure and arrival timestamps.
     * @return true if the timestamps are valid, false otherwise
     */
    public boolean isValidTimestamp() {
        // TO DO: implement timestamp validation logic
        return true;
    }

    /**
     * Checks if the flight is temporally consistent.
     * @param now the current date and time
     * @return true if the flight is temporally consistent, false otherwise
     */
    public boolean isTemporalConsistent(Date now) {
        return now.before(this.departureTime) && this.departureTime.before(this.arrivalTime);
    }

    /**
     * Checks if the flight has route integrity.
     * @return true if the flight has route integrity, false otherwise
     */
    public boolean isRouteIntegrity() {
        return !this.departureAirport.getId().equals(this.arrivalAirport.getId());
    }

    /**
     * Cancels all confirmed reservations for the flight.
     */
    public void cancelConfirmedReservations() {
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELED);
            }
        }
    }

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
}

/**
 * Represents a stopover with its details.
 */
class Stopover {
    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    /**
     * Default constructor.
     */
    public Stopover() {}

    /**
     * Checks if the stopover is valid within the flight's departure and arrival times.
     * @param flightDepartureTime the flight's departure time
     * @param flightArrivalTime the flight's arrival time
     * @return true if the stopover is valid, false otherwise
     */
    public boolean isValid(Date flightDepartureTime, Date flightArrivalTime) {
        return flightDepartureTime.before(this.departureTime) && this.departureTime.before(this.arrivalTime) && this.arrivalTime.before(flightArrivalTime);
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

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}

/**
 * Represents an airport with its details.
 */
class Airport {
    private String id;
    private List<City> servesForCities;

    /**
     * Default constructor.
     */
    public Airport() {
        this.servesForCities = new ArrayList<>();
    }

    /**
     * Adds a city to the airport's list of cities it serves.
     * @param c the city to be added
     */
    public void addCity(City c) {
        this.servesForCities.add(c);
    }

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
}

/**
 * Represents a city.
 */
class City {}

/**
 * Represents a customer with their bookings.
 */
class Customer {
    private List<Booking> bookings;

    /**
     * Default constructor.
     */
    public Customer() {
        this.bookings = new ArrayList<>();
    }

    /**
     * Adds a booking for the customer.
     * @param f the flight to be booked
     * @param now the current date and time
     * @param listOfPassengerNames the list of passenger names
     * @return true if the booking is added successfully, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        // TO DO: implement booking logic
        return true;
    }

    /**
     * Confirms a reservation.
     * @param reservationID the ID of the reservation to be confirmed
     * @param now the current date and time
     * @return true if the reservation is confirmed successfully, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        // TO DO: implement confirmation logic
        return true;
    }

    /**
     * Cancels a reservation.
     * @param reservationID the ID of the reservation to be canceled
     * @param now the current date and time
     * @return true if the reservation is canceled successfully, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        // TO DO: implement cancellation logic
        return true;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}

/**
 * Represents a passenger with their name.
 */
class Passenger {
    private String name;

    /**
     * Default constructor.
     */
    public Passenger() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a booking with its details and reservations.
 */
class Booking {
    private Customer customer;
    private List<Reservation> reservations;

    /**
     * Default constructor.
     */
    public Booking() {
        this.reservations = new ArrayList<>();
    }

    /**
     * Creates a reservation for the booking.
     * @param f the flight to be reserved
     * @param passenger the passenger's name
     * @param now the current date and time
     * @return true if the reservation is created successfully, false otherwise
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        // TO DO: implement reservation logic
        return true;
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
}

/**
 * Enum representing the status of a reservation.
 */
enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELED
}

/**
 * Represents a reservation with its details.
 */
class Reservation {
    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    /**
     * Default constructor.
     */
    public Reservation() {}

    /**
     * Sets the status of the reservation.
     * @param s the new status
     */
    public void setStatus(ReservationStatus s) {
        this.status = s;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReservationStatus getStatus() {
        return status;
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