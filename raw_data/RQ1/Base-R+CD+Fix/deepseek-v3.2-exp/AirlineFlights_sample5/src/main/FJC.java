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
     * Publishes a flight for booking after validating its timestamps and route integrity
     * @param f the flight to be published
     * @param now the current date and time
     * @return true if the flight was successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null) {
            return false;
        }
        
        // Check if flight is already published
        if (f.isOpenForBooking()) {
            return false;
        }
        
        // Validate departure and arrival timestamps
        Date departureTime = f.getDepartureTime();
        Date arrivalTime = f.getArrivalTime();
        
        if (departureTime == null || arrivalTime == null) {
            return false;
        }
        
        // Check temporal consistency: currentTime < departureTime < arrivalTime
        if (!now.before(departureTime) || !departureTime.before(arrivalTime)) {
            return false;
        }
        
        // Check route integrity: departureAirport ≠ arrivalAirport
        Airport departureAirport = f.getDepartureAirport();
        Airport arrivalAirport = f.getArrivalAirport();
        
        if (departureAirport == null || arrivalAirport == null || 
            departureAirport.equals(arrivalAirport)) {
            return false;
        }
        
        // Set flight as open for booking
        f.setOpenForBooking(true);
        return true;
    }

    /**
     * Closes an open flight that has not yet departed
     * @param flightId the ID of the flight to close
     * @param now the current date and time
     * @return true if the flight was successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight flight = findFlightById(flightId);
        if (flight == null) {
            return false;
        }
        
        // Check if flight is currently open
        if (!flight.isOpenForBooking()) {
            return false;
        }
        
        // Check if flight has not yet departed
        if (!now.before(flight.getDepartureTime())) {
            return false;
        }
        
        // Close the flight and cancel all confirmed reservations
        flight.setOpenForBooking(false);
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        for (Reservation reservation : confirmedReservations) {
            reservation.setStatus(ReservationStatus.CANCELED);
        }
        
        return true;
    }

    /**
     * Searches for flights based on origin, destination, and date
     * @param origin the origin city name
     * @param dest the destination city name
     * @param date the departure date
     * @return list of matching flights
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Flight flight : flights) {
            // Check if flight serves the origin city
            boolean servesOrigin = flight.getDepartureAirport().servesCity(origin);
            // Check if flight serves the destination city
            boolean servesDest = flight.getArrivalAirport().servesCity(dest);
            
            // Check if departure date matches
            String flightDate = dateFormat.format(flight.getDepartureTime());
            String searchDate = dateFormat.format(date);
            
            if (servesOrigin && servesDest && flightDate.equals(searchDate) && flight.isOpenForBooking()) {
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
     * Adds a stopover to the flight schedule
     * @param stop the stopover to add
     * @param now the current date and time
     * @return true if the stopover was successfully added, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null) {
            return false;
        }
        
        // Check if flight has not yet departed
        if (!now.before(departureTime)) {
            return false;
        }
        
        // Validate stopover times fit within overall flight schedule
        Date stopArrival = stop.getArrivalTime();
        Date stopDeparture = stop.getDepartureTime();
        
        if (stopArrival == null || stopDeparture == null) {
            return false;
        }
        
        if (!stopArrival.before(stopDeparture) || 
            !departureTime.before(stopArrival) || 
            !stopDeparture.before(arrivalTime)) {
            return false;
        }
        
        // Validate airport serves valid cities
        Airport airport = stop.getAirport();
        if (airport == null || airport.getServesForCities().isEmpty()) {
            return false;
        }
        
        stopovers.add(stop);
        return true;
    }

    /**
     * Removes a stopover from the flight schedule
     * @param stop the stopover to remove
     * @param now the current date and time
     * @return true if the stopover was successfully removed, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null) {
            return false;
        }
        
        // Check if flight has not yet departed
        if (!now.before(departureTime)) {
            return false;
        }
        
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight
     * @return list of confirmed reservations
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
     * Checks if this airport serves a specific city
     * @param cityName the name of the city to check
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
        return Objects.equals(id, airport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
     * Creates a booking for passengers on a flight
     * @param f the flight to book
     * @param now the current date and time
     * @param listOfPassengerNames list of passenger names
     * @return true if the booking was successfully created, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || listOfPassengerNames == null || listOfPassengerNames.isEmpty()) {
            return false;
        }
        
        // Check if flight is open for booking
        if (!f.isOpenForBooking()) {
            return false;
        }
        
        // Check that current time is before flight departure time
        if (!now.before(f.getDepartureTime())) {
            return false;
        }
        
        // Check for duplicate passengers in the request
        Set<String> uniquePassengers = new HashSet<>(listOfPassengerNames);
        if (uniquePassengers.size() != listOfPassengerNames.size()) {
            return false;
        }
        
        // Check for duplicate passengers already on the flight
        for (Reservation existingReservation : f.getReservations()) {
            String existingPassenger = existingReservation.getPassenger().getName();
            if (listOfPassengerNames.contains(existingPassenger)) {
                return false;
            }
        }
        
        // Create booking and reservations
        Booking booking = new Booking();
        booking.setCustomer(this);
        
        for (String passengerName : listOfPassengerNames) {
            Passenger passenger = new Passenger();
            passenger.setName(passengerName);
            
            Reservation reservation = new Reservation();
            reservation.setId(generateReservationId());
            reservation.setPassenger(passenger);
            reservation.setFlight(f);
            reservation.setStatus(ReservationStatus.PENDING);
            
            booking.getReservations().add(reservation);
            f.getReservations().add(reservation);
        }
        
        bookings.add(booking);
        return true;
    }

    /**
     * Confirms an existing reservation
     * @param reservationID the ID of the reservation to confirm
     * @param now the current date and time
     * @return true if the reservation was successfully confirmed, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        Reservation reservation = findReservationById(reservationID);
        if (reservation == null) {
            return false;
        }
        
        Flight flight = reservation.getFlight();
        
        // Check that flight has not yet departed and is still open for booking
        if (!now.before(flight.getDepartureTime()) || !flight.isOpenForBooking()) {
            return false;
        }
        
        reservation.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels an existing reservation
     * @param reservationID the ID of the reservation to cancel
     * @param now the current date and time
     * @return true if the reservation was successfully canceled, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        Reservation reservation = findReservationById(reservationID);
        if (reservation == null) {
            return false;
        }
        
        Flight flight = reservation.getFlight();
        
        // Check that flight has not yet departed and is still open for booking
        if (!now.before(flight.getDepartureTime()) || !flight.isOpenForBooking()) {
            return false;
        }
        
        reservation.setStatus(ReservationStatus.CANCELED);
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

    private String generateReservationId() {
        return "RES_" + System.currentTimeMillis() + "_" + new Random().nextInt(1000);
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
     * Creates a reservation for a passenger on a flight
     * @param f the flight for the reservation
     * @param passenger the passenger name
     * @param now the current date and time
     * @return true if the reservation was successfully created, false otherwise
     */
    public boolean createReservation(Flight f, String passenger, Date now) {
        if (f == null || passenger == null || passenger.trim().isEmpty()) {
            return false;
        }
        
        // Check if flight has not yet departed
        if (!now.before(f.getDepartureTime())) {
            return false;
        }
        
        Passenger p = new Passenger();
        p.setName(passenger);
        
        Reservation reservation = new Reservation();
        reservation.setId(generateReservationId());
        reservation.setPassenger(p);
        reservation.setFlight(f);
        reservation.setStatus(ReservationStatus.PENDING);
        
        reservations.add(reservation);
        f.getReservations().add(reservation);
        return true;
    }

    private String generateReservationId() {
        return "RES_" + System.currentTimeMillis() + "_" + new Random().nextInt(1000);
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