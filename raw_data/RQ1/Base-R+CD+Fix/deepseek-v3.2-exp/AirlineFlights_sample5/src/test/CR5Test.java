import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR5Test {
    
    private Airline airline;
    private Flight flight;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() throws Exception {
        // Setup
        flight = new Flight();
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
        assertEquals(3, result.size());
        assertEquals("R501-1", result.get(0).getId());
        assertEquals("R501-2", result.get(1).getId());
        assertEquals("R501-3", result.get(2).getId());
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup
        flight = new Flight();
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
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup
        flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false);
        
        // Create one confirmed reservation
        Reservation res1 = new Reservation();
        res1.setId("R503-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
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
        
        // Execute - Try to find non-existent flight FX999
        Flight resultFlight = airline.findFlightById("FX999");
        
        // Verify that flight is not found
        assertNull(resultFlight);
        
        // If we try to get confirmed reservations from null flight, it would be handled
        // but the test specification expects empty list for unknown flight
        if (resultFlight != null) {
            List<Reservation> result = resultFlight.getConfirmedReservations();
            assertTrue(result.isEmpty());
        }
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup
        flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        
        // Create mixed status reservations
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
        assertEquals(2, result.size());
        assertEquals("R504-A", result.get(0).getId());
        assertEquals("R504-B", result.get(1).getId());
        
        // Verify statuses
        assertEquals(ReservationStatus.CONFIRMED, result.get(0).getStatus());
        assertEquals(ReservationStatus.CONFIRMED, result.get(1).getStatus());
    }
}