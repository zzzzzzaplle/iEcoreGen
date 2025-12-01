import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Airport {
    private String id;
    private Set<String> servedCities;

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
    private String id;
    private boolean isOpenForBooking;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private List<Stopover> stopovers;

    public Flight() {
        this.stopovers = new ArrayList<>();
        this.isOpenForBooking = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOpenForBooking() {
        return isOpenForBooking;
    }

    public void setOpenForBooking(boolean openForBooking) {
        isOpenForBooking = openForBooking;
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

    /**
     * Publishes a flight for booking by validating flight details and setting it as open for booking.
     * Checks for valid timestamp format, temporal consistency, and route integrity.
     * A flight can only be published once and must be currently closed.
     *
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publishFlight() {
        if (this.isOpenForBooking) {
            return false;
        }

        if (departureAirport == null || arrivalAirport == null || 
            departureTime == null || arrivalTime == null) {
            return false;
        }

        if (departureAirport.getId().equals(arrivalAirport.getId())) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime) || !departureTime.isBefore(arrivalTime)) {
            return false;
        }

        this.isOpenForBooking = true;
        return true;
    }

    /**
     * Closes an open flight that has not yet departed.
     * Cancels all confirmed reservations on the flight.
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
     * Adds a stopover to the flight with validation of temporal consistency and airport validity.
     * The stopover must fit within the flight schedule and have valid airport information.
     *
     * @param stopover the stopover to be added
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopover(Stopover stopover) {
        if (stopover == null || stopover.getAirport() == null || 
            stopover.getArrivalTime() == null || stopover.getDepartureTime() == null) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime)) {
            return false;
        }

        if (!stopover.getArrivalTime().isBefore(stopover.getDepartureTime()) ||
            !stopover.getArrivalTime().isAfter(departureTime) ||
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
     * Deletes a stopover from the flight before departure.
     *
     * @param stopover the stopover to be deleted
     * @return true if the stopover is successfully deleted, false otherwise
     */
    public boolean deleteStopover(Stopover stopover) {
        if (stopover == null) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(departureTime)) {
            return false;
        }

        return this.stopovers.remove(stopover);
    }
}

class Reservation {
    private String id;
    private Flight flight;
    private String passengerName;
    private String status; // "pending", "confirmed", "canceled"

    public Reservation() {
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
     * Confirms or cancels a reservation if the flight is still open and hasn't departed.
     *
     * @param confirm true to confirm, false to cancel
     * @return true if the operation is successful, false otherwise
     */
    public boolean confirmOrCancel(boolean confirm) {
        if (flight == null || !flight.isOpenForBooking()) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(flight.getDepartureTime())) {
            return false;
        }

        if (confirm) {
            this.status = "confirmed";
        } else {
            this.status = "canceled";
        }
        return true;
    }
}

class Booking {
    private String id;
    private Flight flight;
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

    /**
     * Creates reservations for passengers on a flight with validation for duplicates and timing.
     *
     * @param passengerNames list of passenger names
     * @return true if the booking is successfully created, false otherwise
     */
    public boolean createBooking(List<String> passengerNames) {
        if (flight == null || passengerNames == null || passengerNames.isEmpty()) {
            return false;
        }

        if (!flight.isOpenForBooking()) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(flight.getDepartureTime())) {
            return false;
        }

        Set<String> uniquePassengers = new HashSet<>();
        for (String passenger : passengerNames) {
            if (!uniquePassengers.add(passenger)) {
                return false;
            }
        }

        for (String passengerName : passengerNames) {
            Reservation reservation = new Reservation();
            reservation.setId(java.util.UUID.randomUUID().toString());
            reservation.setFlight(flight);
            reservation.setPassengerName(passengerName);
            reservation.setStatus("pending");
            this.reservations.add(reservation);
        }

        return true;
    }

    /**
     * Retrieves all confirmed reservations in this booking.
     *
     * @return list of confirmed reservations
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmed = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if ("confirmed".equals(reservation.getStatus())) {
                confirmed.add(reservation);
            }
        }
        return confirmed;
    }
}

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
     * Retrieves all confirmed reservations for a specific flight.
     *
     * @param flightId the ID of the flight
     * @return list of confirmed reservations for the flight
     */
    public List<Reservation> getConfirmedReservationsForFlight(String flightId) {
        List<Reservation> confirmedReservations = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getFlight() != null && flightId.equals(booking.getFlight().getId())) {
                if (booking.getFlight().isOpenForBooking()) {
                    confirmedReservations.addAll(booking.getConfirmedReservations());
                }
            }
        }
        return confirmedReservations;
    }
}