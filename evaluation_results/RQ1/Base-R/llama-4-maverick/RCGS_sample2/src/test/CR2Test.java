import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // SetUp: Create a store instance and add rental objects
        List<Car> cars = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        
        // Create and add cars
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        
        // Create and add customers (rentals)
        Customer customer1 = new Customer("John", "Doe", "Address1", "ABC123", "2025-11-10", "2025-11-13");
        customer1.setBackDate("2025-11-12"); // Rented for 3 days
        Customer customer2 = new Customer("Jane", "Smith", "Address2", "XYZ789", "2025-11-10", "2025-11-13");
        customer2.setBackDate("2025-11-11"); // Rented for 2 days
        Customer customer3 = new Customer("Bob", "Johnson", "Address3", "LMN456", "2025-11-12", "2025-11-13");
        customer3.setBackDate("2025-11-12"); // Rented for 1 day
        
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        
        store.setCars(cars);
        store.setCustomers(customers);
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // SetUp: Create a store instance with no rentals
        // Verify there are no Rental objects added (empty lists)
        store.setCars(new ArrayList<>());
        store.setCustomers(new ArrayList<>());
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // SetUp: Create a store instance and add rental objects with same daily price
        List<Car> cars = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        
        // Create and add cars with same daily price
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        cars.add(car1);
        cars.add(car2);
        
        // Create and add customers (rentals)
        Customer customer1 = new Customer("Alice", "Brown", "Address4", "CAR001", "2025-11-12", "2025-11-13");
        customer1.setBackDate("2025-11-13"); // Rented for 2 days
        Customer customer2 = new Customer("Charlie", "Wilson", "Address5", "CAR002", "2025-11-12", "2025-12-01");
        customer2.setBackDate("2025-11-15"); // Rented for 4 days
        
        customers.add(customer1);
        customers.add(customer2);
        
        store.setCars(cars);
        store.setCustomers(customers);
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // SetUp: Create a store instance and add rental objects with varied daily prices
        List<Car> cars = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        
        // Create and add cars with varied daily prices
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        
        // Create and add customers (rentals)
        Customer customer1 = new Customer("David", "Lee", "Address6", "SED123", "2025-08-09", "2025-09-01");
        customer1.setBackDate("2025-08-13"); // Rented for 5 days
        Customer customer2 = new Customer("Emma", "Taylor", "Address7", "SUV456", "2025-08-11", "2026-01-01");
        customer2.setBackDate("2025-08-13"); // Rented for 3 days
        Customer customer3 = new Customer("Frank", "Miller", "Address8", "TRK789", "2025-08-09", "2025-09-01");
        customer3.setBackDate("2025-08-09"); // Rented for 1 day
        
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        
        store.setCars(cars);
        store.setCustomers(customers);
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // SetUp: Create a store instance and add rental objects with one-day rentals
        List<Car> cars = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        
        // Create and add cars
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        cars.add(car1);
        cars.add(car2);
        
        // Create and add customers (rentals) with one-day rentals
        Customer customer1 = new Customer("Grace", "Harris", "Address9", "MINI001", "2025-08-12", "2025-09-01");
        customer1.setBackDate("2025-08-12"); // Rented for 1 day
        Customer customer2 = new Customer("Henry", "Clark", "Address10", "MOTO002", "2025-08-09", "2025-09-01");
        customer2.setBackDate("2025-08-09"); // Rented for 1 day
        
        customers.add(customer1);
        customers.add(customer2);
        
        store.setCars(cars);
        store.setCustomers(customers);
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}