import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    
    private AirlineReservationSystem.AirlineService service;
    private AirlineReservationSystem.City city1, city2;
    private AirlineReservationSystem.Airport airport1, airport2;
    private AirlineReservationSystem.Flight flight;
    private AirlineReservationSystem.Customer customer;
    
    @Before
    public void setUp() {
        service = new AirlineReservationSystem.AirlineService();
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() {
        // Setup
        // 1. Create airline AL11 (implicit through service)
        // 2. Create airports AP100 (departure) and AP101 (arrival)
        city1 = new AirlineReservationSystem.City();
        city1.setId("C1");
        city1.setName("City1");
        
        city2 = new AirlineReservationSystem.City();
        city2.setId("C2");
        city2.setName("City2");
        
        airport1 = new AirlineReservationSystem.Airport();
        airport1.setId("AP100");
        airport1.setName("Airport100");
        airport1.setCity(city1);
        
        airport2 = new AirlineReservationSystem.Airport();
        airport2.setId("AP101");
        airport2.setName("Airport101");
        airport2.setCity(city2);
        
        // 3. Create flight F300 under AL11
        flight = new AirlineReservationSystem.Flight();
        flight.setDepartureAirport(airport1);
        flight.setArrivalAirport(airport2);
        flight.setDepartureTime(LocalDateTime.of(2025, 10, 5, 8, 0, 0));
        flight.setArrivalTime(LocalDateTime.of(2025, 10, 5, 12, 0, 0));
        flight.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        boolean published = service.publishFlight(flight);
        assertTrue("Flight should be published successfully", published);
        
        // 4. Instantiate customer CUA
        customer = new AirlineReservationSystem.Customer();
        customer.setId("CUA");
        customer.setName("Customer A");
        
        // 5. Instantiate passengers P1:"Peter", P2:"Beck"
        List<String> passengers = Arrays.asList("Peter", "Beck");
        
        // Test - current time = 2025-10-01 09:00:00 (before departure)
        // Expected Output: True. There are two reservations for passengers P1 and P2.
        boolean result = service.createBooking(customer, flight.getId(), passengers);
        assertTrue("Booking should be created successfully", result);
        
        // Verify two reservations were created
        assertEquals("Should have 2 reservations", 2, service.getAllReservations().size());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() {
        // Setup
        // 1. Create airline AL12 (implicit through service)
        // 2. Create airports AP102 (dep) and AP103 (arr)
        city1 = new AirlineReservationSystem.City();
        city1.setId("C1");
        city1.setName("City1");
        
        city2 = new AirlineReservationSystem.City();
        city2.setId("C2");
        city2.setName("City2");
        
        airport1 = new AirlineReservationSystem.Airport();
        airport1.setId("AP102");
        airport1.setName("Airport102");
        airport1.setCity(city1);
        
        airport2 = new AirlineReservationSystem.Airport();
        airport2.setId("AP103");
        airport2.setName("Airport103");
        airport2.setCity(city2);
        
        // 3. Create flight F301 under AL12
        flight = new AirlineReservationSystem.Flight();
        flight.setDepartureAirport(airport1);
        flight.setArrivalAirport(airport2);
        flight.setDepartureTime(LocalDateTime.of(2025, 10, 5, 8, 0, 0));
        flight.setArrivalTime(LocalDateTime.of(2025, 10, 5, 10, 0, 0));
        flight.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        boolean published = service.publishFlight(flight);
        assertTrue("Flight should be published successfully", published);
        
        // 4. Customer CUB
        customer = new AirlineReservationSystem.Customer();
        customer.setId("CUB");
        customer.setName("Customer B");
        
        // 5. Passenger P3 "Alice" (duplicated in list)
        List<String> passengers = Arrays.asList("Alice", "Alice");
        
        // Test - current time = 2025-10-01 09:00:00 (before departure)
        // Expected Output: False. (Duplicate passenger)
        boolean result = service.createBooking(customer, flight.getId(), passengers);
        assertFalse("Booking should fail due to duplicate passengers", result);
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() {
        // Setup
        // 1. Create airline AL13 with airports AP104, AP105
        city1 = new AirlineReservationSystem.City();
        city1.setId("C1");
        city1.setName("City1");
        
        city2 = new AirlineReservationSystem.City();
        city2.setId("C2");
        city2.setName("City2");
        
        airport1 = new AirlineReservationSystem.Airport();
        airport1.setId("AP104");
        airport1.setName("Airport104");
        airport1.setCity(city1);
        
        airport2 = new AirlineReservationSystem.Airport();
        airport2.setId("AP105");
        airport2.setName("Airport105");
        airport2.setCity(city2);
        
        // 2. Create flight F302
        flight = new AirlineReservationSystem.Flight();
        flight.setDepartureAirport(airport1);
        flight.setArrivalAirport(airport2);
        flight.setDepartureTime(LocalDateTime.of(2025, 10, 5, 18, 0, 0));
        flight.setArrivalTime(LocalDateTime.of(2025, 10, 6, 2, 0, 0));
        flight.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        boolean published = service.publishFlight(flight);
        assertTrue("Flight should be published successfully", published);
        
        // 3. Passenger P4 "Jucy"
        // 4. Customer CUC
        customer = new AirlineReservationSystem.Customer();
        customer.setId("CUC");
        customer.setName("Customer C");
        
        // 5. Pre-existing booking BK-OLD containing reservation R302-A for passenger P4 "Jucy" on F302 (status = PENDING)
        List<String> firstPassengers = Arrays.asList("Jucy");
        boolean firstBooking = service.createBooking(customer, flight.getId(), firstPassengers);
        assertTrue("First booking should succeed", firstBooking);
        
        // Test - current time = 2025-10-01 09:00:00 (before departure)
        // Try to book the same passenger again
        List<String> samePassenger = Arrays.asList("Jucy");
        boolean result = service.createBooking(customer, flight.getId(), samePassenger);
        
        // Expected Output: False (passenger already booked)
        assertFalse("Booking should fail because passenger already booked", result);
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        city1 = new AirlineReservationSystem.City();
        city1.setId("C1");
        city1.setName("City1");
        
        city2 = new AirlineReservationSystem.City();
        city2.setId("C2");
        city2.setName("City2");
        
        airport1 = new AirlineReservationSystem.Airport();
        airport1.setId("AP106");
        airport1.setName("Airport106");
        airport1.setCity(city1);
        
        airport2 = new AirlineReservationSystem.Airport();
        airport2.setId("AP107");
        airport2.setName("Airport107");
        airport2.setCity(city2);
        
        // 2. Create flight F303 under AL14 (closed for booking)
        flight = new AirlineReservationSystem.Flight();
        flight.setDepartureAirport(airport1);
        flight.setArrivalAirport(airport2);
        flight.setDepartureTime(LocalDateTime.of(2025, 10, 5, 18, 0, 0));
        flight.setArrivalTime(LocalDateTime.of(2025, 10, 6, 2, 0, 0));
        flight.setStatus(AirlineReservationSystem.FlightStatus.CLOSED);
        
        // Manually add to flights map since publishFlight would set it to OPEN
        flight.setId("F303");
        service.getAllFlights().add(flight);
        
        // 3. Customer CUD
        customer = new AirlineReservationSystem.Customer();
        customer.setId("CUD");
        customer.setName("Customer D");
        
        // 4. Passenger P5 "Ruby"
        List<String> passengers = Arrays.asList("Ruby");
        
        // Test - current time = 2025-10-01 09:00:00 (before departure)
        // Expected Output: False (flight is closed)
        boolean result = service.createBooking(customer, "F303", passengers);
        assertFalse("Booking should fail because flight is closed", result);
    }
    
    @Test
    public void testCase5_timeIsAfterDepartureTime() {
        // Setup
        // 1. Create airline AL14 with airports AP104, AP105
        city1 = new AirlineReservationSystem.City();
        city1.setId("C1");
        city1.setName("City1");
        
        city2 = new AirlineReservationSystem.City();
        city2.setId("C2");
        city2.setName("City2");
        
        airport1 = new AirlineReservationSystem.Airport();
        airport1.setId("AP104");
        airport1.setName("Airport104");
        airport1.setCity(city1);
        
        airport2 = new AirlineReservationSystem.Airport();
        airport2.setId("AP105");
        airport2.setName("Airport105");
        airport2.setCity(city2);
        
        // 2. Create flight F303 under AL14
        flight = new AirlineReservationSystem.Flight();
        flight.setDepartureAirport(airport1);
        flight.setArrivalAirport(airport2);
        flight.setDepartureTime(LocalDateTime.of(2025, 10, 5, 18, 0, 0));
        flight.setArrivalTime(LocalDateTime.of(2025, 10, 6, 2, 0, 0));
        flight.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        boolean published = service.publishFlight(flight);
        assertTrue("Flight should be published successfully", published);
        
        // 3. Customer CUD
        customer = new AirlineReservationSystem.Customer();
        customer.setId("CUD");
        customer.setName("Customer D");
        
        // 4. Passenger P5 "Ruby"
        List<String> passengers = Arrays.asList("Ruby");
        
        // Test - current time = 2025-10-06 09:00:00 (after departure)
        // We need to mock the current time to be after departure
        // Since we can't mock LocalDateTime.now(), we'll create a flight with past departure time
        AirlineReservationSystem.Flight pastFlight = new AirlineReservationSystem.Flight();
        pastFlight.setDepartureAirport(airport1);
        pastFlight.setArrivalAirport(airport2);
        pastFlight.setDepartureTime(LocalDateTime.of(2024, 10, 5, 18, 0, 0)); // Past date
        pastFlight.setArrivalTime(LocalDateTime.of(2024, 10, 6, 2, 0, 0));
        pastFlight.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        boolean pastPublished = service.publishFlight(pastFlight);
        assertTrue("Past flight should be published", pastPublished);
        
        // Expected Output: False (current time after departure)
        boolean result = service.createBooking(customer, pastFlight.getId(), passengers);
        assertFalse("Booking should fail because current time is after departure", result);
    }
}