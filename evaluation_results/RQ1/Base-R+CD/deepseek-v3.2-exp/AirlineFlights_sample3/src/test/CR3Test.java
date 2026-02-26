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
        cityAA.setName("CityAA");
        ap160.addCity(cityAA);
        
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        City cityAB = new City();
        cityAB.setName("CityAB");
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
        
        // Create customer
        Customer customer = new Customer();
        
        // Create passenger
        Passenger passenger = new Passenger();
        passenger.setName("P9");
        
        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setId("R401");
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        reservation.setStatus(ReservationStatus.PENDING);
        
        // Create booking and add reservation
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.addReservation(reservation);
        customer.getBookings().add(booking);
        
        // Add reservation to flight
        flight.addReservation(reservation);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Pending reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws Exception {
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
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F402");
        flight.setDepartureAirport(ap170);
        flight.setArrivalAirport(ap171);
        flight.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-15 19:00:00")); // Added arrival time for completeness
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create customer
        Customer customer = new Customer();
        
        // Create passenger
        Passenger passenger = new Passenger();
        passenger.setName("P10");
        
        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setId("R402");
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        // Create booking and add reservation
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.addReservation(reservation);
        customer.getBookings().add(booking);
        
        // Add reservation to flight
        flight.addReservation(reservation);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Confirmed reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELLED", ReservationStatus.CANCELLED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws Exception {
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
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F403");
        flight.setDepartureAirport(ap180);
        flight.setArrivalAirport(ap181);
        flight.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-05 10:00:00")); // Added arrival time for completeness
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create customer
        Customer customer = new Customer();
        
        // Create passenger
        Passenger passenger = new Passenger();
        passenger.setName("P11");
        
        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setId("R403");
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        reservation.setStatus(ReservationStatus.PENDING);
        
        // Create booking and add reservation
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.addReservation(reservation);
        customer.getBookings().add(booking);
        
        // Add reservation to flight
        flight.addReservation(reservation);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00"); // After departure time
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail when flight has already departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws Exception {
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
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F404");
        flight.setDepartureAirport(ap190);
        flight.setArrivalAirport(ap191);
        flight.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-01 13:00:00")); // Added arrival time for completeness
        flight.setOpenForBooking(false); // Flight closed for booking
        
        airline.addFlight(flight);
        
        // Create customer
        Customer customer = new Customer();
        
        // Create passenger
        Passenger passenger = new Passenger();
        passenger.setName("P12");
        
        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setId("R404");
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        // Create booking and add reservation
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.addReservation(reservation);
        customer.getBookings().add(booking);
        
        // Add reservation to flight
        flight.addReservation(reservation);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00"); // Before departure but flight closed
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail when flight is closed for booking", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws Exception {
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
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirport(ap200);
        flight.setArrivalAirport(ap201);
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-10 14:00:00")); // Added arrival time for completeness
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create customer CU20
        Customer customer20 = new Customer();
        
        // Create passenger P13 for CU20
        Passenger passenger13 = new Passenger();
        passenger13.setName("P13");
        
        // Create reservation R405 for CU20
        Reservation reservation405 = new Reservation();
        reservation405.setId("R405");
        reservation405.setPassenger(passenger13);
        reservation405.setFlight(flight);
        reservation405.setStatus(ReservationStatus.PENDING);
        
        // Create booking and add reservation for CU20
        Booking booking20 = new Booking();
        booking20.setCustomer(customer20);
        booking20.addReservation(reservation405);
        customer20.getBookings().add(booking20);
        
        // Add reservation to flight
        flight.addReservation(reservation405);
        
        // Create customer CU21
        Customer customer21 = new Customer();
        
        // Create passenger P14 for CU21
        Passenger passenger14 = new Passenger();
        passenger14.setName("P14");
        
        // Create reservation R406 for CU21
        Reservation reservation406 = new Reservation();
        reservation406.setId("R406");
        reservation406.setPassenger(passenger14);
        reservation406.setFlight(flight);
        reservation406.setStatus(ReservationStatus.PENDING);
        
        // Create booking and add reservation for CU21
        Booking booking21 = new Booking();
        booking21.setCustomer(customer21);
        booking21.addReservation(reservation406);
        customer21.getBookings().add(booking21);
        
        // Add reservation to flight
        flight.addReservation(reservation406);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - CU20 tries to confirm R406 (which belongs to CU21)
        boolean result = customer20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation identifier", result);
    }
}