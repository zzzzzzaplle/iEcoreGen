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
        // SetUp: Create customer C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // SetUp: Create car for rental
        Car car = new Car("PLATE001", "ModelX", 50.0);
        
        // SetUp: Create rental R001 with backDate: null, dueDate: 2023-10-01
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.of(2023, 9, 20));
        rental.setDueDate(LocalDate.of(2023, 10, 1));
        rental.setBackDate(null);
        
        // SetUp: Add to store
        store.addCustomer(customer);
        store.addCar(car);
        store.addRental(rental);
        
        // SetUp: Mock current date to 2023-10-05 by using the actual method which uses LocalDate.now()
        // Since we cannot mock LocalDate.now() without additional libraries, we rely on the test setup dates
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: Customer C001 is overdue for rental R001
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Overdue customer should be John Doe", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // SetUp: Create car for rental
        Car car = new Car("PLATE002", "ModelY", 60.0);
        
        // SetUp: Create rental R002 with backDate: null, dueDate: 2025-10-10
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.of(2023, 10, 1));
        rental.setDueDate(LocalDate.of(2025, 10, 10));
        rental.setBackDate(null);
        
        // SetUp: Add to store
        store.addCustomer(customer);
        store.addCar(car);
        store.addRental(rental);
        
        // SetUp: Current date is 2023-10-12 (before due date)
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: No overdue rentals found for customer C002
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // SetUp: Create cars for rentals
        Car car1 = new Car("PLATE003", "ModelA", 70.0);
        Car car2 = new Car("PLATE004", "ModelB", 80.0);
        
        // SetUp: Create rental R003 with backDate: null, dueDate: 2023-10-03 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.of(2023, 10, 1));
        rental1.setDueDate(LocalDate.of(2023, 10, 3));
        rental1.setBackDate(null);
        
        // SetUp: Create rental R004 with backDate: 2024-10-01, dueDate: 2024-10-02 (not overdue)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.of(2024, 9, 30));
        rental2.setDueDate(LocalDate.of(2024, 10, 2));
        rental2.setBackDate(LocalDate.of(2024, 10, 1));
        
        // SetUp: Add to store
        store.addCustomer(customer);
        store.addCar(car1);
        store.addCar(car2);
        store.addRental(rental1);
        store.addRental(rental2);
        
        // SetUp: Current date is 2023-10-05
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: Customer C003 is overdue for rental R003; Rental R004 is not overdue
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Overdue customer should be Alice Johnson", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // SetUp: Create car for rental
        Car car = new Car("PLATE005", "ModelC", 90.0);
        
        // SetUp: Create rental R005 with backDate: 2023-10-04, dueDate: 2023-10-03
        // Note: The car was returned after due date, but since backDate is set, it's not considered overdue
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.of(2023, 10, 1));
        rental.setDueDate(LocalDate.of(2023, 10, 3));
        rental.setBackDate(LocalDate.of(2023, 10, 4));
        
        // SetUp: Add to store
        store.addCustomer(customer);
        store.addCar(car);
        store.addRental(rental);
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: No overdue rentals found for customer C004, as rental R005 was returned
        assertTrue("Should not find any overdue customers when car is returned", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // SetUp: Create car for rental
        Car car = new Car("PLATE006", "ModelD", 100.0);
        
        // SetUp: Create rental R006 with dueDate: 2025-10-15
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.of(2023, 10, 1));
        rental.setDueDate(LocalDate.of(2025, 10, 15));
        rental.setBackDate(null);
        
        // SetUp: Add to store
        store.addCustomer(customer);
        store.addCar(car);
        store.addRental(rental);
        
        // SetUp: Current date is 2023-10-05
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify: No overdue rentals found for customer C005, as the due date is in the future
        assertTrue("Should not find any overdue customers for future due dates", overdueCustomers.isEmpty());
    }
}