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
        
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setOpenForBooking(true);
        f200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setReservations(new ArrayList<Reservation>());
        
        airline.addFlight(f200);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, f200.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setOpenForBooking(true);
        f201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        
        // Create three confirmed reservations
        List<Reservation> reservations = new ArrayList<>();
        Reservation r1 = new Reservation();
        r1.setId("R201-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        reservations.add(r1);
        
        Reservation r2 = new Reservation();
        r2.setId("R201-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        reservations.add(r2);
        
        Reservation r3 = new Reservation();
        r3.setId("R201-3");
        r3.setStatus(ReservationStatus.CONFIRMED);
        reservations.add(r3);
        
        f201.setReservations(reservations);
        
        airline.addFlight(f201);
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", f201.isOpenForBooking());
        assertEquals("All three reservations should be canceled", 3, f201.getReservations().size());
        for (Reservation reservation : f201.getReservations()) {
            assertEquals("Each reservation should be canceled", ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws Exception {
        // Setup
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setOpenForBooking(false); // Already closed
        f202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        
        airline.addFlight(f202);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setOpenForBooking(true);
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        
        // Create two confirmed reservations
        List<Reservation> reservations = new ArrayList<>();
        Reservation r1 = new Reservation();
        r1.setId("R203-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        reservations.add(r1);
        
        Reservation r2 = new Reservation();
        r2.setId("R203-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        reservations.add(r2);
        
        f203.setReservations(reservations);
        
        airline.addFlight(f203);
        
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should return false when closing after departure time", result);
        assertTrue("Flight should remain open for booking", f203.isOpenForBooking());
        // Reservations should remain confirmed
        for (Reservation reservation : f203.getReservations()) {
            assertEquals("Reservations should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws Exception {
        // Setup
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setOpenForBooking(true);
        f204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-01 01:30:00"));
        
        airline.addFlight(f204);
        
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should return false when closing after departure", result);
        assertTrue("Flight should remain open for booking", f204.isOpenForBooking());
    }
}