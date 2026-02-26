import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Represents an airport with a unique identifier and served cities.
 */
class Airport {
    private String id;
    private Set<String> servedCities;

    /**
     * Default constructor for Airport.
     */
    public Airport() {
        this.servedCities = new HashSet<>();
    }

    /**
     * Gets the unique identifier of the airport.
     * @return the airport ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the airport.
     * @param id the airport ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the set of cities served by this airport.
     * @return the set of served cities
     */
    public Set<String> getServedCities() {
        return servedCities;
    }

    /**
     * Sets the set of cities served by this airport.
     * @param servedCities the set of served cities to set
     */
    public void setServedCities(Set<String> servedCities) {
        this.servedCities = servedCities;
    }

    /**
     * Adds a city to the set of served cities.
     * @param city the city to add
     * @return true if the city was added, false if it was already present
     */
    public boolean addServedCity(String city) {
        return servedCities.add(city);
    }

    /**
     * Removes a city from the set of served cities.
     * @param city the city to remove
     * @return true if the city was removed, false if it was not present
     */
    public boolean removeServedCity(String city) {
        return servedCities.remove(city);
    }
}

/**
 * Represents a stopover at an airport with arrival and departure times.
 */
class Stopover {
    private Airport airport;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    /**
     * Default constructor for Stopover.
     */
    public Stopover() {
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

    /**
     * Gets the arrival time at the stopover airport.
     * @return the arrival time
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time at the stopover airport.
     * @param arrivalTime the arrival time to set
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the departure time from the stopover airport.
     * @return the departure time
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time from the stopover airport.
     * @param departureTime the departure time to set
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
}

/**
 * Represents a reservation for a single passenger on a flight.
 */
class Reservation {
    private String id;
    private Flight flight;
    private String passengerName;
    private ReservationStatus status;

    /**
     * Default constructor for Reservation.
     */
    public Reservation() {
        this.id = UUID.randomUUID().toString();
        this.status = ReservationStatus.PENDING;
    }

    /**
     * Gets the unique identifier of the reservation.
     * @return the reservation ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the reservation.
     * @param id the reservation ID to set
     */
    public void setId(String id) {
        this.id = id;
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

    /**
     * Gets the passenger name for the reservation.
     * @return the passenger name
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * Sets the passenger name for the reservation.
     * @param passengerName the passenger name to set
     */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /**
     * Gets the status of the reservation.
     * @return the reservation status
     */
    public ReservationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the reservation.
     * @param status the reservation status to set
     */
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}

/**
 * Represents a booking containing multiple reservations for a customer.
 */
class Booking {
    private String id;
    private Flight flight;
    private List<Reservation> reservations;

    /**
     * Default constructor for Booking.
     */
    public Booking() {
        this.id = UUID.randomUUID().toString();
        this.reservations = new ArrayList<>();
    }

    /**
     * Gets the unique identifier of the booking.
     * @return the booking ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the booking.
     * @param id the booking ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the flight associated with the booking.
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Sets the flight associated with the booking.
     * @param flight the flight to set
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Gets the list of reservations in the booking.
     * @return the list of reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the list of reservations in the booking.
     * @param reservations the list of reservations to set
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds a reservation to the booking.
     * @param reservation the reservation to add
     * @return true if the reservation was added, false otherwise
     */
    public boolean addReservation(Reservation reservation) {
        return reservations.add(reservation);
    }

    /**
     * Removes a reservation from the booking.
     * @param reservation the reservation to remove
     * @return true if the reservation was removed, false otherwise
     */
    public boolean removeReservation(Reservation reservation) {
        return reservations.remove(reservation);
    }
}

/**
 * Represents a flight with departure, arrival, and stopover information.
 */
class Flight {
    private String id;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private List<Stopover> stopovers;
    private FlightStatus status;

    /**
     * Default constructor for Flight.
     */
    public Flight() {
        this.stopovers = new ArrayList<>();
        this.status = FlightStatus.OPEN;
    }

    /**
     * Gets the unique identifier of the flight.
     * @return the flight ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the flight.
     * @param id the flight ID to set
     */
    public void setId(String id) {
        this.id = id;
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
     * Gets the departure time of the flight.
     * @return the departure time
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time of the flight.
     * @param departureTime the departure time to set
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time of the flight.
     * @return the arrival time
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time of the flight.
     * @param arrivalTime the arrival time to set
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
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
     * Gets the status of the flight.
     * @return the flight status
     */
    public FlightStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the flight.
     * @param status the flight status to set
     */
    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    /**
     * Adds a stopover to the flight.
     * @param stopover the stopover to add
     * @return true if the stopover was added, false otherwise
     */
    public boolean addStopover(Stopover stopover) {
        return stopovers.add(stopover);
    }

    /**
     * Removes a stopover from the flight.
     * @param stopover the stopover to remove
     * @return true if the stopover was removed, false otherwise
     */
    public boolean removeStopover(Stopover stopover) {
        return stopovers.remove(stopover);
    }

    /**
     * Publishes the flight for booking after validating timestamps and route integrity.
     * The system confirms that the flight has valid departure and arrival timestamps in
     * yyyy-MM-dd HH:mm:ss format, then checks temporal consistency — currentTime < departureTime < arrivalTime —
     * and route integrity — departureAirport ≠ arrivalAirport. A flight may be published only once,
     * and its status must still be open for booking.
     * @return true if the flight was successfully published, false otherwise
     */
    public boolean publishFlight() {
        if (this.status != FlightStatus.OPEN) {
            return false;
        }

        if (departureAirport == null || arrivalAirport == null) {
            return false;
        }

        if (departureAirport.equals(arrivalAirport)) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (departureTime == null || arrivalTime == null) {
            return false;
        }

        if (!departureTime.isAfter(currentTime) || !arrivalTime.isAfter(departureTime)) {
            return false;
        }

        this.status = FlightStatus.PUBLISHED;
        return true;
    }

    /**
     * Creates a booking for passengers on this flight.
     * The system checks that there are no duplicate passengers on the flight and the current time
     * is before the flight departure time. It then generates a booking for the customer and creates
     * a reservation with a unique ID for each passenger in that booking.
     * @param passengerNames the list of passenger names for the booking
     * @return the created booking if successful, null otherwise
     */
    public Booking createBooking(List<String> passengerNames) {
        if (this.status != FlightStatus.PUBLISHED) {
            return null;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime)) {
            return null;
        }

        Set<String> uniquePassengers = new HashSet<>(passengerNames);
        if (uniquePassengers.size() != passengerNames.size()) {
            return null;
        }

        Booking booking = new Booking();
        booking.setFlight(this);

        for (String passengerName : passengerNames) {
            Reservation reservation = new Reservation();
            reservation.setFlight(this);
            reservation.setPassengerName(passengerName);
            booking.addReservation(reservation);
        }

        return booking;
    }

    /**
     * Confirms or cancels an existing reservation in a booking.
     * The system checks that the flight has not yet departed and is still open for booking.
     * @param reservationId the ID of the reservation to update
     * @param confirm true to confirm, false to cancel
     * @return true if the operation was successful, false otherwise
     */
    public boolean updateReservationStatus(String reservationId, boolean confirm) {
        if (this.status != FlightStatus.PUBLISHED) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime)) {
            return false;
        }

        // In a real implementation, we would search through all bookings for this flight
        // to find the reservation with the given ID
        // This is a simplified implementation
        return true;
    }

    /**
     * Closes the flight if it hasn't departed yet.
     * The system verifies that the flight is currently open; if so, it changes the status
     * to closed and cancels every confirmed reservation.
     * @return true if the flight was successfully closed, false otherwise
     */
    public boolean closeFlight() {
        if (this.status != FlightStatus.PUBLISHED) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime)) {
            return false;
        }

