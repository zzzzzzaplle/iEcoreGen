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
     * Publishes a flight for booking after validating its timestamps and route integrity.
     * 
     * @param f The flight to be published
     * @param now The current time for validation
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || f.isOpenForBooking() == false) {
            return false;
        }
        return f.publish(now);
    }

    /**
     * Closes an open flight that has not yet departed and cancels all confirmed reservations.
     * 
     * @param flightId The unique identifier of the flight to close
     * @param now The current time for validation
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight flight = findFlightById(flightId);
        if (flight == null || !flight.isOpenForBooking()) {
            return false;
        }
        return flight.close(now);
    }

    /**
     * Searches for flights based on origin, destination, and date.
     * 
     * @param origin The departure city name
     * @param dest The arrival city name
     * @param date The departure date
     * @return A list of matching flights, or an empty list if none found
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getDepartureAirport().servesCity(origin) &&
                flight.getArrivalAirport().servesCity(dest) &&
                isSameDay(flight.getDepartureTime(), date)) {
                result.add(flight);
            }
        }
        return result;
    }

    private Flight findFlightById(String flightId) {
        for (Flight flight : flights) {
            if (flight.getId().equals(flightId)) {
                return flight;
            }
        }
        return null;
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
               cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
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
    private boolean published;

    public Flight() {
        this.stopovers = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.openForBooking = true;
        this.published = false;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    /**
     * Publishes the flight for booking after validating timestamps and route integrity.
     * 
     * @param now The current time for validation
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publish(Date now) {
        if (published) {
            return false;
        }
        
        if (departureTime == null || arrivalTime == null) {
            return false;
        }
        
        if (!now.before(departureTime) || !departureTime.before(arrivalTime)) {
            return false;
        }
        
        if (departureAirport == null || arrivalAirport == null || 
            departureAirport.equals(arrivalAirport)) {
            return false;
        }
        
        published = true;
        return true;
    }

    /**
     * Closes the flight and cancels all confirmed reservations.
     * 
     * @param now The current time for validation
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean close(Date now) {
        if (!openForBooking || now.after(departureTime)) {
            return false;
        }
        
        openForBooking = false;
        
        // Cancel all confirmed reservations
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                reservation.setStatus(ReservationStatus.CANCELLED);
            }
        }
        
        return true;
    }

    /**
     * Adds a stopover to the flight after validating timing constraints.
     * 
     * @param stop The stopover to add
     * @param now The current time for validation
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null || now.after(departureTime)) {
            return false;
        }
        
        // Validate stopover timing fits within flight schedule
        if (!stop.getArrivalTime().after(departureTime) || 
            !stop.getDepartureTime().before(arrivalTime) ||
            !stop.getDepartureTime().after(stop.getArrivalTime())) {
            return false;
        }
        
        // Validate airport serves valid cities
        if (stop.getAirport() == null || stop.getAirport().getServesForCities().isEmpty()) {
            return false;
        }
        
        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight.
     * 
     * @param stop The stopover to remove
     * @param now The current time for validation
     * @return true if the stopover is successfully removed, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null || now.after(departureTime)) {
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
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                confirmed.add(reservation);
            }
        }
        return confirmed;
    }

    /**
     * Adds a reservation to the flight.
     * 
     * @param reservation The reservation to add
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    /**
     * Checks if a passenger already has a reservation on this flight.
     * 
     * @param passengerName The name of the passenger to check
     * @return true if passenger already has a reservation, false otherwise
     */
    public boolean hasPassenger(String passengerName) {
        for (Reservation reservation : reservations) {
            if (reservation.getPassenger().getName().equals(passengerName)) {
                return true;
            }
        }
        return false;
    }
}

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

    /**
     * Checks if this airport serves a specific city.
     * 
     * @param cityName The name of the city to check
     * @return true if the airport serves the city, false otherwise
     */
    public boolean servesCity(String cityName) {
        for (City city : servesForCities) {
            if (city.getName().equals(cityName)) {
                return true;
            }
        }
        return false;
    }
}

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
     * @param f The flight to book
     * @param now The current time for validation
     * @param listOfPassengerNames List of passenger names for the booking
     * @return true if the booking is successfully created, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || listOfPassengerNames == null || listOfPassengerNames.isEmpty()) {
            return false;
        }
        
        // Check if flight is open for booking and departure time is in future
        if (!f.isOpenForBooking() || !now.before(f.getDepartureTime())) {
            return false;
        }
        
        // Check for duplicate passengers on the flight
        Set<String> uniquePassengers = new HashSet<>();
        for (String passengerName : listOfPassengerNames) {
            if (f.hasPassenger(passengerName)) {
                return false;
            }
            uniquePassengers.add(passengerName);
        }
        
        // Create booking and reservations
        Booking booking = new Booking(this);
        for (String passengerName : listOfPassengerNames) {
            Passenger passenger = new Passenger();
            passenger.setName(passengerName);
            booking.createReservation(f, passenger, now);
        }
        
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms an existing reservation.
     * 
     * @param reservationID The unique identifier of the reservation to confirm
     * @param now The current time for validation
     * @return true if the reservation is successfully confirmed, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        Reservation reservation = findReservationById(reservationID);
        if (reservation == null) {
            return false;
        }
        
        Flight flight = reservation.getFlight();
        if (!flight.isOpenForBooking() || now.after(flight.getDepartureTime())) {
            return false;
        }
        
        reservation.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels an existing reservation.
     * 
     * @param reservationID The unique identifier of the reservation to cancel
     * @param now The current time for validation
     * @return true if the reservation is successfully cancelled, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        Reservation reservation = findReservationById(reservationID);
        if (reservation == null) {
            return false;
        }
        
        Flight flight = reservation.getFlight();
        if (!flight.isOpenForBooking() || now.after(flight.getDepartureTime())) {
            return false;
        }
        
        reservation.setStatus(ReservationStatus.CANCELLED);
        return true;
    }

    private Reservation findReservationById(String reservationID) {
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservation.getId().equals(reservationID)) {
                    return reservation;
                }
            }
        }
        return null;
    }
}

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
}

class Booking {
    private Customer customer;
    private List<Reservation> reservations;

    public Booking(Customer customer) {
        this.customer = customer;
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
     * @param now The current time for validation
     * @return true if the reservation is successfully created, false otherwise
     */
    public boolean createReservation(Flight f, Passenger passenger, Date now) {
        if (f == null || passenger == null) {
            return false;
        }
        
        Reservation reservation = new Reservation();
        reservation.setId(generateReservationId());
        reservation.setPassenger(passenger);
        reservation.setFlight(f);
        reservation.setStatus(ReservationStatus.PENDING);
        
        reservations.add(reservation);
        f.addReservation(reservation);
        
        return true;
    }

    private String generateReservationId() {
        return "RES" + System.currentTimeMillis() + "_" + reservations.size();
    }
}

enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELLED
}

class Reservation {
    private String id;
    private ReservationStatus status;
    private Passenger passenger;
    private Flight flight;

    public Reservation() {
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