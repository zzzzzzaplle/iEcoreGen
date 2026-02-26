import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR1Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() throws Exception {
        // Create airline AL1
        Airline AL1 = new Airline();
        
        // Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport AP01 = new Airport();
        City cityA = new City();
        cityA.setName("CityA");
        AP01.addCity(cityA);
        
        Airport AP02 = new Airport();
        City cityB = new City();
        cityB.setName("CityB");
        AP02.addCity(cityB);
        
        // Create flight F100
        Flight F100 = new Flight();
        F100.setId("F100");
        F100.setDepartureAirport(AP01);
        F100.setArrivalAirport(AP02);
        F100.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        F100.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        
        // Current system time = 2024-12-01 09:00:00
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Publish flight F100 for airline AL1
        boolean result = AL1.publishFlight(F100, currentTime);
        
        // Expected Output: True
        assertTrue("Flight should be successfully published with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws Exception {
        // Create airline AL2
        Airline AL2 = new Airline();
        
        // Create airports AP03 (CityC) and AP04 (CityD)
        Airport AP03 = new Airport();
        City cityC = new City();
        cityC.setName("CityC");
        AP03.addCity(cityC);
        
        Airport AP04 = new Airport();
        City cityD = new City();
        cityD.setName("CityD");
        AP04.addCity(cityD);
        
        // Create flight F101
        Flight F101 = new Flight();
        F101.setId("F101");
        F101.setDepartureAirport(AP03);
        F101.setArrivalAirport(AP04);
        F101.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        F101.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00")); // Departure after arrival
        
        // Current time = 2024-12-15 10:00:00
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Publish flight F101 for airline AL2
        boolean result = AL2.publishFlight(F101, currentTime);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure time is after arrival time", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws Exception {
        // Create airline AL3
        Airline AL3 = new Airline();
        
        // Create airport AP05 (CityE)
        Airport AP05 = new Airport();
        City cityE = new City();
        cityE.setName("CityE");
        AP05.addCity(cityE);
        
        // Create flight F102 with same departure and arrival airport
        Flight F102 = new Flight();
        F102.setId("F102");
        F102.setDepartureAirport(AP05);
        F102.setArrivalAirport(AP05); // Same airport
        
        F102.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        F102.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        
        // Current time = 2025-02-01 09:00:00
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Publish flight F102 for airline AL3
        boolean result = AL3.publishFlight(F102, currentTime);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws Exception {
        // Create airline AL4
        Airline AL4 = new Airline();
        
        // Create airports AP06 (CityF) and AP07 (CityG)
        Airport AP06 = new Airport();
        City cityF = new City();
        cityF.setName("CityF");
        AP06.addCity(cityF);
        
        Airport AP07 = new Airport();
        City cityG = new City();
        cityG.setName("CityG");
        AP07.addCity(cityG);
        
        // Create flight F103
        Flight F103 = new Flight();
        F103.setId("F103");
        F103.setDepartureAirport(AP06);
        F103.setArrivalAirport(AP07);
        F103.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        F103.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        
        // Current time = 2025-04-01 09:00:00 (after departure time)
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        // Publish flight F103 for airline AL4
        boolean result = AL4.publishFlight(F103, currentTime);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure time is in the past relative to current time", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws Exception {
        // Create airline AL5
        Airline AL5 = new Airline();
        
        // Create airports AP08 (CityH) and AP09 (CityI)
        Airport AP08 = new Airport();
        City cityH = new City();
        cityH.setName("CityH");
        AP08.addCity(cityH);
        
        Airport AP09 = new Airport();
        City cityI = new City();
        cityI.setName("CityI");
        AP09.addCity(cityI);
        
        // Create flight F104
        Flight F104 = new Flight();
        F104.setId("F104");
        F104.setDepartureAirport(AP08);
        F104.setArrivalAirport(AP09);
        F104.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        F104.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        
        // Current time = 2025-04-01 10:00:00
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt - should succeed
        boolean firstResult = AL5.publishFlight(F104, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Close the flight for booking to simulate already published status
        F104.setOpenForBooking(false);
        
        // Second publish attempt - should fail
        boolean secondResult = AL5.publishFlight(F104, currentTime);
        
        // Expected Output: False
        assertFalse("Second publish attempt should fail when flight is no longer open for booking", secondResult);
    }
}