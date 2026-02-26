import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public void testCase1_SingleRentalCalculation() throws ParseException {
        // SetUp: Create a store instance and add Rental objects
        store.setRentals(new ArrayList<>());
        
        // Rental 1: car plate "ABC123", model "Toyota Camry", daily price: 100 CNY
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Customer customer1 = new Customer();
        Date rentalDate1 = dateFormat.parse("2025-11-10 00:00:00");
        Date dueDate1 = dateFormat.parse("2025-11-13 00:00:00");
        Date backDate1 = dateFormat.parse("2025-11-12 00:00:00");
        double totalPrice1 = 100.0 * 3; // 3 days rental
        Rental rental1 = new Rental(rentalDate1, dueDate1, backDate1, totalPrice1, "Standard", car1, customer1);
        
        // Rental 2: car plate "XYZ789", model "Honda Civic", daily price: 150 CNY
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Customer customer2 = new Customer();
        Date rentalDate2 = dateFormat.parse("2025-11-10 00:00:00");
        Date dueDate2 = dateFormat.parse("2025-11-13 00:00:00");
        Date backDate2 = dateFormat.parse("2025-11-11 00:00:00");
        double totalPrice2 = 150.0 * 2; // 2 days rental
        Rental rental2 = new Rental(rentalDate2, dueDate2, backDate2, totalPrice2, "Standard", car2, customer2);
        
        // Rental 3: car plate "LMN456", model "Ford Focus", daily price: 200 CNY
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        Customer customer3 = new Customer();
        Date rentalDate3 = dateFormat.parse("2025-11-12 00:00:00");
        Date dueDate3 = dateFormat.parse("2025-11-13 00:00:00");
        Date backDate3 = dateFormat.parse("2025-11-12 00:00:00");
        double totalPrice3 = 200.0 * 1; // 1 day rental
        Rental rental3 = new Rental(rentalDate3, dueDate3, backDate3, totalPrice3, "Standard", car3, customer3);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate the total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, result, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // SetUp: Create a store instance with no rentals
        store.setRentals(new ArrayList<>());
        
        // Calculate the total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws ParseException {
        // SetUp: Create a store instance and add Rental objects with same daily price
        store.setRentals(new ArrayList<>());
        
        // Rental 1: car plate "CAR001", model "Chevrolet Malibu", daily price: 120 CNY
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Customer customer1 = new Customer();
        Date rentalDate1 = dateFormat.parse("2025-11-12 00:00:00");
        Date dueDate1 = dateFormat.parse("2025-11-13 00:00:00");
        Date backDate1 = dateFormat.parse("2025-11-13 00:00:00");
        double totalPrice1 = 120.0 * 2; // 2 days rental
        Rental rental1 = new Rental(rentalDate1, dueDate1, backDate1, totalPrice1, "Standard", car1, customer1);
        
        // Rental 2: car plate "CAR002", model "Hyundai Elantra", daily price: 120 CNY
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        Customer customer2 = new Customer();
        Date rentalDate2 = dateFormat.parse("2025-11-12 00:00:00");
        Date dueDate2 = dateFormat.parse("2025-12-01 00:00:00");
        Date backDate2 = dateFormat.parse("2025-11-15 00:00:00");
        double totalPrice2 = 120.0 * 4; // 4 days rental
        Rental rental2 = new Rental(rentalDate2, dueDate2, backDate2, totalPrice2, "Standard", car2, customer2);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate the total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws ParseException {
        // SetUp: Create a store instance and add Rental objects with varied daily prices
        store.setRentals(new ArrayList<>());
        
        // Rental 1: car plate "SED123", model "Mazda 3", daily price: 90 CNY
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Customer customer1 = new Customer();
        Date rentalDate1 = dateFormat.parse("2025-08-09 00:00:00");
        Date dueDate1 = dateFormat.parse("2025-09-01 00:00:00");
        Date backDate1 = dateFormat.parse("2025-08-13 00:00:00");
        double totalPrice1 = 90.0 * 5; // 5 days rental
        Rental rental1 = new Rental(rentalDate1, dueDate1, backDate1, totalPrice1, "Standard", car1, customer1);
        
        // Rental 2: car plate "SUV456", model "Kia Sportage", daily price: 150 CNY
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Customer customer2 = new Customer();
        Date rentalDate2 = dateFormat.parse("2025-08-11 00:00:00");
        Date dueDate2 = dateFormat.parse("2026-01-01 00:00:00");
        Date backDate2 = dateFormat.parse("2025-08-13 00:00:00");
        double totalPrice2 = 150.0 * 3; // 3 days rental
        Rental rental2 = new Rental(rentalDate2, dueDate2, backDate2, totalPrice2, "Standard", car2, customer2);
        
        // Rental 3: car plate "TRK789", model "Ford F-150", daily price: 250 CNY
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        Customer customer3 = new Customer();
        Date rentalDate3 = dateFormat.parse("2025-08-09 00:00:00");
        Date dueDate3 = dateFormat.parse("2025-09-01 00:00:00");
        Date backDate3 = dateFormat.parse("2025-08-09 00:00:00");
        double totalPrice3 = 250.0 * 1; // 1 day rental
        Rental rental3 = new Rental(rentalDate3, dueDate3, backDate3, totalPrice3, "Standard", car3, customer3);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate the total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, result, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws ParseException {
        // SetUp: Create a store instance and add Rental objects with one-day rentals
        store.setRentals(new ArrayList<>());
        
        // Rental 1: car plate "MINI001", model "Mini Cooper", daily price: 180 CNY
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Customer customer1 = new Customer();
        Date rentalDate1 = dateFormat.parse("2025-08-12 00:00:00");
        Date dueDate1 = dateFormat.parse("2025-09-01 00:00:00");
        Date backDate1 = dateFormat.parse("2025-08-12 00:00:00");
        double totalPrice1 = 180.0 * 1; // 1 day rental
        Rental rental1 = new Rental(rentalDate1, dueDate1, backDate1, totalPrice1, "Standard", car1, customer1);
        
        // Rental 2: car plate "MOTO002", model "Harley Davidson", daily price: 220 CNY
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        Customer customer2 = new Customer();
        Date rentalDate2 = dateFormat.parse("2025-08-09 00:00:00");
        Date dueDate2 = dateFormat.parse("2025-09-01 00:00:00");
        Date backDate2 = dateFormat.parse("2025-08-09 00:00:00");
        double totalPrice2 = 220.0 * 1; // 1 day rental
        Rental rental2 = new Rental(rentalDate2, dueDate2, backDate2, totalPrice2, "Standard", car2, customer2);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate the total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, result, 0.001);
    }
}