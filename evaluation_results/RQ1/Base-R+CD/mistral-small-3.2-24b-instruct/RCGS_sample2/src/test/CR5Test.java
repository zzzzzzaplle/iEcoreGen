import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
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
    public void testCase1_CountRentalsForSingleCustomer() throws Exception {
        // Create a customer with ID C001
        Customer customer = new Customer();
        customer.setName("Test");
        customer.setSurname("Customer");
        customer.setRentedCarPlate("ABC123");
        
        // Create 3 cars with different plates
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setDailyPrice(70.0);
        
        // Create 3 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-05 10:00:00"));
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-05 10:00:00"));
        
        // Set up store with cars and rentals
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create customers C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("Customer");
        customer1.setSurname("One");
        customer1.setRentedCarPlate("ABC123");
        
        Customer customer2 = new Customer();
        customer2.setName("Customer");
        customer2.setSurname("Two");
        customer2.setRentedCarPlate("LMN789");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setDailyPrice(70.0);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setDailyPrice(80.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setDailyPrice(90.0);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setDailyPrice(100.0);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setDailyPrice(110.0);
        
        // Create rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-05 10:00:00"));
        
        // Create rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-05 10:00:00"));
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2023-04-01 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2023-04-05 10:00:00"));
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentalDate(dateFormat.parse("2023-05-01 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2023-05-05 10:00:00"));
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentalDate(dateFormat.parse("2023-06-01 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2023-06-05 10:00:00"));
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentalDate(dateFormat.parse("2023-07-01 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2023-07-05 10:00:00"));
        
        // Set up store with cars and rentals
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car5);
        cars.add(car6);
        cars.add(car7);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        rentals.add(rental4);
        rentals.add(rental5);
        rentals.add(rental6);
        rentals.add(rental7);
        store.setRentals(rentals);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() throws Exception {
        // Create a customer with ID C003
        Customer customer = new Customer();
        customer.setName("Customer");
        customer.setSurname("Three");
        
        // Set up store with customer but no rentals
        List<Car> cars = new ArrayList<>();
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        store.setRentals(rentals);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result is an empty map (not null)
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // Create a customer with ID C004
        Customer customer = new Customer();
        customer.setName("Customer");
        customer.setSurname("Four");
        customer.setRentedCarPlate("DEF234");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setDailyPrice(70.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setDailyPrice(80.0);
        
        // Create 4 rental records for customer C004, mark 2 as returned
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental1.setBackDate(dateFormat.parse("2023-01-04 10:00:00")); // Returned
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-05 10:00:00"));
        rental2.setBackDate(dateFormat.parse("2023-02-04 10:00:00")); // Returned
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-05 10:00:00"));
        // Not returned
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2023-04-01 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2023-04-05 10:00:00"));
        // Not returned
        
        // Set up store with cars and rentals
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        rentals.add(rental4);
        store.setRentals(rentals);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 4 rentals regardless of return status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create a customer with ID C005
        Customer customer = new Customer();
        customer.setName("Customer");
        customer.setSurname("Five");
        customer.setRentedCarPlate("PQR456");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setDailyPrice(70.0);
        
        // Create 3 rental records for customer C005, with one overdue
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-05 10:00:00")); // Overdue
        // backDate is null
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-05 10:00:00"));
        
        // Set up store with cars and rentals
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 3 rentals regardless of overdue status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
}