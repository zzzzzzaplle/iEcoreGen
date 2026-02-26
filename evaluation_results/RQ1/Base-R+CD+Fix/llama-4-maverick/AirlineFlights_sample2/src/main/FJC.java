import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an airline with a list of flights.
 */
class Airline {
    private List<Flight> flights;

    /**
     * Default constructor to initialize an Airline object.
     */
    public Airline() {
        this.flights = new ArrayList<>();
    }

    /**
     * Adds a flight to the airline's list of flights.
     * 
     * @param f The flight to be added.
     */
    public void addFlight(Flight f) {
        this.flights.add(f);
    }

    /**
     * Removes a flight from the airline's list of flights.
     * 
     * @param f The flight to be removed.
     */
    public void removeFlight(Flight f) {
        this.flights.remove(f);
    }

    /**
     * Publishes a flight for booking.
     * 
     * @param f   The flight to be published.
     * @param now The current date and time.
     * @return true if the flight is successfully published; false otherwise.
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f.getDepartureTime().after(now) && f.getDepartureTime().before(f.getArrivalTime())
                && !f.getDepartureAirport().equals(f.getArrivalAirport()) && f.isOpenForBooking()) {
            f.setOpenForBooking(true);
            return true;
        }
        return false;
    }

    /**
     * Closes an existing flight.
     * 
     * @param flightId The ID of the flight to be closed.
     * @param now      The current date and time.
     * @return true if the flight is successfully closed; false otherwise.
     */
    public boolean closeFlight(String flightId, Date now) {
        for (Flight f : flights) {
            if (f.getId().equals(flightId) && f.isOpenForBooking() && f.getDepartureTime().after(now)) {
                f.setOpenForBooking(false);
                for (Reservation r : f.getReservations()) {
                    if (r.getStatus() == ReservationStatus.CONFIRMED) {
                        r.setStatus(ReservationStatus.CANCELED);
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for flights based on origin, destination, and date.
     * 
     * @param origin The origin airport.
     * @param dest   The destination airport.
     * @param date   The date of the flight.
     * @return A list of flights matching the search criteria.
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        // Implementation not provided in the design model
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
 * Represents a flight with its details and status.
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
     * Default constructor to initialize a Flight object.
     */
    public Flight() {
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    /**
     * Adds a stopover to the flight.
     * 
     * @param stop The stopover to be added.
     * @param now  The current date and time.
     * @return true if the stopover is successfully added; false otherwise.
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (this.departureTime.before(stop.getDepartureTime()) && stop.getDepartureTime().before(stop.getArrivalTime())
                && stop.getArrivalTime().before(this.arrivalTime) && stop.getAirport() != null) {
            this.stopovers.add(stop);
            return true;
        }
        return false;
    }

    /**
     * Removes a stopover from the flight.
     * 
     * @param stop The stopover to be removed.
     * @param now  The current date and time.
     * @return true if the stopover is successfully removed; false otherwise.
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (this.stopovers.contains(stop) && this.departureTime.before(now)) {
            this.stopovers.remove(stop);
            return true;
        }
        return false;
    }

    /**
     * Retrieves all confirmed reservations for the flight.
     * 
     * @return A list of confirmed reservations.
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
     * Default constructor to initialize a Stopover object.
     */
    public Stopover() {
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
     * Default constructor to initialize an Airport object.
     */
    public Airport() {
        this.servesForCities = new ArrayList<>();
    }

    /**
     * Adds a city served by the airport.
     * 
     * @param c The city to be added.
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
class City {
    // Implementation not provided in the design model
}

/**
 * Represents a customer with their bookings.
 */
class Customer {
    private List<Booking> bookings;

    /**
     * Default constructor to initialize a Customer object.
     */
    public Customer() {
        this.bookings = new ArrayList<>();
    }

    /**
     * Creates a booking for the customer.
     * 
     * @param f                   The flight to be booked.
     * @param now                 The current date and time.
     * @param listOfPassengerNames The list of passenger names.
     * @return true if the booking is successfully created; false otherwise.
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f.isOpenForBooking() && f.getDepartureTime().after(now)) {
            Booking booking = new Booking();
            booking.setCustomer(this);
            for (String passengerName : listOfPassengerNames) {
                if (!booking.createReservation(f, passengerName, now)) {
                    return false;
                }
            }
            this.bookings.add(booking);
            return true;
        }
        return false;
    }

    /**
     * Confirms a reservation.
     * 
     * @param reservationID The ID of the reservation to be confirmed.
     * @param now           The current date and time.
     * @return true if the reservation is successfully confirmed; false otherwise.
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId().equals(reservationID) && r.getFlight().getDepartureTime().after(now)) {
                    r.setStatus(ReservationStatus.CONFIRMED);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cancels a reservation.
     * 
     * @param reservationID The ID of the reservation to be canceled.
     * @param now           The current date and time.
     * @return true if the reservation is successfully canceled; false otherwise.
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId().equals(reservationID) && r.getFlight().getDepartureTime().after(now)) {
                    r.setStatus(ReservationStatus.CANCELED);
                    return true;
                }
            }
        }
        return false;
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
     * Default constructor to initialize a Passenger object.
     */
    public Passenger() {
    }

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
     * Default constructor to initialize a Booking object.
     */
    public Booking() {
        this.reservations = new ArrayList<>();
    }

    /**
     * Creates a reservation for a passenger on a flight.
     * 
     * @param f         The flight to be reserved.
     * @param passenger The name of the passenger.
     * @param now       The current date and time.
     * @return true if the reservation is successfully created; false otherwise.
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        for (Reservation r : f.getReservations()) {
            if (r.getPassenger().getName().equals(passenger)) {
                return false;
            }
        }
        Reservation reservation = new Reservation();
        reservation.setFlight(f);
        Passenger p = new Passenger();
        p.setName(passenger);
        reservation.setPassenger(p);
        reservation.setStatus(ReservationStatus.PENDING);
        this.reservations.add(reservation);
        f.getReservations().add(reservation);
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
 * Represents a reservation with its details and status.
 */
class Reservation {
    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    /**
     * Default constructor to initialize a Reservation object.
     */
    public Reservation() {
    }

    /**
     * Sets the status of the reservation.
     * 
     * @param s The new status.
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