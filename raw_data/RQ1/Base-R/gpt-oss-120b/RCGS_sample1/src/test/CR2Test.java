import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
        // Test Case 1: Calculate total revenue from rentals with multiple rental records
        
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 100);
        Car car2 = new Car("XYZ789", "Honda Civic", 150);
        Car car3 = new Car("LMN456", "Ford Focus", 200);
        
        // Create customer (same customer for all rentals)
        Customer customer = new Customer("John", "Doe", "123 Main St");
        
        // Create rentals with specified dates
        Rental rental1 = new Rental(customer, car1, 
                                   LocalDate.of(2025, 11, 10), 
                                   LocalDate.of(2025, 11, 13));
        Rental rental2 = new Rental(customer, car2, 
                                   LocalDate.of(2025, 11, 10), 
                                   LocalDate.of(2025, 11, 13));
        Rental rental3 = new Rental(customer, car3, 
                                   LocalDate.of(2025, 11, 12), 
                                   LocalDate.of(2025, 11, 13));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Set back dates and calculate prices
        store.returnCar(rental1, LocalDate.of(2025, 11, 12)); // 3 days: Nov 10-12
        store.returnCar(rental2, LocalDate.of(2025, 11, 11)); // 2 days: Nov 10-11
        store.returnCar(rental3, LocalDate.of(2025, 11, 12)); // 1 day: Nov 12-12
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: Calculate total revenue from rentals with no rental records
        
        // Verify there are no rentals in the store
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
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120);
        
        // Create customer (same customer for all rentals)
        Customer customer = new Customer("Jane", "Smith", "456 Oak Ave");
        
        // Create rentals with specified dates
        Rental rental1 = new Rental(customer, car1, 
                                   LocalDate.of(2025, 11, 12), 
                                   LocalDate.of(2025, 11, 13));
        Rental rental2 = new Rental(customer, car2, 
                                   LocalDate.of(2025, 11, 12), 
                                   LocalDate.of(2025, 12, 1));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set back dates and calculate prices
        store.returnCar(rental1, LocalDate.of(2025, 11, 13)); // 2 days: Nov 12-13
        store.returnCar(rental2, LocalDate.of(2025, 11, 15)); // 4 days: Nov 12-15
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Test Case 4: Calculate total revenue from rentals with varied daily prices
        
        // Create cars with different daily prices
        Car car1 = new Car("SED123", "Mazda 3", 90);
        Car car2 = new Car("SUV456", "Kia Sportage", 150);
        Car car3 = new Car("TRK789", "Ford F-150", 250);
        
        // Create customer (same customer for all rentals)
        Customer customer = new Customer("Bob", "Johnson", "789 Pine St");
        
        // Create rentals with specified dates
        Rental rental1 = new Rental(customer, car1, 
                                   LocalDate.of(2025, 8, 9), 
                                   LocalDate.of(2025, 9, 1));
        Rental rental2 = new Rental(customer, car2, 
                                   LocalDate.of(2025, 8, 11), 
                                   LocalDate.of(2026, 1, 1));
        Rental rental3 = new Rental(customer, car3, 
                                   LocalDate.of(2025, 8, 9), 
                                   LocalDate.of(2025, 9, 1));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Set back dates and calculate prices
        store.returnCar(rental1, LocalDate.of(2025, 8, 13)); // 5 days: Aug 9-13
        store.returnCar(rental2, LocalDate.of(2025, 8, 13)); // 3 days: Aug 11-13
        store.returnCar(rental3, LocalDate.of(2025, 8, 9));  // 1 day: Aug 9-9
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Test Case 5: Calculate total revenue from rentals with all rentals for only one day
        
        // Create cars
        Car car1 = new Car("MINI001", "Mini Cooper", 180);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220);
        
        // Create customer (same customer for all rentals)
        Customer customer = new Customer("Alice", "Brown", "321 Elm St");
        
        // Create rentals with specified dates
        Rental rental1 = new Rental(customer, car1, 
                                   LocalDate.of(2025, 8, 12), 
                                   LocalDate.of(2025, 9, 1));
        Rental rental2 = new Rental(customer, car2, 
                                   LocalDate.of(2025, 8, 9), 
                                   LocalDate.of(2025, 9, 1));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set back dates and calculate prices
        store.returnCar(rental1, LocalDate.of(2025, 8, 12)); // 1 day: Aug 12-12
        store.returnCar(rental2, LocalDate.of(2025, 8, 9));  // 1 day: Aug 9-9
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}