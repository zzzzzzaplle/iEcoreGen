import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Create airline AL1 (not explicitly modeled, but implied)
        // Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        ap01.setCities(List.of("CityA"));
        
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        ap02.setCities(List.of("CityB"));
        
        // Create flight F100 with valid schedule
        Flight flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(ap01);
        flight.setArrivalAirport(ap02);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        flight.setOpen(true);
        
        // Current system time = 2024-12-01 09:00:00 (implicit in the test logic)
        // Expected Output: True
        assertTrue("Flight should publish successfully with correct schedule and route", 
                   flight.publish());
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Create airline AL2
        // Create airports AP03 (CityC) and AP04 (CityD)
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        ap03.setCities(List.of("CityC"));
        
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        ap04.setCities(List.of("CityD"));
        
        // Create flight F101 with departure after arrival
        Flight flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(ap03);
        flight.setArrivalAirport(ap04);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        flight.setOpen(true);
        
        // Current time = 2024-12-15 10:00:00 (implicit in the test logic)
        // Expected Output: False
        assertFalse("Flight should not publish when departure is after arrival", 
                    flight.publish());
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Create airline AL3
        // Create airport AP05 (CityE)
        Airport ap05 = new Airport();
        ap05.setId("AP05");
        ap05.setCities(List.of("CityE"));
        
        // Create flight F102 with same departure and arrival airport
        Flight flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(ap05);
        flight.setArrivalAirport(ap05); // Same airport
        flight.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        flight.setOpen(true);
        
        // Current time = 2025-02-01 09:00:00 (implicit in the test logic)
        // Expected Output: False
        assertFalse("Flight should not publish when departure and arrival airports are the same", 
                    flight.publish());
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Create airline AL4
        // Create airports AP06 (CityF) and AP07 (CityG)
        Airport ap06 = new Airport();
        ap06.setId("AP06");
        ap06.setCities(List.of("CityF"));
        
        Airport ap07 = new Airport();
        ap07.setId("AP07");
        ap07.setCities(List.of("CityG"));
        
        // Create flight F103 with departure in the past relative to current time
        Flight flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(ap06);
        flight.setArrivalAirport(ap07);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        flight.setOpen(true);
        
        // Current time = 2025-04-01 09:00:00 (implicit in the test logic)
        // Expected Output: False
        assertFalse("Flight should not publish when departure time is in the past", 
                    flight.publish());
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Create airline AL5
        // Create airports AP08 (CityH) and AP09 (CityI)
        Airport ap08 = new Airport();
        ap08.setId("AP08");
        ap08.setCities(List.of("CityH"));
        
        Airport ap09 = new Airport();
        ap09.setId("AP09");
        ap09.setCities(List.of("CityI"));
        
        // Create flight F104 and publish it successfully first
        Flight flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(ap08);
        flight.setArrivalAirport(ap09);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        flight.setOpen(true);
        
        // First publish attempt should succeed
        assertTrue("First publish attempt should succeed", flight.publish());
        
        // Second publish attempt
        // Current time = 2025-04-01 10:00:00 (implicit in the test logic)
        // Expected Output: False
        assertFalse("Second publish attempt should fail", flight.publish());
    }
}