import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR5Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C001
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        // Add 3 rental records for customer C001 with different car details
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
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rentals for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer1);
        rental3.setCar(car3);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Expected Output: Number of rentals for customer C001 = 3
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer1));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // SetUp: Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        // Create cars for customer C001 (2 rentals)
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda");
        car2.setDailyPrice(60.0);
        
        // Create cars for customer C002 (5 rentals)
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford");
        car3.setDailyPrice(55.0);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("Chevrolet");
        car4.setDailyPrice(65.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("BMW");
        car5.setDailyPrice(80.0);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Mercedes");
        car6.setDailyPrice(85.0);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Audi");
        car7.setDailyPrice(75.0);
        
        // Add all cars to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4, car5, car6, car7));
        
        // Create rentals for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        
        // Create rentals for customer C002 (5 rentals)
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
        
        // Add all rentals to store
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3, rental4, rental5, rental6, rental7));
        
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
        // SetUp: Create a store instance
        // Create a customer with customer ID: C003
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        // No rental records are added for customer C003
        
        // Expected Output: Null (should return empty map)
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C004
        Customer customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        
        // Create 4 cars for customer C004
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford");
        car3.setDailyPrice(55.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet");
        car4.setDailyPrice(65.0);
        
        // Add cars to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4));
        
        // Create 4 rental records for customer C004
        Rental rental1 = new Rental();
        rental1.setCustomer(customer4);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer4);
        rental2.setCar(car2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer4);
        rental3.setCar(car3);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer4);
        rental4.setCar(car4);
        
        // Mark 2 of them as returned
        try {
            rental1.setBackDate(dateFormat.parse("2024-01-15 10:00:00"));
            rental3.setBackDate(dateFormat.parse("2024-01-20 14:30:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Add rentals to store
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3, rental4));
        
        // Expected Output: 
        // - Number of rentals for customer C004 = 4 (stored)
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer4));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C005
        Customer customer5 = new Customer();
        customer5.setName("Mike");
        customer5.setSurname("Wilson");
        
        // Create 3 cars for customer C005
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford");
        car3.setDailyPrice(55.0);
        
        // Add cars to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3));
        
        // Create 3 rental records for customer C005
        Rental rental1 = new Rental();
        rental1.setCustomer(customer5);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer5);
        rental2.setCar(car2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer5);
        rental3.setCar(car3);
        
        // Mark one as overdue (STU789)
        try {
            Date dueDate = dateFormat.parse("2024-01-10 10:00:00");
            rental2.setDueDate(dueDate);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Add rentals to store
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3));
        
        // Expected Output: 
        // - Number of rentals for customer C005 = 3
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer5));
    }
}