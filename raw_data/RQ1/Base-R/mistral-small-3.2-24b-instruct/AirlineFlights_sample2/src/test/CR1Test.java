import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class CR1Test {
    
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        // Reset flight and airports before each test
        flight = new Flight();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Setup: Create flight with correct schedule and different airports
        flight.setDepartureAirportId(departureAirport.getId());
        flight.setArrivalAirportId(arrivalAirport.getId());
        
        // Set departure time in the future and arrival time after departure
        LocalDateTime departureTime = LocalDateTime.parse("2025-01-10 10:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-01-10 14:00:00", formatter);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Test: Publish flight should return true
        boolean result = flight.publishFlight();
        assertTrue("Flight with correct schedule and route should publish successfully", result);
        assertTrue("Flight should be open for booking after successful publish", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Setup: Create flight with departure time after arrival time
        flight.setDepartureAirportId("AP03");
        flight.setArrivalAirportId("AP04");
        
        // Set departure time after arrival time (invalid)
        LocalDateTime departureTime = LocalDateTime.parse("2025-02-05 20:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-02-05 18:00:00", formatter);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Test: Publish flight should return false due to invalid timing
        boolean result = flight.publishFlight();
        assertFalse("Flight with departure after arrival should not publish", result);
        assertFalse("Flight should remain closed for booking after failed publish", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Setup: Create flight with same departure and arrival airport
        String sameAirportId = "AP05";
        flight.setDepartureAirportId(sameAirportId);
        flight.setArrivalAirportId(sameAirportId);
        
        // Set valid timestamps
        LocalDateTime departureTime = LocalDateTime.parse("2025-03-01 08:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-03-01 12:00:00", formatter);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Test: Publish flight should return false due to same airport
        boolean result = flight.publishFlight();
        assertFalse("Flight with same departure and arrival airport should not publish", result);
        assertFalse("Flight should remain closed for booking after failed publish", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Setup: Create flight with departure time in the past
        flight.setDepartureAirportId("AP06");
        flight.setArrivalAirportId("AP07");
        
        // Set departure time in the past relative to current test execution
        // Using a past date that will always be before current time
        LocalDateTime departureTime = LocalDateTime.parse("2020-03-30 10:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2020-03-30 12:00:00", formatter);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Test: Publish flight should return false due to past departure time
        boolean result = flight.publishFlight();
        assertFalse("Flight with departure time in the past should not publish", result);
        assertFalse("Flight should remain closed for booking after failed publish", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Setup: Create and successfully publish a flight first
        flight.setDepartureAirportId("AP08");
        flight.setArrivalAirportId("AP09");
        
        LocalDateTime departureTime = LocalDateTime.parse("2025-05-05 07:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-05-05 10:00:00", formatter);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // First publish attempt should succeed
        boolean firstResult = flight.publishFlight();
        assertTrue("First publish attempt should succeed", firstResult);
        assertTrue("Flight should be open for booking after first publish", flight.isOpenForBooking());
        
        // Test: Second publish attempt should return false
        boolean secondResult = flight.publishFlight();
        assertFalse("Second publish attempt should fail", secondResult);
        assertTrue("Flight should remain open for booking after second publish attempt", flight.isOpenForBooking());
    }
}