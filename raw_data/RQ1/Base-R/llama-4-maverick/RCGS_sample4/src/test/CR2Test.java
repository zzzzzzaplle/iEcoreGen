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
        // Add Rental 1: ABC123, Toyota Camry, 100 CNY, 3 days rental
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Customer customer1 = new Customer("John", "Doe", "Address1", "ABC123", 
                                         LocalDate.parse("2025-11-13"));
        customer1.setBackDate(LocalDate.parse("2025-11-12"));
        Rental rental1 = new Rental(customer1, car1);
        store.addRental(rental1);
        
        // Add Rental 2: XYZ789, Honda Civic, 150 CNY, 2 days rental
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Customer customer2 = new Customer("Jane", "Smith", "Address2", "XYZ789", 
                                         LocalDate.parse("2025-11-13"));
        customer2.setBackDate(LocalDate.parse("2025-11-11"));
        Rental rental2 = new Rental(customer2, car2);
        store.addRental(rental2);
        
        // Add Rental 3: LMN456, Ford Focus, 200 CNY, 1 day rental
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        Customer customer3 = new Customer("Bob", "Johnson", "Address3", "LMN456", 
                                         LocalDate.parse("2025-11-13"));
        customer3.setBackDate(LocalDate.parse("2025-11-12"));
        Rental rental3 = new Rental(customer3, car3);
        store.addRental(rental3);
        
        // Calculate total revenue and verify expected output
        double result = store.calculateTotalRevenue();
        assertEquals(800.0, result, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // SetUp: Create a store instance with no rental records
        // Verify there are no Rental objects added - store is empty after setUp()
        
        // Calculate total revenue and verify expected output
        double result = store.calculateTotalRevenue();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // SetUp: Create a store instance and add rental records with same daily price
        // Add Rental 1: CAR001, Chevrolet Malibu, 120 CNY, 2 days rental
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Customer customer1 = new Customer("Alice", "Brown", "Address1", "CAR001", 
                                         LocalDate.parse("2025-11-13"));
        customer1.setBackDate(LocalDate.parse("2025-11-13"));
        Rental rental1 = new Rental(customer1, car1);
        store.addRental(rental1);
        
        // Add Rental 2: CAR002, Hyundai Elantra, 120 CNY, 4 days rental
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        Customer customer2 = new Customer("Charlie", "Wilson", "Address2", "CAR002", 
                                         LocalDate.parse("2025-12-01"));
        customer2.setBackDate(LocalDate.parse("2025-11-15"));
        Rental rental2 = new Rental(customer2, car2);
        store.addRental(rental2);
        
        // Calculate total revenue and verify expected output
        double result = store.calculateTotalRevenue();
        assertEquals(720.0, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // SetUp: Create a store instance and add rental records with varied daily prices
        // Add Rental 1: SED123, Mazda 3, 90 CNY, 5 days rental
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Customer customer1 = new Customer("David", "Lee", "Address1", "SED123", 
                                         LocalDate.parse("2025-09-01"));
        customer1.setBackDate(LocalDate.parse("2025-08-13"));
        Rental rental1 = new Rental(customer1, car1);
        store.addRental(rental1);
        
        // Add Rental 2: SUV456, Kia Sportage, 150 CNY, 3 days rental
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Customer customer2 = new Customer("Emma", "Davis", "Address2", "SUV456", 
                                         LocalDate.parse("2026-01-01"));
        customer2.setBackDate(LocalDate.parse("2025-08-13"));
        Rental rental2 = new Rental(customer2, car2);
        store.addRental(rental2);
        
        // Add Rental 3: TRK789, Ford F-150, 250 CNY, 1 day rental
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        Customer customer3 = new Customer("Frank", "Miller", "Address3", "TRK789", 
                                         LocalDate.parse("2025-09-01"));
        customer3.setBackDate(LocalDate.parse("2025-08-09"));
        Rental rental3 = new Rental(customer3, car3);
        store.addRental(rental3);
        
        // Calculate total revenue and verify expected output
        double result = store.calculateTotalRevenue();
        assertEquals(1150.0, result, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // SetUp: Create a store instance and add rental records with one-day rentals
        // Add Rental 1: MINI001, Mini Cooper, 180 CNY, 1 day rental
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Customer customer1 = new Customer("Grace", "Taylor", "Address1", "MINI001", 
                                         LocalDate.parse("2025-09-01"));
        customer1.setBackDate(LocalDate.parse("2025-08-12"));
        Rental rental1 = new Rental(customer1, car1);
        store.addRental(rental1);
        
        // Add Rental 2: MOTO002, Harley Davidson, 220 CNY, 1 day rental
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        Customer customer2 = new Customer("Henry", "Clark", "Address2", "MOTO002", 
                                         LocalDate.parse("2025-09-01"));
        customer2.setBackDate(LocalDate.parse("2025-08-09"));
        Rental rental2 = new Rental(customer2, car2);
        store.addRental(rental2);
        
        // Calculate total revenue and verify expected output
        double result = store.calculateTotalRevenue();
        assertEquals(400.0, result, 0.001);
    }
}