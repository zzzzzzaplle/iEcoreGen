import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR2Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() throws Exception {
        // Test Case 1: "Single Rental Calculation"
        // Input: Calculate total revenue from rentals with multiple rental records.
        
        // SetUp:
        // 1. Create a store instance (done in @Before)
        // 2. Add Rental objects with specified parameters
        
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-04 10:00:00");
        Date backDate = dateFormat.parse("2024-01-04 10:00:00");
        
        // First rental: Toyota Camry, 100 CNY/day, 3 days
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Customer customer1 = new Customer("John", "Doe", "123 Main St", "ABC123");
        Rental rental1 = new Rental(rentalDate, dueDate, backDate, 300.0, "Standard", car1, customer1);
        store.addRental(rental1);
        
        // Second rental: Honda Civic, 150 CNY/day, 2 days
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak Ave", "XYZ789");
        Rental rental2 = new Rental(rentalDate, dueDate, backDate, 300.0, "Standard", car2, customer2);
        store.addRental(rental2);
        
        // Third rental: Ford Focus, 200 CNY/day, 1 day
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        Customer customer3 = new Customer("Bob", "Johnson", "789 Pine Rd", "LMN456");
        Rental rental3 = new Rental(rentalDate, dueDate, backDate, 200.0, "Standard", car3, customer3);
        store.addRental(rental3);
        
        // 5. Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: "No Rentals Calculation"
        // Input: Calculate total revenue from rentals with no rental records.
        
        // SetUp:
        // 1. Create a store instance (done in @Before)
        // 2. Verify there are no Rental objects added (store is empty by default)
        // 3. Calculate the total revenue
        
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws Exception {
        // Test Case 3: "Multiple Rentals with Same Daily Price"
        // Input: Calculate total revenue from rentals where multiple cars have the same daily price.
        
        // SetUp:
        // 1. Create a store instance (done in @Before)
        
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-03 10:00:00");
        Date backDate = dateFormat.parse("2024-01-03 10:00:00");
        
        // First rental: Chevrolet Malibu, 120 CNY/day, 2 days
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Customer customer1 = new Customer("Alice", "Brown", "111 Elm St", "CAR001");
        Rental rental1 = new Rental(rentalDate, dueDate, backDate, 240.0, "Standard", car1, customer1);
        store.addRental(rental1);
        
        // Second rental: Hyundai Elantra, 120 CNY/day, 4 days
        Date dueDate2 = dateFormat.parse("2024-01-05 10:00:00");
        Date backDate2 = dateFormat.parse("2024-01-05 10:00:00");
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        Customer customer2 = new Customer("Charlie", "Wilson", "222 Maple Ave", "CAR002");
        Rental rental2 = new Rental(rentalDate, dueDate2, backDate2, 480.0, "Standard", car2, customer2);
        store.addRental(rental2);
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws Exception {
        // Test Case 4: "Mixed Prices Calculation"
        // Input: Calculate total revenue from rentals with varied daily prices.
        
        // SetUp:
        // 1. Create a store instance (done in @Before)
        
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        
        // First rental: Mazda 3, 90 CNY/day, 5 days
        Date dueDate1 = dateFormat.parse("2024-01-06 10:00:00");
        Date backDate1 = dateFormat.parse("2024-01-06 10:00:00");
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Customer customer1 = new Customer("David", "Lee", "333 Cedar Ln", "SED123");
        Rental rental1 = new Rental(rentalDate, dueDate1, backDate1, 450.0, "Standard", car1, customer1);
        store.addRental(rental1);
        
        // Second rental: Kia Sportage, 150 CNY/day, 3 days
        Date dueDate2 = dateFormat.parse("2024-01-04 10:00:00");
        Date backDate2 = dateFormat.parse("2024-01-04 10:00:00");
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Customer customer2 = new Customer("Emma", "Garcia", "444 Birch St", "SUV456");
        Rental rental2 = new Rental(rentalDate, dueDate2, backDate2, 450.0, "Standard", car2, customer2);
        store.addRental(rental2);
        
        // Third rental: Ford F-150, 250 CNY/day, 1 day
        Date dueDate3 = dateFormat.parse("2024-01-02 10:00:00");
        Date backDate3 = dateFormat.parse("2024-01-02 10:00:00");
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        Customer customer3 = new Customer("Frank", "Miller", "555 Spruce Dr", "TRK789");
        Rental rental3 = new Rental(rentalDate, dueDate3, backDate3, 250.0, "Standard", car3, customer3);
        store.addRental(rental3);
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws Exception {
        // Test Case 5: "One-Day Rental Calculation"
        // Input: Calculate total revenue from rentals with all rentals for only one day.
        
        // SetUp:
        // 1. Create a store instance (done in @Before)
        
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-02 10:00:00");
        Date backDate = dateFormat.parse("2024-01-02 10:00:00");
        
        // First rental: Mini Cooper, 180 CNY/day, 1 day
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Customer customer1 = new Customer("Grace", "Taylor", "666 Willow Way", "MINI001");
        Rental rental1 = new Rental(rentalDate, dueDate, backDate, 180.0, "Standard", car1, customer1);
        store.addRental(rental1);
        
        // Second rental: Harley Davidson, 220 CNY/day, 1 day
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        Customer customer2 = new Customer("Henry", "Clark", "777 Aspen Ct", "MOTO002");
        Rental rental2 = new Rental(rentalDate, dueDate, backDate, 220.0, "Standard", car2, customer2);
        store.addRental(rental2);
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}