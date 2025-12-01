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
        
        // Create airports and cities
        Airport ap160 = new Airport();
        Airport ap161 = new Airport();
        City cityAA = new City();
        cityAA.setName("CityAA");
        City cityAB = new City();
        cityAB.setName("CityAB");
        ap160.addCity(cityAA);
        ap161.addCity(cityAB);
        
        // Create flight F401
        Flight flight401 = new Flight();
        flight401.setId("F401");
        flight401.setDepartureAirport(ap160);
        flight401.setArrivalAirport(ap161);
        flight401.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        flight401.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        flight401.setOpenForBooking(true);
        
        airline.addFlight(flight401);
        
        // Create customer CU16
        Customer customer16 = new Customer();
        
        // Create passenger P9
        Passenger passenger9 = new Passenger();
        passenger9.setName("P9");
        
        // Create reservation R401
        Reservation reservation401 = new Reservation();
        reservation401.setId("R401");
        reservation401.setStatus(ReservationStatus.PENDING);
        reservation401.setPassenger(passenger9);
        reservation401.setFlight(flight401);
        
        // Create booking BK401 and add reservation
        Booking booking401 = new Booking();
        booking401.setCustomer(customer16);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation401);
        booking401.setReservations(reservations);
        
        // Add booking to customer
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking401);
        customer16.setBookings(bookings);
        
        // Add reservation to flight
        flight401.getReservations().add(reservation401);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer16.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Pending reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", 
                    ReservationStatus.CONFIRMED, reservation401.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports and cities
        Airport ap170 = new Airport();
        Airport ap171 = new Airport();
        City cityAC = new City();
        cityAC.setName("CityAC");
        City cityAD = new City();
        cityAD.setName("CityAD");
        ap170.addCity(cityAC);
        ap171.addCity(cityAD);
        
        // Create flight F402
        Flight flight402 = new Flight();
        flight402.setId("F402");
        flight402.setDepartureAirport(ap170);
        flight402.setArrivalAirport(ap171);
        flight402.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight402.setArrivalTime(dateFormat.parse("2025-12-15 19:00:00"));
        flight402.setOpenForBooking(true);
        
        airline.addFlight(flight402);
        
        // Create customer CU17
        Customer customer17 = new Customer();
        
        // Create passenger P10
        Passenger passenger10 = new Passenger();
        passenger10.setName("P10");
        
        // Create reservation R402
        Reservation reservation402 = new Reservation();
        reservation402.setId("R402");
        reservation402.setStatus(ReservationStatus.CONFIRMED);
        reservation402.setPassenger(passenger10);
        reservation402.setFlight(flight402);
        
        // Create booking BK402 and add reservation
        Booking booking402 = new Booking();
        booking402.setCustomer(customer17);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation402);
        booking402.setReservations(reservations);
        
        // Add booking to customer
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking402);
        customer17.setBookings(bookings);
        
        // Add reservation to flight
        flight402.getReservations().add(reservation402);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer17.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Confirmed reservation should be canceled successfully", result);
        assertEquals("Reservation status should be CANCELED", 
                    ReservationStatus.CANCELED, reservation402.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports and cities
        Airport ap180 = new Airport();
        Airport ap181 = new Airport();
        City cityAE = new City();
        cityAE.setName("CityAE");
        City cityAF = new City();
        cityAF.setName("CityAF");
        ap180.addCity(cityAE);
        ap181.addCity(cityAF);
        
        // Create flight F403
        Flight flight403 = new Flight();
        flight403.setId("F403");
        flight403.setDepartureAirport(ap180);
        flight403.setArrivalAirport(ap181);
        flight403.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight403.setArrivalTime(dateFormat.parse("2025-01-05 10:00:00"));
        flight403.setOpenForBooking(true);
        
        airline.addFlight(flight403);
        
        // Create customer
        Customer customer = new Customer();
        
        // Create passenger P11
        Passenger passenger11 = new Passenger();
        passenger11.setName("P11");
        
        // Create reservation R403
        Reservation reservation403 = new Reservation();
        reservation403.setId("R403");
        reservation403.setStatus(ReservationStatus.PENDING);
        reservation403.setPassenger(passenger11);
        reservation403.setFlight(flight403);
        
        // Create booking and add reservation
        Booking booking = new Booking();
        booking.setCustomer(customer);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation403);
        booking.setReservations(reservations);
        
        // Add booking to customer
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        // Add reservation to flight
        flight403.getReservations().add(reservation403);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
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
        Airline airline = new Airline();
        
        // Create airports and cities
        Airport ap190 = new Airport();
        Airport ap191 = new Airport();
        City cityAG = new City();
        cityAG.setName("CityAG");
        City cityAH = new City();
        cityAH.setName("CityAH");
        ap190.addCity(cityAG);
        ap191.addCity(cityAH);
        
        // Create flight F404
        Flight flight404 = new Flight();
        flight404.setId("F404");
        flight404.setDepartureAirport(ap190);
        flight404.setArrivalAirport(ap191);
        flight404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight404.setArrivalTime(dateFormat.parse("2025-02-01 13:00:00"));
        flight404.setOpenForBooking(false);
        
        airline.addFlight(flight404);
        
        // Create customer
        Customer customer = new Customer();
        
        // Create passenger P12
        Passenger passenger12 = new Passenger();
        passenger12.setName("P12");
        
        // Create reservation R404
        Reservation reservation404 = new Reservation();
        reservation404.setId("R404");
        reservation404.setStatus(ReservationStatus.CONFIRMED);
        reservation404.setPassenger(passenger12);
        reservation404.setFlight(flight404);
        
        // Create booking and add reservation
        Booking booking = new Booking();
        booking.setCustomer(customer);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation404);
        booking.setReservations(reservations);
        
        // Add booking to customer
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        // Add reservation to flight
        flight404.getReservations().add(reservation404);
        
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
        Airline airline = new Airline();
        
        // Create airports and cities
        Airport ap200 = new Airport();
        Airport ap201 = new Airport();
        City cityAI = new City();
        cityAI.setName("CityAI");
        City cityAJ = new City();
        cityAJ.setName("CityAJ");
        ap200.addCity(cityAI);
        ap201.addCity(cityAJ);
        
        // Create flight F405
        Flight flight405 = new Flight();
        flight405.setId("F405");
        flight405.setDepartureAirport(ap200);
        flight405.setArrivalAirport(ap201);
        flight405.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight405.setArrivalTime(dateFormat.parse("2025-03-10 14:00:00"));
        flight405.setOpenForBooking(true);
        
        airline.addFlight(flight405);
        
        // Create customer CU20
        Customer customer20 = new Customer();
        
        // Create passenger P13
        Passenger passenger13 = new Passenger();
        passenger13.setName("P13");
        
        // Create reservation R405 for CU20
        Reservation reservation405 = new Reservation();
        reservation405.setId("R405");
        reservation405.setStatus(ReservationStatus.PENDING);
        reservation405.setPassenger(passenger13);
        reservation405.setFlight(flight405);
        
        // Create booking for CU20 and add reservation
        Booking booking20 = new Booking();
        booking20.setCustomer(customer20);
        List<Reservation> reservations20 = new ArrayList<>();
        reservations20.add(reservation405);
        booking20.setReservations(reservations20);
        
        // Add booking to customer CU20
        List<Booking> bookings20 = new ArrayList<>();
        bookings20.add(booking20);
        customer20.setBookings(bookings20);
        
        // Add reservation to flight
        flight405.getReservations().add(reservation405);
        
        // Create customer CU21
        Customer customer21 = new Customer();
        
        // Create passenger P14
        Passenger passenger14 = new Passenger();
        passenger14.setName("P14");
        
        // Create reservation R406 for CU21
        Reservation reservation406 = new Reservation();
        reservation406.setId("R406");
        reservation406.setStatus(ReservationStatus.PENDING);
        reservation406.setPassenger(passenger14);
        reservation406.setFlight(flight405);
        
        // Create booking for CU21 and add reservation
        Booking booking21 = new Booking();
        booking21.setCustomer(customer21);
        List<Reservation> reservations21 = new ArrayList<>();
        reservations21.add(reservation406);
        booking21.setReservations(reservations21);
        
        // Add booking to customer CU21
        List<Booking> bookings21 = new ArrayList<>();
        bookings21.add(booking21);
        customer21.setBookings(bookings21);
        
        // Add reservation to flight
        flight405.getReservations().add(reservation406);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - CU20 tries to confirm R406 (which belongs to CU21)
        boolean result = customer20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation ID", result);
    }
}