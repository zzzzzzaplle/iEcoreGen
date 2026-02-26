import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
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
    public void testCase1_SingleOverdueRentalCheck() throws ParseException {
        // Test Case 1: Single Overdue Rental Check
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("ABC123");
        
        // Create rental R001 with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        rental.setBackDate(null); // Not returned yet
        
        // Add to store
        store.setCars(List.of(car));
        store.setRentals(List.of(rental));
        
        // Set current date to 2023-10-05 (after due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be John Doe", customer, overdueCustomers.get(0));
        assertEquals("Should create 1 overdue notice", 1, store.getNotices().size());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws ParseException {
        // Test Case 2: No Overdue Rentals
        // Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("DEF456");
        
        // Create rental R002 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        rental.setBackDate(null); // Not returned yet
        
        // Add to store
        store.setCars(List.of(car));
        store.setRentals(List.of(rental));
        
        // Set current date to 2023-10-12 (before due date)
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
        assertTrue("Should not create any overdue notices", store.getNotices().isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws ParseException {
        // Test Case 3: Multiple Rentals with Mixed Status
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // Create cars for rentals
        Car car1 = new Car();
        car1.setPlate("GHI789");
        Car car2 = new Car();
        car2.setPlate("JKL012");
        
        // Create rental R003 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        rental1.setBackDate(null); // Not returned yet
        
        // Create rental R004 (returned, not overdue)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Returned before due date
        
        // Add to store
        store.setCars(List.of(car1, car2));
        store.setRentals(List.of(rental1, rental2));
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be Alice Johnson", customer, overdueCustomers.get(0));
        assertEquals("Should create 1 overdue notice for the overdue rental", 1, store.getNotices().size());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws ParseException {
        // Test Case 4: Rental with Back Date
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("MNO345");
        
        // Create rental R005 (returned on time)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Returned after due date but has back date
        
        // Add to store
        store.setCars(List.of(car));
        store.setRentals(List.of(rental));
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertTrue("Should not find any overdue customers since rental has back date", overdueCustomers.isEmpty());
        assertTrue("Should not create any overdue notices", store.getNotices().isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws ParseException {
        // Test Case 5: Future Due Date Rentals
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("PQR678");
        
        // Create rental R006 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        rental.setBackDate(null); // Not returned yet
        
        // Add to store
        store.setCars(List.of(car));
        store.setRentals(List.of(rental));
        
        // Set current date to 2023-10-05 (before due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
        assertTrue("Should not create any overdue notices", store.getNotices().isEmpty());
    }
}