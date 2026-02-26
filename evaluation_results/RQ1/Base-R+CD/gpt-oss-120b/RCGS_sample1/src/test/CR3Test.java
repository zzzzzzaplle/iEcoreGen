import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void testCase1_SingleOverdueRentalCheck() throws Exception {
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        
        // Create car
        Car car = new Car();
        car.setPlate("ABC123");
        
        // Create rental with backDate: null and dueDate: 2023-10-01
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (overdue)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C001 is in the overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be in overdue list", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        customer.setAddress("456 Oak St");
        
        // Create car
        Car car = new Car();
        car.setPlate("DEF456");
        
        // Create rental with backDate: null and dueDate: 2025-10-10 (future date)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-12 (before due date)
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("GHI789");
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        
        // Create rental R003 with backDate: null and dueDate: 2023-10-03 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Create rental R004 with backDate: 2024-10-01 and dueDate: 2024-10-02 (returned)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(dateFormat.parse("2024-10-01"));
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C003 is in the overdue list (only for rental R003)
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be in overdue list for rental R003", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        
        // Create car
        Car car = new Car();
        car.setPlate("MNO345");
        
        // Create rental with backDate: 2023-10-04 and dueDate: 2023-10-03 (returned after due date)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(dateFormat.parse("2023-10-04"));
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (rental has back date)
        assertTrue("Should not find any overdue customers when rental has back date", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        customer.setAddress("654 Maple St");
        
        // Create car
        Car car = new Car();
        car.setPlate("PQR678");
        
        // Create rental with dueDate: 2025-10-15 (future date)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (before due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (due date is in future)
        assertTrue("Should not find any overdue customers when due date is in future", overdueCustomers.isEmpty());
    }
}