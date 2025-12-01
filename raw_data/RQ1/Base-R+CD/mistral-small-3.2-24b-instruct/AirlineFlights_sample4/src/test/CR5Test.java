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
        Flight flight = createFlight("F503", false);
        airline.addFlight(flight);
        
        Reservation r1 = createReservation("R503-1", ReservationStatus.CONFIRMED);
        flight.getReservations().add(r1);
        
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
        
        // Execute - This test case is about retrieving from airline, but the requirement is about flight method
        // Since the test specification mentions "unknown flight id" but the method operates on flight object,
        // we'll test that getConfirmedReservations returns empty list when called on a flight not in airline
        Flight unknownFlight = createFlight("FX999", true);
        
        // Execute
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup
        Flight flight = createFlight("F504", true);
        airline.addFlight(flight);
        
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
        flight.setDepartureTime(dateFormat.parse("2024-12-15 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2024-12-15 12:00:00"));
        
        Airport departureAirport = new Airport();
        City departureCity = new City();
        departureCity.setName("CityA");
        departureAirport.addCity(departureCity);
        flight.setDepartureAirport(departureAirport);
        
        Airport arrivalAirport = new Airport();
        City arrivalCity = new City();
        arrivalCity.setName("CityB");
        arrivalAirport.addCity(arrivalCity);
        flight.setArrialAirport(arrivalAirport);
        
        return flight;
    }
    
    private Reservation createReservation(String id, ReservationStatus status) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatus(status);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger" + id);
        reservation.setPassenger(passenger);
        
        return reservation;
    }
}