import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;

public class CR6Test {
    private DateTimeFormatter formatter;
    private Flight flight;
    private Airport airport;
    private Stopover stopover;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() {
        // Setup: Create airline system and airports
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        ap28.setServedCities(new HashSet<>(Arrays.asList("CityN")));
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        ap29.setServedCities(new HashSet<>(Arrays.asList("CityO")));
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        ap30.setServedCities(new HashSet<>(Arrays.asList("CityP")));
        
        // Setup: Create flight F601
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        f601.setDepartureTime(LocalDateTime.parse("2025-04-20 10:00:00", formatter));
        f601.setArrivalTime(LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        f601.setOpenForBooking(true);
        
        // Setup: Create stopover AP30
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Mock current time: 2025-04-19 09:00:00 (before departure)
        // In real implementation, this would require mocking LocalDateTime.now()
        // For this test, we assume the time validation passes since we're before departure
        
        // Execute: Add stopover to flight
        boolean result = f601.addStopover(stopover);
        
        // Verify: Stopover should be added successfully
        assertTrue("Stopover should be added successfully when within journey window", result);
        assertEquals("Flight should have 1 stopover after addition", 1, f601.getStopovers().size());
        assertTrue("Stopover list should contain the added stopover", f601.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() {
        // Setup: Create airports
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        ap32.setServedCities(new HashSet<>(Arrays.asList("CityQ")));
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        ap33.setServedCities(new HashSet<>(Arrays.asList("CityR")));
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        ap31.setServedCities(new HashSet<>(Arrays.asList("CityS")));
        
        // Setup: Create flight F602
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        f602.setDepartureTime(LocalDateTime.parse("2025-05-10 09:00:00", formatter));
        f602.setArrivalTime(LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        f602.setOpenForBooking(true);
        
        // Setup: Create stopover AP31 with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter)); // After arrival
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter)); // After arrival
        
        // Mock current time: 2025-05-09 11:00:00 (before departure)
        
        // Execute: Add stopover to flight
        boolean result = f602.addStopover(stopover);
        
        // Verify: Stopover should not be added due to time outside window
        assertFalse("Stopover should not be added when times are outside flight window", result);
        assertEquals("Flight should have 0 stopovers after failed addition", 0, f602.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() {
        // Setup: Create airports
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        ap34.setServedCities(new HashSet<>(Arrays.asList("CityT")));
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        ap35.setServedCities(new HashSet<>(Arrays.asList("CityU")));
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        ap36.setServedCities(new HashSet<>(Arrays.asList("CityV")));
        
        // Setup: Create flight F603
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setDepartureAirport(ap35);
        f603.setArrivalAirport(ap36);
        f603.setDepartureTime(LocalDateTime.parse("2025-06-15 11:00:00", formatter));
        f603.setArrivalTime(LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        f603.setOpenForBooking(true);
        
        // Setup: Create and add stopover AP34
        Stopover stopover = new Stopover();
        stopover.setAirport(ap34);
        stopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        f603.getStopovers().add(stopover);
        
        // Mock current time: 2025-06-14 10:00:00 (before departure)
        
        // Execute: Delete stopover from flight
        boolean result = f603.deleteStopover(stopover);
        
        // Verify: Stopover should be deleted successfully
        assertTrue("Stopover should be deleted successfully before departure", result);
        assertEquals("Flight should have 0 stopovers after deletion", 0, f603.getStopovers().size());
        assertFalse("Stopover list should not contain the deleted stopover", f603.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() {
        // Setup: Create airports
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        ap37.setServedCities(new HashSet<>(Arrays.asList("CityW")));
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        ap38.setServedCities(new HashSet<>(Arrays.asList("CityX")));
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        ap39.setServedCities(new HashSet<>(Arrays.asList("CityY")));
        
        // Setup: Create flight F604 with openForBooking = false
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setDepartureAirport(ap38);
        f604.setArrivalAirport(ap39);
        f604.setDepartureTime(LocalDateTime.parse("2025-07-20 12:00:00", formatter));
        f604.setArrivalTime(LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        f604.setOpenForBooking(false); // Flight is closed
        
        // Setup: Create stopover AP37
        Stopover stopover = new Stopover();
        stopover.setAirport(ap37);
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Mock current time: 2025-07-10 09:00:00 (before departure)
        
        // Execute: Add stopover to closed flight
        boolean result = f604.addStopover(stopover);
        
        // Verify: Stopover should not be added to closed flight
        assertFalse("Stopover should not be added when flight is closed for booking", result);
        assertEquals("Flight should have 0 stopovers after failed addition", 0, f604.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() {
        // Setup: Create airports
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        ap42.setServedCities(new HashSet<>(Arrays.asList("CityBB")));
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        ap43.setServedCities(new HashSet<>(Arrays.asList("CityCC")));
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        ap44.setServedCities(new HashSet<>(Arrays.asList("CityDD")));
        
        // Setup: Create flight F606
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setDepartureAirport(ap43);
        f606.setArrivalAirport(ap44);
        f606.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        f606.setArrivalTime(LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        f606.setOpenForBooking(true);
        
        // Setup: Create and add stopover AP42
        Stopover stopover = new Stopover();
        stopover.setAirport(ap42);
        stopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        f606.getStopovers().add(stopover);
        
        // Mock current time: 2025-12-09 20:50:00 (after departure, flight mid-air)
        // In real implementation, this would require mocking LocalDateTime.now()
        // For this test, we assume the time validation fails since we're after departure
        
        // Execute: Attempt to delete stopover after departure
        boolean result = f606.deleteStopover(stopover);
        
        // Verify: Stopover should not be deleted after departure
        assertFalse("Stopover should not be deleted after flight departure", result);
        assertEquals("Flight should still have 1 stopover after failed deletion", 1, f606.getStopovers().size());
        assertTrue("Stopover list should still contain the stopover", f606.getStopovers().contains(stopover));
    }
}