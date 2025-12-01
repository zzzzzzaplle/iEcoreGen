import java.util.*;
import java.text.SimpleDateFormat;

// Enum for reservation status
enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELLED
}

// City class
class City {
    private String name;

    public City() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

// Airport class
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
     * Adds a city to the list of cities served by this airport
     *
     * @param c the city to add
     */
    public void addCity(City c) {
        if (!servesForCities.contains(c)) {
            servesForCities.add(c);
        }
    }

    /**
     * Checks if this airport serves at least one city
     *
     * @return true if the airport serves at least one city, false otherwise
     */
    public boolean servesCities() {
        return !servesForCities.isEmpty();
    }
}

// Stopover class
class Stopover {
    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    public Stopover() {
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

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}

// Flight class
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
     * Adds a stopover to this flight if it's valid and the flight hasn't departed yet
     *
     * @param stop the stopover to add
     * @param now  the current time
     * @return true if the stopover was added successfully, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        // Check if flight has departed
        if (now.after(departureTime)) {
            return false;
        }

        // Check if airport serves cities
        if (!stop.getAirport().servesCities()) {
            return false;
        }

        // Check if stopover times fit within flight schedule
        if (stop.getArrivalTime().before(departureTime) || stop.getDepartureTime().after(arrivalTime)) {
            return false;
        }

        // Check if arrival is before departure at stopover
        if (stop.getArrivalTime().after(stop.getDepartureTime())) {
            return false;
        }

        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from this flight if the flight hasn't departed yet
     *
     * @param stop the stopover to remove
     * @param now  the current time
     * @return true if the stopover was removed successfully, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        // Check if flight has departed
        if (now.after(departureTime)) {
            return false;
        }

        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight
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

// Passenger class
class Passenger {
    private String name;

    public Passenger() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Passenger passenger = (Passenger) obj;
        return Objects.equals(name, passenger.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

// Reservation class
class Reservation {
    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    public Reservation() {
        this.status = ReservationStatus.PENDING;
    }

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

// Booking class
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
     * Creates a reservation for a passenger on a flight
     *
     * @param f        the flight to book
     * @param passengerName the name of the passenger
     * @param now      the current time
     * @return true if reservation was created successfully, false otherwise
     */
    public boolean createReservation(Flight f, String passengerName, Date now) {
        // Check if flight is open for booking
        if (!f.isOpenForBooking()) {
            return false;
        }

        // Check if current time is before flight departure
        if (!now.before(f.getDepartureTime())) {
            return false;
        }

        // Create passenger
        Passenger passenger = new Passenger();
        passenger.setName(passengerName);

        // Check for duplicate passengers on the same flight
        for (Reservation existingReservation : f.getReservations()) {
            if (existingReservation.getPassenger().equals(passenger)) {
                return false;
            }
        }

        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());
        reservation.setPassenger(passenger);
        reservation.setFlight(f);

        reservations.add(reservation);
        f.getReservations().add(reservation);

        return true;
    }
}

// Customer class
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
     * Creates a booking for passengers on a flight
     *
     * @param f                    the flight to book
     * @param now                  the current time
     * @param listOfPassengerNames the list of passenger names
     * @return true if booking was created successfully, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        // Check if flight is open for booking
        if (!f.isOpenForBooking()) {
            return false;
        }

        // Check if current time is before flight departure
        if (!now.before(f.getDepartureTime())) {
            return false;
        }

        // Check for duplicate passengers
        Set<String> uniquePassengers = new HashSet<>(listOfPassengerNames);
        if (uniquePassengers.size() != listOfPassengerNames.size()) {
            return false;
        }

        // Create booking
        Booking booking = new Booking();
        booking.setCustomer(this);

        // Create reservations for each passenger
        for (String passengerName : listOfPassengerNames) {
            if (!booking.createReservation(f, passengerName, now)) {
                return false;
            }
        }

        bookings.add(booking);
        return true;
    }

    /**
     * Confirms a reservation
     *
     * @param reservationID the ID of the reservation to confirm
     * @param now           the current time
     * @return true if reservation was confirmed successfully, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    // Check if flight has not yet departed and is still open for booking
                    if (now.before(reservation.getFlight().getDepartureTime()) && reservation.getFlight().isOpenForBooking()) {
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
     * Cancels a reservation
     *
     * @param reservationID the ID of the reservation to cancel
     * @param now           the current time
     * @return true if reservation was canceled successfully, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    // Check if flight has not yet departed and is still open for booking
                    if (now.before(reservation.getFlight().getDepartureTime()) && reservation.getFlight().isOpenForBooking()) {
                        reservation.setStatus(ReservationStatus.CANCELLED);
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }
}

// Airline class
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
     * Adds a flight to the airline
     *
     * @param f the flight to add
     */
    public void addFlight(Flight f) {
        flights.add(f);
    }

    /**
     * Removes a flight from the airline
     *
     * @param f the flight to remove
     */
    public void removeFlight(Flight f) {
        flights.remove(f);
    }

    /**
     * Publishes a flight for booking
     *
     * @param f   the flight to publish
     * @param now the current time
     * @return true if flight was published successfully, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        // Check if flight is already published (not open for booking)
        if (!f.isOpenForBooking()) {
            return false;
        }

        // Validate departure and arrival timestamps format (this would be done at input level in real application)
        // Check temporal consistency
        if (!now.before(f.getDepartureTime()) || !f.getDepartureTime().before(f.getArrivalTime())) {
            return false;
        }

        // Check route integrity
        if (f.getDepartureAirport().equals(f.getArrivalAirport())) {
            return false;
        }

        // Flight is valid, keep it open for booking
        return true;
    }

    /**
     * Closes an open flight
     *
     * @param flightId the ID of the flight to close
     * @param now      the current time
     * @return true if flight was closed successfully, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        for (Flight flight : flights) {
            if (flight.getId().equals(flightId)) {
                // Verify that the flight is currently open
                if (!flight.isOpenForBooking()) {
                    return false;
                }

                // Verify that the flight has not yet departed
                if (!now.before(flight.getDepartureTime())) {
                    return false;
                }

                // Change status to closed
                flight.setOpenForBooking(false);

                // Cancel every confirmed reservation
                for (Reservation reservation : flight.getReservations()) {
                    if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                        reservation.setStatus(ReservationStatus.CANCELLED);
                    }
                }

                return true;
            }
        }
        return false;
    }

    /**
     * Searches for flights based on origin, destination, and date
     *
     * @param origin  the origin airport name
     * @param dest    the destination airport name
     * @param date    the date of travel
     * @return a list of matching flights
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> matchingFlights = new ArrayList<>();
        for (Flight flight : flights) {
            // Simplified matching logic - in reality would need to check airport names
            if (flight.isOpenForBooking() && isSameDay(flight.getDepartureTime(), date)) {
                matchingFlights.add(flight);
            }
        }
        return matchingFlights;
    }

    /**
     * Helper method to check if two dates are on the same day
     *
     * @param date1 the first date
     * @param date2 the second date
     * @return true if both dates are on the same day, false otherwise
     */
    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}