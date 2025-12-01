import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CR4Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport ap10 = new Airport("AP10");
        City cityJ = new City("CityJ");
        ap10.addCity(cityJ);
        
        Airport ap11 = new Airport("AP11");
        City cityK = new City("CityK");
        ap11.addCity(cityK);
        
        Date departureTime = dateFormat.parse("2025-06-20 09:00:00");
        Date arrivalTime = dateFormat.parse("2025-06-20 13:00:00");
        Flight f200 = new Flight(departureTime, arrivalTime, ap10, ap11);
        f200.setOpenForBooking(true);
        airline.addFlight(f200);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, f200.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport ap12 = new Airport("AP12");
        City cityL = new City("CityL");
        ap12.addCity(cityL);
        
        Airport ap13 = new Airport("AP13");
        City cityM = new City("CityM");
        ap13.addCity(cityM);
        
        Date departureTime = dateFormat.parse("2025-07-15 14:00:00");
        Date arrivalTime = dateFormat.parse("2025-07-15 18:00:00");
        Flight f201 = new Flight(departureTime, arrivalTime, ap12, ap13);
        f201.setOpenForBooking(true);
        airline.addFlight(f201);
        
        // Create customer and confirmed reservations
        Customer customer = new Customer();
        Passenger passenger1 = new Passenger("Passenger1");
        Passenger passenger2 = new Passenger("Passenger2");
        Passenger passenger3 = new Passenger("Passenger3");
        
        Reservation r201_1 = new Reservation(passenger1, f201);
        r201_1.setStatus(ReservationStatus.CONFIRMED);
        Reservation r201_2 = new Reservation(passenger2, f201);
        r201_2.setStatus(ReservationStatus.CONFIRMED);
        Reservation r201_3 = new Reservation(passenger3, f201);
        r201_3.setStatus(ReservationStatus.CONFIRMED);
        
        Booking booking = new Booking(customer);
        booking.getReservations().add(r201_1);
        booking.getReservations().add(r201_2);
        booking.getReservations().add(r201_3);
        customer.getBookings().add(booking);
        
        f201.getReservations().add(r201_1);
        f201.getReservations().add(r201_2);
        f201.getReservations().add(r201_3);
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        assertEquals("All reservations should be canceled", 3, f201.getReservations().size());
        for (Reservation reservation : f201.getReservations()) {
            assertEquals("Reservation should be canceled", ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport("DEP");
        Airport arrivalAirport = new Airport("ARR");
        
        Date departureTime = dateFormat.parse("2025-08-10 11:00:00");
        Date arrivalTime = dateFormat.parse("2025-08-10 13:30:00");
        Flight f202 = new Flight(departureTime, arrivalTime, departureAirport, arrivalAirport);
        f202.setOpenForBooking(false); // Flight already closed
        airline.addFlight(f202);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport("DEP");
        Airport arrivalAirport = new Airport("ARR");
        
        Date departureTime = dateFormat.parse("2025-09-10 09:00:00");
        Date arrivalTime = dateFormat.parse("2025-09-10 15:30:00");
        Flight f203 = new Flight(departureTime, arrivalTime, departureAirport, arrivalAirport);
        f203.setOpenForBooking(true);
        airline.addFlight(f203);
        
        // Add confirmed reservations
        Passenger passenger1 = new Passenger("Passenger1");
        Passenger passenger2 = new Passenger("Passenger2");
        
        Reservation r203_1 = new Reservation(passenger1, f203);
        r203_1.setStatus(ReservationStatus.CONFIRMED);
        Reservation r203_2 = new Reservation(passenger2, f203);
        r203_2.setStatus(ReservationStatus.CONFIRMED);
        
        f203.getReservations().add(r203_1);
        f203.getReservations().add(r203_2);
        
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should return false when closing after departure time", result);
        assertTrue("Flight should remain open", f203.isOpenForBooking());
        
        // Check that reservations remain confirmed (not canceled)
        for (Reservation reservation : f203.getReservations()) {
            assertEquals("Reservations should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport("DEP");
        Airport arrivalAirport = new Airport("ARR");
        
        Date departureTime = dateFormat.parse("2025-04-01 22:00:00");
        Date arrivalTime = dateFormat.parse("2025-04-01 01:30:00");
        Flight f204 = new Flight(departureTime, arrivalTime, departureAirport, arrivalAirport);
        f204.setOpenForBooking(true);
        airline.addFlight(f204);
        
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should return false when closing after departure", result);
        assertTrue("Flight should remain open", f204.isOpenForBooking());
    }
}