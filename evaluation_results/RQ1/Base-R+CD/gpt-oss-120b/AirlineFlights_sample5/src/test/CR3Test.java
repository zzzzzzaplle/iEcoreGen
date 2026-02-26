import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap160 = new Airport();
        ap160.setId("AP160");
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        
        Flight f401 = new Flight();
        f401.setId("F401");
        f401.setDepartureAirport(ap160);
        f401.setArrivalAirport(ap161);
        f401.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        f401.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        f401.setOpenForBooking(true);
        
        Passenger p9 = new Passenger();
        p9.setName("P9");
        
        Reservation r401 = new Reservation();
        r401.setId("R401");
        r401.setStatus(ReservationStatus.PENDING);
        r401.setPassenger(p9);
        r401.setFlight(f401);
        
        Booking bk401 = new Booking();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r401);
        bk401.setReservations(reservations);
        
        Customer cu16 = new Customer();
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk401);
        cu16.setBookings(bookings);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = cu16.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Pending reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, r401.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        
        Flight f402 = new Flight();
        f402.setId("F402");
        f402.setDepartureAirport(ap170);
        f402.setArrivalAirport(ap171);
        f402.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        f402.setArrivalTime(dateFormat.parse("2025-12-15 19:00:00"));
        f402.setOpenForBooking(true);
        
        Passenger p10 = new Passenger();
        p10.setName("P10");
        
        Reservation r402 = new Reservation();
        r402.setId("R402");
        r402.setStatus(ReservationStatus.CONFIRMED);
        r402.setPassenger(p10);
        r402.setFlight(f402);
        
        Booking bk402 = new Booking();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r402);
        bk402.setReservations(reservations);
        
        Customer cu17 = new Customer();
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk402);
        cu17.setBookings(bookings);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = cu17.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Confirmed reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELLED", ReservationStatus.CANCELLED, r402.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        
        Flight f403 = new Flight();
        f403.setId("F403");
        f403.setDepartureAirport(ap180);
        f403.setArrivalAirport(ap181);
        f403.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        f403.setArrivalTime(dateFormat.parse("2025-01-05 10:00:00"));
        f403.setOpenForBooking(true);
        
        Passenger p11 = new Passenger();
        p11.setName("P11");
        
        Reservation r403 = new Reservation();
        r403.setId("R403");
        r403.setStatus(ReservationStatus.PENDING);
        r403.setPassenger(p11);
        r403.setFlight(f403);
        
        Booking bk403 = new Booking();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r403);
        bk403.setReservations(reservations);
        
        Customer customer = new Customer();
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk403);
        customer.setBookings(bookings);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail when flight has already departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, r403.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        
        Flight f404 = new Flight();
        f404.setId("F404");
        f404.setDepartureAirport(ap190);
        f404.setArrivalAirport(ap191);
        f404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        f404.setArrivalTime(dateFormat.parse("2025-02-01 13:00:00"));
        f404.setOpenForBooking(false);
        
        Passenger p12 = new Passenger();
        p12.setName("P12");
        
        Reservation r404 = new Reservation();
        r404.setId("R404");
        r404.setStatus(ReservationStatus.CONFIRMED);
        r404.setPassenger(p12);
        r404.setFlight(f404);
        
        Booking bk404 = new Booking();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r404);
        bk404.setReservations(reservations);
        
        Customer customer = new Customer();
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk404);
        customer.setBookings(bookings);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail when flight is closed for booking", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, r404.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        
        Flight f405 = new Flight();
        f405.setId("F405");
        f405.setDepartureAirport(ap200);
        f405.setArrivalAirport(ap201);
        f405.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        f405.setArrivalTime(dateFormat.parse("2025-03-10 14:00:00"));
        f405.setOpenForBooking(true);
        
        // Customer CU20 setup
        Passenger p13 = new Passenger();
        p13.setName("P13");
        
        Reservation r405 = new Reservation();
        r405.setId("R405");
        r405.setStatus(ReservationStatus.PENDING);
        r405.setPassenger(p13);
        r405.setFlight(f405);
        
        Booking bk405 = new Booking();
        List<Reservation> reservationsCu20 = new ArrayList<>();
        reservationsCu20.add(r405);
        bk405.setReservations(reservationsCu20);
        
        Customer cu20 = new Customer();
        List<Booking> bookingsCu20 = new ArrayList<>();
        bookingsCu20.add(bk405);
        cu20.setBookings(bookingsCu20);
        
        // Customer CU21 setup (reservation R406 belongs to CU21, not CU20)
        Passenger p14 = new Passenger();
        p14.setName("P14");
        
        Reservation r406 = new Reservation();
        r406.setId("R406");
        r406.setStatus(ReservationStatus.PENDING);
        r406.setPassenger(p14);
        r406.setFlight(f405);
        
        Booking bk406 = new Booking();
        List<Reservation> reservationsCu21 = new ArrayList<>();
        reservationsCu21.add(r406);
        bk406.setReservations(reservationsCu21);
        
        Customer cu21 = new Customer();
        List<Booking> bookingsCu21 = new ArrayList<>();
        bookingsCu21.add(bk406);
        cu21.setBookings(bookingsCu21);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - CU20 tries to confirm R406 (which belongs to CU21)
        boolean result = cu20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation identifier", result);
    }
}