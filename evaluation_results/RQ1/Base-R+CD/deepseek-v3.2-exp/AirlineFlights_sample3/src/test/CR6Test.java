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
    public void testCase1_addFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup
        Airline AL26 = new Airline();
        
        // Create airports
        Airport AP28 = new Airport();
        AP28.setId("AP28");
        City cityN = new City();
        cityN.setName("CityN");
        AP28.addCity(cityN);
        
        Airport AP29 = new Airport();
        AP29.setId("AP29");
        City cityO = new City();
        cityO.setName("CityO");
        AP29.addCity(cityO);
        
        Airport AP30 = new Airport();
        AP30.setId("AP30");
        City cityP = new City();
        cityP.setName("CityP");
        AP30.addCity(cityP);
        
        // Create flight F601
        Flight F601 = new Flight();
        F601.setId("F601");
        F601.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        F601.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        F601.setDepartureAirport(AP28);
        F601.setArrivalAirport(AP29);
        F601.setOpenForBooking(true);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        stopover.setAirport(AP30);
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Execute test
        boolean result = F601.addStopover(stopover, currentTime);
        
        // Verify result
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, F601.getStopovers().size());
        assertTrue("Stopover should be in the flight's stopover list", F601.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airline AL27 = new Airline();
        
        // Create airports
        Airport AP32 = new Airport();
        AP32.setId("AP32");
        City cityQ = new City();
        cityQ.setName("CityQ");
        AP32.addCity(cityQ);
        
        Airport AP33 = new Airport();
        AP33.setId("AP33");
        City cityR = new City();
        cityR.setName("CityR");
        AP33.addCity(cityR);
        
        Airport AP31 = new Airport();
        AP31.setId("AP31");
        City cityS = new City();
        cityS.setName("CityS");
        AP31.addCity(cityS);
        
        // Create flight F602
        Flight F602 = new Flight();
        F602.setId("F602");
        F602.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        F602.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        F602.setDepartureAirport(AP32);
        F602.setArrivalAirport(AP33);
        F602.setOpenForBooking(true);
        
        // Create stopover with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        stopover.setAirport(AP31);
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Execute test
        boolean result = F602.addStopover(stopover, currentTime);
        
        // Verify result
        assertFalse("Stopover should not be added due to time outside window", result);
        assertEquals("Flight should have 0 stopovers", 0, F602.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() throws Exception {
        // Setup
        Airline AL28 = new Airline();
        
        // Create airports
        Airport AP34 = new Airport();
        AP34.setId("AP34");
        City cityT = new City();
        cityT.setName("CityT");
        AP34.addCity(cityT);
        
        Airport AP35 = new Airport();
        AP35.setId("AP35");
        City cityU = new City();
        cityU.setName("CityU");
        AP35.addCity(cityU);
        
        Airport AP36 = new Airport();
        AP36.setId("AP36");
        City cityV = new City();
        cityV.setName("CityV");
        AP36.addCity(cityV);
        
        // Create flight F603
        Flight F603 = new Flight();
        F603.setId("F603");
        F603.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        F603.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        F603.setDepartureAirport(AP34);
        F603.setArrivalAirport(AP35);
        F603.setOpenForBooking(true);
        
        // Create and add stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        stopover.setAirport(AP36);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // First add the stopover
        boolean addResult = F603.addStopover(stopover, currentTime);
        assertTrue("Stopover should be added successfully", addResult);
        assertEquals("Flight should have 1 stopover", 1, F603.getStopovers().size());
        
        // Execute test - remove stopover
        boolean removeResult = F603.removeStopover(stopover, currentTime);
        
        // Verify result
        assertTrue("Stopover should be removed successfully", removeResult);
        assertEquals("Flight should have 0 stopovers after removal", 0, F603.getStopovers().size());
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() throws Exception {
        // Setup
        Airline AL29 = new Airline();
        
        // Create airports
        Airport AP37 = new Airport();
        AP37.setId("AP37");
        City cityW = new City();
        cityW.setName("CityW");
        AP37.addCity(cityW);
        
        Airport AP38 = new Airport();
        AP38.setId("AP38");
        City cityX = new City();
        cityX.setName("CityX");
        AP38.addCity(cityX);
        
        Airport AP39 = new Airport();
        AP39.setId("AP39");
        City cityY = new City();
        cityY.setName("CityY");
        AP39.addCity(cityY);
        
        // Create flight F604 with openForBooking = false
        Flight F604 = new Flight();
        F604.setId("F604");
        F604.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        F604.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        F604.setDepartureAirport(AP37);
        F604.setArrivalAirport(AP38);
        F604.setOpenForBooking(false); // Flight is closed
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        stopover.setAirport(AP39);
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Execute test
        boolean result = F604.addStopover(stopover, currentTime);
        
        // Verify result
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have 0 stopovers", 0, F604.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws Exception {
        // Setup
        Airline AL31 = new Airline();
        
        // Create airports
        Airport AP42 = new Airport();
        AP42.setId("AP42");
        City cityBB = new City();
        cityBB.setName("CityBB");
        AP42.addCity(cityBB);
        
        Airport AP43 = new Airport();
        AP43.setId("AP43");
        City cityCC = new City();
        cityCC.setName("CityCC");
        AP43.addCity(cityCC);
        
        Airport AP44 = new Airport();
        AP44.setId("AP44");
        City cityDD = new City();
        cityDD.setName("CityDD");
        AP44.addCity(cityDD);
        
        // Create flight F606
        Flight F606 = new Flight();
        F606.setId("F606");
        F606.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        F606.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        F606.setDepartureAirport(AP42);
        F606.setArrivalAirport(AP43);
        F606.setOpenForBooking(true);
        
        // Create and add stopover before departure
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        stopover.setAirport(AP44);
        
        Date setupTime = dateFormat.parse("2025-12-08 10:00:00");
        boolean addResult = F606.addStopover(stopover, setupTime);
        assertTrue("Stopover should be added successfully before departure", addResult);
        assertEquals("Flight should have 1 stopover", 1, F606.getStopovers().size());
        
        // Current time is after departure and after stopover
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute test - attempt to remove stopover after departure
        boolean removeResult = F606.removeStopover(stopover, currentTime);
        
        // Verify result
        assertFalse("Stopover should not be removed after flight departure", removeResult);
        assertEquals("Flight should still have 1 stopover", 1, F606.getStopovers().size());
    }
}