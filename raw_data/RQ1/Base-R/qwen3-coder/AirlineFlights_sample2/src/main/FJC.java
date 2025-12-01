import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents an airport with unique ID and served cities.
 */
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
     * Checks if this airport serves at least one city.
     *
     * @return true if the airport serves at least one city, false otherwise
     */
    public boolean servesCities() {
        return cities != null && !cities.isEmpty();
    }
}

/**
 * Represents a stopover in a flight with arrival and departure times.
 */
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
     * Checks if the flight has valid temporal consistency.
     *
     * @return true if departureTime < arrivalTime, false otherwise
     */
    public boolean hasValidTemporalConsistency() {
        return departureTime != null && arrivalTime != null && departureTime.isBefore(arrivalTime);
    }

    /**
     * Checks if the route is valid (departure airport is different from arrival airport).
     *
     * @return true if departure airport is different from arrival airport, false otherwise
     */
    public boolean hasValidRoute() {
        return departureAirport != null && arrivalAirport != null && 
               !departureAirport.getId().equals(arrivalAirport.getId());
    }
}

/**
 * Represents a reservation for a passenger on a flight.
 */
class Reservation {
    private String id;
    private String passengerName;
    private Flight flight;
    private String status; // "pending", "confirmed", "canceled"

    public Reservation() {
        this.status = "pending";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Confirms this reservation.
     */
    public void confirm() {
        this.status = "confirmed";
    }

    /**
     * Cancels this reservation.
     */
    public void cancel() {
        this.status = "canceled";
    }

    /**
     * Checks if this reservation is confirmed.
     *
     * @return true if the reservation is confirmed, false otherwise
     */
    public boolean isConfirmed() {
        return "confirmed".equals(status);
    }

    /**
     * Checks if this reservation is pending.
     *
     * @return true if the reservation is pending, false otherwise
     */
    public boolean isPending() {
        return "pending".equals(status);
    }
}

/**
 * Represents a booking made by a customer for one or more passengers.
 */
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
     * Adds a reservation to this booking.
     *
     * @param reservation the reservation to add
     */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
}

/**
 * Manages flights, bookings, and reservations for an airline.
 */
class AirlineSystem {
    private List<Flight> flights;
    private List<Booking> bookings;

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

        // Check if flight is open for booking
        if (!flight.isOpenForBooking()) {
            return false;
        }

        // Check temporal consistency
        if (!flight.hasValidTemporalConsistency()) {
            return false;
        }

        // Check route integrity
        if (!flight.hasValidRoute()) {
            return false;
        }

        // Check that current time is before departure time
        if (flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            return false;
        }

        flight.setPublished(true);
        return true;
    }

    /**
     * Creates a booking for passengers on a flight.
     *
     * @param flight the flight to book passengers on
     * @param passengerNames list of passenger names
     * @return true if booking is successful, false otherwise
     */
    public boolean createBooking(Flight flight, List<String> passengerNames) {
        // Check if flight is open for booking
        if (!flight.isOpenForBooking()) {
            return false;
        }

        // Check if current time is before flight departure time
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Check for duplicate passengers
        long uniquePassengers = passengerNames.stream().distinct().count();
        if (uniquePassengers != passengerNames.size()) {
            return false;
        }

        // Check if any of these passengers already have reservations on this flight
        List<String> existingPassengers = bookings.stream()
                .flatMap(booking -> booking.getReservations().stream())
                .filter(reservation -> reservation.getFlight().getId().equals(flight.getId()))
                .map(Reservation::getPassengerName)
                .collect(Collectors.toList());

        for (String passenger : passengerNames) {
            if (existingPassengers.contains(passenger)) {
                return false;
            }
        }

        // Create booking and reservations
        Booking booking = new Booking();
        for (String passengerName : passengerNames) {
            Reservation reservation = new Reservation();
            reservation.setId(UUID.randomUUID().toString());
            reservation.setPassengerName(passengerName);
            reservation.setFlight(flight);
            booking.addReservation(reservation);
        }

        bookings.add(booking);
        return true;
    }

    /**
     * Confirms or cancels an existing reservation.
     *
     * @param reservationId the ID of the reservation to update
     * @param confirm true to confirm, false to cancel
     * @return true if the operation is successful, false otherwise
     */
    public boolean confirmOrCancelReservation(String reservationId, boolean confirm) {
        // Find the reservation
        Reservation reservation = bookings.stream()
                .flatMap(booking -> booking.getReservations().stream())
                .filter(r -> r.getId().equals(reservationId))
                .findFirst()
                .orElse(null);

        if (reservation == null) {
            return false;
        }

        Flight flight = reservation.getFlight();

        // Check if flight has not yet departed and is still open for booking
        if (!flight.isOpenForBooking() || !LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        if (confirm) {
            reservation.confirm();
        } else {
            reservation.cancel();
        }

        return true;
    }

    /**
     * Closes an open flight and cancels all confirmed reservations.
     *
     * @param flightId the ID of the flight to close
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId) {
        // Find the flight
        Flight flight = flights.stream()
                .filter(f -> f.getId().equals(flightId))
                .findFirst()
                .orElse(null);

        if (flight == null || !flight.isOpenForBooking()) {
            return false;
        }

        // Check if flight has not yet departed
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Close the flight
        flight.setOpenForBooking(false);

        // Cancel all confirmed reservations for this flight
        bookings.stream()
                .flatMap(booking -> booking.getReservations().stream())
                .filter(reservation -> reservation.getFlight().getId().equals(flightId))
                .filter(Reservation::isConfirmed)
                .forEach(Reservation::cancel);

        return true;
    }

    /**
     * Retrieves all confirmed reservations for a specific flight.
     *
     * @param flightId the ID of the flight
     * @return list of confirmed reservations, or empty list if none exist
     */
    public List<Reservation> getConfirmedReservations(String flightId) {
        // Find the flight
        Flight flight = flights.stream()
                .filter(f -> f.getId().equals(flightId))
                .findFirst()
                .orElse(null);

        if (flight == null || !flight.isOpenForBooking()) {
            return new ArrayList<>();
        }

        return bookings.stream()
                .flatMap(booking -> booking.getReservations().stream())
                .filter(reservation -> reservation.getFlight().getId().equals(flightId))
                .filter(Reservation::isConfirmed)
                .collect(Collectors.toList());
    }

    /**
     * Adds a stopover to a flight.
     *
     * @param flight the flight to add stopover to
     * @param stopover the stopover to add
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopover(Flight flight, Stopover stopover) {
        // Check if flight has not yet departed
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Check if flight is open for booking
        if (!flight.isOpenForBooking()) {
            return false;
        }

        // Check that the airport serves valid cities
        if (!stopover.getAirport().servesCities()) {
            return false;
        }

        // Check that stopover times fit within the overall flight schedule
        if (stopover.getArrivalTime().isBefore(flight.getDepartureTime()) ||
            stopover.getDepartureTime().isAfter(flight.getArrivalTime()) ||
            stopover.getArrivalTime().isAfter(stopover.getDepartureTime())) {
            return false;
        }

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
        // Check if flight has not yet departed
        if (!LocalDateTime.now().isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Check if flight is open for booking
        if (!flight.isOpenForBooking()) {
            return false;
        }

        return flight.getStopovers().remove(stopover);
    }
}