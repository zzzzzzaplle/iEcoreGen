import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR3Test {
    private CarRentalSystem carRentalSystem;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        carRentalSystem = new CarRentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // SetUp: Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("ABC123");
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        
        // Create rental R001 with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(LocalDate.parse("2023-10-01", formatter));
        rental.setBackDate(null);
        
        // Add to system
        carRentalSystem.getCustomers().add(customer);
        carRentalSystem.getCars().add(car);
        carRentalSystem.getRentals().add(rental);
        
        // Set current date to "2023-10-05" (overdue)
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Get customers with overdue rentals
        List<Customer> overdueCustomers = carRentalSystem.getCustomersWithOverdueRentals();
        
        // Verify customer C001 is in overdue list
        assertEquals("Should have one overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be in overdue list", overdueCustomers.contains(customer));
    }

    @Test
    public void testCase2_NoOverdueRentals() {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("DEF456");
        car.setModel("Honda Civic");
        car.setDailyPrice(40.0);
        
        // Create rental R002 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(LocalDate.parse("2025-10-10", formatter));
        rental.setBackDate(null);
        
        // Add to system
        carRentalSystem.getCustomers().add(customer);
        carRentalSystem.getCars().add(car);
        carRentalSystem.getRentals().add(rental);
        
        // Set current date to "2023-10-12"
        LocalDate currentDate = LocalDate.parse("2023-10-12", formatter);
        
        // Get customers with overdue rentals
        List<Customer> overdueCustomers = carRentalSystem.getCustomersWithOverdueRentals();
        
        // Verify no overdue customers found
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }

    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // Create cars for rentals
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Ford Focus");
        car1.setDailyPrice(35.0);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Nissan Altima");
        car2.setDailyPrice(45.0);
        
        // Create rental R003 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setDueDate(LocalDate.parse("2023-10-03", formatter));
        rental1.setBackDate(null);
        
        // Create rental R004 (returned on time)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setDueDate(LocalDate.parse("2024-10-02", formatter));
        rental2.setBackDate(LocalDate.parse("2024-10-01", formatter));
        
        // Add to system
        carRentalSystem.getCustomers().add(customer);
        carRentalSystem.getCars().add(car1);
        carRentalSystem.getCars().add(car2);
        carRentalSystem.getRentals().add(rental1);
        carRentalSystem.getRentals().add(rental2);
        
        // Set current date to "2023-10-05"
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Get customers with overdue rentals
        List<Customer> overdueCustomers = carRentalSystem.getCustomersWithOverdueRentals();
        
        // Verify customer C003 is in overdue list (due to rental R003)
        assertEquals("Should have one overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be in overdue list", overdueCustomers.contains(customer));
    }

    @Test
    public void testCase4_RentalWithBackDate() {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("MNO345");
        car.setModel("Chevrolet Malibu");
        car.setDailyPrice(55.0);
        
        // Create rental R005 with back date (returned)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(LocalDate.parse("2023-10-03", formatter));
        rental.setBackDate(LocalDate.parse("2023-10-04", formatter));
        
        // Add to system
        carRentalSystem.getCustomers().add(customer);
        carRentalSystem.getCars().add(car);
        carRentalSystem.getRentals().add(rental);
        
        // Get customers with overdue rentals
        List<Customer> overdueCustomers = carRentalSystem.getCustomersWithOverdueRentals();
        
        // Verify no overdue customers found (car was returned)
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }

    @Test
    public void testCase5_FutureDueDateRentals() {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // Create car for rental
        Car car = new Car();
        car.setPlate("PQR678");
        car.setModel("BMW 3 Series");
        car.setDailyPrice(80.0);
        
        // Create rental R006 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(LocalDate.parse("2025-10-15", formatter));
        rental.setBackDate(null);
        
        // Add to system
        carRentalSystem.getCustomers().add(customer);
        carRentalSystem.getCars().add(car);
        carRentalSystem.getRentals().add(rental);
        
        // Set current date to "2023-10-05"
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Get customers with overdue rentals
        List<Customer> overdueCustomers = carRentalSystem.getCustomersWithOverdueRentals();
        
        // Verify no overdue customers found (due date is in future)
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }
}