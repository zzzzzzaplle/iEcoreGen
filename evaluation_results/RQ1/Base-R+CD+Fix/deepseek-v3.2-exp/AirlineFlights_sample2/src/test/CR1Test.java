import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR1Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() throws Exception {
        // Create airline AL1
        airline = new Airline();
        
        // Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        ap01.addCity(new City());
        
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        ap02.addCity(new City());
        
        // Create flight F100
        Flight flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(ap01);
        flight.setArrivalAirport(ap02);
        flight.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        
        // Current system time = 2024-12-01 09:00:00
        Date now = dateFormat.parse("2024-12-01 09:00:00");
        
        // Publish flight F100
        boolean result = airline.publishFlight(flight, now);
        
        // Expected Output: True
        assertTrue("Flight should be successfully published with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws Exception {
        // Create airline AL2
        airline = new Airline();
        
        // Create airports AP03 (CityC) and AP04 (CityD)
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        ap03.addCity(new City());
        
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        ap04.addCity(new City());
        
        // Create flight F101
        Flight flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(ap03);
        flight.setArrivalAirport(ap04);
        flight.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00"));
        
        // Current time = 2024-12-15 10:00:00
        Date now = dateFormat.parse("2024-12-15 10:00:00");
        
        // Publish flight F101
        boolean result = airline.publishFlight(flight, now);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure is after arrival", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws Exception {
        // Create airline AL3
        airline = new Airline();
        
        // Create airport AP05 (CityE)
        Airport ap05 = new Airport();
        ap05.setId("AP05");
        ap05.addCity(new City());
        
        // Create flight F102 with same departure and arrival airport
        Flight flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(ap05);
        flight.setArrivalAirport(ap05);
        flight.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        
        // Current time = 2025-02-01 09:00:00
        Date now = dateFormat.parse("2025-02-01 09:00:00");
        
        // Publish flight F102
        boolean result = airline.publishFlight(flight, now);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws Exception {
        // Create airline AL4
        airline = new Airline();
        
        // Create airports AP06 (CityF) and AP07 (CityG)
        Airport ap06 = new Airport();
        ap06.setId("AP06");
        ap06.addCity(new City());
        
        Airport ap07 = new Airport();
        ap07.setId("AP07");
        ap07.addCity(new City());
        
        // Create flight F103
        Flight flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(ap06);
        flight.setArrivalAirport(ap07);
        flight.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        
        // Current time = 2025-04-01 09:00:00 (after departure time)
        Date now = dateFormat.parse("2025-04-01 09:00:00");
        
        // Publish flight F103
        boolean result = airline.publishFlight(flight, now);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws Exception {
        // Create airline AL5
        airline = new Airline();
        
        // Create airports AP08 (CityH) and AP09 (CityI)
        Airport ap08 = new Airport();
        ap08.setId("AP08");
        ap08.addCity(new City());
        
        Airport ap09 = new Airport();
        ap09.setId("AP09");
        ap09.addCity(new City());
        
        // Create flight F104
        Flight flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(ap08);
        flight.setArrivalAirport(ap09);
        flight.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        
        // Current time = 2025-04-01 10:00:00
        Date now = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt - should succeed
        boolean firstResult = airline.publishFlight(flight, now);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt
        boolean secondResult = airline.publishFlight(flight, now);
        
        // Expected Output: False
        assertFalse("Second publish attempt should fail for already published flight", secondResult);
    }
}