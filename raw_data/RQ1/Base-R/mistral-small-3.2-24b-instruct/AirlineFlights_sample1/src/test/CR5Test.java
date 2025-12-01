import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Flight flightF501;
    private Flight flightF502;
    private Flight flightF503;
    private Flight flightF504;
    private Flight flightF505;
    private Flight flightFX999;
    
    private Airport departureAirport;
    private Airport arrivalAirport;
    
    private List<Booking> bookings;
    
    @Before
    public void setUp() {
        // Create airports for testing
        departureAirport = new Airport("DEP001", new ArrayList<>());
        arrivalAirport = new Airport("ARR001", new ArrayList<>());
        
        // Create flights with future dates (to ensure they're valid for booking)
        LocalDateTime futureDeparture = LocalDateTime.now().plusDays(1);
        LocalDateTime futureArrival = LocalDateTime.now().plusDays(2);
        
        flightF501 = new Flight("F501", departureAirport, arrivalAirport, futureDeparture, futureArrival);
        flightF502 = new Flight("F502", departureAirport, arrivalAirport, futureDeparture, futureArrival);
        flightF503 = new Flight("F503", departureAirport, arrivalAirport, futureDeparture, futureArrival);
        flightF504 = new Flight("F504", departureAirport, arrivalAirport, futureDeparture, futureArrival);
        flightF505 = new Flight("F505", departureAirport, arrivalAirport, futureDeparture, futureArrival);
        
        // Initialize bookings list
        bookings = new ArrayList<>();
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Flight F501 with three confirmed reservations
        flightF501.setOpenForBooking(true);
        
        // Create three confirmed reservations for flight F501
        Reservation r5011 = new Reservation("R501-1", flightF501, "Passenger1");
        r5011.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r5012 = new Reservation("R501-2", flightF501, "Passenger2");
        r5012.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r5013 = new Reservation("R501-3", flightF501, "Passenger3");
        r5013.setStatus(ReservationStatus.CONFIRMED);
        
        // Create booking containing the reservations
        Booking booking = new Booking("B501", flightF501);
        booking.getReservations().add(r5011);
        booking.getReservations().add(r5012);
        booking.getReservations().add(r5013);
        
        // Add booking to flight's bookings list (using reflection to simulate getBookings() implementation)
        bookings.add(booking);
        
        // Mock the getBookings method to return our test bookings
        Flight testFlight = new Flight("F501", departureAirport, arrivalAirport, 
                                     LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)) {
            @Override
            public List<Booking> getBookings() {
                return bookings;
            }
        };
        testFlight.setOpenForBooking(true);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = testFlight.getConfirmedReservations();
        
        // Verify: Should return all three confirmed reservations
        assertEquals(3, result.size());
        assertTrue(result.contains(r5011));
        assertTrue(result.contains(r5012));
        assertTrue(result.contains(r5013));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Flight F502 with two pending reservations
        flightF502.setOpenForBooking(true);
        
        // Create two pending reservations for flight F502
        Reservation r5021 = new Reservation("R502-1", flightF502, "Passenger1");
        r5021.setStatus(ReservationStatus.PENDING);
        
        Reservation r5022 = new Reservation("R502-2", flightF502, "Passenger2");
        r5022.setStatus(ReservationStatus.PENDING);
        
        // Create booking containing the reservations
        Booking booking = new Booking("B502", flightF502);
        booking.getReservations().add(r5021);
        booking.getReservations().add(r5022);
        
        // Add booking to flight's bookings list
        bookings.clear();
        bookings.add(booking);
        
        // Mock the getBookings method
        Flight testFlight = new Flight("F502", departureAirport, arrivalAirport, 
                                     LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)) {
            @Override
            public List<Booking> getBookings() {
                return bookings;
            }
        };
        testFlight.setOpenForBooking(true);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = testFlight.getConfirmedReservations();
        
        // Verify: Should return empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Flight F503 is closed with one confirmed reservation
        flightF503.setOpenForBooking(false);
        
        // Create one confirmed reservation for flight F503
        Reservation r5031 = new Reservation("R503-1", flightF503, "Passenger1");
        r5031.setStatus(ReservationStatus.CONFIRMED);
        
        // Create booking containing the reservation
        Booking booking = new Booking("B503", flightF503);
        booking.getReservations().add(r5031);
        
        // Add booking to flight's bookings list
        bookings.clear();
        bookings.add(booking);
        
        // Mock the getBookings method
        Flight testFlight = new Flight("F503", departureAirport, arrivalAirport, 
                                     LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)) {
            @Override
            public List<Booking> getBookings() {
                return bookings;
            }
        };
        testFlight.setOpenForBooking(false);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = testFlight.getConfirmedReservations();
        
        // Verify: Should return empty list even though there's a confirmed reservation
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup: Flight FX999 doesn't exist in the system
        // Create a flight that's not in our test setup
        Flight unknownFlight = new Flight("FX999", departureAirport, arrivalAirport, 
                                        LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        
        // Execute: Retrieve confirmed reservations for unknown flight
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify: Should return empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Flight F504 with mixed reservation statuses
        flightF504.setOpenForBooking(true);
        
        // Create reservations with different statuses
        Reservation r504A = new Reservation("R504-A", flightF504, "PassengerA");
        r504A.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r504B = new Reservation("R504-B", flightF504, "PassengerB");
        r504B.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r504C = new Reservation("R504-C", flightF504, "PassengerC");
        r504C.setStatus(ReservationStatus.CANCELED);
        
        Reservation r504D = new Reservation("R504-D", flightF504, "PassengerD");
        r504D.setStatus(ReservationStatus.PENDING);
        
        // Create booking containing all reservations
        Booking booking = new Booking("B504", flightF504);
        booking.getReservations().add(r504A);
        booking.getReservations().add(r504B);
        booking.getReservations().add(r504C);
        booking.getReservations().add(r504D);
        
        // Add booking to flight's bookings list
        bookings.clear();
        bookings.add(booking);
        
        // Mock the getBookings method
        Flight testFlight = new Flight("F504", departureAirport, arrivalAirport, 
                                     LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)) {
            @Override
            public List<Booking> getBookings() {
                return bookings;
            }
        };
        testFlight.setOpenForBooking(true);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = testFlight.getConfirmedReservations();
        
        // Verify: Should return only the two confirmed reservations (R504-A and R504-B)
        assertEquals(2, result.size());
        assertTrue(result.contains(r504A));
        assertTrue(result.contains(r504B));
        assertFalse(result.contains(r504C));
        assertFalse(result.contains(r504D));
    }
}