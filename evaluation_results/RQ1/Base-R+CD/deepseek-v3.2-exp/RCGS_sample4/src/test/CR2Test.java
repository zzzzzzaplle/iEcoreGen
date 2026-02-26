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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() throws Exception {
        // SetUp: Create a store instance and add rental objects
        Date backDate1 = dateFormat.parse("2025-11-12");
        Date dueDate1 = dateFormat.parse("2025-11-13");
        Date rentalDate1 = dateFormat.parse("2025-11-10");
        
        Date backDate2 = dateFormat.parse("2025-11-11");
        Date dueDate2 = dateFormat.parse("2025-11-13");
        Date rentalDate2 = dateFormat.parse("2025-11-10");
        
        Date backDate3 = dateFormat.parse("2025-11-12");
        Date dueDate3 = dateFormat.parse("2025-11-13");
        Date rentalDate3 = dateFormat.parse("2025-11-12");
        
        // Create and setup first rental
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setRentalDate(rentalDate1);
        rental1.setDueDate(dueDate1);
        rental1.setBackDate(backDate1);
        rental1.setTotalPrice(100.0 * 3); // 3 days
        
        // Create and setup second rental
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setRentalDate(rentalDate2);
        rental2.setDueDate(dueDate2);
        rental2.setBackDate(backDate2);
        rental2.setTotalPrice(150.0 * 2); // 2 days
        
        // Create and setup third rental
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setRentalDate(rentalDate3);
        rental3.setDueDate(dueDate3);
        rental3.setBackDate(backDate3);
        rental3.setTotalPrice(200.0 * 1); // 1 day
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // SetUp: Create a store instance with no rentals
        // Verify there are no Rental objects added by checking the rentals list is empty
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws Exception {
        // SetUp: Create a store instance and add rental objects with same daily price
        Date backDate1 = dateFormat.parse("2025-11-13");
        Date dueDate1 = dateFormat.parse("2025-11-13");
        Date rentalDate1 = dateFormat.parse("2025-11-12");
        
        Date backDate2 = dateFormat.parse("2025-11-15");
        Date dueDate2 = dateFormat.parse("2025-12-01");
        Date rentalDate2 = dateFormat.parse("2025-11-12");
        
        // Create and setup first rental
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setRentalDate(rentalDate1);
        rental1.setDueDate(dueDate1);
        rental1.setBackDate(backDate1);
        rental1.setTotalPrice(120.0 * 2); // 2 days
        
        // Create and setup second rental
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setRentalDate(rentalDate2);
        rental2.setDueDate(dueDate2);
        rental2.setBackDate(backDate2);
        rental2.setTotalPrice(120.0 * 4); // 4 days
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws Exception {
        // SetUp: Create a store instance and add rental objects with varied daily prices
        Date backDate1 = dateFormat.parse("2025-08-13");
        Date dueDate1 = dateFormat.parse("2025-09-01");
        Date rentalDate1 = dateFormat.parse("2025-08-09");
        
        Date backDate2 = dateFormat.parse("2025-08-13");
        Date dueDate2 = dateFormat.parse("2026-01-01");
        Date rentalDate2 = dateFormat.parse("2025-08-11");
        
        Date backDate3 = dateFormat.parse("2025-08-09");
        Date dueDate3 = dateFormat.parse("2025-09-01");
        Date rentalDate3 = dateFormat.parse("2025-08-09");
        
        // Create and setup first rental
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setRentalDate(rentalDate1);
        rental1.setDueDate(dueDate1);
        rental1.setBackDate(backDate1);
        rental1.setTotalPrice(90.0 * 5); // 5 days
        
        // Create and setup second rental
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setRentalDate(rentalDate2);
        rental2.setDueDate(dueDate2);
        rental2.setBackDate(backDate2);
        rental2.setTotalPrice(150.0 * 3); // 3 days
        
        // Create and setup third rental
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setRentalDate(rentalDate3);
        rental3.setDueDate(dueDate3);
        rental3.setBackDate(backDate3);
        rental3.setTotalPrice(250.0 * 1); // 1 day
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws Exception {
        // SetUp: Create a store instance and add rental objects with all rentals for one day
        Date backDate1 = dateFormat.parse("2025-08-12");
        Date dueDate1 = dateFormat.parse("2025-09-01");
        Date rentalDate1 = dateFormat.parse("2025-08-12");
        
        Date backDate2 = dateFormat.parse("2025-08-09");
        Date dueDate2 = dateFormat.parse("2025-09-01");
        Date rentalDate2 = dateFormat.parse("2025-08-09");
        
        // Create and setup first rental
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setRentalDate(rentalDate1);
        rental1.setDueDate(dueDate1);
        rental1.setBackDate(backDate1);
        rental1.setTotalPrice(180.0 * 1); // 1 day
        
        // Create and setup second rental
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setRentalDate(rentalDate2);
        rental2.setDueDate(dueDate2);
        rental2.setBackDate(backDate2);
        rental2.setTotalPrice(220.0 * 1); // 1 day
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}