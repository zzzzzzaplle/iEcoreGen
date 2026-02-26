import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        ap10.setName("AP10");
        ap10.setServedCities(List.of("CityJ"));
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        ap11.setName("AP11");
        ap11.setServedCities(List.of("CityK"));
        
        Flight flightF200 = new Flight();
        flightF200.setId("F200");
        flightF200.setDepartureAirport(ap10);
        flightF200.setArrivalAirport(ap11);
        flightF200.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        flightF200.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        flightF200.setOpenForBooking(true);
        flightF200.setPublished(true);
        
        airlineSystem.getFlights().add(flightF200);
        
        // Set current time to 2025-05-01 08:00:00
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test, we rely on the fact that departure time is in the future
        
        // Execute
        boolean result = airlineSystem.closeFlight("F200");
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertEquals("Flight should be closed", false, flightF200.isOpenForBooking());
        assertEquals("No reservations should exist", 0, airlineSystem.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        ap12.setName("AP12");
        ap12.setServedCities(List.of("CityL"));
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        ap13.setName("AP13");
        ap13.setServedCities(List.of("CityM"));
        
        Flight flightF201 = new Flight();
        flightF201.setId("F201");
        flightF201.setDepartureAirport(ap12);
        flightF201.setArrivalAirport(ap13);
        flightF201.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        flightF201.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        flightF201.setOpenForBooking(true);
        flightF201.setPublished(true);
        
        airlineSystem.getFlights().add(flightF201);
        
        // Create booking with three reservations
        List<String> passengerNames = List.of("Passenger1", "Passenger2", "Passenger3");
        airlineSystem.createBooking(flightF201, passengerNames);
        
        // Confirm all reservations
        for (Reservation reservation : airlineSystem.getReservations()) {
            reservation.setStatus("confirmed");
        }
        
        // Set current time to 2025-06-10 12:00:00
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test, we rely on the fact that departure time is in the future
        
        // Execute
        boolean result = airlineSystem.closeFlight("F201");
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertEquals("Flight should be closed", false, flightF201.isOpenForBooking());
        
        // Check that all three reservations are canceled
        int canceledCount = 0;
        for (Reservation reservation : airlineSystem.getReservations()) {
            if ("canceled".equals(reservation.getStatus())) {
                canceledCount++;
            }
        }
        assertEquals("All three reservations should be canceled", 3, canceledCount);
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup
        Airport ap14 = new Airport();
        ap14.setId("AP14");
        ap14.setName("AP14");
        ap14.setServedCities(List.of("CityN"));
        
        Airport ap15 = new Airport();
        ap15.setId("AP15");
        ap15.setName("AP15");
        ap15.setServedCities(List.of("CityO"));
        
        Flight flightF202 = new Flight();
        flightF202.setId("F202");
        flightF202.setDepartureAirport(ap14);
        flightF202.setArrivalAirport(ap15);
        flightF202.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        flightF202.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        flightF202.setOpenForBooking(false); // Already closed
        flightF202.setPublished(true);
        
        airlineSystem.getFlights().add(flightF202);
        
        // Set current time to 2025-07-01 09:00:00
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test, we rely on the fact that departure time is in the future
        
        // Execute
        boolean result = airlineSystem.closeFlight("F202");
        
        // Verify
        assertFalse("Flight closure should fail when flight is already closed", result);
        assertEquals("Flight should remain closed", false, flightF202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup
        Airport ap16 = new Airport();
        ap16.setId("AP16");
        ap16.setName("AP16");
        ap16.setServedCities(List.of("CityP"));
        
        Airport ap17 = new Airport();
        ap17.setId("AP17");
        ap17.setName("AP17");
        ap17.setServedCities(List.of("CityQ"));
        
        Flight flightF203 = new Flight();
        flightF203.setId("F203");
        flightF203.setDepartureAirport(ap16);
        flightF203.setArrivalAirport(ap17);
        flightF203.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        flightF203.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        flightF203.setOpenForBooking(true);
        flightF203.setPublished(true);
        
        airlineSystem.getFlights().add(flightF203);
        
        // Create two confirmed reservations
        List<String> passengerNames = List.of("Passenger1", "Passenger2");
        airlineSystem.createBooking(flightF203, passengerNames);
        
        // Confirm all reservations
        for (Reservation reservation : airlineSystem.getReservations()) {
            reservation.setStatus("confirmed");
        }
        
        // Set current time to 2025-09-10 09:10:00 (after departure time)
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test case, we need to simulate the time check logic
        // Since we can't modify the LocalDateTime.now() in the actual code,
        // we rely on the fact that the departure time is set to past time relative to test execution
        // In a real test environment, we would use a time mocking framework
        
        // Execute
        boolean result = airlineSystem.closeFlight("F203");
        
        // Verify
        assertFalse("Flight closure should fail when current time is after departure time", result);
        assertEquals("Flight should remain open", true, flightF203.isOpenForBooking());
        
        // Verify reservations remain confirmed (not canceled)
        int confirmedCount = 0;
        for (Reservation reservation : airlineSystem.getReservations()) {
            if ("confirmed".equals(reservation.getStatus())) {
                confirmedCount++;
            }
        }
        assertEquals("Both reservations should remain confirmed", 2, confirmedCount);
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup
        Airport ap18 = new Airport();
        ap18.setId("AP18");
        ap18.setName("AP18");
        ap18.setServedCities(List.of("CityR"));
        
        Airport ap19 = new Airport();
        ap19.setId("AP19");
        ap19.setName("AP19");
        ap19.setServedCities(List.of("CityS"));
        
        Flight flightF204 = new Flight();
        flightF204.setId("F204");
        flightF204.setDepartureAirport(ap18);
        flightF204.setArrivalAirport(ap19);
        flightF204.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        flightF204.setArrivalTime(LocalDateTime.parse("2025-04-01 01:30:00", formatter));
        flightF204.setOpenForBooking(true);
        flightF204.setPublished(true);
        
        airlineSystem.getFlights().add(flightF204);
        
        // Set current time to 2025-04-01 22:05:00 (after departure time)
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test case, we need to simulate the time check logic
        // Since we can't modify the LocalDateTime.now() in the actual code,
        // we rely on the fact that the departure time is set to past time relative to test execution
        // In a real test environment, we would use a time mocking framework
        
        // Execute
        boolean result = airlineSystem.closeFlight("F204");
        
        // Verify
        assertFalse("Flight closure should fail when current time is after departure time", result);
        assertEquals("Flight should remain open", true, flightF204.isOpenForBooking());
    }
}