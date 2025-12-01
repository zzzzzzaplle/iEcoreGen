import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Airline {
    private List<Flight> flights = new ArrayList<>();

    /**
     * Adds a flight to the airline's list of flights.
     * @param f The flight to be added.
     */
    public void addFlight(Flight f) {
        flights.add(f);
    }

    /**
     * Removes a flight from the airline's list of flights.
     * @param f The flight to be removed.
     */
    public void removeFlight(Flight f) {
        flights.remove(f);
    }

    /**
     * Publishes a flight for booking.
     * @param f The flight to be published.
     * @param now The current time.
     * @return true if the flight is published successfully, false otherwise.
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f.isOpenForBooking() && f.validateTimes(now) && f.validateRoute()) {
            f.setOpenForBooking(true);
            return true;
        }
        return false;
    }

    /**
     * Closes an open flight.
     * @param flightId The unique ID of the flight to be closed.
     * @param now The current time.
     * @return true if the flight is closed successfully, false otherwise.
     */
    public boolean closeFlight(String flightId, Date now) {
        for (Flight f : flights) {
            if (f.getId().equals(flightId) && f.isOpenForBooking() && f.getDepartureTime().after(now)) {
                f.setOpenForBooking(false);
                f.cancelAllReservations();
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for flights from origin to destination on the specified date.
     * @param origin The departure city.
     * @param dest The arrival city.
     * @param date The date of the flight.
     * @return A list of flights matching the criteria.
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getDepartureAirport().hasCity(origin) && f.getArrialAirport().hasCity(dest)
                    && isSameDay(f.getDepartureTime(), date)) {
                result.add(f);
            }
        }
        return result;
    }

    // Getters and setters
    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    private boolean isSameDay(Date date1, Date date2) {
        // Implementation of isSameDay
        return date1.toInstant().truncatedTo(java.time.Day ChronoUnit.DAYS)
                .equals(date2.toInstant().truncatedTo(java.time.Day ChronoUnit.DAYS));
    }
}

class Flight {
    private String id;
    private boolean openForBooking;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrialAirport;
    private List<Stopover> stopovers = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    /**
     * Adds a stopover to the flight.
     * @param stop The stopover to be added.
     * @param now The current time.
     * @return true if the stopover is added successfully, false otherwise.
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (departureTime.after(now) && isValidStopover(stop)) {
            stopovers.add(stop);
            return true;
        }
        return false;
    }

    /**
     * Removes a stopover from the flight.
     * @param stop The stopover to be removed.
     * @param now The current time.
     * @return true if the stopover is removed successfully, false otherwise.
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (departureTime.after(now)) {
            stopovers.remove(stop);
            return true;
        }
        return false;
    }

    /**
     * Gets all confirmed reservations for the flight.
     * @return A list of confirmed reservations.
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmedReservations = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                confirmedReservations.add(r);
            }
        }
        return confirmedReservations;
    }

    /**
     * Validates the departure and arrival times of the flight.
     * @param now The current time.
     * @return true if the times are valid, false otherwise.
     */
    public boolean validateTimes(Date now) {
        if (departureTime.before(now)) {
            return false;
        }
        if (departureTime.after(arrivalTime)) {
            return false;
        }
        return true;
    }

    /**
     * Validates the route of the flight.
     * @return true if the route is valid, false otherwise.
     */
    public boolean validateRoute() {
        return departureAirport != arrialAirport;
    }

    /**
     * Cancels all reservations for the flight.
     */
    public void cancelAllReservations() {
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELED);
            }
        }
    }

    private boolean isValidStopover(Stopover stop) {
        // Implementation of isValidStopover
        return true;
    }

    // Getters and setters
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

    public Airport getArrialAirport() {
        return arrialAirport;
    }

    public void setArrialAirport(Airport arrialAirport) {
        this.arrialAirport = arrialAirport;
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

class Stopover {
    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    // Getters and setters
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

class Airport {
    private String id;
    private List<City> servesForCities = new ArrayList<>();

    /**
     * Adds a city to the list of cities served by the airport.
     * @param c The city to be added.
     */
    public void addCity(City c) {
        servesForCities.add(c);
    }

    /**
     * Checks if the airport serves a specific city.
     * @param cityName The name of the city.
     * @return true if the airport serves the city, false otherwise.
     */
    public boolean hasCity(String cityName) {
        for (City c : servesForCities) {
            if (c.getName().equals(cityName)) {
                return true;
            }
        }
        return false;
    }

    // Getters and setters
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

class City {
    private String name;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Customer {
    private List<Booking> bookings = new ArrayList<>();

    /**
     * Adds a booking for the customer.
     * @param f The flight to be booked.
     * @param now The current time.
     * @param listOfPassengerNames The list of passenger names.
     * @return true if the booking is created successfully, false otherwise.
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        Booking booking = new Booking();
        booking.setCustomer(this);
        for (String passengerName : listOfPassengerNames) {
            if (booking.createReservation(f, passengerName, now)) {
                bookings.add(booking);
                return true;
            }
        }
        return false;
    }

    /**
     * Confirms a reservation.
     * @param reservationID The unique ID of the reservation.
     * @param now The current time.
     * @return true if the reservation is confirmed successfully, false otherwise.
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId().equals(reservationID) && r.getFlight().getDepartureTime().after(now)) {
                    r.setStatus(ReservationStatus.CONFIRMED);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cancels a reservation.
     * @param reservationID The unique ID of the reservation.
     * @param now The current time.
     * @return true if the reservation is canceled successfully, false otherwise.
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId().equals(reservationID) && r.getFlight().getDepartureTime().after(now)) {
                    r.setStatus(ReservationStatus.CANCELED);
                    return true;
                }
            }
        }
        return false;
    }

    // Getters and setters
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}

class Passenger {
    private String name;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Booking {
    private Customer customer;
    private List<Reservation> reservations = new ArrayList<>();

    /**
     * Creates a reservation for a passenger on a flight.
     * @param f The flight to be reserved.
     * @param passenger The name of the passenger.
     * @param now The current time.
     * @return true if the reservation is created successfully, false otherwise.
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (f.getDepartureTime().after(now) && !hasDuplicatePassenger(f, passenger)) {
            Reservation reservation = new Reservation();
            reservation.setFlight(f);
            reservation.setPassenger(new Passenger());
            reservation.getPassenger().setName(passenger);
            reservation.setStatus(ReservationStatus.PENDING);
            f.getReservations().add(reservation);
            reservations.add(reservation);
            return true;
        }
        return false;
    }

    private boolean hasDuplicatePassenger(Flight f, String passengerName) {
        for (Reservation r : f.getReservations()) {
            if (r.getPassenger().getName().equals(passengerName)) {
                return true;
            }
        }
        return false;
    }

    // Getters and setters
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

enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELED
}

class Reservation {
    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    /**
     * Sets the status of the reservation.
     * @param s The status to be set.
     */
    public void setStatus(ReservationStatus s) {
        this.status = s;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReservationStatus getStatus() {
        return status;
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