import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_confirmPendingReservation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
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
        
        // Add flight to airline
        airline.addFlight(f401);
        
        // Create customer CU16
        Customer cu16 = new Customer();
        
        // Create booking BK401 with reservation R401
        Booking bk401 = new Booking();
        bk401.setCustomer(cu16);
        
        Reservation r401 = new Reservation();
        r401.setId("R401");
        r401.setStatus(ReservationStatus.PENDING);
        
        Passenger p9 = new Passenger();
        p9.setName("P9");
        r401.setPassenger(p9);
        r401.setFlight(f401);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r401);
        bk401.setReservations(reservations);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r401);
        f401.setReservations(flightReservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk401);
        cu16.setBookings(bookings);
        
        // Current time
        Date now = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = cu16.confirm("R401", now);
        
        // Verify
        assertTrue(result);
        assertEquals(ReservationStatus.CONFIRMED, r401.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
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
        f402.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(f402);
        
        // Create customer CU17
        Customer cu17 = new Customer();
        
        // Create booking BK402 with reservation R402
        Booking bk402 = new Booking();
        bk402.setCustomer(cu17);
        
        Reservation r402 = new Reservation();
        r402.setId("R402");
        r402.setStatus(ReservationStatus.CONFIRMED);
        
        Passenger p10 = new Passenger();
        p10.setName("P10");
        r402.setPassenger(p10);
        r402.setFlight(f402);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r402);
        bk402.setReservations(reservations);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r402);
        f402.setReservations(flightReservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(bk402);
        cu17.setBookings(bookings);
        
        // Current time
        Date now = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = cu17.cancel("R402", now);
        
        // Verify
        assertTrue(result);
        assertEquals(ReservationStatus.CANCELLED, r402.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
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
        f403.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(f403);
        
        // Create reservation R403
        Reservation r403 = new Reservation();
        r403.setId("R403");
        r403.setStatus(ReservationStatus.PENDING);
        
        Passenger p11 = new Passenger();
        p11.setName("P11");
        r403.setPassenger(p11);
        r403.setFlight(f403);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r403);
        f403.setReservations(flightReservations);
        
        // Current time (flight already left)
        Date now = dateFormat.parse("2025-01-05 07:00:00");
        
        // Create customer with reservation
        Customer customer = new Customer();
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r403);
        booking.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        // Execute
        boolean result = customer.confirm("R403", now);
        
        // Verify
        assertFalse(result);
        assertEquals(ReservationStatus.PENDING, r403.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
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
        
        // Create flight F404 (closed for booking)
        Flight f404 = new Flight();
        f404.setId("F404");
        f404.setDepartureAirport(ap190);
        f404.setArrivalAirport(ap191);
        f404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        f404.setOpenForBooking(false); // Closed for booking
        
        // Add flight to airline
        airline.addFlight(f404);
        
        // Create reservation R404
        Reservation r404 = new Reservation();
        r404.setId("R404");
        r404.setStatus(ReservationStatus.CONFIRMED);
        
        Passenger p12 = new Passenger();
        p12.setName("P12");
        r404.setPassenger(p12);
        r404.setFlight(f404);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r404);
        f404.setReservations(flightReservations);
        
        // Current time
        Date now = dateFormat.parse("2025-01-20 08:00:00");
        
        // Create customer with reservation
        Customer customer = new Customer();
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r404);
        booking.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        // Execute
        boolean result = customer.cancel("R404", now);
        
        // Verify
        assertFalse(result);
        assertEquals(ReservationStatus.CONFIRMED, r404.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
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
        f405.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(f405);
        
        // Create customer CU20 with reservation R405
        Customer cu20 = new Customer();
        Booking bk405 = new Booking();
        bk405.setCustomer(cu20);
        
        Reservation r405 = new Reservation();
        r405.setId("R405");
        r405.setStatus(ReservationStatus.PENDING);
        
        Passenger p13 = new Passenger();
        p13.setName("P13");
        r405.setPassenger(p13);
        r405.setFlight(f405);
        
        List<Reservation> cu20Reservations = new ArrayList<>();
        cu20Reservations.add(r405);
        bk405.setReservations(cu20Reservations);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r405);
        f405.setReservations(flightReservations);
        
        List<Booking> cu20Bookings = new ArrayList<>();
        cu20Bookings.add(bk405);
        cu20.setBookings(cu20Bookings);
        
        // Create customer CU21 with reservation R406
        Customer cu21 = new Customer();
        Booking bk406 = new Booking();
        bk406.setCustomer(cu21);
        
        Reservation r406 = new Reservation();
        r406.setId("R406");
        r406.setStatus(ReservationStatus.PENDING);
        
        Passenger p14 = new Passenger();
        p14.setName("P14");
        r406.setPassenger(p14);
        r406.setFlight(f405);
        
        List<Reservation> cu21Reservations = new ArrayList<>();
        cu21Reservations.add(r406);
        bk406.setReservations(cu21Reservations);
        
        List<Booking> cu21Bookings = new ArrayList<>();
        cu21Bookings.add(bk406);
        cu21.setBookings(cu21Bookings);
        
        // Current time
        Date now = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - CU20 trying to confirm R406 (which belongs to CU21)
        boolean result = cu20.confirm("R406", now);
        
        // Verify
        assertFalse(result);
        assertEquals(ReservationStatus.PENDING, r405.getStatus());
        assertEquals(ReservationStatus.PENDING, r406.getStatus());
    }
}