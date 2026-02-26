import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR5Test {
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_countRentalsForSingleCustomer() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        customer.setRentedCarPlate("ABC123");
        
        // Add 3 rental records for customer C001 with different car details
        // Add cars with plates "ABC123", "XYZ456", "LMN789" rented by customer C001
        List<Rental> rentals = new ArrayList<>();
        
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
        car3.setDailyPrice(70.0);
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setTotalPrice(150.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setTotalPrice(180.0);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setTotalPrice(210.0);
        
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Expected Output: Number of rentals for customer C001 = 3
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_countRentalsForMultipleCustomers() {
        // SetUp: Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        customer1.setRentedCarPlate("ABC123");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak St");
        customer2.setRentedCarPlate("LMN789");
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        List<Rental> rentals = new ArrayList<>();
        
        // Customer C001 rented cars with plates "ABC123", "XYZ456"
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda");
        car2.setDailyPrice(60.0);
        
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford");
        car3.setDailyPrice(70.0);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("Chevrolet");
        car4.setDailyPrice(80.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Nissan");
        car5.setDailyPrice(65.0);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("BMW");
        car6.setDailyPrice(100.0);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Mercedes");
        car7.setDailyPrice(110.0);
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setTotalPrice(150.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer1);
        rental2.setTotalPrice(180.0);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer2);
        rental3.setTotalPrice(210.0);
        
        Rental rental4 = new Rental();
        rental4.setCar(car4);
        rental4.setCustomer(customer2);
        rental4.setTotalPrice(240.0);
        
        Rental rental5 = new Rental();
        rental5.setCar(car5);
        rental5.setCustomer(customer2);
        rental5.setTotalPrice(195.0);
        
        Rental rental6 = new Rental();
        rental6.setCar(car6);
        rental6.setCustomer(customer2);
        rental6.setTotalPrice(300.0);
        
        Rental rental7 = new Rental();
        rental7.setCar(car7);
        rental7.setCustomer(customer2);
        rental7.setTotalPrice(330.0);
        
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        rentals.add(rental4);
        rentals.add(rental5);
        rentals.add(rental6);
        rentals.add(rental7);
        
        store.setRentals(rentals);
        
        // Expected Output: 
        // - Number of rentals for customer C001 = 2
        // - Number of rentals for customer C002 = 5
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_countRentalsWithNoRecords() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        customer.setRentedCarPlate(null);
        
        // No rental records are added for customer C003
        store.setRentals(new ArrayList<Rental>());
        
        // Expected Output: Empty map (not null)
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_countRentalsIncludingReturnedCars() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        customer.setRentedCarPlate("DEF234");
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        List<Rental> rentals = new ArrayList<>();
        
        // Rental records: Customer C004 rented cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota");
        car1.setDailyPrice(55.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda");
        car2.setDailyPrice(65.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford");
        car3.setDailyPrice(75.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet");
        car4.setDailyPrice(85.0);
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setTotalPrice(165.0);
        // Mark as returned
        rental1.setBackDate(new Date());
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setTotalPrice(195.0);
        // Mark as returned
        rental2.setBackDate(new Date());
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setTotalPrice(225.0);
        // Not returned (backDate remains null)
        
        Rental rental4 = new Rental();
        rental4.setCar(car4);
        rental4.setCustomer(customer);
        rental4.setTotalPrice(255.0);
        // Not returned (backDate remains null)
        
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        rentals.add(rental4);
        
        store.setRentals(rentals);
        
        // Expected Output: 
        // - Number of rentals for customer C004 = 4 (stored) 
        // The method should count all rentals regardless of return status
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
    }
    
    @Test
    public void testCase5_countRentalsForCustomerWithOverdueCars() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setAddress("654 Cedar St");
        customer.setRentedCarPlate("PQR456");
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        List<Rental> rentals = new ArrayList<>();
        
        // Rental records: Customer C005 rented cars with plates "PQR456", "STU789", "VWX012"
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota");
        car1.setDailyPrice(60.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda");
        car2.setDailyPrice(70.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford");
        car3.setDailyPrice(80.0);
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setTotalPrice(180.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setTotalPrice(210.0);
        // Mark as overdue (backDate is null and dueDate is in the past)
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            rental2.setDueDate(sdf.parse("2023-01-01 12:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setTotalPrice(240.0);
        
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Expected Output: 
        // - Number of rentals for customer C005 = 3.
        // The method should count all rentals regardless of overdue status
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
}