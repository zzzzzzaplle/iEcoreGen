import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Clear static rentals list before each test to avoid interference
        VideoRental.getRentals().clear();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup: Create Customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create and setup Rental 1: returned early, no overdue fee
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        
        // Create and setup Rental 2: returned early, no overdue fee
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        
        // Calculate total price with current date 2025-01-20
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output: Rental 1 price = 2 + 0 = 2, Rental 2 price = 11 + 0 = 11, total = 13
        assertEquals(13.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create and setup Rental 1: returned late with 7 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        
        // Calculate total price with current date 2025-01-20
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output: Total price = 11 (base fee) + 3.50 (overdue) = 14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create and setup Rental 1: 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        
        // Create and setup Rental 2: 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-18", formatter));
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        
        // Calculate total price with current date 2025-01-20
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output: Total price = 8+8 base fees + 2+1.5 overdue = 19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create and setup Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-07", formatter));
        
        // Create and setup Rental 2: on-time
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-14", formatter));
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        
        // Calculate total price with current date 2025-01-20
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output: Total price = (6+4 base) + 1 overdue = 11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup: Create Customer C006
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Create and setup Rental 1: unreturned, 5 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        // return_date is null (unreturned)
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        
        // Calculate total price with current date 2025-01-10
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output: Total price = 9 (base fee) + 2.50 (overdue) = 11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}