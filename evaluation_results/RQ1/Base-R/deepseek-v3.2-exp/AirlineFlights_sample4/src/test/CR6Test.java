import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

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
        Set<String> cities28 = new HashSet<>();
        cities28.add("CityN");
        ap28.setServedCities(cities28);
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        Set<String> cities29 = new HashSet<>();
        cities29.add("CityO");
        ap29.setServedCities(cities29);
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        Set<String> cities30 = new HashSet<>();
        cities30.add("CityP");
        ap30.setServedCities(cities30);
        
        Flight flight = new Flight();
        flight.setId("F601");
        flight.setDepartureAirport(ap28);
        flight.setArrivalAirport(ap29);
        flight.setDepartureTime(LocalDateTime.parse("2025-04-20 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        flight.setOpenForBooking(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Mock current time = 2025-04-19 09:00:00 by setting flight departure in future
        // The actual validation uses LocalDateTime.now(), but for testing we rely on the temporal logic
        
        // Test
        boolean result = flight.addStopover(stopover);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have exactly 1 stopover", 1, flight.getStopovers().size());
        assertTrue("Stopover should be in flight's stopovers list", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() {
        // Setup
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        Set<String> cities32 = new HashSet<>();
        cities32.add("CityQ");
        ap32.setServedCities(cities32);
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        Set<String> cities33 = new HashSet<>();
        cities33.add("CityR");
        ap33.setServedCities(cities33);
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        Set<String> cities31 = new HashSet<>();
        cities31.add("CityS");
        ap31.setServedCities(cities31);
        
        Flight flight = new Flight();
        flight.setId("F602");
        flight.setDepartureAirport(ap32);
        flight.setArrivalAirport(ap33);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        flight.setOpenForBooking(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Mock current time = 2025-05-09 11:00:00 by setting flight departure in future
        
        // Test
        boolean result = flight.addStopover(stopover);
        
        // Verify
        assertFalse("Stopover outside flight schedule should not be added", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() {
        // Setup
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        Set<String> cities34 = new HashSet<>();
        cities34.add("CityT");
        ap34.setServedCities(cities34);
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        Set<String> cities35 = new HashSet<>();
        cities35.add("CityU");
        ap35.setServedCities(cities35);
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        Set<String> cities36 = new HashSet<>();
        cities36.add("CityV");
        ap36.setServedCities(cities36);
        
        Flight flight = new Flight();
        flight.setId("F603");
        flight.setDepartureAirport(ap35);
        flight.setArrivalAirport(ap36);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-15 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        flight.setOpenForBooking(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap34);
        stopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        
        // Add the stopover first
        flight.getStopovers().add(stopover);
        
        // Mock current time = 2025-06-14 10:00:00 by setting flight departure in future
        
        // Test
        boolean result = flight.deleteStopover(stopover);
        
        // Verify
        assertTrue("Existing stopover should be deleted successfully", result);
        assertEquals("Flight should have 0 stopovers after deletion", 0, flight.getStopovers().size());
        assertFalse("Stopover should not be in flight's stopovers list", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() {
        // Setup
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        Set<String> cities37 = new HashSet<>();
        cities37.add("CityW");
        ap37.setServedCities(cities37);
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        Set<String> cities38 = new HashSet<>();
        cities38.add("CityX");
        ap38.setServedCities(cities38);
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        Set<String> cities39 = new HashSet<>();
        cities39.add("CityY");
        ap39.setServedCities(cities39);
        
        Flight flight = new Flight();
        flight.setId("F604");
        flight.setDepartureAirport(ap38);
        flight.setArrivalAirport(ap39);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-20 12:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        flight.setOpenForBooking(false); // Flight is closed
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap37);
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Mock current time = 2025-07-10 09:00:00 by setting flight departure in future
        
        // Test
        boolean result = flight.addStopover(stopover);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() {
        // Setup
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        Set<String> cities42 = new HashSet<>();
        cities42.add("CityBB");
        ap42.setServedCities(cities42);
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        Set<String> cities43 = new HashSet<>();
        cities43.add("CityCC");
        ap43.setServedCities(cities43);
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        Set<String> cities44 = new HashSet<>();
        cities44.add("CityDD");
        ap44.setServedCities(cities44);
        
        Flight flight = new Flight();
        flight.setId("F606");
        flight.setDepartureAirport(ap43);
        flight.setArrivalAirport(ap44);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        flight.setOpenForBooking(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap42);
        stopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        
        // Add the stopover first
        flight.getStopovers().add(stopover);
        
        // Mock current time = 2025-12-09 20:50:00 by setting flight departure in the past
        // Since we can't mock LocalDateTime.now(), we rely on the fact that the flight has departed
        // (current time after departure time)
        
        // Test - deletion should fail because flight has departed
        boolean result = flight.deleteStopover(stopover);
        
        // Verify
        assertFalse("Stopover should not be deleted after flight departure", result);
        assertEquals("Flight should still have 1 stopover", 1, flight.getStopovers().size());
        assertTrue("Stopover should remain in flight's stopovers list", flight.getStopovers().contains(stopover));
    }
}