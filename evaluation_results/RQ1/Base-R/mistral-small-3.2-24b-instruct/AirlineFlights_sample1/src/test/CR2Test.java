import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Flight flight;
    private Booking booking;
    
    @Test
    public void testCase1_twoNewPassengersSucceed() {
        // Setup
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", FORMATTER);
        LocalDateTime departureTime = LocalDateTime.parse("2025-10-05 08:00:00", FORMATTER);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-10-05 12:00:00", FORMATTER);
        
        Airport departureAirport = new Airport("AP100", new ArrayList<>());
        Airport arrivalAirport = new Airport("AP101", new ArrayList<>());
        
        flight = new Flight("F300", departureAirport, arrivalAirport, departureTime, arrivalTime);
        flight.setOpenForBooking(true);
        
        booking = new Booking("BK300", flight);
        
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = booking.createBooking(passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Should have exactly 2 reservations", 2, booking.getReservations().size());
        assertEquals("First passenger should be Peter", "Peter", booking.getReservations().get(0).getPassengerName());
        assertEquals("Second passenger should be Beck", "Beck", booking.getReservations().get(1).getPassengerName());
        assertEquals("First reservation status should be PENDING", ReservationStatus.PENDING, booking.getReservations().get(0).getStatus());
        assertEquals("Second reservation status should be PENDING", ReservationStatus.PENDING, booking.getReservations().get(1).getStatus());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() {
        // Setup
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", FORMATTER);
        LocalDateTime departureTime = LocalDateTime.parse("2025-10-05 08:00:00", FORMATTER);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-10-05 10:00:00", FORMATTER);
        
        Airport departureAirport = new Airport("AP102", new ArrayList<>());
        Airport arrivalAirport = new Airport("AP103", new ArrayList<>());
        
        flight = new Flight("F301", departureAirport, arrivalAirport, departureTime, arrivalTime);
        flight.setOpenForBooking(true);
        
        booking = new Booking("BK301", flight);
        
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Execute
        boolean result = booking.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passengers", result);
        assertTrue("No reservations should be created", booking.getReservations().isEmpty());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() {
        // Setup
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", FORMATTER);
        LocalDateTime departureTime = LocalDateTime.parse("2025-10-05 18:00:00", FORMATTER);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-10-06 02:00:00", FORMATTER);
        
        Airport departureAirport = new Airport("AP104", new ArrayList<>());
        Airport arrivalAirport = new Airport("AP105", new ArrayList<>());
        
        flight = new Flight("F302", departureAirport, arrivalAirport, departureTime, arrivalTime);
        flight.setOpenForBooking(true);
        
        booking = new Booking("BK-OLD", flight);
        
        // Create pre-existing booking for passenger "Jucy"
        Reservation existingReservation = new Reservation("R302-A", flight, "Jucy");
        booking.getReservations().add(existingReservation);
        
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Execute
        boolean result = booking.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to existing passenger", result);
        assertEquals("Should still have only 1 reservation", 1, booking.getReservations().size());
        assertEquals("Existing reservation should remain", "Jucy", booking.getReservations().get(0).getPassengerName());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() {
        // Setup
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", FORMATTER);
        LocalDateTime departureTime = LocalDateTime.parse("2025-10-05 18:00:00", FORMATTER);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-10-06 02:00:00", FORMATTER);
        
        Airport departureAirport = new Airport("AP106", new ArrayList<>());
        Airport arrivalAirport = new Airport("AP107", new ArrayList<>());
        
        flight = new Flight("F303", departureAirport, arrivalAirport, departureTime, arrivalTime);
        flight.setOpenForBooking(false); // Flight is closed
        
        booking = new Booking("BK303", flight);
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = booking.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to closed flight", result);
        assertTrue("No reservations should be created", booking.getReservations().isEmpty());
    }
    
    @Test
    public void testCase5_timeAfterDepartureTime() {
        // Setup - Note: Current time is AFTER departure time
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-06 09:00:00", FORMATTER);
        LocalDateTime departureTime = LocalDateTime.parse("2025-10-05 18:00:00", FORMATTER);
        LocalDateTime arrivalTime = LocalDateTime.parse("2025-10-06 02:00:00", FORMATTER);
        
        Airport departureAirport = new Airport("AP104", new ArrayList<>());
        Airport arrivalAirport = new Airport("AP105", new ArrayList<>());
        
        flight = new Flight("F303", departureAirport, arrivalAirport, departureTime, arrivalTime);
        flight.setOpenForBooking(true);
        
        booking = new Booking("BK303", flight);
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = booking.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail because current time is after departure time", result);
        assertTrue("No reservations should be created", booking.getReservations().isEmpty());
    }
}