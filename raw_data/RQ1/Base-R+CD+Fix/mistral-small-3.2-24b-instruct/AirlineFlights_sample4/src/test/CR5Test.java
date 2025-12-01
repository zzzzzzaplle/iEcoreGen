import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        Date departureTime = dateFormat.parse("2024-06-01 10:00:00");
        Date arrivalTime = dateFormat.parse("2024-06-01 12:00:00");
        Airport departureAirport = new Airport("ORI");
        Airport arrivalAirport = new Airport("DEST");
        
        Flight flight = new Flight(departureTime, arrivalTime, departureAirport, arrivalAirport);
        flight.setOpenForBooking(true);
        flight.setId("F501"); // Override auto-generated ID
        
        // Create confirmed reservations
        Reservation r1 = new Reservation();
        r1.setStatus(ReservationStatus.CONFIRMED);
        r1.setId("R501-1");
        
        Reservation r2 = new Reservation();
        r2.setStatus(ReservationStatus.CONFIRMED);
        r2.setId("R501-2");
        
        Reservation r3 = new Reservation();
        r3.setStatus(ReservationStatus.CONFIRMED);
        r3.setId("R501-3");
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Test
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(3, result.size());
        assertEquals("R501-1", result.get(0).getId());
        assertEquals("R501-2", result.get(1).getId());
        assertEquals("R501-3", result.get(2).getId());
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup
        Date departureTime = dateFormat.parse("2024-06-02 10:00:00");
        Date arrivalTime = dateFormat.parse("2024-06-02 12:00:00");
        Airport departureAirport = new Airport("ORI");
        Airport arrivalAirport = new Airport("DEST");
        
        Flight flight = new Flight(departureTime, arrivalTime, departureAirport, arrivalAirport);
        flight.setOpenForBooking(true);
        flight.setId("F502"); // Override auto-generated ID
        
        // Create pending reservations
        Reservation r1 = new Reservation();
        r1.setStatus(ReservationStatus.PENDING);
        r1.setId("R502-1");
        
        Reservation r2 = new Reservation();
        r2.setStatus(ReservationStatus.PENDING);
        r2.setId("R502-2");
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Test
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup
        Date departureTime = dateFormat.parse("2024-06-03 10:00:00");
        Date arrivalTime = dateFormat.parse("2024-06-03 12:00:00");
        Airport departureAirport = new Airport("ORI");
        Airport arrivalAirport = new Airport("DEST");
        
        Flight flight = new Flight(departureTime, arrivalTime, departureAirport, arrivalAirport);
        flight.setOpenForBooking(false); // Flight is closed
        flight.setId("F503"); // Override auto-generated ID
        
        // Create confirmed reservation
        Reservation r1 = new Reservation();
        r1.setStatus(ReservationStatus.CONFIRMED);
        r1.setId("R503-1");
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Test
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup
        Date departureTime = dateFormat.parse("2024-06-04 10:00:00");
        Date arrivalTime = dateFormat.parse("2024-06-04 12:00:00");
        Airport departureAirport = new Airport("ORI");
        Airport arrivalAirport = new Airport("DEST");
        
        Flight flight1 = new Flight(departureTime, arrivalTime, departureAirport, arrivalAirport);
        flight1.setId("F504");
        
        Flight flight2 = new Flight(departureTime, arrivalTime, departureAirport, arrivalAirport);
        flight2.setId("F505");
        
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        
        // Test - Create a new flight with unknown ID and check its confirmed reservations
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup
        Date departureTime = dateFormat.parse("2024-06-05 10:00:00");
        Date arrivalTime = dateFormat.parse("2024-06-05 12:00:00");
        Airport departureAirport = new Airport("ORI");
        Airport arrivalAirport = new Airport("DEST");
        
        Flight flight = new Flight(departureTime, arrivalTime, departureAirport, arrivalAirport);
        flight.setOpenForBooking(true);
        flight.setId("F504"); // Override auto-generated ID
        
        // Create mixed status reservations
        Reservation r1 = new Reservation();
        r1.setStatus(ReservationStatus.CONFIRMED);
        r1.setId("R504-A");
        
        Reservation r2 = new Reservation();
        r2.setStatus(ReservationStatus.CONFIRMED);
        r2.setId("R504-B");
        
        Reservation r3 = new Reservation();
        r3.setStatus(ReservationStatus.CANCELED);
        r3.setId("R504-C");
        
        Reservation r4 = new Reservation();
        r4.setStatus(ReservationStatus.PENDING);
        r4.setId("R504-D");
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        reservations.add(r4);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Test
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(2, result.size());
        assertEquals("R504-A", result.get(0).getId());
        assertEquals("R504-B", result.get(1).getId());
        assertEquals(ReservationStatus.CONFIRMED, result.get(0).getStatus());
        assertEquals(ReservationStatus.CONFIRMED, result.get(1).getStatus());
    }
}