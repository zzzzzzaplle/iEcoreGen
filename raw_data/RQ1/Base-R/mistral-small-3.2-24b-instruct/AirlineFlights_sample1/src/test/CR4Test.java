import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    // Mock implementation of Flight.getBookings() for testing purposes
    private static class TestFlight extends Flight {
        private List<Booking> bookings = new ArrayList<>();
        
        public void setBookings(List<Booking> bookings) {
            this.bookings = bookings;
        }
        
        @Override
        public List<Booking> getBookings() {
            return bookings;
        }
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup
        Airport ap10 = new Airport("AP10", List.of("CityJ"));
        Airport ap11 = new Airport("AP11", List.of("CityK"));
        
        LocalDateTime departTime = LocalDateTime.parse("2025-06-20 09:00:00", FORMATTER);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-06-20 13:00:00", FORMATTER);
        
        TestFlight f200 = new TestFlight();
        f200.setId("F200");
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setDepartureTime(departTime);
        f200.setArrivalTime(arriveTime);
        f200.setOpenForBooking(true);
        f200.setPublished(false);
        
        // Set current time to 2025-05-01 08:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-05-01 08:00:00", FORMATTER);
        
        // Test closeFlight method
        boolean result = f200.closeFlight();
        
        // Verify results
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, f200.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup
        Airport ap12 = new Airport("AP12", List.of("CityL"));
        Airport ap13 = new Airport("AP13", List.of("CityM"));
        
        LocalDateTime departTime = LocalDateTime.parse("2025-07-15 14:00:00", FORMATTER);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-07-15 18:00:00", FORMATTER);
        
        TestFlight f201 = new TestFlight();
        f201.setId("F201");
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        f201.setDepartureTime(departTime);
        f201.setArrivalTime(arriveTime);
        f201.setOpenForBooking(true);
        f201.setPublished(false);
        
        // Create reservations
        Reservation r201_1 = new Reservation("R201-1", f201, "Passenger1");
        r201_1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r201_2 = new Reservation("R201-2", f201, "Passenger2");
        r201_2.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r201_3 = new Reservation("R201-3", f201, "Passenger3");
        r201_3.setStatus(ReservationStatus.CONFIRMED);
        
        // Create booking with reservations
        Booking booking = new Booking("B201", f201);
        booking.setReservations(List.of(r201_1, r201_2, r201_3));
        
        f201.setBookings(List.of(booking));
        
        // Set current time to 2025-06-10 12:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-06-10 12:00:00", FORMATTER);
        
        // Verify initial state
        assertEquals("Should have 3 confirmed reservations initially", 3, f201.getConfirmedReservations().size());
        
        // Test closeFlight method
        boolean result = f201.closeFlight();
        
        // Verify results
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        List<Reservation> confirmedReservations = f201.getConfirmedReservations();
        assertEquals("All reservations should be canceled", 0, confirmedReservations.size());
        
        // Verify each reservation status
        assertEquals("Reservation R201-1 should be canceled", ReservationStatus.CANCELED, r201_1.getStatus());
        assertEquals("Reservation R201-2 should be canceled", ReservationStatus.CANCELED, r201_2.getStatus());
        assertEquals("Reservation R201-3 should be canceled", ReservationStatus.CANCELED, r201_3.getStatus());
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup
        Airport ap14 = new Airport("AP14", List.of("CityN"));
        Airport ap15 = new Airport("AP15", List.of("CityO"));
        
        LocalDateTime departTime = LocalDateTime.parse("2025-08-10 11:00:00", FORMATTER);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-08-10 13:30:00", FORMATTER);
        
        TestFlight f202 = new TestFlight();
        f202.setId("F202");
        f202.setDepartureAirport(ap14);
        f202.setArrivalAirport(ap15);
        f202.setDepartureTime(departTime);
        f202.setArrivalTime(arriveTime);
        f202.setOpenForBooking(false); // Flight already closed
        f202.setPublished(false);
        
        // Set current time to 2025-07-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-07-01 09:00:00", FORMATTER);
        
        // Test closeFlight method
        boolean result = f202.closeFlight();
        
        // Verify results
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup
        Airport ap16 = new Airport("AP16", List.of("CityP"));
        Airport ap17 = new Airport("AP17", List.of("CityQ"));
        
        LocalDateTime departTime = LocalDateTime.parse("2025-09-10 09:00:00", FORMATTER);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-09-10 15:30:00", FORMATTER);
        
        TestFlight f203 = new TestFlight();
        f203.setId("F203");
        f203.setDepartureAirport(ap16);
        f203.setArrivalAirport(ap17);
        f203.setDepartureTime(departTime);
        f203.setArrivalTime(arriveTime);
        f203.setOpenForBooking(true);
        f203.setPublished(false);
        
        // Create reservations
        Reservation r203_1 = new Reservation("R203-1", f203, "Passenger4");
        r203_1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r203_2 = new Reservation("R203-2", f203, "Passenger5");
        r203_2.setStatus(ReservationStatus.CONFIRMED);
        
        // Create booking with reservations
        Booking booking = new Booking("B203", f203);
        booking.setReservations(List.of(r203_1, r203_2));
        
        f203.setBookings(List.of(booking));
        
        // Set current time to 2025-09-10 09:10:00 (after departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-09-10 09:10:00", FORMATTER);
        
        // Verify initial state
        assertEquals("Should have 2 confirmed reservations initially", 2, f203.getConfirmedReservations().size());
        
        // Test closeFlight method
        boolean result = f203.closeFlight();
        
        // Verify results
        assertFalse("Should return false when trying to close after departure time", result);
        assertTrue("Flight should remain open for booking", f203.isOpenForBooking());
        
        // Verify reservations are not canceled
        List<Reservation> confirmedReservations = f203.getConfirmedReservations();
        assertEquals("Reservations should remain confirmed", 2, confirmedReservations.size());
        assertEquals("Reservation R203-1 should remain confirmed", ReservationStatus.CONFIRMED, r203_1.getStatus());
        assertEquals("Reservation R203-2 should remain confirmed", ReservationStatus.CONFIRMED, r203_2.getStatus());
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup
        Airport ap18 = new Airport("AP18", List.of("CityR"));
        Airport ap19 = new Airport("AP19", List.of("CityS"));
        
        LocalDateTime departTime = LocalDateTime.parse("2025-04-01 22:00:00", FORMATTER);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-04-01 01:30:00", FORMATTER);
        
        TestFlight f204 = new TestFlight();
        f204.setId("F204");
        f204.setDepartureAirport(ap18);
        f204.setArrivalAirport(ap19);
        f204.setDepartureTime(departTime);
        f204.setArrivalTime(arriveTime);
        f204.setOpenForBooking(true);
        f204.setPublished(false);
        
        // Set current time to 2025-04-01 22:05:00 (after departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-04-01 22:05:00", FORMATTER);
        
        // Test closeFlight method
        boolean result = f204.closeFlight();
        
        // Verify results
        assertFalse("Should return false when trying to close after departure", result);
        assertTrue("Flight should remain open for booking", f204.isOpenForBooking());
    }
}