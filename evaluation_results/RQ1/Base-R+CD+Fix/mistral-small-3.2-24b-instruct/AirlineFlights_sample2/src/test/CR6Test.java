import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        
        Airport AP28 = new Airport();
        City CityN = new City();
        CityN.setName("CityN");
        AP28.addCity(CityN);
        
        Airport AP29 = new Airport();
        City CityO = new City();
        CityO.setName("CityO");
        AP29.addCity(CityO);
        
        Airport AP30 = new Airport();
        City CityP = new City();
        CityP.setName("CityP");
        AP30.addCity(CityP);
        
        Flight F601 = new Flight();
        F601.setId("F601");
        F601.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        F601.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        F601.setDepartureAirport(AP28);
        F601.setArrialAirport(AP29);
        F601.setOpenForBooking(true);
        
        AL26.addFlight(F601);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(AP30);
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Execute
        boolean result = F601.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, F601.getStopovers().size());
        assertEquals("Stopover airport should match", AP30, F601.getStopovers().get(0).getAirport());
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airline AL27 = new Airline();
        
        Airport AP32 = new Airport();
        City CityQ = new City();
        CityQ.setName("CityQ");
        AP32.addCity(CityQ);
        
        Airport AP33 = new Airport();
        City CityR = new City();
        CityR.setName("CityR");
        AP33.addCity(CityR);
        
        Airport AP31 = new Airport();
        City CityS = new City();
        CityS.setName("CityS");
        AP31.addCity(CityS);
        
        Flight F602 = new Flight();
        F602.setId("F602");
        F602.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        F602.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        F602.setDepartureAirport(AP32);
        F602.setArrialAirport(AP33);
        F602.setOpenForBooking(true);
        
        AL27.addFlight(F602);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(AP31);
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Execute
        boolean result = F602.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside window", result);
        assertEquals("Flight should have 0 stopovers", 0, F602.getStopovers().size());
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws Exception {
        // Setup
        Airline AL28 = new Airline();
        
        Airport AP34 = new Airport();
        City CityT = new City();
        CityT.setName("CityT");
        AP34.addCity(CityT);
        
        Airport AP35 = new Airport();
        City CityU = new City();
        CityU.setName("CityU");
        AP35.addCity(CityU);
        
        Airport AP36 = new Airport();
        City CityV = new City();
        CityV.setName("CityV");
        AP36.addCity(CityV);
        
        Flight F603 = new Flight();
        F603.setId("F603");
        F603.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        F603.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        F603.setDepartureAirport(AP34);
        F603.setArrialAirport(AP35);
        F603.setOpenForBooking(true);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(AP34);
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        F603.getStopovers().add(existingStopover);
        
        AL28.addFlight(F603);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = F603.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have 0 stopovers after removal", 0, F603.getStopovers().size());
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws Exception {
        // Setup
        Airline AL29 = new Airline();
        
        Airport AP37 = new Airport();
        City CityW = new City();
        CityW.setName("CityW");
        AP37.addCity(CityW);
        
        Airport AP38 = new Airport();
        City CityX = new City();
        CityX.setName("CityX");
        AP38.addCity(CityX);
        
        Airport AP39 = new Airport();
        City CityY = new City();
        CityY.setName("CityY");
        AP39.addCity(CityY);
        
        Flight F604 = new Flight();
        F604.setId("F604");
        F604.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        F604.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        F604.setDepartureAirport(AP37);
        F604.setArrialAirport(AP38);
        F604.setOpenForBooking(false);
        
        AL29.addFlight(F604);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(AP37);
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Execute
        boolean result = F604.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have 0 stopovers", 0, F604.getStopovers().size());
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws Exception {
        // Setup
        Airline AL31 = new Airline();
        
        Airport AP42 = new Airport();
        City CityBB = new City();
        CityBB.setName("CityBB");
        AP42.addCity(CityBB);
        
        Airport AP43 = new Airport();
        City CityCC = new City();
        CityCC.setName("CityCC");
        AP43.addCity(CityCC);
        
        Airport AP44 = new Airport();
        City CityDD = new City();
        CityDD.setName("CityDD");
        AP44.addCity(CityDD);
        
        Flight F606 = new Flight();
        F606.setId("F606");
        F606.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        F606.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        F606.setDepartureAirport(AP42);
        F606.setArrialAirport(AP43);
        F606.setOpenForBooking(true);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(AP42);
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        F606.getStopovers().add(existingStopover);
        
        AL31.addFlight(F606);
        
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute
        boolean result = F606.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after flight departure", result);
        assertEquals("Flight should still have 1 stopover", 1, F606.getStopovers().size());
    }
}