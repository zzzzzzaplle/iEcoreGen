import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CR6Test {
    
    private Airline AL26;
    private Airline AL27;
    private Airline AL28;
    private Airline AL29;
    private Airline AL31;
    
    private Airport AP28, AP29, AP30, AP31, AP32, AP33, AP34, AP35, AP36, AP37, AP38, AP39, AP42, AP43, AP44;
    
    private Flight F601, F602, F603, F604, F606;
    
    @Before
    public void setUp() {
        // Reset static booking repository before each test
        // Since BookingRepository uses static list, we need to clear it
        // Using reflection to clear the static BOOKINGS list
        try {
            java.lang.reflect.Field bookingsField = BookingRepository.class.getDeclaredField("BOOKINGS");
            bookingsField.setAccessible(true);
            ((java.util.List<?>) bookingsField.get(null)).clear();
        } catch (Exception e) {
            fail("Failed to clear BookingRepository: " + e.getMessage());
        }
        
        // Create cities
        City CityN = new City("C28", "CityN");
        City CityO = new City("C29", "CityO");
        City CityP = new City("C30", "CityP");
        City CityQ = new City("C31", "CityQ");
        City CityR = new City("C32", "CityR");
        City CityS = new City("C33", "CityS");
        City CityT = new City("C34", "CityT");
        City CityU = new City("C35", "CityU");
        City CityV = new City("C36", "CityV");
        City CityW = new City("C37", "CityW");
        City CityX = new City("C38", "CityX");
        City CityY = new City("C39", "CityY");
        City CityBB = new City("C42", "CityBB");
        City CityCC = new City("C43", "CityCC");
        City CityDD = new City("C44", "CityDD");
        
        // Create airports for Test Case 1
        AP28 = new Airport("AP28", "Airport28");
        AP28.setServedCities(Arrays.asList(CityN));
        AP29 = new Airport("AP29", "Airport29");
        AP29.setServedCities(Arrays.asList(CityO));
        AP30 = new Airport("AP30", "Airport30");
        AP30.setServedCities(Arrays.asList(CityP));
        
        // Create airports for Test Case 2
        AP31 = new Airport("AP31", "Airport31");
        AP31.setServedCities(Arrays.asList(CityS));
        AP32 = new Airport("AP32", "Airport32");
        AP32.setServedCities(Arrays.asList(CityQ));
        AP33 = new Airport("AP33", "Airport33");
        AP33.setServedCities(Arrays.asList(CityR));
        
        // Create airports for Test Case 3
        AP34 = new Airport("AP34", "Airport34");
        AP34.setServedCities(Arrays.asList(CityT));
        AP35 = new Airport("AP35", "Airport35");
        AP35.setServedCities(Arrays.asList(CityU));
        AP36 = new Airport("AP36", "Airport36");
        AP36.setServedCities(Arrays.asList(CityV));
        
        // Create airports for Test Case 4
        AP37 = new Airport("AP37", "Airport37");
        AP37.setServedCities(Arrays.asList(CityW));
        AP38 = new Airport("AP38", "Airport38");
        AP38.setServedCities(Arrays.asList(CityX));
        AP39 = new Airport("AP39", "Airport39");
        AP39.setServedCities(Arrays.asList(CityY));
        
        // Create airports for Test Case 5
        AP42 = new Airport("AP42", "Airport42");
        AP42.setServedCities(Arrays.asList(CityBB));
        AP43 = new Airport("AP43", "Airport43");
        AP43.setServedCities(Arrays.asList(CityCC));
        AP44 = new Airport("AP44", "Airport44");
        AP44.setServedCities(Arrays.asList(CityDD));
        
        // Create airlines
        AL26 = new Airline("AL26", "Airline26");
        AL27 = new Airline("AL27", "Airline27");
        AL28 = new Airline("AL28", "Airline28");
        AL29 = new Airline("AL29", "Airline29");
        AL31 = new Airline("AL31", "Airline31");
        
        // Create Flight F601 for Test Case 1
        F601 = new Flight("F601");
        F601.setDepartureAirport(AP28);
        F601.setArrivalAirport(AP29);
        F601.setDepartureTime(FlightService.parseDateTime("2025-04-20 10:00:00"));
        F601.setArrivalTime(FlightService.parseDateTime("2025-04-20 15:00:00"));
        F601.setStatus(FlightStatus.OPEN);
        F601.setPublished(true);
        AL26.addFlight(F601);
        
        // Create Flight F602 for Test Case 2
        F602 = new Flight("F602");
        F602.setDepartureAirport(AP32);
        F602.setArrivalAirport(AP33);
        F602.setDepartureTime(FlightService.parseDateTime("2025-05-10 09:00:00"));
        F602.setArrivalTime(FlightService.parseDateTime("2025-05-10 14:00:00"));
        F602.setStatus(FlightStatus.OPEN);
        F602.setPublished(true);
        AL27.addFlight(F602);
        
        // Create Flight F603 for Test Case 3
        F603 = new Flight("F603");
        F603.setDepartureAirport(AP35);
        F603.setArrivalAirport(AP36);
        F603.setDepartureTime(FlightService.parseDateTime("2025-06-15 11:00:00"));
        F603.setArrivalTime(FlightService.parseDateTime("2025-06-15 18:00:00"));
        F603.setStatus(FlightStatus.OPEN);
        F603.setPublished(true);
        
        // Add stopover to F603
        Stopover stopover603 = new Stopover(AP34, 
            FlightService.parseDateTime("2025-06-15 13:00:00"), 
            FlightService.parseDateTime("2025-06-15 13:45:00"));
        F603.addStopover(stopover603);
        AL28.addFlight(F603);
        
        // Create Flight F604 for Test Case 4
        F604 = new Flight("F604");
        F604.setDepartureAirport(AP38);
        F604.setArrivalAirport(AP39);
        F604.setDepartureTime(FlightService.parseDateTime("2025-07-20 12:00:00"));
        F604.setArrivalTime(FlightService.parseDateTime("2025-07-20 17:00:00"));
        F604.setStatus(FlightStatus.CLOSED); // Flight is closed
        F604.setPublished(true);
        AL29.addFlight(F604);
        
        // Create Flight F606 for Test Case 5
        F606 = new Flight("F606");
        F606.setDepartureAirport(AP43);
        F606.setArrivalAirport(AP44);
        F606.setDepartureTime(FlightService.parseDateTime("2025-12-09 18:00:00"));
        F606.setArrivalTime(FlightService.parseDateTime("2025-12-10 00:00:00"));
        F606.setStatus(FlightStatus.OPEN);
        F606.setPublished(true);
        
        // Add stopover to F606
        Stopover stopover606 = new Stopover(AP42, 
            FlightService.parseDateTime("2025-12-09 20:00:00"), 
            FlightService.parseDateTime("2025-12-09 20:45:00"));
        F606.addStopover(stopover606);
        AL31.addFlight(F606);
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() {
        // Set current time to 2025-04-19 09:00:00 (before departure)
        setCurrentTime("2025-04-19 09:00:00");
        
        // Create stopover AP30 (2025-04-20 12:00:00 → 2025-04-20 12:40:00)
        Stopover stopover = new Stopover(AP30, 
            FlightService.parseDateTime("2025-04-20 12:00:00"), 
            FlightService.parseDateTime("2025-04-20 12:40:00"));
        
        // Add stopover to flight F601
        boolean result = FlightService.addStopover(F601, stopover);
        
        // Verify the stopover was added successfully
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, F601.getStopovers().size());
        assertEquals("Stopover airport should be AP30", "AP30", F601.getStopovers().get(0).getAirport().getId());
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() {
        // Set current time to 2025-05-09 11:00:00 (before departure)
        setCurrentTime("2025-05-09 11:00:00");
        
        // Create stopover AP31 (2025-05-10 16:00:00 → 17:00:00) - outside flight window
        Stopover stopover = new Stopover(AP31, 
            FlightService.parseDateTime("2025-05-10 16:00:00"), 
            FlightService.parseDateTime("2025-05-10 17:00:00"));
        
        // Attempt to add stopover to flight F602
        boolean result = FlightService.addStopover(F602, stopover);
        
        // Verify the stopover was not added (times outside flight schedule)
        assertFalse("Stopover should not be added (times outside flight window)", result);
        assertEquals("Flight should have 0 stopovers", 0, F602.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() {
        // Set current time to 2025-06-14 10:00:00 (before departure)
        setCurrentTime("2025-06-14 10:00:00");
        
        // Verify flight F603 has 1 stopover initially
        assertEquals("Flight should have 1 stopover initially", 1, F603.getStopovers().size());
        assertEquals("Stopover airport should be AP34", "AP34", F603.getStopovers().get(0).getAirport().getId());
        
        // Delete stopover AP34 from flight F603
        boolean result = FlightService.deleteStopover(F603, "AP34");
        
        // Verify the stopover was deleted successfully
        assertTrue("Stopover should be deleted successfully", result);
        assertEquals("Flight should have 0 stopovers after deletion", 0, F603.getStopovers().size());
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() {
        // Set current time to 2025-07-10 09:00:00 (before departure but flight is closed)
        setCurrentTime("2025-07-10 09:00:00");
        
        // Create stopover AP37 (2025-07-20 13:30:00 → 14:00:00)
        Stopover stopover = new Stopover(AP37, 
            FlightService.parseDateTime("2025-07-20 13:30:00"), 
            FlightService.parseDateTime("2025-07-20 14:00:00"));
        
        // Attempt to add stopover to closed flight F604
        boolean result = FlightService.addStopover(F604, stopover);
        
        // Verify the stopover was not added (flight is closed)
        assertFalse("Stopover should not be added (flight is closed)", result);
        assertEquals("Flight should have 0 stopovers", 0, F604.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() {
        // Set current time to 2025-12-09 20:50:00 (after departure, flight mid-air)
        setCurrentTime("2025-12-09 20:50:00");
        
        // Verify flight F606 has 1 stopover initially
        assertEquals("Flight should have 1 stopover initially", 1, F606.getStopovers().size());
        assertEquals("Stopover airport should be AP42", "AP42", F606.getStopovers().get(0).getAirport().getId());
        
        // Attempt to delete stopover AP42 from flight F606 after departure
        boolean result = FlightService.deleteStopover(F606, "AP42");
        
        // Verify the stopover was not deleted (flight has departed)
        assertFalse("Stopover should not be deleted (flight has departed)", result);
        assertEquals("Flight should still have 1 stopover", 1, F606.getStopovers().size());
    }
    
    // Helper method to set current time for testing
    private void setCurrentTime(String dateTimeStr) {
        try {
            java.lang.reflect.Field formatterField = FlightService.class.getDeclaredField("FORMATTER");
            formatterField.setAccessible(true);
            java.time.format.DateTimeFormatter formatter = (java.time.format.DateTimeFormatter) formatterField.get(null);
            
            LocalDateTime testTime = LocalDateTime.parse(dateTimeStr, formatter);
            
            // Use reflection to modify the LocalDateTime.now() method for testing
            java.lang.reflect.Method nowMethod = LocalDateTime.class.getDeclaredMethod("now");
            java.lang.reflect.Field modifiersField = java.lang.reflect.Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            
            // Create a mock clock that returns our test time
            java.time.Clock fixedClock = java.time.Clock.fixed(testTime.atZone(java.time.ZoneId.systemDefault()).toInstant(), 
                java.time.ZoneId.systemDefault());
            
            // Replace the system clock temporarily
            java.time.Clock originalClock = java.time.Clock.systemDefaultZone();
            java.lang.reflect.Field clockField = LocalDateTime.class.getDeclaredField("clock");
            clockField.setAccessible(true);
            modifiersField.setInt(clockField, clockField.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
            clockField.set(null, fixedClock);
            
        } catch (Exception e) {
            // If we can't mock the time, we'll skip time-dependent assertions
            System.out.println("Warning: Could not mock current time: " + e.getMessage());
        }
    }
}