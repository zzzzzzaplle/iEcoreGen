import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents an airport serving one or more cities.
 */
class Airport {
    private String id;
    private List<String> cities;

    /**
     * Default constructor for Airport.
     */
    public Airport() {
        this.cities = new ArrayList<>();
    }

    /**
     * Gets the unique identifier of the airport.
     * @return the airport's id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the airport.
     * @param id the airport's id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the list of cities served by the airport.
     * @return the list of cities
     */
    public List<String> getCities() {
        return cities;
    }

    /**
     * Sets the list of cities served by the airport.
     * @param cities the list of cities to set
     */
    public void setCities(List<String> cities) {
        this.cities = cities;
    }
}

/**
 * Represents a stopover in a flight.
 */
class Stopover {
    private Airport airport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    /**
     * Default constructor for Stopover.
     */
    public Stopover() {}

    /**
     * Gets the airport of the stopover.
     * @return the stopover airport
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
     * @return the departure time
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time of the stopover.
     * @param departureTime the departure time to set
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time of the stopover.
     * @return the arrival time
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time of the stopover.
     * @param arrivalTime the arrival time to set
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}

/**
 * Represents a reservation for a passenger on a flight.
 */
class Reservation {
    private String id;
    private String passengerName;
    private Flight flight;
    private boolean isConfirmed;

    /**
     * Default constructor for Reservation.
     */
    public Reservation() {}

    /**
     * Gets the unique identifier of the reservation.
     * @return the reservation's id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the reservation.
     * @param id the reservation's id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the passenger.
     * @return the passenger's name
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * Sets the name of the passenger.
     * @param passengerName the passenger's name to set
     */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /**
     * Gets the flight associated with the reservation.
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Sets the flight associated with the reservation.
     * @param flight the flight to set
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Checks if the reservation is confirmed.
     * @return true if confirmed, false otherwise
     */
    public boolean isConfirmed() {
        return isConfirmed;
    }

    /**
     * Sets the confirmation status of the reservation.
     * @param confirmed true to confirm, false to cancel
     */
    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}

/**
 * Represents a flight offered by an airline.
 */
class Flight {
    private String id;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private List<Stopover> stopovers;
    private boolean isOpen;
    private List<Reservation> reservations;

    /**
     * Default constructor for Flight.
     */
    public Flight() {
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    /**
     * Gets the unique identifier of the flight.
     * @return the flight's id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the flight.
     * @param id the flight's id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the departure airport of the flight.
     * @return the departure airport
     */
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    /**
     * Sets the departure airport of the flight.
     * @param departureAirport the departure airport to set
     */
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    /**
     * Gets the arrival airport of the flight.
     * @return the arrival airport
     */
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    /**
     * Sets the arrival airport of the flight.
     * @param arrivalAirport the arrival airport to set
     */
    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    /**
     * Gets the departure time of the flight.
     * @return the departure time
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time of the flight.
     * @param departureTime the departure time to set
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time of the flight.
     * @return the arrival time
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time of the flight.
     * @param arrivalTime the arrival time to set
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the list of stopovers in the flight.
     * @return the list of stopovers
     */
    public List<Stopover> getStopovers() {
        return stopovers;
    }

    /**
     * Sets the list of stopovers in the flight.
     * @param stopovers the list of stopovers to set
     */
    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    /**
     * Checks if the flight is open for booking.
     * @return true if open, false otherwise
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Sets the status of the flight.
     * @param open true to open, false to close
     */
    public void setOpen(boolean open) {
        isOpen = open;
    }

    /**
     * Gets the list of reservations for the flight.
     * @return the list of reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the list of reservations for the flight.
     * @param reservations the list of reservations to set
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Publishes the flight for booking.
     * @return true on success, false otherwise
     */
    public boolean publish() {
        if (isOpen) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                if (departureTime.isBefore(now) || arrivalTime.isBefore(departureTime) || departureAirport == arrivalAirport) {
                    return false;
                }
                // Additional checks for stopovers can be added here
                isOpen = true;
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Creates a booking for passengers on the flight.
     * @param passengerNames the list of passenger names
     * @return true on success, false otherwise
     */
    public boolean createBooking(List<String> passengerNames) {
        if (!isOpen || departureTime.isBefore(LocalDateTime.now())) {
            return false;
        }
        for (String name : passengerNames) {
            if (reservations.stream().anyMatch(r -> r.getPassengerName().equals(name))) {
                return false; // Duplicate passenger
            }
            Reservation reservation = new Reservation();
            reservation.setId(UUID.randomUUID().toString());
            reservation.setPassengerName(name);
            reservation.setFlight(this);
            reservation.setConfirmed(false);
            reservations.add(reservation);
        }
        return true;
    }

    /**
     * Confirms or cancels an existing reservation.
     * @param reservationId the id of the reservation
     * @param confirm true to confirm, false to cancel
     * @return true on success, false otherwise
     */
    public boolean updateReservation(String reservationId, boolean confirm) {
        if (!isOpen || departureTime.isBefore(LocalDateTime.now())) {
            return false;
        }
        Reservation reservation = reservations.stream()
                .filter(r -> r.getId().equals(reservationId))
                .findFirst()
                .orElse(null);
        if (reservation != null) {
            reservation.setConfirmed(confirm);
            return true;
        }
        return false;
    }

    /**
     * Closes the flight and cancels confirmed reservations.
     * @return true on success, false otherwise
     */
    public boolean close() {
        if (isOpen && departureTime.isAfter(LocalDateTime.now())) {
            isOpen = false;
            reservations.stream()
                    .filter(Reservation::isConfirmed)
                    .forEach(r -> r.setConfirmed(false));
            return true;
        }
        return false;
    }

    /**
     * Retrieves all confirmed reservations for the flight.
     * @return the list of confirmed reservations
     */
    public List<Reservation> getConfirmedReservations() {
        if (isOpen) {
            return reservations.stream()
                    .filter(Reservation::isConfirmed)
                    .toList();
        }
        return new ArrayList<>();
    }

    /**
     * Adds a stopover to the flight.
     * @param stopover the stopover to add
     * @return true on success, false otherwise
     */
    public boolean addStopover(Stopover stopover) {
        if (departureTime.isAfter(LocalDateTime.now()) && stopover.getDepartureTime().isAfter(departureTime) && stopover.getArrivalTime().isBefore(arrivalTime)) {
            stopovers.add(stopover);
            return true;
        }
        return false;
    }

    /**
     * Deletes a stopover from the flight.
     * @param stopover the stopover to delete
     * @return true on success, false otherwise
     */
    public boolean deleteStopover(Stopover stopover) {
        if (departureTime.isAfter(LocalDateTime.now())) {
            return stopovers.remove(stopover);
        }
        return false;
    }
}

 class Main {
    public static void main(String[] args) {
        // Example usage
        Airport departureAirport = new Airport();
        departureAirport.setId("D1");

        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("A1");

        Flight flight = new Flight();
        flight.setId("F1");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2024-12-15 10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        flight.setArrivalTime(LocalDateTime.parse("2024-12-15 12:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        flight.setOpen(true);

        System.out.println(flight.publish()); // Should print: true

        List<String> passengerNames = List.of("John Doe", "Jane Doe");
        System.out.println(flight.createBooking(passengerNames)); // Should print: true

        // Further example usage can be added here
    }
}