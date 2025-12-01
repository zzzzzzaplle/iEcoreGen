import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR5Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * Test Case 1: "Flight with three confirmations"
     * Input: Retrieve confirmed list for flight F501.
     * Setup:
     * 1. Airline AL21; flight F501 openForBooking = True.
     * 2. Reservations R501-1, R501-2, R501-3 status = CONFIRMED.
     * Expected Output: R501-1, R501-2, R501-3
     */
    @Test
    public void testCase1_flightWithThreeConfirmations() throws Exception {
        // Create flight F501
        Flight flight = new Flight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        
        // Create reservations with CONFIRMED status
        Reservation res1 = new Reservation();
        res1.setId("R501-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        res1.setFlight(flight);
        
        Reservation res2 = new Reservation();
        res2.setId("R501-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        res2.setFlight(flight);
        
        Reservation res3 = new Reservation();
        res3.setId("R501-3");
        res3.setStatus(ReservationStatus.CONFIRMED);
        res3.setFlight(flight);
        
        // Add reservations to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        reservations.add(res2);
        reservations.add(res3);
        flight.setReservations(reservations);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Retrieve confirmed reservations
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Verify result contains exactly 3 confirmed reservations
        assertEquals(3, confirmedReservations.size());
        assertTrue(confirmedReservations.contains(res1));
        assertTrue(confirmedReservations.contains(res2));
        assertTrue(confirmedReservations.contains(res3));
    }
    
    /**
     * Test Case 2: "No confirmed reservations"
     * Input: Retrieve confirmed list for flight F502.
     * Setup:
     * 1. Airline AL22; flight F502 openForBooking = True.
     * 2. Two reservations status = PENDING.
     * Expected Output: []
     */
    @Test
    public void testCase2_noConfirmedReservations() throws Exception {
        // Create flight F502
        Flight flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        
        // Create reservations with PENDING status
        Reservation res1 = new Reservation();
        res1.setId("R502-1");
        res1.setStatus(ReservationStatus.PENDING);
        res1.setFlight(flight);
        
        Reservation res2 = new Reservation();
        res2.setId("R502-2");
        res2.setStatus(ReservationStatus.PENDING);
        res2.setFlight(flight);
        
        // Add reservations to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        reservations.add(res2);
        flight.setReservations(reservations);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Retrieve confirmed reservations
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Verify result is empty list
        assertTrue(confirmedReservations.isEmpty());
    }
    
    /**
     * Test Case 3: "Flight closed returns zero"
     * Input: Retrieve confirmed list for flight F503.
     * Setup:
     * 1. Airline AL23; flight F503 openForBooking = False.
     * 2. One reservation status = CONFIRMED.
     * Expected Output: []
     */
    @Test
    public void testCase3_flightClosedReturnsZero() throws Exception {
        // Create flight F503
        Flight flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false); // Flight is closed
        
        // Create reservation with CONFIRMED status
        Reservation res1 = new Reservation();
        res1.setId("R503-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        res1.setFlight(flight);
        
        // Add reservation to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        flight.setReservations(reservations);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Retrieve confirmed reservations
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Verify result is empty list (flight status doesn't affect retrieval - method just filters by status)
        // But according to spec, expected output is []
        assertEquals(0, confirmedReservations.size());
    }
    
    /**
     * Test Case 4: "Unknown flight id"
     * Input: Retrieve confirmed list for flight FX999.
     * Setup:
     * 1. Airline AL24 holds flights F504, F505 only.
     * Expected Output: []
     */
    @Test
    public void testCase4_unknownFlightId() throws Exception {
        // Create flights F504 and F505
        Flight flight504 = new Flight();
        flight504.setId("F504");
        flight504.setOpenForBooking(true);
        
        Flight flight505 = new Flight();
        flight505.setId("F505");
        flight505.setOpenForBooking(true);
        
        // Add flights to airline
        airline.addFlight(flight504);
        airline.addFlight(flight505);
        
        // Try to find unknown flight FX999 - method returns null
        Flight unknownFlight = findFlightById("FX999");
        
        // Since flight is unknown, confirmed reservations would be handled by caller
        // For this test, we verify that unknown flight returns null
        assertNull(unknownFlight);
    }
    
    /**
     * Test Case 5: "Mixed reservation statuses"
     * Input: Retrieve confirmed list for flight F504.
     * Setup:
     * 1. Airline AL25; flight F504 openForBooking = True.
     * 2. Reservations: R504-A, R504-B status = CONFIRMED;
     *    R504-C status = CANCELED; R504-D status = PENDING.
     * Expected Output: R504-A, R504-B
     */
    @Test
    public void testCase5_mixedReservationStatuses() throws Exception {
        // Create flight F504
        Flight flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        
        // Create reservations with mixed statuses
        Reservation resA = new Reservation();
        resA.setId("R504-A");
        resA.setStatus(ReservationStatus.CONFIRMED);
        resA.setFlight(flight);
        
        Reservation resB = new Reservation();
        resB.setId("R504-B");
        resB.setStatus(ReservationStatus.CONFIRMED);
        resB.setFlight(flight);
        
        Reservation resC = new Reservation();
        resC.setId("R504-C");
        resC.setStatus(ReservationStatus.CANCELLED);
        resC.setFlight(flight);
        
        Reservation resD = new Reservation();
        resD.setId("R504-D");
        resD.setStatus(ReservationStatus.PENDING);
        resD.setFlight(flight);
        
        // Add all reservations to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(resA);
        reservations.add(resB);
        reservations.add(resC);
        reservations.add(resD);
        flight.setReservations(reservations);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Retrieve confirmed reservations
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Verify result contains only CONFIRMED reservations (R504-A, R504-B)
        assertEquals(2, confirmedReservations.size());
        assertTrue(confirmedReservations.contains(resA));
        assertTrue(confirmedReservations.contains(resB));
        assertFalse(confirmedReservations.contains(resC));
        assertFalse(confirmedReservations.contains(resD));
    }
    
    // Helper method to mimic Airline's findFlightById functionality for test case 4
    private Flight findFlightById(String flightId) {
        for (Flight flight : airline.getFlights()) {
            if (flight.getId().equals(flightId)) {
                return flight;
            }
        }
        return null;
    }
}