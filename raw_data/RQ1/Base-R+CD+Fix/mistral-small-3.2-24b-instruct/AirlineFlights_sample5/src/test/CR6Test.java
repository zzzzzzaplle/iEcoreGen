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
    private Airport airport;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() throws ParseException {
        // Setup
        airline = new Airline();
        
        Airport ap28 = new Airport();
        City cityN = new City();
        cityN.setName("CityN");
        ap28.addCity(cityN);
        
        Airport ap29 = new Airport();
        City cityO = new City();
        cityO.setName("CityO");
        ap29.addCity(cityO);
        
        Airport ap30 = new Airport();
        City cityP = new City();
        cityP.setName("CityP");
        ap30.addCity(cityP);
        
        flight = new Flight();
        flight.setId("F601");
        flight.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        flight.setDepartureAirport(ap28);
        flight.setArrialAirport(ap29);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, flight.getStopovers().size());
        assertTrue("Stopover should be in flight's stopovers list", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws ParseException {
        // Setup
        airline = new Airline();
        
        Airport ap32 = new Airport();
        City cityQ = new City();
        cityQ.setName("CityQ");
        ap32.addCity(cityQ);
        
        Airport ap33 = new Airport();
        City cityR = new City();
        cityR.setName("CityR");
        ap33.addCity(cityR);
        
        Airport ap31 = new Airport();
        City cityS = new City();
        cityS.setName("CityS");
        ap31.addCity(cityS);
        
        flight = new Flight();
        flight.setId("F602");
        flight.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        flight.setDepartureAirport(ap32);
        flight.setArrialAirport(ap33);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside window", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws ParseException {
        // Setup
        airline = new Airline();
        
        Airport ap34 = new Airport();
        City cityT = new City();
        cityT.setName("CityT");
        ap34.addCity(cityT);
        
        Airport ap35 = new Airport();
        City cityU = new City();
        cityU.setName("CityU");
        ap35.addCity(cityU);
        
        Airport ap36 = new Airport();
        City cityV = new City();
        cityV.setName("CityV");
        ap36.addCity(cityV);
        
        flight = new Flight();
        flight.setId("F603");
        flight.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        flight.setDepartureAirport(ap34);
        flight.setArrialAirport(ap35);
        flight.setOpenForBooking(true);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap36);
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:45:00"));
        flight.getStopovers().add(existingStopover);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = flight.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
        assertFalse("Stopover should not be in flight's stopovers list", flight.getStopovers().contains(existingStopover));
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws ParseException {
        // Setup
        airline = new Airline();
        
        Airport ap37 = new Airport();
        City cityW = new City();
        cityW.setName("CityW");
        ap37.addCity(cityW);
        
        Airport ap38 = new Airport();
        City cityX = new City();
        cityX.setName("CityX");
        ap38.addCity(cityX);
        
        Airport ap39 = new Airport();
        City cityY = new City();
        cityY.setName("CityY");
        ap39.addCity(cityY);
        
        flight = new Flight();
        flight.setId("F604");
        flight.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        flight.setDepartureAirport(ap37);
        flight.setArrialAirport(ap38);
        flight.setOpenForBooking(false);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to flight being closed", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws ParseException {
        // Setup
        airline = new Airline();
        
        Airport ap42 = new Airport();
        City cityBB = new City();
        cityBB.setName("CityBB");
        ap42.addCity(cityBB);
        
        Airport ap43 = new Airport();
        City cityCC = new City();
        cityCC.setName("CityCC");
        ap43.addCity(cityCC);
        
        Airport ap44 = new Airport();
        City cityDD = new City();
        cityDD.setName("CityDD");
        ap44.addCity(cityDD);
        
        flight = new Flight();
        flight.setId("F606");
        flight.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        flight.setDepartureAirport(ap42);
        flight.setArrialAirport(ap43);
        flight.setOpenForBooking(true);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap44);
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:45:00"));
        flight.getStopovers().add(existingStopover);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute
        boolean result = flight.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after departure", result);
        assertEquals("Flight should still have 1 stopover", 1, flight.getStopovers().size());
        assertTrue("Stopover should still be in flight's stopovers list", flight.getStopovers().contains(existingStopover));
    }
}