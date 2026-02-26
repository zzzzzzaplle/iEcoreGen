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
    public void testCase1_CountRentalsForSingleCustomer() throws Exception {
        // Create customer C001
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        // Create cars with different plates
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
        
        // Create rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer1);
        rental2.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental2.setTotalPrice(225.0);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer1);
        rental3.setRentalDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-25 10:00:00"));
        rental3.setTotalPrice(200.0);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer1));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create customer C001
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        // Create customer C002
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak St");
        
        // Create cars for customer C001
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        
        // Create cars for customer C002
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
        
        // Create rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer1);
        rental2.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental2.setTotalPrice(225.0);
        
        // Create rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer2);
        rental3.setRentalDate(dateFormat.parse("2024-01-02 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-06 10:00:00"));
        rental3.setTotalPrice(160.0);
        
        Rental rental4 = new Rental();
        rental4.setCar(car4);
        rental4.setCustomer(customer2);
        rental4.setRentalDate(dateFormat.parse("2024-01-07 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-01-12 10:00:00"));
        rental4.setTotalPrice(275.0);
        
        Rental rental5 = new Rental();
        rental5.setCar(car5);
        rental5.setCustomer(customer2);
        rental5.setRentalDate(dateFormat.parse("2024-01-13 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2024-01-18 10:00:00"));
        rental5.setTotalPrice(240.0);
        
        Rental rental6 = new Rental();
        rental6.setCar(car6);
        rental6.setCustomer(customer2);
        rental6.setRentalDate(dateFormat.parse("2024-01-19 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2024-01-24 10:00:00"));
        rental6.setTotalPrice(210.0);
        
        Rental rental7 = new Rental();
        rental7.setCar(car7);
        rental7.setCustomer(customer2);
        rental7.setRentalDate(dateFormat.parse("2024-01-25 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2024-01-30 10:00:00"));
        rental7.setTotalPrice(230.0);
        
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
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer C003
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        customer3.setAddress("789 Pine St");
        
        // No rental records are added
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should be empty map, not null
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // Create customer C004
        Customer customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        customer4.setAddress("321 Elm St");
        
        // Create cars
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
        car3.setModel("Ford Fusion");
        car3.setDailyPrice(55.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet Impala");
        car4.setDailyPrice(65.0);
        
        // Create rental records for customer C004 (4 rentals)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer4);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setBackDate(dateFormat.parse("2024-01-04 10:00:00"));
        rental1.setTotalPrice(140.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer4);
        rental2.setRentalDate(dateFormat.parse("2024-01-06 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-11 10:00:00"));
        rental2.setBackDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setTotalPrice(240.0);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer4);
        rental3.setRentalDate(dateFormat.parse("2024-01-12 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-17 10:00:00"));
        rental3.setTotalPrice(275.0);
        
        Rental rental4 = new Rental();
        rental4.setCar(car4);
        rental4.setCustomer(customer4);
        rental4.setRentalDate(dateFormat.parse("2024-01-18 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-01-23 10:00:00"));
        rental4.setTotalPrice(325.0);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        rentals.add(rental4);
        store.setRentals(rentals);
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 4 rentals regardless of return status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer4));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create customer C005
        Customer customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Wilson");
        customer5.setAddress("654 Maple St");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("BMW 3 Series");
        car1.setDailyPrice(80.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Mercedes C-Class");
        car2.setDailyPrice(90.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Audi A4");
        car3.setDailyPrice(85.0);
        
        // Create rental records for customer C005 (3 rentals)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer5);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(320.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer5);
        rental2.setRentalDate(dateFormat.parse("2024-01-06 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setTotalPrice(360.0);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer5);
        rental3.setRentalDate(dateFormat.parse("2024-01-11 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-16 10:00:00"));
        rental3.setTotalPrice(425.0);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 3 rentals regardless of overdue status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer5));
    }
}