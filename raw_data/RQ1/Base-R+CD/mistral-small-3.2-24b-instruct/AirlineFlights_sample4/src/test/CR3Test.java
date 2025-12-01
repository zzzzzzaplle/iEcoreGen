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
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    
    @Test
    public void testCase1_confirmPendingReservation() throws Exception {
        // Setup
        Airline airline = new Airline();
        City cityAA = new City();
        cityAA.setName("CityAA");
        City cityAB = new City();
        cityAB.setName("CityAB");
        
        Airport ap160 = new Airport();
        ap160.addCity(cityAA);
        Airport ap161 = new Airport();
        ap161.addCity(cityAB);
        
        Flight flight = new Flight();
        flight.setId("F401");
        flight.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        flight.setDepartureAirport(ap160);
        flight.setArrialAirport(ap161);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Passenger passenger = new Passenger();
        passenger.setName("P9");
        
        Reservation reservation = new Reservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Should confirm pending reservation", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws Exception {
        // Setup
        Airline airline = new Airline();
        City cityAC = new City();
        cityAC.setName("CityAC");
        City cityAD = new City();
        cityAD.setName("CityAD");
        
        Airport ap170 = new Airport();
        ap170.addCity(cityAC);
        Airport ap171 = new Airport();
        ap171.addCity(cityAD);
        
        Flight flight = new Flight();
        flight.setId("F402");
        flight.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight.setDepartureAirport(ap170);
        flight.setArrialAirport(ap171);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Passenger passenger = new Passenger();
        passenger.setName("P10");
        
        Reservation reservation = new Reservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Should cancel confirmed reservation", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws Exception {
        // Setup
        Airline airline = new Airline();
        City cityAE = new City();
        cityAE.setName("CityAE");
        City cityAF = new City();
        cityAF.setName("CityAF");
        
        Airport ap180 = new Airport();
        ap180.addCity(cityAE);
        Airport ap181 = new Airport();
        ap181.addCity(cityAF);
        
        Flight flight = new Flight();
        flight.setId("F403");
        flight.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight.setDepartureAirport(ap180);
        flight.setArrialAirport(ap181);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Passenger passenger = new Passenger();
        passenger.setName("P11");
        
        Reservation reservation = new Reservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Should not confirm reservation for departed flight", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws Exception {
        // Setup
        Airline airline = new Airline();
        City cityAG = new City();
        cityAG.setName("CityAG");
        City cityAH = new City();
        cityAH.setName("CityAH");
        
        Airport ap190 = new Airport();
        ap190.addCity(cityAG);
        Airport ap191 = new Airport();
        ap191.addCity(cityAH);
        
        Flight flight = new Flight();
        flight.setId("F404");
        flight.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight.setDepartureAirport(ap190);
        flight.setArrialAirport(ap191);
        flight.setOpenForBooking(false);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Passenger passenger = new Passenger();
        passenger.setName("P12");
        
        Reservation reservation = new Reservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Should not cancel reservation for closed flight", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws Exception {
        // Setup
        Airline airline = new Airline();
        City cityAI = new City();
        cityAI.setName("CityAI");
        City cityAJ = new City();
        cityAJ.setName("CityAJ");
        
        Airport ap200 = new Airport();
        ap200.addCity(cityAI);
        Airport ap201 = new Airport();
        ap201.addCity(cityAJ);
        
        Flight flight = new Flight();
        flight.setId("F405");
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setDepartureAirport(ap200);
        flight.setArrialAirport(ap201);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer20 = new Customer();
        Passenger passenger13 = new Passenger();
        passenger13.setName("P13");
        
        Reservation reservation405 = new Reservation();
        reservation405.setId("R405");
        reservation405.setStatus(ReservationStatus.PENDING);
        reservation405.setPassenger(passenger13);
        reservation405.setFlight(flight);
        
        Booking booking20 = new Booking();
        booking20.setCustomer(customer20);
        booking20.getReservations().add(reservation405);
        customer20.getBookings().add(booking20);
        flight.getReservations().add(reservation405);
        
        Customer customer21 = new Customer();
        Passenger passenger14 = new Passenger();
        passenger14.setName("P14");
        
        Reservation reservation406 = new Reservation();
        reservation406.setId("R406");
        reservation406.setStatus(ReservationStatus.PENDING);
        reservation406.setPassenger(passenger14);
        reservation406.setFlight(flight);
        
        Booking booking21 = new Booking();
        booking21.setCustomer(customer21);
        booking21.getReservations().add(reservation406);
        customer21.getBookings().add(booking21);
        flight.getReservations().add(reservation406);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute
        boolean result = customer20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Should not confirm unknown reservation for customer", result);
        assertEquals("Reservation R406 status should remain PENDING", ReservationStatus.PENDING, reservation406.getStatus());
    }
}