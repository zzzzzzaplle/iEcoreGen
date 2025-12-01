import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // SetUp: Create a store instance and add rental records
        store = new CarRentalStore();
        
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        
        // Create a customer (customer details not specified, using default)
        Customer customer = new Customer("John", "Doe", "123 Main St");
        
        // Create rentals with specified dates
        Rental rental1 = new Rental(customer, car1, 
            LocalDate.of(2025, 11, 10), 
            LocalDate.of(2025, 11, 13));
        rental1.setBackDate(LocalDate.of(2025, 11, 12)); // 3 days rental
        
        Rental rental2 = new Rental(customer, car2, 
            LocalDate.of(2025, 11, 10), 
            LocalDate.of(2025, 11, 13));
        rental2.setBackDate(LocalDate.of(2025, 11, 11)); // 2 days rental
        
        Rental rental3 = new Rental(customer, car3, 
            LocalDate.of(2025, 11, 12), 
            LocalDate.of(2025, 11, 13));
        rental3.setBackDate(LocalDate.of(2025, 11, 12)); // 1 day rental
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double actualRevenue = store.calculateTotalRevenue();
        
        // Expected: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        double expectedRevenue = 800.0;
        
        // Verify result with delta for floating point comparison
        assertEquals("Total revenue should be 800 CNY", expectedRevenue, actualRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // SetUp: Create a store instance with no rental records
        store = new CarRentalStore();
        
        // Verify there are no rentals (store is newly created)
        assertTrue("Store should have no rentals initially", store.getRentals().isEmpty());
        
        // Calculate total revenue
        double actualRevenue = store.calculateTotalRevenue();
        
        // Expected: Total revenue = 0 CNY
        double expectedRevenue = 0.0;
        
        // Verify result
        assertEquals("Total revenue should be 0 when no rentals exist", expectedRevenue, actualRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // SetUp: Create a store instance and add rental records with same daily price
        store = new CarRentalStore();
        
        // Create cars with same daily price
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        
        // Create a customer
        Customer customer = new Customer("Jane", "Smith", "456 Oak St");
        
        // Create rentals with specified dates
        Rental rental1 = new Rental(customer, car1, 
            LocalDate.of(2025, 11, 12), 
            LocalDate.of(2025, 11, 13));
        rental1.setBackDate(LocalDate.of(2025, 11, 13)); // 2 days rental
        
        Rental rental2 = new Rental(customer, car2, 
            LocalDate.of(2025, 11, 12), 
            LocalDate.of(2025, 12, 1));
        rental2.setBackDate(LocalDate.of(2025, 11, 15)); // 4 days rental
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double actualRevenue = store.calculateTotalRevenue();
        
        // Expected: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        double expectedRevenue = 720.0;
        
        // Verify result
        assertEquals("Total revenue should be 720 CNY for rentals with same daily price", 
                    expectedRevenue, actualRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // SetUp: Create a store instance and add rental records with varied daily prices
        store = new CarRentalStore();
        
        // Create cars with different daily prices
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        
        // Create a customer
        Customer customer = new Customer("Bob", "Johnson", "789 Pine St");
        
        // Create rentals with specified dates
        Rental rental1 = new Rental(customer, car1, 
            LocalDate.of(2025, 8, 9), 
            LocalDate.of(2025, 9, 1));
        rental1.setBackDate(LocalDate.of(2025, 8, 13)); // 5 days rental
        
        Rental rental2 = new Rental(customer, car2, 
            LocalDate.of(2025, 8, 11), 
            LocalDate.of(2026, 1, 1));
        rental2.setBackDate(LocalDate.of(2025, 8, 13)); // 3 days rental
        
        Rental rental3 = new Rental(customer, car3, 
            LocalDate.of(2025, 8, 9), 
            LocalDate.of(2025, 9, 1));
        rental3.setBackDate(LocalDate.of(2025, 8, 9)); // 1 day rental
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double actualRevenue = store.calculateTotalRevenue();
        
        // Expected: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        double expectedRevenue = 1150.0;
        
        // Verify result
        assertEquals("Total revenue should be 1150 CNY for mixed price rentals", 
                    expectedRevenue, actualRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // SetUp: Create a store instance and add rental records with one-day rentals
        store = new CarRentalStore();
        
        // Create cars
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        
        // Create a customer
        Customer customer = new Customer("Alice", "Brown", "321 Elm St");
        
        // Create rentals with one-day durations
        Rental rental1 = new Rental(customer, car1, 
            LocalDate.of(2025, 8, 12), 
            LocalDate.of(2025, 9, 1));
        rental1.setBackDate(LocalDate.of(2025, 8, 12)); // 1 day rental
        
        Rental rental2 = new Rental(customer, car2, 
            LocalDate.of(2025, 8, 9), 
            LocalDate.of(2025, 9, 1));
        rental2.setBackDate(LocalDate.of(2025, 8, 9)); // 1 day rental
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double actualRevenue = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        double expectedRevenue = 400.0;
        
        // Verify result
        assertEquals("Total revenue should be 400 CNY for one-day rentals", 
                    expectedRevenue, actualRevenue, 0.001);
    }
}