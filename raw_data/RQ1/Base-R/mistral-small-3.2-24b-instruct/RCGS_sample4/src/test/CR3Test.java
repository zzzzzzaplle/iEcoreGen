import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR3Test {
    
    private CarGallery carGallery;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        carGallery = new CarGallery();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // SetUp
        Customer customer = new Customer("John", "Doe", "123 Main St");
        Car car = new Car("ABC123", "Toyota Camry", 50.0);
        carGallery.addCar(car);
        
        Rental rental = new Rental(car, customer, 
            LocalDate.parse("2023-09-20", formatter),
            LocalDate.parse("2023-10-01", formatter));
        rental.setBackDate(null);
        carGallery.addRental(rental);
        
        // Set current date to 2023-10-05 (overdue rental)
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Execute
        List<Customer> overdueCustomers = carGallery.listOverdueCustomers();
        
        // Verify
        assertEquals("Should have 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be in overdue list", 
            overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // SetUp
        Customer customer = new Customer("Jane", "Smith", "456 Oak St");
        Car car = new Car("DEF456", "Honda Civic", 40.0);
        carGallery.addCar(car);
        
        Rental rental = new Rental(car, customer,
            LocalDate.parse("2023-10-01", formatter),
            LocalDate.parse("2025-10-10", formatter));
        rental.setBackDate(null);
        carGallery.addRental(rental);
        
        // Set current date to 2023-10-12
        LocalDate currentDate = LocalDate.parse("2023-10-12", formatter);
        
        // Execute
        List<Customer> overdueCustomers = carGallery.listOverdueCustomers();
        
        // Verify
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // SetUp
        Customer customer = new Customer("Alice", "Johnson", "789 Pine St");
        
        Car car1 = new Car("GHI789", "Ford Focus", 45.0);
        Car car2 = new Car("JKL012", "Nissan Altima", 55.0);
        carGallery.addCar(car1);
        carGallery.addCar(car2);
        
        // Overdue rental (R003)
        Rental rental1 = new Rental(car1, customer,
            LocalDate.parse("2023-09-20", formatter),
            LocalDate.parse("2023-10-03", formatter));
        rental1.setBackDate(null);
        carGallery.addRental(rental1);
        
        // Not overdue rental (R004) - already returned
        Rental rental2 = new Rental(car2, customer,
            LocalDate.parse("2024-09-28", formatter),
            LocalDate.parse("2024-10-02", formatter));
        rental2.setBackDate(LocalDate.parse("2024-10-01", formatter));
        carGallery.addRental(rental2);
        
        // Set current date to 2023-10-05
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Execute
        List<Customer> overdueCustomers = carGallery.listOverdueCustomers();
        
        // Verify
        assertEquals("Should have 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be in overdue list", 
            overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // SetUp
        Customer customer = new Customer("Bob", "Brown", "321 Elm St");
        Car car = new Car("MNO345", "Chevrolet Malibu", 60.0);
        carGallery.addCar(car);
        
        Rental rental = new Rental(car, customer,
            LocalDate.parse("2023-09-28", formatter),
            LocalDate.parse("2023-10-03", formatter));
        rental.setBackDate(LocalDate.parse("2023-10-04", formatter));
        carGallery.addRental(rental);
        
        // Execute
        List<Customer> overdueCustomers = carGallery.listOverdueCustomers();
        
        // Verify
        assertTrue("Should have no overdue customers when rental has back date", 
            overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // SetUp
        Customer customer = new Customer("Charlie", "Green", "654 Maple St");
        Car car = new Car("PQR678", "Hyundai Sonata", 35.0);
        carGallery.addCar(car);
        
        Rental rental = new Rental(car, customer,
            LocalDate.parse("2023-10-01", formatter),
            LocalDate.parse("2025-10-15", formatter));
        rental.setBackDate(null);
        carGallery.addRental(rental);
        
        // Set current date to 2023-10-05
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Execute
        List<Customer> overdueCustomers = carGallery.listOverdueCustomers();
        
        // Verify
        assertTrue("Should have no overdue customers when due date is in future", 
            overdueCustomers.isEmpty());
    }
}