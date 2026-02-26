import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR2Test {
    
    private AirlineSystem airline;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airline = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() {
        // Setup
        // 1. Create airline AL11
        // 2. Create airports AP100 (departure) and AP101 (arrival)
        City city1 = airline.createCity("C100", "City100");
        City city2 = airline.createCity("C101", "City101");
        Airport ap100 = airline.createAirport("AP100", "Airport100", Arrays.asList(city1));
        Airport ap101 = airline.createAirport("AP101", "Airport101", Arrays.asList(city2));
        
        // 3. Create flight F300 under AL11 - it is open for booking
        Flight flight = airline.createFlight("F300", "AP100", "AP101", 
                                           "2025-10-05 08:00:00", "2025-10-05 12:00:00");
        assertNotNull(flight);
        flight.setStatus(FlightStatus.OPEN);
        airline.getFlights().put("F300", flight);
        
        // 4. Instantiate customer CUA
        Customer customer = airline.createCustomer("CUA", "CustomerA");
        
        // 5. Instantiate passengers P1:"Peter", P2:"Beck"
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Set current time = 2025-10-01 09:00:00
        // This is done by ensuring the test runs when LocalDateTime.now() is before departure
        
        // Test
        boolean result = airline.createBooking("F300", "CUA", passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        
        // Check that two reservations were created
        Map<String, Reservation> reservations = airline.getReservations();
        int reservationCount = 0;
        for (Reservation res : reservations.values()) {
            if (res.getBooking() != null && res.getBooking().getFlight().getId().equals("F300")) {
                reservationCount++;
                assertEquals("Reservation status should be PENDING", ReservationStatus.PENDING, res.getStatus());
            }
        }
        assertEquals("Should have exactly 2 reservations", 2, reservationCount);
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() {
        // Setup
        // 1. Create airline AL12
        // 2. Create airports AP102 (dep) and AP103 (arr)
        City city1 = airline.createCity("C102", "City102");
        City city2 = airline.createCity("C103", "City103");
        Airport ap102 = airline.createAirport("AP102", "Airport102", Arrays.asList(city1));
        Airport ap103 = airline.createAirport("AP103", "Airport103", Arrays.asList(city2));
        
        // 3. Create flight F301 under AL12 - it is open for booking
        Flight flight = airline.createFlight("F301", "AP102", "AP103", 
                                           "2025-10-05 08:00:00", "2025-10-05 10:00:00");
        assertNotNull(flight);
        flight.setStatus(FlightStatus.OPEN);
        airline.getFlights().put("F301", flight);
        
        // 4. Customer CUB
        Customer customer = airline.createCustomer("CUB", "CustomerB");
        
        // 5. Passenger P3 "Alice" (duplicate in same request)
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Set current time = 2025-10-01 09:00:00
        
        // Test
        boolean result = airline.createBooking("F301", "CUB", passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger in same request", result);
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() {
        // Setup
        // 1. Create airline AL13 with airports AP104, AP105
        City city1 = airline.createCity("C104", "City104");
        City city2 = airline.createCity("C105", "City105");
        Airport ap104 = airline.createAirport("AP104", "Airport104", Arrays.asList(city1));
        Airport ap105 = airline.createAirport("AP105", "Airport105", Arrays.asList(city2));
        
        // 2. Create flight F302 - it is open for booking
        Flight flight = airline.createFlight("F302", "AP104", "AP105", 
                                           "2025-10-05 18:00:00", "2025-10-06 02:00:00");
        assertNotNull(flight);
        flight.setStatus(FlightStatus.OPEN);
        airline.getFlights().put("F302", flight);
        
        // 3. Passenger P4 "Jucy"
        // 4. Customer CUC
        Customer customer = airline.createCustomer("CUC", "CustomerC");
        
        // 5. Pre-existing booking BK-OLD containing reservation R302-A for passenger P4 "Jucy" on F302 (status = PENDING)
        Booking existingBooking = new Booking(customer, flight);
        Reservation existingReservation = new Reservation("Jucy");
        existingReservation.setStatus(ReservationStatus.PENDING);
        existingReservation.setBooking(existingBooking);
        existingBooking.getReservations().add(existingReservation);
        customer.getBookings().add(existingBooking);
        airline.getReservations().put(existingReservation.getId(), existingReservation);
        
        // Test - try to book same passenger again
        List<String> passengerNames = Arrays.asList("Jucy");
        boolean result = airline.createBooking("F302", "CUC", passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked on same flight", result);
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        City city1 = airline.createCity("C106", "City106");
        City city2 = airline.createCity("C107", "City107");
        Airport ap106 = airline.createAirport("AP106", "Airport106", Arrays.asList(city1));
        Airport ap107 = airline.createAirport("AP107", "Airport107", Arrays.asList(city2));
        
        // 2. Create flight F303 under AL14 - it is closed for booking
        Flight flight = airline.createFlight("F303", "AP106", "AP107", 
                                           "2025-10-05 18:00:00", "2025-10-06 02:00:00");
        assertNotNull(flight);
        flight.setStatus(FlightStatus.CLOSED); // Flight is closed
        airline.getFlights().put("F303", flight);
        
        // 3. Customer CUD
        Customer customer = airline.createCustomer("CUD", "CustomerD");
        
        // 4. Passenger P5 "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Set current time = 2025-10-01 09:00:00
        
        // Test
        boolean result = airline.createBooking("F303", "CUD", passengerNames);
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
    }
    
    @Test
    public void testCase5_timeIsAfterDepartureTime() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        City city1 = airline.createCity("C106", "City106");
        City city2 = airline.createCity("C107", "City107");
        Airport ap106 = airline.createAirport("AP106", "Airport106", Arrays.asList(city1));
        Airport ap107 = airline.createAirport("AP107", "Airport107", Arrays.asList(city2));
        
        // 2. Create flight F303 under AL14 - it is open for booking
        Flight flight = airline.createFlight("F303", "AP104", "AP105", 
                                           "2025-10-05 18:00:00", "2025-10-06 02:00:00");
        assertNotNull(flight);
        flight.setStatus(FlightStatus.OPEN);
        airline.getFlights().put("F303", flight);
        
        // 3. Customer CUD
        Customer customer = airline.createCustomer("CUD", "CustomerD");
        
        // 4. Passenger P5 "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Set current time = 2025-10-06 09:00:00 (after departure time)
        // This test relies on the system's current time check in createBooking method
        
        // Test
        boolean result = airline.createBooking("F303", "CUD", passengerNames);
        
        // Verify
        assertFalse("Booking should fail because current time is after departure time", result);
    }
}