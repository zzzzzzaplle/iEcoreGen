import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.*;

public class CR4Test {
    
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        String customerId = "C001";
        Customer customer = new Customer(customerId, "Customer One");
        rentalSystem.addCustomer(customer);
        
        // Create and add Rental 1: returned early
        RentalTransaction rental1 = new RentalTransaction(
            "TR001", customerId, "T001", 
            LocalDate.of(2025, 1, 1), 
            LocalDate.of(2025, 1, 5)
        );
        rental1.setReturnDate(LocalDate.of(2025, 1, 3));
        customer.addRental(rental1);
        rentalSystem.getRentals().add(rental1);
        
        // Create and add Rental 2: returned early
        RentalTransaction rental2 = new RentalTransaction(
            "TR002", customerId, "T002", 
            LocalDate.of(2025, 1, 1), 
            LocalDate.of(2025, 1, 15)
        );
        rental2.setReturnDate(LocalDate.of(2025, 1, 12));
        customer.addRental(rental2);
        rentalSystem.getRentals().add(rental2);
        
        // Execute
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        double result = rentalSystem.calculateTotalPrice(customerId);
        
        // Verify
        assertEquals(13.00, result, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        String customerId = "C002";
        Customer customer = new Customer(customerId, "Customer Two");
        rentalSystem.addCustomer(customer);
        
        // Create and add Rental 1: returned late
        RentalTransaction rental1 = new RentalTransaction(
            "TR003", customerId, "T003", 
            LocalDate.of(2025, 1, 1), 
            LocalDate.of(2025, 1, 5)
        );
        rental1.setReturnDate(LocalDate.of(2025, 1, 12));
        customer.addRental(rental1);
        rentalSystem.getRentals().add(rental1);
        
        // Execute
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        double result = rentalSystem.calculateTotalPrice(customerId);
        
        // Verify
        assertEquals(14.50, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        String customerId = "C003";
        Customer customer = new Customer(customerId, "Customer Three");
        rentalSystem.addCustomer(customer);
        
        // Create and add Rental 1: returned late
        RentalTransaction rental1 = new RentalTransaction(
            "TR004", customerId, "T004", 
            LocalDate.of(2025, 1, 1), 
            LocalDate.of(2025, 1, 5)
        );
        rental1.setReturnDate(LocalDate.of(2025, 1, 9));
        customer.addRental(rental1);
        rentalSystem.getRentals().add(rental1);
        
        // Create and add Rental 2: returned late
        RentalTransaction rental2 = new RentalTransaction(
            "TR005", customerId, "T005", 
            LocalDate.of(2025, 1, 10), 
            LocalDate.of(2025, 1, 15)
        );
        rental2.setReturnDate(LocalDate.of(2025, 1, 18));
        customer.addRental(rental2);
        rentalSystem.getRentals().add(rental2);
        
        // Execute
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        double result = rentalSystem.calculateTotalPrice(customerId);
        
        // Verify
        assertEquals(19.50, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        String customerId = "C004";
        Customer customer = new Customer(customerId, "Customer Four");
        rentalSystem.addCustomer(customer);
        
        // Create and add Rental 1: returned late
        RentalTransaction rental1 = new RentalTransaction(
            "TR006", customerId, "T006", 
            LocalDate.of(2025, 1, 1), 
            LocalDate.of(2025, 1, 5)
        );
        rental1.setReturnDate(LocalDate.of(2025, 1, 7));
        customer.addRental(rental1);
        rentalSystem.getRentals().add(rental1);
        
        // Create and add Rental 2: returned on time
        RentalTransaction rental2 = new RentalTransaction(
            "TR007", customerId, "T007", 
            LocalDate.of(2025, 1, 10), 
            LocalDate.of(2025, 1, 15)
        );
        rental2.setReturnDate(LocalDate.of(2025, 1, 14));
        customer.addRental(rental2);
        rentalSystem.getRentals().add(rental2);
        
        // Execute
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        double result = rentalSystem.calculateTotalPrice(customerId);
        
        // Verify
        assertEquals(11.00, result, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        String customerId = "C006";
        Customer customer = new Customer(customerId, "Customer Six");
        rentalSystem.addCustomer(customer);
        
        // Create and add Rental 1: not returned (overdue)
        RentalTransaction rental1 = new RentalTransaction(
            "TR008", customerId, "T008", 
            LocalDate.of(2025, 1, 1), 
            LocalDate.of(2025, 1, 5)
        );
        // returnDate remains null
        customer.addRental(rental1);
        rentalSystem.getRentals().add(rental1);
        
        // Execute
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        double result = rentalSystem.calculateTotalPrice(customerId);
        
        // Verify
        assertEquals(11.50, result, 0.001);
    }
}