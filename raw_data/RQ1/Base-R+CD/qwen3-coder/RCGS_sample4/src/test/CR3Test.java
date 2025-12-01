import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
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
    public void testCase1_singleOverdueRentalCheck() throws ParseException {
        // Create a customer with customer ID: C001 and name: "John Doe"
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");

        // Create a rental with rental ID: R001 for customer ID: C001, with backDate: null, and dueDate: "2023-10-01"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01"));

        // Add rental to store
        store.getRentals().add(rental);

        // Set the current date to "2023-10-05" (overdue rental)
        Date currentDate = dateFormat.parse("2023-10-05");

        // Execute the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);

        // Expected Output: Customer ID C001 is overdue for rental R001
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
    }

    @Test
    public void testCase2_noOverdueRentals() throws ParseException {
        // Create a customer with customer ID: C002 and name: "Jane Smith"
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");

        // Create a rental with rental ID: R002 for customer ID: C002, with backDate: null, and dueDate: "2025-10-10"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10"));

        // Add rental to store
        store.getRentals().add(rental);

        // Set the current date to "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12");

        // Execute the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);

        // Expected Output: No overdue rentals found for customer ID C002
        assertEquals(0, overdueCustomers.size());
    }

    @Test
    public void testCase3_multipleRentalsWithMixedStatus() throws ParseException {
        // Create a customer with customer ID: C003 and name: "Alice Johnson"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");

        // Create a rental with rental ID: R003 for customer ID: C003, with backDate: null, and dueDate: "2023-10-03"
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));

        // Create another rental with rental ID: R004 for customer ID: C003, with backDate: "2024-10-01", and dueDate: "2024-10-02"
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setBackDate(dateFormat.parse("2024-10-01"));
        rental2.setDueDate(dateFormat.parse("2024-10-02"));

        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);

        // Set the current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");

        // Execute the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);

        // Expected Output: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
    }

    @Test
    public void testCase4_rentalWithBackDate() throws ParseException {
        // Create a customer with customer ID: C004 and name: "Bob Brown"
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");

        // Create a rental with rental ID: R005 for customer ID: C004, with backDate: "2023-10-04", and dueDate: "2023-10-03"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(dateFormat.parse("2023-10-04"));
        rental.setDueDate(dateFormat.parse("2023-10-03"));

        // Add rental to store
        store.getRentals().add(rental);

        // Execute the method (current date doesn't matter since backDate is not null)
        Date currentDate = dateFormat.parse("2023-10-05");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);

        // Expected Output: No overdue rentals found for customer ID C004, as rental R005 was returned on time
        assertEquals(0, overdueCustomers.size());
    }

    @Test
    public void testCase5_futureDueDateRentals() throws ParseException {
        // Create a customer with customer ID: C005 and name: "Charlie Green"
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");

        // Create a rental with rental ID: R006 for customer ID: C005, with dueDate: "2025-10-15"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-15"));

        // Add rental to store
        store.getRentals().add(rental);

        // Set the current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");

        // Execute the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);

        // Expected Output: No overdue rentals found for customer ID C005, as the due date is in the future
        assertEquals(0, overdueCustomers.size());
    }
}