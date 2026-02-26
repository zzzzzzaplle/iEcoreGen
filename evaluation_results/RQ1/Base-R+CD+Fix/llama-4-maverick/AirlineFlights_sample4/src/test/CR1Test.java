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
        // Create airline AL1
        Airline al1 = new Airline();
        
        // Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        ap01.addCity(new City());
        
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        ap02.addCity(new City());
        
        // Create flight F100
        Flight f100 = new Flight();
        f100.setId("F100");
        f100.setDepartureAirport(ap01);
        f100.setArrivalAirport(ap02);
        f100.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        f100.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        f100.setOpenForBooking(true);
        
        // Current system time
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Test publish flight
        boolean result = al1.publishFlight(f100, currentTime);
        
        // Verify expected output
        assertTrue("Flight with correct schedule and route should be publishable", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws Exception {
        // Create airline AL2
        Airline al2 = new Airline();
        
        // Create airports AP03 (CityC) and AP04 (CityD)
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        ap03.addCity(new City());
        
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        ap04.addCity(new City());
        
        // Create flight F101 with departure after arrival
        Flight f101 = new Flight();
        f101.setId("F101");
        f101.setDepartureAirport(ap03);
        f101.setArrivalAirport(ap04);
        f101.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        f101.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00")); // Arrival before departure
        f101.setOpenForBooking(true);
        
        // Current time
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Test publish flight
        boolean result = al2.publishFlight(f101, currentTime);
        
        // Verify expected output
        assertFalse("Flight with departure after arrival should not be publishable", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws Exception {
        // Create airline AL3
        Airline al3 = new Airline();
        
        // Create airport AP05 (CityE)
        Airport ap05 = new Airport();
        ap05.setId("AP05");
        ap05.addCity(new City());
        
        // Create flight F102 with same departure and arrival airport
        Flight f102 = new Flight();
        f102.setId("F102");
        f102.setDepartureAirport(ap05);
        f102.setArrivalAirport(ap05); // Same airport
        f102.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        f102.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        f102.setOpenForBooking(true);
        
        // Current time
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Test publish flight
        boolean result = al3.publishFlight(f102, currentTime);
        
        // Verify expected output
        assertFalse("Flight with same departure and arrival airport should not be publishable", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws Exception {
        // Create airline AL4
        Airline al4 = new Airline();
        
        // Create airports AP06 (CityF) and AP07 (CityG)
        Airport ap06 = new Airport();
        ap06.setId("AP06");
        ap06.addCity(new City());
        
        Airport ap07 = new Airport();
        ap07.setId("AP07");
        ap07.addCity(new City());
        
        // Create flight F103 with departure in the past relative to current time
        Flight f103 = new Flight();
        f103.setId("F103");
        f103.setDepartureAirport(ap06);
        f103.setArrivalAirport(ap07);
        f103.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        f103.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        f103.setOpenForBooking(true);
        
        // Current time is after departure time
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        // Test publish flight
        boolean result = al4.publishFlight(f103, currentTime);
        
        // Verify expected output
        assertFalse("Flight with departure time in the past should not be publishable", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws Exception {
        // Create airline AL5
        Airline al5 = new Airline();
        
        // Create airports AP08 (CityH) and AP09 (CityI)
        Airport ap08 = new Airport();
        ap08.setId("AP08");
        ap08.addCity(new City());
        
        Airport ap09 = new Airport();
        ap09.setId("AP09");
        ap09.addCity(new City());
        
        // Create flight F104
        Flight f104 = new Flight();
        f104.setId("F104");
        f104.setDepartureAirport(ap08);
        f104.setArrivalAirport(ap09);
        f104.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        f104.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        f104.setOpenForBooking(true);
        
        // Current time
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt (should succeed)
        boolean firstAttempt = al5.publishFlight(f104, currentTime);
        assertTrue("First publish attempt should succeed", firstAttempt);
        
        // Close flight for booking to simulate already published status
        f104.setOpenForBooking(false);
        
        // Second publish attempt
        boolean secondAttempt = al5.publishFlight(f104, currentTime);
        
        // Verify expected output
        assertFalse("Second publish attempt should fail as flight is no longer open for booking", secondAttempt);
    }
}