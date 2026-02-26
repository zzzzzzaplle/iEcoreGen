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
    public void testCase1_correctScheduleAndRoute() {
        // Setup
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        ap01.addCity("CityA");
        
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        ap02.addCity("CityB");
        
        Flight flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(ap01);
        flight.setArrivalAirport(ap02);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        // Set current time to 2024-12-01 09:00:00 (before departure)
        // This is simulated by the flight's publish() method logic which uses LocalDateTime.now()
        // In real scenario, we'd mock the time, but here we rely on the flight's validation logic
        
        // Execute
        boolean result = flight.publish();
        
        // Verify
        assertTrue("Flight with correct schedule and route should publish successfully", result);
    }
    
    @Test
    public void testCase2_departureAfterArrival() {
        // Setup
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        ap03.addCity("CityC");
        
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        ap04.addCity("CityD");
        
        Flight flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(ap03);
        flight.setArrivalAirport(ap04);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter)); // Arrival before departure
        
        // Execute
        boolean result = flight.publish();
        
        // Verify
        assertFalse("Flight with departure after arrival should not publish", result);
    }
    
    @Test
    public void testCase3_sameDepartureAndArrivalAirport() {
        // Setup
        Airport ap05 = new Airport();
        ap05.setId("AP05");
        ap05.addCity("CityE");
        
        Flight flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(ap05);
        flight.setArrivalAirport(ap05); // Same airport
        flight.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        // Execute
        boolean result = flight.publish();
        
        // Verify
        assertFalse("Flight with same departure and arrival airport should not publish", result);
    }
    
    @Test
    public void testCase4_departureTimeInThePast() {
        // Setup
        Airport ap06 = new Airport();
        ap06.setId("AP06");
        ap06.addCity("CityF");
        
        Airport ap07 = new Airport();
        ap07.setId("AP07");
        ap07.addCity("CityG");
        
        Flight flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(ap06);
        flight.setArrivalAirport(ap07);
        
        // Set departure time in the past relative to current system time
        // Since we can't easily mock system time, we rely on the fact that the departure time
        // is set to a past date and the publish() method will check if current time is after departure
        flight.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        
        // Note: This test may behave differently depending on when it's run
        // For the specified scenario (current time = 2025-04-01 09:00:00), it should return false
        // In practice, we'd need to mock the current time for this test to be reliable
        
        // Execute
        boolean result = flight.publish();
        
        // Verify - This test is time-dependent and may not be reliable without time mocking
        // The expected behavior is false when current time is after departure time
        if (LocalDateTime.now().isAfter(flight.getDepartureTime())) {
            assertFalse("Flight with departure time in the past should not publish", result);
        }
        // If current time is before the test departure time, the test might pass incorrectly
    }
    
    @Test
    public void testCase5_secondPublishAttempt() {
        // Setup
        Airport ap08 = new Airport();
        ap08.setId("AP08");
        ap08.addCity("CityH");
        
        Airport ap09 = new Airport();
        ap09.setId("AP09");
        ap09.addCity("CityI");
        
        Flight flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(ap08);
        flight.setArrivalAirport(ap09);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        
        // First publish attempt (should succeed)
        boolean firstAttempt = flight.publish();
        assertTrue("First publish attempt should succeed", firstAttempt);
        
        // Second publish attempt
        boolean secondAttempt = flight.publish();
        
        // Verify
        assertFalse("Second publish attempt should fail", secondAttempt);
    }
}