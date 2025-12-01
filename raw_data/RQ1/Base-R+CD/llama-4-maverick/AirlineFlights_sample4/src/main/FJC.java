import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Represents an airline with a list of flights.
 */
class Airline {
    private List<Flight> flights;

    /**
     * Constructor for Airline.
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
     * @return true if the flight is published successfully, false otherwise.
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f.isOpenForBooking() && isValidFlight(f, now)) {
            f.setOpenForBooking(true);
            return true;
        }
        return false;
    }

    /**
     * Closes a flight for booking.
     * 
     * @param flightId The ID of the flight to be closed.
     * @param now      The current date and time.
     * @return true if the flight is closed successfully, false otherwise.
     */
    public boolean closeFlight(String flightId, Date now) {
        for (Flight f : flights) {
            if (f.getId().equals(flightId) && f.isOpenForBooking() && f.getDepartureTime().after(now)) {
                f.setOpenForBooking(false);
                f.cancelConfirmedReservations();
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a flight is valid.
     * 
     * @param f   The flight to be checked.
     * @param now The current date and time.
     * @return true if the flight is valid, false otherwise.
     */
    private boolean isValidFlight(Flight f, Date now) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date departureTime = f.getDepartureTime();
            Date arrivalTime = f.getArrivalTime();
            if (departureTime.after(now) && departureTime.before(arrivalTime)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Gets the list of flights.
     * 
     * @return The list of flights.
     */
    public List<Flight> getFlights() {
        return flights;
    }

    /**
     * Sets the list of flights.
     * 
     * @param flights The list of flights to be set.
     */
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}

/**
 * Represents a flight with its details and operations.
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
     * Constructor for Flight.
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
     * @return true if the stopover is added successfully, false otherwise.
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop.getDepartureTime().after(now) && stop.getDepartureTime().before(stop.getArrivalTime())
                && stop.getArrivalTime().before(arrivalTime)) {
            stopovers.add(stop);
            return true;
        }
        return false;
    }

    /**
     * Removes a stopover from the flight.
     * 
     * @param stop The stopover to be removed.
     * @param now  The current date and time.
     * @return true if the stopover is removed successfully, false otherwise.
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stopovers.contains(stop) && stop.getDepartureTime().after(now)) {
            stopovers.remove(stop);
            return true;
        }
        return false;
    }

    /**
     * Gets the confirmed reservations for the flight.
     * 
     * @return The list of confirmed reservations.
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
     * Cancels all confirmed reservations for the flight.
     */
    public void cancelConfirmedReservations() {
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELED);
            }
        }
    }

    /**
     * Gets the ID of the flight.
     * 
     * @return The ID of the flight.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the flight.
     * 
     * @param id The ID to be set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Checks if the flight is open for booking.
     * 
     * @return true if the flight is open for booking, false otherwise.
     */
    public boolean isOpenForBooking() {
        return openForBooking;
    }

    /**
     * Sets the open for booking status of the flight.
     * 
     * @param openForBooking The status to be set.
     */
    public void setOpenForBooking(boolean openForBooking) {
        this.openForBooking = openForBooking;
    }

    /**
     * Gets the departure time of the flight.
     * 
     * @return The departure time.
     */
    public Date getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time of the flight.
     * 
     * @param departureTime The departure time to be set.
     */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time of the flight.
     * 
     * @return The arrival time.
     */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time of the flight.
     * 
     * @param arrivalTime The arrival time to be set.
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the departure airport of the flight.
     * 
     * @return The departure airport.
     */
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    /**
     * Sets the departure airport of the flight.
     * 
     * @param departureAirport The departure airport to be set.
     */
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    /**
     * Gets the arrival airport of the flight.
     * 
     * @return The arrival airport.
     */
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    /**
     * Sets the arrival airport of the flight.
     * 
     * @param arrivalAirport The arrival airport to be set.
     */
    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    /**
     * Gets the list of stopovers for the flight.
     * 
     * @return The list of stopovers.
     */
    public List<Stopover> getStopovers() {
        return stopovers;
    }

    /**
     * Sets the list of stopovers for the flight.
     * 
     * @param stopovers The list of stopovers to be set.
     */
    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    /**
     * Gets the list of reservations for the flight.
     * 
     * @return The list of reservations.
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the list of reservations for the flight.
     * 
     * @param reservations The list of reservations to be set.
     */
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
     * Constructor for Stopover.
     */
    public Stopover() {
    }

