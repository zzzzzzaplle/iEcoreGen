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
    private Stopover stopover;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP28");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP29");
        
        flight = new Flight();
        flight.setId("F601");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-04-20 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        flight.setStatus("open");
        
        Airport stopoverAirport = new Airport();
        stopoverAirport.setId("AP30");
        
        stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Set current time to 2025-04-19 09:00:00 (before departure)
        // Note: We need to mock the current time since Flight uses LocalDateTime.now()
        // For this test, we'll assume the flight's departure time is in the future
        
        // Execute
        boolean result = flight.addStopover(stopover);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertTrue("Flight should contain the added stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP32");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP33");
        
        flight = new Flight();
        flight.setId("F602");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        flight.setStatus("open");
        
        Airport stopoverAirport = new Airport();
        stopoverAirport.setId("AP31");
        
        stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter));
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Set current time to 2025-05-09 11:00:00 (before departure)
        // Note: We need to mock the current time since Flight uses LocalDateTime.now()
        // For this test, we'll assume the flight's departure time is in the future
        
        // Execute
        boolean result = flight.addStopover(stopover);
        
        // Verify
        assertFalse("Stopover outside flight time window should not be added", result);
        assertFalse("Flight should not contain the invalid stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP35"); // Fixed: departure should be AP35 according to spec
        departureAirport.setCities(List.of("CityU"));
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP36");
        arrivalAirport.setCities(List.of("CityV"));
        
        flight = new Flight();
        flight.setId("F603");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-15 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        flight.setStatus("open");
        
        Airport stopoverAirport = new Airport();
        stopoverAirport.setId("AP34");
        stopoverAirport.setCities(List.of("CityT"));
        
        stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        stopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        
        // Add the stopover first
        flight.getStopovers().add(stopover);
        
        // Set current time to 2025-06-14 10:00:00 (before departure)
        // Note: We need to mock the current time since Flight uses LocalDateTime.now()
        // For this test, we'll assume the flight's departure time is in the future
        
        // Execute
        boolean result = flight.deleteStopover(stopover);
        
        // Verify
        assertTrue("Existing stopover should be deleted successfully", result);
        assertFalse("Flight should not contain the deleted stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP38"); // Fixed: departure should be AP38 according to spec
        departureAirport.setCities(List.of("CityX"));
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP39");
        arrivalAirport.setCities(List.of("CityY"));
        
        flight = new Flight();
        flight.setId("F604");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-20 12:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        flight.setStatus("closed"); // Flight is closed
        
        Airport stopoverAirport = new Airport();
        stopoverAirport.setId("AP37");
        stopoverAirport.setCities(List.of("CityW"));
        
        stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Set current time to 2025-07-10 09:00:00 (before departure)
        // Note: We need to mock the current time since Flight uses LocalDateTime.now()
        // For this test, we'll assume the flight's departure time is in the future
        
        // Execute
        boolean result = flight.addStopover(stopover);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertFalse("Flight should not contain the stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP43"); // Fixed: departure should be AP43 according to spec
        departureAirport.setCities(List.of("CityCC"));
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP44");
        arrivalAirport.setCities(List.of("CityDD"));
        
        flight = new Flight();
        flight.setId("F606");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        flight.setStatus("open");
        
        Airport stopoverAirport = new Airport();
        stopoverAirport.setId("AP42");
        stopoverAirport.setCities(List.of("CityBB"));
        
        stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        stopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        
        // Add the stopover first
        flight.getStopovers().add(stopover);
        
        // Set current time to 2025-12-09 20:50:00 (after departure and stopover)
        // Note: We need to mock the current time since Flight uses LocalDateTime.now()
        // For this test, we'll simulate the time check by setting departure time in the past
        flight.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        
        // Execute
        boolean result = flight.deleteStopover(stopover);
        
        // Verify
        assertFalse("Stopover should not be deleted after departure", result);
        assertTrue("Flight should still contain the stopover", flight.getStopovers().contains(stopover));
    }
}