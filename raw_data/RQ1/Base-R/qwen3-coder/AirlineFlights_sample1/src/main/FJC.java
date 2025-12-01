import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents an airport with unique identifier and served cities.
 */
class Airport {
    private String id;
    private List<String> cities;

    /**
     * Default constructor for Airport.
     */
    public Airport() {
        this.cities = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    /**
     * Checks if the airport serves at least one city.
     *
     * @return true if the airport serves at least one city, false otherwise
     */
    public boolean servesValidCity() {
        return cities != null && !cities.isEmpty();
    }
}

/**
 * Represents a stopover with arrival and departure times at an airport.
 */
class Stopover {
    private Airport airport;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    /**
     * Default constructor for Stopover.
     */
    public Stopover() {}

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
 * Represents a flight with departure and arrival information.
 */
class Flight {
    private String id;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private List<Stopover> stopovers;
    private boolean isOpenForBooking;
    private boolean isPublished;

    /**
     * Default constructor for Flight.
     */
    public Flight() {
        this.stopovers = new ArrayList<>();
        this.isOpenForBooking = true;
        this.isPublished = false;
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

    public List<Stopover> getStopovers() {
        return stopovers;
    }

    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    public boolean isOpenForBooking() {
        return isOpenForBooking;
    }

    public void setOpenForBooking(boolean openForBooking) {
        isOpenForBooking = openForBooking;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    /**
     * Validates the format of timestamp strings.
     *
     * @param timestamp the timestamp string to validate
     * @return true if the timestamp is in valid format, false otherwise
     */
    public boolean isValidTimestampFormat(String timestamp) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime.parse(timestamp, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks temporal consistency of the flight.
     *
     * @return true if currentTime < departureTime < arrivalTime, false otherwise
     */
    public boolean hasTemporalConsistency() {
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(departureTime) && departureTime.isBefore(arrivalTime);
    }

    /**
     * Checks route integrity of the flight.
     *
     * @return true if departure airport is different from arrival airport, false otherwise
     */
    public boolean hasRouteIntegrity() {
        return departureAirport != null && arrivalAirport != null &&
               !departureAirport.getId().equals(arrivalAirport.getId());
    }
}

/**
 * Represents a passenger with a name.
 */
class Passenger {
    private String name;

    /**
     * Default constructor for Passenger.
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
 * Represents a reservation for a passenger on a flight.
 */
class Reservation {
    private String id;
    private Flight flight;
    private Passenger passenger;
    private String status; // "pending", "confirmed", "canceled"

    /**
     * Default constructor for Reservation.
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

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Confirms the reservation.
     */
    public void confirm() {
        this.status = "confirmed";
    }

    /**
     * Cancels the reservation.
     */
    public void cancel() {
        this.status = "canceled";
    }

    /**
     * Checks if the reservation is confirmed.
     *
     * @return true if confirmed, false otherwise
     */
    public boolean isConfirmed() {
        return "confirmed".equals(status);
    }

    /**
     * Checks if the reservation is pending.
     *
     * @return true if pending, false otherwise
     */
    public boolean isPending() {
        return "pending".equals(status);
    }
}

/**
 * Represents a booking made by a customer for one or more passengers on a flight.
 */
class Booking {
    private String id;
    private List<Reservation> reservations;

    /**
     * Default constructor for Booking.
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds a reservation to the booking.
     *
     * @param reservation the reservation to add
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}

/**
 * Manages airline operations including flight publishing, booking, and reservation management.
 */
class AirlineSystem {
    private List<Flight> flights;
    private List<Booking> bookings;

    /**
     * Default constructor for AirlineSystem.
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
     * Publishes a flight for booking after validating its properties.
     *
     * @param flight the flight to publish
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publishFlight(Flight flight) {
        // Check if flight is already published
        if (flight.isPublished()) {
            return false;
        }

        // Check if flight is still open for booking
        if (!flight.isOpenForBooking()) {
            return false;
        }

        // Validate departure and arrival timestamps format
        String departureStr = flight.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String arrivalStr = flight.getArrivalTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (!flight.isValidTimestampFormat(departureStr) || !flight.isValidTimestampFormat(arrivalStr)) {
            return false;
        }

        // Check temporal consistency
        if (!flight.hasTemporalConsistency()) {
            return false;
        }

        // Check route integrity
        if (!flight.hasRouteIntegrity()) {
            return false;
        }

        // If all validations pass, publish the flight
        flight.setPublished(true);
        return true;
    }

    /**
     * Creates a booking for passengers on a flight.
     *
     * @param flight the flight to book
     * @param passengers the list of passengers to book for
     * @return true if booking is successful, false otherwise
     */
    public boolean createBooking(Flight flight, List<Passenger> passengers) {
        // Check if flight is open for booking
        if (!flight.isOpenForBooking()) {
            return false;
        }

        // Check if current time is before flight departure time
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Check for duplicate passengers
        long distinctPassengers = passengers.stream().map(Passenger::getName).distinct().count();
        if (distinctPassengers != passengers.size()) {
            return false;
        }

        // Create booking and reservations
        Booking booking = new Booking();
        
        for (Passenger passenger : passengers) {
            Reservation reservation = new Reservation();
            reservation.setFlight(flight);
            reservation.setPassenger(passenger);
            booking.addReservation(reservation);
        }
        
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms or cancels an existing reservation in a booking.
     *
     * @param reservationId the ID of the reservation to update
     * @param confirm true to confirm, false to cancel
     * @return true if the operation is successful, false otherwise
     */
    public boolean updateReservationStatus(String reservationId, boolean confirm) {
        // Find the reservation
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationId)) {
                    Flight flight = reservation.getFlight();
                    
                    // Check if flight has not yet departed and is still open for booking
                    if (!flight.isOpenForBooking() || !LocalDateTime.now().isBefore(flight.getDepartureTime())) {
                        return false;
                    }
                    
                    // Update reservation status
                    if (confirm) {
                        reservation.confirm();
                    } else {
                        reservation.cancel();
                    }
                    
                    return true;
                }
            }
        }
        
        return false; // Reservation not found
    }

    /**
     * Closes an open flight and cancels all confirmed reservations.
     *
     * @param flight the flight to close
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight(Flight flight) {
        // Verify that the flight is currently open
        if (!flight.isOpenForBooking()) {
            return false;
        }

        // Change the status to closed
        flight.setOpenForBooking(false);

        // Cancel every confirmed reservation
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getFlight().getId().equals(flight.getId()) && reservation.isConfirmed()) {
                    reservation.cancel();
                }
            }
        }

        return true;
    }

    /**
     * Retrieves all confirmed reservations for a specific flight.
     *
     * @param flightId the ID of the flight
     * @return a list of confirmed reservations for the flight, or empty list if none exist
     */
    public List<Reservation> getConfirmedReservations(String flightId) {
        // Find the flight
        Flight targetFlight = null;
        for (Flight flight : flights) {
            if (flight.getId().equals(flightId)) {
                targetFlight = flight;
                break;
            }
        }

        // Check if flight exists and is open for booking
        if (targetFlight == null || !targetFlight.isOpenForBooking()) {
            return new ArrayList<>(); // Return empty list
        }

        // Collect confirmed reservations for this flight
        List<Reservation> confirmedReservations = new ArrayList<>();
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getFlight().getId().equals(flightId) && reservation.isConfirmed()) {
                    confirmedReservations.add(reservation);
                }
            }
        }

        return confirmedReservations;
    }

    /**
     * Adds a stopover to a flight.
     *
     * @param flight the flight to add stopover to
     * @param stopover the stopover to add
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopover(Flight flight, Stopover stopover) {
        // Check if flight has departed
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Check if airport serves valid cities
        if (!stopover.getAirport().servesValidCity()) {
            return false;
        }

        // Check if stopover times fit within flight schedule
        if (stopover.getArrivalTime().isBefore(flight.getDepartureTime()) ||
            stopover.getDepartureTime().isAfter(flight.getArrivalTime()) ||
            !stopover.getArrivalTime().isBefore(stopover.getDepartureTime())) {
            return false;
        }

        // Add stopover
        flight.getStopovers().add(stopover);
        return true;
    }

    /**
     * Deletes a stopover from a flight.
     *
     * @param flight the flight to delete stopover from
     * @param stopover the stopover to delete
     * @return true if the stopover is successfully deleted, false otherwise
     */
    public boolean deleteStopover(Flight flight, Stopover stopover) {
        // Check if flight has departed
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Remove stopover
        return flight.getStopovers().remove(stopover);
    }
}