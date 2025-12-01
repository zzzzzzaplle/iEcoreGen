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
        dateFormat.setLenient(false);
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() throws Exception {
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // Create rental R001 with overdue status
        Rental rental = new Rental();
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        rental.setBackDate(null); // Not returned
        rental.setCustomer(customer);
        rental.setCar(new Car()); // Car is required but not relevant for this test
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Set current date to after due date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C001 is in overdue list
        assertEquals("Should have one overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be John Doe", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // Create rental R002 with future due date
        Rental rental = new Rental();
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        rental.setBackDate(null); // Not returned but not overdue
        rental.setCustomer(customer);
        rental.setCar(new Car());
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Set current date to before due date
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // Create rental R003 - overdue
        Rental rental1 = new Rental();
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        rental1.setBackDate(null); // Not returned and overdue
        rental1.setCustomer(customer);
        rental1.setCar(new Car());
        
        // Create rental R004 - returned on time
        Rental rental2 = new Rental();
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Returned before due date
        rental2.setCustomer(customer);
        rental2.setCar(new Car());
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2));
        
        // Set current date to after R003 due date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C003 is in overdue list (due to R003)
        assertEquals("Should have one overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be Alice Johnson", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // Create rental R005 - returned (even though after due date, it has back date so not considered overdue)
        Rental rental = new Rental();
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Returned (has back date)
        rental.setCustomer(customer);
        rental.setCar(new Car());
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Set current date to after due date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (since rental has back date)
        assertTrue("Should have no overdue customers when rental has back date", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // Create rental R006 - future due date
        Rental rental = new Rental();
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        rental.setBackDate(null); // Not returned but not overdue
        rental.setCustomer(customer);
        rental.setCar(new Car());
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Set current date to before due date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertTrue("Should have no overdue customers with future due dates", overdueCustomers.isEmpty());
    }
}