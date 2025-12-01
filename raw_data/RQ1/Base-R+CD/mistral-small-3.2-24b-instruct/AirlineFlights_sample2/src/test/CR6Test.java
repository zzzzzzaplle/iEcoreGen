import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR6Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Flight flight;
    private Stopover stopover;
    private Airport airport;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() throws ParseException {
        // Setup
        airline = new Airline();
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        ap28.addCity(new City("CityN"));
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        ap29.addCity(new City("CityO"));
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        ap30.addCity(new City("CityP"));
        
        flight = new Flight();
        flight.setId("F601");
        flight.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        flight.setDepartureAirport(ap28);
        flight.setArrialAirport(ap29);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertTrue("Flight should contain the stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws ParseException {
        // Setup
        airline = new Airline();
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        ap32.addCity(new City("CityQ"));
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        ap33.addCity(new City("CityR"));
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        ap31.addCity(new City("CityS"));
        
        flight = new Flight();
        flight.setId("F602");
        flight.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        flight.setDepartureAirport(ap32);
        flight.setArrialAirport(ap33);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside window", result);
        assertFalse("Flight should not contain the stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws ParseException {
        // Setup
        airline = new Airline();
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        ap34.addCity(new City("CityT"));
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        ap35.addCity(new City("CityU"));
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        ap36.addCity(new City("CityV"));
        
        flight = new Flight();
        flight.setId("F603");
        flight.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        flight.setDepartureAirport(ap34);
        flight.setArrialAirport(ap35);
        flight.setOpenForBooking(true);
        
        // Create and add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap36);
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        flight.getStopovers().add(existingStopover);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = flight.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertFalse("Flight should not contain the stopover after removal", flight.getStopovers().contains(existingStopover));
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws ParseException {
        // Setup
        airline = new Airline();
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        ap37.addCity(new City("CityW"));
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        ap38.addCity(new City("CityX"));
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        ap39.addCity(new City("CityY"));
        
        flight = new Flight();
        flight.setId("F604");
        flight.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        flight.setDepartureAirport(ap37);
        flight.setArrialAirport(ap38);
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(flight);
        
        stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Execute
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertFalse("Flight should not contain the stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws ParseException {
        // Setup
        airline = new Airline();
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        ap42.addCity(new City("CityBB"));
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        ap43.addCity(new City("CityCC"));
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        ap44.addCity(new City("CityDD"));
        
        flight = new Flight();
        flight.setId("F606");
        flight.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        flight.setDepartureAirport(ap42);
        flight.setArrialAirport(ap43);
        flight.setOpenForBooking(true);
        
        // Create and add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap44);
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        flight.getStopovers().add(existingStopover);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00"); // After departure and stopover
        
        // Execute
        boolean result = flight.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after departure", result);
        assertTrue("Flight should still contain the stopover", flight.getStopovers().contains(existingStopover));
    }
}