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

// Airport class
class Airport {
    private String id;
    private List<String> cities;

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

// Stopover class
class Stopover {
    private Airport airport;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

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

// Flight class
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
     * Validates the flight's temporal and route integrity.
     *
     * @return true if the flight has valid timestamps and route, false otherwise
     */
    public boolean validateFlight() {
        // Check if departure and arrival airports are different
        if (departureAirport == null || arrivalAirport == null || 
            departureAirport.getId().equals(arrivalAirport.getId())) {
            return false;
        }

        // Check if timestamps are valid
        if (departureTime == null || arrivalTime == null) {
            return false;
        }

        // Check temporal consistency
        LocalDateTime now = LocalDateTime.now();
        if (!now.isBefore(departureTime) || !departureTime.isBefore(arrivalTime)) {
            return false;
        }

        return true;
    }

    /**
     * Publishes the flight for booking if it meets all requirements.
     *
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publishForBooking() {
        // Check if flight is already published or closed
        if (status != FlightStatus.OPEN) {
            return false;
        }

        // Validate flight details
        if (!validateFlight()) {
            return false;
        }

        // If all validations pass, keep the flight open for booking
        return true;
    }

    /**
     * Closes the flight and cancels all confirmed reservations.
     *
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight() {
        // Check if flight is open
        if (status != FlightStatus.OPEN) {
            return false;
        }

        // Check if flight has departed
        if (!LocalDateTime.now().isBefore(departureTime)) {
            return false;
        }

        // Close the flight
        status = FlightStatus.CLOSED;

        // Cancel all confirmed reservations
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                reservation.setStatus(ReservationStatus.CANCELED);
            }
        }

        return true;
    }

    /**
     * Adds a stopover to the flight.
     *
     * @param stopover the stopover to add
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopover(Stopover stopover) {
        // Check if flight has departed
        if (!LocalDateTime.now().isBefore(departureTime)) {
            return false;
        }

        // Check if stopover airport serves valid cities
        if (!stopover.getAirport().servesValidCity()) {
            return false;
        }

        // Check if stopover times fit within flight schedule
        if (stopover.getArrivalTime().isBefore(departureTime) || 
            stopover.getDepartureTime().isAfter(arrivalTime) ||
            !stopover.getArrivalTime().isBefore(stopover.getDepartureTime)) {
            return false;
        }

        stopovers.add(stopover);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     *
     * @param stopover the stopover to remove
     * @return true if the stopover is successfully removed, false otherwise
     */
    public boolean removeStopover(Stopover stopover) {
        // Check if flight has departed
        if (!LocalDateTime.now().isBefore(departureTime)) {
            return false;
        }

        return stopovers.remove(stopover);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     *
     * @return a list of confirmed reservations
     */
    public List<Reservation> getConfirmedReservations() {
        if (status != FlightStatus.OPEN) {
            return new ArrayList<>(); // Return empty list if flight is not open
        }
        
        return reservations.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.CONFIRMED)
                .collect(Collectors.toList());
    }
}

// Passenger class
class Passenger {
    private String name;

    public Passenger() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

// Reservation class
class Reservation {
    private String id;
    private Flight flight;
    private Passenger passenger;
    private ReservationStatus status;

    public Reservation() {
        this.status = ReservationStatus.PENDING;
        this.id = UUID.randomUUID().toString(); // Generate unique ID
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

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    /**
     * Confirms the reservation if the flight has not yet departed and is still open.
     *
     * @return true if the reservation is successfully confirmed, false otherwise
     */
    public boolean confirm() {
        if (flight.getStatus() != FlightStatus.OPEN || 
            !LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }
        
        status = ReservationStatus.CONFIRMED;
        return true;
    }

    /**
     * Cancels the reservation if the flight has not yet departed and is still open.
     *
     * @return true if the reservation is successfully canceled, false otherwise
     */
    public boolean cancel() {
        if (flight.getStatus() != FlightStatus.OPEN || 
            !LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }
        
        status = ReservationStatus.CANCELED;
        return true;
    }
}

// Booking class
class Booking {
    private List<Reservation> reservations;

    public Booking() {
        this.reservations = new ArrayList<>();
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Creates reservations for passengers on a flight.
     *
     * @param flight the flight to book
     * @param passengers the list of passengers
     * @return true if booking is successful, false otherwise
     */
    public boolean createBooking(Flight flight, List<Passenger> passengers) {
        // Check if flight is open for booking
        if (flight.getStatus() != FlightStatus.OPEN) {
            return false;
        }

        // Check if current time is before flight departure
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Check for duplicate passengers
        List<String> passengerNames = passengers.stream()
                .map(Passenger::getName)
                .collect(Collectors.toList());
        
        long distinctNames = passengerNames.stream().distinct().count();
        if (distinctNames != passengerNames.size()) {
            return false; // Duplicate passengers found
        }

        // Create reservations for each passenger
        reservations.clear(); // Clear any existing reservations
        for (Passenger passenger : passengers) {
            Reservation reservation = new Reservation();
            reservation.setFlight(flight);
            reservation.setPassenger(passenger);
            reservations.add(reservation);
            
            // Add reservation to flight's reservation list
            flight.getReservations().add(reservation);
        }

        return true;
    }
}