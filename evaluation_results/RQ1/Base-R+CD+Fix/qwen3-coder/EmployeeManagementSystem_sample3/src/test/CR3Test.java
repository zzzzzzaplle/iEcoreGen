import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        
        // Create salesperson and set values
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add salesperson to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Test Case 2: Zero Salespersons in Company
        // Input: A Company object with no SalesPerson objects
        // Expected Output: The total commission amount is 0
        
        // Company has no employees (default empty list)
        // Calculate total commission and verify expected result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Test Case 3: Multiple Salespersons with Non-zero Sales
        // Input: A Company object with two SalesPerson objects. First has amountOfSales = 2000.00 and commissionPercentage = 0.15, 
        // second has amountOfSales = 3000.00 and commissionPercentage = 0.20
        // Expected Output: The total commission amount is 900.00
        
        // Create first salesperson
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        // Create second salesperson
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        // Add salespersons to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        company.setEmployees(employees);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Test Case 4: Single Salesperson with Zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        // Expected Output: The total commission amount is 0 * 0.12 = 0
        
        // Create salesperson with zero sales
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(0.0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add salesperson to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Test Case 5: Multiple Salespersons with Mixed Sales
        // Input: A Company object with three SalesPerson objects. First has amountOfSales = 1500.00 and commissionPercentage = 0.08, 
        // second has amountOfSales = 0 and commissionPercentage = 0.10, third has amountOfSales = 4000.00 and commissionPercentage = 0.25
        // Expected Output: The total commission amount is 1120.00
        
        // Create first salesperson
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Create second salesperson with zero sales
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(0.0);
        salesPerson2.setCommissionPercentage(0.10);
        
        // Create third salesperson
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        // Add all salespersons to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        employees.add(salesPerson3);
        company.setEmployees(employees);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(1120.00, result, 0.001);
    }
}