import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Represents an airport with a unique identifier and served cities
 */
class Airport {
    private String id;
    private Set<String> servedCities;

    /**
     * Default constructor
     */
    public Airport() {
        this.servedCities = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getServedCities() {
        return servedCities;
    }

    public void setServedCities(Set<String> servedCities) {
        this.servedCities = servedCities;
    }
}

/**
 * Represents a stopover with arrival and departure airports and times
 */
class Stopover {
    private Airport airport;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    /**
     * Default constructor
     */
    public Stopover() {
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
}

/**
 * Represents a flight with unique ID, airports, schedule, and booking status
 */
class Flight {
    private String id;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private boolean isOpenForBooking;
    private List<Stopover> stopovers;
    private List<Reservation> reservations;

    /**
     * Default constructor
     */
    public Flight() {
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.isOpenForBooking = false; // Initially closed
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isOpenForBooking() {
        return isOpenForBooking;
    }

    public void setOpenForBooking(boolean openForBooking) {
        isOpenForBooking = openForBooking;
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
     * Publishes the flight for booking after validating schedule and route integrity
     * @return true if publication successful, false otherwise
     */
    public boolean publishForBooking() {
        if (this.isOpenForBooking) {
            return false; // Already published
        }
        
        if (departureTime == null || arrivalTime == null) {
            return false; // Missing required timestamps
        }
        
        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime) || !departureTime.isBefore(arrivalTime)) {
            return false; // Invalid temporal consistency
        }
        
        if (departureAirport == null || arrivalAirport == null || 
            departureAirport.equals(arrivalAirport)) {
            return false; // Invalid route integrity
        }
        
        this.isOpenForBooking = true;
        return true;
    }

    /**
     * Closes the flight and cancels all confirmed reservations
     * @return true if closure successful, false otherwise
     */
    public boolean closeFlight() {
        if (!this.isOpenForBooking) {
            return false; // Already closed or never published
        }
        
        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime)) {
            return false; // Flight has already departed
        }
        
        this.isOpenForBooking = false;
        
        // Cancel all confirmed reservations
        for (Reservation reservation : reservations) {
            if ("confirmed".equals(reservation.getStatus())) {
                reservation.setStatus("canceled");
            }
        }
        
        return true;
    }

    /**
     * Adds a stopover to the flight with validation
     * @param stopover the stopover to add
     * @return true if addition successful, false otherwise
     */
    public boolean addStopover(Stopover stopover) {
        if (stopover == null || stopover.getAirport() == null || 
            stopover.getArrivalTime() == null || stopover.getDepartureTime() == null) {
            return false;
        }
        
        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime)) {
            return false; // Flight has already departed
        }
        
        // Validate stopover times fit within flight schedule
        if (!stopover.getArrivalTime().isAfter(departureTime) || 
            !stopover.getDepartureTime().isBefore(arrivalTime) ||
            !stopover.getArrivalTime().isBefore(stopover.getDepartureTime())) {
            return false;
        }
        
        // Validate airport serves valid cities
        if (stopover.getAirport().getServedCities() == null || 
            stopover.getAirport().getServedCities().isEmpty()) {
            return false;
        }
        
        stopovers.add(stopover);
        return true;
    }

    /**
     * Deletes a stopover from the flight
     * @param stopover the stopover to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteStopover(Stopover stopover) {
        if (stopover == null) {
            return false;
        }
        
        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime)) {
            return false; // Flight has already departed
        }
        
        return stopovers.remove(stopover);
    }

    /**
     * Retrieves all confirmed reservations for this flight
     * @return list of confirmed reservations, empty list if none exist
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmedReservations = new ArrayList<>();
        
        if (!this.isOpenForBooking) {
            return confirmedReservations; // Flight is not open for booking
        }
        
        for (Reservation reservation : reservations) {
            if ("confirmed".equals(reservation.getStatus())) {
                confirmedReservations.add(reservation);
            }
        }
        
        return confirmedReservations;
    }
}

/**
 * Represents a reservation for a single passenger on a flight
 */
class Reservation {
    private String id;
    private Flight flight;
    private String passengerName;
    private String status; // "pending", "confirmed", "canceled"
    private Booking booking;

    /**
     * Default constructor
     */
    public Reservation() {
        this.id = UUID.randomUUID().toString();
        this.status = "pending";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * Confirms or cancels the reservation
     * @param confirm true to confirm, false to cancel
     * @return true if operation successful, false otherwise
     */
    public boolean confirmOrCancel(boolean confirm) {
        if (flight == null) {
            return false;
        }
        
        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(flight.getDepartureTime())) {
            return false; // Flight has already departed
        }
        
        if (!flight.isOpenForBooking()) {
            return false; // Flight is not open for booking
        }
        
        if (confirm) {
            this.status = "confirmed";
        } else {
            this.status = "canceled";
        }
        
        return true;
    }
}

