import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    
    private Airline airline;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Flight flight;
    private Customer customer;
    private Date currentTime;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        airline = new Airline();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        flight = new Flight();
        customer = new Customer();
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() throws ParseException {
        // Setup
        currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create airline AL11
        airline = new Airline();
        
        // Create airports AP100 (departure) and AP101 (arrival)
        departureAirport = new Airport();
        departureAirport.setId("AP100");
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        
        // Create flight F300 under AL11
        flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Instantiate customer CUA
        customer = new Customer();
        
        // Create passenger list
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertTrue(result);
        assertEquals(1, customer.getBookings().size());
        assertEquals(2, customer.getBookings().get(0).getReservations().size());
        
        List<Reservation> reservations = customer.getBookings().get(0).getReservations();
        boolean foundPeter = false;
        boolean foundBeck = false;
        
        for (Reservation reservation : reservations) {
            assertEquals(ReservationStatus.PENDING, reservation.getStatus());
            if ("Peter".equals(reservation.getPassenger().getName())) {
                foundPeter = true;
            } else if ("Beck".equals(reservation.getPassenger().getName())) {
                foundBeck = true;
            }
        }
        
        assertTrue(foundPeter);
        assertTrue(foundBeck);
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws ParseException {
        // Setup
        currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create airline AL12
        airline = new Airline();
        
        // Create airports AP102 (dep) and AP103 (arr)
        departureAirport = new Airport();
        departureAirport.setId("AP102");
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        
        // Create flight F301 under AL12
        flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Customer CUB
        customer = new Customer();
        
        // Create passenger list with duplicate
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse(result);
        assertTrue(customer.getBookings().isEmpty());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() throws ParseException {
        // Setup
        currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create airline AL13 with airports AP104, AP105
        airline = new Airline();
        
        // Create airports
        departureAirport = new Airport();
        departureAirport.setId("AP104");
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        // Create flight F302
        flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Passenger P4 "Jucy"
        // Customer CUC
        customer = new Customer();
        
        // Pre-existing booking BK-OLD containing reservation R302-A for passenger P4 "Jucy" on F302 (status = PENDING)
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        
        Passenger passenger = new Passenger();
        passenger.setName("Jucy");
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setPassenger(passenger);
        existingReservation.setFlight(flight);
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        existingBooking.getReservations().add(existingReservation);
        flight.getReservations().add(existingReservation);
        customer.getBookings().add(existingBooking);
        
        // Create passenger list
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        // Note: Based on code analysis, the current implementation does not check for existing reservations
        // for the same passenger on the same flight. The test expectation is False, but the implementation
        // would actually return True. Since we must adhere strictly to test specifications, we'll expect False.
        assertFalse(result);
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() throws ParseException {
        // Setup
        currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create airline AL14 with airports AP106, AP107
        airline = new Airline();
        
        // Create airports AP106, AP107
        departureAirport = new Airport();
        departureAirport.setId("AP106");
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        // Create flight F303 under AL14
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // it is closed for booking
        
        airline.addFlight(flight);
        
        // Customer CUD
        customer = new Customer();
        
        // Passenger P5 "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse(result);
        assertTrue(customer.getBookings().isEmpty());
    }
    
    @Test
    public void testCase5_theTimeIsAfterTheDepartureTime() throws ParseException {
        // Setup
        currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        
        // Create airline AL14 with airports AP106, AP107
        airline = new Airline();
        
        // Create airports AP106, AP107
        departureAirport = new Airport();
        departureAirport.setId("AP106");
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        // Create flight F303 under AL14
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00")); // Earlier than current time
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Customer CUD
        customer = new Customer();
        
        // Passenger P5 "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse(result);
        assertTrue(customer.getBookings().isEmpty());
    }
}