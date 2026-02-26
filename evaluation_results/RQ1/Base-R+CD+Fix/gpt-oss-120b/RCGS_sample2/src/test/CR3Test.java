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
    public void testCase1_SingleOverdueRentalCheck() throws ParseException {
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
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        
        // Add rental to store
        store.addRental(rental);
        
        // Set current date to 2023-10-05 (after due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C001 is in overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be John Doe", "John Doe", overdueCustomers.get(0).getName());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws ParseException {
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
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        
        // Add rental to store
        store.addRental(rental);
        
        // Set current date to 2023-10-12 (before due date)
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws ParseException {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // Create cars for rentals
        Car car1 = new Car();
        car1.setPlate("CAR003");
        Car car2 = new Car();
        car2.setPlate("CAR004");
        
        // Create rental R003 with overdue status
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Create rental R004 with back date (already returned)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(dateFormat.parse("2024-10-01"));
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Set current date to 2023-10-05 (after R003's due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C003 is in overdue list (due to R003)
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be Alice Johnson", "Alice Johnson", overdueCustomers.get(0).getName());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws ParseException {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("CAR005");
        
        // Create rental R005 with back date (already returned)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(dateFormat.parse("2023-10-04"));
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Add rental to store
        store.addRental(rental);
        
        // Set current date to any date (backDate is set, so rental is not overdue)
        Date currentDate = dateFormat.parse("2023-10-10");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found since rental was returned
        assertTrue("Should not find any overdue customers when backDate is set", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws ParseException {
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
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        
        // Add rental to store
        store.addRental(rental);
        
        // Set current date to 2023-10-05 (before due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found since due date is in future
        assertTrue("Should not find any overdue customers when due date is in future", overdueCustomers.isEmpty());
    }
}