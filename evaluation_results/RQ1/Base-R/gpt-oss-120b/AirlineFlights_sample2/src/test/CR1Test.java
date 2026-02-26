import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CR1Test {
    
    private AirlineService airlineService;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineService = new AirlineService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Setup: Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport ap01 = new Airport("AP01", "Airport AP01");
        ap01.addCity(new City("CityA"));
        
        Airport ap02 = new Airport("AP02", "Airport AP02");
        ap02.addCity(new City("CityB"));
        
        // Create flight F100: departureAirport = AP01, arrivalAirport = AP02, 
        // departureTime = 2025-01-10 10:00:00, arrivalTime = 2025-01-10 14:00:00
        Flight flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(ap01);
        flight.setArrivalAirport(ap02);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        // Current system time = 2024-12-01 09:00:00
        // Note: We cannot actually set the system time, so we rely on the flight times being in the future
        
        // Execute: Publish flight F100
        boolean result = airlineService.publishFlight(flight);
        
        // Verify: Expected Output: True
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Setup: Airline AL2; airports AP03 (CityC) and AP04 (CityD)
        Airport ap03 = new Airport("AP03", "Airport AP03");
        ap03.addCity(new City("CityC"));
        
        Airport ap04 = new Airport("AP04", "Airport AP04");
        ap04.addCity(new City("CityD"));
        
        // Flight F101: AP03 → AP04, departure 2025-02-05 20:00:00,
        // arrival 2025-02-05 18:00:00 (departure after arrival - invalid)
        Flight flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(ap03);
        flight.setArrivalAirport(ap04);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        // Current time = 2024-12-15 10:00:00
        // Note: We cannot actually set the system time, so we rely on the flight times being in the future
        
        // Execute: Publish flight F101
        boolean result = airlineService.publishFlight(flight);
        
        // Verify: Expected Output: False
        assertFalse("Flight should not be published when departure is after arrival", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Setup: Airline AL3; airport AP05 (CityE)
        Airport ap05 = new Airport("AP05", "Airport AP05");
        ap05.addCity(new City("CityE"));
        
        // Flight F102: AP05 → AP05 (same airport - invalid),
        // departure 2025-03-01 08:00:00, arrival 2025-03-01 12:00:00
        Flight flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(ap05);
        flight.setArrivalAirport(ap05); // Same airport
        flight.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        // Current time = 2025-02-01 09:00:00
        // Note: We cannot actually set the system time, so we rely on the flight times being in the future
        
        // Execute: Publish flight F102
        boolean result = airlineService.publishFlight(flight);
        
        // Verify: Expected Output: False
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Setup: Airline AL4; airports AP06 (CityF) and AP07 (CityG)
        Airport ap06 = new Airport("AP06", "Airport AP06");
        ap06.addCity(new City("CityF"));
        
        Airport ap07 = new Airport("AP07", "Airport AP07");
        ap07.addCity(new City("CityG"));
        
        // Flight F103: departure 2025-03-30 10:00:00, arrival 2025-03-30 12:00:00
        Flight flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(ap06);
        flight.setArrivalAirport(ap07);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        // Current time = 2025-04-01 09:00:00 (after flight departure - invalid)
        // Note: We cannot actually set the system time, so we rely on the flight times being in the future
        // Since we cannot change the actual system time, this test may fail if run after March 30, 2025
        // In a real implementation, we would mock the time or use a different approach
        
        // Execute: Publish flight F103
        boolean result = airlineService.publishFlight(flight);
        
        // Verify: Expected Output: False
        // Note: Due to time dependency, this test might not work as expected in the future
        // In a production environment, we would use time mocking
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Setup: Airline AL5; airports AP08 (CityH) and AP09 (CityI)
        Airport ap08 = new Airport("AP08", "Airport AP08");
        ap08.addCity(new City("CityH"));
        
        Airport ap09 = new Airport("AP09", "Airport AP09");
        ap09.addCity(new City("CityI"));
        
        // Flight F104 already published successfully with
        // departure 2025-05-05 07:00:00, arrival 2025-05-05 10:00:00
        Flight flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(ap08);
        flight.setArrivalAirport(ap09);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        // Current time = 2025-04-01 10:00:00
        // Note: We cannot actually set the system time, so we rely on the flight times being in the future
        
        // First publish attempt (should succeed)
        boolean firstResult = airlineService.publishFlight(flight);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt (should fail)
        boolean secondResult = airlineService.publishFlight(flight);
        
        // Verify: Expected Output: False
        assertFalse("Second publish attempt should fail", secondResult);
    }
}