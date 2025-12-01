import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        
        // Add 3 rental records for customer C001 with different car details
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setDailyPrice(70.0);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setTotalPrice(150.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setTotalPrice(180.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setTotalPrice(210.0);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the expected output: Number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create a store instance
        Store store = new Store();
        
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        List<Rental> rentals = new ArrayList<>();
        
        // Customer C001 rentals
        Car car1 = new Car();
        car1.setPlate("ABC123");
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rentals.add(rental1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rentals.add(rental2);
        
        // Customer C002 rentals
        Car car3 = new Car();
        car3.setPlate("LMN789");
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rentals.add(rental3);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rentals.add(rental4);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rentals.add(rental5);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rentals.add(rental6);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rentals.add(rental7);
        
        store.setRentals(rentals);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the expected output
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine Rd");
        
        // No rental records are added for customer C003
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the expected output: Empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        List<Rental> rentals = new ArrayList<>();
        
        Car car1 = new Car();
        car1.setPlate("DEF234");
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(new Date()); // Marked as returned
        rentals.add(rental1);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(new Date()); // Marked as returned
        rentals.add(rental2);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        // Not returned (backDate is null)
        rentals.add(rental3);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        // Not returned (backDate is null)
        rentals.add(rental4);
        
        store.setRentals(rentals);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the expected output: Number of rentals for customer C004 = 4 (stored)
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
        
        // Count currently active rentals (backDate is null)
        int activeRentals = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getCustomer().equals(customer) && rental.getBackDate() == null) {
                activeRentals++;
            }
        }
        assertEquals(2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setAddress("654 Maple Dr");
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        List<Rental> rentals = new ArrayList<>();
        
        Car car1 = new Car();
        car1.setPlate("PQR456");
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setDueDate(new Date(System.currentTimeMillis() + 86400000)); // Due tomorrow
        rentals.add(rental1);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setDueDate(new Date(System.currentTimeMillis() - 86400000)); // Due yesterday (overdue)
        rentals.add(rental2);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setDueDate(new Date(System.currentTimeMillis() + 172800000)); // Due in 2 days
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the expected output: Number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Count overdue rentals for customer C005
        Date currentDate = new Date();
        int overdueRentals = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getCustomer().equals(customer) && 
                rental.getBackDate() == null && 
                rental.getDueDate() != null && 
                currentDate.after(rental.getDueDate())) {
                overdueRentals++;
            }
        }
        assertEquals(1, overdueRentals);
    }
}