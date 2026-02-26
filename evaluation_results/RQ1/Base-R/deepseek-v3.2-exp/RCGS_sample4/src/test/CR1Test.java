import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    private CarRentalSystem system;
    
    @Before
    public void setUp() {
        system = new CarRentalSystem();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create a Store instance named "City Car Rentals"
        system = new CarRentalSystem();
        
        // Add a car with plate: "ABC123", model: "Toyota Camry", daily price: 500, and status: available
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(500.0);
        car1.setAvailable(true);
        
        // Add a car with plate: "XYZ789", model: "Honda Accord", daily price: 600, and status: rented
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(600.0);
        car2.setAvailable(false);
        
        // Add a car with plate: "DEF456", model: "Ford Focus", daily price: 450, and status: available
        Car car3 = new Car();
        car3.setPlate("DEF456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(450.0);
        car3.setAvailable(true);
        
        system.addCar(car1);
        system.addCar(car2);
        system.addCar(car3);
        
        // Execute: Check for available cars in the store
        List<Car> availableCars = system.getAvailableCarsSortedByPrice();
        
        // Verify: Available cars list sorted by price = [{"plate": "DEF456", "model": "Ford Focus", "daily price": 450}, {"plate": "ABC123", "model": "Toyota Camry", "daily price": 500}]
        assertEquals(2, availableCars.size());
        
        // First car should be the cheapest one
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Second car should be the next cheapest
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create a Store instance named "Downtown Rentals"
        system = new CarRentalSystem();
        
        // Add a car with plate: "AAA111", model: "Nissan Altima", daily price: 600, and status: rented
        Car car1 = new Car();
        car1.setPlate("AAA111");
        car1.setModel("Nissan Altima");
        car1.setDailyPrice(600.0);
        car1.setAvailable(false);
        
        // Add a car with plate: "BBB222", model: "Chevy Malibu", daily price: 700, and status: rented
        Car car2 = new Car();
        car2.setPlate("BBB222");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(700.0);
        car2.setAvailable(false);
        
        // Add a car with plate: "CCC333", model: "Kia Optima", daily price: 650, and status: rented
        Car car3 = new Car();
        car3.setPlate("CCC333");
        car3.setModel("Kia Optima");
        car3.setDailyPrice(650.0);
        car3.setAvailable(false);
        
        system.addCar(car1);
        system.addCar(car2);
        system.addCar(car3);
        
        // Execute: Check for available cars in the store
        List<Car> availableCars = system.getAvailableCarsSortedByPrice();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        system = new CarRentalSystem();
        
        // Add a car with plate: "LMN456", model: "Porsche 911", daily price: 1500, and status: available
        Car car1 = new Car();
        car1.setPlate("LMN456");
        car1.setModel("Porsche 911");
        car1.setDailyPrice(1500.0);
        car1.setAvailable(true);
        
        // Add a car with plate: "OPQ789", model: "Mercedes Benz", daily price: 1200, and status: rented
        Car car2 = new Car();
        car2.setPlate("OPQ789");
        car2.setModel("Mercedes Benz");
        car2.setDailyPrice(1200.0);
        car2.setAvailable(false);
        
        // Add a car with plate: "RST012", model: "BMW 5 Series", daily price: 1300, and status: available
        Car car3 = new Car();
        car3.setPlate("RST012");
        car3.setModel("BMW 5 Series");
        car3.setDailyPrice(1300.0);
        car3.setAvailable(true);
        
        system.addCar(car1);
        system.addCar(car2);
        system.addCar(car3);
        
        // Execute: Check for available cars in the store
        List<Car> availableCars = system.getAvailableCarsSortedByPrice();
        
        // Verify: Available cars list = [ {"plate": "RST012", "model": "BMW 5 Series", "daily price": 1300},{"plate": "LMN456", "model": "Porsche 911", "daily price": 1500}]
        assertEquals(2, availableCars.size());
        
        // First car should be the cheapest available one (BMW 5 Series)
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Second car should be the more expensive available one (Porsche 911)
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals"
        system = new CarRentalSystem();
        
        // Execute: Check for available cars in the store
        List<Car> availableCars = system.getAvailableCarsSortedByPrice();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        system = new CarRentalSystem();
        
        // Add a car with plate: "GHI789", model: "Subaru Impreza", daily price: 400, and status: rented
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        car1.setAvailable(false);
        
        // Add a car with plate: "JKL012", model: "Mazda 3", daily price: 350, and status: available
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        car2.setAvailable(true);
        
        system.addCar(car1);
        system.addCar(car2);
        
        // Execute: Check for available cars in the store
        List<Car> availableCars = system.getAvailableCarsSortedByPrice();
        
        // Verify: Available cars list = [{"plate": "JKL012", "model": "Mazda 3", "daily price": 350}]
        assertEquals(1, availableCars.size());
        
        // Only one available car should be returned
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}