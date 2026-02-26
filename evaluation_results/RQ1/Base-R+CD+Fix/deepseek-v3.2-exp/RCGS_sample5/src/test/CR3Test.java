import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
        // SetUp: Create customer C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // SetUp: Create rental R001 for customer C001
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Set current date to "2023-10-05" (overdue)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer C001 should be in overdue list
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // SetUp: Create rental R002 for customer C002
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2025-10-10")); // Future due date
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Set current date to "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue customers should be found
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // SetUp: Create rental R003 (overdue) for customer C003
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setBackDate(null); // Not returned yet
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        
        // SetUp: Create rental R004 (not overdue) for customer C003
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Already returned
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2));
        
        // Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer C003 should be in overdue list (due to R003)
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // SetUp: Create rental R005 for customer C004 (already returned)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Already returned
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Set current date to any date (method won't check overdue if backDate is not null)
        Date currentDate = dateFormat.parse("2023-10-10");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue customers should be found (rental already returned)
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // SetUp: Create rental R006 for customer C005
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2025-10-15")); // Future due date
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue customers should be found (due date is in future)
        assertTrue(overdueCustomers.isEmpty());
    }
}