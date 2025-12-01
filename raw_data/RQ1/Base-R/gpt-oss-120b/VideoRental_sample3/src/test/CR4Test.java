import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR4Test {
    
    private RentalService rentalService;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalService = new RentalService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = new Customer("C001", "Customer One");
        rentalService.registerCustomer(customer);
        
        VideoTape tape1 = new VideoTape("T001", "Movie One");
        VideoTape tape2 = new VideoTape("T002", "Movie Two");
        rentalService.addVideoTape(tape1);
        rentalService.addVideoTape(tape2);
        
        // Create rental transaction with two rentals
        RentalTransaction transaction = new RentalTransaction("TX001", customer, LocalDate.parse("2025-01-01", formatter));
        
        // Rental 1: returned early, no overdue fee
        Rental rental1 = new Rental(tape1, customer, 
            LocalDate.parse("2025-01-01", formatter), 
            LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        
        // Rental 2: returned early, no overdue fee
        Rental rental2 = new Rental(tape2, customer, 
            LocalDate.parse("2025-01-01", formatter), 
            LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        customer.getTransactions().add(transaction);
        rentalService.getTransactions().put("TX001", transaction);
        
        // Test - current date is 2025-01-20
        double result = rentalService.calculateTotalPriceForTransaction("TX001");
        
        // Verify
        assertEquals(13.00, result, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = new Customer("C002", "Customer Two");
        rentalService.registerCustomer(customer);
        
        VideoTape tape = new VideoTape("T003", "Movie Three");
        rentalService.addVideoTape(tape);
        
        // Create rental transaction with one overdue rental
        RentalTransaction transaction = new RentalTransaction("TX002", customer, LocalDate.parse("2025-01-01", formatter));
        
        // Rental: 7 days overdue
        Rental rental = new Rental(tape, customer, 
            LocalDate.parse("2025-01-01", formatter), 
            LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        transaction.getRentals().add(rental);
        customer.getTransactions().add(transaction);
        rentalService.getTransactions().put("TX002", transaction);
        
        // Test - current date is 2025-01-20
        double result = rentalService.calculateTotalPriceForTransaction("TX002");
        
        // Verify
        assertEquals(14.50, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        Customer customer = new Customer("C003", "Customer Three");
        rentalService.registerCustomer(customer);
        
        VideoTape tape1 = new VideoTape("T004", "Movie Four");
        VideoTape tape2 = new VideoTape("T005", "Movie Five");
        rentalService.addVideoTape(tape1);
        rentalService.addVideoTape(tape2);
        
        // Create rental transaction with two overdue rentals
        RentalTransaction transaction = new RentalTransaction("TX003", customer, LocalDate.parse("2025-01-01", formatter));
        
        // Rental 1: 4 days overdue
        Rental rental1 = new Rental(tape1, customer, 
            LocalDate.parse("2025-01-01", formatter), 
            LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        
        // Rental 2: 3 days overdue
        Rental rental2 = new Rental(tape2, customer, 
            LocalDate.parse("2025-01-10", formatter), 
            LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-18", formatter));
        
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        customer.getTransactions().add(transaction);
        rentalService.getTransactions().put("TX003", transaction);
        
        // Test - current date is 2025-01-20
        double result = rentalService.calculateTotalPriceForTransaction("TX003");
        
        // Verify
        assertEquals(19.50, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = new Customer("C004", "Customer Four");
        rentalService.registerCustomer(customer);
        
        VideoTape tape1 = new VideoTape("T006", "Movie Six");
        VideoTape tape2 = new VideoTape("T007", "Movie Seven");
        rentalService.addVideoTape(tape1);
        rentalService.addVideoTape(tape2);
        
        // Create rental transaction with mixed rentals
        RentalTransaction transaction = new RentalTransaction("TX004", customer, LocalDate.parse("2025-01-01", formatter));
        
        // Rental 1: 2 days overdue
        Rental rental1 = new Rental(tape1, customer, 
            LocalDate.parse("2025-01-01", formatter), 
            LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-07", formatter));
        
        // Rental 2: on-time return
        Rental rental2 = new Rental(tape2, customer, 
            LocalDate.parse("2025-01-10", formatter), 
            LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-14", formatter));
        
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        customer.getTransactions().add(transaction);
        rentalService.getTransactions().put("TX004", transaction);
        
        // Test - current date is 2025-01-20
        double result = rentalService.calculateTotalPriceForTransaction("TX004");
        
        // Verify
        assertEquals(11.00, result, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        Customer customer = new Customer("C006", "Customer Six");
        rentalService.registerCustomer(customer);
        
        VideoTape tape = new VideoTape("T008", "Movie Eight");
        rentalService.addVideoTape(tape);
        
        // Create rental transaction with unreturned rental
        RentalTransaction transaction = new RentalTransaction("TX005", customer, LocalDate.parse("2025-01-01", formatter));
        
        // Rental: unreturned, 5 days overdue as of current date 2025-01-10
        Rental rental = new Rental(tape, customer, 
            LocalDate.parse("2025-01-01", formatter), 
            LocalDate.parse("2025-01-05", formatter));
        // returnDate remains null (unreturned)
        
        transaction.getRentals().add(rental);
        customer.getTransactions().add(transaction);
        rentalService.getTransactions().put("TX005", transaction);
        
        // Test - current date is 2025-01-10
        double result = rentalService.calculateTotalPriceForTransaction("TX005");
        
        // Verify
        assertEquals(11.50, result, 0.001);
    }
}