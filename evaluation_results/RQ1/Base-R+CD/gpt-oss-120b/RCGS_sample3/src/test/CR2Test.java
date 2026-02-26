import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // Test Case 1: "Single Rental Calculation"
        // SetUp: Create rental records with different cars and rental periods
        // Expected Output: Total revenue = 800 CNY
        
        // Create first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        rental1.setCar(car1);
        rental1.setTotalPrice(300.0); // 100 * 3 days
        
        // Create second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        rental2.setCar(car2);
        rental2.setTotalPrice(300.0); // 150 * 2 days
        
        // Create third rental
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        rental3.setCar(car3);
        rental3.setTotalPrice(200.0); // 200 * 1 day
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: "No Rentals Calculation"
        // SetUp: Store with no rental records
        // Expected Output: Total revenue = 0 CNY
        
        // Verify there are no rentals
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Test Case 3: "Multiple Rentals with Same Daily Price"
        // SetUp: Two rentals with same daily price but different rental periods
        // Expected Output: Total revenue = 720 CNY
        
        // Create first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        rental1.setCar(car1);
        rental1.setTotalPrice(240.0); // 120 * 2 days
        
        // Create second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        rental2.setCar(car2);
        rental2.setTotalPrice(480.0); // 120 * 4 days
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Test Case 4: "Mixed Prices Calculation"
        // SetUp: Three rentals with varied daily prices and rental periods
        // Expected Output: Total revenue = 1150 CNY
        
        // Create first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        rental1.setCar(car1);
        rental1.setTotalPrice(450.0); // 90 * 5 days
        
        // Create second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        rental2.setCar(car2);
        rental2.setTotalPrice(450.0); // 150 * 3 days
        
        // Create third rental
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        rental3.setCar(car3);
        rental3.setTotalPrice(250.0); // 250 * 1 day
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Test Case 5: "One-Day Rental Calculation"
        // SetUp: Two rentals both for only one day
        // Expected Output: Total revenue = 400 CNY
        
        // Create first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        rental1.setCar(car1);
        rental1.setTotalPrice(180.0); // 180 * 1 day
        
        // Create second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        rental2.setCar(car2);
        rental2.setTotalPrice(220.0); // 220 * 1 day
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(400.0, totalRevenue, 0.001);
    }
}