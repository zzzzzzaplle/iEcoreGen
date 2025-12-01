import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR3Test {
    
    private RentalStore rentalStore;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalStore = new RentalStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // SetUp: Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        
        // SetUp: Create car
        Car car = new Car();
        car.setPlate("ABC123");
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        
        // SetUp: Create rental with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.parse("2023-09-01", formatter));
        rental.setDueDate(LocalDate.parse("2023-10-01", formatter)); // Due date in past
        rental.setBackDate(null); // Not returned
        
        // Add to rental store
        rentalStore.getCustomers().add(customer);
        rentalStore.getCars().add(car);
        rentalStore.getRentals().add(rental);
        
        // Set current date to overdue scenario
        // Note: The Rental class uses LocalDate.now() in isOverdue()
        // For testing purposes, we'll assume the current date is set to "2023-10-05"
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Verify: Customer C001 should be in overdue list
        assertNotNull("Overdue customers list should not be null", overdueCustomers);
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be in overdue list", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        customer.setAddress("456 Oak St");
        
        // SetUp: Create car
        Car car = new Car();
        car.setPlate("DEF456");
        car.setModel("Honda Civic");
        car.setDailyPrice(40.0);
        
        // SetUp: Create rental with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.parse("2023-10-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-10-10", formatter)); // Future due date
        rental.setBackDate(null); // Not returned but not overdue
        
        // Add to rental store
        rentalStore.getCustomers().add(customer);
        rentalStore.getCars().add(car);
        rentalStore.getRentals().add(rental);
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Verify: No overdue customers should be found
        assertNotNull("Overdue customers list should not be null", overdueCustomers);
        assertTrue("Overdue customers list should be empty", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        
        // SetUp: Create cars
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Ford Focus");
        car1.setDailyPrice(35.0);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Nissan Altima");
        car2.setDailyPrice(45.0);
        
        // SetUp: Create rental R003 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.parse("2023-09-15", formatter));
        rental1.setDueDate(LocalDate.parse("2023-10-03", formatter)); // Past due date
        rental1.setBackDate(null); // Not returned - overdue
        
        // SetUp: Create rental R004 (returned on time)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.parse("2024-09-28", formatter));
        rental2.setDueDate(LocalDate.parse("2024-10-02", formatter));
        rental2.setBackDate(LocalDate.parse("2024-10-01", formatter)); // Returned before due date
        
        // Add to rental store
        rentalStore.getCustomers().add(customer);
        rentalStore.getCars().add(car1);
        rentalStore.getCars().add(car2);
        rentalStore.getRentals().add(rental1);
        rentalStore.getRentals().add(rental2);
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Verify: Customer C003 should be in overdue list due to rental R003
        assertNotNull("Overdue customers list should not be null", overdueCustomers);
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be in overdue list", overdueCustomers.contains(customer));
        
        // Additional verification: Only rental1 should be overdue
        for (Rental rental : rentalStore.getRentals()) {
            if (rental.equals(rental1)) {
                assertTrue("Rental R003 should be overdue", rental.isOverdue());
            } else if (rental.equals(rental2)) {
                assertFalse("Rental R004 should not be overdue", rental.isOverdue());
            }
        }
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        
        // SetUp: Create car
        Car car = new Car();
        car.setPlate("MNO345");
        car.setModel("Chevrolet Malibu");
        car.setDailyPrice(55.0);
        
        // SetUp: Create rental with back date (returned)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.parse("2023-09-28", formatter));
        rental.setDueDate(LocalDate.parse("2023-10-03", formatter));
        rental.setBackDate(LocalDate.parse("2023-10-04", formatter)); // Returned (late but has back date)
        
        // Add to rental store
        rentalStore.getCustomers().add(customer);
        rentalStore.getCars().add(car);
        rentalStore.getRentals().add(rental);
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Verify: No overdue customers should be found (car was returned)
        assertNotNull("Overdue customers list should not be null", overdueCustomers);
        assertTrue("Overdue customers list should be empty", overdueCustomers.isEmpty());
        assertFalse("Rental should not be overdue since it has back date", rental.isOverdue());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        customer.setAddress("654 Maple St");
        
        // SetUp: Create car
        Car car = new Car();
        car.setPlate("PQR678");
        car.setModel("BMW 3 Series");
        car.setDailyPrice(80.0);
        
        // SetUp: Create rental with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.parse("2023-10-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-10-15", formatter)); // Far future due date
        rental.setBackDate(null); // Not returned but not overdue
        
        // Add to rental store
        rentalStore.getCustomers().add(customer);
        rentalStore.getCars().add(car);
        rentalStore.getRentals().add(rental);
        
        // Execute: Get overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Verify: No overdue customers should be found
        assertNotNull("Overdue customers list should not be null", overdueCustomers);
        assertTrue("Overdue customers list should be empty", overdueCustomers.isEmpty());
        assertFalse("Rental should not be overdue with future due date", rental.isOverdue());
    }
}