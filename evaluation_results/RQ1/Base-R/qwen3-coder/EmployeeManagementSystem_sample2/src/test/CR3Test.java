import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a fresh Company object before each test
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
        double expectedCommission = 100.00;
        double actualCommission = company.calculateTotalCommission();
        
        assertEquals("Total commission should be 100.00 for single salesperson with 1000 sales and 10% commission", 
                     expectedCommission, actualCommission, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Test Case 2: Zero Salespersons in Company
        // Input: A Company object with no SalesPerson objects
        // Company is empty after setup
        
        // Expected Output: The total commission amount is 0
        double expectedCommission = 0.0;
        double actualCommission = company.calculateTotalCommission();
        
        assertEquals("Total commission should be 0 when there are no salespersons", 
                     expectedCommission, actualCommission, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Test Case 3: Multiple Salespersons with Non-zero Sales
        // Input: A Company object with two SalesPerson objects
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        
        // Expected Output: The total commission amount is 900.00
        double expectedCommission = 900.00;
        double actualCommission = company.calculateTotalCommission();
        
        assertEquals("Total commission should be 900.00 for two salespersons", 
                     expectedCommission, actualCommission, 0.001);
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
        double expectedCommission = 0.0;
        double actualCommission = company.calculateTotalCommission();
        
        assertEquals("Total commission should be 0 for salesperson with zero sales", 
                     expectedCommission, actualCommission, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Test Case 5: Multiple Salespersons with Mixed Sales
        // Input: A Company object with three SalesPerson objects
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        company.addEmployee(salesPerson3);
        
        // Expected Output: The total commission amount is 1120.00
        double expectedCommission = 1120.00;
        double actualCommission = company.calculateTotalCommission();
        
        assertEquals("Total commission should be 1120.00 for three salespersons with mixed sales", 
                     expectedCommission, actualCommission, 0.001);
    }
}