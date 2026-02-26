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
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
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
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rentals for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setTotalPrice(150.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setTotalPrice(120.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer1);
        rental3.setCar(car3);
        rental3.setTotalPrice(135.0);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer1));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create customers C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
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
        car4.setModel("Nissan Altima");
        car4.setDailyPrice(55.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Chevrolet Malibu");
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
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4, car5, car6, car7));
        
        // Add rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setTotalPrice(100.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setTotalPrice(80.0);
        
        // Add rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setTotalPrice(90.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setTotalPrice(110.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setTotalPrice(96.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setTotalPrice(84.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setTotalPrice(94.0);
        
        // Add rentals to store
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3, rental4, rental5, rental6, rental7));
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        // No rental records are added for customer C003
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - should be empty map, not null
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C004
        Customer customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        
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
        car4.setModel("Nissan Altima");
        car4.setDailyPrice(55.0);
        
        // Add cars to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4));
        
        // Add rental records for customer C004 (4 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer4);
        rental1.setCar(car1);
        rental1.setTotalPrice(150.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer4);
        rental2.setCar(car2);
        rental2.setTotalPrice(120.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer4);
        rental3.setCar(car3);
        rental3.setTotalPrice(135.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer4);
        rental4.setCar(car4);
        rental4.setTotalPrice(165.0);
        
        // Mark 2 of them as returned
        rental1.setBackDate(new Date());
        rental3.setBackDate(new Date());
        
        // Add rentals to store
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3, rental4));
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - should count all 4 rentals regardless of return status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer4));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Wilson");
        
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
        store.getCars().addAll(Arrays.asList(car1, car2, car3));
        
        // Add rental records for customer C005 (3 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer5);
        rental1.setCar(car1);
        rental1.setTotalPrice(150.0);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer5);
        rental2.setCar(car2);
        rental2.setTotalPrice(120.0);
        rental2.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-03 10:00:00")); // Overdue date
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer5);
        rental3.setCar(car3);
        rental3.setTotalPrice(135.0);
        rental3.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-07 10:00:00"));
        
        // Mark STU789 as overdue by setting current date after due date
        rental2.setBackDate(null); // Still rented
        
        // Add rentals to store
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3));
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - should count all 3 rentals regardless of overdue status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer5));
    }
}