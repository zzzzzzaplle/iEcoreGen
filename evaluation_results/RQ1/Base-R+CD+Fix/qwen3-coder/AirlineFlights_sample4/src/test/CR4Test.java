import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR4Test {
    
    private Airline airline;
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        airline = new Airline();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() throws Exception {
        // Setup
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        City cityJ = new City();
        ap10.addCity(cityJ);
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        City cityK = new City();
        ap11.addCity(cityK);
        
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setDepartureTime(sdf.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(sdf.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setOpenForBooking(true);
        
        airline.addFlight(f200);
        
        Date currentTime = sdf.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, f200.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        City cityL = new City();
        ap12.addCity(cityL);
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        City cityM = new City();
        ap13.addCity(cityM);
        
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setDepartureTime(sdf.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(sdf.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        f201.setOpenForBooking(true);
        
        // Create customer and booking with 3 confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        
        Date bookingTime = sdf.parse("2025-06-01 10:00:00");
        boolean bookingResult = customer.addBooking(f201, bookingTime, passengerNames);
        assertTrue("Booking should be created successfully", bookingResult);
        
        // Confirm all reservations
        for (Reservation reservation : f201.getReservations()) {
            boolean confirmResult = customer.confirm(reservation.getId(), bookingTime);
            assertTrue("Reservation should be confirmed", confirmResult);
            assertEquals("Reservation status should be CONFIRMED", 
                         ReservationStatus.CONFIRMED, reservation.getStatus());
        }
        
        airline.addFlight(f201);
        
        Date currentTime = sdf.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        for (Reservation reservation : f201.getReservations()) {
            assertEquals("All confirmed reservations should be canceled", 
                         ReservationStatus.CANCELED, reservation.getStatus());
        }
        assertEquals("All 3 reservations should be present", 3, f201.getReservations().size());
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup
        Airport ap14 = new Airport();
        ap14.setId("AP14");
        City cityN = new City();
        ap14.addCity(cityN);
        
        Airport ap15 = new Airport();
        ap15.setId("AP15");
        City cityO = new City();
        ap15.addCity(cityO);
        
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureTime(sdf.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(sdf.parse("2025-08-10 13:30:00"));
        f202.setDepartureAirport(ap14);
        f202.setArrivalAirport(ap15);
        f202.setOpenForBooking(false); // Flight already closed
        
        airline.addFlight(f202);
        
        Date currentTime = sdf.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Should return false when flight is already closed", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Airport ap16 = new Airport();
        ap16.setId("AP16");
        City cityP = new City();
        ap16.addCity(cityP);
        
        Airport ap17 = new Airport();
        ap17.setId("AP17");
        City cityQ = new City();
        ap17.addCity(cityQ);
        
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureTime(sdf.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(sdf.parse("2025-09-10 15:30:00"));
        f203.setDepartureAirport(ap16);
        f203.setArrivalAirport(ap17);
        f203.setOpenForBooking(true);
        
        // Create customer and booking with 2 confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("PassengerA", "PassengerB");
        
        Date bookingTime = sdf.parse("2025-08-01 10:00:00");
        boolean bookingResult = customer.addBooking(f203, bookingTime, passengerNames);
        assertTrue("Booking should be created successfully", bookingResult);
        
        // Confirm all reservations
        for (Reservation reservation : f203.getReservations()) {
            boolean confirmResult = customer.confirm(reservation.getId(), bookingTime);
            assertTrue("Reservation should be confirmed", confirmResult);
        }
        
        airline.addFlight(f203);
        
        Date currentTime = sdf.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should return false when trying to close after departure time", result);
        assertTrue("Flight should remain open", f203.isOpenForBooking());
        
        // Check that reservations remain confirmed
        for (Reservation reservation : f203.getReservations()) {
            assertEquals("Reservations should remain CONFIRMED", 
                         ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        Airport ap18 = new Airport();
        ap18.setId("AP18");
        City cityR = new City();
        ap18.addCity(cityR);
        
        Airport ap19 = new Airport();
        ap19.setId("AP19");
        City cityS = new City();
        ap19.addCity(cityS);
        
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureTime(sdf.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(sdf.parse("2025-04-01 01:30:00"));
        f204.setDepartureAirport(ap18);
        f204.setArrivalAirport(ap19);
        f204.setOpenForBooking(true);
        
        airline.addFlight(f204);
        
        Date currentTime = sdf.parse("2025-04-01 22:05:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should return false when trying to close after departure", result);
        assertTrue("Flight should remain open", f204.isOpenForBooking());
    }
}