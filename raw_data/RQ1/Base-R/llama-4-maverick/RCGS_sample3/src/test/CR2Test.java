import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // Test Case 1: Calculate total revenue from rentals with multiple rental records
        
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customers with rental information
        Customer customer1 = new Customer("John", "Doe", "Address1", "ABC123", LocalDate.of(2025, 11, 13));
        customer1.setBackDate(LocalDate.of(2025, 11, 12));
        
        Customer customer2 = new Customer("Jane", "Smith", "Address2", "XYZ789", LocalDate.of(2025, 11, 13));
        customer2.setBackDate(LocalDate.of(2025, 11, 11));
        
        Customer customer3 = new Customer("Bob", "Johnson", "Address3", "LMN456", LocalDate.of(2025, 11, 13));
        customer3.setBackDate(LocalDate.of(2025, 11, 12));
        
        // Add customers to store
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        store.addCustomer(customer3);
        
        // Mark cars as rented
        car1.setRented(true);
        car2.setRented(true);
        car3.setRented(true);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: Calculate total revenue from rentals with no rental records
        
        // Verify there are no Rental objects added (store is empty after setup)
        
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
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        
        // Create customers with rental information
        Customer customer1 = new Customer("Alice", "Brown", "Address1", "CAR001", LocalDate.of(2025, 11, 13));
        customer1.setBackDate(LocalDate.of(2025, 11, 13));
        
        Customer customer2 = new Customer("Charlie", "Wilson", "Address2", "CAR002", LocalDate.of(2025, 12, 1));
        customer2.setBackDate(LocalDate.of(2025, 11, 15));
        
        // Add customers to store
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        
        // Mark cars as rented
        car1.setRented(true);
        car2.setRented(true);
        
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
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customers with rental information
        Customer customer1 = new Customer("David", "Lee", "Address1", "SED123", LocalDate.of(2025, 9, 1));
        customer1.setBackDate(LocalDate.of(2025, 8, 13));
        
        Customer customer2 = new Customer("Emma", "Taylor", "Address2", "SUV456", LocalDate.of(2026, 1, 1));
        customer2.setBackDate(LocalDate.of(2025, 8, 13));
        
        Customer customer3 = new Customer("Frank", "Miller", "Address3", "TRK789", LocalDate.of(2025, 9, 1));
        customer3.setBackDate(LocalDate.of(2025, 8, 9));
        
        // Add customers to store
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        store.addCustomer(customer3);
        
        // Mark cars as rented
        car1.setRented(true);
        car2.setRented(true);
        car3.setRented(true);
        
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
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        
        // Create customers with rental information
        Customer customer1 = new Customer("Grace", "Harris", "Address1", "MINI001", LocalDate.of(2025, 9, 1));
        customer1.setBackDate(LocalDate.of(2025, 8, 12));
        
        Customer customer2 = new Customer("Henry", "Clark", "Address2", "MOTO002", LocalDate.of(2025, 9, 1));
        customer2.setBackDate(LocalDate.of(2025, 8, 9));
        
        // Add customers to store
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        
        // Mark cars as rented
        car1.setRented(true);
        car2.setRented(true);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}