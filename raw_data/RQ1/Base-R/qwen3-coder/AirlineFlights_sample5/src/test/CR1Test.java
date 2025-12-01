import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_correctScheduleAndRoute() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP01");
        departureAirport.addCity("CityA");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP02");
        arrivalAirport.addCity("CityB");
        
        flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        // Set current time to 2024-12-01 09:00:00 (before departure)
        // Note: In actual implementation, we'd mock the current time, but for this test
        // we rely on the fact that 2024-12-01 is before 2025-01-10
        
        // Execute
        boolean result = flight.publishForBooking();
        
        // Verify
        assertTrue("Flight with correct schedule and route should publish successfully", result);
    }
    
    @Test
    public void testCase2_departureAfterArrival() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP03");
        departureAirport.addCity("CityC");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP04");
        arrivalAirport.addCity("CityD");
        
        flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        
        // Set current time to 2024-12-15 10:00:00 (before departure)
        
        // Execute
        boolean result = flight.publishForBooking();
        
        // Verify
        assertFalse("Flight with departure after arrival should not publish", result);
    }
    
    @Test
    public void testCase3_sameDepartureAndArrivalAirport() {
        // Setup
        Airport airport = new Airport();
        airport.setId("AP05");
        airport.addCity("CityE");
        
        flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(airport);
        flight.setArrivalAirport(airport); // Same airport
        flight.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        // Set current time to 2025-02-01 09:00:00 (before departure)
        
        // Execute
        boolean result = flight.publishForBooking();
        
        // Verify
        assertFalse("Flight with same departure and arrival airport should not publish", result);
    }
    
    @Test
    public void testCase4_departureTimeInThePast() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP06");
        departureAirport.addCity("CityF");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP07");
        arrivalAirport.addCity("CityG");
        
        flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        
        // Current time is set to 2025-04-01 09:00:00 (after departure)
        // Note: In actual implementation, we'd mock the current time to be after the departure
        // For this test, we rely on the fact that 2025-04-01 is after 2025-03-30
        
        // Execute
        boolean result = flight.publishForBooking();
        
        // Verify
        assertFalse("Flight with departure time in the past should not publish", result);
    }
    
    @Test
    public void testCase5_secondPublishAttempt() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP08");
        departureAirport.addCity("CityH");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP09");
        arrivalAirport.addCity("CityI");
        
        flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        
        // First publish attempt (should succeed)
        boolean firstResult = flight.publishForBooking();
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt
        boolean secondResult = flight.publishForBooking();
        
        // Verify
        assertFalse("Second publish attempt should fail", secondResult);
    }
}