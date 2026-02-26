import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customerA;
    private Customer customerB;
    private Customer customerC;
    private Customer customerD;
    private Airport airport100;
    private Airport airport101;
    private Airport airport102;
    private Airport airport103;
    private Airport airport104;
    private Airport airport105;
    private Airport airport106;
    private Airport airport107;
    private Flight flight300;
    private Flight flight301;
    private Flight flight302;
    private Flight flight303;
    private Date currentTime;
    private Date departureTime;
    private Date arrivalTime;
    
    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentTime = dateFormat.parse("2025-10-01 09:00:00");
        departureTime = dateFormat.parse("2025-10-05 08:00:00");
        arrivalTime = dateFormat.parse("2025-10-05 12:00:00");
        
        // Setup common test objects
        airline = new Airline();
        customerA = new Customer();
        customerB = new Customer();
        customerC = new Customer();
        customerD = new Customer();
        
        airport100 = new Airport("AP100");
        airport101 = new Airport("AP101");
        airport102 = new Airport("AP102");
        airport103 = new Airport("AP103");
        airport104 = new Airport("AP104");
        airport105 = new Airport("AP105");
        airport106 = new Airport("AP106");
        airport107 = new Airport("AP107");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() throws ParseException {
        // Setup flight F300
        flight300 = new Flight(
            dateFormat.parse("2025-10-05 08:00:00"),
            dateFormat.parse("2025-10-05 12:00:00"),
            airport100,
            airport101
        );
        flight300.setOpenForBooking(true);
        airline.addFlight(flight300);
        
        // Create passenger list
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Peter");
        passengerNames.add("Beck");
        
        // Execute booking
        boolean result = customerA.addBooking(flight300, currentTime, passengerNames);
        
        // Verify results
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have 2 bookings", 2, customerA.getBookings().size());
        assertEquals("First booking should have 1 reservation", 1, customerA.getBookings().get(0).getReservations().size());
        assertEquals("Second booking should have 1 reservation", 1, customerA.getBookings().get(1).getReservations().size());
        assertEquals("First passenger should be Peter", "Peter", customerA.getBookings().get(0).getReservations().get(0).getPassenger().getName());
        assertEquals("Second passenger should be Beck", "Beck", customerA.getBookings().get(1).getReservations().get(0).getPassenger().getName());
        assertEquals("Reservation status should be PENDING", ReservationStatus.PENDING, customerA.getBookings().get(0).getReservations().get(0).getStatus());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws ParseException {
        // Setup flight F301
        flight301 = new Flight(
            dateFormat.parse("2025-10-05 08:00:00"),
            dateFormat.parse("2025-10-05 10:00:00"),
            airport102,
            airport103
        );
        flight301.setOpenForBooking(true);
        airline.addFlight(flight301);
        
        // Create passenger list with duplicate
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Alice");
        passengerNames.add("Alice");
        
        // Execute booking
        boolean result = customerB.addBooking(flight301, currentTime, passengerNames);
        
        // Verify results
        assertFalse("Booking should fail for duplicate passengers", result);
        assertTrue("Customer should have no bookings", customerB.getBookings().isEmpty());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() throws ParseException {
        // Setup flight F302
        flight302 = new Flight(
            dateFormat.parse("2025-10-05 18:00:00"),
            dateFormat.parse("2025-10-06 02:00:00"),
            airport104,
            airport105
        );
        flight302.setOpenForBooking(true);
        airline.addFlight(flight302);
        
        // Create pre-existing booking with reservation for Jucy
        Booking existingBooking = new Booking(customerC);
        Passenger jucyPassenger = new Passenger("Jucy");
        Reservation existingReservation = new Reservation(jucyPassenger, flight302);
        existingBooking.getReservations().add(existingReservation);
        customerC.getBookings().add(existingBooking);
        
        // Create passenger list with same passenger
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Jucy");
        
        // Execute booking
        boolean result = customerC.addBooking(flight302, currentTime, passengerNames);
        
        // Verify results
        assertFalse("Booking should fail for already booked passenger", result);
        assertEquals("Customer should still have only 1 booking", 1, customerC.getBookings().size());
        assertEquals("Booking should still have only 1 reservation", 1, customerC.getBookings().get(0).getReservations().size());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() throws ParseException {
        // Setup flight F303 (closed)
        flight303 = new Flight(
            dateFormat.parse("2025-10-05 18:00:00"),
            dateFormat.parse("2025-10-06 02:00:00"),
            airport106,
            airport107
        );
        flight303.setOpenForBooking(false); // Closed for booking
        airline.addFlight(flight303);
        
        // Create passenger list
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        // Execute booking
        boolean result = customerD.addBooking(flight303, currentTime, passengerNames);
        
        // Verify results
        assertFalse("Booking should fail for closed flight", result);
        assertTrue("Customer should have no bookings", customerD.getBookings().isEmpty());
    }
    
    @Test
    public void testCase5_timeAfterDepartureTimeBlocksBooking() throws ParseException {
        // Setup flight F303
        flight303 = new Flight(
            dateFormat.parse("2025-10-05 18:00:00"),
            dateFormat.parse("2025-10-06 02:00:00"),
            airport104,
            airport105
        );
        flight303.setOpenForBooking(true);
        airline.addFlight(flight303);
        
        // Create passenger list
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        // Set current time after departure time
        Date lateTime = dateFormat.parse("2025-10-06 09:00:00");
        
        // Execute booking
        boolean result = customerD.addBooking(flight303, lateTime, passengerNames);
        
        // Verify results
        assertFalse("Booking should fail when current time is after departure time", result);
        assertTrue("Customer should have no bookings", customerD.getBookings().isEmpty());
    }
}