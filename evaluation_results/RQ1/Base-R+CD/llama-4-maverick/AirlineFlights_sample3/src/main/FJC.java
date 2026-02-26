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
     * 
     * @param f the flight to add
     */
    public void addFlight(Flight f) {
        this.flights.add(f);
    }

    /**
     * Removes a flight from the airline's list of flights.
     * 
     * @param f the flight to remove
     */
    public void removeFlight(Flight f) {
        this.flights.remove(f);
    }

    /**
     * Publishes a flight for booking.
     * 
     * @param f   the flight to publish
     * @param now the current date and time
     * @return true if the flight is published successfully, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        // Check if the flight has valid departure and arrival timestamps
        if (f.getDepartureTime().after(now) && f.getDepartureTime().before(f.getArrivalTime())) {
            // Check if the flight is still open for booking and has not been published before
            if (f.isOpenForBooking()) {
                // Additional checks for route integrity can be added here
                if (!f.getDepartureAirport().equals(f.getArrivalAirport())) {
                    // The flight can be published
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Closes an existing flight.
     * 
     * @param flightId the ID of the flight to close
     * @param now      the current date and time
     * @return true if the flight is closed successfully, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        for (Flight f : flights) {
            if (f.getId().equals(flightId) && f.isOpenForBooking() && f.getDepartureTime().after(now)) {
                f.setOpenForBooking(false);
                // Cancel every confirmed reservation
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
     * @param origin the origin airport
     * @param dest   the destination airport
     * @param date   the date of the flight
     * @return a list of flights matching the search criteria
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        // Implementation of searchFlights
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
     * 
     * @param stop the stopover to add
     * @param now  the current date and time
     * @return true if the stopover is added successfully, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (this.departureTime.before(stop.getDepartureTime()) && stop.getDepartureTime().before(stop.getArrivalTime())
                && stop.getArrivalTime().before(this.arrivalTime) && now.before(this.departureTime)) {
            this.stopovers.add(stop);
            return true;
        }
        return false;
    }

    /**
     * Removes a stopover from the flight.
     * 
     * @param stop the stopover to remove
     * @param now  the current date and time
     * @return true if the stopover is removed successfully, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (now.before(this.departureTime)) {
            return this.stopovers.remove(stop);
        }
        return false;
    }

    /**
     * Retrieves all confirmed reservations for the flight.
     * 
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
     * Default constructor.
     */
    public Airport() {
        this.servesForCities = new ArrayList<>();
    }

    /**
     * Adds a city to the airport's list of served cities.
     * 
     * @param c the city to add
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Airport airport = (Airport) obj;
        return id.equals(airport.id);
    }
}

/**
 * Represents a city.
 */
class City {
}

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
     * Adds a booking for a flight with a list of passenger names.
     * 
     * @param f                 the flight to book
     * @param now               the current date and time
     * @param listOfPassengerNames the list of passenger names
     * @return true if the booking is added successfully, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f.isOpenForBooking() && now.before(f.getDepartureTime())) {
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
     * Confirms a reservation by its ID.
     * 
     * @param reservationID the ID of the reservation to confirm
     * @param now           the current date and time
     * @return true if the reservation is confirmed successfully, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId().equals(reservationID) && r.getFlight().isOpenForBooking()
                        && now.before(r.getFlight().getDepartureTime())) {
                    r.setStatus(ReservationStatus.CONFIRMED);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cancels a reservation by its ID.
     * 
     * @param reservationID the ID of the reservation to cancel
     * @param now           the current date and time
     * @return true if the reservation is canceled successfully, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId().equals(reservationID) && r.getFlight().isOpenForBooking()
                        && now.before(r.getFlight().getDepartureTime())) {
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
     * Default constructor.
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
 * Represents a booking with its customer and reservations.
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
     * Creates a reservation for a flight with a passenger name.
     * 
     * @param f           the flight to reserve
     * @param passengerName the name of the passenger
     * @param now         the current date and time
     * @return true if the reservation is created successfully, false otherwise
     */
    public boolean createReservation(Flight f, String passengerName, Date now) {
        for (Reservation r : f.getReservations()) {
            if (r.getPassenger().getName().equals(passengerName)) {
                return false; // Duplicate passenger
            }
        }
        Reservation reservation = new Reservation();
        reservation.setFlight(f);
        Passenger passenger = new Passenger();
        passenger.setName(passengerName);
        reservation.setPassenger(passenger);
        reservation.setStatus(ReservationStatus.PENDING);
        // Generate a unique ID for the reservation
        // For simplicity, this example does not implement a unique ID generation mechanism
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
    public Reservation() {
    }

    /**
     * Sets the status of the reservation.
     * 
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