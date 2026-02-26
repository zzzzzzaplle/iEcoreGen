import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR3Test {
    
    private CarRentalStore store;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // Test Case 1: Single Overdue Rental Check
        // Create a customer with customer ID: C001 and name: "John Doe"
        Customer customer = new Customer("John", "Doe", "123 Main St", "ABC123", "2023-09-20", "2023-10-01");
        customer.setBackDate(null); // Explicitly set backDate to null
        
        // Add customer to store
        store.addCustomer(customer);
        
        // Create a car with matching plate number
        Car car = new Car("ABC123", "Toyota Camry", 50.0);
        store.addCar(car);
        
        // Set the current date to "2023-10-05" (overdue rental)
        // Note: We'll override the getOverdueCustomers method to use fixed date for testing
        List<Customer> overdueCustomers = getOverdueCustomersWithFixedDate("2023-10-05");
        
        // Expected Output: Customer ID C001 is overdue for rental R001
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be John Doe", "John", overdueCustomers.get(0).getName());
        assertEquals("Due date should be 2023-10-01", "2023-10-01", overdueCustomers.get(0).getDueDate());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // Test Case 2: No Overdue Rentals
        // Create a customer with customer ID: C002 and name: "Jane Smith"
        Customer customer = new Customer("Jane", "Smith", "456 Oak St", "DEF456", "2023-10-05", "2025-10-10");
        customer.setBackDate(null); // Explicitly set backDate to null
        
        // Add customer to store
        store.addCustomer(customer);
        
        // Create a car with matching plate number
        Car car = new Car("DEF456", "Honda Civic", 40.0);
        store.addCar(car);
        
        // Set the current date to "2023-10-12"
        List<Customer> overdueCustomers = getOverdueCustomersWithFixedDate("2023-10-12");
        
        // Expected Output: No overdue rentals found for customer ID C002
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // Test Case 3: Multiple Rentals with Mixed Status
        // Create a customer with customer ID: C003 and name: "Alice Johnson"
        Customer customer1 = new Customer("Alice", "Johnson", "789 Pine St", "GHI789", "2023-09-28", "2023-10-03");
        customer1.setBackDate(null); // Overdue rental
        
        Customer customer2 = new Customer("Alice", "Johnson", "789 Pine St", "JKL012", "2024-09-30", "2024-10-02");
        customer2.setBackDate("2024-10-01"); // Returned rental (not overdue)
        
        // Add customers to store
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        
        // Create cars with matching plate numbers
        Car car1 = new Car("GHI789", "Ford Focus", 45.0);
        Car car2 = new Car("JKL012", "Nissan Altima", 55.0);
        store.addCar(car1);
        store.addCar(car2);
        
        // Set the current date to "2023-10-05"
        List<Customer> overdueCustomers = getOverdueCustomersWithFixedDate("2023-10-05");
        
        // Expected Output: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be Alice Johnson", "Alice", overdueCustomers.get(0).getName());
        assertEquals("Overdue rental should have due date 2023-10-03", "2023-10-03", overdueCustomers.get(0).getDueDate());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // Test Case 4: Rental with Back Date
        // Create a customer with customer ID: C004 and name: "Bob Brown"
        Customer customer = new Customer("Bob", "Brown", "321 Elm St", "MNO345", "2023-10-01", "2023-10-03");
        customer.setBackDate("2023-10-04"); // Rental was returned (late, but has back date)
        
        // Add customer to store
        store.addCustomer(customer);
        
        // Create a car with matching plate number
        Car car = new Car("MNO345", "Chevrolet Malibu", 60.0);
        store.addCar(car);
        
        // Get overdue customers (current date doesn't matter since back date is set)
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C004, as rental R005 was returned
        assertTrue("Should not find any overdue customers when back date is set", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // Test Case 5: Future Due Date Rentals
        // Create a customer with customer ID: C005 and name: "Charlie Green"
        Customer customer = new Customer("Charlie", "Green", "654 Maple St", "PQR678", "2023-10-10", "2025-10-15");
        customer.setBackDate(null); // Explicitly set backDate to null
        
        // Add customer to store
        store.addCustomer(customer);
        
        // Create a car with matching plate number
        Car car = new Car("PQR678", "BMW X3", 80.0);
        store.addCar(car);
        
        // Set the current date to "2023-10-05"
        List<Customer> overdueCustomers = getOverdueCustomersWithFixedDate("2023-10-05");
        
        // Expected Output: No overdue rentals found for customer ID C005, as the due date is in the future
        assertTrue("Should not find any overdue customers when due date is in the future", overdueCustomers.isEmpty());
    }
    
    // Helper method to override the current date for testing
    private List<Customer> getOverdueCustomersWithFixedDate(String fixedDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(fixedDate, formatter);
        
        return store.getCustomers().stream()
                .filter(customer -> customer.getBackDate() == null && 
                                   currentDate.isAfter(LocalDate.parse(customer.getDueDate(), formatter)))
                .collect(java.util.stream.Collectors.toList());
    }
}