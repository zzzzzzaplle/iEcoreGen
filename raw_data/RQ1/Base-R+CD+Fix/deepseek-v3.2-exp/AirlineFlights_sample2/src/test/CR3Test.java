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
        ap160.setId("AP160");
        City cityAA = new City();
        ap160.addCity(cityAA);
        
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        City cityAB = new City();
        ap161.addCity(cityAB);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F401");
        flight.setDepartureAirport(ap160);
        flight.setArrivalAirport(ap161);
        flight.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        // Create customer and passenger
        Customer customer = new Customer();
        Passenger passenger = new Passenger();
        passenger.setName("P9");
        
        // Create booking with reservation
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        Reservation reservation = new Reservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        flight.addReservation(reservation);
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", 
                    ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        City cityAC = new City();
        ap170.addCity(cityAC);
        
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        City cityAD = new City();
        ap171.addCity(cityAD);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F402");
        flight.setDepartureAirport(ap170);
        flight.setArrivalAirport(ap171);
        flight.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-15 19:00:00"));
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        // Create customer and passenger
        Customer customer = new Customer();
        Passenger passenger = new Passenger();
        passenger.setName("P10");
        
        // Create booking with reservation
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        Reservation reservation = new Reservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        flight.addReservation(reservation);
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be CANCELED", 
                    ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        City cityAE = new City();
        ap180.addCity(cityAE);
        
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        City cityAF = new City();
        ap181.addCity(cityAF);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F403");
        flight.setDepartureAirport(ap180);
        flight.setArrivalAirport(ap181);
        flight.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-05 10:00:00"));
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        // Create customer and passenger
        Customer customer = new Customer();
        Passenger passenger = new Passenger();
        passenger.setName("P11");
        
        // Create booking with reservation
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        Reservation reservation = new Reservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        flight.addReservation(reservation);
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00"); // After departure
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail when flight has departed", result);
        assertEquals("Reservation status should remain PENDING", 
                    ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        City cityAG = new City();
        ap190.addCity(cityAG);
        
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        City cityAH = new City();
        ap191.addCity(cityAH);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F404");
        flight.setDepartureAirport(ap190);
        flight.setArrivalAirport(ap191);
        flight.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-01 13:00:00"));
        flight.setOpenForBooking(false); // Flight is closed
        airline.addFlight(flight);
        
        // Create customer and passenger
        Customer customer = new Customer();
        Passenger passenger = new Passenger();
        passenger.setName("P12");
        
        // Create booking with reservation
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        Reservation reservation = new Reservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        flight.addReservation(reservation);
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", 
                    ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        City cityAI = new City();
        ap200.addCity(cityAI);
        
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        City cityAJ = new City();
        ap201.addCity(cityAJ);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirport(ap200);
        flight.setArrivalAirport(ap201);
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-10 14:00:00"));
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        // Create customer CU20 and passenger P13
        Customer customer20 = new Customer();
        Passenger passenger13 = new Passenger();
        passenger13.setName("P13");
        
        // Create booking for CU20 with reservation R405
        Booking booking20 = new Booking();
        booking20.setCustomer(customer20);
        
        Reservation reservation405 = new Reservation();
        reservation405.setId("R405");
        reservation405.setStatus(ReservationStatus.PENDING);
        reservation405.setPassenger(passenger13);
        reservation405.setFlight(flight);
        
        booking20.getReservations().add(reservation405);
        flight.addReservation(reservation405);
        customer20.getBookings().add(booking20);
        
        // Create customer CU21 and passenger P14
        Customer customer21 = new Customer();
        Passenger passenger14 = new Passenger();
        passenger14.setName("P14");
        
        // Create booking for CU21 with reservation R406
        Booking booking21 = new Booking();
        booking21.setCustomer(customer21);
        
        Reservation reservation406 = new Reservation();
        reservation406.setId("R406");
        reservation406.setStatus(ReservationStatus.PENDING);
        reservation406.setPassenger(passenger14);
        reservation406.setFlight(flight);
        
        booking21.getReservations().add(reservation406);
        flight.addReservation(reservation406);
        customer21.getBookings().add(booking21);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute: CU20 tries to confirm R406 (which belongs to CU21)
        boolean result = customer20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation ID", result);
        assertEquals("Reservation R406 status should remain PENDING", 
                    ReservationStatus.PENDING, reservation406.getStatus());
    }
}