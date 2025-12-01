import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

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
        City cityJ = new City();
        cityJ.setName("CityJ");
        ap10.addCity(cityJ);
        
        Airport ap11 = new Airport();
        City cityK = new City();
        cityK.setName("CityK");
        ap11.addCity(cityK);
        
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setOpenForBooking(true);
        f200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        
        airline.addFlight(f200);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertEquals("Flight should be closed", false, f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, f200.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Airport ap12 = new Airport();
        City cityL = new City();
        cityL.setName("CityL");
        ap12.addCity(cityL);
        
        Airport ap13 = new Airport();
        City cityM = new City();
        cityM.setName("CityM");
        ap13.addCity(cityM);
        
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setOpenForBooking(true);
        f201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        
        airline.addFlight(f201);
        
        // Create customer and booking with three confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        
        Date bookingTime = dateFormat.parse("2025-06-01 10:00:00");
        boolean bookingCreated = customer.addBooking(f201, bookingTime, passengerNames);
        assertTrue("Booking should be created successfully", bookingCreated);
        
        // Confirm all reservations
        for (Reservation reservation : customer.getBookings().get(0).getReservations()) {
            boolean confirmed = customer.confirm(reservation.getId(), bookingTime);
            assertTrue("Reservation should be confirmed", confirmed);
            assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertEquals("Flight should be closed", false, f201.isOpenForBooking());
        
        // Check that all three reservations are canceled
        int canceledCount = 0;
        for (Reservation reservation : f201.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CANCELED) {
                canceledCount++;
            }
        }
        assertEquals("All three reservations should be canceled", 3, canceledCount);
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws Exception {
        // Setup
        Airport ap14 = new Airport();
        City cityN = new City();
        cityN.setName("CityN");
        ap14.addCity(cityN);
        
        Airport ap15 = new Airport();
        City cityO = new City();
        cityO.setName("CityO");
        ap15.addCity(cityO);
        
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setOpenForBooking(false); // Flight already closed
        f202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        f202.setDepartureAirport(ap14);
        f202.setArrivalAirport(ap15);
        
        airline.addFlight(f202);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Flight closure should fail when flight is already closed", result);
        assertEquals("Flight should remain closed", false, f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Airport ap16 = new Airport();
        City cityP = new City();
        cityP.setName("CityP");
        ap16.addCity(cityP);
        
        Airport ap17 = new Airport();
        City cityQ = new City();
        cityQ.setName("CityQ");
        ap17.addCity(cityQ);
        
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setOpenForBooking(true);
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        f203.setDepartureAirport(ap16);
        f203.setArrivalAirport(ap17);
        
        airline.addFlight(f203);
        
        // Add two confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("PassengerA", "PassengerB");
        
        Date bookingTime = dateFormat.parse("2025-08-01 10:00:00");
        boolean bookingCreated = customer.addBooking(f203, bookingTime, passengerNames);
        assertTrue("Booking should be created successfully", bookingCreated);
        
        // Confirm all reservations
        for (Reservation reservation : customer.getBookings().get(0).getReservations()) {
            boolean confirmed = customer.confirm(reservation.getId(), bookingTime);
            assertTrue("Reservation should be confirmed", confirmed);
        }
        
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Flight closure should fail when current time is after departure time", result);
        assertEquals("Flight should remain open", true, f203.isOpenForBooking());
        
        // Reservations should remain confirmed (not canceled)
        int confirmedCount = 0;
        for (Reservation reservation : f203.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                confirmedCount++;
            }
        }
        assertEquals("Both reservations should remain confirmed", 2, confirmedCount);
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws Exception {
        // Setup
        Airport ap18 = new Airport();
        City cityR = new City();
        cityR.setName("CityR");
        ap18.addCity(cityR);
        
        Airport ap19 = new Airport();
        City cityS = new City();
        cityS.setName("CityS");
        ap19.addCity(cityS);
        
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setOpenForBooking(true);
        f204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-02 01:30:00")); // Note: Fixed date for arrival (next day)
        f204.setDepartureAirport(ap18);
        f204.setArrivalAirport(ap19);
        
        airline.addFlight(f204);
        
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Flight closure should fail when current time is after departure time", result);
        assertEquals("Flight should remain open", true, f204.isOpenForBooking());
    }
}