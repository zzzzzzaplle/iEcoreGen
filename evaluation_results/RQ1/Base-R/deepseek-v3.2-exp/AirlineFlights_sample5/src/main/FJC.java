import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

class Airport {
    private String id;
    private String name;
    private List<String> servedCities;

    public Airport() {
        this.servedCities = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getServedCities() {
        return servedCities;
    }

    public void setServedCities(List<String> servedCities) {
        this.servedCities = servedCities;
    }
}

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

class Flight {
    private String id;
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
     * Publishes a flight for booking by validating flight details and temporal consistency
     * @return true if flight is successfully published, false otherwise
     */
    public boolean publishFlight() {
        if (isPublished) {
            return false;
        }

        if (!isOpenForBooking) {
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

        if (!currentTime.isBefore(departureTime) || !departureTime.isBefore(arrivalTime)) {
            return false;
        }

        isPublished = true;
        return true;
    }

    /**
     * Closes an open flight and cancels all confirmed reservations
     * @return true if flight is successfully closed, false otherwise
     */
    public boolean closeFlight() {
        if (!isOpenForBooking) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(departureTime)) {
            return false;
        }

        isOpenForBooking = false;
        return true;
    }

    /**
     * Adds a stopover to the flight with validation
     * @param stopover the stopover to add
     * @return true if stopover is successfully added, false otherwise
     */
    public boolean addStopover(Stopover stopover) {
        if (stopover == null) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(departureTime)) {
            return false;
        }

        if (stopover.getAirport() == null || stopover.getArrivalTime() == null || stopover.getDepartureTime() == null) {
            return false;
        }

        if (!stopover.getArrivalTime().isBefore(stopover.getDepartureTime())) {
            return false;
        }

        if (!departureTime.isBefore(stopover.getArrivalTime()) || !stopover.getDepartureTime().isBefore(arrivalTime)) {
            return false;
        }

        stopovers.add(stopover);
        return true;
    }

    /**
     * Deletes a stopover from the flight
     * @param stopover the stopover to delete
     * @return true if stopover is successfully deleted, false otherwise
     */
    public boolean deleteStopover(Stopover stopover) {
        if (stopover == null) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(departureTime)) {
            return false;
        }

        return stopovers.remove(stopover);
    }
}

class Reservation {
    private String id;
    private Flight flight;
    private String passengerName;
    private String status;

    public Reservation() {
        this.id = UUID.randomUUID().toString();
        this.status = "pending";
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
     * Confirms or cancels the reservation
     * @param confirm true to confirm, false to cancel
     * @return true if operation is successful, false otherwise
     */
    public boolean confirmOrCancel(boolean confirm) {
        if (flight == null) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(flight.getDepartureTime())) {
            return false;
        }

        if (!flight.isOpenForBooking()) {
            return false;
        }

        if (confirm) {
            status = "confirmed";
        } else {
            status = "canceled";
        }
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
}

class AirlineSystem {
    private List<Flight> flights;
    private List<Booking> bookings;
    private List<Reservation> reservations;

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
     * Creates a booking for passengers on a flight
     * @param flight the flight to book
     * @param passengerNames list of passenger names
     * @return true if booking is successfully created, false otherwise
     */
    public boolean createBooking(Flight flight, List<String> passengerNames) {
        if (flight == null || passengerNames == null || passengerNames.isEmpty()) {
            return false;
        }

        if (!flight.isOpenForBooking() || !flight.isPublished()) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(flight.getDepartureTime())) {
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
            reservation.setFlight(flight);
            reservation.setPassengerName(passengerName);
            booking.getReservations().add(reservation);
            reservations.add(reservation);
        }

        bookings.add(booking);
        return true;
    }

    /**
     * Confirms or cancels an existing reservation
     * @param reservationId the ID of the reservation to update
     * @param confirm true to confirm, false to cancel
     * @return true if operation is successful, false otherwise
     */
    public boolean confirmOrCancelReservation(String reservationId, boolean confirm) {
        Reservation reservation = findReservationById(reservationId);
        if (reservation == null) {
            return false;
        }

        return reservation.confirmOrCancel(confirm);
    }

    /**
     * Closes an open flight and cancels all confirmed reservations
     * @param flightId the ID of the flight to close
     * @return true if flight is successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId) {
        Flight flight = findFlightById(flightId);
        if (flight == null) {
            return false;
        }

        if (flight.closeFlight()) {
            for (Reservation reservation : reservations) {
                if (reservation.getFlight().getId().equals(flightId) && "confirmed".equals(reservation.getStatus())) {
                    reservation.setStatus("canceled");
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Retrieves all confirmed reservations for a specific flight
     * @param flightId the ID of the flight
     * @return list of confirmed reservations for the flight
     */
    public List<Reservation> getConfirmedReservationsForFlight(String flightId) {
        List<Reservation> confirmedReservations = new ArrayList<>();
        Flight flight = findFlightById(flightId);
        
        if (flight == null || !flight.isOpenForBooking()) {
            return confirmedReservations;
        }

        for (Reservation reservation : reservations) {
            if (reservation.getFlight().getId().equals(flightId) && "confirmed".equals(reservation.getStatus())) {
                confirmedReservations.add(reservation);
            }
        }
        return confirmedReservations;
    }

    /**
     * Adds a stopover to a flight
     * @param flightId the ID of the flight
     * @param stopover the stopover to add
     * @return true if stopover is successfully added, false otherwise
     */
    public boolean addStopoverToFlight(String flightId, Stopover stopover) {
        Flight flight = findFlightById(flightId);
        if (flight == null) {
            return false;
        }

        if (stopover.getAirport().getServedCities().isEmpty()) {
            return false;
        }

        return flight.addStopover(stopover);
    }

    /**
     * Deletes a stopover from a flight
     * @param flightId the ID of the flight
     * @param stopover the stopover to delete
     * @return true if stopover is successfully deleted, false otherwise
     */
    public boolean deleteStopoverFromFlight(String flightId, Stopover stopover) {
        Flight flight = findFlightById(flightId);
        if (flight == null) {
            return false;
        }

        return flight.deleteStopover(stopover);
    }

    private Flight findFlightById(String flightId) {
        for (Flight flight : flights) {
            if (flight.getId().equals(flightId)) {
                return flight;
            }
        }
        return null;
    }

    private Reservation findReservationById(String reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(reservationId)) {
                return reservation;
            }
        }
        return null;
    }
}