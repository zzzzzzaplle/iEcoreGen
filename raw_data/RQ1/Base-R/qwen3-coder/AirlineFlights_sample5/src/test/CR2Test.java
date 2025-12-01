import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    private DateTimeFormatter formatter;
    private LocalDateTime currentTime;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() {
        // Setup
        currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Create airline AL11 (implicit in the flight creation)
        Airport ap100 = new Airport();
        ap100.setId("AP100");
        ap100.addCity("City100");
        
        Airport ap101 = new Airport();
        ap101.setId("AP101");
        ap101.addCity("City101");
        
        Flight f300 = new Flight();
        f300.setId("F300");
        f300.setDepartureAirport(ap100);
        f300.setArrivalAirport(ap101);
        f300.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        f300.setArrivalTime(LocalDateTime.parse("2025-10-05 12:00:00", formatter));
        f300.setStatus(FlightStatus.OPEN);
        
        Customer cua = new Customer();
        
        Passenger p1 = new Passenger();
        p1.setName("Peter");
        
        Passenger p2 = new Passenger();
        p2.setName("Beck");
        
        List<Passenger> passengers = Arrays.asList(p1, p2);
        
        // Test
        boolean result = cua.createBooking(f300, passengers);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have one booking", 1, cua.getBookings().size());
        assertEquals("Booking should contain two reservations", 2, cua.getBookings().get(0).getReservations().size());
        assertEquals("Flight should have two reservations", 2, f300.getReservations().size());
        
        // Check reservation details
        List<Reservation> reservations = cua.getBookings().get(0).getReservations();
        assertEquals("Peter should have a reservation", "Peter", reservations.get(0).getPassenger().getName());
        assertEquals("Beck should have a reservation", "Beck", reservations.get(1).getPassenger().getName());
        assertEquals("Reservations should be pending", ReservationStatus.PENDING, reservations.get(0).getStatus());
        assertEquals("Reservations should be pending", ReservationStatus.PENDING, reservations.get(1).getStatus());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() {
        // Setup
        currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        Airport ap102 = new Airport();
        ap102.setId("AP102");
        ap102.addCity("City102");
        
        Airport ap103 = new Airport();
        ap103.setId("AP103");
        ap103.addCity("City103");
        
        Flight f301 = new Flight();
        f301.setId("F301");
        f301.setDepartureAirport(ap102);
        f301.setArrivalAirport(ap103);
        f301.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        f301.setArrivalTime(LocalDateTime.parse("2025-10-05 10:00:00", formatter));
        f301.setStatus(FlightStatus.OPEN);
        
        Customer cub = new Customer();
        
        Passenger p3 = new Passenger();
        p3.setName("Alice");
        
        // Create duplicate passengers with same name
        List<Passenger> passengers = Arrays.asList(p3, p3);
        
        // Test
        boolean result = cub.createBooking(f301, passengers);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passengers", result);
        assertEquals("Customer should have no bookings", 0, cub.getBookings().size());
        assertEquals("Flight should have no reservations", 0, f301.getReservations().size());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() {
        // Setup
        currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        ap104.addCity("City104");
        
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        ap105.addCity("City105");
        
        Flight f302 = new Flight();
        f302.setId("F302");
        f302.setDepartureAirport(ap104);
        f302.setArrivalAirport(ap105);
        f302.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f302.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f302.setStatus(FlightStatus.OPEN);
        
        Customer cuc = new Customer();
        
        Passenger p4 = new Passenger();
        p4.setName("Jucy");
        
        // Create pre-existing booking
        Booking oldBooking = new Booking();
        Reservation oldReservation = new Reservation();
        oldReservation.setFlight(f302);
        oldReservation.setPassenger(p4);
        oldReservation.setStatus(ReservationStatus.PENDING);
        oldBooking.getReservations().add(oldReservation);
        f302.getReservations().add(oldReservation);
        cuc.getBookings().add(oldBooking);
        
        List<Passenger> passengers = Arrays.asList(p4);
        
        // Test
        boolean result = cuc.createBooking(f302, passengers);
        
        // Verify
        assertFalse("Booking should fail due to existing passenger booking", result);
        assertEquals("Customer should still have only one booking", 1, cuc.getBookings().size());
        assertEquals("Flight should still have only one reservation", 1, f302.getReservations().size());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() {
        // Setup
        currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        ap106.addCity("City106");
        
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        ap107.addCity("City107");
        
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f303.setStatus(FlightStatus.CLOSED);
        
        Customer cud = new Customer();
        
        Passenger p5 = new Passenger();
        p5.setName("Ruby");
        
        List<Passenger> passengers = Arrays.asList(p5);
        
        // Test
        boolean result = cud.createBooking(f303, passengers);
        
        // Verify
        assertFalse("Booking should fail due to closed flight", result);
        assertEquals("Customer should have no bookings", 0, cud.getBookings().size());
        assertEquals("Flight should have no reservations", 0, f303.getReservations().size());
    }
    
    @Test
    public void testCase5_timeAfterDepartureTime() {
        // Setup - Note: Flight ID F303 reused but with different status and airports
        currentTime = LocalDateTime.parse("2025-10-06 09:00:00", formatter);
        
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        ap104.addCity("City104");
        
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        ap105.addCity("City105");
        
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap104);
        f303.setArrivalAirport(ap105);
        f303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f303.setStatus(FlightStatus.OPEN);
        
        Customer cud = new Customer();
        
        Passenger p5 = new Passenger();
        p5.setName("Ruby");
        
        List<Passenger> passengers = Arrays.asList(p5);
        
        // Test
        boolean result = cud.createBooking(f303, passengers);
        
        // Verify
        assertFalse("Booking should fail due to current time after departure time", result);
        assertEquals("Customer should have no bookings", 0, cud.getBookings().size());
        assertEquals("Flight should have no reservations", 0, f303.getReservations().size());
    }
}