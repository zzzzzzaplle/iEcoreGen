import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed for booking", f200.isOpenForBooking());
        assertEquals("No reservations should exist", 0, f200.getReservations().size());
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
        f201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        f201.setOpenForBooking(true);
        
        airline.addFlight(f201);
        
        // Create customer and reservations
        Customer customer = new Customer();
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Passenger1");
        passengerNames.add("Passenger2");
        passengerNames.add("Passenger3");
        
        Date bookingTime = dateFormat.parse("2025-06-09 12:00:00");
        boolean bookingResult = customer.addBooking(f201, bookingTime, passengerNames);
        assertTrue("Booking should be successful", bookingResult);
        
        // Confirm all reservations
        for (Booking booking : customer.getBookings()) {
            for (Reservation reservation : booking.getReservations()) {
                Date confirmTime = dateFormat.parse("2025-06-09 13:00:00");
                boolean confirmResult = customer.confirm(reservation.getId(), confirmTime);
                assertTrue("Reservation should be confirmed", confirmResult);
                assertEquals("Reservation should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
            }
        }
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed for booking", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        for (Reservation reservation : f201.getReservations()) {
            assertEquals("All reservations should be CANCELED", ReservationStatus.CANCELED, reservation.getStatus());
        }
        assertEquals("Should have 3 reservations", 3, f201.getReservations().size());
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup
        Airport apUnknown = new Airport();
        apUnknown.setId("APX");
        City cityX = new City();
        cityX.setName("CityX");
        apUnknown.addCity(cityX);
        
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        f202.setDepartureAirport(apUnknown);
        f202.setArrivalAirport(apUnknown);
        f202.setOpenForBooking(false); // Already closed
        
        airline.addFlight(f202);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Should not be able to close already closed flight", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Airport apUnknown = new Airport();
        apUnknown.setId("APY");
        City cityY = new City();
        cityY.setName("CityY");
        apUnknown.addCity(cityY);
        
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        f203.setDepartureAirport(apUnknown);
        f203.setArrivalAirport(apUnknown);
        f203.setOpenForBooking(true);
        
        airline.addFlight(f203);
        
        // Create confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("PassengerA");
        passengerNames.add("PassengerB");
        
        Date bookingTime = dateFormat.parse("2025-09-09 10:00:00");
        boolean bookingResult = customer.addBooking(f203, bookingTime, passengerNames);
        assertTrue("Booking should be successful", bookingResult);
        
        // Confirm reservations
        for (Booking booking : customer.getBookings()) {
            for (Reservation reservation : booking.getReservations()) {
                Date confirmTime = dateFormat.parse("2025-09-09 11:00:00");
                boolean confirmResult = customer.confirm(reservation.getId(), confirmTime);
                assertTrue("Reservation should be confirmed", confirmResult);
            }
        }
        
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should not be able to close flight after departure time", result);
        assertTrue("Flight should remain open for booking", f203.isOpenForBooking());
        
        // Check that reservations remain confirmed
        for (Reservation reservation : f203.getReservations()) {
            assertEquals("Reservations should remain CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        Airport apUnknown = new Airport();
        apUnknown.setId("APZ");
        City cityZ = new City();
        cityZ.setName("CityZ");
        apUnknown.addCity(cityZ);
        
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-01 01:30:00")); // Next day arrival
        f204.setDepartureAirport(apUnknown);
        f204.setArrivalAirport(apUnknown);
        f204.setOpenForBooking(true);
        
        airline.addFlight(f204);
        
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should not be able to close flight after departure", result);
        assertTrue("Flight should remain open for booking", f204.isOpenForBooking());
    }
}