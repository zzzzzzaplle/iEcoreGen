import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CR5Test {
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // SetUp: Create a store instance
        store = new Store();
        
        // Create a customer with customer ID: C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        customer.setRentedCarPlate("ABC123");
        
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
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Create 3 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(new Date());
        rental1.setDueDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day later
        rental1.setTotalPrice(50.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(new Date());
        rental2.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        rental2.setTotalPrice(45.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(new Date());
        rental3.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        rental3.setTotalPrice(40.0);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // SetUp: Create a store instance
        store = new Store();
        
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
        
        // Create cars with plates "ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Car car = new Car();
            String[] plates = {"ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"};
            car.setPlate(plates[i]);
            car.setModel("Model " + (i + 1));
            car.setDailyPrice(40.0 + i * 5);
            cars.add(car);
        }
        store.setCars(cars);
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        List<Rental> rentals = new ArrayList<>();
        
        // Customer C001 rentals
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(cars.get(0)); // ABC123
        rental1.setRentalDate(new Date());
        rental1.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        rental1.setTotalPrice(40.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(cars.get(1)); // XYZ456
        rental2.setRentalDate(new Date());
        rental2.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        rental2.setTotalPrice(45.0);
        
        // Customer C002 rentals
        for (int i = 2; i < 7; i++) {
            Rental rental = new Rental();
            rental.setCustomer(customer2);
            rental.setCar(cars.get(i));
            rental.setRentalDate(new Date());
            rental.setDueDate(new Date(System.currentTimeMillis() + 86400000));
            rental.setTotalPrice(40.0 + i * 5);
            rentals.add(rental);
        }
        
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C001 = 2, customer C002 = 5
        assertEquals(2, result.size());
        assertTrue(result.containsKey(customer1));
        assertTrue(result.containsKey(customer2));
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create a store instance
        store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        customer.setRentedCarPlate("");
        
        // No rental records are added for customer C003
        store.setRentals(new ArrayList<>());
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Empty map (not null, but empty)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // SetUp: Create a store instance
        store = new Store();
        
        // Create a customer with customer ID: C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        customer.setRentedCarPlate("DEF234");
        
        // Create cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        List<Car> cars = new ArrayList<>();
        String[] plates = {"DEF234", "GHI567", "JKL890", "MNO123"};
        for (int i = 0; i < 4; i++) {
            Car car = new Car();
            car.setPlate(plates[i]);
            car.setModel("Model " + (i + 1));
            car.setDailyPrice(35.0 + i * 10);
            cars.add(car);
        }
        store.setCars(cars);
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        List<Rental> rentals = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setCar(cars.get(i));
            rental.setRentalDate(new Date());
            rental.setDueDate(new Date(System.currentTimeMillis() + 86400000));
            rental.setTotalPrice(35.0 + i * 10);
            
            // Mark first 2 rentals as returned
            if (i < 2) {
                rental.setBackDate(new Date());
            }
            
            rentals.add(rental);
        }
        store.setRentals(rentals);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C004 = 4 (all stored rentals count)
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(Integer.valueOf(4), result.get(customer));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // SetUp: Create a store instance
        store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setAddress("654 Maple St");
        customer.setRentedCarPlate("PQR456");
        
        // Create cars with plates "PQR456", "STU789", "VWX012"
        List<Car> cars = new ArrayList<>();
        String[] plates = {"PQR456", "STU789", "VWX012"};
        for (int i = 0; i < 3; i++) {
            Car car = new Car();
            car.setPlate(plates[i]);
            car.setModel("Model " + (i + 1));
            car.setDailyPrice(40.0 + i * 5);
            cars.add(car);
        }
        store.setCars(cars);
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        List<Rental> rentals = new ArrayList<>();
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(cars.get(0)); // PQR456
        rental1.setRentalDate(new Date(System.currentTimeMillis() - 172800000)); // 2 days ago
        rental1.setDueDate(new Date(System.currentTimeMillis() - 86400000)); // 1 day ago (not overdue yet)
        rental1.setTotalPrice(40.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(cars.get(1)); // STU789 - overdue
        rental2.setRentalDate(new Date(System.currentTimeMillis() - 259200000)); // 3 days ago
        rental2.setDueDate(new Date(System.currentTimeMillis() - 172800000)); // 2 days ago (overdue)
        rental2.setTotalPrice(45.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(cars.get(2)); // VWX012
        rental3.setRentalDate(new Date());
        rental3.setDueDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day later
        rental3.setTotalPrice(50.0);
        
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Additional verification for overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(new Date());
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }
}