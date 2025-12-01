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
     * Publishes a flight to make it available for customer bookings.
     * Validates flight timestamps, temporal consistency, and route integrity.
     * 
     * @param f The flight to be published
     * @param now The current time for validation
     * @return true if flight was successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || !f.isOpenForBooking()) {
            return false;
        }
        
        Date departureTime = f.getDepartureTime();
        Date arrivalTime = f.getArrivalTime();
        
        if (departureTime == null || arrivalTime == null) {
            return false;
        }
        
        if (!now.before(departureTime) || !departureTime.before(arrivalTime)) {
            return false;
        }
        
        Airport departureAirport = f.getDepartureAirport();
        Airport arrivalAirport = f.getArrivalAirport();
        
        if (departureAirport == null || arrivalAirport == null || 
            departureAirport.equals(arrivalAirport)) {
            return false;
        }
        
        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an existing flight that has not yet departed.
     * Cancels all confirmed reservations on the flight.
     * 
     * @param flightId The ID of the flight to close
     * @param now The current time for validation
     * @return true if flight was successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight flight = findFlightById(flightId);
        if (flight == null || !flight.isOpenForBooking() || 
            !now.before(flight.getDepartureTime())) {
            return false;
        }
        
        flight.setOpenForBooking(false);
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        for (Reservation reservation : confirmedReservations) {
            reservation.setStatus(ReservationStatus.CANCELLED);
        }
        return true;
    }

    /**
     * Searches for flights based on origin, destination, and date.
     * 
     * @param origin The departure city name
     * @param dest The arrival city name
     * @param date The departure date
     * @return List of matching flights
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Flight flight : flights) {
            if (!flight.isOpenForBooking()) continue;
            
            Airport depAirport = flight.getDepartureAirport();
            Airport arrAirport = flight.getArrivalAirport();
            
            boolean originMatch = depAirport != null && depAirport.servesCity(origin);
            boolean destMatch = arrAirport != null && arrAirport.servesCity(dest);
            boolean dateMatch = sdf.format(flight.getDepartureTime()).equals(sdf.format(date));
            
            if (originMatch && destMatch && dateMatch) {
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
     * Adds a stopover to the flight if it fits within the flight schedule.
     * 
     * @param stop The stopover to add
     * @param now The current time for validation
     * @return true if stopover was successfully added, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null || !now.before(departureTime)) {
            return false;
        }
        
        Date stopArrival = stop.getArrivalTime();
        Date stopDeparture = stop.getDepartureTime();
        
        if (stopArrival == null || stopDeparture == null || 
            !stopArrival.before(stopDeparture) ||
            !departureTime.before(stopArrival) || 
            !stopDeparture.before(arrivalTime)) {
            return false;
        }
        
        for (Stopover existing : stopovers) {
            if (existing.getArrivalTime().before(stopArrival) && 
                stopArrival.before(existing.getDepartureTime())) {
                return false;
            }
        }
        
        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight before departure.
     * 
     * @param stop The stopover to remove
     * @param now The current time for validation
     * @return true if stopover was successfully removed, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null || !now.before(departureTime)) {
            return false;
        }
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     * 
     * @return List of confirmed reservations
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

    public City() {}

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
     * @param listOfPassengerNames List of passenger names
     * @return true if booking was successfully created, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || !f.isOpenForBooking() || listOfPassengerNames == null || 
            listOfPassengerNames.isEmpty() || !now.before(f.getDepartureTime())) {
            return false;
        }
        
        Set<String> passengerSet = new HashSet<>(listOfPassengerNames);
        if (passengerSet.size() != listOfPassengerNames.size()) {
            return false;
        }
        
        Booking booking = new Booking();
        booking.setCustomer(this);
        
        for (String passengerName : listOfPassengerNames) {
            Passenger passenger = new Passenger();
            passenger.setName(passengerName);
            
            if (!booking.createReservation(f, passenger, now)) {
                return false;
            }
        }
        
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms an existing reservation.
     * 
     * @param reservationID The reservation ID to confirm
     * @param now The current time for validation
     * @return true if reservation was successfully confirmed, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        Reservation reservation = findReservationById(reservationID);
        if (reservation == null || reservation.getStatus() != ReservationStatus.PENDING) {
            return false;
        }
        
        Flight flight = reservation.getFlight();
        if (!flight.isOpenForBooking() || !now.before(flight.getDepartureTime())) {
            return false;
        }
        
        reservation.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels an existing reservation.
     * 
     * @param reservationID The reservation ID to cancel
     * @param now The current time for validation
     * @return true if reservation was successfully cancelled, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        Reservation reservation = findReservationById(reservationID);
        if (reservation == null) {
            return false;
        }
        
        Flight flight = reservation.getFlight();
        if (!flight.isOpenForBooking() || !now.before(flight.getDepartureTime())) {
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
     * @param now The current time for validation
     * @return true if reservation was successfully created, false otherwise
     */
    public boolean createReservation(Flight f, Passenger passenger, Date now) {
        if (f == null || passenger == null || !f.isOpenForBooking() || 
            !now.before(f.getDepartureTime())) {
            return false;
        }
        
        for (Reservation existing : f.getReservations()) {
            if (existing.getPassenger().getName().equals(passenger.getName())) {
                return false;
            }
        }
        
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