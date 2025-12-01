import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
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
    public void testCase1_AddFirstStopoverInsideJourneyWindow() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F601");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        flight.setDepartureAirport(ap28);
        flight.setArrivalAirport(ap29);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertTrue("Flight should contain the stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F602");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        flight.setDepartureAirport(ap32);
        flight.setArrivalAirport(ap33);
        
        // Create stopover with time outside flight window
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside flight window", result);
        assertFalse("Flight should not contain the stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F603");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        flight.setDepartureAirport(ap35);
        flight.setArrivalAirport(ap36);
        
        // Create and add stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap34);
        stopover.setDepartureTime(dateFormat.parse("2025-06-15 13:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-06-15 13:45:00"));
        flight.getStopovers().add(stopover);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = flight.removeStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertFalse("Flight should not contain the stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        
        // Create flight with openForBooking = false
        Flight flight = new Flight();
        flight.setId("F604");
        flight.setOpenForBooking(false);
        flight.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        flight.setDepartureAirport(ap38);
        flight.setArrivalAirport(ap39);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap37);
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertFalse("Flight should not contain the stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F606");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        flight.setDepartureAirport(ap43);
        flight.setArrivalAirport(ap44);
        
        // Create and add stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap42);
        stopover.setDepartureTime(dateFormat.parse("2025-12-09 20:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-12-09 20:45:00"));
        flight.getStopovers().add(stopover);
        
        // Current time is after stopover's exit time (flight mid-air)
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute
        boolean result = flight.removeStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after departure", result);
        assertTrue("Flight should still contain the stopover", flight.getStopovers().contains(stopover));
    }
}