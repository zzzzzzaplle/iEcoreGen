import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        flight = new Flight();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Setup
        departureAirport.setId("AP01");
        departureAirport.addCity("CityA");
        
        arrivalAirport.setId("AP02");
        arrivalAirport.addCity("CityB");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        // Execute
        boolean result = flight.publish();
        
        // Verify
        assertTrue("Flight should publish successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Setup
        departureAirport.setId("AP03");
        departureAirport.addCity("CityC");
        
        arrivalAirport.setId("AP04");
        arrivalAirport.addCity("CityD");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        
        // Execute
        boolean result = flight.publish();
        
        // Verify
        assertFalse("Flight should not publish when departure time is after arrival time", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Setup
        departureAirport.setId("AP05");
        departureAirport.addCity("CityE");
        
        arrivalAirport.setId("AP05"); // Same airport
        arrivalAirport.addCity("CityE");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        // Execute
        boolean result = flight.publish();
        
        // Verify
        assertFalse("Flight should not publish when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Setup
        departureAirport.setId("AP06");
        departureAirport.addCity("CityF");
        
        arrivalAirport.setId("AP07");
        arrivalAirport.addCity("CityG");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        // Set departure time in the past relative to test execution
        flight.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        
        // Note: Since we cannot actually set the system time to 2025-04-01,
        // we rely on the fact that 2025-03-30 is in the past relative to when this test runs
        // This test will only pass correctly if run after 2025-03-30
        // For proper testing, the Flight class would need to be refactored to allow time injection
        
        // Execute
        boolean result = flight.publish();
        
        // Verify - This test may fail if run before 2025-03-30
        // For demonstration purposes, we'll accept the current behavior
        // In a real scenario, we would mock the time
        assertFalse("Flight should not publish when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Setup
        departureAirport.setId("AP08");
        departureAirport.addCity("CityH");
        
        arrivalAirport.setId("AP09");
        arrivalAirport.addCity("CityI");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        
        // First publish attempt
        boolean firstResult = flight.publish();
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt
        boolean secondResult = flight.publish();
        
        // Verify
        assertFalse("Second publish attempt should fail", secondResult);
    }
}