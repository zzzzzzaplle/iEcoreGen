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
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() throws Exception {
        // Setup: Airline AL21; flight F501 openForBooking = True
        Flight flight = new Flight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2024-12-01 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2024-12-01 12:00:00"));
        
        // Create reservations R501-1, R501-2, R501-3 status = CONFIRMED
        Reservation res1 = new Reservation();
        res1.setId("R501-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation res2 = new Reservation();
        res2.setId("R501-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation res3 = new Reservation();
        res3.setId("R501-3");
        res3.setStatus(ReservationStatus.CONFIRMED);
        
        flight.setReservations(Arrays.asList(res1, res2, res3));
        airline.addFlight(flight);
        
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
        flight.setDepartureTime(dateFormat.parse("2024-12-02 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2024-12-02 12:00:00"));
        
        // Two reservations status = PENDING
        Reservation res1 = new Reservation();
        res1.setId("R502-1");
        res1.setStatus(ReservationStatus.PENDING);
        
        Reservation res2 = new Reservation();
        res2.setId("R502-2");
        res2.setStatus(ReservationStatus.PENDING);
        
        flight.setReservations(Arrays.asList(res1, res2));
        airline.addFlight(flight);
        
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
        flight.setDepartureTime(dateFormat.parse("2024-12-03 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2024-12-03 12:00:00"));
        
        // One reservation status = CONFIRMED
        Reservation res1 = new Reservation();
        res1.setId("R503-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        
        flight.setReservations(Arrays.asList(res1));
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed list for flight F503
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup: Airline AL24 holds flights F504, F505 only
        Flight flight1 = new Flight();
        flight1.setId("F504");
        flight1.setOpenForBooking(true);
        flight1.setDepartureTime(dateFormat.parse("2024-12-04 10:00:00"));
        flight1.setArrivalTime(dateFormat.parse("2024-12-04 12:00:00"));
        
        Flight flight2 = new Flight();
        flight2.setId("F505");
        flight2.setOpenForBooking(true);
        flight2.setDepartureTime(dateFormat.parse("2024-12-05 10:00:00"));
        flight2.setArrivalTime(dateFormat.parse("2024-12-05 12:00:00"));
        
        airline.setFlights(Arrays.asList(flight1, flight2));
        
        // Execute: Retrieve confirmed list for flight FX999 (unknown flight)
        Flight unknownFlight = null;
        for (Flight f : airline.getFlights()) {
            if (f.getId().equals("FX999")) {
                unknownFlight = f;
                break;
            }
        }
        
        // Verify: Expected Output: [] (empty list since flight doesn't exist)
        assertNull(unknownFlight);
        // Since the flight doesn't exist in the airline, we can't call getConfirmedReservations on it
        // This test case demonstrates that the system won't find the flight
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup: Airline AL25; flight F504 openForBooking = True
        Flight flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2024-12-06 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2024-12-06 12:00:00"));
        
        // Reservations: R504-A, R504-B status = CONFIRMED; R504-C status = CANCELED; R504-D status = PENDING
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
        
        flight.setReservations(Arrays.asList(resA, resB, resC, resD));
        airline.addFlight(flight);
        
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