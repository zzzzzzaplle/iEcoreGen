import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void testCase1_ConfirmPendingReservation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap160 = new Airport("AP160");
        City cityAA = new City("CityAA");
        ap160.addCity(cityAA);
        
        Airport ap161 = new Airport("AP161");
        City cityAB = new City("CityAB");
        ap161.addCity(cityAB);
        
        Date departureTime = dateFormat.parse("2025-12-10 11:00:00");
        Date arrivalTime = dateFormat.parse("2025-12-10 15:00:00");
        Flight flight = new Flight(departureTime, arrivalTime, ap160, ap161);
        flight.setOpenForBooking(true);
        
        Customer customer = new Customer();
        Passenger passenger = new Passenger("P9");
        
        Reservation reservation = new Reservation(passenger, flight);
        reservation.setStatus(ReservationStatus.PENDING);
        
        Booking booking = new Booking(customer);
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer.confirm(reservation.getId(), currentTime);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", 
                     ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap170 = new Airport("AP170");
        City cityAC = new City("CityAC");
        ap170.addCity(cityAC);
        
        Airport ap171 = new Airport("AP171");
        City cityAD = new City("CityAD");
        ap171.addCity(cityAD);
        
        Date departureTime = dateFormat.parse("2025-12-15 15:00:00");
        Flight flight = new Flight(departureTime, new Date(), ap170, ap171);
        flight.setOpenForBooking(true);
        
        Customer customer = new Customer();
        Passenger passenger = new Passenger("P10");
        
        Reservation reservation = new Reservation(passenger, flight);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        Booking booking = new Booking(customer);
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel(reservation.getId(), currentTime);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be CANCELED", 
                     ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap180 = new Airport("AP180");
        City cityAE = new City("CityAE");
        ap180.addCity(cityAE);
        
        Airport ap181 = new Airport("AP181");
        City cityAF = new City("CityAF");
        ap181.addCity(cityAF);
        
        Date departureTime = dateFormat.parse("2025-01-05 06:00:00");
        Flight flight = new Flight(departureTime, new Date(), ap180, ap181);
        flight.setOpenForBooking(true);
        
        Customer customer = new Customer();
        Passenger passenger = new Passenger("P11");
        
        Reservation reservation = new Reservation(passenger, flight);
        reservation.setStatus(ReservationStatus.PENDING);
        
        Booking booking = new Booking(customer);
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = customer.confirm(reservation.getId(), currentTime);
        
        // Verify
        assertFalse("Confirmation should fail when flight has departed", result);
        assertEquals("Reservation status should remain PENDING", 
                     ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap190 = new Airport("AP190");
        City cityAG = new City("CityAG");
        ap190.addCity(cityAG);
        
        Airport ap191 = new Airport("AP191");
        City cityAH = new City("CityAH");
        ap191.addCity(cityAH);
        
        Date departureTime = dateFormat.parse("2025-02-01 09:00:00");
        Flight flight = new Flight(departureTime, new Date(), ap190, ap191);
        flight.setOpenForBooking(false);
        
        Customer customer = new Customer();
        Passenger passenger = new Passenger("P12");
        
        Reservation reservation = new Reservation(passenger, flight);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        Booking booking = new Booking(customer);
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel(reservation.getId(), currentTime);
        
        // Verify
        assertFalse("Cancellation should fail when flight is closed for booking", result);
        assertEquals("Reservation status should remain CONFIRMED", 
                     ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport ap200 = new Airport("AP200");
        City cityAI = new City("CityAI");
        ap200.addCity(cityAI);
        
        Airport ap201 = new Airport("AP201");
        City cityAJ = new City("CityAJ");
        ap201.addCity(cityAJ);
        
        Date departureTime = dateFormat.parse("2025-03-10 10:00:00");
        Flight flight = new Flight(departureTime, new Date(), ap200, ap201);
        flight.setOpenForBooking(true);
        
        Customer customer20 = new Customer();
        Passenger passenger13 = new Passenger("P13");
        
        Reservation reservation405 = new Reservation(passenger13, flight);
        reservation405.setStatus(ReservationStatus.PENDING);
        
        Booking booking20 = new Booking(customer20);
        booking20.getReservations().add(reservation405);
        customer20.getBookings().add(booking20);
        
        Customer customer21 = new Customer();
        Passenger passenger14 = new Passenger("P14");
        
        Reservation reservation406 = new Reservation(passenger14, flight);
        reservation406.setStatus(ReservationStatus.PENDING);
        
        Booking booking21 = new Booking(customer21);
        booking21.getReservations().add(reservation406);
        customer21.getBookings().add(booking21);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - Customer20 tries to confirm Customer21's reservation
        boolean result = customer20.confirm(reservation406.getId(), currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation identifier", result);
        assertEquals("Reservation status should remain PENDING", 
                     ReservationStatus.PENDING, reservation406.getStatus());
    }
}