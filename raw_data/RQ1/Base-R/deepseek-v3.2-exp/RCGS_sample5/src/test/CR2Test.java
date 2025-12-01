import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private CarRentalSystem carRentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        carRentalSystem = new CarRentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // Test Case 1: Single Rental Calculation
        // SetUp: Create a store instance and add rental records
        
        // Create and setup first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        rental1.setCar(car1);
        rental1.setStartDate(LocalDate.parse("2025-11-10", formatter));
        rental1.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-12", formatter));
        rental1.setTotalPrice(100.0 * 3); // 3 days rental
        
        // Create and setup second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        rental2.setCar(car2);
        rental2.setStartDate(LocalDate.parse("2025-11-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-11", formatter));
        rental2.setTotalPrice(150.0 * 2); // 2 days rental
        
        // Create and setup third rental
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        rental3.setCar(car3);
        rental3.setStartDate(LocalDate.parse("2025-11-12", formatter));
        rental3.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental3.setBackDate(LocalDate.parse("2025-11-12", formatter));
        rental3.setTotalPrice(200.0 * 1); // 1 day rental
        
        // Add rentals to the system
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        carRentalSystem.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = carRentalSystem.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: No Rentals Calculation
        // SetUp: Create a store instance with no rental records
        
        // Verify there are no Rental objects added (system starts with empty rentals list)
        assertTrue(carRentalSystem.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = carRentalSystem.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Test Case 3: Multiple Rentals with Same Daily Price
        // SetUp: Create a store instance and add rental records with same daily price
        
        // Create and setup first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        rental1.setCar(car1);
        rental1.setStartDate(LocalDate.parse("2025-11-12", formatter));
        rental1.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-13", formatter));
        rental1.setTotalPrice(120.0 * 2); // 2 days rental
        
        // Create and setup second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        rental2.setCar(car2);
        rental2.setStartDate(LocalDate.parse("2025-11-12", formatter));
        rental2.setDueDate(LocalDate.parse("2025-12-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-15", formatter));
        rental2.setTotalPrice(120.0 * 4); // 4 days rental
        
        // Add rentals to the system
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        carRentalSystem.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = carRentalSystem.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Test Case 4: Mixed Prices Calculation
        // SetUp: Create a store instance and add rental records with varied daily prices
        
        // Create and setup first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        rental1.setCar(car1);
        rental1.setStartDate(LocalDate.parse("2025-08-09", formatter));
        rental1.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-13", formatter));
        rental1.setTotalPrice(90.0 * 5); // 5 days rental
        
        // Create and setup second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        rental2.setCar(car2);
        rental2.setStartDate(LocalDate.parse("2025-08-11", formatter));
        rental2.setDueDate(LocalDate.parse("2026-01-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-13", formatter));
        rental2.setTotalPrice(150.0 * 3); // 3 days rental
        
        // Create and setup third rental
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        rental3.setCar(car3);
        rental3.setStartDate(LocalDate.parse("2025-08-09", formatter));
        rental3.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental3.setBackDate(LocalDate.parse("2025-08-09", formatter));
        rental3.setTotalPrice(250.0 * 1); // 1 day rental
        
        // Add rentals to the system
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        carRentalSystem.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = carRentalSystem.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Test Case 5: One-Day Rental Calculation
        // SetUp: Create a store instance and add rental records with one-day rentals
        
        // Create and setup first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        rental1.setCar(car1);
        rental1.setStartDate(LocalDate.parse("2025-08-12", formatter));
        rental1.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-12", formatter));
        rental1.setTotalPrice(180.0 * 1); // 1 day rental
        
        // Create and setup second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        rental2.setCar(car2);
        rental2.setStartDate(LocalDate.parse("2025-08-09", formatter));
        rental2.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-09", formatter));
        rental2.setTotalPrice(220.0 * 1); // 1 day rental
        
        // Add rentals to the system
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        carRentalSystem.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = carRentalSystem.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}