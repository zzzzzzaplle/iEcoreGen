import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.util.List;

public class CR3Test {
    
    private CarRentalSystem carRentalSystem;
    
    @Before
    public void setUp() {
        carRentalSystem = new CarRentalSystem();
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() throws ParseException {
        // Setup customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("Test Address");
        carRentalSystem.addCustomer(customer);
        
        // Setup car for rental
        Car car = new Car();
        car.setPlate("ABC123");
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        carRentalSystem.addCar(car);
        
        // Setup rental R001 with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setStartDate("2023-09-01");
        rental.setDueDate("2023-10-01");  // Due date in past
        rental.setBackDate(null);  // Not returned yet
        rental.setTotalCost(1500.0);
        carRentalSystem.addRental(rental);
        
        // Check for overdue customers with current date after due date
        List<Customer> overdueCustomers = carRentalSystem.getOverdueCustomers("2023-10-05");
        
        // Verify customer C001 is in overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be overdue", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws ParseException {
        // Setup customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        customer.setAddress("Test Address 2");
        carRentalSystem.addCustomer(customer);
        
        // Setup car for rental
        Car car = new Car();
        car.setPlate("DEF456");
        car.setModel("Honda Civic");
        car.setDailyPrice(40.0);
        carRentalSystem.addCar(car);
        
        // Setup rental R002 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setStartDate("2023-10-01");
        rental.setDueDate("2025-10-10");  // Due date in future
        rental.setBackDate(null);  // Not returned yet
        rental.setTotalCost(2000.0);
        carRentalSystem.addRental(rental);
        
        // Check for overdue customers with current date before due date
        List<Customer> overdueCustomers = carRentalSystem.getOverdueCustomers("2023-10-12");
        
        // Verify no overdue customers found
        assertTrue("Should return empty list when no overdue rentals", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws ParseException {
        // Setup customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setAddress("Test Address 3");
        carRentalSystem.addCustomer(customer);
        
        // Setup cars for rentals
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Ford Focus");
        car1.setDailyPrice(35.0);
        carRentalSystem.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Nissan Altima");
        car2.setDailyPrice(45.0);
        carRentalSystem.addCar(car2);
        
        // Setup rental R003 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setStartDate("2023-09-28");
        rental1.setDueDate("2023-10-03");  // Due date in past
        rental1.setBackDate(null);  // Not returned yet
        rental1.setTotalCost(175.0);
        carRentalSystem.addRental(rental1);
        
        // Setup rental R004 (returned, not overdue)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setStartDate("2024-09-28");
        rental2.setDueDate("2024-10-02");
        rental2.setBackDate("2024-10-01");  // Returned before due date
        rental2.setTotalCost(180.0);
        carRentalSystem.addRental(rental2);
        
        // Check for overdue customers with current date after first rental's due date
        List<Customer> overdueCustomers = carRentalSystem.getOverdueCustomers("2023-10-05");
        
        // Verify customer C003 is in overdue list (due to rental R003)
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be overdue due to rental R003", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws ParseException {
        // Setup customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        customer.setAddress("Test Address 4");
        carRentalSystem.addCustomer(customer);
        
        // Setup car for rental
        Car car = new Car();
        car.setPlate("MNO345");
        car.setModel("Chevrolet Malibu");
        car.setDailyPrice(55.0);
        carRentalSystem.addCar(car);
        
        // Setup rental R005 that was returned (has back date)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setStartDate("2023-10-01");
        rental.setDueDate("2023-10-03");
        rental.setBackDate("2023-10-04");  // Returned (has back date)
        rental.setTotalCost(165.0);
        carRentalSystem.addRental(rental);
        
        // Check for overdue customers
        List<Customer> overdueCustomers = carRentalSystem.getOverdueCustomers("2023-10-05");
        
        // Verify no overdue customers found since rental has back date
        assertTrue("Should return empty list when rental has back date", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws ParseException {
        // Setup customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        customer.setAddress("Test Address 5");
        carRentalSystem.addCustomer(customer);
        
        // Setup car for rental
        Car car = new Car();
        car.setPlate("PQR678");
        car.setModel("Hyundai Elantra");
        car.setDailyPrice(38.0);
        carRentalSystem.addCar(car);
        
        // Setup rental R006 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setStartDate("2023-10-01");
        rental.setDueDate("2025-10-15");  // Due date far in future
        rental.setBackDate(null);  // Not returned yet
        rental.setTotalCost(2500.0);
        carRentalSystem.addRental(rental);
        
        // Check for overdue customers with current date before due date
        List<Customer> overdueCustomers = carRentalSystem.getOverdueCustomers("2023-10-05");
        
        // Verify no overdue customers found since due date is in future
        assertTrue("Should return empty list when due date is in future", overdueCustomers.isEmpty());
    }
}