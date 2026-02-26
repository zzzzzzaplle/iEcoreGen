import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR6Test {
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        f601.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        f601.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        f601.setOpenForBooking(true);
        
        airline.addFlight(f601);
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        // Execute
        boolean result = f601.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Stopover should be added to the flight", 1, f601.getStopovers().size());
        assertTrue("Stopover should be in the list", f601.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        f602.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        f602.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        f602.setOpenForBooking(true);
        
        airline.addFlight(f602);
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Create stopover with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        // Execute
        boolean result = f602.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside flight window", result);
        assertEquals("No stopovers should be added", 0, f602.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() throws Exception {
        // Setup
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setDepartureAirport(ap35); // Depart from AP35
        f603.setArrivalAirport(ap36);   // Arrive at AP36
        f603.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        f603.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        f603.setOpenForBooking(true);
        
        // Create and add stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap34);
        stopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        f603.getStopovers().add(stopover);
        
        airline.addFlight(f603);
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = f603.removeStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Stopover should be removed from the flight", 0, f603.getStopovers().size());
        assertFalse("Stopover should not be in the list", f603.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() throws Exception {
        // Setup
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setDepartureAirport(ap38); // Depart from AP38
        f604.setArrivalAirport(ap39);   // Arrive at AP39
        f604.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        f604.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        f604.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(f604);
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap37);
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        // Execute
        boolean result = f604.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to flight being closed", result);
        assertEquals("No stopovers should be added", 0, f604.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws Exception {
        // Setup
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setDepartureAirport(ap43); // Depart from AP43
        f606.setArrivalAirport(ap44);   // Arrive at AP44
        f606.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        f606.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        f606.setOpenForBooking(true);
        
        // Create and add stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap42);
        stopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        f606.getStopovers().add(stopover);
        
        airline.addFlight(f606);
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00"); // After departure
        
        // Execute
        boolean result = f606.removeStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after flight departure", result);
        assertEquals("Stopover should remain in the flight", 1, f606.getStopovers().size());
        assertTrue("Stopover should still be in the list", f606.getStopovers().contains(stopover));
    }
}