import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a fresh Company object before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_singleSalespersonWithNonZeroSales() {
        // Test Case 1: Single Salesperson with Non-zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        // Expected Output: The total commission amount is 100.00
        
        // Create and configure a SalesPerson
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add the SalesPerson to the company
        company.addEmployee(salesPerson);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalCommission();
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_zeroSalespersonsInCompany() {
        // Test Case 2: Zero Salespersons in Company
        // Input: A Company object with no SalesPerson objects
        // Expected Output: The total commission amount is 0
        
        // Company has no employees, so no salespersons
        double result = company.calculateTotalCommission();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleSalespersonsWithNonZeroSales() {
        // Test Case 3: Multiple Salespersons with Non-zero Sales
        // Input: A Company object with two SalesPerson objects. 
        // First has amountOfSales = 2000.00 and commissionPercentage = 0.15, 
        // second has amountOfSales = 3000.00 and commissionPercentage = 0.20
        // Expected Output: The total commission amount is 900.00
        
        // Create and configure first SalesPerson
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        // Create and configure second SalesPerson
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        // Add both SalesPersons to the company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalCommission();
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_singleSalespersonWithZeroSales() {
        // Test Case 4: Single Salesperson with Zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        // Expected Output: The total commission amount is 0 * 0.12 = 0
        
        // Create and configure a SalesPerson with zero sales
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add the SalesPerson to the company
        company.addEmployee(salesPerson);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalCommission();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_multipleSalespersonsWithMixedSales() {
        // Test Case 5: Multiple Salespersons with Mixed Sales
        // Input: A Company object with three SalesPerson objects.
        // First has amountOfSales = 1500.00 and commissionPercentage = 0.08,
        // second has amountOfSales = 0 and commissionPercentage = 0.10,
        // third has amountOfSales = 4000.00 and commissionPercentage = 0.25
        // Expected Output: The total commission amount is 1120.00
        
        // Create and configure first SalesPerson
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Create and configure second SalesPerson (zero sales)
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        
        // Create and configure third SalesPerson
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        // Add all SalesPersons to the company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        company.addEmployee(salesPerson3);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalCommission();
        assertEquals(1120.00, result, 0.001);
    }
}