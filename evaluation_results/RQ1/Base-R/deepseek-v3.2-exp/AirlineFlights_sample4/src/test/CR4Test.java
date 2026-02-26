import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CR4Test {
    
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup: Create airline AL6 with airports AP10 (CityJ) and AP11 (CityK)
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        Set<String> cities10 = new HashSet<>();
        cities10.add("CityJ");
        ap10.setServedCities(cities10);
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        Set<String> cities11 = new HashSet<>();
        cities11.add("CityK");
        ap11.setServedCities(cities11);
        
        // Create flight F200
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        flight.setOpenForBooking(true);
        
        // Set current time to 2025-05-01 08:00:00 (before departure)
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        // For this test, we rely on the flight's internal validation
        
        // Publish the flight to the system
        boolean publishResult = airlineSystem.publishFlight(flight);
        assertTrue("Flight should be published successfully", publishResult);
        
        // Verify initial state: flight is open, 0 reservations
        assertTrue("Flight should be open for booking", flight.isOpenForBooking());
        assertEquals("Flight should have 0 reservations", 0, flight.getReservations().size());
        
        // Execute: Close flight F200
        boolean result = airlineSystem.closeFlight("F200");
        
        // Verify: Should return true, flight should be closed, no reservations to cancel
        assertTrue("Closing flight should return true", result);
        assertFalse("Flight should be closed after operation", flight.isOpenForBooking());
        assertEquals("No reservations should exist", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup: Create airline AL7 with airports AP12 (CityL) and AP13 (CityM)
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        Set<String> cities12 = new HashSet<>();
        cities12.add("CityL");
        ap12.setServedCities(cities12);
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        Set<String> cities13 = new HashSet<>();
        cities13.add("CityM");
        ap13.setServedCities(cities13);
        
        // Create flight F201
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        flight.setOpenForBooking(true);
        
        // Publish the flight to the system
        boolean publishResult = airlineSystem.publishFlight(flight);
        assertTrue("Flight should be published successfully", publishResult);
        
        // Create booking with three reservations and confirm them
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        boolean bookingResult = airlineSystem.createBooking(flight, passengerNames, "CUST001");
        assertTrue("Booking should be created successfully", bookingResult);
        
        // Confirm all reservations
        List<Reservation> reservations = flight.getReservations();
        assertEquals("Flight should have 3 reservations", 3, reservations.size());
        
        for (Reservation reservation : reservations) {
            boolean confirmResult = airlineSystem.confirmOrCancelReservation(reservation.getId(), true);
            assertTrue("Reservation confirmation should succeed", confirmResult);
            assertEquals("Reservation should be confirmed", "confirmed", reservation.getStatus());
        }
        
        // Set current time to 2025-06-10 12:00:00 (before departure)
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        
        // Execute: Close flight F201
        boolean result = airlineSystem.closeFlight("F201");
        
        // Verify: Should return true, flight should be closed, all reservations canceled
        assertTrue("Closing flight should return true", result);
        assertFalse("Flight should be closed after operation", flight.isOpenForBooking());
        
        // Check that all reservations are canceled
        for (Reservation reservation : reservations) {
            assertEquals("All reservations should be canceled", "canceled", reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup: Create airline AL8 with flight F202 already closed
        Airport ap14 = new Airport();
        ap14.setId("AP14");
        Set<String> cities14 = new HashSet<>();
        cities14.add("CityN");
        ap14.setServedCities(cities14);
        
        Airport ap15 = new Airport();
        ap15.setId("AP15");
        Set<String> cities15 = new HashSet<>();
        cities15.add("CityO");
        ap15.setServedCities(cities15);
        
        // Create flight F202 with openForBooking = false
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setDepartureAirport(ap14);
        flight.setArrivalAirport(ap15);
        flight.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        flight.setOpenForBooking(false); // Already closed
        
        // Add flight to system (even though it's closed, we add it to test the scenario)
        airlineSystem.getFlights().add(flight);
        
        // Set current time to 2025-07-01 09:00:00 (before departure)
        
        // Execute: Close flight F202
        boolean result = airlineSystem.closeFlight("F202");
        
        // Verify: Should return false since flight is already closed
        assertFalse("Closing already closed flight should return false", result);
        assertFalse("Flight should remain closed", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup: Create airline AL9 with flight F203 open for booking
        Airport ap16 = new Airport();
        ap16.setId("AP16");
        Set<String> cities16 = new HashSet<>();
        cities16.add("CityP");
        ap16.setServedCities(cities16);
        
        Airport ap17 = new Airport();
        ap17.setId("AP17");
        Set<String> cities17 = new HashSet<>();
        cities17.add("CityQ");
        ap17.setServedCities(cities17);
        
        // Create flight F203
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setDepartureAirport(ap16);
        flight.setArrivalAirport(ap17);
        flight.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        flight.setOpenForBooking(true);
        
        // Publish the flight to the system
        boolean publishResult = airlineSystem.publishFlight(flight);
        assertTrue("Flight should be published successfully", publishResult);
        
        // Create two confirmed reservations
        List<String> passengerNames = Arrays.asList("PassengerA", "PassengerB");
        boolean bookingResult = airlineSystem.createBooking(flight, passengerNames, "CUST002");
        assertTrue("Booking should be created successfully", bookingResult);
        
        // Confirm all reservations
        List<Reservation> reservations = flight.getReservations();
        assertEquals("Flight should have 2 reservations", 2, reservations.size());
        
        for (Reservation reservation : reservations) {
            boolean confirmResult = airlineSystem.confirmOrCancelReservation(reservation.getId(), true);
            assertTrue("Reservation confirmation should succeed", confirmResult);
            assertEquals("Reservation should be confirmed", "confirmed", reservation.getStatus());
        }
        
        // Set current time to 2025-09-10 09:10:00 (after departure time)
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        // For this test, the flight's closeFlight method will check if current time is before departure
        
        // Execute: Close flight F203
        boolean result = airlineSystem.closeFlight("F203");
        
        // Verify: Should return false since flight has already departed
        assertFalse("Closing flight after departure should return false", result);
        assertTrue("Flight should remain open since closure failed", flight.isOpenForBooking());
        
        // Verify reservations remain confirmed (not canceled)
        for (Reservation reservation : reservations) {
            assertEquals("Reservations should remain confirmed", "confirmed", reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup: Create airline AL10 with flight F204 open for booking
        Airport ap18 = new Airport();
        ap18.setId("AP18");
        Set<String> cities18 = new HashSet<>();
        cities18.add("CityR");
        ap18.setServedCities(cities18);
        
        Airport ap19 = new Airport();
        ap19.setId("AP19");
        Set<String> cities19 = new HashSet<>();
        cities19.add("CityS");
        ap19.setServedCities(cities19);
        
        // Create flight F204 (note: arrival is technically next day due to time)
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setDepartureAirport(ap18);
        flight.setArrivalAirport(ap19);
        flight.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-02 01:30:00", formatter)); // Fixed: should be April 2nd
        flight.setOpenForBooking(true);
        
        // Publish the flight to the system
        boolean publishResult = airlineSystem.publishFlight(flight);
        assertTrue("Flight should be published successfully", publishResult);
        
        // Set current time to 2025-04-01 22:05:00 (after departure)
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        
        // Execute: Close flight F204
        boolean result = airlineSystem.closeFlight("F204");
        
        // Verify: Should return false since flight has already departed
        assertFalse("Closing flight after departure should return false", result);
        assertTrue("Flight should remain open since closure failed", flight.isOpenForBooking());
    }
}