import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        
        Flight flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(ap01);
        flight.setArrivalAirport(ap02);
        flight.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        flight.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws Exception {
        // Setup
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        
        Flight flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(ap03);
        flight.setArrivalAirport(ap04);
        flight.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00"));
        flight.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure is after arrival", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws Exception {
        // Setup
        Airport ap05 = new Airport();
        ap05.setId("AP05");
        
        Flight flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(ap05);
        flight.setArrivalAirport(ap05);
        flight.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        flight.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws Exception {
        // Setup
        Airport ap06 = new Airport();
        ap06.setId("AP06");
        Airport ap07 = new Airport();
        ap07.setId("AP07");
        
        Flight flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(ap06);
        flight.setArrivalAirport(ap07);
        flight.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        flight.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws Exception {
        // Setup
        Airport ap08 = new Airport();
        ap08.setId("AP08");
        Airport ap09 = new Airport();
        ap09.setId("AP09");
        
        Flight flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(ap08);
        flight.setArrivalAirport(ap09);
        flight.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        flight.setOpenForBooking(false); // Flight already published, so not open for booking
        
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when it's not open for booking (already published)", result);
    }
}