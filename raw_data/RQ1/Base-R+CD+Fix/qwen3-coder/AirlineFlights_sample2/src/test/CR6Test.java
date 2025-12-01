import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR6Test {
    
    private SimpleDateFormat sdf;
    private Airline airline;
    private Flight flight;
    private Stopover stopover;
    private Airport airport;
    private City city;
    
    @Before
    public void setUp() throws Exception {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        City cityN = new City();
        ap28.addCity(cityN);
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        City cityO = new City();
        ap29.addCity(cityO);
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        City cityP = new City();
        ap30.addCity(cityP);
        
        // Create flight F601
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setDepartureTime(sdf.parse("2025-04-20 10:00:00"));
        f601.setArrivalTime(sdf.parse("2025-04-20 15:00:00"));
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        f601.setOpenForBooking(true);
        
        airline.addFlight(f601);
        
        // Create stopover for AP30
        Stopover stop = new Stopover();
        stop.setArrivalTime(sdf.parse("2025-04-20 12:00:00"));
        stop.setDepartureTime(sdf.parse("2025-04-20 12:40:00"));
        stop.setAirport(ap30);
        
        Date currentTime = sdf.parse("2025-04-19 09:00:00");
        
        // Execute
        boolean result = f601.addStopover(stop, currentTime);
        
        // Verify
        assertTrue("Stopover should be successfully added", result);
        assertTrue("Flight should contain the stopover", f601.getStopovers().contains(stop));
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        City cityS = new City();
        ap31.addCity(cityS);
        
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        City cityQ = new City();
        ap32.addCity(cityQ);
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        City cityR = new City();
        ap33.addCity(cityR);
        
        // Create flight F602
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setDepartureTime(sdf.parse("2025-05-10 09:00:00"));
        f602.setArrivalTime(sdf.parse("2025-05-10 14:00:00"));
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        f602.setOpenForBooking(true);
        
        airline.addFlight(f602);
        
        // Create stopover for AP31 with times outside flight window
        Stopover stop = new Stopover();
        stop.setArrivalTime(sdf.parse("2025-05-10 16:00:00"));
        stop.setDepartureTime(sdf.parse("2025-05-10 17:00:00"));
        stop.setAirport(ap31);
        
        Date currentTime = sdf.parse("2025-05-09 11:00:00");
        
        // Execute
        boolean result = f602.addStopover(stop, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside window", result);
        assertFalse("Flight should not contain the stopover", f602.getStopovers().contains(stop));
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        City cityT = new City();
        ap34.addCity(cityT);
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        City cityU = new City();
        ap35.addCity(cityU);
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        City cityV = new City();
        ap36.addCity(cityV);
        
        // Create flight F603
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setDepartureTime(sdf.parse("2025-06-15 11:00:00"));
        f603.setArrivalTime(sdf.parse("2025-06-15 18:00:00"));
        f603.setDepartureAirport(ap35);
        f603.setArrivalAirport(ap36);
        f603.setOpenForBooking(true);
        
        // Create and add existing stopover
        Stopover existingStop = new Stopover();
        existingStop.setArrivalTime(sdf.parse("2025-06-15 13:00:00"));
        existingStop.setDepartureTime(sdf.parse("2025-06-15 13:45:00"));
        existingStop.setAirport(ap34);
        
        f603.getStopovers().add(existingStop);
        airline.addFlight(f603);
        
        Date currentTime = sdf.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = f603.removeStopover(existingStop, currentTime);
        
        // Verify
        assertTrue("Stopover should be successfully removed", result);
        assertFalse("Flight should not contain the stopover", f603.getStopovers().contains(existingStop));
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        City cityW = new City();
        ap37.addCity(cityW);
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        City cityX = new City();
        ap38.addCity(cityX);
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        City cityY = new City();
        ap39.addCity(cityY);
        
        // Create flight F604 with openForBooking = false
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setDepartureTime(sdf.parse("2025-07-20 12:00:00"));
        f604.setArrivalTime(sdf.parse("2025-07-20 17:00:00"));
        f604.setDepartureAirport(ap38);
        f604.setArrivalAirport(ap39);
        f604.setOpenForBooking(false);
        
        airline.addFlight(f604);
        
        // Create stopover for AP37
        Stopover stop = new Stopover();
        stop.setArrivalTime(sdf.parse("2025-07-20 13:30:00"));
        stop.setDepartureTime(sdf.parse("2025-07-20 14:00:00"));
        stop.setAirport(ap37);
        
        Date currentTime = sdf.parse("2025-07-10 09:00:00");
        
        // Execute
        boolean result = f604.addStopover(stop, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to closed flight", result);
        assertFalse("Flight should not contain the stopover", f604.getStopovers().contains(stop));
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        City cityBB = new City();
        ap42.addCity(cityBB);
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        City cityCC = new City();
        ap43.addCity(cityCC);
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        City cityDD = new City();
        ap44.addCity(cityDD);
        
        // Create flight F606
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setDepartureTime(sdf.parse("2025-12-09 18:00:00"));
        f606.setArrivalTime(sdf.parse("2025-12-10 00:00:00"));
        f606.setDepartureAirport(ap43);
        f606.setArrivalAirport(ap44);
        f606.setOpenForBooking(true);
        
        // Create and add existing stopover
        Stopover existingStop = new Stopover();
        existingStop.setArrivalTime(sdf.parse("2025-12-09 20:00:00"));
        existingStop.setDepartureTime(sdf.parse("2025-12-09 20:45:00"));
        existingStop.setAirport(ap42);
        
        f606.getStopovers().add(existingStop);
        airline.addFlight(f606);
        
        Date currentTime = sdf.parse("2025-12-09 20:50:00"); // After stopover's departure
        
        // Execute
        boolean result = f606.removeStopover(existingStop, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after departure", result);
        assertTrue("Flight should still contain the stopover", f606.getStopovers().contains(existingStop));
    }
}