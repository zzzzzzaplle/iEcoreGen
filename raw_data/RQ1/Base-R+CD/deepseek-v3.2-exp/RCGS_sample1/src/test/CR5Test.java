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
        
        // Add 3 cars with different plates
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
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Add 3 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-05 10:00:00"));
        rental2.setTotalPrice(160.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-03-05 10:00:00"));
        rental3.setTotalPrice(180.0);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(3, result.get(customer).intValue());
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create customers C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        
        // Add cars with different plates
        String[] plates = {"ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setModel("Model " + plate);
            car.setDailyPrice(50.0);
            store.addCar(car);
        }
        
        // Add rental records for customer C001 (2 rentals)
        List<Car> cars = store.getCars();
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(cars.get(0)); // ABC123
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(cars.get(1)); // XYZ456
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-05 10:00:00"));
        rental2.setTotalPrice(200.0);
        
        // Add rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(cars.get(2)); // LMN789
        rental3.setRentalDate(dateFormat.parse("2024-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-03-05 10:00:00"));
        rental3.setTotalPrice(200.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(cars.get(3)); // OPQ012
        rental4.setRentalDate(dateFormat.parse("2024-04-01 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-04-05 10:00:00"));
        rental4.setTotalPrice(200.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(cars.get(4)); // RST345
        rental5.setRentalDate(dateFormat.parse("2024-05-01 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2024-05-05 10:00:00"));
        rental5.setTotalPrice(200.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(cars.get(5)); // UVW678
        rental6.setRentalDate(dateFormat.parse("2024-06-01 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2024-06-05 10:00:00"));
        rental6.setTotalPrice(200.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(cars.get(6)); // JKL901
        rental7.setRentalDate(dateFormat.parse("2024-07-01 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2024-07-05 10:00:00"));
        rental7.setTotalPrice(200.0);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        store.addRental(rental5);
        store.addRental(rental6);
        store.addRental(rental7);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C001 = 2
        // - Number of rentals for customer C002 = 5
        assertEquals(2, result.size());
        assertEquals(2, result.get(customer1).intValue());
        assertEquals(5, result.get(customer2).intValue());
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine Rd");
        
        // No rental records are added for customer C003
        
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
        
        // Add cars with different plates
        String[] plates = {"DEF234", "GHI567", "JKL890", "MNO123"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setModel("Model " + plate);
            car.setDailyPrice(50.0);
            store.addCar(car);
        }
        
        List<Car> cars = store.getCars();
        
        // Add rental records for customer C004 (4 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(cars.get(0)); // DEF234
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(cars.get(1)); // GHI567
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-05 10:00:00"));
        rental2.setTotalPrice(200.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(cars.get(2)); // JKL890
        rental3.setRentalDate(dateFormat.parse("2024-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-03-05 10:00:00"));
        rental3.setTotalPrice(200.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(cars.get(3)); // MNO123
        rental4.setRentalDate(dateFormat.parse("2024-04-01 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-04-05 10:00:00"));
        rental4.setTotalPrice(200.0);
        
        // Mark 2 rentals as returned
        rental1.setBackDate(dateFormat.parse("2024-01-04 10:00:00"));
        rental2.setBackDate(dateFormat.parse("2024-02-04 10:00:00"));
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Number of rentals for customer C004 = 4 (stored)
        assertEquals(1, result.size());
        assertEquals(4, result.get(customer).intValue());
        
        // Count currently active rentals
        long activeRentals = store.getRentals().stream()
            .filter(Rental::isActive)
            .count();
        
        // Currently active rentals = 2 (after marking 2 as returned)
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
        customer.setAddress("654 Maple Dr");
        
        // Add cars with different plates
        String[] plates = {"PQR456", "STU789", "VWX012"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setModel("Model " + plate);
            car.setDailyPrice(50.0);
            store.addCar(car);
        }
        
        List<Car> cars = store.getCars();
        
        // Add rental records for customer C005 (3 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(cars.get(0)); // PQR456
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(cars.get(1)); // STU789
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-05 10:00:00"));
        rental2.setTotalPrice(200.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(cars.get(2)); // VWX012
        rental3.setRentalDate(dateFormat.parse("2024-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-03-05 10:00:00"));
        rental3.setTotalPrice(200.0);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertEquals(3, result.get(customer).intValue());
        
        // Check for overdue rentals with current date after due date for STU789
        Date currentDate = dateFormat.parse("2024-02-06 10:00:00");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Number of overdue rentals for customer C005 = 1
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }
}