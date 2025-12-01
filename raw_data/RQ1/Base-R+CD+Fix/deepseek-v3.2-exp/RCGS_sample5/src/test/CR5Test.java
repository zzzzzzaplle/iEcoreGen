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
        customer.setRentedCarPlate("ABC123");
        
        // Create 3 cars with plates "ABC123", "XYZ456", "LMN789"
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setDailyPrice(70.0);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2, car3));
        
        // Create 3 rental records for customer C001 with different car details
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental1.setTotalPrice(500.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental2.setTotalPrice(600.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental3.setTotalPrice(700.0);
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create customers C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setRentedCarPlate("ABC123");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setRentedCarPlate("LMN789");
        
        // Create cars with plates "ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        List<Car> cars = new ArrayList<>();
        String[] plates = {"ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setDailyPrice(50.0 + new Random().nextDouble() * 50);
            cars.add(car);
        }
        store.setCars(cars);
        
        // Create rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(cars.get(0)); // ABC123
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental1.setTotalPrice(500.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(cars.get(1)); // XYZ456
        rental2.setRentalDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental2.setTotalPrice(600.0);
        
        // Create rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(cars.get(2)); // LMN789
        rental3.setRentalDate(dateFormat.parse("2024-01-02 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-12 10:00:00"));
        rental3.setTotalPrice(550.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(cars.get(3)); // OPQ012
        rental4.setRentalDate(dateFormat.parse("2024-01-03 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-01-13 10:00:00"));
        rental4.setTotalPrice(650.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(cars.get(4)); // RST345
        rental5.setRentalDate(dateFormat.parse("2024-01-04 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2024-01-14 10:00:00"));
        rental5.setTotalPrice(750.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(cars.get(5)); // UVW678
        rental6.setRentalDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental6.setTotalPrice(850.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(cars.get(6)); // JKL901
        rental7.setRentalDate(dateFormat.parse("2024-01-06 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2024-01-16 10:00:00"));
        rental7.setTotalPrice(950.0);
        
        // Add all rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3, rental4, rental5, rental6, rental7));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setRentedCarPlate(null);
        
        // No rental records are added for customer C003
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should be an empty map (not null per specification)
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
        customer.setRentedCarPlate("DEF234");
        
        // Create cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        List<Car> cars = new ArrayList<>();
        String[] plates = {"DEF234", "GHI567", "JKL890", "MNO123"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setDailyPrice(50.0 + new Random().nextDouble() * 50);
            cars.add(car);
        }
        store.setCars(cars);
        
        // Create 4 rental records for customer C004
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(cars.get(0)); // DEF234
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental1.setTotalPrice(500.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(cars.get(1)); // GHI567
        rental2.setRentalDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental2.setTotalPrice(600.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(cars.get(2)); // JKL890
        rental3.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental3.setTotalPrice(700.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(cars.get(3)); // MNO123
        rental4.setRentalDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-01-25 10:00:00"));
        rental4.setTotalPrice(800.0);
        
        // Mark 2 of them as returned
        rental1.setBackDate(dateFormat.parse("2024-01-09 10:00:00"));
        rental3.setBackDate(dateFormat.parse("2024-01-18 10:00:00"));
        
        // Add all rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3, rental4));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 4 rentals regardless of return status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
        
        // Verify currently active rentals count (not part of the specification but mentioned in comments)
        long activeRentals = store.getRentals().stream()
            .filter(rental -> rental.getBackDate() == null)
            .count();
        assertEquals(2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setRentedCarPlate("PQR456");
        
        // Create cars with plates "PQR456", "STU789", "VWX012"
        List<Car> cars = new ArrayList<>();
        String[] plates = {"PQR456", "STU789", "VWX012"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setDailyPrice(50.0 + new Random().nextDouble() * 50);
            cars.add(car);
        }
        store.setCars(cars);
        
        // Create 3 rental records for customer C005
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(cars.get(0)); // PQR456
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental1.setTotalPrice(500.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(cars.get(1)); // STU789
        rental2.setRentalDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-08 10:00:00")); // Past due date
        rental2.setTotalPrice(400.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(cars.get(2)); // VWX012
        rental3.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental3.setTotalPrice(600.0);
        
        // Add all rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 3 rentals regardless of overdue status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Verify overdue rentals count (not part of the specification but mentioned in comments)
        Date currentDate = dateFormat.parse("2024-01-15 10:00:00");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
    }
}