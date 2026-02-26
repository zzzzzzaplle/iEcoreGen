import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private VideoRentalSystem system;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private VideoTape tape1;
    private VideoTape tape2;
    private VideoTape tape3;
    private VideoTape tape4;
    private VideoTape tape5;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_successfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer1 = new Customer();
        customer1.setAccountNumber("C001");
        customer1.setPastDueAmount(0.0);
        
        // Create 5 active rentals for customer1
        for (int i = 1; i <= 5; i++) {
            Rental rental = new Rental();
            rental.setRentalDate(LocalDate.parse("2025-01-0" + i, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            rental.setDueDate(rental.getRentalDate().plusDays(7));
            rental.setReturnDate(null);
            customer1.getRentals().add(rental);
        }
        
        // Create available Tape T001 with no active rentals
        tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        tape1.setAvailable(true);
        
        // Add customer and tape to system
        system.getCustomers().add(customer1);
        system.getInventory().add(tape1);
        
        // Test: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental(customer1, tape1, "2025-01-01");
        
        // Verify: Expected output is True
        assertTrue("Rental should be successful when customer has <20 rentals, no past due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_customerHas20Rentals() {
        // Setup: Create Customer C002 with 20 active rentals
        customer2 = new Customer();
        customer2.setAccountNumber("C002");
        customer2.setPastDueAmount(0.0);
        
        // Create 20 active rentals for customer2
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setRentalDate(LocalDate.parse("2025-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            rental.setDueDate(rental.getRentalDate().plusDays(7));
            rental.setReturnDate(null);
            customer2.getRentals().add(rental);
        }
        
        // Create available Tape T002
        tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        tape2.setAvailable(true);
        
        // Add customer and tape to system
        system.getCustomers().add(customer2);
        system.getInventory().add(tape2);
        
        // Test: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental(customer2, tape2, "2025-01-01");
        
        // Verify: Expected output is False (customer has 20 rentals)
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental, due_date="2025-01-02", return_date=null (3 days overdue from current_date="2025-01-05")
        customer3 = new Customer();
        customer3.setAccountNumber("C003");
        customer3.setPastDueAmount(1.5); // 3 days * $0.5 = $1.50 overdue fee
        
        // Create one active rental for customer3 that is overdue
        Rental overdueRental = new Rental();
        overdueRental.setRentalDate(LocalDate.parse("2024-12-26", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        overdueRental.setDueDate(LocalDate.parse("2025-01-02", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        overdueRental.setReturnDate(null);
        customer3.getRentals().add(overdueRental);
        
        // Create available Tape T003
        tape3 = new VideoTape();
        tape3.setBarCodeId("T003");
        tape3.setAvailable(true);
        
        // Add customer and tape to system
        system.getCustomers().add(customer3);
        system.getInventory().add(tape3);
        
        // Test: C003 rents tape "T003" with current_date="2025-01-05"
        boolean result = system.addVideoTapeRental(customer3, tape3, "2025-01-05");
        
        // Verify: Expected output is False (customer has past due amount)
        assertFalse("Rental should fail when customer has past due amount", result);
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer4 = new Customer();
        customer4.setAccountNumber("C004");
        customer4.setPastDueAmount(0.0);
        
        // Create Tape T004 with active rental (rented by another customer C010)
        tape4 = new VideoTape();
        tape4.setBarCodeId("T004");
        tape4.setAvailable(false);
        
        // Create another customer C010 who has rented T004
        Customer customer10 = new Customer();
        customer10.setAccountNumber("C010");
        
        Rental activeRental = new Rental();
        activeRental.setCustomer(customer10);
        activeRental.setTape(tape4);
        activeRental.setRentalDate(LocalDate.parse("2024-12-29", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        activeRental.setDueDate(LocalDate.parse("2025-01-05", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        activeRental.setReturnDate(null);
        
        // Add the active rental to system
        system.getRentals().add(activeRental);
        customer10.getRentals().add(activeRental);
        
        // Add customers and tape to system
        system.getCustomers().add(customer4);
        system.getCustomers().add(customer10);
        system.getInventory().add(tape4);
        
        // Test: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental(customer4, tape4, "2025-01-01");
        
        // Verify: Expected output is False (tape is unavailable)
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_allConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        customer5 = new Customer();
        customer5.setAccountNumber("C005");
        customer5.setPastDueAmount(5.0); // $5.00 overdue amount
        
        // Create 20 active rentals for customer5
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setRentalDate(LocalDate.parse("2025-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            rental.setDueDate(rental.getRentalDate().plusDays(7));
            rental.setReturnDate(null);
            customer5.getRentals().add(rental);
        }
        
        // Create Tape T005 with active rental (rented by another customer C011)
        tape5 = new VideoTape();
        tape5.setBarCodeId("T005");
        tape5.setAvailable(false);
        
        // Create another customer C011 who has rented T005
        Customer customer11 = new Customer();
        customer11.setAccountNumber("C011");
        
        Rental activeRental = new Rental();
        activeRental.setCustomer(customer11);
        activeRental.setTape(tape5);
        activeRental.setRentalDate(LocalDate.parse("2024-12-29", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        activeRental.setDueDate(LocalDate.parse("2025-01-05", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        activeRental.setReturnDate(null);
        
        // Add the active rental to system
        system.getRentals().add(activeRental);
        customer11.getRentals().add(activeRental);
        
        // Add customers and tape to system
        system.getCustomers().add(customer5);
        system.getCustomers().add(customer11);
        system.getInventory().add(tape5);
        
        // Test: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental(customer5, tape5, "2025-01-01");
        
        // Verify: Expected output is False (all conditions fail)
        assertFalse("Rental should fail when all conditions are violated", result);
    }
}