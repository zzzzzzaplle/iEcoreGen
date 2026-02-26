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
        Airport departureAirport = new Airport();
        City cityJ = new City();
        cityJ.setName("CityJ");
        departureAirport.addCity(cityJ);
        
        Airport arrivalAirport = new Airport();
        City cityK = new City();
        cityK.setName("CityK");
        arrivalAirport.addCity(cityK);
        
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed for booking", flight.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Airport departureAirport = new Airport();
        City cityL = new City();
        cityL.setName("CityL");
        departureAirport.addCity(cityL);
        
        Airport arrivalAirport = new Airport();
        City cityM = new City();
        cityM.setName("CityM");
        arrivalAirport.addCity(cityM);
        
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        // Create and confirm three reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        boolean bookingResult = customer.addBooking(flight, dateFormat.parse("2025-06-10 11:00:00"), passengerNames);
        assertTrue("Booking should be successful", bookingResult);
        
        // Confirm all reservations
        for (Booking booking : customer.getBookings()) {
            for (Reservation reservation : booking.getReservations()) {
                boolean confirmResult = customer.confirm(reservation.getId(), dateFormat.parse("2025-06-10 11:30:00"));
                assertTrue("Reservation should be confirmed", confirmResult);
                assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
            }
        }
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed for booking", flight.isOpenForBooking());
        
        // Check that all confirmed reservations are now canceled
        int canceledCount = 0;
        for (Reservation reservation : flight.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CANCELED) {
                canceledCount++;
            }
        }
        assertEquals("All three reservations should be canceled", 3, canceledCount);
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup
        Airport departureAirport = new Airport();
        City city = new City();
        city.setName("City");
        departureAirport.addCity(city);
        
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(departureAirport); // Same airport for simplicity
        flight.setOpenForBooking(false); // Already closed
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Airport departureAirport = new Airport();
        City city = new City();
        city.setName("City");
        departureAirport.addCity(city);
        
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(departureAirport);
        flight.setOpenForBooking(true);
        
        // Add two confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2");
        boolean bookingResult = customer.addBooking(flight, dateFormat.parse("2025-09-09 10:00:00"), passengerNames);
        assertTrue("Booking should be successful", bookingResult);
        
        // Confirm all reservations
        for (Booking booking : customer.getBookings()) {
            for (Reservation reservation : booking.getReservations()) {
                boolean confirmResult = customer.confirm(reservation.getId(), dateFormat.parse("2025-09-09 11:00:00"));
                assertTrue("Reservation should be confirmed", confirmResult);
            }
        }
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should return false when closing after departure time", result);
        assertTrue("Flight should remain open for booking", flight.isOpenForBooking());
        
        // Verify reservations are still confirmed (not canceled)
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("Reservations should remain CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        Airport departureAirport = new Airport();
        City city = new City();
        city.setName("City");
        departureAirport.addCity(city);
        
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-02 01:30:00")); // Next day arrival
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(departureAirport);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should return false when closing after flight departure", result);
        assertTrue("Flight should remain open for booking", flight.isOpenForBooking());
    }
}