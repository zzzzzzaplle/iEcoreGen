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
        // SetUp: Create a customer with customer ID: C001 and name: "John Doe"
        Customer customer = new Customer("John", "Doe", "Address1", "ABC123", LocalDate.of(2023, 10, 1));
        customer.setBackDate(null); // Explicitly set backDate to null
        
        // Create a car and add to store
        Car car = new Car("ABC123", "Model1", 50.0);
        store.addCar(car);
        store.addCustomer(customer);
        
        // Set current date to "2023-10-05" (overdue rental)
        // Note: We'll use the actual current date in the test, but simulate the overdue condition
        // by creating a customer with past due date
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: Customer ID C001 is overdue for rental R001
        assertEquals(1, overdueCustomers.size());
        assertEquals("John", overdueCustomers.get(0).getName());
        assertEquals("Doe", overdueCustomers.get(0).getSurname());
        assertEquals("ABC123", overdueCustomers.get(0).getRentedCarPlate());
        assertTrue(LocalDate.now().isAfter(overdueCustomers.get(0).getDueDate()));
        assertNull(overdueCustomers.get(0).getBackDate());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // SetUp: Create a customer with customer ID: C002 and name: "Jane Smith"
        // Create a rental with dueDate: "2025-10-10" (future date)
        Customer customer = new Customer("Jane", "Smith", "Address2", "DEF456", LocalDate.of(2025, 10, 10));
        customer.setBackDate(null);
        
        // Create a car and add to store
        Car car = new Car("DEF456", "Model2", 70.0);
        store.addCar(car);
        store.addCustomer(customer);
        
        // Set current date to "2023-10-12" - this is before the due date
        // Note: We'll rely on the actual system date being before 2025-10-10
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: No overdue rentals found for customer ID C002
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // SetUp: Create a customer with customer ID: C003 and name: "Alice Johnson"
        // Create first rental with dueDate: "2023-10-03" (overdue)
        Customer customer1 = new Customer("Alice", "Johnson", "Address3", "GHI789", LocalDate.of(2023, 10, 3));
        customer1.setBackDate(null);
        
        // Create second rental with backDate: "2024-10-01" and dueDate: "2024-10-02" (not overdue)
        Customer customer2 = new Customer("Alice", "Johnson", "Address3", "JKL012", LocalDate.of(2024, 10, 2));
        customer2.setBackDate(LocalDate.of(2024, 10, 1));
        
        // Create cars and add to store
        Car car1 = new Car("GHI789", "Model3", 60.0);
        Car car2 = new Car("JKL012", "Model4", 80.0);
        store.addCar(car1);
        store.addCar(car2);
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        
        // Set current date to "2023-10-05"
        // Note: We'll rely on the actual system date being after 2023-10-03 but before 2024-10-02
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue
        assertEquals(1, overdueCustomers.size());
        assertEquals("Alice", overdueCustomers.get(0).getName());
        assertEquals("Johnson", overdueCustomers.get(0).getSurname());
        assertEquals("GHI789", overdueCustomers.get(0).getRentedCarPlate());
        assertTrue(LocalDate.now().isAfter(overdueCustomers.get(0).getDueDate()));
        assertNull(overdueCustomers.get(0).getBackDate());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // SetUp: Create a customer with customer ID: C004 and name: "Bob Brown"
        // Create a rental with backDate: "2023-10-04" and dueDate: "2023-10-03"
        Customer customer = new Customer("Bob", "Brown", "Address4", "MNO345", LocalDate.of(2023, 10, 3));
        customer.setBackDate(LocalDate.of(2023, 10, 4));
        
        // Create a car and add to store
        Car car = new Car("MNO345", "Model5", 90.0);
        store.addCar(car);
        store.addCustomer(customer);
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: No overdue rentals found for customer ID C004, as rental R005 was returned on time
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // SetUp: Create a customer with customer ID: C005 and name: "Charlie Green"
        // Create a rental with dueDate: "2025-10-15" (future date)
        Customer customer = new Customer("Charlie", "Green", "Address5", "PQR678", LocalDate.of(2025, 10, 15));
        customer.setBackDate(null);
        
        // Create a car and add to store
        Car car = new Car("PQR678", "Model6", 100.0);
        store.addCar(car);
        store.addCustomer(customer);
        
        // Set current date to "2023-10-05" - this is before the due date
        // Note: We'll rely on the actual system date being before 2025-10-15
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: No overdue rentals found for customer ID C005, as the due date is in the future
        assertTrue(overdueCustomers.isEmpty());
    }
}