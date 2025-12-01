import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents an airport with a unique id.
 */
class Airport {
    private String id;
    private List<String> cities;

    /**
     * Unparameterized constructor for Airport.
     */
    public Airport() {
        this.cities = new ArrayList<>();
    }

    /**
     * Gets the id of the airport.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the airport.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the cities served by the airport.
     * @return the cities
     */
    public List<String> getCities() {
        return cities;
    }

    /**
     * Sets the cities served by the airport.
     * @param cities the cities to set
     */
    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    /**
     * Adds a city served by the airport.
     * @param city the city to add
     */
    public void addCity(String city) {
        this.cities.add(city);
    }
}

/**
 * Represents a stopover with departure and arrival times.
 */
class Stopover {
    private Airport airport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    /**
     * Unparameterized constructor for Stopover.
     */
    public Stopover() {}

    /**
     * Gets the airport of the stopover.
     * @return the airport
     */
    public Airport getAirport() {
        return airport;
    }

    /**
     * Sets the airport of the stopover.
     * @param airport the airport to set
     */
    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    /**
     * Gets the departure time of the stopover.
     * @return the departureTime
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time of the stopover.
     * @param departureTime the departureTime to set
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time of the stopover.
     * @return the arrivalTime
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time of the stopover.
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}

/**
 * Represents a reservation with a unique id and status.
 */
class Reservation {
    private String id;
    private String passengerName;
    private String status; // "pending", "confirmed", "canceled"

    /**
     * Unparameterized constructor for Reservation.
     */
    public Reservation() {
        this.status = "pending";
    }

    /**
     * Gets the id of the reservation.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the reservation.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the passenger name of the reservation.
     * @return the passengerName
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * Sets the passenger name of the reservation.
     * @param passengerName the passengerName to set
     */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /**
     * Gets the status of the reservation.
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the reservation.
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}

/**
 * Represents a flight with its details and status.
 */
class Flight {
    private String id;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private List<Stopover> stopovers;
    private String status; // "open", "closed"
    private List<Reservation> reservations;

    /**
     * Unparameterized constructor for Flight.
     */
    public Flight() {
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.status = "open";
    }

    /**
     * Gets the id of the flight.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the flight.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the departure airport of the flight.
     * @return the departureAirport
     */
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    /**
     * Sets the departure airport of the flight.
     * @param departureAirport the departureAirport to set
     */
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    /**
     * Gets the arrival airport of the flight.
     * @return the arrivalAirport
     */
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    /**
     * Sets the arrival airport of the flight.
     * @param arrivalAirport the arrivalAirport to set
     */
    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    /**
     * Gets the departure time of the flight.
     * @return the departureTime
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time of the flight.
     * @param departureTime the departureTime to set
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time of the flight.
     * @return the arrivalTime
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time of the flight.
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the stopovers of the flight.
     * @return the stopovers
     */
    public List<Stopover> getStopovers() {
        return stopovers;
    }

    /**
     * Sets the stopovers of the flight.
     * @param stopovers the stopovers to set
     */
    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    /**
     * Adds a stopover to the flight.
     * @param stopover the stopover to add
     */
    public void addStopover(Stopover stopover) {
        this.stopovers.add(stopover);
    }

    /**
     * Removes a stopover from the flight.
     * @param stopover the stopover to remove
     * @return true if removed, false otherwise
     */
    public boolean removeStopover(Stopover stopover) {
        return this.stopovers.remove(stopover);
    }

    /**
     * Gets the status of the flight.
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the flight.
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the reservations of the flight.
     * @return the reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the reservations of the flight.
     * @param reservations the reservations to set
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds a reservation to the flight.
     * @param reservation the reservation to add
     */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    /**
     * Publishes the flight for booking.
     * @return true if successful, false otherwise
     */
    public boolean publish() {
        if (!this.status.equals("open")) {
            return false;
        }
        if (this.departureTime == null || this.arrivalTime == null) {
            return false;
        }
        if (this.departureTime.isAfter(this.arrivalTime)) {
            return false;
        }
        if (this.departureAirport == null || this.arrivalAirport == null || this.departureAirport == this.arrivalAirport) {
            return false;
        }
        // Additional checks for stopovers can be added here
        this.status = "open"; // Mark as published
        return true;
    }

