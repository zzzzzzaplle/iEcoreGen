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
        // SetUp: Create customer C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // SetUp: Create rental R001 with backDate: null, dueDate: "2023-10-01"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01 00:00:00"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to "2023-10-05" (overdue rental)
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer C001 is overdue for rental R001
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // SetUp: Create rental R002 with backDate: null, dueDate: "2025-10-10"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10 00:00:00"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue rentals found for customer ID C002
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // SetUp: Create rental R003 with backDate: null, dueDate: "2023-10-03" (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        
        // SetUp: Create rental R004 with backDate: "2024-10-01", dueDate: "2024-10-02" (not overdue - already returned)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setBackDate(dateFormat.parse("2024-10-01 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-10-02 00:00:00"));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // SetUp: Create rental R005 with backDate: "2023-10-04", dueDate: "2023-10-03"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(dateFormat.parse("2023-10-04 00:00:00"));
        rental.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to current system date (any date since rental is already returned)
        Date currentDate = new Date();
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue rentals found for customer ID C004, as rental R005 was returned
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // SetUp: Create rental R006 with dueDate: "2025-10-15" (backDate is null by default)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setDueDate(dateFormat.parse("2025-10-15 00:00:00"));
        // backDate is null (not returned yet)
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue rentals found for customer ID C005, as the due date is in the future
        assertTrue(overdueCustomers.isEmpty());
    }
}