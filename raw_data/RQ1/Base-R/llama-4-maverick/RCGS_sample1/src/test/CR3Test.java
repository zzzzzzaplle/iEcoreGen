import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR3Test {
    
    private CarRentalStore carRentalStore;
    
    @Before
    public void setUp() {
        carRentalStore = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // Set up test data
        Customer customer = new Customer("John", "Doe", "123 Main St");
        customer.setName("John Doe"); // Override to match specification exactly
        
        Car car = new Car("PLATE001", "Model X", 50.0);
        Rental rental = new Rental(car, LocalDate.of(2023, 10, 1));
        rental.setBackDate(null); // Explicitly set to null as per specification
        
        customer.addRental(rental);
        carRentalStore.getCustomers().add(customer);
        
        // Set current date to 2023-10-05 (overdue condition)
        // Note: In actual implementation, we would need to mock LocalDate.now()
        // For this test, we'll rely on the actual implementation using system date
        
        // Execute method under test
        List<Customer> overdueCustomers = carRentalStore.getCustomersWithOverdueRentals();
        
        // Verify result - customer should be in overdue list
        assertTrue("Customer C001 should be in overdue list", 
                   overdueCustomers.contains(customer));
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // Set up test data
        Customer customer = new Customer("Jane", "Smith", "456 Oak St");
        customer.setName("Jane Smith"); // Override to match specification exactly
        
        Car car = new Car("PLATE002", "Model Y", 60.0);
        Rental rental = new Rental(car, LocalDate.of(2025, 10, 10));
        rental.setBackDate(null); // Not returned yet, but due date is in future
        
        customer.addRental(rental);
        carRentalStore.getCustomers().add(customer);
        
        // Set current date to 2023-10-12 (before due date)
        // Note: In actual implementation, we would need to mock LocalDate.now()
        
        // Execute method under test
        List<Customer> overdueCustomers = carRentalStore.getCustomersWithOverdueRentals();
        
        // Verify result - no overdue customers should be found
        assertTrue("No overdue customers should be found", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // Set up test data
        Customer customer = new Customer("Alice", "Johnson", "789 Pine St");
        customer.setName("Alice Johnson"); // Override to match specification exactly
        
        // First rental - overdue (backDate null, due date in past)
        Car car1 = new Car("PLATE003", "Model Z", 70.0);
        Rental rental1 = new Rental(car1, LocalDate.of(2023, 10, 3));
        rental1.setBackDate(null); // Not returned, overdue
        
        // Second rental - not overdue (already returned)
        Car car2 = new Car("PLATE004", "Model A", 80.0);
        Rental rental2 = new Rental(car2, LocalDate.of(2024, 10, 2));
        rental2.setBackDate(LocalDate.of(2024, 10, 1)); // Returned before due date
        
        customer.addRental(rental1);
        customer.addRental(rental2);
        carRentalStore.getCustomers().add(customer);
        
        // Set current date to 2023-10-05
        // Note: In actual implementation, we would need to mock LocalDate.now()
        
        // Execute method under test
        List<Customer> overdueCustomers = carRentalStore.getCustomersWithOverdueRentals();
        
        // Verify result - customer should be in overdue list due to rental1
        assertTrue("Customer C003 should be in overdue list due to R003", 
                   overdueCustomers.contains(customer));
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // Set up test data
        Customer customer = new Customer("Bob", "Brown", "101 Maple St");
        customer.setName("Bob Brown"); // Override to match specification exactly
        
        Car car = new Car("PLATE005", "Model B", 90.0);
        Rental rental = new Rental(car, LocalDate.of(2023, 10, 3));
        rental.setBackDate(LocalDate.of(2023, 10, 4)); // Returned after due date but has back date
        
        customer.addRental(rental);
        carRentalStore.getCustomers().add(customer);
        
        // Execute method under test
        List<Customer> overdueCustomers = carRentalStore.getCustomersWithOverdueRentals();
        
        // Verify result - no overdue customers since rental has back date
        assertTrue("No overdue customers should be found as rental has back date", 
                   overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // Set up test data
        Customer customer = new Customer("Charlie", "Green", "202 Elm St");
        customer.setName("Charlie Green"); // Override to match specification exactly
        
        Car car = new Car("PLATE006", "Model C", 100.0);
        Rental rental = new Rental(car, LocalDate.of(2025, 10, 15));
        rental.setBackDate(null); // Not returned but due date is in future
        
        customer.addRental(rental);
        carRentalStore.getCustomers().add(customer);
        
        // Set current date to 2023-10-05
        // Note: In actual implementation, we would need to mock LocalDate.now()
        
        // Execute method under test
        List<Customer> overdueCustomers = carRentalStore.getCustomersWithOverdueRentals();
        
        // Verify result - no overdue customers since due date is in future
        assertTrue("No overdue customers should be found as due date is in future", 
                   overdueCustomers.isEmpty());
    }
}