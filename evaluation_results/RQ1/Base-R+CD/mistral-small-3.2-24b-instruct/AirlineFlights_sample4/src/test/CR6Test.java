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
        
        // Create cities
        City cityN = new City();
        cityN.setName("CityN");
        City cityO = new City();
        cityO.setName("CityO");
        City cityP = new City();
        cityP.setName("CityP");
        
        // Create airports and link cities
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        ap28.addCity(cityN);
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        ap29.addCity(cityO);
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        ap30.addCity(cityP);
        
        // Create flight F601
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setOpenForBooking(true);
        f601.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        f601.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        f601.setDepartureAirport(ap28);
        f601.setArrialAirport(ap29);
        
        airline.addFlight(f601);
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        // Execute test
        boolean result = f601.addStopover(stopover, currentTime);
        
        // Verify result
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have exactly 1 stopover", 1, f601.getStopovers().size());
        assertEquals("Stopover airport should be AP30", ap30, f601.getStopovers().get(0).getAirport());
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create cities
        City cityQ = new City();
        cityQ.setName("CityQ");
        City cityR = new City();
        cityR.setName("CityR");
        City cityS = new City();
        cityS.setName("CityS");
        
        // Create airports and link cities
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        ap32.addCity(cityQ);
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        ap33.addCity(cityR);
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        ap31.addCity(cityS);
        
        // Create flight F602
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setOpenForBooking(true);
        f602.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        f602.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        f602.setDepartureAirport(ap32);
        f602.setArrialAirport(ap33);
        
        airline.addFlight(f602);
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Create stopover with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        // Execute test
        boolean result = f602.addStopover(stopover, currentTime);
        
        // Verify result
        assertFalse("Stopover should not be added due to time outside window", result);
        assertEquals("Flight should have 0 stopovers", 0, f602.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create cities
        City cityT = new City();
        cityT.setName("CityT");
        City cityU = new City();
        cityU.setName("CityU");
        City cityV = new City();
        cityV.setName("CityV");
        
        // Create airports and link cities
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        ap34.addCity(cityT);
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        ap35.addCity(cityU);
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        ap36.addCity(cityV);
        
        // Create flight F603
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setOpenForBooking(true);
        f603.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        f603.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        f603.setDepartureAirport(ap34);
        f603.setArrialAirport(ap35);
        
        // Add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap36);
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:45:00"));
        f603.getStopovers().add(existingStopover);
        
        airline.addFlight(f603);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute test
        boolean result = f603.removeStopover(existingStopover, currentTime);
        
        // Verify result
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have 0 stopovers after removal", 0, f603.getStopovers().size());
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create cities
        City cityW = new City();
        cityW.setName("CityW");
        City cityX = new City();
        cityX.setName("CityX");
        City cityY = new City();
        cityY.setName("CityY");
        
        // Create airports and link cities
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        ap37.addCity(cityW);
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        ap38.addCity(cityX);
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        ap39.addCity(cityY);
        
        // Create flight F604 with openForBooking = false
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setOpenForBooking(false);
        f604.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        f604.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        f604.setDepartureAirport(ap37);
        f604.setArrialAirport(ap38);
        
        airline.addFlight(f604);
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        // Execute test
        boolean result = f604.addStopover(stopover, currentTime);
        
        // Verify result
        assertFalse("Stopover should not be added due to closed flight", result);
        assertEquals("Flight should have 0 stopovers", 0, f604.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create cities
        City cityBB = new City();
        cityBB.setName("CityBB");
        City cityCC = new City();
        cityCC.setName("CityCC");
        City cityDD = new City();
        cityDD.setName("CityDD");
        
        // Create airports and link cities
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        ap42.addCity(cityBB);
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        ap43.addCity(cityCC);
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        ap44.addCity(cityDD);
        
        // Create flight F606
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setOpenForBooking(true);
        f606.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        f606.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        f606.setDepartureAirport(ap42);
        f606.setArrialAirport(ap43);
        
        // Add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap44);
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:45:00"));
        f606.getStopovers().add(existingStopover);
        
        airline.addFlight(f606);
        
        // Current time after stopover's departure but before flight arrival
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute test
        boolean result = f606.removeStopover(existingStopover, currentTime);
        
        // Verify result
        assertFalse("Stopover should not be removed after flight departure", result);
        assertEquals("Flight should still have 1 stopover", 1, f606.getStopovers().size());
    }
}