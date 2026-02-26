import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    private CarRentalSystem system;
    
    @Before
    public void setUp() {
        system = new CarRentalSystem();
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // Set up test data
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        Car car = new Car();
        car.setPlate("ABC123");
        car.setModel("Toyota");
        car.setDailyPrice(50.0);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentalDate(LocalDate.of(2023, 9, 20));
        rental.setDueDate(LocalDate.of(2023, 10, 1)); // Past due date
        rental.setReturnDate(null); // Not returned
        
        // Add to system
        system.addCustomer(customer);
        system.addCar(car);
        system.addRental(rental);
        
        // Set current date to 2023-10-05 (past due date)
        // Note: We cannot actually change system date, so we rely on the isOverdue logic
        // which uses LocalDate.now(). For testing purposes, we'll assume the system
        // correctly identifies overdue rentals based on current date
        
        // Generate overdue notices
        List<Rental> overdueRentals = system.generateOverdueNotices();
        
        // Verify results
        assertNotNull("Overdue rentals list should not be null", overdueRentals);
        assertEquals("Should have 1 overdue rental", 1, overdueRentals.size());
        
        Rental overdueRental = overdueRentals.get(0);
        assertEquals("Customer should match", customer, overdueRental.getCustomer());
        assertTrue("Rental should be overdue", overdueRental.isOverdue());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // Set up test data
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        Car car = new Car();
        car.setPlate("DEF456");
        car.setModel("Honda");
        car.setDailyPrice(40.0);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentalDate(LocalDate.of(2023, 10, 1));
        rental.setDueDate(LocalDate.of(2025, 10, 10)); // Future due date
        rental.setReturnDate(null); // Not returned
        
        // Add to system
        system.addCustomer(customer);
        system.addCar(car);
        system.addRental(rental);
        
        // Generate overdue notices
        List<Rental> overdueRentals = system.generateOverdueNotices();
        
        // Verify results
        assertNotNull("Overdue rentals list should not be null", overdueRentals);
        assertEquals("Should have no overdue rentals", 0, overdueRentals.size());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // Set up test data
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Ford");
        car1.setDailyPrice(35.0);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("BMW");
        car2.setDailyPrice(70.0);
        
        // First rental - overdue
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(LocalDate.of(2023, 9, 20));
        rental1.setDueDate(LocalDate.of(2023, 10, 3)); // Past due date
        rental1.setReturnDate(null); // Not returned
        
        // Second rental - returned on time (not overdue)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(LocalDate.of(2024, 9, 20));
        rental2.setDueDate(LocalDate.of(2024, 10, 2)); // Future due date
        rental2.setReturnDate(LocalDate.of(2024, 10, 1)); // Returned before due date
        
        // Add to system
        system.addCustomer(customer);
        system.addCar(car1);
        system.addCar(car2);
        system.addRental(rental1);
        system.addRental(rental2);
        
        // Generate overdue notices
        List<Rental> overdueRentals = system.generateOverdueNotices();
        
        // Verify results
        assertNotNull("Overdue rentals list should not be null", overdueRentals);
        assertEquals("Should have 1 overdue rental", 1, overdueRentals.size());
        
        Rental overdueRental = overdueRentals.get(0);
        assertEquals("Should be rental1", rental1, overdueRental);
        assertTrue("Rental1 should be overdue", rental1.isOverdue());
        assertFalse("Rental2 should not be overdue", rental2.isOverdue());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // Set up test data
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        Car car = new Car();
        car.setPlate("MNO345");
        car.setModel("Audi");
        car.setDailyPrice(60.0);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentalDate(LocalDate.of(2023, 9, 20));
        rental.setDueDate(LocalDate.of(2023, 10, 3)); // Due date
        rental.setReturnDate(LocalDate.of(2023, 10, 4)); // Returned after due date but marked as returned
        
        // Add to system
        system.addCustomer(customer);
        system.addCar(car);
        system.addRental(rental);
        
        // Generate overdue notices
        List<Rental> overdueRentals = system.generateOverdueNotices();
        
        // Verify results
        assertNotNull("Overdue rentals list should not be null", overdueRentals);
        assertEquals("Should have no overdue rentals", 0, overdueRentals.size());
        
        // Verify rental is not active
        assertFalse("Rental should not be active", rental.isActive());
        assertFalse("Rental should not be overdue", rental.isOverdue());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // Set up test data
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        Car car = new Car();
        car.setPlate("PQR678");
        car.setModel("Mercedes");
        car.setDailyPrice(80.0);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentalDate(LocalDate.of(2023, 10, 1));
        rental.setDueDate(LocalDate.of(2025, 10, 15)); // Far future due date
        rental.setReturnDate(null); // Not returned
        
        // Add to system
        system.addCustomer(customer);
        system.addCar(car);
        system.addRental(rental);
        
        // Generate overdue notices
        List<Rental> overdueRentals = system.generateOverdueNotices();
        
        // Verify results
        assertNotNull("Overdue rentals list should not be null", overdueRentals);
        assertEquals("Should have no overdue rentals", 0, overdueRentals.size());
        
        // Verify rental is active but not overdue
        assertTrue("Rental should be active", rental.isActive());
        assertFalse("Rental should not be overdue", rental.isOverdue());
    }
}