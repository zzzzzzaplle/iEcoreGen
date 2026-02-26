import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
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
    public void testCase1_singleOverdueRentalCheck() throws ParseException {
        // Create a customer with customer ID: C001 and name: "John Doe"
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // Create a rental with rental ID: R001 for customer ID: C001, 
        // with backDate: null, and dueDate: "2023-10-01"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        Date dueDate = dateFormat.parse("2023-10-01");
        rental.setDueDate(dueDate);
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set the current date to "2023-10-05" (overdue rental)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C001 is in the overdue list
        assertEquals(1, overdueCustomers.size());
        assertEquals("John", overdueCustomers.get(0).getName());
        assertEquals("Doe", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase2_noOverdueRentals() throws ParseException {
        // Create a customer with customer ID: C002 and name: "Jane Smith"
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // Create a rental with rental ID: R002 for customer ID: C002, 
        // with backDate: null, and dueDate: "2025-10-10"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        Date dueDate = dateFormat.parse("2025-10-10");
        rental.setDueDate(dueDate);
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set the current date to "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute the method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are in the overdue list
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_multipleRentalsWithMixedStatus() throws ParseException {
        // Create a customer with customer ID: C003 and name: "Alice Johnson"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // Create a rental with rental ID: R003 for customer ID: C003, 
        // with backDate: null, and dueDate: "2023-10-03"
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setBackDate(null);
        Date dueDate1 = dateFormat.parse("2023-10-03");
        rental1.setDueDate(dueDate1);
        
        // Create another rental with rental ID: R004 for customer ID: C003, 
        // with backDate: "2024-10-01", and dueDate: "2024-10-02"
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        Date backDate2 = dateFormat.parse("2024-10-01");
        rental2.setBackDate(backDate2);
        Date dueDate2 = dateFormat.parse("2024-10-02");
        rental2.setDueDate(dueDate2);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set the current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C003 is in the overdue list (only for rental R003)
        assertEquals(1, overdueCustomers.size());
        assertEquals("Alice", overdueCustomers.get(0).getName());
        assertEquals("Johnson", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase4_rentalWithBackDate() throws ParseException {
        // Create a customer with customer ID: C004 and name: "Bob Brown"
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // Create a rental with rental ID: R005 for customer ID: C004, 
        // with backDate: "2023-10-04", and dueDate: "2023-10-03"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        Date backDate = dateFormat.parse("2023-10-04");
        rental.setBackDate(backDate);
        Date dueDate = dateFormat.parse("2023-10-03");
        rental.setDueDate(dueDate);
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Execute the method under test (current date doesn't matter for returned rentals)
        Date currentDate = dateFormat.parse("2023-10-05");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are in the overdue list since rental was returned
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_futureDueDateRentals() throws ParseException {
        // Create a customer with customer ID: C005 and name: "Charlie Green"
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // Create a rental with rental ID: R006 for customer ID: C005, 
        // with dueDate: "2025-10-15" and backDate: null
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        Date dueDate = dateFormat.parse("2025-10-15");
        rental.setDueDate(dueDate);
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set the current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are in the overdue list since due date is in the future
        assertTrue(overdueCustomers.isEmpty());
    }
}