import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR3Test {
    
    private AirlineSystem airline;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airline = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_confirmPendingReservation() {
        // Setup
        // 1. Create cities
        City cityAA = airline.createCity("CT160", "CityAA");
        City cityAB = airline.createCity("CT161", "CityAB");
        
        // 2. Create airports with cities
        List<City> citiesAP160 = Arrays.asList(cityAA);
        List<City> citiesAP161 = Arrays.asList(cityAB);
        airline.createAirport("AP160", "Airport160", citiesAP160);
        airline.createAirport("AP161", "Airport161", citiesAP161);
        
        // 3. Create flight F401 (OPEN status)
        Flight flight = airline.createFlight("F401", "AP160", "AP161", 
                                           "2025-12-10 11:00:00", "2025-12-10 15:00:00");
        flight.setStatus(FlightStatus.OPEN);
        
        // 4. Create customer CU16
        airline.createCustomer("CU16", "Customer16");
        
        // 5. Create booking BK401 with reservation R401 (PENDING)
        Booking booking = new Booking(airline.getCustomers().get("CU16"), flight);
        Reservation reservation = new Reservation("P9");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setBooking(booking);
        booking.getReservations().add(reservation);
        airline.getReservations().put("R401", reservation);
        airline.getCustomers().get("CU16").getBookings().add(booking);
        
        // 6. Set current time to 2025-11-01 09:00:00 (before flight departure)
        // This requires mocking time, but we'll rely on the actual implementation
        // which uses LocalDateTime.now() - test should pass if run before 2025-12-10
        
        // Test: Confirm reservation R401
        boolean result = airline.updateReservationStatus("R401", true);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", 
                    ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() {
        // Setup
        // 1. Create cities
        City cityAC = airline.createCity("CT170", "CityAC");
        City cityAD = airline.createCity("CT171", "CityAD");
        
        // 2. Create airports with cities
        List<City> citiesAP170 = Arrays.asList(cityAC);
        List<City> citiesAP171 = Arrays.asList(cityAD);
        airline.createAirport("AP170", "Airport170", citiesAP170);
        airline.createAirport("AP171", "Airport171", citiesAP171);
        
        // 3. Create flight F402 (OPEN status)
        Flight flight = airline.createFlight("F402", "AP170", "AP171", 
                                           "2025-12-15 15:00:00", "2025-12-15 19:00:00");
        flight.setStatus(FlightStatus.OPEN);
        
        // 4. Create customer CU17
        airline.createCustomer("CU17", "Customer17");
        
        // 5. Create booking BK402 with reservation R402 (CONFIRMED)
        Booking booking = new Booking(airline.getCustomers().get("CU17"), flight);
        Reservation reservation = new Reservation("P10");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setBooking(booking);
        booking.getReservations().add(reservation);
        airline.getReservations().put("R402", reservation);
        airline.getCustomers().get("CU17").getBookings().add(booking);
        
        // 6. Set current time to 2025-12-01 12:00:00 (before flight departure)
        // This requires mocking time, but we'll rely on the actual implementation
        
        // Test: Cancel reservation R402
        boolean result = airline.updateReservationStatus("R402", false);
        
        // Verify
        assertTrue("Reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELED", 
                    ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() {
        // Setup
        // 1. Create cities
        City cityAE = airline.createCity("CT180", "CityAE");
        City cityAF = airline.createCity("CT181", "CityAF");
        
        // 2. Create airports with cities
        List<City> citiesAP180 = Arrays.asList(cityAE);
        List<City> citiesAP181 = Arrays.asList(cityAF);
        airline.createAirport("AP180", "Airport180", citiesAP180);
        airline.createAirport("AP181", "Airport181", citiesAP181);
        
        // 3. Create flight F403 (OPEN status) with past departure time
        Flight flight = airline.createFlight("F403", "AP180", "AP181", 
                                           "2025-01-05 06:00:00", "2025-01-05 10:00:00");
        flight.setStatus(FlightStatus.OPEN);
        
        // 4. Create reservation R403 (PENDING)
        Booking booking = new Booking(new Customer("CU18", "Customer18"), flight);
        Reservation reservation = new Reservation("P11");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setBooking(booking);
        booking.getReservations().add(reservation);
        airline.getReservations().put("R403", reservation);
        
        // 5. Current time is 2025-01-05 07:00:00 (after flight departure)
        // This requires mocking time, but we'll rely on the actual implementation
        // which uses LocalDateTime.now() - test will fail if run after 2025-01-05
        
        // Test: Try to confirm reservation R403 (should fail due to departed flight)
        boolean result = airline.updateReservationStatus("R403", true);
        
        // Verify
        assertFalse("Confirmation should fail for departed flight", result);
        assertEquals("Reservation status should remain PENDING", 
                    ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() {
        // Setup
        // 1. Create cities
        City cityAG = airline.createCity("CT190", "CityAG");
        City cityAH = airline.createCity("CT191", "CityAH");
        
        // 2. Create airports with cities
        List<City> citiesAP190 = Arrays.asList(cityAG);
        List<City> citiesAP191 = Arrays.asList(cityAH);
        airline.createAirport("AP190", "Airport190", citiesAP190);
        airline.createAirport("AP191", "Airport191", citiesAP191);
        
        // 3. Create flight F404 (CLOSED status)
        Flight flight = airline.createFlight("F404", "AP190", "AP191", 
                                           "2025-02-01 09:00:00", "2025-02-01 13:00:00");
        flight.setStatus(FlightStatus.CLOSED);
        
        // 4. Create reservation R404 (CONFIRMED)
        Booking booking = new Booking(new Customer("CU19", "Customer19"), flight);
        Reservation reservation = new Reservation("P12");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setBooking(booking);
        booking.getReservations().add(reservation);
        airline.getReservations().put("R404", reservation);
        
        // 5. Current time is 2025-01-20 08:00:00 (before flight departure)
        // This requires mocking time, but we'll rely on the actual implementation
        
        // Test: Try to cancel reservation R404 (should fail due to closed flight)
        boolean result = airline.updateReservationStatus("R404", false);
        
        // Verify
        assertFalse("Cancellation should fail for closed flight", result);
        assertEquals("Reservation status should remain CONFIRMED", 
                    ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() {
        // Setup
        // 1. Create cities
        City cityAI = airline.createCity("CT200", "CityAI");
        City cityAJ = airline.createCity("CT201", "CityAJ");
        
        // 2. Create airports with cities
        List<City> citiesAP200 = Arrays.asList(cityAI);
        List<City> citiesAP201 = Arrays.asList(cityAJ);
        airline.createAirport("AP200", "Airport200", citiesAP200);
        airline.createAirport("AP201", "Airport201", citiesAP201);
        
        // 3. Create flight F405 (OPEN status)
        Flight flight = airline.createFlight("F405", "AP200", "AP201", 
                                           "2025-03-10 10:00:00", "2025-03-10 14:00:00");
        flight.setStatus(FlightStatus.OPEN);
        
        // 4. Create customer CU20 with reservation R405 (PENDING)
        airline.createCustomer("CU20", "Customer20");
        Booking booking1 = new Booking(airline.getCustomers().get("CU20"), flight);
        Reservation reservation1 = new Reservation("P13");
        reservation1.setStatus(ReservationStatus.PENDING);
        reservation1.setBooking(booking1);
        booking1.getReservations().add(reservation1);
        airline.getReservations().put("R405", reservation1);
        airline.getCustomers().get("CU20").getBookings().add(booking1);
        
        // 5. Create customer CU21 with reservation R406 (PENDING)  
        airline.createCustomer("CU21", "Customer21");
        Booking booking2 = new Booking(airline.getCustomers().get("CU21"), flight);
        Reservation reservation2 = new Reservation("P14");
        reservation2.setStatus(ReservationStatus.PENDING);
        reservation2.setBooking(booking2);
        booking2.getReservations().add(reservation2);
        airline.getReservations().put("R406", reservation2);
        airline.getCustomers().get("CU21").getBookings().add(booking2);
        
        // 6. Current time is 2025-02-15 09:00:00 (before flight departure)
        // This requires mocking time, but we'll rely on the actual implementation
        
        // Test: Customer CU20 tries to confirm unknown reservation R407
        boolean result = airline.updateReservationStatus("R407", true);
        
        // Verify
        assertFalse("Operation should fail for unknown reservation", result);
    }
}