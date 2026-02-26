import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
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
    public void testCase1_countRentalsForSingleCustomer() throws Exception {
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        
        // Create cars with plates "ABC123", "XYZ456", "LMN789"
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Create rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-05 10:00:00"));
        rental2.setTotalPrice(180.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-05 10:00:00"));
        rental3.setTotalPrice(160.0);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_countRentalsForMultipleCustomers() throws Exception {
        // Create customers C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak St");
        
        // Create cars with plates "ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        
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
        car7.setDailyPrice(46.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car5);
        cars.add(car6);
        cars.add(car7);
        store.setCars(cars);
        
        // Create rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-05 10:00:00"));
        rental2.setTotalPrice(180.0);
        
        // Create rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-05 10:00:00"));
        rental3.setTotalPrice(160.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2023-04-01 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2023-04-05 10:00:00"));
        rental4.setTotalPrice(220.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentalDate(dateFormat.parse("2023-05-01 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2023-05-05 10:00:00"));
        rental5.setTotalPrice(192.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentalDate(dateFormat.parse("2023-06-01 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2023-06-05 10:00:00"));
        rental6.setTotalPrice(168.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentalDate(dateFormat.parse("2023-07-01 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2023-07-05 10:00:00"));
        rental7.setTotalPrice(184.0);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        rentals.add(rental4);
        rentals.add(rental5);
        rentals.add(rental6);
        rentals.add(rental7);
        store.setRentals(rentals);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_countRentalsWithNoRecords() {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        
        // No rental records are added
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result is an empty map
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_countRentalsIncludingReturnedCars() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        
        // Create cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota Corolla");
        car1.setDailyPrice(42.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(55.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford Fusion");
        car3.setDailyPrice(48.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet Impala");
        car4.setDailyPrice(60.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        store.setCars(cars);
        
        // Create rental records for customer C004 (4 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental1.setTotalPrice(168.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-05 10:00:00"));
        rental2.setTotalPrice(220.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-05 10:00:00"));
        rental3.setTotalPrice(192.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2023-04-01 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2023-04-05 10:00:00"));
        rental4.setTotalPrice(240.0);
        
        // Mark 2 rentals as returned
        rental1.setBackDate(dateFormat.parse("2023-01-04 10:00:00"));
        rental2.setBackDate(dateFormat.parse("2023-02-04 10:00:00"));
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        rentals.add(rental4);
        store.setRentals(rentals);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - should count all 4 rentals regardless of return status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
    }
    
    @Test
    public void testCase5_countRentalsForCustomerWithOverdueCars() throws Exception {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setAddress("654 Cedar St");
        
        // Create cars with plates "PQR456", "STU789", "VWX012"
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota Avalon");
        car1.setDailyPrice(65.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda CR-V");
        car2.setDailyPrice(70.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford Escape");
        car3.setDailyPrice(68.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Create rental records for customer C005 (3 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental1.setTotalPrice(260.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-05 10:00:00"));
        rental2.setTotalPrice(280.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-05 10:00:00"));
        rental3.setTotalPrice(272.0);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - should count all 3 rentals regardless of overdue status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
}