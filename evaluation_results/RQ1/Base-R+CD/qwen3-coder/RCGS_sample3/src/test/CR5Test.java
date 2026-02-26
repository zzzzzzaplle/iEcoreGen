import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR5Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_countRentalsForSingleCustomer() throws ParseException {
        // Create customer C001
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford");
        car3.setDailyPrice(70.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create 3 rental records for customer C001
        Date rentalDate = dateFormat.parse("2023-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2023-01-10 10:00:00");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(rentalDate);
        rental1.setDueDate(dueDate);
        rental1.setTotalPrice(450.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(rentalDate);
        rental2.setDueDate(dueDate);
        rental2.setTotalPrice(540.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer1);
        rental3.setCar(car3);
        rental3.setRentalDate(rentalDate);
        rental3.setDueDate(dueDate);
        rental3.setTotalPrice(630.0);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer1));
        assertEquals(Integer.valueOf(3), result.get(customer1));
    }
    
    @Test
    public void testCase2_countRentalsForMultipleCustomers() throws ParseException {
        // Create customers
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford");
        car3.setDailyPrice(70.0);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("BMW");
        car4.setDailyPrice(100.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Audi");
        car5.setDailyPrice(120.0);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Mercedes");
        car6.setDailyPrice(150.0);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Tesla");
        car7.setDailyPrice(200.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getCars().add(car4);
        store.getCars().add(car5);
        store.getCars().add(car6);
        store.getCars().add(car7);
        
        // Create rental records
        Date rentalDate = dateFormat.parse("2023-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2023-01-10 10:00:00");
        
        // Customer C001 - 2 rentals
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(rentalDate);
        rental1.setDueDate(dueDate);
        rental1.setTotalPrice(450.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(rentalDate);
        rental2.setDueDate(dueDate);
        rental2.setTotalPrice(540.0);
        
        // Customer C002 - 5 rentals
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentalDate(rentalDate);
        rental3.setDueDate(dueDate);
        rental3.setTotalPrice(630.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentalDate(rentalDate);
        rental4.setDueDate(dueDate);
        rental4.setTotalPrice(900.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentalDate(rentalDate);
        rental5.setDueDate(dueDate);
        rental5.setTotalPrice(1080.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentalDate(rentalDate);
        rental6.setDueDate(dueDate);
        rental6.setTotalPrice(1350.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentalDate(rentalDate);
        rental7.setDueDate(dueDate);
        rental7.setTotalPrice(1800.0);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        store.getRentals().add(rental5);
        store.getRentals().add(rental6);
        store.getRentals().add(rental7);
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey(customer1));
        assertTrue(result.containsKey(customer2));
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_countRentalsWithNoRecords() {
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // No rental records are added
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should return an empty map, not null
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_countRentalsIncludingReturnedCars() throws ParseException {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Nissan");
        car1.setDailyPrice(55.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Hyundai");
        car2.setDailyPrice(45.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Kia");
        car3.setDailyPrice(40.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Mazda");
        car4.setDailyPrice(65.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getCars().add(car4);
        
        // Create rental records
        Date rentalDate = dateFormat.parse("2023-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2023-01-10 10:00:00");
        Date backDate = dateFormat.parse("2023-01-09 15:00:00");
        
        // 4 rental records for customer C004
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(rentalDate);
        rental1.setDueDate(dueDate);
        rental1.setTotalPrice(495.0);
        // This rental is returned
        rental1.setBackDate(backDate);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(rentalDate);
        rental2.setDueDate(dueDate);
        rental2.setTotalPrice(405.0);
        // This rental is returned
        rental2.setBackDate(backDate);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(rentalDate);
        rental3.setDueDate(dueDate);
        rental3.setTotalPrice(360.0);
        // This rental is still active (not returned)
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        rental4.setRentalDate(rentalDate);
        rental4.setDueDate(dueDate);
        rental4.setTotalPrice(585.0);
        // This rental is still active (not returned)
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - all 4 rentals should be counted, regardless of return status
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(Integer.valueOf(4), result.get(customer));
    }
    
    @Test
    public void testCase5_countRentalsForCustomerWithOverdueCars() throws ParseException {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Volkswagen");
        car1.setDailyPrice(75.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Subaru");
        car2.setDailyPrice(80.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Lexus");
        car3.setDailyPrice(110.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rental records
        Date rentalDate = dateFormat.parse("2023-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2023-01-10 10:00:00");
        Date currentDate = dateFormat.parse("2023-01-15 10:00:00");
        
        // 3 rental records for customer C005
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(rentalDate);
        rental1.setDueDate(dueDate);
        rental1.setTotalPrice(675.0);
        // Not overdue (back date set)
        rental1.setBackDate(dateFormat.parse("2023-01-09 10:00:00"));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(rentalDate);
        rental2.setDueDate(dueDate);
        rental2.setTotalPrice(720.0);
        // Overdue (no back date, current date after due date)
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(rentalDate);
        rental3.setDueDate(dueDate);
        rental3.setTotalPrice(990.0);
        // Not overdue (back date set)
        rental3.setBackDate(dateFormat.parse("2023-01-08 10:00:00"));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - all 3 rentals should be counted regardless of overdue status
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Also verify overdue rentals separately
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }
}