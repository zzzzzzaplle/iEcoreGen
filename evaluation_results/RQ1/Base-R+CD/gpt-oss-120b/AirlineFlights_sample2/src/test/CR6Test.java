import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR6Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup
        Airline AL26 = new Airline();
        
        Airport AP28 = new Airport();
        AP28.setId("AP28");
        Airport AP29 = new Airport();
        AP29.setId("AP29");
        Airport AP30 = new Airport();
        AP30.setId("AP30");
        
        Flight F601 = new Flight();
        F601.setId("F601");
        F601.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        F601.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        F601.setDepartureAirport(AP28);
        F601.setArrivalAirport(AP29);
        F601.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        stopover.setAirport(AP30);
        
        // Execute
        boolean result = F601.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have one stopover", 1, F601.getStopovers().size());
        assertTrue("Stopover should be in the list", F601.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airline AL27 = new Airline();
        
        Airport AP32 = new Airport();
        AP32.setId("AP32");
        Airport AP33 = new Airport();
        AP33.setId("AP33");
        Airport AP31 = new Airport();
        AP31.setId("AP31");
        
        Flight F602 = new Flight();
        F602.setId("F602");
        F602.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        F602.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        F602.setDepartureAirport(AP32);
        F602.setArrivalAirport(AP33);
        F602.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        stopover.setAirport(AP31);
        
        // Execute
        boolean result = F602.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover outside flight window should not be added", result);
        assertEquals("Flight should have no stopovers", 0, F602.getStopovers().size());
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws Exception {
        // Setup
        Airline AL28 = new Airline();
        
        Airport AP34 = new Airport();
        AP34.setId("AP34");
        Airport AP35 = new Airport();
        AP35.setId("AP35");
        Airport AP36 = new Airport();
        AP36.setId("AP36");
        
        Flight F603 = new Flight();
        F603.setId("F603");
        F603.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        F603.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        F603.setDepartureAirport(AP34);
        F603.setArrivalAirport(AP35);
        F603.setOpenForBooking(true);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        existingStopover.setAirport(AP36);
        
        F603.getStopovers().add(existingStopover);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = F603.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertTrue("Existing stopover should be removed successfully", result);
        assertEquals("Flight should have no stopovers after removal", 0, F603.getStopovers().size());
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws Exception {
        // Setup
        Airline AL29 = new Airline();
        
        Airport AP37 = new Airport();
        AP37.setId("AP37");
        Airport AP38 = new Airport();
        AP38.setId("AP38");
        Airport AP39 = new Airport();
        AP39.setId("AP39");
        
        Flight F604 = new Flight();
        F604.setId("F604");
        F604.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        F604.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        F604.setDepartureAirport(AP37);
        F604.setArrivalAirport(AP38);
        F604.setOpenForBooking(false); // Flight is closed
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        stopover.setAirport(AP39);
        
        // Execute
        boolean result = F604.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have no stopovers", 0, F604.getStopovers().size());
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws Exception {
        // Setup
        Airline AL31 = new Airline();
        
        Airport AP42 = new Airport();
        AP42.setId("AP42");
        Airport AP43 = new Airport();
        AP43.setId("AP43");
        Airport AP44 = new Airport();
        AP44.setId("AP44");
        
        Flight F606 = new Flight();
        F606.setId("F606");
        F606.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        F606.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        F606.setDepartureAirport(AP42);
        F606.setArrivalAirport(AP43);
        F606.setOpenForBooking(true);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        existingStopover.setAirport(AP44);
        
        F606.getStopovers().add(existingStopover);
        
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00"); // After departure
        
        // Execute
        boolean result = F606.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after flight departure", result);
        assertEquals("Flight should still have one stopover", 1, F606.getStopovers().size());
        assertTrue("Stopover should remain in the list", F606.getStopovers().contains(existingStopover));
    }
}