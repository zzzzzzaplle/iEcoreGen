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
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // Create rental R001 for customer C001
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Set current date to 2023-10-05 (after due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C001 is in the overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be John Doe", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // Create rental R002 for customer C002 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2025-10-10")); // Future date
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Set current date to 2023-10-12 (before due date)
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no overdue customers are found
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // Create rental R003 for customer C003 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setBackDate(null); // Not returned yet
        rental1.setDueDate(dateFormat.parse("2023-10-03")); // Past due date
        
        // Create rental R004 for customer C003 (returned on time)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Already returned
        rental2.setDueDate(dateFormat.parse("2024-10-02")); // Future due date
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2));
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C003 is in the overdue list (due to R003)
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be Alice Johnson", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // Create rental R005 for customer C004 (already returned)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Already returned
        rental.setDueDate(dateFormat.parse("2023-10-03")); // Due date before return date
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Set current date to any date (should not matter since car is returned)
        Date currentDate = dateFormat.parse("2023-10-10");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no overdue customers are found (car already returned)
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // Create rental R006 for customer C005 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2025-10-15")); // Future due date
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Set current date to 2023-10-05 (before due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no overdue customers are found (due date is in future)
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
}