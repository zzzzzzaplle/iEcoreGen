package edu.carrental.carrental1.test;

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
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // Create a Store instance named "City Car Rentals"
        store = factory.createStore();
        
        // Create cars using factory pattern
        Car car1 = factory.createCar();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(500.0);
        
        Car car2 = factory.createCar();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(600.0);
        
        Car car3 = factory.createCar();
        car3.setPlate("DEF456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(450.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rental for car2 (status: rented)
        Rental rental = factory.createRental();
        rental.setCar(car2);
        // Set backDate to null to indicate currently rented
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Test identifyAvailableCars method
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        assertEquals("First car should be the cheapest", "DEF456", availableCars.get(0).getPlate());
        assertEquals("Second car should be next cheapest", "ABC123", availableCars.get(1).getPlate());
        assertEquals("First car model should match", "Ford Focus", availableCars.get(0).getModel());
        assertEquals("First car daily price should match", 450.0, availableCars.get(0).getDailyPrice(), 0.001);
        assertEquals("Second car model should match", "Toyota Camry", availableCars.get(1).getModel());
        assertEquals("Second car daily price should match", 500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // Create a Store instance named "Downtown Rentals"
        store = factory.createStore();
        
        // Create cars using factory pattern
        Car car1 = factory.createCar();
        car1.setPlate("AAA111");
        car1.setModel("Nissan Altima");
        car1.setDailyPrice(600.0);
        
        Car car2 = factory.createCar();
        car2.setPlate("BBB222");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(700.0);
        
        Car car3 = factory.createCar();
        car3.setPlate("CCC333");
        car3.setModel("Kia Optima");
        car3.setDailyPrice(650.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rentals for all cars (status: rented)
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
        
        // Test identifyAvailableCars method
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should be empty list
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // Create a Store instance named "Luxury Car Rentals"
        store = factory.createStore();
        
        // Create cars using factory pattern
        Car car1 = factory.createCar();
        car1.setPlate("LMN456");
        car1.setModel("Porsche 911");
        car1.setDailyPrice(1500.0);
        
        Car car2 = factory.createCar();
        car2.setPlate("OPQ789");
        car2.setModel("Mercedes Benz");
        car2.setDailyPrice(1200.0);
        
        Car car3 = factory.createCar();
        car3.setPlate("RST012");
        car3.setModel("BMW 5 Series");
        car3.setDailyPrice(1300.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rental only for car2 (status: rented)
        Rental rental = factory.createRental();
        rental.setCar(car2);
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Test identifyAvailableCars method
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should have 2 available cars sorted by price
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        assertEquals("First car should be BMW 5 Series", "RST012", availableCars.get(0).getPlate());
        assertEquals("Second car should be Porsche 911", "LMN456", availableCars.get(1).getPlate());
        assertEquals("First car model should match", "BMW 5 Series", availableCars.get(0).getModel());
        assertEquals("First car daily price should match", 1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        assertEquals("Second car model should match", "Porsche 911", availableCars.get(1).getModel());
        assertEquals("Second car daily price should match", 1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // Create a Store instance named "Empty Rentals"
        store = factory.createStore();
        
        // Test identifyAvailableCars method on empty store
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should be empty list
        assertTrue("Available cars list should be empty when no cars in store", availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // Create a Store instance named "Coastal Rentals"
        store = factory.createStore();
        
        // Create cars using factory pattern
        Car car1 = factory.createCar();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        
        Car car2 = factory.createCar();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        
        // Create rental for car1 (status: rented)
        Rental rental = factory.createRental();
        rental.setCar(car1);
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Test identifyAvailableCars method
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should have 1 available car
        assertEquals("Should have 1 available car", 1, availableCars.size());
        assertEquals("Available car should be Mazda 3", "JKL012", availableCars.get(0).getPlate());
        assertEquals("Car model should match", "Mazda 3", availableCars.get(0).getModel());
        assertEquals("Car daily price should match", 350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}