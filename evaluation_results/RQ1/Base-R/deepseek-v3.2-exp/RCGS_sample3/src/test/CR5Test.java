import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR5Test {
    
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        rentalSystem.addCustomer(customer);
        
        // Create and add 3 cars
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        rentalSystem.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        rentalSystem.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        rentalSystem.addCar(car3);
        
        // Create 3 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setStartDate(LocalDate.now().minusDays(10));
        rental1.setDueDate(LocalDate.now().plusDays(5));
        rental1.setTotalPrice(500.0);
        rentalSystem.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setStartDate(LocalDate.now().minusDays(5));
        rental2.setDueDate(LocalDate.now().plusDays(10));
        rental2.setTotalPrice(450.0);
        rentalSystem.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setStartDate(LocalDate.now().minusDays(2));
        rental3.setDueDate(LocalDate.now().plusDays(8));
        rental3.setTotalPrice(320.0);
        rentalSystem.addRental(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = rentalSystem.countRentalsPerCustomer();
        
        // Verify result
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create customer C001
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        rentalSystem.addCustomer(customer1);
        
        // Create customer C002
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak St");
        rentalSystem.addCustomer(customer2);
        
        // Create and add cars
        String[] plates = {"ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setModel("Model " + plate);
            car.setDailyPrice(50.0);
            rentalSystem.addCar(car);
        }
        
        // Create 2 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(rentalSystem.getCars().get(0)); // ABC123
        rental1.setStartDate(LocalDate.now().minusDays(10));
        rental1.setDueDate(LocalDate.now().plusDays(5));
        rental1.setTotalPrice(500.0);
        rentalSystem.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(rentalSystem.getCars().get(1)); // XYZ456
        rental2.setStartDate(LocalDate.now().minusDays(5));
        rental2.setDueDate(LocalDate.now().plusDays(10));
        rental2.setTotalPrice(450.0);
        rentalSystem.addRental(rental2);
        
        // Create 5 rental records for customer C002
        for (int i = 2; i < 7; i++) {
            Rental rental = new Rental();
            rental.setCustomer(customer2);
            rental.setCar(rentalSystem.getCars().get(i));
            rental.setStartDate(LocalDate.now().minusDays(15 - i));
            rental.setDueDate(LocalDate.now().plusDays(i));
            rental.setTotalPrice(400.0 + i * 10);
            rentalSystem.addRental(rental);
        }
        
        // Count rentals per customer
        Map<Customer, Integer> result = rentalSystem.countRentalsPerCustomer();
        
        // Verify result
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        rentalSystem.addCustomer(customer);
        
        // No rental records added
        
        // Count rentals per customer
        Map<Customer, Integer> result = rentalSystem.countRentalsPerCustomer();
        
        // Verify result is empty map (not null)
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        rentalSystem.addCustomer(customer);
        
        // Create and add 4 cars
        String[] plates = {"DEF234", "GHI567", "JKL890", "MNO123"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setModel("Model " + plate);
            car.setDailyPrice(60.0);
            rentalSystem.addCar(car);
        }
        
        // Create 4 rental records for customer C004
        Rental[] rentals = new Rental[4];
        for (int i = 0; i < 4; i++) {
            rentals[i] = new Rental();
            rentals[i].setCustomer(customer);
            rentals[i].setCar(rentalSystem.getCars().get(i));
            rentals[i].setStartDate(LocalDate.now().minusDays(20 - i));
            rentals[i].setDueDate(LocalDate.now().plusDays(10 - i));
            rentals[i].setTotalPrice(600.0 + i * 20);
            rentalSystem.addRental(rentals[i]);
        }
        
        // Mark 2 rentals as returned
        rentalSystem.returnCar(rentals[0], LocalDate.now().minusDays(5));
        rentalSystem.returnCar(rentals[1], LocalDate.now().minusDays(3));
        
        // Count rentals per customer
        Map<Customer, Integer> result = rentalSystem.countRentalsPerCustomer();
        
        // Verify result - should count all 4 rentals regardless of return status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
        
        // Additional verification: check active rentals count
        assertEquals(2, rentalSystem.getActiveRentals().size());
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setAddress("654 Maple St");
        rentalSystem.addCustomer(customer);
        
        // Create and add 3 cars
        String[] plates = {"PQR456", "STU789", "VWX012"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setModel("Model " + plate);
            car.setDailyPrice(55.0);
            rentalSystem.addCar(car);
        }
        
        // Create 3 rental records for customer C005
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(rentalSystem.getCars().get(0)); // PQR456
        rental1.setStartDate(LocalDate.now().minusDays(10));
        rental1.setDueDate(LocalDate.now().plusDays(5));
        rental1.setTotalPrice(550.0);
        rentalSystem.addRental(rental1);
        
        Rental rental2 = new Rental(); // This will be overdue
        rental2.setCustomer(customer);
        rental2.setCar(rentalSystem.getCars().get(1)); // STU789
        rental2.setStartDate(LocalDate.now().minusDays(15));
        rental2.setDueDate(LocalDate.now().minusDays(1)); // Due date in past
        rental2.setTotalPrice(825.0);
        rentalSystem.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(rentalSystem.getCars().get(2)); // VWX012
        rental3.setStartDate(LocalDate.now().minusDays(3));
        rental3.setDueDate(LocalDate.now().plusDays(7));
        rental3.setTotalPrice(385.0);
        rentalSystem.addRental(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = rentalSystem.countRentalsPerCustomer();
        
        // Verify result - should count all 3 rentals
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Additional verification: check overdue rentals count
        assertEquals(1, rentalSystem.getCustomersWithOverdueRentals().size());
        assertTrue(rentalSystem.getCustomersWithOverdueRentals().contains(customer));
    }
}