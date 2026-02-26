import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports AP100 (departure) and AP101 (arrival)
        Airport ap100 = new Airport();
        Airport ap101 = new Airport();
        
        // Create flight F300
        Flight f300 = new Flight();
        f300.setId("F300");
        f300.setDepartureAirport(ap100);
        f300.setArrivalAirport(ap101);
        f300.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        f300.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        f300.setOpenForBooking(true);
        
        airline.addFlight(f300);
        
        // Instantiate customer CUA
        Customer cua = new Customer();
        
        // Current time = 2025-10-01 09:00:00
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Passenger list: "Peter", "Beck"
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = cua.addBooking(f300, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, cua.getBookings().size());
        assertEquals("Booking should contain 2 reservations", 2, cua.getBookings().get(0).getReservations().size());
        
        // Verify reservations for passengers
        List<Reservation> reservations = cua.getBookings().get(0).getReservations();
        Set<String> passengerNamesInReservations = new HashSet<>();
        for (Reservation reservation : reservations) {
            passengerNamesInReservations.add(reservation.getPassenger().getName());
            assertEquals("Reservation should be in PENDING status", ReservationStatus.PENDING, reservation.getStatus());
        }
        
        assertTrue("Reservation should contain Peter", passengerNamesInReservations.contains("Peter"));
        assertTrue("Reservation should contain Beck", passengerNamesInReservations.contains("Beck"));
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports AP102 (dep) and AP103 (arr)
        Airport ap102 = new Airport();
        Airport ap103 = new Airport();
        
        // Create flight F301
        Flight f301 = new Flight();
        f301.setId("F301");
        f301.setDepartureAirport(ap102);
        f301.setArrivalAirport(ap103);
        f301.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        f301.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        f301.setOpenForBooking(true);
        
        airline.addFlight(f301);
        
        // Customer CUB
        Customer cub = new Customer();
        
        // Current time = 2025-10-01 09:00:00
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Passenger list with duplicate: "Alice", "Alice"
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Execute
        boolean result = cub.addBooking(f301, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger", result);
        assertEquals("Customer should have no bookings", 0, cub.getBookings().size());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports AP104 and AP105
        Airport ap104 = new Airport();
        Airport ap105 = new Airport();
        
        // Create flight F302
        Flight f302 = new Flight();
        f302.setId("F302");
        f302.setDepartureAirport(ap104);
        f302.setArrivalAirport(ap105);
        f302.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        f302.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        f302.setOpenForBooking(true);
        
        airline.addFlight(f302);
        
        // Customer CUC
        Customer cuc = new Customer();
        
        // Pre-existing booking with passenger "Jucy"
        Booking oldBooking = new Booking();
        oldBooking.setCustomer(cuc);
        
        Passenger p4 = new Passenger();
        p4.setName("Jucy");
        
        // Create pre-existing reservation
        Date setupTime = dateFormat.parse("2025-09-30 10:00:00");
        boolean setupResult = oldBooking.createReservation(f302, p4, setupTime);
        assertTrue("Setup: Pre-existing reservation should be created successfully", setupResult);
        
        cuc.getBookings().add(oldBooking);
        
        // Current time = 2025-10-01 09:00:00
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Try to book same passenger "Jucy" again
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Execute
        boolean result = cuc.addBooking(f302, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("Customer should still have only 1 booking", 1, cuc.getBookings().size());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports AP106 and AP107
        Airport ap106 = new Airport();
        Airport ap107 = new Airport();
        
        // Create flight F303 (closed for booking)
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        f303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        f303.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(f303);
        
        // Customer CUD
        Customer cud = new Customer();
        
        // Current time = 2025-10-01 09:00:00
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Passenger "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = cud.addBooking(f303, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to flight being closed", result);
        assertEquals("Customer should have no bookings", 0, cud.getBookings().size());
    }
    
    @Test
    public void testCase5_timeIsAfterDepartureTime() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports AP106 and AP107
        Airport ap106 = new Airport();
        Airport ap107 = new Airport();
        
        // Create flight F303 (open for booking)
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        f303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        f303.setOpenForBooking(true);
        
        airline.addFlight(f303);
        
        // Customer CUD
        Customer cud = new Customer();
        
        // Current time = 2025-10-06 09:00:00 (AFTER departure time)
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00");
        
        // Passenger "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = cud.addBooking(f303, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time being after departure time", result);
        assertEquals("Customer should have no bookings", 0, cud.getBookings().size());
    }
}