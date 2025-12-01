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
    private Airline airline;
    private Flight flight;
    private Stopover stopover;
    private Date currentTime;

    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    // Test Case 1: "Add first stopover inside journey window"
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() throws ParseException {
        // Setup
        airline = new Airline();
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        
        flight = new Flight();
        flight.setId("F601");
        flight.setDepartureAirport(ap28);
        flight.setArrivalAirport(ap29);
        flight.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertTrue("Flight should contain the stopover", flight.getStopovers().contains(stopover));
    }

    // Test Case 2: "Stopover time outside window"
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws ParseException {
        // Setup
        airline = new Airline();
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        
        flight = new Flight();
        flight.setId("F602");
        flight.setDepartureAirport(ap32);
        flight.setArrivalAirport(ap33);
        flight.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside window", result);
        assertFalse("Flight should not contain the stopover", flight.getStopovers().contains(stopover));
    }

    // Test Case 3: "Delete existing stopover"
    @Test
    public void testCase3_deleteExistingStopover() throws ParseException {
        // Setup
        airline = new Airline();
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        
        flight = new Flight();
        flight.setId("F603");
        flight.setDepartureAirport(ap34);
        flight.setArrivalAirport(ap35);
        flight.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap36);
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:45:00"));
        flight.getStopovers().add(existingStopover);
        
        currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = flight.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertFalse("Flight should not contain the stopover", flight.getStopovers().contains(existingStopover));
    }

    // Test Case 4: "Flight closed prevents modification"
    @Test
    public void testCase4_flightClosedPreventsModification() throws ParseException {
        // Setup
        airline = new Airline();
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        
        flight = new Flight();
        flight.setId("F604");
        flight.setDepartureAirport(ap37);
        flight.setArrivalAirport(ap38);
        flight.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        flight.setOpenForBooking(false);
        airline.addFlight(flight);
        
        currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to flight being closed", result);
        assertFalse("Flight should not contain the stopover", flight.getStopovers().contains(stopover));
    }

    // Test Case 5: "Attempt removal after departure"
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws ParseException {
        // Setup
        airline = new Airline();
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        
        flight = new Flight();
        flight.setId("F606");
        flight.setDepartureAirport(ap42);
        flight.setArrivalAirport(ap43);
        flight.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap44);
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:45:00"));
        flight.getStopovers().add(existingStopover);
        
        currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute
        boolean result = flight.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after departure", result);
        assertTrue("Flight should still contain the stopover", flight.getStopovers().contains(existingStopover));
    }
}