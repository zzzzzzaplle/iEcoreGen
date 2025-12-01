package edu.employee.employee1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.employee.Company;
import edu.employee.SalesPeople;
import edu.employee.EmployeeFactory;
import edu.employee.EmployeePackage;

public class CR3Test {
    
    private EmployeeFactory factory;
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize the factory and company using Ecore factory pattern
        factory = EmployeeFactory.eINSTANCE;
        company = factory.createCompany();
    }
    
    @Test
    public void testCase1_SingleSalespersonWithNonZeroSales() {
        // Test Case 1: Single Salesperson with Non-zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        // Expected Output: The total commission amount is 100.00
        
        // Create salesperson using factory
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add salesperson to company
        company.getEmployees().add(salesPerson);
        
        // Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify result with delta for floating point comparison
        assertEquals("Total commission should be 100.00 for single salesperson with 1000 sales and 10% commission", 
                     100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Test Case 2: Zero Salespersons in Company
        // Input: A Company object with no SalesPerson objects
        // Expected Output: The total commission amount is 0
        
        // Company has no employees (default state)
        
        // Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify result
        assertEquals("Total commission should be 0 when company has no salespersons", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Test Case 3: Multiple Salespersons with Non-zero Sales
        // Input: A Company object with two SalesPerson objects. 
        // First has amountOfSales = 2000.00 and commissionPercentage = 0.15, 
        // second has amountOfSales = 3000.00 and commissionPercentage = 0.20
        // Expected Output: The total commission amount is 900.00
        
        // Create first salesperson
        SalesPeople salesPerson1 = factory.createSalesPeople();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        // Create second salesperson
        SalesPeople salesPerson2 = factory.createSalesPeople();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        // Add both salespersons to company
        company.getEmployees().add(salesPerson1);
        company.getEmployees().add(salesPerson2);
        
        // Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify result
        assertEquals("Total commission should be 900.00 for two salespersons with 2000*0.15 + 3000*0.20", 
                     900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Test Case 4: Single Salesperson with Zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        // Expected Output: The total commission amount is 0 * 0.12 = 0
        
        // Create salesperson with zero sales
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setAmountOfSales(0.0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add salesperson to company
        company.getEmployees().add(salesPerson);
        
        // Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify result
        assertEquals("Total commission should be 0 for salesperson with zero sales regardless of commission percentage", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Test Case 5: Multiple Salespersons with Mixed Sales
        // Input: A Company object with three SalesPerson objects. 
        // First has amountOfSales = 1500.00 and commissionPercentage = 0.08, 
        // second has amountOfSales = 0 and commissionPercentage = 0.10, 
        // third has amountOfSales = 4000.00 and commissionPercentage = 0.25
        // Expected Output: The total commission amount is 1120.00
        
        // Create first salesperson
        SalesPeople salesPerson1 = factory.createSalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Create second salesperson (zero sales)
        SalesPeople salesPerson2 = factory.createSalesPeople();
        salesPerson2.setAmountOfSales(0.0);
        salesPerson2.setCommissionPercentage(0.10);
        
        // Create third salesperson
        SalesPeople salesPerson3 = factory.createSalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        // Add all salespersons to company
        company.getEmployees().add(salesPerson1);
        company.getEmployees().add(salesPerson2);
        company.getEmployees().add(salesPerson3);
        
        // Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify result
        assertEquals("Total commission should be 1120.00 for mixed salespersons: 1500*0.08 + 0*0.10 + 4000*0.25", 
                     1120.00, result, 0.001);
    }
}