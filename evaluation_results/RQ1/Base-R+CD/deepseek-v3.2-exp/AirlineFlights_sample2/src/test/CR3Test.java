import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customer;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_confirmPendingReservation() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap160 = new Airport();
        City cityAA = new City();
        cityAA.setName("CityAA");
        ap160.addCity(cityAA);
        
        Airport ap161 = new Airport();
        City cityAB = new City();
        cityAB.setName("CityAB");
        ap161.addCity(cityAB);
        
        // Create flight F401
        Flight flight401 = new Flight();
        flight401.setId("F401");
        flight401.setDepartureAirport(ap160);
        flight401.setArrivalAirport(ap161);
        flight401.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        flight401.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        flight401.setOpenForBooking(true);
        
        // Create customer CU16
        customer = new Customer();
        
        // Create passenger P9
        Passenger passenger9 = new Passenger();
        passenger9.setName("P9");
        
        // Create booking BK401 with reservation R401 (status = PENDING)
        Booking booking401 = new Booking();
        booking401.setCustomer(customer);
        
        Reservation reservation401 = new Reservation();
        reservation401.setId("R401");
        reservation401.setStatus(ReservationStatus.PENDING);
        reservation401.setPassenger(passenger9);
        reservation401.setFlight(flight401);
        
        booking401.getReservations().add(reservation401);
        flight401.getReservations().add(reservation401);
        customer.getBookings().add(booking401);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", 
                     ReservationStatus.CONFIRMED, reservation401.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap170 = new Airport();
        City cityAC = new City();
        cityAC.setName("CityAC");
        ap170.addCity(cityAC);
        
        Airport ap171 = new Airport();
        City cityAD = new City();
        cityAD.setName("CityAD");
        ap171.addCity(cityAD);
        
        // Create flight F402
        Flight flight402 = new Flight();
        flight402.setId("F402");
        flight402.setDepartureAirport(ap170);
        flight402.setArrivalAirport(ap171);
        flight402.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight402.setArrivalTime(dateFormat.parse("2025-12-15 19:00:00"));
        flight402.setOpenForBooking(true);
        
        // Create customer CU17
        customer = new Customer();
        
        // Create passenger P10
        Passenger passenger10 = new Passenger();
        passenger10.setName("P10");
        
        // Create booking BK402 with reservation R402 (status = CONFIRMED)
        Booking booking402 = new Booking();
        booking402.setCustomer(customer);
        
        Reservation reservation402 = new Reservation();
        reservation402.setId("R402");
        reservation402.setStatus(ReservationStatus.CONFIRMED);
        reservation402.setPassenger(passenger10);
        reservation402.setFlight(flight402);
        
        booking402.getReservations().add(reservation402);
        flight402.getReservations().add(reservation402);
        customer.getBookings().add(booking402);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELLED", 
                     ReservationStatus.CANCELLED, reservation402.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap180 = new Airport();
        City cityAE = new City();
        cityAE.setName("CityAE");
        ap180.addCity(cityAE);
        
        Airport ap181 = new Airport();
        City cityAF = new City();
        cityAF.setName("CityAF");
        ap181.addCity(cityAF);
        
        // Create flight F403
        Flight flight403 = new Flight();
        flight403.setId("F403");
        flight403.setDepartureAirport(ap180);
        flight403.setArrivalAirport(ap181);
        flight403.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight403.setArrivalTime(dateFormat.parse("2025-01-05 10:00:00"));
        flight403.setOpenForBooking(true);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger P11
        Passenger passenger11 = new Passenger();
        passenger11.setName("P11");
        
        // Create reservation R403 (status = PENDING)
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        Reservation reservation403 = new Reservation();
        reservation403.setId("R403");
        reservation403.setStatus(ReservationStatus.PENDING);
        reservation403.setPassenger(passenger11);
        reservation403.setFlight(flight403);
        
        booking.getReservations().add(reservation403);
        flight403.getReservations().add(reservation403);
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00"); // flight already left
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail when flight has departed", result);
        assertEquals("Reservation status should remain PENDING", 
                     ReservationStatus.PENDING, reservation403.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap190 = new Airport();
        City cityAG = new City();
        cityAG.setName("CityAG");
        ap190.addCity(cityAG);
        
        Airport ap191 = new Airport();
        City cityAH = new City();
        cityAH.setName("CityAH");
        ap191.addCity(cityAH);
        
        // Create flight F404
        Flight flight404 = new Flight();
        flight404.setId("F404");
        flight404.setDepartureAirport(ap190);
        flight404.setArrivalAirport(ap191);
        flight404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight404.setArrivalTime(dateFormat.parse("2025-02-01 13:00:00"));
        flight404.setOpenForBooking(false); // flight is closed
        
        // Create customer
        customer = new Customer();
        
        // Create passenger P12
        Passenger passenger12 = new Passenger();
        passenger12.setName("P12");
        
        // Create reservation R404 (status = CONFIRMED)
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        Reservation reservation404 = new Reservation();
        reservation404.setId("R404");
        reservation404.setStatus(ReservationStatus.CONFIRMED);
        reservation404.setPassenger(passenger12);
        reservation404.setFlight(flight404);
        
        booking.getReservations().add(reservation404);
        flight404.getReservations().add(reservation404);
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", 
                     ReservationStatus.CONFIRMED, reservation404.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap200 = new Airport();
        City cityAI = new City();
        cityAI.setName("CityAI");
        ap200.addCity(cityAI);
        
        Airport ap201 = new Airport();
        City cityAJ = new City();
        cityAJ.setName("CityAJ");
        ap201.addCity(cityAJ);
        
        // Create flight F405
        Flight flight405 = new Flight();
        flight405.setId("F405");
        flight405.setDepartureAirport(ap200);
        flight405.setArrivalAirport(ap201);
        flight405.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight405.setArrivalTime(dateFormat.parse("2025-03-10 14:00:00"));
        flight405.setOpenForBooking(true);
        
        // Create customer CU20
        Customer customer20 = new Customer();
        
        // Create passenger P13
        Passenger passenger13 = new Passenger();
        passenger13.setName("P13");
        
        // Create reservation R405 (status = PENDING) for CU20
        Booking booking20 = new Booking();
        booking20.setCustomer(customer20);
        
        Reservation reservation405 = new Reservation();
        reservation405.setId("R405");
        reservation405.setStatus(ReservationStatus.PENDING);
        reservation405.setPassenger(passenger13);
        reservation405.setFlight(flight405);
        
        booking20.getReservations().add(reservation405);
        flight405.getReservations().add(reservation405);
        customer20.getBookings().add(booking20);
        
        // Create customer CU21
        Customer customer21 = new Customer();
        
        // Create passenger P14
        Passenger passenger14 = new Passenger();
        passenger14.setName("P14");
        
        // Create reservation R406 (status = PENDING) for CU21
        Booking booking21 = new Booking();
        booking21.setCustomer(customer21);
        
        Reservation reservation406 = new Reservation();
        reservation406.setId("R406");
        reservation406.setStatus(ReservationStatus.PENDING);
        reservation406.setPassenger(passenger14);
        reservation406.setFlight(flight405);
        
        booking21.getReservations().add(reservation406);
        flight405.getReservations().add(reservation406);
        customer21.getBookings().add(booking21);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - CU20 tries to confirm CU21's reservation R406
        boolean result = customer20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation ID", result);
        assertEquals("Reservation R406 status should remain PENDING", 
                     ReservationStatus.PENDING, reservation406.getStatus());
    }
}