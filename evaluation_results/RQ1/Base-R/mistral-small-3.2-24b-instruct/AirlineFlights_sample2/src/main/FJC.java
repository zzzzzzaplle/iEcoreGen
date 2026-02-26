import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

class Airport {
    private String id;
    private Set<String> cities;

    public Airport() {
        this.id = UUID.randomUUID().toString();
        this.cities = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getCities() {
        return cities;
    }

    public void setCities(Set<String> cities) {
        this.cities = cities;
    }

    public void addCity(String city) {
        this.cities.add(city);
    }

    public boolean removeCity(String city) {
        return this.cities.remove(city);
    }
}

class Flight {
    private String id;
    private String departureAirportId;
    private String arrivalAirportId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private boolean isOpenForBooking;
    private List<Stopover> stopovers;

    public Flight() {
        this.id = UUID.randomUUID().toString();
        this.isOpenForBooking = false;
        this.stopovers = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(String departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public String getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(String arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
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

    /**
     * Publishes a flight for booking after validating the flight details.
     *
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publishFlight() {
        if (!isValidFlight()) {
            return false;
        }
        this.isOpenForBooking = true;
        return true;
    }

    /**
     * Validates the flight details including timestamps and route integrity.
     *
     * @return true if the flight details are valid, false otherwise
     */
    private boolean isValidFlight() {
        LocalDateTime currentTime = LocalDateTime.now();
        if (this.departureTime == null || this.arrivalTime == null) {
            return false;
        }
        if (!this.departureTime.toString().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}") ||
            !this.arrivalTime.toString().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            return false;
        }
        if (currentTime.isAfter(this.departureTime) || this.departureTime.isAfter(this.arrivalTime) ||
            this.departureAirportId.equals(this.arrivalAirportId)) {
            return false;
        }
        return true;
    }

    /**
     * Adds a stopover to the flight if it fits within the overall flight schedule.
     *
     * @param stopover the stopover to add
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopover(Stopover stopover) {
        if (this.stopovers.isEmpty()) {
            if (stopover.getDepartureTime().isBefore(this.departureTime) ||
                stopover.getArrivalTime().isAfter(this.arrivalTime)) {
                return false;
            }
        } else {
            Stopover lastStopover = this.stopovers.get(this.stopovers.size() - 1);
            if (stopover.getDepartureTime().isBefore(lastStopover.getArrivalTime()) ||
                stopover.getArrivalTime().isAfter(this.arrivalTime)) {
                return false;
            }
        }
        this.stopovers.add(stopover);
        return true;
    }

    /**
     * Deletes a stopover from the flight.
     *
     * @param stopover the stopover to delete
     * @return true if the stopover is successfully deleted, false otherwise
     */
    public boolean deleteStopover(Stopover stopover) {
        return this.stopovers.remove(stopover);
    }

    /**
     * Closes the flight and cancels all confirmed reservations.
     *
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight() {
        if (!this.isOpenForBooking) {
            return false;
        }
        this.isOpenForBooking = false;
        for (Reservation reservation : getConfirmedReservations()) {
            reservation.cancelReservation();
        }
        return true;
    }

    /**
     * Retrieves all confirmed reservations for the flight.
     *
     * @return a list of confirmed reservations
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmedReservations = new ArrayList<>();
        for (Booking booking : getBookings()) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.isConfirmed()) {
                    confirmedReservations.add(reservation);
                }
            }
        }
        return confirmedReservations;
    }

    /**
     * Retrieves all bookings for the flight.
     *
     * @return a list of bookings
     */
    public List<Booking> getBookings() {
        // This method should be implemented based on your system's design
        return new ArrayList<>();
    }
}

class Stopover {
    private String airportId;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public Stopover() {
        this.airportId = UUID.randomUUID().toString();
    }

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId;
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

class Booking {
    private String id;
    private String customerId;
    private String flightId;
    private List<Reservation> reservations;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Creates a booking for passengers on a flight.
     *
     * @param flight the flight to book
     * @param passengerNames the list of passenger names
     * @return true if the booking is successfully created, false otherwise
     */
    public boolean createBooking(Flight flight, List<String> passengerNames) {
        if (flight == null || passengerNames == null || passengerNames.isEmpty()) {
            return false;
        }
        if (!flight.isOpenForBooking() || flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            return false;
        }
        for (String passengerName : passengerNames) {
            Reservation reservation = new Reservation();
            reservation.setPassengerName(passengerName);
            this.reservations.add(reservation);
        }
        this.flightId = flight.getId();
        return true;
    }
}

class Reservation {
    private String id;
    private String passengerName;
    private String flightId;
    private boolean isConfirmed;

    public Reservation() {
        this.id = UUID.randomUUID().toString();
        this.isConfirmed = false;
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

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    /**
     * Confirms the reservation if the flight is still open for booking.
     *
     * @return true if the reservation is successfully confirmed, false otherwise
     */
    public boolean confirmReservation() {
        Flight flight = getFlight();
        if (flight == null || !flight.isOpenForBooking() || flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            return false;
        }
        this.isConfirmed = true;
        return true;
    }

    /**
     * Cancels the reservation if the flight is still open for booking.
     *
     * @return true if the reservation is successfully canceled, false otherwise
     */
    public boolean cancelReservation() {
        Flight flight = getFlight();
        if (flight == null || !flight.isOpenForBooking() || flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            return false;
        }
        this.isConfirmed = false;
        return true;
    }

    /**
     * Retrieves the flight associated with the reservation.
     *
     * @return the flight, or null if not found
     */
    private Flight getFlight() {
        // This method should be implemented based on your system's design
        return null;
    }
}