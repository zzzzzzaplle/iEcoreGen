import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private Airline airlineAL11;
    private Airport airportAP100;
    private Airport airportAP101;
    private Flight flightF300;
    private Customer customerCUA;
    
    private Airline airlineAL12;
    private Airport airportAP102;
    private Airport airportAP103;
    private Flight flightF301;
    private Customer customerCUB;
    
    private Airline airlineAL13;
    private Airport airportAP104;
    private Airport airportAP105;
    private Flight flightF302;
    private Customer customerCUC;
    private Booking existingBooking;
    
    private Airline airlineAL14;
    private Airport airportAP106;
    private Airport airportAP107;
    private Flight flightF303;
    private Customer customerCUD;
    
    @Before
    public void setUp() {
        // Clear previous bookings
        BookingRepository.getAllBookings().clear();
        
        // Setup for Test Case 1
        airlineAL11 = new Airline("AL11", "Airline 11");
        airportAP100 = new Airport("AP100", "Airport 100");
        airportAP101 = new Airport("AP101", "Airport 101");
        
        flightF300 = new Flight("F300");
        flightF300.setDepartureAirport(airportAP100);
        flightF300.setArrivalAirport(airportAP101);
        flightF300.setDepartureTime(LocalDateTime.of(2025, 10, 5, 8, 0, 0));
        flightF300.setArrivalTime(LocalDateTime.of(2025, 10, 5, 12, 0, 0));
        flightF300.setStatus(FlightStatus.OPEN);
        airlineAL11.addFlight(flightF300);
        
        customerCUA = new Customer("CUA", "Customer A");
        
        // Setup for Test Case 2
        airlineAL12 = new Airline("AL12", "Airline 12");
        airportAP102 = new Airport("AP102", "Airport 102");
        airportAP103 = new Airport("AP103", "Airport 103");
        
        flightF301 = new Flight("F301");
        flightF301.setDepartureAirport(airportAP102);
        flightF301.setArrivalAirport(airportAP103);
        flightF301.setDepartureTime(LocalDateTime.of(2025, 10, 5, 8, 0, 0));
        flightF301.setArrivalTime(LocalDateTime.of(2025, 10, 5, 10, 0, 0));
        flightF301.setStatus(FlightStatus.OPEN);
        airlineAL12.addFlight(flightF301);
        
        customerCUB = new Customer("CUB", "Customer B");
        
        // Setup for Test Case 3
        airlineAL13 = new Airline("AL13", "Airline 13");
        airportAP104 = new Airport("AP104", "Airport 104");
        airportAP105 = new Airport("AP105", "Airport 105");
        
        flightF302 = new Flight("F302");
        flightF302.setDepartureAirport(airportAP104);
        flightF302.setArrivalAirport(airportAP105);
        flightF302.setDepartureTime(LocalDateTime.of(2025, 10, 5, 18, 0, 0));
        flightF302.setArrivalTime(LocalDateTime.of(2025, 10, 6, 2, 0, 0));
        flightF302.setStatus(FlightStatus.OPEN);
        airlineAL13.addFlight(flightF302);
        
        customerCUC = new Customer("CUC", "Customer C");
        
        // Create pre-existing booking for Test Case 3
        existingBooking = new Booking(customerCUC, flightF302);
        Reservation existingReservation = new Reservation(flightF302, "Jucy");
        existingBooking.addReservation(existingReservation);
        BookingRepository.addBooking(existingBooking);
        
        // Setup for Test Cases 4 and 5
        airlineAL14 = new Airline("AL14", "Airline 14");
        airportAP106 = new Airport("AP106", "Airport 106");
        airportAP107 = new Airport("AP107", "Airport 107");
        
        flightF303 = new Flight("F303");
        flightF303.setDepartureAirport(airportAP106);
        flightF303.setArrivalAirport(airportAP107);
        flightF303.setDepartureTime(LocalDateTime.of(2025, 10, 5, 18, 0, 0));
        flightF303.setArrivalTime(LocalDateTime.of(2025, 10, 6, 2, 0, 0));
        flightF303.setStatus(FlightStatus.OPEN);
        airlineAL14.addFlight(flightF303);
        
        customerCUD = new Customer("CUD", "Customer D");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() {
        // Set current time to 2025-10-01 09:00:00
        // This would normally require mocking, but for this test we'll rely on the flight departure time being in the future
        
        // Create passenger list
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute booking creation
        boolean result = FlightService.createBooking(customerCUA, flightF300, passengerNames);
        
        // Verify result is true
        assertTrue("Booking should succeed for two new passengers", result);
        
        // Verify there are two reservations for passengers P1 and P2
        List<Booking> allBookings = BookingRepository.getAllBookings();
        assertEquals("Should have one booking", 2, allBookings.size()); // 1 from setup + 1 new
        
        // Find the new booking and verify reservations
        Booking newBooking = null;
        for (Booking booking : allBookings) {
            if (booking.getCustomer().getId().equals("CUA")) {
                newBooking = booking;
                break;
            }
        }
        assertNotNull("New booking should exist", newBooking);
        assertEquals("Should have 2 reservations", 2, newBooking.getReservations().size());
        
        // Verify passenger names
        List<String> actualNames = new ArrayList<>();
        for (Reservation reservation : newBooking.getReservations()) {
            actualNames.add(reservation.getPassengerName());
        }
        assertTrue("Should contain Peter", actualNames.contains("Peter"));
        assertTrue("Should contain Beck", actualNames.contains("Beck"));
        
        // Verify all reservations are in PENDING status
        for (Reservation reservation : newBooking.getReservations()) {
            assertEquals("Reservation should be PENDING", ReservationStatus.PENDING, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() {
        // Set current time to 2025-10-01 09:00:00
        
        // Create passenger list with duplicate names
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Execute booking creation
        boolean result = FlightService.createBooking(customerCUB, flightF301, passengerNames);
        
        // Verify result is false due to duplicate passenger
        assertFalse("Booking should fail due to duplicate passenger in same request", result);
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() {
        // Set current time to 2025-10-01 09:00:00
        
        // Create passenger list with passenger who already has a booking
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Execute booking creation
        boolean result = FlightService.createBooking(customerCUC, flightF302, passengerNames);
        
        // Verify result is false due to passenger already booked
        assertFalse("Booking should fail due to passenger already booked on this flight", result);
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() {
        // Set current time to 2025-10-01 09:00:00
        
        // Set flight status to CLOSED
        flightF303.setStatus(FlightStatus.CLOSED);
        
        // Create passenger list
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute booking creation
        boolean result = FlightService.createBooking(customerCUD, flightF303, passengerNames);
        
        // Verify result is false due to flight being closed
        assertFalse("Booking should fail due to flight being closed", result);
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTime() {
        // For this test, we need to simulate that current time is after departure time
        // Since we can't easily mock LocalDateTime.now(), we'll use a flight with departure time in the past
        
        // Create a flight with departure time in the past relative to when the test runs
        Flight pastFlight = new Flight("F304");
        pastFlight.setDepartureAirport(airportAP106);
        pastFlight.setArrivalAirport(airportAP107);
        pastFlight.setDepartureTime(LocalDateTime.now().minusHours(1)); // Departed 1 hour ago
        pastFlight.setArrivalTime(LocalDateTime.now().plusHours(2));
        pastFlight.setStatus(FlightStatus.OPEN);
        
        // Create passenger list
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute booking creation
        boolean result = FlightService.createBooking(customerCUD, pastFlight, passengerNames);
        
        // Verify result is false due to flight already departed
        assertFalse("Booking should fail due to flight already departed", result);
    }
}