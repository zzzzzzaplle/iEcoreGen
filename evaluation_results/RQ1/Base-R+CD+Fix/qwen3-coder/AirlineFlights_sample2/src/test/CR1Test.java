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
    public void testCase1_correctScheduleAndRoute() throws Exception {
        // Create airline AL1
        Airline AL1 = new Airline();
        
        // Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport AP01 = new Airport();
        AP01.setId("AP01");
        City cityA = new City();
        AP01.addCity(cityA);
        
        Airport AP02 = new Airport();
        AP02.setId("AP02");
        City cityB = new City();
        AP02.addCity(cityB);
        
        // Create flight F100
        Flight F100 = new Flight();
        F100.setId("F100");
        F100.setDepartureAirport(AP01);
        F100.setArrivalAirport(AP02);
        F100.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        F100.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        F100.setOpenForBooking(true);
        
        // Current system time = 2024-12-01 09:00:00
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Publish flight F100 for airline AL1
        boolean result = AL1.publishFlight(F100, currentTime);
        
        // Expected Output: True
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_departureAfterArrival() throws Exception {
        // Create airline AL2
        Airline AL2 = new Airline();
        
        // Create airports AP03 (CityC) and AP04 (CityD)
        Airport AP03 = new Airport();
        AP03.setId("AP03");
        City cityC = new City();
        AP03.addCity(cityC);
        
        Airport AP04 = new Airport();
        AP04.setId("AP04");
        City cityD = new City();
        AP04.addCity(cityD);
        
        // Create flight F101
        Flight F101 = new Flight();
        F101.setId("F101");
        F101.setDepartureAirport(AP03);
        F101.setArrivalAirport(AP04);
        F101.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        F101.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00"));
        F101.setOpenForBooking(true);
        
        // Current time = 2024-12-15 10:00:00
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Publish flight F101 for airline AL2
        boolean result = AL2.publishFlight(F101, currentTime);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure is after arrival", result);
    }
    
    @Test
    public void testCase3_sameDepartureAndArrivalAirport() throws Exception {
        // Create airline AL3
        Airline AL3 = new Airline();
        
        // Create airport AP05 (CityE)
        Airport AP05 = new Airport();
        AP05.setId("AP05");
        City cityE = new City();
        AP05.addCity(cityE);
        
        // Create flight F102 with same departure and arrival airport
        Flight F102 = new Flight();
        F102.setId("F102");
        F102.setDepartureAirport(AP05);
        F102.setArrivalAirport(AP05);
        F102.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        F102.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        F102.setOpenForBooking(true);
        
        // Current time = 2025-02-01 09:00:00
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Publish flight F102 for airline AL3
        boolean result = AL3.publishFlight(F102, currentTime);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_departureTimeInThePast() throws Exception {
        // Create airline AL4
        Airline AL4 = new Airline();
        
        // Create airports AP06 (CityF) and AP07 (CityG)
        Airport AP06 = new Airport();
        AP06.setId("AP06");
        City cityF = new City();
        AP06.addCity(cityF);
        
        Airport AP07 = new Airport();
        AP07.setId("AP07");
        City cityG = new City();
        AP07.addCity(cityG);
        
        // Create flight F103
        Flight F103 = new Flight();
        F103.setId("F103");
        F103.setDepartureAirport(AP06);
        F103.setArrivalAirport(AP07);
        F103.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        F103.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        F103.setOpenForBooking(true);
        
        // Current time = 2025-04-01 09:00:00 (after departure time)
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        // Publish flight F103 for airline AL4
        boolean result = AL4.publishFlight(F103, currentTime);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_secondPublishAttempt() throws Exception {
        // Create airline AL5
        Airline AL5 = new Airline();
        
        // Create airports AP08 (CityH) and AP09 (CityI)
        Airport AP08 = new Airport();
        AP08.setId("AP08");
        City cityH = new City();
        AP08.addCity(cityH);
        
        Airport AP09 = new Airport();
        AP09.setId("AP09");
        City cityI = new City();
        AP09.addCity(cityI);
        
        // Create flight F104
        Flight F104 = new Flight();
        F104.setId("F104");
        F104.setDepartureAirport(AP08);
        F104.setArrivalAirport(AP09);
        F104.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        F104.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        F104.setOpenForBooking(true);
        
        // Current time = 2025-04-01 10:00:00
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt - should succeed
        boolean firstResult = AL5.publishFlight(F104, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Flight F104 already published successfully, now attempt second publish
        boolean secondResult = AL5.publishFlight(F104, currentTime);
        
        // Expected Output: False
        assertFalse("Second publish attempt should fail", secondResult);
    }
}