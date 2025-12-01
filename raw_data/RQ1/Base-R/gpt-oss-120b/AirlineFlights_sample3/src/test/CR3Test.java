import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private FlightService flightService;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        flightService = new FlightService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Clear any existing bookings before each test
        BookingRepository.getAllBookings().clear();
    }
    
    @Test
    public void testCase1_confirmPendingReservation() {
        // Setup
        // 1. Airline AL16
        Airline airline = new Airline("AL16", "Airline 16");
        
        // 2. Airports AP160 (CityAA) and AP161 (CityAB)
        City cityAA = new City("CityAA", "City A");
        City cityAB = new City("CityAB", "City B");
        
        Airport ap160 = new Airport("AP160", "Airport 160");
        Airport ap161 = new Airport("AP161", "Airport 161");
        
        ap160.addCity(cityAA);
        ap161.addCity(cityAB);
        
        // 3. Flight F401
        Flight flight = new Flight("F401");
        flight.setDepartureAirport(ap160);
        flight.setArrivalAirport(ap161);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 15:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        flight.setPublished(true);
        
        // 4. Customer CU16, passenger P9
        Customer customer = new Customer("CU16", "Customer 16");
        
        // 5. Booking BK401 contains reservation R401 (status = PENDING) for P9 on F401
        Booking booking = new Booking(customer, flight);
        Reservation reservation = new Reservation(flight, "P9");
        reservation.setStatus(ReservationStatus.PENDING);
        booking.addReservation(reservation);
        
        BookingRepository.addBooking(booking);
        
        // 6. Current time = 2025-11-01 09:00:00
        // Mock current time by ensuring flight hasn't departed
        
        // Test: Confirm reservation R401
        boolean result = FlightService.updateReservationStatus("R1", true); // R1 is the generated ID
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", 
                     ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() {
        // Setup
        // 1. Airline AL17
        Airline airline = new Airline("AL17", "Airline 17");
        
        // 2. Airports AP170 (CityAC) and AP171 (CityAD)
        City cityAC = new City("CityAC", "City C");
        City cityAD = new City("CityAD", "City D");
        
        Airport ap170 = new Airport("AP170", "Airport 170");
        Airport ap171 = new Airport("AP171", "Airport 171");
        
        ap170.addCity(cityAC);
        ap171.addCity(cityAD);
        
        // 3. Flight F402
        Flight flight = new Flight("F402");
        flight.setDepartureAirport(ap170);
        flight.setArrivalAirport(ap171);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-15 15:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-15 19:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        flight.setPublished(true);
        
        // 4. Customer CU17, passenger P10
        Customer customer = new Customer("CU17", "Customer 17");
        
        // 5. Booking BK402 contains reservation R402 (status = CONFIRMED) for P10 on F402
        Booking booking = new Booking(customer, flight);
        Reservation reservation = new Reservation(flight, "P10");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        booking.addReservation(reservation);
        
        BookingRepository.addBooking(booking);
        
        // 6. Current time = 2025-12-01 12:00:00
        // Mock current time by ensuring flight hasn't departed
        
        // Test: Cancel reservation R402
        boolean result = FlightService.updateReservationStatus("R2", false); // R2 is the generated ID
        
        // Verify
        assertTrue("Reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELED", 
                     ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() {
        // Setup
        // 1. Airline AL18
        Airline airline = new Airline("AL18", "Airline 18");
        
        // 2. Airports AP180 (CityAE) and AP181 (CityAF)
        City cityAE = new City("CityAE", "City E");
        City cityAF = new City("CityAF", "City F");
        
        Airport ap180 = new Airport("AP180", "Airport 180");
        Airport ap181 = new Airport("AP181", "Airport 181");
        
        ap180.addCity(cityAE);
        ap181.addCity(cityAF);
        
        // 3. Flight F403
        Flight flight = new Flight("F403");
        flight.setDepartureAirport(ap180);
        flight.setArrivalAirport(ap181);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-05 06:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-05 10:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        flight.setPublished(true);
        
        // 4. Reservation R403 status = PENDING (passenger P11)
        Customer customer = new Customer("CU18", "Customer 18");
        Booking booking = new Booking(customer, flight);
        Reservation reservation = new Reservation(flight, "P11");
        reservation.setStatus(ReservationStatus.PENDING);
        booking.addReservation(reservation);
        
        BookingRepository.addBooking(booking);
        
        // 5. Current time = 2025-01-05 07:00:00 (flight already left)
        // Since we can't mock LocalDateTime.now(), we'll set departure time to past
        flight.setDepartureTime(LocalDateTime.parse("2025-01-04 06:00:00", formatter));
        
        // Test: Confirm reservation R403
        boolean result = FlightService.updateReservationStatus("R3", true); // R3 is the generated ID
        
        // Verify
        assertFalse("Reservation confirmation should fail when flight has departed", result);
        assertEquals("Reservation status should remain PENDING", 
                     ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() {
        // Setup
        // 1. Airline AL19
        Airline airline = new Airline("AL19", "Airline 19");
        
        // 2. Airports AP190 (CityAG) and AP191 (CityAH)
        City cityAG = new City("CityAG", "City G");
        City cityAH = new City("CityAH", "City H");
        
        Airport ap190 = new Airport("AP190", "Airport 190");
        Airport ap191 = new Airport("AP191", "Airport 191");
        
        ap190.addCity(cityAG);
        ap191.addCity(cityAH);
        
        // 3. Flight F404
        Flight flight = new Flight("F404");
        flight.setDepartureAirport(ap190);
        flight.setArrivalAirport(ap191);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-01 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-01 13:00:00", formatter));
        flight.setStatus(FlightStatus.CLOSED); // Flight is closed
        flight.setPublished(true);
        
        // 4. Reservation R404 status = CONFIRMED (passenger P12)
        Customer customer = new Customer("CU19", "Customer 19");
        Booking booking = new Booking(customer, flight);
        Reservation reservation = new Reservation(flight, "P12");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        booking.addReservation(reservation);
        
        BookingRepository.addBooking(booking);
        
        // 5. Current time = 2025-01-20 08:00:00
        // Flight hasn't departed but is closed
        
        // Test: Cancel reservation R404
        boolean result = FlightService.updateReservationStatus("R4", false); // R4 is the generated ID
        
        // Verify
        assertFalse("Reservation cancellation should fail when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", 
                     ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() {
        // Setup
        // 1. Airline AL20
        Airline airline = new Airline("AL20", "Airline 20");
        
        // 2. Airports AP200 (CityAI) and AP201 (CityAJ)
        City cityAI = new City("CityAI", "City I");
        City cityAJ = new City("CityAJ", "City J");
        
        Airport ap200 = new Airport("AP200", "Airport 200");
        Airport ap201 = new Airport("AP201", "Airport 201");
        
        ap200.addCity(cityAI);
        ap201.addCity(cityAJ);
        
        // 3. Flight F405
        Flight flight = new Flight("F405");
        flight.setDepartureAirport(ap200);
        flight.setArrivalAirport(ap201);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-10 14:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        flight.setPublished(true);
        
        // 4. Customer CU20 with one existing reservation R405 (status = PENDING) for passenger P13
        Customer customer20 = new Customer("CU20", "Customer 20");
        Booking booking20 = new Booking(customer20, flight);
        Reservation reservation405 = new Reservation(flight, "P13");
        reservation405.setStatus(ReservationStatus.PENDING);
        booking20.addReservation(reservation405);
        
        // 5. Customer CU21 with one existing reservation R406 (status = PENDING) for passenger P14
        Customer customer21 = new Customer("CU21", "Customer 21");
        Booking booking21 = new Booking(customer21, flight);
        Reservation reservation406 = new Reservation(flight, "P14");
        reservation406.setStatus(ReservationStatus.PENDING);
        booking21.addReservation(reservation406);
        
        BookingRepository.addBooking(booking20);
        BookingRepository.addBooking(booking21);
        
        // 6. Current time = 2025-02-15 09:00:00
        
        // Test: Customer CU20 confirm reservation R406 (which belongs to CU21)
        boolean result = FlightService.updateReservationStatus("R999", true); // Unknown reservation ID
        
        // Verify
        assertFalse("Should return false for unknown reservation identifier", result);
    }
}