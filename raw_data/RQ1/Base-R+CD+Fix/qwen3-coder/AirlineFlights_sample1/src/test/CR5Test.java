import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR5Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() throws Exception {
        // Setup
        Flight flight = new Flight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        
        // Create three confirmed reservations
        Reservation res1 = new Reservation();
        res1.setId("R501-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation res2 = new Reservation();
        res2.setId("R501-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation res3 = new Reservation();
        res3.setId("R501-3");
        res3.setStatus(ReservationStatus.CONFIRMED);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        reservations.add(res2);
        reservations.add(res3);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1", result.contains(res1));
        assertTrue("Should contain R501-2", result.contains(res2));
        assertTrue("Should contain R501-3", result.contains(res3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup
        Flight flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        
        // Create two pending reservations
        Reservation res1 = new Reservation();
        res1.setId("R502-1");
        res1.setStatus(ReservationStatus.PENDING);
        
        Reservation res2 = new Reservation();
        res2.setId("R502-2");
        res2.setStatus(ReservationStatus.PENDING);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        reservations.add(res2);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup
        Flight flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false);
        
        // Create one confirmed reservation
        Reservation res = new Reservation();
        res.setId("R503-1");
        res.setStatus(ReservationStatus.CONFIRMED);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list when flight is closed", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup
        Flight flight1 = new Flight();
        flight1.setId("F504");
        flight1.setOpenForBooking(true);
        
        Flight flight2 = new Flight();
        flight2.setId("F505");
        flight2.setOpenForBooking(true);
        
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        
        // Execute - try to get confirmed reservations for non-existent flight
        // Note: Since we're testing Flight.getConfirmedReservations() directly,
        // we need to create a flight with ID FX999
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setOpenForBooking(true);
        
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list for unknown flight", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup
        Flight flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        
        // Create reservations with mixed statuses
        Reservation resA = new Reservation();
        resA.setId("R504-A");
        resA.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation resB = new Reservation();
        resB.setId("R504-B");
        resB.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation resC = new Reservation();
        resC.setId("R504-C");
        resC.setStatus(ReservationStatus.CANCELED);
        
        Reservation resD = new Reservation();
        resD.setId("R504-D");
        resD.setStatus(ReservationStatus.PENDING);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(resA);
        reservations.add(resB);
        reservations.add(resC);
        reservations.add(resD);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return 2 confirmed reservations", 2, result.size());
        assertTrue("Should contain R504-A", result.contains(resA));
        assertTrue("Should contain R504-B", result.contains(resB));
        assertFalse("Should not contain canceled reservation R504-C", result.contains(resC));
        assertFalse("Should not contain pending reservation R504-D", result.contains(resD));
    }
}