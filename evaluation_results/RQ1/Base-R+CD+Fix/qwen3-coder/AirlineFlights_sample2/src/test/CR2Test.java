import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    
    private Airline airline;
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        airline = new Airline();
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
        F300.setId("F300");
        F300.setDepartureAirport(AP100);
        F300.setArrivalAirport(AP101);
        F300.setDepartureTime(sdf.parse("2025-10-05 08:00:00"));
        F300.setArrivalTime(sdf.parse("2025-10-05 12:00:00"));
        F300.setOpenForBooking(true);
        
        AL11.addFlight(F300);
        
        Customer CUA = new Customer();
        
        Date currentTime = sdf.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = CUA.addBooking(F300, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, CUA.getBookings().size());
        assertEquals("Booking should contain 2 reservations", 2, CUA.getBookings().get(0).getReservations().size());
        
        // Check reservation details
        List<Reservation> reservations = CUA.getBookings().get(0).getReservations();
        assertEquals("First passenger should be Peter", "Peter", reservations.get(0).getPassenger().getName());
        assertEquals("Second passenger should be Beck", "Beck", reservations.get(1).getPassenger().getName());
        assertEquals("Reservation status should be PENDING", ReservationStatus.PENDING, reservations.get(0).getStatus());
        assertEquals("Reservation status should be PENDING", ReservationStatus.PENDING, reservations.get(1).getStatus());
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
        F301.setId("F301");
        F301.setDepartureAirport(AP102);
        F301.setArrivalAirport(AP103);
        F301.setDepartureTime(sdf.parse("2025-10-05 08:00:00"));
        F301.setArrivalTime(sdf.parse("2025-10-05 10:00:00"));
        F301.setOpenForBooking(true);
        
        AL12.addFlight(F301);
        
        Customer CUB = new Customer();
        
        Date currentTime = sdf.parse("2025-10-01 09:00:00");
        
        // Duplicate passenger names
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Execute
        boolean result = CUB.addBooking(F301, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail for duplicate passengers", result);
        assertEquals("Customer should have no bookings", 0, CUB.getBookings().size());
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
        F302.setId("F302");
        F302.setDepartureAirport(AP104);
        F302.setArrivalAirport(AP105);
        F302.setDepartureTime(sdf.parse("2025-10-05 18:00:00"));
        F302.setArrivalTime(sdf.parse("2025-10-06 02:00:00"));
        F302.setOpenForBooking(true);
        
        AL13.addFlight(F302);
        
        Customer CUC = new Customer();
        
        Date currentTime = sdf.parse("2025-10-01 09:00:00");
        
        // Create pre-existing booking with passenger "Jucy"
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(CUC);
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        Passenger existingPassenger = new Passenger();
        existingPassenger.setName("Jucy");
        existingReservation.setPassenger(existingPassenger);
        existingReservation.setFlight(F302);
        
        existingBooking.getReservations().add(existingReservation);
        F302.getReservations().add(existingReservation);
        CUC.getBookings().add(existingBooking);
        
        // Try to book the same passenger again
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Execute
        boolean result = CUC.addBooking(F302, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail for already booked passenger", result);
        assertEquals("Customer should still have only 1 booking", 1, CUC.getBookings().size());
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
        F303.setId("F303");
        F303.setDepartureAirport(AP106);
        F303.setArrivalAirport(AP107);
        F303.setDepartureTime(sdf.parse("2025-10-05 18:00:00"));
        F303.setArrivalTime(sdf.parse("2025-10-06 02:00:00"));
        F303.setOpenForBooking(false); // Flight is closed
        
        AL14.addFlight(F303);
        
        Customer CUD = new Customer();
        
        Date currentTime = sdf.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = CUD.addBooking(F303, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail for closed flight", result);
        assertEquals("Customer should have no bookings", 0, CUD.getBookings().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTimeBlocksBooking() throws Exception {
        // Setup
        Airline AL14 = new Airline();
        
        Airport AP104 = new Airport();
        AP104.setId("AP104");
        
        Airport AP105 = new Airport();
        AP105.setId("AP105");
        
        Flight F303 = new Flight();
        F303.setId("F303");
        F303.setDepartureAirport(AP104);
        F303.setArrivalAirport(AP105);
        F303.setDepartureTime(sdf.parse("2025-10-05 18:00:00"));
        F303.setArrivalTime(sdf.parse("2025-10-06 02:00:00"));
        F303.setOpenForBooking(true); // Flight is open
        
        AL14.addFlight(F303);
        
        Customer CUD = new Customer();
        
        // Current time is after departure time
        Date currentTime = sdf.parse("2025-10-06 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = CUD.addBooking(F303, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail when current time is after departure", result);
        assertEquals("Customer should have no bookings", 0, CUD.getBookings().size());
    }
}