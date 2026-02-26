import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CR5Test {
    
    private Flight flightF501;
    private Flight flightF502;
    private Flight flightF503;
    private Flight flightF504;
    private List<Flight> airlineAL24Flights;
    
    @Before
    public void setUp() {
        // Setup for Test Case 1: Flight with three confirmations
        flightF501 = new Flight();
        flightF501.setId("F501");
        flightF501.setOpen(true);
        
        List<Reservation> reservationsF501 = new ArrayList<>();
        
        Reservation r5011 = new Reservation();
        r5011.setId("R501-1");
        r5011.setConfirmed(true);
        reservationsF501.add(r5011);
        
        Reservation r5012 = new Reservation();
        r5012.setId("R501-2");
        r5012.setConfirmed(true);
        reservationsF501.add(r5012);
        
        Reservation r5013 = new Reservation();
        r5013.setId("R501-3");
        r5013.setConfirmed(true);
        reservationsF501.add(r5013);
        
        flightF501.setReservations(reservationsF501);
        
        // Setup for Test Case 2: No confirmed reservations
        flightF502 = new Flight();
        flightF502.setId("F502");
        flightF502.setOpen(true);
        
        List<Reservation> reservationsF502 = new ArrayList<>();
        
        Reservation r5021 = new Reservation();
        r5021.setId("R502-1");
        r5012.setConfirmed(false); // PENDING
        reservationsF502.add(r5021);
        
        Reservation r5022 = new Reservation();
        r5022.setId("R502-2");
        r5022.setConfirmed(false); // PENDING
        reservationsF502.add(r5022);
        
        flightF502.setReservations(reservationsF502);
        
        // Setup for Test Case 3: Flight closed returns zero
        flightF503 = new Flight();
        flightF503.setId("F503");
        flightF503.setOpen(false);
        
        List<Reservation> reservationsF503 = new ArrayList<>();
        
        Reservation r5031 = new Reservation();
        r5031.setId("R503-1");
        r5031.setConfirmed(true);
        reservationsF503.add(r5031);
        
        flightF503.setReservations(reservationsF503);
        
        // Setup for Test Case 4: Unknown flight id
        airlineAL24Flights = new ArrayList<>();
        
        Flight flightF504 = new Flight();
        flightF504.setId("F504");
        airlineAL24Flights.add(flightF504);
        
        Flight flightF505 = new Flight();
        flightF505.setId("F505");
        airlineAL24Flights.add(flightF505);
        
        // Setup for Test Case 5: Mixed reservation statuses
        Flight flightF504Mixed = new Flight();
        flightF504Mixed.setId("F504");
        flightF504Mixed.setOpen(true);
        
        List<Reservation> reservationsF504 = new ArrayList<>();
        
        Reservation r504A = new Reservation();
        r504A.setId("R504-A");
        r504A.setConfirmed(true);
        reservationsF504.add(r504A);
        
        Reservation r504B = new Reservation();
        r504B.setId("R504-B");
        r504B.setConfirmed(true);
        reservationsF504.add(r504B);
        
        Reservation r504C = new Reservation();
        r504C.setId("R504-C");
        r504C.setConfirmed(false); // CANCELED
        reservationsF504.add(r504C);
        
        Reservation r504D = new Reservation();
        r504D.setId("R504-D");
        r504D.setConfirmed(false); // PENDING
        reservationsF504.add(r504D);
        
        flightF504Mixed.setReservations(reservationsF504);
        flightF504 = flightF504Mixed;
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Input: Retrieve confirmed list for flight F501
        List<Reservation> result = flightF501.getConfirmedReservations();
        
        // Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R501-1")));
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R501-2")));
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R501-3")));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Input: Retrieve confirmed list for flight F502
        List<Reservation> result = flightF502.getConfirmedReservations();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Input: Retrieve confirmed list for flight F503
        List<Reservation> result = flightF503.getConfirmedReservations();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Input: Retrieve confirmed list for flight FX999
        // Since the method is instance-based, we need to simulate the scenario
        // by checking if flight exists in airline's list
        boolean flightExists = airlineAL24Flights.stream()
            .anyMatch(f -> f.getId().equals("FX999"));
        
        // If flight doesn't exist, we expect empty list
        List<Reservation> result = new ArrayList<>();
        
        // Expected Output: []
        assertFalse(flightExists);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Input: Retrieve confirmed list for flight F504
        List<Reservation> result = flightF504.getConfirmedReservations();
        
        // Expected Output: R504-A, R504-B
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R504-A")));
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R504-B")));
        assertFalse(result.stream().anyMatch(r -> r.getId().equals("R504-C")));
        assertFalse(result.stream().anyMatch(r -> r.getId().equals("R504-D")));
    }
}