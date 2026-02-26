import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Represents an airline that manages flights.
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
     * Publishes a flight for booking if it meets all required conditions.
     *
     * @param f   the flight to be published
     * @param now the current date and time
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        // Check if flight is already published (not open for booking)
        if (!f.isOpenForBooking()) {
            return false;
        }

        // Validate timestamps format (assuming they are already Date objects)
        // Check temporal consistency: currentTime < departureTime < arrivalTime
        if (!(now.before(f.getDepartureTime()) && f.getDepartureTime().before(f.getArrivalTime()))) {
            return false;
        }

        // Check route integrity: departureAirport ≠ arrivalAirport
        if (f.getDepartureAirport().equals(f.getArrivalAirport())) {
            return false;
        }

        // If all checks pass, flight is already considered "published" as openForBooking=true
        // In a more complex system, we might set additional flags here
        return true;
    }

    /**
     * Closes an open flight and cancels all confirmed reservations.
     *
     * @param flightId the ID of the flight to close
     * @param now      the current date and time
     * @return true if the flight is successfully closed, false otherwise
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
        for (Reservation reservation : flight.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                reservation.setStatus(ReservationStatus.CANCELLED);
            }
        }

        return true;
    }

    /**
     * Searches for flights based on origin, destination, and date.
     *
     * @param origin      the departure city name
     * @param dest        the arrival city name
     * @param date        the date of travel
     * @return a list of matching flights
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String targetDate = sdf.format(date);

        for (Flight flight : flights) {
            if (!flight.isOpenForBooking()) continue;

            String flightDate = sdf.format(flight.getDepartureTime());

            boolean originMatch = false;
            boolean destMatch = false;

            // Check departure airport serves origin city
            for (City city : flight.getDepartureAirport().getServesForCities()) {
                if (city.getName().equalsIgnoreCase(origin)) {
                    originMatch = true;
                    break;
                }
            }

            // Check arrival airport serves destination city
            for (City city : flight.getArrivalAirport().getServesForCities()) {
                if (city.getName().equalsIgnoreCase(dest)) {
                    destMatch = true;
                    break;
                }
            }

            if (originMatch && destMatch && flightDate.equals(targetDate)) {
                result.add(flight);
            }
        }

        return result;
    }
}

/**
 * Represents a flight with schedule, route, and booking information.
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
     * Adds a stopover to the flight if it fits within the schedule.
     *
     * @param stop the stopover to add
     * @param now  the current date and time
     * @return true if the stopover is added successfully, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (!isOpenForBooking() || !now.before(departureTime)) {
            return false;
        }

        // Verify that the airport serves valid cities
        if (stop.getAirport().getServesForCities().isEmpty()) {
            return false;
        }

        // Check if stopover times fit within the overall flight schedule
        if (!(departureTime.before(stop.getArrivalTime()) && stop.getDepartureTime().before(arrivalTime))) {
            return false;
        }

        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     *
     * @param stop the stopover to remove
     * @param now  the current date and time
     * @return true if the stopover is removed successfully, false otherwise
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
     * @return a list of confirmed reservations
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmedReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                confirmedReservations.add(reservation);
            }
        }
        return confirmedReservations;
    }
}

/**
 * Represents a stopover in a flight's journey.
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
 * Represents an airport that serves one or more cities.
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
 * Represents a city served by airports.
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
     * @param f                    the flight to book
     * @param now                  the current date and time
     * @param listOfPassengerNames the list of passenger names
     * @return true if the booking is created successfully, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (!f.isOpenForBooking() || !now.before(f.getDepartureTime())) {
            return false;
        }

        // Check for duplicate passengers on this flight
        Set<String> existingPassengers = new HashSet<>();
        for (Reservation res : f.getReservations()) {
            existingPassengers.add(res.getPassenger().getName());
        }

        for (String passengerName : listOfPassengerNames) {
            if (existingPassengers.contains(passengerName)) {
                return false;
            }
        }

        Booking booking = new Booking();
        booking.setCustomer(this);

        for (String passengerName : listOfPassengerNames) {
            booking.createReservation(f, passengerName, now);
        }

        bookings.add(booking);
        return true;
    }

    /**
     * Confirms a reservation in one of the customer's bookings.
     *
     * @param reservationID the ID of the reservation to confirm
     * @param now           the current date and time
     * @return true if the reservation is confirmed successfully, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    Flight flight = reservation.getFlight();
                    if (!flight.isOpenForBooking() || !now.before(flight.getDepartureTime())) {
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
     * Cancels a reservation in one of the customer's bookings.
     *
     * @param reservationID the ID of the reservation to cancel
     * @param now           the current date and time
     * @return true if the reservation is canceled successfully, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    Flight flight = reservation.getFlight();
                    if (!flight.isOpenForBooking() || !now.before(flight.getDepartureTime())) {
                        return false;
                    }
                    reservation.setStatus(ReservationStatus.CANCELLED);
                    return true;
                }
            }
        }
        return false;
    }
}

/**
 * Represents a passenger traveling on a flight.
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
 * Represents a booking made by a customer for one or more passengers.
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
     * @param f         the flight for the reservation
     * @param passengerName the name of the passenger
     * @param now       the current date and time
     * @return true if the reservation is created successfully, false otherwise
     */
    public boolean createReservation(Flight f, String passengerName, Date now) {
        if (!f.isOpenForBooking() || !now.before(f.getDepartureTime())) {
            return false;
        }

        Passenger passenger = new Passenger();
        passenger.setName(passengerName);

        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
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
    CANCELLED
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