import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Clear static rentals list before each test
        VideoRental.getAllRentals().clear();
    }
    
    @Test
    public void testCase1_NoOverdueFees() throws Exception {
        // Setup
        Customer customer = new Customer("C001");
        
        // Create Rental 1: returned early, no overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape("T001", "Movie1");
        rental1.setTape(tape1);
        rental1.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-03 00:00:00"));
        
        // Create Rental 2: returned early, no overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape("T002", "Movie2");
        rental2.setTape(tape2);
        rental2.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-12 00:00:00"));
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(customer.getRentals());
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() throws Exception {
        // Setup
        Customer customer = new Customer("C002");
        
        // Create Rental: 7 days overdue
        VideoRental rental = new VideoRental();
        Tape tape = new Tape("T003", "Movie3");
        rental.setTape(tape);
        rental.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-12 00:00:00"));
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(customer.getRentals());
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() throws Exception {
        // Setup
        Customer customer = new Customer("C003");
        
        // Create Rental 1: 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape("T004", "Movie4");
        rental1.setTape(tape1);
        rental1.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-09 00:00:00"));
        
        // Create Rental 2: 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape("T005", "Movie5");
        rental2.setTape(tape2);
        rental2.setRentalDate(dateFormat.parse("2025-01-10 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-18 00:00:00"));
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(customer.getRentals());
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() throws Exception {
        // Setup
        Customer customer = new Customer("C004");
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape("T006", "Movie6");
        rental1.setTape(tape1);
        rental1.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-07 00:00:00"));
        
        // Create Rental 2: on-time, no overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape("T007", "Movie7");
        rental2.setTape(tape2);
        rental2.setRentalDate(dateFormat.parse("2025-01-10 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-14 00:00:00"));
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(customer.getRentals());
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() throws Exception {
        // Setup
        Customer customer = new Customer("C006");
        
        // Create Rental: unreturned, 5 days overdue
        VideoRental rental = new VideoRental();
        Tape tape = new Tape("T008", "Movie8");
        rental.setTape(tape);
        rental.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null); // Unreturned
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(customer.getRentals());
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output
        assertEquals(11.50, totalPrice, 0.001);
    }
}