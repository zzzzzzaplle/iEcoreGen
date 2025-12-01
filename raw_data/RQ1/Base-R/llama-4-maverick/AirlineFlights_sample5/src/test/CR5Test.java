import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CR5Test {
    
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    
    @Before
    public void setUp() {
        // Common setup for tests that need a basic flight
        departureAirport = new Airport();
        departureAirport.setId("DEP");
        departureAirport.addCity("Departure City");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("ARR");
        arrivalAirport.addCity("Arrival City");
        
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
        flight.setId("F501");
        flight.setStatus("open");
        
        // Create and add 3 confirmed reservations
        Reservation r1 = new Reservation();
        r1.setId("R501-1");
        r1.setPassengerName("Passenger1");
        r1.setStatus("confirmed");
        flight.addReservation(r1);
        
        Reservation r2 = new Reservation();
        r2.setId("R501-2");
        r2.setPassengerName("Passenger2");
        r2.setStatus("confirmed");
        flight.addReservation(r2);
        
        Reservation r3 = new Reservation();
        r3.setId("R501-3");
        r3.setPassengerName("Passenger3");
        r3.setStatus("confirmed");
        flight.addReservation(r3);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Should return all 3 confirmed reservations
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R501-1")));
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R501-2")));
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R501-3")));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Flight F502 open for booking with 2 pending reservations
        flight.setId("F502");
        flight.setStatus("open");
        
        // Create and add 2 pending reservations
        Reservation r1 = new Reservation();
        r1.setId("R502-1");
        r1.setPassengerName("Passenger1");
        r1.setStatus("pending");
        flight.addReservation(r1);
        
        Reservation r2 = new Reservation();
        r2.setId("R502-2");
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
        flight.setId("F503");
        flight.setStatus("closed");
        
        // Create and add 1 confirmed reservation
        Reservation r1 = new Reservation();
        r1.setId("R503-1");
        r1.setPassengerName("Passenger1");
        r1.setStatus("confirmed");
        flight.addReservation(r1);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Should return empty list when flight is closed
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Note: Since the method is instance-based, we'll simulate unknown flight by using a different flight instance
        // Setup: Create a flight that's not F504 or F505 (as per specification)
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setStatus("open");
        
        // Execute: Retrieve confirmed reservations from unknown flight
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify: Should return empty list for flight with no reservations
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Flight F504 open for booking with mixed reservation statuses
        flight.setId("F504");
        flight.setStatus("open");
        
        // Create and add reservations with mixed statuses
        Reservation r1 = new Reservation();
        r1.setId("R504-A");
        r1.setPassengerName("PassengerA");
        r1.setStatus("confirmed");
        flight.addReservation(r1);
        
        Reservation r2 = new Reservation();
        r2.setId("R504-B");
        r2.setPassengerName("PassengerB");
        r2.setStatus("confirmed");
        flight.addReservation(r2);
        
        Reservation r3 = new Reservation();
        r3.setId("R504-C");
        r3.setPassengerName("PassengerC");
        r3.setStatus("canceled");
        flight.addReservation(r3);
        
        Reservation r4 = new Reservation();
        r4.setId("R504-D");
        r4.setPassengerName("PassengerD");
        r4.setStatus("pending");
        flight.addReservation(r4);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Should return only the 2 confirmed reservations
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R504-A")));
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R504-B")));
        assertFalse(result.stream().anyMatch(r -> r.getId().equals("R504-C")));
        assertFalse(result.stream().anyMatch(r -> r.getId().equals("R504-D")));
    }
}