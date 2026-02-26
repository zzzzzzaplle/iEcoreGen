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
        // Set up test data
        Date rentalDate1 = dateFormat.parse("2025-11-10");
        Date dueDate1 = dateFormat.parse("2025-11-13");
        Date backDate1 = dateFormat.parse("2025-11-12");
        
        Date rentalDate2 = dateFormat.parse("2025-11-10");
        Date dueDate2 = dateFormat.parse("2025-11-13");
        Date backDate2 = dateFormat.parse("2025-11-11");
        
        Date rentalDate3 = dateFormat.parse("2025-11-12");
        Date dueDate3 = dateFormat.parse("2025-11-13");
        Date backDate3 = dateFormat.parse("2025-11-12");
        
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        
        // Create customers
        Customer customer1 = new Customer("John", "Doe", "Address1", "ABC123");
        Customer customer2 = new Customer("Jane", "Smith", "Address2", "XYZ789");
        Customer customer3 = new Customer("Bob", "Johnson", "Address3", "LMN456");
        
        // Create rentals
        Rental rental1 = new Rental(rentalDate1, dueDate1, backDate1, 300.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate2, dueDate2, backDate2, 300.0, "Standard", car2, customer2);
        Rental rental3 = new Rental(rentalDate3, dueDate3, backDate3, 200.0, "Standard", car3, customer3);
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected result: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Verify there are no rental objects added (store is empty)
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected result: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws Exception {
        // Set up test data
        Date rentalDate1 = dateFormat.parse("2025-11-12");
        Date dueDate1 = dateFormat.parse("2025-11-13");
        Date backDate1 = dateFormat.parse("2025-11-13");
        
        Date rentalDate2 = dateFormat.parse("2025-11-12");
        Date dueDate2 = dateFormat.parse("2025-12-01");
        Date backDate2 = dateFormat.parse("2025-11-15");
        
        // Create cars with same daily price
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        
        // Create customers
        Customer customer1 = new Customer("Alice", "Brown", "Address4", "CAR001");
        Customer customer2 = new Customer("Charlie", "Wilson", "Address5", "CAR002");
        
        // Create rentals
        Rental rental1 = new Rental(rentalDate1, dueDate1, backDate1, 240.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate2, dueDate2, backDate2, 480.0, "Standard", car2, customer2);
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected result: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws Exception {
        // Set up test data
        Date rentalDate1 = dateFormat.parse("2025-08-09");
        Date dueDate1 = dateFormat.parse("2025-09-01");
        Date backDate1 = dateFormat.parse("2025-08-13");
        
        Date rentalDate2 = dateFormat.parse("2025-08-11");
        Date dueDate2 = dateFormat.parse("2026-01-01");
        Date backDate2 = dateFormat.parse("2025-08-13");
        
        Date rentalDate3 = dateFormat.parse("2025-08-09");
        Date dueDate3 = dateFormat.parse("2025-09-01");
        Date backDate3 = dateFormat.parse("2025-08-09");
        
        // Create cars with varied daily prices
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        
        // Create customers
        Customer customer1 = new Customer("David", "Lee", "Address6", "SED123");
        Customer customer2 = new Customer("Emma", "Garcia", "Address7", "SUV456");
        Customer customer3 = new Customer("Frank", "Martinez", "Address8", "TRK789");
        
        // Create rentals
        Rental rental1 = new Rental(rentalDate1, dueDate1, backDate1, 450.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate2, dueDate2, backDate2, 450.0, "Standard", car2, customer2);
        Rental rental3 = new Rental(rentalDate3, dueDate3, backDate3, 250.0, "Standard", car3, customer3);
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected result: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws Exception {
        // Set up test data
        Date rentalDate1 = dateFormat.parse("2025-08-12");
        Date dueDate1 = dateFormat.parse("2025-09-01");
        Date backDate1 = dateFormat.parse("2025-08-12");
        
        Date rentalDate2 = dateFormat.parse("2025-08-09");
        Date dueDate2 = dateFormat.parse("2025-09-01");
        Date backDate2 = dateFormat.parse("2025-08-09");
        
        // Create cars
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        
        // Create customers
        Customer customer1 = new Customer("Grace", "Taylor", "Address9", "MINI001");
        Customer customer2 = new Customer("Henry", "Anderson", "Address10", "MOTO002");
        
        // Create rentals
        Rental rental1 = new Rental(rentalDate1, dueDate1, backDate1, 180.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate2, dueDate2, backDate2, 220.0, "Standard", car2, customer2);
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected result: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}