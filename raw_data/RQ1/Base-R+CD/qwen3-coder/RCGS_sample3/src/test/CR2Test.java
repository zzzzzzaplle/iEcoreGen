import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR2Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() throws ParseException {
        // Create rental date (arbitrary date for testing)
        Date rentalDate = dateFormat.parse("2023-01-01 10:00:00");
        
        // Add first rental: Toyota Camry, 100 CNY/day, 3 days
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(rentalDate);
        rental1.setTotalPrice(300.0); // 100 * 3
        
        // Add second rental: Honda Civic, 150 CNY/day, 2 days
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(rentalDate);
        rental2.setTotalPrice(300.0); // 150 * 2
        
        // Add third rental: Ford Focus, 200 CNY/day, 1 day
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(rentalDate);
        rental3.setTotalPrice(200.0); // 200 * 1
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.01);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Create empty store with no rentals
        store.setRentals(new ArrayList<>());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: 0 CNY
        assertEquals(0.0, totalRevenue, 0.01);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws ParseException {
        // Create rental date (arbitrary date for testing)
        Date rentalDate = dateFormat.parse("2023-01-01 10:00:00");
        
        // Add first rental: Chevrolet Malibu, 120 CNY/day, 2 days
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Brown");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(rentalDate);
        rental1.setTotalPrice(240.0); // 120 * 2
        
        // Add second rental: Hyundai Elantra, 120 CNY/day, 4 days
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Charlie");
        customer2.setSurname("Davis");
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(rentalDate);
        rental2.setTotalPrice(480.0); // 120 * 4
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.01);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws ParseException {
        // Create rental date (arbitrary date for testing)
        Date rentalDate = dateFormat.parse("2023-01-01 10:00:00");
        
        // Add first rental: Mazda 3, 90 CNY/day, 5 days
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        
        Customer customer1 = new Customer();
        customer1.setName("Diana");
        customer1.setSurname("Wilson");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(rentalDate);
        rental1.setTotalPrice(450.0); // 90 * 5
        
        // Add second rental: Kia Sportage, 150 CNY/day, 3 days
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Eve");
        customer2.setSurname("Miller");
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(rentalDate);
        rental2.setTotalPrice(450.0); // 150 * 3
        
        // Add third rental: Ford F-150, 250 CNY/day, 1 day
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        
        Customer customer3 = new Customer();
        customer3.setName("Frank");
        customer3.setSurname("Moore");
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(rentalDate);
        rental3.setTotalPrice(250.0); // 250 * 1
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.01);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws ParseException {
        // Create rental date (arbitrary date for testing)
        Date rentalDate = dateFormat.parse("2023-01-01 10:00:00");
        
        // Add first rental: Mini Cooper, 180 CNY/day, 1 day
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        
        Customer customer1 = new Customer();
        customer1.setName("Grace");
        customer1.setSurname("Taylor");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(rentalDate);
        rental1.setTotalPrice(180.0); // 180 * 1
        
        // Add second rental: Harley Davidson, 220 CNY/day, 1 day
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Henry");
        customer2.setSurname("Anderson");
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(rentalDate);
        rental2.setTotalPrice(220.0); // 220 * 1
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.01);
    }
}