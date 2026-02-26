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
        // Setup
        Airline AL1 = new Airline();
        
        Airport AP01 = new Airport();
        City cityA = new City();
        cityA.setName("CityA");
        AP01.addCity(cityA);
        
        Airport AP02 = new Airport();
        City cityB = new City();
        cityB.setName("CityB");
        AP02.addCity(cityB);
        
        Flight F100 = new Flight();
        F100.setId("F100");
        F100.setDepartureAirport(AP01);
        F100.setArrivalAirport(AP02);
        F100.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        F100.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        F100.setOpenForBooking(true); //应该set false
        
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Execute
        boolean result = AL1.publishFlight(F100, currentTime);
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_departureAfterArrival() throws Exception {
        // Setup
        Airline AL2 = new Airline();
        
        Airport AP03 = new Airport();
        City cityC = new City();
        cityC.setName("CityC");
        AP03.addCity(cityC);
        
        Airport AP04 = new Airport();
        City cityD = new City();
        cityD.setName("CityD");
        AP04.addCity(cityD);
        
        Flight F101 = new Flight();
        F101.setId("F101");
        F101.setDepartureAirport(AP03);
        F101.setArrivalAirport(AP04);
        F101.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        F101.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00"));
        F101.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Execute
        boolean result = AL2.publishFlight(F101, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is after arrival time", result);
    }
    
    @Test
    public void testCase3_sameDepartureAndArrivalAirport() throws Exception {
        // Setup
        Airline AL3 = new Airline();
        
        Airport AP05 = new Airport();
        City cityE = new City();
        cityE.setName("CityE");
        AP05.addCity(cityE);
        
        Flight F102 = new Flight();
        F102.setId("F102");
        F102.setDepartureAirport(AP05);
        F102.setArrivalAirport(AP05); // Same airport
        F102.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        F102.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        F102.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Execute
        boolean result = AL3.publishFlight(F102, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_departureTimeInThePast() throws Exception {
        // Setup
        Airline AL4 = new Airline();
        
        Airport AP06 = new Airport();
        City cityF = new City();
        cityF.setName("CityF");
        AP06.addCity(cityF);
        
        Airport AP07 = new Airport();
        City cityG = new City();
        cityG.setName("CityG");
        AP07.addCity(cityG);
        
        Flight F103 = new Flight();
        F103.setId("F103");
        F103.setDepartureAirport(AP06);
        F103.setArrivalAirport(AP07);
        F103.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        F103.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        F103.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00"); // After departure time
        
        // Execute
        boolean result = AL4.publishFlight(F103, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_secondPublishAttempt() throws Exception {
        // Setup
        Airline AL5 = new Airline();
        
        Airport AP08 = new Airport();
        City cityH = new City();
        cityH.setName("CityH");
        AP08.addCity(cityH);
        
        Airport AP09 = new Airport();
        City cityI = new City();
        cityI.setName("CityI");
        AP09.addCity(cityI);
        
        Flight F104 = new Flight();
        F104.setId("F104");
        F104.setDepartureAirport(AP08);
        F104.setArrivalAirport(AP09);
        F104.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        F104.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        F104.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt (should succeed)
        boolean firstResult = AL5.publishFlight(F104, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt
        boolean secondResult = AL5.publishFlight(F104, currentTime);
        
        // Verify
        assertFalse("Second publish attempt should fail", secondResult);
    }
}