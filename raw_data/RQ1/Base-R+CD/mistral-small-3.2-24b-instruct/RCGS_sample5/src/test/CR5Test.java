import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CR5Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Test Case 1: Count Rentals for a Single Customer
        // Input: Customer with ID C001 rented 3 cars.
        
        // SetUp: Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        customer.setRentedCarPlate("C001");
        
        // SetUp: Add 3 rental records for customer C001 with different car details
        List<Rental> rentals = new ArrayList<>();
        
        // Create and add first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setTotalPrice(150.0);
        rentals.add(rental1);
        
        // Create and add second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setTotalPrice(135.0);
        rentals.add(rental2);
        
        // Create and add third rental
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setTotalPrice(120.0);
        rentals.add(rental3);
        
        // Set rentals to store
        store.setRentals(rentals);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals("Map should contain exactly one customer", 1, result.size());
        assertTrue("Map should contain customer C001", result.containsKey(customer));
        assertEquals("Number of rentals for customer C001 should be 3", 3, (int)result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Test Case 2: Count Rentals for Multiple Customers
        // Input: Customers C001 and C002 rented cars.
        
        // SetUp: Create customer C001
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        customer1.setRentedCarPlate("C001");
        
        // SetUp: Create customer C002
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        customer2.setRentedCarPlate("C002");
        
        List<Rental> rentals = new ArrayList<>();
        
        // Add 2 rentals for customer C001
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setTotalPrice(100.0);
        rentals.add(rental1);
        
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        rental2.setCar(car2);
        rental2.setCustomer(customer1);
        rental2.setTotalPrice(90.0);
        rentals.add(rental2);
        
        // Add 5 rentals for customer C002
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        rental3.setCar(car3);
        rental3.setCustomer(customer2);
        rental3.setTotalPrice(80.0);
        rentals.add(rental3);
        
        Rental rental4 = new Rental();
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("Chevrolet Malibu");
        car4.setDailyPrice(55.0);
        rental4.setCar(car4);
        rental4.setCustomer(customer2);
        rental4.setTotalPrice(110.0);
        rentals.add(rental4);
        
        Rental rental5 = new Rental();
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Nissan Altima");
        car5.setDailyPrice(48.0);
        rental5.setCar(car5);
        rental5.setCustomer(customer2);
        rental5.setTotalPrice(96.0);
        rentals.add(rental5);
        
        Rental rental6 = new Rental();
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Hyundai Sonata");
        car6.setDailyPrice(42.0);
        rental6.setCar(car6);
        rental6.setCustomer(customer2);
        rental6.setTotalPrice(84.0);
        rentals.add(rental6);
        
        Rental rental7 = new Rental();
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Kia Optima");
        car7.setDailyPrice(38.0);
        rental7.setCar(car7);
        rental7.setCustomer(customer2);
        rental7.setTotalPrice(76.0);
        rentals.add(rental7);
        
        store.setRentals(rentals);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals("Map should contain exactly two customers", 2, result.size());
        assertTrue("Map should contain customer C001", result.containsKey(customer1));
        assertTrue("Map should contain customer C002", result.containsKey(customer2));
        assertEquals("Number of rentals for customer C001 should be 2", 2, (int)result.get(customer1));
        assertEquals("Number of rentals for customer C002 should be 5", 5, (int)result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Test Case 3: Count Rentals with No Records
        // Input: No cars rented by any customer.
        
        // SetUp: Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine Rd");
        customer.setRentedCarPlate("C003");
        
        // No rental records are added for customer C003
        store.setRentals(new ArrayList<Rental>());
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should be empty map, not null
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be an empty map", result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Test Case 4: Count Rentals Including Returned Cars
        // Input: Customer rented and returned 4 cars.
        
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        customer.setRentedCarPlate("C004");
        
        List<Rental> rentals = new ArrayList<>();
        
        try {
            // Create 4 rental records for customer C004
            Rental rental1 = new Rental();
            Car car1 = new Car();
            car1.setPlate("DEF234");
            car1.setModel("Toyota Corolla");
            car1.setDailyPrice(35.0);
            rental1.setCar(car1);
            rental1.setCustomer(customer);
            rental1.setTotalPrice(70.0);
            // Mark as returned
            rental1.setBackDate(dateFormat.parse("2024-01-15 10:00:00"));
            rentals.add(rental1);
            
            Rental rental2 = new Rental();
            Car car2 = new Car();
            car2.setPlate("GHI567");
            car2.setModel("Honda Accord");
            car2.setDailyPrice(60.0);
            rental2.setCar(car2);
            rental2.setCustomer(customer);
            rental2.setTotalPrice(120.0);
            // Mark as returned
            rental2.setBackDate(dateFormat.parse("2024-02-20 14:30:00"));
            rentals.add(rental2);
            
            Rental rental3 = new Rental();
            Car car3 = new Car();
            car3.setPlate("JKL890");
            car3.setModel("Ford Mustang");
            car3.setDailyPrice(85.0);
            rental3.setCar(car3);
            rental3.setCustomer(customer);
            rental3.setTotalPrice(170.0);
            // Not returned (backDate remains null)
            rentals.add(rental3);
            
            Rental rental4 = new Rental();
            Car car4 = new Car();
            car4.setPlate("MNO123");
            car4.setModel("Chevrolet Camaro");
            car4.setDailyPrice(90.0);
            rental4.setCar(car4);
            rental4.setCustomer(customer);
            rental4.setTotalPrice(180.0);
            // Not returned (backDate remains null)
            rentals.add(rental4);
            
            store.setRentals(rentals);
            
            // Execute the method under test
            Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
            
            // Verify the result - method should count all rentals regardless of return status
            assertEquals("Map should contain exactly one customer", 1, result.size());
            assertTrue("Map should contain customer C004", result.containsKey(customer));
            assertEquals("Number of rentals for customer C004 should be 4 (all stored rentals)", 4, (int)result.get(customer));
            
        } catch (Exception e) {
            fail("Date parsing failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Test Case 5: Count Rentals for a Customer with Overdue Cars
        // Input: Customer rented 3 cars, with 1 overdue.
        
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setAddress("654 Maple Dr");
        customer.setRentedCarPlate("C005");
        
        List<Rental> rentals = new ArrayList<>();
        
        try {
            // Create 3 rental records for customer C005
            Rental rental1 = new Rental();
            Car car1 = new Car();
            car1.setPlate("PQR456");
            car1.setModel("Toyota RAV4");
            car1.setDailyPrice(55.0);
            rental1.setCar(car1);
            rental1.setCustomer(customer);
            rental1.setTotalPrice(110.0);
            // Set due date in the future (not overdue)
            rental1.setDueDate(dateFormat.parse("2024-12-31 23:59:59"));
            rentals.add(rental1);
            
            Rental rental2 = new Rental();
            Car car2 = new Car();
            car2.setPlate("STU789");
            car2.setModel("Honda CR-V");
            car2.setDailyPrice(60.0);
            rental2.setCar(car2);
            rental2.setCustomer(customer);
            rental2.setTotalPrice(120.0);
            // Set due date in the past (overdue)
            rental2.setDueDate(dateFormat.parse("2024-01-01 00:00:00"));
            rentals.add(rental2);
            
            Rental rental3 = new Rental();
            Car car3 = new Car();
            car3.setPlate("VWX012");
            car3.setModel("Ford Escape");
            car3.setDailyPrice(50.0);
            rental3.setCar(car3);
            rental3.setCustomer(customer);
            rental3.setTotalPrice(100.0);
            // Set due date in the future (not overdue)
            rental3.setDueDate(dateFormat.parse("2024-12-31 23:59:59"));
            rentals.add(rental3);
            
            store.setRentals(rentals);
            
            // Execute the method under test
            Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
            
            // Verify the result - method should count all rentals regardless of overdue status
            assertEquals("Map should contain exactly one customer", 1, result.size());
            assertTrue("Map should contain customer C005", result.containsKey(customer));
            assertEquals("Number of rentals for customer C005 should be 3 (all rentals)", 3, (int)result.get(customer));
            
        } catch (Exception e) {
            fail("Date parsing failed: " + e.getMessage());
        }
    }
}