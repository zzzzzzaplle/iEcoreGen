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
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap160 = new Airport();
        ap160.setId("AP160");
        City cityAA = new City();
        cityAA.setName("CityAA");
        ap160.addCity(cityAA);
        
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        City cityAB = new City();
        cityAB.setName("CityAB");
        ap161.addCity(cityAB);
        
        // Create flight F401
        Flight f401 = new Flight();
        f401.setId("F401");
        f401.setDepartureAirport(ap160);
        f401.setArrivalAirport(ap161);
        f401.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        f401.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        f401.setOpenForBooking(true);
        
        airline.addFlight(f401);
        
        // Create customer CU16
        customer = new Customer();
        
        // Create passenger P9
        Passenger p9 = new Passenger();
        p9.setName("P9");
        
        // Create reservation R401
        Reservation r401 = new Reservation();
        r401.setId("R401");
        r401.setPassenger(p9);
        r401.setFlight(f401);
        r401.setStatus(ReservationStatus.PENDING);
        
        // Create booking BK401 and add reservation
        Booking bk401 = new Booking();
        bk401.setCustomer(customer);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r401);
        bk401.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk401);
        customer.setBookings(bookings);
        
        // Add reservation to flight
        f401.addReservation(r401);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", 
                    ReservationStatus.CONFIRMED, r401.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        City cityAC = new City();
        cityAC.setName("CityAC");
        ap170.addCity(cityAC);
        
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        City cityAD = new City();
        cityAD.setName("CityAD");
        ap171.addCity(cityAD);
        
        // Create flight F402
        Flight f402 = new Flight();
        f402.setId("F402");
        f402.setDepartureAirport(ap170);
        f402.setArrivalAirport(ap171);
        f402.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        f402.setArrivalTime(dateFormat.parse("2025-12-15 19:00:00"));
        f402.setOpenForBooking(true);
        
        airline.addFlight(f402);
        
        // Create customer CU17
        customer = new Customer();
        
        // Create passenger P10
        Passenger p10 = new Passenger();
        p10.setName("P10");
        
        // Create reservation R402
        Reservation r402 = new Reservation();
        r402.setId("R402");
        r402.setPassenger(p10);
        r402.setFlight(f402);
        r402.setStatus(ReservationStatus.CONFIRMED);
        
        // Create booking BK402 and add reservation
        Booking bk402 = new Booking();
        bk402.setCustomer(customer);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r402);
        bk402.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk402);
        customer.setBookings(bookings);
        
        // Add reservation to flight
        f402.addReservation(r402);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELLED", 
                    ReservationStatus.CANCELLED, r402.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        City cityAE = new City();
        cityAE.setName("CityAE");
        ap180.addCity(cityAE);
        
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        City cityAF = new City();
        cityAF.setName("CityAF");
        ap181.addCity(cityAF);
        
        // Create flight F403
        Flight f403 = new Flight();
        f403.setId("F403");
        f403.setDepartureAirport(ap180);
        f403.setArrivalAirport(ap181);
        f403.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        f403.setArrivalTime(dateFormat.parse("2025-01-05 10:00:00"));
        f403.setOpenForBooking(true);
        
        airline.addFlight(f403);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger P11
        Passenger p11 = new Passenger();
        p11.setName("P11");
        
        // Create reservation R403
        Reservation r403 = new Reservation();
        r403.setId("R403");
        r403.setPassenger(p11);
        r403.setFlight(f403);
        r403.setStatus(ReservationStatus.PENDING);
        
        // Create booking and add reservation
        Booking booking = new Booking();
        booking.setCustomer(customer);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r403);
        booking.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        // Add reservation to flight
        f403.addReservation(r403);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail when flight has departed", result);
        assertEquals("Reservation status should remain PENDING", 
                    ReservationStatus.PENDING, r403.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        City cityAG = new City();
        cityAG.setName("CityAG");
        ap190.addCity(cityAG);
        
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        City cityAH = new City();
        cityAH.setName("CityAH");
        ap191.addCity(cityAH);
        
        // Create flight F404
        Flight f404 = new Flight();
        f404.setId("F404");
        f404.setDepartureAirport(ap190);
        f404.setArrivalAirport(ap191);
        f404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        f404.setArrivalTime(dateFormat.parse("2025-02-01 13:00:00"));
        f404.setOpenForBooking(false);
        
        airline.addFlight(f404);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger P12
        Passenger p12 = new Passenger();
        p12.setName("P12");
        
        // Create reservation R404
        Reservation r404 = new Reservation();
        r404.setId("R404");
        r404.setPassenger(p12);
        r404.setFlight(f404);
        r404.setStatus(ReservationStatus.CONFIRMED);
        
        // Create booking and add reservation
        Booking booking = new Booking();
        booking.setCustomer(customer);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r404);
        booking.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        // Add reservation to flight
        f404.addReservation(r404);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", 
                    ReservationStatus.CONFIRMED, r404.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        City cityAI = new City();
        cityAI.setName("CityAI");
        ap200.addCity(cityAI);
        
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        City cityAJ = new City();
        cityAJ.setName("CityAJ");
        ap201.addCity(cityAJ);
        
        // Create flight F405
        Flight f405 = new Flight();
        f405.setId("F405");
        f405.setDepartureAirport(ap200);
        f405.setArrivalAirport(ap201);
        f405.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        f405.setArrivalTime(dateFormat.parse("2025-03-10 14:00:00"));
        f405.setOpenForBooking(true);
        
        airline.addFlight(f405);
        
        // Create customer CU20
        Customer cu20 = new Customer();
        
        // Create passenger P13
        Passenger p13 = new Passenger();
        p13.setName("P13");
        
        // Create reservation R405 for CU20
        Reservation r405 = new Reservation();
        r405.setId("R405");
        r405.setPassenger(p13);
        r405.setFlight(f405);
        r405.setStatus(ReservationStatus.PENDING);
        
        // Create booking for CU20 and add reservation R405
        Booking bookingCu20 = new Booking();
        bookingCu20.setCustomer(cu20);
        List<Reservation> reservationsCu20 = new ArrayList<>();
        reservationsCu20.add(r405);
        bookingCu20.setReservations(reservationsCu20);
        
        List<Booking> bookingsCu20 = new ArrayList<>();
        bookingsCu20.add(bookingCu20);
        cu20.setBookings(bookingsCu20);
        
        // Add reservation to flight
        f405.addReservation(r405);
        
        // Create customer CU21 (different customer)
        Customer cu21 = new Customer();
        
        // Create passenger P14
        Passenger p14 = new Passenger();
        p14.setName("P14");
        
        // Create reservation R406 for CU21
        Reservation r406 = new Reservation();
        r406.setId("R406");
        r406.setPassenger(p14);
        r406.setFlight(f405);
        r406.setStatus(ReservationStatus.PENDING);
        
        // Create booking for CU21 and add reservation R406
        Booking bookingCu21 = new Booking();
        bookingCu21.setCustomer(cu21);
        List<Reservation> reservationsCu21 = new ArrayList<>();
        reservationsCu21.add(r406);
        bookingCu21.setReservations(reservationsCu21);
        
        List<Booking> bookingsCu21 = new ArrayList<>();
        bookingsCu21.add(bookingCu21);
        cu21.setBookings(bookingsCu21);
        
        // Add reservation to flight
        f405.addReservation(r406);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - CU20 tries to confirm R406 (which belongs to CU21)
        boolean result = cu20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation ID", result);
        assertEquals("R405 status should remain unchanged", 
                    ReservationStatus.PENDING, r405.getStatus());
        assertEquals("R406 status should remain unchanged", 
                    ReservationStatus.PENDING, r406.getStatus());
    }
}