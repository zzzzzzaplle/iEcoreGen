import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class CR1Test {
    
    private Airline airline;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airline = new Airline();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Create airline AL1
        Airline al1 = new Airline();
        
        // Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport ap01 = new Airport("AP01");
        City cityA = new City("CityA");
        ap01.addCity(cityA);
        
        Airport ap02 = new Airport("AP02");
        City cityB = new City("CityB");
        ap02.addCity(cityB);
        
        // Create flight F100
        Flight f100 = new Flight("F100");
        f100.setDepartureAirport(ap01);
        f100.setArrivalAirport(ap02);
        f100.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        f100.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        // Current system time = 2024-12-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 09:00:00", formatter);
        
        // Publish flight F100 for airline AL1
        boolean result = al1.publishFlight(f100, currentTime);
        
        // Expected Output: True
        assertTrue("Flight should be published successfully with correct schedule and route", result);
        assertTrue("Flight should be open for booking after successful publication", f100.isOpenForBooking());
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Create airline AL2
        Airline al2 = new Airline();
        
        // Create airports AP03 (CityC) and AP04 (CityD)
        Airport ap03 = new Airport("AP03");
        City cityC = new City("CityC");
        ap03.addCity(cityC);
        
        Airport ap04 = new Airport("AP04");
        City cityD = new City("CityD");
        ap04.addCity(cityD);
        
        // Create flight F101
        Flight f101 = new Flight("F101");
        f101.setDepartureAirport(ap03);
        f101.setArrivalAirport(ap04);
        f101.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        f101.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter)); // Arrival before departure
        
        // Current time = 2024-12-15 10:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-15 10:00:00", formatter);
        
        // Publish flight F101 for airline AL2
        boolean result = al2.publishFlight(f101, currentTime);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure time is after arrival time", result);
        assertFalse("Flight should remain closed for booking after failed publication", f101.isOpenForBooking());
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Create airline AL3
        Airline al3 = new Airline();
        
        // Create airport AP05 (CityE)
        Airport ap05 = new Airport("AP05");
        City cityE = new City("CityE");
        ap05.addCity(cityE);
        
        // Create flight F102 with same departure and arrival airport
        Flight f102 = new Flight("F102");
        f102.setDepartureAirport(ap05);
        f102.setArrivalAirport(ap05); // Same airport
        f102.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        f102.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        // Current time = 2025-02-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-02-01 09:00:00", formatter);
        
        // Publish flight F102 for airline AL3
        boolean result = al3.publishFlight(f102, currentTime);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
        assertFalse("Flight should remain closed for booking after failed publication", f102.isOpenForBooking());
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Create airline AL4
        Airline al4 = new Airline();
        
        // Create airports AP06 (CityF) and AP07 (CityG)
        Airport ap06 = new Airport("AP06");
        City cityF = new City("CityF");
        ap06.addCity(cityF);
        
        Airport ap07 = new Airport("AP07");
        City cityG = new City("CityG");
        ap07.addCity(cityG);
        
        // Create flight F103
        Flight f103 = new Flight("F103");
        f103.setDepartureAirport(ap06);
        f103.setArrivalAirport(ap07);
        f103.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        f103.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        
        // Current time = 2025-04-01 09:00:00 (after departure time)
        LocalDateTime currentTime = LocalDateTime.parse("2025-04-01 09:00:00", formatter);
        
        // Publish flight F103 for airline AL4
        boolean result = al4.publishFlight(f103, currentTime);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure time is in the past", result);
        assertFalse("Flight should remain closed for booking after failed publication", f103.isOpenForBooking());
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Create airline AL5
        Airline al5 = new Airline();
        
        // Create airports AP08 (CityH) and AP09 (CityI)
        Airport ap08 = new Airport("AP08");
        City cityH = new City("CityH");
        ap08.addCity(cityH);
        
        Airport ap09 = new Airport("AP09");
        City cityI = new City("CityI");
        ap09.addCity(cityI);
        
        // Create flight F104
        Flight f104 = new Flight("F104");
        f104.setDepartureAirport(ap08);
        f104.setArrivalAirport(ap09);
        f104.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        f104.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        
        // Current time = 2025-04-01 10:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-04-01 10:00:00", formatter);
        
        // First publish attempt - should succeed
        boolean firstResult = al5.publishFlight(f104, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        assertTrue("Flight should be open for booking after first successful publication", f104.isOpenForBooking());
        
        // Second publish attempt - should fail
        boolean secondResult = al5.publishFlight(f104, currentTime);
        
        // Expected Output: False for second attempt
        assertFalse("Second publish attempt should fail when flight is already open for booking", secondResult);
        assertTrue("Flight should remain open for booking after second publication attempt", f104.isOpenForBooking());
    }
}