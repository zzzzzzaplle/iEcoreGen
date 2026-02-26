import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        // Setup
        // 1. Create airline AL1
        Airline AL1 = new Airline();
        
        // 2. Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport AP01 = new Airport();
        City CityA = new City("CityA");
        AP01.addCity(CityA);
        
        Airport AP02 = new Airport();
        City CityB = new City("CityB");
        AP02.addCity(CityB);
        
        // 3. Create flight F100
        Flight F100 = new Flight();
        F100.setId("F100");
        F100.setDepartureAirport(AP01);
        F100.setArrialAirport(AP02);
        F100.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        F100.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        
        // 4. Current system time = 2024-12-01 09:00:00
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Test
        boolean result = AL1.publishFlight(F100, currentTime);
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
        assertTrue("Flight should be open for booking after successful publication", F100.isOpenForBooking());
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws Exception {
        // Setup
        // 1. Airline AL2; airports AP03 (CityC) and AP04 (CityD)
        Airline AL2 = new Airline();
        
        Airport AP03 = new Airport();
        City CityC = new City("CityC");
        AP03.addCity(CityC);
        
        Airport AP04 = new Airport();
        City CityD = new City("CityD");
        AP04.addCity(CityD);
        
        // 2. Flight F101: AP03 ➜ AP04
        Flight F101 = new Flight();
        F101.setId("F101");
        F101.setDepartureAirport(AP03);
        F101.setArrialAirport(AP04);
        F101.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        F101.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00")); // Departure after arrival
        
        // 3. Current time = 2024-12-15 10:00:00
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Test
        boolean result = AL2.publishFlight(F101, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is after arrival time", result);
        assertFalse("Flight should remain closed for booking after failed publication", F101.isOpenForBooking());
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws Exception {
        // Setup
        // 1. Airline AL3; airport AP05 (CityE)
        Airline AL3 = new Airline();
        
        Airport AP05 = new Airport();
        City CityE = new City("CityE");
        AP05.addCity(CityE);
        
        // 2. Flight F102: AP05 ➜ AP05
        Flight F102 = new Flight();
        F102.setId("F102");
        F102.setDepartureAirport(AP05);
        F102.setArrialAirport(AP05); // Same departure and arrival airport
        F102.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        F102.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        
        // 3. Current time = 2025-02-01 09:00:00
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Test
        boolean result = AL3.publishFlight(F102, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
        assertFalse("Flight should remain closed for booking after failed publication", F102.isOpenForBooking());
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws Exception {
        // Setup
        // 1. Airline AL4; airports AP06 (CityF) and AP07 (CityG)
        Airline AL4 = new Airline();
        
        Airport AP06 = new Airport();
        City CityF = new City("CityF");
        AP06.addCity(CityF);
        
        Airport AP07 = new Airport();
        City CityG = new City("CityG");
        AP07.addCity(CityG);
        
        // 2. Flight F103
        Flight F103 = new Flight();
        F103.setId("F103");
        F103.setDepartureAirport(AP06);
        F103.setArrialAirport(AP07);
        F103.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        F103.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        
        // 3. Current time = 2025-04-01 09:00:00 (after departure time)
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        // Test
        boolean result = AL4.publishFlight(F103, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past", result);
        assertFalse("Flight should remain closed for booking after failed publication", F103.isOpenForBooking());
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws Exception {
        // Setup
        // 1. Airline AL5; airports AP08 (CityH) and AP09 (CityI)
        Airline AL5 = new Airline();
        
        Airport AP08 = new Airport();
        City CityH = new City("CityH");
        AP08.addCity(CityH);
        
        Airport AP09 = new Airport();
        City CityI = new City("CityI");
        AP09.addCity(CityI);
        
        // 2. Flight F104 already published successfully
        Flight F104 = new Flight();
        F104.setId("F104");
        F104.setDepartureAirport(AP08);
        F104.setArrialAirport(AP09);
        F104.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        F104.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        
        // First successful publication
        Date firstPublishTime = dateFormat.parse("2025-04-01 09:00:00");
        boolean firstResult = AL5.publishFlight(F104, firstPublishTime);
        assertTrue("First publication should succeed", firstResult);
        assertTrue("Flight should be open for booking after first publication", F104.isOpenForBooking());
        
        // 3. Current time = 2025-04-01 10:00:00
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // Test second publication attempt
        boolean secondResult = AL5.publishFlight(F104, currentTime);
        
        // Verify
        assertFalse("Second publication attempt should fail", secondResult);
        assertTrue("Flight should remain open for booking after second publication attempt", F104.isOpenForBooking());
    }
}