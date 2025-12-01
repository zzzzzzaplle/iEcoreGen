import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Airline airline;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Flight flight;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        airline = new Airline();
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Setup
        // 1. Create airline AL1
        Airline al1 = new Airline();
        
        // 2. Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport ap01 = new Airport();
        ap01.addCity("CityA");
        Airport ap02 = new Airport();
        ap02.addCity("CityB");
        
        // 3. Create flight F100
        Flight f100 = new Flight();
        f100.setDepartureAirportId(ap01.getId());
        f100.setArrivalAirportId(ap02.getId());
        f100.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        f100.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        // Set current time to 2024-12-01 09:00:00 (mock current time by validating against this)
        // Since we cannot mock LocalDateTime.now(), we'll rely on the flight's validation logic
        // which compares against the actual current time. For this test to pass, we need to ensure
        // the flight times are in the future relative to when the test runs.
        
        // Test execution
        boolean result = f100.publishFlight();
        
        // Expected output: True
        assertTrue("Flight with correct schedule and route should publish successfully", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Setup
        // 1. Airline AL2; airports AP03 (CityC) and AP04 (CityD)
        Airline al2 = new Airline();
        Airport ap03 = new Airport();
        ap03.addCity("CityC");
        Airport ap04 = new Airport();
        ap04.addCity("CityD");
        
        // 2. Flight F101
        Flight f101 = new Flight();
        f101.setDepartureAirportId(ap03.getId());
        f101.setArrivalAirportId(ap04.getId());
        f101.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        f101.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter)); // Departure after arrival
        
        // Test execution
        boolean result = f101.publishFlight();
        
        // Expected output: False
        assertFalse("Flight with departure after arrival should not publish", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Setup
        // 1. Airline AL3; airport AP05 (CityE)
        Airline al3 = new Airline();
        Airport ap05 = new Airport();
        ap05.addCity("CityE");
        
        // 2. Flight F102
        Flight f102 = new Flight();
        f102.setDepartureAirportId(ap05.getId());
        f102.setArrivalAirportId(ap05.getId()); // Same airport
        f102.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        f102.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        // Test execution
        boolean result = f102.publishFlight();
        
        // Expected output: False
        assertFalse("Flight with same departure and arrival airport should not publish", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Setup
        // 1. Airline AL4; airports AP06 (CityF) and AP07 (CityG)
        Airline al4 = new Airline();
        Airport ap06 = new Airport();
        ap06.addCity("CityF");
        Airport ap07 = new Airport();
        ap07.addCity("CityG");
        
        // 2. Flight F103
        Flight f103 = new Flight();
        f103.setDepartureAirportId(ap06.getId());
        f103.setArrivalAirportId(ap07.getId());
        
        // Set departure time in the past relative to test execution time
        LocalDateTime pastDeparture = LocalDateTime.now().minusDays(1);
        LocalDateTime pastArrival = pastDeparture.plusHours(2);
        
        f103.setDepartureTime(pastDeparture);
        f103.setArrivalTime(pastArrival);
        
        // Test execution
        boolean result = f103.publishFlight();
        
        // Expected output: False
        assertFalse("Flight with departure time in the past should not publish", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Setup
        // 1. Airline AL5; airports AP08 (CityH) and AP09 (CityI)
        Airline al5 = new Airline();
        Airport ap08 = new Airport();
        ap08.addCity("CityH");
        Airport ap09 = new Airport();
        ap09.addCity("CityI");
        
        // 2. Flight F104 already published successfully
        Flight f104 = new Flight();
        f104.setDepartureAirportId(ap08.getId());
        f104.setArrivalAirportId(ap09.getId());
        
        // Set times in the future to ensure first publish succeeds
        LocalDateTime futureDeparture = LocalDateTime.now().plusDays(30);
        LocalDateTime futureArrival = futureDeparture.plusHours(3);
        
        f104.setDepartureTime(futureDeparture);
        f104.setArrivalTime(futureArrival);
        
        // First publish attempt (should succeed)
        boolean firstAttempt = f104.publishFlight();
        assertTrue("First publish attempt should succeed", firstAttempt);
        
        // Second publish attempt
        boolean secondAttempt = f104.publishFlight();
        
        // Expected output: False
        assertFalse("Second publish attempt should fail", secondAttempt);
    }
}