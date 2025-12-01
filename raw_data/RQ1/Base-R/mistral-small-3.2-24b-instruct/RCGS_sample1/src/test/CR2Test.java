import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    private CarStore store;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        store = new CarStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // Set up test data for single rental calculation
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        
        Customer customer1 = new Customer("John", "Doe", "123 Main St");
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak Ave");
        Customer customer3 = new Customer("Bob", "Johnson", "789 Pine Rd");
        
        Rental rental1 = new Rental(customer1, car1, 
            LocalDate.parse("2025-11-10", formatter), 
            LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-12", formatter));
        
        Rental rental2 = new Rental(customer2, car2, 
            LocalDate.parse("2025-11-10", formatter), 
            LocalDate.parse("2025-11-13", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-11", formatter));
        
        Rental rental3 = new Rental(customer3, car3, 
            LocalDate.parse("2025-11-12", formatter), 
            LocalDate.parse("2025-11-13", formatter));
        rental3.setBackDate(LocalDate.parse("2025-11-12", formatter));
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output: (100 * 3) + (150 * 2) + (200 * 1) = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Verify there are no rental objects added
        assertTrue(store.getAvailableCars().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output: 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Set up test data for multiple rentals with same daily price
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        
        Customer customer1 = new Customer("Alice", "Brown", "111 First St");
        Customer customer2 = new Customer("Charlie", "Wilson", "222 Second St");
        
        Rental rental1 = new Rental(customer1, car1, 
            LocalDate.parse("2025-11-12", formatter), 
            LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-13", formatter));
        
        Rental rental2 = new Rental(customer2, car2, 
            LocalDate.parse("2025-11-12", formatter), 
            LocalDate.parse("2025-12-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-15", formatter));
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output: (120 * 2) + (120 * 4) = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Set up test data for mixed prices calculation
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        
        Customer customer1 = new Customer("David", "Lee", "333 Third St");
        Customer customer2 = new Customer("Emma", "Davis", "444 Fourth St");
        Customer customer3 = new Customer("Frank", "Miller", "555 Fifth St");
        
        Rental rental1 = new Rental(customer1, car1, 
            LocalDate.parse("2025-08-09", formatter), 
            LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-13", formatter));
        
        Rental rental2 = new Rental(customer2, car2, 
            LocalDate.parse("2025-08-11", formatter), 
            LocalDate.parse("2026-01-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-13", formatter));
        
        Rental rental3 = new Rental(customer3, car3, 
            LocalDate.parse("2025-08-09", formatter), 
            LocalDate.parse("2025-09-01", formatter));
        rental3.setBackDate(LocalDate.parse("2025-08-09", formatter));
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output: (90 * 5) + (150 * 3) + (250 * 1) = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Set up test data for one-day rental calculation
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        
        Customer customer1 = new Customer("Grace", "Taylor", "666 Sixth St");
        Customer customer2 = new Customer("Henry", "Anderson", "777 Seventh St");
        
        Rental rental1 = new Rental(customer1, car1, 
            LocalDate.parse("2025-08-12", formatter), 
            LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-12", formatter));
        
        Rental rental2 = new Rental(customer2, car2, 
            LocalDate.parse("2025-08-09", formatter), 
            LocalDate.parse("2025-09-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-09", formatter));
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output: (180 * 1) + (220 * 1) = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}