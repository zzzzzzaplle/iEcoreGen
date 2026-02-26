import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private RentalSystem rentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Create customer c1
        Customer c1 = new Customer();
        c1.setAccountNumber(1);
        c1.setName("Customer 1");
        
        // Create video tape V001
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("V001");
        tape1.setTitle("Video 1");
        
        // Add customer and tape to system
        rentalSystem.getCustomers().add(c1);
        rentalSystem.getInventory().add(tape1);
        
        // Create rental transaction
        RentalTransaction rental = new RentalTransaction();
        rental.setCustomer(c1);
        rental.setTape(tape1);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter)); // 7-day rental
        rental.setReturnDate(LocalDate.parse("2025-01-09", formatter)); // returned 1 day late
        
        // Add rental to customer's rentals
        c1.getRentals().add(rental);
        
        // Calculate overdue fee with current date (not used since return date is set)
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double overdueFee = rental.calculateOverdueFee(currentDate);
        
        assertEquals("Overdue fee should be $0.50 for 1 day late", 0.50, overdueFee, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Create customer c2
        Customer c2 = new Customer();
        c2.setAccountNumber(2);
        c2.setName("Customer 2");
        
        // Create video tape V002
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("V002");
        tape2.setTitle("Video 2");
        
        // Add customer and tape to system
        rentalSystem.getCustomers().add(c2);
        rentalSystem.getInventory().add(tape2);
        
        // Create rental transaction (unreturned)
        RentalTransaction rental = new RentalTransaction();
        rental.setCustomer(c2);
        rental.setTape(tape2);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-09", formatter)); // 8-day rental
        rental.setReturnDate(null); // not returned
        
        // Add rental to customer's rentals
        c2.getRentals().add(rental);
        
        // Calculate overdue fee with current date 3 days after due date
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        double overdueFee = rental.calculateOverdueFee(currentDate);
        
        assertEquals("Overdue fee should be $1.50 for 3 days overdue", 1.50, overdueFee, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Create customer c3
        Customer c3 = new Customer();
        c3.setAccountNumber(3);
        c3.setName("Customer 3");
        
        // Create video tape V003
        VideoTape tape3 = new VideoTape();
        tape3.setBarcodeId("V003");
        tape3.setTitle("Video 3");
        
        // Add customer and tape to system
        rentalSystem.getCustomers().add(c3);
        rentalSystem.getInventory().add(tape3);
        
        // Create rental transaction
        RentalTransaction rental = new RentalTransaction();
        rental.setCustomer(c3);
        rental.setTape(tape3);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-06", formatter)); // 5-day rental
        rental.setReturnDate(LocalDate.parse("2025-01-06", formatter)); // returned on due date
        
        // Add rental to customer's rentals
        c3.getRentals().add(rental);
        
        // Calculate overdue fee with current date
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double overdueFee = rental.calculateOverdueFee(currentDate);
        
        assertEquals("Overdue fee should be $0.00 when returned on due date", 0.00, overdueFee, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Create customer c4
        Customer c4 = new Customer();
        c4.setAccountNumber(4);
        c4.setName("Customer 4");
        
        // Create video tape V004
        VideoTape tape4 = new VideoTape();
        tape4.setBarcodeId("V004");
        tape4.setTitle("Video 4");
        
        // Add customer and tape to system
        rentalSystem.getCustomers().add(c4);
        rentalSystem.getInventory().add(tape4);
        
        // Create rental transaction (unreturned but not overdue)
        RentalTransaction rental = new RentalTransaction();
        rental.setCustomer(c4);
        rental.setTape(tape4);
        rental.setRentalDate(LocalDate.parse("2025-01-05", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-10", formatter)); // 5-day rental
        rental.setReturnDate(null); // not returned
        
        // Add rental to customer's rentals
        c4.getRentals().add(rental);
        
        // Calculate overdue fee with current date before due date
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        double overdueFee = rental.calculateOverdueFee(currentDate);
        
        assertEquals("Overdue fee should be $0.00 when unreturned but not overdue", 0.00, overdueFee, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Create customer c5
        Customer c5 = new Customer();
        c5.setAccountNumber(5);
        c5.setName("Customer 5");
        
        // Create video tape V005
        VideoTape tape5 = new VideoTape();
        tape5.setBarcodeId("V005");
        tape5.setTitle("Video 5");
        
        // Add customer and tape to system
        rentalSystem.getCustomers().add(c5);
        rentalSystem.getInventory().add(tape5);
        
        // Create rental transaction (unreturned and 5 days overdue)
        RentalTransaction rental = new RentalTransaction();
        rental.setCustomer(c5);
        rental.setTape(tape5);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter)); // 7-day rental
        rental.setReturnDate(null); // not returned
        
        // Add rental to customer's rentals
        c5.getRentals().add(rental);
        
        // Calculate overdue fee with current date 5 days after due date
        LocalDate currentDate = LocalDate.parse("2025-01-13", formatter);
        double overdueFee = rental.calculateOverdueFee(currentDate);
        
        assertEquals("Overdue fee should be $3.00 for 5 days overdue", 3.00, overdueFee, 0.001);
    }
}