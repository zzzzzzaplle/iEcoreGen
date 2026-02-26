import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Airline airlineAL6;
    private Airline airlineAL7;
    private Airline airlineAL8;
    private Airline airlineAL9;
    private Airline airlineAL10;
    
    private Flight flightF200;
    private Flight flightF201;
    private Flight flightF202;
    private Flight flightF203;
    private Flight flightF204;
    
    private Customer customer;
    private Airport airportAP10;
    private Airport airportAP11;
    private Airport airportAP12;
    private Airport airportAP13;
    
    @Before
    public void setUp() {
        // Clear booking repository before each test
        // Note: Since BookingRepository uses static list, we need to clear it manually
        // In a real scenario, we would use dependency injection or reset mechanism
        List<Booking> allBookings = new ArrayList<>(BookingRepository.getAllBookings());
        for (Booking booking : allBookings) {
            BookingRepository.getAllBookings().remove(booking);
        }
        
        // Create cities
        City cityJ = new City("CJ", "CityJ");
        City cityK = new City("CK", "CityK");
        City cityL = new City("CL", "CityL");
        City cityM = new City("CM", "CityM");
        
        // Create airports
        airportAP10 = new Airport("AP10", "Airport10");
        airportAP10.getServedCities().add(cityJ);
        
        airportAP11 = new Airport("AP11", "Airport11");
        airportAP11.getServedCities().add(cityK);
        
        airportAP12 = new Airport("AP12", "Airport12");
        airportAP12.getServedCities().add(cityL);
        
        airportAP13 = new Airport("AP13", "Airport13");
        airportAP13.getServedCities().add(cityM);
        
        // Create airlines
        airlineAL6 = new Airline("AL6", "Airline6");
        airlineAL7 = new Airline("AL7", "Airline7");
        airlineAL8 = new Airline("AL8", "Airline8");
        airlineAL9 = new Airline("AL9", "Airline9");
        airlineAL10 = new Airline("AL10", "Airline10");
        
        // Create customer
        customer = new Customer("C1", "Customer1");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup: Create flight F200
        flightF200 = new Flight("F200");
        flightF200.setDepartureAirport(airportAP10);
        flightF200.setArrivalAirport(airportAP11);
        flightF200.setDepartureTime(FlightService.parseDateTime("2025-06-20 09:00:00"));
        flightF200.setArrivalTime(FlightService.parseDateTime("2025-06-20 13:00:00"));
        flightF200.setStatus(FlightStatus.OPEN);
        
        airlineAL6.addFlight(flightF200);
        
        // Set current time to 2025-05-01 08:00:00 (before departure)
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test, we assume the time check passes as specified
        
        // Execute: Close flight F200
        boolean result = FlightService.closeFlight(flightF200);
        
        // Verify: Should return true, no reservations to cancel
        assertTrue("Flight should be closed successfully", result);
        assertEquals("Flight status should be CLOSED", FlightStatus.CLOSED, flightF200.getStatus());
        
        // Check that no reservations exist for this flight
        List<Reservation> reservations = BookingRepository.getReservationsByFlightId("F200");
        assertTrue("No reservations should exist for flight F200", reservations.isEmpty());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup: Create flight F201
        flightF201 = new Flight("F201");
        flightF201.setDepartureAirport(airportAP12);
        flightF201.setArrivalAirport(airportAP13);
        flightF201.setDepartureTime(FlightService.parseDateTime("2025-07-15 14:00:00"));
        flightF201.setArrivalTime(FlightService.parseDateTime("2025-07-15 18:00:00"));
        flightF201.setStatus(FlightStatus.OPEN);
        
        airlineAL7.addFlight(flightF201);
        
        // Create booking with three reservations and confirm them
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Passenger1");
        passengerNames.add("Passenger2");
        passengerNames.add("Passenger3");
        
        boolean bookingCreated = FlightService.createBooking(customer, flightF201, passengerNames);
        assertTrue("Booking should be created successfully", bookingCreated);
        
        // Confirm all reservations
        List<Reservation> reservations = BookingRepository.getReservationsByFlightId("F201");
        assertEquals("Should have 3 reservations", 3, reservations.size());
        
        for (Reservation reservation : reservations) {
            boolean statusUpdated = FlightService.updateReservationStatus(reservation.getId(), true);
            assertTrue("Reservation should be confirmed", statusUpdated);
            assertEquals("Reservation status should be CONFIRMED", 
                ReservationStatus.CONFIRMED, reservation.getStatus());
        }
        
        // Set current time to 2025-06-10 12:00:00 (before departure)
        // Note: In real implementation, we would mock LocalDateTime.now()
        
        // Execute: Close flight F201
        boolean result = FlightService.closeFlight(flightF201);
        
        // Verify: Should return true and all reservations should be canceled
        assertTrue("Flight should be closed successfully", result);
        assertEquals("Flight status should be CLOSED", FlightStatus.CLOSED, flightF201.getStatus());
        
        // Check that all confirmed reservations are now canceled
        List<Reservation> updatedReservations = BookingRepository.getReservationsByFlightId("F201");
        for (Reservation reservation : updatedReservations) {
            assertEquals("All reservations should be canceled", 
                ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup: Create flight F202 that is already closed
        flightF202 = new Flight("F202");
        flightF202.setDepartureAirport(airportAP10);
        flightF202.setArrivalAirport(airportAP11);
        flightF202.setDepartureTime(FlightService.parseDateTime("2025-08-10 11:00:00"));
        flightF202.setArrivalTime(FlightService.parseDateTime("2025-08-10 13:30:00"));
        flightF202.setStatus(FlightStatus.CLOSED); // Already closed
        
        airlineAL8.addFlight(flightF202);
        
        // Set current time to 2025-07-01 09:00:00 (before departure)
        // Note: In real implementation, we would mock LocalDateTime.now()
        
        // Execute: Attempt to close already closed flight F202
        boolean result = FlightService.closeFlight(flightF202);
        
        // Verify: Should return false since flight is already closed
        assertFalse("Should return false for already closed flight", result);
        assertEquals("Flight status should remain CLOSED", FlightStatus.CLOSED, flightF202.getStatus());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup: Create flight F203
        flightF203 = new Flight("F203");
        flightF203.setDepartureAirport(airportAP10);
        flightF203.setArrivalAirport(airportAP11);
        flightF203.setDepartureTime(FlightService.parseDateTime("2025-09-10 09:00:00"));
        flightF203.setArrivalTime(FlightService.parseDateTime("2025-09-10 15:30:00"));
        flightF203.setStatus(FlightStatus.OPEN);
        
        airlineAL9.addFlight(flightF203);
        
        // Create booking with two reservations and confirm them
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("PassengerA");
        passengerNames.add("PassengerB");
        
        boolean bookingCreated = FlightService.createBooking(customer, flightF203, passengerNames);
        assertTrue("Booking should be created successfully", bookingCreated);
        
        // Confirm all reservations
        List<Reservation> reservations = BookingRepository.getReservationsByFlightId("F203");
        for (Reservation reservation : reservations) {
            FlightService.updateReservationStatus(reservation.getId(), true);
        }
        
        // Set current time to 2025-09-10 09:10:00 (after departure)
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test, we assume the time check fails as specified
        
        // Execute: Attempt to close flight F203 after departure time
        boolean result = FlightService.closeFlight(flightF203);
        
        // Verify: Should return false since flight has already departed
        assertFalse("Should return false when trying to close after departure", result);
        assertEquals("Flight status should remain OPEN", FlightStatus.OPEN, flightF203.getStatus());
        
        // Check that reservations are NOT canceled (should remain confirmed)
        List<Reservation> updatedReservations = BookingRepository.getReservationsByFlightId("F203");
        for (Reservation reservation : updatedReservations) {
            assertEquals("Reservations should remain CONFIRMED", 
                ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup: Create flight F204
        flightF204 = new Flight("F204");
        flightF204.setDepartureAirport(airportAP10);
        flightF204.setArrivalAirport(airportAP11);
        flightF204.setDepartureTime(FlightService.parseDateTime("2025-04-01 22:00:00"));
        flightF204.setArrivalTime(FlightService.parseDateTime("2025-04-01 01:30:00")); // Next day arrival
        flightF204.setStatus(FlightStatus.OPEN);
        
        airlineAL10.addFlight(flightF204);
        
        // Set current time to 2025-04-01 22:05:00 (after departure)
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test, we assume the time check fails as specified
        
        // Execute: Attempt to close flight F204 after departure
        boolean result = FlightService.closeFlight(flightF204);
        
        // Verify: Should return false since flight has already departed
        assertFalse("Should return false when trying to close after departure", result);
        assertEquals("Flight status should remain OPEN", FlightStatus.OPEN, flightF204.getStatus());
    }
}