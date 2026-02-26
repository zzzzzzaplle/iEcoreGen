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
        customer.setAddress("123 Main St");
        
        // Add 3 rental records for customer C001 with different car details
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(35.0);
        store.addCar(car3);
        
        // Create rentals for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(200.0);
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-05 10:00:00"));
        rental2.setTotalPrice(160.0);
        store.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-03-05 10:00:00"));
        rental3.setTotalPrice(140.0);
        store.addRental(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals("Should have exactly one customer in the map", 1, result.size());
        assertTrue("Map should contain the customer", result.containsKey(customer));
        assertEquals("Number of rentals for customer C001 should be 3", 3, (int)result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Smith");
        customer1.setAddress("456 Oak Ave");
        
        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setSurname("Johnson");
        customer2.setAddress("789 Pine St");
        
        // Add cars for rental
        Car[] cars = new Car[7];
        String[] plates = {"ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"};
        
        for (int i = 0; i < plates.length; i++) {
            cars[i] = new Car();
            cars[i].setPlate(plates[i]);
            cars[i].setModel("Model " + (i + 1));
            cars[i].setDailyPrice(30.0 + i * 5);
            store.addCar(cars[i]);
        }
        
        // Customer C001 rented 2 cars
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(cars[0]); // ABC123
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setTotalPrice(120.0);
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(cars[1]); // XYZ456
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-05 10:00:00"));
        rental2.setTotalPrice(140.0);
        store.addRental(rental2);
        
        // Customer C002 rented 5 cars
        for (int i = 2; i < 7; i++) {
            Rental rental = new Rental();
            rental.setCustomer(customer2);
            rental.setCar(cars[i]);
            rental.setRentalDate(dateFormat.parse("2024-0" + (i-1) + "-01 10:00:00"));
            rental.setDueDate(dateFormat.parse("2024-0" + (i-1) + "-05 10:00:00"));
            rental.setTotalPrice(100.0 + i * 10);
            store.addRental(rental);
        }
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the results
        assertEquals("Should have exactly two customers in the map", 2, result.size());
        assertTrue("Map should contain customer C001", result.containsKey(customer1));
        assertTrue("Map should contain customer C002", result.containsKey(customer2));
        assertEquals("Number of rentals for customer C001 should be 2", 2, (int)result.get(customer1));
        assertEquals("Number of rentals for customer C002 should be 5", 5, (int)result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        
        // No rental records are added for customer C003
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should be an empty map, not null
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be an empty map", result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C004
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Wilson");
        customer.setAddress("654 Maple Dr");
        
        // Add cars for rental
        Car[] cars = new Car[4];
        String[] plates = {"DEF234", "GHI567", "JKL890", "MNO123"};
        
        for (int i = 0; i < plates.length; i++) {
            cars[i] = new Car();
            cars[i].setPlate(plates[i]);
            cars[i].setModel("Model " + (i + 1));
            cars[i].setDailyPrice(40.0 + i * 5);
            store.addCar(cars[i]);
        }
        
        // Add rental records for customer C004 (4 rentals)
        Rental[] rentals = new Rental[4];
        for (int i = 0; i < rentals.length; i++) {
            rentals[i] = new Rental();
            rentals[i].setCustomer(customer);
            rentals[i].setCar(cars[i]);
            rentals[i].setRentalDate(dateFormat.parse("2024-0" + (i+1) + "-01 10:00:00"));
            rentals[i].setDueDate(dateFormat.parse("2024-0" + (i+1) + "-05 10:00:00"));
            rentals[i].setTotalPrice(150.0 + i * 20);
            store.addRental(rentals[i]);
        }
        
        // Mark 2 of them as returned
        rentals[0].setBackDate(dateFormat.parse("2024-01-04 15:00:00")); // DEF234 returned
        rentals[1].setBackDate(dateFormat.parse("2024-02-03 14:00:00")); // GHI567 returned
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the results
        assertEquals("Should have exactly one customer in the map", 1, result.size());
        assertTrue("Map should contain the customer", result.containsKey(customer));
        assertEquals("Number of rentals for customer C004 should be 4 (all stored rentals)", 4, (int)result.get(customer));
        
        // Verify currently active rentals count separately
        long activeRentals = store.getRentals().stream()
            .filter(Rental::isActive)
            .filter(rental -> rental.getCustomer().equals(customer))
            .count();
        assertEquals("Currently active rentals should be 2", 2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Eva");
        customer.setSurname("Davis");
        customer.setAddress("987 Cedar Ln");
        
        // Add cars for rental
        Car[] cars = new Car[3];
        String[] plates = {"PQR456", "STU789", "VWX012"};
        
        for (int i = 0; i < plates.length; i++) {
            cars[i] = new Car();
            cars[i].setPlate(plates[i]);
            cars[i].setModel("Model " + (i + 1));
            cars[i].setDailyPrice(45.0 + i * 5);
            store.addCar(cars[i]);
        }
        
        // Add rental records for customer C005 (3 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(cars[0]); // PQR456
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental1.setTotalPrice(270.0);
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(cars[1]); // STU789 (overdue)
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-05 10:00:00")); // Overdue if current date is after this
        rental2.setTotalPrice(200.0);
        store.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(cars[2]); // VWX012
        rental3.setRentalDate(dateFormat.parse("2024-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-03-10 10:00:00"));
        rental3.setTotalPrice(300.0);
        store.addRental(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the results
        assertEquals("Should have exactly one customer in the map", 1, result.size());
        assertTrue("Map should contain the customer", result.containsKey(customer));
        assertEquals("Number of rentals for customer C005 should be 3", 3, (int)result.get(customer));
        
        // Verify overdue rentals count separately
        Date currentDate = dateFormat.parse("2024-02-10 15:00:00"); // After due date of STU789
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        assertEquals("Should have exactly one customer with overdue rentals", 1, overdueCustomers.size());
        assertTrue("Customer should be in overdue list", overdueCustomers.contains(customer));
    }
}