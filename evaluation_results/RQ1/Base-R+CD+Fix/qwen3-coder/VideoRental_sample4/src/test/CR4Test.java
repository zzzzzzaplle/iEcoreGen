import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_NoOverdueFees() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C001");
        
        Date currentDate = dateFormat.parse("2025-01-20");
        
        // Rental 1: T001, returned early
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date dueDate1 = dateFormat.parse("2025-01-05");
        Date returnDate1 = dateFormat.parse("2025-01-03");
        
        VideoRental rental1 = new VideoRental(tape1, dueDate1);
        rental1.setReturnDate(returnDate1);
        
        // Rental 2: T002, returned early
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        Date rentalDate2 = dateFormat.parse("2025-01-01");
        Date dueDate2 = dateFormat.parse("2025-01-15");
        Date returnDate2 = dateFormat.parse("2025-01-12");
        
        VideoRental rental2 = new VideoRental(tape2, dueDate2);
        rental2.setReturnDate(returnDate2);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        transaction.setRentalDate(rentalDate1);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(rentalDate1, currentDate);
        
        // Verify result
        assertEquals(13.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C002");
        
        Date currentDate = dateFormat.parse("2025-01-20");
        
        // Rental 1: T003, overdue
        Tape tape1 = new Tape();
        tape1.setId("T003");
        
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date dueDate1 = dateFormat.parse("2025-01-05");
        Date returnDate1 = dateFormat.parse("2025-01-12");
        
        VideoRental rental1 = new VideoRental(tape1, dueDate1);
        rental1.setReturnDate(returnDate1);
        
        // Add rental to customer
        customer.getRentals().add(rental1);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.setRentalDate(rentalDate1);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(rentalDate1, currentDate);
        
        // Verify result
        assertEquals(14.5, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C003");
        
        Date currentDate = dateFormat.parse("2025-01-20");
        
        // Rental 1: T004, overdue
        Tape tape1 = new Tape();
        tape1.setId("T004");
        
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date dueDate1 = dateFormat.parse("2025-01-05");
        Date returnDate1 = dateFormat.parse("2025-01-09");
        
        VideoRental rental1 = new VideoRental(tape1, dueDate1);
        rental1.setReturnDate(returnDate1);
        
        // Rental 2: T005, overdue
        Tape tape2 = new Tape();
        tape2.setId("T005");
        
        Date rentalDate2 = dateFormat.parse("2025-01-10");
        Date dueDate2 = dateFormat.parse("2025-01-15");
        Date returnDate2 = dateFormat.parse("2025-01-18");
        
        VideoRental rental2 = new VideoRental(tape2, dueDate2);
        rental2.setReturnDate(returnDate2);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        transaction.setRentalDate(rentalDate1);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(rentalDate1, currentDate);
        
        // Verify result
        assertEquals(19.5, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C004");
        
        Date currentDate = dateFormat.parse("2025-01-20");
        
        // Rental 1: T006, overdue
        Tape tape1 = new Tape();
        tape1.setId("T006");
        
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date dueDate1 = dateFormat.parse("2025-01-05");
        Date returnDate1 = dateFormat.parse("2025-01-07");
        
        VideoRental rental1 = new VideoRental(tape1, dueDate1);
        rental1.setReturnDate(returnDate1);
        
        // Rental 2: T007, on-time
        Tape tape2 = new Tape();
        tape2.setId("T007");
        
        Date rentalDate2 = dateFormat.parse("2025-01-10");
        Date dueDate2 = dateFormat.parse("2025-01-15");
        Date returnDate2 = dateFormat.parse("2025-01-14");
        
        VideoRental rental2 = new VideoRental(tape2, dueDate2);
        rental2.setReturnDate(returnDate2);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        transaction.setRentalDate(rentalDate1);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(rentalDate1, currentDate);
        
        // Verify result
        assertEquals(11.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C006");
        
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Rental 1: T008, unreturned and overdue
        Tape tape1 = new Tape();
        tape1.setId("T008");
        
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date dueDate1 = dateFormat.parse("2025-01-05");
        
        VideoRental rental1 = new VideoRental(tape1, dueDate1);
        // returnDate remains null (unreturned)
        
        // Add rental to customer
        customer.getRentals().add(rental1);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.setRentalDate(rentalDate1);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(rentalDate1, currentDate);
        
        // Verify result
        assertEquals(11.5, totalPrice, 0.001);
    }
}