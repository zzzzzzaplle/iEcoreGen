import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR6Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup
        airline = new Airline();
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setOpenForBooking(true);
        f601.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        f601.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Action
        boolean result = f601.addStopover(stopover, currentTime);
        
        // Assert
        assertTrue("Stopover should be added successfully", result);
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws Exception {
        // Setup
        airline = new Airline();
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setOpenForBooking(true);
        f602.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        f602.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Action
        boolean result = f602.addStopover(stopover, currentTime);
        
        // Assert
        assertFalse("Stopover should not be added due to time outside flight window", result);
    }
    
    @Test
    public void testCase3_deleteExistingStopover() throws Exception {
        // Setup
        airline = new Airline();
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setOpenForBooking(true);
        f603.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        f603.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        f603.setDepartureAirport(ap35);
        f603.setArrivalAirport(ap36);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap34);
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:45:00"));
        f603.getStopovers().add(existingStopover);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Action
        boolean result = f603.removeStopover(existingStopover, currentTime);
        
        // Assert
        assertTrue("Stopover should be removed successfully", result);
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() throws Exception {
        // Setup
        airline = new Airline();
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setOpenForBooking(false);
        f604.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        f604.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        f604.setDepartureAirport(ap38);
        f604.setArrivalAirport(ap39);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap37);
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Action
        boolean result = f604.addStopover(stopover, currentTime);
        
        // Assert
        assertFalse("Stopover should not be added to closed flight", result);
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws Exception {
        // Setup
        airline = new Airline();
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setOpenForBooking(true);
        f606.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        f606.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        f606.setDepartureAirport(ap43);
        f606.setArrivalAirport(ap44);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap42);
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:45:00"));
        f606.getStopovers().add(existingStopover);
        
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Action
        boolean result = f606.removeStopover(existingStopover, currentTime);
        
        // Assert
        assertFalse("Stopover should not be removed after departure", result);
    }
}