import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Create customer C001
        Customer customer = new Customer();
        customer.setAccountId("C001");
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create and setup Rental 1: returned early, no overdue fee
        Rental rental1 = new Rental();
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 3));
        rental1.setRentalDays(4); // Jan 1 to Jan 5 is 4 days
        rental1.setBaseRentalFee(4.0); // 4 days × $1 = $4
        rental1.setOverdueFee(0.0); // returned early
        
        // Create and setup Rental 2: returned early, no overdue fee
        Rental rental2 = new Rental();
        rental2.setRentalDate(LocalDate.of(2025, 1, 1));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 12));
        rental2.setRentalDays(14); // Jan 1 to Jan 15 is 14 days
        rental2.setBaseRentalFee(14.0); // 14 days × $1 = $14
        rental2.setOverdueFee(0.0); // returned early
        
        // Add rentals to transaction
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice();
        
        // Verify expected total: 4 + 14 = $18
        assertEquals(18.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Create customer C002
        Customer customer = new Customer();
        customer.setAccountId("C002");
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create and setup Rental 1: 7 days overdue
        Rental rental1 = new Rental();
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 12));
        rental1.setRentalDays(4); // Jan 1 to Jan 5 is 4 days
        rental1.setBaseRentalFee(4.0); // 4 days × $1 = $4
        rental1.setOverdueFee(3.50); // 7 days × $0.5 = $3.50
        
        // Add rental to transaction
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice();
        
        // Verify expected total: 4 + 3.50 = $7.50
        assertEquals(7.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Create customer C003
        Customer customer = new Customer();
        customer.setAccountId("C003");
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create and setup Rental 1: 4 days overdue
        Rental rental1 = new Rental();
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 9));
        rental1.setRentalDays(4); // Jan 1 to Jan 5 is 4 days
        rental1.setBaseRentalFee(4.0); // 4 days × $1 = $4
        rental1.setOverdueFee(2.00); // 4 days × $0.5 = $2.00
        
        // Create and setup Rental 2: 3 days overdue
        Rental rental2 = new Rental();
        rental2.setRentalDate(LocalDate.of(2025, 1, 10));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 18));
        rental2.setRentalDays(5); // Jan 10 to Jan 15 is 5 days
        rental2.setBaseRentalFee(5.0); // 5 days × $1 = $5
        rental2.setOverdueFee(1.50); // 3 days × $0.5 = $1.50
        
        // Add rentals to transaction
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice();
        
        // Verify expected total: 4 + 5 + 2 + 1.50 = $12.50
        assertEquals(12.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Create customer C004
        Customer customer = new Customer();
        customer.setAccountId("C004");
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create and setup Rental 1: 2 days overdue
        Rental rental1 = new Rental();
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 7));
        rental1.setRentalDays(4); // Jan 1 to Jan 5 is 4 days
        rental1.setBaseRentalFee(4.0); // 4 days × $1 = $4
        rental1.setOverdueFee(1.00); // 2 days × $0.5 = $1.00
        
        // Create and setup Rental 2: on-time return
        Rental rental2 = new Rental();
        rental2.setRentalDate(LocalDate.of(2025, 1, 10));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 14));
        rental2.setRentalDays(5); // Jan 10 to Jan 15 is 5 days
        rental2.setBaseRentalFee(5.0); // 5 days × $1 = $5
        rental2.setOverdueFee(0.0); // returned early
        
        // Add rentals to transaction
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice();
        
        // Verify expected total: 4 + 5 + 1 = $10.00
        assertEquals(10.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Create customer C006
        Customer customer = new Customer();
        customer.setAccountId("C006");
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create and setup Rental 1: unreturned, 5 days overdue as of current date
        Rental rental1 = new Rental();
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // not returned
        rental1.setRentalDays(4); // Jan 1 to Jan 5 is 4 days
        rental1.setBaseRentalFee(4.0); // 4 days × $1 = $4
        
        // Calculate overdue fee for unreturned tape as of current date (2025-01-10)
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(
            LocalDate.of(2025, 1, 5), currentDate);
        double overdueFee = Math.round(overdueDays * 0.5 * 100.0) / 100.0;
        rental1.setOverdueFee(overdueFee); // 5 days × $0.5 = $2.50
        
        // Add rental to transaction
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice();
        
        // Verify expected total: 4 + 2.50 = $6.50
        assertEquals(6.50, totalPrice, 0.001);
    }
}