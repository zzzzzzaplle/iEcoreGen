import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR6Test {
    
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() {
        // Setup
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        List<String> cities28 = new ArrayList<>();
        cities28.add("CityN");
        ap28.setCities(cities28);
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        List<String> cities29 = new ArrayList<>();
        cities29.add("CityO");
        ap29.setCities(cities29);
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        List<String> cities30 = new ArrayList<>();
        cities30.add("CityP");
        ap30.setCities(cities30);
        
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        f601.setDepartureTime(LocalDateTime.parse("2025-04-20 10:00:00", formatter));
        f601.setArrivalTime(LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        f601.setOpenForBooking(true);
        f601.setPublished(false);
        
        airlineSystem.getFlights().add(f601);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Set current time to 2025-04-19 09:00:00
        // Note: In real implementation, we would need to mock LocalDateTime.now()
        // For this test, we assume the current time is correctly handled by the implementation
        
        // Execute
        boolean result = airlineSystem.addStopover(f601, stopover);
        
        // Verify
        assertTrue("Stopover should be added successfully when times are within flight schedule", result);
        assertEquals("Flight should have 1 stopover after addition", 1, f601.getStopovers().size());
        assertEquals("Added stopover should be AP30", "AP30", f601.getStopovers().get(0).getAirport().getId());
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() {
        // Setup
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        List<String> cities32 = new ArrayList<>();
        cities32.add("CityQ");
        ap32.setCities(cities32);
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        List<String> cities33 = new ArrayList<>();
        cities33.add("CityR");
        ap33.setCities(cities33);
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        List<String> cities31 = new ArrayList<>();
        cities31.add("CityS");
        ap31.setCities(cities31);
        
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        f602.setDepartureTime(LocalDateTime.parse("2025-05-10 09:00:00", formatter));
        f602.setArrivalTime(LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        f602.setOpenForBooking(true);
        f602.setPublished(false);
        
        airlineSystem.getFlights().add(f602);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Set current time to 2025-05-09 11:00:00
        // Note: In real implementation, we would need to mock LocalDateTime.now()
        
        // Execute
        boolean result = airlineSystem.addStopover(f602, stopover);
        
        // Verify
        assertFalse("Stopover should fail when times are outside flight schedule", result);
        assertEquals("Flight should have 0 stopovers after failed addition", 0, f602.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() {
        // Setup
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        List<String> cities34 = new ArrayList<>();
        cities34.add("CityT");
        ap34.setCities(cities34);
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        List<String> cities35 = new ArrayList<>();
        cities35.add("CityU");
        ap35.setCities(cities35);
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        List<String> cities36 = new ArrayList<>();
        cities36.add("CityV");
        ap36.setCities(cities36);
        
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setDepartureAirport(ap34);
        f603.setArrivalAirport(ap35);
        f603.setDepartureTime(LocalDateTime.parse("2025-06-15 11:00:00", formatter));
        f603.setArrivalTime(LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        f603.setOpenForBooking(true);
        f603.setPublished(false);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap36);
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        
        List<Stopover> stopovers = new ArrayList<>();
        stopovers.add(existingStopover);
        f603.setStopovers(stopovers);
        
        airlineSystem.getFlights().add(f603);
        
        // Set current time to 2025-06-14 10:00:00
        // Note: In real implementation, we would need to mock LocalDateTime.now()
        
        // Execute
        boolean result = airlineSystem.deleteStopover(f603, existingStopover);
        
        // Verify
        assertTrue("Stopover should be deleted successfully before flight departure", result);
        assertEquals("Flight should have 0 stopovers after deletion", 0, f603.getStopovers().size());
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() {
        // Setup
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        List<String> cities37 = new ArrayList<>();
        cities37.add("CityW");
        ap37.setCities(cities37);
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        List<String> cities38 = new ArrayList<>();
        cities38.add("CityX");
        ap38.setCities(cities38);
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        List<String> cities39 = new ArrayList<>();
        cities39.add("CityY");
        ap39.setCities(cities39);
        
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setDepartureAirport(ap37);
        f604.setArrivalAirport(ap38);
        f604.setDepartureTime(LocalDateTime.parse("2025-07-20 12:00:00", formatter));
        f604.setArrivalTime(LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        f604.setOpenForBooking(false); // Flight is closed
        f604.setPublished(false);
        
        airlineSystem.getFlights().add(f604);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Set current time to 2025-07-10 09:00:00
        // Note: In real implementation, we would need to mock LocalDateTime.now()
        
        // Execute
        boolean result = airlineSystem.addStopover(f604, stopover);
        
        // Verify
        assertFalse("Stopover should fail when flight is closed for booking", result);
        assertEquals("Flight should have 0 stopovers after failed addition", 0, f604.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() {
        // Setup
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        List<String> cities42 = new ArrayList<>();
        cities42.add("CityBB");
        ap42.setCities(cities42);
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        List<String> cities43 = new ArrayList<>();
        cities43.add("CityCC");
        ap43.setCities(cities43);
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        List<String> cities44 = new ArrayList<>();
        cities44.add("CityDD");
        ap44.setCities(cities44);
        
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setDepartureAirport(ap42);
        f606.setArrivalAirport(ap43);
        f606.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        f606.setArrivalTime(LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        f606.setOpenForBooking(true);
        f606.setPublished(false);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap44);
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        
        List<Stopover> stopovers = new ArrayList<>();
        stopovers.add(existingStopover);
        f606.setStopovers(stopovers);
        
        airlineSystem.getFlights().add(f606);
        
        // Set current time to 2025-12-09 20:50:00 (after stopover's exit, flight mid-air)
        // Note: In real implementation, we would need to mock LocalDateTime.now()
        
        // Execute
        boolean result = airlineSystem.deleteStopover(f606, existingStopover);
        
        // Verify
        assertFalse("Stopover deletion should fail when attempted after departure", result);
        assertEquals("Flight should still have 1 stopover after failed deletion", 1, f606.getStopovers().size());
    }
}