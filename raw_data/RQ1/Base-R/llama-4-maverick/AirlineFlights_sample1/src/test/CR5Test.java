import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    
    @Before
    public void setUp() {
        // Setup common test data
        departureAirport = new Airport();
        departureAirport.setId("D1");
        departureAirport.addCity("City1");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("A1");
        arrivalAirport.addCity("City2");
        
        flight = new Flight();
        flight.setId("F501");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        
        // Set departure time in the future
        flight.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Flight F501 open for booking with 3 confirmed reservations
        flight.setStatus("published");
        flight.setId("F501");
        
        // Create and add confirmed reservations
        Reservation r1 = new Reservation();
        r1.setPassengerName("Passenger1");
        r1.setStatus("confirmed");
        flight.addReservation(r1);
        
        Reservation r2 = new Reservation();
        r2.setPassengerName("Passenger2");
        r2.setStatus("confirmed");
        flight.addReservation(r2);
        
        Reservation r3 = new Reservation();
        r3.setPassengerName("Passenger3");
        r3.setStatus("confirmed");
        flight.addReservation(r3);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Should return all 3 confirmed reservations
        assertEquals(3, result.size());
        assertTrue(result.contains(r1));
        assertTrue(result.contains(r2));
        assertTrue(result.contains(r3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Flight F502 open for booking with 2 pending reservations
        flight.setStatus("published");
        flight.setId("F502");
        
        // Create and add pending reservations
        Reservation r1 = new Reservation();
        r1.setPassengerName("Passenger1");
        r1.setStatus("pending");
        flight.addReservation(r1);
        
        Reservation r2 = new Reservation();
        r2.setPassengerName("Passenger2");
        r2.setStatus("pending");
        flight.addReservation(r2);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Should return empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Flight F503 closed with 1 confirmed reservation
        flight.setStatus("closed");
        flight.setId("F503");
        
        // Create and add confirmed reservation
        Reservation r1 = new Reservation();
        r1.setPassengerName("Passenger1");
        r1.setStatus("confirmed");
        flight.addReservation(r1);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Should return empty list (flight is not published)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup: Different flight object to simulate unknown flight
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setStatus("published");
        
        // Execute: Retrieve confirmed reservations for unknown flight
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify: Should return empty list (no reservations exist)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Flight F504 open for booking with mixed reservation statuses
        flight.setStatus("published");
        flight.setId("F504");
        
        // Create and add reservations with different statuses
        Reservation rConfirmed1 = new Reservation();
        rConfirmed1.setPassengerName("PassengerA");
        rConfirmed1.setStatus("confirmed");
        flight.addReservation(rConfirmed1);
        
        Reservation rConfirmed2 = new Reservation();
        rConfirmed2.setPassengerName("PassengerB");
        rConfirmed2.setStatus("confirmed");
        flight.addReservation(rConfirmed2);
        
        Reservation rCanceled = new Reservation();
        rCanceled.setPassengerName("PassengerC");
        rCanceled.setStatus("canceled");
        flight.addReservation(rCanceled);
        
        Reservation rPending = new Reservation();
        rPending.setPassengerName("PassengerD");
        rPending.setStatus("pending");
        flight.addReservation(rPending);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Should return only the 2 confirmed reservations
        assertEquals(2, result.size());
        
        // Check that only confirmed reservations are returned
        for (Reservation res : result) {
            assertEquals("confirmed", res.getStatus());
        }
        
        // Verify specific confirmed reservations are included
        boolean hasA = false, hasB = false;
        for (Reservation res : result) {
            if ("PassengerA".equals(res.getPassengerName())) hasA = true;
            if ("PassengerB".equals(res.getPassengerName())) hasB = true;
        }
        assertTrue(hasA);
        assertTrue(hasB);
    }
}