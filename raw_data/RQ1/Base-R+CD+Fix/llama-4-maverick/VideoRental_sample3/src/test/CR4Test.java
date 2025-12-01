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
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create Tape 1
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        // Create Rental 1: returned early, no overdue fee
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        
        // Create Tape 2
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create Rental 2: returned early, no overdue fee
        VideoRental rental2 = new VideoRental();
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
        
        // Set current date
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01", formatter), currentDate);
        
        // Verify expected output: Rental 1: 2 + 0 = 2, Rental 2: 11 + 0 = 11, total = 13
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape
        Tape tape = new Tape();
        tape.setId("T003");
        
        // Create Rental: 7 days overdue, rental duration 11 days
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        
        // Set current date
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01", formatter), currentDate);
        
        // Verify expected output: 11 (base fee) + 3.50 (overdue) = 14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape 1
        Tape tape1 = new Tape();
        tape1.setId("T004");
        
        // Create Rental 1: 4 days overdue, rental duration 8 days
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        
        // Create Tape 2
        Tape tape2 = new Tape();
        tape2.setId("T005");
        
        // Create Rental 2: 3 days overdue, rental duration 8 days
        VideoRental rental2 = new VideoRental();
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
        
        // Set current date
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01", formatter), currentDate);
        
        // Verify expected output: 8+8 base fees + 2+1.5 overdue = 19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tape 1
        Tape tape1 = new Tape();
        tape1.setId("T006");
        
        // Create Rental 1: 2 days overdue, rental duration 6 days
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-07", formatter));
        
        // Create Tape 2
        Tape tape2 = new Tape();
        tape2.setId("T007");
        
        // Create Rental 2: on-time, rental duration 4 days
        VideoRental rental2 = new VideoRental();
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
        
        // Set current date
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01", formatter), currentDate);
        
        // Verify expected output: 6+4 base + 1 overdue = 11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Create Tape
        Tape tape = new Tape();
        tape.setId("T008");
        
        // Create Rental: unreturned, 5 days overdue, rental duration 9 days
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null); // Unreturned
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        
        // Set current date
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01", formatter), currentDate);
        
        // Verify expected output: 9 (base fee) + 2.50 (overdue) = 11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}