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
        customer.setName("John Doe");
        
        // Create rental R001 for customer C001
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2023-10-01")); // Due date in the past
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (after due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C001 is in the overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be John Doe", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // Create rental R002 for customer C002
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2025-10-10")); // Due date in the future
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-12 (before due date)
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are overdue
        assertTrue("Should return empty list when no overdue rentals exist", overdueCustomers.isEmpty());
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
        rental1.setDueDate(dateFormat.parse("2023-10-03")); // Due date in the past
        
        // Create rental R004 for customer C003 (not overdue - already returned)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Already returned
        rental2.setDueDate(dateFormat.parse("2024-10-02")); // Due date in the future
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C003 is in the overdue list (only for rental R003)
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be Alice Johnson", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // Create rental R005 for customer C004
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Already returned
        rental.setDueDate(dateFormat.parse("2023-10-03")); // Due date in the past
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date (any date since car is already returned)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are overdue (car was returned)
        assertTrue("Should return empty list when rental has back date", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // Create rental R006 for customer C005
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2025-10-15")); // Due date in the future
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (before due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are overdue (due date is in future)
        assertTrue("Should return empty list when due date is in the future", overdueCustomers.isEmpty());
    }
}