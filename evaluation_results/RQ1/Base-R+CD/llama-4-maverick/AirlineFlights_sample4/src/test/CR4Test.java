import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    private SimpleDateFormat sdf;
    private Airline airline;
    
    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() throws Exception {
        // Setup
        airline = new Airline();
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setDepartureTime(sdf.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(sdf.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setOpenForBooking(true);
        
        airline.addFlight(f200);
        Date currentTime = sdf.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed for booking", f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, f200.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() throws Exception {
        // Setup
        airline = new Airline();
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setDepartureTime(sdf.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(sdf.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        f201.setOpenForBooking(true);
        
        // Create and add confirmed reservations
        List<Reservation> reservations = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Reservation r = new Reservation();
            r.setId("R201-" + i);
            r.setStatus(ReservationStatus.CONFIRMED);
            Passenger p = new Passenger();
            p.setName("Passenger" + i);
            r.setPassenger(p);
            r.setFlight(f201);
            reservations.add(r);
        }
        f201.setReservations(reservations);
        
        airline.addFlight(f201);
        Date currentTime = sdf.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed for booking", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        List<Reservation> confirmedReservations = f201.getConfirmedReservations();
        assertEquals("All confirmed reservations should be canceled", 0, confirmedReservations.size());
        
        for (Reservation r : f201.getReservations()) {
            assertEquals("Each reservation should be canceled", ReservationStatus.CANCELED, r.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws Exception {
        // Setup
        airline = new Airline();
        Airport ap = new Airport();
        ap.setId("AP");
        
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureTime(sdf.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(sdf.parse("2025-08-10 13:30:00"));
        f202.setDepartureAirport(ap);
        f202.setArrivalAirport(ap);
        f202.setOpenForBooking(false); // Flight already closed
        
        airline.addFlight(f202);
        Date currentTime = sdf.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        airline = new Airline();
        Airport ap = new Airport();
        ap.setId("AP");
        
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureTime(sdf.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(sdf.parse("2025-09-10 15:30:00"));
        f203.setDepartureAirport(ap);
        f203.setArrivalAirport(ap);
        f203.setOpenForBooking(true);
        
        // Create and add confirmed reservations
        List<Reservation> reservations = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Reservation r = new Reservation();
            r.setId("R203-" + i);
            r.setStatus(ReservationStatus.CONFIRMED);
            Passenger p = new Passenger();
            p.setName("Passenger" + i);
            r.setPassenger(p);
            r.setFlight(f203);
            reservations.add(r);
        }
        f203.setReservations(reservations);
        
        airline.addFlight(f203);
        Date currentTime = sdf.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should return false when closing after departure time", result);
        assertTrue("Flight should remain open for booking", f203.isOpenForBooking());
        
        // Check that reservations remain confirmed (not canceled)
        List<Reservation> confirmedReservations = f203.getConfirmedReservations();
        assertEquals("Reservations should remain confirmed", 2, confirmedReservations.size());
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws Exception {
        // Setup
        airline = new Airline();
        Airport ap = new Airport();
        ap.setId("AP");
        
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureTime(sdf.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(sdf.parse("2025-04-01 01:30:00")); // Note: arrival is after midnight
        f204.setDepartureAirport(ap);
        f204.setArrivalAirport(ap);
        f204.setOpenForBooking(true);
        
        airline.addFlight(f204);
        Date currentTime = sdf.parse("2025-04-01 22:05:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should return false when closing after departure", result);
        assertTrue("Flight should remain open for booking", f204.isOpenForBooking());
    }
}