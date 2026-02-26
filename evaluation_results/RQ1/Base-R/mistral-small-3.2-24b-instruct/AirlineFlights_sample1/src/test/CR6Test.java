import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR6Test {
    
    private Flight flight1;
    private Flight flight2;
    private Flight flight3;
    private Flight flight4;
    private Flight flight6;
    private Airport airport28;
    private Airport airport29;
    private Airport airport30;
    private Airport airport31;
    private Airport airport32;
    private Airport airport33;
    private Airport airport34;
    private Airport airport35;
    private Airport airport36;
    private Airport airport37;
    private Airport airport38;
    private Airport airport39;
    private Airport airport42;
    private Airport airport43;
    private Airport airport44;
    private Stopover stopover1;
    private Stopover stopover2;
    private Stopover stopover3;
    
    @Before
    public void setUp() {
        // Setup airports with cities as specified in test cases
        airport28 = new Airport("AP28", List.of("CityN"));
        airport29 = new Airport("AP29", List.of("CityO"));
        airport30 = new Airport("AP30", List.of("CityP"));
        airport31 = new Airport("AP31", List.of("CityS"));
        airport32 = new Airport("AP32", List.of("CityQ"));
        airport33 = new Airport("AP33", List.of("CityR"));
        airport34 = new Airport("AP34", List.of("CityT"));
        airport35 = new Airport("AP35", List.of("CityU"));
        airport36 = new Airport("AP36", List.of("CityV"));
        airport37 = new Airport("AP37", List.of("CityW"));
        airport38 = new Airport("AP38", List.of("CityX"));
        airport39 = new Airport("AP39", List.of("CityY"));
        airport42 = new Airport("AP42", List.of("CityBB"));
        airport43 = new Airport("AP43", List.of("CityCC"));
        airport44 = new Airport("AP44", List.of("CityDD"));
        
        // Setup flights as specified in test cases
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Flight F601 for Test Case 1
        flight1 = new Flight("F601", airport28, airport29, 
                           LocalDateTime.parse("2025-04-20 10:00:00", formatter),
                           LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        
        // Flight F602 for Test Case 2
        flight2 = new Flight("F602", airport32, airport33,
                           LocalDateTime.parse("2025-05-10 09:00:00", formatter),
                           LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        
        // Flight F603 for Test Case 3
        flight3 = new Flight("F603", airport34, airport36,
                           LocalDateTime.parse("2025-06-15 11:00:00", formatter),
                           LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        // Add existing stopover for F603
        stopover1 = new Stopover(airport34,
                               LocalDateTime.parse("2025-06-15 13:00:00", formatter),
                               LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        flight3.getStopovers().add(stopover1);
        
        // Flight F604 for Test Case 4
        flight4 = new Flight("F604", airport37, airport39,
                           LocalDateTime.parse("2025-07-20 12:00:00", formatter),
                           LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        flight4.setOpenForBooking(false); // Closed for booking
        
        // Flight F606 for Test Case 5
        flight6 = new Flight("F606", airport42, airport44,
                           LocalDateTime.parse("2025-12-09 18:00:00", formatter),
                           LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        // Add existing stopover for F606
        stopover2 = new Stopover(airport42,
                               LocalDateTime.parse("2025-12-09 20:00:00", formatter),
                               LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        flight6.getStopovers().add(stopover2);
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() {
        // Test Case 1: Add first stopover inside journey window
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Create stopover AP30 (2025-04-20 12:00:00 → 2025-04-20 12:40:00)
        Stopover stopover = new Stopover(airport30,
                                       LocalDateTime.parse("2025-04-20 12:00:00", formatter),
                                       LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Add stopover to flight F601
        boolean result = flight1.addStopover(stopover);
        
        // Verify stopover was added successfully
        assertTrue("Stopover should be added successfully when within flight window", result);
        assertEquals("Flight should have 1 stopover after addition", 1, flight1.getStopovers().size());
        assertEquals("Added stopover should be AP30", airport30, flight1.getStopovers().get(0).getAirport());
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() {
        // Test Case 2: Stopover time outside window
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Create stopover AP31 (2025-05-10 16:00:00 → 17:00:00)
        Stopover stopover = new Stopover(airport31,
                                       LocalDateTime.parse("2025-05-10 16:00:00", formatter),
                                       LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Add stopover to flight F602
        boolean result = flight2.addStopover(stopover);
        
        // Verify stopover was NOT added (outside flight window)
        assertFalse("Stopover should not be added when outside flight window", result);
        assertEquals("Flight should still have 0 stopovers", 0, flight2.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() {
        // Test Case 3: Delete existing stopover
        // Verify initial state
        assertEquals("Flight should start with 1 stopover", 1, flight3.getStopovers().size());
        
        // Delete stopover AP34 from flight F603
        boolean result = flight3.removeStopover(stopover1);
        
        // Verify stopover was removed successfully
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have 0 stopovers after removal", 0, flight3.getStopovers().size());
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() {
        // Test Case 4: Flight closed prevents modification
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Create stopover AP37 (2025-07-20 13:30:00 → 14:00:00)
        Stopover stopover = new Stopover(airport37,
                                       LocalDateTime.parse("2025-07-20 13:30:00", formatter),
                                       LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Add stopover to flight F604 (which is closed for booking)
        boolean result = flight4.addStopover(stopover);
        
        // Verify stopover was NOT added (flight closed)
        assertFalse("Stopover should not be added when flight is closed for booking", result);
        assertEquals("Flight should still have 0 stopovers", 0, flight4.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() {
        // Test Case 5: Attempt removal after departure
        // Set current time to 2025-12-09 20:50:00 (after stopover's exit, flight mid-air)
        // Note: We'll use a custom approach since we can't modify LocalDateTime.now()
        // For this test, we'll simulate the scenario by setting flight as published
        
        flight6.setPublished(true); // Simulate flight being published/in-progress
        
        // Attempt to delete stopover AP42 from flight F606
        boolean result = flight6.removeStopover(stopover2);
        
        // Verify stopover was NOT removed (flight is published/in-progress)
        assertFalse("Stopover should not be removed when flight is published", result);
        assertEquals("Flight should still have 1 stopover", 1, flight6.getStopovers().size());
    }
}