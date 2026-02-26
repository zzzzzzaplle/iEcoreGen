import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR4Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() throws ParseException {
        // Setup airline and airports
        Airline airline = new Airline();
        
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        City cityJ = new City();
        cityJ.setName("CityJ");
        ap10.addCity(cityJ);
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        City cityK = new City();
        cityK.setName("CityK");
        ap11.addCity(cityK);
        
        // Setup flight F200
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        flight.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Current time
        Date now = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute close flight
        boolean result = airline.closeFlight("F200", now);
        
        // Verify result
        assertTrue(result);
        assertFalse(flight.isOpenForBooking());
        assertEquals(0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws ParseException {
        // Setup airline and airports
        Airline airline = new Airline();
        
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        City cityL = new City();
        cityL.setName("CityL");
        ap12.addCity(cityL);
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        City cityM = new City();
        cityM.setName("CityM");
        ap13.addCity(cityM);
        
        // Setup flight F201
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        flight.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flight.setOpenForBooking(true);
        
        // Setup customer and reservations
        Customer customer = new Customer();
        List<String> passengers = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        Date now = dateFormat.parse("2025-06-10 12:00:00");
        
        // Create booking with reservations
        customer.addBooking(flight, now, passengers);
        
        // Confirm all reservations
        for (Reservation reservation : flight.getReservations()) {
            reservation.setStatus(ReservationStatus.CONFIRMED);
        }
        
        airline.addFlight(flight);
        
        // Execute close flight
        boolean result = airline.closeFlight("F201", now);
        
        // Verify result
        assertTrue(result);
        assertFalse(flight.isOpenForBooking());
        assertEquals(3, flight.getReservations().size());
        
        // Verify all reservations are canceled
        for (Reservation reservation : flight.getReservations()) {
            assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws ParseException {
        // Setup airline
        Airline airline = new Airline();
        
        // Setup flight F202 (already closed)
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        flight.setOpenForBooking(false); // Already closed
        
        airline.addFlight(flight);
        
        // Current time
        Date now = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute close flight
        boolean result = airline.closeFlight("F202", now);
        
        // Verify result
        assertFalse(result);
        assertFalse(flight.isOpenForBooking()); // Should still be closed
    }
    
    @Test
    public void testCase4_closeOnDepartureDay() throws ParseException {
        // Setup airline
        Airline airline = new Airline();
        
        // Setup flight F203
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flight.setOpenForBooking(true);
        
        // Setup reservations
        Reservation reservation1 = new Reservation();
        reservation1.setId("R203-1");
        reservation1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation reservation2 = new Reservation();
        reservation2.setId("R203-2");
        reservation2.setStatus(ReservationStatus.CONFIRMED);
        
        flight.getReservations().add(reservation1);
        flight.getReservations().add(reservation2);
        
        airline.addFlight(flight);
        
        // Current time (on departure day but before departure)
        Date now = dateFormat.parse("2025-09-10 05:00:00");
        
        // Execute close flight
        boolean result = airline.closeFlight("F203", now);
        
        // Verify result
        assertFalse(result);
        assertTrue(flight.isOpenForBooking()); // Should still be open
        assertEquals(ReservationStatus.CONFIRMED, reservation1.getStatus()); // Should still be confirmed
        assertEquals(ReservationStatus.CONFIRMED, reservation2.getStatus()); // Should still be confirmed
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws ParseException {
        // Setup airline
        Airline airline = new Airline();
        
        // Setup flight F204
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setDepartureTime(dateFormat.parse("2025-04-01 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Current time (after departure)
        Date now = dateFormat.parse("2025-04-01 12:00:00");
        
        // Execute close flight
        boolean result = airline.closeFlight("F204", now);
        
        // Verify result
        assertFalse(result);
        assertTrue(flight.isOpenForBooking()); // Should still be open
    }
}