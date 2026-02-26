import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private DateTimeFormatter formatter;
    private Customer customer;
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Passenger passenger;
    private Reservation reservation;
    private Booking booking;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() {
        // Setup
        // 1. Airline AL16 (not directly modeled in classes, so we skip)
        // 2. Airports AP160 (CityAA) and AP161 (CityAB)
        departureAirport = new Airport();
        departureAirport.setId("AP160");
        departureAirport.addCity("CityAA");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP161");
        arrivalAirport.addCity("CityAB");
        
        // 3. Flight F401
        flight = new Flight();
        flight.setId("F401");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 15:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        // 4. Customer CU16, passenger P9
        customer = new Customer();
        passenger = new Passenger();
        passenger.setName("P9");
        
        // 5. Booking BK401 contains reservation R401 (status = PENDING) for P9 on F401
        booking = new Booking();
        reservation = new Reservation();
        reservation.setId("R401");
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setStatus(ReservationStatus.PENDING);
        
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        // 6. Current time = 2025-11-01 09:00:00 (simulated by setting flight departure time appropriately)
        // The method uses LocalDateTime.now() which we can't mock easily, so we rely on the setup times
        
        // Test: CU16 confirm reservation R401
        boolean result = customer.updateReservationStatus("R401", true);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() {
        // Setup
        // 1. Airline AL17 (not directly modeled in classes, so we skip)
        // 2. Airports AP170 (CityAC) and AP171 (CityAD)
        departureAirport = new Airport();
        departureAirport.setId("AP170");
        departureAirport.addCity("CityAC");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP171");
        arrivalAirport.addCity("CityAD");
        
        // 3. Flight F402
        flight = new Flight();
        flight.setId("F402");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-15 15:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-15 19:00:00", formatter)); // Added arrival time for completeness
        flight.setStatus(FlightStatus.OPEN);
        
        // 4. Customer CU17, passenger P10
        customer = new Customer();
        passenger = new Passenger();
        passenger.setName("P10");
        
        // 5. Booking BK402 contains reservation R402 (status = CONFIRMED) for P10 on F402
        booking = new Booking();
        reservation = new Reservation();
        reservation.setId("R402");
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        // 6. Current time = 2025-12-01 12:00:00 (simulated by setting flight departure time appropriately)
        // The method uses LocalDateTime.now() which we can't mock easily, so we rely on the setup times
        
        // Test: CU17 cancel reservation R402
        boolean result = customer.updateReservationStatus("R402", false);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() {
        // Setup
        // 1. Airline AL18 (not directly modeled in classes, so we skip)
        // 2. Airports AP180 (CityAE) and AP181 (CityAF)
        departureAirport = new Airport();
        departureAirport.setId("AP180");
        departureAirport.addCity("CityAE");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP181");
        arrivalAirport.addCity("CityAF");
        
        // 3. Flight F403
        flight = new Flight();
        flight.setId("F403");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-05 06:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-05 10:00:00", formatter)); // Added arrival time for completeness
        flight.setStatus(FlightStatus.OPEN);
        
        // 4. Reservation R403 status = PENDING (passenger P11)
        customer = new Customer();
        passenger = new Passenger();
        passenger.setName("P11");
        
        booking = new Booking();
        reservation = new Reservation();
        reservation.setId("R403");
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setStatus(ReservationStatus.PENDING);
        
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        // 5. Current time = 2025-01-05 07:00:00 (flight already left)
        // The method uses LocalDateTime.now() which we can't mock easily, but the flight departure time
        // is set to 2025-01-05 06:00:00 which is before the "current" time of 07:00:00
        // This will cause the validation to fail
        
        // Test: Confirm reservation R403
        boolean result = customer.updateReservationStatus("R403", true);
        
        // Verify
        assertFalse("Reservation confirmation should fail when flight has departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() {
        // Setup
        // 1. Airline AL19 (not directly modeled in classes, so we skip)
        // 2. Airports AP190 (CityAG) and AP191 (CityAH)
        departureAirport = new Airport();
        departureAirport.setId("AP190");
        departureAirport.addCity("CityAG");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP191");
        arrivalAirport.addCity("CityAH");
        
        // 3. Flight F404
        flight = new Flight();
        flight.setId("F404");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-01 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-01 13:00:00", formatter)); // Added arrival time for completeness
        flight.setStatus(FlightStatus.CLOSED); // openForBooking = False
        
        // 4. Reservation R404 status = CONFIRMED (passenger P12)
        customer = new Customer();
        passenger = new Passenger();
        passenger.setName("P12");
        
        booking = new Booking();
        reservation = new Reservation();
        reservation.setId("R404");
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        // 5. Current time = 2025-01-20 08:00:00
        // The method uses LocalDateTime.now() which we can't mock easily, but the flight is CLOSED
        // which will cause the validation to fail regardless of time
        
        // Test: Cancel reservation R404
        boolean result = customer.updateReservationStatus("R404", false);
        
        // Verify
        assertFalse("Reservation cancellation should fail when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() {
        // Setup
        // 1. Airline AL20 (not directly modeled in classes, so we skip)
        // 2. Airports AP200 (CityAI) and AP201 (CityAJ)
        departureAirport = new Airport();
        departureAirport.setId("AP200");
        departureAirport.addCity("CityAI");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP201");
        arrivalAirport.addCity("CityAJ");
        
        // 3. Flight F405
        flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-10 14:00:00", formatter)); // Added arrival time for completeness
        flight.setStatus(FlightStatus.OPEN);
        
        // 4. Customer CU20 with one existing reservation R405 (status = PENDING) for passenger P13
        customer = new Customer();
        Passenger passengerP13 = new Passenger();
        passengerP13.setName("P13");
        
        booking = new Booking();
        Reservation reservationR405 = new Reservation();
        reservationR405.setId("R405");
        reservationR405.setFlight(flight);
        reservationR405.setPassenger(passengerP13);
        reservationR405.setStatus(ReservationStatus.PENDING);
        
        booking.getReservations().add(reservationR405);
        customer.getBookings().add(booking);
        flight.getReservations().add(reservationR405);
        
        // 5. Customer CU21 with one existing reservation R406 (status = PENDING) for passenger P14
        Customer customerCU21 = new Customer();
        Passenger passengerP14 = new Passenger();
        passengerP14.setName("P14");
        
        Booking bookingCU21 = new Booking();
        Reservation reservationR406 = new Reservation();
        reservationR406.setId("R406");
        reservationR406.setFlight(flight);
        reservationR406.setPassenger(passengerP14);
        reservationR406.setStatus(ReservationStatus.PENDING);
        
        bookingCU21.getReservations().add(reservationR406);
        customerCU21.getBookings().add(bookingCU21);
        flight.getReservations().add(reservationR406);
        
        // 6. Current time = 2025-02-15 09:00:00
        // The method uses LocalDateTime.now() which we can't mock easily, so we rely on the setup times
        
        // Test: Customer CU20 confirm reservation R406 (which belongs to CU21)
        boolean result = customer.updateReservationStatus("R406", true);
        
        // Verify
        assertFalse("Reservation confirmation should fail for unknown reservation ID", result);
        assertEquals("Reservation R406 status should remain PENDING", ReservationStatus.PENDING, reservationR406.getStatus());
    }
}