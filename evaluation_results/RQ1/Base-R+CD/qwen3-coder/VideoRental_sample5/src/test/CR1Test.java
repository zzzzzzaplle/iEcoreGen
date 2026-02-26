import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_returned1DayLate() throws Exception {
        // Create customer
        Customer c1 = new Customer();
        
        // Create tape and rental
        Tape tape = new Tape();
        tape.setId("V001");
        
        // Create rental with due date 3 days after rental date (2025-01-01 + 3 days = 2025-01-04)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-04 00:00:00");
        rental.setDueDate(dueDate);
        
        // Set return date to 2025-01-05 (1 day late)
        Date returnDate = dateFormat.parse("2025-01-05 00:00:00");
        rental.setReturnDate(returnDate);
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        c1.setRentals(rentals);
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(dateFormat.parse("2025-01-10 00:00:00"));
        
        // Expected: 1 day * $0.5 = $0.50
        assertEquals(0.50, pastDueAmount, 0.01);
    }
    
    @Test
    public void testCase2_unreturnedAnd3DaysOverdue() throws Exception {
        // Create customer
        Customer c2 = new Customer();
        
        // Create tape and rental
        Tape tape = new Tape();
        tape.setId("V002");
        
        // Create rental with due date 3 days after rental date (2025-01-01 + 3 days = 2025-01-04)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-04 00:00:00");
        rental.setDueDate(dueDate);
        
        // No return date (null)
        rental.setReturnDate(null);
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        c2.setRentals(rentals);
        
        // Current date is 2025-01-08 (4 days after due date)
        Date currentDate = dateFormat.parse("2025-01-08 00:00:00");
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 4 days * $0.5 = $2.00
        assertEquals(2.00, pastDueAmount, 0.01);
    }
    
    @Test
    public void testCase3_returnedOnDueDate() throws Exception {
        // Create customer
        Customer c3 = new Customer();
        
        // Create tape and rental
        Tape tape = new Tape();
        tape.setId("V003");
        
        // Create rental with due date 2025-01-06
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date dueDate = dateFormat.parse("2025-01-06 00:00:00");
        rental.setDueDate(dueDate);
        
        // Set return date to due date
        Date returnDate = dateFormat.parse("2025-01-06 00:00:00");
        rental.setReturnDate(returnDate);
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        c3.setRentals(rentals);
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(dateFormat.parse("2025-01-10 00:00:00"));
        
        // Expected: 0.00 (returned on due date)
        assertEquals(0.00, pastDueAmount, 0.01);
    }
    
    @Test
    public void testCase4_unreturnedButNotOverdue() throws Exception {
        // Create customer
        Customer c4 = new Customer();
        
        // Create tape and rental
        Tape tape = new Tape();
        tape.setId("V004");
        
        // Create rental with due date 2025-01-10
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date dueDate = dateFormat.parse("2025-01-10 00:00:00");
        rental.setDueDate(dueDate);
        
        // No return date (null)
        rental.setReturnDate(null);
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        c4.setRentals(rentals);
        
        // Current date is 2025-01-06 (before due date)
        Date currentDate = dateFormat.parse("2025-01-06 00:00:00");
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 0.00 (not overdue yet)
        assertEquals(0.00, pastDueAmount, 0.01);
    }
    
    @Test
    public void testCase5_returned5DaysLate() throws Exception {
        // Create customer
        Customer c5 = new Customer();
        
        // Create tape and rental
        Tape tape = new Tape();
        tape.setId("V005");
        
        // Create rental with due date 2025-01-08
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date dueDate = dateFormat.parse("2025-01-08 00:00:00");
        rental.setDueDate(dueDate);
        
        // Set return date to 2025-01-13 (5 days late)
        Date returnDate = dateFormat.parse("2025-01-13 00:00:00");
        rental.setReturnDate(returnDate);
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        c5.setRentals(rentals);
        
        // Current date is 2025-01-10
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 5 days * $0.5 = $2.50
        assertEquals(2.50, pastDueAmount, 0.01);
    }
}