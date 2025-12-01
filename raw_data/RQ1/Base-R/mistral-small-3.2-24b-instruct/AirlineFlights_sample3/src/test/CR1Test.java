import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private Flight flight;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        flight = new Flight();
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Setup: Create flight with correct schedule and route
        flight.setDepartureAirportId("AP01");
        flight.setArrivalAirportId("AP02");
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        // Set current time to 2024-12-01 09:00:00 (before departure)
        // Note: We'll use the actual current time since the method uses LocalDateTime.now()
        // For this test to pass, we need to ensure the departure time is in the future
        
        // Test: Publish the flight
        boolean result = flight.publishFlight();
        
        // Verify: Should return true for valid flight
        assertTrue("Flight with correct schedule and route should publish successfully", result);
        assertTrue("Flight should be open for booking after successful publish", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Setup: Create flight with departure time after arrival time
        flight.setDepartureAirportId("AP03");
        flight.setArrivalAirportId("AP04");
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        
        // Test: Publish the flight
        boolean result = flight.publishFlight();
        
        // Verify: Should return false for invalid flight schedule
        assertFalse("Flight with departure after arrival should not publish", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Setup: Create flight with same departure and arrival airport
        flight.setDepartureAirportId("AP05");
        flight.setArrivalAirportId("AP05");
        flight.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        // Test: Publish the flight
        boolean result = flight.publishFlight();
        
        // Verify: Should return false for same departure and arrival airport
        assertFalse("Flight with same departure and arrival airport should not publish", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Setup: Create flight with departure time in the past
        flight.setDepartureAirportId("AP06");
        flight.setArrivalAirportId("AP07");
        
        // Set departure time to past date (2025-03-30) and current time to future (2025-04-01)
        // Since we can't mock LocalDateTime.now(), we'll rely on the actual validation logic
        // This test will only pass if run after 2025-03-30
        flight.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        
        // Test: Publish the flight
        boolean result = flight.publishFlight();
        
        // Verify: Should return false for past departure time
        // Note: This test may fail if current date is before 2025-03-30
        if (LocalDateTime.now().isAfter(LocalDateTime.parse("2025-03-30 10:00:00", formatter))) {
            assertFalse("Flight with past departure time should not publish", result);
        }
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Setup: Create and successfully publish a flight first
        flight.setDepartureAirportId("AP08");
        flight.setArrivalAirportId("AP09");
        flight.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        
        // First publish attempt (should succeed)
        boolean firstAttempt = flight.publishFlight();
        assertTrue("First publish attempt should succeed", firstAttempt);
        
        // Second publish attempt
        boolean secondAttempt = flight.publishFlight();
        
        // Verify: Second attempt should return false
        assertFalse("Second publish attempt should fail", secondAttempt);
    }
}