import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() throws Exception {
        // Setup
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        City cityJ = new City();
        cityJ.setName("CityJ");
        ap10.addCity(cityJ);
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        City cityK = new City();
        cityK.setName("CityK");
        ap11.addCity(cityK);
        
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setOpenForBooking(true);
        
        airline.addFlight(f200);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed", f200.isOpenForBooking());
        assertEquals("No reservations should be cancelled", 0, f200.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        City cityL = new City();
        cityL.setName("CityL");
        ap12.addCity(cityL);
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        City cityM = new City();
        cityM.setName("CityM");
        ap13.addCity(cityM);
        
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        f201.setOpenForBooking(true);
        
        // Create customer and booking with three confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        
        // Create booking and confirm reservations
        boolean bookingCreated = customer.addBooking(f201, dateFormat.parse("2025-06-01 10:00:00"), passengerNames);
        assertTrue("Booking should be created successfully", bookingCreated);
        
        // Confirm all reservations
        List<Reservation> reservations = f201.getReservations();
        for (Reservation reservation : reservations) {
            boolean confirmed = customer.confirm(reservation.getId(), dateFormat.parse("2025-06-01 11:00:00"));
            assertTrue("Reservation should be confirmed", confirmed);
            assertEquals("Reservation should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
        
        airline.addFlight(f201);
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed", f201.isOpenForBooking());
        
        // Check that all confirmed reservations are cancelled
        for (Reservation reservation : f201.getReservations()) {
            assertEquals("All confirmed reservations should be cancelled", 
                         ReservationStatus.CANCELLED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws Exception {
        // Setup
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(dateFormat.parse("2025-08-10 15:00:00"));
        f202.setOpenForBooking(false); // Flight is already closed
        
        Airport depAirport = new Airport();
        depAirport.setId("DEP1");
        Airport arrAirport = new Airport();
        arrAirport.setId("ARR1");
        f202.setDepartureAirport(depAirport);
        f202.setArrivalAirport(arrAirport);
        
        airline.addFlight(f202);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Close flight should return false for already closed flight", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDay() throws Exception {
        // Setup
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 13:00:00"));
        f203.setOpenForBooking(true);
        
        Airport depAirport = new Airport();
        depAirport.setId("DEP2");
        Airport arrAirport = new Airport();
        arrAirport.setId("ARR2");
        f203.setDepartureAirport(depAirport);
        f203.setArrivalAirport(arrAirport);
        
        // Create two confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("PassengerA", "PassengerB");
        boolean bookingCreated = customer.addBooking(f203, dateFormat.parse("2025-08-01 10:00:00"), passengerNames);
        assertTrue("Booking should be created successfully", bookingCreated);
        
        // Confirm reservations
        List<Reservation> reservations = f203.getReservations();
        for (Reservation reservation : reservations) {
            boolean confirmed = customer.confirm(reservation.getId(), dateFormat.parse("2025-08-01 11:00:00"));
            assertTrue("Reservation should be confirmed", confirmed);
        }
        
        airline.addFlight(f203);
        
        Date currentTime = dateFormat.parse("2025-09-10 05:00:00"); // Same day as departure
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Close flight should return false when attempted on departure day", result);
        assertTrue("Flight should remain open", f203.isOpenForBooking());
        
        // Verify reservations are still confirmed (not cancelled)
        for (Reservation reservation : f203.getReservations()) {
            assertEquals("Reservations should remain CONFIRMED", 
                         ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws Exception {
        // Setup
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureTime(dateFormat.parse("2025-04-01 10:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-01 14:00:00"));
        f204.setOpenForBooking(true);
        
        Airport depAirport = new Airport();
        depAirport.setId("DEP3");
        Airport arrAirport = new Airport();
        arrAirport.setId("ARR3");
        f204.setDepartureAirport(depAirport);
        f204.setArrivalAirport(arrAirport);
        
        airline.addFlight(f204);
        
        Date currentTime = dateFormat.parse("2025-04-01 12:00:00"); // After departure
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Close flight should return false when attempted after departure", result);
        assertTrue("Flight should remain open", f204.isOpenForBooking());
    }
}