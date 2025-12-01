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
    public void testCase1_NoReservationsToCancel() {
        // Setup airports
        Airport ap10 = new Airport("AP10", "Airport J");
        Airport ap11 = new Airport("AP11", "Airport K");
        
        // Setup cities
        City cityJ = new City("CJ", "CityJ");
        City cityK = new City("CK", "CityK");
        
        // Add cities to airports
        ap10.addCity(cityJ);
        ap11.addCity(cityK);
        
        // Add airports to system
        system.addAirport(ap10);
        system.addAirport(ap11);
        
        // Setup flight
        LocalDateTime departTime = LocalDateTime.parse("2025-06-20 09:00:00", formatter);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-06-20 13:00:00", formatter);
        Flight flight = new Flight(ap10, ap11, departTime, arriveTime);
        flight.setStatus(FlightStatus.PUBLISHED);
        system.addFlight(flight);
        
        // Set current time to before departure
        LocalDateTime currentTime = LocalDateTime.parse("2025-05-01 08:00:00", formatter);
        
        // Execute closeFlight
        boolean result = system.closeFlight("FL-1");
        
        // Verify results
        assertTrue("Close flight should return true", result);
        assertEquals("Flight status should be CLOSED", FlightStatus.CLOSED, flight.getStatus());
        
        // Verify no reservations were canceled (no reservations exist)
        List<Reservation> confirmedReservations = system.getConfirmedReservations("FL-1");
        assertTrue("No reservations should exist", confirmedReservations.isEmpty());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup airports
        Airport ap12 = new Airport("AP12", "Airport L");
        Airport ap13 = new Airport("AP13", "Airport M");
        
        // Setup cities
        City cityL = new City("CL", "CityL");
        City cityM = new City("CM", "CityM");
        
        // Add cities to airports
        ap12.addCity(cityL);
        ap13.addCity(cityM);
        
        // Add airports to system
        system.addAirport(ap12);
        system.addAirport(ap13);
        
        // Setup customer
        Customer customer = new Customer("CUST1", "Customer AL7");
        system.addCustomer(customer);
        
        // Setup flight
        LocalDateTime departTime = LocalDateTime.parse("2025-07-15 14:00:00", formatter);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-07-15 18:00:00", formatter);
        Flight flight = new Flight(ap12, ap13, departTime, arriveTime);
        flight.setStatus(FlightStatus.PUBLISHED);
        system.addFlight(flight);
        
        // Create booking with three reservations
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        boolean bookingCreated = system.createBooking("FL-1", "CUST1", passengerNames);
        assertTrue("Booking should be created successfully", bookingCreated);
        
        // Confirm all three reservations
        Map<String, Reservation> reservations = system.getReservations();
        for (Reservation reservation : reservations.values()) {
            boolean confirmed = system.confirmReservation(reservation.getId());
            assertTrue("Reservation should be confirmed", confirmed);
        }
        
        // Verify three confirmed reservations exist before closing
        List<Reservation> confirmedBefore = system.getConfirmedReservations("FL-1");
        assertEquals("Three reservations should be confirmed", 3, confirmedBefore.size());
        
        // Set current time to before departure
        LocalDateTime currentTime = LocalDateTime.parse("2025-06-10 12:00:00", formatter);
        
        // Execute closeFlight
        boolean result = system.closeFlight("FL-1");
        
        // Verify results
        assertTrue("Close flight should return true", result);
        assertEquals("Flight status should be CLOSED", FlightStatus.CLOSED, flight.getStatus());
        
        // Verify all three reservations were canceled
        List<Reservation> confirmedAfter = system.getConfirmedReservations("FL-1");
        assertTrue("No confirmed reservations should remain", confirmedAfter.isEmpty());
        
        // Verify all reservations are now canceled
        for (Reservation reservation : reservations.values()) {
            assertEquals("Reservation should be CANCELED", ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup airports
        Airport ap14 = new Airport("AP14", "Airport N");
        Airport ap15 = new Airport("AP15", "Airport O");
        
        // Setup cities
        City cityN = new City("CN", "CityN");
        City cityO = new City("CO", "CityO");
        
        // Add cities to airports
        ap14.addCity(cityN);
        ap15.addCity(cityO);
        
        // Add airports to system
        system.addAirport(ap14);
        system.addAirport(ap15);
        
        // Setup flight that is already closed
        LocalDateTime departTime = LocalDateTime.parse("2025-08-10 11:00:00", formatter);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-08-10 13:30:00", formatter);
        Flight flight = new Flight(ap14, ap15, departTime, arriveTime);
        flight.setStatus(FlightStatus.CLOSED);
        system.addFlight(flight);
        
        // Set current time to before departure
        LocalDateTime currentTime = LocalDateTime.parse("2025-07-01 09:00:00", formatter);
        
        // Execute closeFlight
        boolean result = system.closeFlight("FL-1");
        
        // Verify results
        assertFalse("Close flight should return false for already closed flight", result);
        assertEquals("Flight status should remain CLOSED", FlightStatus.CLOSED, flight.getStatus());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup airports
        Airport ap16 = new Airport("AP16", "Airport P");
        Airport ap17 = new Airport("AP17", "Airport Q");
        
        // Setup cities
        City cityP = new City("CP", "CityP");
        City cityQ = new City("CQ", "CityQ");
        
        // Add cities to airports
        ap16.addCity(cityP);
        ap17.addCity(cityQ);
        
        // Add airports to system
        system.addAirport(ap16);
        system.addAirport(ap17);
        
        // Setup customer
        Customer customer = new Customer("CUST2", "Customer AL9");
        system.addCustomer(customer);
        
        // Setup flight
        LocalDateTime departTime = LocalDateTime.parse("2025-09-10 09:00:00", formatter);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-09-10 15:30:00", formatter);
        Flight flight = new Flight(ap16, ap17, departTime, arriveTime);
        flight.setStatus(FlightStatus.PUBLISHED);
        system.addFlight(flight);
        
        // Create booking with two reservations
        List<String> passengerNames = Arrays.asList("PassengerA", "PassengerB");
        boolean bookingCreated = system.createBooking("FL-1", "CUST2", passengerNames);
        assertTrue("Booking should be created successfully", bookingCreated);
        
        // Confirm both reservations
        Map<String, Reservation> reservations = system.getReservations();
        for (Reservation reservation : reservations.values()) {
            boolean confirmed = system.confirmReservation(reservation.getId());
            assertTrue("Reservation should be confirmed", confirmed);
        }
        
        // Set current time to after departure time
        LocalDateTime currentTime = LocalDateTime.parse("2025-09-10 09:10:00", formatter);
        
        // Execute closeFlight
        boolean result = system.closeFlight("FL-1");
        
        // Verify results
        assertFalse("Close flight should return false after departure time", result);
        assertEquals("Flight status should remain PUBLISHED", FlightStatus.PUBLISHED, flight.getStatus());
        
        // Verify reservations are still confirmed (not canceled)
        List<Reservation> confirmedAfter = system.getConfirmedReservations("FL-1");
        assertEquals("Two reservations should still be confirmed", 2, confirmedAfter.size());
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup airports
        Airport ap18 = new Airport("AP18", "Airport R");
        Airport ap19 = new Airport("AP19", "Airport S");
        
        // Setup cities
        City cityR = new City("CR", "CityR");
        City cityS = new City("CS", "CityS");
        
        // Add cities to airports
        ap18.addCity(cityR);
        ap19.addCity(cityS);
        
        // Add airports to system
        system.addAirport(ap18);
        system.addAirport(ap19);
        
        // Setup flight that has already departed
        LocalDateTime departTime = LocalDateTime.parse("2025-04-01 22:00:00", formatter);
        LocalDateTime arriveTime = LocalDateTime.parse("2025-04-01 01:30:00", formatter);
        Flight flight = new Flight(ap18, ap19, departTime, arriveTime);
        flight.setStatus(FlightStatus.PUBLISHED);
        system.addFlight(flight);
        
        // Set current time to after departure
        LocalDateTime currentTime = LocalDateTime.parse("2025-04-01 22:05:00", formatter);
        
        // Execute closeFlight
        boolean result = system.closeFlight("FL-1");
        
        // Verify results
        assertFalse("Close flight should return false after departure", result);
        assertEquals("Flight status should remain PUBLISHED", FlightStatus.PUBLISHED, flight.getStatus());
    }
}