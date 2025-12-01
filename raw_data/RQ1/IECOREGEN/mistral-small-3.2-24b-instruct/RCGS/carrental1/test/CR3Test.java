package edu.carrental.carrental1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import edu.carrental.CarrentalFactory;
import edu.carrental.CarrentalPackage;
import edu.carrental.Store;
import edu.carrental.Customer;
import edu.carrental.Rental;
import edu.carrental.OverdueNotice;
import edu.carrental.Car;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

public class CR3Test {
    
    private Store store;
    private CarrentalFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize the factory and store using Ecore factory pattern
        factory = CarrentalFactory.eINSTANCE;
        store = factory.createStore();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() throws Exception {
        // Create customer C001
        Customer customer = factory.createCustomer();
        customer.setName("John Doe");
        
        // Create car for rental
        Car car = factory.createCar();
        car.setPlate("CAR001");
        
        // Create rental R001 with overdue conditions
        Rental rental = factory.createRental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null); // Not returned
        rental.setDueDate(dateFormat.parse("2023-10-01")); // Past due date
        
        // Create overdue notice for customer C001
        OverdueNotice notice = factory.createOverdueNotice();
        notice.setCustomer(customer);
        
        // Add objects to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        store.getNotices().add(notice);
        
        // Set current date to "2023-10-05" (after due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method under test
        EList<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertNotNull("Overdue customers list should not be null", overdueCustomers);
        assertEquals("Should find exactly 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be in overdue list", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = factory.createCustomer();
        customer.setName("Jane Smith");
        
        // Create car for rental
        Car car = factory.createCar();
        car.setPlate("CAR002");
        
        // Create rental R002 with future due date
        Rental rental = factory.createRental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null); // Not returned
        rental.setDueDate(dateFormat.parse("2025-10-10")); // Future due date
        
        // Add objects to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date to "2023-10-12" (before due date)
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute the method under test
        EList<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertNotNull("Overdue customers list should not be null", overdueCustomers);
        assertTrue("Should return empty list when no overdue rentals exist", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = factory.createCustomer();
        customer.setName("Alice Johnson");
        
        // Create cars for rentals
        Car car1 = factory.createCar();
        car1.setPlate("CAR003");
        Car car2 = factory.createCar();
        car2.setPlate("CAR004");
        
        // Create rental R003 with overdue conditions
        Rental rental1 = factory.createRental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(null); // Not returned
        rental1.setDueDate(dateFormat.parse("2023-10-03")); // Past due date
        
        // Create rental R004 with back date (returned)
        Rental rental2 = factory.createRental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Returned
        rental2.setDueDate(dateFormat.parse("2024-10-02")); // Future due date
        
        // Add objects to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to "2023-10-05" (after R003 due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method under test
        EList<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertNotNull("Overdue customers list should not be null", overdueCustomers);
        assertEquals("Should find exactly 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be in overdue list for rental R003", overdueCustomers.contains(customer));
        // Rental R004 is not overdue because it has a back date
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = factory.createCustomer();
        customer.setName("Bob Brown");
        
        // Create car for rental
        Car car = factory.createCar();
        car.setPlate("CAR005");
        
        // Create rental R005 with back date (returned)
        Rental rental = factory.createRental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Returned
        rental.setDueDate(dateFormat.parse("2023-10-03")); // Due date before return date
        
        // Add objects to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Execute the method under test with any current date
        Date currentDate = dateFormat.parse("2023-10-05");
        EList<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertNotNull("Overdue customers list should not be null", overdueCustomers);
        assertTrue("Should return empty list when rental has back date", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = factory.createCustomer();
        customer.setName("Charlie Green");
        
        // Create car for rental
        Car car = factory.createCar();
        car.setPlate("CAR006");
        
        // Create rental R006 with future due date
        Rental rental = factory.createRental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null); // Not returned
        rental.setDueDate(dateFormat.parse("2025-10-15")); // Future due date
        
        // Add objects to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date to "2023-10-05" (before due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method under test
        EList<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertNotNull("Overdue customers list should not be null", overdueCustomers);
        assertTrue("Should return empty list for future due date rentals", overdueCustomers.isEmpty());
    }
}