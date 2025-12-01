import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import java.util.List;
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
        // Create customer C001
        Customer customer = new Customer();
        
        // Create 3 cars with different plates
        Car car1 = new Car();
        car1.setPlate("ABC123");
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        Car car3 = new Car();
        car3.setPlate("LMN789");
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Create 3 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result: Customer C001 should have 3 rentals
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(3, (int) result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create customers C001 and C002
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        
        // Create cars for both customers
        Car car1 = new Car();
        car1.setPlate("ABC123");
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        Car car3 = new Car();
        car3.setPlate("LMN789");
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        Car car5 = new Car();
        car5.setPlate("RST345");
        Car car6 = new Car();
        car6.setPlate("UVW678");
        Car car7 = new Car();
        car7.setPlate("JKL901");
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car5);
        cars.add(car6);
        cars.add(car7);
        store.setCars(cars);
        
        // Create rental records: 2 for customer C001, 5 for customer C002
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer1);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer2);
        
        Rental rental4 = new Rental();
        rental4.setCar(car4);
        rental4.setCustomer(customer2);
        
        Rental rental5 = new Rental();
        rental5.setCar(car5);
        rental5.setCustomer(customer2);
        
        Rental rental6 = new Rental();
        rental6.setCar(car6);
        rental6.setCustomer(customer2);
        
        Rental rental7 = new Rental();
        rental7.setCar(car7);
        rental7.setCustomer(customer2);
        
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
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify results: Customer C001 has 2 rentals, Customer C002 has 5 rentals
        assertEquals(2, result.size());
        assertTrue(result.containsKey(customer1));
        assertTrue(result.containsKey(customer2));
        assertEquals(2, (int) result.get(customer1));
        assertEquals(5, (int) result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() throws Exception {
        // Create customer C003
        Customer customer = new Customer();
        
        // No rental records are added to the store
        // The store's rental list remains empty
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result: Empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        
        // Create 4 cars
        Car car1 = new Car();
        car1.setPlate("DEF234");
        Car car2 = new Car();
        car2.setPlate("GHI567");
        Car car3 = new Car();
        car3.setPlate("JKL890");
        Car car4 = new Car();
        car4.setPlate("MNO123");
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        store.setCars(cars);
        
        // Create current date for marking returns
        Date currentDate = dateFormat.parse("2024-01-15 12:00:00");
        
        // Create 4 rental records for customer C004, mark 2 as returned
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setBackDate(currentDate); // Marked as returned
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setBackDate(currentDate); // Marked as returned
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        // Not returned (backDate remains null)
        
        Rental rental4 = new Rental();
        rental4.setCar(car4);
        rental4.setCustomer(customer);
        // Not returned (backDate remains null)
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        rentals.add(rental4);
        store.setRentals(rentals);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result: Customer C004 has 4 rentals total (includes returned cars)
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(4, (int) result.get(customer));
        
        // Verify currently active rentals (backDate is null)
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
        // Create customer C005
        Customer customer = new Customer();
        
        // Create 3 cars
        Car car1 = new Car();
        car1.setPlate("PQR456");
        Car car2 = new Car();
        car2.setPlate("STU789");
        Car car3 = new Car();
        car3.setPlate("VWX012");
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Create dates for overdue scenario
        Date pastDueDate = dateFormat.parse("2024-01-10 12:00:00");
        Date futureDueDate = dateFormat.parse("2024-01-20 12:00:00");
        Date currentDate = dateFormat.parse("2024-01-15 12:00:00");
        
        // Create 3 rental records for customer C005, with one overdue
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setDueDate(futureDueDate); // Not overdue
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setDueDate(pastDueDate); // Overdue
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        rental3.setDueDate(futureDueDate); // Not overdue
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result: Customer C005 has 3 rentals total
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(3, (int) result.get(customer));
        
        // Find overdue rentals for customer C005
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        int overdueCountForCustomer = 0;
        for (Customer c : overdueCustomers) {
            if (c.equals(customer)) {
                overdueCountForCustomer++;
            }
        }
        assertEquals(1, overdueCountForCustomer);
    }
}