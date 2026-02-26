package edu.carrental.carrental4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.carrental.CarrentalFactory;
import edu.carrental.CarrentalPackage;
import edu.carrental.Store;
import edu.carrental.Car;
import edu.carrental.Rental;
import edu.carrental.Customer;
import java.util.Date;
import org.eclipse.emf.common.util.EList;

public class CR1Test {
    
    private CarrentalFactory factory;
    private Store store;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = CarrentalFactory.eINSTANCE;
        // Create store instance using factory
        store = factory.createStore();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create "City Car Rentals" store with cars
        store = factory.createStore();
        
        // Create available car: "ABC123", "Toyota Camry", 500
        Car car1 = factory.createCar();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(500.0);
        store.getCars().add(car1);
        
        // Create rented car: "XYZ789", "Honda Accord", 600
        Car car2 = factory.createCar();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(600.0);
        store.getCars().add(car2);
        
        // Create available car: "DEF456", "Ford Focus", 450
        Car car3 = factory.createCar();
        car3.setPlate("DEF456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(450.0);
        store.getCars().add(car3);
        
        // Create rental for the rented car
        Rental rental = factory.createRental();
        rental.setCar(car2);
        // Set backDate to null to indicate car is currently rented
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Test: Check for available cars
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price = [{"DEF456", "Ford Focus", 450}, {"ABC123", "Toyota Camry", 500}]
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Check first car (lowest price)
        Car firstCar = availableCars.get(0);
        assertEquals("First car plate should be DEF456", "DEF456", firstCar.getPlate());
        assertEquals("First car model should be Ford Focus", "Ford Focus", firstCar.getModel());
        assertEquals("First car daily price should be 450", 450.0, firstCar.getDailyPrice(), 0.001);
        
        // Check second car
        Car secondCar = availableCars.get(1);
        assertEquals("Second car plate should be ABC123", "ABC123", secondCar.getPlate());
        assertEquals("Second car model should be Toyota Camry", "Toyota Camry", secondCar.getModel());
        assertEquals("Second car daily price should be 500", 500.0, secondCar.getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create "Downtown Rentals" store with all cars rented
        store = factory.createStore();
        
        // Create rented car: "AAA111", "Nissan Altima", 600
        Car car1 = factory.createCar();
        car1.setPlate("AAA111");
        car1.setModel("Nissan Altima");
        car1.setDailyPrice(600.0);
        store.getCars().add(car1);
        
        // Create rented car: "BBB222", "Chevy Malibu", 700
        Car car2 = factory.createCar();
        car2.setPlate("BBB222");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(700.0);
        store.getCars().add(car2);
        
        // Create rented car: "CCC333", "Kia Optima", 650
        Car car3 = factory.createCar();
        car3.setPlate("CCC333");
        car3.setModel("Kia Optima");
        car3.setDailyPrice(650.0);
        store.getCars().add(car3);
        
        // Create rentals for all cars (all with backDate = null indicating currently rented)
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setBackDate(null);
        store.getRentals().add(rental1);
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setBackDate(null);
        store.getRentals().add(rental2);
        
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setBackDate(null);
        store.getRentals().add(rental3);
        
        // Test: Check for available cars
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create "Luxury Car Rentals" store with mixed rental status
        store = factory.createStore();
        
        // Create available car: "LMN456", "Porsche 911", 1500
        Car car1 = factory.createCar();
        car1.setPlate("LMN456");
        car1.setModel("Porsche 911");
        car1.setDailyPrice(1500.0);
        store.getCars().add(car1);
        
        // Create rented car: "OPQ789", "Mercedes Benz", 1200
        Car car2 = factory.createCar();
        car2.setPlate("OPQ789");
        car2.setModel("Mercedes Benz");
        car2.setDailyPrice(1200.0);
        store.getCars().add(car2);
        
        // Create available car: "RST012", "BMW 5 Series", 1300
        Car car3 = factory.createCar();
        car3.setPlate("RST012");
        car3.setModel("BMW 5 Series");
        car3.setDailyPrice(1300.0);
        store.getCars().add(car3);
        
        // Create rental only for the rented car
        Rental rental = factory.createRental();
        rental.setCar(car2);
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Test: Check for available cars
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [ {"RST012", "BMW 5 Series", 1300}, {"LMN456", "Porsche 911", 1500}]
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Check first car (lower price)
        Car firstCar = availableCars.get(0);
        assertEquals("First car plate should be RST012", "RST012", firstCar.getPlate());
        assertEquals("First car model should be BMW 5 Series", "BMW 5 Series", firstCar.getModel());
        assertEquals("First car daily price should be 1300", 1300.0, firstCar.getDailyPrice(), 0.001);
        
        // Check second car
        Car secondCar = availableCars.get(1);
        assertEquals("Second car plate should be LMN456", "LMN456", secondCar.getPlate());
        assertEquals("Second car model should be Porsche 911", "Porsche 911", secondCar.getModel());
        assertEquals("Second car daily price should be 1500", 1500.0, secondCar.getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create "Empty Rentals" store with no cars
        store = factory.createStore();
        // No cars added to store
        
        // Test: Check for available cars
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create "Coastal Rentals" store with one rented and one available car
        store = factory.createStore();
        
        // Create rented car: "GHI789", "Subaru Impreza", 400
        Car car1 = factory.createCar();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        store.getCars().add(car1);
        
        // Create available car: "JKL012", "Mazda 3", 350
        Car car2 = factory.createCar();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        store.getCars().add(car2);
        
        // Create rental only for the rented car
        Rental rental = factory.createRental();
        rental.setCar(car1);
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Test: Check for available cars
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [{"JKL012", "Mazda 3", 350}]
        assertEquals("Should have 1 available car", 1, availableCars.size());
        
        Car availableCar = availableCars.get(0);
        assertEquals("Available car plate should be JKL012", "JKL012", availableCar.getPlate());
        assertEquals("Available car model should be Mazda 3", "Mazda 3", availableCar.getModel());
        assertEquals("Available car daily price should be 350", 350.0, availableCar.getDailyPrice(), 0.001);
    }
}