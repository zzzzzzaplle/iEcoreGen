import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Represents an airline that manages flights.
 */
class Airline {
    private List<Flight> flights;

    /**
     * Constructs a new Airline with empty flights list.
     */
    public Airline() {
        this.flights = new ArrayList<>();
    }

    /**
     * Adds a flight to the airline's flight list.
     *
     * @param f The flight to be added.
     */
    public void addFlight(Flight f) {
        flights.add(f);
    }

    /**
     * Removes a flight from the airline's flight list.
     *
     * @param f The flight to be removed.
     */
    public void removeFlight(Flight f) {
        flights.remove(f);
    }

    /**
     * Publishes a flight for booking after validating its properties.
     *
     * @param f   The flight to be published.
     * @param now The current date and time.
     * @return True if the flight was successfully published, false otherwise.
     */
    public boolean publishFlight(Flight f, Date now) {
        // Check that flight is not already published and is open for booking
        if (!f.isOpenForBooking()) {
            return false;
        }

        // Validate departure and arrival timestamps format (assumed to be done during object creation)
        
        // Check temporal consistency
        if (!(now.before(f.getDepartureTime()) && f.getDepartureTime().before(f.getArrivalTime()))) {
            return false;
        }
        
        // Check route integrity
        if (f.getDepartureAirport().equals(f.getArrivalAirport())) {
            return false;
        }
        
        // Mark flight as published/open for booking
        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an open flight that hasn't departed yet.
     *
     * @param flightId The ID of the flight to be closed.
     * @param now      The current date and time.
     * @return True if the flight was successfully closed, false otherwise.
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
        
        // Cancel every confirmed reservation
        for (Reservation res : flight.getReservations()) {
            if (res.getStatus() == ReservationStatus.CONFIRMED) {
                res.setStatus(ReservationStatus.CANCELLED);
            }
        }
        
        return true;
    }

    /**
     * Searches for flights between origin and destination airports on a given date.
     *
     * @param origin The origin airport ID.
     * @param dest   The destination airport ID.
     * @param date   The travel date.
     * @return A list of matching flights.
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String targetDateStr = sdf.format(date);
        
        for (Flight flight : flights) {
            String flightDateStr = sdf.format(flight.getDepartureTime());
            
            if (flight.getDepartureAirport().getId().equals(origin) &&
                flight.getArrivalAirport().getId().equals(dest) &&
                flightDateStr.equals(targetDateStr) &&
                flight.isOpenForBooking()) {
                result.add(flight);
            }
        }
        
        return result;
    }

    // Getters and Setters
    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}

/**
 * Represents a flight with its details like schedule, airports, etc.
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

    /**
     * Constructs a new Flight with default values.
     */
    public Flight() {
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.openForBooking = false;
    }

    /**
     * Adds a stopover to the flight if it fits within the schedule.
     *
     * @param stop The stopover to be added.
     * @param now  The current date and time.
     * @return True if the stopover was successfully added, false otherwise.
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (!isOpenForBooking() || !now.before(departureTime)) {
            return false;
        }
        
        // Validate that stopover times fit within overall flight schedule
        if (!(stop.getArrivalTime().after(departureTime) && 
              stop.getDepartureTime().before(arrivalTime) &&
              stop.getArrivalTime().before(stop.getDepartureTime()))) {
            return false;
        }
        
        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     *
     * @param stop The stopover to be removed.
     * @param now  The current date and time.
     * @return True if the stopover was successfully removed, false otherwise.
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
     * @return A list of confirmed reservations.
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmed = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                confirmed.add(r);
            }
        }
        return confirmed;
    }

    // Getters and Setters
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
}

/**
 * Represents a stopover at an airport during a flight.
 */
class Stopover {
    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    /**
     * Constructs a new Stopover with default values.
     */
    public Stopover() {}

    // Getters and Setters
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

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}

/**
 * Represents an airport with its served cities.
 */
class Airport {
    private String id;
    private List<City> servesForCities;

    /**
     * Constructs a new Airport with default values.
     */
    public Airport() {
        this.servesForCities = new ArrayList<>();
    }

    /**
     * Adds a city to the list of cities served by this airport.
     *
     * @param c The city to be added.
     */
    public void addCity(City c) {
        servesForCities.add(c);
    }

    // Getters and Setters
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
}

/**
 * Represents a city.
 */
class City {
    // City implementation could include name, country, etc.
    
    /**
     * Constructs a new City with default values.
     */
    public City() {}
}

/**
 * Represents a customer who makes bookings.
 */
class Customer {
    private List<Booking> bookings;

    /**
     * Constructs a new Customer with default values.
     */
    public Customer() {
        this.bookings = new ArrayList<>();
    }

    /**
     * Creates a booking for passengers on a specified flight.
     *
     * @param flight               The flight to book.
     * @param now                  The current date and time.
     * @param listOfPassengerNames The list of passenger names.
     * @return True if the booking was successful, false otherwise.
     */
    public boolean addBooking(Flight flight, Date now, List<String> listOfPassengerNames) {
        if (!flight.isOpenForBooking() || !now.before(flight.getDepartureTime())) {
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
            if (!booking.createReservation(flight, passengerName, now)) {
                return false;
            }
        }

        bookings.add(booking);
        return true;
    }

    /**
     * Confirms an existing reservation in a booking.
     *
     * @param reservationID The ID of the reservation to confirm.
     * @param now           The current date and time.
     * @return True if the confirmation was successful, false otherwise.
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
     * @param reservationID The ID of the reservation to cancel.
     * @param now           The current date and time.
     * @return True if the cancellation was successful, false otherwise.
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    Flight flight = reservation.getFlight();
                    if (flight.isOpenForBooking() && now.before(flight.getDepartureTime())) {
                        reservation.setStatus(ReservationStatus.CANCELLED);
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    // Getters and Setters
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}

/**
 * Represents a passenger with their name.
 */
class Passenger {
    private String name;

    /**
     * Constructs a new Passenger with default values.
     */
    public Passenger() {}

    // Getters and Setters
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

    /**
     * Constructs a new Booking with default values.
     */
    public Booking() {
        this.reservations = new ArrayList<>();
    }

    /**
     * Creates a reservation for a passenger on a flight.
     *
     * @param flight       The flight for which the reservation is made.
     * @param passengerName The name of the passenger.
     * @param now          The current date and time.
     * @return True if the reservation was created successfully, false otherwise.
     */
    public boolean createReservation(Flight flight, String passengerName, Date now) {
        if (!flight.isOpenForBooking() || !now.before(flight.getDepartureTime())) {
            return false;
        }

        Passenger passenger = new Passenger();
        passenger.setName(passengerName);

        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString()); // Generate unique ID
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);

        reservations.add(reservation);
        flight.getReservations().add(reservation);
        return true;
    }

    // Getters and Setters
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
}

/**
 * Enumeration representing possible statuses of a reservation.
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

    /**
     * Constructs a new Reservation with default values.
     */
    public Reservation() {}

    // Getters and Setters
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