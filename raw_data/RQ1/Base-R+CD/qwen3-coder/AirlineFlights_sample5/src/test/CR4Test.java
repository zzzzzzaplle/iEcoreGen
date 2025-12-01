import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR4Test {
    
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        
        // Create flight F200
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        flight.setDepartureTime(sdf.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(sdf.parse("2025-06-20 13:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Current time
        Date now = sdf.parse("2025-05-01 08:00:00");
        
        // Execute close flight
        boolean result = airline.closeFlight("F200", now);
        
        // Verify
        assertTrue(result);
        assertFalse(flight.isOpenForBooking());
        assertEquals(0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        
        // Create flight F201
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        flight.setDepartureTime(sdf.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(sdf.parse("2025-07-15 18:00:00"));
        flight.setOpenForBooking(true);
        
        // Create customer and bookings
        Customer customer = new Customer();
        
        // Create three reservations
        Reservation r1 = new Reservation();
        r1.setId("R201-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        r1.setFlight(flight);
        Passenger p1 = new Passenger();
        p1.setName("Passenger1");
        r1.setPassenger(p1);
        
        Reservation r2 = new Reservation();
        r2.setId("R201-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        r2.setFlight(flight);
        Passenger p2 = new Passenger();
        p2.setName("Passenger2");
        r2.setPassenger(p2);
        
        Reservation r3 = new Reservation();
        r3.setId("R201-3");
        r3.setStatus(ReservationStatus.CONFIRMED);
        r3.setFlight(flight);
        Passenger p3 = new Passenger();
        p3.setName("Passenger3");
        r3.setPassenger(p3);
        
        // Add reservations to flight
        flight.getReservations().add(r1);
        flight.getReservations().add(r2);
        flight.getReservations().add(r3);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Current time
        Date now = sdf.parse("2025-06-10 12:00:00");
        
        // Execute close flight
        boolean result = airline.closeFlight("F201", now);
        
        // Verify
        assertTrue(result);
        assertFalse(flight.isOpenForBooking());
        assertEquals(ReservationStatus.CANCELLED, r1.getStatus());
        assertEquals(ReservationStatus.CANCELLED, r2.getStatus());
        assertEquals(ReservationStatus.CANCELLED, r3.getStatus());
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create flight F202
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setDepartureTime(sdf.parse("2025-08-10 11:00:00"));
        flight.setOpenForBooking(false); // Already closed
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Current time
        Date now = sdf.parse("2025-07-01 09:00:00");
        
        // Execute close flight
        boolean result = airline.closeFlight("F202", now);
        
        // Verify
        assertFalse(result);
    }
    
    @Test
    public void testCase4_closeOnDepartureDay() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create flight F203
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setDepartureTime(sdf.parse("2025-09-10 09:00:00"));
        flight.setOpenForBooking(true);
        
        // Create two confirmed reservations
        Reservation r1 = new Reservation();
        r1.setId("R203-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        r1.setFlight(flight);
        
        Reservation r2 = new Reservation();
        r2.setId("R203-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        r2.setFlight(flight);
        
        // Add reservations to flight
        flight.getReservations().add(r1);
        flight.getReservations().add(r2);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Current time (same day but before departure)
        Date now = sdf.parse("2025-09-10 05:00:00");
        
        // Execute close flight
        boolean result = airline.closeFlight("F203", now);
        
        // Verify
        assertFalse(result);
        // Reservations should remain confirmed since operation failed
        assertEquals(ReservationStatus.CONFIRMED, r1.getStatus());
        assertEquals(ReservationStatus.CONFIRMED, r2.getStatus());
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create flight F204
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setDepartureTime(sdf.parse("2025-04-01 10:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Current time (after departure)
        Date now = sdf.parse("2025-04-01 12:00:00");
        
        // Execute close flight
        boolean result = airline.closeFlight("F204", now);
        
        // Verify
        assertFalse(result);
    }
}