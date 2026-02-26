import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CR5Test {
    private Airline airline;
    
    @Before
    public void setUp() {
        airline = new Airline();
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Airline AL21; flight F501 openForBooking = True
        Flight flightF501 = new Flight();
        flightF501.setId("F501");
        flightF501.setOpenForBooking(true);
        airline.setFlights(new ArrayList<>());
        airline.getFlights().add(flightF501);
        
        // Setup: Reservations R501-1, R501-2, R501-3 status = CONFIRMED
        Booking booking1 = new Booking();
        booking1.setFlightId("F501");
        
        Reservation reservation1 = new Reservation();
        reservation1.setId("R501-1");
        reservation1.setStatus("confirmed");
        
        Reservation reservation2 = new Reservation();
        reservation2.setId("R501-2");
        reservation2.setStatus("confirmed");
        
        Reservation reservation3 = new Reservation();
        reservation3.setId("R501-3");
        reservation3.setStatus("confirmed");
        
        booking1.setReservations(new ArrayList<>());
        booking1.getReservations().add(reservation1);
        booking1.getReservations().add(reservation2);
        booking1.getReservations().add(reservation3);
        
        airline.setBookings(new HashMap<>());
        airline.getBookings().put("B501", booking1);
        
        // Input: Retrieve confirmed list for flight F501
        List<Reservation> result = airline.getConfirmedReservations("F501");
        
        // Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, result.size());
        assertTrue(result.contains(reservation1));
        assertTrue(result.contains(reservation2));
        assertTrue(result.contains(reservation3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Airline AL22; flight F502 openForBooking = True
        Flight flightF502 = new Flight();
        flightF502.setId("F502");
        flightF502.setOpenForBooking(true);
        airline.setFlights(new ArrayList<>());
        airline.getFlights().add(flightF502);
        
        // Setup: Two reservations status = PENDING
        Booking booking2 = new Booking();
        booking2.setFlightId("F502");
        
        Reservation reservation1 = new Reservation();
        reservation1.setId("R502-1");
        reservation1.setStatus("pending");
        
        Reservation reservation2 = new Reservation();
        reservation2.setId("R502-2");
        reservation2.setStatus("pending");
        
        booking2.setReservations(new ArrayList<>());
        booking2.getReservations().add(reservation1);
        booking2.getReservations().add(reservation2);
        
        airline.setBookings(new HashMap<>());
        airline.getBookings().put("B502", booking2);
        
        // Input: Retrieve confirmed list for flight F502
        List<Reservation> result = airline.getConfirmedReservations("F502");
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Airline AL23; flight F503 openForBooking = False
        Flight flightF503 = new Flight();
        flightF503.setId("F503");
        flightF503.setOpenForBooking(false);
        airline.setFlights(new ArrayList<>());
        airline.getFlights().add(flightF503);
        
        // Setup: One reservation status = CONFIRMED
        Booking booking3 = new Booking();
        booking3.setFlightId("F503");
        
        Reservation reservation = new Reservation();
        reservation.setId("R503-1");
        reservation.setStatus("confirmed");
        
        booking3.setReservations(new ArrayList<>());
        booking3.getReservations().add(reservation);
        
        airline.setBookings(new HashMap<>());
        airline.getBookings().put("B503", booking3);
        
        // Input: Retrieve confirmed list for flight F503
        List<Reservation> result = airline.getConfirmedReservations("F503");
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup: Airline AL24 holds flights F504, F505 only
        Flight flightF504 = new Flight();
        flightF504.setId("F504");
        flightF504.setOpenForBooking(true);
        
        Flight flightF505 = new Flight();
        flightF505.setId("F505");
        flightF505.setOpenForBooking(true);
        
        airline.setFlights(new ArrayList<>());
        airline.getFlights().add(flightF504);
        airline.getFlights().add(flightF505);
        
        // Input: Retrieve confirmed list for flight FX999
        List<Reservation> result = airline.getConfirmedReservations("FX999");
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Airline AL25; flight F504 openForBooking = True
        Flight flightF504 = new Flight();
        flightF504.setId("F504");
        flightF504.setOpenForBooking(true);
        airline.setFlights(new ArrayList<>());
        airline.getFlights().add(flightF504);
        
        // Setup: Reservations: R504-A, R504-B status = CONFIRMED; R504-C status = CANCELED; R504-D status = PENDING
        Booking booking5 = new Booking();
        booking5.setFlightId("F504");
        
        Reservation reservationA = new Reservation();
        reservationA.setId("R504-A");
        reservationA.setStatus("confirmed");
        
        Reservation reservationB = new Reservation();
        reservationB.setId("R504-B");
        reservationB.setStatus("confirmed");
        
        Reservation reservationC = new Reservation();
        reservationC.setId("R504-C");
        reservationC.setStatus("canceled");
        
        Reservation reservationD = new Reservation();
        reservationD.setId("R504-D");
        reservationD.setStatus("pending");
        
        booking5.setReservations(new ArrayList<>());
        booking5.getReservations().add(reservationA);
        booking5.getReservations().add(reservationB);
        booking5.getReservations().add(reservationC);
        booking5.getReservations().add(reservationD);
        
        airline.setBookings(new HashMap<>());
        airline.getBookings().put("B504", booking5);
        
        // Input: Retrieve confirmed list for flight F504
        List<Reservation> result = airline.getConfirmedReservations("F504");
        
        // Expected Output: R504-A, R504-B
        assertEquals(2, result.size());
        assertTrue(result.contains(reservationA));
        assertTrue(result.contains(reservationB));
        assertFalse(result.contains(reservationC));
        assertFalse(result.contains(reservationD));
    }
}