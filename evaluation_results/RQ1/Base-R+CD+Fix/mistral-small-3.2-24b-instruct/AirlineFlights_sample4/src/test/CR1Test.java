import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        
        Airport ap01 = new Airport("AP01");
        Airport ap02 = new Airport("AP02");
        
        Date departureTime = dateFormat.parse("2025-01-10 10:00:00");
        Date arrivalTime = dateFormat.parse("2025-01-10 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        Flight flight = new Flight(departureTime, arrivalTime, ap01, ap02);
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws ParseException {
        // Setup
        airline = new Airline();
        
        Airport ap03 = new Airport("AP03");
        Airport ap04 = new Airport("AP04");
        
        Date departureTime = dateFormat.parse("2025-02-05 20:00:00");
        Date arrivalTime = dateFormat.parse("2025-02-05 18:00:00");
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        Flight flight = new Flight(departureTime, arrivalTime, ap03, ap04);
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure is after arrival", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws ParseException {
        // Setup
        airline = new Airline();
        
        Airport ap05 = new Airport("AP05");
        
        Date departureTime = dateFormat.parse("2025-03-01 08:00:00");
        Date arrivalTime = dateFormat.parse("2025-03-01 12:00:00");
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        Flight flight = new Flight(departureTime, arrivalTime, ap05, ap05);
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws ParseException {
        // Setup
        airline = new Airline();
        
        Airport ap06 = new Airport("AP06");
        Airport ap07 = new Airport("AP07");
        
        Date departureTime = dateFormat.parse("2025-03-30 10:00:00");
        Date arrivalTime = dateFormat.parse("2025-03-30 12:00:00");
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        Flight flight = new Flight(departureTime, arrivalTime, ap06, ap07);
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws ParseException {
        // Setup
        airline = new Airline();
        
        Airport ap08 = new Airport("AP08");
        Airport ap09 = new Airport("AP09");
        
        Date departureTime = dateFormat.parse("2025-05-05 07:00:00");
        Date arrivalTime = dateFormat.parse("2025-05-05 10:00:00");
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        Flight flight = new Flight(departureTime, arrivalTime, ap08, ap09);
        
        // First publish attempt (should succeed)
        boolean firstResult = airline.publishFlight(flight, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt
        boolean secondResult = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published a second time", secondResult);
    }
}