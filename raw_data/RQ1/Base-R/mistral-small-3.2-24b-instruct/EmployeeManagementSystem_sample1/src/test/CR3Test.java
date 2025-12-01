import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR3Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new Company object before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleSalespersonWithNonZeroSales() {
        // Create a Salesperson with amountOfSales = 1000.00 and commissionPercentage = 0.10
        Salesperson salesperson = new Salesperson("Sales", "John Doe", 
            LocalDate.of(1990, 1, 1), "123-45-6789", 50000.0, 1000.00, 0.10);
        
        // Add the salesperson to the company
        company.addEmployee(salesperson);
        
        // Calculate total commission and verify it equals 100.00
        double totalCommission = company.calculateTotalCommission();
        assertEquals(100.00, totalCommission, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Company has no salespersons - only add non-salesperson employees
        Worker worker = new Worker("Delivery", "Jane Smith", 
            LocalDate.of(1985, 5, 15), "987-65-4321", 40, 25.0, true);
        Manager manager = new Manager("Management", "Bob Johnson", 
            LocalDate.of(1975, 8, 20), "456-78-9012", "Senior Manager");
        
        company.addEmployee(worker);
        company.addEmployee(manager);
        
        // Calculate total commission and verify it equals 0
        double totalCommission = company.calculateTotalCommission();
        assertEquals(0.0, totalCommission, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Create first salesperson: amountOfSales = 2000.00, commissionPercentage = 0.15
        Salesperson salesperson1 = new Salesperson("Sales", "Alice Brown", 
            LocalDate.of(1988, 3, 10), "111-22-3333", 60000.0, 2000.00, 0.15);
        
        // Create second salesperson: amountOfSales = 3000.00, commissionPercentage = 0.20
        Salesperson salesperson2 = new Salesperson("Sales", "Charlie Davis", 
            LocalDate.of(1992, 7, 25), "444-55-6666", 55000.0, 3000.00, 0.20);
        
        // Add both salespersons to the company
        company.addEmployee(salesperson1);
        company.addEmployee(salesperson2);
        
        // Calculate total commission and verify it equals 900.00
        double totalCommission = company.calculateTotalCommission();
        assertEquals(900.00, totalCommission, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Create a Salesperson with amountOfSales = 0 and commissionPercentage = 0.12
        Salesperson salesperson = new Salesperson("Sales", "Emma Wilson", 
            LocalDate.of(1995, 12, 5), "777-88-9999", 45000.0, 0.0, 0.12);
        
        // Add the salesperson to the company
        company.addEmployee(salesperson);
        
        // Calculate total commission and verify it equals 0
        double totalCommission = company.calculateTotalCommission();
        assertEquals(0.0, totalCommission, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Create first salesperson: amountOfSales = 1500.00, commissionPercentage = 0.08
        Salesperson salesperson1 = new Salesperson("Sales", "Frank Miller", 
            LocalDate.of(1980, 4, 18), "222-33-4444", 52000.0, 1500.00, 0.08);
        
        // Create second salesperson: amountOfSales = 0, commissionPercentage = 0.10
        Salesperson salesperson2 = new Salesperson("Sales", "Grace Lee", 
            LocalDate.of(1993, 9, 30), "555-66-7777", 48000.0, 0.0, 0.10);
        
        // Create third salesperson: amountOfSales = 4000.00, commissionPercentage = 0.25
        Salesperson salesperson3 = new Salesperson("Sales", "Henry Taylor", 
            LocalDate.of(1987, 11, 12), "888-99-0000", 58000.0, 4000.00, 0.25);
        
        // Add all three salespersons to the company
        company.addEmployee(salesperson1);
        company.addEmployee(salesperson2);
        company.addEmployee(salesperson3);
        
        // Calculate total commission and verify it equals 1120.00
        double totalCommission = company.calculateTotalCommission();
        assertEquals(1120.00, totalCommission, 0.001);
    }
}