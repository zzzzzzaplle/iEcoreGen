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
        
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        f601.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        f601.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        f601.setOpenForBooking(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Execute
        boolean result = f601.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertTrue("Stopover should be in the flight's stopovers list", f601.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        City cityQ = new City();
        ap32.addCity(cityQ);
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        City cityR = new City();
        ap33.addCity(cityR);
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        City cityS = new City();
        ap31.addCity(cityS);
        
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        f602.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        f602.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        f602.setOpenForBooking(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Execute
        boolean result = f602.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover outside flight schedule should not be added", result);
        assertFalse("Stopover should not be in the flight's stopovers list", f602.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase3_deleteExistingStopover() throws Exception {
        // Setup
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
        
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setDepartureAirport(ap34);
        f603.setArrivalAirport(ap35);
        f603.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        f603.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        f603.setOpenForBooking(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap36);
        stopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        f603.getStopovers().add(stopover);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = f603.removeStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertFalse("Stopover should not be in the flight's stopovers list", f603.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() throws Exception {
        // Setup
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
        
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setDepartureAirport(ap37);
        f604.setArrivalAirport(ap38);
        f604.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        f604.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        f604.setOpenForBooking(false);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Execute
        boolean result = f604.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertFalse("Stopover should not be in the flight's stopovers list", f604.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws Exception {
        // Setup
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
        
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setDepartureAirport(ap42);
        f606.setArrivalAirport(ap43);
        f606.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        f606.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        f606.setOpenForBooking(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap44);
        stopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        f606.getStopovers().add(stopover);
        
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute
        boolean result = f606.removeStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after flight departure", result);
        assertTrue("Stopover should remain in the flight's stopovers list", f606.getStopovers().contains(stopover));
    }
}