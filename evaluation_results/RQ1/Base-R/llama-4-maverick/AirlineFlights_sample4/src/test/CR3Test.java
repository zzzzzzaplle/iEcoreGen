import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Reservation reservation;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        flight = new Flight();
    }
    
    @Test
    public void testCase1_confirmPendingReservation() {
        // Setup for Test Case 1
        departureAirport.setId("AP160");
        arrivalAirport.setId("AP161");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-10T11:00:00"));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10T15:00:00"));
        flight.setStatus("open");
        
        // Create reservation R401 with status "pending"
        reservation = new Reservation();
        reservation.setPassengerName("P9");
        reservation.setStatus("pending");
        flight.getReservations().add(reservation);
        
        // Mock current time to be before departure
        // Since we cannot directly mock LocalDateTime.now(), we rely on the flight's departure time being in the future
        
        // Test: Confirm reservation R401
        boolean result = flight.updateReservation(reservation.getId(), true);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be 'confirmed'", "confirmed", reservation.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() {
        // Setup for Test Case 2
        departureAirport.setId("AP170");
        arrivalAirport.setId("AP171");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-15T15:00:00"));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-15T18:00:00"));
        flight.setStatus("open");
        
        // Create reservation R402 with status "confirmed"
        reservation = new Reservation();
        reservation.setPassengerName("P10");
        reservation.setStatus("confirmed");
        flight.getReservations().add(reservation);
        
        // Mock current time to be before departure
        // Since we cannot directly mock LocalDateTime.now(), we rely on the flight's departure time being in the future
        
        // Test: Cancel reservation R402
        boolean result = flight.updateReservation(reservation.getId(), false);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be 'canceled'", "canceled", reservation.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() {
        // Setup for Test Case 3
        departureAirport.setId("AP180");
        arrivalAirport.setId("AP181");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        
        // Set departure time in the past (flight already departed)
        flight.setDepartureTime(LocalDateTime.parse("2025-01-05T06:00:00"));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-05T09:00:00"));
        flight.setStatus("open");
        
        // Create reservation R403 with status "pending"
        reservation = new Reservation();
        reservation.setPassengerName("P11");
        reservation.setStatus("pending");
        flight.getReservations().add(reservation);
        
        // Test: Try to confirm reservation after flight departure
        boolean result = flight.updateReservation(reservation.getId(), true);
        
        // Verify
        assertFalse("Reservation confirmation should fail when flight has departed", result);
        assertEquals("Reservation status should remain 'pending'", "pending", reservation.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() {
        // Setup for Test Case 4
        departureAirport.setId("AP190");
        arrivalAirport.setId("AP191");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-01T09:00:00"));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-01T12:00:00"));
        flight.setStatus("closed"); // Flight is closed
        
        // Create reservation R404 with status "confirmed"
        reservation = new Reservation();
        reservation.setPassengerName("P12");
        reservation.setStatus("confirmed");
        flight.getReservations().add(reservation);
        
        // Test: Try to cancel reservation on closed flight
        boolean result = flight.updateReservation(reservation.getId(), false);
        
        // Verify
        assertFalse("Reservation cancellation should fail when flight is closed", result);
        assertEquals("Reservation status should remain 'confirmed'", "confirmed", reservation.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() {
        // Setup for Test Case 5
        departureAirport.setId("AP200");
        arrivalAirport.setId("AP201");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-10T10:00:00"));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-10T13:00:00"));
        flight.setStatus("open");
        
        // Create reservation R405 for customer CU20
        Reservation reservationR405 = new Reservation();
        reservationR405.setPassengerName("P13");
        reservationR405.setStatus("pending");
        flight.getReservations().add(reservationR405);
        
        // Create reservation R406 for customer CU21
        Reservation reservationR406 = new Reservation();
        reservationR406.setPassengerName("P14");
        reservationR406.setStatus("pending");
        // Note: R406 is NOT added to flight's reservations to simulate unknown reservation
        
        // Test: Try to confirm unknown reservation R406
        boolean result = flight.updateReservation(reservationR406.getId(), true);
        
        // Verify
        assertFalse("Reservation confirmation should fail for unknown reservation ID", result);
    }
}