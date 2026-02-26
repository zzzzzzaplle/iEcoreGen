import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CR6Test {
    
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() throws ParseException {
        // Setup Airline
        Airline airline = new Airline();
        
        // Setup Airports
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        City cityN = new City();
        ap28.addCity(cityN);
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        City cityO = new City();
        ap29.addCity(cityO);
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        City cityP = new City();
        ap30.addCity(cityP);
        
        // Setup Flight F601
        Flight flight = new Flight();
        flight.setId("F601");
        flight.setDepartureAirport(ap28);
        flight.setArrivalAirport(ap29);
        flight.setDepartureTime(sdf.parse("2025-04-20 10:00:00"));
        flight.setArrivalTime(sdf.parse("2025-04-20 15:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Setup Stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(sdf.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(sdf.parse("2025-04-20 12:40:00"));
        
        // Current time
        Date now = sdf.parse("2025-04-19 09:00:00");
        
        // Execute addStopover
        boolean result = flight.addStopover(stopover, now);
        
        // Verify result
        assertTrue(result);
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() throws ParseException {
        // Setup Airline
        Airline airline = new Airline();
        
        // Setup Airports
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        City cityQ = new City();
        ap32.addCity(cityQ);
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        City cityR = new City();
        ap33.addCity(cityR);
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        City cityS = new City();
        ap31.addCity(cityS);
        
        // Setup Flight F602
        Flight flight = new Flight();
        flight.setId("F602");
        flight.setDepartureAirport(ap32);
        flight.setArrivalAirport(ap33);
        flight.setDepartureTime(sdf.parse("2025-05-10 09:00:00"));
        flight.setArrivalTime(sdf.parse("2025-05-10 14:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Setup Stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(sdf.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(sdf.parse("2025-05-10 17:00:00"));
        
        // Current time
        Date now = sdf.parse("2025-05-09 11:00:00");
        
        // Execute addStopover
        boolean result = flight.addStopover(stopover, now);
        
        // Verify result
        assertFalse(result);
    }
    
    @Test
    public void testCase3_deleteExistingStopover() throws ParseException {
        // Setup Airline
        Airline airline = new Airline();
        
        // Setup Airports
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        City cityT = new City();
        ap34.addCity(cityT);
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        City cityU = new City();
        ap35.addCity(cityU);
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        City cityV = new City();
        ap36.addCity(cityV);
        
        // Setup Flight F603
        Flight flight = new Flight();
        flight.setId("F603");
        flight.setDepartureAirport(ap35);
        flight.setArrivalAirport(ap36);
        flight.setDepartureTime(sdf.parse("2025-06-15 11:00:00"));
        flight.setArrivalTime(sdf.parse("2025-06-15 18:00:00"));
        flight.setOpenForBooking(true);
        
        // Setup existing stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap34);
        stopover.setArrivalTime(sdf.parse("2025-06-15 13:00:00"));
        stopover.setDepartureTime(sdf.parse("2025-06-15 13:45:00"));
        flight.getStopovers().add(stopover);
        
        airline.addFlight(flight);
        
        // Current time
        Date now = sdf.parse("2025-06-14 10:00:00");
        
        // Execute removeStopover
        boolean result = flight.removeStopover(stopover, now);
        
        // Verify result
        assertTrue(result);
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() throws ParseException {
        // Setup Airline
        Airline airline = new Airline();
        
        // Setup Airports
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        City cityW = new City();
        ap37.addCity(cityW);
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        City cityX = new City();
        ap38.addCity(cityX);
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        City cityY = new City();
        ap39.addCity(cityY);
        
        // Setup Flight F604 (closed for booking)
        Flight flight = new Flight();
        flight.setId("F604");
        flight.setDepartureAirport(ap38);
        flight.setArrivalAirport(ap39);
        flight.setDepartureTime(sdf.parse("2025-07-20 12:00:00"));
        flight.setArrivalTime(sdf.parse("2025-07-20 17:00:00"));
        flight.setOpenForBooking(false); // Flight closed
        
        airline.addFlight(flight);
        
        // Setup Stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap37);
        stopover.setArrivalTime(sdf.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(sdf.parse("2025-07-20 14:00:00"));
        
        // Current time
        Date now = sdf.parse("2025-07-10 09:00:00");
        
        // Execute addStopover
        boolean result = flight.addStopover(stopover, now);
        
        // Verify result
        assertFalse(result);
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() throws ParseException {
        // Setup Airline
        Airline airline = new Airline();
        
        // Setup Airports
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        City cityBB = new City();
        ap42.addCity(cityBB);
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        City cityCC = new City();
        ap43.addCity(cityCC);
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        City cityDD = new City();
        ap44.addCity(cityDD);
        
        // Setup Flight F606
        Flight flight = new Flight();
        flight.setId("F606");
        flight.setDepartureAirport(ap43);
        flight.setArrivalAirport(ap44);
        flight.setDepartureTime(sdf.parse("2025-12-09 18:00:00"));
        flight.setArrivalTime(sdf.parse("2025-12-10 00:00:00"));
        flight.setOpenForBooking(true);
        
        // Setup existing stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap42);
        stopover.setArrivalTime(sdf.parse("2025-12-09 20:00:00"));
        stopover.setDepartureTime(sdf.parse("2025-12-09 20:45:00"));
        flight.getStopovers().add(stopover);
        
        airline.addFlight(flight);
        
        // Current time (after flight departure and stopover departure)
        Date now = sdf.parse("2025-12-09 20:50:00");
        
        // Execute removeStopover
        boolean result = flight.removeStopover(stopover, now);
        
        // Verify result
        assertFalse(result);
    }
}