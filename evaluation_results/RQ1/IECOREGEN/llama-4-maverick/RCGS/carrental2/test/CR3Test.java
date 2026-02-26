package edu.carrental.carrental2.test;

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
import org.eclipse.emf.common.util.EList;

public class CR3Test {
    
    private Store store;
    private CarrentalFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = CarrentalFactory.eINSTANCE;
        store = factory.createStore();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() throws Exception {
        // Create customer C001
        Customer customer = factory.createCustomer();
        customer.setName("John Doe");
        
        // Create rental R001 with overdue status
        Rental rental = factory.createRental();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        
        // Add customer and rental to store
        store.getRentals().add(rental);
        
        // Set current date to overdue date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method under test
        EList<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be John Doe", customer, overdueCustomers.get(0));
        
        // Create overdue notice for verification
        OverdueNotice notice = factory.createOverdueNotice();
        notice.setCustomer(customer);
        store.getNotices().add(notice);
        
        assertNotNull("Overdue notice should be created", store.getNotices().get(0));
        assertEquals("Notice should be for customer C001", customer, store.getNotices().get(0).getCustomer());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = factory.createCustomer();
        customer.setName("Jane Smith");
        
        // Create rental R002 with future due date
        Rental rental = factory.createRental();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        
        // Add customer and rental to store
        store.getRentals().add(rental);
        
        // Set current date to before due date
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute the method under test
        EList<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue rentals found
        assertTrue("Should not find any overdue customers", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = factory.createCustomer();
        customer.setName("Alice Johnson");
        
        // Create overdue rental R003
        Rental rental1 = factory.createRental();
        rental1.setCustomer(customer);
        rental1.setBackDate(null); // Not returned yet
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        
        // Create returned rental R004 (not overdue)
        Rental rental2 = factory.createRental();
        rental2.setCustomer(customer);
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Already returned
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to after R003 due date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method under test
        EList<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify only R003 is marked as overdue
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be Alice Johnson", customer, overdueCustomers.get(0));
        
        // Verify rental1 is overdue (backDate null and current date after due date)
        assertNull("Rental R003 should not have back date", rental1.getBackDate());
        assertTrue("Rental R003 should be overdue", currentDate.after(rental1.getDueDate()));
        
        // Verify rental2 is not overdue (has back date)
        assertNotNull("Rental R004 should have back date", rental2.getBackDate());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = factory.createCustomer();
        customer.setName("Bob Brown");
        
        // Create rental R005 that was returned on time
        Rental rental = factory.createRental();
        rental.setCustomer(customer);
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Returned
        rental.setDueDate(dateFormat.parse("2023-10-03")); // Due date before return date
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set any current date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method under test
        EList<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue rentals found since rental has back date
        assertTrue("Should not find any overdue customers when rental has back date", overdueCustomers.isEmpty());
        
        // Additional verification: rental has back date so cannot be overdue
        assertNotNull("Rental should have back date", rental.getBackDate());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = factory.createCustomer();
        customer.setName("Charlie Green");
        
        // Create rental R006 with future due date
        Rental rental = factory.createRental();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Not returned yet
        rental.setDueDate(dateFormat.parse("2025-10-15")); // Future due date
        
        // Add rental to store
        store.getRentals().add(rental);
        
        // Set current date to before due date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute the method under test
        EList<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue rentals found since due date is in future
        assertTrue("Should not find any overdue customers for future due dates", overdueCustomers.isEmpty());
        
        // Additional verification: current date is before due date
        assertTrue("Current date should be before due date", currentDate.before(rental.getDueDate()));
    }
}