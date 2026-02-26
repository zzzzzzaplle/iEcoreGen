import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR3Test {
    private CarStore carStore;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        carStore = new CarStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // Create customer C001
        Customer customer = new Customer("John", "Doe", "123 Main St");
        
        // Create car for rental
        Car car = new Car("ABC123", "Toyota Camry", 50.0);
        carStore.addCar(car);
        
        // Create rental with overdue due date
        LocalDate rentDate = LocalDate.parse("2023-09-20", formatter);
        LocalDate dueDate = LocalDate.parse("2023-10-01", formatter);
        Rental rental = new Rental(customer, car, rentDate, dueDate);
        carStore.addRental(rental);
        
        // Set current date to overdue scenario
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Get overdue customers
        List<Customer> overdueCustomers = carStore.listOverdueCustomers();
        
        // Verify customer C001 is in overdue list
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be John Doe", customer, overdueCustomers.get(0));
    }

    @Test
    public void testCase2_NoOverdueRentals() {
        // Create customer C002
        Customer customer = new Customer("Jane", "Smith", "456 Oak St");
        
        // Create car for rental
        Car car = new Car("DEF456", "Honda Civic", 45.0);
        carStore.addCar(car);
        
        // Create rental with future due date
        LocalDate rentDate = LocalDate.parse("2023-10-01", formatter);
        LocalDate dueDate = LocalDate.parse("2025-10-10", formatter);
        Rental rental = new Rental(customer, car, rentDate, dueDate);
        carStore.addRental(rental);
        
        // Set current date to before due date
        LocalDate currentDate = LocalDate.parse("2023-10-12", formatter);
        
        // Get overdue customers
        List<Customer> overdueCustomers = carStore.listOverdueCustomers();
        
        // Verify no overdue customers found
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }

    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // Create customer C003
        Customer customer = new Customer("Alice", "Johnson", "789 Pine St");
        
        // Create cars for rentals
        Car car1 = new Car("GHI789", "Ford Focus", 40.0);
        Car car2 = new Car("JKL012", "Nissan Altima", 55.0);
        carStore.addCar(car1);
        carStore.addCar(car2);
        
        // Create overdue rental R003
        LocalDate rentDate1 = LocalDate.parse("2023-09-25", formatter);
        LocalDate dueDate1 = LocalDate.parse("2023-10-03", formatter);
        Rental rental1 = new Rental(customer, car1, rentDate1, dueDate1);
        carStore.addRental(rental1);
        
        // Create returned rental R004 (not overdue)
        LocalDate rentDate2 = LocalDate.parse("2024-09-28", formatter);
        LocalDate dueDate2 = LocalDate.parse("2024-10-02", formatter);
        LocalDate backDate2 = LocalDate.parse("2024-10-01", formatter);
        Rental rental2 = new Rental(customer, car2, rentDate2, dueDate2);
        rental2.setBackDate(backDate2);
        carStore.addRental(rental2);
        
        // Set current date to overdue scenario
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Get overdue customers
        List<Customer> overdueCustomers = carStore.listOverdueCustomers();
        
        // Verify customer C003 is in overdue list due to rental R003
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be Alice Johnson", customer, overdueCustomers.get(0));
    }

    @Test
    public void testCase4_RentalWithBackDate() {
        // Create customer C004
        Customer customer = new Customer("Bob", "Brown", "321 Elm St");
        
        // Create car for rental
        Car car = new Car("MNO345", "Chevrolet Malibu", 60.0);
        carStore.addCar(car);
        
        // Create rental that was returned (has back date)
        LocalDate rentDate = LocalDate.parse("2023-09-28", formatter);
        LocalDate dueDate = LocalDate.parse("2023-10-03", formatter);
        LocalDate backDate = LocalDate.parse("2023-10-04", formatter);
        Rental rental = new Rental(customer, car, rentDate, dueDate);
        rental.setBackDate(backDate);
        carStore.addRental(rental);
        
        // Get overdue customers
        List<Customer> overdueCustomers = carStore.listOverdueCustomers();
        
        // Verify no overdue customers found since rental was returned
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }

    @Test
    public void testCase5_FutureDueDateRentals() {
        // Create customer C005
        Customer customer = new Customer("Charlie", "Green", "654 Maple St");
        
        // Create car for rental
        Car car = new Car("PQR678", "BMW 3 Series", 80.0);
        carStore.addCar(car);
        
        // Create rental with future due date
        LocalDate rentDate = LocalDate.parse("2023-10-01", formatter);
        LocalDate dueDate = LocalDate.parse("2025-10-15", formatter);
        Rental rental = new Rental(customer, car, rentDate, dueDate);
        carStore.addRental(rental);
        
        // Set current date to before due date
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Get overdue customers
        List<Customer> overdueCustomers = carStore.listOverdueCustomers();
        
        // Verify no overdue customers found
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }
}