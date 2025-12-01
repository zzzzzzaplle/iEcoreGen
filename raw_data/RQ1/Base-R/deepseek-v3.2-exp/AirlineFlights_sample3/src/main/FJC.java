import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

 class Airport {
    private String airportId;
    private Set<String> servedCities;

    public Airport() {
        this.servedCities = new HashSet<>();
    }

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId;
    }

    public Set<String> getServedCities() {
        return servedCities;
    }

    public void setServedCities(Set<String> servedCities) {
        this.servedCities = servedCities;
    }

    public void addServedCity(String city) {
        this.servedCities.add(city);
    }

    public void removeServedCity(String city) {
        this.servedCities.remove(city);
    }
}

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

 class Flight {
    private String flightId;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private boolean isOpenForBooking;
    private boolean isPublished;
    private List<Stopover> stopovers;

    public Flight() {
        this.stopovers = new ArrayList<>();
        this.isOpenForBooking = true;
        this.isPublished = false;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
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

    /**
     * Publishes a flight for booking by validating its schedule and route integrity.
     * The method checks that the flight has valid departure and arrival timestamps,
     * ensures temporal consistency (currentTime < departureTime < arrivalTime),
     * verifies that departure and arrival airports are different, and confirms that
     * the flight hasn't been published before and is still open for booking.
     *
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publishFlight() {
        if (this.isPublished) {
            return false;
        }
        
        if (!this.isOpenForBooking) {
            return false;
        }
        
        if (departureTime == null || arrivalTime == null) {
            return false;
        }
        
        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime) || !departureTime.isBefore(arrivalTime)) {
            return false;
        }
        
        if (departureAirport == null || arrivalAirport == null || 
            departureAirport.equals(arrivalAirport)) {
            return false;
        }
        
        this.isPublished = true;
        return true;
    }

    /**
     * Adds a stopover to the flight schedule, ensuring it fits within the overall flight timeline
     * and that the airport serves valid cities.
     *
     * @param stopover the stopover to be added
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopover(Stopover stopover) {
        if (stopover == null || stopover.getAirport() == null) {
            return false;
        }
        
        if (stopover.getArrivalTime() == null || stopover.getDepartureTime() == null) {
            return false;
        }
        
        if (!stopover.getArrivalTime().isBefore(stopover.getDepartureTime())) {
            return false;
        }
        
        if (!departureTime.isBefore(stopover.getArrivalTime()) || 
            !stopover.getDepartureTime().isBefore(arrivalTime)) {
            return false;
        }
        
        if (stopover.getAirport().getServedCities().isEmpty()) {
            return false;
        }
        
        this.stopovers.add(stopover);
        return true;
    }

    /**
     * Deletes a stopover from the flight schedule.
     *
     * @param stopover the stopover to be deleted
     * @return true if the stopover is successfully deleted, false otherwise
     */
    public boolean deleteStopover(Stopover stopover) {
        return this.stopovers.remove(stopover);
    }

    /**
     * Closes an open flight that has not yet departed, canceling all confirmed reservations.
     *
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight() {
        if (!this.isOpenForBooking) {
            return false;
        }
        
        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime)) {
            return false;
        }
        
        this.isOpenForBooking = false;
        return true;
    }

    /**
     * Checks if the flight has departed based on current time and departure time.
     *
     * @return true if the flight has departed, false otherwise
     */
    public boolean hasDeparted() {
        return LocalDateTime.now().isAfter(departureTime);
    }
}

 class Reservation {
    private String reservationId;
    private Flight flight;
    private String passengerName;
    private String status;
    private Booking booking;

    public Reservation() {
        this.status = "pending";
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
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
     * Confirms a reservation if the flight hasn't departed and is still open for booking.
     *
     * @return true if the reservation is successfully confirmed, false otherwise
     */
    public boolean confirmReservation() {
        if (flight == null) {
            return false;
        }
        
        if (flight.hasDeparted() || !flight.isOpenForBooking()) {
            return false;
        }
        
        this.status = "confirmed";
        return true;
    }

    /**
     * Cancels a reservation if the flight hasn't departed and is still open for booking.
     *
     * @return true if the reservation is successfully canceled, false otherwise
     */
    public boolean cancelReservation() {
        if (flight == null) {
            return false;
        }
        
        if (flight.hasDeparted() || !flight.isOpenForBooking()) {
            return false;
        }
        
        this.status = "canceled";
        return true;
    }
}

 class Booking {
    private String bookingId;
    private Flight flight;
    private List<Reservation> reservations;

    public Booking() {
        this.reservations = new ArrayList<>();
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }
}

 class AirlineSystem {
    private List<Flight> flights;
    private List<Booking> bookings;
    private List<Reservation> reservations;
    private static int reservationCounter = 1;

    public AirlineSystem() {
        this.flights = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.reservations = new ArrayList<>();
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Creates a booking for passengers on a flight, checking for duplicate passengers
     * and ensuring the current time is before flight departure.
     *
     * @param flight the flight to book
     * @param passengerNames list of passenger names
     * @return true if the booking is successfully created, false otherwise
     */
    public boolean createBooking(Flight flight, List<String> passengerNames) {
        if (flight == null || passengerNames == null || passengerNames.isEmpty()) {
            return false;
        }
        
        if (!flight.isOpenForBooking() || !flight.isPublished()) {
            return false;
        }
        
        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(flight.getDepartureTime())) {
            return false;
        }
        
        Set<String> uniquePassengers = new HashSet<>(passengerNames);
        if (uniquePassengers.size() != passengerNames.size()) {
            return false;
        }
        
        Booking booking = new Booking();
        booking.setFlight(flight);
        
        for (String passengerName : passengerNames) {
            Reservation reservation = new Reservation();
            reservation.setReservationId("RES" + reservationCounter++);
            reservation.setFlight(flight);
            reservation.setPassengerName(passengerName);
            reservation.setBooking(booking);
            
            booking.addReservation(reservation);
            this.reservations.add(reservation);
        }
        
        this.bookings.add(booking);
        return true;
    }

    /**
     * Confirms or cancels an existing reservation by its ID.
     *
     * @param reservationId the ID of the reservation to update
     * @param confirm true to confirm, false to cancel
     * @return true if the operation is successful, false otherwise
     */
    public boolean updateReservationStatus(String reservationId, boolean confirm) {
        Reservation reservation = findReservationById(reservationId);
        if (reservation == null) {
            return false;
        }
        
        if (confirm) {
            return reservation.confirmReservation();
        } else {
            return reservation.cancelReservation();
        }
    }

    /**
     * Closes an open flight that has not yet departed, canceling all confirmed reservations.
     *
     * @param flightId the ID of the flight to close
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId) {
        Flight flight = findFlightById(flightId);
        if (flight == null) {
            return false;
        }
        
        if (flight.closeFlight()) {
            for (Reservation reservation : this.reservations) {
                if (reservation.getFlight().equals(flight) && 
                    "confirmed".equals(reservation.getStatus())) {
                    reservation.setStatus("canceled");
                }
            }
            return true;
        }
        
        return false;
    }

    /**
     * Retrieves all confirmed reservations for a specific flight.
     *
     * @param flightId the ID of the flight
     * @return list of confirmed reservations for the flight, empty list if none exist
     */
    public List<Reservation> getConfirmedReservationsForFlight(String flightId) {
        Flight flight = findFlightById(flightId);
        List<Reservation> confirmedReservations = new ArrayList<>();
        
        if (flight == null || !flight.isOpenForBooking()) {
            return confirmedReservations;
        }
        
        for (Reservation reservation : this.reservations) {
            if (reservation.getFlight().equals(flight) && 
                "confirmed".equals(reservation.getStatus())) {
                confirmedReservations.add(reservation);
            }
        }
        
        return confirmedReservations;
    }

    /**
     * Adds a stopover to a flight before departure.
     *
     * @param flightId the ID of the flight
     * @param stopover the stopover to add
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopoverToFlight(String flightId, Stopover stopover) {
        Flight flight = findFlightById(flightId);
        if (flight == null || flight.hasDeparted()) {
            return false;
        }
        
        return flight.addStopover(stopover);
    }

    /**
     * Deletes a stopover from a flight before departure.
     *
     * @param flightId the ID of the flight
     * @param stopover the stopover to delete
     * @return true if the stopover is successfully deleted, false otherwise
     */
    public boolean deleteStopoverFromFlight(String flightId, Stopover stopover) {
        Flight flight = findFlightById(flightId);
        if (flight == null || flight.hasDeparted()) {
            return false;
        }
        
        return flight.deleteStopover(stopover);
    }

    private Flight findFlightById(String flightId) {
        for (Flight flight : this.flights) {
            if (flight.getFlightId().equals(flightId)) {
                return flight;
            }
        }
        return null;
    }

    private Reservation findReservationById(String reservationId) {
        for (Reservation reservation : this.reservations) {
            if (reservation.getReservationId().equals(reservationId)) {
            return reservation;
        }
    }
    return null;
}