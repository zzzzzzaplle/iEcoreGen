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
    public void setUp() throws ParseException {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() throws ParseException {
        // Setup
        Airline AL1 = new Airline();
        
        Airport AP01 = new Airport();
        AP01.setId("AP01");
        Airport AP02 = new Airport();
        AP02.setId("AP02");
        
        Flight F100 = new Flight();
        F100.setId("F100");
        F100.setDepartureAirport(AP01);
        F100.setArrialAirport(AP02);
        F100.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        F100.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        F100.setOpenForBooking(false); // Not yet published
        
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Execute
        boolean result = AL1.publishFlight(F100, currentTime);
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
        assertTrue("Flight should be open for booking after successful publication", F100.isOpenForBooking());
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws ParseException {
        // Setup
        Airline AL2 = new Airline();
        
        Airport AP03 = new Airport();
        AP03.setId("AP03");
        Airport AP04 = new Airport();
        AP04.setId("AP04");
        
        Flight F101 = new Flight();
        F101.setId("F101");
        F101.setDepartureAirport(AP03);
        F101.setArrialAirport(AP04);
        F101.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        F101.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00")); // Arrival before departure
        F101.setOpenForBooking(false);
        
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Execute
        boolean result = AL2.publishFlight(F101, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure is after arrival", result);
        assertFalse("Flight should remain closed for booking after failed publication", F101.isOpenForBooking());
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws ParseException {
        // Setup
        Airline AL3 = new Airline();
        
        Airport AP05 = new Airport();
        AP05.setId("AP05");
        
        Flight F102 = new Flight();
        F102.setId("F102");
        F102.setDepartureAirport(AP05);
        F102.setArrialAirport(AP05); // Same airport
        F102.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        F102.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        F102.setOpenForBooking(false);
        
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Execute
        boolean result = AL3.publishFlight(F102, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
        assertFalse("Flight should remain closed for booking after failed publication", F102.isOpenForBooking());
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws ParseException {
        // Setup
        Airline AL4 = new Airline();
        
        Airport AP06 = new Airport();
        AP06.setId("AP06");
        Airport AP07 = new Airport();
        AP07.setId("AP07");
        
        Flight F103 = new Flight();
        F103.setId("F103");
        F103.setDepartureAirport(AP06);
        F103.setArrialAirport(AP07);
        F103.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        F103.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        F103.setOpenForBooking(false);
        
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00"); // Current time after departure
        
        // Execute
        boolean result = AL4.publishFlight(F103, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when current time is after departure time", result);
        assertFalse("Flight should remain closed for booking after failed publication", F103.isOpenForBooking());
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws ParseException {
        // Setup
        Airline AL5 = new Airline();
        
        Airport AP08 = new Airport();
        AP08.setId("AP08");
        Airport AP09 = new Airport();
        AP09.setId("AP09");
        
        Flight F104 = new Flight();
        F104.setId("F104");
        F104.setDepartureAirport(AP08);
        F104.setArrialAirport(AP09);
        F104.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        F104.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        F104.setOpenForBooking(false);
        
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt (should succeed)
        boolean firstResult = AL5.publishFlight(F104, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        assertTrue("Flight should be open for booking after first publication", F104.isOpenForBooking());
        
        // Second publish attempt
        boolean secondResult = AL5.publishFlight(F104, currentTime);
        
        // Verify
        assertFalse("Second publish attempt should fail", secondResult);
        assertTrue("Flight should remain open for booking after second publication attempt", F104.isOpenForBooking());
    }
}