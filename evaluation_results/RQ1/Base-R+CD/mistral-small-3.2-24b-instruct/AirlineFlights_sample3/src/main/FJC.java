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
     * Publishes a flight for booking if it meets the required conditions.
     * @param f The flight to be published.
     * @param now The current time.
     * @return true if the flight is published successfully, false otherwise.
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f.getDepartureTime().before(f.getArrivalTime()) &&
            f.getDepartureAirport() != f.getArrivalAirport() &&
            f.isOpenForBooking() && now.before(f.getDepartureTime())) {
            f.setOpenForBooking(false);
            return true;
        }
        return false;
    }

    /**
     * Closes an open flight and cancels all confirmed reservations.
     * @param flightId The ID of the flight to be closed.
     * @param now The current time.
     * @return true if the flight is closed successfully, false otherwise.
     */
    public boolean closeFlight(String flightId, Date now) {
        for (Flight f : flights) {
            if (f.getId().equals(flightId) && f.isOpenForBooking() && now.before(f.getDepartureTime())) {
                f.setOpenForBooking(false);
                for (Reservation r : f.getReservations()) {
                    if (r.getStatus() == ReservationStatus.CONFIRMED) {
                        r.setStatus(ReservationStatus.CANCELED);
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for flights based on origin, destination, and date.
     * @param origin The origin city.
     * @param dest The destination city.
     * @param date The date of the flight.
     * @return A list of flights matching the search criteria.
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getDepartureAirport().getServesForCities().contains(new City(origin)) &&
                f.getArrivalAirport().getServesForCities().contains(new City(dest)) &&
                f.getDepartureTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().equals(date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate())) {
                result.add(f);
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

class Flight {
    private String id;
    private boolean openForBooking;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private List<Stopover> stopovers = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    /**
     * Adds a stopover to the flight if it fits within the overall flight schedule.
     * @param stop The stopover to be added.
     * @param now The current time.
     * @return true if the stopover is added successfully, false otherwise.
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (now.before(this.departureTime) && stop.getDepartureTime().after(this.departureTime) &&
            stop.getArrivalTime().before(this.arrivalTime)) {
            this.stopovers.add(stop);
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
        if (now.before(this.departureTime)) {
            this.stopovers.remove(stop);
            return true;
        }
        return false;
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     * @return A list of confirmed reservations.
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmedReservations = new ArrayList<>();
        for (Reservation r : this.reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                confirmedReservations.add(r);
            }
        }
        return confirmedReservations;
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

class Stopover {
    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

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

class Airport {
    private String id;
    private List<City> servesForCities = new ArrayList<>();

    /**
     * Adds a city to the list of cities served by this airport.
     * @param c The city to be added.
     */
    public void addCity(City c) {
        this.servesForCities.add(c);
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

class City {
    private String name;

    public City(String name) {
        this.name = name;
    }

    // Getters and Setters
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
        City city = (City) obj;
        return name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

class Customer {
    private List<Booking> bookings = new ArrayList<>();

    /**
     * Creates a booking for a list of passengers on a specific flight.
     * @param f The flight to be booked.
     * @param now The current time.
     * @param listOfPassengerNames The list of passenger names.
     * @return true if the booking is created successfully, false otherwise.
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (now.before(f.getDepartureTime())) {
            Booking booking = new Booking(this);
            for (String passengerName : listOfPassengerNames) {
                if (!booking.createReservation(f, passengerName, now)) {
                    return false;
                }
            }
            this.bookings.add(booking);
            return true;
        }
        return false;
    }

    /**
     * Confirms a reservation by its ID.
     * @param reservationID The ID of the reservation to be confirmed.
     * @param now The current time.
     * @return true if the reservation is confirmed successfully, false otherwise.
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking b : this.bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId().equals(reservationID) && r.getStatus() == ReservationStatus.PENDING &&
                    now.before(r.getFlight().getDepartureTime())) {
                    r.setStatus(ReservationStatus.CONFIRMED);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cancels a reservation by its ID.
     * @param reservationID The ID of the reservation to be canceled.
     * @param now The current time.
     * @return true if the reservation is canceled successfully, false otherwise.
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking b : this.bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId().equals(reservationID) && now.before(r.getFlight().getDepartureTime())) {
                    r.setStatus(ReservationStatus.CANCELED);
                    return true;
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

class Passenger {
    private String name;

    // Getters and Setters
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

    public Booking(Customer customer) {
        this.customer = customer;
    }

    /**
     * Creates a reservation for a passenger on a specific flight.
     * @param f The flight to be reserved.
     * @param passenger The name of the passenger.
     * @param now The current time.
     * @return true if the reservation is created successfully, false otherwise.
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (now.before(f.getDepartureTime())) {
            for (Reservation r : this.reservations) {
                if (r.getPassenger().getName().equals(passenger)) {
                    return false;
                }
            }
            Reservation reservation = new Reservation();
            reservation.setId(java.util.UUID.randomUUID().toString());
            reservation.setStatus(ReservationStatus.PENDING);
            Passenger p = new Passenger();
            p.setName(passenger);
            reservation.setPassenger(p);
            reservation.setFlight(f);
            this.reservations.add(reservation);
            f.getReservations().add(reservation);
            return true;
        }
        return false;
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
     * @param s The new status.
     */
    public void setStatus(ReservationStatus s) {
        this.status = s;
    }

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