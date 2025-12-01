import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR3Test {
    
    private RentalSystem rentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // Set up customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        rentalSystem.addCustomer(customer);
        
        // Set up car for rental
        Car car = new Car();
        car.setPlate("ABC123");
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        rentalSystem.addCar(car);
        
        // Set up rental R001 with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setStartDate(LocalDate.parse("2023-09-20", formatter));
        rental.setDueDate(LocalDate.parse("2023-10-01", formatter));
        rental.setBackDate(null); // Not returned yet
        rental.setTotalPrice(500.0);
        rentalSystem.addRental(rental);
        
        // Set current date to 2023-10-05 (overdue)
        // Note: In actual implementation, we'd need to mock LocalDate.now()
        // For this test, we rely on the isOverdue() method logic
        
        // Get customers with overdue rentals
        List<Customer> overdueCustomers = rentalSystem.getCustomersWithOverdueRentals();
        
        // Verify that customer C001 is in the overdue list
        assertFalse("Should have overdue customers", overdueCustomers.isEmpty());
        assertEquals("Should have exactly one overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be in overdue list", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // Set up customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        rentalSystem.addCustomer(customer);
        
        // Set up car for rental
        Car car = new Car();
        car.setPlate("DEF456");
        car.setModel("Honda Civic");
        car.setDailyPrice(40.0);
        rentalSystem.addCar(car);
        
        // Set up rental R002 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setStartDate(LocalDate.parse("2023-10-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-10-10", formatter)); // Far future date
        rental.setBackDate(null); // Not returned yet
        rental.setTotalPrice(600.0);
        rentalSystem.addRental(rental);
        
        // Set current date to 2023-10-12 (before due date)
        // Note: In actual implementation, we'd need to mock LocalDate.now()
        
        // Get customers with overdue rentals
        List<Customer> overdueCustomers = rentalSystem.getCustomersWithOverdueRentals();
        
        // Verify that no overdue customers found
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // Set up customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        rentalSystem.addCustomer(customer);
        
        // Set up cars for rentals
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Ford Focus");
        car1.setDailyPrice(45.0);
        rentalSystem.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Nissan Altima");
        car2.setDailyPrice(55.0);
        rentalSystem.addCar(car2);
        
        // Set up rental R003 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setStartDate(LocalDate.parse("2023-09-25", formatter));
        rental1.setDueDate(LocalDate.parse("2023-10-03", formatter)); // Past due date
        rental1.setBackDate(null); // Not returned yet
        rental1.setTotalPrice(450.0);
        rentalSystem.addRental(rental1);
        
        // Set up rental R004 (returned, not overdue)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setStartDate(LocalDate.parse("2024-09-28", formatter));
        rental2.setDueDate(LocalDate.parse("2024-10-02", formatter));
        rental2.setBackDate(LocalDate.parse("2024-10-01", formatter)); // Returned early
        rental2.setTotalPrice(550.0);
        rentalSystem.addRental(rental2);
        
        // Set current date to 2023-10-05 (R003 is overdue, R004 is not applicable)
        // Note: In actual implementation, we'd need to mock LocalDate.now()
        
        // Get customers with overdue rentals
        List<Customer> overdueCustomers = rentalSystem.getCustomersWithOverdueRentals();
        
        // Verify that customer C003 is in the overdue list due to R003
        assertFalse("Should have overdue customers", overdueCustomers.isEmpty());
        assertEquals("Should have exactly one overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be in overdue list due to R003", overdueCustomers.contains(customer));
        
        // Verify that R004 is not considered overdue (it has a back date)
        assertFalse("R004 should not be overdue", rental2.isOverdue());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // Set up customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        rentalSystem.addCustomer(customer);
        
        // Set up car for rental
        Car car = new Car();
        car.setPlate("MNO345");
        car.setModel("Chevrolet Malibu");
        car.setDailyPrice(60.0);
        rentalSystem.addCar(car);
        
        // Set up rental R005 with back date (returned)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setStartDate(LocalDate.parse("2023-09-29", formatter));
        rental.setDueDate(LocalDate.parse("2023-10-03", formatter));
        rental.setBackDate(LocalDate.parse("2023-10-04", formatter)); // Returned (late but returned)
        rental.setTotalPrice(300.0);
        rentalSystem.addRental(rental);
        
        // Get customers with overdue rentals
        List<Customer> overdueCustomers = rentalSystem.getCustomersWithOverdueRentals();
        
        // Verify that no overdue customers found (car was returned)
        assertTrue("Should have no overdue customers since car was returned", overdueCustomers.isEmpty());
        
        // Verify that rental is not active and not overdue
        assertFalse("Rental should not be active", rental.isActive());
        assertFalse("Rental should not be overdue", rental.isOverdue());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // Set up customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        rentalSystem.addCustomer(customer);
        
        // Set up car for rental
        Car car = new Car();
        car.setPlate("PQR678");
        car.setModel("Hyundai Elantra");
        car.setDailyPrice(35.0);
        rentalSystem.addCar(car);
        
        // Set up rental R006 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setStartDate(LocalDate.parse("2023-10-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-10-15", formatter)); // Far future date
        rental.setBackDate(null); // Not returned yet
        rental.setTotalPrice(700.0);
        rentalSystem.addRental(rental);
        
        // Set current date to 2023-10-05 (well before due date)
        // Note: In actual implementation, we'd need to mock LocalDate.now()
        
        // Get customers with overdue rentals
        List<Customer> overdueCustomers = rentalSystem.getCustomersWithOverdueRentals();
        
        // Verify that no overdue customers found
        assertTrue("Should have no overdue customers with future due date", overdueCustomers.isEmpty());
        
        // Verify that rental is not overdue
        assertFalse("Rental should not be overdue with future due date", rental.isOverdue());
    }
}