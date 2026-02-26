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
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // Create cars with plates "ABC123", "XYZ456", "LMN789"
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
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental records for customer C001 with different car details
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental2.setTotalPrice(225.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-25 10:00:00"));
        rental3.setTotalPrice(200.0);
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
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
        
        // Add all cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        store.addCar(car5);
        store.addCar(car6);
        store.addCar(car7);
        
        // Add rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental2.setTotalPrice(225.0);
        
        // Add rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-02 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-06 10:00:00"));
        rental3.setTotalPrice(160.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2024-01-07 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-01-12 10:00:00"));
        rental4.setTotalPrice(275.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentalDate(dateFormat.parse("2024-01-13 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2024-01-18 10:00:00"));
        rental5.setTotalPrice(240.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentalDate(dateFormat.parse("2024-01-19 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2024-01-24 10:00:00"));
        rental6.setTotalPrice(210.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentalDate(dateFormat.parse("2024-01-25 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2024-01-30 10:00:00"));
        rental7.setTotalPrice(230.0);
        
        // Add all rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        store.addRental(rental5);
        store.addRental(rental6);
        store.addRental(rental7);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the number of rentals
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
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result is an empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        
        // Create cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet Malibu");
        car4.setDailyPrice(55.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        
        // Add rental records for customer C004 (4 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setBackDate(dateFormat.parse("2024-01-04 10:00:00")); // Returned early
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-06 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setBackDate(dateFormat.parse("2024-01-09 10:00:00")); // Returned early
        rental2.setTotalPrice(180.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-11 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental3.setTotalPrice(160.0); // Not returned yet
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2024-01-16 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental4.setTotalPrice(220.0); // Not returned yet
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the number of rentals for customer C004 = 4 (all stored rentals)
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        
        // Create cars with plates "PQR456", "STU789", "VWX012"
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Add rental records for customer C005 (3 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-01-06 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-10 10:00:00")); // This will be overdue
        rental2.setTotalPrice(180.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-01-11 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental3.setTotalPrice(160.0);
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Additional verification for overdue rentals (using findCustomersWithOverdueRentals)
        Date currentDate = dateFormat.parse("2024-01-12 10:00:00"); // After due date of rental2
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer has overdue rentals
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }
}