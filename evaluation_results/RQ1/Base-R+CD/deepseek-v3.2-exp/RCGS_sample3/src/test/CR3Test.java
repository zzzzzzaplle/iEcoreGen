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
        customer.setName("John");
        customer.setSurname("Doe");
        
        // SetUp: Create rental R001 for customer C001
        Rental rental = new Rental();
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        rental.setCustomer(customer);
        
        // Add rental to store
        store.addRental(rental);
        
        // Set current date to 2023-10-05 (overdue)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer C001 should be in the overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be overdue", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // SetUp: Create rental R002 for customer C002
        Rental rental = new Rental();
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2025-10-10")); // Future due date
        rental.setCustomer(customer);
        
        // Add rental to store
        store.addRental(rental);
        
        // Set current date to 2023-10-12
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue customers should be found
        assertTrue("Should return empty list when no overdue rentals exist", 
                   overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // SetUp: Create rental R003 (overdue) for customer C003
        Rental rental1 = new Rental();
        rental1.setBackDate(null); // Not returned yet
        rental1.setDueDate(dateFormat.parse("2023-10-03")); // Past due date
        rental1.setCustomer(customer);
        
        // SetUp: Create rental R004 (returned) for customer C003
        Rental rental2 = new Rental();
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Already returned
        rental2.setDueDate(dateFormat.parse("2024-10-02")); // Future due date
        rental2.setCustomer(customer);
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer C003 should be in overdue list due to rental R003
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be overdue due to rental R003", 
                   overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // SetUp: Create rental R005 for customer C004 (already returned)
        Rental rental = new Rental();
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Already returned
        rental.setDueDate(dateFormat.parse("2023-10-03")); // Due date before return date
        rental.setCustomer(customer);
        
        // Add rental to store
        store.addRental(rental);
        
        // Set current date to any date (irrelevant since rental is returned)
        Date currentDate = dateFormat.parse("2023-10-10");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue customers should be found since rental was returned
        assertTrue("Should return empty list when rental has back date", 
                   overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // SetUp: Create rental R006 for customer C005
        Rental rental = new Rental();
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2025-10-15")); // Future due date
        rental.setCustomer(customer);
        
        // Add rental to store
        store.addRental(rental);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue customers should be found since due date is in future
        assertTrue("Should return empty list when due date is in future", 
                   overdueCustomers.isEmpty());
    }
}