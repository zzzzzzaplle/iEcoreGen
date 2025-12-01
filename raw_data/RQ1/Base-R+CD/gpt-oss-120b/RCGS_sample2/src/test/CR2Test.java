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
        // Input: Calculate total revenue from rentals with multiple rental records
        
        // SetUp: Create rental objects with specified parameters
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: "ABC123", "Toyota Camry", daily price: 100 CNY, rented for 3 days
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        rental1.setCar(car1);
        rental1.setTotalPrice(100.0 * 3); // 300 CNY
        rentals.add(rental1);
        
        // Rental 2: "XYZ789", "Honda Civic", daily price: 150 CNY, rented for 2 days
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        rental2.setCar(car2);
        rental2.setTotalPrice(150.0 * 2); // 300 CNY
        rentals.add(rental2);
        
        // Rental 3: "LMN456", "Ford Focus", daily price: 200 CNY, rented for 1 day
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        rental3.setCar(car3);
        rental3.setTotalPrice(200.0 * 1); // 200 CNY
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Calculate total revenue
        double actualRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        double expectedRevenue = 800.0;
        assertEquals("Total revenue should be 800 CNY for three rentals", expectedRevenue, actualRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: "No Rentals Calculation"
        // Input: Calculate total revenue from rentals with no rental records
        
        // SetUp: Verify there are no Rental objects added (store is empty by default)
        assertTrue("Rentals list should be empty", store.getRentals().isEmpty());
        
        // Calculate total revenue
        double actualRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        double expectedRevenue = 0.0;
        assertEquals("Total revenue should be 0 CNY when no rentals exist", expectedRevenue, actualRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Test Case 3: "Multiple Rentals with Same Daily Price"
        // Input: Calculate total revenue from rentals where multiple cars have the same daily price
        
        // SetUp: Create rental objects with same daily price
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: "CAR001", "Chevrolet Malibu", daily price: 120 CNY, rented for 2 days
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        rental1.setCar(car1);
        rental1.setTotalPrice(120.0 * 2); // 240 CNY
        rentals.add(rental1);
        
        // Rental 2: "CAR002", "Hyundai Elantra", daily price: 120 CNY, rented for 4 days
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        rental2.setCar(car2);
        rental2.setTotalPrice(120.0 * 4); // 480 CNY
        rentals.add(rental2);
        
        store.setRentals(rentals);
        
        // Calculate total revenue
        double actualRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        double expectedRevenue = 720.0;
        assertEquals("Total revenue should be 720 CNY for two rentals with same daily price", 
                    expectedRevenue, actualRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Test Case 4: "Mixed Prices Calculation"
        // Input: Calculate total revenue from rentals with varied daily prices
        
        // SetUp: Create rental objects with varied daily prices
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: "SED123", "Mazda 3", daily price: 90 CNY, rented for 5 days
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        rental1.setCar(car1);
        rental1.setTotalPrice(90.0 * 5); // 450 CNY
        rentals.add(rental1);
        
        // Rental 2: "SUV456", "Kia Sportage", daily price: 150 CNY, rented for 3 days
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        rental2.setCar(car2);
        rental2.setTotalPrice(150.0 * 3); // 450 CNY
        rentals.add(rental2);
        
        // Rental 3: "TRK789", "Ford F-150", daily price: 250 CNY, rented for 1 day
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        rental3.setCar(car3);
        rental3.setTotalPrice(250.0 * 1); // 250 CNY
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Calculate total revenue
        double actualRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        double expectedRevenue = 1150.0;
        assertEquals("Total revenue should be 1150 CNY for three rentals with mixed prices", 
                    expectedRevenue, actualRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Test Case 5: "One-Day Rental Calculation"
        // Input: Calculate total revenue from rentals with all rentals for only one day
        
        // SetUp: Create rental objects with one-day rentals
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: "MINI001", "Mini Cooper", daily price: 180 CNY, rented for 1 day
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        rental1.setCar(car1);
        rental1.setTotalPrice(180.0 * 1); // 180 CNY
        rentals.add(rental1);
        
        // Rental 2: "MOTO002", "Harley Davidson", daily price: 220 CNY, rented for 1 day
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        rental2.setCar(car2);
        rental2.setTotalPrice(220.0 * 1); // 220 CNY
        rentals.add(rental2);
        
        store.setRentals(rentals);
        
        // Calculate total revenue
        double actualRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        double expectedRevenue = 400.0;
        assertEquals("Total revenue should be 400 CNY for two one-day rentals", 
                    expectedRevenue, actualRevenue, 0.001);
    }
}