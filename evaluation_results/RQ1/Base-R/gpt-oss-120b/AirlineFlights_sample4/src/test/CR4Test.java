import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR4Test {
    
    private AirlineSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() {
        // Setup
        // 1. Airline AL6; airports AP10 (CityJ) and AP11 (CityK).
        City cityJ = system.createCity("CJ", "CityJ");
        City cityK = system.createCity("CK", "CityK");
        List<City> citiesAP10 = Arrays.asList(cityJ);
        List<City> citiesAP11 = Arrays.asList(cityK);
        system.createAirport("AP10", "Airport10", citiesAP10);
        system.createAirport("AP11", "Airport11", citiesAP11);
        
        // 2. Flight F200: depart 2025-06-20 09:00:00, arrive 2025-06-20 13:00:00, 0 reservations. The flight is open for booking for customers.
        Flight flight = system.createFlight("F200", "AP10", "AP11", "2025-06-20 09:00:00", "2025-06-20 13:00:00");
        flight.setStatus(FlightStatus.OPEN);
        
        // 3. Current time = 2025-05-01 08:00:00.
        // Mock current time by ensuring the test runs when conditions are met (departure is in future)
        
        // Test
        boolean result = system.closeFlight("F200");
        
        // Verify
        assertTrue("Close flight should return true", result);
        assertEquals("Flight status should be CLOSED", FlightStatus.CLOSED, flight.getStatus());
        assertEquals("No reservations should be canceled", 0, system.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() {
        // Setup
        // 1. Airline AL7; airports AP12 (CityL) and AP13 (CityM).
        City cityL = system.createCity("CL", "CityL");
        City cityM = system.createCity("CM", "CityM");
        List<City> citiesAP12 = Arrays.asList(cityL);
        List<City> citiesAP13 = Arrays.asList(cityM);
        system.createAirport("AP12", "Airport12", citiesAP12);
        system.createAirport("AP13", "Airport13", citiesAP13);
        
        // 2. Flight F201: depart 2025-07-15 14:00:00, arrive 2025-07-15 18:00:00. The flight is open for booking for customers.
        Flight flight = system.createFlight("F201", "AP12", "AP13", "2025-07-15 14:00:00", "2025-07-15 18:00:00");
        flight.setStatus(FlightStatus.OPEN);
        
        // 3. Customer make a booking with three reservations R201-1, R201-2, R201-3. Customer has confirmed these reservations.
        Customer customer = system.createCustomer("CUST1", "Customer1");
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        boolean bookingCreated = system.createBooking("F201", "CUST1", passengerNames);
        assertTrue("Booking should be created", bookingCreated);
        
        // Confirm all reservations
        for (Reservation reservation : system.getReservations().values()) {
            boolean statusUpdated = system.updateReservationStatus(reservation.getId(), true);
            assertTrue("Reservation should be confirmed", statusUpdated);
        }
        
        // 4. Current time = 2025-06-10 12:00:00.
        // Mock current time by ensuring the test runs when conditions are met (departure is in future)
        
        // Test
        boolean result = system.closeFlight("F201");
        
        // Verify
        assertTrue("Close flight should return true", result);
        assertEquals("Flight status should be CLOSED", FlightStatus.CLOSED, flight.getStatus());
        
        // Check that all reservations are canceled
        for (Reservation reservation : system.getReservations().values()) {
            assertEquals("Reservation should be canceled", ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() {
        // Setup
        // 1. Airline AL8; flight F202 openForBooking = False,
        //    depart 2025-08-10 11:00:00, arrive 2025-08-10 13:30:00.
        City cityN = system.createCity("CN", "CityN");
        City cityO = system.createCity("CO", "CityO");
        List<City> citiesAP14 = Arrays.asList(cityN);
        List<City> citiesAP15 = Arrays.asList(cityO);
        system.createAirport("AP14", "Airport14", citiesAP14);
        system.createAirport("AP15", "Airport15", citiesAP15);
        
        Flight flight = system.createFlight("F202", "AP14", "AP15", "2025-08-10 11:00:00", "2025-08-10 13:30:00");
        flight.setStatus(FlightStatus.CLOSED); // Already closed
        
        // 2. Current time = 2025-07-01 09:00:00.
        // Mock current time by ensuring the test runs when conditions are met (departure is in future)
        
        // Test
        boolean result = system.closeFlight("F202");
        
        // Verify
        assertFalse("Close flight should return false for already closed flight", result);
        assertEquals("Flight status should remain CLOSED", FlightStatus.CLOSED, flight.getStatus());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() {
        // Setup
        // 1. Airline AL9; flight F203 openForBooking = True,
        //    depart 2025-09-10 09:00:00, arrive 2025-09-10 15:30:00.
        City cityP = system.createCity("CP", "CityP");
        City cityQ = system.createCity("CQ", "CityQ");
        List<City> citiesAP16 = Arrays.asList(cityP);
        List<City> citiesAP17 = Arrays.asList(cityQ);
        system.createAirport("AP16", "Airport16", citiesAP16);
        system.createAirport("AP17", "Airport17", citiesAP17);
        
        Flight flight = system.createFlight("F203", "AP16", "AP17", "2025-09-10 09:00:00", "2025-09-10 15:30:00");
        flight.setStatus(FlightStatus.OPEN);
        
        // 2. Two confirmed reservations R203-1, R203-2.
        Customer customer = system.createCustomer("CUST2", "Customer2");
        List<String> passengerNames = Arrays.asList("Passenger4", "Passenger5");
        boolean bookingCreated = system.createBooking("F203", "CUST2", passengerNames);
        assertTrue("Booking should be created", bookingCreated);
        
        // Confirm all reservations
        for (Reservation reservation : system.getReservations().values()) {
            boolean statusUpdated = system.updateReservationStatus(reservation.getId(), true);
            assertTrue("Reservation should be confirmed", statusUpdated);
        }
        
        // 3. Current time = 2025-09-10 09:10:00.
        // This test requires the system to detect that current time is after departure
        // Since we can't mock LocalDateTime.now(), we rely on the fact that departure time is in the past relative to test execution
        // For proper testing, this would require mocking the current time
        
        // Test - flight should not be closed because current time is after departure
        boolean result = system.closeFlight("F203");
        
        // Verify
        assertFalse("Close flight should return false when current time is after departure", result);
        assertEquals("Flight status should remain OPEN", FlightStatus.OPEN, flight.getStatus());
        
        // Reservations should remain confirmed since flight wasn't closed
        for (Reservation reservation : system.getReservations().values()) {
            assertEquals("Reservation should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() {
        // Setup
        // 1. Airline AL10; flight F204 openForBooking = True,
        //    depart 2025-04-01 22:00:00, arrive 2025-04-1 01:30:00.
        City cityR = system.createCity("CR", "CityR");
        City cityS = system.createCity("CS", "CityS");
        List<City> citiesAP18 = Arrays.asList(cityR);
        List<City> citiesAP19 = Arrays.asList(cityS);
        system.createAirport("AP18", "Airport18", citiesAP18);
        system.createAirport("AP19", "Airport19", citiesAP19);
        
        Flight flight = system.createFlight("F204", "AP18", "AP19", "2025-04-01 22:00:00", "2025-04-02 01:30:00");
        flight.setStatus(FlightStatus.OPEN);
        
        // 2. Current time = 2025-04-01 22:05:00 (flight already left).
        // This test requires the system to detect that current time is after departure
        // Since we can't mock LocalDateTime.now(), we rely on the fact that departure time is in the past relative to test execution
        // For proper testing, this would require mocking the current time
        
        // Test - flight should not be closed because current time is after departure
        boolean result = system.closeFlight("F204");
        
        // Verify
        assertFalse("Close flight should return false when current time is after departure", result);
        assertEquals("Flight status should remain OPEN", FlightStatus.OPEN, flight.getStatus());
    }
}