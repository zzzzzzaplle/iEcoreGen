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
        // Setup Airline AL6
        Airline airline = new Airline();
        
        // Setup airports AP10 (CityJ) and AP11 (CityK)
        Airport ap10 = new Airport();
        City cityJ = new City();
        cityJ.setName("CityJ");
        ap10.addCity(cityJ);
        
        Airport ap11 = new Airport();
        City cityK = new City();
        cityK.setName("CityK");
        ap11.addCity(cityK);
        
        // Setup Flight F200
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify results
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed", flight.isOpenForBooking());
        assertEquals("No reservations should be cancelled", 0, flight.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() throws Exception {
        // Setup Airline AL7
        Airline airline = new Airline();
        
        // Setup airports AP12 (CityL) and AP13 (CityM)
        Airport ap12 = new Airport();
        City cityL = new City();
        cityL.setName("CityL");
        ap12.addCity(cityL);
        
        Airport ap13 = new Airport();
        City cityM = new City();
        cityM.setName("CityM");
        ap13.addCity(cityM);
        
        // Setup Flight F201
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        flight.setOpenForBooking(true);
        
        // Create customer and add three confirmed reservations
        Customer customer = new Customer();
        
        // Create reservations R201-1, R201-2, R201-3
        for (int i = 1; i <= 3; i++) {
            Reservation reservation = new Reservation();
            reservation.setId("R201-" + i);
            
            Passenger passenger = new Passenger();
            passenger.setName("Passenger" + i);
            reservation.setPassenger(passenger);
            
            reservation.setFlight(flight);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            flight.addReservation(reservation);
            
            // Add reservation to customer's booking
            Booking booking = new Booking(customer);
            booking.getReservations().add(reservation);
            customer.getBookings().add(booking);
        }
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Verify initial state
        assertEquals("Should have 3 confirmed reservations initially", 3, flight.getConfirmedReservations().size());
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify results
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed", flight.isOpenForBooking());
        
        // Check that all reservations are cancelled
        assertEquals("All confirmed reservations should be cancelled", 0, flight.getConfirmedReservations().size());
        
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("Reservation should be cancelled", ReservationStatus.CANCELLED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws Exception {
        // Setup Airline AL8
        Airline airline = new Airline();
        
        // Setup Flight F202 (already closed)
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        flight.setOpenForBooking(false); // Flight already closed
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify results
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup Airline AL9
        Airline airline = new Airline();
        
        // Setup Flight F203
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        flight.setOpenForBooking(true);
        
        // Add two confirmed reservations R203-1, R203-2
        for (int i = 1; i <= 2; i++) {
            Reservation reservation = new Reservation();
            reservation.setId("R203-" + i);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            flight.addReservation(reservation);
        }
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Verify initial state
        assertEquals("Should have 2 confirmed reservations initially", 2, flight.getConfirmedReservations().size());
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify results
        assertFalse("Should return false when trying to close after departure time", result);
        assertTrue("Flight should remain open for booking", flight.isOpenForBooking());
        assertEquals("Confirmed reservations should remain unchanged", 2, flight.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws Exception {
        // Setup Airline AL10
        Airline airline = new Airline();
        
        // Setup Flight F204
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-01 01:30:00")); // Note: arrival is next day
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure time
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify results
        assertFalse("Should return false when trying to close after departure", result);
        assertTrue("Flight should remain open for booking", flight.isOpenForBooking());
    }
}