import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CR5Test {
    
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Airline AL21; flight F501 openForBooking = True
        Flight flight = createFlight("F501", true);
        airlineSystem.getFlights().add(flight);
        
        // Setup: Reservations R501-1, R501-2, R501-3 status = CONFIRMED
        Booking booking = createBookingWithReservations(flight, 
            List.of("R501-1", "R501-2", "R501-3"), "confirmed");
        airlineSystem.getBookings().add(booking);
        
        // Input: Retrieve confirmed list for flight F501
        List<Reservation> result = airlineSystem.getConfirmedReservationsForFlight("F501");
        
        // Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, result.size());
        assertTrue(containsReservationWithId(result, "R501-1"));
        assertTrue(containsReservationWithId(result, "R501-2"));
        assertTrue(containsReservationWithId(result, "R501-3"));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Airline AL22; flight F502 openForBooking = True
        Flight flight = createFlight("F502", true);
        airlineSystem.getFlights().add(flight);
        
        // Setup: Two reservations status = PENDING
        Booking booking = createBookingWithReservations(flight, 
            List.of("R502-1", "R502-2"), "pending");
        airlineSystem.getBookings().add(booking);
        
        // Input: Retrieve confirmed list for flight F502
        List<Reservation> result = airlineSystem.getConfirmedReservationsForFlight("F502");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Airline AL23; flight F503 openForBooking = False
        Flight flight = createFlight("F503", false);
        airlineSystem.getFlights().add(flight);
        
        // Setup: One reservation status = CONFIRMED
        Booking booking = createBookingWithReservations(flight, 
            List.of("R503-1"), "confirmed");
        airlineSystem.getBookings().add(booking);
        
        // Input: Retrieve confirmed list for flight F503
        List<Reservation> result = airlineSystem.getConfirmedReservationsForFlight("F503");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup: Airline AL24 holds flights F504, F505 only
        Flight flight1 = createFlight("F504", true);
        Flight flight2 = createFlight("F505", true);
        airlineSystem.getFlights().add(flight1);
        airlineSystem.getFlights().add(flight2);
        
        // Input: Retrieve confirmed list for flight FX999
        List<Reservation> result = airlineSystem.getConfirmedReservationsForFlight("FX999");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Airline AL25; flight F504 openForBooking = True
        Flight flight = createFlight("F504", true);
        airlineSystem.getFlights().add(flight);
        
        // Setup: Mixed reservation statuses
        Booking booking = new Booking();
        booking.setFlight(flight);
        
        // Add confirmed reservations
        addReservationToBooking(booking, "R504-A", "confirmed");
        addReservationToBooking(booking, "R504-B", "confirmed");
        
        // Add canceled reservation
        addReservationToBooking(booking, "R504-C", "canceled");
        
        // Add pending reservation
        addReservationToBooking(booking, "R504-D", "pending");
        
        airlineSystem.getBookings().add(booking);
        
        // Input: Retrieve confirmed list for flight F504
        List<Reservation> result = airlineSystem.getConfirmedReservationsForFlight("F504");
        
        // Expected Output: R504-A, R504-B
        assertEquals(2, result.size());
        assertTrue(containsReservationWithId(result, "R504-A"));
        assertTrue(containsReservationWithId(result, "R504-B"));
        assertFalse(containsReservationWithId(result, "R504-C"));
        assertFalse(containsReservationWithId(result, "R504-D"));
    }
    
    private Flight createFlight(String flightId, boolean openForBooking) {
        Flight flight = new Flight();
        flight.setId(flightId);
        flight.setOpenForBooking(openForBooking);
        
        // Set future departure and arrival times
        LocalDateTime now = LocalDateTime.now();
        flight.setDepartureTime(now.plusDays(1));
        flight.setArrivalTime(now.plusDays(2));
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP" + flightId);
        departureAirport.getServedCities().add("CityA");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR" + flightId);
        arrivalAirport.getServedCities().add("CityB");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        
        return flight;
    }
    
    private Booking createBookingWithReservations(Flight flight, List<String> reservationIds, String status) {
        Booking booking = new Booking();
        booking.setFlight(flight);
        
        for (String reservationId : reservationIds) {
            Reservation reservation = new Reservation();
            reservation.setId(reservationId);
            reservation.setFlight(flight);
            reservation.setPassengerName("Passenger_" + reservationId);
            reservation.setStatus(status);
            booking.getReservations().add(reservation);
        }
        
        return booking;
    }
    
    private void addReservationToBooking(Booking booking, String reservationId, String status) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setFlight(booking.getFlight());
        reservation.setPassengerName("Passenger_" + reservationId);
        reservation.setStatus(status);
        booking.getReservations().add(reservation);
    }
    
    private boolean containsReservationWithId(List<Reservation> reservations, String reservationId) {
        for (Reservation reservation : reservations) {
            if (reservationId.equals(reservation.getId())) {
                return true;
            }
        }
        return false;
    }
}