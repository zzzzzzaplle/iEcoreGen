package edu.carrental.carrental3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.Date;
import edu.carrental.CarrentalFactory;
import edu.carrental.CarrentalPackage;
import edu.carrental.Store;
import edu.carrental.Car;
import edu.carrental.Customer;
import edu.carrental.Rental;

public class CR5Test {
    
    private Store store;
    private CarrentalFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory and store using Ecore factory pattern
        factory = CarrentalFactory.eINSTANCE;
        store = factory.createStore();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create customer C001
        Customer customer = factory.createCustomer();
        
        // Create 3 cars with different plates
        Car car1 = factory.createCar();
        car1.setPlate("ABC123");
        Car car2 = factory.createCar();
        car2.setPlate("XYZ456");
        Car car3 = factory.createCar();
        car3.setPlate("LMN789");
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create 3 rental records for customer C001
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify customer C001 has 3 rentals
        assertEquals("Customer should have 3 rentals", Integer.valueOf(3), result.get(customer));
        assertEquals("Map should contain exactly 1 customer", 1, result.size());
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create customers C001 and C002
        Customer customer1 = factory.createCustomer();
        Customer customer2 = factory.createCustomer();
        
        // Create cars for customer C001
        Car car1 = factory.createCar();
        car1.setPlate("ABC123");
        Car car2 = factory.createCar();
        car2.setPlate("XYZ456");
        
        // Create cars for customer C002
        Car car3 = factory.createCar();
        car3.setPlate("LMN789");
        Car car4 = factory.createCar();
        car4.setPlate("OPQ012");
        Car car5 = factory.createCar();
        car5.setPlate("RST345");
        Car car6 = factory.createCar();
        car6.setPlate("UVW678");
        Car car7 = factory.createCar();
        car7.setPlate("JKL901");
        
        // Add all cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getCars().add(car4);
        store.getCars().add(car5);
        store.getCars().add(car6);
        store.getCars().add(car7);
        
        // Create 2 rental records for customer C001
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer1);
        
        // Create 5 rental records for customer C002
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setCustomer(customer2);
        
        Rental rental4 = factory.createRental();
        rental4.setCar(car4);
        rental4.setCustomer(customer2);
        
        Rental rental5 = factory.createRental();
        rental5.setCar(car5);
        rental5.setCustomer(customer2);
        
        Rental rental6 = factory.createRental();
        rental6.setCar(car6);
        rental6.setCustomer(customer2);
        
        Rental rental7 = factory.createRental();
        rental7.setCar(car7);
        rental7.setCustomer(customer2);
        
        // Add all rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        store.getRentals().add(rental5);
        store.getRentals().add(rental6);
        store.getRentals().add(rental7);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify customer C001 has 2 rentals and customer C002 has 5 rentals
        assertEquals("Customer C001 should have 2 rentals", Integer.valueOf(2), result.get(customer1));
        assertEquals("Customer C002 should have 5 rentals", Integer.valueOf(5), result.get(customer2));
        assertEquals("Map should contain exactly 2 customers", 2, result.size());
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer C003
        Customer customer = factory.createCustomer();
        
        // No rental records are added
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the map is empty since no rentals exist
        assertTrue("Map should be empty when no rentals exist", result.isEmpty());
        assertNull("Customer should not be in the result map", result.get(customer));
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create customer C004
        Customer customer = factory.createCustomer();
        
        // Create 4 cars
        Car car1 = factory.createCar();
        car1.setPlate("DEF234");
        Car car2 = factory.createCar();
        car2.setPlate("GHI567");
        Car car3 = factory.createCar();
        car3.setPlate("JKL890");
        Car car4 = factory.createCar();
        car4.setPlate("MNO123");
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getCars().add(car4);
        
        // Create 4 rental records for customer C004
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setBackDate(new Date()); // Mark as returned
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setBackDate(new Date()); // Mark as returned
        
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        // rental3 remains active (no back date)
        
        Rental rental4 = factory.createRental();
        rental4.setCar(car4);
        rental4.setCustomer(customer);
        // rental4 remains active (no back date)
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify customer C004 has 4 rentals (count includes both returned and active rentals)
        assertEquals("Customer should have 4 total rentals (including returned)", Integer.valueOf(4), result.get(customer));
        assertEquals("Map should contain exactly 1 customer", 1, result.size());
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create customer C005
        Customer customer = factory.createCustomer();
        
        // Create 3 cars
        Car car1 = factory.createCar();
        car1.setPlate("PQR456");
        Car car2 = factory.createCar();
        car2.setPlate("STU789");
        Car car3 = factory.createCar();
        car3.setPlate("VWX012");
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create 3 rental records for customer C005
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        // Mark as overdue by setting due date in the past and no back date
        rental2.setDueDate(new Date(System.currentTimeMillis() - 86400000)); // 1 day ago
        
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify customer C005 has 3 rentals (count includes overdue rentals)
        assertEquals("Customer should have 3 total rentals (including overdue)", Integer.valueOf(3), result.get(customer));
        assertEquals("Map should contain exactly 1 customer", 1, result.size());
    }
}