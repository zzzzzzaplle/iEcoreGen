import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR6Test {
    private Airline airline;
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        airline = new Airline();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup airports
        Airport ap28 = createAirport("AP28", "CityN");
        Airport ap29 = createAirport("AP29", "CityO");
        Airport ap30 = createAirport("AP30", "CityP");
        
        // Setup flight F601
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setOpenForBooking(true);
        f601.setDepartureTime(sdf.parse("2025-04-20 10:00:00"));
        f601.setArrivalTime(sdf.parse("2025-04-20 15:00:00"));
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        
        airline.addFlight(f601);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(sdf.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(sdf.parse("2025-04-20 12:40:00"));
        
        Date currentTime = sdf.parse("2025-04-19 09:00:00");
        
        // Execute and verify
        boolean result = f601.addStopover(stopover, currentTime);
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, f601.getStopovers().size());
        assertSame("Added stopover should match", stopover, f601.getStopovers().get(0));
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws Exception {
        // Setup airports
        Airport ap32 = createAirport("AP32", "CityQ");
        Airport ap33 = createAirport("AP33", "CityR");
        Airport ap31 = createAirport("AP31", "CityS");
        
        // Setup flight F602
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setOpenForBooking(true);
        f602.setDepartureTime(sdf.parse("2025-05-10 09:00:00"));
        f602.setArrivalTime(sdf.parse("2025-05-10 14:00:00"));
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        
        airline.addFlight(f602);
        
        // Create stopover with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(sdf.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(sdf.parse("2025-05-10 17:00:00"));
        
        Date currentTime = sdf.parse("2025-05-09 11:00:00");
        
        // Execute and verify
        boolean result = f602.addStopover(stopover, currentTime);
        assertFalse("Stopover should not be added due to time outside window", result);
        assertEquals("Flight should have 0 stopovers", 0, f602.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() throws Exception {
        // Setup airports
        Airport ap34 = createAirport("AP34", "CityT");
        Airport ap35 = createAirport("AP35", "CityU");
        Airport ap36 = createAirport("AP36", "CityV");
        
        // Setup flight F603 with existing stopover
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setOpenForBooking(true);
        f603.setDepartureTime(sdf.parse("2025-06-15 11:00:00"));
        f603.setArrivalTime(sdf.parse("2025-06-15 18:00:00"));
        f603.setDepartureAirport(ap34);
        f603.setArrivalAirport(ap35);
        
        // Create and add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap36);
        existingStopover.setArrivalTime(sdf.parse("2025-06-15 13:00:00"));
        existingStopover.setDepartureTime(sdf.parse("2025-06-15 13:45:00"));
        f603.getStopovers().add(existingStopover);
        
        airline.addFlight(f603);
        
        Date currentTime = sdf.parse("2025-06-14 10:00:00");
        
        // Execute and verify
        boolean result = f603.removeStopover(existingStopover, currentTime);
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have 0 stopovers after removal", 0, f603.getStopovers().size());
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() throws Exception {
        // Setup airports
        Airport ap37 = createAirport("AP37", "CityW");
        Airport ap38 = createAirport("AP38", "CityX");
        Airport ap39 = createAirport("AP39", "CityY");
        
        // Setup flight F604 with closed booking
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setOpenForBooking(false); // Flight closed for booking
        f604.setDepartureTime(sdf.parse("2025-07-20 12:00:00"));
        f604.setArrivalTime(sdf.parse("2025-07-20 17:00:00"));
        f604.setDepartureAirport(ap37);
        f604.setArrivalAirport(ap38);
        
        airline.addFlight(f604);
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setArrivalTime(sdf.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(sdf.parse("2025-07-20 14:00:00"));
        
        Date currentTime = sdf.parse("2025-07-10 09:00:00");
        
        // Execute and verify
        boolean result = f604.addStopover(stopover, currentTime);
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have 0 stopovers", 0, f604.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws Exception {
        // Setup airports
        Airport ap42 = createAirport("AP42", "CityBB");
        Airport ap43 = createAirport("AP43", "CityCC");
        Airport ap44 = createAirport("AP44", "CityDD");
        
        // Setup flight F606 with existing stopover
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setOpenForBooking(true);
        f606.setDepartureTime(sdf.parse("2025-12-09 18:00:00"));
        f606.setArrivalTime(sdf.parse("2025-12-10 00:00:00"));
        f606.setDepartureAirport(ap42);
        f606.setArrivalAirport(ap43);
        
        // Create and add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap44);
        existingStopover.setArrivalTime(sdf.parse("2025-12-09 20:00:00"));
        existingStopover.setDepartureTime(sdf.parse("2025-12-09 20:45:00"));
        f606.getStopovers().add(existingStopover);
        
        airline.addFlight(f606);
        
        // Current time after stopover departure (flight mid-air)
        Date currentTime = sdf.parse("2025-12-09 20:50:00");
        
        // Execute and verify
        boolean result = f606.removeStopover(existingStopover, currentTime);
        assertFalse("Stopover should not be removed after departure", result);
        assertEquals("Flight should still have 1 stopover", 1, f606.getStopovers().size());
    }
    
    private Airport createAirport(String id, String cityName) {
        Airport airport = new Airport();
        airport.setId(id);
        
        City city = new City();
        city.setName(cityName);
        
        List<City> cities = new ArrayList<>();
        cities.add(city);
        airport.setServesForCities(cities);
        
        return airport;
    }
}