import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR3Test {
    
    private CarRentalStore store;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // SetUp
        Customer customer = new Customer("John Doe", "Doe", "123 Main St");
        Car car = new Car("PLATE001", "Toyota", 50.0);
        LocalDate rentDate = LocalDate.parse("2023-09-20", formatter);
        LocalDate dueDate = LocalDate.parse("2023-10-01", formatter);
        
        store.addCustomer(customer);
        store.addCar(car);
        store.rentCar(customer, car, rentDate, dueDate);
        
        // Set current date to "2023-10-05" (overdue rental)
        // Note: We'll use the actual system date, so we need to mock this by creating appropriate rentals
        
        // Create a rental with due date in the past and no back date
        Rental rental = store.getRentals().get(0);
        rental.setBackDate(null); // Ensure back date is null
        rental.setDueDate(LocalDate.parse("2023-10-01", formatter));
        
        // Test the method
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Expected Output: Customer C001 is overdue for rental R001
        // Since we can't mock LocalDate.now(), we'll rely on the rental setup
        // The rental is overdue if backDate is null and current date is after dueDate
        assertTrue("Customer should be in overdue list", overdueCustomers.contains(customer));
        assertEquals("Only one customer should be overdue", 1, overdueCustomers.size());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // SetUp
        Customer customer = new Customer("Jane Smith", "Smith", "456 Oak St");
        Car car = new Car("PLATE002", "Honda", 60.0);
        LocalDate rentDate = LocalDate.parse("2023-10-10", formatter);
        LocalDate dueDate = LocalDate.parse("2025-10-10", formatter); // Future due date
        
        store.addCustomer(customer);
        store.addCar(car);
        store.rentCar(customer, car, rentDate, dueDate);
        
        // Set current date to "2023-10-12"
        // The due date is in the future, so no overdue
        
        // Test the method
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C002
        assertTrue("No customers should be overdue", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // SetUp
        Customer customer = new Customer("Alice Johnson", "Johnson", "789 Pine St");
        Car car1 = new Car("PLATE003", "Ford", 70.0);
        Car car2 = new Car("PLATE004", "BMW", 100.0);
        
        // First rental: overdue (backDate null, dueDate in past)
        LocalDate rentDate1 = LocalDate.parse("2023-09-28", formatter);
        LocalDate dueDate1 = LocalDate.parse("2023-10-03", formatter);
        
        // Second rental: not overdue (backDate set)
        LocalDate rentDate2 = LocalDate.parse("2024-09-28", formatter);
        LocalDate dueDate2 = LocalDate.parse("2024-10-02", formatter);
        LocalDate backDate2 = LocalDate.parse("2024-10-01", formatter);
        
        store.addCustomer(customer);
        store.addCar(car1);
        store.addCar(car2);
        
        store.rentCar(customer, car1, rentDate1, dueDate1);
        store.rentCar(customer, car2, rentDate2, dueDate2);
        
        // Set back date for second rental
        Rental rental2 = store.getRentals().get(1);
        rental2.setBackDate(backDate2);
        
        // Set current date to "2023-10-05"
        // First rental is overdue, second is not
        
        // Test the method
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Expected Output: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue
        assertTrue("Customer should be in overdue list", overdueCustomers.contains(customer));
        assertEquals("Only one customer should be overdue", 1, overdueCustomers.size());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // SetUp
        Customer customer = new Customer("Bob Brown", "Brown", "321 Elm St");
        Car car = new Car("PLATE005", "Mercedes", 120.0);
        LocalDate rentDate = LocalDate.parse("2023-09-28", formatter);
        LocalDate dueDate = LocalDate.parse("2023-10-03", formatter);
        LocalDate backDate = LocalDate.parse("2023-10-04", formatter);
        
        store.addCustomer(customer);
        store.addCar(car);
        store.rentCar(customer, car, rentDate, dueDate);
        
        // Set back date (car returned)
        Rental rental = store.getRentals().get(0);
        rental.setBackDate(backDate);
        
        // Test the method
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C004, as rental R005 was returned on time
        assertTrue("No customers should be overdue when car is returned", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // SetUp
        Customer customer = new Customer("Charlie Green", "Green", "654 Maple St");
        Car car = new Car("PLATE006", "Audi", 90.0);
        LocalDate rentDate = LocalDate.parse("2023-10-05", formatter);
        LocalDate dueDate = LocalDate.parse("2025-10-15", formatter); // Far future due date
        
        store.addCustomer(customer);
        store.addCar(car);
        store.rentCar(customer, car, rentDate, dueDate);
        
        // Set current date to "2023-10-05" - same as rent date
        
        // Test the method
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C005, as the due date is in the future
        assertTrue("No customers should be overdue with future due date", overdueCustomers.isEmpty());
    }
}