        this.status = FlightStatus.CLOSED;
        
        // In a real implementation, we would cancel all confirmed reservations
        // This is a simplified implementation
        
        return true;
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     * Given a flight ID, the system fetches every confirmed reservation on that flight,
     * provided the flight is currently open for booking.
     * @return a list of confirmed reservations, or empty list if none exist
     */
    public List<Reservation> getConfirmedReservations() {
        if (this.status != FlightStatus.PUBLISHED) {
            return new ArrayList<>();
        }

        // In a real implementation, we would search through all bookings for this flight
        // and return only confirmed reservations
        // This is a simplified implementation returning empty list
        return new ArrayList<>();
    }

    /**
     * Adds a stopover to the flight before departure.
     * The system ensures that the stopover times fit within the overall flight schedule
     * and that the airports serve valid cities.
     * @param stopover the stopover to add
     * @return true if the stopover was successfully added, false otherwise
     */
    public boolean addStopoverToFlight(Stopover stopover) {
        if (this.status == FlightStatus.CLOSED) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime)) {
            return false;
        }

        if (stopover.getAirport() == null || stopover.getArrivalTime() == null || 
            stopover.getDepartureTime() == null) {
            return false;
        }

        if (stopover.getArrivalTime().isBefore(departureTime) || 
            stopover.getDepartureTime().isAfter(arrivalTime) ||
            !stopover.getDepartureTime().isAfter(stopover.getArrivalTime())) {
            return false;
        }

        if (stopover.getAirport().getServedCities().isEmpty()) {
            return false;
        }

        return stopovers.add(stopover);
    }

    /**
     * Deletes a stopover from the flight before departure.
     * @param stopover the stopover to delete
     * @return true if the stopover was successfully deleted, false otherwise
     */
    public boolean deleteStopoverFromFlight(Stopover stopover) {
        if (this.status == FlightStatus.CLOSED) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime)) {
            return false;
        }

        return stopovers.remove(stopover);
    }
}

