import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR4Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoOverdueFees() throws Exception {
        // Create Customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create Rental 1: returned early, overdue fee=0
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-03 00:00:00"));
        
        // Create Rental 2: returned early, overdue fee=0
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-12 00:00:00"));
        
        // Add rentals to transaction
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price with current date "2025-01-20"
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        double totalPrice = transaction.calculateTotalPrice(null, currentDate);
        
        // Expected: Rental 1 price: 2 + 0 = 2, Rental 2 price: 11 + 0 = 11, total = 13
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() throws Exception {
        // Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create Rental 1: 7 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-12 00:00:00"));
        
        // Add rental to transaction
        transaction.getRentals().add(rental1);
        
        // Calculate total price with current date "2025-01-20"
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        double totalPrice = transaction.calculateTotalPrice(null, currentDate);
        
        // Expected: 11 (base fee) + 3.50 (overdue) = 14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() throws Exception {
        // Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create Rental 1: 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-09 00:00:00"));
        
        // Create Rental 2: 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-18 00:00:00"));
        
        // Add rentals to transaction
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price with current date "2025-01-20"
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        double totalPrice = transaction.calculateTotalPrice(null, currentDate);
        
        // Expected: 8+8 base fees + 2+1.5 overdue = 19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() throws Exception {
        // Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-07 00:00:00"));
        
        // Create Rental 2: on-time
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-14 00:00:00"));
        
        // Add rentals to transaction
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price with current date "2025-01-20"
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        double totalPrice = transaction.calculateTotalPrice(null, currentDate);
        
        // Expected: (6+4 base) + 1 overdue = 11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() throws Exception {
        // Create Customer C006
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create Rental 1: unreturned, 5 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        // returnDate remains null (unreturned)
        
        // Add rental to transaction
        transaction.getRentals().add(rental1);
        
        // Calculate total price with current date "2025-01-10"
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double totalPrice = transaction.calculateTotalPrice(null, currentDate);
        
        // Expected: 9 (base fee) + 2.50 (overdue) = 11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}