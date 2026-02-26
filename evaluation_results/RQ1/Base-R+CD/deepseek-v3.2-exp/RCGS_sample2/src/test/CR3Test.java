import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void testCase1_SingleOverdueRentalCheck() throws Exception {
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // Create car
        Car car = new Car();
        car.setPlate("ABC123");
        
        // Create rental R001 with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        
        // Set up store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (overdue)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // Create car
        Car car = new Car();
        car.setPlate("DEF456");
        
        // Create rental R002 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        
        // Set up store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-12 (before due date)
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue rentals found
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("GHI789");
        Car car2 = new Car();
        car2.setPlate("JKL012");
        
        // Create rental R003 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Create rental R004 (returned, not overdue)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(dateFormat.parse("2024-10-01"));
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        
        // Set up store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify only customer C003 is overdue for rental R003
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // Create car
        Car car = new Car();
        car.setPlate("MNO345");
        
        // Create rental R005 (returned on time)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(dateFormat.parse("2023-10-04"));
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Set up store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date (any date since car is returned)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue rentals found since car was returned
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // Create car
        Car car = new Car();
        car.setPlate("PQR678");
        
        // Create rental R006 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        
        // Set up store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue rentals found since due date is in future
        assertTrue(overdueCustomers.isEmpty());
    }
}