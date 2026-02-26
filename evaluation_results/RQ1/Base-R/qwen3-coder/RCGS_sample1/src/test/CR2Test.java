import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private RentalStore store;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        store = new RentalStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental records
        Rental rental1 = new Rental();
        rental1.setCarPlate("ABC123");
        rental1.setCustomerName("John");
        rental1.setCustomerSurname("Doe");
        rental1.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-12", formatter));
        rental1.setLeasingTermDays(3); // From 2025-11-10 to 2025-11-12 = 3 days
        
        Rental rental2 = new Rental();
        rental2.setCarPlate("XYZ789");
        rental2.setCustomerName("Jane");
        rental2.setCustomerSurname("Smith");
        rental2.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-11", formatter));
        rental2.setLeasingTermDays(2); // From 2025-11-10 to 2025-11-11 = 2 days
        
        Rental rental3 = new Rental();
        rental3.setCarPlate("LMN456");
        rental3.setCustomerName("Bob");
        rental3.setCustomerSurname("Johnson");
        rental3.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental3.setBackDate(LocalDate.parse("2025-11-12", formatter));
        rental3.setLeasingTermDays(1); // From 2025-11-12 to 2025-11-12 = 1 day
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: Calculate total revenue from rentals with no rental records
        
        // Create a store instance (already done in setUp)
        // Verify there are no Rental objects added (store starts empty)
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Test Case 3: Calculate total revenue from rentals where multiple cars have the same daily price
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        
        // Create rental records
        Rental rental1 = new Rental();
        rental1.setCarPlate("CAR001");
        rental1.setCustomerName("Alice");
        rental1.setCustomerSurname("Brown");
        rental1.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-13", formatter));
        rental1.setLeasingTermDays(2); // From 2025-11-12 to 2025-11-13 = 2 days
        
        Rental rental2 = new Rental();
        rental2.setCarPlate("CAR002");
        rental2.setCustomerName("Charlie");
        rental2.setCustomerSurname("Wilson");
        rental2.setDueDate(LocalDate.parse("2025-12-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-15", formatter));
        rental2.setLeasingTermDays(4); // From 2025-11-12 to 2025-11-15 = 4 days
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Test Case 4: Calculate total revenue from rentals with varied daily prices
        
        // Create cars
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
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental records
        Rental rental1 = new Rental();
        rental1.setCarPlate("SED123");
        rental1.setCustomerName("David");
        rental1.setCustomerSurname("Lee");
        rental1.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-13", formatter));
        rental1.setLeasingTermDays(5); // From 2025-08-09 to 2025-08-13 = 5 days
        
        Rental rental2 = new Rental();
        rental2.setCarPlate("SUV456");
        rental2.setCustomerName("Emma");
        rental2.setCustomerSurname("Davis");
        rental2.setDueDate(LocalDate.parse("2026-01-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-13", formatter));
        rental2.setLeasingTermDays(3); // From 2025-08-11 to 2025-08-13 = 3 days
        
        Rental rental3 = new Rental();
        rental3.setCarPlate("TRK789");
        rental3.setCustomerName("Frank");
        rental3.setCustomerSurname("Miller");
        rental3.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental3.setBackDate(LocalDate.parse("2025-08-09", formatter));
        rental3.setLeasingTermDays(1); // From 2025-08-09 to 2025-08-09 = 1 day
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
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
        store.addCar(car1);
        store.addCar(car2);
        
        // Create rental records
        Rental rental1 = new Rental();
        rental1.setCarPlate("MINI001");
        rental1.setCustomerName("Grace");
        rental1.setCustomerSurname("Taylor");
        rental1.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-12", formatter));
        rental1.setLeasingTermDays(1); // From 2025-08-12 to 2025-08-12 = 1 day
        
        Rental rental2 = new Rental();
        rental2.setCarPlate("MOTO002");
        rental2.setCustomerName("Henry");
        rental2.setCustomerSurname("Clark");
        rental2.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-09", formatter));
        rental2.setLeasingTermDays(1); // From 2025-08-09 to 2025-08-09 = 1 day
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}