import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
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
    public void testCase1_SingleOverdueRentalCheck() throws ParseException {
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("ABC123");
        
        // Create rental R001 with backDate: null, dueDate: 2023-10-01
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        
        // Add rental to store
        store.setRentals(List.of(rental));
        
        // Set current date to 2023-10-05 (overdue)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C001 is overdue
        assertEquals(1, overdueCustomers.size());
        assertEquals("John Doe", overdueCustomers.get(0).getName());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws ParseException {
        // Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("DEF456");
        
        // Create rental R002 with backDate: null, dueDate: 2025-10-10
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        
        // Add rental to store
        store.setRentals(List.of(rental));
        
        // Set current date to 2023-10-12
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws ParseException {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // Create cars for rentals
        Car car1 = new Car();
        car1.setPlate("GHI789");
        Car car2 = new Car();
        car2.setPlate("JKL012");
        
        // Create rental R003 (overdue) with backDate: null, dueDate: 2023-10-03
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Create rental R004 (not overdue) with backDate: 2024-10-01, dueDate: 2024-10-02
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(dateFormat.parse("2024-10-01"));
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        
        // Add rentals to store
        store.setRentals(List.of(rental1, rental2));
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C003 is overdue for rental R003
        assertEquals(1, overdueCustomers.size());
        assertEquals("Alice Johnson", overdueCustomers.get(0).getName());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws ParseException {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("MNO345");
        
        // Create rental R005 with backDate: 2023-10-04, dueDate: 2023-10-03
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(dateFormat.parse("2023-10-04"));
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Add rental to store
        store.setRentals(List.of(rental));
        
        // Set current date to any date (method should ignore rentals with back date)
        Date currentDate = dateFormat.parse("2023-10-10");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found since rental has back date
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws ParseException {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("PQR678");
        
        // Create rental R006 with dueDate: 2025-10-15 (backDate is null by default)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        
        // Add rental to store
        store.setRentals(List.of(rental));
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found since due date is in future
        assertTrue(overdueCustomers.isEmpty());
    }
}