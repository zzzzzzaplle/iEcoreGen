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
     * Publishes a flight for booking after validating the flight's details.
     * @param f The flight to be published.
     * @param now The current time.
     * @return true if the flight is successfully published, false otherwise.
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f.isOpenForBooking() || f.getDepartureTime().before(now) || f.getDepartureTime().after(f.getArrivalTime()) || f.getDepartureAirport().equals(f.getArrialAirport())) {
            return false;
        }
        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an open flight and cancels all confirmed reservations.
     * @param flightId The ID of the flight to be closed.
     * @param now The current time.
     * @return true if the flight is successfully closed, false otherwise.
     */
    public boolean closeFlight(String flightId, Date now) {
        for (Flight f : flights) {
            if (f.getId().equals(flightId) && f.isOpenForBooking() && f.getDepartureTime().after(now)) {
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
     * @param date The departure date.
     * @return A list of matching flights.
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getDepartureAirport().getServesForCities().stream().anyMatch(c -> c.getName().equals(origin)) &&
                f.getArrialAirport().getServesForCities().stream().anyMatch(c -> c.getName().equals(dest)) &&
                f.getDepartureTime().toInstant().toEpochMilli() >= date.toInstant().toEpochMilli() &&
                f.getDepartureTime().toInstant().toEpochMilli() < date.toInstant().toEpochMilli() + 86400000) {
                result.add(f);
            }
        }
        return result;
    }

    public List<Flight> getFlights() {
        return flights;
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
     * Adds a stopover to the flight if it fits within the overall flight schedule.
     * @param stop The stopover to be added.
     * @param now The current time.
     * @return true if the stopover is successfully added, false otherwise.
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (!isOpenForBooking() || departureTime.before(now) || stop.getDepartureTime().before(departureTime) || stop.getArrivalTime().after(arrivalTime)) {
            return false;
        }
        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     * @param stop The stopover to be removed.
     * @param now The current time.
     * @return true if the stopover is successfully removed, false otherwise.
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (!isOpenForBooking() || departureTime.before(now)) {
            return false;
        }
        stopovers.remove(stop);
        return true;
    }

    /**
     * Retrieves all confirmed reservations for the flight.
     * @return A list of confirmed reservations.
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                result.add(r);
            }
        }
        return result;
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

    public List<Reservation> getReservations() {
        return reservations;
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
}

class Customer {
    private List<Booking> bookings = new ArrayList<>();

    /**
     * Creates a booking for a list of passengers on a specific flight.
     * @param f The flight to be booked.
     * @param now The current time.
     * @param listOfPassengerNames A list of passenger names.
     * @return true if the booking is successfully created, false otherwise.
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (!f.isOpenForBooking() || f.getDepartureTime().before(now)) {
            return false;
        }
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
     * Confirms a reservation by its ID.
     * @param reservationID The ID of the reservation to be confirmed.
     * @param now The current time.
     * @return true if the reservation is successfully confirmed, false otherwise.
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId().equals(reservationID) && r.getFlight().isOpenForBooking() && r.getFlight().getDepartureTime().after(now)) {
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
     * @return true if the reservation is successfully canceled, false otherwise.
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId().equals(reservationID) && r.getFlight().isOpenForBooking() && r.getFlight().getDepartureTime().after(now)) {
                    r.setStatus(ReservationStatus.CANCELED);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}

class Passenger {
    private String name;

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
     * Creates a reservation for a passenger on a specific flight.
     * @param f The flight to be reserved.
     * @param passenger The name of the passenger.
     * @param now The current time.
     * @return true if the reservation is successfully created, false otherwise.
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (!f.isOpenForBooking() || f.getDepartureTime().before(now)) {
            return false;
        }
        for (Reservation r : reservations) {
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
        reservations.add(reservation);
        f.getReservations().add(reservation);
        return true;
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
     * @param s The new status of the reservation.
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

class City {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}