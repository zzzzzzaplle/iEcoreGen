import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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
        Airline AL1 = new Airline();
        Airport AP01 = new Airport();
        AP01.setId("AP01");
        Airport AP02 = new Airport();
        AP02.setId("AP02");
        
        Flight F100 = new Flight();
        F100.setId("F100");
        F100.setDepartureAirport(AP01);
        F100.setArrivalAirport(AP02);
        F100.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        F100.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        F100.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Execute and Verify
        assertTrue("Flight should be published successfully with correct schedule and route", 
                   AL1.publishFlight(F100, currentTime));
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws Exception {
        // Setup
        Airline AL2 = new Airline();
        Airport AP03 = new Airport();
        AP03.setId("AP03");
        Airport AP04 = new Airport();
        AP04.setId("AP04");
        
        Flight F101 = new Flight();
        F101.setId("F101");
        F101.setDepartureAirport(AP03);
        F101.setArrivalAirport(AP04);
        F101.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        F101.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00"));
        F101.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Execute and Verify
        assertFalse("Flight should not be published when departure is after arrival", 
                    AL2.publishFlight(F101, currentTime));
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws Exception {
        // Setup
        Airline AL3 = new Airline();
        Airport AP05 = new Airport();
        AP05.setId("AP05");
        
        Flight F102 = new Flight();
        F102.setId("F102");
        F102.setDepartureAirport(AP05);
        F102.setArrivalAirport(AP05);
        F102.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        F102.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        F102.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Execute and Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", 
                    AL3.publishFlight(F102, currentTime));
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws Exception {
        // Setup
        Airline AL4 = new Airline();
        Airport AP06 = new Airport();
        AP06.setId("AP06");
        Airport AP07 = new Airport();
        AP07.setId("AP07");
        
        Flight F103 = new Flight();
        F103.setId("F103");
        F103.setDepartureAirport(AP06);
        F103.setArrivalAirport(AP07);
        F103.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        F103.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        F103.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        // Execute and Verify
        assertFalse("Flight should not be published when departure time is in the past", 
                    AL4.publishFlight(F103, currentTime));
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws Exception {
        // Setup
        Airline AL5 = new Airline();
        Airport AP08 = new Airport();
        AP08.setId("AP08");
        Airport AP09 = new Airport();
        AP09.setId("AP09");
        
        Flight F104 = new Flight();
        F104.setId("F104");
        F104.setDepartureAirport(AP08);
        F104.setArrivalAirport(AP09);
        F104.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        F104.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        F104.setOpenForBooking(false); // Flight is already published, so not open for booking
        
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // Execute and Verify
        assertFalse("Flight should not be published when it's already published", 
                    AL5.publishFlight(F104, currentTime));
    }
}