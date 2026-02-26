import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
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
    public void testCase1_singleOverdueRentalCheck() throws ParseException {
        // Create a customer with customer ID: C001 and name: "John Doe"
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        // Create a rental with rental ID: R001 for customer ID: C001, with backDate: null, and dueDate: "2023-10-01"
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-01"));
        
        // Add rental to store
        store.getRentals().add(rental1);
        
        // Set the current date to "2023-10-05" (overdue rental)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C001 is in the overdue list
        assertEquals(1, overdueCustomers.size());
        assertEquals("John", overdueCustomers.get(0).getName());
        assertEquals("Doe", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase2_noOverdueRentals() throws ParseException {
        // Create a customer with customer ID: C002 and name: "Jane Smith"
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        // Create a rental with rental ID: R002 for customer ID: C002, with backDate: null, and dueDate: "2025-10-10"
        Rental rental2 = new Rental();
        rental2.setCustomer(customer2);
        rental2.setBackDate(null);
        rental2.setDueDate(dateFormat.parse("2025-10-10"));
        
        // Add rental to store
        store.getRentals().add(rental2);
        
        // Set the current date to "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are in the overdue list
        assertEquals(0, overdueCustomers.size());
    }
    
    @Test
    public void testCase3_multipleRentalsWithMixedStatus() throws ParseException {
        // Create a customer with customer ID: C003 and name: "Alice Johnson"
        Customer customer3 = new Customer();
        customer3.setName("Alice");
        customer3.setSurname("Johnson");
        
        // Create a rental with rental ID: R003 for customer ID: C003, with backDate: null, and dueDate: "2023-10-03"
        Rental rental3 = new Rental();
        rental3.setCustomer(customer3);
        rental3.setBackDate(null);
        rental3.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Create another rental with rental ID: R004 for customer ID: C003, with backDate: "2024-10-01", and dueDate: "2024-10-02"
        Rental rental4 = new Rental();
        rental4.setCustomer(customer3);
        rental4.setBackDate(dateFormat.parse("2024-10-01"));
        rental4.setDueDate(dateFormat.parse("2024-10-02"));
        
        // Add rentals to store
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        
        // Set the current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C003 is in the overdue list (only once, for the overdue rental)
        assertEquals(1, overdueCustomers.size());
        assertEquals("Alice", overdueCustomers.get(0).getName());
        assertEquals("Johnson", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase4_rentalWithBackDate() throws ParseException {
        // Create a customer with customer ID: C004 and name: "Bob Brown"
        Customer customer4 = new Customer();
        customer4.setName("Bob");
        customer4.setSurname("Brown");
        
        // Create a rental with rental ID: R005 for customer ID: C004, with backDate: "2023-10-04", and dueDate: "2023-10-03"
        Rental rental5 = new Rental();
        rental5.setCustomer(customer4);
        rental5.setBackDate(dateFormat.parse("2023-10-04"));
        rental5.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Add rental to store
        store.getRentals().add(rental5);
        
        // Execute the method (current date doesn't matter since rental has back date)
        Date currentDate = dateFormat.parse("2023-10-05");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are in the overdue list
        assertEquals(0, overdueCustomers.size());
    }
    
    @Test
    public void testCase5_futureDueDateRentals() throws ParseException {
        // Create a customer with customer ID: C005 and name: "Charlie Green"
        Customer customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Green");
        
        // Create a rental with rental ID: R006 for customer ID: C005, with dueDate: "2025-10-15"
        Rental rental6 = new Rental();
        rental6.setCustomer(customer5);
        rental6.setBackDate(null);
        rental6.setDueDate(dateFormat.parse("2025-10-15"));
        
        // Add rental to store
        store.getRentals().add(rental6);
        
        // Set the current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are in the overdue list
        assertEquals(0, overdueCustomers.size());
    }
}