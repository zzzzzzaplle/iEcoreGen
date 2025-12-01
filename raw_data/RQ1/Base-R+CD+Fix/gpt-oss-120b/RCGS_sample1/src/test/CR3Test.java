import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        // Create customer C001
        Customer customer = new Customer("John", "Doe", "123 Main St", null);
        
        // Create car
        Car car = new Car("CAR001", "Toyota", 50.0);
        
        // Create rental with null backDate and past due date
        Date dueDate = dateFormat.parse("2023-10-01 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        Rental rental = new Rental(dateFormat.parse("2023-09-20 00:00:00"), 
                             dueDate, 
                             null, 
                             500.0, 
                             "Standard", 
                             car, 
                             customer);
        
        // Add to store
        store.addCar(car);
        store.addRental(rental);
        
        // Check for overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C001 is in the overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be John Doe", "John", overdueCustomers.get(0).getName());
        assertEquals("Customer surname should be Doe", "Doe", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = new Customer("Jane", "Smith", "456 Oak St", null);
        
        // Create car
        Car car = new Car("CAR002", "Honda", 60.0);
        
        // Create rental with future due date
        Date dueDate = dateFormat.parse("2025-10-10 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        Rental rental = new Rental(dateFormat.parse("2023-10-01 00:00:00"), 
                             dueDate, 
                             null, 
                             600.0, 
                             "Standard", 
                             car, 
                             customer);
        
        // Add to store
        store.addCar(car);
        store.addRental(rental);
        
        // Check for overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertEquals("Should find no overdue customers", 0, overdueCustomers.size());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = new Customer("Alice", "Johnson", "789 Pine St", null);
        
        // Create cars
        Car car1 = new Car("CAR003", "BMW", 100.0);
        Car car2 = new Car("CAR004", "Mercedes", 120.0);
        
        // Create first rental (overdue - backDate null, past due date)
        Date overdueDueDate = dateFormat.parse("2023-10-03 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        Rental rental1 = new Rental(dateFormat.parse("2023-09-25 00:00:00"), 
                              overdueDueDate, 
                              null, 
                              800.0, 
                              "Standard", 
                              car1, 
                              customer);
        
        // Create second rental (not overdue - backDate set)
        Date futureDueDate = dateFormat.parse("2024-10-02 00:00:00");
        Rental rental2 = new Rental(dateFormat.parse("2024-09-28 00:00:00"), 
                              futureDueDate, 
                              dateFormat.parse("2024-10-01 00:00:00"), 
                              900.0, 
                              "Premium", 
                              car2, 
                              customer);
        
        // Add to store
        store.addCar(car1);
        store.addCar(car2);
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Check for overdue customers
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C003 is in the overdue list due to rental1
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be Alice Johnson", "Alice", overdueCustomers.get(0).getName());
        assertEquals("Customer surname should be Johnson", "Johnson", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer("Bob", "Brown", "321 Elm St", null);
        
        // Create car
        Car car = new Car("CAR005", "Ford", 45.0);
        
        // Create rental with back date set (returned on time)
        Date dueDate = dateFormat.parse("2023-10-03 00:00:00");
        Rental rental = new Rental(dateFormat.parse("2023-09-28 00:00:00"), 
                             dueDate, 
                             dateFormat.parse("2023-10-04 00:00:00"), 
                             450.0, 
                             "Standard", 
                             car, 
                             customer);
        
        // Add to store
        store.addCar(car);
        store.addRental(rental);
        
        // Check for overdue customers with current date after due date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found since car was returned
        assertEquals("Should find no overdue customers when back date is set", 0, overdueCustomers.size());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer("Charlie", "Green", "654 Maple St", null);
        
        // Create car
        Car car = new Car("CAR006", "Nissan", 55.0);
        
        // Create rental with future due date
        Date dueDate = dateFormat.parse("2025-10-15 00:00:00");
        Rental rental = new Rental(dateFormat.parse("2023-10-01 00:00:00"), 
                             dueDate, 
                             null, 
                             550.0, 
                             "Standard", 
                             car, 
                             customer);
        
        // Add to store
        store.addCar(car);
        store.addRental(rental);
        
        // Check for overdue customers with current date well before due date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found since due date is in future
        assertEquals("Should find no overdue customers for future due dates", 0, overdueCustomers.size());
    }
}