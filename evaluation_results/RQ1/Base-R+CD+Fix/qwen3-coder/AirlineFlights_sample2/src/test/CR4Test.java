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
        // Setup airports
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        City cityJ = new City();
        ap10.addCity(cityJ);
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        City cityK = new City();
        ap11.addCity(cityK);
        
        // Setup flight
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setOpenForBooking(true);
        
        airline.addFlight(f200);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify results
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed", f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, f200.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
        // Setup airports
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        City cityL = new City();
        ap12.addCity(cityL);
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        City cityM = new City();
        ap13.addCity(cityM);
        
        // Setup flight
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        f201.setOpenForBooking(true);
        
        airline.addFlight(f201);
        
        // Setup customer and booking with confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        
        // Create booking and confirm all reservations
        boolean bookingResult = customer.addBooking(f201, dateFormat.parse("2025-06-09 12:00:00"), passengerNames);
        assertTrue("Booking should be created successfully", bookingResult);
        
        // Confirm all reservations
        for (Booking booking : customer.getBookings()) {
            for (Reservation reservation : booking.getReservations()) {
                boolean confirmResult = customer.confirm(reservation.getId(), dateFormat.parse("2025-06-09 12:00:00"));
                assertTrue("Reservation should be confirmed", confirmResult);
            }
        }
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify results
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed", f201.isOpenForBooking());
        
        // Check that all confirmed reservations are now canceled
        List<Reservation> reservations = f201.getReservations();
        assertEquals("Should have 3 reservations", 3, reservations.size());
        for (Reservation reservation : reservations) {
            assertEquals("All reservations should be canceled", ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup airports
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP");
        City city1 = new City();
        departureAirport.addCity(city1);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR");
        City city2 = new City();
        arrivalAirport.addCity(city2);
        
        // Setup flight that is already closed
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        f202.setDepartureAirport(departureAirport);
        f202.setArrivalAirport(arrivalAirport);
        f202.setOpenForBooking(false); // Already closed
        
        airline.addFlight(f202);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify results
        assertFalse("Close flight should return false for already closed flight", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup airports
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP");
        City city1 = new City();
        departureAirport.addCity(city1);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR");
        City city2 = new City();
        arrivalAirport.addCity(city2);
        
        // Setup flight
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        f203.setDepartureAirport(departureAirport);
        f203.setArrivalAirport(arrivalAirport);
        f203.setOpenForBooking(true);
        
        airline.addFlight(f203);
        
        // Setup confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2");
        boolean bookingResult = customer.addBooking(f203, dateFormat.parse("2025-09-09 10:00:00"), passengerNames);
        assertTrue("Booking should be created successfully", bookingResult);
        
        // Confirm all reservations
        for (Booking booking : customer.getBookings()) {
            for (Reservation reservation : booking.getReservations()) {
                boolean confirmResult = customer.confirm(reservation.getId(), dateFormat.parse("2025-09-09 10:00:00"));
                assertTrue("Reservation should be confirmed", confirmResult);
            }
        }
        
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify results
        assertFalse("Close flight should return false when current time is after departure", result);
        assertTrue("Flight should remain open for booking", f203.isOpenForBooking());
        
        // Verify reservations are still confirmed (not canceled)
        List<Reservation> reservations = f203.getReservations();
        for (Reservation reservation : reservations) {
            assertEquals("Reservations should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup airports
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP");
        City city1 = new City();
        departureAirport.addCity(city1);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR");
        City city2 = new City();
        arrivalAirport.addCity(city2);
        
        // Setup flight (note: arrival is next day)
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-02 01:30:00")); // Fixed: arrival on next day
        f204.setDepartureAirport(departureAirport);
        f204.setArrivalAirport(arrivalAirport);
        f204.setOpenForBooking(true);
        
        airline.addFlight(f204);
        
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify results
        assertFalse("Close flight should return false when current time is after departure", result);
        assertTrue("Flight should remain open for booking", f204.isOpenForBooking());
    }
}