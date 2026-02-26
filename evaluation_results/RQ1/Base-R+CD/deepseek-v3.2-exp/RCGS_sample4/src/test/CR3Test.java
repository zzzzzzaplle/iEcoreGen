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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        store.addCar(car);
        
        // Create rental with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-01 00:00:00"));
        rental.setBackDate(null); // Not returned yet
        store.addRental(rental);
        
        // Set current date to after due date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Test the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertTrue("Overdue customer should be John Doe", 
                   overdueCustomers.contains(customer));
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
        car.setModel("Honda Civic");
        car.setDailyPrice(40.0);
        store.addCar(car);
        
        // Create rental with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-10 00:00:00"));
        rental.setBackDate(null); // Not returned yet
        store.addRental(rental);
        
        // Set current date to before due date
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        
        // Test the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
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
        car1.setModel("Ford Focus");
        car1.setDailyPrice(35.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Nissan Altima");
        car2.setDailyPrice(45.0);
        store.addCar(car2);
        
        // Create first rental (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        rental1.setBackDate(null); // Not returned yet
        store.addRental(rental1);
        
        // Create second rental (not overdue - already returned)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setDueDate(dateFormat.parse("2024-10-02 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2024-10-01 00:00:00")); // Returned early
        store.addRental(rental2);
        
        // Set current date to after first rental's due date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Test the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertTrue("Overdue customer should be Alice Johnson", 
                   overdueCustomers.contains(customer));
        
        // Verify rental status
        assertTrue("Rental1 should be overdue", rental1.isOverdue(currentDate));
        assertFalse("Rental2 should not be overdue (already returned)", rental2.isOverdue(currentDate));
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
        car.setModel("Chevrolet Malibu");
        car.setDailyPrice(55.0);
        store.addCar(car);
        
        // Create rental that was returned (backDate set)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        rental.setBackDate(dateFormat.parse("2023-10-04 00:00:00")); // Returned (late)
        store.addRental(rental);
        
        // Set current date (any date since car is returned)
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Test the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertTrue("Should not find any overdue customers (car already returned)", 
                   overdueCustomers.isEmpty());
        
        // Verify rental is not active
        assertFalse("Rental should not be active", rental.isActive());
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
        car.setModel("Hyundai Elantra");
        car.setDailyPrice(42.0);
        store.addCar(car);
        
        // Create rental with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-15 00:00:00"));
        rental.setBackDate(null); // Not returned yet
        store.addRental(rental);
        
        // Set current date to before due date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Test the method
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertTrue("Should not find any overdue customers (due date in future)", 
                   overdueCustomers.isEmpty());
        
        // Verify rental status
        assertTrue("Rental should be active", rental.isActive());
        assertFalse("Rental should not be overdue", rental.isOverdue(currentDate));
    }
}