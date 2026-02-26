import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR5Test {
    
    private AirlineSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup
        // Create cities and airports
        City city1 = system.createCity("C501", "City501");
        City city2 = system.createCity("C502", "City502");
        List<City> cities1 = Arrays.asList(city1);
        List<City> cities2 = Arrays.asList(city2);
        system.createAirport("AP501", "Airport501", cities1);
        system.createAirport("AP502", "Airport502", cities2);
        
        // Create flight F501 with OPEN status
        String futureTime = LocalDateTime.now().plusDays(1).format(formatter);
        String furtherFutureTime = LocalDateTime.now().plusDays(2).format(formatter);
        Flight flight = system.createFlight("F501", "AP501", "AP502", futureTime, furtherFutureTime);
        flight.setStatus(FlightStatus.OPEN);
        
        // Create customer
        system.createCustomer("CUST501", "Customer501");
        
        // Create booking with three confirmed reservations
        List<String> passengerNames = Arrays.asList("Passenger501-1", "Passenger501-2", "Passenger501-3");
        system.createBooking("F501", "CUST501", passengerNames);
        
        // Update all reservations to CONFIRMED status
        for (Reservation reservation : system.getReservations().values()) {
            system.updateReservationStatus(reservation.getId(), true);
        }
        
        // Test
        List<Reservation> result = system.getConfirmedReservations("F501");
        
        // Verify
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        Set<String> passengerNamesSet = new HashSet<>();
        for (Reservation res : result) {
            passengerNamesSet.add(res.getPassengerName());
            assertEquals("Reservation should be CONFIRMED", ReservationStatus.CONFIRMED, res.getStatus());
        }
        assertTrue("Should contain Passenger501-1", passengerNamesSet.contains("Passenger501-1"));
        assertTrue("Should contain Passenger501-2", passengerNamesSet.contains("Passenger501-2"));
        assertTrue("Should contain Passenger501-3", passengerNamesSet.contains("Passenger501-3"));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup
        // Create cities and airports
        City city1 = system.createCity("C503", "City503");
        City city2 = system.createCity("C504", "City504");
        List<City> cities1 = Arrays.asList(city1);
        List<City> cities2 = Arrays.asList(city2);
        system.createAirport("AP503", "Airport503", cities1);
        system.createAirport("AP504", "Airport504", cities2);
        
        // Create flight F502 with OPEN status
        String futureTime = LocalDateTime.now().plusDays(1).format(formatter);
        String furtherFutureTime = LocalDateTime.now().plusDays(2).format(formatter);
        Flight flight = system.createFlight("F502", "AP503", "AP504", futureTime, furtherFutureTime);
        flight.setStatus(FlightStatus.OPEN);
        
        // Create customer
        system.createCustomer("CUST502", "Customer502");
        
        // Create booking with two PENDING reservations (do not confirm them)
        List<String> passengerNames = Arrays.asList("Passenger502-1", "Passenger502-2");
        system.createBooking("F502", "CUST502", passengerNames);
        
        // Test
        List<Reservation> result = system.getConfirmedReservations("F502");
        
        // Verify
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup
        // Create cities and airports
        City city1 = system.createCity("C505", "City505");
        City city2 = system.createCity("C506", "City506");
        List<City> cities1 = Arrays.asList(city1);
        List<City> cities2 = Arrays.asList(city2);
        system.createAirport("AP505", "Airport505", cities1);
        system.createAirport("AP506", "Airport506", cities2);
        
        // Create flight F503 with CLOSED status
        String futureTime = LocalDateTime.now().plusDays(1).format(formatter);
        String furtherFutureTime = LocalDateTime.now().plusDays(2).format(formatter);
        Flight flight = system.createFlight("F503", "AP505", "AP506", futureTime, furtherFutureTime);
        flight.setStatus(FlightStatus.CLOSED);
        
        // Create customer
        system.createCustomer("CUST503", "Customer503");
        
        // Create booking with one confirmed reservation
        List<String> passengerNames = Arrays.asList("Passenger503-1");
        system.createBooking("F503", "CUST503", passengerNames);
        system.updateReservationStatus(system.getReservations().values().iterator().next().getId(), true);
        
        // Test
        List<Reservation> result = system.getConfirmedReservations("F503");
        
        // Verify
        assertTrue("Should return empty list when flight is closed", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup
        // Create cities and airports for other flights
        City city1 = system.createCity("C507", "City507");
        City city2 = system.createCity("C508", "City508");
        List<City> cities1 = Arrays.asList(city1);
        List<City> cities2 = Arrays.asList(city2);
        system.createAirport("AP507", "Airport507", cities1);
        system.createAirport("AP508", "Airport508", cities2);
        
        // Create flights F504 and F505 only
        String futureTime = LocalDateTime.now().plusDays(1).format(formatter);
        String furtherFutureTime = LocalDateTime.now().plusDays(2).format(formatter);
        system.createFlight("F504", "AP507", "AP508", futureTime, furtherFutureTime);
        system.createFlight("F505", "AP508", "AP507", futureTime, furtherFutureTime);
        
        // Test with unknown flight ID FX999
        List<Reservation> result = system.getConfirmedReservations("FX999");
        
        // Verify
        assertTrue("Should return empty list for unknown flight ID", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup
        // Create cities and airports
        City city1 = system.createCity("C509", "City509");
        City city2 = system.createCity("C510", "City510");
        List<City> cities1 = Arrays.asList(city1);
        List<City> cities2 = Arrays.asList(city2);
        system.createAirport("AP509", "Airport509", cities1);
        system.createAirport("AP510", "Airport510", cities2);
        
        // Create flight F504 with OPEN status
        String futureTime = LocalDateTime.now().plusDays(1).format(formatter);
        String furtherFutureTime = LocalDateTime.now().plusDays(2).format(formatter);
        Flight flight = system.createFlight("F504", "AP509", "AP510", futureTime, furtherFutureTime);
        flight.setStatus(FlightStatus.OPEN);
        
        // Create customer
        system.createCustomer("CUST504", "Customer504");
        
        // Create multiple bookings to get mixed reservation statuses
        List<String> passengers1 = Arrays.asList("Passenger504-A", "Passenger504-B");
        system.createBooking("F504", "CUST504", passengers1);
        
        List<String> passengers2 = Arrays.asList("Passenger504-C", "Passenger504-D");
        system.createBooking("F504", "CUST504", passengers2);
        
        // Update statuses: R504-A, R504-B = CONFIRMED; R504-C = CANCELED; R504-D = PENDING
        Map<String, Reservation> reservations = system.getReservations();
        int count = 0;
        for (Reservation reservation : reservations.values()) {
            switch (count) {
                case 0: // R504-A
                    system.updateReservationStatus(reservation.getId(), true);
                    break;
                case 1: // R504-B
                    system.updateReservationStatus(reservation.getId(), true);
                    break;
                case 2: // R504-C
                    system.updateReservationStatus(reservation.getId(), false);
                    break;
                case 3: // R504-D remains PENDING
                    break;
            }
            count++;
        }
        
        // Test
        List<Reservation> result = system.getConfirmedReservations("F504");
        
        // Verify
        assertEquals("Should return only 2 confirmed reservations", 2, result.size());
        Set<String> confirmedPassengers = new HashSet<>();
        for (Reservation res : result) {
            confirmedPassengers.add(res.getPassengerName());
            assertEquals("Reservation should be CONFIRMED", ReservationStatus.CONFIRMED, res.getStatus());
        }
        assertTrue("Should contain Passenger504-A", confirmedPassengers.contains("Passenger504-A"));
        assertTrue("Should contain Passenger504-B", confirmedPassengers.contains("Passenger504-B"));
        assertFalse("Should not contain Passenger504-C", confirmedPassengers.contains("Passenger504-C"));
        assertFalse("Should not contain Passenger504-D", confirmedPassengers.contains("Passenger504-D"));
    }
}