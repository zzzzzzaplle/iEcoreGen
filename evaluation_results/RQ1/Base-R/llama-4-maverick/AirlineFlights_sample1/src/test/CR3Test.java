import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Flight flight;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() {
        // Setup for Test Case 1
        Airport ap160 = new Airport();
        ap160.setId("AP160");
        ap160.addCity("CityAA");
        
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        ap161.addCity("CityAB");
        
        flight = new Flight();
        flight.setId("F401");
        flight.setDepartureAirport(ap160);
        flight.setArrivalAirport(ap161);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 15:00:00", formatter));
        flight.setStatus("published"); // openForBooking = True
        
        // Create reservation R401 with PENDING status for passenger P9
        Reservation r401 = new Reservation();
        r401.setPassengerName("P9");
        r401.setStatus("pending");
        flight.addReservation(r401);
        
        // Set current time to 2025-11-01 09:00:00
        // In actual implementation, this would require mocking LocalDateTime.now()
        // For this test, we'll rely on the flight departure time being in the future
        
        // Test: Confirm reservation R401
        boolean result = flight.updateReservation(r401.getId(), true);
        
        // Verify result and reservation status
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be confirmed", "confirmed", r401.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() {
        // Setup for Test Case 2
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        ap170.addCity("CityAC");
        
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        ap171.addCity("CityAD");
        
        flight = new Flight();
        flight.setId("F402");
        flight.setDepartureAirport(ap170);
        flight.setArrivalAirport(ap171);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-15 15:00:00", formatter));
        flight.setStatus("published"); // openForBooking = True
        
        // Create reservation R402 with CONFIRMED status for passenger P10
        Reservation r402 = new Reservation();
        r402.setPassengerName("P10");
        r402.setStatus("confirmed");
        flight.addReservation(r402);
        
        // Set current time to 2025-12-01 12:00:00
        // In actual implementation, this would require mocking LocalDateTime.now()
        // For this test, we'll rely on the flight departure time being in the future
        
        // Test: Cancel reservation R402
        boolean result = flight.updateReservation(r402.getId(), false);
        
        // Verify result and reservation status
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be canceled", "canceled", r402.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() {
        // Setup for Test Case 3
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        ap180.addCity("CityAE");
        
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        ap181.addCity("CityAF");
        
        flight = new Flight();
        flight.setId("F403");
        flight.setDepartureAirport(ap180);
        flight.setArrivalAirport(ap181);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-05 06:00:00", formatter));
        flight.setStatus("published"); // openForBooking = True
        
        // Create reservation R403 with PENDING status for passenger P11
        Reservation r403 = new Reservation();
        r403.setPassengerName("P11");
        r403.setStatus("pending");
        flight.addReservation(r403);
        
        // Set current time to 2025-01-05 07:00:00 (flight already departed)
        // This test case expects false because flight has departed
        // In actual implementation, we would need to mock LocalDateTime.now() to return 2025-01-05 07:00:00
        // Since we can't mock static methods in this test, we'll rely on the logic that checks departure time
        
        // The flight departure time is in the past relative to the test time
        // The method should return false because flight has departed
        boolean result = flight.updateReservation(r403.getId(), true);
        
        // Verify result - should be false because flight has departed
        assertFalse("Should return false when flight has departed", result);
        assertEquals("Reservation status should remain pending", "pending", r403.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() {
        // Setup for Test Case 4
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        ap190.addCity("CityAG");
        
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        ap191.addCity("CityAH");
        
        flight = new Flight();
        flight.setId("F404");
        flight.setDepartureAirport(ap190);
        flight.setArrivalAirport(ap191);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-01 09:00:00", formatter));
        flight.setStatus("closed"); // openForBooking = False
        
        // Create reservation R404 with CONFIRMED status for passenger P12
        Reservation r404 = new Reservation();
        r404.setPassengerName("P12");
        r404.setStatus("confirmed");
        flight.addReservation(r404);
        
        // Set current time to 2025-01-20 08:00:00
        // In actual implementation, this would require mocking LocalDateTime.now()
        
        // Test: Cancel reservation R404 - should fail because flight is closed
        boolean result = flight.updateReservation(r404.getId(), false);
        
        // Verify result - should be false because flight is closed
        assertFalse("Should return false when flight is closed", result);
        assertEquals("Reservation status should remain confirmed", "confirmed", r404.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() {
        // Setup for Test Case 5
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        ap200.addCity("CityAI");
        
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        ap201.addCity("CityAJ");
        
        flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirport(ap200);
        flight.setArrivalAirport(ap201);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-10 10:00:00", formatter));
        flight.setStatus("published"); // openForBooking = True
        
        // Create reservation R405 with PENDING status for passenger P13 (CU20)
        Reservation r405 = new Reservation();
        r405.setPassengerName("P13");
        r405.setStatus("pending");
        flight.addReservation(r405);
        
        // Create reservation R406 with PENDING status for passenger P14 (CU21)
        Reservation r406 = new Reservation();
        r406.setPassengerName("P14");
        r406.setStatus("pending");
        // Note: R406 is NOT added to the flight - this simulates "unknown reservation"
        
        // Set current time to 2025-02-15 09:00:00
        // In actual implementation, this would require mocking LocalDateTime.now()
        
        // Test: Try to confirm reservation R406 which doesn't exist on this flight
        boolean result = flight.updateReservation(r406.getId(), true);
        
        // Verify result - should be false because reservation R406 is not on this flight
        assertFalse("Should return false for unknown reservation ID", result);
    }
}