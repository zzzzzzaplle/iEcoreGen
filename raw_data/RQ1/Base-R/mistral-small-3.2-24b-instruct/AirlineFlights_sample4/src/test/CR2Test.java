import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private Airline airline;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airline = new Airline();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() {
        // Setup
        Airport ap100 = new Airport();
        Airport ap101 = new Airport();
        
        Flight f300 = new Flight();
        f300.setDepartureAirportId(ap100.getId());
        f300.setArrivalAirportId(ap101.getId());
        f300.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        f300.setArrivalTime(LocalDateTime.parse("2025-10-05 12:00:00", formatter));
        f300.setOpenForBooking(true);
        
        airline.getFlights().add(f300);
        
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Test - Create booking with current time mocked by setting flight departure time appropriately
        Booking booking = new Booking();
        boolean result = booking.createBooking(f300, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Flight ID should be set", f300.getId(), booking.getFlightId());
        assertEquals("Should have 2 reservations", 2, booking.getReservations().size());
        
        // Check passenger names and status
        assertEquals("Peter", booking.getReservations().get(0).getPassengerName());
        assertEquals("Beck", booking.getReservations().get(1).getPassengerName());
        assertEquals("pending", booking.getReservations().get(0).getStatus());
        assertEquals("pending", booking.getReservations().get(1).getStatus());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() {
        // Setup
        Airport ap102 = new Airport();
        Airport ap103 = new Airport();
        
        Flight f301 = new Flight();
        f301.setDepartureAirportId(ap102.getId());
        f301.setArrivalAirportId(ap103.getId());
        f301.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        f301.setArrivalTime(LocalDateTime.parse("2025-10-05 10:00:00", formatter));
        f301.setOpenForBooking(true);
        
        airline.getFlights().add(f301);
        
        List<String> passengerNames = Arrays.asList("Alice", "Alice"); // Duplicate passenger
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Test
        Booking booking = new Booking();
        boolean result = booking.createBooking(f301, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger", result);
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() {
        // Setup
        Airport ap104 = new Airport();
        Airport ap105 = new Airport();
        
        Flight f302 = new Flight();
        f302.setDepartureAirportId(ap104.getId());
        f302.setArrivalAirportId(ap105.getId());
        f302.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f302.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f302.setOpenForBooking(true);
        
        airline.getFlights().add(f302);
        
        // Create pre-existing booking for passenger "Jucy"
        Booking existingBooking = new Booking();
        Reservation existingReservation = new Reservation();
        existingReservation.setPassengerName("Jucy");
        existingReservation.setStatus("pending");
        existingBooking.getReservations().add(existingReservation);
        existingBooking.setFlightId(f302.getId());
        
        airline.getBookings().put(existingBooking.getId(), existingBooking);
        
        List<String> passengerNames = Arrays.asList("Jucy");
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Test
        Booking newBooking = new Booking();
        boolean result = newBooking.createBooking(f302, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() {
        // Setup
        Airport ap106 = new Airport();
        Airport ap107 = new Airport();
        
        Flight f303 = new Flight();
        f303.setDepartureAirportId(ap106.getId());
        f303.setArrivalAirportId(ap107.getId());
        f303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f303.setOpenForBooking(false); // Flight is closed
        
        airline.getFlights().add(f303);
        
        List<String> passengerNames = Arrays.asList("Ruby");
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Test
        Booking booking = new Booking();
        boolean result = booking.createBooking(f303, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to flight being closed", result);
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTimeBlocksBooking() {
        // Setup
        Airport ap104 = new Airport();
        Airport ap105 = new Airport();
        
        Flight f303 = new Flight();
        f303.setDepartureAirportId(ap104.getId());
        f303.setArrivalAirportId(ap105.getId());
        f303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f303.setOpenForBooking(true);
        
        airline.getFlights().add(f303);
        
        List<String> passengerNames = Arrays.asList("Ruby");
        // Current time is after departure time (flight has already departed)
        
        // Test - The flight's departure time is in the past relative to current system time
        // We need to create a flight that has already departed
        Flight departedFlight = new Flight();
        departedFlight.setDepartureAirportId(ap104.getId());
        departedFlight.setArrivalAirportId(ap105.getId());
        departedFlight.setDepartureTime(LocalDateTime.now().minusHours(1)); // Departed 1 hour ago
        departedFlight.setArrivalTime(LocalDateTime.now().plusHours(1)); // Will arrive in 1 hour
        departedFlight.setOpenForBooking(true);
        
        Booking booking = new Booking();
        boolean result = booking.createBooking(departedFlight, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time after departure time", result);
    }
}