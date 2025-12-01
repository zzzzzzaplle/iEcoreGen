import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR6Test {
    private AirlineSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() {
        // Setup
        // Create airline AL26 (not explicitly modeled, but airports are needed)
        City cityN = new City("CTY_N", "CityN");
        City cityO = new City("CTY_O", "CityO");
        City cityP = new City("CTY_P", "CityP");
        
        Airport ap28 = new Airport("AP28", "Airport28");
        ap28.addCity(cityN);
        Airport ap29 = new Airport("AP29", "Airport29");
        ap29.addCity(cityO);
        Airport ap30 = new Airport("AP30", "Airport30");
        ap30.addCity(cityP);
        
        system.addAirport(ap28);
        system.addAirport(ap29);
        system.addAirport(ap30);
        
        LocalDateTime departTime = LocalDateTime.parse("2025-04-20 10:00:00", formatter);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-04-20 15:00:00", formatter);
        Flight flight = new Flight(ap28, ap29, departTime, arriveTime);
        flight.setStatus(FlightStatus.PUBLISHED); // openForBooking = True
        system.addFlight(flight);
        
        // Mock current time = 2025-04-19 09:00:00 (before departure)
        // In real implementation, we might need to mock LocalDateTime.now()
        // For this test, we rely on the fact that current time is before departure
        
        // Test
        boolean result = system.addStopover("FL-1", "AP30", 
                                           "2025-04-20 12:00:00", 
                                           "2025-04-20 12:40:00");
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() {
        // Setup
        City cityQ = new City("CTY_Q", "CityQ");
        City cityR = new City("CTY_R", "CityR");
        City cityS = new City("CTY_S", "CityS");
        
        Airport ap32 = new Airport("AP32", "Airport32");
        ap32.addCity(cityQ);
        Airport ap33 = new Airport("AP33", "Airport33");
        ap33.addCity(cityR);
        Airport ap31 = new Airport("AP31", "Airport31");
        ap31.addCity(cityS);
        
        system.addAirport(ap32);
        system.addAirport(ap33);
        system.addAirport(ap31);
        
        LocalDateTime departTime = LocalDateTime.parse("2025-05-10 09:00:00", formatter);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-05-10 14:00:00", formatter);
        Flight flight = new Flight(ap32, ap33, departTime, arriveTime);
        flight.setStatus(FlightStatus.PUBLISHED); // openForBooking = True
        system.addFlight(flight);
        
        // Mock current time = 2025-05-09 11:00:00 (before departure)
        
        // Test - stopover times (16:00-17:00) are outside flight window (09:00-14:00)
        boolean result = system.addStopover("FL-1", "AP31", 
                                           "2025-05-10 16:00:00", 
                                           "2025-05-10 17:00:00");
        
        // Verify
        assertFalse("Stopover outside flight window should fail", result);
    }
    
    @Test
    public void testCase3_deleteExistingStopover() {
        // Setup
        City cityT = new City("CTY_T", "CityT");
        City cityU = new City("CTY_U", "CityU");
        City cityV = new City("CTY_V", "CityV");
        
        Airport ap34 = new Airport("AP34", "Airport34");
        ap34.addCity(cityT);
        Airport ap35 = new Airport("AP35", "Airport35");
        ap35.addCity(cityU);
        Airport ap36 = new Airport("AP36", "Airport36");
        ap36.addCity(cityV);
        
        system.addAirport(ap34);
        system.addAirport(ap35);
        system.addAirport(ap36);
        
        LocalDateTime departTime = LocalDateTime.parse("2025-06-15 11:00:00", formatter);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-06-15 18:00:00", formatter);
        Flight flight = new Flight(ap34, ap35, departTime, arriveTime);
        flight.setStatus(FlightStatus.PUBLISHED); // openForBooking = True
        
        // Add the existing stopover
        LocalDateTime stopArrival = LocalDateTime.parse("2025-06-15 13:00:00", formatter);
        LocalDateTime stopDeparture = LocalDateTime.parse("2025-06-15 13:45:00", formatter);
        Stopover stopover = new Stopover(ap34, stopArrival, stopDeparture);
        flight.addStopover(stopover);
        
        system.addFlight(flight);
        
        // Mock current time = 2025-06-14 10:00:00 (before departure)
        
        // Test
        boolean result = system.deleteStopover("FL-1", stopover.getId());
        
        // Verify
        assertTrue("Existing stopover should be deleted successfully", result);
        assertEquals("Flight should have 0 stopovers after deletion", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() {
        // Setup
        City cityW = new City("CTY_W", "CityW");
        City cityX = new City("CTY_X", "CityX");
        City cityY = new City("CTY_Y", "CityY");
        
        Airport ap37 = new Airport("AP37", "Airport37");
        ap37.addCity(cityW);
        Airport ap38 = new Airport("AP38", "Airport38");
        ap38.addCity(cityX);
        Airport ap39 = new Airport("AP39", "Airport39");
        ap39.addCity(cityY);
        
        system.addAirport(ap37);
        system.addAirport(ap38);
        system.addAirport(ap39);
        
        LocalDateTime departTime = LocalDateTime.parse("2025-07-20 12:00:00", formatter);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-07-20 17:00:00", formatter);
        Flight flight = new Flight(ap37, ap38, departTime, arriveTime);
        flight.setStatus(FlightStatus.CLOSED); // openForBooking = False
        system.addFlight(flight);
        
        // Mock current time = 2025-07-10 09:00:00 (before departure)
        
        // Test
        boolean result = system.addStopover("FL-1", "AP37", 
                                           "2025-07-20 13:30:00", 
                                           "2025-07-20 14:00:00");
        
        // Verify
        assertFalse("Stopover addition should fail for closed flight", result);
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() {
        // Setup
        City cityBB = new City("CTY_BB", "CityBB");
        City cityCC = new City("CTY_CC", "CityCC");
        City cityDD = new City("CTY_DD", "CityDD");
        
        Airport ap42 = new Airport("AP42", "Airport42");
        ap42.addCity(cityBB);
        Airport ap43 = new Airport("AP43", "Airport43");
        ap43.addCity(cityCC);
        Airport ap44 = new Airport("AP44", "Airport44");
        ap44.addCity(cityDD);
        
        system.addAirport(ap42);
        system.addAirport(ap43);
        system.addAirport(ap44);
        
        LocalDateTime departTime = LocalDateTime.parse("2025-12-09 18:00:00", formatter);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-12-10 00:00:00", formatter);
        Flight flight = new Flight(ap42, ap43, departTime, arriveTime);
        flight.setStatus(FlightStatus.PUBLISHED); // openForBooking = True
        
        // Add the existing stopover
        LocalDateTime stopArrival = LocalDateTime.parse("2025-12-09 20:00:00", formatter);
        LocalDateTime stopDeparture = LocalDateTime.parse("2025-12-09 20:45:00", formatter);
        Stopover stopover = new Stopover(ap42, stopArrival, stopDeparture);
        flight.addStopover(stopover);
        
        system.addFlight(flight);
        
        // Mock current time = 2025-12-09 20:50:00 (after departure and stopover)
        // In real implementation, we would need to mock LocalDateTime.now()
        // For this test, we rely on the fact that current time is after departure
        
        // Test
        boolean result = system.deleteStopover("FL-1", stopover.getId());
        
        // Verify
        assertFalse("Stopover deletion should fail after flight departure", result);
    }
}