import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        // Setup
        // 1. Create airline AL1 (implicit through airlineSystem)
        // 2. Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        List<String> citiesA = new ArrayList<>();
        citiesA.add("CityA");
        ap01.setCities(citiesA);
        
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        List<String> citiesB = new ArrayList<>();
        citiesB.add("CityB");
        ap02.setCities(citiesB);
        
        // 3. Create flight F100
        Flight flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(ap01);
        flight.setArrivalAirport(ap02);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        // 4. Current system time = 2024-12-01 09:00:00 (mocked by setting flight times appropriately)
        
        // Test
        boolean result = airlineSystem.publishFlight(flight);
        
        // Verify
        assertTrue("Flight with correct schedule and route should be published successfully", result);
        assertTrue("Flight should be marked as published", flight.isPublished());
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Setup
        // 1. Airline AL2; airports AP03 (CityC) and AP04 (CityD)
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        List<String> citiesC = new ArrayList<>();
        citiesC.add("CityC");
        ap03.setCities(citiesC);
        
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        List<String> citiesD = new ArrayList<>();
        citiesD.add("CityD");
        ap04.setCities(citiesD);
        
        // 2. Flight F101: AP03 -> AP04, departure 2025-02-05 20:00:00, arrival 2025-02-05 18:00:00
        Flight flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(ap03);
        flight.setArrivalAirport(ap04);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        
        // 3. Current time = 2024-12-15 10:00:00 (mocked by setting flight times appropriately)
        
        // Test
        boolean result = airlineSystem.publishFlight(flight);
        
        // Verify
        assertFalse("Flight with departure after arrival should not be published", result);
        assertFalse("Flight should not be marked as published", flight.isPublished());
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Setup
        // 1. Airline AL3; airport AP05 (CityE)
        Airport ap05 = new Airport();
        ap05.setId("AP05");
        List<String> citiesE = new ArrayList<>();
        citiesE.add("CityE");
        ap05.setCities(citiesE);
        
        // 2. Flight F102: AP05 -> AP05, departure 2025-03-01 08:00:00, arrival 2025-03-01 12:00:00
        Flight flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(ap05);
        flight.setArrivalAirport(ap05);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        // 3. Current time = 2025-02-01 09:00:00 (mocked by setting flight times appropriately)
        
        // Test
        boolean result = airlineSystem.publishFlight(flight);
        
        // Verify
        assertFalse("Flight with same departure and arrival airport should not be published", result);
        assertFalse("Flight should not be marked as published", flight.isPublished());
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Setup
        // 1. Airline AL4; airports AP06 (CityF) and AP07 (CityG)
        Airport ap06 = new Airport();
        ap06.setId("AP06");
        List<String> citiesF = new ArrayList<>();
        citiesF.add("CityF");
        ap06.setCities(citiesF);
        
        Airport ap07 = new Airport();
        ap07.setId("AP07");
        List<String> citiesG = new ArrayList<>();
        citiesG.add("CityG");
        ap07.setCities(citiesG);
        
        // 2. Flight F103: departure 2025-03-30 10:00:00, arrival 2025-03-30 12:00:00
        Flight flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(ap06);
        flight.setArrivalAirport(ap07);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        
        // 3. Current time = 2025-04-01 09:00:00 (mocked by setting flight times appropriately)
        
        // Test
        boolean result = airlineSystem.publishFlight(flight);
        
        // Verify
        assertFalse("Flight with departure time in the past should not be published", result);
        assertFalse("Flight should not be marked as published", flight.isPublished());
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Setup
        // 1. Airline AL5; airports AP08 (CityH) and AP09 (CityI)
        Airport ap08 = new Airport();
        ap08.setId("AP08");
        List<String> citiesH = new ArrayList<>();
        citiesH.add("CityH");
        ap08.setCities(citiesH);
        
        Airport ap09 = new Airport();
        ap09.setId("AP09");
        List<String> citiesI = new ArrayList<>();
        citiesI.add("CityI");
        ap09.setCities(citiesI);
        
        // 2. Flight F104 already published successfully
        Flight flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(ap08);
        flight.setArrivalAirport(ap09);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        
        // First publish attempt (should succeed)
        boolean firstResult = airlineSystem.publishFlight(flight);
        assertTrue("First publish attempt should succeed", firstResult);
        assertTrue("Flight should be marked as published after first attempt", flight.isPublished());
        
        // 3. Current time = 2025-04-01 10:00:00 (mocked by setting flight times appropriately)
        
        // Test - Second publish attempt
        boolean secondResult = airlineSystem.publishFlight(flight);
        
        // Verify
        assertFalse("Second publish attempt should fail", secondResult);
        assertTrue("Flight should remain published after second attempt", flight.isPublished());
    }
}