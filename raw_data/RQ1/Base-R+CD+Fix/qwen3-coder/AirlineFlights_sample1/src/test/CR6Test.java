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
        Airport AP29 = new Airport();
        Airport AP30 = new Airport();
        
        // Create cities
        City cityN = new City();
        cityN.setName("CityN");
        City cityO = new City();
        cityO.setName("CityO");
        City cityP = new City();
        cityP.setName("CityP");
        
        // Assign cities to airports
        AP28.addCity(cityN);
        AP29.addCity(cityO);
        AP30.addCity(cityP);
        
        // Create flight F601
        Flight F601 = new Flight();
        F601.setId("F601");
        F601.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        F601.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        F601.setDepartureAirport(AP28);
        F601.setArrivalAirport(AP29);
        F601.setOpenForBooking(true);
        
        AL26.addFlight(F601);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        stopover.setAirport(AP30);
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Execute
        boolean result = F601.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, F601.getStopovers().size());
        assertTrue("Stopover should be in flight's stopover list", F601.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airline AL27 = new Airline();
        
        // Create airports
        Airport AP32 = new Airport();
        Airport AP33 = new Airport();
        Airport AP31 = new Airport();
        
        // Create cities
        City cityQ = new City();
        cityQ.setName("CityQ");
        City cityR = new City();
        cityR.setName("CityR");
        City cityS = new City();
        cityS.setName("CityS");
        
        // Assign cities to airports
        AP32.addCity(cityQ);
        AP33.addCity(cityR);
        AP31.addCity(cityS);
        
        // Create flight F602
        Flight F602 = new Flight();
        F602.setId("F602");
        F602.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        F602.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        F602.setDepartureAirport(AP32);
        F602.setArrivalAirport(AP33);
        F602.setOpenForBooking(true);
        
        AL27.addFlight(F602);
        
        // Create stopover with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        stopover.setAirport(AP31);
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Execute
        boolean result = F602.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside window", result);
        assertEquals("Flight should have 0 stopovers", 0, F602.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() throws Exception {
        // Setup
        Airline AL28 = new Airline();
        
        // Create airports
        Airport AP34 = new Airport();
        Airport AP35 = new Airport();
        Airport AP36 = new Airport();
        
        // Create cities
        City cityT = new City();
        cityT.setName("CityT");
        City cityU = new City();
        cityU.setName("CityU");
        City cityV = new City();
        cityV.setName("CityV");
        
        // Assign cities to airports
        AP34.addCity(cityT);
        AP35.addCity(cityU);
        AP36.addCity(cityV);
        
        // Create flight F603
        Flight F603 = new Flight();
        F603.setId("F603");
        F603.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        F603.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        F603.setDepartureAirport(AP35);
        F603.setArrivalAirport(AP36);
        F603.setOpenForBooking(true);
        
        // Create and add stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        stopover.setAirport(AP34);
        
        F603.getStopovers().add(stopover);
        
        AL28.addFlight(F603);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = F603.removeStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have 0 stopovers after removal", 0, F603.getStopovers().size());
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() throws Exception {
        // Setup
        Airline AL29 = new Airline();
        
        // Create airports
        Airport AP37 = new Airport();
        Airport AP38 = new Airport();
        Airport AP39 = new Airport();
        
        // Create cities
        City cityW = new City();
        cityW.setName("CityW");
        City cityX = new City();
        cityX.setName("CityX");
        City cityY = new City();
        cityY.setName("CityY");
        
        // Assign cities to airports
        AP37.addCity(cityW);
        AP38.addCity(cityX);
        AP39.addCity(cityY);
        
        // Create flight F604 (closed for booking)
        Flight F604 = new Flight();
        F604.setId("F604");
        F604.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        F604.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        F604.setDepartureAirport(AP38);
        F604.setArrivalAirport(AP39);
        F604.setOpenForBooking(false);
        
        AL29.addFlight(F604);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        stopover.setAirport(AP37);
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Execute
        boolean result = F604.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have 0 stopovers", 0, F604.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws Exception {
        // Setup
        Airline AL31 = new Airline();
        
        // Create airports
        Airport AP42 = new Airport();
        Airport AP43 = new Airport();
        Airport AP44 = new Airport();
        
        // Create cities
        City cityBB = new City();
        cityBB.setName("CityBB");
        City cityCC = new City();
        cityCC.setName("CityCC");
        City cityDD = new City();
        cityDD.setName("CityDD");
        
        // Assign cities to airports
        AP42.addCity(cityBB);
        AP43.addCity(cityCC);
        AP44.addCity(cityDD);
        
        // Create flight F606
        Flight F606 = new Flight();
        F606.setId("F606");
        F606.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        F606.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        F606.setDepartureAirport(AP43);
        F606.setArrivalAirport(AP44);
        F606.setOpenForBooking(true);
        
        // Create and add stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        stopover.setAirport(AP42);
        
        F606.getStopovers().add(stopover);
        
        AL31.addFlight(F606);
        
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute
        boolean result = F606.removeStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after departure", result);
        assertEquals("Flight should still have 1 stopover", 1, F606.getStopovers().size());
    }
}