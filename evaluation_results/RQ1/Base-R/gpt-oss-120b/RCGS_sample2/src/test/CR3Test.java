import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR3Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // SetUp: Create customer C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("ABC123");
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        
        // SetUp: Create rental R001 with due date in the past and backDate null
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setRentDate(LocalDate.parse("2023-09-20"));
        rental.setDueDate(LocalDate.parse("2023-10-01")); // Past due date
        rental.setBackDate(null); // Not returned
        
        // Add entities to store
        store.addCustomer(customer);
        store.addCar(car);
        store.addRental(rental);
        
        // Set current date to 2023-10-05 (after due date)
        // Note: This test relies on the system clock being set appropriately
        // In practice, we would mock LocalDate.now() for better testing
        
        // Execute: Get customers with overdue rentals
        List<Customer> overdueCustomers = store.getCustomersWithOverdueRentals();
        
        // Verify: Customer C001 should be in overdue list
        assertEquals("Should have one overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer should be in overdue list", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("DEF456");
        car.setModel("Honda Civic");
        car.setDailyPrice(40.0);
        
        // SetUp: Create rental R002 with future due date and backDate null
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setRentDate(LocalDate.parse("2023-10-01"));
        rental.setDueDate(LocalDate.parse("2025-10-10")); // Future due date
        rental.setBackDate(null); // Not returned
        
        // Add entities to store
        store.addCustomer(customer);
        store.addCar(car);
        store.addRental(rental);
        
        // Set current date to 2023-10-12 (before due date)
        // Note: This test relies on the system clock being set appropriately
        
        // Execute: Get customers with overdue rentals
        List<Customer> overdueCustomers = store.getCustomersWithOverdueRentals();
        
        // Verify: No overdue customers should be found
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // SetUp: Create cars for rentals
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Ford Focus");
        car1.setDailyPrice(45.0);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Chevrolet Malibu");
        car2.setDailyPrice(55.0);
        
        // SetUp: Create rental R003 (overdue) - backDate null, due date in past
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setRentDate(LocalDate.parse("2023-09-28"));
        rental1.setDueDate(LocalDate.parse("2023-10-03")); // Past due date
        rental1.setBackDate(null); // Not returned
        
        // SetUp: Create rental R004 (not overdue) - has back date
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setRentDate(LocalDate.parse("2024-09-28"));
        rental2.setDueDate(LocalDate.parse("2024-10-02"));
        rental2.setBackDate(LocalDate.parse("2024-10-01")); // Returned
        
        // Add entities to store
        store.addCustomer(customer);
        store.addCar(car1);
        store.addCar(car2);
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Set current date to 2023-10-05 (after R003's due date)
        // Note: This test relies on the system clock being set appropriately
        
        // Execute: Get customers with overdue rentals
        List<Customer> overdueCustomers = store.getCustomersWithOverdueRentals();
        
        // Verify: Customer C003 should be in overdue list due to R003
        assertEquals("Should have one overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer should be in overdue list due to R003", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("MNO345");
        car.setModel("Nissan Altima");
        car.setDailyPrice(48.0);
        
        // SetUp: Create rental R005 with back date (returned)
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setRentDate(LocalDate.parse("2023-09-28"));
        rental.setDueDate(LocalDate.parse("2023-10-03"));
        rental.setBackDate(LocalDate.parse("2023-10-04")); // Returned (even if after due date)
        
        // Add entities to store
        store.addCustomer(customer);
        store.addCar(car);
        store.addRental(rental);
        
        // Execute: Get customers with overdue rentals
        List<Customer> overdueCustomers = store.getCustomersWithOverdueRentals();
        
        // Verify: No overdue customers should be found since rental has back date
        assertTrue("Should have no overdue customers when rental has back date", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("PQR678");
        car.setModel("Hyundai Elantra");
        car.setDailyPrice(42.0);
        
        // SetUp: Create rental R006 with future due date
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setRentDate(LocalDate.parse("2023-10-01"));
        rental.setDueDate(LocalDate.parse("2025-10-15")); // Future due date
        rental.setBackDate(null); // Not returned
        
        // Add entities to store
        store.addCustomer(customer);
        store.addCar(car);
        store.addRental(rental);
        
        // Set current date to 2023-10-05 (before due date)
        // Note: This test relies on the system clock being set appropriately
        
        // Execute: Get customers with overdue rentals
        List<Customer> overdueCustomers = store.getCustomersWithOverdueRentals();
        
        // Verify: No overdue customers should be found since due date is in future
        assertTrue("Should have no overdue customers when due date is in future", overdueCustomers.isEmpty());
    }
}