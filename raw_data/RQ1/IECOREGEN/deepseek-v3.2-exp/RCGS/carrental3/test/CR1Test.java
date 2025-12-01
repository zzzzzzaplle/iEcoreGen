package edu.carrental.carrental3.test;

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
        // Initialize the Ecore factory and package
        factory = CarrentalFactory.eINSTANCE;
        CarrentalPackage.eINSTANCE.eClass(); // Ensure package is initialized
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // Create a Store instance named "City Car Rentals"
        store = factory.createStore();
        
        // Create and add cars with different statuses
        Car car1 = factory.createCar();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(500.0);
        store.getCars().add(car1);
        
        Car car2 = factory.createCar();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(600.0);
        store.getCars().add(car2);
        
        Car car3 = factory.createCar();
        car3.setPlate("DEF456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(450.0);
        store.getCars().add(car3);
        
        // Create rental for the rented car (XYZ789)
        Rental rental = factory.createRental();
        rental.setCar(car2);
        // No back date means currently rented
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Test: Check for available cars
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        assertEquals("First car should be Ford Focus with lowest price", "DEF456", availableCars.get(0).getPlate());
        assertEquals("Second car should be Toyota Camry", "ABC123", availableCars.get(1).getPlate());
        assertEquals("Ford Focus daily price should be 450", 450.0, availableCars.get(0).getDailyPrice(), 0.001);
        assertEquals("Toyota Camry daily price should be 500", 500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // Create a Store instance named "Downtown Rentals"
        store = factory.createStore();
        
        // Create and add all rented cars
        Car car1 = factory.createCar();
        car1.setPlate("AAA111");
        car1.setModel("Nissan Altima");
        car1.setDailyPrice(600.0);
        store.getCars().add(car1);
        
        Car car2 = factory.createCar();
        car2.setPlate("BBB222");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(700.0);
        store.getCars().add(car2);
        
        Car car3 = factory.createCar();
        car3.setPlate("CCC333");
        car3.setModel("Kia Optima");
        car3.setDailyPrice(650.0);
        store.getCars().add(car3);
        
        // Create rentals for all cars (all rented)
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
        
        // Verify results - should be empty list
        assertTrue("Available cars list should be empty when all cars are rented", availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // Create a Store instance named "Luxury Car Rentals"
        store = factory.createStore();
        
        // Create and add luxury cars with different statuses
        Car car1 = factory.createCar();
        car1.setPlate("LMN456");
        car1.setModel("Porsche 911");
        car1.setDailyPrice(1500.0);
        store.getCars().add(car1);
        
        Car car2 = factory.createCar();
        car2.setPlate("OPQ789");
        car2.setModel("Mercedes Benz");
        car2.setDailyPrice(1200.0);
        store.getCars().add(car2);
        
        Car car3 = factory.createCar();
        car3.setPlate("RST012");
        car3.setModel("BMW 5 Series");
        car3.setDailyPrice(1300.0);
        store.getCars().add(car3);
        
        // Create rental for the rented car (OPQ789)
        Rental rental = factory.createRental();
        rental.setCar(car2);
        rental.setBackDate(null); // Currently rented
        store.getRentals().add(rental);
        
        // Test: Check for available cars
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should have 2 available cars sorted by price
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        assertEquals("First car should be BMW 5 Series with lower price", "RST012", availableCars.get(0).getPlate());
        assertEquals("Second car should be Porsche 911", "LMN456", availableCars.get(1).getPlate());
        assertEquals("BMW 5 Series daily price should be 1300", 1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        assertEquals("Porsche 911 daily price should be 1500", 1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // Create a Store instance named "Empty Rentals"
        store = factory.createStore();
        
        // Test: Check for available cars in empty store
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should be empty list
        assertTrue("Available cars list should be empty when store has no cars", availableCars.isEmpty());
        assertTrue("Cars list should be empty", store.getCars().isEmpty());
        assertTrue("Rentals list should be empty", store.getRentals().isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // Create a Store instance named "Coastal Rentals"
        store = factory.createStore();
        
        // Create and add cars
        Car car1 = factory.createCar();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        store.getCars().add(car1);
        
        Car car2 = factory.createCar();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        store.getCars().add(car2);
        
        // Create rental for the rented car (GHI789)
        Rental rental = factory.createRental();
        rental.setCar(car1);
        rental.setBackDate(null); // Currently rented
        store.getRentals().add(rental);
        
        // Test: Check for available cars
        EList<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should have 1 available car
        assertEquals("Should have 1 available car", 1, availableCars.size());
        assertEquals("Available car should be Mazda 3", "JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3 daily price should be 350", 350.0, availableCars.get(0).getDailyPrice(), 0.001);
        assertEquals("Mazda 3 model should be correct", "Mazda 3", availableCars.get(0).getModel());
    }
}