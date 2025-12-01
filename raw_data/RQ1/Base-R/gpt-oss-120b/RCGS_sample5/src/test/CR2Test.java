import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    
    private CarRentalStore store;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        
        // Create customer (same customer for all rentals as not specified)
        Customer customer = new Customer("John", "Doe", "123 Main St");
        
        // Create rental dates
        LocalDate rentDate1 = LocalDate.parse("2025-11-10", formatter);
        LocalDate dueDate1 = LocalDate.parse("2025-11-13", formatter);
        LocalDate backDate1 = LocalDate.parse("2025-11-12", formatter);
        
        LocalDate rentDate2 = LocalDate.parse("2025-11-10", formatter);
        LocalDate dueDate2 = LocalDate.parse("2025-11-13", formatter);
        LocalDate backDate2 = LocalDate.parse("2025-11-11", formatter);
        
        LocalDate rentDate3 = LocalDate.parse("2025-11-12", formatter);
        LocalDate dueDate3 = LocalDate.parse("2025-11-13", formatter);
        LocalDate backDate3 = LocalDate.parse("2025-11-12", formatter);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Add customer to store
        store.addCustomer(customer);
        
        // Create and add rentals
        Rental rental1 = new Rental(customer, car1, rentDate1, dueDate1);
        rental1.setBackDate(backDate1);
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental(customer, car2, rentDate2, dueDate2);
        rental2.setBackDate(backDate2);
        store.getRentals().add(rental2);
        
        Rental rental3 = new Rental(customer, car3, rentDate3, dueDate3);
        rental3.setBackDate(backDate3);
        store.getRentals().add(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Create store instance (already done in setUp)
        // Verify there are no rental objects
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Create cars with same daily price
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        
        // Create customer
        Customer customer = new Customer("Jane", "Smith", "456 Oak Ave");
        
        // Create rental dates
        LocalDate rentDate1 = LocalDate.parse("2025-11-12", formatter);
        LocalDate dueDate1 = LocalDate.parse("2025-11-13", formatter);
        LocalDate backDate1 = LocalDate.parse("2025-11-13", formatter);
        
        LocalDate rentDate2 = LocalDate.parse("2025-11-12", formatter);
        LocalDate dueDate2 = LocalDate.parse("2025-12-01", formatter);
        LocalDate backDate2 = LocalDate.parse("2025-11-15", formatter);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        
        // Add customer to store
        store.addCustomer(customer);
        
        // Create and add rentals
        Rental rental1 = new Rental(customer, car1, rentDate1, dueDate1);
        rental1.setBackDate(backDate1);
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental(customer, car2, rentDate2, dueDate2);
        rental2.setBackDate(backDate2);
        store.getRentals().add(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Create cars with varied daily prices
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        
        // Create customer
        Customer customer = new Customer("Bob", "Johnson", "789 Pine St");
        
        // Create rental dates
        LocalDate rentDate1 = LocalDate.parse("2025-08-09", formatter);
        LocalDate dueDate1 = LocalDate.parse("2025-09-01", formatter);
        LocalDate backDate1 = LocalDate.parse("2025-08-13", formatter);
        
        LocalDate rentDate2 = LocalDate.parse("2025-08-11", formatter);
        LocalDate dueDate2 = LocalDate.parse("2026-01-01", formatter);
        LocalDate backDate2 = LocalDate.parse("2025-08-13", formatter);
        
        LocalDate rentDate3 = LocalDate.parse("2025-08-09", formatter);
        LocalDate dueDate3 = LocalDate.parse("2025-09-01", formatter);
        LocalDate backDate3 = LocalDate.parse("2025-08-09", formatter);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Add customer to store
        store.addCustomer(customer);
        
        // Create and add rentals
        Rental rental1 = new Rental(customer, car1, rentDate1, dueDate1);
        rental1.setBackDate(backDate1);
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental(customer, car2, rentDate2, dueDate2);
        rental2.setBackDate(backDate2);
        store.getRentals().add(rental2);
        
        Rental rental3 = new Rental(customer, car3, rentDate3, dueDate3);
        rental3.setBackDate(backDate3);
        store.getRentals().add(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Create cars
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        
        // Create customer
        Customer customer = new Customer("Alice", "Brown", "321 Elm St");
        
        // Create rental dates (both are one-day rentals)
        LocalDate rentDate1 = LocalDate.parse("2025-08-12", formatter);
        LocalDate dueDate1 = LocalDate.parse("2025-09-01", formatter);
        LocalDate backDate1 = LocalDate.parse("2025-08-12", formatter);
        
        LocalDate rentDate2 = LocalDate.parse("2025-08-09", formatter);
        LocalDate dueDate2 = LocalDate.parse("2025-09-01", formatter);
        LocalDate backDate2 = LocalDate.parse("2025-08-09", formatter);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        
        // Add customer to store
        store.addCustomer(customer);
        
        // Create and add rentals
        Rental rental1 = new Rental(customer, car1, rentDate1, dueDate1);
        rental1.setBackDate(backDate1);
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental(customer, car2, rentDate2, dueDate2);
        rental2.setBackDate(backDate2);
        store.getRentals().add(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}