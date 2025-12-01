import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customer;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws Exception {
        // Setup
        Airline AL11 = new Airline();
        
        Airport AP100 = new Airport();
        AP100.setId("AP100");
        City depCity1 = new City();
        depCity1.setName("OriginCity");
        AP100.addCity(depCity1);
        
        Airport AP101 = new Airport();
        AP101.setId("AP101");
        City arrCity1 = new City();
        arrCity1.setName("DestCity");
        AP101.addCity(arrCity1);
        
        Flight F300 = new Flight();
        F300.setId("F300");
        F300.setDepartureAirport(AP100);
        F300.setArrivalAirport(AP101);
        F300.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        F300.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        F300.setOpenForBooking(true);
        AL11.addFlight(F300);
        
        Customer CUA = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = CUA.addBooking(F300, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, CUA.getBookings().size());
        assertEquals("Booking should have 2 reservations", 2, CUA.getBookings().get(0).getReservations().size());
        assertEquals("Flight should have 2 reservations", 2, F300.getReservations().size());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws Exception {
        // Setup
        Airline AL12 = new Airline();
        
        Airport AP102 = new Airport();
        AP102.setId("AP102");
        City depCity2 = new City();
        depCity2.setName("OriginCity2");
        AP102.addCity(depCity2);
        
        Airport AP103 = new Airport();
        AP103.setId("AP103");
        City arrCity2 = new City();
        arrCity2.setName("DestCity2");
        AP103.addCity(arrCity2);
        
        Flight F301 = new Flight();
        F301.setId("F301");
        F301.setDepartureAirport(AP102);
        F301.setArrivalAirport(AP103);
        F301.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        F301.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        F301.setOpenForBooking(true);
        AL12.addFlight(F301);
        
        Customer CUB = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Alice", "Alice"); // Duplicate passenger
        
        // Execute
        boolean result = CUB.addBooking(F301, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger in same request", result);
        assertEquals("Customer should have no bookings", 0, CUB.getBookings().size());
        assertEquals("Flight should have no reservations", 0, F301.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws Exception {
        // Setup
        Airline AL13 = new Airline();
        
        Airport AP104 = new Airport();
        AP104.setId("AP104");
        City depCity3 = new City();
        depCity3.setName("OriginCity3");
        AP104.addCity(depCity3);
        
        Airport AP105 = new Airport();
        AP105.setId("AP105");
        City arrCity3 = new City();
        arrCity3.setName("DestCity3");
        AP105.addCity(arrCity3);
        
        Flight F302 = new Flight();
        F302.setId("F302");
        F302.setDepartureAirport(AP104);
        F302.setArrivalAirport(AP105);
        F302.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        F302.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        F302.setOpenForBooking(true);
        AL13.addFlight(F302);
        
        Customer CUC = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create pre-existing booking for passenger "Jucy"
        Passenger P4 = new Passenger();
        P4.setName("Jucy");
        Booking preExistingBooking = new Booking(CUC);
        preExistingBooking.createReservation(F302, P4, currentTime);
        CUC.getBookings().add(preExistingBooking);
        
        List<String> passengerNames = Arrays.asList("Jucy"); // Same passenger as pre-existing booking
        
        // Execute
        boolean result = CUC.addBooking(F302, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked earlier", result);
        assertEquals("Customer should have only the original booking", 1, CUC.getBookings().size());
        assertEquals("Flight should have only the original reservation", 1, F302.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        Airline AL14 = new Airline();
        
        Airport AP106 = new Airport();
        AP106.setId("AP106");
        City depCity4 = new City();
        depCity4.setName("OriginCity4");
        AP106.addCity(depCity4);
        
        Airport AP107 = new Airport();
        AP107.setId("AP107");
        City arrCity4 = new City();
        arrCity4.setName("DestCity4");
        AP107.addCity(arrCity4);
        
        Flight F303 = new Flight();
        F303.setId("F303");
        F303.setDepartureAirport(AP106);
        F303.setArrivalAirport(AP107);
        F303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        F303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        F303.setOpenForBooking(false); // Flight closed for booking
        AL14.addFlight(F303);
        
        Customer CUD = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = CUD.addBooking(F303, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to flight being closed", result);
        assertEquals("Customer should have no bookings", 0, CUD.getBookings().size());
        assertEquals("Flight should have no reservations", 0, F303.getReservations().size());
    }
    
    @Test
    public void testCase5_TimeIsAfterDepartureTime() throws Exception {
        // Setup
        Airline AL14 = new Airline();
        
        Airport AP104 = new Airport();
        AP104.setId("AP104");
        City depCity5 = new City();
        depCity5.setName("OriginCity5");
        AP104.addCity(depCity5);
        
        Airport AP105 = new Airport();
        AP105.setId("AP105");
        City arrCity5 = new City();
        arrCity5.setName("DestCity5");
        AP105.addCity(arrCity5);
        
        Flight F303 = new Flight();
        F303.setId("F303");
        F303.setDepartureAirport(AP104);
        F303.setArrivalAirport(AP105);
        F303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        F303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        F303.setOpenForBooking(true);
        AL14.addFlight(F303);
        
        Customer CUD = new Customer();
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = CUD.addBooking(F303, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time being after departure time", result);
        assertEquals("Customer should have no bookings", 0, CUD.getBookings().size());
        assertEquals("Flight should have no reservations", 0, F303.getReservations().size());
    }
}