    /**
     * Creates a booking for passengers on the flight.
     * @param passengerNames the names of the passengers
     * @return true if successful, false otherwise
     */
    public boolean createBooking(List<String> passengerNames) {
        if (!this.status.equals("open") || LocalDateTime.now().isAfter(this.departureTime)) {
            return false;
        }
        for (String passengerName : passengerNames) {
            if (this.reservations.stream().anyMatch(r -> r.getPassengerName().equals(passengerName))) {
                return false; // Duplicate passenger
            }
            Reservation reservation = new Reservation();
            reservation.setId(UUID.randomUUID().toString());
            reservation.setPassengerName(passengerName);
            this.addReservation(reservation);
        }
        return true;
    }

    /**
     * Confirms or cancels an existing reservation.
     * @param reservationId the id of the reservation
     * @param confirm true to confirm, false to cancel
     * @return true if successful, false otherwise
     */
    public boolean updateReservation(String reservationId, boolean confirm) {
        if (!this.status.equals("open") || LocalDateTime.now().isAfter(this.departureTime)) {
            return false;
        }
        Reservation reservation = this.reservations.stream().filter(r -> r.getId().equals(reservationId)).findFirst().orElse(null);
        if (reservation == null) {
            return false;
        }
        reservation.setStatus(confirm ? "confirmed" : "canceled");
        return true;
    }

    /**
     * Closes the flight.
     * @return true if successful, false otherwise
     */
    public boolean close() {
        if (!this.status.equals("open") || LocalDateTime.now().isAfter(this.departureTime)) {
            return false;
        }
        this.status = "closed";
        this.reservations.forEach(r -> r.setStatus("canceled"));
        return true;
    }

    /**
     * Retrieves all confirmed reservations for the flight.
     * @return a list of confirmed reservations
     */
    public List<Reservation> getConfirmedReservations() {
        if (!this.status.equals("open")) {
            return new ArrayList<>();
        }
        return this.reservations.stream().filter(r -> r.getStatus().equals("confirmed")).toList();
    }

    /**
     * Adds a stopover to the flight.
     * @param stopover the stopover to add
     * @return true if successful, false otherwise
     */
    public boolean addStopoverToFlight(Stopover stopover) {
        if (LocalDateTime.now().isAfter(this.departureTime)) {
            return false;
        }
        if (stopover.getDepartureTime().isBefore(this.departureTime) || stopover.getArrivalTime().isAfter(this.arrivalTime)) {
            return false;
        }
        this.addStopover(stopover);
        return true;
    }

    /**
     * Deletes a stopover from the flight.
     * @param stopover the stopover to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteStopoverFromFlight(Stopover stopover) {
        if (LocalDateTime.now().isAfter(this.departureTime)) {
            return false;
        }
        return this.removeStopover(stopover);
    }
}

 class Main {

    public static void main(String[] args) {
        // Example usage
        Airport departureAirport = new Airport();
        departureAirport.setId("D1");
        departureAirport.addCity("City1");

        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("A1");
        arrivalAirport.addCity("City2");

        Flight flight = new Flight();
        flight.setId("F1");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2024-12-08 13:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        flight.setArrivalTime(LocalDateTime.parse("2024-12-08 15:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        System.out.println(flight.publish()); // Should print: true

        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("John Doe");
        passengerNames.add("Jane Doe");
        System.out.println(flight.createBooking(passengerNames)); // Should print: true

        // Confirm a reservation
        Reservation reservation = flight.getReservations().get(0);
        System.out.println(flight.updateReservation(reservation.getId(), true)); // Should print: true

        // Get confirmed reservations
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        confirmedReservations.forEach(r -> System.out.println(r.getPassengerName() + " - " + r.getStatus()));

        // Add a stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(new Airport());
        stopover.getAirport().setId("S1");
        stopover.setDepartureTime(LocalDateTime.parse("2024-12-08 13:30:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        stopover.setArrivalTime(LocalDateTime.parse("2024-12-08 14:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println(flight.addStopoverToFlight(stopover)); // Should print: true
    }
}