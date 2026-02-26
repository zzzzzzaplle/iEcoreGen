import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() throws ParseException {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        
        // Create flight
        Flight f100 = new Flight();
        f100.setId("F100");
        f100.setDepartureAirport(ap01);
        f100.setArrivalAirport(ap02);
        f100.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        f100.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        f100.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(f100);
        
        // Current system time
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Execute and verify
        boolean result = airline.publishFlight(f100, currentTime);
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws ParseException {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        
        // Create flight with departure after arrival (invalid)
        Flight f101 = new Flight();
        f101.setId("F101");
        f101.setDepartureAirport(ap03);
        f101.setArrivalAirport(ap04);
        f101.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        f101.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00")); // Arrival before departure
        f101.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(f101);
        
        // Current system time
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Execute and verify
        boolean result = airline.publishFlight(f101, currentTime);
        assertFalse("Flight should not be published when departure is after arrival", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws ParseException {
        // Setup
        airline = new Airline();
        
        // Create airport
        Airport ap05 = new Airport();
        ap05.setId("AP05");
        
        // Create flight with same departure and arrival airport
        Flight f102 = new Flight();
        f102.setId("F102");
        f102.setDepartureAirport(ap05);
        f102.setArrivalAirport(ap05); // Same airport
        f102.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        f102.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        f102.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(f102);
        
        // Current system time
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Execute and verify
        boolean result = airline.publishFlight(f102, currentTime);
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws ParseException {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap06 = new Airport();
        ap06.setId("AP06");
        Airport ap07 = new Airport();
        ap07.setId("AP07");
        
        // Create flight with departure time in the past relative to current time
        Flight f103 = new Flight();
        f103.setId("F103");
        f103.setDepartureAirport(ap06);
        f103.setArrivalAirport(ap07);
        f103.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        f103.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        f103.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(f103);
        
        // Current system time is AFTER the departure time
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        // Execute and verify
        boolean result = airline.publishFlight(f103, currentTime);
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws ParseException {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap08 = new Airport();
        ap08.setId("AP08");
        Airport ap09 = new Airport();
        ap09.setId("AP09");
        
        // Create flight that has already been published
        Flight f104 = new Flight();
        f104.setId("F104");
        f104.setDepartureAirport(ap08);
        f104.setArrivalAirport(ap09);
        f104.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        f104.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        f104.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(f104);
        
        // Current system time
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt (should succeed)
        boolean firstResult = airline.publishFlight(f104, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt (should fail)
        boolean secondResult = airline.publishFlight(f104, currentTime);
        assertFalse("Second publish attempt should fail", secondResult);
    }
}