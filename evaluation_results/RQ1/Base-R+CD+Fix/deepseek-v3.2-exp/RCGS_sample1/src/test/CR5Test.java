import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR5Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C001
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setRentedCarPlate("C001");
        
        // Add 3 rental records for customer C001 with different car details
        // Add cars with plates "ABC123", "XYZ456", "LMN789" rented by customer C001
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setDailyPrice(70.0);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer1);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-20 10:00:00"));
        
        store.setRentals(Arrays.asList(rental1, rental2, rental3));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer1));
        assertEquals(Integer.valueOf(3), result.get(customer1));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // SetUp: Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setRentedCarPlate("C001");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setRentedCarPlate("C002");
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        // Customer C001 rented cars with plates "ABC123", "XYZ456"
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setDailyPrice(60.0);
        
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setDailyPrice(70.0);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setDailyPrice(80.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setDailyPrice(90.0);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setDailyPrice(100.0);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setDailyPrice(110.0);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2024-01-20 10:00:00"));
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentalDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2024-01-25 10:00:00"));
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentalDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2024-01-30 10:00:00"));
        
        store.setRentals(Arrays.asList(rental1, rental2, rental3, rental4, rental5, rental6, rental7));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C001 = 2
        // - Number of rentals for customer C002 = 5
        assertEquals(2, result.size());
        assertTrue(result.containsKey(customer1));
        assertTrue(result.containsKey(customer2));
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C003
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        customer3.setRentedCarPlate("C003");
        
        // No rental records are added for customer C003
        store.setRentals(new ArrayList<>());
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C004
        Customer customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        customer4.setRentedCarPlate("C004");
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        // Rental records: Customer C004 rented cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setDailyPrice(70.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setDailyPrice(80.0);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer4);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental1.setBackDate(dateFormat.parse("2024-01-09 10:00:00")); // returned
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer4);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental2.setBackDate(dateFormat.parse("2024-01-14 10:00:00")); // returned
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer4);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-20 10:00:00"));
        // not returned
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer4);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-01-25 10:00:00"));
        // not returned
        
        store.setRentals(Arrays.asList(rental1, rental2, rental3, rental4));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C004 = 4 (stored)
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer4));
        assertEquals(Integer.valueOf(4), result.get(customer4));
        
        // Verify currently active rentals = 2 (after marking 2 as returned)
        long activeRentals = store.getRentals().stream()
            .filter(rental -> rental.getBackDate() == null)
            .count();
        assertEquals(2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C005
        Customer customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Wilson");
        customer5.setRentedCarPlate("C005");
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        // Rental records: Customer C005 rented cars with plates "PQR456", "STU789", "VWX012" with "STU789" being overdue
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setDailyPrice(70.0);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer5);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer5);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        // This rental is overdue (due date is in the past)
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer5);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-20 10:00:00"));
        
        store.setRentals(Arrays.asList(rental1, rental2, rental3));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer5));
        assertEquals(Integer.valueOf(3), result.get(customer5));
        
        // Verify number of overdue rentals for customer C005 = 1
        Date currentDate = dateFormat.parse("2024-01-16 10:00:00"); // Past due date for rental2
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer5));
    }
}