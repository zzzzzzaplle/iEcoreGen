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
        // Create a customer with customer ID: C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        customer.setRentedCarPlate("ABC123");
        
        // Add 3 rental records for customer C001 with different car details
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
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-05 10:00:00"));
        rental2.setTotalPrice(180.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-05 10:00:00"));
        rental3.setTotalPrice(160.0);
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method under test
        Map<Customer, Integer> rentalCounts = store.countCarsRentedPerCustomer();
        
        // Expected Output: Number of rentals for customer C001 = 3
        assertEquals(1, rentalCounts.size());
        assertEquals(Integer.valueOf(3), rentalCounts.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
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
        // Customer C001 rented cars with plates "ABC123", "XYZ456"
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("Nissan Altima");
        car4.setDailyPrice(55.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Chevrolet Malibu");
        car5.setDailyPrice(48.0);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Hyundai Elantra");
        car6.setDailyPrice(42.0);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Kia Optima");
        car7.setDailyPrice(46.0);
        
        // Create rentals for customer1
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-05 10:00:00"));
        rental2.setTotalPrice(180.0);
        
        // Create rentals for customer2
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-05 10:00:00"));
        rental3.setTotalPrice(160.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2023-04-01 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2023-04-05 10:00:00"));
        rental4.setTotalPrice(220.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentalDate(dateFormat.parse("2023-05-01 10:00:00"));
        rental5.setDueDate(dateFormat.parse("2023-05-05 10:00:00"));
        rental5.setTotalPrice(192.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentalDate(dateFormat.parse("2023-06-01 10:00:00"));
        rental6.setDueDate(dateFormat.parse("2023-06-05 10:00:00"));
        rental6.setTotalPrice(168.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentalDate(dateFormat.parse("2023-07-01 10:00:00"));
        rental7.setDueDate(dateFormat.parse("2023-07-05 10:00:00"));
        rental7.setTotalPrice(184.0);
        
        // Add all cars and rentals to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getCars().add(car4);
        store.getCars().add(car5);
        store.getCars().add(car6);
        store.getCars().add(car7);
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        store.getRentals().add(rental5);
        store.getRentals().add(rental6);
        store.getRentals().add(rental7);
        
        // Execute the method under test
        Map<Customer, Integer> rentalCounts = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C001 = 2
        // - Number of rentals for customer C002 = 5
        assertEquals(2, rentalCounts.size());
        assertEquals(Integer.valueOf(2), rentalCounts.get(customer1));
        assertEquals(Integer.valueOf(5), rentalCounts.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        customer.setRentedCarPlate(null); // No car rented
        
        // No rental records are added for customer C003
        store.getCars().add(new Car()); // Add at least one car to avoid empty list issues
        
        // Execute the method under test
        Map<Customer, Integer> rentalCounts = store.countCarsRentedPerCustomer();
        
        // Expected Output: Empty map (not null)
        assertNotNull(rentalCounts);
        assertTrue(rentalCounts.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        customer.setRentedCarPlate("DEF234");
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        // Rental records: Customer C004 rented cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
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
        car4.setModel("Nissan Altima");
        car4.setDailyPrice(55.0);
        
        // Create rentals - mark 2 as returned
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental1.setBackDate(dateFormat.parse("2023-01-04 10:00:00")); // Returned
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-05 10:00:00"));
        rental2.setBackDate(dateFormat.parse("2023-02-03 10:00:00")); // Returned
        rental2.setTotalPrice(180.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-05 10:00:00"));
        rental3.setTotalPrice(160.0); // Not returned
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        rental4.setRentalDate(dateFormat.parse("2023-04-01 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2023-04-05 10:00:00"));
        rental4.setTotalPrice(220.0); // Not returned
        
        // Add all cars and rentals to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getCars().add(car4);
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        
        // Execute the method under test
        Map<Customer, Integer> rentalCounts = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C004 = 4 (stored)
        assertEquals(1, rentalCounts.size());
        assertEquals(Integer.valueOf(4), rentalCounts.get(customer));
        
        // Verify currently active rentals = 2 (after marking 2 as returned)
        int activeRentals = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getCustomer().equals(customer) && rental.getBackDate() == null) {
                activeRentals++;
            }
        }
        assertEquals(2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setAddress("654 Maple St");
        customer.setRentedCarPlate("PQR456");
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        // Rental records: Customer C005 rented cars with plates "PQR456", "STU789", "VWX012" with "STU789" being overdue
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
        
        // Create rentals - one overdue
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-02-05 10:00:00"));
        rental2.setTotalPrice(180.0); // Overdue
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-03-05 10:00:00"));
        rental3.setTotalPrice(160.0);
        
        // Add all cars and rentals to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method under test
        Map<Customer, Integer> rentalCounts = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C005 = 3
        assertEquals(1, rentalCounts.size());
        assertEquals(Integer.valueOf(3), rentalCounts.get(customer));
        
        // Verify number of overdue rentals for customer C005 = 1
        Date currentDate = dateFormat.parse("2023-02-10 10:00:00"); // After due date of rental2
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        int overdueCount = 0;
        for (Customer c : overdueCustomers) {
            if (c.equals(customer)) {
                overdueCount++;
            }
        }
        assertEquals(1, overdueCount);
    }
}