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
    private Airport stopoverAirport;
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
        List<String> departureCities = new ArrayList<>();
        departureCities.add("CityN");
        departureAirport.setCities(departureCities);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP29");
        List<String> arrivalCities = new ArrayList<>();
        arrivalCities.add("CityO");
        arrivalAirport.setCities(arrivalCities);
        
        stopoverAirport = new Airport();
        stopoverAirport.setId("AP30");
        List<String> stopoverCities = new ArrayList<>();
        stopoverCities.add("CityP");
        stopoverAirport.setCities(stopoverCities);
        
        flight = new Flight();
        flight.setId("F601");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-04-20 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        flight.setOpen(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Execute
        boolean result = flight.addStopover(stopover);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, flight.getStopovers().size());
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP32");
        List<String> departureCities = new ArrayList<>();
        departureCities.add("CityQ");
        departureAirport.setCities(departureCities);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP33");
        List<String> arrivalCities = new ArrayList<>();
        arrivalCities.add("CityR");
        arrivalAirport.setCities(arrivalCities);
        
        stopoverAirport = new Airport();
        stopoverAirport.setId("AP31");
        List<String> stopoverCities = new ArrayList<>();
        stopoverCities.add("CityS");
        stopoverAirport.setCities(stopoverCities);
        
        flight = new Flight();
        flight.setId("F602");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        flight.setOpen(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Execute
        boolean result = flight.addStopover(stopover);
        
        // Verify
        assertFalse("Stopover should not be added when times are outside flight window", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP35");
        List<String> departureCities = new ArrayList<>();
        departureCities.add("CityU");
        departureAirport.setCities(departureCities);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP36");
        List<String> arrivalCities = new ArrayList<>();
        arrivalCities.add("CityV");
        arrivalAirport.setCities(arrivalCities);
        
        stopoverAirport = new Airport();
        stopoverAirport.setId("AP34");
        List<String> stopoverCities = new ArrayList<>();
        stopoverCities.add("CityT");
        stopoverAirport.setCities(stopoverCities);
        
        flight = new Flight();
        flight.setId("F603");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-15 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        flight.setOpen(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        flight.getStopovers().add(stopover);
        
        // Execute
        boolean result = flight.deleteStopover(stopover);
        
        // Verify
        assertTrue("Stopover should be deleted successfully", result);
        assertEquals("Flight should have 0 stopovers after deletion", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP38");
        List<String> departureCities = new ArrayList<>();
        departureCities.add("CityX");
        departureAirport.setCities(departureCities);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP39");
        List<String> arrivalCities = new ArrayList<>();
        arrivalCities.add("CityY");
        arrivalAirport.setCities(arrivalCities);
        
        stopoverAirport = new Airport();
        stopoverAirport.setId("AP37");
        List<String> stopoverCities = new ArrayList<>();
        stopoverCities.add("CityW");
        stopoverAirport.setCities(stopoverCities);
        
        flight = new Flight();
        flight.setId("F604");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-20 12:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        flight.setOpen(false); // Flight is closed
        
        Stopover stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Execute
        boolean result = flight.addStopover(stopover);
        
        // Verify
        assertFalse("Stopover should not be added when flight is closed", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP43");
        List<String> departureCities = new ArrayList<>();
        departureCities.add("CityCC");
        departureAirport.setCities(departureCities);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP44");
        List<String> arrivalCities = new ArrayList<>();
        arrivalCities.add("CityDD");
        arrivalAirport.setCities(arrivalCities);
        
        stopoverAirport = new Airport();
        stopoverAirport.setId("AP42");
        List<String> stopoverCities = new ArrayList<>();
        stopoverCities.add("CityBB");
        stopoverAirport.setCities(stopoverCities);
        
        flight = new Flight();
        flight.setId("F606");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        flight.setOpen(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(stopoverAirport);
        stopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        flight.getStopovers().add(stopover);
        
        // Set departure time in the past to simulate flight already departed
        flight.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        
        // Execute
        boolean result = flight.deleteStopover(stopover);
        
        // Verify
        assertFalse("Stopover should not be deleted after flight departure", result);
        assertEquals("Flight should still have 1 stopover", 1, flight.getStopovers().size());
    }
}