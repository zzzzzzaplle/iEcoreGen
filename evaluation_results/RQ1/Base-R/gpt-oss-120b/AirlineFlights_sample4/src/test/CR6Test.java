import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR6Test {
    
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * Test Case 1: "Add first stopover inside journey window"
     * Expected Output: true
     */
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() {
        // Setup
        // Create cities
        City cityN = airlineSystem.createCity("CITY_N", "CityN");
        City cityO = airlineSystem.createCity("CITY_O", "CityO");
        City cityP = airlineSystem.createCity("CITY_P", "CityP");
        
        // Create airports with cities
        List<City> citiesAP28 = Arrays.asList(cityN);
        List<City> citiesAP29 = Arrays.asList(cityO);
        List<City> citiesAP30 = Arrays.asList(cityP);
        
        airlineSystem.createAirport("AP28", "Airport28", citiesAP28);
        airlineSystem.createAirport("AP29", "Airport29", citiesAP29);
        airlineSystem.createAirport("AP30", "Airport30", citiesAP30);
        
        // Create flight
        airlineSystem.createFlight("F601", "AP28", "AP29", 
                                  "2025-04-20 10:00:00", "2025-04-20 15:00:00");
        
        // Publish flight to make it OPEN
        airlineSystem.publishFlight("F601");
        
        // Set current time to 2025-04-19 09:00:00 (before departure)
        // This is simulated by the addStopover method checking LocalDateTime.now()
        // In a real test, we might need to mock the time, but for this test we'll rely on
        // the fact that current time (when test runs) is before 2025-04-20 10:00:00
        
        // Test
        boolean result = airlineSystem.addStopover("F601", "AP30", 
                                                  "2025-04-20 12:00:00", "2025-04-20 12:40:00");
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
    }
    
    /**
     * Test Case 2: "Stopover time outside window"
     * Expected Output: False
     */
    @Test
    public void testCase2_StopoverTimeOutsideWindow() {
        // Setup
        // Create cities
        City cityQ = airlineSystem.createCity("CITY_Q", "CityQ");
        City cityR = airlineSystem.createCity("CITY_R", "CityR");
        City cityS = airlineSystem.createCity("CITY_S", "CityS");
        
        // Create airports with cities
        List<City> citiesAP32 = Arrays.asList(cityQ);
        List<City> citiesAP33 = Arrays.asList(cityR);
        List<City> citiesAP31 = Arrays.asList(cityS);
        
        airlineSystem.createAirport("AP32", "Airport32", citiesAP32);
        airlineSystem.createAirport("AP33", "Airport33", citiesAP33);
        airlineSystem.createAirport("AP31", "Airport31", citiesAP31);
        
        // Create flight
        airlineSystem.createFlight("F602", "AP32", "AP33", 
                                  "2025-05-10 09:00:00", "2025-05-10 14:00:00");
        
        // Publish flight to make it OPEN
        airlineSystem.publishFlight("F602");
        
        // Set current time to 2025-05-09 11:00:00 (before departure)
        // This is simulated by the addStopover method checking LocalDateTime.now()
        
        // Test - Stopover times (16:00:00 → 17:00:00) are outside flight schedule (09:00:00 → 14:00:00)
        boolean result = airlineSystem.addStopover("F602", "AP31", 
                                                  "2025-05-10 16:00:00", "2025-05-10 17:00:00");
        
        // Verify
        assertFalse("Stopover should not be added when times are outside flight window", result);
    }
    
    /**
     * Test Case 3: "Delete existing stopover"
     * Expected Output: True
     */
    @Test
    public void testCase3_DeleteExistingStopover() {
        // Setup
        // Create cities
        City cityT = airlineSystem.createCity("CITY_T", "CityT");
        City cityU = airlineSystem.createCity("CITY_U", "CityU");
        City cityV = airlineSystem.createCity("CITY_V", "CityV");
        
        // Create airports with cities
        List<City> citiesAP34 = Arrays.asList(cityT);
        List<City> citiesAP35 = Arrays.asList(cityU);
        List<City> citiesAP36 = Arrays.asList(cityV);
        
        airlineSystem.createAirport("AP34", "Airport34", citiesAP34);
        airlineSystem.createAirport("AP35", "Airport35", citiesAP35);
        airlineSystem.createAirport("AP36", "Airport36", citiesAP36);
        
        // Create flight
        airlineSystem.createFlight("F603", "AP34", "AP35", 
                                  "2025-06-15 11:00:00", "2025-06-15 18:00:00");
        
        // Publish flight to make it OPEN
        airlineSystem.publishFlight("F603");
        
        // Add stopover first
        boolean addResult = airlineSystem.addStopover("F603", "AP34", 
                                                     "2025-06-15 13:00:00", "2025-06-15 13:45:00");
        assertTrue("Stopover should be added successfully", addResult);
        
        // Get the stopover ID that was just added
        Flight flight = airlineSystem.getFlights().get("F603");
        String stopoverId = null;
        if (!flight.getStopovers().isEmpty()) {
            stopoverId = flight.getStopovers().get(0).getId();
        }
        assertNotNull("Stopover ID should be available", stopoverId);
        
        // Set current time to 2025-06-14 10:00:00 (before departure)
        // This is simulated by the deleteStopover method checking LocalDateTime.now()
        
        // Test - Delete the stopover
        boolean result = airlineSystem.deleteStopover("F603", stopoverId);
        
        // Verify
        assertTrue("Stopover should be deleted successfully", result);
    }
    
    /**
     * Test Case 4: "Flight closed prevents modification"
     * Expected Output: False
     */
    @Test
    public void testCase4_FlightClosedPreventsModification() {
        // Setup
        // Create cities
        City cityW = airlineSystem.createCity("CITY_W", "CityW");
        City cityX = airlineSystem.createCity("CITY_X", "CityX");
        City cityY = airlineSystem.createCity("CITY_Y", "CityY");
        
        // Create airports with cities
        List<City> citiesAP37 = Arrays.asList(cityW);
        List<City> citiesAP38 = Arrays.asList(cityX);
        List<City> citiesAP39 = Arrays.asList(cityY);
        
        airlineSystem.createAirport("AP37", "Airport37", citiesAP37);
        airlineSystem.createAirport("AP38", "Airport38", citiesAP38);
        airlineSystem.createAirport("AP39", "Airport39", citiesAP39);
        
        // Create flight but don't publish it (stays in DRAFT state)
        airlineSystem.createFlight("F604", "AP37", "AP38", 
                                  "2025-07-20 12:00:00", "2025-07-20 17:00:00");
        
        // Flight is in DRAFT state (not OPEN), so modifications should fail
        // Set current time to 2025-07-10 09:00:00 (before departure)
        // This is simulated by the addStopover method checking LocalDateTime.now()
        
        // Test
        boolean result = airlineSystem.addStopover("F604", "AP37", 
                                                  "2025-07-20 13:30:00", "2025-07-20 14:00:00");
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
    }
    
    /**
     * Test Case 5: "Attempt removal after departure"
     * Expected Output: False
     */
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() {
        // Setup
        // Create cities
        City cityBB = airlineSystem.createCity("CITY_BB", "CityBB");
        City cityCC = airlineSystem.createCity("CITY_CC", "CityCC");
        City cityDD = airlineSystem.createCity("CITY_DD", "CityDD");
        
        // Create airports with cities
        List<City> citiesAP42 = Arrays.asList(cityBB);
        List<City> citiesAP43 = Arrays.asList(cityCC);
        List<City> citiesAP44 = Arrays.asList(cityDD);
        
        airlineSystem.createAirport("AP42", "Airport42", citiesAP42);
        airlineSystem.createAirport("AP43", "Airport43", citiesAP43);
        airlineSystem.createAirport("AP44", "Airport44", citiesAP44);
        
        // Create flight with departure in the past
        // Use a past date to simulate "after departure" scenario
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime pastDeparture = now.minusHours(2);
        LocalDateTime pastArrival = now.plusHours(4);
        
        Flight flight = new Flight("F606", 
                                  airlineSystem.getAirports().get("AP42"),
                                  airlineSystem.getAirports().get("AP43"),
                                  pastDeparture, pastArrival);
        airlineSystem.getFlights().put("F606", flight);
        
        // Publish flight to make it OPEN
        airlineSystem.publishFlight("F606");
        
        // Add stopover
        Stopover stopover = new Stopover(airlineSystem.getAirports().get("AP42"), 
                                        now.minusMinutes(30), now.minusMinutes(15));
        flight.getStopovers().add(stopover);
        
        // Test - Current time is after departure, so deletion should fail
        boolean result = airlineSystem.deleteStopover("F606", stopover.getId());
        
        // Verify
        assertFalse("Stopover should not be deleted after flight departure", result);
    }
}