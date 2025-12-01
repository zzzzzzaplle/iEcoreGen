import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CR3Test {
    
    private Airline airline;
    private Flight flight;
    private Booking booking;
    private Reservation reservation;
    
    @Before
    public void setUp() {
        airline = new Airline();
    }
    
    @Test
    public void testCase1_confirmPendingReservation() {
        // Setup
        // Create airports AP160 and AP161
        Airport ap160 = new Airport();
        ap160.setId("AP160");
        ap160.addCity("CityAA");
        
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        ap161.addCity("CityAB");
        
        // Create flight F401
        flight = new Flight();
        flight.setId("F401");
        flight.setDepartureAirportId("AP160");
        flight.setArrivalAirportId("AP161");
        flight.setDepartureTime(LocalDateTime.of(2025, 12, 10, 11, 0, 0));
        flight.setArrivalTime(LocalDateTime.of(2025, 12, 10, 15, 0, 0));
        flight.setOpenForBooking(true);
        
        // Create booking BK401 with reservation R401
        booking = new Booking();
        booking.setId("BK401");
        booking.setFlightId("F401");
        
        reservation = new Reservation();
        reservation.setId("R401");
        reservation.setPassengerName("P9");
        reservation.setStatus("pending");
        booking.getReservations().add(reservation);
        
        // Add booking to airline
        airline.getBookings().put("BK401", booking);
        
        // Set current time to 2025-11-01 09:00:00
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        // For this test, we'll assume the flight departure time is in the future
        
        // Test: Confirm reservation R401
        boolean result = reservation.confirmOrCancelReservation(flight, "R401", true);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be 'confirmed'", "confirmed", reservation.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() {
        // Setup
        // Create airports AP170 and AP171
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        ap170.addCity("CityAC");
        
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        ap171.addCity("CityAD");
        
        // Create flight F402
        flight = new Flight();
        flight.setId("F402");
        flight.setDepartureAirportId("AP170");
        flight.setArrivalAirportId("AP171");
        flight.setDepartureTime(LocalDateTime.of(2025, 12, 15, 15, 0, 0));
        flight.setArrivalTime(LocalDateTime.of(2025, 12, 15, 19, 0, 0));
        flight.setOpenForBooking(true);
        
        // Create booking BK402 with reservation R402
        booking = new Booking();
        booking.setId("BK402");
        booking.setFlightId("F402");
        
        reservation = new Reservation();
        reservation.setId("R402");
        reservation.setPassengerName("P10");
        reservation.setStatus("confirmed");
        booking.getReservations().add(reservation);
        
        // Add booking to airline
        airline.getBookings().put("BK402", booking);
        
        // Set current time to 2025-12-01 12:00:00
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        // For this test, we'll assume the flight departure time is in the future
        
        // Test: Cancel reservation R402
        boolean result = reservation.confirmOrCancelReservation(flight, "R402", false);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be 'canceled'", "canceled", reservation.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() {
        // Setup
        // Create airports AP180 and AP181
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        ap180.addCity("CityAE");
        
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        ap181.addCity("CityAF");
        
        // Create flight F403 with departure in the past
        flight = new Flight();
        flight.setId("F403");
        flight.setDepartureAirportId("AP180");
        flight.setArrivalAirportId("AP181");
        flight.setDepartureTime(LocalDateTime.of(2025, 1, 5, 6, 0, 0));
        flight.setArrivalTime(LocalDateTime.of(2025, 1, 5, 10, 0, 0));
        flight.setOpenForBooking(true);
        
        // Create reservation R403
        reservation = new Reservation();
        reservation.setId("R403");
        reservation.setPassengerName("P11");
        reservation.setStatus("pending");
        
        // Set current time to 2025-01-05 07:00:00 (flight already departed)
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        // For this test, the flight departure time is set to be in the past
        
        // Test: Try to confirm reservation R403 (should fail due to departed flight)
        boolean result = reservation.confirmOrCancelReservation(flight, "R403", true);
        
        // Verify
        assertFalse("Confirmation should fail because flight has departed", result);
        assertEquals("Reservation status should remain 'pending'", "pending", reservation.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() {
        // Setup
        // Create airports AP190 and AP191
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        ap190.addCity("CityAG");
        
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        ap191.addCity("CityAH");
        
        // Create flight F404 with openForBooking = false
        flight = new Flight();
        flight.setId("F404");
        flight.setDepartureAirportId("AP190");
        flight.setArrivalAirportId("AP191");
        flight.setDepartureTime(LocalDateTime.of(2025, 2, 1, 9, 0, 0));
        flight.setArrivalTime(LocalDateTime.of(2025, 2, 1, 13, 0, 0));
        flight.setOpenForBooking(false);
        
        // Create reservation R404
        reservation = new Reservation();
        reservation.setId("R404");
        reservation.setPassengerName("P12");
        reservation.setStatus("confirmed");
        
        // Set current time to 2025-01-20 08:00:00
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        // For this test, the flight departure time is set to be in the future
        
        // Test: Try to cancel reservation R404 (should fail due to closed flight)
        boolean result = reservation.confirmOrCancelReservation(flight, "R404", false);
        
        // Verify
        assertFalse("Cancellation should fail because flight is closed", result);
        assertEquals("Reservation status should remain 'confirmed'", "confirmed", reservation.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() {
        // Setup
        // Create airports AP200 and AP201
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        ap200.addCity("CityAI");
        
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        ap201.addCity("CityAJ");
        
        // Create flight F405
        flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirportId("AP200");
        flight.setArrivalAirportId("AP201");
        flight.setDepartureTime(LocalDateTime.of(2025, 3, 10, 10, 0, 0));
        flight.setArrivalTime(LocalDateTime.of(2025, 3, 10, 14, 0, 0));
        flight.setOpenForBooking(true);
        
        // Create booking for CU20 with reservation R405
        Booking bookingCU20 = new Booking();
        bookingCU20.setId("BK405");
        bookingCU20.setFlightId("F405");
        
        Reservation reservationR405 = new Reservation();
        reservationR405.setId("R405");
        reservationR405.setPassengerName("P13");
        reservationR405.setStatus("pending");
        bookingCU20.getReservations().add(reservationR405);
        
        // Create booking for CU21 with reservation R406
        Booking bookingCU21 = new Booking();
        bookingCU21.setId("BK406");
        bookingCU21.setFlightId("F405");
        
        Reservation reservationR406 = new Reservation();
        reservationR406.setId("R406");
        reservationR406.setPassengerName("P14");
        reservationR406.setStatus("pending");
        bookingCU21.getReservations().add(reservationR406);
        
        // Add bookings to airline
        airline.getBookings().put("BK405", bookingCU20);
        airline.getBookings().put("BK406", bookingCU21);
        
        // Set current time to 2025-02-15 09:00:00
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        // For this test, the flight departure time is set to be in the future
        
        // Test: CU20 tries to confirm R406 (which belongs to CU21) - should fail
        boolean result = reservationR405.confirmOrCancelReservation(flight, "R406", true);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation ID", result);
        assertEquals("Reservation R405 status should remain 'pending'", "pending", reservationR405.getStatus());
        assertEquals("Reservation R406 status should remain 'pending'", "pending", reservationR406.getStatus());
    }
}