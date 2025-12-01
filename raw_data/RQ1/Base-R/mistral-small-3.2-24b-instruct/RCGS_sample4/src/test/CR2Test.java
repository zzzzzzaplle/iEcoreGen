import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    private CarGallery carGallery;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        carGallery = new CarGallery();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    public void testCase1_SingleRentalCalculation() {
        // Test Case 1: Calculate total revenue from rentals with multiple rental records
        // SetUp: Create a store instance and add Rental objects
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        
        Customer customer = new Customer("John", "Doe", "123 Main St");
        
        Rental rental1 = new Rental(car1, customer, 
            LocalDate.parse("2025-11-10", formatter), 
            LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-12", formatter));
        
        Rental rental2 = new Rental(car2, customer, 
            LocalDate.parse("2025-11-10", formatter), 
            LocalDate.parse("2025-11-13", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-11", formatter));
        
        Rental rental3 = new Rental(car3, customer, 
            LocalDate.parse("2025-11-12", formatter), 
            LocalDate.parse("2025-11-13", formatter));
        rental3.setBackDate(LocalDate.parse("2025-11-12", formatter));
        
        carGallery.addRental(rental1);
        carGallery.addRental(rental2);
        carGallery.addRental(rental3);
        
        // Calculate the total revenue
        double totalRevenue = carGallery.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }

    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: Calculate total revenue from rentals with no rental records
        // SetUp: Create a store instance (no rentals added)
        
        // Calculate the total revenue
        double totalRevenue = carGallery.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }

    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Test Case 3: Calculate total revenue from rentals where multiple cars have the same daily price
        // SetUp: Create a store instance and add Rental objects
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        
        Customer customer = new Customer("Jane", "Smith", "456 Oak Ave");
        
        Rental rental1 = new Rental(car1, customer, 
            LocalDate.parse("2025-11-12", formatter), 
            LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-13", formatter));
        
        Rental rental2 = new Rental(car2, customer, 
            LocalDate.parse("2025-11-12", formatter), 
            LocalDate.parse("2025-12-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-15", formatter));
        
        carGallery.addRental(rental1);
        carGallery.addRental(rental2);
        
        // Calculate the total revenue
        double totalRevenue = carGallery.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }

    @Test
    public void testCase4_MixedPricesCalculation() {
        // Test Case 4: Calculate total revenue from rentals with varied daily prices
        // SetUp: Create a store instance and add Rental objects
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        
        Customer customer = new Customer("Bob", "Johnson", "789 Pine St");
        
        Rental rental1 = new Rental(car1, customer, 
            LocalDate.parse("2025-08-09", formatter), 
            LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-13", formatter));
        
        Rental rental2 = new Rental(car2, customer, 
            LocalDate.parse("2025-08-11", formatter), 
            LocalDate.parse("2026-01-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-13", formatter));
        
        Rental rental3 = new Rental(car3, customer, 
            LocalDate.parse("2025-08-09", formatter), 
            LocalDate.parse("2025-09-01", formatter));
        rental3.setBackDate(LocalDate.parse("2025-08-09", formatter));
        
        carGallery.addRental(rental1);
        carGallery.addRental(rental2);
        carGallery.addRental(rental3);
        
        // Calculate the total revenue
        double totalRevenue = carGallery.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }

    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Test Case 5: Calculate total revenue from rentals with all rentals for only one day
        // SetUp: Create a store instance and add Rental objects
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        
        Customer customer = new Customer("Alice", "Brown", "321 Elm St");
        
        Rental rental1 = new Rental(car1, customer, 
            LocalDate.parse("2025-08-12", formatter), 
            LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-12", formatter));
        
        Rental rental2 = new Rental(car2, customer, 
            LocalDate.parse("2025-08-09", formatter), 
            LocalDate.parse("2025-09-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-09", formatter));
        
        carGallery.addRental(rental1);
        carGallery.addRental(rental2);
        
        // Calculate the total revenue
        double totalRevenue = carGallery.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}