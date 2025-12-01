import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class Airport {
    private String id;
    private List<String> cities;

    public Airport() {
        this.id = UUID.randomUUID().toString();
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
        this.isOpenForBooking = true;
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
     * Validates the flight details including departure and arrival times, and route integrity.
     *
     * @return true if the flight details are valid, false otherwise
     */
    private boolean isValidFlight() {
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(this.departureTime) || currentTime.isAfter(this.arrivalTime)) {
            return false;
        }
        if (this.departureTime.isAfter(this.arrivalTime)) {
            return false;
        }
        if (this.departureAirportId.equals(this.arrivalAirportId)) {
            return false;
        }
        return true;
    }

    public boolean addStopover(Stopover stopover) {
        if (!isValidStopover(stopover)) {
            return false;
        }
        this.stopovers.add(stopover);
        return true;
    }

    public boolean removeStopover(Stopover stopover) {
        return this.stopovers.remove(stopover);
    }

    private boolean isValidStopover(Stopover stopover) {
        if (stopover.getDepartureTime().isBefore(this.departureTime) ||
            stopover.getArrivalTime().isAfter(this.arrivalTime)) {
            return false;
        }
        return true;
    }
}

class Stopover {
    private String airportId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public Stopover() {
        this.airportId = UUID.randomUUID().toString();
    }

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId;
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
}

class Booking {
    private String id;
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
     * @param passengerNames list of passenger names
     * @return true if the booking is successfully created, false otherwise
     */
    public boolean createBooking(Flight flight, List<String> passengerNames) {
        if (!flight.isOpenForBooking() || flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            return false;
        }
        for (String passengerName : passengerNames) {
            Reservation reservation = new Reservation();
            reservation.setPassengerName(passengerName);
            reservation.setStatus("pending");
            this.reservations.add(reservation);
        }
        this.flightId = flight.getId();
        return true;
    }
}

class Reservation {
    private String id;
    private String passengerName;
    private String status;

    public Reservation() {
        this.id = UUID.randomUUID().toString();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Confirms or cancels a reservation.
     *
     * @param flight the flight associated with the reservation
     * @param reservationId the ID of the reservation to confirm or cancel
     * @param confirm true to confirm the reservation, false to cancel it
     * @return true if the reservation is successfully confirmed or canceled, false otherwise
     */
    public boolean confirmOrCancelReservation(Flight flight, String reservationId, boolean confirm) {
        if (!flight.isOpenForBooking() || flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            return false;
        }
        if (this.id.equals(reservationId)) {
            this.status = confirm ? "confirmed" : "canceled";
            return true;
        }
        return false;
    }
}

class Airline {
    private List<Flight> flights;
    private Map<String, Booking> bookings;

    public Airline() {
        this.flights = new ArrayList<>();
        this.bookings = new HashMap<>();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public Map<String, Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Map<String, Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Closes an open flight and cancels all confirmed reservations.
     *
     * @param flightId the ID of the flight to close
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId) {
        Flight flight = findFlightById(flightId);
        if (flight == null || !flight.isOpenForBooking()) {
            return false;
        }
        flight.setOpenForBooking(false);
        for (Booking booking : bookings.values()) {
            if (booking.getFlightId().equals(flightId)) {
                for (Reservation reservation : booking.getReservations()) {
                    if (reservation.getStatus().equals("confirmed")) {
                        reservation.setStatus("canceled");
                    }
                }
            }
        }
        return true;
    }

    /**
     * Retrieves all confirmed reservations for a specific flight.
     *
     * @param flightId the ID of the flight
     * @return a list of confirmed reservations, or an empty list if none exist
     */
    public List<Reservation> getConfirmedReservations(String flightId) {
        List<Reservation> confirmedReservations = new ArrayList<>();
        Flight flight = findFlightById(flightId);
        if (flight == null || !flight.isOpenForBooking()) {
            return confirmedReservations;
        }
        for (Booking booking : bookings.values()) {
            if (booking.getFlightId().equals(flightId)) {
                for (Reservation reservation : booking.getReservations()) {
                    if (reservation.getStatus().equals("confirmed")) {
                        confirmedReservations.add(reservation);
                    }
                }
            }
        }
        return confirmedReservations;
    }

    private Flight findFlightById(String flightId) {
        for (Flight flight : flights) {
            if (flight.getId().equals(flightId)) {
                return flight;
            }
        }
        return null;
    }
}