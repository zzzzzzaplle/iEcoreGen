import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
        ap28.setCities(Arrays.asList("CityN"));
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        ap29.setCities(Arrays.asList("CityO"));
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        ap30.setCities(Arrays.asList("CityP"));
        
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        f601.setDepartureTime(LocalDateTime.parse("2025-04-20 10:00:00", formatter));
        f601.setArrivalTime(LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        f601.setOpenForBooking(true);
        
        airlineSystem.setFlights(Arrays.asList(f601));
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Execute and Verify
        assertTrue(airlineSystem.addStopover(f601, stopover));
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() {
        // Setup
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        ap32.setCities(Arrays.asList("CityQ"));
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        ap33.setCities(Arrays.asList("CityR"));
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        ap31.setCities(Arrays.asList("CityS"));
        
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        f602.setDepartureTime(LocalDateTime.parse("2025-05-10 09:00:00", formatter));
        f602.setArrivalTime(LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        f602.setOpenForBooking(true);
        
        airlineSystem.setFlights(Arrays.asList(f602));
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Execute and Verify
        assertFalse(airlineSystem.addStopover(f602, stopover));
    }
    
    @Test
    public void testCase3_deleteExistingStopover() {
        // Setup
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        ap34.setCities(Arrays.asList("CityT"));
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        ap35.setCities(Arrays.asList("CityU"));
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        ap36.setCities(Arrays.asList("CityV"));
        
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setDepartureAirport(ap34);
        f603.setArrivalAirport(ap35);
        f603.setDepartureTime(LocalDateTime.parse("2025-06-15 11:00:00", formatter));
        f603.setArrivalTime(LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        f603.setOpenForBooking(true);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap36);
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        f603.setStopovers(Arrays.asList(existingStopover));
        
        airlineSystem.setFlights(Arrays.asList(f603));
        
        // Execute and Verify
        assertTrue(airlineSystem.deleteStopover(f603, existingStopover));
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() {
        // Setup
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        ap37.setCities(Arrays.asList("CityW"));
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        ap38.setCities(Arrays.asList("CityX"));
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        ap39.setCities(Arrays.asList("CityY"));
        
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setDepartureAirport(ap37);
        f604.setArrivalAirport(ap38);
        f604.setDepartureTime(LocalDateTime.parse("2025-07-20 12:00:00", formatter));
        f604.setArrivalTime(LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        f604.setOpenForBooking(false);
        
        airlineSystem.setFlights(Arrays.asList(f604));
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Execute and Verify
        assertFalse(airlineSystem.addStopover(f604, stopover));
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() {
        // Setup
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        ap42.setCities(Arrays.asList("CityBB"));
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        ap43.setCities(Arrays.asList("CityCC"));
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        ap44.setCities(Arrays.asList("CityDD"));
        
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setDepartureAirport(ap42);
        f606.setArrivalAirport(ap43);
        f606.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        f606.setArrivalTime(LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        f606.setOpenForBooking(true);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap44);
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        f606.setStopovers(Arrays.asList(existingStopover));
        
        airlineSystem.setFlights(Arrays.asList(f606));
        
        // Execute and Verify
        assertFalse(airlineSystem.deleteStopover(f606, existingStopover));
    }
}