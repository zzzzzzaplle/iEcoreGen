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
    }
    
    @Test
    public void testCase1_singleOverdueRentalCheck() throws Exception {
        // Create a customer with customer ID: C001 and name: "John Doe"
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // Create a rental with rental ID: R001 for customer ID: C001, 
        // with backDate: null, and dueDate: "2023-10-01"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        
        // Add rental to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Set the current date to "2023-10-05" (overdue rental)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C001 is in the overdue list
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_noOverdueRentals() throws Exception {
        // Create a customer with customer ID: C002 and name: "Jane Smith"
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // Create a rental with rental ID: R002 for customer ID: C002, 
        // with backDate: null, and dueDate: "2025-10-10"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        
        // Add rental to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Set the current date to "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are in the overdue list
        assertEquals(0, overdueCustomers.size());
    }
    
    @Test
    public void testCase3_multipleRentalsWithMixedStatus() throws Exception {
        // Create a customer with customer ID: C003 and name: "Alice Johnson"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // Create a rental with rental ID: R003 for customer ID: C003, 
        // with backDate: null, and dueDate: "2023-10-03"
        Rental overdueRental = new Rental();
        overdueRental.setCustomer(customer);
        overdueRental.setBackDate(null);
        overdueRental.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Create another rental with rental ID: R004 for customer ID: C003, 
        // with backDate: "2024-10-01", and dueDate: "2024-10-02"
        Rental returnedRental = new Rental();
        returnedRental.setCustomer(customer);
        returnedRental.setBackDate(dateFormat.parse("2024-10-01"));
        returnedRental.setDueDate(dateFormat.parse("2024-10-02"));
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(overdueRental);
        rentals.add(returnedRental);
        store.setRentals(rentals);
        
        // Set the current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C003 is in the overdue list (due to R003)
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase4_rentalWithBackDate() throws Exception {
        // Create a customer with customer ID: C004 and name: "Bob Brown"
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // Create a rental with rental ID: R005 for customer ID: C004, 
        // with backDate: "2023-10-04", and dueDate: "2023-10-03"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(dateFormat.parse("2023-10-04"));
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Add rental to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Execute the method with any current date (rental already returned)
        Date currentDate = dateFormat.parse("2023-10-05");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are in the overdue list
        assertEquals(0, overdueCustomers.size());
    }
    
    @Test
    public void testCase5_futureDueDateRentals() throws Exception {
        // Create a customer with customer ID: C005 and name: "Charlie Green"
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // Create a rental with rental ID: R006 for customer ID: C005, 
        // with dueDate: "2025-10-15" and backDate: null
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        
        // Add rental to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Set the current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are in the overdue list
        assertEquals(0, overdueCustomers.size());
    }
}