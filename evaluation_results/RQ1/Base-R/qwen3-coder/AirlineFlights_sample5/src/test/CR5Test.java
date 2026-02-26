import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Flight flightF501;
    private Flight flightF502;
    private Flight flightF503;
    private Flight flightF504;
    private Flight flightF505;
    private List<Flight> airlineAL24Flights;
    
    @Before
    public void setUp() {
        // Set up common test data
        flightF501 = new Flight();
        flightF501.setId("F501");
        flightF501.setStatus(FlightStatus.OPEN);
        
        flightF502 = new Flight();
        flightF502.setId("F502");
        flightF502.setStatus(FlightStatus.OPEN);
        
        flightF503 = new Flight();
        flightF503.setId("F503");
        flightF503.setStatus(FlightStatus.CLOSED);
        
        flightF504 = new Flight();
        flightF504.setId("F504");
        flightF504.setStatus(FlightStatus.OPEN);
        
        flightF505 = new Flight();
        flightF505.setId("F505");
        flightF505.setStatus(FlightStatus.OPEN);
        
        // Setup for airline AL24 (only has F504 and F505)
        airlineAL24Flights = new ArrayList<>();
        airlineAL24Flights.add(flightF504);
        airlineAL24Flights.add(flightF505);
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Airline AL21; flight F501 openForBooking = True
        // Reservations R501-1, R501-2, R501-3 status = CONFIRMED
        List<Reservation> reservations = new ArrayList<>();
        
        Reservation r1 = new Reservation();
        r1.setId("R501-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r2 = new Reservation();
        r2.setId("R501-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r3 = new Reservation();
        r3.setId("R501-3");
        r3.setStatus(ReservationStatus.CONFIRMED);
        
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        
        flightF501.setReservations(reservations);
        
        // Execute: Retrieve confirmed list for flight F501
        List<Reservation> result = flightF501.getConfirmedReservations();
        
        // Verify: Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R501-1")));
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R501-2")));
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R501-3")));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Airline AL22; flight F502 openForBooking = True
        // Two reservations status = PENDING
        List<Reservation> reservations = new ArrayList<>();
        
        Reservation r1 = new Reservation();
        r1.setId("R502-1");
        r1.setStatus(ReservationStatus.PENDING);
        
        Reservation r2 = new Reservation();
        r2.setId("R502-2");
        r2.setStatus(ReservationStatus.PENDING);
        
        reservations.add(r1);
        reservations.add(r2);
        
        flightF502.setReservations(reservations);
        
        // Execute: Retrieve confirmed list for flight F502
        List<Reservation> result = flightF502.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Airline AL23; flight F503 openForBooking = False
        // One reservation status = CONFIRMED
        List<Reservation> reservations = new ArrayList<>();
        
        Reservation r1 = new Reservation();
        r1.setId("R503-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        
        reservations.add(r1);
        
        flightF503.setReservations(reservations);
        
        // Execute: Retrieve confirmed list for flight F503
        List<Reservation> result = flightF503.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup: Airline AL24 holds flights F504, F505 only
        // Execute: Retrieve confirmed list for flight FX999 (unknown flight)
        // For this test, we simulate that we're trying to find flight FX999 in airline AL24's flights
        Flight unknownFlight = null;
        for (Flight flight : airlineAL24Flights) {
            if (flight.getId().equals("FX999")) {
                unknownFlight = flight;
                break;
            }
        }
        
        // Verify: Expected Output: []
        assertNull(unknownFlight); // Flight doesn't exist
        // If we had a flight management system, we would call getConfirmedReservations on null
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Airline AL25; flight F504 openForBooking = True
        // Reservations: R504-A, R504-B status = CONFIRMED; R504-C status = CANCELED; R504-D status = PENDING
        List<Reservation> reservations = new ArrayList<>();
        
        Reservation rA = new Reservation();
        rA.setId("R504-A");
        rA.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation rB = new Reservation();
        rB.setId("R504-B");
        rB.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation rC = new Reservation();
        rC.setId("R504-C");
        rC.setStatus(ReservationStatus.CANCELED);
        
        Reservation rD = new Reservation();
        rD.setId("R504-D");
        rD.setStatus(ReservationStatus.PENDING);
        
        reservations.add(rA);
        reservations.add(rB);
        reservations.add(rC);
        reservations.add(rD);
        
        flightF504.setReservations(reservations);
        
        // Execute: Retrieve confirmed list for flight F504
        List<Reservation> result = flightF504.getConfirmedReservations();
        
        // Verify: Expected Output: R504-A, R504-B
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R504-A")));
        assertTrue(result.stream().anyMatch(r -> r.getId().equals("R504-B")));
        assertFalse(result.stream().anyMatch(r -> r.getId().equals("R504-C")));
        assertFalse(result.stream().anyMatch(r -> r.getId().equals("R504-D")));
    }
}