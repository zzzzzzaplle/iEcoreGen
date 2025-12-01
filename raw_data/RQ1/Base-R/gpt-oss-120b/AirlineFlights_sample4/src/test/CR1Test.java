import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR1Test {

    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Setup: Create airline AL1
        // Create airports AP01 (serves CityA) and AP02 (serves CityB)
        City cityA = airlineSystem.createCity("C001", "CityA");
        City cityB = airlineSystem.createCity("C002", "CityB");
        
        List<City> citiesAP01 = Arrays.asList(cityA);
        List<City> citiesAP02 = Arrays.asList(cityB);
        
        airlineSystem.createAirport("AP01", "Airport1", citiesAP01);
        airlineSystem.createAirport("AP02", "Airport2", citiesAP02);
        
        // Create flight F100: departureAirport = AP01, arrivalAirport = AP02, 
        // departureTime = 2025-01-10 10:00:00, arrivalTime = 2025-01-10 14:00:00
        airlineSystem.createFlight("F100", "AP01", "AP02", "2025-01-10 10:00:00", "2025-01-10 14:00:00");
        
        // Current system time = 2024-12-01 09:00:00
        // Mock current time by ensuring the flight's departure is in the future
        
        // Test: Publish flight F100
        boolean result = airlineSystem.publishFlight("F100");
        
        // Expected Output: True
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }

    @Test
    public void testCase2_DepartureAfterArrival() {
        // Setup: Create airline AL2
        // Create airports AP03 (CityC) and AP04 (CityD)
        City cityC = airlineSystem.createCity("C003", "CityC");
        City cityD = airlineSystem.createCity("C004", "CityD");
        
        List<City> citiesAP03 = Arrays.asList(cityC);
        List<City> citiesAP04 = Arrays.asList(cityD);
        
        airlineSystem.createAirport("AP03", "Airport3", citiesAP03);
        airlineSystem.createAirport("AP04", "Airport4", citiesAP04);
        
        // Flight F101: AP03  ➜ AP04, departure 2025-02-05 20:00:00, arrival 2025-02-05 18:00:00
        // Note: Departure time is after arrival time (invalid)
        airlineSystem.createFlight("F101", "AP03", "AP04", "2025-02-05 20:00:00", "2025-02-05 18:00:00");
        
        // Current time = 2024-12-15 10:00:00 (before departure, but departure > arrival)
        
        // Test: Publish flight F101
        boolean result = airlineSystem.publishFlight("F101");
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure is after arrival", result);
    }

    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Setup: Create airline AL3
        // Create airport AP05 (CityE)
        City cityE = airlineSystem.createCity("C005", "CityE");
        List<City> citiesAP05 = Arrays.asList(cityE);
        
        airlineSystem.createAirport("AP05", "Airport5", citiesAP05);
        
        // Flight F102: AP05 ➜ AP05 (same airport)
        airlineSystem.createFlight("F102", "AP05", "AP05", "2025-03-01 08:00:00", "2025-03-01 12:00:00");
        
        // Current time = 2025-02-01 09:00:00
        
        // Test: Publish flight F102
        boolean result = airlineSystem.publishFlight("F102");
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }

    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Setup: Create airline AL4
        // Create airports AP06 (CityF) and AP07 (CityG)
        City cityF = airlineSystem.createCity("C006", "CityF");
        City cityG = airlineSystem.createCity("C007", "CityG");
        
        List<City> citiesAP06 = Arrays.asList(cityF);
        List<City> citiesAP07 = Arrays.asList(cityG);
        
        airlineSystem.createAirport("AP06", "Airport6", citiesAP06);
        airlineSystem.createAirport("AP07", "Airport7", citiesAP07);
        
        // Flight F103: departure 2025-03-30 10:00:00, arrival 2025-03-30 12:00:00
        airlineSystem.createFlight("F103", "AP06", "AP07", "2025-03-30 10:00:00", "2025-03-30 12:00:00");
        
        // Current time = 2025-04-01 09:00:00 (after departure time)
        // Note: We cannot directly set system time, but the publishFlight method checks if current time is before departure
        // Since current system time will be after the test flight's departure, this should fail
        
        // Test: Publish flight F103
        boolean result = airlineSystem.publishFlight("F103");
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure time is in the past", result);
    }

    @Test
    public void testCase5_SecondPublishAttempt() {
        // Setup: Create airline AL5
        // Create airports AP08 (CityH) and AP09 (CityI)
        City cityH = airlineSystem.createCity("C008", "CityH");
        City cityI = airlineSystem.createCity("C009", "CityI");
        
        List<City> citiesAP08 = Arrays.asList(cityH);
        List<City> citiesAP09 = Arrays.asList(cityI);
        
        airlineSystem.createAirport("AP08", "Airport8", citiesAP08);
        airlineSystem.createAirport("AP09", "Airport9", citiesAP09);
        
        // Flight F104 already published successfully with departure 2025-05-05 07:00:00, arrival 2025-05-05 10:00:00
        airlineSystem.createFlight("F104", "AP08", "AP09", "2025-05-05 07:00:00", "2025-05-05 10:00:00");
        
        // First publish attempt (should succeed)
        boolean firstResult = airlineSystem.publishFlight("F104");
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Current time = 2025-04-01 10:00:00 (before departure)
        
        // Test: Second publish attempt
        boolean secondResult = airlineSystem.publishFlight("F104");
        
        // Expected Output: False
        assertFalse("Second publish attempt should fail", secondResult);
    }
}