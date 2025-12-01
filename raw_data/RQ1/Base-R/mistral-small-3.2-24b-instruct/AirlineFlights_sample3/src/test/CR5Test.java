import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CR5Test {
    
    private Flight flightF501;
    private Flight flightF502;
    private Flight flightF503;
    private Flight flightF504;
    private Map<String, Flight> airlineFlights;
    
    @Before
    public void setUp() {
        // Initialize flights for different test cases
        flightF501 = new Flight();
        flightF501.setId("F501");
        flightF501.setOpenForBooking(true);
        flightF501.setDepartureAirportId("DEP501");
        flightF501.setArrivalAirportId("ARR501");
        flightF501.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF501.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
        
        flightF502 = new Flight();
        flightF502.setId("F502");
        flightF502.setOpenForBooking(true);
        flightF502.setDepartureAirportId("DEP502");
        flightF502.setArrivalAirportId("ARR502");
        flightF502.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF502.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
        
        flightF503 = new Flight();
        flightF503.setId("F503");
        flightF503.setOpenForBooking(false);
        flightF503.setDepartureAirportId("DEP503");
        flightF503.setArrivalAirportId("ARR503");
        flightF503.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF503.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
        
        flightF504 = new Flight();
        flightF504.setId("F504");
        flightF504.setOpenForBooking(true);
        flightF504.setDepartureAirportId("DEP504");
        flightF504.setArrivalAirportId("ARR504");
        flightF504.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF504.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Create flight F501 with three confirmed reservations
        Reservation r5011 = new Reservation();
        r5011.setId("R501-1");
        r5011.setFlightId("F501");
        r5011.setPassengerName("Passenger1");
        r5011.confirm();
        
        Reservation r5012 = new Reservation();
        r5012.setId("R501-2");
        r5012.setFlightId("F501");
        r5012.setPassengerName("Passenger2");
        r5012.confirm();
        
        Reservation r5013 = new Reservation();
        r5013.setId("R501-3");
        r5013.setFlightId("F501");
        r5013.setPassengerName("Passenger3");
        r5013.confirm();
        
        flightF501.getReservations().put("R501-1", r5011);
        flightF501.getReservations().put("R501-2", r5012);
        flightF501.getReservations().put("R501-3", r5013);
        
        // Execute: Retrieve confirmed reservations for flight F501
        List<Reservation> confirmedReservations = flightF501.getConfirmedReservations();
        
        // Verify: Should return all three confirmed reservations
        assertEquals(3, confirmedReservations.size());
        assertTrue(confirmedReservations.contains(r5011));
        assertTrue(confirmedReservations.contains(r5012));
        assertTrue(confirmedReservations.contains(r5013));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Create flight F502 with two pending reservations
        Reservation r5021 = new Reservation();
        r5021.setId("R502-1");
        r5021.setFlightId("F502");
        r5021.setPassengerName("Passenger1");
        // Not confirmed - status remains pending
        
        Reservation r5022 = new Reservation();
        r5022.setId("R502-2");
        r5022.setFlightId("F502");
        r5022.setPassengerName("Passenger2");
        // Not confirmed - status remains pending
        
        flightF502.getReservations().put("R502-1", r5021);
        flightF502.getReservations().put("R502-2", r5022);
        
        // Execute: Retrieve confirmed reservations for flight F502
        List<Reservation> confirmedReservations = flightF502.getConfirmedReservations();
        
        // Verify: Should return empty list since no reservations are confirmed
        assertEquals(0, confirmedReservations.size());
        assertTrue(confirmedReservations.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Create flight F503 (closed) with one confirmed reservation
        Reservation r5031 = new Reservation();
        r5031.setId("R503-1");
        r5031.setFlightId("F503");
        r5031.setPassengerName("Passenger1");
        r5031.confirm();
        
        flightF503.getReservations().put("R503-1", r5031);
        
        // Execute: Retrieve confirmed reservations for flight F503
        List<Reservation> confirmedReservations = flightF503.getConfirmedReservations();
        
        // Verify: Should return empty list even though there's a confirmed reservation
        // because flight is closed for booking
        assertEquals(0, confirmedReservations.size());
        assertTrue(confirmedReservations.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup: Create a list of known flights (F504, F505)
        List<Flight> knownFlights = new ArrayList<>();
        Flight flightF505 = new Flight();
        flightF505.setId("F505");
        flightF505.setOpenForBooking(true);
        
        knownFlights.add(flightF504);
        knownFlights.add(flightF505);
        
        // Execute: Try to get confirmed reservations for unknown flight FX999
        // Since FX999 doesn't exist in our known flights, we simulate this by using a non-existent flight
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setOpenForBooking(true);
        
        List<Reservation> confirmedReservations = unknownFlight.getConfirmedReservations();
        
        // Verify: Should return empty list for unknown flight
        assertEquals(0, confirmedReservations.size());
        assertTrue(confirmedReservations.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Create flight F504 with mixed reservation statuses
        Reservation r504A = new Reservation();
        r504A.setId("R504-A");
        r504A.setFlightId("F504");
        r504A.setPassengerName("PassengerA");
        r504A.confirm(); // CONFIRMED
        
        Reservation r504B = new Reservation();
        r504B.setId("R504-B");
        r504B.setFlightId("F504");
        r504B.setPassengerName("PassengerB");
        r504B.confirm(); // CONFIRMED
        
        Reservation r504C = new Reservation();
        r504C.setId("R504-C");
        r504C.setFlightId("F504");
        r504C.setPassengerName("PassengerC");
        r504C.confirm();
        r504C.cancel(); // CANCELED
        
        Reservation r504D = new Reservation();
        r504D.setId("R504-D");
        r504D.setFlightId("F504");
        r504D.setPassengerName("PassengerD");
        // PENDING (not confirmed)
        
        flightF504.getReservations().put("R504-A", r504A);
        flightF504.getReservations().put("R504-B", r504B);
        flightF504.getReservations().put("R504-C", r504C);
        flightF504.getReservations().put("R504-D", r504D);
        
        // Execute: Retrieve confirmed reservations for flight F504
        List<Reservation> confirmedReservations = flightF504.getConfirmedReservations();
        
        // Verify: Should return only R504-A and R504-B (confirmed reservations)
        assertEquals(2, confirmedReservations.size());
        assertTrue(confirmedReservations.contains(r504A));
        assertTrue(confirmedReservations.contains(r504B));
        assertFalse(confirmedReservations.contains(r504C)); // Canceled reservation
        assertFalse(confirmedReservations.contains(r504D)); // Pending reservation
    }
}