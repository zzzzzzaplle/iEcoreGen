import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
     * Publishes a flight for booking by verifying its validity and temporal consistency.
     * 
     * @param f The flight to be published
     * @param now The current date and time
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || now == null) {
            return false;
        }
        return f.publish(now);
    }

    /**
     * Closes an open flight that has not yet departed and cancels all confirmed reservations.
     * 
     * @param flightId The ID of the flight to close
     * @param now The current date and time
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        if (flightId == null || now == null) {
            return false;
        }
        for (Flight f : flights) {
            if (flightId.equals(f.getId())) {
                return f.close(now);
            }
        }
        return false;
    }

    /**
     * Searches for flights based on origin, destination, and date.
     * 
     * @param origin The departure airport ID
     * @param dest The arrival airport ID
     * @param date The departure date
     * @return A list of matching flights
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        if (origin == null || dest == null || date == null) {
            return result;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String targetDate = sdf.format(date);
        for (Flight f : flights) {
            if (f.isOpenForBooking() && 
                origin.equals(f.getDepartureAirport().getId()) && 
                dest.equals(f.getArrivalAirport().getId()) && 
                targetDate.equals(sdf.format(f.getDepartureTime()))) {
                result.add(f);
            }
        }
        return result;
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
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.openForBooking = false;
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
     * Publishes the flight by validating timestamp format, temporal consistency, and route integrity.
     * 
     * @param now The current date and time
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publish(Date now) {
        if (openForBooking) {
            return false;
        }
        if (departureTime == null || arrivalTime == null) {
            return false;
        }
        if (now.after(departureTime) || departureTime.after(arrivalTime)) {
            return false;
        }
        if (departureAirport == null || arrivalAirport == null || 
            departureAirport.equals(arrivalAirport)) {
            return false;
        }
        openForBooking = true;
        return true;
    }

    /**
     * Closes the flight and cancels all confirmed reservations.
     * 
     * @param now The current date and time
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean close(Date now) {
        if (!openForBooking || now.after(departureTime)) {
            return false;
        }
        openForBooking = false;
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                r.setStatus(ReservationStatus.CANCELED);
            }
        }
        return true;
    }

    /**
     * Adds a stopover to the flight after validating its schedule and airport.
     * 
     * @param stop The stopover to add
     * @param now The current date and time
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null || now == null || now.after(departureTime)) {
            return false;
        }
        if (stop.getAirport() == null || stop.getArrivalTime() == null || stop.getDepartureTime() == null) {
            return false;
        }
        if (stop.getArrivalTime().after(stop.getDepartureTime()) || 
            stop.getArrivalTime().before(departureTime) || 
            stop.getDepartureTime().after(arrivalTime)) {
            return false;
        }
        if (stop.getAirport().getServesForCities().isEmpty()) {
            return false;
        }
        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight before departure.
     * 
     * @param stop The stopover to remove
     * @param now The current date and time
     * @return true if the stopover is successfully removed, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null || now == null || now.after(departureTime)) {
            return false;
        }
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     * 
     * @return A list of confirmed reservations, or empty list if none exist
     */
    public List<Reservation> getConfirmedReservations() {
        List<Reservation> confirmed = new ArrayList<>();
        if (!openForBooking) {
            return confirmed;
        }
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.CONFIRMED) {
                confirmed.add(r);
            }
        }
        return confirmed;
    }

    /**
     * Checks if a passenger already has a reservation on this flight.
     * 
     * @param passengerName The name of the passenger to check
     * @return true if the passenger has a reservation, false otherwise
     */
    public boolean hasPassenger(String passengerName) {
        for (Reservation r : reservations) {
            if (r.getPassenger().getName().equals(passengerName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a reservation to the flight.
     * 
     * @param r The reservation to add
     */
    public void addReservation(Reservation r) {
        reservations.add(r);
    }
}

class Stopover {
    private Date departureTime;
    private Date arrivalTime;
    private Airport airport;

    public Stopover() {}

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

class City {
    // Basic city class - can be extended with additional properties
    public City() {}
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
     * Creates a booking for a flight with a list of passengers.
     * 
     * @param f The flight to book
     * @param now The current date and time
     * @param listOfPassengerNames The list of passenger names
     * @return true if the booking is successfully created, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || now == null || listOfPassengerNames == null || listOfPassengerNames.isEmpty()) {
            return false;
        }
        if (!f.isOpenForBooking() || now.after(f.getDepartureTime())) {
            return false;
        }
        Set<String> uniqueNames = new HashSet<>(listOfPassengerNames);
        if (uniqueNames.size() != listOfPassengerNames.size()) {
            return false;
        }
        for (String name : listOfPassengerNames) {
            if (f.hasPassenger(name)) {
                return false;
            }
        }
        Booking booking = new Booking();
        booking.setCustomer(this);
        for (String name : listOfPassengerNames) {
            Passenger p = new Passenger();
            p.setName(name);
            booking.createReservation(f, p, now);
        }
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms a reservation if the flight is still open and hasn't departed.
     * 
     * @param reservationID The ID of the reservation to confirm
     * @param now The current date and time
     * @return true if the reservation is successfully confirmed, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        return updateReservationStatus(reservationID, now, ReservationStatus.CONFIRMED);
    }

    /**
     * Cancels a reservation if the flight is still open and hasn't departed.
     * 
     * @param reservationID The ID of the reservation to cancel
     * @param now The current date and time
     * @return true if the reservation is successfully canceled, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        return updateReservationStatus(reservationID, now, ReservationStatus.CANCELED);
    }

    private boolean updateReservationStatus(String reservationID, Date now, ReservationStatus status) {
        if (reservationID == null || now == null) {
            return false;
        }
        for (Booking b : bookings) {
            for (Reservation r : b.getReservations()) {
                if (reservationID.equals(r.getId())) {
                    Flight f = r.getFlight();
                    if (!f.isOpenForBooking() || now.after(f.getDepartureTime())) {
                        return false;
                    }
                    r.setStatus(status);
                    return true;
                }
            }
        }
        return false;
    }
}

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
     * @param f The flight for the reservation
     * @param passenger The passenger for the reservation
     * @param now The current date and time
     * @return true if the reservation is successfully created, false otherwise
     */
    public boolean createReservation(Flight f, Passenger passenger, Date now) {
        if (f == null || passenger == null || now == null) {
            return false;
        }
        Reservation r = new Reservation();
        r.setId(UUID.randomUUID().toString());
        r.setStatus(ReservationStatus.PENDING);
        r.setPassenger(passenger);
        r.setFlight(f);
        reservations.add(r);
        f.addReservation(r);
        return true;
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