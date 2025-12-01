import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        // Test Case 1: Calculate total revenue from rentals with multiple rental records
        
        // Set up rental objects as specified
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        
        Customer customer1 = new Customer("John", "Doe", "123 Main St", "ABC123");
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak St", "XYZ789");
        Customer customer3 = new Customer("Bob", "Johnson", "789 Pine St", "LMN456");
        
        Rental rental1 = new Rental(
            dateFormat.parse("2025-11-10 00:00:00"),  // rental date
            dateFormat.parse("2025-11-13 00:00:00"),  // due date
            dateFormat.parse("2025-11-12 00:00:00"),  // back date
            300.0,  // total price: 100 * 3 = 300
            "Standard lease", car1, customer1
        );
        
        Rental rental2 = new Rental(
            dateFormat.parse("2025-11-10 00:00:00"),  // rental date
            dateFormat.parse("2025-11-13 00:00:00"),  // due date
            dateFormat.parse("2025-11-11 00:00:00"),  // back date
            300.0,  // total price: 150 * 2 = 300
            "Standard lease", car2, customer2
        );
        
        Rental rental3 = new Rental(
            dateFormat.parse("2025-11-12 00:00:00"),  // rental date
            dateFormat.parse("2025-11-13 00:00:00"),  // due date
            dateFormat.parse("2025-11-12 00:00:00"),  // back date
            200.0,  // total price: 200 * 1 = 200
            "Standard lease", car3, customer3
        );
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: Calculate total revenue from rentals with no rental records
        
        // Verify there are no Rental objects added (store is created with empty rentals list)
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws Exception {
        // Test Case 3: Calculate total revenue from rentals where multiple cars have the same daily price
        
        // Set up rental objects as specified
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        
        Customer customer1 = new Customer("Alice", "Brown", "111 Elm St", "CAR001");
        Customer customer2 = new Customer("Charlie", "Wilson", "222 Maple St", "CAR002");
        
        Rental rental1 = new Rental(
            dateFormat.parse("2025-11-12 00:00:00"),  // rental date
            dateFormat.parse("2025-11-13 00:00:00"),  // due date
            dateFormat.parse("2025-11-13 00:00:00"),  // back date
            240.0,  // total price: 120 * 2 = 240
            "Standard lease", car1, customer1
        );
        
        Rental rental2 = new Rental(
            dateFormat.parse("2025-11-12 00:00:00"),  // rental date
            dateFormat.parse("2025-12-01 00:00:00"),  // due date
            dateFormat.parse("2025-11-15 00:00:00"),  // back date
            480.0,  // total price: 120 * 4 = 480
            "Standard lease", car2, customer2
        );
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws Exception {
        // Test Case 4: Calculate total revenue from rentals with varied daily prices
        
        // Set up rental objects as specified
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        
        Customer customer1 = new Customer("David", "Lee", "333 Cedar St", "SED123");
        Customer customer2 = new Customer("Emily", "Chen", "444 Birch St", "SUV456");
        Customer customer3 = new Customer("Frank", "Garcia", "555 Spruce St", "TRK789");
        
        Rental rental1 = new Rental(
            dateFormat.parse("2025-08-09 00:00:00"),  // rental date
            dateFormat.parse("2025-09-01 00:00:00"),  // due date
            dateFormat.parse("2025-08-13 00:00:00"),  // back date
            450.0,  // total price: 90 * 5 = 450
            "Standard lease", car1, customer1
        );
        
        Rental rental2 = new Rental(
            dateFormat.parse("2025-08-11 00:00:00"),  // rental date
            dateFormat.parse("2026-01-01 00:00:00"),  // due date
            dateFormat.parse("2025-08-13 00:00:00"),  // back date
            450.0,  // total price: 150 * 3 = 450
            "Standard lease", car2, customer2
        );
        
        Rental rental3 = new Rental(
            dateFormat.parse("2025-08-09 00:00:00"),  // rental date
            dateFormat.parse("2025-09-01 00:00:00"),  // due date
            dateFormat.parse("2025-08-09 00:00:00"),  // back date
            250.0,  // total price: 250 * 1 = 250
            "Standard lease", car3, customer3
        );
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws Exception {
        // Test Case 5: Calculate total revenue from rentals with all rentals for only one day
        
        // Set up rental objects as specified
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        
        Customer customer1 = new Customer("Grace", "Taylor", "666 Willow St", "MINI001");
        Customer customer2 = new Customer("Henry", "Martinez", "777 Aspen St", "MOTO002");
        
        Rental rental1 = new Rental(
            dateFormat.parse("2025-08-12 00:00:00"),  // rental date
            dateFormat.parse("2025-09-01 00:00:00"),  // due date
            dateFormat.parse("2025-08-12 00:00:00"),  // back date
            180.0,  // total price: 180 * 1 = 180
            "Standard lease", car1, customer1
        );
        
        Rental rental2 = new Rental(
            dateFormat.parse("2025-08-09 00:00:00"),  // rental date
            dateFormat.parse("2025-09-01 00:00:00"),  // due date
            dateFormat.parse("2025-08-09 00:00:00"),  // back date
            220.0,  // total price: 220 * 1 = 220
            "Standard lease", car2, customer2
        );
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}