import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Flight flightF501;
    private Flight flightF502;
    private Flight flightF503;
    private Flight flightF504;
    private Flight flightF505;
    
    @Before
    public void setUp() {
        // Setup airports
        Airport airport1 = new Airport();
        airport1.setId("AP1");
        airport1.addCity("City1");
        
        Airport airport2 = new Airport();
        airport2.setId("AP2");
        airport2.addCity("City2");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Setup flight F501 with three confirmed reservations
        flightF501 = new Flight();
        flightF501.setId("F501");
        flightF501.setDepartureAirport(airport1);
        flightF501.setArrivalAirport(airport2);
        flightF501.setDepartureTime(LocalDateTime.parse("2024-12-10 10:00:00", formatter));
        flightF501.setArrivalTime(LocalDateTime.parse("2024-12-10 12:00:00", formatter));
        flightF501.setStatus("open");
        
        Reservation r5011 = new Reservation();
        r5011.setPassengerName("Passenger1");
        r5011.setStatus("confirmed");
        flightF501.addReservation(r5011);
        
        Reservation r5012 = new Reservation();
        r5012.setPassengerName("Passenger2");
        r5012.setStatus("confirmed");
        flightF501.addReservation(r5012);
        
        Reservation r5013 = new Reservation();
        r5013.setPassengerName("Passenger3");
        r5013.setStatus("confirmed");
        flightF501.addReservation(r5013);
        
        // Setup flight F502 with two pending reservations
        flightF502 = new Flight();
        flightF502.setId("F502");
        flightF502.setDepartureAirport(airport1);
        flightF502.setArrivalAirport(airport2);
        flightF502.setDepartureTime(LocalDateTime.parse("2024-12-11 10:00:00", formatter));
        flightF502.setArrivalTime(LocalDateTime.parse("2024-12-11 12:00:00", formatter));
        flightF502.setStatus("open");
        
        Reservation r5021 = new Reservation();
        r5021.setPassengerName("Passenger4");
        r5021.setStatus("pending");
        flightF502.addReservation(r5021);
        
        Reservation r5022 = new Reservation();
        r5022.setPassengerName("Passenger5");
        r5022.setStatus("pending");
        flightF502.addReservation(r5022);
        
        // Setup flight F503 (closed) with one confirmed reservation
        flightF503 = new Flight();
        flightF503.setId("F503");
        flightF503.setDepartureAirport(airport1);
        flightF503.setArrivalAirport(airport2);
        flightF503.setDepartureTime(LocalDateTime.parse("2024-12-12 10:00:00", formatter));
        flightF503.setArrivalTime(LocalDateTime.parse("2024-12-12 12:00:00", formatter));
        flightF503.setStatus("closed");
        
        Reservation r503 = new Reservation();
        r503.setPassengerName("Passenger6");
        r503.setStatus("confirmed");
        flightF503.addReservation(r503);
        
        // Setup flight F504 with mixed reservation statuses
        flightF504 = new Flight();
        flightF504.setId("F504");
        flightF504.setDepartureAirport(airport1);
        flightF504.setArrivalAirport(airport2);
        flightF504.setDepartureTime(LocalDateTime.parse("2024-12-13 10:00:00", formatter));
        flightF504.setArrivalTime(LocalDateTime.parse("2024-12-13 12:00:00", formatter));
        flightF504.setStatus("open");
        
        Reservation r504A = new Reservation();
        r504A.setPassengerName("PassengerA");
        r504A.setStatus("confirmed");
        flightF504.addReservation(r504A);
        
        Reservation r504B = new Reservation();
        r504B.setPassengerName("PassengerB");
        r504B.setStatus("confirmed");
        flightF504.addReservation(r504B);
        
        Reservation r504C = new Reservation();
        r504C.setPassengerName("PassengerC");
        r504C.setStatus("canceled");
        flightF504.addReservation(r504C);
        
        Reservation r504D = new Reservation();
        r504D.setPassengerName("PassengerD");
        r504D.setStatus("pending");
        flightF504.addReservation(r504D);
        
        // Setup flight F505
        flightF505 = new Flight();
        flightF505.setId("F505");
        flightF505.setDepartureAirport(airport1);
        flightF505.setArrivalAirport(airport2);
        flightF505.setDepartureTime(LocalDateTime.parse("2024-12-14 10:00:00", formatter));
        flightF505.setArrivalTime(LocalDateTime.parse("2024-12-14 12:00:00", formatter));
        flightF505.setStatus("open");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Test Case 1: Flight with three confirmations
        // Input: Retrieve confirmed list for flight F501
        List<Reservation> result = flightF501.getConfirmedReservations();
        
        // Expected Output: Three confirmed reservations
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        
        // Verify all reservations are confirmed
        for (Reservation reservation : result) {
            assertEquals("Reservation should be confirmed", "confirmed", reservation.getStatus());
        }
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Test Case 2: No confirmed reservations
        // Input: Retrieve confirmed list for flight F502
        List<Reservation> result = flightF502.getConfirmedReservations();
        
        // Expected Output: Empty list
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Test Case 3: Flight closed returns zero
        // Input: Retrieve confirmed list for flight F503
        List<Reservation> result = flightF503.getConfirmedReservations();
        
        // Expected Output: Empty list (flight is closed)
        assertTrue("Should return empty list when flight is closed", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Test Case 4: Unknown flight id
        // Since we're testing the Flight class directly, we simulate this by creating a flight not in our setup
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setStatus("open");
        
        // Input: Retrieve confirmed list for flight FX999
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Expected Output: Empty list
        assertTrue("Should return empty list for unknown flight", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Test Case 5: Mixed reservation statuses
        // Input: Retrieve confirmed list for flight F504
        List<Reservation> result = flightF504.getConfirmedReservations();
        
        // Expected Output: Only R504-A and R504-B (confirmed reservations)
        assertEquals("Should return 2 confirmed reservations", 2, result.size());
        
        // Verify only confirmed reservations are returned
        for (Reservation reservation : result) {
            assertEquals("Reservation should be confirmed", "confirmed", reservation.getStatus());
        }
        
        // Verify specific passengers are in the result
        List<String> passengerNames = new ArrayList<>();
        for (Reservation reservation : result) {
            passengerNames.add(reservation.getPassengerName());
        }
        assertTrue("Should contain PassengerA", passengerNames.contains("PassengerA"));
        assertTrue("Should contain PassengerB", passengerNames.contains("PassengerB"));
        assertFalse("Should not contain PassengerC", passengerNames.contains("PassengerC"));
        assertFalse("Should not contain PassengerD", passengerNames.contains("PassengerD"));
    }
}