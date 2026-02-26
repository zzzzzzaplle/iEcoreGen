import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CR2Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() throws ParseException {
        // Setup
        Airline al11 = new Airline();
        
        Airport ap100 = new Airport();
        ap100.setId("AP100");
        
        Airport ap101 = new Airport();
        ap101.setId("AP101");
        
        Flight f300 = new Flight();
        f300.setId("F300");
        f300.setDepartureAirport(ap100);
        f300.setArrivalAirport(ap101);
        f300.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        f300.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        f300.setOpenForBooking(true);
        
        al11.addFlight(f300);
        
        Customer cua = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = cua.addBooking(f300, currentTime, passengerNames);
        
        // Verify
        assertTrue(result);
        assertEquals(1, cua.getBookings().size());
        assertEquals(2, cua.getBookings().get(0).getReservations().size());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws ParseException {
        // Setup
        Airline al12 = new Airline();
        
        Airport ap102 = new Airport();
        ap102.setId("AP102");
        
        Airport ap103 = new Airport();
        ap103.setId("AP103");
        
        Flight f301 = new Flight();
        f301.setId("F301");
        f301.setDepartureAirport(ap102);
        f301.setArrivalAirport(ap103);
        f301.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        f301.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        f301.setOpenForBooking(true);
        
        al12.addFlight(f301);
        
        Customer cub = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Execute
        boolean result = cub.addBooking(f301, currentTime, passengerNames);
        
        // Verify
        assertFalse(result);
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() throws ParseException {
        // Setup
        Airline al13 = new Airline();
        
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        
        Flight f302 = new Flight();
        f302.setId("F302");
        f302.setDepartureAirport(ap104);
        f302.setArrivalAirport(ap105);
        f302.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        f302.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        f302.setOpenForBooking(true);
        
        al13.addFlight(f302);
        
        // Create pre-existing booking
        Customer cuc = new Customer();
        List<String> existingPassenger = Arrays.asList("Jucy");
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create the existing booking first
        cuc.addBooking(f302, currentTime, existingPassenger);
        
        // Now try to book the same passenger again
        List<String> newPassengerList = Arrays.asList("Jucy");
        
        // Execute
        boolean result = cuc.addBooking(f302, currentTime, newPassengerList);
        
        // Verify
        assertFalse(result);
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() throws ParseException {
        // Setup
        Airline al14 = new Airline();
        
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        f303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        f303.setOpenForBooking(false); // Closed for booking
        
        al14.addFlight(f303);
        
        Customer cud = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = cud.addBooking(f303, currentTime, passengerNames);
        
        // Verify
        assertFalse(result);
    }
    
    @Test
    public void testCase5_theTimeIsAfterTheDepartureTime() throws ParseException {
        // Setup
        Airline al14 = new Airline();
        
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        f303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        f303.setOpenForBooking(true);
        
        al14.addFlight(f303);
        
        Customer cud = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = cud.addBooking(f303, currentTime, passengerNames);
        
        // Verify
        assertFalse(result);
    }
}