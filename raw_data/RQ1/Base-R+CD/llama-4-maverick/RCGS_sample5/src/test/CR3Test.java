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
        // SetUp: Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // SetUp: Create car and rental R001 for customer C001
        Car car = new Car();
        car.setPlate("CAR001");
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to "2023-10-05" (overdue)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer C001 should be in overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be overdue", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // SetUp: Create car and rental R002 for customer C002
        Car car = new Car();
        car.setPlate("CAR002");
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue customers should be found
        assertTrue("Should return empty list when no overdue rentals", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // SetUp: Create first rental R003 (overdue)
        Car car1 = new Car();
        car1.setPlate("CAR003");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        
        // SetUp: Create second rental R004 (returned, not overdue)
        Car car2 = new Car();
        car2.setPlate("CAR004");
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(dateFormat.parse("2024-10-01"));
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer C003 should be in overdue list due to rental R003
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be overdue for rental R003", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // SetUp: Create car and rental R005 with back date
        Car car = new Car();
        car.setPlate("CAR005");
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(dateFormat.parse("2023-10-04"));
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Execute: Find customers with overdue rentals (current date doesn't matter for returned rentals)
        Date currentDate = dateFormat.parse("2023-10-05");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue customers should be found since rental was returned
        assertTrue("Should return empty list for returned rental", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // SetUp: Create car and rental R006 with future due date
        Car car = new Car();
        car.setPlate("CAR006");
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue customers should be found since due date is in future
        assertTrue("Should return empty list for future due date", overdueCustomers.isEmpty());
    }
}