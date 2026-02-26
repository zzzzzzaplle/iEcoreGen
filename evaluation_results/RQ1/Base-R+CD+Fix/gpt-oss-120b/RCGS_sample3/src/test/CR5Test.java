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
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        
        // Create cars
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
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create 3 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setTotalPrice(150.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setTotalPrice(135.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setTotalPrice(120.0);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Test the countCarsRentedPerCustomer method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals("Should have exactly one customer in the map", 1, result.size());
        assertEquals("Customer C001 should have 3 rentals", 3, (int)result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create customer C001
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        // Create customer C002
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        
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
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4, car5, car6, car7));
        
        // Create 2 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setTotalPrice(150.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setTotalPrice(135.0);
        
        // Create 5 rental records for customer C002
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setTotalPrice(120.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setTotalPrice(165.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setTotalPrice(144.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setTotalPrice(126.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setTotalPrice(138.0);
        
        // Add all rentals to store
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3, rental4, rental5, rental6, rental7));
        
        // Test the countCarsRentedPerCustomer method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals("Should have exactly two customers in the map", 2, result.size());
        assertEquals("Customer C001 should have 2 rentals", 2, (int)result.get(customer1));
        assertEquals("Customer C002 should have 5 rentals", 5, (int)result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        
        // No rental records are added for customer C003
        
        // Test the countCarsRentedPerCustomer method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should be an empty map
        assertNotNull("Result should not be null", result);
        assertTrue("Result map should be empty when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws ParseException {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota Corolla");
        car1.setDailyPrice(35.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(55.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford Fusion");
        car3.setDailyPrice(50.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet Impala");
        car4.setDailyPrice(60.0);
        
        // Add cars to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4));
        
        // Create 4 rental records for customer C004
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setTotalPrice(105.0);
        rental1.setBackDate(dateFormat.parse("2024-01-10 10:00:00")); // Mark as returned
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setTotalPrice(165.0);
        rental2.setBackDate(dateFormat.parse("2024-01-15 14:00:00")); // Mark as returned
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setTotalPrice(150.0);
        // Not returned (backDate is null)
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        rental4.setTotalPrice(180.0);
        // Not returned (backDate is null)
        
        // Add all rentals to store
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3, rental4));
        
        // Test the countCarsRentedPerCustomer method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 4 rentals regardless of return status
        assertEquals("Should have exactly one customer in the map", 1, result.size());
        assertEquals("Customer C004 should have 4 rentals (including returned ones)", 4, (int)result.get(customer));
        
        // Additional verification: currently active rentals (not returned)
        int activeRentals = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getCustomer().equals(customer) && rental.getBackDate() == null) {
                activeRentals++;
            }
        }
        assertEquals("Customer C004 should have 2 currently active rentals", 2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws ParseException {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setAddress("654 Maple Dr");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota RAV4");
        car1.setDailyPrice(65.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda CR-V");
        car2.setDailyPrice(70.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford Escape");
        car3.setDailyPrice(60.0);
        
        // Add cars to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3));
        
        Date currentDate = dateFormat.parse("2024-01-20 15:00:00");
        
        // Create 3 rental records for customer C005
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setTotalPrice(195.0);
        rental1.setRentalDate(dateFormat.parse("2024-01-15 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-25 10:00:00")); // Not overdue
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setTotalPrice(210.0);
        rental2.setRentalDate(dateFormat.parse("2024-01-10 09:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-15 09:00:00")); // Overdue
        rental2.setBackDate(null); // Not returned
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setTotalPrice(180.0);
        rental3.setRentalDate(dateFormat.parse("2024-01-18 11:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-22 11:00:00")); // Not overdue yet
        
        // Add all rentals to store
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3));
        
        // Test the countCarsRentedPerCustomer method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 3 rentals regardless of overdue status
        assertEquals("Should have exactly one customer in the map", 1, result.size());
        assertEquals("Customer C005 should have 3 rentals", 3, (int)result.get(customer));
        
        // Additional verification: overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        int overdueCount = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getCustomer().equals(customer) && 
                rental.getBackDate() == null && 
                rental.getDueDate() != null &&
                currentDate.after(rental.getDueDate())) {
                overdueCount++;
            }
        }
        assertEquals("Customer C005 should have 1 overdue rental", 1, overdueCount);
        assertTrue("Customer C005 should be in overdue customers list", overdueCustomers.contains(customer));
    }
}