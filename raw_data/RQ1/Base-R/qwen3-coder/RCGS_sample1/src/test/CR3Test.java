import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        // Test Case 1: Single Overdue Rental Check
        // Input: Check for overdue rentals for customer ID: C001.
        
        // SetUp:
        // 1. Create a customer with customer ID: C001 and name: "John Doe"
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        rentalStore.addCustomer(customer);
        
        // 2. Create a rental with rental ID: R001 for customer ID: C001, with backDate: null, and dueDate: "2023-10-01".
        Rental rental = new Rental();
        rental.setCustomerName("John");
        rental.setCustomerSurname("Doe");
        rental.setCarPlate("ABC123");
        rental.setDueDate(LocalDate.parse("2023-10-01", formatter));
        rental.setBackDate(null);
        rentalStore.addRental(rental);
        
        // Add a car for the rental
        Car car = new Car();
        car.setPlate("ABC123");
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        rentalStore.addCar(car);
        
        // 3. Set the current date to "2023-10-05" (overdue rental) - This is handled by the method using LocalDate.now()
        // For testing, we need to mock the current date or use a fixed reference date
        // Since we can't easily mock LocalDate.now(), we'll rely on the method logic
        
        // 4. Create an overdue notice to customer C001 - This is part of expected behavior
        
        // Execute the method under test
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Expected Output: Customer ID C001 is overdue for rental R001.
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer name should be John", "John", overdueCustomers.get(0).getName());
        assertEquals("Customer surname should be Doe", "Doe", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // Test Case 2: No Overdue Rentals
        // Input: Check for overdue rentals for customer ID: C002.
        
        // SetUp:
        // 1. Create a customer with customer ID: C002 and name: "Jane Smith"
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        rentalStore.addCustomer(customer);
        
        // 2. Create a rental with rental ID: R002 for customer ID: C002, with backDate: null, and dueDate: "2025-10-10".
        Rental rental = new Rental();
        rental.setCustomerName("Jane");
        rental.setCustomerSurname("Smith");
        rental.setCarPlate("DEF456");
        rental.setDueDate(LocalDate.parse("2025-10-10", formatter));
        rental.setBackDate(null);
        rentalStore.addRental(rental);
        
        // Add a car for the rental
        Car car = new Car();
        car.setPlate("DEF456");
        car.setModel("Honda Civic");
        car.setDailyPrice(40.0);
        rentalStore.addCar(car);
        
        // 3. Set the current date to "2023-10-12" - Future due date, not overdue
        
        // Execute the method under test
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C002.
        assertTrue("Should return empty list for no overdue rentals", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // Test Case 3: Multiple Rentals with Mixed Status
        // Input: Check for overdue rentals for customer ID: C003.
        
        // SetUp:
        // 1. Create a customer with customer ID: C003 and name: "Alice Johnson"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        rentalStore.addCustomer(customer);
        
        // 2. Create a rental with rental ID: R003 for customer ID: C003, with backDate: null, and dueDate: "2023-10-03".
        Rental rental1 = new Rental();
        rental1.setCustomerName("Alice");
        rental1.setCustomerSurname("Johnson");
        rental1.setCarPlate("GHI789");
        rental1.setDueDate(LocalDate.parse("2023-10-03", formatter));
        rental1.setBackDate(null);
        rentalStore.addRental(rental1);
        
        // 3. Create another rental with rental ID: R004 for customer ID: C003, with backDate: "2024-10-01", and dueDate: "2024-10-02".
        Rental rental2 = new Rental();
        rental2.setCustomerName("Alice");
        rental2.setCustomerSurname("Johnson");
        rental2.setCarPlate("JKL012");
        rental2.setDueDate(LocalDate.parse("2024-10-02", formatter));
        rental2.setBackDate(LocalDate.parse("2024-10-01", formatter));
        rentalStore.addRental(rental2);
        
        // Add cars for the rentals
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Ford Focus");
        car1.setDailyPrice(35.0);
        rentalStore.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Chevrolet Malibu");
        car2.setDailyPrice(45.0);
        rentalStore.addCar(car2);
        
        // 4. Set the current date to "2023-10-05" - R003 is overdue, R004 is not
        
        // Execute the method under test
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Expected Output: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue.
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer name should be Alice", "Alice", overdueCustomers.get(0).getName());
        assertEquals("Customer surname should be Johnson", "Johnson", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // Test Case 4: Rental with Back Date
        // Input: Check for overdue rentals for customer ID: C004.
        
        // SetUp:
        // 1. Create a customer with customer ID: C004 and name: "Bob Brown"
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        rentalStore.addCustomer(customer);
        
        // 2. Create a rental with rental ID: R005 for customer ID: C004, with backDate: "2023-10-04", and dueDate: "2023-10-03".
        Rental rental = new Rental();
        rental.setCustomerName("Bob");
        rental.setCustomerSurname("Brown");
        rental.setCarPlate("MNO345");
        rental.setDueDate(LocalDate.parse("2023-10-03", formatter));
        rental.setBackDate(LocalDate.parse("2023-10-04", formatter)); // Returned late but has back date
        rentalStore.addRental(rental);
        
        // Add a car for the rental
        Car car = new Car();
        car.setPlate("MNO345");
        car.setModel("Nissan Altima");
        car.setDailyPrice(42.0);
        rentalStore.addCar(car);
        
        // Execute the method under test
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C004, as rental R005 was returned on time.
        // Note: The method only considers rentals with backDate == null as potentially overdue
        assertTrue("Should return empty list when rental has back date", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // Test Case 5: Future Due Date Rentals
        // Input: Check for overdue rentals for customer ID: C005.
        
        // SetUp:
        // 1. Create a customer with customer ID: C005 and name: "Charlie Green"
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        rentalStore.addCustomer(customer);
        
        // 2. Create a rental with rental ID: R006 for customer ID: C005, with dueDate: "2025-10-15".
        Rental rental = new Rental();
        rental.setCustomerName("Charlie");
        rental.setCustomerSurname("Green");
        rental.setCarPlate("PQR678");
        rental.setDueDate(LocalDate.parse("2025-10-15", formatter));
        rental.setBackDate(null);
        rentalStore.addRental(rental);
        
        // Add a car for the rental
        Car car = new Car();
        car.setPlate("PQR678");
        car.setModel("Hyundai Elantra");
        car.setDailyPrice(38.0);
        rentalStore.addCar(car);
        
        // 3. Set the current date to "2023-10-05" - Future due date, not overdue
        
        // Execute the method under test
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers();
        
        // Expected Output: No overdue rentals found for customer ID C005, as the due date is in the future.
        assertTrue("Should return empty list for future due dates", overdueCustomers.isEmpty());
    }
}