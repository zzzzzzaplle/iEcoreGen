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
        customer.setName("John");
        customer.setSurname("Doe");
        
        // Create rental R001 with backDate: null and dueDate: 2023-10-01
        Rental rental = new Rental();
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01 00:00:00"));
        rental.setCustomer(customer);
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (overdue)
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C001 is in overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be in overdue list", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // Create rental R002 with backDate: null and dueDate: 2025-10-10 (future)
        Rental rental = new Rental();
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10 00:00:00"));
        rental.setCustomer(customer);
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-12 (before due date)
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertTrue("Should find no overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // Create rental R003 with backDate: null and dueDate: 2023-10-03 (overdue)
        Rental rental1 = new Rental();
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        rental1.setCustomer(customer);
        
        // Create rental R004 with backDate: 2024-10-01 (returned) and dueDate: 2024-10-02
        Rental rental2 = new Rental();
        rental2.setBackDate(dateFormat.parse("2024-10-01 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-10-02 00:00:00"));
        rental2.setCustomer(customer);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C003 is in overdue list (only for rental R003)
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be in overdue list for rental R003", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // Create rental R005 with backDate: 2023-10-04 (returned) and dueDate: 2023-10-03
        Rental rental = new Rental();
        rental.setBackDate(dateFormat.parse("2023-10-04 00:00:00"));
        rental.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        rental.setCustomer(customer);
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date (any date since backDate is not null)
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (backDate is not null)
        assertTrue("Should find no overdue customers when backDate is not null", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // Create rental R006 with dueDate: 2025-10-15 (future)
        Rental rental = new Rental();
        rental.setDueDate(dateFormat.parse("2025-10-15 00:00:00"));
        rental.setCustomer(customer);
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (before due date)
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (due date is in future)
        assertTrue("Should find no overdue customers when due date is in future", overdueCustomers.isEmpty());
    }
}