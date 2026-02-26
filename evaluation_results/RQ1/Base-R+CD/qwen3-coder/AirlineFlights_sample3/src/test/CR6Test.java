import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR6Test {
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() throws ParseException {
        // Setup Airline AL26
        Airline airline = new Airline();
        
        // Create airports
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        City cityN = new City();
        cityN.setName("CityN");
        ap28.addCity(cityN);
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        City cityO = new City();
        cityO.setName("CityO");
        ap29.addCity(cityO);
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        City cityP = new City();
        cityP.setName("CityP");
        ap30.addCity(cityP);
        
        // Create flight F601
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        f601.setDepartureTime(sdf.parse("2025-04-20 10:00:00"));
        f601.setArrivalTime(sdf.parse("2025-04-20 15:00:00"));
        f601.setOpenForBooking(true);
        
        airline.addFlight(f601);
        
        // Current time
        Date now = sdf.parse("2025-04-19 09:00:00");
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(sdf.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(sdf.parse("2025-04-20 12:40:00"));
        
        // Execute test
        boolean result = f601.addStopover(stopover, now);
        
        // Assert result
        assertTrue(result);
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws ParseException {
        // Setup Airline AL27
        Airline airline = new Airline();
        
        // Create airports
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        City cityQ = new City();
        cityQ.setName("CityQ");
        ap32.addCity(cityQ);
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        City cityR = new City();
        cityR.setName("CityR");
        ap33.addCity(cityR);
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        City cityS = new City();
        cityS.setName("CityS");
        ap31.addCity(cityS);
        
        // Create flight F602
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        f602.setDepartureTime(sdf.parse("2025-05-10 09:00:00"));
        f602.setArrivalTime(sdf.parse("2025-05-10 14:00:00"));
        f602.setOpenForBooking(true);
        
        airline.addFlight(f602);
        
        // Current time
        Date now = sdf.parse("2025-05-09 11:00:00");
        
        // Create stopover (outside flight window)
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(sdf.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(sdf.parse("2025-05-10 17:00:00"));
        
        // Execute test
        boolean result = f602.addStopover(stopover, now);
        
        // Assert result
        assertFalse(result);
    }
    
    @Test
    public void testCase3_deleteExistingStopover() throws ParseException {
        // Setup Airline AL28
        Airline airline = new Airline();
        
        // Create airports
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        City cityT = new City();
        cityT.setName("CityT");
        ap34.addCity(cityT);
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        City cityU = new City();
        cityU.setName("CityU");
        ap35.addCity(cityU);
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        City cityV = new City();
        cityV.setName("CityV");
        ap36.addCity(cityV);
        
        // Create flight F603
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setDepartureAirport(ap35);
        f603.setArrivalAirport(ap36);
        f603.setDepartureTime(sdf.parse("2025-06-15 11:00:00"));
        f603.setArrivalTime(sdf.parse("2025-06-15 18:00:00"));
        f603.setOpenForBooking(true);
        
        airline.addFlight(f603);
        
        // Create existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap34);
        existingStopover.setArrivalTime(sdf.parse("2025-06-15 13:00:00"));
        existingStopover.setDepartureTime(sdf.parse("2025-06-15 13:45:00"));
        f603.getStopovers().add(existingStopover);
        
        // Current time
        Date now = sdf.parse("2025-06-14 10:00:00");
        
        // Execute test
        boolean result = f603.removeStopover(existingStopover, now);
        
        // Assert result
        assertTrue(result);
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() throws ParseException {
        // Setup Airline AL29
        Airline airline = new Airline();
        
        // Create airports
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        City cityW = new City();
        cityW.setName("CityW");
        ap37.addCity(cityW);
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        City cityX = new City();
        cityX.setName("CityX");
        ap38.addCity(cityX);
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        City cityY = new City();
        cityY.setName("CityY");
        ap39.addCity(cityY);
        
        // Create flight F604 (closed for booking)
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setDepartureAirport(ap38);
        f604.setArrivalAirport(ap39);
        f604.setDepartureTime(sdf.parse("2025-07-20 12:00:00"));
        f604.setArrivalTime(sdf.parse("2025-07-20 17:00:00"));
        f604.setOpenForBooking(false); // Closed for booking
        
        airline.addFlight(f604);
        
        // Current time
        Date now = sdf.parse("2025-07-10 09:00:00");
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap37);
        stopover.setArrivalTime(sdf.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(sdf.parse("2025-07-20 14:00:00"));
        
        // Execute test
        boolean result = f604.addStopover(stopover, now);
        
        // Assert result
        assertFalse(result);
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws ParseException {
        // Setup Airline AL31
        Airline airline = new Airline();
        
        // Create airports
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        City cityBB = new City();
        cityBB.setName("CityBB");
        ap42.addCity(cityBB);
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        City cityCC = new City();
        cityCC.setName("CityCC");
        ap43.addCity(cityCC);
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        City cityDD = new City();
        cityDD.setName("CityDD");
        ap44.addCity(cityDD);
        
        // Create flight F606
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setDepartureAirport(ap43);
        f606.setArrivalAirport(ap44);
        f606.setDepartureTime(sdf.parse("2025-12-09 18:00:00"));
        f606.setArrivalTime(sdf.parse("2025-12-10 00:00:00"));
        f606.setOpenForBooking(true);
        
        airline.addFlight(f606);
        
        // Create existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap42);
        existingStopover.setArrivalTime(sdf.parse("2025-12-09 20:00:00"));
        existingStopover.setDepartureTime(sdf.parse("2025-12-09 20:45:00"));
        f606.getStopovers().add(existingStopover);
        
        // Current time (after departure and after stopover)
        Date now = sdf.parse("2025-12-09 20:50:00");
        
        // Execute test
        boolean result = f606.removeStopover(existingStopover, now);
        
        // Assert result
        assertFalse(result);
    }
}