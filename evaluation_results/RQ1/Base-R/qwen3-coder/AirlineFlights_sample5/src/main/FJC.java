import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// Enumerations for status tracking
enum FlightStatus {
    OPEN, CLOSED
}

enum ReservationStatus {
    PENDING, CONFIRMED, CANCELED
}

// Base classes
class Airport {
    private String id;
    private List<String> cities;

    public Airport() {
        this.cities = new ArrayList<>();
    }

    // Getters and setters
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

    public void addCity(String city) {
        if (!cities.contains(city)) {
            cities.add(city);
        }
    }
}

class Stopover {
    private Airport airport;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public Stopover() {}

    // Getters and setters
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

class Flight {
    private String id;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightStatus status;
    private List<Stopover> stopovers;
    private List<Reservation> reservations;

    public Flight() {
        this.status = FlightStatus.OPEN;
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    // Getters and setters
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

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
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
     * Publishes a flight for booking after validating all necessary conditions.
     *
     * @return true if the flight was successfully published, false otherwise
     */
    public boolean publishForBooking() {
        // Check if flight is already published or closed
        if (this.status != FlightStatus.OPEN) {
            return false;
        }

        // Validate timestamps format and consistency
        if (departureTime == null || arrivalTime == null) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (!(currentTime.isBefore(departureTime) && departureTime.isBefore(arrivalTime))) {
            return false;
        }

        // Check route integrity
        if (departureAirport == null || arrivalAirport == null || 
            departureAirport.getId().equals(arrivalAirport.getId())) {
            return false;
        }

        return true;
    }

    /**
     * Closes an open flight and cancels all confirmed reservations.
     *
     * @return true if the flight was successfully closed, false otherwise
     */
    public boolean closeFlight() {
        if (this.status != FlightStatus.OPEN) {
            return false;
        }

        this.status = FlightStatus.CLOSED;
        
        // Cancel all confirmed reservations
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                reservation.setStatus(ReservationStatus.CANCELED);
            }
        }
        
        return true;
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     *
     * @return list of confirmed reservations
     */
    public List<Reservation> getConfirmedReservations() {
        if (this.status != FlightStatus.OPEN) {
            return new ArrayList<>();
        }
        
        return reservations.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

    /**
     * Adds a stopover to the flight if it fits within the schedule and has valid airports.
     *
     * @param stopover the stopover to add
     * @return true if the stopover was added successfully, false otherwise
     */
    public boolean addStopover(Stopover stopover) {
        if (status != FlightStatus.OPEN || 
            stopover.getAirport() == null || 
            stopover.getAirport().getCities().isEmpty() ||
            stopover.getArrivalTime() == null || 
            stopover.getDepartureTime() == null ||
            !stopover.getArrivalTime().isBefore(stopover.getDepartureTime())) {
            return false;
        }

        // Check that stopover fits within flight schedule
        if (!departureTime.isBefore(stopover.getArrivalTime()) || 
            !stopover.getDepartureTime().isBefore(arrivalTime)) {
            return false;
        }

        // Check that stopover times don't conflict with existing stopovers
        for (Stopover existing : stopovers) {
            if (!((stopover.getDepartureTime().isBefore(existing.getArrivalTime())) || 
                  (stopover.getArrivalTime().isAfter(existing.getDepartureTime())))) {
                return false;
            }
        }

        stopovers.add(stopover);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     *
     * @param stopover the stopover to remove
     * @return true if the stopover was removed successfully, false otherwise
     */
    public boolean removeStopover(Stopover stopover) {
        if (status != FlightStatus.OPEN) {
            return false;
        }
        
        return stopovers.remove(stopover);
    }
}

class Passenger {
    private String name;

    public Passenger() {}

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Reservation {
    private String id;
    private Flight flight;
    private Passenger passenger;
    private ReservationStatus status;

    public Reservation() {
        this.id = UUID.randomUUID().toString();
        this.status = ReservationStatus.PENDING;
    }

    // Getters and setters
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

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}

class Booking {
    private List<Reservation> reservations;

    public Booking() {
        this.reservations = new ArrayList<>();
    }

    // Getters and setters
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}

class Customer {
    private List<Booking> bookings;

    public Customer() {
        this.bookings = new ArrayList<>();
    }

    // Getters and setters
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Creates a booking for passengers on a flight.
     *
     * @param flight the flight to book
     * @param passengers list of passengers
     * @return true if booking was created successfully, false otherwise
     */
    public boolean createBooking(Flight flight, List<Passenger> passengers) {
        // Check if flight is open and hasn't departed
        if (flight.getStatus() != FlightStatus.OPEN || 
            !LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Check for duplicate passengers
        long distinctPassengers = passengers.stream()
                .map(Passenger::getName)
                .distinct()
                .count();
        
        if (distinctPassengers != passengers.size()) {
            return false;
        }

        // Create booking and reservations
        Booking booking = new Booking();
        
        for (Passenger passenger : passengers) {
            Reservation reservation = new Reservation();
            reservation.setFlight(flight);
            reservation.setPassenger(passenger);
            reservation.setStatus(ReservationStatus.PENDING);
            
            booking.getReservations().add(reservation);
            flight.getReservations().add(reservation);
        }
        
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms or cancels an existing reservation in a booking.
     *
     * @param reservationId the ID of the reservation to update
     * @param confirm true to confirm, false to cancel
     * @return true if the operation was successful, false otherwise
     */
    public boolean updateReservationStatus(String reservationId, boolean confirm) {
        Reservation targetReservation = null;
        
        // Find reservation across all bookings
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationId)) {
                    targetReservation = reservation;
                    break;
                }
            }
            if (targetReservation != null) break;
        }
        
        if (targetReservation == null) {
            return false;
        }
        
        Flight flight = targetReservation.getFlight();
        
        // Check if flight has departed or is closed
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime()) || 
            flight.getStatus() != FlightStatus.OPEN) {
            return false;
        }
        
        if (confirm) {
            targetReservation.setStatus(ReservationStatus.CONFIRMED);
        } else {
            targetReservation.setStatus(ReservationStatus.CANCELED);
        }
        
        return true;
    }
}