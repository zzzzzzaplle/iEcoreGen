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
     * Gets the list of flights.
     * @return the list of flights
     */
    public List<Flight> getFlights() {
        return flights;
    }

    /**
     * Sets the list of flights.
     * @param flights the list of flights to set
     */
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    /**
     * Adds a flight to the airline's list of flights.
     * @param f the flight to add
     */
    public void addFlight(Flight f) {
        this.flights.add(f);
    }

    /**
     * Removes a flight from the airline's list of flights.
     * @param f the flight to remove
     */
    public void removeFlight(Flight f) {
        this.flights.remove(f);
    }

    /**
     * Publishes a flight for booking.
     * @param f the flight to publish
     * @param now the current date and time
     * @return true if the flight is published successfully, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f.getDepartureTime().after(now) && f.getDepartureTime().before(f.getArrivalTime())) {
            if (f.isOpenForBooking()) {
                // Additional checks for valid departure and arrival timestamps
                // and route integrity can be added here
                return true;
            }
        }
        return false;
    }

    /**
     * Closes a flight.
     * @param flightId the ID of the flight to close
     * @param now the current date and time
     * @return true if the flight is closed successfully, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        for (Flight f : flights) {
            if (f.getId().equals(flightId) && f.isOpenForBooking() && f.getDepartureTime().after(now)) {
                f.setOpenForBooking(false);
                // Cancel all confirmed reservations
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
     * @param origin the origin airport
     * @param dest the destination airport
     * @param date the date of travel
     * @return a list of flights matching the search criteria
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        // Implement search logic here
        return new ArrayList<>();
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
     * Gets the ID of the flight.
     * @return the ID of the flight
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the flight.
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Checks if the flight is open for booking.
     * @return true if the flight is open for booking, false otherwise
     */
    public boolean isOpenForBooking() {
        return openForBooking;
    }

    /**
     * Sets the open for booking status of the flight.
     * @param openForBooking the status to set
     */
    public void setOpenForBooking(boolean openForBooking) {
        this.openForBooking = openForBooking;
    }

    /**
     * Gets the departure time of the flight.
     * @return the departure time
     */
    public Date getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time of the flight.
     * @param departureTime the departure time to set
     */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time of the flight.
     * @return the arrival time
     */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time of the flight.
     * @param arrivalTime the arrival time to set
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the departure airport of the flight.
     * @return the departure airport
     */
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    /**
     * Sets the departure airport of the flight.
     * @param departureAirport the departure airport to set
     */
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    /**
     * Gets the arrival airport of the flight.
     * @return the arrival airport
     */
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    /**
     * Sets the arrival airport of the flight.
     * @param arrivalAirport the arrival airport to set
     */
    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    /**
     * Gets the list of stopovers for the flight.
     * @return the list of stopovers
     */
    public List<Stopover> getStopovers() {
        return stopovers;
    }

    /**
     * Sets the list of stopovers for the flight.
     * @param stopovers the list of stopovers to set
     */
    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    /**
     * Gets the list of reservations for the flight.
     * @return the list of reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the list of reservations for the flight.
     * @param reservations the list of reservations to set
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds a stopover to the flight.
     * @param stop the stopover to add
     * @param now the current date and time
     * @return true if the stopover is added successfully, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (this.departureTime.before(now) || stop.getDepartureTime().before(this.departureTime) || stop.getArrivalTime().after(this.arrivalTime)) {
            return false;
        }
        this.stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     * @param stop the stopover to remove
     * @param now the current date and time
     * @return true if the stopover is removed successfully, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (this.departureTime.before(now)) {
            return false;
        }
        return this.stopovers.remove(stop);
    }

    /**
     * Gets the confirmed reservations for the flight.
     * @return the list of confirmed reservations
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
     * Gets the departure time of the stopover.
     * @return the departure time
     */
    public Date getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time of the stopover.
     * @param departureTime the departure time to set
     */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time of the stopover.
     * @return the arrival time
     */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time of the stopover.
     * @param arrivalTime the arrival time to set
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the airport of the stopover.
     * @return the airport
     */
    public Airport getAirport() {
        return airport;
    }

    /**
     * Sets the airport of the stopover.
     * @param airport the airport to set
     */
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
     * Gets the ID of the airport.
     * @return the ID of the airport
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the airport.
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the list of cities served by the airport.
     * @return the list of cities
     */
    public List<City> getServesForCities() {
        return servesForCities;
    }

    /**
     * Sets the list of cities served by the airport.
     * @param servesForCities the list of cities to set
     */
    public void setServesForCities(List<City> servesForCities) {
        this.servesForCities = servesForCities;
    }

    /**
     * Adds a city to the list of cities served by the airport.
     * @param c the city to add
     */
    public void addCity(City c) {
        this.servesForCities.add(c);
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
     * Gets the list of bookings for the customer.
     * @return the list of bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the list of bookings for the customer.
     * @param bookings the list of bookings to set
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Adds a booking for the customer.
     * @param f the flight to book
     * @param now the current date and time
     * @param listOfPassengerNames the list of passenger names
     * @return true if the booking is added successfully, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f.getDepartureTime().before(now)) {
            return false;
        }
        Booking booking = new Booking();
        for (String passengerName : listOfPassengerNames) {
            if (!booking.createReservation(f, passengerName, now)) {
                return false;
            }
        }
        this.bookings.add(booking);
        return true;
    }

    /**
     * Confirms a reservation.
     * @param reservationID the ID of the reservation to confirm
     * @param now the current date and time
     * @return true if the reservation is confirmed successfully, false otherwise
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
     * @param reservationID the ID of the reservation to cancel
     * @param now the current date and time
     * @return true if the reservation is canceled successfully, false otherwise
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

    /**
     * Gets the name of the passenger.
     * @return the name of the passenger
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the passenger.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a booking with its reservations.
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
     * Gets the customer who made the booking.
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who made the booking.
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the list of reservations for the booking.
     * @return the list of reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the list of reservations for the booking.
     * @param reservations the list of reservations to set
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Creates a reservation for a passenger on a flight.
     * @param f the flight to reserve
     * @param passengerName the name of the passenger
     * @param now the current date and time
     * @return true if the reservation is created successfully, false otherwise
     */
    public boolean createReservation(Flight f, String passengerName, Date now) {
        if (f.getDepartureTime().before(now)) {
            return false;
        }
        Reservation reservation = new Reservation();
        Passenger passenger = new Passenger();
        passenger.setName(passengerName);
        reservation.setPassenger(passenger);
        reservation.setFlight(f);
        reservation.setStatus(ReservationStatus.PENDING);
        this.reservations.add(reservation);
        f.getReservations().add(reservation);
        return true;
    }
}

/**
 * Represents the status of a reservation.
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
     * Gets the ID of the reservation.
     * @return the ID of the reservation
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the reservation.
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the status of the reservation.
     * @return the status of the reservation
     */
    public ReservationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the reservation.
     * @param status the status to set
     */
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    /**
     * Gets the passenger who made the reservation.
     * @return the passenger
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * Sets the passenger who made the reservation.
     * @param passenger the passenger to set
     */
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    /**
     * Gets the flight for which the reservation was made.
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Sets the flight for which the reservation was made.
     * @param flight the flight to set
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}