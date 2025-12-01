import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() throws ParseException {
        // Setup: Airline AL6; airports AP10 (CityJ) and AP11 (CityK)
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
        
        // Setup: Flight F200: depart 2025-06-20 09:00:00, arrive 2025-06-20 13:00:00, 0 reservations
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Current time = 2025-05-01 08:00:00
        Date now = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute: Close flight F200 for airline AL6
        boolean result = airline.closeFlight("F200", now);
        
        // Expected Output: True. No canceled reservation.
        assertTrue(result);
        assertFalse(flight.isOpenForBooking());
        assertEquals(0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws ParseException {
        // Setup: Airline AL7; airports AP12 (CityL) and AP13 (CityM)
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
        
        // Setup: Flight F201: depart 2025-07-15 14:00:00, arrive 2025-07-15 18:00:00
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        flight.setOpenForBooking(true);
        
        // Setup: Customer make a booking with three reservations R201-1, R201-2, R201-3. Customer has confirmed these reservation.
        Reservation r1 = new Reservation();
        r1.setId("R201-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        r1.setFlight(flight);
        
        Reservation r2 = new Reservation();
        r2.setId("R201-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        r2.setFlight(flight);
        
        Reservation r3 = new Reservation();
        r3.setId("R201-3");
        r3.setStatus(ReservationStatus.CONFIRMED);
        r3.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Current time = 2025-06-10 12:00:00
        Date now = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute: Close flight F201 for airline AL7
        boolean result = airline.closeFlight("F201", now);
        
        // Expected Output: True. The three reservation are canceled.
        assertTrue(result);
        assertFalse(flight.isOpenForBooking());
        assertEquals(ReservationStatus.CANCELLED, r1.getStatus());
        assertEquals(ReservationStatus.CANCELLED, r2.getStatus());
        assertEquals(ReservationStatus.CANCELLED, r3.getStatus());
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws ParseException {
        // Setup: Airline AL8; flight F202 openForBooking = False, depart 2025-08-10 11:00:00
        Airline airline = new Airline();
        
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        flight.setOpenForBooking(false);
        
        airline.addFlight(flight);
        
        // Current time = 2025-07-01 09:00:00
        Date now = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute: Close flight F202 for airline AL8
        boolean result = airline.closeFlight("F202", now);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_closeOnDepartureDay() throws ParseException {
        // Setup: Airline AL9; flight F203 openForBooking = True, depart 2025-09-10 09:00:00
        Airline airline = new Airline();
        
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flight.setOpenForBooking(true);
        
        // Setup: Two confirmed reservations R203-1, R203-2
        Reservation r1 = new Reservation();
        r1.setId("R203-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        r1.setFlight(flight);
        
        Reservation r2 = new Reservation();
        r2.setId("R203-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        r2.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Current time = 2025-09-10 05:00:00
        Date now = dateFormat.parse("2025-09-10 05:00:00");
        
        // Execute: Close flight F203 for airline AL9
        boolean result = airline.closeFlight("F203", now);
        
        // Expected Output: False
        assertFalse(result);
        // Reservations should remain confirmed since operation failed
        assertEquals(ReservationStatus.CONFIRMED, r1.getStatus());
        assertEquals(ReservationStatus.CONFIRMED, r2.getStatus());
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws ParseException {
        // Setup: Airline AL10; flight F204 openForBooking = True, depart 2025-04-01 10:00:00
        Airline airline = new Airline();
        
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setDepartureTime(dateFormat.parse("2025-04-01 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Current time = 2025-04-01 12:00:00 (flight already left)
        Date now = dateFormat.parse("2025-04-01 12:00:00");
        
        // Execute: Close flight F204 for airline AL10
        boolean result = airline.closeFlight("F204", now);
        
        // Expected Output: False
        assertFalse(result);
    }
}