/**
 * Represents a booking containing multiple reservations for a customer
 */
class Booking {
    private String id;
    private Flight flight;
    private List<Reservation> reservations;
    private String customerId;

    /**
     * Default constructor
     */
    public Booking() {
        this.id = UUID.randomUUID().toString();
        this.reservations = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Creates a booking for passengers on a flight
     * @param flight the flight to book
     * @param passengerNames list of passenger names
     * @return true if booking creation successful, false otherwise
     */
    public boolean createBooking(Flight flight, List<String> passengerNames) {
        if (flight == null || passengerNames == null || passengerNames.isEmpty()) {
            return false;
        }
        
        if (!flight.isOpenForBooking()) {
            return false; // Flight is not open for booking
        }
        
        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(flight.getDepartureTime())) {
            return false; // Flight has already departed
        }
        
        // Check for duplicate passengers
        Set<String> uniquePassengers = new HashSet<>(passengerNames);
        if (uniquePassengers.size() != passengerNames.size()) {
            return false; // Duplicate passengers found
        }
        
        this.flight = flight;
        
        // Create reservations for each passenger
        for (String passengerName : passengerNames) {
            Reservation reservation = new Reservation();
            reservation.setFlight(flight);
            reservation.setPassengerName(passengerName);
            reservation.setBooking(this);
            reservations.add(reservation);
            flight.getReservations().add(reservation);
        }
        
        return true;
    }
}

/**
 * Main system class managing flights, bookings, and reservations
 */
class AirlineSystem {
    private List<Flight> flights;
    private List<Booking> bookings;

    /**
     * Default constructor
     */
    public AirlineSystem() {
        this.flights = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Publishes a flight for booking
     * @param flight the flight to publish
     * @return true if publication successful, false otherwise
     */
    public boolean publishFlight(Flight flight) {
        if (flight == null || flights.contains(flight)) {
            return false;
        }
        
        boolean success = flight.publishForBooking();
        if (success) {
            flights.add(flight);
        }
        return success;
    }

    /**
     * Creates a booking for passengers on a flight
     * @param flight the flight to book
     * @param passengerNames list of passenger names
     * @param customerId the customer identifier
     * @return true if booking creation successful, false otherwise
     */
    public boolean createBooking(Flight flight, List<String> passengerNames, String customerId) {
        if (flight == null || passengerNames == null || passengerNames.isEmpty() || customerId == null) {
            return false;
        }
        
        Booking booking = new Booking();
        booking.setCustomerId(customerId);
        boolean success = booking.createBooking(flight, passengerNames);
        
        if (success) {
            bookings.add(booking);
        }
        
        return success;
    }

    /**
     * Confirms or cancels an existing reservation
     * @param reservationId the reservation identifier
     * @param confirm true to confirm, false to cancel
     * @return true if operation successful, false otherwise
     */
    public boolean confirmOrCancelReservation(String reservationId, boolean confirm) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationId)) {
                    return reservation.confirmOrCancel(confirm);
                }
            }
        }
        return false; // Reservation not found
    }

    /**
     * Closes an open flight
     * @param flightId the flight identifier
     * @return true if closure successful, false otherwise
     */
    public boolean closeFlight(String flightId) {
        for (Flight flight : flights) {
            if (flight.getId().equals(flightId)) {
                return flight.closeFlight();
            }
        }
        return false; // Flight not found
    }

    /**
     * Retrieves all confirmed reservations for a specific flight
     * @param flightId the flight identifier
     * @return list of confirmed reservations, empty list if none exist
     */
    public List<Reservation> getConfirmedReservations(String flightId) {
        for (Flight flight : flights) {
            if (flight.getId().equals(flightId)) {
                return flight.getConfirmedReservations();
            }
        }
        return new ArrayList<>(); // Flight not found
    }

    /**
     * Adds a stopover to a flight
     * @param flightId the flight identifier
     * @param stopover the stopover to add
     * @return true if addition successful, false otherwise
     */
    public boolean addStopover(String flightId, Stopover stopover) {
        for (Flight flight : flights) {
            if (flight.getId().equals(flightId)) {
                return flight.addStopover(stopover);
            }
        }
        return false; // Flight not found
    }

    /**
     * Deletes a stopover from a flight
     * @param flightId the flight identifier
     * @param stopover the stopover to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteStopover(String flightId, Stopover stopover) {
        for (Flight flight : flights) {
            if (flight.getId().equals(flightId)) {
                return flight.deleteStopover(stopover);
            }
        }
        return false; // Flight not found
    }
}