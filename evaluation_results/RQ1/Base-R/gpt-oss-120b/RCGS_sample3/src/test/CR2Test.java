import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // SetUp: Create a store instance and add rental records
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        
        // Create customer (same customer for all rentals)
        Customer customer = new Customer("John", "Doe", "123 Main St");
        
        // Create rentals with specified dates
        Rental rental1 = new Rental(car1, customer, 
                                   LocalDate.of(2025, 11, 10),  // rent date
                                   LocalDate.of(2025, 11, 13),  // due date
                                   LocalDate.of(2025, 11, 12),  // back date
                                   300.0);  // 100 * 3 days
        
        Rental rental2 = new Rental(car2, customer,
                                   LocalDate.of(2025, 11, 10),
                                   LocalDate.of(2025, 11, 13),
                                   LocalDate.of(2025, 11, 11),
                                   300.0);  // 150 * 2 days
        
        Rental rental3 = new Rental(car3, customer,
                                   LocalDate.of(2025, 11, 12),
                                   LocalDate.of(2025, 11, 13),
                                   LocalDate.of(2025, 11, 12),
                                   200.0);  // 200 * 1 day
        
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
        // SetUp: Create a store instance with no rentals
        // Verify there are no rental objects added
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // SetUp: Create a store instance and add rental records with same daily price
        // Create cars with same daily price
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        
        // Create customer
        Customer customer = new Customer("Jane", "Smith", "456 Oak Ave");
        
        // Create rentals
        Rental rental1 = new Rental(car1, customer,
                                   LocalDate.of(2025, 11, 12),
                                   LocalDate.of(2025, 11, 13),
                                   LocalDate.of(2025, 11, 13),
                                   240.0);  // 120 * 2 days
        
        Rental rental2 = new Rental(car2, customer,
                                   LocalDate.of(2025, 11, 12),
                                   LocalDate.of(2025, 12, 1),
                                   LocalDate.of(2025, 11, 15),
                                   480.0);  // 120 * 4 days
        
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
        // SetUp: Create a store instance and add rental records with varied daily prices
        // Create cars with different daily prices
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        
        // Create customer
        Customer customer = new Customer("Bob", "Johnson", "789 Pine St");
        
        // Create rentals
        Rental rental1 = new Rental(car1, customer,
                                   LocalDate.of(2025, 8, 9),
                                   LocalDate.of(2025, 9, 1),
                                   LocalDate.of(2025, 8, 13),
                                   450.0);  // 90 * 5 days
        
        Rental rental2 = new Rental(car2, customer,
                                   LocalDate.of(2025, 8, 11),
                                   LocalDate.of(2026, 1, 1),
                                   LocalDate.of(2025, 8, 13),
                                   450.0);  // 150 * 3 days
        
        Rental rental3 = new Rental(car3, customer,
                                   LocalDate.of(2025, 8, 9),
                                   LocalDate.of(2025, 9, 1),
                                   LocalDate.of(2025, 8, 9),
                                   250.0);  // 250 * 1 day
        
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
        // SetUp: Create a store instance and add rentals for only one day
        // Create cars
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        
        // Create customer
        Customer customer = new Customer("Alice", "Brown", "321 Elm St");
        
        // Create one-day rentals
        Rental rental1 = new Rental(car1, customer,
                                   LocalDate.of(2025, 8, 12),
                                   LocalDate.of(2025, 9, 1),
                                   LocalDate.of(2025, 8, 12),
                                   180.0);  // 180 * 1 day
        
        Rental rental2 = new Rental(car2, customer,
                                   LocalDate.of(2025, 8, 9),
                                   LocalDate.of(2025, 9, 1),
                                   LocalDate.of(2025, 8, 9),
                                   220.0);  // 220 * 1 day
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}