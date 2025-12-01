import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an airline with a list of flights.
 */
class Airline {
    private List<Flight> flights;

    /**
     * Unparameterized constructor for Airline.
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
        // Check if the flight has valid departure and arrival timestamps
        if (!f.isValidTimestamp()) {
            return false;
        }
        // Check temporal consistency
        if (now.after(f.getDepartureTime()) || f.getDepartureTime().after(f.getArrivalTime())) {
            return false;
        }
        // Check route integrity
        if (f.getDepartureAirport().equals(f.getArrivalAirport())) {
            return false;
        }
        // Check if the flight is already published
        if (!f.isOpenForBooking()) {
            return false;
        }
        return true;
    }

    /**
     * Closes an existing flight.
     * @param flightId the ID of the flight to close
     * @param now the current date and time
     * @return true if the flight is closed successfully, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight flight = getFlightById(flightId);
        if (flight == null || !flight.isOpenForBooking() || now.after(flight.getDepartureTime())) {
            return false;
        }
        flight.setOpenForBooking(false);
        // Cancel every confirmed reservation
        for (Reservation reservation : flight.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                reservation.setStatus(ReservationStatus.CANCELED);
            }
        }
        return true;
    }

    /**
     * Searches for flights based on origin, destination, and date.
     * @param origin the origin airport ID
     * @param dest the destination airport ID
     * @param date the date of departure
     * @return a list of flights that match the search criteria
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getDepartureAirport().getId().equals(origin) && flight.getArrivalAirport().getId().equals(dest) && flight.getDepartureTime().equals(date)) {
                result.add(flight);
            }
        }
        return result;
    }

    /**
     * Gets a flight by its ID.
     * @param flightId the ID of the flight to retrieve
     * @return the flight with the specified ID, or null if not found
     */
    private Flight getFlightById(String flightId) {
        for (Flight flight : flights) {
            if (flight.getId().equals(flightId)) {
                return flight;
            }
        }
        return null;
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
     * Unparameterized constructor for Flight.
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
     * Checks if the flight has valid timestamps.
     * @return true if the timestamps are valid, false otherwise
     */
    public boolean isValidTimestamp() {
        // Assuming the timestamps are in the correct format
        return true;
    }

    /**
     * Adds a stopover to the flight.
     * @param stop the stopover to add
     * @param now the current date and time
     * @return true if the stopover is added successfully, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (now.after(departureTime)) {
            return false;
        }
        if (stop.getDepartureTime().before(departureTime) || stop.getArrivalTime().after(arrivalTime)) {
            return false;
        }
        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     * @param stop the stopover to remove
     * @param now the current date and time
     * @return true if the stopover is removed successfully, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (now.after(departureTime)) {
            return false;
        }
        return stopovers.remove(stop);
    }

    /**
     * Gets the confirmed reservations for the flight.
     * @return a list of confirmed reservations
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmedReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                confirmedReservations.add(reservation);
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
     * Unparameterized constructor for Stopover.
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
     * Unparameterized constructor for Airport.
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
class City {
    // Add properties and methods as needed
}

/**
 * Represents a customer with their bookings.
 */
class Customer {
    private List<Booking> bookings;

    /**
     * Unparameterized constructor for Customer.
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
        if (now.after(f.getDepartureTime())) {
            return false;
        }
        Booking booking = new Booking();
        booking.setCustomer(this);
        for (String passengerName : listOfPassengerNames) {
            if (!booking.createReservation(f, passengerName, now)) {
                return false;
            }
        }
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms a reservation.
     * @param reservationID the ID of the reservation to confirm
     * @param now the current date and time
     * @return true if the reservation is confirmed successfully, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    if (now.after(reservation.getFlight().getDepartureTime())) {
                        return false;
                    }
                    reservation.setStatus(ReservationStatus.CONFIRMED);
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
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    if (now.after(reservation.getFlight().getDepartureTime())) {
                        return false;
                    }
                    reservation.setStatus(ReservationStatus.CANCELED);
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
     * Unparameterized constructor for Passenger.
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
 * Represents a booking with its customer and reservations.
 */
class Booking {
    private Customer customer;
    private List<Reservation> reservations;

    /**
     * Unparameterized constructor for Booking.
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
        if (now.after(f.getDepartureTime())) {
            return false;
        }
        for (Reservation reservation : reservations) {
            if (reservation.getPassenger().getName().equals(passengerName)) {
                return false;
            }
        }
        Reservation reservation = new Reservation();
        reservation.setFlight(f);
        Passenger passenger = new Passenger();
        passenger.setName(passengerName);
        reservation.setPassenger(passenger);
        reservations.add(reservation);
        return true;
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
     * Unparameterized constructor for Reservation.
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
     * Gets the passenger associated with the reservation.
     * @return the passenger
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * Sets the passenger associated with the reservation.
     * @param passenger the passenger to set
     */
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    /**
     * Gets the flight associated with the reservation.
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Sets the flight associated with the reservation.
     * @param flight the flight to set
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}