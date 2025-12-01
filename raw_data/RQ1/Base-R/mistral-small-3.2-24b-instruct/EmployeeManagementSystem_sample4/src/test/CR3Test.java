import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleSalespersonWithNonZeroSales() {
        // Test Case 1: Single Salesperson with Non-zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        // Expected Output: The total commission amount is 100.00
        
        // Create salesperson with specified parameters
        SalesPerson salesPerson = new SalesPerson(
            "Sales", 
            "John Doe", 
            LocalDate.of(1990, 1, 1), 
            "123-45-6789", 
            2000.00,  // fixed salary
            1000.00,  // amount of sales
            0.10      // commission percentage
        );
        
        // Add salesperson to company
        company.addEmployee(salesPerson);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalCommission();
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Test Case 2: Zero Salespersons in Company
        // Input: A Company object with no SalesPerson objects
        // Expected Output: The total commission amount is 0
        
        // Company has no salespersons (only other employee types or empty)
        // Add a non-salesperson employee to ensure company is not empty
        Worker worker = new Worker(
            "Delivery",
            "Jane Smith",
            LocalDate.of(1985, 5, 15),
            "987-65-4321",
            40.0,
            25.0,
            true
        );
        company.addEmployee(worker);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalCommission();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Test Case 3: Multiple Salespersons with Non-zero Sales
        // Input: A Company object with two SalesPerson objects. First has amountOfSales = 2000.00 and commissionPercentage = 0.15, 
        // second has amountOfSales = 3000.00 and commissionPercentage = 0.20
        // Expected Output: The total commission amount is 900.00
        
        // Create first salesperson
        SalesPerson salesPerson1 = new SalesPerson(
            "Sales", 
            "Alice Johnson", 
            LocalDate.of(1988, 3, 10), 
            "111-22-3333", 
            2500.00,  // fixed salary
            2000.00,  // amount of sales
            0.15      // commission percentage
        );
        
        // Create second salesperson
        SalesPerson salesPerson2 = new SalesPerson(
            "Sales", 
            "Bob Wilson", 
            LocalDate.of(1992, 7, 25), 
            "444-55-6666", 
            2800.00,  // fixed salary
            3000.00,  // amount of sales
            0.20      // commission percentage
        );
        
        // Add both salespersons to company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalCommission();
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Test Case 4: Single Salesperson with Zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        // Expected Output: The total commission amount is 0 * 0.12 = 0
        
        // Create salesperson with zero sales
        SalesPerson salesPerson = new SalesPerson(
            "Sales", 
            "Charlie Brown", 
            LocalDate.of(1995, 12, 5), 
            "777-88-9999", 
            2200.00,  // fixed salary
            0.00,     // amount of sales (zero)
            0.12      // commission percentage
        );
        
        // Add salesperson to company
        company.addEmployee(salesPerson);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalCommission();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Test Case 5: Multiple Salespersons with Mixed Sales
        // Input: A Company object with three SalesPerson objects. First has amountOfSales = 1500.00 and commissionPercentage = 0.08, 
        // second has amountOfSales = 0 and commissionPercentage = 0.10, third has amountOfSales = 4000.00 and commissionPercentage = 0.25
        // Expected Output: The total commission amount is 1120.00
        
        // Create first salesperson
        SalesPerson salesPerson1 = new SalesPerson(
            "Sales", 
            "David Lee", 
            LocalDate.of(1987, 4, 18), 
            "123-45-6780", 
            2300.00,  // fixed salary
            1500.00,  // amount of sales
            0.08      // commission percentage
        );
        
        // Create second salesperson (with zero sales)
        SalesPerson salesPerson2 = new SalesPerson(
            "Sales", 
            "Emma Davis", 
            LocalDate.of(1993, 9, 30), 
            "123-45-6781", 
            2400.00,  // fixed salary
            0.00,     // amount of sales (zero)
            0.10      // commission percentage
        );
        
        // Create third salesperson
        SalesPerson salesPerson3 = new SalesPerson(
            "Sales", 
            "Frank Miller", 
            LocalDate.of(1980, 11, 12), 
            "123-45-6782", 
            2600.00,  // fixed salary
            4000.00,  // amount of sales
            0.25      // commission percentage
        );
        
        // Add all salespersons to company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        company.addEmployee(salesPerson3);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalCommission();
        assertEquals(1120.00, result, 0.001);
    }
}