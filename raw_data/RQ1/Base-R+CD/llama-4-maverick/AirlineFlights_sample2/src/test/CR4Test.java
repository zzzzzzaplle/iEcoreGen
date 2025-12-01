import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() throws Exception {
        // Setup
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", flight.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        
        // Create and add confirmed reservations
        List<Reservation> reservations = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Reservation reservation = new Reservation();
            reservation.setId("R201-" + i);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservation.setFlight(flight);
            reservations.add(reservation);
        }
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", flight.isOpenForBooking());
        
        // Check that all confirmed reservations are canceled
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("Reservation should be canceled", ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws Exception {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setOpenForBooking(false); // Flight already closed
        flight.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        
        // Create and add confirmed reservations
        List<Reservation> reservations = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Reservation reservation = new Reservation();
            reservation.setId("R203-" + i);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservation.setFlight(flight);
            reservations.add(reservation);
        }
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should return false when closing after departure time", result);
        assertTrue("Flight should remain open", flight.isOpenForBooking());
        
        // Verify reservations remain unchanged
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("Reservations should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws Exception {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-02 01:30:00")); // Fixed: arrival should be next day
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should return false when closing after departure", result);
        assertTrue("Flight should remain open", flight.isOpenForBooking());
    }
}