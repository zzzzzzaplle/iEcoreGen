import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void testCase1_CountRentalsForSingleCustomer() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C001
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        // Add 3 rental records for customer C001 with different car details
        // Add cars with plates "ABC123", "XYZ456", "LMN789" rented by customer C001
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
        
        // Create rentals for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setTotalPrice(150.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setTotalPrice(135.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer1);
        rental3.setCar(car3);
        rental3.setTotalPrice(120.0);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer1));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // SetUp: Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        
        // Create cars for rentals
        List<Car> cars = new ArrayList<>();
        String[] plates = {"ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setModel("Model " + plate);
            car.setDailyPrice(50.0);
            cars.add(car);
            store.getCars().add(car);
        }
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        // Customer C001 rented cars with plates "ABC123", "XYZ456"
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(cars.get(0));
        rental1.setTotalPrice(100.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(cars.get(1));
        rental2.setTotalPrice(100.0);
        
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        for (int i = 2; i < 7; i++) {
            Rental rental = new Rental();
            rental.setCustomer(customer2);
            rental.setCar(cars.get(i));
            rental.setTotalPrice(100.0);
            store.getRentals().add(rental);
        }
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C001 = 2
        // - Number of rentals for customer C002 = 5
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C003
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        customer3.setAddress("789 Pine St");
        
        // No rental records are added for customer C003
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C004
        Customer customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        customer4.setAddress("321 Elm St");
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        // Rental records: Customer C004 rented cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        String[] plates = {"DEF234", "GHI567", "JKL890", "MNO123"};
        List<Car> cars = new ArrayList<>();
        List<Rental> rentals = new ArrayList<>();
        
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setModel("Model " + plate);
            car.setDailyPrice(50.0);
            cars.add(car);
            store.getCars().add(car);
            
            Rental rental = new Rental();
            rental.setCustomer(customer4);
            rental.setCar(car);
            rental.setTotalPrice(100.0);
            rental.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
            rental.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
            rentals.add(rental);
        }
        
        // Mark 2 rentals as returned
        rentals.get(0).setBackDate(dateFormat.parse("2024-01-04 10:00:00"));
        rentals.get(1).setBackDate(dateFormat.parse("2024-01-06 10:00:00"));
        
        // Add all rentals to store
        store.getRentals().addAll(rentals);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C004 = 4 (stored) 
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer4));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C005
        Customer customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Wilson");
        customer5.setAddress("654 Maple St");
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        // Rental records: Customer C005 rented cars with plates "PQR456", "STU789", "VWX012" with "STU789" being overdue
        String[] plates = {"PQR456", "STU789", "VWX012"};
        List<Car> cars = new ArrayList<>();
        List<Rental> rentals = new ArrayList<>();
        
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setModel("Model " + plate);
            car.setDailyPrice(50.0);
            cars.add(car);
            store.getCars().add(car);
            
            Rental rental = new Rental();
            rental.setCustomer(customer5);
            rental.setCar(car);
            rental.setTotalPrice(100.0);
            rental.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
            rental.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
            rentals.add(rental);
        }
        
        // Mark "STU789" as overdue (back date is null and current date is past due date)
        // The other two have back dates set (not overdue)
        rentals.get(0).setBackDate(dateFormat.parse("2024-01-04 10:00:00"));
        rentals.get(2).setBackDate(dateFormat.parse("2024-01-03 10:00:00"));
        // STU789 remains with backDate = null (overdue)
        
        // Add all rentals to store
        store.getRentals().addAll(rentals);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer5));
    }
}