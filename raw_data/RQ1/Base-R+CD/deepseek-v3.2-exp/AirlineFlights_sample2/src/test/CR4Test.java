import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR4Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() throws Exception {
        // Setup
        // 1. Airline AL6; airports AP10 (CityJ) and AP11 (CityK).
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
        
        // 2. Flight F200: depart 2025-06-20 09:00:00, arrive 2025-06-20 13:00:00, 0 reservations. The flight is open for booking for customers.
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        
        airline.addFlight(flight);
        
        // 3. Current time = 2025-05-01 08:00:00.
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        // Expected Output: True. No canceled reservation.
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", flight.isOpenForBooking());
        assertEquals("No reservations should be cancelled", 0, flight.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
        // Setup
        // 1. Airline AL7; airports AP12 (CityL) and AP13 (CityM).
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
        
        // 2. Flight F201: depart 2025-07-15 14:00:00, arrive 2025-07-15 18:00:00. The flight is open for booking for customers.
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        
        // 3. Customer make a booking with three reservations R201-1, R201-2, R201-3. Customer has confirmed these reservation.
        Customer customer = new Customer();
        
        // Create passengers
        Passenger passenger1 = new Passenger();
        passenger1.setName("Passenger1");
        Passenger passenger2 = new Passenger();
        passenger2.setName("Passenger2");
        Passenger passenger3 = new Passenger();
        passenger3.setName("Passenger3");
        
        // Create reservations
        Reservation res1 = new Reservation();
        res1.setId("R201-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        res1.setPassenger(passenger1);
        res1.setFlight(flight);
        
        Reservation res2 = new Reservation();
        res2.setId("R201-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        res2.setPassenger(passenger2);
        res2.setFlight(flight);
        
        Reservation res3 = new Reservation();
        res3.setId("R201-3");
        res3.setStatus(ReservationStatus.CONFIRMED);
        res3.setPassenger(passenger3);
        res3.setFlight(flight);
        
        // Add reservations to flight
        flight.getReservations().add(res1);
        flight.getReservations().add(res2);
        flight.getReservations().add(res3);
        
        airline.addFlight(flight);
        
        // 4. Current time = 2025-06-10 12:00:00.
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        // Expected Output: True. The three reservation are canceled.
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", flight.isOpenForBooking());
        
        // Check that all reservations are cancelled
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        assertEquals("All confirmed reservations should be cancelled", 0, confirmedReservations.size());
        
        // Verify individual reservation status
        assertEquals("Reservation 1 should be cancelled", ReservationStatus.CANCELLED, res1.getStatus());
        assertEquals("Reservation 2 should be cancelled", ReservationStatus.CANCELLED, res2.getStatus());
        assertEquals("Reservation 3 should be cancelled", ReservationStatus.CANCELLED, res3.getStatus());
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup
        // 1. Airline AL8; flight F202 openForBooking = False,
        //    depart 2025-08-10 11:00:00, arrive 2025-08-10 13:30:00.
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setOpenForBooking(false);
        flight.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        
        // Create minimal airports to satisfy flight requirements
        Airport depAirport = new Airport();
        Airport arrAirport = new Airport();
        flight.setDepartureAirport(depAirport);
        flight.setArrivalAirport(arrAirport);
        
        airline.addFlight(flight);
        
        // 2. Current time = 2025-07-01 09:00:00.
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        // Expected Output: False
        assertFalse("Should return false when trying to close an already closed flight", result);
        assertFalse("Flight should remain closed", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        // 1. Airline AL9; flight F203 openForBooking = True,
        //    depart 2025-09-10 09:00:00, arrive 2025-09-10 15:30:00.
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        
        // Create minimal airports to satisfy flight requirements
        Airport depAirport = new Airport();
        Airport arrAirport = new Airport();
        flight.setDepartureAirport(depAirport);
        flight.setArrivalAirport(arrAirport);
        
        // 2. Two confirmed reservations R203-1, R203-2.
        Passenger passenger1 = new Passenger();
        passenger1.setName("Passenger1");
        Passenger passenger2 = new Passenger();
        passenger2.setName("Passenger2");
        
        Reservation res1 = new Reservation();
        res1.setId("R203-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        res1.setPassenger(passenger1);
        res1.setFlight(flight);
        
        Reservation res2 = new Reservation();
        res2.setId("R203-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        res2.setPassenger(passenger2);
        res2.setFlight(flight);
        
        flight.getReservations().add(res1);
        flight.getReservations().add(res2);
        
        airline.addFlight(flight);
        
        // 3. Current time = 2025-09-10 09:10:00.
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00");
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        // Expected Output: False
        assertFalse("Should return false when trying to close flight after departure time", result);
        assertTrue("Flight should remain open since closure failed", flight.isOpenForBooking());
        
        // Verify reservations remain confirmed
        assertEquals("Reservation 1 should remain confirmed", ReservationStatus.CONFIRMED, res1.getStatus());
        assertEquals("Reservation 2 should remain confirmed", ReservationStatus.CONFIRMED, res2.getStatus());
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        // 1. Airline AL10; flight F204 openForBooking = True,
        //    depart 2025-04-01 22:00:00, arrive 2025-04-1 01:30:00.
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-01 01:30:00"));
        
        // Create minimal airports to satisfy flight requirements
        Airport depAirport = new Airport();
        Airport arrAirport = new Airport();
        flight.setDepartureAirport(depAirport);
        flight.setArrivalAirport(arrAirport);
        
        airline.addFlight(flight);
        
        // 2. Current time = 2025-04-01 22:05:00 (flight already left).
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00");
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        // Expected Output: False
        assertFalse("Should return false when trying to close flight after departure", result);
        assertTrue("Flight should remain open since closure failed", flight.isOpenForBooking());
    }
}