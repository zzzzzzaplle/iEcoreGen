import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR4Test {
    
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup: Create Customer C001
        Customer customer = new Customer();
        customer.setAccountId("C001");
        
        // Create Rental 1: returned early, no overdue
        Rental rental1 = new Rental();
        rental1.setTapeId("T001");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 3));
        rental1.setRentalDays(4); // Jan 1-5 = 4 days rental
        rental1.setBaseRentalFee(4.0);
        
        // Create Rental 2: returned early, no overdue
        Rental rental2 = new Rental();
        rental2.setTapeId("T002");
        rental2.setRentalDate(LocalDate.of(2025, 1, 1));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 12));
        rental2.setRentalDays(14); // Jan 1-15 = 14 days rental
        rental2.setBaseRentalFee(14.0);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice();
        
        // Verify: Rental 1 price: 4 + 0 = 4, Rental 2 price: 14 + 0 = 14, total = 18
        assertEquals(18.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setAccountId("C002");
        
        // Create Rental 1: returned late, 7 days overdue
        Rental rental1 = new Rental();
        rental1.setTapeId("T003");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 12));
        rental1.setRentalDays(4); // Jan 1-5 = 4 days rental
        rental1.setBaseRentalFee(4.0);
        
        // Add rental to customer
        customer.getRentals().add(rental1);
        
        // Create transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice();
        
        // Verify: 4 (base fee) + 3.50 (7 days × $0.50) = $7.50
        assertEquals(7.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setAccountId("C003");
        
        // Create Rental 1: 4 days overdue
        Rental rental1 = new Rental();
        rental1.setTapeId("T004");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 9));
        rental1.setRentalDays(4); // Jan 1-5 = 4 days rental
        rental1.setBaseRentalFee(4.0);
        
        // Create Rental 2: 3 days overdue
        Rental rental2 = new Rental();
        rental2.setTapeId("T005");
        rental2.setRentalDate(LocalDate.of(2025, 1, 10));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 18));
        rental2.setRentalDays(5); // Jan 10-15 = 5 days rental
        rental2.setBaseRentalFee(5.0);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice();
        
        // Verify: (4+5 base fees) + (2.00+1.50 overdue) = 9 + 3.50 = $12.50
        assertEquals(12.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setAccountId("C004");
        
        // Create Rental 1: 2 days overdue
        Rental rental1 = new Rental();
        rental1.setTapeId("T006");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 7));
        rental1.setRentalDays(4); // Jan 1-5 = 4 days rental
        rental1.setBaseRentalFee(4.0);
        
        // Create Rental 2: on-time
        Rental rental2 = new Rental();
        rental2.setTapeId("T007");
        rental2.setRentalDate(LocalDate.of(2025, 1, 10));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 14));
        rental2.setRentalDays(5); // Jan 10-15 = 5 days rental
        rental2.setBaseRentalFee(5.0);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice();
        
        // Verify: (4+5 base fees) + 1.00 overdue = 9 + 1.00 = $10.00
        assertEquals(10.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup: Create Customer C006
        Customer customer = new Customer();
        customer.setAccountId("C006");
        
        // Create Rental 1: unreturned, 5 days overdue as of current date 2025-01-10
        Rental rental1 = new Rental();
        rental1.setTapeId("T008");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Not returned
        rental1.setRentalDays(4); // Jan 1-5 = 4 days rental
        rental1.setBaseRentalFee(4.0);
        
        // Add rental to customer
        customer.getRentals().add(rental1);
        
        // Create transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        
        // Calculate total price (current date is 2025-01-10)
        double totalPrice = transaction.calculateTotalPrice();
        
        // Verify: 4 (base fee) + 2.50 (5 days × $0.50) = $6.50
        assertEquals(6.50, totalPrice, 0.001);
    }
}