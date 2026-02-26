import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR5Test {
    
    private CarRentalStore store;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // SetUp: Create a store instance
        // Create customer C001
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        // Create and add 3 cars
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setDailyPrice(70.0);
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Add 3 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setStartDate(LocalDate.parse("2024-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2024-01-05", formatter));
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer1);
        rental2.setStartDate(LocalDate.parse("2024-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2024-01-15", formatter));
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer1);
        rental3.setStartDate(LocalDate.parse("2024-01-20", formatter));
        rental3.setDueDate(LocalDate.parse("2024-01-25", formatter));
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer1));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // SetUp: Create a store instance
        // Create customers C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        // Create and add 7 cars
        Car[] cars = new Car[7];
        String[] plates = {"ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"};
        
        for (int i = 0; i < 7; i++) {
            cars[i] = new Car();
            cars[i].setPlate(plates[i]);
            cars[i].setDailyPrice(50.0 + i * 10);
            store.getCars().add(cars[i]);
        }
        
        // Add rental records: 2 for customer C001, 5 for customer C002
        // Customer C001 rentals
        Rental rental1 = new Rental();
        rental1.setCar(cars[0]); // ABC123
        rental1.setCustomer(customer1);
        rental1.setStartDate(LocalDate.parse("2024-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2024-01-05", formatter));
        
        Rental rental2 = new Rental();
        rental2.setCar(cars[1]); // XYZ456
        rental2.setCustomer(customer1);
        rental2.setStartDate(LocalDate.parse("2024-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2024-01-15", formatter));
        
        // Customer C002 rentals
        Rental rental3 = new Rental();
        rental3.setCar(cars[2]); // LMN789
        rental3.setCustomer(customer2);
        rental3.setStartDate(LocalDate.parse("2024-01-02", formatter));
        rental3.setDueDate(LocalDate.parse("2024-01-07", formatter));
        
        Rental rental4 = new Rental();
        rental4.setCar(cars[3]); // OPQ012
        rental4.setCustomer(customer2);
        rental4.setStartDate(LocalDate.parse("2024-01-08", formatter));
        rental4.setDueDate(LocalDate.parse("2024-01-12", formatter));
        
        Rental rental5 = new Rental();
        rental5.setCar(cars[4]); // RST345
        rental5.setCustomer(customer2);
        rental5.setStartDate(LocalDate.parse("2024-01-13", formatter));
        rental5.setDueDate(LocalDate.parse("2024-01-18", formatter));
        
        Rental rental6 = new Rental();
        rental6.setCar(cars[5]); // UVW678
        rental6.setCustomer(customer2);
        rental6.setStartDate(LocalDate.parse("2024-01-19", formatter));
        rental6.setDueDate(LocalDate.parse("2024-01-24", formatter));
        
        Rental rental7 = new Rental();
        rental7.setCar(cars[6]); // JKL901
        rental7.setCustomer(customer2);
        rental7.setStartDate(LocalDate.parse("2024-01-25", formatter));
        rental7.setDueDate(LocalDate.parse("2024-01-30", formatter));
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        store.getRentals().add(rental5);
        store.getRentals().add(rental6);
        store.getRentals().add(rental7);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create a store instance
        // Create customer C003
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        // No rental records are added for customer C003
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should be an empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // SetUp: Create a store instance
        // Create customer C004
        Customer customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        
        // Create and add 4 cars
        Car[] cars = new Car[4];
        String[] plates = {"DEF234", "GHI567", "JKL890", "MNO123"};
        
        for (int i = 0; i < 4; i++) {
            cars[i] = new Car();
            cars[i].setPlate(plates[i]);
            cars[i].setDailyPrice(40.0 + i * 10);
            store.getCars().add(cars[i]);
        }
        
        // Add 4 rental records for customer C004
        Rental[] rentals = new Rental[4];
        for (int i = 0; i < 4; i++) {
            rentals[i] = new Rental();
            rentals[i].setCar(cars[i]);
            rentals[i].setCustomer(customer4);
            rentals[i].setStartDate(LocalDate.parse("2024-01-01", formatter));
            rentals[i].setDueDate(LocalDate.parse("2024-01-05", formatter));
            store.getRentals().add(rentals[i]);
        }
        
        // Mark 2 of them as returned
        rentals[0].setBackDate(LocalDate.parse("2024-01-04", formatter)); // DEF234 returned
        rentals[2].setBackDate(LocalDate.parse("2024-01-03", formatter)); // JKL890 returned
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 4 rentals regardless of return status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer4));
        
        // Additional verification: count currently active rentals (not specified but mentioned in test case)
        int activeRentals = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getBackDate() == null) {
                activeRentals++;
            }
        }
        assertEquals(2, activeRentals); // 2 rentals are still active
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // SetUp: Create a store instance
        // Create customer C005
        Customer customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Wilson");
        
        // Create and add 3 cars
        Car[] cars = new Car[3];
        String[] plates = {"PQR456", "STU789", "VWX012"};
        
        for (int i = 0; i < 3; i++) {
            cars[i] = new Car();
            cars[i].setPlate(plates[i]);
            cars[i].setDailyPrice(45.0 + i * 10);
            store.getCars().add(cars[i]);
        }
        
        // Add 3 rental records for customer C005
        Rental rental1 = new Rental();
        rental1.setCar(cars[0]); // PQR456
        rental1.setCustomer(customer5);
        rental1.setStartDate(LocalDate.parse("2024-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2024-01-05", formatter));
        
        Rental rental2 = new Rental();
        rental2.setCar(cars[1]); // STU789 - overdue
        rental2.setCustomer(customer5);
        rental2.setStartDate(LocalDate.parse("2024-01-01", formatter));
        rental2.setDueDate(LocalDate.parse("2024-01-05", formatter)); // Past due date
        
        Rental rental3 = new Rental();
        rental3.setCar(cars[2]); // VWX012
        rental3.setCustomer(customer5);
        rental3.setStartDate(LocalDate.parse("2024-01-10", formatter));
        rental3.setDueDate(LocalDate.parse("2024-01-15", formatter));
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 3 rentals regardless of overdue status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer5));
        
        // Additional verification: count overdue rentals (not specified but mentioned in test case)
        int overdueRentals = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.isOverdue()) {
                overdueRentals++;
            }
        }
        assertEquals(1, overdueRentals); // 1 rental is overdue
    }
}