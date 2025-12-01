import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // Test Case 1: "Single Rental Calculation"
        // SetUp: Create a store instance and add multiple rental records
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        
        // Create customers
        Customer customer1 = new Customer("John", "Doe", "Address 1");
        Customer customer2 = new Customer("Jane", "Smith", "Address 2");
        Customer customer3 = new Customer("Bob", "Johnson", "Address 3");
        
        // Create rentals with specified dates
        Rental rental1 = new Rental(car1, customer1, 
            LocalDate.parse("2025-11-10", formatter), 
            LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-12", formatter));
        
        Rental rental2 = new Rental(car2, customer2, 
            LocalDate.parse("2025-11-10", formatter), 
            LocalDate.parse("2025-11-13", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-11", formatter));
        
        Rental rental3 = new Rental(car3, customer3, 
            LocalDate.parse("2025-11-12", formatter), 
            LocalDate.parse("2025-11-13", formatter));
        rental3.setBackDate(LocalDate.parse("2025-11-12", formatter));
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, result, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: "No Rentals Calculation"
        // SetUp: Create a store instance with no rental records
        
        // Verify there are no Rental objects added
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Test Case 3: "Multiple Rentals with Same Daily Price"
        // SetUp: Create a store instance and add rentals with same daily price
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Create cars with same daily price
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        
        // Create customers
        Customer customer1 = new Customer("Alice", "Brown", "Address 4");
        Customer customer2 = new Customer("Charlie", "Wilson", "Address 5");
        
        // Create rentals with specified dates
        Rental rental1 = new Rental(car1, customer1, 
            LocalDate.parse("2025-11-12", formatter), 
            LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-13", formatter));
        
        Rental rental2 = new Rental(car2, customer2, 
            LocalDate.parse("2025-11-12", formatter), 
            LocalDate.parse("2025-12-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-15", formatter));
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Test Case 4: "Mixed Prices Calculation"
        // SetUp: Create a store instance and add rentals with varied daily prices
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Create cars with different daily prices
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        
        // Create customers
        Customer customer1 = new Customer("David", "Lee", "Address 6");
        Customer customer2 = new Customer("Emma", "Garcia", "Address 7");
        Customer customer3 = new Customer("Frank", "Miller", "Address 8");
        
        // Create rentals with specified dates
        Rental rental1 = new Rental(car1, customer1, 
            LocalDate.parse("2025-08-09", formatter), 
            LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-13", formatter));
        
        Rental rental2 = new Rental(car2, customer2, 
            LocalDate.parse("2025-08-11", formatter), 
            LocalDate.parse("2026-01-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-13", formatter));
        
        Rental rental3 = new Rental(car3, customer3, 
            LocalDate.parse("2025-08-09", formatter), 
            LocalDate.parse("2025-09-01", formatter));
        rental3.setBackDate(LocalDate.parse("2025-08-09", formatter));
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, result, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Test Case 5: "One-Day Rental Calculation"
        // SetUp: Create a store instance and add rentals for only one day
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Create cars
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        
        // Create customers
        Customer customer1 = new Customer("Grace", "Taylor", "Address 9");
        Customer customer2 = new Customer("Henry", "Adams", "Address 10");
        
        // Create one-day rentals
        Rental rental1 = new Rental(car1, customer1, 
            LocalDate.parse("2025-08-12", formatter), 
            LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-12", formatter));
        
        Rental rental2 = new Rental(car2, customer2, 
            LocalDate.parse("2025-08-09", formatter), 
            LocalDate.parse("2025-09-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-09", formatter));
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, result, 0.001);
    }
}