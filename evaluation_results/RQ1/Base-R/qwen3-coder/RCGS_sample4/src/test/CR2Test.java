import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR2Test {
    
    private RentalStore store;
    
    @Before
    public void setUp() {
        store = new RentalStore();
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // Test Case 1: Calculate total revenue from rentals with multiple rental records
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rental records
        Rental rental1 = new Rental();
        rental1.setCarPlate("ABC123");
        rental1.setLeasingTermDays(3);
        rental1.setBackDate(LocalDate.of(2025, 11, 12));
        
        Rental rental2 = new Rental();
        rental2.setCarPlate("XYZ789");
        rental2.setLeasingTermDays(2);
        rental2.setBackDate(LocalDate.of(2025, 11, 11));
        
        Rental rental3 = new Rental();
        rental3.setCarPlate("LMN456");
        rental3.setLeasingTermDays(1);
        rental3.setBackDate(LocalDate.of(2025, 11, 12));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: Calculate total revenue from rentals with no rental records
        
        // Verify there are no rentals
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Test Case 3: Calculate total revenue from rentals where multiple cars have the same daily price
        
        // Create cars with same daily price
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        
        // Create rental records
        Rental rental1 = new Rental();
        rental1.setCarPlate("CAR001");
        rental1.setLeasingTermDays(2);
        rental1.setBackDate(LocalDate.of(2025, 11, 13));
        
        Rental rental2 = new Rental();
        rental2.setCarPlate("CAR002");
        rental2.setLeasingTermDays(4);
        rental2.setBackDate(LocalDate.of(2025, 11, 15));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Test Case 4: Calculate total revenue from rentals with varied daily prices
        
        // Create cars with different daily prices
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rental records
        Rental rental1 = new Rental();
        rental1.setCarPlate("SED123");
        rental1.setLeasingTermDays(5);
        rental1.setBackDate(LocalDate.of(2025, 8, 13));
        
        Rental rental2 = new Rental();
        rental2.setCarPlate("SUV456");
        rental2.setLeasingTermDays(3);
        rental2.setBackDate(LocalDate.of(2025, 8, 13));
        
        Rental rental3 = new Rental();
        rental3.setCarPlate("TRK789");
        rental3.setLeasingTermDays(1);
        rental3.setBackDate(LocalDate.of(2025, 8, 9));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Test Case 5: Calculate total revenue from rentals with all rentals for only one day
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        
        // Create rental records
        Rental rental1 = new Rental();
        rental1.setCarPlate("MINI001");
        rental1.setLeasingTermDays(1);
        rental1.setBackDate(LocalDate.of(2025, 8, 12));
        
        Rental rental2 = new Rental();
        rental2.setCarPlate("MOTO002");
        rental2.setLeasingTermDays(1);
        rental2.setBackDate(LocalDate.of(2025, 8, 9));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}