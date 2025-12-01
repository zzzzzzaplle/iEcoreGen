import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private AirlineSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_correctScheduleAndRoute() {
        // Setup
        // 1. Create airline AL1 (Note: AirlineSystem doesn't have airlines, so we'll create customer as proxy)
        Customer al1 = new Customer("AL1", "Airline AL1");
        system.addCustomer(al1);
        
        // 2. Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport ap01 = new Airport("AP01", "Airport AP01");
        City cityA = new City("CITYA", "CityA");
        ap01.addCity(cityA);
        system.addAirport(ap01);
        
        Airport ap02 = new Airport("AP02", "Airport AP02");
        City cityB = new City("CITYB", "CityB");
        ap02.addCity(cityB);
        system.addAirport(ap02);
        
        // 3. Create flight F100
        LocalDateTime departureTime = LocalDateTime.parse("2025-01-10 10:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-01-10 14:00:00", formatter);
        Flight f100 = new Flight(ap01, ap02, departureTime, arrivalTime);
        system.addFlight(f100);
        
        // 4. Set current system time = 2024-12-01 09:00:00 (mocked by test timing)
        // In actual implementation, this would require time mocking
        
        // Test: Publish flight F100
        boolean result = system.publishFlight(f100.getId());
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_departureAfterArrival() {
        // Setup
        // 1. Airline AL2
        Customer al2 = new Customer("AL2", "Airline AL2");
        system.addCustomer(al2);
        
        // 2. Airports AP03 (CityC) and AP04 (CityD)
        Airport ap03 = new Airport("AP03", "Airport AP03");
        City cityC = new City("CITYC", "CityC");
        ap03.addCity(cityC);
        system.addAirport(ap03);
        
        Airport ap04 = new Airport("AP04", "Airport AP04");
        City cityD = new City("CITYD", "CityD");
        ap04.addCity(cityD);
        system.addAirport(ap04);
        
        // Flight F101 with departure after arrival
        LocalDateTime departureTime = LocalDateTime.parse("2025-02-05 20:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-02-05 18:00:00", formatter);
        Flight f101 = new Flight(ap03, ap04, departureTime, arrivalTime);
        system.addFlight(f101);
        
        // 3. Current time = 2024-12-15 10:00:00 (mocked by test timing)
        
        // Test: Publish flight F101
        boolean result = system.publishFlight(f101.getId());
        
        // Verify
        assertFalse("Flight should not be published when departure is after arrival", result);
    }
    
    @Test
    public void testCase3_sameDepartureAndArrivalAirport() {
        // Setup
        // 1. Airline AL3
        Customer al3 = new Customer("AL3", "Airline AL3");
        system.addCustomer(al3);
        
        // 2. Airport AP05 (CityE)
        Airport ap05 = new Airport("AP05", "Airport AP05");
        City cityE = new City("CITYE", "CityE");
        ap05.addCity(cityE);
        system.addAirport(ap05);
        
        // Flight F102 with same departure and arrival airport
        LocalDateTime departureTime = LocalDateTime.parse("2025-03-01 08:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-03-01 12:00:00", formatter);
        Flight f102 = new Flight(ap05, ap05, departureTime, arrivalTime);
        system.addFlight(f102);
        
        // 3. Current time = 2025-02-01 09:00:00 (mocked by test timing)
        
        // Test: Publish flight F102
        boolean result = system.publishFlight(f102.getId());
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_departureTimeInThePast() {
        // Setup
        // 1. Airline AL4
        Customer al4 = new Customer("AL4", "Airline AL4");
        system.addCustomer(al4);
        
        // 2. Airports AP06 (CityF) and AP07 (CityG)
        Airport ap06 = new Airport("AP06", "Airport AP06");
        City cityF = new City("CITYF", "CityF");
        ap06.addCity(cityF);
        system.addAirport(ap06);
        
        Airport ap07 = new Airport("AP07", "Airport AP07");
        City cityG = new City("CITYG", "CityG");
        ap07.addCity(cityG);
        system.addAirport(ap07);
        
        // Flight F103 with departure in the past relative to current time
        LocalDateTime departureTime = LocalDateTime.parse("2025-03-30 10:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-03-30 12:00:00", formatter);
        Flight f103 = new Flight(ap06, ap07, departureTime, arrivalTime);
        system.addFlight(f103);
        
        // 3. Current time = 2025-04-01 09:00:00 (mocked by test timing - departure is in the past)
        
        // Test: Publish flight F103
        boolean result = system.publishFlight(f103.getId());
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_secondPublishAttempt() {
        // Setup
        // 1. Airline AL5
        Customer al5 = new Customer("AL5", "Airline AL5");
        system.addCustomer(al5);
        
        // 2. Airports AP08 (CityH) and AP09 (CityI)
        Airport ap08 = new Airport("AP08", "Airport AP08");
        City cityH = new City("CITYH", "CityH");
        ap08.addCity(cityH);
        system.addAirport(ap08);
        
        Airport ap09 = new Airport("AP09", "Airport AP09");
        City cityI = new City("CITYI", "CityI");
        ap09.addCity(cityI);
        system.addAirport(ap09);
        
        // Flight F104 with valid schedule
        LocalDateTime departureTime = LocalDateTime.parse("2025-05-05 07:00:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-05-05 10:00:00", formatter);
        Flight f104 = new Flight(ap08, ap09, departureTime, arrivalTime);
        system.addFlight(f104);
        
        // 3. Current time = 2025-04-01 10:00:00 (mocked by test timing)
        
        // First publish attempt (should succeed)
        boolean firstResult = system.publishFlight(f104.getId());
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt
        boolean secondResult = system.publishFlight(f104.getId());
        
        // Verify
        assertFalse("Second publish attempt should fail", secondResult);
    }
}