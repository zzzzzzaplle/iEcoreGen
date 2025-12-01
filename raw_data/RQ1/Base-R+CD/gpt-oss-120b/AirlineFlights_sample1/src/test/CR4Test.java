import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws ParseException {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() throws ParseException {
        // Setup
        // 1. Airline AL6; airports AP10 (CityJ) and AP11 (CityK)
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        
        // 2. Flight F200: depart 2025-06-20 09:00:00, arrive 2025-06-20 13:00:00, 0 reservations
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flight.setDepartureAirport(ap10);
        flight.setArrialAirport(ap11);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // 3. Current time = 2025-05-01 08:00:00
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed for booking", flight.isOpenForBooking());
        assertEquals("No reservations should be cancelled", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() throws ParseException {
        // Setup
        // 1. Airline AL7; airports AP12 (CityL) and AP13 (CityM)
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        
        // 2. Flight F201: depart 2025-07-15 14:00:00, arrive 2025-07-15 18:00:00
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flight.setDepartureAirport(ap12);
        flight.setArrialAirport(ap13);
        flight.setOpenForBooking(true);
        
        // 3. Customer make a booking with three reservations R201-1, R201-2, R201-3
        List<Reservation> reservations = new ArrayList<>();
        
        // Create and add three confirmed reservations
        for (int i = 1; i <= 3; i++) {
            Reservation reservation = new Reservation();
            reservation.setId("R201-" + i);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            
            Passenger passenger = new Passenger();
            passenger.setName("Passenger" + i);
            reservation.setPassenger(passenger);
            
            reservation.setFlight(flight);
            reservations.add(reservation);
        }
        
        flight.setReservations(reservations);
        airline.addFlight(flight);
        
        // 4. Current time = 2025-06-10 12:00:00
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed for booking", flight.isOpenForBooking());
        
        // Check that all three reservations are cancelled
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("Reservation should be cancelled", ReservationStatus.CANCELLED, reservation.getStatus());
        }
        assertEquals("All three reservations should be cancelled", 3, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws ParseException {
        // Setup
        // 1. Airline AL8; flight F202 openForBooking = False, depart 2025-08-10 11:00:00
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-08-10 15:00:00"));
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP14");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP15");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(false); // Already closed
        
        airline.addFlight(flight);
        
        // 2. Current time = 2025-07-01 09:00:00
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDay() throws ParseException {
        // Setup
        // 1. Airline AL9; flight F203 openForBooking = True, depart 2025-09-10 09:00:00
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-09-10 13:00:00"));
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP16");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP17");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        // 2. Two confirmed reservations R203-1, R203-2
        List<Reservation> reservations = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Reservation reservation = new Reservation();
            reservation.setId("R203-" + i);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            
            Passenger passenger = new Passenger();
            passenger.setName("Passenger" + i);
            reservation.setPassenger(passenger);
            
            reservation.setFlight(flight);
            reservations.add(reservation);
        }
        flight.setReservations(reservations);
        airline.addFlight(flight);
        
        // 3. Current time = 2025-09-10 05:00:00
        Date currentTime = dateFormat.parse("2025-09-10 05:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should return false when closing on departure day", result);
        assertTrue("Flight should remain open for booking", flight.isOpenForBooking());
        
        // Verify reservations remain confirmed (not cancelled)
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("Reservations should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws ParseException {
        // Setup
        // 1. Airline AL10; flight F204 openForBooking = True, depart 2025-04-01 10:00:00
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setDepartureTime(dateFormat.parse("2025-04-01 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-01 14:00:00"));
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP18");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP19");
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // 2. Current time = 2025-04-01 12:00:00 (flight already left)
        Date currentTime = dateFormat.parse("2025-04-01 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should return false when trying to close after departure", result);
        assertTrue("Flight should remain open for booking", flight.isOpenForBooking());
    }
}