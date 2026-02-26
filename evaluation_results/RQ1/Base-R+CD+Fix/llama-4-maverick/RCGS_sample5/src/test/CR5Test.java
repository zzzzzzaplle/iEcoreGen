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
        // Create a store instance
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
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(45.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rentals for customer C001
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental2.setTotalPrice(200.0);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setRentalDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-25 10:00:00"));
        rental3.setTotalPrice(225.0);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> rentalCounts = store.countCarsRentedPerCustomer();
        
        // Verify the number of rentals for customer C001 = 3
        assertEquals(1, rentalCounts.size());
        assertEquals(Integer.valueOf(3), rentalCounts.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create a store instance
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
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(45.0);
        
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
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getCars().add(car4);
        store.getCars().add(car5);
        store.getCars().add(car6);
        store.getCars().add(car7);
        
        // Create 2 rentals for customer C001
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
        rental2.setTotalPrice(200.0);
        
        // Create 5 rentals for customer C002
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer2);
        rental3.setRentalDate(dateFormat.parse("2024-01-02 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-06 10:00:00"));
        rental3.setTotalPrice(180.0);
        
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
        
        // Add all rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        store.getRentals().add(rental5);
        store.getRentals().add(rental6);
        store.getRentals().add(rental7);
        
        // Count rentals per customer
        Map<Customer, Integer> rentalCounts = store.countCarsRentedPerCustomer();
        
        // Verify the number of rentals
        assertEquals(2, rentalCounts.size());
        assertEquals(Integer.valueOf(2), rentalCounts.get(customer1)); // Number of rentals for customer C001 = 2
        assertEquals(Integer.valueOf(5), rentalCounts.get(customer2)); // Number of rentals for customer C002 = 5
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        
        // No rental records are added for customer C003
        
        // Count rentals per customer
        Map<Customer, Integer> rentalCounts = store.countCarsRentedPerCustomer();
        
        // Expected Output: Null - but actually should be an empty map according to specification
        assertTrue(rentalCounts.isEmpty());
        assertEquals(0, rentalCounts.size());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // Create a store instance
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
        car1.setModel("Toyota Corolla");
        car1.setDailyPrice(35.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford Fusion");
        car3.setDailyPrice(52.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet Impala");
        car4.setDailyPrice(65.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getCars().add(car4);
        
        // Create 4 rentals for customer C004
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setBackDate(dateFormat.parse("2024-01-05 09:00:00")); // Marked as returned
        rental1.setTotalPrice(140.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(dateFormat.parse("2024-01-06 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental2.setBackDate(dateFormat.parse("2024-01-10 09:00:00")); // Marked as returned
        rental2.setTotalPrice(240.0);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setRentalDate(dateFormat.parse("2024-01-11 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental3.setTotalPrice(208.0); // Not returned
        
        Rental rental4 = new Rental();
        rental4.setCar(car4);
        rental4.setCustomer(customer);
        rental4.setRentalDate(dateFormat.parse("2024-01-16 10:00:00"));
        rental4.setDueDate(dateFormat.parse("2024-01-20 10:00:00"));
        rental4.setTotalPrice(260.0); // Not returned
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        
        // Count rentals per customer
        Map<Customer, Integer> rentalCounts = store.countCarsRentedPerCustomer();
        
        // Verify the number of rentals for customer C004 = 4 (stored)
        assertEquals(1, rentalCounts.size());
        assertEquals(Integer.valueOf(4), rentalCounts.get(customer));
        
        // Note: The specification mentions "Currently active rentals = 2 (after marking 2 as returned)"
        // but this test is specifically for countCarsRentedPerCustomer which counts all rentals regardless of return status
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setAddress("654 Maple Dr");
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        // Rental records: Customer C005 rented cars with plates "PQR456", "STU789", "VWX012" with "STU789" being overdue
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota RAV4");
        car1.setDailyPrice(70.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda CR-V");
        car2.setDailyPrice(75.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford Escape");
        car3.setDailyPrice(68.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create 3 rentals for customer C005
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setBackDate(dateFormat.parse("2024-01-05 09:00:00")); // Returned on time
        rental1.setTotalPrice(280.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(dateFormat.parse("2024-01-06 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-10 10:00:00")); // Overdue (no back date set)
        rental2.setTotalPrice(300.0); // Marked as overdue
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setRentalDate(dateFormat.parse("2024-01-11 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental3.setBackDate(dateFormat.parse("2024-01-15 09:00:00")); // Returned on time
        rental3.setTotalPrice(272.0);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> rentalCounts = store.countCarsRentedPerCustomer();
        
        // Verify the number of rentals for customer C005 = 3
        assertEquals(1, rentalCounts.size());
        assertEquals(Integer.valueOf(3), rentalCounts.get(customer));
        
        // Note: The overdue status doesn't affect the rental count - all rentals are counted regardless of status
    }
}