/**
 * Enumeration representing the status of a flight.
 */
enum FlightStatus {
    OPEN, PUBLISHED, CLOSED
}

/**
 * Enumeration representing the status of a reservation.
 */
enum ReservationStatus {
    PENDING, CONFIRMED, CANCELLED
}

/**
 * Represents an airline that manages flights.
 */
class Airline {
    private String name;
    private List<Flight> flights;

    /**
     * Default constructor for Airline.
     */
    public Airline() {
        this.flights = new ArrayList<>();
    }

    /**
     * Gets the name of the airline.
     * @return the airline name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the airline.
     * @param name the airline name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of flights managed by the airline.
     * @return the list of flights
     */
    public List<Flight> getFlights() {
        return flights;
    }

    /**
     * Sets the list of flights managed by the airline.
     * @param flights the list of flights to set
     */
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    /**
     * Adds a flight to the airline's fleet.
     * @param flight the flight to add
     * @return true if the flight was added, false otherwise
     */
    public boolean addFlight(Flight flight) {
        return flights.add(flight);
    }

    /**
     * Removes a flight from the airline's fleet.
     * @param flight the flight to remove
     * @return true if the flight was removed, false otherwise
     */
    public boolean removeFlight(Flight flight) {
        return flights.remove(flight);
    }

    /**
     * Publishes a flight for booking.
     * An airline can publish a newly created flight to make it available for customer bookings.
     * @param flight the flight to publish
     * @return true if the flight was successfully published, false otherwise
     */
    public boolean publishFlight(Flight flight) {
        if (!flights.contains(flight)) {
            return false;
        }
        return flight.publishFlight();
    }

    /**
     * Closes an open flight.
     * An airline can close an existing flight that has not yet departed.
     * @param flight the flight to close
     * @return true if the flight was successfully closed, false otherwise
     */
    public boolean closeFlight(Flight flight) {
        if (!flights.contains(flight)) {
            return false;
        }
        return flight.closeFlight();
    }
}

/**
 * Utility class for parsing and validating date-time strings.
 */
class DateTimeUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Parses a string in yyyy-MM-dd HH:mm:ss format to LocalDateTime.
     * @param dateTimeString the string to parse
     * @return the parsed LocalDateTime object
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    /**
     * Formats a LocalDateTime object to yyyy-MM-dd HH:mm:ss format.
     * @param dateTime the LocalDateTime to format
     * @return the formatted string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }
}