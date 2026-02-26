import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleOverdueRental() throws Exception {
        // Test Case 1: Single Overdue Rental Check
        // Set up customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        
        // Set up car
        Car car = new Car();
        car.setPlate("ABC123");
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        
        // Set up rental R001 with overdue status
        Rental rental = new Rental();
        rental.setRentalDate(dateFormat.parse("2023-09-15 10:00:00"));
        rental.setDueDate(dateFormat.parse("2023-10-01 17:00:00"));
        rental.setBackDate(null); // Not returned
        rental.setTotalPrice(500.0);
        rental.setLeasingTerms("Standard");
        rental.setCar(car);
        rental.setCustomer(customer);
        
        // Add to store
        store.addCar(car);
        store.addRental(rental);
        
        // Set current date to overdue scenario
        Date currentDate = dateFormat.parse("2023-10-05 12:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be in overdue list", overdueCustomers.contains(customer));
        
        // Verify customer details
        Customer foundCustomer = overdueCustomers.get(0);
        assertEquals("John", foundCustomer.getName());
        assertEquals("Doe", foundCustomer.getSurname());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Test Case 2: No Overdue Rentals
        // Set up customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        customer.setAddress("456 Oak Ave");
        
        // Set up car
        Car car = new Car();
        car.setPlate("XYZ789");
        car.setModel("Honda Civic");
        car.setDailyPrice(45.0);
        
        // Set up rental R002 with future due date
        Rental rental = new Rental();
        rental.setRentalDate(dateFormat.parse("2023-10-01 09:00:00"));
        rental.setDueDate(dateFormat.parse("2025-10-10 17:00:00")); // Future date
        rental.setBackDate(null); // Not returned but not overdue
        rental.setTotalPrice(450.0);
        rental.setLeasingTerms("Extended");
        rental.setCar(car);
        rental.setCustomer(customer);
        
        // Add to store
        store.addCar(car);
        store.addRental(rental);
        
        // Set current date before due date
        Date currentDate = dateFormat.parse("2023-10-12 14:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results - no overdue rentals should be found
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Test Case 3: Multiple Rentals with Mixed Status
        // Set up customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine Rd");
        
        // Set up cars
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Ford Focus");
        car1.setDailyPrice(40.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(55.0);
        
        // Set up rental R003 (overdue)
        Rental rental1 = new Rental();
        rental1.setRentalDate(dateFormat.parse("2023-09-20 11:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-10-03 17:00:00")); // Past due
        rental1.setBackDate(null); // Not returned
        rental1.setTotalPrice(400.0);
        rental1.setLeasingTerms("Basic");
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        
        // Set up rental R004 (returned, not overdue)
        Rental rental2 = new Rental();
        rental2.setRentalDate(dateFormat.parse("2024-09-28 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-10-02 17:00:00"));
        rental2.setBackDate(dateFormat.parse("2024-10-01 15:00:00")); // Returned before due
        rental2.setTotalPrice(550.0);
        rental2.setLeasingTerms("Premium");
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        
        // Add to store
        store.addCar(car1);
        store.addCar(car2);
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Set current date for overdue evaluation
        Date currentDate = dateFormat.parse("2023-10-05 16:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results - customer should be overdue due to rental1
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be in overdue list", overdueCustomers.contains(customer));
        
        // Verify customer details
        Customer foundCustomer = overdueCustomers.get(0);
        assertEquals("Alice", foundCustomer.getName());
        assertEquals("Johnson", foundCustomer.getSurname());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Test Case 4: Rental with Back Date
        // Set up customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        
        // Set up car
        Car car = new Car();
        car.setPlate("DEF456");
        car.setModel("Nissan Altima");
        car.setDailyPrice(48.0);
        
        // Set up rental R005 (returned, but after due date)
        Rental rental = new Rental();
        rental.setRentalDate(dateFormat.parse("2023-09-25 08:00:00"));
        rental.setDueDate(dateFormat.parse("2023-10-03 17:00:00"));
        rental.setBackDate(dateFormat.parse("2023-10-04 12:00:00")); // Returned after due date
        rental.setTotalPrice(480.0);
        rental.setLeasingTerms("Standard");
        rental.setCar(car);
        rental.setCustomer(customer);
        
        // Add to store
        store.addCar(car);
        store.addRental(rental);
        
        // Set current date
        Date currentDate = dateFormat.parse("2023-10-05 10:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results - should not be overdue since car was returned (even if late)
        assertTrue("Should not find any overdue customers when car is returned", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Test Case 5: Future Due Date Rentals
        // Set up customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        customer.setAddress("654 Maple Dr");
        
        // Set up car
        Car car = new Car();
        car.setPlate("GHI789");
        car.setModel("Hyundai Sonata");
        car.setDailyPrice(42.0);
        
        // Set up rental R006 with future due date
        Rental rental = new Rental();
        rental.setRentalDate(dateFormat.parse("2023-10-01 09:00:00"));
        rental.setDueDate(dateFormat.parse("2025-10-15 17:00:00")); // Far future date
        rental.setBackDate(null); // Not returned but not overdue
        rental.setTotalPrice(420.0);
        rental.setLeasingTerms("Long-term");
        rental.setCar(car);
        rental.setCustomer(customer);
        
        // Add to store
        store.addCar(car);
        store.addRental(rental);
        
        // Set current date (well before due date)
        Date currentDate = dateFormat.parse("2023-10-05 14:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results - no overdue rentals should be found
        assertTrue("Should not find any overdue customers with future due dates", overdueCustomers.isEmpty());
    }
}