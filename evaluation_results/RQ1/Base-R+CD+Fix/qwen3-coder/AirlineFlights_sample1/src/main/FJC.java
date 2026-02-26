import java.util.*;
import java.text.SimpleDateFormat;

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

    /**
     * Adds a flight to the airline's flight list.
     *
     * @param f the flight to add
     */
    public void addFlight(Flight f) {
        flights.add(f);
    }

    /**
     * Removes a flight from the airline's flight list.
     *
     * @param f the flight to remove
     */
    public void removeFlight(Flight f) {
        flights.remove(f);
    }

    /**
     * Publishes a flight for booking, making it available to customers.
     * Validates flight timestamps, temporal consistency, route integrity,
     * and ensures the flight is not already published and is open for booking.
     *
     * @param f   the flight to publish
     * @param now the current date/time
     * @return true if the flight was successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        // Check if flight is already published (not open for booking)
        if (!f.isOpenForBooking()) {
            return false;
        }

        // Validate timestamp formats (assumed to be validated during object creation)

        // Check temporal consistency: currentTime < departureTime < arrivalTime
        if (!(now.before(f.getDepartureTime()) && f.getDepartureTime().before(f.getArrivalTime()))) {
            return false;
        }

        // Check route integrity: departureAirport ≠ arrivalAirport
        if (f.getDepartureAirport().equals(f.getArrivalAirport())) {
            return false;
        }

        // If all validations pass, publish the flight (it's already open for booking by default)
        return true;
    }

    /**
     * Closes an open flight and cancels all confirmed reservations if the flight hasn't departed.
     *
     * @param flightId the ID of the flight to close
     * @param now      the current date/time
     * @return true if the flight was successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight flight = null;
        for (Flight f : flights) {
            if (f.getId().equals(flightId)) {
                flight = f;
                break;
            }
        }

        if (flight == null || !flight.isOpenForBooking() || !now.before(flight.getDepartureTime())) {
            return false;
        }

        flight.setOpenForBooking(false);

        // Cancel all confirmed reservations
        for (Reservation res : flight.getReservations()) {
            if (res.getStatus() == ReservationStatus.CONFIRMED) {
                res.setStatus(ReservationStatus.CANCELED);
            }
        }

        return true;
    }

    /**
     * Searches for flights based on origin, destination, and date.
     *
     * @param origin  the origin city name
     * @param dest    the destination city name
     * @param date    the travel date
     * @return a list of matching flights
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String targetDateStr = sdf.format(date);

        for (Flight flight : flights) {
            if (!flight.isOpenForBooking()) continue;

            String depCity = null;
            String arrCity = null;

            for (City c : flight.getDepartureAirport().getServesForCities()) {
                if (c.getName().equals(origin)) {
                    depCity = origin;
                    break;
                }
            }

            for (City c : flight.getArrivalAirport().getServesForCities()) {
                if (c.getName().equals(dest)) {
                    arrCity = dest;
                    break;
                }
            }

            if (depCity != null && arrCity != null &&
                targetDateStr.equals(sdf.format(flight.getDepartureTime()))) {
                result.add(flight);
            }
        }

        return result;
    }
}

/**
 * Represents a flight with scheduling and reservation information.
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
     * Adds a stopover to the flight if the flight hasn't departed and times are valid.
     *
     * @param stop the stopover to add
     * @param now  the current date/time
     * @return true if the stopover was added successfully, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (!isOpenForBooking() || !now.before(departureTime)) {
            return false;
        }

        // Verify that the stopover times fit within the overall flight schedule
        if (!(stop.getArrivalTime().after(departureTime) && stop.getDepartureTime().before(arrivalTime))) {
            return false;
        }

        // Ensure the airport serves valid cities
        if (stop.getAirport().getServesForCities().isEmpty()) {
            return false;
        }

        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight if the flight hasn't departed.
     *
     * @param stop the stopover to remove
     * @param now  the current date/time
     * @return true if the stopover was removed successfully, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (!isOpenForBooking() || !now.before(departureTime)) {
            return false;
        }

        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight if it's open for booking.
     *
     * @return a list of confirmed reservations, or empty list if none exist
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
 * Represents a stopover in a flight with arrival and departure times at an airport.
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
 * Represents an airport serving one or more cities.
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

    /**
     * Adds a city to the list of cities served by this airport.
     *
     * @param c the city to add
     */
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
     * @param f                     the flight to book
     * @param now                   the current date/time
     * @param listOfPassengerNames  the list of passenger names
     * @return true if the booking was created successfully, false otherwise
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
     * @param reservationID the ID of the reservation to confirm
     * @param now           the current date/time
     * @return true if the reservation was confirmed successfully, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    Flight flight = reservation.getFlight();
                    if (flight.isOpenForBooking() && now.before(flight.getDepartureTime())) {
                        reservation.setStatus(ReservationStatus.CONFIRMED);
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Cancels an existing reservation in a booking.
     *
     * @param reservationID the ID of the reservation to cancel
     * @param now           the current date/time
     * @return true if the reservation was canceled successfully, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    Flight flight = reservation.getFlight();
                    if (flight.isOpenForBooking() && now.before(flight.getDepartureTime())) {
                        reservation.setStatus(ReservationStatus.CANCELED);
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }
}

/**
 * Represents a passenger with a name.
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
 * Represents a booking made by a customer for one or more passengers on a flight.
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
     * @param f         the flight
     * @param passenger the passenger name
     * @param now       the current date/time
     * @return true if the reservation was created successfully, false otherwise
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (!f.isOpenForBooking() || !now.before(f.getDepartureTime())) {
            return false;
        }

        // Check if passenger already has a reservation on this flight
        for (Reservation res : f.getReservations()) {
            if (res.getPassenger().getName().equals(passenger)) {
                return false;
            }
        }

        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());
        reservation.setStatus(ReservationStatus.PENDING);

        Passenger p = new Passenger();
        p.setName(passenger);
        reservation.setPassenger(p);

        reservation.setFlight(f);

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
 * Represents a reservation for a passenger on a specific flight.
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