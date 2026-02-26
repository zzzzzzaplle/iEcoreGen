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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() throws Exception {
        // SetUp: Create a store instance
        store = new Store();
        List<Rental> rentals = new ArrayList<>();
        
        // Add Rental object with car plate "ABC123", model "Toyota Camry", daily price: 100 CNY, 
        // rented for 3 days with back date "2025-11-12", due date "2025-11-13", rental date "2025-11-10"
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2025-11-10"));
        rental1.setDueDate(dateFormat.parse("2025-11-13"));
        rental1.setBackDate(dateFormat.parse("2025-11-12"));
        rental1.setTotalPrice(100.0 * 3); // 3 days rental
        rentals.add(rental1);
        
        // Add Rental object with car plate "XYZ789", model "Honda Civic", daily price: 150 CNY,
        // rented for 2 days with back date "2025-11-11", due date "2025-11-13", rental date "2025-11-10"
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2025-11-10"));
        rental2.setDueDate(dateFormat.parse("2025-11-13"));
        rental2.setBackDate(dateFormat.parse("2025-11-11"));
        rental2.setTotalPrice(150.0 * 2); // 2 days rental
        rentals.add(rental2);
        
        // Add Rental object with car plate "LMN456", model "Ford Focus", daily price: 200 CNY,
        // rented for 1 day with back date "2025-11-12", due date "2025-11-13", rental date "2025-11-12"
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2025-11-12"));
        rental3.setDueDate(dateFormat.parse("2025-11-13"));
        rental3.setBackDate(dateFormat.parse("2025-11-12"));
        rental3.setTotalPrice(200.0 * 1); // 1 day rental
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // SetUp: Create a store instance
        store = new Store();
        
        // Verify there are no Rental objects added
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws Exception {
        // SetUp: Create a store instance
        store = new Store();
        List<Rental> rentals = new ArrayList<>();
        
        // Add Rental object with car plate "CAR001", model "Chevrolet Malibu", daily price: 120 CNY,
        // rented for 2 days with back date "2025-11-13", due date "2025-11-13", rental date "2025-11-12"
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2025-11-12"));
        rental1.setDueDate(dateFormat.parse("2025-11-13"));
        rental1.setBackDate(dateFormat.parse("2025-11-13"));
        rental1.setTotalPrice(120.0 * 2); // 2 days rental
        rentals.add(rental1);
        
        // Add Rental object with car plate "CAR002", model "Hyundai Elantra", daily price: 120 CNY,
        // rented for 4 days with back date "2025-11-15", due date "2025-12-01", rental date "2025-11-12"
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2025-11-12"));
        rental2.setDueDate(dateFormat.parse("2025-12-01"));
        rental2.setBackDate(dateFormat.parse("2025-11-15"));
        rental2.setTotalPrice(120.0 * 4); // 4 days rental
        rentals.add(rental2);
        
        store.setRentals(rentals);
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws Exception {
        // SetUp: Create a store instance
        store = new Store();
        List<Rental> rentals = new ArrayList<>();
        
        // Add Rental object with car plate "SED123", model "Mazda 3", daily price: 90 CNY,
        // rented for 5 days with back date "2025-08-13", due date "2025-09-01", rental date "2025-08-09"
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2025-08-09"));
        rental1.setDueDate(dateFormat.parse("2025-09-01"));
        rental1.setBackDate(dateFormat.parse("2025-08-13"));
        rental1.setTotalPrice(90.0 * 5); // 5 days rental
        rentals.add(rental1);
        
        // Add Rental object with car plate "SUV456", model "Kia Sportage", daily price: 150 CNY,
        // rented for 3 days with back date "2025-08-13", due date "2026-01-01", rental date "2025-08-11"
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2025-08-11"));
        rental2.setDueDate(dateFormat.parse("2026-01-01"));
        rental2.setBackDate(dateFormat.parse("2025-08-13"));
        rental2.setTotalPrice(150.0 * 3); // 3 days rental
        rentals.add(rental2);
        
        // Add Rental object with car plate "TRK789", model "Ford F-150", daily price: 250 CNY,
        // rented for 1 day with back date "2025-08-09", due date "2025-09-01", rental date "2025-08-09"
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2025-08-09"));
        rental3.setDueDate(dateFormat.parse("2025-09-01"));
        rental3.setBackDate(dateFormat.parse("2025-08-09"));
        rental3.setTotalPrice(250.0 * 1); // 1 day rental
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws Exception {
        // SetUp: Create a store instance
        store = new Store();
        List<Rental> rentals = new ArrayList<>();
        
        // Add Rental object with car plate "MINI001", model "Mini Cooper", daily price: 180 CNY,
        // rented for 1 day with back date "2025-08-12", due date "2025-09-01", rental date "2025-08-12"
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2025-08-12"));
        rental1.setDueDate(dateFormat.parse("2025-09-01"));
        rental1.setBackDate(dateFormat.parse("2025-08-12"));
        rental1.setTotalPrice(180.0 * 1); // 1 day rental
        rentals.add(rental1);
        
        // Add Rental object with car plate "MOTO002", model "Harley Davidson", daily price: 220 CNY,
        // rented for 1 day with back date "2025-08-09", due date "2025-09-01", rental date "2025-08-09"
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2025-08-09"));
        rental2.setDueDate(dateFormat.parse("2025-09-01"));
        rental2.setBackDate(dateFormat.parse("2025-08-09"));
        rental2.setTotalPrice(220.0 * 1); // 1 day rental
        rentals.add(rental2);
        
        store.setRentals(rentals);
        
        // Calculate the total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}