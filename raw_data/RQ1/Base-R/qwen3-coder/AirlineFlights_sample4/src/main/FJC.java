import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents an Airport with unique ID serving one or more cities.
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
    public boolean hasCities() {
        return cities != null && !cities.isEmpty();
    }
}

/**
 * Represents a Stopover at an airport with arrival and departure times.
 */
class Stopover {
    private Airport airport;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

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
 * Represents a Flight with unique ID, departure/arrival airports and times.
 */
class Flight {
    private String id;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private boolean isOpenForBooking;
    private boolean isPublished;
    private List<Stopover> stopovers;
    private List<Reservation> reservations;

    public Flight() {
        this.isOpenForBooking = true;
        this.isPublished = false;
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

    public boolean isOpenForBooking() {
        return isOpenForBooking;
    }

    public void setOpenForBooking(boolean openForBooking) {
        this.isOpenForBooking = openForBooking;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
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
     * Checks if the flight has valid departure and arrival timestamps and temporal consistency.
     *
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        if (departureTime == null || arrivalTime == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(departureTime) && departureTime.isBefore(arrivalTime);
    }

    /**
     * Checks if route integrity is maintained (departureAirport ≠ arrivalAirport).
     *
     * @return true if valid, false otherwise
     */
    public boolean hasValidRoute() {
        return departureAirport != null && arrivalAirport != null &&
                !departureAirport.getId().equals(arrivalAirport.getId());
    }

    /**
     * Checks if all stopovers have valid airports that serve cities.
     *
     * @return true if all stopovers are valid, false otherwise
     */
    public boolean areStopoversValid() {
        for (Stopover stopover : stopovers) {
            if (stopover.getAirport() == null || !stopover.getAirport().hasCities()) {
                return false;
            }
            if (stopover.getArrivalTime() == null || stopover.getDepartureTime() == null) {
                return false;
            }
            if (!stopover.getArrivalTime().isBefore(stopover.getDepartureTime())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates that stopover times fit within the overall flight schedule.
     *
     * @return true if stopover times are valid, false otherwise
     */
    public boolean validateStopoverTimes() {
        if (stopovers.isEmpty()) {
            return true;
        }

        // Sort stopovers by arrival time
        List<Stopover> sorted = new ArrayList<>(stopovers);
        sorted.sort((s1, s2) -> s1.getArrivalTime().compareTo(s2.getArrivalTime()));

        // Check first stopover arrives after flight departure
        if (!departureTime.isBefore(sorted.get(0).getArrivalTime())) {
            return false;
        }

        // Check last stopover departs before flight arrival
        if (!sorted.get(sorted.size() - 1).getDepartureTime().isBefore(arrivalTime)) {
            return false;
        }

        // Check consecutive stopovers
        for (int i = 0; i < sorted.size() - 1; i++) {
            if (!sorted.get(i).getDepartureTime().isBefore(sorted.get(i + 1).getArrivalTime())) {
                return false;
            }
        }

        return true;
    }
}

/**
 * Represents a Reservation for a passenger on a flight.
 */
class Reservation {
    public static final String PENDING = "pending";
    public static final String CONFIRMED = "confirmed";
    public static final String CANCELED = "canceled";

    private String id;
    private Flight flight;
    private String passengerName;
    private String status;

    public Reservation() {
        this.status = PENDING;
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

    /**
     * Checks if the reservation can be confirmed or canceled.
     *
     * @return true if the flight has not yet departed and is still open for booking, false otherwise
     */
    public boolean canBeModified() {
        if (flight == null || !flight.isOpenForBooking()) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(flight.getDepartureTime());
    }
}

/**
 * Represents a Booking which contains multiple reservations.
 */
class Booking {
    private String id;
    private List<Reservation> reservations;

    public Booking() {
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
}

/**
 * Main system class that handles flight operations.
 */
 class FlightSystem {
    private List<Flight> flights;
    private List<Booking> bookings;

    public FlightSystem() {
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
     * Publishes a flight for booking.
     * 
     * The method first confirms that the flight has valid departure and arrival timestamps,
     * then checks temporal consistency and route integrity. A flight may be published only once,
     * and its status must still be open for booking.
     *
     * @param flight the flight to publish
     * @return true on success, false otherwise
     */
    public boolean publishFlight(Flight flight) {
        if (flight == null || flight.isPublished() || !flight.isOpenForBooking()) {
            return false;
        }

        if (!flight.isValid() || !flight.hasValidRoute() || !flight.areStopoversValid() || !flight.validateStopoverTimes()) {
            return false;
        }

        flight.setPublished(true);
        return true;
    }

    /**
     * Creates a booking for passengers on a flight.
     * 
     * The system checks that there are no duplicate passengers on the flight and the current time
     * is before the flight departure time. It then generates a booking and creates a reservation
     * with a unique ID for each passenger.
     *
     * @param flight the flight to book
     * @param passengerNames list of passenger names
     * @return true on success, false otherwise
     */
    public boolean createBooking(Flight flight, List<String> passengerNames) {
        if (flight == null || passengerNames == null || passengerNames.isEmpty()) {
            return false;
        }

        if (!flight.isOpenForBooking() || flight.isPublished() == false) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        if (!now.isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Check for duplicate passengers in existing reservations for this flight
        List<String> existingPassengers = flight.getReservations().stream()
                .map(Reservation::getPassengerName)
                .collect(Collectors.toList());

        for (String passenger : passengerNames) {
            if (existingPassengers.contains(passenger)) {
                return false;
            }
        }

        // Create booking
        Booking booking = new Booking();
        booking.setId(UUID.randomUUID().toString());

        // Create reservations for each passenger
        for (String passengerName : passengerNames) {
            Reservation reservation = new Reservation();
            reservation.setId(UUID.randomUUID().toString());
            reservation.setFlight(flight);
            reservation.setPassengerName(passengerName);
            reservation.setStatus(Reservation.PENDING);
            booking.getReservations().add(reservation);
            flight.getReservations().add(reservation);
        }

        bookings.add(booking);
        return true;
    }

    /**
     * Confirms or cancels an existing reservation in a booking.
     * 
     * The system checks that the flight has not yet departed and is still open for booking.
     * The customer can then confirm or cancel a reservation by providing its reservation ID.
     *
     * @param reservationId the ID of the reservation to modify
     * @param confirm true to confirm, false to cancel
     * @return true on success, false otherwise
     */
    public boolean modifyReservation(String reservationId, boolean confirm) {
        if (reservationId == null) {
            return false;
        }

        Reservation reservation = null;
        for (Flight flight : flights) {
            for (Reservation res : flight.getReservations()) {
                if (res.getId().equals(reservationId)) {
                    reservation = res;
                    break;
                }
            }
            if (reservation != null) break;
        }

        if (reservation == null || !reservation.canBeModified()) {
            return false;
        }

        if (confirm) {
            reservation.setStatus(Reservation.CONFIRMED);
        } else {
            reservation.setStatus(Reservation.CANCELED);
        }

        return true;
    }

    /**
     * Closes an open flight.
     * 
     * An airline can close an existing flight that has not yet departed.
     * The system verifies that the flight is currently open; if so, it changes
     * the status to closed and cancels every confirmed reservation.
     *
     * @param flightId the ID of the flight to close
     * @return true on success, false otherwise
     */
    public boolean closeFlight(String flightId) {
        if (flightId == null) {
            return false;
        }

        Flight flight = null;
        for (Flight f : flights) {
            if (f.getId().equals(flightId)) {
                flight = f;
                break;
            }
        }

        if (flight == null || !flight.isOpenForBooking()) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        if (!now.isBefore(flight.getDepartureTime())) {
            return false;
        }

        flight.setOpenForBooking(false);

        // Cancel every confirmed reservation
        for (Reservation reservation : flight.getReservations()) {
            if (Reservation.CONFIRMED.equals(reservation.getStatus())) {
                reservation.setStatus(Reservation.CANCELED);
            }
        }

        return true;
    }

    /**
     * Retrieves all confirmed reservations for a specific flight.
     * 
     * Given a flight ID, the system fetches every confirmed reservation on that flight,
     * provided the flight is currently open for booking.
     *
     * @param flightId the ID of the flight
     * @return a list of confirmed reservations, or empty list if none exist
     */
    public List<Reservation> getConfirmedReservations(String flightId) {
        if (flightId == null) {
            return new ArrayList<>();
        }

        Flight flight = null;
        for (Flight f : flights) {
            if (f.getId().equals(flightId)) {
                flight = f;
                break;
            }
        }

        if (flight == null || !flight.isOpenForBooking()) {
            return new ArrayList<>();
        }

        return flight.getReservations().stream()
                .filter(res -> Reservation.CONFIRMED.equals(res.getStatus()))
                .collect(Collectors.toList());
    }

    /**
     * Adds a stopover to a flight.
     * 
     * Before the flight departs, the system can add a stopover on a flight,
     * specifying each stop's airport along with its arrival and departure times.
     * The system ensures that the stopover times fit within the overall flight schedule
     * and that the airports serve valid cities.
     *
     * @param flight the flight to add stopover to
     * @param stopover the stopover to add
     * @return true on success, false otherwise
     */
    public boolean addStopover(Flight flight, Stopover stopover) {
        if (flight == null || stopover == null) {
            return false;
        }

        if (!flight.isOpenForBooking()) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        if (!now.isBefore(flight.getDepartureTime())) {
            return false;
        }

        if (stopover.getAirport() == null || !stopover.getAirport().hasCities()) {
            return false;
        }

        if (stopover.getArrivalTime() == null || stopover.getDepartureTime() == null) {
            return false;
        }

        if (!stopover.getArrivalTime().isBefore(stopover.getDepartureTime())) {
            return false;
        }

        // Add stopover and validate all stopovers
        flight.getStopovers().add(stopover);
        if (!flight.validateStopoverTimes()) {
            flight.getStopovers().remove(stopover);
            return false;
        }

        return true;
    }

    /**
     * Deletes a stopover from a flight.
     * 
     * Before the flight departs, the system can delete a stopover on a flight.
     * The system ensures that the remaining stopover times fit within the overall flight schedule.
     *
     * @param flight the flight to delete stopover from
     * @param stopover the stopover to delete
     * @return true on success, false otherwise
     */
    public boolean deleteStopover(Flight flight, Stopover stopover) {
        if (flight == null || stopover == null) {
            return false;
        }

        if (!flight.isOpenForBooking()) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        if (!now.isBefore(flight.getDepartureTime())) {
            return false;
        }

        boolean removed = flight.getStopovers().remove(stopover);
        if (!removed) {
            return false;
        }

        // Validate remaining stopovers
        if (!flight.validateStopoverTimes()) {
            // Re-add the stopover if validation fails
            flight.getStopovers().add(stopover);
            return false;
        }

        return true;
    }
}