import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Airline {
    private List<Flight> flights;

    public Airline() {
        this.flights = new ArrayList<>();
    }

    public void addFlight(Flight f) {
        this.flights.add(f);
    }

    public void removeFlight(Flight f) {
        this.flights.remove(f);
    }

    /**
     * Publishes a flight for booking if it meets all the required conditions.
     *
     * @param f The flight to be published
     * @param now The current time
     * @return true if the flight was successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f.isValidTimeFormat() && f.isTemporallyConsistent(now) && f.hasValidRoute()) {
            f.setOpenForBooking(true);
            return true;
        }
        return false;
    }

    /**
     * Closes an open flight and cancels all confirmed reservations.
     *
     * @param flightId The ID of the flight to be closed
     * @param now The current time
     * @return true if the flight was successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        for (Flight flight : flights) {
            if (flight.getId().equals(flightId) && flight.isOpenForBooking()) {
                flight.setOpenForBooking(false);
                flight.cancelAllConfirmedReservations();
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for flights based on origin, destination, and date.
     *
     * @param origin The origin city
     * @param dest The destination city
     * @param date The departure date
     * @return A list of flights matching the search criteria
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getDepartureAirport().getServesForCities().contains(new City(origin)) &&
                flight.getArrialAirport().getServesForCities().contains(new City(dest)) &&
                flight.getDepartureTime().equals(date)) {
                result.add(flight);
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
}

class Flight {
    private String id;
    private boolean openForBooking;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrialAirport;
    private List<Stopover> stopovers;
    private List<Reservation> reservations;

    public Flight() {
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    /**
     * Adds a stopover to the flight if it fits within the overall flight schedule.
     *
     * @param stop The stopover to be added
     * @param now The current time
     * @return true if the stopover was successfully added, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (isBeforeDeparture(now) && stop.isValid()) {
            this.stopovers.add(stop);
            return true;
        }
        return false;
    }

    /**
     * Removes a stopover from the flight.
     *
     * @param stop The stopover to be removed
     * @param now The current time
     * @return true if the stopover was successfully removed, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (isBeforeDeparture(now)) {
            this.stopovers.remove(stop);
            return true;
        }
        return false;
    }

    /**
     * Retrieves all confirmed reservations for the flight.
     *
     * @return A list of confirmed reservations
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

    /**
     * Checks if the flight has valid departure and arrival timestamps.
     *
     * @return true if the timestamps are valid, false otherwise
     */
    public boolean isValidTimeFormat() {
        return departureTime != null && arrivalTime != null;
    }

    /**
     * Checks if the flight's departure and arrival times are temporally consistent.
     *
     * @param now The current time
     * @return true if the times are consistent, false otherwise
     */
    public boolean isTemporallyConsistent(Date now) {
        return now.before(departureTime) && departureTime.before(arrivalTime);
    }

    /**
     * Checks if the flight has a valid route.
     *
     * @return true if the route is valid, false otherwise
     */
    public boolean hasValidRoute() {
        return departureAirport != null && arrialAirport != null && !departureAirport.equals(arrialAirport);
    }

    /**
     * Checks if the current time is before the flight's departure time.
     *
     * @param now The current time
     * @return true if the current time is before departure, false otherwise
     */
    public boolean isBeforeDeparture(Date now) {
        return now.before(departureTime);
    }

    /**
     * Cancels all confirmed reservations on the flight.
     */
    public void cancelAllConfirmedReservations() {
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                reservation.setStatus(ReservationStatus.CANCELED);
            }
        }
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

    public Stopover() {
    }

    /**
     * Checks if the stopover has valid departure and arrival times.
     *
     * @return true if the times are valid, false otherwise
     */
    public boolean isValid() {
        return departureTime != null && arrivalTime != null && departureTime.before(arrivalTime);
    }

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
    private List<City> servesForCities;

    public Airport() {
        this.servesForCities = new ArrayList<>();
    }

    public void addCity(City c) {
        this.servesForCities.add(c);
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

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Customer {
    private List<Booking> bookings;

    public Customer() {
        this.bookings = new ArrayList<>();
    }

    /**
     * Creates a booking for a list of passengers on a specific flight.
     *
     * @param f The flight to be booked
     * @param now The current time
     * @param listOfPassengerNames A list of passenger names
     * @return true if the booking was successfully created, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f.isBeforeDeparture(now) && !hasDuplicatePassengers(f, listOfPassengerNames)) {
            Booking booking = new Booking();
            booking.setCustomer(this);
            for (String passengerName : listOfPassengerNames) {
                booking.createReservation(f, passengerName, now);
            }
            this.bookings.add(booking);
            return true;
        }
        return false;
    }

    /**
     * Confirms a reservation by its ID.
     *
     * @param reservationID The ID of the reservation to be confirmed
     * @param now The current time
     * @return true if the reservation was successfully confirmed, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID) && reservation.getFlight().isBeforeDeparture(now)) {
                    reservation.setStatus(ReservationStatus.CONFIRMED);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cancels a reservation by its ID.
     *
     * @param reservationID The ID of the reservation to be canceled
     * @param now The current time
     * @return true if the reservation was successfully canceled, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID) && reservation.getFlight().isBeforeDeparture(now)) {
                    reservation.setStatus(ReservationStatus.CANCELED);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a list of passenger names contains duplicates for a specific flight.
     *
     * @param f The flight to be checked
     * @param listOfPassengerNames A list of passenger names
     * @return true if there are duplicate passengers, false otherwise
     */
    private boolean hasDuplicatePassengers(Flight f, List<String> listOfPassengerNames) {
        for (Reservation reservation : f.getReservations()) {
            if (listOfPassengerNames.contains(reservation.getPassenger().getName())) {
                return true;
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

    public Passenger() {
    }

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
    private List<Reservation> reservations;

    public Booking() {
        this.reservations = new ArrayList<>();
    }

    /**
     * Creates a reservation for a passenger on a specific flight.
     *
     * @param f The flight to be reserved
     * @param passenger The name of the passenger
     * @param now The current time
     * @return true if the reservation was successfully created, false otherwise
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (f.isBeforeDeparture(now)) {
            Reservation reservation = new Reservation();
            reservation.setId(java.util.UUID.randomUUID().toString());
            reservation.setPassenger(new Passenger());
            reservation.getPassenger().setName(passenger);
            reservation.setFlight(f);
            reservation.setStatus(ReservationStatus.PENDING);
            this.reservations.add(reservation);
            f.getReservations().add(reservation);
            return true;
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

    public Reservation() {
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