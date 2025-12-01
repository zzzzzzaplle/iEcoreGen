import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

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
        
        // SetUp: Create rental R001 with overdue condition
        Rental rental = new Rental();
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        rental.setCustomer(customer);
        
        // SetUp: Add rental to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Input: Check for overdue rentals with current date "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Expected Output: Customer C001 should be in overdue list
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be John Doe", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // SetUp: Create rental R002 with future due date
        Rental rental = new Rental();
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        rental.setCustomer(customer);
        
        // SetUp: Add rental to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Input: Check for overdue rentals with current date "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Expected Output: No overdue rentals found
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // SetUp: Create overdue rental R003
        Rental rental1 = new Rental();
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        rental1.setCustomer(customer);
        
        // SetUp: Create returned rental R004
        Rental rental2 = new Rental();
        rental2.setBackDate(dateFormat.parse("2024-10-01"));
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        rental2.setCustomer(customer);
        
        // SetUp: Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Input: Check for overdue rentals with current date "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Expected Output: Customer C003 should be in overdue list (for rental R003 only)
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be Alice Johnson", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // SetUp: Create rental R005 that was returned (backDate not null)
        Rental rental = new Rental();
        rental.setBackDate(dateFormat.parse("2023-10-04"));
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        rental.setCustomer(customer);
        
        // SetUp: Add rental to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Input: Check for overdue rentals with any current date
        Date currentDate = dateFormat.parse("2023-10-05");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Expected Output: No overdue rentals found since rental was returned
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // SetUp: Create rental R006 with future due date
        Rental rental = new Rental();
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        rental.setCustomer(customer);
        
        // SetUp: Add rental to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Input: Check for overdue rentals with current date "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Expected Output: No overdue rentals found as due date is in future
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
}