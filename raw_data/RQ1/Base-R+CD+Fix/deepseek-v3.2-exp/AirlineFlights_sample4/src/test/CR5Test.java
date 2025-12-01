import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    
    private Airline airline;
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        airline = new Airline();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() throws Exception {
        // Setup
        Flight flight = createFlight("F501", true);
        airline.addFlight(flight);
        
        // Create three confirmed reservations
        Reservation r1 = createReservation("R501-1", ReservationStatus.CONFIRMED);
        Reservation r2 = createReservation("R501-2", ReservationStatus.CONFIRMED);
        Reservation r3 = createReservation("R501-3", ReservationStatus.CONFIRMED);
        
        flight.addReservation(r1);
        flight.addReservation(r2);
        flight.addReservation(r3);
        
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
        
        // Create two pending reservations
        Reservation r1 = createReservation("R502-1", ReservationStatus.PENDING);
        Reservation r2 = createReservation("R502-2", ReservationStatus.PENDING);
        
        flight.addReservation(r1);
        flight.addReservation(r2);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup
        Flight flight = createFlight("F503", false); // Flight closed for booking
        airline.addFlight(flight);
        
        // Create one confirmed reservation
        Reservation r1 = createReservation("R503-1", ReservationStatus.CONFIRMED);
        flight.addReservation(r1);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup - Airline AL24 holds flights F504, F505 only
        Flight flight1 = createFlight("F504", true);
        Flight flight2 = createFlight("F505", true);
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        
        // Execute - Try to find non-existent flight
        Flight result = airline.findFlightById("FX999");
        
        // Verify
        assertNull(result);
        // Note: We can't call getConfirmedReservations() on null, so we verify findFlightById returns null
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
        
        flight.addReservation(rA);
        flight.addReservation(rB);
        flight.addReservation(rC);
        flight.addReservation(rD);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(2, result.size());
        assertTrue(result.contains(rA));
        assertTrue(result.contains(rB));
        assertFalse(result.contains(rC));
        assertFalse(result.contains(rD));
    }
    
    // Helper method to create a flight with basic setup
    private Flight createFlight(String id, boolean openForBooking) throws Exception {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setOpenForBooking(openForBooking);
        
        // Set departure and arrival times
        Date departureTime = sdf.parse("2024-12-01 10:00:00");
        Date arrivalTime = sdf.parse("2024-12-01 12:00:00");
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Set airports
        Airport departureAirport = new Airport();
        City depCity = new City();
        depCity.setName("New York");
        departureAirport.addCity(depCity);
        
        Airport arrivalAirport = new Airport();
        City arrCity = new City();
        arrCity.setName("Los Angeles");
        arrivalAirport.addCity(arrCity);
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        
        return flight;
    }
    
    // Helper method to create a reservation
    private Reservation createReservation(String id, ReservationStatus status) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatus(status);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger_" + id);
        reservation.setPassenger(passenger);
        
        return reservation;
    }
}