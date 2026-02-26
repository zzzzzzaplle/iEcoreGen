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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() throws Exception {
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
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2023-10-01 00:00:00")); // Due date in past
        
        // Add to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date to overdue date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify result
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be John Doe", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
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
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2025-10-10 00:00:00")); // Due date in future
        
        // Add to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date before due date
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify result - should be empty list
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // Create cars for rentals
        Car car1 = new Car();
        car1.setPlate("CAR003");
        Car car2 = new Car();
        car2.setPlate("CAR004");
        
        // Create rental R003 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(null); // Not returned yet
        rental1.setDueDate(dateFormat.parse("2023-10-03 00:00:00")); // Due date in past
        
        // Create rental R004 (returned, not overdue)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(dateFormat.parse("2024-10-01 00:00:00")); // Returned
        rental2.setDueDate(dateFormat.parse("2024-10-02 00:00:00")); // Due date in future
        
        // Add to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify result - should only include customer for overdue rental R003
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be Alice Johnson", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("CAR005");
        
        // Create rental R005 (returned before due date)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(dateFormat.parse("2023-10-04 00:00:00")); // Returned
        rental.setDueDate(dateFormat.parse("2023-10-03 00:00:00")); // Due date (note: this is actually overdue, but since it's returned, it shouldn't count)
        
        // Add to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date after due date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify result - should be empty since rental was returned
        assertTrue("Should not find any overdue customers when rental is returned", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("CAR006");
        
        // Create rental R006 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2025-10-15 00:00:00")); // Due date in future
        
        // Add to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date before due date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify result - should be empty since due date is in future
        assertTrue("Should not find any overdue customers when due date is in future", overdueCustomers.isEmpty());
    }
}