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
        // Setup
        Customer customer = new Customer("John", "Doe", "C001");
        Car car = new Car("PLATE001", "ModelX", 50.0);
        Rental rental = new Rental(car, customer, 
                                 LocalDate.of(2023, 9, 20),
                                 LocalDate.of(2023, 10, 1), // due date in past
                                 null, // backDate null = active rental
                                 0.0);
        
        store.addCustomer(customer);
        store.addCar(car);
        store.addRental(rental);
        
        // Test: Get overdue customers with current date after due date
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: Customer C001 should be in overdue list
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be overdue", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // Setup
        Customer customer = new Customer("Jane", "Smith", "C002");
        Car car = new Car("PLATE002", "ModelY", 60.0);
        Rental rental = new Rental(car, customer,
                                 LocalDate.of(2023, 10, 1),
                                 LocalDate.of(2025, 10, 10), // due date in future
                                 null, // active rental but not overdue
                                 0.0);
        
        store.addCustomer(customer);
        store.addCar(car);
        store.addRental(rental);
        
        // Test: Get overdue customers with current date before due date
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: No overdue customers should be found
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // Setup
        Customer customer = new Customer("Alice", "Johnson", "C003");
        Car car1 = new Car("PLATE003", "ModelA", 70.0);
        Car car2 = new Car("PLATE004", "ModelB", 80.0);
        
        // Overdue rental (active + due date in past)
        Rental rental1 = new Rental(car1, customer,
                                   LocalDate.of(2023, 9, 20),
                                   LocalDate.of(2023, 10, 3), // due date in past
                                   null, // active rental
                                   0.0);
        
        // Completed rental (not active)
        Rental rental2 = new Rental(car2, customer,
                                   LocalDate.of(2024, 9, 20),
                                   LocalDate.of(2024, 10, 2),
                                   LocalDate.of(2024, 10, 1), // returned early
                                   80.0);
        
        store.addCustomer(customer);
        store.addCar(car1);
        store.addCar(car2);
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Test: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: Customer C003 should be overdue for rental1 only
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be overdue", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // Setup
        Customer customer = new Customer("Bob", "Brown", "C004");
        Car car = new Car("PLATE005", "ModelC", 90.0);
        Rental rental = new Rental(car, customer,
                                 LocalDate.of(2023, 9, 20),
                                 LocalDate.of(2023, 10, 3),
                                 LocalDate.of(2023, 10, 4), // returned (not active)
                                 90.0);
        
        store.addCustomer(customer);
        store.addCar(car);
        store.addRental(rental);
        
        // Test: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: No overdue customers since rental was returned
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // Setup
        Customer customer = new Customer("Charlie", "Green", "C005");
        Car car = new Car("PLATE006", "ModelD", 100.0);
        Rental rental = new Rental(car, customer,
                                 LocalDate.of(2023, 10, 1),
                                 LocalDate.of(2025, 10, 15), // due date far in future
                                 null, // active but not overdue
                                 0.0);
        
        store.addCustomer(customer);
        store.addCar(car);
        store.addRental(rental);
        
        // Test: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: No overdue customers since due date is in future
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }
}