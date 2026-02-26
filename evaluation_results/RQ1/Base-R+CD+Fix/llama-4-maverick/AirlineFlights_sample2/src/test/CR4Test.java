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
        f200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setOpenForBooking(true);
        
        airline.addFlight(f200);
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Should return true when successfully closing flight", result);
        assertFalse("Flight should be closed after operation", f200.isOpenForBooking());
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
        f201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        f201.setOpenForBooking(true);
        
        // Create reservations
        Reservation r2011 = new Reservation();
        r2011.setId("R201-1");
        r2011.setStatus(ReservationStatus.CONFIRMED);
        r2011.setFlight(f201);
        
        Reservation r2012 = new Reservation();
        r2012.setId("R201-2");
        r2012.setStatus(ReservationStatus.CONFIRMED);
        r2012.setFlight(f201);
        
        Reservation r2013 = new Reservation();
        r2013.setId("R201-3");
        r2013.setStatus(ReservationStatus.CONFIRMED);
        r2013.setFlight(f201);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r2011);
        reservations.add(r2012);
        reservations.add(r2013);
        f201.setReservations(reservations);
        
        airline.addFlight(f201);
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Should return true when successfully closing flight", result);
        assertFalse("Flight should be closed after operation", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        List<Reservation> flightReservations = f201.getReservations();
        assertEquals("Should have 3 reservations", 3, flightReservations.size());
        for (Reservation r : flightReservations) {
            assertEquals("All confirmed reservations should be canceled", 
                        ReservationStatus.CANCELED, r.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws Exception {
        // Setup
        Airport ap14 = new Airport();
        ap14.setId("AP14");
        Airport ap15 = new Airport();
        ap15.setId("AP15");
        
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        f202.setDepartureAirport(ap14);
        f202.setArrivalAirport(ap15);
        f202.setOpenForBooking(false); // Already closed
        
        airline.addFlight(f202);
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Should return false when flight is already closed", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Airport ap16 = new Airport();
        ap16.setId("AP16");
        Airport ap17 = new Airport();
        ap17.setId("AP17");
        
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        f203.setDepartureAirport(ap16);
        f203.setArrivalAirport(ap17);
        f203.setOpenForBooking(true);
        
        // Create confirmed reservations
        Reservation r2031 = new Reservation();
        r2031.setId("R203-1");
        r2031.setStatus(ReservationStatus.CONFIRMED);
        r2031.setFlight(f203);
        
        Reservation r2032 = new Reservation();
        r2032.setId("R203-2");
        r2032.setStatus(ReservationStatus.CONFIRMED);
        r2032.setFlight(f203);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r2031);
        reservations.add(r2032);
        f203.setReservations(reservations);
        
        airline.addFlight(f203);
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should return false when trying to close after departure time", result);
        assertTrue("Flight should remain open since close operation failed", f203.isOpenForBooking());
        
        // Reservations should remain confirmed
        List<Reservation> flightReservations = f203.getReservations();
        for (Reservation r : flightReservations) {
            assertEquals("Reservations should remain unchanged", 
                        ReservationStatus.CONFIRMED, r.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws Exception {
        // Setup
        Airport ap18 = new Airport();
        ap18.setId("AP18");
        Airport ap19 = new Airport();
        ap19.setId("AP19");
        
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-01 01:30:00"));
        f204.setDepartureAirport(ap18);
        f204.setArrivalAirport(ap19);
        f204.setOpenForBooking(true);
        
        airline.addFlight(f204);
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should return false when trying to close after departure", result);
        assertTrue("Flight should remain open since close operation failed", f204.isOpenForBooking());
    }
}