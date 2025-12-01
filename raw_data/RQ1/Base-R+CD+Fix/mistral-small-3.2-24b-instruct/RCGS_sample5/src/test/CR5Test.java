import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        // SetUp: Create store instance and customer C001
        Customer customerC001 = new Customer();
        customerC001.setName("John");
        customerC001.setSurname("Doe");
        customerC001.setAddress("123 Main St");
        
        // Create 3 cars with different plates
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        
        // Add cars to store
        List<Car> cars = Arrays.asList(car1, car2, car3);
        store.setCars(cars);
        
        // Create 3 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customerC001);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customerC001);
        rental2.setCar(car2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customerC001);
        rental3.setCar(car3);
        
        // Add rentals to store
        List<Rental> rentals = Arrays.asList(rental1, rental2, rental3);
        store.setRentals(rentals);
        
        // Execute method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify expected output: Number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customerC001));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // SetUp: Create store instance and customers C001 and C002
        Customer customerC001 = new Customer();
        customerC001.setName("John");
        customerC001.setSurname("Doe");
        customerC001.setAddress("123 Main St");
        
        Customer customerC002 = new Customer();
        customerC002.setName("Jane");
        customerC002.setSurname("Smith");
        customerC002.setAddress("456 Oak Ave");
        
        // Create cars with specified plates
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("Chevrolet Malibu");
        car4.setDailyPrice(55.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Nissan Altima");
        car5.setDailyPrice(48.0);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Hyundai Elantra");
        car6.setDailyPrice(42.0);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Kia Optima");
        car7.setDailyPrice(46.0);
        
        // Add cars to store
        List<Car> cars = Arrays.asList(car1, car2, car3, car4, car5, car6, car7);
        store.setCars(cars);
        
        // Create rental records: 2 for C001, 5 for C002
        Rental rental1 = new Rental();
        rental1.setCustomer(customerC001);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customerC001);
        rental2.setCar(car2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customerC002);
        rental3.setCar(car3);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customerC002);
        rental4.setCar(car4);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customerC002);
        rental5.setCar(car5);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customerC002);
        rental6.setCar(car6);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customerC002);
        rental7.setCar(car7);
        
        // Add rentals to store
        List<Rental> rentals = Arrays.asList(rental1, rental2, rental3, rental4, rental5, rental6, rental7);
        store.setRentals(rentals);
        
        // Execute method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify expected output
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customerC001)); // C001 has 2 rentals
        assertEquals(Integer.valueOf(5), result.get(customerC002)); // C002 has 5 rentals
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create store instance and customer C003
        Customer customerC003 = new Customer();
        customerC003.setName("Bob");
        customerC003.setSurname("Johnson");
        customerC003.setAddress("789 Pine St");
        
        // No rental records are added for customer C003
        // The store has no rentals
        
        // Execute method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify expected output: Empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws ParseException {
        // SetUp: Create store instance and customer C004
        Customer customerC004 = new Customer();
        customerC004.setName("Alice");
        customerC004.setSurname("Brown");
        customerC004.setAddress("321 Elm St");
        
        // Create cars with specified plates
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota Corolla");
        car1.setDailyPrice(35.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford Mustang");
        car3.setDailyPrice(75.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet Impala");
        car4.setDailyPrice(55.0);
        
        // Add cars to store
        List<Car> cars = Arrays.asList(car1, car2, car3, car4);
        store.setCars(cars);
        
        // Create rental records for customer C004 (4 rentals)
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-10 10:00:00");
        Date backDate = dateFormat.parse("2024-01-08 10:00:00");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customerC004);
        rental1.setCar(car1);
        rental1.setRentalDate(rentalDate);
        rental1.setDueDate(dueDate);
        rental1.setBackDate(backDate); // Mark as returned
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customerC004);
        rental2.setCar(car2);
        rental2.setRentalDate(rentalDate);
        rental2.setDueDate(dueDate);
        rental2.setBackDate(backDate); // Mark as returned
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customerC004);
        rental3.setCar(car3);
        rental3.setRentalDate(rentalDate);
        rental3.setDueDate(dueDate);
        // No back date - still active
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customerC004);
        rental4.setCar(car4);
        rental4.setRentalDate(rentalDate);
        rental4.setDueDate(dueDate);
        // No back date - still active
        
        // Add rentals to store
        List<Rental> rentals = Arrays.asList(rental1, rental2, rental3, rental4);
        store.setRentals(rentals);
        
        // Execute method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify expected output: Number of rentals for customer C004 = 4 (stored)
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customerC004));
        
        // Additional verification: Currently active rentals = 2
        long activeRentals = rentals.stream()
                .filter(rental -> rental.getBackDate() == null)
                .count();
        assertEquals(2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws ParseException {
        // SetUp: Create store instance and customer C005
        Customer customerC005 = new Customer();
        customerC005.setName("Charlie");
        customerC005.setSurname("Wilson");
        customerC005.setAddress("654 Maple Rd");
        
        // Create cars with specified plates
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("BMW 3 Series");
        car1.setDailyPrice(80.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Audi A4");
        car2.setDailyPrice(85.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Mercedes C-Class");
        car3.setDailyPrice(90.0);
        
        // Add cars to store
        List<Car> cars = Arrays.asList(car1, car2, car3);
        store.setCars(cars);
        
        // Create rental records for customer C005 (3 rentals)
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date pastDueDate = dateFormat.parse("2024-01-05 10:00:00"); // Overdue
        Date futureDueDate = dateFormat.parse("2024-02-01 10:00:00"); // Not overdue
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customerC005);
        rental1.setCar(car1);
        rental1.setRentalDate(rentalDate);
        rental1.setDueDate(futureDueDate);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customerC005);
        rental2.setCar(car2);
        rental2.setRentalDate(rentalDate);
        rental2.setDueDate(pastDueDate); // Overdue rental
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customerC005);
        rental3.setCar(car3);
        rental3.setRentalDate(rentalDate);
        rental3.setDueDate(futureDueDate);
        
        // Add rentals to store
        List<Rental> rentals = Arrays.asList(rental1, rental2, rental3);
        store.setRentals(rentals);
        
        // Execute method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify expected output: Number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customerC005));
        
        // Additional verification: Number of overdue rentals for customer C005 = 1
        Date currentDate = dateFormat.parse("2024-01-10 10:00:00");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        assertEquals(1, overdueCustomers.size());
        assertEquals(customerC005, overdueCustomers.get(0));
    }
}