    /**
     * Gets the departure time of the stopover.
     * 
     * @return The departure time.
     */
    public Date getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time of the stopover.
     * 
     * @param departureTime The departure time to be set.
     */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time of the stopover.
     * 
     * @return The arrival time.
     */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time of the stopover.
     * 
     * @param arrivalTime The arrival time to be set.
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the airport of the stopover.
     * 
     * @return The airport.
     */
    public Airport getAirport() {
        return airport;
    }

    /**
     * Sets the airport of the stopover.
     * 
     * @param airport The airport to be set.
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
     * Constructor for Airport.
     */
    public Airport() {
        this.servesForCities = new ArrayList<>();
    }

    /**
     * Adds a city to the airport's list of served cities.
     * 
     * @param c The city to be added.
     */
    public void addCity(City c) {
        this.servesForCities.add(c);
    }

    /**
     * Gets the ID of the airport.
     * 
     * @return The ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the airport.
     * 
     * @param id The ID to be set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the list of cities served by the airport.
     * 
     * @return The list of cities.
     */
    public List<City> getServesForCities() {
        return servesForCities;
    }

    /**
     * Sets the list of cities served by the airport.
     * 
     * @param servesForCities The list of cities to be set.
     */
    public void setServesForCities(List<City> servesForCities) {
        this.servesForCities = servesForCities;
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
     * Constructor for Customer.
     */
    public Customer() {
        this.bookings = new ArrayList<>();
    }

    /**
     * Adds a booking for the customer.
     * 
     * @param f               The flight to be booked.
     * @param now             The current date and time.
     * @param listOfPassengerNames The list of passenger names.
     * @return true if the booking is added successfully, false otherwise.
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f.isOpenForBooking() && f.getDepartureTime().after(now)) {
            Booking booking = new Booking();
            for (String passengerName : listOfPassengerNames) {
                if (!booking.createReservation(f, passengerName, now)) {
                    return false;
                }
            }
            bookings.add(booking);
            return true;
        }
        return false;
    }

    /**
     * Confirms a reservation.
     * 
     * @param reservationID The ID of the reservation to be confirmed.
     * @param now           The current date and time.
     * @return true if the reservation is confirmed successfully, false otherwise.
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
     * @return true if the reservation is canceled successfully, false otherwise.
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

    /**
     * Gets the list of bookings for the customer.
     * 
     * @return The list of bookings.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the list of bookings for the customer.
     * 
     * @param bookings The list of bookings to be set.
     */
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
     * Constructor for Passenger.
     */
    public Passenger() {
    }

    /**
     * Gets the name of the passenger.
     * 
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the passenger.
     * 
     * @param name The name to be set.
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
     * Constructor for Booking.
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
     * @return true if the reservation is created successfully, false otherwise.
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (f.isOpenForBooking() && f.getDepartureTime().after(now)) {
            Reservation r = new Reservation();
            r.setId(UUID.randomUUID().toString());
            r.setStatus(ReservationStatus.PENDING);
            Passenger p = new Passenger();
            p.setName(passenger);
            r.setPassenger(p);
            r.setFlight(f);
            reservations.add(r);
            f.getReservations().add(r);
            return true;
        }
        return false;
    }

    /**
     * Gets the list of reservations for the booking.
     * 
     * @return The list of reservations.
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the list of reservations for the booking.
     * 
     * @param reservations The list of reservations to be set.
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Gets the customer who made the booking.
     * 
     * @return The customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who made the booking.
     * 
     * @param customer The customer to be set.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

/**
 * Enum for reservation status.
 */
enum ReservationStatus {
    PENDING, CONFIRMED, CANCELED
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
     * Constructor for Reservation.
     */
    public Reservation() {
    }

    /**
     * Gets the ID of the reservation.
     * 
     * @return The ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the reservation.
     * 
     * @param id The ID to be set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the status of the reservation.
     * 
     * @return The status.
     */
    public ReservationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the reservation.
     * 
     * @param status The status to be set.
     */
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    /**
     * Gets the passenger of the reservation.
     * 
     * @return The passenger.
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * Sets the passenger of the reservation.
     * 
     * @param passenger The passenger to be set.
     */
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    /**
     * Gets the flight of the reservation.
     * 
     * @return The flight.
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Sets the flight of the reservation.
     * 
     * @param flight The flight to be set.
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}