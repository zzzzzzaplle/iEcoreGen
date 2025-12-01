import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR5Test {
    
    private Store store;
    private Customer customer1, customer2, customer3, customer4, customer5;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        
        customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Wilson");
        
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_countRentalsForSingleCustomer() throws ParseException {
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
        
        // Create rentals for customer C001 (3 rentals)
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
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer1);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-20 10:00:00"));
        rental3.setTotalPrice(700.0);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Execute method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer1));
        assertEquals(Integer.valueOf(3), result.get(customer1));
    }
    
    @Test
    public void testCase2_countRentalsForMultipleCustomers() throws ParseException {
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
        
        // Create rentals for customer C001 (2 rentals)
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
        
        // Create rentals for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-20 10:00:00"));
        rental3.setTotalPrice(700.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2023-01-15 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2023-01-25 10:00:00"));
        rental4.setTotalPrice(1000.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentalDate(dateFormat.parse("2023-01-20 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2023-01-30 10:00:00"));
        rental5.setTotalPrice(1200.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentalDate(dateFormat.parse("2023-01-25 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2023-02-05 10:00:00"));
        rental6.setTotalPrice(1500.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2023-02-10 10:00:00"));
        rental7.setTotalPrice(2000.0);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        rentals.add(rental4);
        rentals.add(rental5);
        rentals.add(rental6);
        rentals.add(rental7);
        store.setRentals(rentals);
        
        // Execute method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey(customer1));
        assertTrue(result.containsKey(customer2));
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_countRentalsWithNoRecords() {
        // Create store with no rentals
        store.setRentals(new ArrayList<>());
        
        // Execute method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - should return empty map, not null
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_countRentalsIncludingReturnedCars() throws ParseException {
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
        
        // Create rentals for customer C004 (4 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer4);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental1.setTotalPrice(495.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer4);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-01-15 10:00:00"));
        rental2.setTotalPrice(650.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer4);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-20 10:00:00"));
        rental3.setTotalPrice(750.0);
        rental3.setBackDate(dateFormat.parse("2023-01-18 10:00:00")); // Returned
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer4);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2023-01-15 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2023-01-25 10:00:00"));
        rental4.setTotalPrice(850.0);
        rental4.setBackDate(dateFormat.parse("2023-01-23 10:00:00")); // Returned
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        rentals.add(rental4);
        store.setRentals(rentals);
        
        // Execute method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - all 4 rentals should be counted, regardless of return status
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer4));
        assertEquals(Integer.valueOf(4), result.get(customer4));
    }
    
    @Test
    public void testCase5_countRentalsForCustomerWithOverdueCars() throws ParseException {
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
        
        // Create rentals for customer C005 (3 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer5);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental1.setTotalPrice(450.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer5);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-01-15 10:00:00"));
        rental2.setTotalPrice(400.0);
        // This rental is overdue (no back date and due date has passed)
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer5);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-20 10:00:00"));
        rental3.setTotalPrice(550.0);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Execute method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - all 3 rentals should be counted
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer5));
        assertEquals(Integer.valueOf(3), result.get(customer5));
    }
}