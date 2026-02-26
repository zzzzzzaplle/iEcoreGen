import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR6Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() throws ParseException {
        // Setup Airline AL26
        Airline airline = new Airline();
        
        // Setup Airports: AP28 (CityN), AP29 (CityO), AP30 (CityP)
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
        
        // Setup Flight F601: AP28 -> AP29, depart 2025-04-20 10:00:00, arrive 2025-04-20 15:00:00
        Flight flight = new Flight();
        flight.setId("F601");
        flight.setDepartureAirport(ap28);
        flight.setArrivalAirport(ap29);
        flight.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Current time = 2025-04-19 09:00:00
        Date now = dateFormat.parse("2025-04-19 09:00:00");
        
        // Create stopover: AP30 (2025-04-20 12:00:00 → 2025-04-20 12:40:00)
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        // Add stopover to flight
        boolean result = flight.addStopover(stopover, now);
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws ParseException {
        // Setup Airline AL27
        Airline airline = new Airline();
        
        // Setup Airports: AP32 (CityQ), AP33 (CityR), AP31 (CityS)
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
        
        // Setup Flight F602: depart 2025-05-10 09:00:00, arrive 2025-05-10 14:00:00
        Flight flight = new Flight();
        flight.setId("F602");
        flight.setDepartureAirport(ap32);
        flight.setArrivalAirport(ap33);
        flight.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Current time = 2025-05-09 11:00:00
        Date now = dateFormat.parse("2025-05-09 11:00:00");
        
        // Create stopover: AP31 (2025-05-10 16:00:00 → 17:00:00)
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        // Add stopover to flight
        boolean result = flight.addStopover(stopover, now);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase3_deleteExistingStopover() throws ParseException {
        // Setup Airline AL28
        Airline airline = new Airline();
        
        // Setup Airports: AP34 (CityT), AP35 (CityU), AP36 (CityV)
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
        
        // Setup Flight F603: depart 2025-06-15 11:00:00, arrive 2025-06-15 18:00:00
        Flight flight = new Flight();
        flight.setId("F603");
        flight.setDepartureAirport(ap35);
        flight.setArrivalAirport(ap36);
        flight.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        flight.setOpenForBooking(true);
        
        // Add existing stopover: AP34 (2025-06-15 13:00:00 → 2025-06-15 13:45:00)
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap34);
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        flight.getStopovers().add(existingStopover);
        
        airline.addFlight(flight);
        
        // Current time = 2025-06-14 10:00:00
        Date now = dateFormat.parse("2025-06-14 10:00:00");
        
        // Delete stopover AP34 from flight F603
        boolean result = flight.removeStopover(existingStopover, now);
        
        // Expected Output: True
        assertTrue(result);
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() throws ParseException {
        // Setup Airline AL29
        Airline airline = new Airline();
        
        // Setup Airports: AP37 (CityW), AP38 (CityX), AP39 (CityY)
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
        
        // Setup Flight F604: depart 2025-07-20 12:00:00, arrive 2025-07-20 17:00:00
        Flight flight = new Flight();
        flight.setId("F604");
        flight.setDepartureAirport(ap38);
        flight.setArrivalAirport(ap39);
        flight.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(flight);
        
        // Current time = 2025-07-10 09:00:00
        Date now = dateFormat.parse("2025-07-10 09:00:00");
        
        // Create stopover: AP37 (2025-07-20 13:30:00 → 14:00:00)
        Stopover stopover = new Stopover();
        stopover.setAirport(ap37);
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        // Add stopover to flight
        boolean result = flight.addStopover(stopover, now);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws ParseException {
        // Setup Airline AL31
        Airline airline = new Airline();
        
        // Setup Airports: AP42 (CityBB), AP43 (CityCC), AP44 (CityDD)
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
        
        // Setup Flight F606: depart 2025-12-09 18:00:00, arrive 2025-12-10 00:00:00
        Flight flight = new Flight();
        flight.setId("F606");
        flight.setDepartureAirport(ap43);
        flight.setArrivalAirport(ap44);
        flight.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        flight.setOpenForBooking(true);
        
        // Add existing stopover: AP42 (2025-12-09 20:00:00 → 2025-12-09 20:45:00)
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap42);
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        flight.getStopovers().add(existingStopover);
        
        airline.addFlight(flight);
        
        // Current time = 2025-12-09 20:50:00 (after stopover's exit, flight mid-air)
        Date now = dateFormat.parse("2025-12-09 20:50:00");
        
        // Delete stopover AP42 from flight F606
        boolean result = flight.removeStopover(existingStopover, now);
        
        // Expected Output: False
        assertFalse(result);
    }
}