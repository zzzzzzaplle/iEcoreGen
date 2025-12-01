import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() throws ParseException {
        // Test Case 1: Single Rental Calculation
        // SetUp: Create store instance and add multiple rental records
        
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        
        // Create customers
        Customer customer1 = new Customer("John", "Doe", "123 Main St", "ABC123");
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak St", "XYZ789");
        Customer customer3 = new Customer("Bob", "Johnson", "789 Pine St", "LMN456");
        
        // Create rentals with specified dates and total prices
        Rental rental1 = new Rental(
            dateFormat.parse("2025-11-10"),
            dateFormat.parse("2025-11-13"),
            dateFormat.parse("2025-11-12"),
            300.0, // 100 * 3 days
            "Standard lease",
            car1,
            customer1
        );
        
        Rental rental2 = new Rental(
            dateFormat.parse("2025-11-10"),
            dateFormat.parse("2025-11-13"),
            dateFormat.parse("2025-11-11"),
            300.0, // 150 * 2 days
            "Standard lease",
            car2,
            customer2
        );
        
        Rental rental3 = new Rental(
            dateFormat.parse("2025-11-12"),
            dateFormat.parse("2025-11-13"),
            dateFormat.parse("2025-11-12"),
            200.0, // 200 * 1 day
            "Standard lease",
            car3,
            customer3
        );
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: No Rentals Calculation
        // SetUp: Create store instance with no rental records
        
        // Verify there are no Rental objects added (store should be empty by default)
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws ParseException {
        // Test Case 3: Multiple Rentals with Same Daily Price
        // SetUp: Create store instance and add rentals with same daily price
        
        // Create cars with same daily price
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        
        // Create customers
        Customer customer1 = new Customer("Alice", "Brown", "111 Elm St", "CAR001");
        Customer customer2 = new Customer("Charlie", "Wilson", "222 Maple St", "CAR002");
        
        // Create rentals with specified dates and total prices
        Rental rental1 = new Rental(
            dateFormat.parse("2025-11-12"),
            dateFormat.parse("2025-11-13"),
            dateFormat.parse("2025-11-13"),
            240.0, // 120 * 2 days
            "Standard lease",
            car1,
            customer1
        );
        
        Rental rental2 = new Rental(
            dateFormat.parse("2025-11-12"),
            dateFormat.parse("2025-12-01"),
            dateFormat.parse("2025-11-15"),
            480.0, // 120 * 4 days
            "Standard lease",
            car2,
            customer2
        );
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws ParseException {
        // Test Case 4: Mixed Prices Calculation
        // SetUp: Create store instance and add rentals with varied daily prices
        
        // Create cars with different daily prices
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        
        // Create customers
        Customer customer1 = new Customer("David", "Lee", "333 Cedar St", "SED123");
        Customer customer2 = new Customer("Emma", "Davis", "444 Birch St", "SUV456");
        Customer customer3 = new Customer("Frank", "Miller", "555 Spruce St", "TRK789");
        
        // Create rentals with specified dates and total prices
        Rental rental1 = new Rental(
            dateFormat.parse("2025-08-09"),
            dateFormat.parse("2025-09-01"),
            dateFormat.parse("2025-08-13"),
            450.0, // 90 * 5 days
            "Standard lease",
            car1,
            customer1
        );
        
        Rental rental2 = new Rental(
            dateFormat.parse("2025-08-11"),
            dateFormat.parse("2026-01-01"),
            dateFormat.parse("2025-08-13"),
            450.0, // 150 * 3 days
            "Standard lease",
            car2,
            customer2
        );
        
        Rental rental3 = new Rental(
            dateFormat.parse("2025-08-09"),
            dateFormat.parse("2025-09-01"),
            dateFormat.parse("2025-08-09"),
            250.0, // 250 * 1 day
            "Standard lease",
            car3,
            customer3
        );
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws ParseException {
        // Test Case 5: One-Day Rental Calculation
        // SetUp: Create store instance and add rentals for only one day
        
        // Create cars
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        
        // Create customers
        Customer customer1 = new Customer("Grace", "Taylor", "666 Willow St", "MINI001");
        Customer customer2 = new Customer("Henry", "Anderson", "777 Poplar St", "MOTO002");
        
        // Create rentals with specified dates and total prices
        Rental rental1 = new Rental(
            dateFormat.parse("2025-08-12"),
            dateFormat.parse("2025-09-01"),
            dateFormat.parse("2025-08-12"),
            180.0, // 180 * 1 day
            "Standard lease",
            car1,
            customer1
        );
        
        Rental rental2 = new Rental(
            dateFormat.parse("2025-08-09"),
            dateFormat.parse("2025-09-01"),
            dateFormat.parse("2025-08-09"),
            220.0, // 220 * 1 day
            "Standard lease",
            car2,
            customer2
        );
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}