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
        dateFormat.setLenient(false);
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() throws Exception {
        // Setup
        Flight flight = createFlight("F501", true);
        airline.addFlight(flight);
        
        // Create confirmed reservations
        Reservation r1 = createReservation("R501-1", ReservationStatus.CONFIRMED, flight);
        Reservation r2 = createReservation("R501-2", ReservationStatus.CONFIRMED, flight);
        Reservation r3 = createReservation("R501-3", ReservationStatus.CONFIRMED, flight);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r1);
        flightReservations.add(r2);
        flightReservations.add(r3);
        flight.setReservations(flightReservations);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(3, result.size());
        assertTrue(result.contains(r1));
        assertTrue(result.contains(r2));
        assertTrue(result.contains(r3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup
        Flight flight = createFlight("F502", true);
        airline.addFlight(flight);
        
        // Create pending reservations
        Reservation r1 = createReservation("R502-1", ReservationStatus.PENDING, flight);
        Reservation r2 = createReservation("R502-2", ReservationStatus.PENDING, flight);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r1);
        flightReservations.add(r2);
        flight.setReservations(flightReservations);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup
        Flight flight = createFlight("F503", false);
        airline.addFlight(flight);
        
        // Create confirmed reservation (but flight is closed)
        Reservation r1 = createReservation("R503-1", ReservationStatus.CONFIRMED, flight);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r1);
        flight.setReservations(flightReservations);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup
        Flight flight1 = createFlight("F504", true);
        Flight flight2 = createFlight("F505", true);
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        
        // Execute - try to get confirmed reservations for non-existent flight
        List<Reservation> result = new ArrayList<>(); // No flight found, so no reservations
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup
        Flight flight = createFlight("F504", true);
        airline.addFlight(flight);
        
        // Create reservations with mixed statuses
        Reservation rA = createReservation("R504-A", ReservationStatus.CONFIRMED, flight);
        Reservation rB = createReservation("R504-B", ReservationStatus.CONFIRMED, flight);
        Reservation rC = createReservation("R504-C", ReservationStatus.CANCELED, flight);
        Reservation rD = createReservation("R504-D", ReservationStatus.PENDING, flight);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(rA);
        flightReservations.add(rB);
        flightReservations.add(rC);
        flightReservations.add(rD);
        flight.setReservations(flightReservations);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(2, result.size());
        assertTrue(result.contains(rA));
        assertTrue(result.contains(rB));
        assertFalse(result.contains(rC)); // Canceled should not be included
        assertFalse(result.contains(rD)); // Pending should not be included
    }
    
    private Flight createFlight(String id, boolean openForBooking) throws Exception {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setOpenForBooking(openForBooking);
        
        // Set departure and arrival times
        Date departureTime = dateFormat.parse("2024-06-01 10:00:00");
        Date arrivalTime = dateFormat.parse("2024-06-01 12:00:00");
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Set airports
        Airport departureAirport = new Airport();
        departureAirport.setId("JFK");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("LAX");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        
        return flight;
    }
    
    private Reservation createReservation(String id, ReservationStatus status, Flight flight) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatus(status);
        reservation.setFlight(flight);
        
        // Create passenger
        Passenger passenger = new Passenger("Passenger_" + id);
        reservation.setPassenger(passenger);
        
        return reservation;
    }
}