import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Flight flight;
    private Customer customer;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        Airline AL11 = new Airline();
        
        Airport AP100 = new Airport();
        Airport AP101 = new Airport();
        
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
        
        // Execute
        boolean result = CUA.addBooking(F300, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed with two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, CUA.getBookings().size());
        assertEquals("Flight should have 2 reservations", 2, F300.getReservations().size());
        
        // Check reservation details
        List<Reservation> reservations = F300.getReservations();
        Set<String> passengerNamesSet = new HashSet<>();
        for (Reservation res : reservations) {
            passengerNamesSet.add(res.getPassenger().getName());
            assertEquals("Reservation should be PENDING", ReservationStatus.PENDING, res.getStatus());
        }
        assertTrue("Should contain Peter", passengerNamesSet.contains("Peter"));
        assertTrue("Should contain Beck", passengerNamesSet.contains("Beck"));
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        Airline AL12 = new Airline();
        
        Airport AP102 = new Airport();
        Airport AP103 = new Airport();
        
        Flight F301 = new Flight();
        F301.setId("F301");
        F301.setDepartureAirport(AP102);
        F301.setArrialAirport(AP103);
        F301.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        F301.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        F301.setOpenForBooking(true);
        
        AL12.addFlight(F301);
        
        Customer CUB = new Customer();
        List<String> passengerNames = Arrays.asList("Alice", "Alice"); // Duplicate
        
        // Execute
        boolean result = CUB.addBooking(F301, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail with duplicate passengers", result);
        assertEquals("Customer should have no bookings", 0, CUB.getBookings().size());
        assertEquals("Flight should have no reservations", 0, F301.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        Airline AL13 = new Airline();
        
        Airport AP104 = new Airport();
        Airport AP105 = new Airport();
        
        Flight F302 = new Flight();
        F302.setId("F302");
        F302.setDepartureAirport(AP104);
        F302.setArrialAirport(AP105);
        F302.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        F302.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        F302.setOpenForBooking(true);
        
        AL13.addFlight(F302);
        
        Customer CUC = new Customer();
        
        // Create pre-existing booking with reservation for Jucy
        Booking BK_OLD = new Booking();
        BK_OLD.setCustomer(CUC);
        
        Reservation R302_A = new Reservation();
        R302_A.setId("R302-A");
        Passenger P4 = new Passenger();
        P4.setName("Jucy");
        R302_A.setPassenger(P4);
        R302_A.setFlight(F302);
        R302_A.setStatus(ReservationStatus.PENDING);
        
        BK_OLD.getReservations().add(R302_A);
        F302.getReservations().add(R302_A);
        CUC.getBookings().add(BK_OLD);
        
        List<String> passengerNames = Arrays.asList("Jucy"); // Already booked
        
        // Execute
        boolean result = CUC.addBooking(F302, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail with already booked passenger", result);
        assertEquals("Customer should still have 1 booking", 1, CUC.getBookings().size());
        assertEquals("Flight should still have 1 reservation", 1, F302.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        Airline AL14 = new Airline();
        
        Airport AP106 = new Airport();
        Airport AP107 = new Airport();
        
        Flight F303 = new Flight();
        F303.setId("F303");
        F303.setDepartureAirport(AP106);
        F303.setArrialAirport(AP107);
        F303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        F303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        F303.setOpenForBooking(false); // Flight closed
        
        AL14.addFlight(F303);
        
        Customer CUD = new Customer();
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = CUD.addBooking(F303, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail for closed flight", result);
        assertEquals("Customer should have no bookings", 0, CUD.getBookings().size());
        assertEquals("Flight should have no reservations", 0, F303.getReservations().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTime() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure
        
        Airline AL14 = new Airline();
        
        Airport AP106 = new Airport();
        Airport AP107 = new Airport();
        
        Flight F303 = new Flight();
        F303.setId("F303");
        F303.setDepartureAirport(AP106);
        F303.setArrialAirport(AP107);
        F303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        F303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        F303.setOpenForBooking(true);
        
        AL14.addFlight(F303);
        
        Customer CUD = new Customer();
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = CUD.addBooking(F303, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail when current time is after departure", result);
        assertEquals("Customer should have no bookings", 0, CUD.getBookings().size());
        assertEquals("Flight should have no reservations", 0, F303.getReservations().size());
    }
}