import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR3Test {
    
    private CarRentalStore carRentalStore;
    
    @Before
    public void setUp() {
        carRentalStore = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // SetUp: Create a customer with customer ID: C001 and name: "John Doe"
        Customer customer = new Customer("John", "Doe", "123 Main St", "CAR001", "2023-09-01", "2023-10-01");
        customer.setBackDate(null); // backDate is null
        carRentalStore.getCustomers().add(customer);
        
        // Create a car with plate CAR001 to associate with the rental
        Car car = new Car("CAR001", "Toyota Camry", 50.0);
        carRentalStore.getCars().add(car);
        
        // Set current date to "2023-10-05" (overdue rental)
        // Note: We'll use LocalDate.now() in the method, so we need to mock it
        // Since we can't easily mock LocalDate.now() in JUnit 4 without additional libraries,
        // we'll assume the method uses the real current date and adjust test dates accordingly
        // For this test, we'll set the due date to a past date relative to when the test runs
        
        // Get overdue customers
        List<Customer> overdueCustomers = carRentalStore.getOverdueCustomers();
        
        // Expected Output: Customer ID C001 is overdue for rental R001
        // Since Customer class doesn't have an ID field, we'll check by name and rental details
        assertEquals(1, overdueCustomers.size());
        assertEquals("John", overdueCustomers.get(0).getName());
        assertEquals("Doe", overdueCustomers.get(0).getSurname());
        assertEquals("2023-10-01", overdueCustomers.get(0).getLeaseDueDate());
        assertNull(overdueCustomers.get(0).getBackDate());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // SetUp: Create a customer with customer ID: C002 and name: "Jane Smith"
        Customer customer = new Customer("Jane", "Smith", "456 Oak St", "CAR002", "2023-10-01", "2025-10-10");
        customer.setBackDate(null); // backDate is null
        carRentalStore.getCustomers().add(customer);
        
        // Create a car with plate CAR002 to associate with the rental
        Car car = new Car("CAR002", "Honda Civic", 40.0);
        carRentalStore.getCars().add(car);
        
        // Set current date to "2023-10-12" - due date is in future, so not overdue
        // Get overdue customers
        List<Customer> overdueCustomers = carRentalStore.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C002
        assertEquals(0, overdueCustomers.size());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // SetUp: Create a customer with customer ID: C003 and name: "Alice Johnson"
        Customer customer = new Customer("Alice", "Johnson", "789 Pine St", "CAR003", "2023-09-28", "2023-10-03");
        customer.setBackDate(null); // backDate is null for first rental
        carRentalStore.getCustomers().add(customer);
        
        // Create a second customer for the second rental (since one customer can't have multiple rentals in current design)
        Customer customer2 = new Customer("Alice", "Johnson", "789 Pine St", "CAR004", "2024-09-30", "2024-10-02");
        customer2.setBackDate("2024-10-01"); // backDate is set for second rental
        carRentalStore.getCustomers().add(customer2);
        
        // Create cars for both rentals
        Car car1 = new Car("CAR003", "Ford Focus", 35.0);
        Car car2 = new Car("CAR004", "Nissan Altima", 45.0);
        carRentalStore.getCars().add(car1);
        carRentalStore.getCars().add(car2);
        
        // Set current date to "2023-10-05" - first rental is overdue, second rental is in future and already returned
        List<Customer> overdueCustomers = carRentalStore.getOverdueCustomers();
        
        // Expected Output: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue
        // We should only have one overdue customer (the first one)
        assertEquals(1, overdueCustomers.size());
        assertEquals("Alice", overdueCustomers.get(0).getName());
        assertEquals("Johnson", overdueCustomers.get(0).getSurname());
        assertEquals("CAR003", overdueCustomers.get(0).getRentedCarPlate());
        assertEquals("2023-10-03", overdueCustomers.get(0).getLeaseDueDate());
        assertNull(overdueCustomers.get(0).getBackDate());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // SetUp: Create a customer with customer ID: C004 and name: "Bob Brown"
        Customer customer = new Customer("Bob", "Brown", "101 Elm St", "CAR005", "2023-10-01", "2023-10-03");
        customer.setBackDate("2023-10-04"); // backDate is set (rental was returned)
        carRentalStore.getCustomers().add(customer);
        
        // Create a car with plate CAR005 to associate with the rental
        Car car = new Car("CAR005", "Chevrolet Malibu", 55.0);
        carRentalStore.getCars().add(car);
        
        // Get overdue customers
        List<Customer> overdueCustomers = carRentalStore.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C004, as rental R005 was returned
        assertEquals(0, overdueCustomers.size());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // SetUp: Create a customer with customer ID: C005 and name: "Charlie Green"
        Customer customer = new Customer("Charlie", "Green", "202 Maple St", "CAR006", "2023-10-01", "2025-10-15");
        customer.setBackDate(null); // backDate is null but due date is in future
        carRentalStore.getCustomers().add(customer);
        
        // Create a car with plate CAR006 to associate with the rental
        Car car = new Car("CAR006", "BMW X5", 100.0);
        carRentalStore.getCars().add(car);
        
        // Set current date to "2023-10-05" - due date is in future, so not overdue
        List<Customer> overdueCustomers = carRentalStore.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C005, as the due date is in the future
        assertEquals(0, overdueCustomers.size());
    }
}