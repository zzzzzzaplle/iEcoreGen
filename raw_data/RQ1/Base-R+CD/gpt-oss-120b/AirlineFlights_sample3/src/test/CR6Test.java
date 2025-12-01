import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR6Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    
    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() throws ParseException {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        
        // Create cities and associate with airports
        City cityN = new City();
        cityN.setName("CityN");
        City cityO = new City();
        cityO.setName("CityO");
        City cityP = new City();
        cityP.setName("CityP");
        
        ap28.addCity(cityN);
        ap29.addCity(cityO);
        ap30.addCity(cityP);
        
        // Create flight F601
        Flight flight = new Flight();
        flight.setId("F601");
        flight.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        flight.setDepartureAirport(ap28);
        flight.setArrialAirport(ap29);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        stopover.setAirport(ap30);
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Execute test
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have one stopover", 1, flight.getStopovers().size());
        assertTrue("Stopover should be in flight's stopover list", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws ParseException {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        
        // Create cities and associate with airports
        City cityQ = new City();
        cityQ.setName("CityQ");
        City cityR = new City();
        cityR.setName("CityR");
        City cityS = new City();
        cityS.setName("CityS");
        
        ap32.addCity(cityQ);
        ap33.addCity(cityR);
        ap31.addCity(cityS);
        
        // Create flight F602
        Flight flight = new Flight();
        flight.setId("F602");
        flight.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        flight.setDepartureAirport(ap32);
        flight.setArrialAirport(ap33);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create stopover with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        stopover.setAirport(ap31);
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Execute test
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside window", result);
        assertEquals("Flight should have no stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws ParseException {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        
        // Create cities and associate with airports
        City cityT = new City();
        cityT.setName("CityT");
        City cityU = new City();
        cityU.setName("CityU");
        City cityV = new City();
        cityV.setName("CityV");
        
        ap34.addCity(cityT);
        ap35.addCity(cityU);
        ap36.addCity(cityV);
        
        // Create flight F603
        Flight flight = new Flight();
        flight.setId("F603");
        flight.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        flight.setDepartureAirport(ap34);
        flight.setArrialAirport(ap35);
        flight.setOpenForBooking(true);
        
        // Create and add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        existingStopover.setAirport(ap36);
        
        flight.getStopovers().add(existingStopover);
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute test
        boolean result = flight.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have no stopovers after removal", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws ParseException {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        
        // Create cities and associate with airports
        City cityW = new City();
        cityW.setName("CityW");
        City cityX = new City();
        cityX.setName("CityX");
        City cityY = new City();
        cityY.setName("CityY");
        
        ap37.addCity(cityW);
        ap38.addCity(cityX);
        ap39.addCity(cityY);
        
        // Create flight F604 with openForBooking = false
        Flight flight = new Flight();
        flight.setId("F604");
        flight.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        flight.setDepartureAirport(ap37);
        flight.setArrialAirport(ap38);
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(flight);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        stopover.setAirport(ap39);
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Execute test
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have no stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws ParseException {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        
        // Create cities and associate with airports
        City cityBB = new City();
        cityBB.setName("CityBB");
        City cityCC = new City();
        cityCC.setName("CityCC");
        City cityDD = new City();
        cityDD.setName("CityDD");
        
        ap42.addCity(cityBB);
        ap43.addCity(cityCC);
        ap44.addCity(cityDD);
        
        // Create flight F606
        Flight flight = new Flight();
        flight.setId("F606");
        flight.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        flight.setDepartureAirport(ap42);
        flight.setArrialAirport(ap43);
        flight.setOpenForBooking(true);
        
        // Create and add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        existingStopover.setAirport(ap44);
        
        flight.getStopovers().add(existingStopover);
        airline.addFlight(flight);
        
        // Current time is after stopover's departure (flight is mid-air)
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute test
        boolean result = flight.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after flight departure", result);
        assertEquals("Flight should still have one stopover", 1, flight.getStopovers().size());
        assertTrue("Stopover should remain in flight's stopover list", flight.getStopovers().contains(existingStopover));
    }
}