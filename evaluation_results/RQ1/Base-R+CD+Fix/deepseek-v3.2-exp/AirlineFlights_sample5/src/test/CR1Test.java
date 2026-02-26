import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR1Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() throws ParseException {
        // Setup
        Airport ap01 = new Airport();
        City cityA = new City();
        cityA.setName("CityA");
        ap01.addCity(cityA);
        
        Airport ap02 = new Airport();
        City cityB = new City();
        cityB.setName("CityB");
        ap02.addCity(cityB);
        
        Flight f100 = new Flight();
        f100.setId("F100");
        f100.setDepartureAirport(ap01);
        f100.setArrivalAirport(ap02);
        f100.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        f100.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Execute
        boolean result = airline.publishFlight(f100, currentTime);
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
        assertTrue("Flight should be open for booking after successful publication", f100.isOpenForBooking());
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws ParseException {
        // Setup
        Airline al2 = new Airline();
        Airport ap03 = new Airport();
        City cityC = new City();
        cityC.setName("CityC");
        ap03.addCity(cityC);
        
        Airport ap04 = new Airport();
        City cityD = new City();
        cityD.setName("CityD");
        ap04.addCity(cityD);
        
        Flight f101 = new Flight();
        f101.setId("F101");
        f101.setDepartureAirport(ap03);
        f101.setArrivalAirport(ap04);
        f101.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        f101.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00"));
        
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Execute
        boolean result = al2.publishFlight(f101, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure is after arrival", result);
        assertFalse("Flight should remain closed for booking", f101.isOpenForBooking());
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws ParseException {
        // Setup
        Airline al3 = new Airline();
        Airport ap05 = new Airport();
        City cityE = new City();
        cityE.setName("CityE");
        ap05.addCity(cityE);
        
        Flight f102 = new Flight();
        f102.setId("F102");
        f102.setDepartureAirport(ap05);
        f102.setArrivalAirport(ap05); // Same airport
        f102.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        f102.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Execute
        boolean result = al3.publishFlight(f102, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
        assertFalse("Flight should remain closed for booking", f102.isOpenForBooking());
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws ParseException {
        // Setup
        Airline al4 = new Airline();
        Airport ap06 = new Airport();
        City cityF = new City();
        cityF.setName("CityF");
        ap06.addCity(cityF);
        
        Airport ap07 = new Airport();
        City cityG = new City();
        cityG.setName("CityG");
        ap07.addCity(cityG);
        
        Flight f103 = new Flight();
        f103.setId("F103");
        f103.setDepartureAirport(ap06);
        f103.setArrivalAirport(ap07);
        f103.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        f103.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00"); // After departure time
        
        // Execute
        boolean result = al4.publishFlight(f103, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past", result);
        assertFalse("Flight should remain closed for booking", f103.isOpenForBooking());
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws ParseException {
        // Setup
        Airline al5 = new Airline();
        Airport ap08 = new Airport();
        City cityH = new City();
        cityH.setName("CityH");
        ap08.addCity(cityH);
        
        Airport ap09 = new Airport();
        City cityI = new City();
        cityI.setName("CityI");
        ap09.addCity(cityI);
        
        Flight f104 = new Flight();
        f104.setId("F104");
        f104.setDepartureAirport(ap08);
        f104.setArrivalAirport(ap09);
        f104.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        f104.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt (should succeed)
        boolean firstResult = al5.publishFlight(f104, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Execute second publish attempt
        boolean secondResult = al5.publishFlight(f104, currentTime);
        
        // Verify
        assertFalse("Second publish attempt should fail", secondResult);
        assertTrue("Flight should remain open for booking after first successful publication", f104.isOpenForBooking());
    }
}