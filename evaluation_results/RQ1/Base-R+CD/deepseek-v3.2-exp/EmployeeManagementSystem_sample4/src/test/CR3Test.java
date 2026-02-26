import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add salesperson to company
        company.addEmployee(salesPerson);
        
        // Calculate total commission
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result
        assertEquals("Total commission should be 100.00", 100.00, actualCommission, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Test Case 2: Zero Salespersons in Company
        // Input: A Company object with no SalesPerson objects
        // Expected Output: The total commission amount is 0
        
        // Calculate total commission with no salespeople
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result
        assertEquals("Total commission should be 0 when no salespeople exist", 0.0, actualCommission, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Test Case 3: Multiple Salespersons with Non-zero Sales
        // Input: A Company object with two SalesPerson objects. First has amountOfSales = 2000.00 and commissionPercentage = 0.15, 
        // second has amountOfSales = 3000.00 and commissionPercentage = 0.20
        // Expected Output: The total commission amount is 900.00
        
        // Create and configure first salesperson
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        // Create and configure second salesperson
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        // Add salespeople to company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        
        // Calculate total commission
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result
        assertEquals("Total commission should be 900.00", 900.00, actualCommission, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Test Case 4: Single Salesperson with Zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        // Expected Output: The total commission amount is 0 * 0.12 = 0
        
        // Create and configure salesperson with zero sales
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add salesperson to company
        company.addEmployee(salesPerson);
        
        // Calculate total commission
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result
        assertEquals("Total commission should be 0 when sales are zero", 0.0, actualCommission, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Test Case 5: Multiple Salespersons with Mixed Sales
        // Input: A Company object with three SalesPerson objects. First has amountOfSales = 1500.00 and commissionPercentage = 0.08, 
        // second has amountOfSales = 0 and commissionPercentage = 0.10, third has amountOfSales = 4000.00 and commissionPercentage = 0.25
        // Expected Output: The total commission amount is 1120.00
        
        // Create and configure first salesperson
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Create and configure second salesperson (zero sales)
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        
        // Create and configure third salesperson
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        // Add all salespeople to company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        company.addEmployee(salesPerson3);
        
        // Calculate total commission
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result
        assertEquals("Total commission should be 1120.00", 1120.00, actualCommission, 0.001);
    }
}