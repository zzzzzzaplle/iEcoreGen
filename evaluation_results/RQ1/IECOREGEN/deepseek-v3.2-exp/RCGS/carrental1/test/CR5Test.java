package edu.carrental.carrental1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import edu.carrental.CarrentalFactory;
import edu.carrental.CarrentalPackage;
import edu.carrental.Store;
import edu.carrental.Car;
import edu.carrental.Customer;
import edu.carrental.Rental;

public class CR5Test {
    
    private Store store;
    private CarrentalFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize the factory and store using Ecore factory pattern
        factory = CarrentalFactory.eINSTANCE;
        store = factory.createStore();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() throws Exception {
        // Create customer C001
        Customer customer = factory.createCustomer();
        
        // Create 3 cars
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
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(dateFormat.parse("2024-01-02 11:00:00"));
        
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setRentalDate(dateFormat.parse("2024-01-03 12:00:00"));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer().get(0);
        
        // Verify the count for customer C001
        assertEquals("Customer should have 3 rentals", Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create customers C001 and C002
        Customer customer1 = factory.createCustomer();
        Customer customer2 = factory.createCustomer();
        
        // Create cars for customer1 (2 rentals)
        Car car1 = factory.createCar();
        car1.setPlate("ABC123");
        
        Car car2 = factory.createCar();
        car2.setPlate("XYZ456");
        
        // Create cars for customer2 (5 rentals)
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
        
        // Create rental records for customer1 (2 rentals)
        Rental rental1 = factory.createRental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer1);
        rental2.setRentalDate(dateFormat.parse("2024-01-02 11:00:00"));
        
        // Create rental records for customer2 (5 rentals)
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setCustomer(customer2);
        rental3.setRentalDate(dateFormat.parse("2024-01-03 12:00:00"));
        
        Rental rental4 = factory.createRental();
        rental4.setCar(car4);
        rental4.setCustomer(customer2);
        rental4.setRentalDate(dateFormat.parse("2024-01-04 13:00:00"));
        
        Rental rental5 = factory.createRental();
        rental5.setCar(car5);
        rental5.setCustomer(customer2);
        rental5.setRentalDate(dateFormat.parse("2024-01-05 14:00:00"));
        
        Rental rental6 = factory.createRental();
        rental6.setCar(car6);
        rental6.setCustomer(customer2);
        rental6.setRentalDate(dateFormat.parse("2024-01-06 15:00:00"));
        
        Rental rental7 = factory.createRental();
        rental7.setCar(car7);
        rental7.setCustomer(customer2);
        rental7.setRentalDate(dateFormat.parse("2024-01-07 16:00:00"));
        
        // Add all rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        store.getRentals().add(rental5);
        store.getRentals().add(rental6);
        store.getRentals().add(rental7);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer().get(0);
        
        // Verify counts for both customers
        assertEquals("Customer1 should have 2 rentals", Integer.valueOf(2), result.get(customer1));
        assertEquals("Customer2 should have 5 rentals", Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer C003
        Customer customer = factory.createCustomer();
        
        // No rental records are added
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer().get(0);
        
        // Verify the result is an empty map (no entries)
        assertTrue("Result map should be empty when no rentals exist", result.isEmpty());
        assertNull("Customer should not be in the result map", result.get(customer));
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
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
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(dateFormat.parse("2024-01-02 11:00:00"));
        
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setRentalDate(dateFormat.parse("2024-01-03 12:00:00"));
        
        Rental rental4 = factory.createRental();
        rental4.setCar(car4);
        rental4.setCustomer(customer);
        rental4.setRentalDate(dateFormat.parse("2024-01-04 13:00:00"));
        
        // Mark 2 rentals as returned
        rental1.setBackDate(dateFormat.parse("2024-01-05 14:00:00"));
        rental2.setBackDate(dateFormat.parse("2024-01-06 15:00:00"));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer().get(0);
        
        // Verify the total count (should be 4 regardless of return status)
        assertEquals("Customer should have 4 total rentals", Integer.valueOf(4), result.get(customer));
        
        // Count currently active rentals (backDate is null)
        int activeRentals = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getCustomer() == customer && rental.getBackDate() == null) {
                activeRentals++;
            }
        }
        assertEquals("Customer should have 2 active rentals", 2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
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
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        
        Rental rental2 = factory.createRental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(dateFormat.parse("2024-01-02 11:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-05 11:00:00")); // Overdue date
        
        Rental rental3 = factory.createRental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setRentalDate(dateFormat.parse("2024-01-03 12:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-15 12:00:00"));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer().get(0);
        
        // Verify the total count
        assertEquals("Customer should have 3 rentals", Integer.valueOf(3), result.get(customer));
        
        // Count overdue rentals (backDate is null and current date is past due date)
        Date currentDate = dateFormat.parse("2024-01-08 14:00:00");
        int overdueRentals = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getCustomer() == customer && 
                rental.getBackDate() == null && 
                currentDate.after(rental.getDueDate())) {
                overdueRentals++;
            }
        }
        assertEquals("Customer should have 1 overdue rental", 1, overdueRentals);
    }
}