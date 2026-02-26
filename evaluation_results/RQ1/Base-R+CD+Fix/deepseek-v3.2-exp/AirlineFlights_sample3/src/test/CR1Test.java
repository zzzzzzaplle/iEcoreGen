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
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap01 = new Airport();
        City cityA = new City();
        cityA.setName("CityA");
        ap01.addCity(cityA);
        
        Airport ap02 = new Airport();
        City cityB = new City();
        cityB.setName("CityB");
        ap02.addCity(cityB);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(ap01);
        flight.setArrivalAirport(ap02);
        flight.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        
        // Current time
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Execute and verify
        boolean result = airline.publishFlight(flight, currentTime);
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap03 = new Airport();
        City cityC = new City();
        cityC.setName("CityC");
        ap03.addCity(cityC);
        
        Airport ap04 = new Airport();
        City cityD = new City();
        cityD.setName("CityD");
        ap04.addCity(cityD);
        
        // Create flight with departure after arrival
        Flight flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(ap03);
        flight.setArrivalAirport(ap04);
        flight.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00"));
        
        // Current time
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Execute and verify
        boolean result = airline.publishFlight(flight, currentTime);
        assertFalse("Flight should not be published when departure is after arrival", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airport
        Airport ap05 = new Airport();
        City cityE = new City();
        cityE.setName("CityE");
        ap05.addCity(cityE);
        
        // Create flight with same departure and arrival airport
        Flight flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(ap05);
        flight.setArrivalAirport(ap05); // Same airport
        flight.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        
        // Current time
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Execute and verify
        boolean result = airline.publishFlight(flight, currentTime);
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap06 = new Airport();
        City cityF = new City();
        cityF.setName("CityF");
        ap06.addCity(cityF);
        
        Airport ap07 = new Airport();
        City cityG = new City();
        cityG.setName("CityG");
        ap07.addCity(cityG);
        
        // Create flight with departure in the past relative to current time
        Flight flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(ap06);
        flight.setArrivalAirport(ap07);
        flight.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        
        // Current time is after departure time
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        // Execute and verify
        boolean result = airline.publishFlight(flight, currentTime);
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap08 = new Airport();
        City cityH = new City();
        cityH.setName("CityH");
        ap08.addCity(cityH);
        
        Airport ap09 = new Airport();
        City cityI = new City();
        cityI.setName("CityI");
        ap09.addCity(cityI);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(ap08);
        flight.setArrivalAirport(ap09);
        flight.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        
        // Current time
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt (should succeed)
        boolean firstResult = airline.publishFlight(flight, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt (should fail)
        boolean secondResult = airline.publishFlight(flight, currentTime);
        assertFalse("Second publish attempt should fail", secondResult);
    }
}