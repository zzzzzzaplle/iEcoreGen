import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        // SetUp: Create store instance and add rental records
        store = new Store();
        
        // Create first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2025-11-10 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental1.setTotalPrice(300.0); // 100 * 3 days
        
        // Create second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2025-11-10 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-11-11 00:00:00"));
        rental2.setTotalPrice(300.0); // 150 * 2 days
        
        // Create third rental
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental3.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental3.setBackDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental3.setTotalPrice(200.0); // 200 * 1 day
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // SetUp: Create store instance with no rentals
        store = new Store();
        
        // Verify there are no Rental objects added
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws Exception {
        // SetUp: Create store instance
        store = new Store();
        
        // Create first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental1.setTotalPrice(240.0); // 120 * 2 days
        
        // Create second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-12-01 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-11-15 00:00:00"));
        rental2.setTotalPrice(480.0); // 120 * 4 days
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws Exception {
        // SetUp: Create store instance
        store = new Store();
        
        // Create first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-08-13 00:00:00"));
        rental1.setTotalPrice(450.0); // 90 * 5 days
        
        // Create second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2025-08-11 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2026-01-01 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-08-13 00:00:00"));
        rental2.setTotalPrice(450.0); // 150 * 3 days
        
        // Create third rental
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental3.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental3.setBackDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental3.setTotalPrice(250.0); // 250 * 1 day
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws Exception {
        // SetUp: Create store instance
        store = new Store();
        
        // Create first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2025-08-12 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-08-12 00:00:00"));
        rental1.setTotalPrice(180.0); // 180 * 1 day
        
        // Create second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental2.setTotalPrice(220.0); // 220 * 1 day
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}