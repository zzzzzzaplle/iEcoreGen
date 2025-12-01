import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR3Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // SetUp: Create customer C001 "John Doe"
        Customer customer = new Customer("John", "Doe", "Address1", "ABC123", LocalDate.of(2023, 10, 1));
        customer.setBackDate(null); // Explicitly set backDate to null
        
        // SetUp: Create car and rental for customer C001
        Car car = new Car("ABC123", "Model1", 50.0);
        Rental rental = new Rental(customer, car);
        
        // Add to store
        store.addCar(car);
        store.addCustomer(customer);
        store.addRental(rental);
        
        // Set current date to 2023-10-05 (overdue)
        // Note: In actual implementation, we would need to mock LocalDate.now()
        // For this test, we'll verify the logic by checking the customer's due date
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Expected Output: Customer ID C001 is overdue for rental R001
        // Since our Customer class doesn't have ID field, we'll check by name
        assertEquals(1, overdueCustomers.size());
        assertEquals("John", overdueCustomers.get(0).getName());
        assertEquals("Doe", overdueCustomers.get(0).getSurname());
        assertTrue(overdueCustomers.get(0).getDueDate().isBefore(LocalDate.now()));
        assertNull(overdueCustomers.get(0).getBackDate());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // SetUp: Create customer C002 "Jane Smith"
        Customer customer = new Customer("Jane", "Smith", "Address2", "DEF456", LocalDate.of(2025, 10, 10));
        customer.setBackDate(null); // Explicitly set backDate to null
        
        // SetUp: Create car and rental for customer C002
        Car car = new Car("DEF456", "Model2", 70.0);
        Rental rental = new Rental(customer, car);
        
        // Add to store
        store.addCar(car);
        store.addCustomer(customer);
        store.addRental(rental);
        
        // Set current date to 2023-10-12 (not overdue since due date is in future)
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C002
        assertEquals(0, overdueCustomers.size());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // SetUp: Create customer C003 "Alice Johnson"
        Customer customer = new Customer("Alice", "Johnson", "Address3", "GHI789", LocalDate.of(2023, 10, 3));
        customer.setBackDate(null); // Explicitly set backDate to null for first rental
        
        // SetUp: Create first rental R003 (overdue)
        Car car1 = new Car("GHI789", "Model3", 60.0);
        Rental rental1 = new Rental(customer, car1);
        
        // SetUp: Create second rental R004 (returned, not overdue)
        Customer customer2 = new Customer("Alice", "Johnson", "Address3", "JKL012", LocalDate.of(2024, 10, 2));
        customer2.setBackDate(LocalDate.of(2024, 10, 1)); // Returned before due date
        Car car2 = new Car("JKL012", "Model4", 80.0);
        Rental rental2 = new Rental(customer2, car2);
        
        // Add to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCustomer(customer);
        store.addCustomer(customer2);
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Set current date to 2023-10-05 (first rental is overdue)
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Expected Output: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue
        assertEquals(1, overdueCustomers.size());
        assertEquals("Alice", overdueCustomers.get(0).getName());
        assertEquals("Johnson", overdueCustomers.get(0).getSurname());
        
        // Verify that the returned rental is not in overdue list
        assertTrue(overdueCustomers.stream()
            .noneMatch(c -> c.getBackDate() != null));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // SetUp: Create customer C004 "Bob Brown"
        Customer customer = new Customer("Bob", "Brown", "Address4", "MNO345", LocalDate.of(2023, 10, 3));
        customer.setBackDate(LocalDate.of(2023, 10, 4)); // Returned after due date but has back date
        
        // SetUp: Create car and rental for customer C004
        Car car = new Car("MNO345", "Model5", 55.0);
        Rental rental = new Rental(customer, car);
        
        // Add to store
        store.addCar(car);
        store.addCustomer(customer);
        store.addRental(rental);
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C004, as rental R005 was returned on time
        // Note: The method only considers rentals with backDate == null as potentially overdue
        assertEquals(0, overdueCustomers.size());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // SetUp: Create customer C005 "Charlie Green"
        Customer customer = new Customer("Charlie", "Green", "Address5", "PQR678", LocalDate.of(2025, 10, 15));
        customer.setBackDate(null); // Explicitly set backDate to null
        
        // SetUp: Create car and rental for customer C005
        Car car = new Car("PQR678", "Model6", 65.0);
        Rental rental = new Rental(customer, car);
        
        // Add to store
        store.addCar(car);
        store.addCustomer(customer);
        store.addRental(rental);
        
        // Set current date to 2023-10-05 (due date is in future)
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C005, as the due date is in the future
        assertEquals(0, overdueCustomers.size());
    }
}