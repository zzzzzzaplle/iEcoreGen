import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CR6Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup
        Airport ap28 = new Airport();
        City cityN = new City();
        cityN.setName("CityN");
        ap28.addCity(cityN);
        
        Airport ap29 = new Airport();
        City cityO = new City();
        cityO.setName("CityO");
        ap29.addCity(cityO);
        
        Airport ap30 = new Airport();
        City cityP = new City();
        cityP.setName("CityP");
        ap30.addCity(cityP);
        
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        f601.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        f601.setOpenForBooking(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Execute
        boolean result = f601.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully when within journey window", result);
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airport ap32 = new Airport();
        City cityQ = new City();
        cityQ.setName("CityQ");
        ap32.addCity(cityQ);
        
        Airport ap33 = new Airport();
        City cityR = new City();
        cityR.setName("CityR");
        ap33.addCity(cityR);
        
        Airport ap31 = new Airport();
        City cityS = new City();
        cityS.setName("CityS");
        ap31.addCity(cityS);
        
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        f602.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        f602.setOpenForBooking(true);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Execute
        boolean result = f602.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should fail when outside journey window", result);
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws Exception {
        // Setup
        Airport ap34 = new Airport();
        City cityT = new City();
        cityT.setName("CityT");
        ap34.addCity(cityT);
        
        Airport ap35 = new Airport();
        City cityU = new City();
        cityU.setName("CityU");
        ap35.addCity(cityU);
        
        Airport ap36 = new Airport();
        City cityV = new City();
        cityV.setName("CityV");
        ap36.addCity(cityV);
        
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        f603.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        f603.setDepartureAirport(ap35);
        f603.setArrivalAirport(ap36);
        f603.setOpenForBooking(true);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap34);
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        f603.getStopovers().add(existingStopover);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Execute
        boolean result = f603.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully before departure", result);
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws Exception {
        // Setup
        Airport ap37 = new Airport();
        City cityW = new City();
        cityW.setName("CityW");
        ap37.addCity(cityW);
        
        Airport ap38 = new Airport();
        City cityX = new City();
        cityX.setName("CityX");
        ap38.addCity(cityX);
        
        Airport ap39 = new Airport();
        City cityY = new City();
        cityY.setName("CityY");
        ap39.addCity(cityY);
        
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        f604.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        f604.setDepartureAirport(ap38);
        f604.setArrivalAirport(ap39);
        f604.setOpenForBooking(false);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap37);
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Execute
        boolean result = f604.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should fail when flight is closed for booking", result);
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws Exception {
        // Setup
        Airport ap42 = new Airport();
        City cityBB = new City();
        cityBB.setName("CityBB");
        ap42.addCity(cityBB);
        
        Airport ap43 = new Airport();
        City cityCC = new City();
        cityCC.setName("CityCC");
        ap43.addCity(cityCC);
        
        Airport ap44 = new Airport();
        City cityDD = new City();
        cityDD.setName("CityDD");
        ap44.addCity(cityDD);
        
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        f606.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        f606.setDepartureAirport(ap43);
        f606.setArrivalAirport(ap44);
        f606.setOpenForBooking(true);
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap42);
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        f606.getStopovers().add(existingStopover);
        
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute
        boolean result = f606.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover removal should fail after flight departure", result);
    }
}