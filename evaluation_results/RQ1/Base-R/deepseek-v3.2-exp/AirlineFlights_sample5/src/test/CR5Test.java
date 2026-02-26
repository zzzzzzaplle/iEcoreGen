import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup
        Flight flight = createFlight("F501", true, false);
        airlineSystem.getFlights().add(flight);
        
        // Create confirmed reservations
        Reservation r1 = createReservation("R501-1", flight, "confirmed");
        Reservation r2 = createReservation("R501-2", flight, "confirmed");
        Reservation r3 = createReservation("R501-3", flight, "confirmed");
        
        airlineSystem.getReservations().add(r1);
        airlineSystem.getReservations().add(r2);
        airlineSystem.getReservations().add(r3);
        
        // Execute
        List<Reservation> result = airlineSystem.getConfirmedReservationsForFlight("F501");
        
        // Verify
        assertEquals(3, result.size());
        assertTrue(containsReservation(result, "R501-1"));
        assertTrue(containsReservation(result, "R501-2"));
        assertTrue(containsReservation(result, "R501-3"));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup
        Flight flight = createFlight("F502", true, false);
        airlineSystem.getFlights().add(flight);
        
        // Create pending reservations
        Reservation r1 = createReservation("R502-1", flight, "pending");
        Reservation r2 = createReservation("R502-2", flight, "pending");
        
        airlineSystem.getReservations().add(r1);
        airlineSystem.getReservations().add(r2);
        
        // Execute
        List<Reservation> result = airlineSystem.getConfirmedReservationsForFlight("F502");
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup
        Flight flight = createFlight("F503", false, false);
        airlineSystem.getFlights().add(flight);
        
        // Create confirmed reservation on closed flight
        Reservation r1 = createReservation("R503-1", flight, "confirmed");
        airlineSystem.getReservations().add(r1);
        
        // Execute
        List<Reservation> result = airlineSystem.getConfirmedReservationsForFlight("F503");
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup - Add flights F504 and F505 only
        Flight flight1 = createFlight("F504", true, false);
        Flight flight2 = createFlight("F505", true, false);
        airlineSystem.getFlights().add(flight1);
        airlineSystem.getFlights().add(flight2);
        
        // Execute with unknown flight ID
        List<Reservation> result = airlineSystem.getConfirmedReservationsForFlight("FX999");
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup
        Flight flight = createFlight("F504", true, false);
        airlineSystem.getFlights().add(flight);
        
        // Create mixed status reservations
        Reservation r1 = createReservation("R504-A", flight, "confirmed");
        Reservation r2 = createReservation("R504-B", flight, "confirmed");
        Reservation r3 = createReservation("R504-C", flight, "canceled");
        Reservation r4 = createReservation("R504-D", flight, "pending");
        
        airlineSystem.getReservations().add(r1);
        airlineSystem.getReservations().add(r2);
        airlineSystem.getReservations().add(r3);
        airlineSystem.getReservations().add(r4);
        
        // Execute
        List<Reservation> result = airlineSystem.getConfirmedReservationsForFlight("F504");
        
        // Verify
        assertEquals(2, result.size());
        assertTrue(containsReservation(result, "R504-A"));
        assertTrue(containsReservation(result, "R504-B"));
        assertFalse(containsReservation(result, "R504-C"));
        assertFalse(containsReservation(result, "R504-D"));
    }
    
    // Helper method to create a flight with specified properties
    private Flight createFlight(String id, boolean openForBooking, boolean isPublished) {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setOpenForBooking(openForBooking);
        flight.setPublished(isPublished);
        
        // Set airports
        Airport departure = new Airport();
        departure.setId("DEP" + id);
        departure.setName("Departure Airport " + id);
        
        Airport arrival = new Airport();
        arrival.setId("ARR" + id);
        arrival.setName("Arrival Airport " + id);
        
        flight.setDepartureAirport(departure);
        flight.setArrivalAirport(arrival);
        
        // Set future departure and arrival times
        LocalDateTime now = LocalDateTime.now();
        flight.setDepartureTime(now.plusDays(1));
        flight.setArrivalTime(now.plusDays(1).plusHours(3));
        
        return flight;
    }
    
    // Helper method to create a reservation with specified properties
    private Reservation createReservation(String id, Flight flight, String status) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setFlight(flight);
        reservation.setPassengerName("Passenger " + id);
        reservation.setStatus(status);
        return reservation;
    }
    
    // Helper method to check if a reservation exists in the list by ID
    private boolean containsReservation(List<Reservation> reservations, String reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(reservationId)) {
                return true;
            }
        }
        return false;
    }
}