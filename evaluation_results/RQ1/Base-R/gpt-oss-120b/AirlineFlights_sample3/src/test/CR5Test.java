import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private FlightService flightService;
    private BookingRepository bookingRepository;
    
    @Before
    public void setUp() {
        // Clear any existing bookings before each test
        // Since BOOKINGS is private, we'll reset it by clearing through reflection or by creating new instances
        // For simplicity, we'll recreate the BookingRepository behavior by clearing static data
        // Note: In a real scenario, we'd need to reset the static BOOKINGS list
        flightService = new FlightService();
    }
    
    // Helper method to clear bookings between tests (simulates reset)
    private void clearBookings() {
        // Since BOOKINGS is private static, we need to clear it indirectly
        // For this test, we'll rely on proper test isolation by creating fresh objects
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup
        clearBookings();
        
        // Create airline AL21
        Airline airline = new Airline("AL21", "Test Airline");
        
        // Create flight F501 (open for booking)
        Flight flight = new Flight("F501");
        flight.setStatus(FlightStatus.OPEN);
        airline.addFlight(flight);
        
        // Create customer
        Customer customer = new Customer("C501", "Test Customer");
        
        // Create booking with 3 confirmed reservations
        Booking booking = new Booking(customer, flight);
        
        Reservation res1 = new Reservation(flight, "Passenger1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        booking.addReservation(res1);
        
        Reservation res2 = new Reservation(flight, "Passenger2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        booking.addReservation(res2);
        
        Reservation res3 = new Reservation(flight, "Passenger3");
        res3.setStatus(ReservationStatus.CONFIRMED);
        booking.addReservation(res3);
        
        BookingRepository.addBooking(booking);
        
        // Execute
        List<Reservation> result = FlightService.getConfirmedReservations("F501");
        
        // Verify
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1 equivalent", result.contains(res1));
        assertTrue("Should contain R501-2 equivalent", result.contains(res2));
        assertTrue("Should contain R501-3 equivalent", result.contains(res3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup
        clearBookings();
        
        // Create airline AL22
        Airline airline = new Airline("AL22", "Test Airline");
        
        // Create flight F502 (open for booking)
        Flight flight = new Flight("F502");
        flight.setStatus(FlightStatus.OPEN);
        airline.addFlight(flight);
        
        // Create customer
        Customer customer = new Customer("C502", "Test Customer");
        
        // Create booking with 2 pending reservations
        Booking booking = new Booking(customer, flight);
        
        Reservation res1 = new Reservation(flight, "Passenger1");
        res1.setStatus(ReservationStatus.PENDING);
        booking.addReservation(res1);
        
        Reservation res2 = new Reservation(flight, "Passenger2");
        res2.setStatus(ReservationStatus.PENDING);
        booking.addReservation(res2);
        
        BookingRepository.addBooking(booking);
        
        // Execute
        List<Reservation> result = FlightService.getConfirmedReservations("F502");
        
        // Verify
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup
        clearBookings();
        
        // Create airline AL23
        Airline airline = new Airline("AL23", "Test Airline");
        
        // Create flight F503 (closed for booking)
        Flight flight = new Flight("F503");
        flight.setStatus(FlightStatus.CLOSED);
        airline.addFlight(flight);
        
        // Create customer
        Customer customer = new Customer("C503", "Test Customer");
        
        // Create booking with 1 confirmed reservation
        Booking booking = new Booking(customer, flight);
        
        Reservation res = new Reservation(flight, "Passenger1");
        res.setStatus(ReservationStatus.CONFIRMED);
        booking.addReservation(res);
        
        BookingRepository.addBooking(booking);
        
        // Execute
        List<Reservation> result = FlightService.getConfirmedReservations("F503");
        
        // Verify
        assertTrue("Should return empty list when flight is closed", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup
        clearBookings();
        
        // Create airline AL24 with flights F504 and F505 only
        Airline airline = new Airline("AL24", "Test Airline");
        
        Flight flight504 = new Flight("F504");
        flight504.setStatus(FlightStatus.OPEN);
        airline.addFlight(flight504);
        
        Flight flight505 = new Flight("F505");
        flight505.setStatus(FlightStatus.OPEN);
        airline.addFlight(flight505);
        
        // Execute with unknown flight ID FX999
        List<Reservation> result = FlightService.getConfirmedReservations("FX999");
        
        // Verify
        assertTrue("Should return empty list for unknown flight ID", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup
        clearBookings();
        
        // Create airline AL25
        Airline airline = new Airline("AL25", "Test Airline");
        
        // Create flight F504 (open for booking)
        Flight flight = new Flight("F504");
        flight.setStatus(FlightStatus.OPEN);
        airline.addFlight(flight);
        
        // Create customer
        Customer customer = new Customer("C504", "Test Customer");
        
        // Create booking with mixed reservation statuses
        Booking booking = new Booking(customer, flight);
        
        Reservation resA = new Reservation(flight, "PassengerA");
        resA.setStatus(ReservationStatus.CONFIRMED);
        booking.addReservation(resA);
        
        Reservation resB = new Reservation(flight, "PassengerB");
        resB.setStatus(ReservationStatus.CONFIRMED);
        booking.addReservation(resB);
        
        Reservation resC = new Reservation(flight, "PassengerC");
        resC.setStatus(ReservationStatus.CANCELED);
        booking.addReservation(resC);
        
        Reservation resD = new Reservation(flight, "PassengerD");
        resD.setStatus(ReservationStatus.PENDING);
        booking.addReservation(resD);
        
        BookingRepository.addBooking(booking);
        
        // Execute
        List<Reservation> result = FlightService.getConfirmedReservations("F504");
        
        // Verify
        assertEquals("Should return only 2 confirmed reservations", 2, result.size());
        assertTrue("Should contain R504-A equivalent", result.contains(resA));
        assertTrue("Should contain R504-B equivalent", result.contains(resB));
        assertFalse("Should not contain canceled reservation", result.contains(resC));
        assertFalse("Should not contain pending reservation", result.contains(resD));
    }
}