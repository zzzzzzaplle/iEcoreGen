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
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        // Create and add 3 cars
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
        
        store.getCars().addAll(Arrays.asList(car1, car2, car3));
        
        // Add 3 rental records for customer C001 with different car details
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental1.setTotalPrice(450.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-10 10:00:00"));
        rental2.setTotalPrice(405.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer1);
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2024-03-01 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-03-10 10:00:00"));
        rental3.setTotalPrice(360.0);
        
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3));
        
        // Expected Output: Number of rentals for customer C001 = 3
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer1));
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
        
        // Create and add 7 cars
        List<Car> cars = new ArrayList<>();
        String[] plates = {"ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setModel("Model " + plate);
            car.setDailyPrice(50.0);
            cars.add(car);
        }
        store.getCars().addAll(cars);
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        List<Rental> rentals = new ArrayList<>();
        
        // Customer C001 rentals
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(cars.get(0)); // ABC123
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-10 10:00:00"));
        rental1.setTotalPrice(450.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(cars.get(1)); // XYZ456
        rental2.setRentalDate(dateFormat.parse("2024-02-01 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-02-10 10:00:00"));
        rental2.setTotalPrice(450.0);
        
        // Customer C002 rentals
        for (int i = 2; i < 7; i++) {
            Rental rental = new Rental();
            rental.setCustomer(customer2);
            rental.setCar(cars.get(i));
            rental.setRentalDate(dateFormat.parse("2024-0" + (i+1) + "-01 10:00:00"));
            rental.setDueDate(dateFormat.parse("2024-0" + (i+1) + "-10 10:00:00"));
            rental.setTotalPrice(450.0);
            rentals.add(rental);
        }
        
        rentals.addAll(Arrays.asList(rental1, rental2));
        store.getRentals().addAll(rentals);
        
        // Expected Output: Number of rentals for customer C001 = 2, customer C002 = 5
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        customer3.setAddress("789 Pine Rd");
        
        // No rental records are added for customer C003
        
        // Expected Output: Empty map (not null)
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // SetUp: Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C004
        Customer customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        customer4.setAddress("321 Elm St");
        
        // Create and add 4 cars
        String[] plates = {"DEF234", "GHI567", "JKL890", "MNO123"};
        List<Car> cars = new ArrayList<>();
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setModel("Model " + plate);
            car.setDailyPrice(50.0);
            cars.add(car);
        }
        store.getCars().addAll(cars);
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        List<Rental> rentals = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Rental rental = new Rental();
            rental.setCustomer(customer4);
            rental.setCar(cars.get(i));
            rental.setRentalDate(dateFormat.parse("2024-0" + (i+1) + "-01 10:00:00"));
            rental.setDueDate(dateFormat.parse("2024-0" + (i+1) + "-10 10:00:00"));
            rental.setTotalPrice(450.0);
            
            // Mark first 2 rentals as returned
            if (i < 2) {
                rental.setBackDate(dateFormat.parse("2024-0" + (i+1) + "-05 10:00:00"));
            }
            
            rentals.add(rental);
        }
        
        store.getRentals().addAll(rentals);
        
        // Expected Output: Number of rentals for customer C004 = 4 (stored)
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer4));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // SetUp: Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Wilson");
        customer5.setAddress("654 Maple Dr");
        
        // Create and add 3 cars
        String[] plates = {"PQR456", "STU789", "VWX012"};
        List<Car> cars = new ArrayList<>();
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setModel("Model " + plate);
            car.setDailyPrice(50.0);
            cars.add(car);
        }
        store.getCars().addAll(cars);
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        List<Rental> rentals = new ArrayList<>();
        Date currentDate = dateFormat.parse("2024-04-15 10:00:00");
        
        for (int i = 0; i < 3; i++) {
            Rental rental = new Rental();
            rental.setCustomer(customer5);
            rental.setCar(cars.get(i));
            rental.setRentalDate(dateFormat.parse("2024-0" + (i+1) + "-01 10:00:00"));
            
            // Set due dates - make STU789 overdue
            if (i == 1) { // STU789
                rental.setDueDate(dateFormat.parse("2024-04-10 10:00:00")); // Overdue
            } else {
                rental.setDueDate(dateFormat.parse("2024-0" + (i+5) + "-01 10:00:00")); // Not overdue
            }
            
            rental.setTotalPrice(450.0);
            rentals.add(rental);
        }
        
        store.getRentals().addAll(rentals);
        
        // Expected Output: Number of rentals for customer C005 = 3
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer5));
        
        // Additional verification for overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer5));
    }
}