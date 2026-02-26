import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    
    private Airline airline;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airline = new Airline();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() {
        // Setup
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        ap10.addCity("CityJ");
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        ap11.addCity("CityK");
        
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setDepartureAirportId("AP10");
        f200.setArrivalAirportId("AP11");
        f200.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        f200.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        f200.setOpenForBooking(true);
        
        airline.getFlights().add(f200);
        
        // Current time set to 2025-05-01 08:00:00 (before departure)
        // No bookings/reservations created
        
        // Execute
        boolean result = airline.closeFlight("F200");
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, airline.getConfirmedReservations("F200").size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        ap12.addCity("CityL");
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        ap13.addCity("CityM");
        
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setDepartureAirportId("AP12");
        f201.setArrivalAirportId("AP13");
        f201.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        f201.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        f201.setOpenForBooking(true);
        
        airline.getFlights().add(f201);
        
        // Create booking with three reservations
        Booking booking = new Booking();
        booking.setFlightId("F201");
        
        Reservation r2011 = new Reservation();
        r2011.setId("R201-1");
        r2011.setPassengerName("Passenger1");
        r2011.setStatus("confirmed");
        
        Reservation r2012 = new Reservation();
        r2012.setId("R201-2");
        r2012.setPassengerName("Passenger2");
        r2012.setStatus("confirmed");
        
        Reservation r2013 = new Reservation();
        r2013.setId("R201-3");
        r2013.setPassengerName("Passenger3");
        r2013.setStatus("confirmed");
        
        booking.getReservations().add(r2011);
        booking.getReservations().add(r2012);
        booking.getReservations().add(r2013);
        
        airline.getBookings().put(booking.getId(), booking);
        
        // Current time set to 2025-06-10 12:00:00 (before departure)
        
        // Execute
        boolean result = airline.closeFlight("F201");
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        List<Reservation> reservations = booking.getReservations();
        for (Reservation reservation : reservations) {
            assertEquals("Reservation should be canceled", "canceled", reservation.getStatus());
        }
        
        assertEquals("No confirmed reservations should remain", 0, airline.getConfirmedReservations("F201").size());
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() {
        // Setup
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureAirportId("AP14");
        f202.setArrivalAirportId("AP15");
        f202.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        f202.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        f202.setOpenForBooking(false); // Flight already closed
        
        airline.getFlights().add(f202);
        
        // Current time set to 2025-07-01 09:00:00 (before departure)
        
        // Execute
        boolean result = airline.closeFlight("F202");
        
        // Verify
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() {
        // Setup
        Airport ap16 = new Airport();
        ap16.setId("AP16");
        
        Airport ap17 = new Airport();
        ap17.setId("AP17");
        
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureAirportId("AP16");
        f203.setArrivalAirportId("AP17");
        f203.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        f203.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        f203.setOpenForBooking(true);
        
        airline.getFlights().add(f203);
        
        // Create booking with two confirmed reservations
        Booking booking = new Booking();
        booking.setFlightId("F203");
        
        Reservation r2031 = new Reservation();
        r2031.setId("R203-1");
        r2031.setPassengerName("Passenger1");
        r2031.setStatus("confirmed");
        
        Reservation r2032 = new Reservation();
        r2032.setId("R203-2");
        r2032.setPassengerName("Passenger2");
        r2032.setStatus("confirmed");
        
        booking.getReservations().add(r2031);
        booking.getReservations().add(r2032);
        
        airline.getBookings().put(booking.getId(), booking);
        
        // Current time set to 2025-09-10 09:10:00 (after departure time)
        // Flight departure time has passed
        
        // Execute
        boolean result = airline.closeFlight("F203");
        
        // Verify
        assertFalse("Should return false when trying to close flight after departure time", result);
        assertTrue("Flight should remain open since close operation failed", f203.isOpenForBooking());
        
        // Reservations should remain confirmed
        List<Reservation> reservations = booking.getReservations();
        for (Reservation reservation : reservations) {
            assertEquals("Reservations should remain confirmed", "confirmed", reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() {
        // Setup
        Airport ap18 = new Airport();
        ap18.setId("AP18");
        
        Airport ap19 = new Airport();
        ap19.setId("AP19");
        
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureAirportId("AP18");
        f204.setArrivalAirportId("AP19");
        f204.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        f204.setArrivalTime(LocalDateTime.parse("2025-04-02 01:30:00", formatter)); // Fixed date format
        f204.setOpenForBooking(true);
        
        airline.getFlights().add(f204);
        
        // Current time set to 2025-04-01 22:05:00 (after departure time)
        // Flight has already departed
        
        // Execute
        boolean result = airline.closeFlight("F204");
        
        // Verify
        assertFalse("Should return false when trying to close flight after departure", result);
        assertTrue("Flight should remain open since close operation failed", f204.isOpenForBooking());
    }
}