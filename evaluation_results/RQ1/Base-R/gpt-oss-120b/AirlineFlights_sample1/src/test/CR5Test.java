import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CR5Test {
    
    private AirlineReservationSystem.AirlineService service;
    private AirlineReservationSystem.City city1, city2;
    private AirlineReservationSystem.Airport airport1, airport2;
    
    @Before
    public void setUp() {
        service = new AirlineReservationSystem.AirlineService();
        
        // Create common test data
        city1 = new AirlineReservationSystem.City();
        city1.setId("C1");
        city1.setName("Metropolis");
        
        city2 = new AirlineReservationSystem.City();
        city2.setId("C2");
        city2.setName("Gotham");
        
        airport1 = new AirlineReservationSystem.Airport();
        airport1.setId("AP1");
        airport1.setName("Metro Intl");
        airport1.setCity(city1);
        
        airport2 = new AirlineReservationSystem.Airport();
        airport2.setId("AP2");
        airport2.setName("Gotham Central");
        airport2.setCity(city2);
    }
    
    private AirlineReservationSystem.Flight createFlight(String flightId, boolean openForBooking) {
        AirlineReservationSystem.Flight flight = new AirlineReservationSystem.Flight();
        flight.setId(flightId);
        flight.setDepartureAirport(airport1);
        flight.setArrivalAirport(airport2);
        
        // Set departure time in the future to ensure flight is valid
        flight.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(3));
        
        flight.setStatus(openForBooking ? 
            AirlineReservationSystem.FlightStatus.OPEN : 
            AirlineReservationSystem.FlightStatus.CLOSED);
        
        // Manually add to service's flights map since we're setting ID directly
        service.getAllFlights().clear(); // Clear any existing flights
        service.getAllFlights().add(flight);
        
        return flight;
    }
    
    private AirlineReservationSystem.Reservation createReservation(String reservationId, 
            AirlineReservationSystem.Flight flight, 
            AirlineReservationSystem.ReservationStatus status) {
        AirlineReservationSystem.Reservation reservation = new AirlineReservationSystem.Reservation();
        reservation.setId(reservationId);
        reservation.setPassengerName("Passenger " + reservationId);
        reservation.setFlight(flight);
        reservation.setStatus(status);
        
        // Manually add to service's reservations map
        service.getAllReservations().add(reservation);
        
        return reservation;
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Flight F501 with three confirmed reservations
        AirlineReservationSystem.Flight flight = createFlight("F501", true);
        
        createReservation("R501-1", flight, AirlineReservationSystem.ReservationStatus.CONFIRMED);
        createReservation("R501-2", flight, AirlineReservationSystem.ReservationStatus.CONFIRMED);
        createReservation("R501-3", flight, AirlineReservationSystem.ReservationStatus.CONFIRMED);
        
        // Execute: Retrieve confirmed reservations for flight F501
        List<AirlineReservationSystem.Reservation> result = service.getConfirmedReservations("F501");
        
        // Verify: Should return all three confirmed reservations
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        
        // Verify all reservations have CONFIRMED status
        for (AirlineReservationSystem.Reservation res : result) {
            assertEquals("Reservation should be CONFIRMED", 
                AirlineReservationSystem.ReservationStatus.CONFIRMED, res.getStatus());
            assertEquals("Reservation should belong to flight F501", "F501", res.getFlight().getId());
        }
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Flight F502 with two pending reservations (no confirmed)
        AirlineReservationSystem.Flight flight = createFlight("F502", true);
        
        createReservation("R502-1", flight, AirlineReservationSystem.ReservationStatus.PENDING);
        createReservation("R502-2", flight, AirlineReservationSystem.ReservationStatus.PENDING);
        
        // Execute: Retrieve confirmed reservations for flight F502
        List<AirlineReservationSystem.Reservation> result = service.getConfirmedReservations("F502");
        
        // Verify: Should return empty list
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Flight F503 is closed with one confirmed reservation
        AirlineReservationSystem.Flight flight = createFlight("F503", false);
        
        createReservation("R503-1", flight, AirlineReservationSystem.ReservationStatus.CONFIRMED);
        
        // Execute: Retrieve confirmed reservations for closed flight F503
        List<AirlineReservationSystem.Reservation> result = service.getConfirmedReservations("F503");
        
        // Verify: Should return empty list for closed flight
        assertTrue("Should return empty list for closed flight", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup: Create some flights but not FX999
        createFlight("F504", true);
        createFlight("F505", true);
        
        // Execute: Retrieve confirmed reservations for unknown flight FX999
        List<AirlineReservationSystem.Reservation> result = service.getConfirmedReservations("FX999");
        
        // Verify: Should return empty list for unknown flight
        assertTrue("Should return empty list for unknown flight", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Flight F504 with mixed reservation statuses
        AirlineReservationSystem.Flight flight = createFlight("F504", true);
        
        createReservation("R504-A", flight, AirlineReservationSystem.ReservationStatus.CONFIRMED);
        createReservation("R504-B", flight, AirlineReservationSystem.ReservationStatus.CONFIRMED);
        createReservation("R504-C", flight, AirlineReservationSystem.ReservationStatus.CANCELED);
        createReservation("R504-D", flight, AirlineReservationSystem.ReservationStatus.PENDING);
        
        // Execute: Retrieve confirmed reservations for flight F504
        List<AirlineReservationSystem.Reservation> result = service.getConfirmedReservations("F504");
        
        // Verify: Should return only the two confirmed reservations
        assertEquals("Should return 2 confirmed reservations", 2, result.size());
        
        // Verify specific reservations are included
        boolean foundA = false;
        boolean foundB = false;
        
        for (AirlineReservationSystem.Reservation res : result) {
            if ("R504-A".equals(res.getId())) foundA = true;
            if ("R504-B".equals(res.getId())) foundB = true;
            assertEquals("All returned reservations should be CONFIRMED", 
                AirlineReservationSystem.ReservationStatus.CONFIRMED, res.getStatus());
        }
        
        assertTrue("Should include R504-A", foundA);
        assertTrue("Should include R504-B", foundB);
    }
}