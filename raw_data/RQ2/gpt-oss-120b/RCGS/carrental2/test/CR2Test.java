package edu.carrental.carrental2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.carrental.Car;
import edu.carrental.CarrentalFactory;
import edu.carrental.Customer;
import edu.carrental.Rental;
import edu.carrental.Store;
import java.util.Date;

public class CR2Test {
    
    private Store store;
    private CarrentalFactory factory;
    
    @Before
    public void setUp() {
        // Create store instance using Ecore factory pattern
        factory = CarrentalFactory.eINSTANCE;
        store = factory.createStore();
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // Test Case 1: "Single Rental Calculation"
        // SetUp: Create multiple rental records with different daily prices and rental durations
        
        // Create cars using factory
        Car car1 = factory.createCar();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        
        Car car2 = factory.createCar();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        
        Car car3 = factory.createCar();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create customers using factory
        Customer customer1 = factory.createCustomer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Customer customer2 = factory.createCustomer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        Customer customer3 = factory.createCustomer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        // Create rentals using factory
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setTotalPrice(300.0); // 100 * 3 days
        rental1.setRentalDate(new Date());
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setTotalPrice(300.0); // 150 * 2 days
        rental2.setRentalDate(new Date());
        
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setTotalPrice(200.0); // 200 * 1 day
        rental3.setRentalDate(new Date());
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: "No Rentals Calculation"
        // SetUp: Verify there are no Rental objects added
        
        // Verify no rentals exist
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Test Case 3: "Multiple Rentals with Same Daily Price"
        // SetUp: Create rentals where multiple cars have the same daily price
        
        // Create cars using factory
        Car car1 = factory.createCar();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        
        Car car2 = factory.createCar();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        
        // Create customers using factory
        Customer customer1 = factory.createCustomer();
        customer1.setName("Alice");
        customer1.setSurname("Brown");
        
        Customer customer2 = factory.createCustomer();
        customer2.setName("Charlie");
        customer2.setSurname("Wilson");
        
        // Create rentals using factory
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setTotalPrice(240.0); // 120 * 2 days
        rental1.setRentalDate(new Date());
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setTotalPrice(480.0); // 120 * 4 days
        rental2.setRentalDate(new Date());
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Test Case 4: "Mixed Prices Calculation"
        // SetUp: Create rentals with varied daily prices
        
        // Create cars using factory
        Car car1 = factory.createCar();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        
        Car car2 = factory.createCar();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        
        Car car3 = factory.createCar();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create customers using factory
        Customer customer1 = factory.createCustomer();
        customer1.setName("David");
        customer1.setSurname("Lee");
        
        Customer customer2 = factory.createCustomer();
        customer2.setName("Emma");
        customer2.setSurname("Davis");
        
        Customer customer3 = factory.createCustomer();
        customer3.setName("Frank");
        customer3.setSurname("Miller");
        
        // Create rentals using factory
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setTotalPrice(450.0); // 90 * 5 days
        rental1.setRentalDate(new Date());
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setTotalPrice(450.0); // 150 * 3 days
        rental2.setRentalDate(new Date());
        
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setTotalPrice(250.0); // 250 * 1 day
        rental3.setRentalDate(new Date());
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Test Case 5: "One-Day Rental Calculation"
        // SetUp: Create rentals with all rentals for only one day
        
        // Create cars using factory
        Car car1 = factory.createCar();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        
        Car car2 = factory.createCar();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        
        // Create customers using factory
        Customer customer1 = factory.createCustomer();
        customer1.setName("Grace");
        customer1.setSurname("Taylor");
        
        Customer customer2 = factory.createCustomer();
        customer2.setName("Henry");
        customer2.setSurname("Anderson");
        
        // Create rentals using factory
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setTotalPrice(180.0); // 180 * 1 day
        rental1.setRentalDate(new Date());
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setTotalPrice(220.0); // 220 * 1 day
        rental2.setRentalDate(new Date());
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}