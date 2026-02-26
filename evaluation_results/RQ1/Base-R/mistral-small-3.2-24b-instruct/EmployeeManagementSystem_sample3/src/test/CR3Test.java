import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new Company object before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleSalespersonWithNonZeroSales() {
        // Test Case 1: Single Salesperson with Non-zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        company.addEmployee(salesPerson);
        
        // Expected Output: The total commission amount is 100.00
        double expected = 100.00;
        double actual = company.calculateTotalCommission();
        assertEquals("Total commission for single salesperson with non-zero sales", expected, actual, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Test Case 2: Zero Salespersons in Company
        // Input: A Company object with no SalesPerson objects
        // Company is already empty from setUp()
        
        // Expected Output: The total commission amount is 0
        double expected = 0.0;
        double actual = company.calculateTotalCommission();
        assertEquals("Total commission when no salespersons in company", expected, actual, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Test Case 3: Multiple Salespersons with Non-zero Sales
        // Input: A Company object with two SalesPerson objects
        // First has amountOfSales = 2000.00 and commissionPercentage = 0.15
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        company.addEmployee(salesPerson1);
        
        // Second has amountOfSales = 3000.00 and commissionPercentage = 0.20
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        company.addEmployee(salesPerson2);
        
        // Expected Output: The total commission amount is 900.00
        double expected = 900.00;
        double actual = company.calculateTotalCommission();
        assertEquals("Total commission for multiple salespersons with non-zero sales", expected, actual, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Test Case 4: Single Salesperson with Zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        company.addEmployee(salesPerson);
        
        // Expected Output: The total commission amount is 0 * 0.12 = 0
        double expected = 0.0;
        double actual = company.calculateTotalCommission();
        assertEquals("Total commission for single salesperson with zero sales", expected, actual, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Test Case 5: Multiple Salespersons with Mixed Sales
        // Input: A Company object with three SalesPerson objects
        // First has amountOfSales = 1500.00 and commissionPercentage = 0.08
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        company.addEmployee(salesPerson1);
        
        // Second has amountOfSales = 0 and commissionPercentage = 0.10
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        company.addEmployee(salesPerson2);
        
        // Third has amountOfSales = 4000.00 and commissionPercentage = 0.25
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        company.addEmployee(salesPerson3);
        
        // Expected Output: The total commission amount is 1120.00
        double expected = 1120.00;
        double actual = company.calculateTotalCommission();
        assertEquals("Total commission for multiple salespersons with mixed sales", expected, actual, 0.001);
    }
}