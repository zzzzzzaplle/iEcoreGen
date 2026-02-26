import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class Flight {
    private String id;
    private String departureAirportId;
    private String arrivalAirportId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private boolean isOpenForBooking;
    private List<Stopover> stopovers;
    private Map<String, Reservation> reservations;

    public Flight() {
        this.id = UUID.randomUUID().toString();
        this.isOpenForBooking = true;
        this.stopovers = new ArrayList<>();
        this.reservations = new HashMap<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
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

    public Map<String, Reservation> getReservations() {
        return reservations;
    }

    /**
     * Publishes a flight for booking after validating the flight details.
     * @return true if the flight is successfully published, false otherwise.
     */
    public boolean publishFlight() {
        if (!isValidFlight()) {
            return false;
        }
        isOpenForBooking = true;
        return true;
    }

    /**
     * Validates the flight details including timestamps and route integrity.
     * @return true if the flight details are valid, false otherwise.
     */
    private boolean isValidFlight() {
        LocalDateTime currentTime = LocalDateTime.now();
        if (departureTime == null || arrivalTime == null) {
            return false;
        }
        if (departureTime.isBefore(currentTime) || arrivalTime.isBefore(departureTime)) {
            return false;
        }
        if (departureAirportId == null || arrivalAirportId == null || departureAirportId.equals(arrivalAirportId)) {
            return false;
        }
        return true;
    }

    /**
     * Adds a stopover to the flight.
     * @param stopover The stopover to be added.
     * @return true if the stopover is successfully added, false otherwise.
     */
    public boolean addStopover(Stopover stopover) {
        if (!isValidStopover(stopover)) {
            return false;
        }
        stopovers.add(stopover);
        return true;
    }

    /**
     * Deletes a stopover from the flight.
     * @param stopover The stopover to be deleted.
     * @return true if the stopover is successfully deleted, false otherwise.
     */
    public boolean deleteStopover(Stopover stopover) {
        return stopovers.remove(stopover);
    }

    /**
     * Validates the stopover details.
     * @param stopover The stopover to be validated.
     * @return true if the stopover details are valid, false otherwise.
     */
    private boolean isValidStopover(Stopover stopover) {
        if (stopover.getDepartureTime() == null || stopover.getArrivalTime() == null) {
            return false;
        }
        if (stopover.getDepartureTime().isBefore(departureTime) || stopover.getArrivalTime().isAfter(arrivalTime)) {
            return false;
        }
        return true;
    }

    /**
     * Closes the flight and cancels all confirmed reservations.
     * @return true if the flight is successfully closed, false otherwise.
     */
    public boolean closeFlight() {
        if (!isOpenForBooking) {
            return false;
        }
        isOpenForBooking = false;
        for (Reservation reservation : reservations.values()) {
            if (reservation.isConfirmed()) {
                reservation.cancel();
            }
        }
        return true;
    }

    /**
     * Retrieves all confirmed reservations for the flight.
     * @return A list of confirmed reservations.
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmedReservations = new ArrayList<>();
        for (Reservation reservation : reservations.values()) {
            if (reservation.isConfirmed()) {
                confirmedReservations.add(reservation);
            }
        }
        return confirmedReservations;
    }
}

class Stopover {
    private String airportId;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public Stopover() {
        this.airportId = UUID.randomUUID().toString();
    }

    // Getters and Setters
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

class Reservation {
    private String id;
    private String flightId;
    private String passengerName;
    private boolean isConfirmed;

    public Reservation() {
        this.id = UUID.randomUUID().toString();
        this.isConfirmed = false;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    /**
     * Confirms the reservation.
     * @return true if the reservation is successfully confirmed, false otherwise.
     */
    public boolean confirm() {
        if (isConfirmed) {
            return false;
        }
        isConfirmed = true;
        return true;
    }

    /**
     * Cancels the reservation.
     * @return true if the reservation is successfully canceled, false otherwise.
     */
    public boolean cancel() {
        if (!isConfirmed) {
            return false;
        }
        isConfirmed = false;
        return true;
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

    // Getters and Setters
    public String getId() {
        return id;
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

    /**
     * Creates a booking for passengers on a flight.
     * @param flight The flight to book.
     * @param passengerNames The list of passenger names.
     * @return true if the booking is successfully created, false otherwise.
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
            reservation.setFlightId(flight.getId());
            reservation.setPassengerName(passengerName);
            reservations.add(reservation);
            flight.getReservations().put(reservation.getId(), reservation);
        }
        return true;
    }

    /**
     * Confirms or cancels a reservation in the booking.
     * @param reservationId The ID of the reservation to confirm or cancel.
     * @param confirm true to confirm the reservation, false to cancel it.
     * @return true if the reservation is successfully confirmed or canceled, false otherwise.
     */
    public boolean updateReservation(String reservationId, boolean confirm) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(reservationId)) {
                if (confirm) {
                    return reservation.confirm();
                } else {
                    return reservation.cancel();
                }
            }
        }
        return false;
    }
}

class Airport {
    private String id;
    private List<String> cities;

    public Airport() {
        this.id = UUID.randomUUID().toString();
        this.cities = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    /**
     * Adds a city to the airport.
     * @param city The city to be added.
     * @return true if the city is successfully added, false otherwise.
     */
    public boolean addCity(String city) {
        if (city == null || cities.contains(city)) {
            return false;
        }
        cities.add(city);
        return true;
    }

    /**
     * Deletes a city from the airport.
     * @param city The city to be deleted.
     * @return true if the city is successfully deleted, false otherwise.
     */
    public boolean deleteCity(String city) {
        return cities.remove(city);
    }
}