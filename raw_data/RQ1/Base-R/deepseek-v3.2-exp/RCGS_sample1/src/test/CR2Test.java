import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private CarRentalSystem carRentalSystem;
    
    @Before
    public void setUp() {
        carRentalSystem = new CarRentalSystem();
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() throws ParseException {
        // Test Case 1: "Single Rental Calculation"
        // Input: Calculate total revenue from rentals with multiple rental records
        
        // SetUp: Create a store instance
        CarRentalSystem system = new CarRentalSystem();
        
        // Create first rental
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setStartDate("2025-11-10");
        rental1.setDueDate("2025-11-13");
        rental1.setBackDate("2025-11-12");
        rental1.setTotalCost(300.0); // 100 * 3 days
        
        // Create second rental
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak St");
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setStartDate("2025-11-10");
        rental2.setDueDate("2025-11-13");
        rental2.setBackDate("2025-11-11");
        rental2.setTotalCost(300.0); // 150 * 2 days
        
        // Create third rental
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        customer3.setAddress("789 Pine St");
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setStartDate("2025-11-12");
        rental3.setDueDate("2025-11-13");
        rental3.setBackDate("2025-11-12");
        rental3.setTotalCost(200.0); // 200 * 1 day
        
        // Add rentals to system
        system.addRental(rental1);
        system.addRental(rental2);
        system.addRental(rental3);
        
        // Calculate the total revenue
        double totalRevenue = system.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: "No Rentals Calculation"
        // Input: Calculate total revenue from rentals with no rental records
        
        // SetUp: Create a store instance
        CarRentalSystem system = new CarRentalSystem();
        
        // Verify there are no Rental objects added
        assertTrue(system.getRentals().isEmpty());
        
        // Calculate the total revenue
        double totalRevenue = system.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws ParseException {
        // Test Case 3: "Multiple Rentals with Same Daily Price"
        // Input: Calculate total revenue from rentals where multiple cars have the same daily price
        
        // SetUp: Create a store instance
        CarRentalSystem system = new CarRentalSystem();
        
        // Create first rental
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Brown");
        customer1.setAddress("111 Elm St");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setStartDate("2025-11-12");
        rental1.setDueDate("2025-11-13");
        rental1.setBackDate("2025-11-13");
        rental1.setTotalCost(240.0); // 120 * 2 days
        
        // Create second rental
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Charlie");
        customer2.setSurname("Wilson");
        customer2.setAddress("222 Maple St");
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setStartDate("2025-11-12");
        rental2.setDueDate("2025-12-01");
        rental2.setBackDate("2025-11-15");
        rental2.setTotalCost(480.0); // 120 * 4 days
        
        // Add rentals to system
        system.addRental(rental1);
        system.addRental(rental2);
        
        // Calculate the total revenue
        double totalRevenue = system.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws ParseException {
        // Test Case 4: "Mixed Prices Calculation"
        // Input: Calculate total revenue from rentals with varied daily prices
        
        // SetUp: Create a store instance
        CarRentalSystem system = new CarRentalSystem();
        
        // Create first rental
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        
        Customer customer1 = new Customer();
        customer1.setName("David");
        customer1.setSurname("Lee");
        customer1.setAddress("333 Cedar St");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setStartDate("2025-08-09");
        rental1.setDueDate("2025-09-01");
        rental1.setBackDate("2025-08-13");
        rental1.setTotalCost(450.0); // 90 * 5 days
        
        // Create second rental
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Emma");
        customer2.setSurname("Davis");
        customer2.setAddress("444 Birch St");
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setStartDate("2025-08-11");
        rental2.setDueDate("2026-01-01");
        rental2.setBackDate("2025-08-13");
        rental2.setTotalCost(450.0); // 150 * 3 days
        
        // Create third rental
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        
        Customer customer3 = new Customer();
        customer3.setName("Frank");
        customer3.setSurname("Miller");
        customer3.setAddress("555 Walnut St");
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setStartDate("2025-08-09");
        rental3.setDueDate("2025-09-01");
        rental3.setBackDate("2025-08-09");
        rental3.setTotalCost(250.0); // 250 * 1 day
        
        // Add rentals to system
        system.addRental(rental1);
        system.addRental(rental2);
        system.addRental(rental3);
        
        // Calculate the total revenue
        double totalRevenue = system.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws ParseException {
        // Test Case 5: "One-Day Rental Calculation"
        // Input: Calculate total revenue from rentals with all rentals for only one day
        
        // SetUp: Create a store instance
        CarRentalSystem system = new CarRentalSystem();
        
        // Create first rental
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        
        Customer customer1 = new Customer();
        customer1.setName("Grace");
        customer1.setSurname("Taylor");
        customer1.setAddress("666 Spruce St");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setStartDate("2025-08-12");
        rental1.setDueDate("2025-09-01");
        rental1.setBackDate("2025-08-12");
        rental1.setTotalCost(180.0); // 180 * 1 day
        
        // Create second rental
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Henry");
        customer2.setSurname("Clark");
        customer2.setAddress("777 Pineapple St");
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setStartDate("2025-08-09");
        rental2.setDueDate("2025-09-01");
        rental2.setBackDate("2025-08-09");
        rental2.setTotalCost(220.0); // 220 * 1 day
        
        // Add rentals to system
        system.addRental(rental1);
        system.addRental(rental2);
        
        // Calculate the total revenue
        double totalRevenue = system.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}