import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        Airport ap160 = new Airport();
        ap160.setId("AP160");
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        
        Flight f401 = new Flight();
        f401.setId("F401");
        f401.setDepartureAirport(ap160);
        f401.setArrialAirport(ap161);
        f401.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        f401.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        f401.setOpenForBooking(true);
        
        Customer cu16 = new Customer();
        Passenger p9 = new Passenger();
        p9.setName("P9");
        
        Reservation r401 = new Reservation();
        r401.setId("R401");
        r401.setStatus(ReservationStatus.PENDING);
        r401.setPassenger(p9);
        r401.setFlight(f401);
        
        Booking bk401 = new Booking();
        bk401.setCustomer(cu16);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r401);
        bk401.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk401);
        cu16.setBookings(bookings);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r401);
        f401.setReservations(flightReservations);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = cu16.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Pending reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, r401.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        
        Flight f402 = new Flight();
        f402.setId("F402");
        f402.setDepartureAirport(ap170);
        f402.setArrialAirport(ap171);
        f402.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        f402.setOpenForBooking(true);
        
        Customer cu17 = new Customer();
        Passenger p10 = new Passenger();
        p10.setName("P10");
        
        Reservation r402 = new Reservation();
        r402.setId("R402");
        r402.setStatus(ReservationStatus.CONFIRMED);
        r402.setPassenger(p10);
        r402.setFlight(f402);
        
        Booking bk402 = new Booking();
        bk402.setCustomer(cu17);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r402);
        bk402.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk402);
        cu17.setBookings(bookings);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r402);
        f402.setReservations(flightReservations);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = cu17.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Confirmed reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELLED", ReservationStatus.CANCELLED, r402.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        
        Flight f403 = new Flight();
        f403.setId("F403");
        f403.setDepartureAirport(ap180);
        f403.setArrialAirport(ap181);
        f403.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        f403.setOpenForBooking(true);
        
        Customer cu18 = new Customer();
        Passenger p11 = new Passenger();
        p11.setName("P11");
        
        Reservation r403 = new Reservation();
        r403.setId("R403");
        r403.setStatus(ReservationStatus.PENDING);
        r403.setPassenger(p11);
        r403.setFlight(f403);
        
        Booking bk403 = new Booking();
        bk403.setCustomer(cu18);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r403);
        bk403.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk403);
        cu18.setBookings(bookings);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r403);
        f403.setReservations(flightReservations);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = cu18.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail when flight has already departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, r403.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        
        Flight f404 = new Flight();
        f404.setId("F404");
        f404.setDepartureAirport(ap190);
        f404.setArrialAirport(ap191);
        f404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        f404.setOpenForBooking(false);
        
        Customer cu19 = new Customer();
        Passenger p12 = new Passenger();
        p12.setName("P12");
        
        Reservation r404 = new Reservation();
        r404.setId("R404");
        r404.setStatus(ReservationStatus.CONFIRMED);
        r404.setPassenger(p12);
        r404.setFlight(f404);
        
        Booking bk404 = new Booking();
        bk404.setCustomer(cu19);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r404);
        bk404.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk404);
        cu19.setBookings(bookings);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r404);
        f404.setReservations(flightReservations);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = cu19.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail when flight is closed for booking", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, r404.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        
        Flight f405 = new Flight();
        f405.setId("F405");
        f405.setDepartureAirport(ap200);
        f405.setArrialAirport(ap201);
        f405.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        f405.setOpenForBooking(true);
        
        Customer cu20 = new Customer();
        Passenger p13 = new Passenger();
        p13.setName("P13");
        
        Reservation r405 = new Reservation();
        r405.setId("R405");
        r405.setStatus(ReservationStatus.PENDING);
        r405.setPassenger(p13);
        r405.setFlight(f405);
        
        Booking bk405 = new Booking();
        bk405.setCustomer(cu20);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r405);
        bk405.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk405);
        cu20.setBookings(bookings);
        
        Customer cu21 = new Customer();
        Passenger p14 = new Passenger();
        p14.setName("P14");
        
        Reservation r406 = new Reservation();
        r406.setId("R406");
        r406.setStatus(ReservationStatus.PENDING);
        r406.setPassenger(p14);
        r406.setFlight(f405);
        
        Booking bk406 = new Booking();
        bk406.setCustomer(cu21);
        List<Reservation> reservations2 = new ArrayList<>();
        reservations2.add(r406);
        bk406.setReservations(reservations2);
        
        List<Booking> bookings2 = new ArrayList<>();
        bookings2.add(bk406);
        cu21.setBookings(bookings2);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r405);
        flightReservations.add(r406);
        f405.setReservations(flightReservations);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute
        boolean result = cu20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation ID", result);
        assertEquals("Reservation R406 status should remain PENDING", ReservationStatus.PENDING, r406.getStatus());
    }
}