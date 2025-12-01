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
    public void testCase1_SingleOverdueRental() throws Exception {
        // Create customer with ID C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("CAR001");
        
        // Create rental with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        
        // Add to store
        store.getRentals().add(rental);
        
        // Set current date to overdue date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Check for overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer is overdue
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer should be in overdue list", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer with ID C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("CAR002");
        
        // Create rental with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        
        // Add to store
        store.getRentals().add(rental);
        
        // Set current date before due date
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Check for overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertEquals("Should find no overdue customers", 0, overdueCustomers.size());
    }
    
    @Test
    public void testCase3_MultipleRentalsMixedStatus() throws Exception {
        // Create customer with ID C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // Create cars for rentals
        Car car1 = new Car();
        car1.setPlate("CAR003A");
        Car car2 = new Car();
        car2.setPlate("CAR003B");
        
        // Create overdue rental
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Create returned rental (not overdue)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(dateFormat.parse("2024-10-01"));
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to make rental1 overdue
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Check for overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer is overdue (for rental1 only)
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer should be in overdue list", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer with ID C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("CAR004");
        
        // Create rental that was returned
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(dateFormat.parse("2023-10-04"));
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Add to store
        store.getRentals().add(rental);
        
        // Set current date (any date since rental has back date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Check for overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (rental was returned)
        assertEquals("Should find no overdue customers", 0, overdueCustomers.size());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer with ID C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("CAR005");
        
        // Create rental with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        
        // Add to store
        store.getRentals().add(rental);
        
        // Set current date before due date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Check for overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (due date is in future)
        assertEquals("Should find no overdue customers", 0, overdueCustomers.size());
    }
}