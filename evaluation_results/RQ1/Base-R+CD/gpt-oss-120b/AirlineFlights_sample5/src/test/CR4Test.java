import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR4Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should no longer be open for booking", flight.isOpenForBooking());
        assertEquals("No reservations should exist", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        flight.setOpenForBooking(true);
        
        // Create customer and booking
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        
        // Create booking and reservations
        boolean bookingCreated = customer.addBooking(flight, dateFormat.parse("2025-06-10 11:00:00"), passengerNames);
        assertTrue("Booking should be created successfully", bookingCreated);
        
        // Confirm all reservations
        for (Booking booking : customer.getBookings()) {
            for (Reservation reservation : booking.getReservations()) {
                boolean confirmed = customer.confirm(reservation.getId(), dateFormat.parse("2025-06-10 11:30:00"));
                assertTrue("Reservation should be confirmed", confirmed);
                assertEquals("Reservation should be confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
            }
        }
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should no longer be open for booking", flight.isOpenForBooking());
        
        // Check that all confirmed reservations are cancelled
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("All reservations should be cancelled", ReservationStatus.CANCELLED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create flight that is already closed
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        flight.setOpenForBooking(false);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDay() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR");
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-09-10 12:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        // Create reservations
        Reservation reservation1 = new Reservation();
        reservation1.setId("R203-1");
        reservation1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation reservation2 = new Reservation();
        reservation2.setId("R203-2");
        reservation2.setStatus(ReservationStatus.CONFIRMED);
        
        flight.setReservations(Arrays.asList(reservation1, reservation2));
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-09-10 05:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should return false when closing on departure day", result);
        assertTrue("Flight should remain open for booking", flight.isOpenForBooking());
        
        // Verify reservations are not cancelled
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("Reservations should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setDepartureTime(dateFormat.parse("2025-04-01 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-04-01 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should return false when trying to close after departure", result);
        assertTrue("Flight should remain open for booking", flight.isOpenForBooking());
    }
}