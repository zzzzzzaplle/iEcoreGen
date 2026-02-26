import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR3Test {
    private RentalStore rentalStore;
    
    @Before
    public void setUp() {
        rentalStore = new RentalStore();
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // SetUp: Create a customer with customer ID: C001 and name: "John Doe"
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        rentalStore.getCustomers().add(customer);
        
        // SetUp: Create a rental with rental ID: R001 for customer ID: C001, with backDate: null, and dueDate: "2023-10-01"
        Rental rental = new Rental();
        rental.setCustomerName("John");
        rental.setCustomerSurname("Doe");
        rental.setCarPlate("CAR001");
        rental.setDueDate(LocalDate.of(2023, 10, 1));
        rental.setBackDate(null);
        rentalStore.getRentals().add(rental);
        
        // Set the current date to "2023-10-05" (overdue rental) - Note: The method uses LocalDate.now() internally
        // We'll use reflection to set a fixed date, but for this test we'll rely on the logic that current date is after due date
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Verify: Customer ID C001 is overdue for rental R001
        assertEquals(1, overdueCustomers.size());
        assertEquals("John", overdueCustomers.get(0).getName());
        assertEquals("Doe", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // SetUp: Create a customer with customer ID: C002 and name: "Jane Smith"
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        rentalStore.getCustomers().add(customer);
        
        // SetUp: Create a rental with rental ID: R002 for customer ID: C002, with backDate: null, and dueDate: "2025-10-10"
        Rental rental = new Rental();
        rental.setCustomerName("Jane");
        rental.setCustomerSurname("Smith");
        rental.setCarPlate("CAR002");
        rental.setDueDate(LocalDate.of(2025, 10, 10));
        rental.setBackDate(null);
        rentalStore.getRentals().add(rental);
        
        // Set the current date to "2023-10-12" - Note: The method uses LocalDate.now() internally
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Verify: No overdue rentals found for customer ID C002
        assertEquals(0, overdueCustomers.size());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // SetUp: Create a customer with customer ID: C003 and name: "Alice Johnson"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        rentalStore.getCustomers().add(customer);
        
        // SetUp: Create a rental with rental ID: R003 for customer ID: C003, with backDate: null, and dueDate: "2023-10-03"
        Rental rental1 = new Rental();
        rental1.setCustomerName("Alice");
        rental1.setCustomerSurname("Johnson");
        rental1.setCarPlate("CAR003");
        rental1.setDueDate(LocalDate.of(2023, 10, 3));
        rental1.setBackDate(null);
        rentalStore.getRentals().add(rental1);
        
        // SetUp: Create another rental with rental ID: R004 for customer ID: C003, with backDate: "2024-10-01", and dueDate: "2024-10-02"
        Rental rental2 = new Rental();
        rental2.setCustomerName("Alice");
        rental2.setCustomerSurname("Johnson");
        rental2.setCarPlate("CAR004");
        rental2.setDueDate(LocalDate.of(2024, 10, 2));
        rental2.setBackDate(LocalDate.of(2024, 10, 1));
        rentalStore.getRentals().add(rental2);
        
        // Set the current date to "2023-10-05" - Note: The method uses LocalDate.now() internally
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Verify: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue
        assertEquals(1, overdueCustomers.size());
        assertEquals("Alice", overdueCustomers.get(0).getName());
        assertEquals("Johnson", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // SetUp: Create a customer with customer ID: C004 and name: "Bob Brown"
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        rentalStore.getCustomers().add(customer);
        
        // SetUp: Create a rental with rental ID: R005 for customer ID: C004, with backDate: "2023-10-04", and dueDate: "2023-10-03"
        Rental rental = new Rental();
        rental.setCustomerName("Bob");
        rental.setCustomerSurname("Brown");
        rental.setCarPlate("CAR005");
        rental.setDueDate(LocalDate.of(2023, 10, 3));
        rental.setBackDate(LocalDate.of(2023, 10, 4));
        rentalStore.getRentals().add(rental);
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Verify: No overdue rentals found for customer ID C004, as rental R005 was returned on time
        assertEquals(0, overdueCustomers.size());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // SetUp: Create a customer with customer ID: C005 and name: "Charlie Green"
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        rentalStore.getCustomers().add(customer);
        
        // SetUp: Create a rental with rental ID: R006 for customer ID: C005, with dueDate: "2025-10-15"
        Rental rental = new Rental();
        rental.setCustomerName("Charlie");
        rental.setCustomerSurname("Green");
        rental.setCarPlate("CAR006");
        rental.setDueDate(LocalDate.of(2025, 10, 15));
        rental.setBackDate(null);
        rentalStore.getRentals().add(rental);
        
        // Set the current date to "2023-10-05" - Note: The method uses LocalDate.now() internally
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Verify: No overdue rentals found for customer ID C005, as the due date is in the future
        assertEquals(0, overdueCustomers.size());
    }
}