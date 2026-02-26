import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR3Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() throws Exception {
        // Setup
        Airline AL16 = new Airline();
        Airport AP160 = new Airport();
        Airport AP161 = new Airport();
        City CityAA = new City("CityAA");
        City CityAB = new City("CityAB");
        AP160.addCity(CityAA);
        AP161.addCity(CityAB);
        
        Flight F401 = new Flight();
        F401.setId("F401");
        F401.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        F401.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        F401.setDepartureAirport(AP160);
        F401.setArrialAirport(AP161);
        F401.setOpenForBooking(true);
        
        Customer CU16 = new Customer();
        Passenger P9 = new Passenger("P9");
        
        Booking BK401 = new Booking(CU16);
        Reservation R401 = new Reservation(F401, P9);
        R401.setStatus(ReservationStatus.PENDING);
        BK401.getReservations().add(R401);
        F401.getReservations().add(R401);
        CU16.getBookings().add(BK401);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = CU16.confirm(R401.getId(), currentTime);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, R401.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() throws Exception {
        // Setup
        Airline AL17 = new Airline();
        Airport AP170 = new Airport();
        Airport AP171 = new Airport();
        City CityAC = new City("CityAC");
        City CityAD = new City("CityAD");
        AP170.addCity(CityAC);
        AP171.addCity(CityAD);
        
        Flight F402 = new Flight();
        F402.setId("F402");
        F402.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        F402.setDepartureAirport(AP170);
        F402.setArrialAirport(AP171);
        F402.setOpenForBooking(true);
        
        Customer CU17 = new Customer();
        Passenger P10 = new Passenger("P10");
        
        Booking BK402 = new Booking(CU17);
        Reservation R402 = new Reservation(F402, P10);
        R402.setStatus(ReservationStatus.CONFIRMED);
        BK402.getReservations().add(R402);
        F402.getReservations().add(R402);
        CU17.getBookings().add(BK402);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = CU17.cancel(R402.getId(), currentTime);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, R402.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() throws Exception {
        // Setup
        Airline AL18 = new Airline();
        Airport AP180 = new Airport();
        Airport AP181 = new Airport();
        City CityAE = new City("CityAE");
        City CityAF = new City("CityAF");
        AP180.addCity(CityAE);
        AP181.addCity(CityAF);
        
        Flight F403 = new Flight();
        F403.setId("F403");
        F403.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        F403.setDepartureAirport(AP180);
        F403.setArrialAirport(AP181);
        F403.setOpenForBooking(true);
        
        Passenger P11 = new Passenger("P11");
        Reservation R403 = new Reservation(F403, P11);
        R403.setStatus(ReservationStatus.PENDING);
        F403.getReservations().add(R403);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute - Create a customer and attempt to confirm
        Customer customer = new Customer();
        Booking booking = new Booking(customer);
        booking.getReservations().add(R403);
        customer.getBookings().add(booking);
        
        boolean result = customer.confirm(R403.getId(), currentTime);
        
        // Verify
        assertFalse("Confirmation should fail when flight has departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, R403.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() throws Exception {
        // Setup
        Airline AL19 = new Airline();
        Airport AP190 = new Airport();
        Airport AP191 = new Airport();
        City CityAG = new City("CityAG");
        City CityAH = new City("CityAH");
        AP190.addCity(CityAG);
        AP191.addCity(CityAH);
        
        Flight F404 = new Flight();
        F404.setId("F404");
        F404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        F404.setDepartureAirport(AP190);
        F404.setArrialAirport(AP191);
        F404.setOpenForBooking(false);
        
        Passenger P12 = new Passenger("P12");
        Reservation R404 = new Reservation(F404, P12);
        R404.setStatus(ReservationStatus.CONFIRMED);
        F404.getReservations().add(R404);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute - Create a customer and attempt to cancel
        Customer customer = new Customer();
        Booking booking = new Booking(customer);
        booking.getReservations().add(R404);
        customer.getBookings().add(booking);
        
        boolean result = customer.cancel(R404.getId(), currentTime);
        
        // Verify
        assertFalse("Cancellation should fail when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, R404.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() throws Exception {
        // Setup
        Airline AL20 = new Airline();
        Airport AP200 = new Airport();
        Airport AP201 = new Airport();
        City CityAI = new City("CityAI");
        City CityAJ = new City("CityAJ");
        AP200.addCity(CityAI);
        AP201.addCity(CityAJ);
        
        Flight F405 = new Flight();
        F405.setId("F405");
        F405.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        F405.setDepartureAirport(AP200);
        F405.setArrialAirport(AP201);
        F405.setOpenForBooking(true);
        
        Customer CU20 = new Customer();
        Passenger P13 = new Passenger("P13");
        Reservation R405 = new Reservation(F405, P13);
        R405.setStatus(ReservationStatus.PENDING);
        F405.getReservations().add(R405);
        
        Customer CU21 = new Customer();
        Passenger P14 = new Passenger("P14");
        Reservation R406 = new Reservation(F405, P14);
        R406.setStatus(ReservationStatus.PENDING);
        F405.getReservations().add(R406);
        
        Booking booking20 = new Booking(CU20);
        booking20.getReservations().add(R405);
        CU20.getBookings().add(booking20);
        
        Booking booking21 = new Booking(CU21);
        booking21.getReservations().add(R406);
        CU21.getBookings().add(booking21);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - CU20 tries to confirm R406 (which belongs to CU21)
        boolean result = CU20.confirm(R406.getId(), currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation ID", result);
        assertEquals("R406 status should remain PENDING", ReservationStatus.PENDING, R406.getStatus());
    }
}