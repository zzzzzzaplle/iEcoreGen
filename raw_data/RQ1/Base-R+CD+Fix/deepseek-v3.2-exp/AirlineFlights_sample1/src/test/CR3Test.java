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
        
        // Create airports
        Airport ap160 = new Airport();
        City cityAA = new City();
        cityAA.setName("CityAA");
        ap160.addCity(cityAA);
        
        Airport ap161 = new Airport();
        City cityAB = new City();
        cityAB.setName("CityAB");
        ap161.addCity(cityAB);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F401");
        flight.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        flight.setDepartureAirport(ap160);
        flight.setArrivalAirport(ap161);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create customer and booking
        Customer customer = new Customer();
        Passenger passenger = new Passenger();
        passenger.setName("P9");
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        // Create reservation
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        Reservation reservation = new Reservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        // Test
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Should successfully confirm pending reservation", result);
        assertEquals("Reservation status should be CONFIRMED", 
                     ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap170 = new Airport();
        City cityAC = new City();
        cityAC.setName("CityAC");
        ap170.addCity(cityAC);
        
        Airport ap171 = new Airport();
        City cityAD = new City();
        cityAD.setName("CityAD");
        ap171.addCity(cityAD);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F402");
        flight.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-15 19:00:00"));
        flight.setDepartureAirport(ap170);
        flight.setArrivalAirport(ap171);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create customer and booking
        Customer customer = new Customer();
        Passenger passenger = new Passenger();
        passenger.setName("P10");
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        // Create reservation
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        Reservation reservation = new Reservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        // Test
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Should successfully cancel confirmed reservation", result);
        assertEquals("Reservation status should be CANCELED", 
                     ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap180 = new Airport();
        City cityAE = new City();
        cityAE.setName("CityAE");
        ap180.addCity(cityAE);
        
        Airport ap181 = new Airport();
        City cityAF = new City();
        cityAF.setName("CityAF");
        ap181.addCity(cityAF);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F403");
        flight.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-05 10:00:00"));
        flight.setDepartureAirport(ap180);
        flight.setArrivalAirport(ap181);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create customer and booking
        Customer customer = new Customer();
        Passenger passenger = new Passenger();
        passenger.setName("P11");
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        // Create reservation
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        Reservation reservation = new Reservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        // Test
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Should fail to confirm reservation when flight has departed", result);
        assertEquals("Reservation status should remain PENDING", 
                     ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap190 = new Airport();
        City cityAG = new City();
        cityAG.setName("CityAG");
        ap190.addCity(cityAG);
        
        Airport ap191 = new Airport();
        City cityAH = new City();
        cityAH.setName("CityAH");
        ap191.addCity(cityAH);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F404");
        flight.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-01 13:00:00"));
        flight.setDepartureAirport(ap190);
        flight.setArrivalAirport(ap191);
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(flight);
        
        // Create customer and booking
        Customer customer = new Customer();
        Passenger passenger = new Passenger();
        passenger.setName("P12");
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        // Create reservation
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        Reservation reservation = new Reservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        // Test
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Should fail to cancel reservation when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", 
                     ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap200 = new Airport();
        City cityAI = new City();
        cityAI.setName("CityAI");
        ap200.addCity(cityAI);
        
        Airport ap201 = new Airport();
        City cityAJ = new City();
        cityAJ.setName("CityAJ");
        ap201.addCity(cityAJ);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F405");
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-10 14:00:00"));
        flight.setDepartureAirport(ap200);
        flight.setArrivalAirport(ap201);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create customer CU20 with reservation R405
        Customer customer20 = new Customer();
        Passenger passenger13 = new Passenger();
        passenger13.setName("P13");
        
        Booking booking20 = new Booking();
        booking20.setCustomer(customer20);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        Reservation reservation405 = new Reservation();
        reservation405.setId("R405");
        reservation405.setStatus(ReservationStatus.PENDING);
        reservation405.setPassenger(passenger13);
        reservation405.setFlight(flight);
        
        booking20.getReservations().add(reservation405);
        flight.getReservations().add(reservation405);
        customer20.getBookings().add(booking20);
        
        // Create customer CU21 with reservation R406 (separate customer)
        Customer customer21 = new Customer();
        Passenger passenger14 = new Passenger();
        passenger14.setName("P14");
        
        Booking booking21 = new Booking();
        booking21.setCustomer(customer21);
        
        Reservation reservation406 = new Reservation();
        reservation406.setId("R406");
        reservation406.setStatus(ReservationStatus.PENDING);
        reservation406.setPassenger(passenger14);
        reservation406.setFlight(flight);
        
        booking21.getReservations().add(reservation406);
        flight.getReservations().add(reservation406);
        customer21.getBookings().add(booking21);
        
        // Test: Customer CU20 tries to confirm reservation R406 (which belongs to CU21)
        boolean result = customer20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Should fail when trying to confirm unknown reservation ID", result);
    }
}