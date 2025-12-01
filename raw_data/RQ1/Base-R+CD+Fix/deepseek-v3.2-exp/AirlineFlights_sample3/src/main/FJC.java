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
     * Publishes a flight for booking after validating temporal consistency and route integrity.
     * The flight must have valid timestamps, departure time must be after current time and before arrival time,
     * and departure airport must be different from arrival airport. Flight can only be published once.
     * 
     * @param f The flight to be published
     * @param now The current time for validation
     * @return true if publication is successful, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || now == null) return false;
        
        try {
            if (!f.isOpenForBooking()) return false;
            
            Date departureTime = f.getDepartureTime();
            Date arrivalTime = f.getArrivalTime();
            
            if (departureTime == null || arrivalTime == null) return false;
            
            if (!now.before(departureTime)) return false;
            if (!departureTime.before(arrivalTime)) return false;
            
            Airport departureAirport = f.getDepartureAirport();
            Airport arrivalAirport = f.getArrivalAirport();
            
            if (departureAirport == null || arrivalAirport == null) return false;
            if (departureAirport.equals(arrivalAirport)) return false;
            
            f.setOpenForBooking(true);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Closes an open flight that hasn't departed yet and cancels all confirmed reservations.
     * 
     * @param flightId The ID of the flight to close
     * @param now The current time for validation
     * @return true if closure is successful, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        if (flightId == null || now == null) return false;
        
        for (Flight flight : flights) {
            if (flightId.equals(flight.getId())) {
                if (!flight.isOpenForBooking()) return false;
                
                Date departureTime = flight.getDepartureTime();
                if (departureTime == null || !now.before(departureTime)) return false;
                
                flight.setOpenForBooking(false);
                
                for (Reservation reservation : flight.getReservations()) {
                    if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                        reservation.setStatus(ReservationStatus.CANCELED);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        if (origin == null || dest == null || date == null) return result;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String targetDate = sdf.format(date);
        
        for (Flight flight : flights) {
            if (!flight.isOpenForBooking()) continue;
            
            Airport depAirport = flight.getDepartureAirport();
            Airport arrAirport = flight.getArrivalAirport();
            
            if (depAirport == null || arrAirport == null) continue;
            
            boolean originMatch = depAirport.getServesForCities().stream()
                .anyMatch(city -> city.getName().equalsIgnoreCase(origin));
            boolean destMatch = arrAirport.getServesForCities().stream()
                .anyMatch(city -> city.getName().equalsIgnoreCase(dest));
            
            String flightDate = sdf.format(flight.getDepartureTime());
            
            if (originMatch && destMatch && targetDate.equals(flightDate)) {
                result.add(flight);
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
     * Adds a stopover to the flight after validating temporal consistency and airport validity.
     * Stopover times must fit within flight schedule and airport must serve valid cities.
     * 
     * @param stop The stopover to be added
     * @param now The current time for validation
     * @return true if addition is successful, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null || now == null) return false;
        
        if (!openForBooking) return false;
        if (departureTime == null || arrivalTime == null) return false;
        
        Date stopArrival = stop.getArrivalTime();
        Date stopDeparture = stop.getDepartureTime();
        
        if (stopArrival == null || stopDeparture == null) return false;
        
        if (!stopArrival.before(stopDeparture)) return false;
        if (!departureTime.before(stopArrival)) return false;
        if (!stopDeparture.before(arrivalTime)) return false;
        
        Airport airport = stop.getAirport();
        if (airport == null || airport.getServesForCities().isEmpty()) return false;
        
        if (!now.before(departureTime)) return false;
        
        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight before departure.
     * 
     * @param stop The stopover to be removed
     * @param now The current time for validation
     * @return true if removal is successful, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null || now == null) return false;
        
        if (!openForBooking) return false;
        if (departureTime == null) return false;
        
        if (!now.before(departureTime)) return false;
        
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     * 
     * @return List of confirmed reservations, empty list if none exist
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
        if (c != null) {
            servesForCities.add(c);
        }
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
     * Creates a booking for passengers on a flight after validating flight availability and passenger uniqueness.
     * 
     * @param f The flight to book
     * @param now The current time for validation
     * @param listOfPassengerNames List of passenger names for the booking
     * @return true if booking creation is successful, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || now == null || listOfPassengerNames == null || listOfPassengerNames.isEmpty()) return false;
        
        if (!f.isOpenForBooking()) return false;
        
        Date departureTime = f.getDepartureTime();
        if (departureTime == null || !now.before(departureTime)) return false;
        
        Set<String> uniquePassengers = new HashSet<>(listOfPassengerNames);
        if (uniquePassengers.size() != listOfPassengerNames.size()) return false;
        
        Booking booking = new Booking();
        booking.setCustomer(this);
        
        for (String passengerName : listOfPassengerNames) {
            Passenger passenger = new Passenger();
            passenger.setName(passengerName);
            
            if (!booking.createReservation(f, passengerName, now)) {
                return false;
            }
        }
        
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms an existing reservation after validating flight status.
     * 
     * @param reservationID The ID of the reservation to confirm
     * @param now The current time for validation
     * @return true if confirmation is successful, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        if (reservationID == null || now == null) return false;
        
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservationID.equals(reservation.getId())) {
                    Flight flight = reservation.getFlight();
                    if (flight == null || !flight.isOpenForBooking()) return false;
                    
                    Date departureTime = flight.getDepartureTime();
                    if (departureTime == null || !now.before(departureTime)) return false;
                    
                    reservation.setStatus(ReservationStatus.CONFIRMED);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cancels an existing reservation after validating flight status.
     * 
     * @param reservationID The ID of the reservation to cancel
     * @param now The current time for validation
     * @return true if cancellation is successful, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        if (reservationID == null || now == null) return false;
        
        for (Booking booking : bookings) {
            for (Reservation reservation : booking.getReservations()) {
                if (reservationID.equals(reservation.getId())) {
                    Flight flight = reservation.getFlight();
                    if (flight == null || !flight.isOpenForBooking()) return false;
                    
                    Date departureTime = flight.getDepartureTime();
                    if (departureTime == null || !now.before(departureTime)) return false;
                    
                    reservation.setStatus(ReservationStatus.CANCELED);
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
     * Creates a reservation for a passenger on a flight with pending status.
     * 
     * @param f The flight for the reservation
     * @param passenger The passenger name for the reservation
     * @param now The current time for validation
     * @return true if reservation creation is successful, false otherwise
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (f == null || passenger == null || now == null) return false;
        
        if (!f.isOpenForBooking()) return false;
        
        Date departureTime = f.getDepartureTime();
        if (departureTime == null || !now.before(departureTime)) return false;
        
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());
        reservation.setStatus(ReservationStatus.PENDING);
        
        Passenger p = new Passenger();
        p.setName(passenger);
        reservation.setPassenger(p);
        
        reservation.setFlight(f);
        
        reservations.add(reservation);
        f.getReservations().add(reservation);
        
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