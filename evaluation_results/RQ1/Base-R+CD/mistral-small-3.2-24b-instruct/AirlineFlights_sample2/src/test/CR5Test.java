import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR5Test {
    private Airline airline;
    private Flight flight;
    
    @Before
    public void setUp() {
        airline = new Airline();
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() throws Exception {
        // Setup
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date futureDate = sdf.parse("2024-12-31 23:59:59");
        
        flight = new Flight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(futureDate);
        
        // Create confirmed reservations
        Reservation r1 = new Reservation(flight, new Passenger("Passenger1"));
        r1.setStatus(ReservationStatus.CONFIRMED);
        Reservation r2 = new Reservation(flight, new Passenger("Passenger2"));
        r2.setStatus(ReservationStatus.CONFIRMED);
        Reservation r3 = new Reservation(flight, new Passenger("Passenger3"));
        r3.setStatus(ReservationStatus.CONFIRMED);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1", result.contains(r1));
        assertTrue("Should contain R501-2", result.contains(r2));
        assertTrue("Should contain R501-3", result.contains(r3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date futureDate = sdf.parse("2024-12-31 23:59:59");
        
        flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(futureDate);
        
        // Create pending reservations
        Reservation r1 = new Reservation(flight, new Passenger("Passenger1"));
        r1.setStatus(ReservationStatus.PENDING);
        Reservation r2 = new Reservation(flight, new Passenger("Passenger2"));
        r2.setStatus(ReservationStatus.PENDING);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date futureDate = sdf.parse("2024-12-31 23:59:59");
        
        flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false); // Flight is closed
        flight.setDepartureTime(futureDate);
        
        // Create confirmed reservation
        Reservation r1 = new Reservation(flight, new Passenger("Passenger1"));
        r1.setStatus(ReservationStatus.CONFIRMED);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list when flight is closed", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date futureDate = sdf.parse("2024-12-31 23:59:59");
        
        Flight flight504 = new Flight();
        flight504.setId("F504");
        flight504.setOpenForBooking(true);
        flight504.setDepartureTime(futureDate);
        
        Flight flight505 = new Flight();
        flight505.setId("F505");
        flight505.setOpenForBooking(true);
        flight505.setDepartureTime(futureDate);
        
        airline.addFlight(flight504);
        airline.addFlight(flight505);
        
        // Execute - Create a new flight object with unknown ID and call getConfirmedReservations
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setOpenForBooking(true);
        unknownFlight.setDepartureTime(futureDate);
        
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list for unknown flight", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date futureDate = sdf.parse("2024-12-31 23:59:59");
        
        flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(futureDate);
        
        // Create mixed status reservations
        Reservation rA = new Reservation(flight, new Passenger("PassengerA"));
        rA.setStatus(ReservationStatus.CONFIRMED);
        Reservation rB = new Reservation(flight, new Passenger("PassengerB"));
        rB.setStatus(ReservationStatus.CONFIRMED);
        Reservation rC = new Reservation(flight, new Passenger("PassengerC"));
        rC.setStatus(ReservationStatus.CANCELED);
        Reservation rD = new Reservation(flight, new Passenger("PassengerD"));
        rD.setStatus(ReservationStatus.PENDING);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(rA);
        reservations.add(rB);
        reservations.add(rC);
        reservations.add(rD);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return 2 confirmed reservations", 2, result.size());
        assertTrue("Should contain R504-A", result.contains(rA));
        assertTrue("Should contain R504-B", result.contains(rB));
        assertFalse("Should not contain canceled reservation R504-C", result.contains(rC));
        assertFalse("Should not contain pending reservation R504-D", result.contains(rD));
    }
}