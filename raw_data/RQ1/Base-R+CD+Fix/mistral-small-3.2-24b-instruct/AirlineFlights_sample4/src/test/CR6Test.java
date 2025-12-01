import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR6Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup
        Airline AL26 = new Airline();
        Airport AP28 = new Airport("AP28");
        Airport AP29 = new Airport("AP29");
        Airport AP30 = new Airport("AP30");
        
        City cityN = new City("CityN");
        City cityO = new City("CityO");
        City cityP = new City("CityP");
        
        AP28.addCity(cityN);
        AP29.addCity(cityO);
        AP30.addCity(cityP);
        
        Date departureTime = dateFormat.parse("2025-04-20 10:00:00");
        Date arrivalTime = dateFormat.parse("2025-04-20 15:00:00");
        Flight F601 = new Flight(departureTime, arrivalTime, AP28, AP29);
        F601.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        Date stopoverDeparture = dateFormat.parse("2025-04-20 12:00:00");
        Date stopoverArrival = dateFormat.parse("2025-04-20 12:40:00");
        Stopover stopover = new Stopover(stopoverDeparture, stopoverArrival, AP30);
        
        // Execute
        boolean result = F601.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, F601.getStopovers().size());
        assertEquals("Stopover airport should be AP30", AP30, F601.getStopovers().get(0).getAirport());
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airline AL27 = new Airline();
        Airport AP32 = new Airport("AP32");
        Airport AP33 = new Airport("AP33");
        Airport AP31 = new Airport("AP31");
        
        City cityQ = new City("CityQ");
        City cityR = new City("CityR");
        City cityS = new City("CityS");
        
        AP32.addCity(cityQ);
        AP33.addCity(cityR);
        AP31.addCity(cityS);
        
        Date departureTime = dateFormat.parse("2025-05-10 09:00:00");
        Date arrivalTime = dateFormat.parse("2025-05-10 14:00:00");
        Flight F602 = new Flight(departureTime, arrivalTime, AP32, AP33);
        F602.setOpenForBooking(true);
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        Date stopoverDeparture = dateFormat.parse("2025-05-10 16:00:00");
        Date stopoverArrival = dateFormat.parse("2025-05-10 17:00:00");
        Stopover stopover = new Stopover(stopoverDeparture, stopoverArrival, AP31);
        
        // Execute
        boolean result = F602.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover time outside flight window should be rejected", result);
        assertEquals("Flight should have 0 stopovers", 0, F602.getStopovers().size());
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws Exception {
        // Setup
        Airline AL28 = new Airline();
        Airport AP34 = new Airport("AP34");
        Airport AP35 = new Airport("AP35");
        Airport AP36 = new Airport("AP36");
        
        City cityT = new City("CityT");
        City cityU = new City("CityU");
        City cityV = new City("CityV");
        
        AP34.addCity(cityT);
        AP35.addCity(cityU);
        AP36.addCity(cityV);
        
        Date departureTime = dateFormat.parse("2025-06-15 11:00:00");
        Date arrivalTime = dateFormat.parse("2025-06-15 18:00:00");
        Flight F603 = new Flight(departureTime, arrivalTime, AP34, AP35);
        F603.setOpenForBooking(true);
        
        Date stopoverDeparture = dateFormat.parse("2025-06-15 13:00:00");
        Date stopoverArrival = dateFormat.parse("2025-06-15 13:45:00");
        Stopover existingStopover = new Stopover(stopoverDeparture, stopoverArrival, AP34);
        F603.addStopover(existingStopover, dateFormat.parse("2025-06-01 00:00:00"));
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = F603.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have 0 stopovers", 0, F603.getStopovers().size());
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws Exception {
        // Setup
        Airline AL29 = new Airline();
        Airport AP37 = new Airport("AP37");
        Airport AP38 = new Airport("AP38");
        Airport AP39 = new Airport("AP39");
        
        City cityW = new City("CityW");
        City cityX = new City("CityX");
        City cityY = new City("CityY");
        
        AP37.addCity(cityW);
        AP38.addCity(cityX);
        AP39.addCity(cityY);
        
        Date departureTime = dateFormat.parse("2025-07-20 12:00:00");
        Date arrivalTime = dateFormat.parse("2025-07-20 17:00:00");
        Flight F604 = new Flight(departureTime, arrivalTime, AP37, AP38);
        F604.setOpenForBooking(false); // Flight is closed
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        Date stopoverDeparture = dateFormat.parse("2025-07-20 13:30:00");
        Date stopoverArrival = dateFormat.parse("2025-07-20 14:00:00");
        Stopover stopover = new Stopover(stopoverDeparture, stopoverArrival, AP37);
        
        // Execute
        boolean result = F604.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have 0 stopovers", 0, F604.getStopovers().size());
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws Exception {
        // Setup
        Airline AL31 = new Airline();
        Airport AP42 = new Airport("AP42");
        Airport AP43 = new Airport("AP43");
        Airport AP44 = new Airport("AP44");
        
        City cityBB = new City("CityBB");
        City cityCC = new City("CityCC");
        City cityDD = new City("CityDD");
        
        AP42.addCity(cityBB);
        AP43.addCity(cityCC);
        AP44.addCity(cityDD);
        
        Date departureTime = dateFormat.parse("2025-12-09 18:00:00");
        Date arrivalTime = dateFormat.parse("2025-12-10 00:00:00");
        Flight F606 = new Flight(departureTime, arrivalTime, AP42, AP43);
        F606.setOpenForBooking(true);
        
        Date stopoverDeparture = dateFormat.parse("2025-12-09 20:00:00");
        Date stopoverArrival = dateFormat.parse("2025-12-09 20:45:00");
        Stopover existingStopover = new Stopover(stopoverDeparture, stopoverArrival, AP42);
        F606.addStopover(existingStopover, dateFormat.parse("2025-12-01 00:00:00"));
        
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00"); // After departure
        
        // Execute
        boolean result = F606.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after flight departure", result);
        assertEquals("Flight should still have 1 stopover", 1, F606.getStopovers().size());
    }
}