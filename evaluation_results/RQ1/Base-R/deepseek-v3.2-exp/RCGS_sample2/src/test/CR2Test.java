import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private CarRentalSystem carRentalSystem;
    
    @Before
    public void setUp() {
        carRentalSystem = new CarRentalSystem();
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // SetUp: Create store instance and add rental records
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        customer3.setAddress("789 Pine Rd");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.of(2025, 11, 10));
        rental1.setDueDate(LocalDate.of(2025, 11, 13));
        rental1.setBackDate(LocalDate.of(2025, 11, 12));
        rental1.setTotalCost(300.0); // 100 * 3 days
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer2);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.of(2025, 11, 10));
        rental2.setDueDate(LocalDate.of(2025, 11, 13));
        rental2.setBackDate(LocalDate.of(2025, 11, 11));
        rental2.setTotalCost(300.0); // 150 * 2 days
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer3);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.of(2025, 11, 12));
        rental3.setDueDate(LocalDate.of(2025, 11, 13));
        rental3.setBackDate(LocalDate.of(2025, 11, 12));
        rental3.setTotalCost(200.0); // 200 * 1 day
        
        carRentalSystem.addRental(rental1);
        carRentalSystem.addRental(rental2);
        carRentalSystem.addRental(rental3);
        
        // Calculate total revenue
        double totalRevenue = carRentalSystem.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // SetUp: Create store instance with no rentals
        // Verify there are no Rental objects added
        assertEquals(0, carRentalSystem.getRentals().size());
        
        // Calculate total revenue
        double totalRevenue = carRentalSystem.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // SetUp: Create store instance and add rental records with same daily price
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Brown");
        customer1.setAddress("111 Elm St");
        
        Customer customer2 = new Customer();
        customer2.setName("Charlie");
        customer2.setSurname("Davis");
        customer2.setAddress("222 Maple Ave");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.of(2025, 11, 12));
        rental1.setDueDate(LocalDate.of(2025, 11, 13));
        rental1.setBackDate(LocalDate.of(2025, 11, 13));
        rental1.setTotalCost(240.0); // 120 * 2 days
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer2);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.of(2025, 11, 12));
        rental2.setDueDate(LocalDate.of(2025, 12, 1));
        rental2.setBackDate(LocalDate.of(2025, 11, 15));
        rental2.setTotalCost(480.0); // 120 * 4 days
        
        carRentalSystem.addRental(rental1);
        carRentalSystem.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = carRentalSystem.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // SetUp: Create store instance and add rental records with varied daily prices
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        
        Customer customer1 = new Customer();
        customer1.setName("David");
        customer1.setSurname("Wilson");
        customer1.setAddress("333 Cedar Ln");
        
        Customer customer2 = new Customer();
        customer2.setName("Eva");
        customer2.setSurname("Martinez");
        customer2.setAddress("444 Birch St");
        
        Customer customer3 = new Customer();
        customer3.setName("Frank");
        customer3.setSurname("Garcia");
        customer3.setAddress("555 Willow Rd");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.of(2025, 8, 9));
        rental1.setDueDate(LocalDate.of(2025, 9, 1));
        rental1.setBackDate(LocalDate.of(2025, 8, 13));
        rental1.setTotalCost(450.0); // 90 * 5 days
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer2);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.of(2025, 8, 11));
        rental2.setDueDate(LocalDate.of(2026, 1, 1));
        rental2.setBackDate(LocalDate.of(2025, 8, 13));
        rental2.setTotalCost(450.0); // 150 * 3 days
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer3);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.of(2025, 8, 9));
        rental3.setDueDate(LocalDate.of(2025, 9, 1));
        rental3.setBackDate(LocalDate.of(2025, 8, 9));
        rental3.setTotalCost(250.0); // 250 * 1 day
        
        carRentalSystem.addRental(rental1);
        carRentalSystem.addRental(rental2);
        carRentalSystem.addRental(rental3);
        
        // Calculate total revenue
        double totalRevenue = carRentalSystem.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // SetUp: Create store instance and add rental records with one-day rentals
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        
        Customer customer1 = new Customer();
        customer1.setName("Grace");
        customer1.setSurname("Lee");
        customer1.setAddress("666 Spruce Ave");
        
        Customer customer2 = new Customer();
        customer2.setName("Henry");
        customer2.setSurname("Taylor");
        customer2.setAddress("777 Aspen Dr");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.of(2025, 8, 12));
        rental1.setDueDate(LocalDate.of(2025, 9, 1));
        rental1.setBackDate(LocalDate.of(2025, 8, 12));
        rental1.setTotalCost(180.0); // 180 * 1 day
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer2);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.of(2025, 8, 9));
        rental2.setDueDate(LocalDate.of(2025, 9, 1));
        rental2.setBackDate(LocalDate.of(2025, 8, 9));
        rental2.setTotalCost(220.0); // 220 * 1 day
        
        carRentalSystem.addRental(rental1);
        carRentalSystem.addRental(rental2);
        
        // Calculate total revenue
        double totalRevenue = carRentalSystem.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}