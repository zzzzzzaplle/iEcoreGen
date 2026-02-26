import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

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
        ap10.addCity(new City("CityJ"));
        Airport ap11 = new Airport();
        ap11.addCity(new City("CityK"));
        
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrialAirport(ap11);
        f200.setOpenForBooking(true);
        
        airline.addFlight(f200);
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, f200.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Airport ap12 = new Airport();
        ap12.addCity(new City("CityL"));
        Airport ap13 = new Airport();
        ap13.addCity(new City("CityM"));
        
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrialAirport(ap13);
        f201.setOpenForBooking(true);
        
        // Create and add confirmed reservations
        Reservation r1 = new Reservation();
        r1.setId("R201-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        r1.setFlight(f201);
        
        Reservation r2 = new Reservation();
        r2.setId("R201-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        r2.setFlight(f201);
        
        Reservation r3 = new Reservation();
        r3.setId("R201-3");
        r3.setStatus(ReservationStatus.CONFIRMED);
        r3.setFlight(f201);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        f201.setReservations(reservations);
        
        airline.addFlight(f201);
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        List<Reservation> confirmedReservations = f201.getConfirmedReservations();
        assertEquals("All three reservations should be canceled", 0, confirmedReservations.size());
        
        for (Reservation reservation : f201.getReservations()) {
            assertEquals("Each reservation should be canceled", ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws Exception {
        // Setup
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        f202.setOpenForBooking(false); // Already closed
        
        airline.addFlight(f202);
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Flight should not be closed (already closed)", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        f203.setOpenForBooking(true);
        
        // Create and add confirmed reservations
        Reservation r1 = new Reservation();
        r1.setId("R203-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        r1.setFlight(f203);
        
        Reservation r2 = new Reservation();
        r2.setId("R203-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        r2.setFlight(f203);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        f203.setReservations(reservations);
        
        airline.addFlight(f203);
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Flight should not be closed (after departure time)", result);
        assertTrue("Flight should remain open", f203.isOpenForBooking());
        
        // Check that reservations are still confirmed (not canceled)
        List<Reservation> confirmedReservations = f203.getConfirmedReservations();
        assertEquals("Both reservations should remain confirmed", 2, confirmedReservations.size());
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws Exception {
        // Setup
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-01 01:30:00"));
        f204.setOpenForBooking(true);
        
        airline.addFlight(f204);
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Flight should not be closed (after departure time)", result);
        assertTrue("Flight should remain open", f204.isOpenForBooking());
    }
}