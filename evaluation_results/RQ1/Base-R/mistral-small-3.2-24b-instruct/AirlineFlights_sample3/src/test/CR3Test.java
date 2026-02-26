import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CR3Test {
    private Flight flight;
    private Booking booking;
    private Reservation reservation;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        // Reset flight and booking objects before each test
        flight = new Flight();
        booking = new Booking();
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() {
        // Setup
        flight.setDepartureAirportId("AP160");
        flight.setArrivalAirportId("AP161");
        flight.setDepartureTime(LocalDateTime.parse("2025-12-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 15:00:00", formatter));
        flight.setOpenForBooking(true);
        
        // Create reservation R401 with PENDING status
        reservation = new Reservation();
        reservation.setFlightId(flight.getId());
        reservation.setPassengerName("P9");
        // Status is PENDING by default (isConfirmed = false)
        
        // Add reservation to booking
        booking.getReservations().add(reservation);
        flight.getReservations().put(reservation.getId(), reservation);
        
        // Set current time to 2025-11-01 09:00:00 (before departure)
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        // For this test, we rely on the flight's departure time validation
        
        // Test: Confirm reservation R401
        boolean result = booking.updateReservation(reservation.getId(), true);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertTrue("Reservation status should be CONFIRMED", reservation.isConfirmed());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() {
        // Setup
        flight.setDepartureAirportId("AP170");
        flight.setArrivalAirportId("AP171");
        flight.setDepartureTime(LocalDateTime.parse("2025-12-15 15:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-15 19:00:00", formatter)); // Added arrival time for completeness
        flight.setOpenForBooking(true);
        
        // Create reservation R402 with CONFIRMED status
        reservation = new Reservation();
        reservation.setFlightId(flight.getId());
        reservation.setPassengerName("P10");
        reservation.confirm(); // Set status to CONFIRMED
        
        // Add reservation to booking
        booking.getReservations().add(reservation);
        flight.getReservations().put(reservation.getId(), reservation);
        
        // Set current time to 2025-12-01 12:00:00 (before departure)
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        
        // Test: Cancel reservation R402
        boolean result = booking.updateReservation(reservation.getId(), false);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertFalse("Reservation status should be PENDING after cancellation", reservation.isConfirmed());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() {
        // Setup
        flight.setDepartureAirportId("AP180");
        flight.setArrivalAirportId("AP181");
        flight.setDepartureTime(LocalDateTime.parse("2025-01-05 06:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-05 10:00:00", formatter)); // Added arrival time for completeness
        flight.setOpenForBooking(true);
        
        // Create reservation R403 with PENDING status
        reservation = new Reservation();
        reservation.setFlightId(flight.getId());
        reservation.setPassengerName("P11");
        // Status is PENDING by default (isConfirmed = false)
        
        // Add reservation to booking
        booking.getReservations().add(reservation);
        flight.getReservations().put(reservation.getId(), reservation);
        
        // Current time is 2025-01-05 07:00:00 (after departure)
        // The Booking.createBooking method checks if flight departure time is before current time
        // Since we cannot easily mock LocalDateTime.now(), we'll test the logic indirectly
        // by ensuring that if we try to create a booking for a departed flight, it should fail
        
        // Test: Try to confirm reservation - should fail because flight has departed
        // Since we can't easily mock the current time, we'll test the validation logic
        // by creating a booking with current time after departure (which should fail)
        Booking testBooking = new Booking();
        List<String> passengerNames = Arrays.asList("P11");
        
        // Create a flight that has already departed
        Flight departedFlight = new Flight();
        departedFlight.setDepartureAirportId("AP180");
        departedFlight.setArrivalAirportId("AP181");
        departedFlight.setDepartureTime(LocalDateTime.parse("2025-01-05 06:00:00", formatter));
        departedFlight.setArrivalTime(LocalDateTime.parse("2025-01-05 10:00:00", formatter));
        departedFlight.setOpenForBooking(true);
        
        // The booking creation should fail because flight has departed
        boolean bookingResult = testBooking.createBooking(departedFlight, passengerNames);
        
        // Verify that booking creation fails (indirect test of time validation)
        assertFalse("Booking should fail for departed flight", bookingResult);
        
        // For the direct reservation update test, we expect false since the validation
        // would prevent the operation if properly implemented with time checking
        boolean result = booking.updateReservation(reservation.getId(), true);
        
        // The test specification expects false when flight has departed
        // This is a limitation of the current implementation which doesn't check flight time in updateReservation
        // In a real implementation, updateReservation should validate flight status
        assertFalse("Confirmation should fail for departed flight", result);
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() {
        // Setup
        flight.setDepartureAirportId("AP190");
        flight.setArrivalAirportId("AP191");
        flight.setDepartureTime(LocalDateTime.parse("2025-02-01 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-01 13:00:00", formatter)); // Added arrival time for completeness
        flight.setOpenForBooking(false); // Flight is closed for booking
        
        // Create reservation R404 with CONFIRMED status
        reservation = new Reservation();
        reservation.setFlightId(flight.getId());
        reservation.setPassengerName("P12");
        reservation.confirm(); // Set status to CONFIRMED
        
        // Add reservation to booking
        booking.getReservations().add(reservation);
        flight.getReservations().put(reservation.getId(), reservation);
        
        // Current time is 2025-01-20 08:00:00 (before departure but flight is closed)
        
        // Test: Try to cancel reservation - should fail because flight is closed
        // The Booking.createBooking method checks if flight is open for booking
        // Since the flight is closed, any booking operations should fail
        
        Booking testBooking = new Booking();
        List<String> passengerNames = Arrays.asList("P12");
        
        // Try to create booking for closed flight - should fail
        boolean bookingResult = testBooking.createBooking(flight, passengerNames);
        assertFalse("Booking should fail for closed flight", bookingResult);
        
        // For the direct reservation update test, we expect false
        boolean result = booking.updateReservation(reservation.getId(), false);
        
        // The test specification expects false when flight is closed
        // This is a limitation of the current implementation which doesn't check flight status in updateReservation
        // In a real implementation, updateReservation should validate flight is open for booking
        assertFalse("Cancellation should fail for closed flight", result);
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() {
        // Setup
        flight.setDepartureAirportId("AP200");
        flight.setArrivalAirportId("AP201");
        flight.setDepartureTime(LocalDateTime.parse("2025-03-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-10 14:00:00", formatter)); // Added arrival time for completeness
        flight.setOpenForBooking(true);
        
        // Create reservation R405 for customer CU20
        Reservation reservationCU20 = new Reservation();
        reservationCU20.setFlightId(flight.getId());
        reservationCU20.setPassengerName("P13");
        // Status is PENDING by default
        
        // Add reservation to booking for CU20
        booking.getReservations().add(reservationCU20);
        flight.getReservations().put(reservationCU20.getId(), reservationCU20);
        
        // Create a different booking for CU21 with reservation R406
        Booking bookingCU21 = new Booking();
        Reservation reservationCU21 = new Reservation();
        reservationCU21.setFlightId(flight.getId());
        reservationCU21.setPassengerName("P14");
        // Status is PENDING by default
        
        bookingCU21.getReservations().add(reservationCU21);
        flight.getReservations().put(reservationCU21.getId(), reservationCU21);
        
        // Current time is 2025-02-15 09:00:00
        
        // Test: Customer CU20 tries to confirm reservation R406 (which belongs to CU21)
        // This should fail because R406 is not in CU20's booking
        boolean result = booking.updateReservation(reservationCU21.getId(), true);
        
        // Verify
        assertFalse("Should return false for unknown reservation identifier", result);
    }
}