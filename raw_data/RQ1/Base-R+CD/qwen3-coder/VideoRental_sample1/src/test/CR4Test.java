import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_noOverdueFees() throws Exception {
        // Create Customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Add Rental 1: Tape ID="T001", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-03"
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-03"));
        customer.getRentals().add(rental1);
        transaction.getRentals().add(rental1);
        
        // Add Rental 2: Tape ID="T002", rental_date="2025-01-01", due_date="2025-01-15", return_date="2025-01-12"
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(dateFormat.parse("2025-01-12"));
        customer.getRentals().add(rental2);
        transaction.getRentals().add(rental2);
        
        // Current date is "2025-01-20"
        Date currentDate = dateFormat.parse("2025-01-20");
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(rentalDate, currentDate);
        
        // Expected: Rental 1 price: 2 + 0 = 2, Rental 2 price: 11 + 0 = 11, total price = 13
        assertEquals(13.0, totalPrice, 0.01);
    }
    
    @Test
    public void testCase2_oneOverdueRental() throws Exception {
        // Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Add Rental 1: Tape ID="T003", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-12"
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-12"));
        customer.getRentals().add(rental1);
        transaction.getRentals().add(rental1);
        
        // Current date is "2025-01-20"
        Date currentDate = dateFormat.parse("2025-01-20");
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(rentalDate, currentDate);
        
        // Expected: Total price = 11 (base fee) + 3.50 (overdue) = $14.50
        assertEquals(14.5, totalPrice, 0.01);
    }
    
    @Test
    public void testCase3_multipleOverdueRentals() throws Exception {
        // Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Add Rental 1: Tape ID="T004", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-09"
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-09"));
        customer.getRentals().add(rental1);
        transaction.getRentals().add(rental1);
        
        // Add Rental 2: Tape ID="T005", rental_date="2025-01-10", due_date="2025-01-15", return_date="2025-01-18"
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(dateFormat.parse("2025-01-18"));
        customer.getRentals().add(rental2);
        transaction.getRentals().add(rental2);
        
        // Current date is "2025-01-20"
        Date currentDate = dateFormat.parse("2025-01-20");
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(rentalDate, currentDate);
        
        // Expected: Total price = 8+8 base fees + 2+1.5 overdue = $19.50
        assertEquals(19.5, totalPrice, 0.01);
    }
    
    @Test
    public void testCase4_mixedOverdueAndOnTimeRentals() throws Exception {
        // Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Add Rental 1: Tape ID="T006", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-07"
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-07"));
        customer.getRentals().add(rental1);
        transaction.getRentals().add(rental1);
        
        // Add Rental 2: Tape ID="T007", rental_date="2025-01-10", due_date="2025-01-15", return_date="2025-01-14"
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(dateFormat.parse("2025-01-14"));
        customer.getRentals().add(rental2);
        transaction.getRentals().add(rental2);
        
        // Current date is "2025-01-20"
        Date currentDate = dateFormat.parse("2025-01-20");
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(rentalDate, currentDate);
        
        // Expected: Total price = (6+4 base) + 1 overdue = $11.00
        assertEquals(11.0, totalPrice, 0.01);
    }
    
    @Test
    public void testCase5_unreturnedTapeWithCurrentDateOverdue() throws Exception {
        // Create Customer C006
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Add Rental 1: Tape ID="T008", rental_date="2025-01-01", due_date="2025-01-05", return_date=null
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        // return_date is null (not returned)
        customer.getRentals().add(rental1);
        transaction.getRentals().add(rental1);
        
        // Current date is "2025-01-10"
        Date currentDate = dateFormat.parse("2025-01-10");
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(rentalDate, currentDate);
        
        // Expected: Total price = 9 (base fee) + 2.50 (overdue) = $11.50
        assertEquals(11.5, totalPrice, 0.01);
    }
}