import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR4Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        
        // Create flight
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setOpenForBooking(true);
        f200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        
        airline.addFlight(f200);
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed", f200.isOpenForBooking());
        assertEquals("No reservations should exist", 0, f200.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        
        // Create flight
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setOpenForBooking(true);
        f201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        
        // Create customer and booking with three confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        
        // Manually create and add reservations to simulate confirmed status
        List<Reservation> reservations = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Reservation reservation = new Reservation();
            reservation.setId("R201-" + i);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            
            Passenger passenger = new Passenger();
            passenger.setName("Passenger" + i);
            reservation.setPassenger(passenger);
            reservation.setFlight(f201);
            
            reservations.add(reservation);
        }
        f201.setReservations(reservations);
        
        airline.addFlight(f201);
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        for (Reservation reservation : f201.getReservations()) {
            assertEquals("Reservation should be canceled", ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create flight that is already closed
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setOpenForBooking(false);
        f202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        
        airline.addFlight(f202);
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Close flight should return false for already closed flight", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create flight
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setOpenForBooking(true);
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        
        // Add two confirmed reservations
        List<Reservation> reservations = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Reservation reservation = new Reservation();
            reservation.setId("R203-" + i);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservation.setFlight(f203);
            reservations.add(reservation);
        }
        f203.setReservations(reservations);
        
        airline.addFlight(f203);
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00");
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Close flight should return false when current time is after departure", result);
        assertTrue("Flight should remain open", f203.isOpenForBooking());
        
        // Verify reservations remain confirmed (not canceled)
        for (Reservation reservation : f203.getReservations()) {
            assertEquals("Reservations should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create flight
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setOpenForBooking(true);
        f204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-01 01:30:00"));
        
        airline.addFlight(f204);
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00");
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Close flight should return false when current time is after departure", result);
        assertTrue("Flight should remain open", f204.isOpenForBooking());
    }
}