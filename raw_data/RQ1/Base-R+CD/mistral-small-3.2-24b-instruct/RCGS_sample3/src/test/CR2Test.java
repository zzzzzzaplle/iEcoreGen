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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() throws ParseException {
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 100.0);
        Car car2 = new Car("XYZ789", "Honda Civic", 150.0);
        Car car3 = new Car("LMN456", "Ford Focus", 200.0);
        
        // Create customers
        Customer customer1 = new Customer("John", "Doe", "Address1", "ABC123");
        Customer customer2 = new Customer("Jane", "Smith", "Address2", "XYZ789");
        Customer customer3 = new Customer("Bob", "Johnson", "Address3", "LMN456");
        
        // Create dates
        Date rentalDate1 = dateFormat.parse("2025-11-10");
        Date dueDate1 = dateFormat.parse("2025-11-13");
        Date backDate1 = dateFormat.parse("2025-11-12");
        
        Date rentalDate2 = dateFormat.parse("2025-11-10");
        Date dueDate2 = dateFormat.parse("2025-11-13");
        Date backDate2 = dateFormat.parse("2025-11-11");
        
        Date rentalDate3 = dateFormat.parse("2025-11-12");
        Date dueDate3 = dateFormat.parse("2025-11-13");
        Date backDate3 = dateFormat.parse("2025-11-12");
        
        // Create rentals
        Rental rental1 = new Rental(rentalDate1, dueDate1, backDate1, 300.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate2, dueDate2, backDate2, 300.0, "Standard", car2, customer2);
        Rental rental3 = new Rental(rentalDate3, dueDate3, backDate3, 200.0, "Standard", car3, customer3);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(800.0, result, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Create empty rentals list
        store.setRentals(new ArrayList<>());
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws ParseException {
        // Create cars
        Car car1 = new Car("CAR001", "Chevrolet Malibu", 120.0);
        Car car2 = new Car("CAR002", "Hyundai Elantra", 120.0);
        
        // Create customers
        Customer customer1 = new Customer("Alice", "Brown", "Address4", "CAR001");
        Customer customer2 = new Customer("Charlie", "Wilson", "Address5", "CAR002");
        
        // Create dates
        Date rentalDate1 = dateFormat.parse("2025-11-12");
        Date dueDate1 = dateFormat.parse("2025-11-13");
        Date backDate1 = dateFormat.parse("2025-11-13");
        
        Date rentalDate2 = dateFormat.parse("2025-11-12");
        Date dueDate2 = dateFormat.parse("2025-12-01");
        Date backDate2 = dateFormat.parse("2025-11-15");
        
        // Create rentals
        Rental rental1 = new Rental(rentalDate1, dueDate1, backDate1, 240.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate2, dueDate2, backDate2, 480.0, "Standard", car2, customer2);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(720.0, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws ParseException {
        // Create cars
        Car car1 = new Car("SED123", "Mazda 3", 90.0);
        Car car2 = new Car("SUV456", "Kia Sportage", 150.0);
        Car car3 = new Car("TRK789", "Ford F-150", 250.0);
        
        // Create customers
        Customer customer1 = new Customer("David", "Lee", "Address6", "SED123");
        Customer customer2 = new Customer("Emma", "Davis", "Address7", "SUV456");
        Customer customer3 = new Customer("Frank", "Miller", "Address8", "TRK789");
        
        // Create dates
        Date rentalDate1 = dateFormat.parse("2025-08-09");
        Date dueDate1 = dateFormat.parse("2025-09-01");
        Date backDate1 = dateFormat.parse("2025-08-13");
        
        Date rentalDate2 = dateFormat.parse("2025-08-11");
        Date dueDate2 = dateFormat.parse("2026-01-01");
        Date backDate2 = dateFormat.parse("2025-08-13");
        
        Date rentalDate3 = dateFormat.parse("2025-08-09");
        Date dueDate3 = dateFormat.parse("2025-09-01");
        Date backDate3 = dateFormat.parse("2025-08-09");
        
        // Create rentals
        Rental rental1 = new Rental(rentalDate1, dueDate1, backDate1, 450.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate2, dueDate2, backDate2, 450.0, "Standard", car2, customer2);
        Rental rental3 = new Rental(rentalDate3, dueDate3, backDate3, 250.0, "Standard", car3, customer3);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(1150.0, result, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws ParseException {
        // Create cars
        Car car1 = new Car("MINI001", "Mini Cooper", 180.0);
        Car car2 = new Car("MOTO002", "Harley Davidson", 220.0);
        
        // Create customers
        Customer customer1 = new Customer("Grace", "Taylor", "Address9", "MINI001");
        Customer customer2 = new Customer("Henry", "Clark", "Address10", "MOTO002");
        
        // Create dates
        Date rentalDate1 = dateFormat.parse("2025-08-12");
        Date dueDate1 = dateFormat.parse("2025-09-01");
        Date backDate1 = dateFormat.parse("2025-08-12");
        
        Date rentalDate2 = dateFormat.parse("2025-08-09");
        Date dueDate2 = dateFormat.parse("2025-09-01");
        Date backDate2 = dateFormat.parse("2025-08-09");
        
        // Create rentals
        Rental rental1 = new Rental(rentalDate1, dueDate1, backDate1, 180.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate2, dueDate2, backDate2, 220.0, "Standard", car2, customer2);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(400.0, result, 0.001);
    }
}