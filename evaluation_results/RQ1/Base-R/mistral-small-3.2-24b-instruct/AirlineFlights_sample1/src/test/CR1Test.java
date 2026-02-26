import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class CR1Test {
    
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        // Initialize formatter for date parsing
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_correctScheduleAndRoute() {
        // Setup
        departureAirport = new Airport("AP01", Arrays.asList("CityA"));
        arrivalAirport = new Airport("AP02", Arrays.asList("CityB"));
        
        LocalDateTime departureTime = LocalDateTime.parse("2025-01-10 10:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-01-10 14:00:00", formatter);
        
        flight = new Flight("F100", departureAirport, arrivalAirport, departureTime, arrivalTime);
        
        // Mock current time to be before departure time
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 09:00:00", formatter);
        // Note: In actual implementation, you might need to use a time mocking framework
        
        // Execute
        boolean result = flight.publishFlight();
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
        assertTrue("Flight should be marked as published", flight.isPublished());
    }
    
    @Test
    public void testCase2_departureAfterArrival() {
        // Setup
        departureAirport = new Airport("AP03", Arrays.asList("CityC"));
        arrivalAirport = new Airport("AP04", Arrays.asList("CityD"));
        
        LocalDateTime departureTime = LocalDateTime.parse("2025-02-05 20:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-02-05 18:00:00", formatter);
        
        flight = new Flight("F101", departureAirport, arrivalAirport, departureTime, arrivalTime);
        
        // Mock current time
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-15 10:00:00", formatter);
        
        // Execute
        boolean result = flight.publishFlight();
        
        // Verify
        assertFalse("Flight should not be published when departure time is after arrival time", result);
        assertFalse("Flight should remain unpublished", flight.isPublished());
    }
    
    @Test
    public void testCase3_sameDepartureAndArrivalAirport() {
        // Setup
        Airport airport = new Airport("AP05", Arrays.asList("CityE"));
        
        LocalDateTime departureTime = LocalDateTime.parse("2025-03-01 08:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-03-01 12:00:00", formatter);
        
        flight = new Flight("F102", airport, airport, departureTime, arrivalTime);
        
        // Mock current time
        LocalDateTime currentTime = LocalDateTime.parse("2025-02-01 09:00:00", formatter);
        
        // Execute
        boolean result = flight.publishFlight();
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
        assertFalse("Flight should remain unpublished", flight.isPublished());
    }
    
    @Test
    public void testCase4_departureTimeInThePast() {
        // Setup
        departureAirport = new Airport("AP06", Arrays.asList("CityF"));
        arrivalAirport = new Airport("AP07", Arrays.asList("CityG"));
        
        LocalDateTime departureTime = LocalDateTime.parse("2025-03-30 10:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-03-30 12:00:00", formatter);
        
        flight = new Flight("F103", departureAirport, arrivalAirport, departureTime, arrivalTime);
        
        // Mock current time to be after departure time
        LocalDateTime currentTime = LocalDateTime.parse("2025-04-01 09:00:00", formatter);
        
        // Execute
        boolean result = flight.publishFlight();
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past", result);
        assertFalse("Flight should remain unpublished", flight.isPublished());
    }
    
    @Test
    public void testCase5_secondPublishAttempt() {
        // Setup
        departureAirport = new Airport("AP08", Arrays.asList("CityH"));
        arrivalAirport = new Airport("AP09", Arrays.asList("CityI"));
        
        LocalDateTime departureTime = LocalDateTime.parse("2025-05-05 07:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-05-05 10:00:00", formatter);
        
        flight = new Flight("F104", departureAirport, arrivalAirport, departureTime, arrivalTime);
        
        // Mock current time
        LocalDateTime currentTime = LocalDateTime.parse("2025-04-01 10:00:00", formatter);
        
        // First publish attempt (should succeed)
        boolean firstAttempt = flight.publishFlight();
        assertTrue("First publish attempt should succeed", firstAttempt);
        assertTrue("Flight should be marked as published", flight.isPublished());
        
        // Execute second publish attempt
        boolean secondAttempt = flight.publishFlight();
        
        // Verify
        assertFalse("Second publish attempt should fail", secondAttempt);
        assertTrue("Flight should remain published", flight.isPublished());
    }
}