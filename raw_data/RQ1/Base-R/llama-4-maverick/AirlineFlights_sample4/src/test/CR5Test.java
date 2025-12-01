import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    
    @Before
    public void setUp() {
        // Common setup for tests that need a flight
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        flight = new Flight();
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Flight F501 open for booking with 3 confirmed reservations
        flight.setId("F501");
        flight.setStatus("open");
        
        // Create and add confirmed reservations
        Reservation r1 = new Reservation();
        r1.setId("R501-1");
        r1.setStatus("confirmed");
        r1.setPassengerName("Passenger1");
        
        Reservation r2 = new Reservation();
        r2.setId("R501-2");
        r2.setStatus("confirmed");
        r2.setPassengerName("Passenger2");
        
        Reservation r3 = new Reservation();
        r3.setId("R501-3");
        r3.setStatus("confirmed");
        r3.setPassengerName("Passenger3");
        
        flight.getReservations().add(r1);
        flight.getReservations().add(r2);
        flight.getReservations().add(r3);
        
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
        flight.setId("F502");
        flight.setStatus("open");
        
        // Create and add pending reservations
        Reservation r1 = new Reservation();
        r1.setId("R502-1");
        r1.setStatus("pending");
        r1.setPassengerName("Passenger1");
        
        Reservation r2 = new Reservation();
        r2.setId("R502-2");
        r2.setStatus("pending");
        r2.setPassengerName("Passenger2");
        
        flight.getReservations().add(r1);
        flight.getReservations().add(r2);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Should return empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Flight F503 closed with 1 confirmed reservation
        flight.setId("F503");
        flight.setStatus("closed");
        
        // Create and add confirmed reservation
        Reservation r1 = new Reservation();
        r1.setId("R503-1");
        r1.setStatus("confirmed");
        r1.setPassengerName("Passenger1");
        
        flight.getReservations().add(r1);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Should return empty list because flight is closed
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup: Create a flight that doesn't match the query
        flight.setId("F504"); // Different from FX999 being queried
        
        // For this test, we need to simulate the scenario where we're looking for FX999
        // Since the method operates on the current flight instance, we'll create a separate scenario
        
        // Create a flight manager or repository simulation
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setStatus("open");
        
        // Execute: Retrieve confirmed reservations from unknown flight
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify: Should return empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Flight F504 open for booking with mixed reservation statuses
        flight.setId("F504");
        flight.setStatus("open");
        
        // Create confirmed reservations
        Reservation rA = new Reservation();
        rA.setId("R504-A");
        rA.setStatus("confirmed");
        rA.setPassengerName("PassengerA");
        
        Reservation rB = new Reservation();
        rB.setId("R504-B");
        rB.setStatus("confirmed");
        rB.setPassengerName("PassengerB");
        
        // Create canceled reservation
        Reservation rC = new Reservation();
        rC.setId("R504-C");
        rC.setStatus("canceled");
        rC.setPassengerName("PassengerC");
        
        // Create pending reservation
        Reservation rD = new Reservation();
        rD.setId("R504-D");
        rD.setStatus("pending");
        rD.setPassengerName("PassengerD");
        
        flight.getReservations().add(rA);
        flight.getReservations().add(rB);
        flight.getReservations().add(rC);
        flight.getReservations().add(rD);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Should return only the 2 confirmed reservations (R504-A, R504-B)
        assertEquals(2, result.size());
        assertTrue(result.contains(rA));
        assertTrue(result.contains(rB));
        assertFalse(result.contains(rC)); // Canceled should not be included
        assertFalse(result.contains(rD)); // Pending should not be included
    }
}