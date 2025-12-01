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
    private Airport airport1;
    private Airport airport2;
    private Flight flight;
    private Date currentTime;
    
    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() throws ParseException {
        // Setup
        airline = new Airline();
        airport1 = new Airport();
        airport1.setId("AP01");
        airport2 = new Airport();
        airport2.setId("AP02");
        
        flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(airport1);
        flight.setArrivalAirport(airport2);
        flight.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        flight.setOpenForBooking(true);
        
        currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws ParseException {
        // Setup
        airline = new Airline();
        airport1 = new Airport();
        airport1.setId("AP03");
        airport2 = new Airport();
        airport2.setId("AP04");
        
        flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(airport1);
        flight.setArrivalAirport(airport2);
        flight.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00"));
        flight.setOpenForBooking(true);
        
        currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is after arrival time", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws ParseException {
        // Setup
        airline = new Airline();
        airport1 = new Airport();
        airport1.setId("AP05");
        
        flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(airport1);
        flight.setArrivalAirport(airport1); // Same airport
        flight.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        flight.setOpenForBooking(true);
        
        currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws ParseException {
        // Setup
        airline = new Airline();
        airport1 = new Airport();
        airport1.setId("AP06");
        airport2 = new Airport();
        airport2.setId("AP07");
        
        flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(airport1);
        flight.setArrivalAirport(airport2);
        flight.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        flight.setOpenForBooking(true);
        
        currentTime = dateFormat.parse("2025-04-01 09:00:00"); // Current time after departure
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws ParseException {
        // Setup
        airline = new Airline();
        airport1 = new Airport();
        airport1.setId("AP08");
        airport2 = new Airport();
        airport2.setId("AP09");
        
        flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(airport1);
        flight.setArrivalAirport(airport2);
        flight.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First successful publish
        boolean firstResult = airline.publishFlight(flight, currentTime);
        assertTrue("First publish should succeed", firstResult);
        
        // Second attempt - flight should no longer be open for booking after first publish
        flight.setOpenForBooking(false);
        
        // Execute
        boolean secondResult = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Second publish attempt should fail", secondResult);
    }
}