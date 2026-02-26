import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR1Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        airline = new Airline();
    }
    
    @Test
    public void testCase1_correctScheduleAndRoute() throws ParseException {
        // Setup
        Airline AL1 = new Airline();
        
        Airport AP01 = new Airport();
        City CityA = new City();
        CityA.setName("CityA");
        AP01.addCity(CityA);
        
        Airport AP02 = new Airport();
        City CityB = new City();
        CityB.setName("CityB");
        AP02.addCity(CityB);
        
        Flight F100 = new Flight();
        F100.setId("F100");
        F100.setDepartureAirport(AP01);
        F100.setArrivalAirport(AP02);
        F100.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        F100.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Execute
        boolean result = AL1.publishFlight(F100, currentTime);
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_departureAfterArrival() throws ParseException {
        // Setup
        Airline AL2 = new Airline();
        
        Airport AP03 = new Airport();
        City CityC = new City();
        CityC.setName("CityC");
        AP03.addCity(CityC);
        
        Airport AP04 = new Airport();
        City CityD = new City();
        CityD.setName("CityD");
        AP04.addCity(CityD);
        
        Flight F101 = new Flight();
        F101.setId("F101");
        F101.setDepartureAirport(AP03);
        F101.setArrivalAirport(AP04);
        F101.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        F101.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00"));
        
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Execute
        boolean result = AL2.publishFlight(F101, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is after arrival time", result);
    }
    
    @Test
    public void testCase3_sameDepartureAndArrivalAirport() throws ParseException {
        // Setup
        Airline AL3 = new Airline();
        
        Airport AP05 = new Airport();
        City CityE = new City();
        CityE.setName("CityE");
        AP05.addCity(CityE);
        
        Flight F102 = new Flight();
        F102.setId("F102");
        F102.setDepartureAirport(AP05);
        F102.setArrivalAirport(AP05);
        F102.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        F102.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Execute
        boolean result = AL3.publishFlight(F102, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_departureTimeInThePast() throws ParseException {
        // Setup
        Airline AL4 = new Airline();
        
        Airport AP06 = new Airport();
        City CityF = new City();
        CityF.setName("CityF");
        AP06.addCity(CityF);
        
        Airport AP07 = new Airport();
        City CityG = new City();
        CityG.setName("CityG");
        AP07.addCity(CityG);
        
        Flight F103 = new Flight();
        F103.setId("F103");
        F103.setDepartureAirport(AP06);
        F103.setArrivalAirport(AP07);
        F103.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        F103.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        // Execute
        boolean result = AL4.publishFlight(F103, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_secondPublishAttempt() throws ParseException {
        // Setup
        Airline AL5 = new Airline();
        
        Airport AP08 = new Airport();
        City CityH = new City();
        CityH.setName("CityH");
        AP08.addCity(CityH);
        
        Airport AP09 = new Airport();
        City CityI = new City();
        CityI.setName("CityI");
        AP09.addCity(CityI);
        
        Flight F104 = new Flight();
        F104.setId("F104");
        F104.setDepartureAirport(AP08);
        F104.setArrivalAirport(AP09);
        F104.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        F104.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt (should succeed)
        boolean firstResult = AL5.publishFlight(F104, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Execute second publish attempt
        boolean secondResult = AL5.publishFlight(F104, currentTime);
        
        // Verify
        assertFalse("Second publish attempt should fail", secondResult);
    }
}