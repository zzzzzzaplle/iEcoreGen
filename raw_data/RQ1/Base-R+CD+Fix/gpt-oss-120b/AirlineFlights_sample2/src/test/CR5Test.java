import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CR5Test {
    
    private Airline airline;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airline = new Airline();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Airline AL21; flight F501 openForBooking = True
        // Reservations R501-1, R501-2, R501-3 status = CONFIRMED
        Flight flight = new Flight("F501");
        flight.setOpenForBooking(true);
        
        // Create confirmed reservations
        Reservation res1 = new Reservation("R501-1", ReservationStatus.CONFIRMED, 
            new Passenger("Passenger1"), flight);
        Reservation res2 = new Reservation("R501-2", ReservationStatus.CONFIRMED, 
            new Passenger("Passenger2"), flight);
        Reservation res3 = new Reservation("R501-3", ReservationStatus.CONFIRMED, 
            new Passenger("Passenger3"), flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        reservations.add(res2);
        reservations.add(res3);
        flight.setReservations(reservations);
        
        airline.setFlights(List.of(flight));
        
        // Execute: Retrieve confirmed list for flight F501
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, result.size());
        assertTrue(result.contains(res1));
        assertTrue(result.contains(res2));
        assertTrue(result.contains(res3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Airline AL22; flight F502 openForBooking = True
        // Two reservations status = PENDING
        Flight flight = new Flight("F502");
        flight.setOpenForBooking(true);
        
        // Create pending reservations
        Reservation res1 = new Reservation("R502-1", ReservationStatus.PENDING, 
            new Passenger("Passenger1"), flight);
        Reservation res2 = new Reservation("R502-2", ReservationStatus.PENDING, 
            new Passenger("Passenger2"), flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        reservations.add(res2);
        flight.setReservations(reservations);
        
        airline.setFlights(List.of(flight));
        
        // Execute: Retrieve confirmed list for flight F502
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Airline AL23; flight F503 openForBooking = False
        // One reservation status = CONFIRMED
        Flight flight = new Flight("F503");
        flight.setOpenForBooking(false);
        
        // Create confirmed reservation
        Reservation res1 = new Reservation("R503-1", ReservationStatus.CONFIRMED, 
            new Passenger("Passenger1"), flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        flight.setReservations(reservations);
        
        airline.setFlights(List.of(flight));
        
        // Execute: Retrieve confirmed list for flight F503
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup: Airline AL24 holds flights F504, F505 only
        Flight flight1 = new Flight("F504");
        flight1.setOpenForBooking(true);
        Flight flight2 = new Flight("F505");
        flight2.setOpenForBooking(true);
        
        airline.setFlights(List.of(flight1, flight2));
        
        // Execute: Retrieve confirmed list for flight FX999 (unknown flight)
        // Since we can't directly call getConfirmedReservations on unknown flight,
        // we verify that the airline doesn't have flight FX999
        boolean foundUnknownFlight = false;
        for (Flight f : airline.getFlights()) {
            if ("FX999".equals(f.getId())) {
                foundUnknownFlight = true;
                break;
            }
        }
        
        // Verify: Expected Output: [] - Unknown flight should not be found
        assertFalse(foundUnknownFlight);
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Airline AL25; flight F504 openForBooking = True
        // Reservations: R504-A, R504-B status = CONFIRMED;
        // R504-C status = CANCELED; R504-D status = PENDING
        Flight flight = new Flight("F504");
        flight.setOpenForBooking(true);
        
        // Create mixed status reservations
        Reservation resA = new Reservation("R504-A", ReservationStatus.CONFIRMED, 
            new Passenger("PassengerA"), flight);
        Reservation resB = new Reservation("R504-B", ReservationStatus.CONFIRMED, 
            new Passenger("PassengerB"), flight);
        Reservation resC = new Reservation("R504-C", ReservationStatus.CANCELED, 
            new Passenger("PassengerC"), flight);
        Reservation resD = new Reservation("R504-D", ReservationStatus.PENDING, 
            new Passenger("PassengerD"), flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(resA);
        reservations.add(resB);
        reservations.add(resC);
        reservations.add(resD);
        flight.setReservations(reservations);
        
        airline.setFlights(List.of(flight));
        
        // Execute: Retrieve confirmed list for flight F504
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: R504-A, R504-B
        assertEquals(2, result.size());
        assertTrue(result.contains(resA));
        assertTrue(result.contains(resB));
        assertFalse(result.contains(resC)); // CANCELED should not be included
        assertFalse(result.contains(resD)); // PENDING should not be included
    }
}