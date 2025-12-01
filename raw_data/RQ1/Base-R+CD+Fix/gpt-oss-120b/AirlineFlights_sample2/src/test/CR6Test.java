import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR6Test {
    
    private Airline airline;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airline = new Airline();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() {
        // Setup
        Airport ap28 = new Airport("AP28");
        Airport ap29 = new Airport("AP29");
        Airport ap30 = new Airport("AP30");
        
        City cityN = new City("CityN");
        City cityO = new City("CityO");
        City cityP = new City("CityP");
        
        ap28.addCity(cityN);
        ap29.addCity(cityO);
        ap30.addCity(cityP);
        
        Flight f601 = new Flight("F601");
        f601.setDepartureTime(LocalDateTime.parse("2025-04-20 10:00:00", formatter));
        f601.setArrivalTime(LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        f601.setOpenForBooking(true);
        
        airline.addFlight(f601);
        
        LocalDateTime now = LocalDateTime.parse("2025-04-19 09:00:00", formatter);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Execute
        boolean result = f601.addStopover(stopover, now);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have one stopover", 1, f601.getStopovers().size());
        assertEquals("Stopover airport should match", ap30, f601.getStopovers().get(0).getAirport());
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() {
        // Setup
        Airport ap32 = new Airport("AP32");
        Airport ap33 = new Airport("AP33");
        Airport ap31 = new Airport("AP31");
        
        City cityQ = new City("CityQ");
        City cityR = new City("CityR");
        City cityS = new City("CityS");
        
        ap32.addCity(cityQ);
        ap33.addCity(cityR);
        ap31.addCity(cityS);
        
        Flight f602 = new Flight("F602");
        f602.setDepartureTime(LocalDateTime.parse("2025-05-10 09:00:00", formatter));
        f602.setArrivalTime(LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        f602.setOpenForBooking(true);
        
        airline.addFlight(f602);
        
        LocalDateTime now = LocalDateTime.parse("2025-05-09 11:00:00", formatter);
        
        // Create stopover with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Execute
        boolean result = f602.addStopover(stopover, now);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside window", result);
        assertEquals("Flight should have no stopovers", 0, f602.getStopovers().size());
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() {
        // Setup
        Airport ap34 = new Airport("AP34");
        Airport ap35 = new Airport("AP35");
        Airport ap36 = new Airport("AP36");
        
        City cityT = new City("CityT");
        City cityU = new City("CityU");
        City cityV = new City("CityV");
        
        ap34.addCity(cityT);
        ap35.addCity(cityU);
        ap36.addCity(cityV);
        
        Flight f603 = new Flight("F603");
        f603.setDepartureTime(LocalDateTime.parse("2025-06-15 11:00:00", formatter));
        f603.setArrivalTime(LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        f603.setDepartureAirport(ap34);
        f603.setArrivalAirport(ap35);
        f603.setOpenForBooking(true);
        
        // Create and add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap36);
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        
        List<Stopover> stopovers = new ArrayList<>();
        stopovers.add(existingStopover);
        f603.setStopovers(stopovers);
        
        airline.addFlight(f603);
        
        LocalDateTime now = LocalDateTime.parse("2025-06-14 10:00:00", formatter);
        
        // Execute
        boolean result = f603.removeStopover(existingStopover, now);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have no stopovers after removal", 0, f603.getStopovers().size());
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() {
        // Setup
        Airport ap37 = new Airport("AP37");
        Airport ap38 = new Airport("AP38");
        Airport ap39 = new Airport("AP39");
        
        City cityW = new City("CityW");
        City cityX = new City("CityX");
        City cityY = new City("CityY");
        
        ap37.addCity(cityW);
        ap38.addCity(cityX);
        ap39.addCity(cityY);
        
        Flight f604 = new Flight("F604");
        f604.setDepartureTime(LocalDateTime.parse("2025-07-20 12:00:00", formatter));
        f604.setArrivalTime(LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        f604.setDepartureAirport(ap37);
        f604.setArrivalAirport(ap38);
        f604.setOpenForBooking(false); // Flight closed for booking
        
        airline.addFlight(f604);
        
        LocalDateTime now = LocalDateTime.parse("2025-07-10 09:00:00", formatter);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Execute
        boolean result = f604.addStopover(stopover, now);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have no stopovers", 0, f604.getStopovers().size());
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() {
        // Setup
        Airport ap42 = new Airport("AP42");
        Airport ap43 = new Airport("AP43");
        Airport ap44 = new Airport("AP44");
        
        City cityBB = new City("CityBB");
        City cityCC = new City("CityCC");
        City cityDD = new City("CityDD");
        
        ap42.addCity(cityBB);
        ap43.addCity(cityCC);
        ap44.addCity(cityDD);
        
        Flight f606 = new Flight("F606");
        f606.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        f606.setArrivalTime(LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        f606.setDepartureAirport(ap42);
        f606.setArrivalAirport(ap43);
        f606.setOpenForBooking(true);
        
        // Create and add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap44);
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        
        List<Stopover> stopovers = new ArrayList<>();
        stopovers.add(existingStopover);
        f606.setStopovers(stopovers);
        
        airline.addFlight(f606);
        
        LocalDateTime now = LocalDateTime.parse("2025-12-09 20:50:00", formatter); // After stopover departure
        
        // Execute
        boolean result = f606.removeStopover(existingStopover, now);
        
        // Verify
        assertFalse("Stopover should not be removed after flight departure", result);
        assertEquals("Flight should still have one stopover", 1, f606.getStopovers().size());
    }
}