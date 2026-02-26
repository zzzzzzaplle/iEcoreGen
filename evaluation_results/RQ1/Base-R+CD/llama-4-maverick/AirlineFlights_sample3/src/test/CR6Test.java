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
    private Airport ap28, ap29, ap30, ap31, ap32, ap33, ap34, ap35, ap36, ap37, ap38, ap39, ap42, ap43, ap44;
    private Flight f601, f602, f603, f604, f606;
    private Airline al26, al27, al28, al29, al31;
    private Stopover stopoverForF603, stopoverForF606;
    
    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Create airports
        ap28 = new Airport(); ap28.setId("AP28");
        ap29 = new Airport(); ap29.setId("AP29");
        ap30 = new Airport(); ap30.setId("AP30");
        ap31 = new Airport(); ap31.setId("AP31");
        ap32 = new Airport(); ap32.setId("AP32");
        ap33 = new Airport(); ap33.setId("AP33");
        ap34 = new Airport(); ap34.setId("AP34");
        ap35 = new Airport(); ap35.setId("AP35");
        ap36 = new Airport(); ap36.setId("AP36");
        ap37 = new Airport(); ap37.setId("AP37");
        ap38 = new Airport(); ap38.setId("AP38");
        ap39 = new Airport(); ap39.setId("AP39");
        ap42 = new Airport(); ap42.setId("AP42");
        ap43 = new Airport(); ap43.setId("AP43");
        ap44 = new Airport(); ap44.setId("AP44");
        
        // Create airlines
        al26 = new Airline();
        al27 = new Airline();
        al28 = new Airline();
        al29 = new Airline();
        al31 = new Airline();
        
        // Create flight F601
        f601 = new Flight();
        f601.setId("F601");
        f601.setOpenForBooking(true);
        f601.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        f601.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        f601.setStopovers(new ArrayList<>());
        al26.addFlight(f601);
        
        // Create flight F602
        f602 = new Flight();
        f602.setId("F602");
        f602.setOpenForBooking(true);
        f602.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        f602.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        f602.setStopovers(new ArrayList<>());
        al27.addFlight(f602);
        
        // Create flight F603 with existing stopover
        f603 = new Flight();
        f603.setId("F603");
        f603.setOpenForBooking(true);
        f603.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        f603.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        f603.setDepartureAirport(ap34);
        f603.setArrivalAirport(ap35);
        stopoverForF603 = new Stopover();
        stopoverForF603.setDepartureTime(dateFormat.parse("2025-06-15 13:00:00"));
        stopoverForF603.setArrivalTime(dateFormat.parse("2025-06-15 13:45:00"));
        stopoverForF603.setAirport(ap34);
        List<Stopover> f603Stopovers = new ArrayList<>();
        f603Stopovers.add(stopoverForF603);
        f603.setStopovers(f603Stopovers);
        al28.addFlight(f603);
        
        // Create flight F604 (closed for booking)
        f604 = new Flight();
        f604.setId("F604");
        f604.setOpenForBooking(false);
        f604.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        f604.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        f604.setDepartureAirport(ap37);
        f604.setArrivalAirport(ap38);
        f604.setStopovers(new ArrayList<>());
        al29.addFlight(f604);
        
        // Create flight F606 with existing stopover
        f606 = new Flight();
        f606.setId("F606");
        f606.setOpenForBooking(true);
        f606.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        f606.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        f606.setDepartureAirport(ap42);
        f606.setArrivalAirport(ap43);
        stopoverForF606 = new Stopover();
        stopoverForF606.setDepartureTime(dateFormat.parse("2025-12-09 20:00:00"));
        stopoverForF606.setArrivalTime(dateFormat.parse("2025-12-09 20:45:00"));
        stopoverForF606.setAirport(ap42);
        List<Stopover> f606Stopovers = new ArrayList<>();
        f606Stopovers.add(stopoverForF606);
        f606.setStopovers(f606Stopovers);
        al31.addFlight(f606);
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() throws ParseException {
        // Test adding a stopover that fits within the flight schedule
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        Stopover stopover = new Stopover();
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:40:00"));
        stopover.setAirport(ap30);
        
        boolean result = f601.addStopover(stopover, currentTime);
        
        assertTrue("Stopover should be added successfully", result);
        assertTrue("Flight should contain the new stopover", f601.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws ParseException {
        // Test adding a stopover with times outside the flight schedule
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        Stopover stopover = new Stopover();
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 17:00:00"));
        stopover.setAirport(ap31);
        
        boolean result = f602.addStopover(stopover, currentTime);
        
        assertFalse("Stopover should not be added due to time constraints", result);
        assertFalse("Flight should not contain the invalid stopover", f602.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws ParseException {
        // Test removing an existing stopover before flight departure
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        boolean result = f603.removeStopover(stopoverForF603, currentTime);
        
        assertTrue("Stopover should be removed successfully", result);
        assertFalse("Flight should not contain the removed stopover", f603.getStopovers().contains(stopoverForF603));
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws ParseException {
        // Test adding a stopover to a flight that is closed for booking
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        Stopover stopover = new Stopover();
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 14:00:00"));
        stopover.setAirport(ap37);
        
        boolean result = f604.addStopover(stopover, currentTime);
        
        assertFalse("Stopover should not be added to closed flight", result);
        assertFalse("Flight should not contain the stopover", f604.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws ParseException {
        // Test removing a stopover after flight departure
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        boolean result = f606.removeStopover(stopoverForF606, currentTime);
        
        assertFalse("Stopover should not be removed after departure", result);
        assertTrue("Flight should still contain the stopover", f606.getStopovers().contains(stopoverForF606));
    }
}