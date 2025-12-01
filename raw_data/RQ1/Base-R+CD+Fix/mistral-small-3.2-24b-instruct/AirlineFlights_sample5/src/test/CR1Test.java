import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
        // Create airline AL1
        Airline al1 = new Airline();
        
        // Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport ap01 = new Airport();
        City cityA = new City();
        cityA.setName("CityA");
        ap01.addCity(cityA);
        
        Airport ap02 = new Airport();
        City cityB = new City();
        cityB.setName("CityB");
        ap02.addCity(cityB);
        
        // Create flight F100
        Flight f100 = new Flight();
        f100.setId("F100");
        f100.setDepartureAirport(ap01);
        f100.setArrialAirport(ap02);
        f100.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        f100.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        f100.setOpenForBooking(true);
        
        // Current system time = 2024-12-01 09:00:00
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Add flight to airline
        al1.addFlight(f100);
        
        // Publish flight and verify result
        boolean result = al1.publishFlight(f100, currentTime);
        assertTrue("Flight with correct schedule and route should publish successfully", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws Exception {
        // Create airline AL2
        Airline al2 = new Airline();
        
        // Create airports AP03 (CityC) and AP04 (CityD)
        Airport ap03 = new Airport();
        City cityC = new City();
        cityC.setName("CityC");
        ap03.addCity(cityC);
        
        Airport ap04 = new Airport();
        City cityD = new City();
        cityD.setName("CityD");
        ap04.addCity(cityD);
        
        // Create flight F101 with departure after arrival
        Flight f101 = new Flight();
        f101.setId("F101");
        f101.setDepartureAirport(ap03);
        f101.setArrialAirport(ap04);
        f101.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        f101.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00")); // Departure after arrival
        f101.setOpenForBooking(true);
        
        // Current time = 2024-12-15 10:00:00
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Add flight to airline
        al2.addFlight(f101);
        
        // Publish flight and verify result
        boolean result = al2.publishFlight(f101, currentTime);
        assertFalse("Flight with departure after arrival should not publish", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws Exception {
        // Create airline AL3
        Airline al3 = new Airline();
        
        // Create airport AP05 (CityE)
        Airport ap05 = new Airport();
        City cityE = new City();
        cityE.setName("CityE");
        ap05.addCity(cityE);
        
        // Create flight F102 with same departure and arrival airport
        Flight f102 = new Flight();
        f102.setId("F102");
        f102.setDepartureAirport(ap05);
        f102.setArrialAirport(ap05); // Same airport
        f102.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        f102.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        f102.setOpenForBooking(true);
        
        // Current time = 2025-02-01 09:00:00
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Add flight to airline
        al3.addFlight(f102);
        
        // Publish flight and verify result
        boolean result = al3.publishFlight(f102, currentTime);
        assertFalse("Flight with same departure and arrival airport should not publish", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws Exception {
        // Create airline AL4
        Airline al4 = new Airline();
        
        // Create airports AP06 (CityF) and AP07 (CityG)
        Airport ap06 = new Airport();
        City cityF = new City();
        cityF.setName("CityF");
        ap06.addCity(cityF);
        
        Airport ap07 = new Airport();
        City cityG = new City();
        cityG.setName("CityG");
        ap07.addCity(cityG);
        
        // Create flight F103 with departure time in the past
        Flight f103 = new Flight();
        f103.setId("F103");
        f103.setDepartureAirport(ap06);
        f103.setArrialAirport(ap07);
        f103.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        f103.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        f103.setOpenForBooking(true);
        
        // Current time = 2025-04-01 09:00:00 (after departure time)
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        // Add flight to airline
        al4.addFlight(f103);
        
        // Publish flight and verify result
        boolean result = al4.publishFlight(f103, currentTime);
        assertFalse("Flight with departure time in the past should not publish", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws Exception {
        // Create airline AL5
        Airline al5 = new Airline();
        
        // Create airports AP08 (CityH) and AP09 (CityI)
        Airport ap08 = new Airport();
        City cityH = new City();
        cityH.setName("CityH");
        ap08.addCity(cityH);
        
        Airport ap09 = new Airport();
        City cityI = new City();
        cityI.setName("CityI");
        ap09.addCity(cityI);
        
        // Create flight F104
        Flight f104 = new Flight();
        f104.setId("F104");
        f104.setDepartureAirport(ap08);
        f104.setArrialAirport(ap09);
        f104.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        f104.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        f104.setOpenForBooking(true);
        
        // Current time = 2025-04-01 10:00:00
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // Add flight to airline
        al5.addFlight(f104);
        
        // First publish attempt (should succeed)
        boolean firstResult = al5.publishFlight(f104, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt (should fail)
        boolean secondResult = al5.publishFlight(f104, currentTime);
        assertFalse("Second publish attempt should fail", result);
    }
}