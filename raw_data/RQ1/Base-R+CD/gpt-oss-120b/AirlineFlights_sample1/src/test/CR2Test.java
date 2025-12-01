import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customer;
    
    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws ParseException {
        // Setup
        Airline AL11 = new Airline();
        
        Airport AP100 = new Airport();
        AP100.setId("AP100");
        Airport AP101 = new Airport();
        AP101.setId("AP101");
        
        Flight F300 = new Flight();
        F300.setId("F300");
        F300.setDepartureAirport(AP100);
        F300.setArrialAirport(AP101);
        F300.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        F300.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        F300.setOpenForBooking(true);
        
        AL11.addFlight(F300);
        
        Customer CUA = new Customer();
        
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = CUA.addBooking(F300, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have one booking", 1, CUA.getBookings().size());
        assertEquals("Booking should contain two reservations", 2, CUA.getBookings().get(0).getReservations().size());
        assertEquals("Flight should have two reservations", 2, F300.getReservations().size());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws ParseException {
        // Setup
        Airline AL12 = new Airline();
        
        Airport AP102 = new Airport();
        AP102.setId("AP102");
        Airport AP103 = new Airport();
        AP103.setId("AP103");
        
        Flight F301 = new Flight();
        F301.setId("F301");
        F301.setDepartureAirport(AP102);
        F301.setArrialAirport(AP103);
        F301.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        F301.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        F301.setOpenForBooking(true);
        
        AL12.addFlight(F301);
        
        Customer CUB = new Customer();
        
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = CUB.addBooking(F301, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger names", result);
        assertEquals("Customer should have no bookings", 0, CUB.getBookings().size());
        assertEquals("Flight should have no reservations", 0, F301.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws ParseException {
        // Setup
        Airline AL13 = new Airline();
        
        Airport AP104 = new Airport();
        AP104.setId("AP104");
        Airport AP105 = new Airport();
        AP105.setId("AP105");
        
        Flight F302 = new Flight();
        F302.setId("F302");
        F302.setDepartureAirport(AP104);
        F302.setArrialAirport(AP105);
        F302.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        F302.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        F302.setOpenForBooking(false);
        
        AL13.addFlight(F302);
        
        Customer CUC = new Customer();
        
        // Create pre-existing booking with passenger "Jucy"
        Booking BK_OLD = new Booking();
        BK_OLD.setCustomer(CUC);
        
        Passenger P4 = new Passenger();
        P4.setName("Jucy");
        
        Reservation R302_A = new Reservation();
        R302_A.setId("R302-A");
        R302_A.setStatus(ReservationStatus.PENDING);
        R302_A.setPassenger(P4);
        R302_A.setFlight(F302);
        
        BK_OLD.getReservations().add(R302_A);
        F302.getReservations().add(R302_A);
        CUC.getBookings().add(BK_OLD);
        
        List<String> passengerNames = Arrays.asList("Jucy");
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = CUC.addBooking(F302, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger on same flight", result);
        assertEquals("Customer should still have only one booking", 1, CUC.getBookings().size());
        assertEquals("Flight should still have only one reservation", 1, F302.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws ParseException {
        // Setup
        Airline AL14 = new Airline();
        
        Airport AP106 = new Airport();
        AP106.setId("AP106");
        Airport AP107 = new Airport();
        AP107.setId("AP107");
        
        Flight F303 = new Flight();
        F303.setId("F303");
        F303.setDepartureAirport(AP106);
        F303.setArrialAirport(AP107);
        F303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        F303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        F303.setOpenForBooking(false); // Flight is closed for booking
        
        AL14.addFlight(F303);
        
        Customer CUD = new Customer();
        
        List<String> passengerNames = Arrays.asList("Ruby");
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = CUD.addBooking(F303, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("Customer should have no bookings", 0, CUD.getBookings().size());
        assertEquals("Flight should have no reservations", 0, F303.getReservations().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTimeBlocksBooking() throws ParseException {
        // Setup
        Airline AL14 = new Airline();
        
        Airport AP106 = new Airport();
        AP106.setId("AP106");
        Airport AP107 = new Airport();
        AP107.setId("AP107");
        
        Flight F303 = new Flight();
        F303.setId("F303");
        F303.setDepartureAirport(AP106);
        F303.setArrialAirport(AP107);
        F303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        F303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        F303.setOpenForBooking(true); // Flight is open for booking
        
        AL14.addFlight(F303);
        
        Customer CUD = new Customer();
        
        List<String> passengerNames = Arrays.asList("Ruby");
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // Current time is after departure
        
        // Execute
        boolean result = CUD.addBooking(F303, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail because current time is after departure time", result);
        assertEquals("Customer should have no bookings", 0, CUD.getBookings().size());
        assertEquals("Flight should have no reservations", 0, F303.getReservations().size());
    }
}