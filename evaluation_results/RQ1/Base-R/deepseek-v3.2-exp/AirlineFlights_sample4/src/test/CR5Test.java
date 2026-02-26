import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
        // Setup flight F501
        Flight flight = createFlight("F501", true);
        airlineSystem.publishFlight(flight);
        
        // Create reservations with CONFIRMED status
        createReservation(flight, "R501-1", "confirmed");
        createReservation(flight, "R501-2", "confirmed"); 
        createReservation(flight, "R501-3", "confirmed");
        
        // Retrieve confirmed reservations for flight F501
        List<Reservation> result = airlineSystem.getConfirmedReservations("F501");
        
        // Verify result contains exactly 3 confirmed reservations
        assertEquals(3, result.size());
        assertTrue(containsReservationWithId(result, "R501-1"));
        assertTrue(containsReservationWithId(result, "R501-2"));
        assertTrue(containsReservationWithId(result, "R501-3"));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup flight F502
        Flight flight = createFlight("F502", true);
        airlineSystem.publishFlight(flight);
        
        // Create reservations with PENDING status
        createReservation(flight, "R502-1", "pending");
        createReservation(flight, "R502-2", "pending");
        
        // Retrieve confirmed reservations for flight F502
        List<Reservation> result = airlineSystem.getConfirmedReservations("F502");
        
        // Verify result is empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup flight F503 with openForBooking = false
        Flight flight = createFlight("F503", false);
        airlineSystem.publishFlight(flight);
        
        // Create reservation with CONFIRMED status
        createReservation(flight, "R503-1", "confirmed");
        
        // Retrieve confirmed reservations for flight F503
        List<Reservation> result = airlineSystem.getConfirmedReservations("F503");
        
        // Verify result is empty list (flight is closed)
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup airline with flights F504 and F505 only
        Flight flight504 = createFlight("F504", true);
        Flight flight505 = createFlight("F505", true);
        airlineSystem.publishFlight(flight504);
        airlineSystem.publishFlight(flight505);
        
        // Retrieve confirmed reservations for unknown flight FX999
        List<Reservation> result = airlineSystem.getConfirmedReservations("FX999");
        
        // Verify result is empty list (flight not found)
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup flight F504
        Flight flight = createFlight("F504", true);
        airlineSystem.publishFlight(flight);
        
        // Create reservations with mixed statuses
        createReservation(flight, "R504-A", "confirmed");
        createReservation(flight, "R504-B", "confirmed");
        createReservation(flight, "R504-C", "canceled");
        createReservation(flight, "R504-D", "pending");
        
        // Retrieve confirmed reservations for flight F504
        List<Reservation> result = airlineSystem.getConfirmedReservations("F504");
        
        // Verify result contains only confirmed reservations R504-A and R504-B
        assertEquals(2, result.size());
        assertTrue(containsReservationWithId(result, "R504-A"));
        assertTrue(containsReservationWithId(result, "R504-B"));
        assertFalse(containsReservationWithId(result, "R504-C"));
        assertFalse(containsReservationWithId(result, "R504-D"));
    }
    
    // Helper method to create a flight with basic setup
    private Flight createFlight(String flightId, boolean openForBooking) {
        Flight flight = new Flight();
        flight.setId(flightId);
        flight.setOpenForBooking(openForBooking);
        
        // Set departure and arrival airports
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP");
        Set<String> depCities = new HashSet<>();
        depCities.add("CityA");
        departureAirport.setServedCities(depCities);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR");
        Set<String> arrCities = new HashSet<>();
        arrCities.add("CityB");
        arrivalAirport.setServedCities(arrCities);
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        
        // Set future departure and arrival times
        LocalDateTime now = LocalDateTime.now();
        flight.setDepartureTime(now.plusDays(1));
        flight.setArrivalTime(now.plusDays(1).plusHours(3));
        
        return flight;
    }
    
    // Helper method to create a reservation with specific status
    private void createReservation(Flight flight, String reservationId, String status) {
        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setCustomerId("CUST123");
        
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setFlight(flight);
        reservation.setPassengerName("Passenger " + reservationId);
        reservation.setStatus(status);
        reservation.setBooking(booking);
        
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        airlineSystem.getBookings().add(booking);
    }
    
    // Helper method to check if a list contains a reservation with specific ID
    private boolean containsReservationWithId(List<Reservation> reservations, String reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(reservationId)) {
                return true;
            }
        }
        return false;
    }
}