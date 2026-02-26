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
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        ap28.addCity(new City("CityN"));
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        ap29.addCity(new City("CityO"));
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        ap30.addCity(new City("CityP"));
        
        // Create flight
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setOpenForBooking(true);
        f601.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        f601.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Execute test
        boolean result = f601.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertTrue("Flight should contain the stopover", f601.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        ap32.addCity(new City("CityQ"));
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        ap33.addCity(new City("CityR"));
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        ap31.addCity(new City("CityS"));
        
        // Create flight
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setOpenForBooking(true);
        f602.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        f602.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        
        // Create stopover with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Execute test
        boolean result = f602.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside flight window", result);
        assertFalse("Flight should not contain the stopover", f602.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        ap34.addCity(new City("CityT"));
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        ap35.addCity(new City("CityU"));
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        ap36.addCity(new City("CityV"));
        
        // Create flight
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setOpenForBooking(true);
        f603.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        f603.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        f603.setDepartureAirport(ap34);
        f603.setArrivalAirport(ap35);
        
        // Create and add existing stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap34);
        stopover.setDepartureTime(dateFormat.parse("2025-06-15 13:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-06-15 13:45:00"));
        f603.getStopovers().add(stopover);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute test
        boolean result = f603.removeStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertFalse("Flight should not contain the stopover after removal", f603.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        ap37.addCity(new City("CityW"));
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        ap38.addCity(new City("CityX"));
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        ap39.addCity(new City("CityY"));
        
        // Create flight with openForBooking = false
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setOpenForBooking(false);
        f604.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        f604.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        f604.setDepartureAirport(ap37);
        f604.setArrivalAirport(ap38);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap37);
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Execute test
        boolean result = f604.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertFalse("Flight should not contain the stopover", f604.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        ap42.addCity(new City("CityBB"));
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        ap43.addCity(new City("CityCC"));
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        ap44.addCity(new City("CityDD"));
        
        // Create flight
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setOpenForBooking(true);
        f606.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        f606.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        f606.setDepartureAirport(ap42);
        f606.setArrivalAirport(ap43);
        
        // Create and add existing stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap42);
        stopover.setDepartureTime(dateFormat.parse("2025-12-09 20:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-12-09 20:45:00"));
        f606.getStopovers().add(stopover);
        
        // Current time after departure and after stopover
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute test
        boolean result = f606.removeStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after flight departure", result);
        assertTrue("Flight should still contain the stopover", f606.getStopovers().contains(stopover));
    }
}