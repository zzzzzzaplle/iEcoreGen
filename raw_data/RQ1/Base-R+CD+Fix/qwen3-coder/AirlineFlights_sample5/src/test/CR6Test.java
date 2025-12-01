import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
        
        // Create airports
        Airport AP28 = new Airport();
        City CityN = new City();
        CityN.setName("CityN");
        AP28.addCity(CityN);
        AP28.setId("AP28");
        
        Airport AP29 = new Airport();
        City CityO = new City();
        CityO.setName("CityO");
        AP29.addCity(CityO);
        AP29.setId("AP29");
        
        Airport AP30 = new Airport();
        City CityP = new City();
        CityP.setName("CityP");
        AP30.addCity(CityP);
        AP30.setId("AP30");
        
        // Create flight F601
        Flight F601 = new Flight();
        F601.setId("F601");
        F601.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        F601.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        F601.setDepartureAirport(AP28);
        F601.setArrivalAirport(AP29);
        F601.setOpenForBooking(true);
        
        AL26.addFlight(F601);
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        stopover.setAirport(AP30);
        
        // Test
        boolean result = F601.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, F601.getStopovers().size());
        assertTrue("Stopover should be in flight's stopover list", F601.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airline AL27 = new Airline();
        
        // Create airports
        Airport AP32 = new Airport();
        City CityQ = new City();
        CityQ.setName("CityQ");
        AP32.addCity(CityQ);
        AP32.setId("AP32");
        
        Airport AP33 = new Airport();
        City CityR = new City();
        CityR.setName("CityR");
        AP33.addCity(CityR);
        AP33.setId("AP33");
        
        Airport AP31 = new Airport();
        City CityS = new City();
        CityS.setName("CityS");
        AP31.addCity(CityS);
        AP31.setId("AP31");
        
        // Create flight F602
        Flight F602 = new Flight();
        F602.setId("F602");
        F602.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        F602.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        F602.setDepartureAirport(AP32);
        F602.setArrivalAirport(AP33);
        F602.setOpenForBooking(true);
        
        AL27.addFlight(F602);
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Create stopover with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        stopover.setAirport(AP31);
        
        // Test
        boolean result = F602.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside window", result);
        assertEquals("Flight should have 0 stopovers", 0, F602.getStopovers().size());
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws Exception {
        // Setup
        Airline AL28 = new Airline();
        
        // Create airports
        Airport AP34 = new Airport();
        City CityT = new City();
        CityT.setName("CityT");
        AP34.addCity(CityT);
        AP34.setId("AP34");
        
        Airport AP35 = new Airport();
        City CityU = new City();
        CityU.setName("CityU");
        AP35.addCity(CityU);
        AP35.setId("AP35");
        
        Airport AP36 = new Airport();
        City CityV = new City();
        CityV.setName("CityV");
        AP36.addCity(CityV);
        AP36.setId("AP36");
        
        // Create flight F603
        Flight F603 = new Flight();
        F603.setId("F603");
        F603.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        F603.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        F603.setDepartureAirport(AP34);
        F603.setArrivalAirport(AP35);
        F603.setOpenForBooking(true);
        
        // Create and add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        existingStopover.setAirport(AP36);
        F603.getStopovers().add(existingStopover);
        
        AL28.addFlight(F603);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Test
        boolean result = F603.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have 0 stopovers after removal", 0, F603.getStopovers().size());
        assertFalse("Stopover should not be in flight's stopover list", F603.getStopovers().contains(existingStopover));
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws Exception {
        // Setup
        Airline AL29 = new Airline();
        
        // Create airports
        Airport AP37 = new Airport();
        City CityW = new City();
        CityW.setName("CityW");
        AP37.addCity(CityW);
        AP37.setId("AP37");
        
        Airport AP38 = new Airport();
        City CityX = new City();
        CityX.setName("CityX");
        AP38.addCity(CityX);
        AP38.setId("AP38");
        
        Airport AP39 = new Airport();
        City CityY = new City();
        CityY.setName("CityY");
        AP39.addCity(CityY);
        AP39.setId("AP39");
        
        // Create flight F604 with openForBooking = false
        Flight F604 = new Flight();
        F604.setId("F604");
        F604.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        F604.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        F604.setDepartureAirport(AP37);
        F604.setArrivalAirport(AP38);
        F604.setOpenForBooking(false);
        
        AL29.addFlight(F604);
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        stopover.setAirport(AP39);
        
        // Test
        boolean result = F604.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to closed flight", result);
        assertEquals("Flight should have 0 stopovers", 0, F604.getStopovers().size());
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws Exception {
        // Setup
        Airline AL31 = new Airline();
        
        // Create airports
        Airport AP42 = new Airport();
        City CityBB = new City();
        CityBB.setName("CityBB");
        AP42.addCity(CityBB);
        AP42.setId("AP42");
        
        Airport AP43 = new Airport();
        City CityCC = new City();
        CityCC.setName("CityCC");
        AP43.addCity(CityCC);
        AP43.setId("AP43");
        
        Airport AP44 = new Airport();
        City CityDD = new City();
        CityDD.setName("CityDD");
        AP44.addCity(CityDD);
        AP44.setId("AP44");
        
        // Create flight F606
        Flight F606 = new Flight();
        F606.setId("F606");
        F606.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        F606.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        F606.setDepartureAirport(AP42);
        F606.setArrivalAirport(AP43);
        F606.setOpenForBooking(true);
        
        // Create and add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        existingStopover.setAirport(AP44);
        F606.getStopovers().add(existingStopover);
        
        AL31.addFlight(F606);
        
        // Current time is after stopover's exit and flight is mid-air
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Test
        boolean result = F606.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after departure", result);
        assertEquals("Flight should still have 1 stopover", 1, F606.getStopovers().size());
        assertTrue("Stopover should still be in flight's stopover list", F606.getStopovers().contains(existingStopover));
    }
}