import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CR5Test {
    private Flight flightF501;
    private Flight flightF502;
    private Flight flightF503;
    private Flight flightF504;
    private Booking bookingF501;
    private Booking bookingF502;
    private Booking bookingF503;
    private Booking bookingF504;
    
    @Before
    public void setUp() {
        // Create flights
        flightF501 = new Flight();
        flightF501.setId("F501");
        flightF501.setDepartureAirportId("AP501");
        flightF501.setArrivalAirportId("AP502");
        flightF501.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF501.setArrivalTime(LocalDateTime.now().plusDays(2));
        flightF501.setOpenForBooking(true);
        
        flightF502 = new Flight();
        flightF502.setId("F502");
        flightF502.setDepartureAirportId("AP503");
        flightF502.setArrivalAirportId("AP504");
        flightF502.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF502.setArrivalTime(LocalDateTime.now().plusDays(2));
        flightF502.setOpenForBooking(true);
        
        flightF503 = new Flight();
        flightF503.setId("F503");
        flightF503.setDepartureAirportId("AP505");
        flightF503.setArrivalAirportId("AP506");
        flightF503.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF503.setArrivalTime(LocalDateTime.now().plusDays(2));
        flightF503.setOpenForBooking(false);
        
        flightF504 = new Flight();
        flightF504.setId("F504");
        flightF504.setDepartureAirportId("AP507");
        flightF504.setArrivalAirportId("AP508");
        flightF504.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF504.setArrivalTime(LocalDateTime.now().plusDays(2));
        flightF504.setOpenForBooking(true);
        
        // Create bookings and reservations for F501 (3 confirmed)
        bookingF501 = new Booking();
        bookingF501.setFlightId("F501");
        
        Reservation r501_1 = new Reservation();
        r501_1.setId("R501-1");
        r501_1.setFlightId("F501");
        r501_1.setPassengerName("Passenger1");
        r501_1.setConfirmed(true);
        
        Reservation r501_2 = new Reservation();
        r501_2.setId("R501-2");
        r501_2.setFlightId("F501");
        r501_2.setPassengerName("Passenger2");
        r501_2.setConfirmed(true);
        
        Reservation r501_3 = new Reservation();
        r501_3.setId("R501-3");
        r501_3.setFlightId("F501");
        r501_3.setPassengerName("Passenger3");
        r501_3.setConfirmed(true);
        
        List<Reservation> reservationsF501 = new ArrayList<>();
        reservationsF501.add(r501_1);
        reservationsF501.add(r501_2);
        reservationsF501.add(r501_3);
        bookingF501.setReservations(reservationsF501);
        
        // Create bookings and reservations for F502 (2 pending)
        bookingF502 = new Booking();
        bookingF502.setFlightId("F502");
        
        Reservation r502_1 = new Reservation();
        r502_1.setId("R502-1");
        r502_1.setFlightId("F502");
        r502_1.setPassengerName("Passenger4");
        r502_1.setConfirmed(false);
        
        Reservation r502_2 = new Reservation();
        r502_2.setId("R502-2");
        r502_2.setFlightId("F502");
        r502_2.setPassengerName("Passenger5");
        r502_2.setConfirmed(false);
        
        List<Reservation> reservationsF502 = new ArrayList<>();
        reservationsF502.add(r502_1);
        reservationsF502.add(r502_2);
        bookingF502.setReservations(reservationsF502);
        
        // Create bookings and reservations for F503 (1 confirmed, but flight closed)
        bookingF503 = new Booking();
        bookingF503.setFlightId("F503");
        
        Reservation r503_1 = new Reservation();
        r503_1.setId("R503-1");
        r503_1.setFlightId("F503");
        r503_1.setPassengerName("Passenger6");
        r503_1.setConfirmed(true);
        
        List<Reservation> reservationsF503 = new ArrayList<>();
        reservationsF503.add(r503_1);
        bookingF503.setReservations(reservationsF503);
        
        // Create bookings and reservations for F504 (mixed statuses)
        bookingF504 = new Booking();
        bookingF504.setFlightId("F504");
        
        Reservation r504_A = new Reservation();
        r504_A.setId("R504-A");
        r504_A.setFlightId("F504");
        r504_A.setPassengerName("Passenger7");
        r504_A.setConfirmed(true);
        
        Reservation r504_B = new Reservation();
        r504_B.setId("R504-B");
        r504_B.setFlightId("F504");
        r504_B.setPassengerName("Passenger8");
        r504_B.setConfirmed(true);
        
        Reservation r504_C = new Reservation();
        r504_C.setId("R504-C");
        r504_C.setFlightId("F504");
        r504_C.setPassengerName("Passenger9");
        r504_C.setConfirmed(false); // CANCELED
        
        Reservation r504_D = new Reservation();
        r504_D.setId("R504-D");
        r504_D.setFlightId("F504");
        r504_D.setPassengerName("Passenger10");
        r504_D.setConfirmed(false); // PENDING
        
        List<Reservation> reservationsF504 = new ArrayList<>();
        reservationsF504.add(r504_A);
        reservationsF504.add(r504_B);
        reservationsF504.add(r504_C);
        reservationsF504.add(r504_D);
        bookingF504.setReservations(reservationsF504);
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Mock the getBookings method to return our test booking
        flightF501.setBookings(List.of(bookingF501));
        
        // Execute: Retrieve confirmed reservations for flight F501
        List<Reservation> result = flightF501.getConfirmedReservations();
        
        // Verify: Should return 3 confirmed reservations
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1", result.stream().anyMatch(r -> r.getId().equals("R501-1")));
        assertTrue("Should contain R501-2", result.stream().anyMatch(r -> r.getId().equals("R501-2")));
        assertTrue("Should contain R501-3", result.stream().anyMatch(r -> r.getId().equals("R501-3")));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Mock the getBookings method to return our test booking
        flightF502.setBookings(List.of(bookingF502));
        
        // Execute: Retrieve confirmed reservations for flight F502
        List<Reservation> result = flightF502.getConfirmedReservations();
        
        // Verify: Should return empty list since all reservations are pending
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Mock the getBookings method to return our test booking
        flightF503.setBookings(List.of(bookingF503));
        
        // Execute: Retrieve confirmed reservations for flight F503
        List<Reservation> result = flightF503.getConfirmedReservations();
        
        // Verify: Should return empty list even though there's a confirmed reservation (flight is closed)
        assertTrue("Should return empty list when flight is closed", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Create a flight that doesn't exist in our test data
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setDepartureAirportId("AP999");
        unknownFlight.setArrivalAirportId("AP1000");
        unknownFlight.setDepartureTime(LocalDateTime.now().plusDays(1));
        unknownFlight.setArrivalTime(LocalDateTime.now().plusDays(2));
        unknownFlight.setOpenForBooking(true);
        
        // Execute: Retrieve confirmed reservations for unknown flight FX999
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify: Should return empty list for unknown flight
        assertTrue("Should return empty list for unknown flight", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Mock the getBookings method to return our test booking
        flightF504.setBookings(List.of(bookingF504));
        
        // Execute: Retrieve confirmed reservations for flight F504
        List<Reservation> result = flightF504.getConfirmedReservations();
        
        // Verify: Should return only the 2 confirmed reservations (R504-A, R504-B)
        assertEquals("Should return 2 confirmed reservations", 2, result.size());
        assertTrue("Should contain R504-A", result.stream().anyMatch(r -> r.getId().equals("R504-A")));
        assertTrue("Should contain R504-B", result.stream().anyMatch(r -> r.getId().equals("R504-B")));
        assertFalse("Should not contain R504-C (canceled)", result.stream().anyMatch(r -> r.getId().equals("R504-C")));
        assertFalse("Should not contain R504-D (pending)", result.stream().anyMatch(r -> r.getId().equals("R504-D")));
    }
    
    // Helper class to mock the Flight.getBookings() method
    private static class FlightWithMockedBookings extends Flight {
        private List<Booking> bookings;
        
        public void setBookings(List<Booking> bookings) {
            this.bookings = bookings;
        }
        
        @Override
        public List<Booking> getBookings() {
            return bookings != null ? bookings : new ArrayList<>();
        }
    }
    
    // Helper method to create flights with mocked bookings capability
    private Flight createFlightWithMockedBookings() {
        return new FlightWithMockedBookings();
    }
    
    // Update the setUp method to use our helper class
    static {
        // Replace Flight instances with our mocked version
        CR5Test testInstance = new CR5Test();
        testInstance.flightF501 = testInstance.createFlightWithMockedBookings();
        testInstance.flightF502 = testInstance.createFlightWithMockedBookings();
        testInstance.flightF503 = testInstance.createFlightWithMockedBookings();
        testInstance.flightF504 = testInstance.createFlightWithMockedBookings();
    }
}