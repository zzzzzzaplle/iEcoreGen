import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
        // SetUp: Create store instance and add rental records
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: Toyota Camry, 100 CNY/day, 3 days
        Rental rental1 = createRental("ABC123", "Toyota Camry", 100.0, 
                                     "2025-11-10 00:00:00", "2025-11-13 00:00:00", "2025-11-12 00:00:00", 3);
        
        // Rental 2: Honda Civic, 150 CNY/day, 2 days
        Rental rental2 = createRental("XYZ789", "Honda Civic", 150.0, 
                                     "2025-11-10 00:00:00", "2025-11-13 00:00:00", "2025-11-11 00:00:00", 2);
        
        // Rental 3: Ford Focus, 200 CNY/day, 1 day
        Rental rental3 = createRental("LMN456", "Ford Focus", 200.0, 
                                     "2025-11-12 00:00:00", "2025-11-13 00:00:00", "2025-11-12 00:00:00", 1);
        
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // SetUp: Create store instance with no rentals
        store.setRentals(new ArrayList<>());
        
        // Verify there are no rental objects
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws Exception {
        // SetUp: Create store instance and add rental records with same daily price
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: Chevrolet Malibu, 120 CNY/day, 2 days
        Rental rental1 = createRental("CAR001", "Chevrolet Malibu", 120.0, 
                                     "2025-11-12 00:00:00", "2025-11-13 00:00:00", "2025-11-13 00:00:00", 2);
        
        // Rental 2: Hyundai Elantra, 120 CNY/day, 4 days
        Rental rental2 = createRental("CAR002", "Hyundai Elantra", 120.0, 
                                     "2025-11-12 00:00:00", "2025-12-01 00:00:00", "2025-11-15 00:00:00", 4);
        
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws Exception {
        // SetUp: Create store instance and add rental records with varied daily prices
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: Mazda 3, 90 CNY/day, 5 days
        Rental rental1 = createRental("SED123", "Mazda 3", 90.0, 
                                     "2025-08-09 00:00:00", "2025-09-01 00:00:00", "2025-08-13 00:00:00", 5);
        
        // Rental 2: Kia Sportage, 150 CNY/day, 3 days
        Rental rental2 = createRental("SUV456", "Kia Sportage", 150.0, 
                                     "2025-08-11 00:00:00", "2026-01-01 00:00:00", "2025-08-13 00:00:00", 3);
        
        // Rental 3: Ford F-150, 250 CNY/day, 1 day
        Rental rental3 = createRental("TRK789", "Ford F-150", 250.0, 
                                     "2025-08-09 00:00:00", "2025-09-01 00:00:00", "2025-08-09 00:00:00", 1);
        
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws Exception {
        // SetUp: Create store instance and add one-day rental records
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: Mini Cooper, 180 CNY/day, 1 day
        Rental rental1 = createRental("MINI001", "Mini Cooper", 180.0, 
                                     "2025-08-12 00:00:00", "2025-09-01 00:00:00", "2025-08-12 00:00:00", 1);
        
        // Rental 2: Harley Davidson, 220 CNY/day, 1 day
        Rental rental2 = createRental("MOTO002", "Harley Davidson", 220.0, 
                                     "2025-08-09 00:00:00", "2025-09-01 00:00:00", "2025-08-09 00:00:00", 1);
        
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
    
    // Helper method to create Rental objects with proper setup
    private Rental createRental(String plate, String model, double dailyPrice, 
                               String rentalDateStr, String dueDateStr, String backDateStr, int days) throws Exception {
        // Create car
        Car car = new Car();
        car.setPlate(plate);
        car.setModel(model);
        car.setDailyPrice(dailyPrice);
        
        // Create customer
        Customer customer = new Customer();
        customer.setName("Test");
        customer.setSurname("Customer");
        customer.setAddress("Test Address");
        customer.setRentedCarPlate(plate);
        
        // Create rental
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setRentalDate(dateFormat.parse(rentalDateStr));
        rental.setDueDate(dateFormat.parse(dueDateStr));
        rental.setBackDate(dateFormat.parse(backDateStr));
        rental.setTotalPrice(dailyPrice * days);
        
        return rental;
    }
}