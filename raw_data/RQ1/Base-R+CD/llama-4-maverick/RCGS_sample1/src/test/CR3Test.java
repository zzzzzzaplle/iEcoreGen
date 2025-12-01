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
        // Test Case 1: Single Overdue Rental Check
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("CAR001");
        
        // Create rental R001 with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        rental.setBackDate(null); // Not returned yet
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (past due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C001 is in the overdue list
        assertTrue("Customer C001 should be in overdue list", overdueCustomers.contains(customer));
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Test Case 2: No Overdue Rentals
        // Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("CAR002");
        
        // Create rental R002 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        rental.setBackDate(null); // Not returned yet, but due date is in future
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-12 (before due date)
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are overdue
        assertTrue("Overdue list should be empty", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Test Case 3: Multiple Rentals with Mixed Status
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // Create cars for rentals
        Car car1 = new Car();
        car1.setPlate("CAR003A");
        Car car2 = new Car();
        car2.setPlate("CAR003B");
        
        // Create rental R003 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        rental1.setBackDate(null); // Not returned and overdue
        
        // Create rental R004 (returned on time)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Returned before due date
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that customer C003 is in the overdue list due to rental R003
        assertTrue("Customer C003 should be in overdue list due to rental R003", 
                  overdueCustomers.contains(customer));
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Test Case 4: Rental with Back Date
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("CAR004");
        
        // Create rental R005 (returned after due date but has back date set)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Returned after due date
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to any date (back date is set, so rental is not considered overdue)
        Date currentDate = dateFormat.parse("2023-10-10");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are overdue (back date is set)
        assertTrue("Overdue list should be empty as rental has back date set", 
                  overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Test Case 5: Future Due Date Rentals
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("CAR005");
        
        // Create rental R006 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        rental.setBackDate(null); // Not returned yet, but due date is in future
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (before due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify that no customers are overdue (due date is in future)
        assertTrue("Overdue list should be empty as due date is in future", 
                  overdueCustomers.isEmpty());
    }
}