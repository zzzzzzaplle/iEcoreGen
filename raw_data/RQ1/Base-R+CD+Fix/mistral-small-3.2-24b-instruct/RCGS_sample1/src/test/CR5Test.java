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
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        customer.setRentedCarPlate("ABC123");
        
        // Add 3 rental records for customer C001 with different car details
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(45.0);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2, car3));
        
        // Create rentals for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental2.setTotalPrice(200.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-25 10:00:00"));
        rental3.setTotalPrice(225.0);
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3));
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(3, result.get(customer).intValue());
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        customer1.setRentedCarPlate("ABC123");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        customer2.setRentedCarPlate("LMN789");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(45.0);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("Chevrolet Malibu");
        car4.setDailyPrice(55.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Nissan Altima");
        car5.setDailyPrice(48.0);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Hyundai Elantra");
        car6.setDailyPrice(42.0);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Kia Optima");
        car7.setDailyPrice(47.0);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2, car3, car4, car5, car6, car7));
        
        // Add rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental2.setTotalPrice(200.0);
        
        // Add rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental3.setTotalPrice(225.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental4.setTotalPrice(275.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentalDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2024-01-25 10:00:00"));
        rental5.setTotalPrice(240.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2024-02-05 10:00:00"));
        rental6.setTotalPrice(210.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentalDate(dateFormat.parse("2024-02-10 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2024-02-15 10:00:00"));
        rental7.setTotalPrice(235.0);
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3, rental4, rental5, rental6, rental7));
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the number of rentals
        assertEquals(2, result.size());
        assertEquals(2, result.get(customer1).intValue()); // Customer C001 = 2
        assertEquals(5, result.get(customer2).intValue()); // Customer C002 = 5
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        customer.setRentedCarPlate(null);
        
        // Add customer to store's car list (but no rentals)
        Car car = new Car();
        car.setPlate("DEF321");
        car.setModel("Toyota Corolla");
        car.setDailyPrice(35.0);
        store.setCars(Arrays.asList(car));
        
        // No rental records are added for customer C003
        store.setRentals(new ArrayList<Rental>());
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        customer.setRentedCarPlate("MNO123");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(45.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet Malibu");
        car4.setDailyPrice(55.0);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2, car3, car4));
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        Rental rental1 = new Rental(); // Will be returned
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setBackDate(dateFormat.parse("2024-01-05 10:00:00")); // Returned
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental(); // Will be returned
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental2.setBackDate(dateFormat.parse("2024-01-15 10:00:00")); // Returned
        rental2.setTotalPrice(200.0);
        
        Rental rental3 = new Rental(); // Active rental
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-25 10:00:00"));
        rental3.setBackDate(null); // Not returned
        rental3.setTotalPrice(225.0);
        
        Rental rental4 = new Rental(); // Active rental
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-02-05 10:00:00"));
        rental4.setBackDate(null); // Not returned
        rental4.setTotalPrice(220.0);
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3, rental4));
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the number of rentals for customer C004 = 4 (all stored rentals count)
        assertEquals(1, result.size());
        assertEquals(4, result.get(customer).intValue());
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setAddress("654 Maple Ave");
        customer.setRentedCarPlate("STU789");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(45.0);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2, car3));
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        Rental rental1 = new Rental(); // Normal rental
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setBackDate(dateFormat.parse("2024-01-05 10:00:00")); // Returned on time
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental(); // Overdue rental
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00")); // Past due date
        rental2.setBackDate(null); // Not returned (overdue)
        rental2.setTotalPrice(200.0);
        
        Rental rental3 = new Rental(); // Normal rental
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-25 10:00:00"));
        rental3.setBackDate(dateFormat.parse("2024-01-25 10:00:00")); // Returned on time
        rental3.setTotalPrice(225.0);
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3));
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the number of rentals for customer C005 = 3 (all rentals count, regardless of status)
        assertEquals(1, result.size());
        assertEquals(3, result.get(customer).intValue());
        
        // Additional verification for overdue rentals (using findCustomersWithOverdueRentals method)
        Date currentDate = dateFormat.parse("2024-01-20 10:00:00"); // Current date after due date of rental2
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }
}