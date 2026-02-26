import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CR4Test {
    private Flight flight;
    private Booking booking;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        flight = new Flight();
        booking = new Booking();
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup
        flight.setDepartureAirportId("AP10");
        flight.setArrivalAirportId("AP11");
        flight.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        flight.setOpenForBooking(true);
        
        // Execute
        boolean result = flight.closeFlight();
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", flight.isOpenForBooking());
        assertEquals("No reservations should exist", 0, flight.getReservations().size());
        assertEquals("No confirmed reservations should exist", 0, flight.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup
        flight.setDepartureAirportId("AP12");
        flight.setArrivalAirportId("AP13");
        flight.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        flight.setOpenForBooking(true);
        
        // Create booking with three reservations
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Passenger1");
        passengerNames.add("Passenger2");
        passengerNames.add("Passenger3");
        
        boolean bookingCreated = booking.createBooking(flight, passengerNames);
        assertTrue("Booking should be created successfully", bookingCreated);
        
        // Confirm all reservations
        Map<String, Reservation> reservations = flight.getReservations();
        for (Reservation reservation : reservations.values()) {
            boolean confirmed = reservation.confirm();
            assertTrue("Reservation should be confirmed", confirmed);
        }
        
        // Verify initial state
        assertEquals("Should have 3 reservations", 3, flight.getReservations().size());
        assertEquals("All 3 reservations should be confirmed", 3, flight.getConfirmedReservations().size());
        
        // Execute
        boolean result = flight.closeFlight();
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", flight.isOpenForBooking());
        assertEquals("Should still have 3 reservations", 3, flight.getReservations().size());
        assertEquals("No reservations should be confirmed after flight closure", 0, flight.getConfirmedReservations().size());
        
        // Verify all reservations are canceled
        for (Reservation reservation : reservations.values()) {
            assertFalse("Reservation should be canceled", reservation.isConfirmed());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup
        flight.setDepartureAirportId("AP14");
        flight.setArrivalAirportId("AP15");
        flight.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        flight.setOpenForBooking(false);
        
        // Execute
        boolean result = flight.closeFlight();
        
        // Verify
        assertFalse("Should return false when flight is already closed", result);
        assertFalse("Flight should remain closed", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup
        flight.setDepartureAirportId("AP16");
        flight.setArrivalAirportId("AP17");
        flight.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        flight.setOpenForBooking(true);
        
        // Create booking with two reservations
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Passenger4");
        passengerNames.add("Passenger5");
        
        boolean bookingCreated = booking.createBooking(flight, passengerNames);
        assertTrue("Booking should be created successfully", bookingCreated);
        
        // Confirm all reservations
        Map<String, Reservation> reservations = flight.getReservations();
        for (Reservation reservation : reservations.values()) {
            boolean confirmed = reservation.confirm();
            assertTrue("Reservation should be confirmed", confirmed);
        }
        
        // Execute - Attempt to close flight after departure time
        boolean result = flight.closeFlight();
        
        // Verify
        assertFalse("Should return false when trying to close flight after departure time", result);
        assertTrue("Flight should remain open for booking", flight.isOpenForBooking());
        assertEquals("Both reservations should remain confirmed", 2, flight.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup
        flight.setDepartureAirportId("AP18");
        flight.setArrivalAirportId("AP19");
        flight.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-02 01:30:00", formatter)); // Fixed: arrival should be next day
        flight.setOpenForBooking(true);
        
        // Execute - Attempt to close flight after departure
        boolean result = flight.closeFlight();
        
        // Verify
        assertFalse("Should return false when trying to close flight after departure", result);
        assertTrue("Flight should remain open for booking", flight.isOpenForBooking());
    }
}