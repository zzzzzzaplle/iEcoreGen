import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Airport {
    private String id;
    private List<String> cities;

    public Airport() {
        this.id = UUID.randomUUID().toString();
        this.cities = new ArrayList<>();
    }

    public Airport(String id, List<String> cities) {
        this.id = id;
        this.cities = cities;
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
    private Airport departureAirport;
    private Airport arrivalAirport;
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

    public Flight(String id, Airport departureAirport, Airport arrivalAirport, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
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
     * Publishes a flight for booking if it meets the required conditions.
     *
     * @return true if the flight is published successfully, false otherwise
     */
    public boolean publishFlight() {
        if (isPublished || !isOpenForBooking) {
            return false;
        }

        if (!departureTime.toString().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}") ||
            !arrivalTime.toString().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(departureTime) || currentTime.isAfter(arrivalTime) ||
            departureTime.isAfter(arrivalTime) || departureAirport.getId().equals(arrivalAirport.getId())) {
            return false;
        }

        isPublished = true;
        return true;
    }

    /**
     * Adds a stopover to the flight if it fits within the overall flight schedule.
     *
     * @param stopover the stopover to add
     * @return true if the stopover is added successfully, false otherwise
     */
    public boolean addStopover(Stopover stopover) {
        if (!isOpenForBooking || isPublished) {
            return false;
        }

        if (stopover.getDepartureTime().isBefore(departureTime) ||
            stopover.getArrivalTime().isAfter(arrivalTime) ||
            stopover.getDepartureTime().isAfter(stopover.getArrivalTime())) {
            return false;
        }

        for (Stopover existingStopover : stopovers) {
            if (existingStopover.getDepartureTime().isBefore(stopover.getArrivalTime()) &&
                existingStopover.getArrivalTime().isAfter(stopover.getDepartureTime())) {
                return false;
            }
        }

        stopovers.add(stopover);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     *
     * @param stopover the stopover to remove
     * @return true if the stopover is removed successfully, false otherwise
     */
    public boolean removeStopover(Stopover stopover) {
        if (!isOpenForBooking || isPublished) {
            return false;
        }

        return stopovers.remove(stopover);
    }

    /**
     * Closes the flight and cancels all confirmed reservations.
     *
     * @return true if the flight is closed successfully, false otherwise
     */
    public boolean closeFlight() {
        if (!isOpenForBooking || isPublished) {
            return false;
        }

        isOpenForBooking = false;
        for (Reservation reservation : getConfirmedReservations()) {
            reservation.setStatus(ReservationStatus.CANCELED);
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
                if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
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
        // This method should be implemented to retrieve bookings for the flight
        return new ArrayList<>();
    }
}

class Stopover {
    private Airport airport;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public Stopover() {
        this.airport = new Airport();
    }

    public Stopover(Airport airport, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.airport = airport;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
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

enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELED
}

class Reservation {
    private String id;
    private Flight flight;
    private String passengerName;
    private ReservationStatus status;

    public Reservation() {
        this.id = UUID.randomUUID().toString();
        this.status = ReservationStatus.PENDING;
    }

    public Reservation(String id, Flight flight, String passengerName) {
        this.id = id;
        this.flight = flight;
        this.passengerName = passengerName;
        this.status = ReservationStatus.PENDING;
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

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    /**
     * Confirms the reservation if the flight has not yet departed and is still open for booking.
     *
     * @return true if the reservation is confirmed successfully, false otherwise
     */
    public boolean confirmReservation() {
        if (flight == null || !flight.isOpenForBooking() || flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            return false;
        }

        status = ReservationStatus.CONFIRMED;
        return true;
    }

    /**
     * Cancels the reservation if the flight has not yet departed and is still open for booking.
     *
     * @return true if the reservation is canceled successfully, false otherwise
     */
    public boolean cancelReservation() {
        if (flight == null || !flight.isOpenForBooking() || flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            return false;
        }

        status = ReservationStatus.CANCELED;
        return true;
    }
}

class Booking {
    private String id;
    private Flight flight;
    private List<Reservation> reservations;

    public Booking() {
        this.id = UUID.randomUUID().toString();
        this.reservations = new ArrayList<>();
    }

    public Booking(String id, Flight flight) {
        this.id = id;
        this.flight = flight;
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
     * Creates a booking for passengers on a flight if there are no duplicate passengers and the current time is before the flight departure time.
     *
     * @param passengerNames a list of passenger names
     * @return true if the booking is created successfully, false otherwise
     */
    public boolean createBooking(List<String> passengerNames) {
        if (flight == null || !flight.isOpenForBooking() || flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            return false;
        }

        for (String passengerName : passengerNames) {
            for (Reservation reservation : reservations) {
                if (reservation.getPassengerName().equals(passengerName)) {
                    return false;
                }
            }
        }

        for (String passengerName : passengerNames) {
            Reservation reservation = new Reservation();
            reservation.setFlight(flight);
            reservation.setPassengerName(passengerName);
            reservations.add(reservation);
        }

        return true;
    }
}