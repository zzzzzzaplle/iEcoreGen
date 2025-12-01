import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR3Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // Test Case 1: Single Overdue Rental Check
        // Input: Check for overdue rentals for customer ID: C001
        // SetUp:
        // 1. Create a customer with customer ID: C001 and name: "John Doe"
        Customer customer = new Customer("John", "Doe", "123 Main St");
        
        // 2. Create a rental with rental ID: R001 for customer ID: C001, with backDate: null, and dueDate: "2023-10-01"
        Car car = new Car("ABC123", "Toyota", 50.0);
        store.addCar(car);
        
        LocalDate rentDate = LocalDate.of(2023, 9, 20);
        LocalDate dueDate = LocalDate.of(2023, 10, 1);
        store.rentCar(customer, car, rentDate, dueDate);
        
        // 3. Set the current date to "2023-10-05" (overdue rental)
        // Note: We cannot directly set LocalDate.now() in the method, so we'll test with the logic
        
        // Get the rental that was just created
        Rental rental = store.getRentals().get(0);
        
        // 4. Create an overdue notice to customer C001
        // Expected Output: Customer ID C001 is overdue for rental R001
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify that customer C001 is in the overdue list
        assertTrue("Customer should be in overdue list", overdueCustomers.contains(customer));
        assertEquals("Should have exactly one overdue customer", 1, overdueCustomers.size());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // Test Case 2: No Overdue Rentals
        // Input: Check for overdue rentals for customer ID: C002
        // SetUp:
        // 1. Create a customer with customer ID: C002 and name: "Jane Smith"
        Customer customer = new Customer("Jane", "Smith", "456 Oak St");
        
        // 2. Create a rental with rental ID: R002 for customer ID: C002, with backDate: null, and dueDate: "2025-10-10"
        Car car = new Car("DEF456", "Honda", 60.0);
        store.addCar(car);
        
        LocalDate rentDate = LocalDate.of(2023, 10, 1);
        LocalDate dueDate = LocalDate.of(2025, 10, 10);
        store.rentCar(customer, car, rentDate, dueDate);
        
        // 3. Set the current date to "2023-10-12"
        // Note: We cannot directly set LocalDate.now() in the method, so we'll test with the logic
        
        // Expected Output: No overdue rentals found for customer ID C002
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify that no customers are overdue
        assertTrue("Overdue customers list should be empty", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // Test Case 3: Multiple Rentals with Mixed Status
        // Input: Check for overdue rentals for customer ID: C003
        // SetUp:
        // 1. Create a customer with customer ID: C003 and name: "Alice Johnson"
        Customer customer = new Customer("Alice", "Johnson", "789 Pine St");
        
        // 2. Create a rental with rental ID: R003 for customer ID: C003, with backDate: null, and dueDate: "2023-10-03"
        Car car1 = new Car("GHI789", "Ford", 70.0);
        store.addCar(car1);
        
        LocalDate rentDate1 = LocalDate.of(2023, 9, 25);
        LocalDate dueDate1 = LocalDate.of(2023, 10, 3);
        store.rentCar(customer, car1, rentDate1, dueDate1);
        
        // 3. Create another rental with rental ID: R004 for customer ID: C003, with backDate: "2024-10-01", and dueDate: "2024-10-02"
        Car car2 = new Car("JKL012", "BMW", 100.0);
        store.addCar(car2);
        
        LocalDate rentDate2 = LocalDate.of(2024, 9, 28);
        LocalDate dueDate2 = LocalDate.of(2024, 10, 2);
        Rental rental2 = new Rental(customer, car2, rentDate2, dueDate2);
        rental2.setBackDate(LocalDate.of(2024, 10, 1));
        store.getRentals().add(rental2);
        
        // 4. Set the current date to "2023-10-05"
        // Note: We cannot directly set LocalDate.now() in the method, so we'll test with the logic
        
        // Expected Output: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify that customer C003 is in the overdue list
        assertTrue("Customer should be in overdue list", overdueCustomers.contains(customer));
        assertEquals("Should have exactly one overdue customer", 1, overdueCustomers.size());
        
        // Verify that only the first rental is overdue
        Rental rental1 = store.getRentals().get(0);
        Rental rental2FromStore = store.getRentals().get(1);
        
        assertNull("First rental should not have back date", rental1.getBackDate());
        assertNotNull("Second rental should have back date", rental2FromStore.getBackDate());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // Test Case 4: Rental with Back Date
        // Input: Check for overdue rentals for customer ID: C004
        // SetUp:
        // 1. Create a customer with customer ID: C004 and name: "Bob Brown"
        Customer customer = new Customer("Bob", "Brown", "321 Elm St");
        
        // 2. Create a rental with rental ID: R005 for customer ID: C004, with backDate: "2023-10-04", and dueDate: "2023-10-03"
        Car car = new Car("MNO345", "Mercedes", 120.0);
        store.addCar(car);
        
        LocalDate rentDate = LocalDate.of(2023, 10, 1);
        LocalDate dueDate = LocalDate.of(2023, 10, 3);
        Rental rental = new Rental(customer, car, rentDate, dueDate);
        rental.setBackDate(LocalDate.of(2023, 10, 4));
        store.getRentals().add(rental);
        
        // Expected Output: No overdue rentals found for customer ID C004, as rental R005 was returned on time
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify that no customers are overdue
        assertTrue("Overdue customers list should be empty", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // Test Case 5: Future Due Date Rentals
        // Input: Check for overdue rentals for customer ID: C005
        // SetUp:
        // 1. Create a customer with customer ID: C005 and name: "Charlie Green"
        Customer customer = new Customer("Charlie", "Green", "654 Maple St");
        
        // 2. Create a rental with rental ID: R006 for customer ID: C005, with dueDate: "2025-10-15"
        Car car = new Car("PQR678", "Audi", 90.0);
        store.addCar(car);
        
        LocalDate rentDate = LocalDate.of(2023, 10, 1);
        LocalDate dueDate = LocalDate.of(2025, 10, 15);
        store.rentCar(customer, car, rentDate, dueDate);
        
        // 3. Set the current date to "2023-10-05"
        // Note: We cannot directly set LocalDate.now() in the method, so we'll test with the logic
        
        // Expected Output: No overdue rentals found for customer ID C005, as the due date is in the future
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify that no customers are overdue
        assertTrue("Overdue customers list should be empty", overdueCustomers.isEmpty());
    }
}