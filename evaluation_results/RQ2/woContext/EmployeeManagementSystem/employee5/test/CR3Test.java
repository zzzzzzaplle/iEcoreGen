package edu.employee.employee5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.employee.EmployeeFactory;
import edu.employee.EmployeePackage;
import edu.employee.Company;
import edu.employee.SalesPeople;

public class CR3Test {
    
    private EmployeeFactory factory;
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = EmployeeFactory.eINSTANCE;
        // Create a new company instance
        company = factory.createCompany();
    }
    
    @Test
    public void testCase1_SingleSalespersonWithNonZeroSales() {
        // Input: A Company object with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add the salesperson to the company
        company.getEmployees().add(salesPerson);
        
        // Expected Output: The total commission amount is 100.00
        double expectedCommission = 100.00;
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        assertEquals("Single salesperson with non-zero sales should calculate commission correctly", 
                     expectedCommission, actualCommission, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Input: A Company object with no SalesPerson objects
        // No salespersons added to the company
        
        // Expected Output: The total commission amount is 0
        double expectedCommission = 0.0;
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        assertEquals("Company with no salespersons should return zero commission", 
                     expectedCommission, actualCommission, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Input: A Company object with two SalesPerson objects
        // First has amountOfSales = 2000.00 and commissionPercentage = 0.15
        SalesPeople salesPerson1 = factory.createSalesPeople();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        // Second has amountOfSales = 3000.00 and commissionPercentage = 0.20
        SalesPeople salesPerson2 = factory.createSalesPeople();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        // Add both salespersons to the company
        company.getEmployees().add(salesPerson1);
        company.getEmployees().add(salesPerson2);
        
        // Expected Output: The total commission amount is 900.00
        // Calculation: (2000 * 0.15) + (3000 * 0.20) = 300 + 600 = 900
        double expectedCommission = 900.00;
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        assertEquals("Multiple salespersons with non-zero sales should sum commissions correctly", 
                     expectedCommission, actualCommission, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Input: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setAmountOfSales(0.0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add the salesperson to the company
        company.getEmployees().add(salesPerson);
        
        // Expected Output: The total commission amount is 0 * 0.12 = 0
        double expectedCommission = 0.0;
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        assertEquals("Salesperson with zero sales should return zero commission", 
                     expectedCommission, actualCommission, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Input: A Company object with three SalesPerson objects
        // First has amountOfSales = 1500.00 and commissionPercentage = 0.08
        SalesPeople salesPerson1 = factory.createSalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Second has amountOfSales = 0 and commissionPercentage = 0.10
        SalesPeople salesPerson2 = factory.createSalesPeople();
        salesPerson2.setAmountOfSales(0.0);
        salesPerson2.setCommissionPercentage(0.10);
        
        // Third has amountOfSales = 4000.00 and commissionPercentage = 0.25
        SalesPeople salesPerson3 = factory.createSalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        // Add all salespersons to the company
        company.getEmployees().add(salesPerson1);
        company.getEmployees().add(salesPerson2);
        company.getEmployees().add(salesPerson3);
        
        // Expected Output: The total commission amount is 1120.00
        // Calculation: (1500 * 0.08) + (0 * 0.10) + (4000 * 0.25) = 120 + 0 + 1000 = 1120
        double expectedCommission = 1120.00;
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        assertEquals("Multiple salespersons with mixed sales should calculate total commission correctly", 
                     expectedCommission, actualCommission, 0.001);
    }
}