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
    public void testCase1_confirmPendingReservation() throws Exception {
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
        
        Booking bk401 = new Booking(cu16);
        bk401.getReservations().add(r401);
        cu16.getBookings().add(bk401);
        f401.getReservations().add(r401);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = cu16.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Pending reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, r401.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws Exception {
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
        
        Booking bk402 = new Booking(cu17);
        bk402.getReservations().add(r402);
        cu17.getBookings().add(bk402);
        f402.getReservations().add(r402);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = cu17.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Confirmed reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, r402.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws Exception {
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
        
        Passenger p11 = new Passenger();
        p11.setName("P11");
        
        Reservation r403 = new Reservation();
        r403.setId("R403");
        r403.setStatus(ReservationStatus.PENDING);
        r403.setPassenger(p11);
        r403.setFlight(f403);
        
        Customer customer = new Customer();
        Booking booking = new Booking(customer);
        booking.getReservations().add(r403);
        customer.getBookings().add(booking);
        f403.getReservations().add(r403);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail when flight has already departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, r403.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws Exception {
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
        
        Passenger p12 = new Passenger();
        p12.setName("P12");
        
        Reservation r404 = new Reservation();
        r404.setId("R404");
        r404.setStatus(ReservationStatus.CONFIRMED);
        r404.setPassenger(p12);
        r404.setFlight(f404);
        
        Customer customer = new Customer();
        Booking booking = new Customer.Booking(customer);
        booking.getReservations().add(r404);
        customer.getBookings().add(booking);
        f404.getReservations().add(r404);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail when flight is closed for booking", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, r404.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws Exception {
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
        
        // Customer CU20 with reservation R405
        Customer cu20 = new Customer();
        Passenger p13 = new Passenger();
        p13.setName("P13");
        
        Reservation r405 = new Reservation();
        r405.setId("R405");
        r405.setStatus(ReservationStatus.PENDING);
        r405.setPassenger(p13);
        r405.setFlight(f405);
        
        Booking bk405 = new Booking(cu20);
        bk405.getReservations().add(r405);
        cu20.getBookings().add(bk405);
        f405.getReservations().add(r405);
        
        // Customer CU21 with reservation R406
        Customer cu21 = new Customer();
        Passenger p14 = new Passenger();
        p14.setName("P14");
        
        Reservation r406 = new Reservation();
        r406.setId("R406");
        r406.setStatus(ReservationStatus.PENDING);
        r406.setPassenger(p14);
        r406.setFlight(f405);
        
        Booking bk406 = new Booking(cu21);
        bk406.getReservations().add(r406);
        cu21.getBookings().add(bk406);
        f405.getReservations().add(r406);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - CU20 tries to confirm R406 (which belongs to CU21)
        boolean result = cu20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation identifier", result);
        assertEquals("R406 status should remain PENDING", ReservationStatus.PENDING, r406.getStatus());
    }
}