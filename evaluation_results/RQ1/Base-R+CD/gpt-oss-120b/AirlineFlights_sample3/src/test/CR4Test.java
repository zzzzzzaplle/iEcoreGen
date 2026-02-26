import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws ParseException {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() throws ParseException {
        // Setup
        Airline AL6 = new Airline();
        
        Airport AP10 = new Airport();
        AP10.setId("AP10");
        Airport AP11 = new Airport();
        AP11.setId("AP11");
        
        Flight F200 = new Flight();
        F200.setId("F200");
        F200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        F200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        F200.setDepartureAirport(AP10);
        F200.setArrialAirport(AP11);
        F200.setOpenForBooking(true);
        
        AL6.addFlight(F200);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = AL6.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed", F200.isOpenForBooking());
        assertEquals("No reservations should be cancelled", 0, F200.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() throws ParseException {
        // Setup
        Airline AL7 = new Airline();
        
        Airport AP12 = new Airport();
        AP12.setId("AP12");
        Airport AP13 = new Airport();
        AP13.setId("AP13");
        
        Flight F201 = new Flight();
        F201.setId("F201");
        F201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        F201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        F201.setDepartureAirport(AP12);
        F201.setArrialAirport(AP13);
        F201.setOpenForBooking(true);
        
        AL7.addFlight(F201);
        
        // Create customer and booking with three confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        Date bookingTime = dateFormat.parse("2025-06-01 10:00:00");
        
        boolean bookingResult = customer.addBooking(F201, bookingTime, passengerNames);
        assertTrue("Booking should be successful", bookingResult);
        
        // Confirm all reservations
        for (Booking booking : customer.getBookings()) {
            for (Reservation reservation : booking.getReservations()) {
                boolean confirmResult = customer.confirm(reservation.getId(), bookingTime);
                assertTrue("Reservation confirmation should succeed", confirmResult);
                assertEquals("Reservation should be confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
            }
        }
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = AL7.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed", F201.isOpenForBooking());
        
        // Check that all confirmed reservations are cancelled
        for (Reservation reservation : F201.getReservations()) {
            assertEquals("All confirmed reservations should be cancelled", 
                         ReservationStatus.CANCELLED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws ParseException {
        // Setup
        Airline AL8 = new Airline();
        
        Flight F202 = new Flight();
        F202.setId("F202");
        F202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        F202.setArrivalTime(dateFormat.parse("2025-08-10 15:00:00"));
        F202.setOpenForBooking(false); // Already closed
        
        AL8.addFlight(F202);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = AL8.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Close flight should return false for already closed flight", result);
        assertFalse("Flight should remain closed", F202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDay() throws ParseException {
        // Setup
        Airline AL9 = new Airline();
        
        Flight F203 = new Flight();
        F203.setId("F203");
        F203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        F203.setArrivalTime(dateFormat.parse("2025-09-10 13:00:00"));
        F203.setOpenForBooking(true);
        
        AL9.addFlight(F203);
        
        // Add two confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2");
        Date bookingTime = dateFormat.parse("2025-08-01 10:00:00");
        
        boolean bookingResult = customer.addBooking(F203, bookingTime, passengerNames);
        assertTrue("Booking should be successful", bookingResult);
        
        // Confirm all reservations
        for (Booking booking : customer.getBookings()) {
            for (Reservation reservation : booking.getReservations()) {
                boolean confirmResult = customer.confirm(reservation.getId(), bookingTime);
                assertTrue("Reservation confirmation should succeed", confirmResult);
            }
        }
        
        Date currentTime = dateFormat.parse("2025-09-10 05:00:00");
        
        // Execute
        boolean result = AL9.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Close flight should return false on departure day", result);
        assertTrue("Flight should remain open", F203.isOpenForBooking());
        
        // Check that reservations are still confirmed (not cancelled)
        for (Reservation reservation : F203.getReservations()) {
            assertEquals("Reservations should remain confirmed", 
                         ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws ParseException {
        // Setup
        Airline AL10 = new Airline();
        
        Flight F204 = new Flight();
        F204.setId("F204");
        F204.setDepartureTime(dateFormat.parse("2025-04-01 10:00:00"));
        F204.setArrivalTime(dateFormat.parse("2025-04-01 14:00:00"));
        F204.setOpenForBooking(true);
        
        AL10.addFlight(F204);
        
        Date currentTime = dateFormat.parse("2025-04-01 12:00:00"); // After departure
        
        // Execute
        boolean result = AL10.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Close flight should return false after departure", result);
        assertTrue("Flight should remain open", F204.isOpenForBooking());
    }
}