import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void testCase1_addFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        City cityN = new City();
        cityN.setName("CityN");
        ap28.addCity(cityN);
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        City cityO = new City();
        cityO.setName("CityO");
        ap29.addCity(cityO);
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        City cityP = new City();
        cityP.setName("CityP");
        ap30.addCity(cityP);
        
        // Create flight F601
        Flight flight = new Flight();
        flight.setId("F601");
        flight.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        flight.setDepartureAirport(ap28);
        flight.setArrivalAirport(ap29);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        stopover.setAirport(ap30);
        
        // Test
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, flight.getStopovers().size());
        assertTrue("Stopover should be in flight's stopovers list", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        City cityQ = new City();
        cityQ.setName("CityQ");
        ap32.addCity(cityQ);
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        City cityR = new City();
        cityR.setName("CityR");
        ap33.addCity(cityR);
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        City cityS = new City();
        cityS.setName("CityS");
        ap31.addCity(cityS);
        
        // Create flight F602
        Flight flight = new Flight();
        flight.setId("F602");
        flight.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        flight.setDepartureAirport(ap32);
        flight.setArrivalAirport(ap33);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Create stopover with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        stopover.setAirport(ap31);
        
        // Test
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside window", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        City cityT = new City();
        cityT.setName("CityT");
        ap34.addCity(cityT);
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        City cityU = new City();
        cityU.setName("CityU");
        ap35.addCity(cityU);
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        City cityV = new City();
        cityV.setName("CityV");
        ap36.addCity(cityV);
        
        // Create flight F603
        Flight flight = new Flight();
        flight.setId("F603");
        flight.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        flight.setDepartureAirport(ap34);
        flight.setArrivalAirport(ap35);
        flight.setOpenForBooking(true);
        
        // Create and add existing stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        stopover.setAirport(ap36);
        
        List<Stopover> stopovers = new ArrayList<>();
        stopovers.add(stopover);
        flight.setStopovers(stopovers);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Test
        boolean result = flight.removeStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have 0 stopovers after removal", 0, flight.getStopovers().size());
        assertFalse("Stopover should not be in flight's stopovers list", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        City cityW = new City();
        cityW.setName("CityW");
        ap37.addCity(cityW);
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        City cityX = new City();
        cityX.setName("CityX");
        ap38.addCity(cityX);
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        City cityY = new City();
        cityY.setName("CityY");
        ap39.addCity(cityY);
        
        // Create flight F604 with openForBooking = false
        Flight flight = new Flight();
        flight.setId("F604");
        flight.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        flight.setDepartureAirport(ap37);
        flight.setArrivalAirport(ap38);
        flight.setOpenForBooking(false);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        stopover.setAirport(ap39);
        
        // Test
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        City cityBB = new City();
        cityBB.setName("CityBB");
        ap42.addCity(cityBB);
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        City cityCC = new City();
        cityCC.setName("CityCC");
        ap43.addCity(cityCC);
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        City cityDD = new City();
        cityDD.setName("CityDD");
        ap44.addCity(cityDD);
        
        // Create flight F606
        Flight flight = new Flight();
        flight.setId("F606");
        flight.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        flight.setDepartureAirport(ap42);
        flight.setArrivalAirport(ap43);
        flight.setOpenForBooking(true);
        
        // Create and add existing stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        stopover.setAirport(ap44);
        
        List<Stopover> stopovers = new ArrayList<>();
        stopovers.add(stopover);
        flight.setStopovers(stopovers);
        
        airline.addFlight(flight);
        
        // Current time after stopover departure (flight is mid-air)
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Test
        boolean result = flight.removeStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after departure", result);
        assertEquals("Flight should still have 1 stopover", 1, flight.getStopovers().size());
        assertTrue("Stopover should remain in flight's stopovers list", flight.getStopovers().contains(stopover));
    }
}