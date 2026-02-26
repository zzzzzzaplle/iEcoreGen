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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() throws Exception {
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // Create rental R001 with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setDueDate(dateFormat.parse("2023-10-01 00:00:00"));
        rental.setBackDate(null); // Not returned yet
        
        // Set current date to after due date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Find overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer is overdue
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
        
        // Verify overdue notice was created
        assertEquals(1, store.getNotices().size());
        assertEquals(customer, store.getNotices().get(0).getCustomer());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // Create rental R002 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setDueDate(dateFormat.parse("2025-10-10 00:00:00"));
        rental.setBackDate(null); // Not returned yet
        
        // Set current date to before due date
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Find overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertTrue(overdueCustomers.isEmpty());
        
        // Verify no overdue notices were created
        assertTrue(store.getNotices().isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // Create overdue rental R003
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        rental1.setBackDate(null); // Not returned yet - overdue
        
        // Create returned rental R004 (not overdue since it's returned)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setDueDate(dateFormat.parse("2024-10-02 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2024-10-01 00:00:00")); // Returned before due date
        
        // Set current date to after R003's due date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Find overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify only one overdue customer found (for R003)
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
        
        // Verify overdue notice was created for R003
        assertEquals(1, store.getNotices().size());
        assertEquals(customer, store.getNotices().get(0).getCustomer());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // Create rental R005 that was returned (has back date)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        rental.setBackDate(dateFormat.parse("2023-10-04 00:00:00")); // Returned (even though after due date)
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to after due date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Find overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found since rental was returned
        assertTrue(overdueCustomers.isEmpty());
        
        // Verify no overdue notices were created
        assertTrue(store.getNotices().isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // Create rental R006 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setDueDate(dateFormat.parse("2025-10-15 00:00:00"));
        rental.setBackDate(null); // Not returned yet
        
        // Set current date to before due date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Find overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found since due date is in future
        assertTrue(overdueCustomers.isEmpty());
        
        // Verify no overdue notices were created
        assertTrue(store.getNotices().isEmpty());
    }
}