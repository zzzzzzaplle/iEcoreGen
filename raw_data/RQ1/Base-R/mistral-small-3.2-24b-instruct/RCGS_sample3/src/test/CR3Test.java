import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private Store store;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private Car car1;
    private Car car2;
    private Car car3;
    private Car car4;
    private Car car5;
    private Rental rental1;
    private Rental rental2;
    private Rental rental3;
    private Rental rental4;
    private Rental rental5;
    private Rental rental6;
    
    @Before
    public void setUp() {
        store = new Store();
        
        // Create customers
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        customer3 = new Customer();
        customer3.setName("Alice");
        customer3.setSurname("Johnson");
        
        customer4 = new Customer();
        customer4.setName("Bob");
        customer4.setSurname("Brown");
        
        customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Green");
        
        // Create cars
        car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Model1");
        car1.setDailyPrice(50.0);
        
        car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Model2");
        car2.setDailyPrice(60.0);
        
        car3 = new Car();
        car3.setPlate("CAR003");
        car3.setModel("Model3");
        car3.setDailyPrice(70.0);
        
        car4 = new Car();
        car4.setPlate("CAR004");
        car4.setModel("Model4");
        car4.setDailyPrice(80.0);
        
        car5 = new Car();
        car5.setPlate("CAR005");
        car5.setModel("Model5");
        car5.setDailyPrice(90.0);
        
        // Add customers and cars to store
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);
        store.setCustomers(customers);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car5);
        store.setCarGallery(cars);
        
        // Create rentals
        rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.parse("2023-09-20"));
        rental1.setDueDate(LocalDate.parse("2023-10-01"));
        rental1.setBackDate(null);
        
        rental2 = new Rental();
        rental2.setCustomer(customer2);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.parse("2023-10-10"));
        rental2.setDueDate(LocalDate.parse("2025-10-10"));
        rental2.setBackDate(null);
        
        rental3 = new Rental();
        rental3.setCustomer(customer3);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.parse("2023-09-25"));
        rental3.setDueDate(LocalDate.parse("2023-10-03"));
        rental3.setBackDate(null);
        
        rental4 = new Rental();
        rental4.setCustomer(customer3);
        rental4.setCar(car4);
        rental4.setRentDate(LocalDate.parse("2024-09-30"));
        rental4.setDueDate(LocalDate.parse("2024-10-02"));
        rental4.setBackDate(LocalDate.parse("2024-10-01"));
        
        rental5 = new Rental();
        rental5.setCustomer(customer4);
        rental5.setCar(car5);
        rental5.setRentDate(LocalDate.parse("2023-10-01"));
        rental5.setDueDate(LocalDate.parse("2023-10-03"));
        rental5.setBackDate(LocalDate.parse("2023-10-04"));
        
        rental6 = new Rental();
        rental6.setCustomer(customer5);
        rental6.setCar(car1);
        rental6.setRentDate(LocalDate.parse("2023-10-04"));
        rental6.setDueDate(LocalDate.parse("2025-10-15"));
        rental6.setBackDate(null);
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // Set up rentals for this test case
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        store.setRentals(rentals);
        
        // Set fixed current date for testing
        LocalDate fixedCurrentDate = LocalDate.parse("2023-10-05");
        
        // Temporarily override LocalDate.now() for testing
        try {
            java.lang.reflect.Field field = LocalDate.class.getDeclaredField("currentDate");
            field.setAccessible(true);
            field.set(null, fixedCurrentDate);
        } catch (Exception e) {
            // In real implementation, use a date provider interface
            // For this test, we'll manually verify the logic
        }
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify customer1 is in overdue list
        assertTrue("Customer C001 should be in overdue list", overdueCustomers.contains(customer1));
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // Set up rentals for this test case
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Set fixed current date for testing
        LocalDate fixedCurrentDate = LocalDate.parse("2023-10-12");
        
        // Temporarily override LocalDate.now() for testing
        try {
            java.lang.reflect.Field field = LocalDate.class.getDeclaredField("currentDate");
            field.setAccessible(true);
            field.set(null, fixedCurrentDate);
        } catch (Exception e) {
            // In real implementation, use a date provider interface
        }
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify customer2 is NOT in overdue list
        assertFalse("Customer C002 should not be in overdue list", overdueCustomers.contains(customer2));
        assertEquals("Should have no overdue customers", 0, overdueCustomers.size());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // Set up rentals for this test case
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental3);
        rentals.add(rental4);
        store.setRentals(rentals);
        
        // Set fixed current date for testing
        LocalDate fixedCurrentDate = LocalDate.parse("2023-10-05");
        
        // Temporarily override LocalDate.now() for testing
        try {
            java.lang.reflect.Field field = LocalDate.class.getDeclaredField("currentDate");
            field.setAccessible(true);
            field.set(null, fixedCurrentDate);
        } catch (Exception e) {
            // In real implementation, use a date provider interface
        }
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify customer3 is in overdue list (due to rental3)
        assertTrue("Customer C003 should be in overdue list", overdueCustomers.contains(customer3));
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // Set up rentals for this test case
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental5);
        store.setRentals(rentals);
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify customer4 is NOT in overdue list (rental was returned)
        assertFalse("Customer C004 should not be in overdue list", overdueCustomers.contains(customer4));
        assertEquals("Should have no overdue customers", 0, overdueCustomers.size());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // Set up rentals for this test case
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental6);
        store.setRentals(rentals);
        
        // Set fixed current date for testing
        LocalDate fixedCurrentDate = LocalDate.parse("2023-10-05");
        
        // Temporarily override LocalDate.now() for testing
        try {
            java.lang.reflect.Field field = LocalDate.class.getDeclaredField("currentDate");
            field.setAccessible(true);
            field.set(null, fixedCurrentDate);
        } catch (Exception e) {
            // In real implementation, use a date provider interface
        }
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify customer5 is NOT in overdue list (due date is in future)
        assertFalse("Customer C005 should not be in overdue list", overdueCustomers.contains(customer5));
        assertEquals("Should have no overdue customers", 0, overdueCustomers.size());
    }
}