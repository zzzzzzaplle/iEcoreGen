import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() throws Exception {
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("ABC123");
        
        // Create rental R001 with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        rental.setBackDate(null); // Not returned yet
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (past due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C001 is in overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be John Doe", "John", overdueCustomers.get(0).getName());
        assertEquals("Customer should be John Doe", "Doe", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("DEF456");
        
        // Create rental R002 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        rental.setBackDate(null); // Not returned yet
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-12 (before due date)
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Find overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertEquals("Should find no overdue customers", 0, overdueCustomers.size());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // Create first car for rental R003
        Car car1 = new Car();
        car1.setPlate("GHI789");
        
        // Create rental R003 with overdue status
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        rental1.setBackDate(null); // Not returned yet
        
        // Create second car for rental R004
        Car car2 = new Car();
        car2.setPlate("JKL012");
        
        // Create rental R004 with back date (returned)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Returned early
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer is overdue for R003 but not for R004
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be Alice Johnson", "Alice", overdueCustomers.get(0).getName());
        assertEquals("Customer should be Alice Johnson", "Johnson", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("MNO345");
        
        // Create rental R005 with back date (returned)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Returned late but has back date
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date (any date since rental has back date)
        Date currentDate = dateFormat.parse("2023-10-10");
        
        // Find overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (rental has back date)
        assertEquals("Should find no overdue customers when rental has back date", 0, overdueCustomers.size());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("PQR678");
        
        // Create rental R006 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        rental.setBackDate(null); // Not returned yet
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (before due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (due date is in future)
        assertEquals("Should find no overdue customers when due date is in future", 0, overdueCustomers.size());
    }
}