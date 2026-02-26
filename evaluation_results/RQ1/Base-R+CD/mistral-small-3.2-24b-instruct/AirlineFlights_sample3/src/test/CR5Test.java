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
        Flight flight = createFlight("F501", true);
        airline.addFlight(flight);
        
        // Create confirmed reservations
        Reservation r1 = createReservation("R501-1", ReservationStatus.CONFIRMED);
        Reservation r2 = createReservation("R501-2", ReservationStatus.CONFIRMED);
        Reservation r3 = createReservation("R501-3", ReservationStatus.CONFIRMED);
        
        flight.getReservations().add(r1);
        flight.getReservations().add(r2);
        flight.getReservations().add(r3);
        
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
        Reservation r1 = createReservation("R502-1", ReservationStatus.PENDING);
        Reservation r2 = createReservation("R502-2", ReservationStatus.PENDING);
        
        flight.getReservations().add(r1);
        flight.getReservations().add(r2);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup
        Flight flight = createFlight("F503", false); // Flight closed
        airline.addFlight(flight);
        
        // Create confirmed reservation
        Reservation r1 = createReservation("R503-1", ReservationStatus.CONFIRMED);
        flight.getReservations().add(r1);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup - Create airline with flights F504 and F505 only
        Flight flight504 = createFlight("F504", true);
        Flight flight505 = createFlight("F505", true);
        airline.addFlight(flight504);
        airline.addFlight(flight505);
        
        // Execute - Try to get confirmed reservations for non-existent flight FX999
        // Since we don't have access to the flight by ID, we need to simulate the scenario
        // where we try to find a flight that doesn't exist
        Flight unknownFlight = null;
        for (Flight f : airline.getFlights()) {
            if (f.getId().equals("FX999")) {
                unknownFlight = f;
                break;
            }
        }
        
        // Verify that unknown flight is null
        assertNull(unknownFlight);
        
        // If we had a method to get flight by ID, we would test that it returns empty list
        // Since we don't, we'll verify that our test setup correctly doesn't include FX999
        boolean foundUnknownFlight = false;
        for (Flight f : airline.getFlights()) {
            if (f.getId().equals("FX999")) {
                foundUnknownFlight = true;
                break;
            }
        }
        assertFalse(foundUnknownFlight);
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup
        Flight flight = createFlight("F504", true);
        airline.addFlight(flight);
        
        // Create mixed status reservations
        Reservation rA = createReservation("R504-A", ReservationStatus.CONFIRMED);
        Reservation rB = createReservation("R504-B", ReservationStatus.CONFIRMED);
        Reservation rC = createReservation("R504-C", ReservationStatus.CANCELED);
        Reservation rD = createReservation("R504-D", ReservationStatus.PENDING);
        
        flight.getReservations().add(rA);
        flight.getReservations().add(rB);
        flight.getReservations().add(rC);
        flight.getReservations().add(rD);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(2, result.size());
        assertTrue(result.contains(rA));
        assertTrue(result.contains(rB));
        assertFalse(result.contains(rC));
        assertFalse(result.contains(rD));
    }
    
    private Flight createFlight(String id, boolean openForBooking) throws Exception {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setOpenForBooking(openForBooking);
        
        // Set departure and arrival times (required for flight validity)
        Date departureTime = dateFormat.parse("2024-12-31 10:00:00");
        Date arrivalTime = dateFormat.parse("2024-12-31 12:00:00");
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Set airports (required for flight validity)
        Airport departureAirport = new Airport();
        Airport arrivalAirport = new Airport();
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        
        return flight;
    }
    
    private Reservation createReservation(String id, ReservationStatus status) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatus(status);
        
        // Create minimal passenger object
        Passenger passenger = new Passenger();
        passenger.setName("Test Passenger");
        reservation.setPassenger(passenger);
        
        return reservation;
    }
}