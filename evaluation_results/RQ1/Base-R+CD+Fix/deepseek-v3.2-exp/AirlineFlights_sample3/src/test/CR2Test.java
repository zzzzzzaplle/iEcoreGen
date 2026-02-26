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
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap100 = new Airport();
        ap100.setId("AP100");
        Airport ap101 = new Airport();
        ap101.setId("AP101");
        
        // Create flight F300
        Flight f300 = new Flight();
        f300.setId("F300");
        f300.setDepartureAirport(ap100);
        f300.setArrivalAirport(ap101);
        f300.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        f300.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        f300.setOpenForBooking(true);
        
        airline.addFlight(f300);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger list
        List<String> passengers = Arrays.asList("Peter", "Beck");
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(f300, currentTime, passengers);
        
        // Verify
        assertTrue("Booking should succeed with two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, customer.getBookings().size());
        assertEquals("Booking should have 2 reservations", 2, customer.getBookings().get(0).getReservations().size());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap102 = new Airport();
        ap102.setId("AP102");
        Airport ap103 = new Airport();
        ap103.setId("AP103");
        
        // Create flight F301
        Flight f301 = new Flight();
        f301.setId("F301");
        f301.setDepartureAirport(ap102);
        f301.setArrivalAirport(ap103);
        f301.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        f301.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        f301.setOpenForBooking(true);
        
        airline.addFlight(f301);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger list with duplicate
        List<String> passengers = Arrays.asList("Alice", "Alice");
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(f301, currentTime, passengers);
        
        // Verify
        assertFalse("Booking should fail with duplicate passengers", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        
        // Create flight F302
        Flight f302 = new Flight();
        f302.setId("F302");
        f302.setDepartureAirport(ap104);
        f302.setArrivalAirport(ap105);
        f302.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        f302.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        f302.setOpenForBooking(true);
        
        airline.addFlight(f302);
        
        // Create customer
        customer = new Customer();
        
        // Create pre-existing booking for Jucy
        Booking oldBooking = new Booking();
        oldBooking.setCustomer(customer);
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        Passenger jucy = new Passenger();
        jucy.setName("Jucy");
        existingReservation.setPassenger(jucy);
        existingReservation.setFlight(f302);
        
        oldBooking.getReservations().add(existingReservation);
        f302.getReservations().add(existingReservation);
        customer.getBookings().add(oldBooking);
        
        // Create new booking request for the same passenger
        List<String> passengers = Arrays.asList("Jucy");
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(f302, currentTime, passengers);
        
        // Verify
        assertFalse("Booking should fail with duplicate passenger on same flight", result);
        assertEquals("Customer should still have only 1 booking", 1, customer.getBookings().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        
        // Create flight F303 (closed for booking)
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        f303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        f303.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(f303);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger list
        List<String> passengers = Arrays.asList("Ruby");
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(f303, currentTime, passengers);
        
        // Verify
        assertFalse("Booking should fail with closed flight", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTimeBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        
        // Create flight F303 (open for booking, but current time is after departure)
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        f303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        f303.setOpenForBooking(true);
        
        airline.addFlight(f303);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger list
        List<String> passengers = Arrays.asList("Ruby");
        
        // Current time is AFTER departure time
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(f303, currentTime, passengers);
        
        // Verify
        assertFalse("Booking should fail when current time is after departure time", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
}