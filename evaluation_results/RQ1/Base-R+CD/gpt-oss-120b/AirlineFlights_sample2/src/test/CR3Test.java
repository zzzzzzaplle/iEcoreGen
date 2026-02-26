import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() throws Exception {
        // Setup Airline AL16
        Airline airline = new Airline();
        
        // Setup Airports AP160 (CityAA) and AP161 (CityAB)
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
        
        // Setup Flight F401
        Flight flight = new Flight();
        flight.setId("F401");
        flight.setDepartureAirport(ap160);
        flight.setArrivalAirport(ap161);
        flight.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        flight.setOpenForBooking(true);
        
        // Setup Customer CU16, passenger P9
        Customer customer = new Customer();
        
        // Setup Booking BK401 contains reservation R401 (status = PENDING) for P9 on F401
        Booking booking = new Booking(customer);
        Passenger passenger = new Passenger();
        passenger.setName("P9");
        
        Reservation reservation = new Reservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        // Current time = 2025-11-01 09:00:00
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute: CU16 confirm reservation R401
        boolean result = customer.confirm("R401", currentTime);
        
        // Expected Output: True
        assertTrue("Pending reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() throws Exception {
        // Setup Airline AL17
        Airline airline = new Airline();
        
        // Setup Airports AP170 (CityAC) and AP171 (CityAD)
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
        
        // Setup Flight F402
        Flight flight = new Flight();
        flight.setId("F402");
        flight.setDepartureAirport(ap170);
        flight.setArrivalAirport(ap171);
        flight.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-15 19:00:00"));
        flight.setOpenForBooking(true);
        
        // Setup Customer CU17, passenger P10
        Customer customer = new Customer();
        
        // Setup Booking BK402 contains reservation R402 (status = CONFIRMED) for P10 on F402
        Booking booking = new Booking(customer);
        Passenger passenger = new Passenger();
        passenger.setName("P10");
        
        Reservation reservation = new Reservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        // Current time = 2025-12-01 12:00:00
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute: CU17 cancel reservation R402
        boolean result = customer.cancel("R402", currentTime);
        
        // Expected Output: True
        assertTrue("Confirmed reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELLED", ReservationStatus.CANCELLED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() throws Exception {
        // Setup Airline AL18
        Airline airline = new Airline();
        
        // Setup Airports AP180 (CityAE) and AP181 (CityAF)
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
        
        // Setup Flight F403
        Flight flight = new Flight();
        flight.setId("F403");
        flight.setDepartureAirport(ap180);
        flight.setArrivalAirport(ap181);
        flight.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        // Setup Customer
        Customer customer = new Customer();
        
        // Setup Reservation R403 status = PENDING (passenger P11)
        Booking booking = new Booking(customer);
        Passenger passenger = new Passenger();
        passenger.setName("P11");
        
        Reservation reservation = new Reservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        // Current time = 2025-01-05 07:00:00 (flight already left)
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute: Confirm reservation R403
        boolean result = customer.confirm("R403", currentTime);
        
        // Expected Output: False
        assertFalse("Confirmation should fail when flight has already departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() throws Exception {
        // Setup Airline AL19
        Airline airline = new Airline();
        
        // Setup Airports AP190 (CityAG) and AP191 (CityAH)
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
        
        // Setup Flight F404
        Flight flight = new Flight();
        flight.setId("F404");
        flight.setDepartureAirport(ap190);
        flight.setArrivalAirport(ap191);
        flight.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-01 13:00:00"));
        flight.setOpenForBooking(false); // Closed flight
        
        // Setup Customer
        Customer customer = new Customer();
        
        // Setup Reservation R404 status = CONFIRMED (passenger P12)
        Booking booking = new Booking(customer);
        Passenger passenger = new Passenger();
        passenger.setName("P12");
        
        Reservation reservation = new Reservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        // Current time = 2025-01-20 08:00:00
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute: Cancel reservation R404
        boolean result = customer.cancel("R404", currentTime);
        
        // Expected Output: False
        assertFalse("Cancellation should fail when flight is closed for booking", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() throws Exception {
        // Setup Airline AL20
        Airline airline = new Airline();
        
        // Setup Airports AP200 (CityAI) and AP201 (CityAJ)
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
        
        // Setup Flight F405
        Flight flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirport(ap200);
        flight.setArrivalAirport(ap201);
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-10 14:00:00"));
        flight.setOpenForBooking(true);
        
        // Setup Customer CU20 with one existing reservation R405 (status = PENDING) for passenger P13
        Customer customerCU20 = new Customer();
        Booking bookingCU20 = new Booking(customerCU20);
        Passenger passengerP13 = new Passenger();
        passengerP13.setName("P13");
        
        Reservation reservationR405 = new Reservation();
        reservationR405.setId("R405");
        reservationR405.setStatus(ReservationStatus.PENDING);
        reservationR405.setPassenger(passengerP13);
        reservationR405.setFlight(flight);
        
        bookingCU20.getReservations().add(reservationR405);
        flight.getReservations().add(reservationR405);
        customerCU20.getBookings().add(bookingCU20);
        
        // Setup Customer CU21 with one existing reservation R406 (status = PENDING) for passenger P14
        Customer customerCU21 = new Customer();
        Booking bookingCU21 = new Booking(customerCU21);
        Passenger passengerP14 = new Passenger();
        passengerP14.setName("P14");
        
        Reservation reservationR406 = new Reservation();
        reservationR406.setId("R406");
        reservationR406.setStatus(ReservationStatus.PENDING);
        reservationR406.setPassenger(passengerP14);
        reservationR406.setFlight(flight);
        
        bookingCU21.getReservations().add(reservationR406);
        flight.getReservations().add(reservationR406);
        customerCU21.getBookings().add(bookingCU21);
        
        // Current time = 2025-02-15 09:00:00
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute: Customer CU20 confirm reservation R406 (which belongs to CU21)
        boolean result = customerCU20.confirm("R406", currentTime);
        
        // Expected Output: False
        assertFalse("Confirmation should fail for unknown reservation identifier", result);
        assertEquals("Reservation R406 status should remain PENDING", ReservationStatus.PENDING, reservationR406.getStatus());
    }
}