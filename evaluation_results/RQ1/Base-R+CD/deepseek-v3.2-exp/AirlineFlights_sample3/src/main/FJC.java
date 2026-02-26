import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

class Airline {
    private List<Flight> flights = new ArrayList<>();

    public Airline() {}

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
     * @param f The flight to be published
     * @param now The current timestamp
     * @return true if flight is successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null) return false;
        
        // Check if flight is already published
        if (!f.isOpenForBooking()) return false;
        
        // Check temporal consistency: currentTime < departureTime < arrivalTime
        if (now.after(f.getDepartureTime()) || 
            f.getDepartureTime().after(f.getArrivalTime())) {
            return false;
        }
        
        // Check route integrity: departureAirport ≠ arrivalAirport
        if (f.getDepartureAirport().equals(f.getArrivalAirport())) {
            return false;
        }
        
        // Validate timestamp format (yyyy-MM-dd HH:mm:ss)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sdf.format(f.getDepartureTime());
            sdf.format(f.getArrivalTime());
        } catch (Exception e) {
            return false;
        }
        
        // Flight can be published only once - additional check
        if (flights.contains(f)) return false;
        
        flights.add(f);
        return true;
    }

    /**
     * Closes an open flight that hasn't departed yet and cancels all confirmed reservations.
     * @param flightId The unique identifier of the flight to close
     * @param now The current timestamp
     * @return true if flight is successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight flight = findFlightById(flightId);
        if (flight == null) return false;
        
        if (!flight.isOpenForBooking()) return false;
        
        // Check if flight hasn't departed yet
        if (now.after(flight.getDepartureTime())) return false;
        
        flight.setOpenForBooking(false);
        
        // Cancel all confirmed reservations
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        for (Reservation reservation : confirmedReservations) {
            reservation.setStatus(ReservationStatus.CANCELLED);
        }
        
        return true;
    }

    /**
     * Searches for flights based on origin, destination, and date.
     * @param origin The departure airport/city
     * @param dest The arrival airport/city
     * @param date The departure date
     * @return List of matching flights
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Flight flight : flights) {
            // Check if flight matches origin and destination
            boolean originMatch = flight.getDepartureAirport().servesCity(origin) || 
                                 flight.getDepartureAirport().getId().equals(origin);
            boolean destMatch = flight.getArrivalAirport().servesCity(dest) || 
                               flight.getArrivalAirport().getId().equals(dest);
            
            // Check date match
            boolean dateMatch = dateFormat.format(flight.getDepartureTime()).equals(dateFormat.format(date));
            
            if (originMatch && destMatch && dateMatch && flight.isOpenForBooking()) {
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
    private List<Stopover> stopovers = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public Flight() {}

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
     * Adds a stopover to the flight schedule before departure.
     * @param stop The stopover to add
     * @param now The current timestamp
     * @return true if stopover is successfully added, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null) return false;
        
        // Check if flight hasn't departed yet
        if (now.after(departureTime)) return false;
        
        // Validate stopover times fit within overall flight schedule
        if (stop.getArrivalTime().before(departureTime) || 
            stop.getDepartureTime().after(arrivalTime) ||
            stop.getArrivalTime().after(stop.getDepartureTime())) {
            return false;
        }
        
        // Check airport validity
        if (stop.getAirport() == null || stop.getAirport().getServesForCities().isEmpty()) {
            return false;
        }
        
        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight schedule before departure.
     * @param stop The stopover to remove
     * @param now The current timestamp
     * @return true if stopover is successfully removed, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null) return false;
        
        // Check if flight hasn't departed yet
        if (now.after(departureTime)) return false;
        
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
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

    /**
     * Adds a reservation to the flight.
     * @param reservation The reservation to add
     * @return true if reservation is successfully added, false otherwise
     */
    public boolean addReservation(Reservation reservation) {
        if (reservation == null) return false;
        
        // Check for duplicate passengers
        for (Reservation existingReservation : reservations) {
            if (existingReservation.getPassenger().getName().equals(reservation.getPassenger().getName())) {
                return false;
            }
        }
        
        reservations.add(reservation);
        return true;
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
    private List<City> servesForCities = new ArrayList<>();

    public Airport() {}

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Airport airport = (Airport) obj;
        return id.equals(airport.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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
    private List<Booking> bookings = new ArrayList<>();

    public Customer() {}

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Creates a booking for passengers on a specific flight.
     * @param f The flight to book
     * @param now The current timestamp
     * @param listOfPassengerNames List of passenger names
     * @return true if booking is successfully created, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || listOfPassengerNames == null || listOfPassengerNames.isEmpty()) {
            return false;
        }
        
        // Check if flight is open for booking
        if (!f.isOpenForBooking()) return false;
        
        // Check if current time is before flight departure time
        if (now.after(f.getDepartureTime())) return false;
        
        // Check for duplicate passengers in the request
        Set<String> uniquePassengers = new HashSet<>(listOfPassengerNames);
        if (uniquePassengers.size() != listOfPassengerNames.size()) {
            return false;
        }
        
        Booking booking = new Booking();
        booking.setCustomer(this);
        
        // Create reservations for each passenger
        for (String passengerName : listOfPassengerNames) {
            Passenger passenger = new Passenger();
            passenger.setName(passengerName);
            
            Reservation reservation = new Reservation();
            reservation.setPassenger(passenger);
            reservation.setFlight(f);
            reservation.setStatus(ReservationStatus.PENDING);
            reservation.setId(UUID.randomUUID().toString());
            
            // Check if passenger already has a reservation on this flight
            if (!f.addReservation(reservation)) {
                return false;
            }
            
            booking.addReservation(reservation);
        }
        
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms an existing reservation.
     * @param reservationID The unique identifier of the reservation to confirm
     * @param now The current timestamp
     * @return true if reservation is successfully confirmed, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        Reservation reservation = findReservationById(reservationID);
        if (reservation == null) return false;
        
        Flight flight = reservation.getFlight();
        
        // Check if flight hasn't departed yet and is still open for booking
        if (now.after(flight.getDepartureTime()) || !flight.isOpenForBooking()) {
            return false;
        }
        
        reservation.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels an existing reservation.
     * @param reservationID The unique identifier of the reservation to cancel
     * @param now The current timestamp
     * @return true if reservation is successfully canceled, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        Reservation reservation = findReservationById(reservationID);
        if (reservation == null) return false;
        
        Flight flight = reservation.getFlight();
        
        // Check if flight hasn't departed yet and is still open for booking
        if (now.after(flight.getDepartureTime()) || !flight.isOpenForBooking()) {
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
    private List<Reservation> reservations = new ArrayList<>();

    public Booking() {}

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

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    /**
     * Creates a reservation for a passenger on a flight.
     * @param f The flight for the reservation
     * @param passengerName The name of the passenger
     * @param now The current timestamp
     * @return true if reservation is successfully created, false otherwise
     */
    public boolean createReservation(Flight f, String passengerName, Date now) {
        if (f == null || passengerName == null || passengerName.trim().isEmpty()) {
            return false;
        }
        
        // Check if flight is open for booking and hasn't departed
        if (!f.isOpenForBooking() || now.after(f.getDepartureTime())) {
            return false;
        }
        
        Passenger passenger = new Passenger();
        passenger.setName(passengerName);
        
        Reservation reservation = new Reservation();
        reservation.setPassenger(passenger);
        reservation.setFlight(f);
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setId(UUID.randomUUID().toString());
        
        // Check for duplicate passengers on the flight
        if (!f.addReservation(reservation)) {
            return false;
        }
        
        reservations.add(reservation);
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