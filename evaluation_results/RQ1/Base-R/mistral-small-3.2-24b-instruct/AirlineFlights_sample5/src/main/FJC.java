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
    private List<Stopover> stopovers;
    private boolean isOpenForBooking;
    private boolean isPublished;

    public Flight() {
        this.id = UUID.randomUUID().toString();
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
     * Publishes a flight for booking.
     *
     * @param currentTime The current time to check temporal consistency
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publishFlight(LocalDateTime currentTime) {
        if (isPublished || !isOpenForBooking) {
            return false;
        }

        if (departureTime == null || arrivalTime == null ||
            !departureTime.toString().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}" ||
            !arrivalTime.toString().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            return false;
        }

        if (currentTime.isAfter(departureTime) || departureTime.isAfter(arrivalTime) || departureAirportId.equals(arrivalAirportId)) {
            return false;
        }

        isPublished = true;
        return true;
    }

    /**
     * Adds a stopover to the flight.
     *
     * @param stopover The stopover to add
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopover(Stopover stopover) {
        if (departureTime == null || arrivalTime == null || stopover.getDepartureTime() == null || stopover.getArrivalTime() == null) {
            return false;
        }

        if (stopover.getDepartureTime().isBefore(departureTime) || stopover.getArrivalTime().isAfter(arrivalTime)) {
            return false;
        }

        stopovers.add(stopover);
        return true;
    }

    /**
     * Deletes a stopover from the flight.
     *
     * @param stopover The stopover to delete
     * @return true if the stopover is successfully deleted, false otherwise
     */
    public boolean deleteStopover(Stopover stopover) {
        return stopovers.remove(stopover);
    }

    /**
     * Closes the flight.
     *
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight() {
        if (!isOpenForBooking) {
            return false;
        }

        isOpenForBooking = false;
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
     * @param flight The flight to book
     * @param passengerNames The list of passenger names
     * @param currentTime The current time to check booking validity
     * @return true if the booking is successfully created, false otherwise
     */
    public boolean createBooking(Flight flight, List<String> passengerNames, LocalDateTime currentTime) {
        if (flight == null || !flight.isOpenForBooking() || flight.getDepartureTime() == null || currentTime.isAfter(flight.getDepartureTime())) {
            return false;
        }

        Set<String> uniquePassengers = new HashSet<>(passengerNames);
        if (uniquePassengers.size() != passengerNames.size()) {
            return false;
        }

        for (String passengerName : uniquePassengers) {
            Reservation reservation = new Reservation();
            reservation.setPassengerName(passengerName);
            reservation.setFlightId(flight.getId());
            reservations.add(reservation);
        }

        return true;
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

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    /**
     * Confirms the reservation.
     *
     * @param flight The flight associated with the reservation
     * @return true if the reservation is successfully confirmed, false otherwise
     */
    public boolean confirmReservation(Flight flight) {
        if (flight == null || !flight.isOpenForBooking() || flight.getDepartureTime() == null || LocalDateTime.now().isAfter(flight.getDepartureTime())) {
            return false;
        }

        isConfirmed = true;
        return true;
    }

    /**
     * Cancels the reservation.
     *
     * @param flight The flight associated with the reservation
     * @return true if the reservation is successfully canceled, false otherwise
     */
    public boolean cancelReservation(Flight flight) {
        if (flight == null || !flight.isOpenForBooking() || flight.getDepartureTime() == null || LocalDateTime.now().isAfter(flight.getDepartureTime())) {
            return false;
        }

        isConfirmed = false;
        return true;
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
     * @param flightId The ID of the flight
     * @return A list of confirmed reservations, or an empty list if none exist
     */
    public List<Reservation> getConfirmedReservations(String flightId) {
        List<Reservation> confirmedReservations = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getFlightId().equals(flightId)) {
                for (Reservation reservation : booking.getReservations()) {
                    if (reservation.isConfirmed()) {
                        confirmedReservations.add(reservation);
                    }
                }
            }
        }

        return confirmedReservations;
    }
}