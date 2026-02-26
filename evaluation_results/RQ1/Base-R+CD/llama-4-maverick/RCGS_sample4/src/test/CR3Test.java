import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // Create rental R001 with backDate: null and dueDate: "2023-10-01"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to "2023-10-05" (overdue rental)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C001 is in the overdue list
        assertTrue("Customer C001 should be overdue", overdueCustomers.contains(customer));
        assertEquals("Only one customer should be overdue", 1, overdueCustomers.size());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // Create rental R002 with backDate: null and dueDate: "2025-10-10"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are overdue
        assertTrue("No customers should be overdue", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // Create rental R003 with backDate: null and dueDate: "2023-10-03" (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Create rental R004 with backDate: "2024-10-01" and dueDate: "2024-10-02" (not overdue)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setBackDate(dateFormat.parse("2024-10-01"));
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C003 is overdue for rental R003
        assertTrue("Customer C003 should be overdue for rental R003", overdueCustomers.contains(customer));
        assertEquals("Only one customer should be overdue", 1, overdueCustomers.size());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // Create rental R005 with backDate: "2023-10-04" and dueDate: "2023-10-03"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(dateFormat.parse("2023-10-04"));
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to any date (method doesn't check rentals with backDate)
        Date currentDate = dateFormat.parse("2023-10-10");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are overdue (since rental has backDate)
        assertTrue("No customers should be overdue when rental has backDate", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // Create rental R006 with dueDate: "2025-10-15" (no backDate specified)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are overdue (due date is in future)
        assertTrue("No customers should be overdue when due date is in future", overdueCustomers.isEmpty());
    }
}