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
        // SetUp: Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        
        // Add 3 rental records for customer C001 with different car details
        // Add cars with plates "ABC123", "XYZ456", "LMN789" rented by customer C001
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        store.addCar(car3);
        
        // Create rentals for customer C001
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-05 10:00:00"));
        rental2.setTotalPrice(180.0);
        store.getRentals().add(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setRentalDate(dateFormat.parse("2024-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-03-05 10:00:00"));
        rental3.setTotalPrice(160.0);
        store.getRentals().add(rental3);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // SetUp: Create a store instance
        Store store = new Store();
        
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        // Customer C001 rented cars with plates "ABC123", "XYZ456"
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        store.addCar(car2);
        
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        store.addCar(car3);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("Chevrolet Malibu");
        car4.setDailyPrice(55.0);
        store.addCar(car4);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Nissan Altima");
        car5.setDailyPrice(48.0);
        store.addCar(car5);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Hyundai Elantra");
        car6.setDailyPrice(42.0);
        store.addCar(car6);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Kia Optima");
        car7.setDailyPrice(46.0);
        store.addCar(car7);
        
        // Create rentals for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer1);
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-05 10:00:00"));
        rental2.setTotalPrice(180.0);
        store.getRentals().add(rental2);
        
        // Create rentals for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer2);
        rental3.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental3.setTotalPrice(200.0);
        store.getRentals().add(rental3);
        
        Rental rental4 = new Rental();
        rental4.setCar(car4);
        rental4.setCustomer(customer2);
        rental4.setRentalDate(dateFormat.parse("2024-02-10 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-02-15 10:00:00"));
        rental4.setTotalPrice(275.0);
        store.getRentals().add(rental4);
        
        Rental rental5 = new Rental();
        rental5.setCar(car5);
        rental5.setCustomer(customer2);
        rental5.setRentalDate(dateFormat.parse("2024-03-10 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2024-03-15 10:00:00"));
        rental5.setTotalPrice(240.0);
        store.getRentals().add(rental5);
        
        Rental rental6 = new Rental();
        rental6.setCar(car6);
        rental6.setCustomer(customer2);
        rental6.setRentalDate(dateFormat.parse("2024-04-10 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2024-04-15 10:00:00"));
        rental6.setTotalPrice(210.0);
        store.getRentals().add(rental6);
        
        Rental rental7 = new Rental();
        rental7.setCar(car7);
        rental7.setCustomer(customer2);
        rental7.setRentalDate(dateFormat.parse("2024-05-10 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2024-05-15 10:00:00"));
        rental7.setTotalPrice(230.0);
        store.getRentals().add(rental7);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C001 = 2, customer C002 = 5
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        
        // No rental records are added for customer C003
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Expected Output: Empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // SetUp: Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        // Rental records: Customer C004 rented cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        store.addCar(car3);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet Malibu");
        car4.setDailyPrice(55.0);
        store.addCar(car4);
        
        // Create 4 rental records for customer C004
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        rental1.setBackDate(dateFormat.parse("2024-01-04 10:00:00")); // Marked as returned
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-05 10:00:00"));
        rental2.setTotalPrice(180.0);
        rental2.setBackDate(dateFormat.parse("2024-02-03 10:00:00")); // Marked as returned
        store.getRentals().add(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setRentalDate(dateFormat.parse("2024-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-03-05 10:00:00"));
        rental3.setTotalPrice(160.0);
        // Not returned - active rental
        store.getRentals().add(rental3);
        
        Rental rental4 = new Rental();
        rental4.setCar(car4);
        rental4.setCustomer(customer);
        rental4.setRentalDate(dateFormat.parse("2024-04-01 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-04-05 10:00:00"));
        rental4.setTotalPrice(220.0);
        // Not returned - active rental
        store.getRentals().add(rental4);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C004 = 4 (stored)
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
        
        // Verify: Currently active rentals = 2 (after marking 2 as returned)
        long activeRentals = store.getRentals().stream()
            .filter(Rental::isActive)
            .count();
        assertEquals(2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // SetUp: Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setAddress("654 Maple Dr");
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        // Rental records: Customer C005 rented cars with plates "PQR456", "STU789", "VWX012"
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        store.addCar(car3);
        
        // Create 3 rental records for customer C005
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-05 10:00:00"));
        rental2.setTotalPrice(180.0);
        // This rental will be overdue when checked with current date after due date
        store.getRentals().add(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setRentalDate(dateFormat.parse("2024-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-03-05 10:00:00"));
        rental3.setTotalPrice(160.0);
        store.getRentals().add(rental3);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Verify: Number of overdue rentals for customer C005 = 1
        Date currentDate = dateFormat.parse("2024-02-10 10:00:00"); // After due date of rental2
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
        
        long overdueCount = store.getRentals().stream()
            .filter(rental -> rental.isOverdue(currentDate))
            .count();
        assertEquals(1, overdueCount);
    }
}