import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CR4Test {
    
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP10");
        departureAirport.setCities(List.of("CityJ"));
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP11");
        arrivalAirport.setCities(List.of("CityK"));
        
        flight = new Flight();
        flight.setId("F200");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        flight.setOpen(true);
        
        // Set current time to 2025-05-01 08:00:00 (before departure)
        // In actual implementation, we'd need to mock LocalDateTime.now()
        // For this test, we'll rely on the flight departure time being in the future
        
        // Execute
        boolean result = flight.close();
        
        // Verify
        assertTrue("Flight should close successfully", result);
        assertFalse("Flight should be closed after close operation", flight.isOpen());
        assertEquals("No reservations should exist", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP12");
        departureAirport.setCities(List.of("CityL"));
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP13");
        arrivalAirport.setCities(List.of("CityM"));
        
        flight = new Flight();
        flight.setId("F201");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        flight.setOpen(true);
        
        // Create three confirmed reservations
        List<String> passengerNames = List.of("Passenger1", "Passenger2", "Passenger3");
        boolean bookingResult = flight.createBooking(passengerNames);
        assertTrue("Booking should be successful", bookingResult);
        
        // Confirm all reservations
        for (Reservation reservation : flight.getReservations()) {
            boolean updateResult = flight.updateReservation(reservation.getId(), true);
            assertTrue("Reservation confirmation should be successful", updateResult);
        }
        
        // Verify initial state
        assertEquals("Should have 3 reservations", 3, flight.getReservations().size());
        assertEquals("All 3 reservations should be confirmed", 3, flight.getConfirmedReservations().size());
        
        // Execute
        boolean result = flight.close();
        
        // Verify
        assertTrue("Flight should close successfully", result);
        assertFalse("Flight should be closed after close operation", flight.isOpen());
        assertEquals("All 3 reservations should still exist", 3, flight.getReservations().size());
        
        // Check that all confirmed reservations are now canceled
        List<Reservation> confirmedAfterClose = flight.getReservations().stream()
                .filter(Reservation::isConfirmed)
                .toList();
        assertEquals("No reservations should remain confirmed after close", 0, confirmedAfterClose.size());
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP14");
        departureAirport.setCities(List.of("CityN"));
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP15");
        arrivalAirport.setCities(List.of("CityO"));
        
        flight = new Flight();
        flight.setId("F202");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        flight.setOpen(false); // Flight is already closed
        
        // Execute
        boolean result = flight.close();
        
        // Verify
        assertFalse("Closing an already closed flight should return false", result);
        assertFalse("Flight should remain closed", flight.isOpen());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP16");
        departureAirport.setCities(List.of("CityP"));
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP17");
        arrivalAirport.setCities(List.of("CityQ"));
        
        flight = new Flight();
        flight.setId("F203");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        flight.setOpen(true);
        
        // Create two confirmed reservations
        List<String> passengerNames = List.of("PassengerA", "PassengerB");
        boolean bookingResult = flight.createBooking(passengerNames);
        assertTrue("Booking should be successful", bookingResult);
        
        // Confirm all reservations
        for (Reservation reservation : flight.getReservations()) {
            boolean updateResult = flight.updateReservation(reservation.getId(), true);
            assertTrue("Reservation confirmation should be successful", updateResult);
        }
        
        // Set departure time to be in the past (simulating current time after departure)
        flight.setDepartureTime(LocalDateTime.parse("2025-09-10 08:00:00", formatter));
        
        // Execute
        boolean result = flight.close();
        
        // Verify
        assertFalse("Closing flight after departure time should return false", result);
        assertTrue("Flight should remain open when close fails", flight.isOpen());
        
        // Verify reservations remain confirmed
        List<Reservation> confirmedReservations = flight.getReservations().stream()
                .filter(Reservation::isConfirmed)
                .toList();
        assertEquals("Confirmed reservations should remain unchanged", 2, confirmedReservations.size());
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP18");
        departureAirport.setCities(List.of("CityR"));
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP19");
        arrivalAirport.setCities(List.of("CityS"));
        
        flight = new Flight();
        flight.setId("F204");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-02 01:30:00", formatter)); // Note: corrected date format
        flight.setOpen(true);
        
        // Set departure time to be in the past (simulating flight already departed)
        flight.setDepartureTime(LocalDateTime.parse("2025-04-01 21:00:00", formatter));
        
        // Execute
        boolean result = flight.close();
        
        // Verify
        assertFalse("Closing flight after departure should return false", result);
        assertTrue("Flight should remain open when close fails", flight.isOpen());
    }
}