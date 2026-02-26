import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoOverdueFees() throws Exception {
        // Setup test data
        Customer customer = new Customer();
        customer.setId("C001");
        
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        
        // Create Rental 1: returned early, no overdue fee
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-03 00:00:00")); // returned early
        
        // Create Rental 2: returned early, no overdue fee
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-12 00:00:00")); // returned early
        
        // Create transaction and add rentals
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01 00:00:00"), currentDate);
        
        // Verify results
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() throws Exception {
        // Setup test data
        Customer customer = new Customer();
        customer.setId("C002");
        
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        
        // Create Rental 1: 7 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-12 00:00:00")); // 7 days overdue
        
        // Create transaction and add rental
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01 00:00:00"), currentDate);
        
        // Verify results
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() throws Exception {
        // Setup test data
        Customer customer = new Customer();
        customer.setId("C003");
        
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        
        // Create Rental 1: 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-09 00:00:00")); // 4 days overdue
        
        // Create Rental 2: 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-18 00:00:00")); // 3 days overdue
        
        // Create transaction and add rentals
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01 00:00:00"), currentDate);
        
        // Verify results
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() throws Exception {
        // Setup test data
        Customer customer = new Customer();
        customer.setId("C004");
        
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-07 00:00:00")); // 2 days overdue
        
        // Create Rental 2: on-time
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-14 00:00:00")); // returned early
        
        // Create transaction and add rentals
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01 00:00:00"), currentDate);
        
        // Verify results
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() throws Exception {
        // Setup test data
        Customer customer = new Customer();
        customer.setId("C006");
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Create Rental 1: unreturned, 5 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(null); // not returned yet
        
        // Create transaction and add rental
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01 00:00:00"), currentDate);
        
        // Verify results
        assertEquals(11.50, totalPrice, 0.001);
    }
}