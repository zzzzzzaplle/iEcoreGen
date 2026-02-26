import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

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
        
        // Create and configure salesperson
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add salesperson to company
        company.addEmployee(salesPerson);
        
        // Calculate total commission amount
        double result = company.totalCommissionAmount();
        
        // Verify expected result with delta for floating point precision
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Test Case 2: Zero Salespersons in Company
        // Input: A Company object with no SalesPerson objects
        // Expected Output: The total commission amount is 0
        
        // Add some non-salesperson employees to ensure they don't affect the result
        NonShiftWorker worker = new NonShiftWorker();
        worker.setDepartment(Department.PRODUCTION);
        worker.setWeeklyWorkingHours(40.0);
        worker.setHourlyRate(15.0);
        company.addEmployee(worker);
        
        Manager manager = new Manager();
        manager.setSalaryBase(5000.00);
        company.addEmployee(manager);
        
        // Calculate total commission amount
        double result = company.totalCommissionAmount();
        
        // Verify expected result
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Test Case 3: Multiple Salespersons with Non-zero Sales
        // Input: A Company object with two SalesPerson objects. First has amountOfSales = 2000.00 and commissionPercentage = 0.15, 
        // second has amountOfSales = 3000.00 and commissionPercentage = 0.20
        // Expected Output: The total commission amount is 900.00
        
        // Create and configure first salesperson
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        // Create and configure second salesperson
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        // Add salespersons to company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        
        // Calculate total commission amount
        double result = company.totalCommissionAmount();
        
        // Verify expected result
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Test Case 4: Single Salesperson with Zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        // Expected Output: The total commission amount is 0 * 0.12 = 0
        
        // Create and configure salesperson with zero sales
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setAmountOfSales(0.0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add salesperson to company
        company.addEmployee(salesPerson);
        
        // Calculate total commission amount
        double result = company.totalCommissionAmount();
        
        // Verify expected result
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Test Case 5: Multiple Salespersons with Mixed Sales
        // Input: A Company object with three SalesPerson objects. First has amountOfSales = 1500.00 and commissionPercentage = 0.08, 
        // second has amountOfSales = 0 and commissionPercentage = 0.10, third has amountOfSales = 4000.00 and commissionPercentage = 0.25
        // Expected Output: The total commission amount is 1120.00
        
        // Create and configure first salesperson
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Create and configure second salesperson (zero sales)
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(0.0);
        salesPerson2.setCommissionPercentage(0.10);
        
        // Create and configure third salesperson
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        // Add all salespersons to company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        company.addEmployee(salesPerson3);
        
        // Calculate total commission amount
        double result = company.totalCommissionAmount();
        
        // Verify expected result
        assertEquals(1120.00, result, 0.001);
    }
}