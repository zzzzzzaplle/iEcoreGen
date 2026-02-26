import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private CarRentalStore store;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        // Initialize store and date formatter before each test
        store = new CarRentalStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // Test Case 1: Calculate total revenue from rentals with multiple rental records
        
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        
        // Create customers with rental information
        Customer customer1 = new Customer("John", "Doe", "Address1", "ABC123", "2025-11-10", "2025-11-13");
        customer1.setBackDate("2025-11-12");
        
        Customer customer2 = new Customer("Jane", "Smith", "Address2", "XYZ789", "2025-11-10", "2025-11-13");
        customer2.setBackDate("2025-11-11");
        
        Customer customer3 = new Customer("Bob", "Johnson", "Address3", "LMN456", "2025-11-12", "2025-11-13");
        customer3.setBackDate("2025-11-12");
        
        // Add cars and customers to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        store.addCustomer(customer3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: Calculate total revenue from rentals with no rental records
        
        // Verify there are no rental objects added (store is empty after setUp)
        assertTrue(store.getCars().isEmpty());
        assertTrue(store.getCustomers().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Test Case 3: Calculate total revenue from rentals where multiple cars have the same daily price
        
        // Create cars with same daily price
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        
        // Create customers with rental information
        Customer customer1 = new Customer("Alice", "Brown", "Address4", "CAR001", "2025-11-12", "2025-11-13");
        customer1.setBackDate("2025-11-13");
        
        Customer customer2 = new Customer("Charlie", "Wilson", "Address5", "CAR002", "2025-11-12", "2025-12-01");
        customer2.setBackDate("2025-11-15");
        
        // Add cars and customers to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Test Case 4: Calculate total revenue from rentals with varied daily prices
        
        // Create cars with different daily prices
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        
        // Create customers with rental information
        Customer customer1 = new Customer("David", "Lee", "Address6", "SED123", "2025-08-09", "2025-09-01");
        customer1.setBackDate("2025-08-13");
        
        Customer customer2 = new Customer("Emma", "Davis", "Address7", "SUV456", "2025-08-11", "2026-01-01");
        customer2.setBackDate("2025-08-13");
        
        Customer customer3 = new Customer("Frank", "Miller", "Address8", "TRK789", "2025-08-09", "2025-09-01");
        customer3.setBackDate("2025-08-09");
        
        // Add cars and customers to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        store.addCustomer(customer3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Test Case 5: Calculate total revenue from rentals with all rentals for only one day
        
        // Create cars
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        
        // Create customers with rental information (one-day rentals)
        Customer customer1 = new Customer("Grace", "Taylor", "Address9", "MINI001", "2025-08-12", "2025-09-01");
        customer1.setBackDate("2025-08-12");
        
        Customer customer2 = new Customer("Henry", "Clark", "Address10", "MOTO002", "2025-08-09", "2025-09-01");
        customer2.setBackDate("2025-08-09");
        
        // Add cars and customers to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}