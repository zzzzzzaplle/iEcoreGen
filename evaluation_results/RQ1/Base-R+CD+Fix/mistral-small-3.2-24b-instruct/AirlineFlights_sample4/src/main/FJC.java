import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELED
}

class Passenger {
    private String name;

    public Passenger() {
    }

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class City {
    private String name;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Airport {
    private String id;
    private List<City> servesForCities;

    public Airport() {
        this.servesForCities = new ArrayList<>();
    }

    public Airport(String id) {
        this.id = id;
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
        this.servesForCities.add(c);
    }
}

class Stopover {
    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    public Stopover() {
    }

    public Stopover(Date departureTime, Date arrivalTime, Airport airport) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airport = airport;
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

class Reservation {
    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    public Reservation() {
        this.id = UUID.randomUUID().toString();
        this.status = ReservationStatus.PENDING;
    }

    public Reservation(Passenger passenger, Flight flight) {
        this();
        this.passenger = passenger;
        this.flight = flight;
    }

    public String getId() {
        return id;
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

class Booking {
    private Customer customer;
    private List<Reservation> reservations;

    public Booking() {
        this.reservations = new ArrayList<>();
    }

    public Booking(Customer customer) {
        this();
        this.customer = customer;
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
     * @param f The flight to reserve.
     * @param passenger The name of the passenger.
     * @param now The current date and time.
     * @return true if the reservation was created successfully, false otherwise.
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (now.after(f.getDepartureTime()) || f.getDepartureAirport() == null || f.getArrivalAirport() == null) {
            return false;
        }

        Passenger p = new Passenger(passenger);
        Reservation r = new Reservation(p, f);
        this.reservations.add(r);
        return true;
    }
}

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
     * Adds a booking for a customer on a flight.
     *
     * @param f The flight to book.
     * @param now The current date and time.
     * @param listOfPassengerNames The list of passenger names.
     * @return true if the booking was created successfully, false otherwise.
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (now.after(f.getDepartureTime()) || f.getDepartureAirport() == null || f.getArrivalAirport() == null) {
            return false;
        }

        for (String passengerName : listOfPassengerNames) {
            Booking b = new Booking(this);
            if (!b.createReservation(f, passengerName, now)) {
                return false;
            }
            this.bookings.add(b);
        }
        return true;
    }

    /**
     * Confirms a reservation by its ID.
     *
     * @param reservationID The ID of the reservation to confirm.
     * @param now The current date and time.
     * @return true if the reservation was confirmed successfully, false otherwise.
     */
    public boolean confirm(String reservationID, Date now) {
        for (Booking b : this.bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId().equals(reservationID) && now.before(r.getFlight().getDepartureTime()) && r.getFlight().isOpenForBooking()) {
                    r.setStatus(ReservationStatus.CONFIRMED);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cancels a reservation by its ID.
     *
     * @param reservationID The ID of the reservation to cancel.
     * @param now The current date and time.
     * @return true if the reservation was canceled successfully, false otherwise.
     */
    public boolean cancel(String reservationID, Date now) {
        for (Booking b : this.bookings) {
            for (Reservation r : b.getReservations()) {
                if (r.getId().equals(reservationID) && now.before(r.getFlight().getDepartureTime()) && r.getFlight().isOpenForBooking()) {
                    r.setStatus(ReservationStatus.CANCELED);
                    return true;
                }
            }
        }
        return false;
    }
}

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
        this.id = UUID.randomUUID().toString();
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.openForBooking = false;
    }

    public Flight(Date departureTime, Date arrivalTime, Airport departureAirport, Airport arrivalAirport) {
        this();
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
    }

    public String getId() {
        return id;
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
     * @param stop The stopover to add.
     * @param now The current date and time.
     * @return true if the stopover was added successfully, false otherwise.
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (now.after(this.departureTime) || this.arrivalAirport == null || this.departureAirport == null) {
            return false;
        }

        this.stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     *
     * @param stop The stopover to remove.
     * @param now The current date and time.
     * @return true if the stopover was removed successfully, false otherwise.
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (now.after(this.departureTime) || this.arrivalAirport == null || this.departureAirport == null) {
            return false;
        }

        this.stopovers.remove(stop);
        return true;
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     *
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
}

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
        this.flights.add(f);
    }

    public void removeFlight(Flight f) {
        this.flights.remove(f);
    }

    /**
     * Publishes a flight for booking.
     *
     * @param f The flight to publish.
     * @param now The current date and time.
     * @return true if the flight was published successfully, false otherwise.
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f.isOpenForBooking() || f.getDepartureTime() == null || f.getArrivalTime() == null || now.after(f.getDepartureTime()) || f.getDepartureTime().after(f.getArrivalTime()) || f.getDepartureAirport() == null || f.getArrivalAirport() == null || f.getDepartureAirport().equals(f.getArrivalAirport())) {
            return false;
        }

        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an open flight.
     *
     * @param flightId The ID of the flight to close.
     * @param now The current date and time.
     * @return true if the flight was closed successfully, false otherwise.
     */
    public boolean closeFlight(String flightId, Date now) {
        for (Flight f : this.flights) {
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
     *
     * @param origin The origin airport.
     * @param dest The destination airport.
     * @param date The date of the flight.
     * @return A list of flights matching the criteria.
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        for (Flight f : this.flights) {
            if (f.getDepartureAirport().getId().equals(origin) && f.getArrivalAirport().getId().equals(dest) && f.getDepartureTime().equals(date)) {
                result.add(f);
            }
        }
        return result;
    }
}