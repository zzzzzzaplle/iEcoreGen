import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR3Test {
    
    private CarRentalSystem rentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalSystem = new CarRentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // Set up test data
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        Car car = new Car();
        car.setPlate("PLATE001");
        car.setModel("ModelX");
        car.setDailyPrice(50.0);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.parse("2023-09-20", formatter));
        rental.setDueDate(LocalDate.parse("2023-10-01", formatter));
        rental.setBackDate(null); // Not returned yet
        
        // Add to system
        rentalSystem.addCustomer(customer);
        rentalSystem.addCar(car);
        rentalSystem.addRental(rental);
        
        // Set mock current date to 2023-10-05 (overdue)
        // Note: In real implementation, we would mock LocalDate.now()
        // For this test, we'll rely on the actual implementation
        
        // Get overdue customers
        List<Customer> overdueCustomers = rentalSystem.getOverdueCustomers();
        
        // Verify result
        assertEquals("Should have exactly one overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be John Doe", "John Doe", overdueCustomers.get(0).toString());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // Set up test data
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        Car car = new Car();
        car.setPlate("PLATE002");
        car.setModel("ModelY");
        car.setDailyPrice(60.0);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.parse("2023-10-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-10-10", formatter)); // Future due date
        rental.setBackDate(null); // Not returned yet
        
        // Add to system
        rentalSystem.addCustomer(customer);
        rentalSystem.addCar(car);
        rentalSystem.addRental(rental);
        
        // Get overdue customers
        List<Customer> overdueCustomers = rentalSystem.getOverdueCustomers();
        
        // Verify result - should be empty since due date is in future
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // Set up test data
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        Car car1 = new Car();
        car1.setPlate("PLATE003");
        car1.setModel("ModelA");
        car1.setDailyPrice(40.0);
        
        Car car2 = new Car();
        car2.setPlate("PLATE004");
        car2.setModel("ModelB");
        car2.setDailyPrice(70.0);
        
        // First rental - overdue
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.parse("2023-09-20", formatter));
        rental1.setDueDate(LocalDate.parse("2023-10-03", formatter)); // Past due
        rental1.setBackDate(null); // Not returned yet
        
        // Second rental - not overdue (already returned)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.parse("2024-09-20", formatter));
        rental2.setDueDate(LocalDate.parse("2024-10-02", formatter));
        rental2.setBackDate(LocalDate.parse("2024-10-01", formatter)); // Returned early
        
        // Add to system
        rentalSystem.addCustomer(customer);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addRental(rental1);
        rentalSystem.addRental(rental2);
        
        // Get overdue customers
        List<Customer> overdueCustomers = rentalSystem.getOverdueCustomers();
        
        // Verify result - should contain Alice Johnson due to rental1 being overdue
        assertEquals("Should have exactly one overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be Alice Johnson", "Alice Johnson", overdueCustomers.get(0).toString());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // Set up test data
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        Car car = new Car();
        car.setPlate("PLATE005");
        car.setModel("ModelC");
        car.setDailyPrice(55.0);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.parse("2023-09-20", formatter));
        rental.setDueDate(LocalDate.parse("2023-10-03", formatter));
        rental.setBackDate(LocalDate.parse("2023-10-04", formatter)); // Returned (late but returned)
        
        // Add to system
        rentalSystem.addCustomer(customer);
        rentalSystem.addCar(car);
        rentalSystem.addRental(rental);
        
        // Get overdue customers
        List<Customer> overdueCustomers = rentalSystem.getOverdueCustomers();
        
        // Verify result - should be empty since car was returned (even though it was late)
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // Set up test data
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        Car car = new Car();
        car.setPlate("PLATE006");
        car.setModel("ModelD");
        car.setDailyPrice(45.0);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.parse("2023-10-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-10-15", formatter)); // Far future due date
        rental.setBackDate(null); // Not returned yet
        
        // Add to system
        rentalSystem.addCustomer(customer);
        rentalSystem.addCar(car);
        rentalSystem.addRental(rental);
        
        // Get overdue customers
        List<Customer> overdueCustomers = rentalSystem.getOverdueCustomers();
        
        // Verify result - should be empty since due date is in future
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }
}