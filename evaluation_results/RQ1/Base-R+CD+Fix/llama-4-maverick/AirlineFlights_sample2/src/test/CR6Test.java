import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR6Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        
        Flight flight = new Flight();
        flight.setId("F601");
        flight.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        flight.setDepartureAirport(ap28);
        flight.setArrivalAirport(ap29);
        flight.setOpenForBooking(true);
        
        Stopover stopover = new Stopover();
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:40:00"));
        stopover.setAirport(ap30);
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, flight.getStopovers().size());
        assertSame("Added stopover should match", stopover, flight.getStopovers().get(0));
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        
        Flight flight = new Flight();
        flight.setId("F602");
        flight.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        flight.setDepartureAirport(ap32);
        flight.setArrivalAirport(ap33);
        flight.setOpenForBooking(true);
        
        Stopover stopover = new Stopover();
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 17:00:00"));
        stopover.setAirport(ap31);
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added - time outside flight window", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        
        Flight flight = new Flight();
        flight.setId("F603");
        flight.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        flight.setDepartureAirport(ap35);
        flight.setArrivalAirport(ap36);
        flight.setOpenForBooking(true);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:45:00"));
        existingStopover.setAirport(ap34);
        flight.getStopovers().add(existingStopover);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = flight.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        
        Flight flight = new Flight();
        flight.setId("F604");
        flight.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        flight.setDepartureAirport(ap38);
        flight.setArrivalAirport(ap39);
        flight.setOpenForBooking(false);
        
        Stopover stopover = new Stopover();
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 14:00:00"));
        stopover.setAirport(ap37);
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added - flight closed", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        
        Flight flight = new Flight();
        flight.setId("F606");
        flight.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        flight.setDepartureAirport(ap43);
        flight.setArrivalAirport(ap44);
        flight.setOpenForBooking(true);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:45:00"));
        existingStopover.setAirport(ap42);
        flight.getStopovers().add(existingStopover);
        
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute
        boolean result = flight.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed - flight already departed", result);
        assertEquals("Flight should still have 1 stopover", 1, flight.getStopovers().size());
    }
}