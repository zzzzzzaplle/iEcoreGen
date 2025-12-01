import java.util.*;

/**
 * Represents an airline company that manages flights.
 */
class Airline {
    private List<Flight> flights;

    public Airline() {
        this.flights = new ArrayList<>();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void addFlight(Flight f) {
        flights.add(f);
    }

    public void removeFlight(Flight f) {
        flights.remove(f);
    }

    /**
     * Publishes a flight for booking.
     *
     * @param f   The flight to be published
     * @param now The current date and time
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        // Check if flight is already published (not open for booking)
        if (!f.isOpenForBooking()) {
            return false;
        }

        // Validate departure and arrival times format and values
        if (f.getDepartureTime() == null || f.getArrivalTime() == null) {
            return false;
        }

        // Check temporal consistency: currentTime < departureTime < arrivalTime
        if (!(now.before(f.getDepartureTime()) && f.getDepartureTime().before(f.getArrivalTime()))) {
            return false;
        }

        // Check route integrity: departureAirport ≠ arrivalAirport
        if (f.getDepartureAirport().equals(f.getArrivalAirport())) {
            return false;
        }

        // If all conditions pass, flight is considered published
        return true;
    }

    /**
     * Closes an open flight and cancels all confirmed reservations.
     *
     * @param flightId The ID of the flight to close
     * @param now      The current date and time
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight flightToClose = null;
        for (Flight f : flights) {
            if (f.getId().equals(flightId)) {
                flightToClose = f;
                break;
            }
        }

        if (flightToClose == null || !flightToClose.isOpenForBooking() || !now.before(flightToClose.getDepartureTime())) {
            return false;
        }

        // Close the flight
        flightToClose.setOpenForBooking(false);

        // Cancel all confirmed reservations
        for (Reservation res : flightToClose.getReservations()) {
            if (res.getStatus() == ReservationStatus.CONFIRMED) {
                res.setStatus(ReservationStatus.CANCELED);
            }
        }

        return true;
    }

    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f.isOpenForBooking() &&
                f.getDepartureAirport().getServesForCities().stream().anyMatch(city -> city.getName().equals(origin)) &&
                f.getArrivalAirport().getServesForCities().stream().anyMatch(city -> city.getName().equals(dest))) {
                Calendar flightDate = Calendar.getInstance();
                flightDate.setTime(f.getDepartureTime());
                
                Calendar searchDate = Calendar.getInstance();
                searchDate.setTime(date);
                
                if (flightDate.get(Calendar.YEAR) == searchDate.get(Calendar.YEAR) &&
                    flightDate.get(Calendar.DAY_OF_YEAR) == searchDate.get(Calendar.DAY_OF_YEAR)) {
                    result.add(f);
                }
            }
        }
        return result;
    }
}

/**
 * Represents a flight with its details.
 */
class Flight {
    private String id;
    private boolean openForBooking;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private List<Stopover> stopovers;
    private List<Reservation> reservations;

    public Flight() {
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.openForBooking = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOpenForBooking() {
        return openForBooking;
    }

    public void setOpenForBooking(boolean openForBooking) {
        this.openForBooking = openForBooking;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    public List<Stopover> getStopovers() {
        return stopovers;
    }

    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds a stopover to the flight.
     *
     * @param stop The stopover to add
     * @param now  The current date and time
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (!isOpenForBooking() || !now.before(departureTime)) {
            return false;
        }

        // Check that the stopover times fit within the overall flight schedule
        if (!(stop.getArrivalTime().after(departureTime) && stop.getDepartureTime().before(arrivalTime))) {
            return false;
        }

        // Check that airports serve valid cities
        if (stop.getAirport().getServesForCities().isEmpty()) {
            return false;
        }

        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     *
     * @param stop The stopover to remove
     * @param now  The current date and time
     * @return true if the stopover is successfully removed, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (!isOpenForBooking() || !now.before(departureTime)) {
            return false;
        }

        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     *
     * @return A list of confirmed reservations
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmedReservations = new ArrayList<>();
        if (!isOpenForBooking()) {
            return confirmedReservations;
        }

        for (Reservation res : reservations) {
            if (res.getStatus() == ReservationStatus.CONFIRMED) {
                confirmedReservations.add(res);
            }
        }
        return confirmedReservations;
    }
}

/**
 * Represents a stopover in a flight.
 */
class Stopover {
    private Date arrivalTime;
    private Date departureTime;
    private Airport airport;

    public Stopover() {}

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}

/**
 * Represents an airport.
 */
class Airport {
    private String id;
    private List<City> servesForCities;

    public Airport() {
        this.servesForCities = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<City> getServesForCities() {
        return servesForCities;
    }

    public void setServesForCities(List<City> servesForCities) {
        this.servesForCities = servesForCities;
    }

    public void addCity(City c) {
        servesForCities.add(c);
    }
}

/**
 * Represents a city.
 */
class City {
    private String name;

    public City() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a customer who makes bookings.
 */
class Customer {
    private List<Booking> bookings;

    public Customer() {
        this.bookings = new ArrayList<>();
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Creates a booking for passengers on a flight.
     *
     * @param f                   The flight to book
     * @param now                 The current date and time
     * @param listOfPassengerNames The list of passenger names
     * @return true if the booking is successfully created, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (!f.isOpenForBooking() || !now.before(f.getDepartureTime())) {
            return false;
        }

        // Check for duplicate passengers
        Set<String> uniquePassengers = new HashSet<>(listOfPassengerNames);
        if (uniquePassengers.size() != listOfPassengerNames.size()) {
            return false;
        }

        Booking booking = new Booking();
        booking.setCustomer(this);

        for (String passengerName : listOfPassengerNames) {
            if (!booking.createReservation(f, passengerName, now)) {
                return false;
            }
        }

        bookings.add(booking);
        return true;
    }

    /**
     * Confirms an existing reservation in a booking.
     *
     * @param reservationID The ID of the reservation to confirm
     * @param now           The current date and time
     * @return true if the reservation is successfully confirmed, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    Flight flight = reservation.getFlight();
                    if (now.after(flight.getDepartureTime()) || !flight.isOpenForBooking()) {
                        return false;
                    }
                    reservation.setStatus(ReservationStatus.CONFIRMED);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cancels an existing reservation in a booking.
     *
     * @param reservationID The ID of the reservation to cancel
     * @param now           The current date and time
     * @return true if the reservation is successfully canceled, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    Flight flight = reservation.getFlight();
                    if (now.after(flight.getDepartureTime()) || !flight.isOpenForBooking()) {
                        return false;
                    }
                    reservation.setStatus(ReservationStatus.CANCELED);
                    return true;
                }
            }
        }
        return false;
    }
}

/**
 * Represents a passenger.
 */
class Passenger {
    private String name;

    public Passenger() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a booking made by a customer.
 */
class Booking {
    private Customer customer;
    private List<Reservation> reservations;

    public Booking() {
        this.reservations = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Creates a reservation for a passenger on a flight.
     *
     * @param f         The flight for the reservation
     * @param passengerName The name of the passenger
     * @param now       The current date and time
     * @return true if the reservation is successfully created, false otherwise
     */
    public boolean createReservation(Flight f, String passengerName, Date now) {
        if (!f.isOpenForBooking() || !now.before(f.getDepartureTime())) {
            return false;
        }

        Passenger passenger = new Passenger();
        passenger.setName(passengerName);

        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());
        reservation.setPassenger(passenger);
        reservation.setFlight(f);
        reservation.setStatus(ReservationStatus.PENDING);

        reservations.add(reservation);
        f.getReservations().add(reservation);
        return true;
    }
}

/**
 * Enumeration of possible reservation statuses.
 */
enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELED
}

/**
 * Represents a reservation for a passenger on a flight.
 */
class Reservation {
    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    public Reservation() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}