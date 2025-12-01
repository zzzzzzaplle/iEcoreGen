import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR4Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() throws Exception {
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
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        f200.setOpenForBooking(true);
        
        airline.addFlight(f200);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Should return true when closing flight with no reservations", result);
        assertFalse("Flight should be closed after successful close operation", f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, countCanceledReservations(f200));
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
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
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        f201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        f201.setOpenForBooking(true);
        
        // Create customer and booking with three confirmed reservations
        Customer customer = new Customer();
        
        // Create reservations directly on flight
        Reservation r201_1 = f201.createReservation("Passenger1");
        Reservation r201_2 = f201.createReservation("Passenger2");
        Reservation r201_3 = f201.createReservation("Passenger3");
        
        // Confirm reservations
        r201_1.setStatus(ReservationStatus.CONFIRMED);
        r201_2.setStatus(ReservationStatus.CONFIRMED);
        r201_3.setStatus(ReservationStatus.CONFIRMED);
        
        airline.addFlight(f201);
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Should return true when closing flight with confirmed reservations", result);
        assertFalse("Flight should be closed after successful close operation", f201.isOpenForBooking());
        assertEquals("All three confirmed reservations should be canceled", 3, countCanceledReservations(f201));
        assertEquals("Reservation R201-1 should be canceled", ReservationStatus.CANCELED, r201_1.getStatus());
        assertEquals("Reservation R201-2 should be canceled", ReservationStatus.CANCELED, r201_2.getStatus());
        assertEquals("Reservation R201-3 should be canceled", ReservationStatus.CANCELED, r201_3.getStatus());
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup
        Airport ap14 = new Airport();
        ap14.setId("AP14");
        Airport ap15 = new Airport();
        ap15.setId("AP15");
        
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureAirport(ap14);
        f202.setArrivalAirport(ap15);
        f202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        f202.setOpenForBooking(false); // Already closed
        
        airline.addFlight(f202);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
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
        Airport ap17 = new Airport();
        ap17.setId("AP17");
        
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureAirport(ap16);
        f203.setArrivalAirport(ap17);
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        f203.setOpenForBooking(true);
        
        // Create two confirmed reservations
        Reservation r203_1 = f203.createReservation("Passenger1");
        Reservation r203_2 = f203.createReservation("Passenger2");
        r203_1.setStatus(ReservationStatus.CONFIRMED);
        r203_2.setStatus(ReservationStatus.CONFIRMED);
        
        airline.addFlight(f203);
        
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should return false when trying to close flight after departure time", result);
        assertTrue("Flight should remain open when close operation fails", f203.isOpenForBooking());
        assertEquals("Reservations should remain confirmed when close fails", 
                     ReservationStatus.CONFIRMED, r203_1.getStatus());
        assertEquals("Reservations should remain confirmed when close fails", 
                     ReservationStatus.CONFIRMED, r203_2.getStatus());
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        Airport ap18 = new Airport();
        ap18.setId("AP18");
        Airport ap19 = new Airport();
        ap19.setId("AP19");
        
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureAirport(ap18);
        f204.setArrivalAirport(ap19);
        f204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-02 01:30:00")); // Fixed date format
        f204.setOpenForBooking(true);
        
        airline.addFlight(f204);
        
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should return false when trying to close flight after departure", result);
        assertTrue("Flight should remain open when close operation fails", f204.isOpenForBooking());
    }
    
    // Helper method to count canceled reservations
    private int countCanceledReservations(Flight flight) {
        int count = 0;
        for (Reservation reservation : flight.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CANCELED) {
                count++;
            }
        }
        return count;
    }
}