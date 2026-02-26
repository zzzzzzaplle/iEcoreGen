import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_correctScheduleAndRoute() {
        // Setup: Create airline AL1
        Airline al1 = new Airline("AL1", "Airline 1");
        
        // Setup: Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport ap01 = new Airport("AP01", "Airport 01");
        City cityA = new City("CITYA", "CityA");
        ap01.addCity(cityA);
        
        Airport ap02 = new Airport("AP02", "Airport 02");
        City cityB = new City("CITYB", "CityB");
        ap02.addCity(cityB);
        
        // Setup: Create flight F100 with valid schedule
        Flight flight = new Flight("F100");
        flight.setDepartureAirport(ap01);
        flight.setArrivalAirport(ap02);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        flight.setPublished(false);
        
        // Setup: Set current system time = 2024-12-01 09:00:00
        // Note: In actual implementation, we'd mock LocalDateTime.now()
        // For this test, we assume the FlightService uses the real current time
        
        // Execute: Publish flight F100
        boolean result = FlightService.publishFlight(flight);
        
        // Verify: Expected output is True
        assertTrue("Flight with correct schedule and route should publish successfully", result);
    }
    
    @Test
    public void testCase2_departureAfterArrival() {
        // Setup: Create airline AL2
        Airline al2 = new Airline("AL2", "Airline 2");
        
        // Setup: Create airports AP03 (CityC) and AP04 (CityD)
        Airport ap03 = new Airport("AP03", "Airport 03");
        City cityC = new City("CITYC", "CityC");
        ap03.addCity(cityC);
        
        Airport ap04 = new Airport("AP04", "Airport 04");
        City cityD = new City("CITYD", "CityD");
        ap04.addCity(cityD);
        
        // Setup: Create flight F101 with departure after arrival
        Flight flight = new Flight("F101");
        flight.setDepartureAirport(ap03);
        flight.setArrivalAirport(ap04);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        flight.setPublished(false);
        
        // Setup: Current time = 2024-12-15 10:00:00
        // Note: In actual implementation, we'd mock LocalDateTime.now()
        
        // Execute: Publish flight F101
        boolean result = FlightService.publishFlight(flight);
        
        // Verify: Expected output is False due to departure after arrival
        assertFalse("Flight with departure after arrival should not publish", result);
    }
    
    @Test
    public void testCase3_sameDepartureAndArrivalAirport() {
        // Setup: Create airline AL3
        Airline al3 = new Airline("AL3", "Airline 3");
        
        // Setup: Create airport AP05 (CityE)
        Airport ap05 = new Airport("AP05", "Airport 05");
        City cityE = new City("CITYE", "CityE");
        ap05.addCity(cityE);
        
        // Setup: Create flight F102 with same departure and arrival airport
        Flight flight = new Flight("F102");
        flight.setDepartureAirport(ap05);
        flight.setArrivalAirport(ap05); // Same airport
        flight.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        flight.setPublished(false);
        
        // Setup: Current time = 2025-02-01 09:00:00
        // Note: In actual implementation, we'd mock LocalDateTime.now()
        
        // Execute: Publish flight F102
        boolean result = FlightService.publishFlight(flight);
        
        // Verify: Expected output is False due to same departure and arrival airport
        assertFalse("Flight with same departure and arrival airport should not publish", result);
    }
    
    @Test
    public void testCase4_departureTimeInThePast() {
        // Setup: Create airline AL4
        Airline al4 = new Airline("AL4", "Airline 4");
        
        // Setup: Create airports AP06 (CityF) and AP07 (CityG)
        Airport ap06 = new Airport("AP06", "Airport 06");
        City cityF = new City("CITYF", "CityF");
        ap06.addCity(cityF);
        
        Airport ap07 = new Airport("AP07", "Airport 07");
        City cityG = new City("CITYG", "CityG");
        ap07.addCity(cityG);
        
        // Setup: Create flight F103 with departure in the past relative to current time
        Flight flight = new Flight("F103");
        flight.setDepartureAirport(ap06);
        flight.setArrivalAirport(ap07);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        flight.setPublished(false);
        
        // Setup: Current time = 2025-04-01 09:00:00 (after departure time)
        // Note: In actual implementation, we'd mock LocalDateTime.now()
        
        // Execute: Publish flight F103
        boolean result = FlightService.publishFlight(flight);
        
        // Verify: Expected output is False due to departure time in the past
        assertFalse("Flight with departure time in the past should not publish", result);
    }
    
    @Test
    public void testCase5_secondPublishAttempt() {
        // Setup: Create airline AL5
        Airline al5 = new Airline("AL5", "Airline 5");
        
        // Setup: Create airports AP08 (CityH) and AP09 (CityI)
        Airport ap08 = new Airport("AP08", "Airport 08");
        City cityH = new City("CITYH", "CityH");
        ap08.addCity(cityH);
        
        Airport ap09 = new Airport("AP09", "Airport 09");
        City cityI = new City("CITYI", "CityI");
        ap09.addCity(cityI);
        
        // Setup: Create flight F104 and publish it first
        Flight flight = new Flight("F104");
        flight.setDepartureAirport(ap08);
        flight.setArrivalAirport(ap09);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        flight.setPublished(false);
        
        // First publish attempt (should succeed)
        boolean firstResult = FlightService.publishFlight(flight);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Setup: Current time = 2025-04-01 10:00:00
        // Note: In actual implementation, we'd mock LocalDateTime.now()
        
        // Execute: Second publish attempt for the same flight
        boolean secondResult = FlightService.publishFlight(flight);
        
        // Verify: Expected output is False for second publish attempt
        assertFalse("Second publish attempt should fail", secondResult);
    }
}