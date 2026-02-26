package edu.carrental.carrental1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.Date;
import edu.carrental.CarrentalFactory;
import edu.carrental.CarrentalPackage;
import edu.carrental.Store;
import edu.carrental.Customer;
import edu.carrental.Car;
import edu.carrental.Rental;
import org.eclipse.emf.common.util.EList;

public class CR5Test {
    
    private Store store;
    private CarrentalFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory and create a store instance
        factory = CarrentalFactory.eINSTANCE;
        store = factory.createStore();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create customer C001
        Customer customer = factory.createCustomer();
        customer.setName("C001");
        
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
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        
        Rental rental2 = factory.createRental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        
        Rental rental3 = factory.createRental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method under test
        EList<Map<Customer, Integer>> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
        
        Map<Customer, Integer> rentalCounts_case1 = result.get(0);
        assertEquals("Customer C001 should have 3 rentals", 3, (int)rentalCounts_case1.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create customers C001 and C002
        Customer customer1 = factory.createCustomer();
        customer1.setName("C001");
        Customer customer2 = factory.createCustomer();
        customer2.setName("C002");
        
        // Create cars for customer C001 (2 rentals)
        Car car1 = factory.createCar();
        car1.setPlate("ABC123");
        Car car2 = factory.createCar();
        car2.setPlate("XYZ456");
        
        // Create cars for customer C002 (5 rentals)
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
        
        // Create rental records for customer C001 (2 rentals)
        Rental rental1 = factory.createRental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        
        Rental rental2 = factory.createRental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        
        // Create rental records for customer C002 (5 rentals)
        Rental rental3 = factory.createRental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        
        Rental rental4 = factory.createRental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        
        Rental rental5 = factory.createRental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        
        Rental rental6 = factory.createRental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        
        Rental rental7 = factory.createRental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        
        // Add all rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        store.getRentals().add(rental5);
        store.getRentals().add(rental6);
        store.getRentals().add(rental7);
        
        // Execute the method under test
        EList<Map<Customer, Integer>> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
        
        Map<Customer, Integer> rentalCounts_case2 = result.get(0);
        assertEquals("Customer C001 should have 2 rentals", 2, (int)rentalCounts_case2.get(customer1));
        assertEquals("Customer C002 should have 5 rentals", 5, (int)rentalCounts_case2.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer C003
        Customer customer = factory.createCustomer();
        customer.setName("C003");
        
        // No rental records are added
        
        // Execute the method under test
        EList<Map<Customer, Integer>> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should be an empty map structure
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when no rentals exist", result.isEmpty() || result.get(0).isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create customer C004
        Customer customer = factory.createCustomer();
        customer.setName("C004");
        
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
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        
        Rental rental2 = factory.createRental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        
        Rental rental3 = factory.createRental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        
        Rental rental4 = factory.createRental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        
        // Mark 2 rentals as returned (set back date)
        rental1.setBackDate(new Date());
        rental2.setBackDate(new Date());
        
        // Add all rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        
        // Execute the method under test
        EList<Map<Customer, Integer>> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 4 rentals regardless of return status
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
        
        Map<Customer, Integer> rentalCounts_case4 = result.get(0);
        assertEquals("Customer C004 should have 4 total rentals", 4, (int)rentalCounts_case4.get(customer));
        
        // Verify that 2 rentals are currently active (not returned)
        int activeRentals = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getCustomer() == customer && rental.getBackDate() == null) {
                activeRentals++;
            }
        }
        assertEquals("Customer C004 should have 2 active rentals", 2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create customer C005
        Customer customer = factory.createCustomer();
        customer.setName("C005");
        
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
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        
        Rental rental2 = factory.createRental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        
        Rental rental3 = factory.createRental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        
        // Mark one rental as overdue (due date in the past, back date null)
        rental2.setDueDate(new Date(System.currentTimeMillis() - 86400000)); // 1 day ago
        rental2.setBackDate(null);
        
        // Add all rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method under test
        EList<Map<Customer, Integer>> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 3 rentals regardless of overdue status
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
        
        Map<Customer, Integer> rentalCounts_case5 = result.get(0);
        assertEquals("Customer C005 should have 3 total rentals", 3, (int)rentalCounts_case5.get(customer));
        
        // Verify that 1 rental is overdue
        int overdueRentals = 0;
        Date currentDate = new Date();
        for (Rental rental : store.getRentals()) {
            if (rental.getCustomer() == customer && 
                rental.getBackDate() == null && 
                rental.getDueDate() != null && 
                rental.getDueDate().before(currentDate)) {
                overdueRentals++;
            }
        }
        assertEquals("Customer C005 should have 1 overdue rental", 1, overdueRentals);
    }
}