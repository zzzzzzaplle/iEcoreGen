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
    public void testCase1_SingleOverdueRental() throws Exception {
        // SetUp: Create customer C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // SetUp: Create rental R001 with backDate: null, dueDate: 2023-10-01
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setDueDate(dateFormat.parse("2023-10-01 00:00:00"));
        rental.setBackDate(null);
        
        // Add rental to store
        store.setRentals(List.of(rental));
        
        // SetUp: Set current date to 2023-10-05 (overdue)
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer C001 is overdue
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
        
        // Verify: Overdue notice was created
        assertEquals(1, store.getNotices().size());
        assertEquals(customer, store.getNotices().get(0).getCustomer());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // SetUp: Create rental R002 with backDate: null, dueDate: 2025-10-10
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setDueDate(dateFormat.parse("2025-10-10 00:00:00"));
        rental.setBackDate(null);
        
        // Add rental to store
        store.setRentals(List.of(rental));
        
        // SetUp: Set current date to 2023-10-12
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue rentals found
        assertTrue(overdueCustomers.isEmpty());
        
        // Verify: No overdue notices created
        assertTrue(store.getNotices().isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // SetUp: Create rental R003 (overdue) with backDate: null, dueDate: 2023-10-03
        Rental rentalOverdue = new Rental();
        rentalOverdue.setCustomer(customer);
        rentalOverdue.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        rentalOverdue.setBackDate(null);
        
        // SetUp: Create rental R004 (not overdue) with backDate: 2024-10-01, dueDate: 2024-10-02
        Rental rentalReturned = new Rental();
        rentalReturned.setCustomer(customer);
        rentalReturned.setDueDate(dateFormat.parse("2024-10-02 00:00:00"));
        rentalReturned.setBackDate(dateFormat.parse("2024-10-01 00:00:00"));
        
        // Add rentals to store
        store.setRentals(List.of(rentalOverdue, rentalReturned));
        
        // SetUp: Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer C003 is overdue for rental R003
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
        
        // Verify: Overdue notice was created for the overdue rental
        assertEquals(1, store.getNotices().size());
        assertEquals(customer, store.getNotices().get(0).getCustomer());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // SetUp: Create rental R005 with backDate: 2023-10-04, dueDate: 2023-10-03
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        rental.setBackDate(dateFormat.parse("2023-10-04 00:00:00"));
        
        // Add rental to store
        store.setRentals(List.of(rental));
        
        // Execute: Find customers with overdue rentals (current date doesn't matter for returned rentals)
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue rentals found since car was returned (even though late)
        assertTrue(overdueCustomers.isEmpty());
        
        // Verify: No overdue notices created
        assertTrue(store.getNotices().isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // SetUp: Create rental R006 with dueDate: 2025-10-15
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setDueDate(dateFormat.parse("2025-10-15 00:00:00"));
        rental.setBackDate(null);
        
        // Add rental to store
        store.setRentals(List.of(rental));
        
        // SetUp: Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue rentals found as due date is in the future
        assertTrue(overdueCustomers.isEmpty());
        
        // Verify: No overdue notices created
        assertTrue(store.getNotices().isEmpty());
    }
}