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
     * @param now The current time for validation
     * @return true if the flight is successfully published, false otherwise
     */
    public boolean publishFlight(Flight f, Date now) {
        if (f == null || !f.isOpenForBooking() || f.getDepartureTime() == null || f.getArrivalTime() == null) {
            return false;
        }
        
        if (f.getDepartureTime().compareTo(now) <= 0 || f.getArrivalTime().compareTo(f.getDepartureTime()) <= 0) {
            return false;
        }
        
        if (f.getDepartureAirport() == null || f.getArrivalAirport() == null || 
            f.getDepartureAirport().equals(f.getArrivalAirport())) {
            return false;
        }
        
        if (f.isPublished()) {
            return false;
        }
        
        f.setOpenForBooking(true);
        f.setPublished(true);
        return true;
    }

    /**
     * Closes an open flight that hasn't departed yet and cancels all confirmed reservations.
     * 
     * @param flightId The ID of the flight to close
     * @param now The current time for validation
     * @return true if the flight is successfully closed, false otherwise
     */
    public boolean closeFlight(String flightId, Date now) {
        Flight flight = findFlightById(flightId);
        if (flight == null || !flight.isOpenForBooking() || flight.getDepartureTime().compareTo(now) <= 0) {
            return false;
        }
        
        flight.setOpenForBooking(false);
        for (Reservation reservation : flight.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                reservation.setStatus(ReservationStatus.CANCELED);
            }
        }
        return true;
    }

    /**
     * Searches for flights based on origin, destination, and date.
     * 
     * @param origin The departure city name
     * @param dest The arrival city name
     * @param date The departure date to match
     * @return A list of matching flights
     */
    public List<Flight> searchFlights(String origin, String dest, Date date) {
        List<Flight> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String targetDate = sdf.format(date);
        
        for (Flight flight : flights) {
            if (!flight.isOpenForBooking()) continue;
            
            String flightDate = sdf.format(flight.getDepartureTime());
            if (!flightDate.equals(targetDate)) continue;
            
            boolean originMatch = flight.getDepartureAirport().servesCity(origin);
            boolean destMatch = flight.getArrivalAirport().servesCity(dest);
            
            if (originMatch && destMatch) {
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
    private boolean published;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
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
     * Adds a stopover to the flight while ensuring temporal consistency.
     * 
     * @param stop The stopover to add
     * @param now The current time for validation
     * @return true if the stopover is successfully added, false otherwise
     */
    public boolean addStopover(Stopover stop, Date now) {
        if (stop == null || departureTime.compareTo(now) <= 0) {
            return false;
        }
        
        if (stop.getArrivalTime().compareTo(departureTime) <= 0 || 
            stop.getDepartureTime().compareTo(arrivalTime) >= 0) {
            return false;
        }
        
        if (stop.getArrivalTime().compareTo(stop.getDepartureTime()) >= 0) {
            return false;
        }
        
        for (Stopover existing : stopovers) {
            if (existing.getArrivalTime().compareTo(stop.getArrivalTime()) == 0 ||
                existing.getDepartureTime().compareTo(stop.getDepartureTime()) == 0) {
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
     * @return true if the stopover is successfully removed, false otherwise
     */
    public boolean removeStopover(Stopover stop, Date now) {
        if (stop == null || departureTime.compareTo(now) <= 0) {
            return false;
        }
        return stopovers.remove(stop);
    }

    /**
     * Retrieves all confirmed reservations for this flight.
     * 
     * @return A list of confirmed reservations
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
     * Creates a booking for passengers on a specific flight.
     * 
     * @param f The flight to book
     * @param now The current time for validation
     * @param listOfPassengerNames List of passenger names
     * @return true if the booking is successfully created, false otherwise
     */
    public boolean addBooking(Flight f, Date now, List<String> listOfPassengerNames) {
        if (f == null || !f.isOpenForBooking() || f.getDepartureTime().compareTo(now) <= 0) {
            return false;
        }
        
        Set<String> passengerSet = new HashSet<>();
        for (String name : listOfPassengerNames) {
            if (!passengerSet.add(name)) {
                return false;
            }
        }
        
        Booking booking = new Booking();
        booking.setCustomer(this);
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
     * @param reservationID The ID of the reservation to confirm
     * @param now The current time for validation
     * @return true if the reservation is successfully confirmed, false otherwise
     */
    public boolean confirm(String reservationID, Date now) {
        Reservation reservation = findReservationById(reservationID);
        if (reservation == null) return false;
        
        Flight flight = reservation.getFlight();
        if (!flight.isOpenForBooking() || flight.getDepartureTime().compareTo(now) <= 0) {
            return false;
        }
        
        reservation.setStatus(ReservationStatus.CONFIRMED);
        return true;
    }

    /**
     * Cancels an existing reservation.
     * 
     * @param reservationID The ID of the reservation to cancel
     * @param now The current time for validation
     * @return true if the reservation is successfully canceled, false otherwise
     */
    public boolean cancel(String reservationID, Date now) {
        Reservation reservation = findReservationById(reservationID);
        if (reservation == null) return false;
        
        Flight flight = reservation.getFlight();
        if (!flight.isOpenForBooking() || flight.getDepartureTime().compareTo(now) <= 0) {
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
     * Creates a reservation for a passenger on a flight.
     * 
     * @param f The flight for the reservation
     * @param passenger The passenger for the reservation
     * @param now The current time for validation
     * @return true if the reservation is successfully created, false otherwise
     */
    public boolean createReservation(Flight f, Passenger passenger, Date now) {
        if (f == null || passenger == null || !f.isOpenForBooking() || 
            f.getDepartureTime().compareTo(now) <= 0) {
            return false;
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