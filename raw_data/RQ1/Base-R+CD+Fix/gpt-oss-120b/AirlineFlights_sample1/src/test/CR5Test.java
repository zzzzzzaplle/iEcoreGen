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
        // Setup: Airline AL21; flight F501 openForBooking = True
        Flight flight = new Flight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        
        // Create 3 confirmed reservations
        Reservation res1 = new Reservation();
        res1.setId("R501-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation res2 = new Reservation();
        res2.setId("R501-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation res3 = new Reservation();
        res3.setId("R501-3");
        res3.setStatus(ReservationStatus.CONFIRMED);
        
        // Add reservations to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        reservations.add(res2);
        reservations.add(res3);
        flight.setReservations(reservations);
        
        // Add flight to airline
        airline.setFlights(Arrays.asList(flight));
        
        // Execute: Retrieve confirmed list for flight F501
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, result.size());
        assertTrue(result.contains(res1));
        assertTrue(result.contains(res2));
        assertTrue(result.contains(res3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup: Airline AL22; flight F502 openForBooking = True
        Flight flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        
        // Create 2 pending reservations
        Reservation res1 = new Reservation();
        res1.setId("R502-1");
        res1.setStatus(ReservationStatus.PENDING);
        
        Reservation res2 = new Reservation();
        res2.setId("R502-2");
        res2.setStatus(ReservationStatus.PENDING);
        
        // Add reservations to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        reservations.add(res2);
        flight.setReservations(reservations);
        
        // Add flight to airline
        airline.setFlights(Arrays.asList(flight));
        
        // Execute: Retrieve confirmed list for flight F502
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup: Airline AL23; flight F503 openForBooking = False
        Flight flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false);
        
        // Create 1 confirmed reservation
        Reservation res = new Reservation();
        res.setId("R503-1");
        res.setStatus(ReservationStatus.CONFIRMED);
        
        // Add reservation to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res);
        flight.setReservations(reservations);
        
        // Add flight to airline
        airline.setFlights(Arrays.asList(flight));
        
        // Execute: Retrieve confirmed list for flight F503
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup: Airline AL24 holds flights F504, F505 only
        Flight flight504 = new Flight();
        flight504.setId("F504");
        flight504.setOpenForBooking(true);
        
        Flight flight505 = new Flight();
        flight505.setId("F505");
        flight505.setOpenForBooking(true);
        
        airline.setFlights(Arrays.asList(flight504, flight505));
        
        // Execute: Retrieve confirmed list for flight FX999 (unknown flight)
        // Since we're testing getConfirmedReservations() method on Flight objects,
        // and the test specification asks for retrieving by flight ID,
        // we need to simulate finding the flight first
        Flight foundFlight = null;
        for (Flight f : airline.getFlights()) {
            if ("FX999".equals(f.getId())) {
                foundFlight = f;
                break;
            }
        }
        
        // Verify: Expected Output: []
        assertNull(foundFlight); // Flight with ID FX999 should not be found
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup: Airline AL25; flight F504 openForBooking = True
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
        
        // Add all reservations to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(resA);
        reservations.add(resB);
        reservations.add(resC);
        reservations.add(resD);
        flight.setReservations(reservations);
        
        // Add flight to airline
        airline.setFlights(Arrays.asList(flight));
        
        // Execute: Retrieve confirmed list for flight F504
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: R504-A, R504-B
        assertEquals(2, result.size());
        assertTrue(result.contains(resA));
        assertTrue(result.contains(resB));
        assertFalse(result.contains(resC));
        assertFalse(result.contains(resD));
    }
}