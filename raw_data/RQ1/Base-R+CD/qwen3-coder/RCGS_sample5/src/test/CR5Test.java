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
    public void testCase1_CountRentalsForSingleCustomer() throws ParseException {
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Model1");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Model2");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Model3");
        car3.setDailyPrice(70.0);
        
        // Create rentals for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental1.setTotalPrice(450.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-01-02 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-01-12 10:00:00"));
        rental2.setTotalPrice(600.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-01-03 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-15 10:00:00"));
        rental3.setTotalPrice(800.0);
        
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
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws ParseException {
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
        car1.setModel("Model1");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Model2");
        car2.setDailyPrice(60.0);
        
        // Create cars for customer 2
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Model3");
        car3.setDailyPrice(70.0);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("Model4");
        car4.setDailyPrice(80.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Model5");
        car5.setDailyPrice(90.0);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Model6");
        car6.setDailyPrice(100.0);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Model7");
        car7.setDailyPrice(110.0);
        
        // Create rentals for customer 1 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental1.setTotalPrice(450.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-01-02 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-01-12 10:00:00"));
        rental2.setTotalPrice(600.0);
        
        // Create rentals for customer 2 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-01-03 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-15 10:00:00"));
        rental3.setTotalPrice(800.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2023-01-04 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2023-01-18 10:00:00"));
        rental4.setTotalPrice(1200.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentalDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2023-01-20 10:00:00"));
        rental5.setTotalPrice(1350.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentalDate(dateFormat.parse("2023-01-06 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2023-01-22 10:00:00"));
        rental6.setTotalPrice(1500.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentalDate(dateFormat.parse("2023-01-07 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2023-01-25 10:00:00"));
        rental7.setTotalPrice(1650.0);
        
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
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // No rentals added to store
        store.setRentals(new ArrayList<>());
        
        // Execute method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - empty map is returned, not null
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws ParseException {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Model1");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Model2");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Model3");
        car3.setDailyPrice(70.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Model4");
        car4.setDailyPrice(80.0);
        
        // Create rentals for customer C004 (4 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental1.setTotalPrice(450.0);
        // This rental is still active (backDate is null)
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-01-02 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-01-12 10:00:00"));
        rental2.setBackDate(dateFormat.parse("2023-01-11 10:00:00")); // Returned
        rental2.setTotalPrice(600.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-01-03 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-15 10:00:00"));
        rental3.setBackDate(dateFormat.parse("2023-01-14 10:00:00")); // Returned
        rental3.setTotalPrice(800.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2023-01-04 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2023-01-18 10:00:00"));
        rental4.setTotalPrice(1200.0);
        // This rental is still active (backDate is null)
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        rentals.add(rental4);
        store.setRentals(rentals);
        
        // Execute method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - all 4 rentals are counted regardless of return status
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws ParseException {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Model1");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Model2");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Model3");
        car3.setDailyPrice(70.0);
        
        // Create rentals for customer C005 (3 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental1.setTotalPrice(450.0);
        // Not overdue
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-01-02 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental2.setTotalPrice(300.0);
        // Overdue (no back date and past due date)
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-01-03 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-15 10:00:00"));
        rental3.setTotalPrice(700.0);
        // Not overdue
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Execute method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - all 3 rentals are counted
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
}