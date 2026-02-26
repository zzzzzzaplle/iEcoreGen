import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR2Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws Exception {
        // Setup
        Airline AL11 = new Airline();
        
        Airport AP100 = new Airport();
        AP100.setId("AP100");
        Airport AP101 = new Airport();
        AP101.setId("AP101");
        
        Flight F300 = new Flight();
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
        assertEquals("Customer should have one booking", 1, CUA.getBookings().size());
        assertEquals("Flight should have two reservations", 2, F300.getReservations().size());
        
        // Check reservation details
        List<Reservation> reservations = CUA.getBookings().get(0).getReservations();
        assertEquals("Booking should contain two reservations", 2, reservations.size());
        
        Set<String> passengerNamesInReservations = new HashSet<>();
        for (Reservation r : reservations) {
            assertEquals("Reservation status should be PENDING", ReservationStatus.PENDING, r.getStatus());
            assertNotNull("Reservation should have an ID", r.getId());
            passengerNamesInReservations.add(r.getPassenger().getName());
        }
        
        assertTrue("Should contain passenger Peter", passengerNamesInReservations.contains("Peter"));
        assertTrue("Should contain passenger Beck", passengerNamesInReservations.contains("Beck"));
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws Exception {
        // Setup
        Airline AL12 = new Airline();
        
        Airport AP102 = new Airport();
        AP102.setId("AP102");
        Airport AP103 = new Airport();
        AP103.setId("AP103");
        
        Flight F301 = new Flight();
        F301.setDepartureAirport(AP102);
        F301.setArrivalAirport(AP103);
        F301.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        F301.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        F301.setOpenForBooking(true);
        
        AL12.addFlight(F301);
        
        Customer CUB = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Alice", "Alice"); // Duplicate passenger names
        
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
        Airport AP105 = new Airport();
        AP105.setId("AP105");
        
        Flight F302 = new Flight();
        F302.setDepartureAirport(AP104);
        F302.setArrivalAirport(AP105);
        F302.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        F302.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        F302.setOpenForBooking(true);
        
        AL13.addFlight(F302);
        
        Customer CUC = new Customer();
        
        // Create pre-existing booking with passenger "Jucy"
        Date currentTime = dateFormat.parse("2025-10-01 08:00:00");
        List<String> existingPassengerNames = Arrays.asList("Jucy");
        boolean existingBookingResult = CUC.addBooking(F302, currentTime, existingPassengerNames);
        assertTrue("Pre-existing booking should succeed", existingBookingResult);
        
        // Now try to book the same passenger again
        Date newCurrentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> newPassengerNames = Arrays.asList("Jucy");
        
        // Execute
        boolean result = CUC.addBooking(F302, newCurrentTime, newPassengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("Customer should still have only one booking", 1, CUC.getBookings().size());
        assertEquals("Flight should still have only one reservation", 1, F302.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        Airline AL14 = new Airline();
        
        Airport AP106 = new Airport();
        AP106.setId("AP106");
        Airport AP107 = new Airport();
        AP107.setId("AP107");
        
        Flight F303 = new Flight();
        F303.setDepartureAirport(AP106);
        F303.setArrivalAirport(AP107);
        F303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        F303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        F303.setOpenForBooking(false); // Flight is closed for booking
        
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
    public void testCase5_TimeAfterDepartureTime() throws Exception {
        // Setup
        Airline AL14 = new Airline();
        
        Airport AP106 = new Airport();
        AP106.setId("AP106");
        Airport AP107 = new Airport();
        AP107.setId("AP107");
        
        Flight F303 = new Flight();
        F303.setDepartureAirport(AP106);
        F303.setArrivalAirport(AP107);
        F303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        F303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        F303.setOpenForBooking(true); // Flight is open for booking
        
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