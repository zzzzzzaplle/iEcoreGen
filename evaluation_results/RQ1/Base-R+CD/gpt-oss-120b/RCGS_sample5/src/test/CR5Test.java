import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
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
        
        // Add 3 rental records for customer C001 with different car details
        // Add cars with plates "ABC123", "XYZ456", "LMN789" rented by customer C001
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford");
        car3.setDailyPrice(55.0);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Expected Output: Number of rentals for customer C001 = 3
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
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
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        // Customer C001 rented cars with plates "ABC123", "XYZ456"
        Car car1 = new Car();
        car1.setPlate("ABC123");
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        Car car3 = new Car();
        car3.setPlate("LMN789");
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        Car car5 = new Car();
        car5.setPlate("RST345");
        Car car6 = new Car();
        car6.setPlate("UVW678");
        Car car7 = new Car();
        car7.setPlate("JKL901");
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        store.getRentals().add(rental5);
        store.getRentals().add(rental6);
        store.getRentals().add(rental7);
        
        // Expected Output: 
        // - Number of rentals for customer C001 = 2
        // - Number of rentals for customer C002 = 5
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
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
        
        // No rental records are added for customer C003
        
        // Expected Output: Empty map (not null)
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
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
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        // Rental records: Customer C004 rented cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Car car1 = new Car();
        car1.setPlate("DEF234");
        Car car2 = new Car();
        car2.setPlate("GHI567");
        Car car3 = new Car();
        car3.setPlate("JKL890");
        Car car4 = new Car();
        car4.setPlate("MNO123");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(new Date()); // Mark as returned
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(new Date()); // Mark as returned
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        // Not returned (backDate remains null)
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        // Not returned (backDate remains null)
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        
        // Expected Output: 
        // - Number of rentals for customer C004 = 4 (stored)
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
        
        // Verify currently active rentals = 2 (after marking 2 as returned)
        List<Car> availableCars = store.identifyAvailableCars();
        // This is just to verify the setup - the main test is for countCarsRentedPerCustomer
        assertEquals(0, availableCars.size()); // No cars in cars list, so none available
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        // Rental records: Customer C005 rented cars with plates "PQR456", "STU789", "VWX012" with "STU789" being overdue
        Car car1 = new Car();
        car1.setPlate("PQR456");
        Car car2 = new Car();
        car2.setPlate("STU789");
        Car car3 = new Car();
        car3.setPlate("VWX012");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        // Set due date in the past to make it overdue
        rental2.setDueDate(new Date(System.currentTimeMillis() - 86400000)); // 1 day ago
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Expected Output: 
        // - Number of rentals for customer C005 = 3
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Verify number of overdue rentals for customer C005 = 1
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(new Date());
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
    }
}