package edu.carrental.carrental2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.carrental.CarrentalFactory;
import edu.carrental.Store;
import edu.carrental.Car;
import edu.carrental.Rental;
import edu.carrental.Customer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private CarrentalFactory factory;
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = CarrentalFactory.eINSTANCE;
        store = factory.createStore();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() throws Exception {
        // Create cars
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
        
        // Create customers
        Customer customer1 = factory.createCustomer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Customer customer2 = factory.createCustomer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        Customer customer3 = factory.createCustomer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        // Create rentals
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-11-10 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental1.setTotalPrice(300.0); // 100 * 3 days
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-11-10 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-11-11 00:00:00"));
        rental2.setTotalPrice(300.0); // 150 * 2 days
        
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental3.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental3.setBackDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental3.setTotalPrice(200.0); // 200 * 1 day
        
        // Add cars and rentals to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Verify there are no rental objects added
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws Exception {
        // Create cars
        Car car1 = factory.createCar();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        
        Car car2 = factory.createCar();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        
        // Create customers
        Customer customer1 = factory.createCustomer();
        customer1.setName("Alice");
        customer1.setSurname("Brown");
        
        Customer customer2 = factory.createCustomer();
        customer2.setName("Charlie");
        customer2.setSurname("Wilson");
        
        // Create rentals
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental1.setTotalPrice(240.0); // 120 * 2 days
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-12-01 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-11-15 00:00:00"));
        rental2.setTotalPrice(480.0); // 120 * 4 days
        
        // Add cars and rentals to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws Exception {
        // Create cars
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
        
        // Create customers
        Customer customer1 = factory.createCustomer();
        customer1.setName("David");
        customer1.setSurname("Lee");
        
        Customer customer2 = factory.createCustomer();
        customer2.setName("Emma");
        customer2.setSurname("Davis");
        
        Customer customer3 = factory.createCustomer();
        customer3.setName("Frank");
        customer3.setSurname("Miller");
        
        // Create rentals
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-08-13 00:00:00"));
        rental1.setTotalPrice(450.0); // 90 * 5 days
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-08-11 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2026-01-01 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-08-13 00:00:00"));
        rental2.setTotalPrice(450.0); // 150 * 3 days
        
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental3.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental3.setBackDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental3.setTotalPrice(250.0); // 250 * 1 day
        
        // Add cars and rentals to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws Exception {
        // Create cars
        Car car1 = factory.createCar();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        
        Car car2 = factory.createCar();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        
        // Create customers
        Customer customer1 = factory.createCustomer();
        customer1.setName("Grace");
        customer1.setSurname("Taylor");
        
        Customer customer2 = factory.createCustomer();
        customer2.setName("Henry");
        customer2.setSurname("Anderson");
        
        // Create rentals
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-08-12 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-08-12 00:00:00"));
        rental1.setTotalPrice(180.0); // 180 * 1 day
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental2.setTotalPrice(220.0); // 220 * 1 day
        
        // Add cars and rentals to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected output
        assertEquals(400.0, totalRevenue, 0.001);
    }
}