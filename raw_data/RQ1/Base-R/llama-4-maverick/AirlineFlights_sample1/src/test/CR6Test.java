import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR6Test {
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() {
        // Setup for Test Case 1
        departureAirport = new Airport();
        departureAirport.setId("AP28");
        departureAirport.addCity("CityN");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP29");
        arrivalAirport.addCity("CityO");
        
        Airport stopoverAirport = new Airport();
        stopoverAirport.setId("AP30");
        stopoverAirport.addCity("CityP");
        
        flight = new Flight();
        flight.setId("F601");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-04-20 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        
        Stopover stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Execute test - add stopover
        boolean result = flight.addStopoverToFlight(stopover);
        
        // Verify result
        assertTrue("Stopover should be added successfully", result);
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() {
        // Setup for Test Case 2
        departureAirport = new Airport();
        departureAirport.setId("AP32");
        departureAirport.addCity("CityQ");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP33");
        arrivalAirport.addCity("CityR");
        
        Airport stopoverAirport = new Airport();
        stopoverAirport.setId("AP31");
        stopoverAirport.addCity("CityS");
        
        flight = new Flight();
        flight.setId("F602");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        
        Stopover stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter));
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Execute test - add stopover
        boolean result = flight.addStopoverToFlight(stopover);
        
        // Verify result
        assertFalse("Stopover should not be added due to time outside window", result);
    }
    
    @Test
    public void testCase3_deleteExistingStopover() {
        // Setup for Test Case 3
        departureAirport = new Airport();
        departureAirport.setId("AP35");
        departureAirport.addCity("CityU");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP36");
        arrivalAirport.addCity("CityV");
        
        Airport stopoverAirport = new Airport();
        stopoverAirport.setId("AP34");
        stopoverAirport.addCity("CityT");
        
        flight = new Flight();
        flight.setId("F603");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-15 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        
        Stopover stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        stopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        
        // Add stopover first
        flight.addStopoverToFlight(stopover);
        
        // Execute test - delete stopover
        boolean result = flight.deleteStopoverFromFlight(stopover);
        
        // Verify result
        assertTrue("Stopover should be deleted successfully", result);
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() {
        // Setup for Test Case 4
        departureAirport = new Airport();
        departureAirport.setId("AP38");
        departureAirport.addCity("CityX");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP39");
        arrivalAirport.addCity("CityY");
        
        Airport stopoverAirport = new Airport();
        stopoverAirport.setId("AP37");
        stopoverAirport.addCity("CityW");
        
        flight = new Flight();
        flight.setId("F604");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-20 12:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        
        // Set flight status to closed
        flight.setStatus("closed");
        
        Stopover stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Execute test - add stopover to closed flight
        boolean result = flight.addStopoverToFlight(stopover);
        
        // Verify result
        assertFalse("Stopover should not be added to closed flight", result);
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() {
        // Setup for Test Case 5
        departureAirport = new Airport();
        departureAirport.setId("AP43");
        departureAirport.addCity("CityCC");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP44");
        arrivalAirport.addCity("CityDD");
        
        Airport stopoverAirport = new Airport();
        stopoverAirport.setId("AP42");
        stopoverAirport.addCity("CityBB");
        
        flight = new Flight();
        flight.setId("F606");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        
        Stopover stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        stopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        
        // Add stopover first
        flight.addStopoverToFlight(stopover);
        
        // Execute test - delete stopover after departure time
        // Simulate current time after departure by setting departure time to past
        flight.setDepartureTime(LocalDateTime.parse("2024-12-09 18:00:00", formatter));
        boolean result = flight.deleteStopoverFromFlight(stopover);
        
        // Verify result
        assertFalse("Stopover should not be deleted after flight departure", result);
    }
}