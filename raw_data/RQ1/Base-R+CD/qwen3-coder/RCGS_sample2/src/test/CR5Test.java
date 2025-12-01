import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR5Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_countRentalsForSingleCustomer() throws ParseException {
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // Create cars
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
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental1.setTotalPrice(450.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-01-15 10:00:00"));
        rental2.setTotalPrice(600.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-20 10:00:00"));
        rental3.setTotalPrice(700.0);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_countRentalsForMultipleCustomers() throws ParseException {
        // Create customers
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        // Create cars for customer 1
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda");
        car2.setDailyPrice(60.0);
        
        // Create cars for customer 2
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford");
        car3.setDailyPrice(70.0);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("BMW");
        car4.setDailyPrice(100.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Audi");
        car5.setDailyPrice(120.0);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Mercedes");
        car6.setDailyPrice(150.0);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Tesla");
        car7.setDailyPrice(200.0);
        
        // Add all cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getCars().add(car4);
        store.getCars().add(car5);
        store.getCars().add(car6);
        store.getCars().add(car7);
        
        // Create rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental1.setTotalPrice(450.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-01-15 10:00:00"));
        rental2.setTotalPrice(600.0);
        
        // Create rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-01-02 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-12 10:00:00"));
        rental3.setTotalPrice(700.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2023-01-03 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2023-01-13 10:00:00"));
        rental4.setTotalPrice(1000.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentalDate(dateFormat.parse("2023-01-04 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2023-01-14 10:00:00"));
        rental5.setTotalPrice(1200.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentalDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2023-01-15 10:00:00"));
        rental6.setTotalPrice(1500.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentalDate(dateFormat.parse("2023-01-06 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2023-01-16 10:00:00"));
        rental7.setTotalPrice(2000.0);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        store.getRentals().add(rental5);
        store.getRentals().add(rental6);
        store.getRentals().add(rental7);
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey(customer1));
        assertTrue(result.containsKey(customer2));
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_countRentalsWithNoRecords() {
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        
        // No rental records are added
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should return empty map, not null
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_countRentalsIncludingReturnedCars() throws ParseException {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Nissan");
        car1.setDailyPrice(55.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Hyundai");
        car2.setDailyPrice(65.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Kia");
        car3.setDailyPrice(75.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Mazda");
        car4.setDailyPrice(85.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getCars().add(car4);
        
        // Create rental records for customer C004 (4 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-02-10 10:00:00"));
        rental1.setBackDate(dateFormat.parse("2023-02-09 10:00:00")); // Returned
        rental1.setTotalPrice(495.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-02 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-12 10:00:00"));
        rental2.setBackDate(dateFormat.parse("2023-02-11 10:00:00")); // Returned
        rental2.setTotalPrice(650.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-02-03 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-02-13 10:00:00"));
        rental3.setTotalPrice(750.0); // Not returned
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2023-02-04 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2023-02-14 10:00:00"));
        rental4.setTotalPrice(850.0); // Not returned
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - all rentals are counted, including returned ones
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(Integer.valueOf(4), result.get(customer)); // All 4 rentals counted
    }
    
    @Test
    public void testCase5_countRentalsForCustomerWithOverdueCars() throws ParseException {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Subaru");
        car1.setDailyPrice(90.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Mitsubishi");
        car2.setDailyPrice(80.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Lexus");
        car3.setDailyPrice(110.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rental records for customer C005 (3 rentals)
        Date currentDate = dateFormat.parse("2023-03-15 10:00:00");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-03-10 10:00:00"));
        rental1.setTotalPrice(900.0); // Overdue rental
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-03-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-03-20 10:00:00"));
        rental2.setTotalPrice(1200.0); // Not overdue
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-03 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-12 10:00:00"));
        rental3.setTotalPrice(1210.0); // Overdue rental
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - all rentals are counted regardless of overdue status
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(Integer.valueOf(3), result.get(customer)); // All 3 rentals counted
    }
}