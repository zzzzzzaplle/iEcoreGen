import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
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
        // Input: Company with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        // Expected Output: Total commission amount is 100.00
        
        // Create sales person with specified values
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add sales person to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate and verify total commission
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Test Case 2: Zero Salespersons in Company
        // Input: Company with no SalesPerson objects
        // Expected Output: Total commission amount is 0
        
        // Company has no employees (default empty list)
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Test Case 3: Multiple Salespersons with Non-zero Sales
        // Input: Company with two SalesPerson objects
        // First: amountOfSales = 2000.00, commissionPercentage = 0.15
        // Second: amountOfSales = 3000.00, commissionPercentage = 0.20
        // Expected Output: Total commission amount is 900.00
        
        // Create first sales person
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        // Create second sales person
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        // Add sales persons to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        company.setEmployees(employees);
        
        // Calculate and verify total commission
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Test Case 4: Single Salesperson with Zero Sales
        // Input: Company with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        // Expected Output: Total commission amount is 0 * 0.12 = 0
        
        // Create sales person with zero sales
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add sales person to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate and verify total commission
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Test Case 5: Multiple Salespersons with Mixed Sales
        // Input: Company with three SalesPerson objects
        // First: amountOfSales = 1500.00, commissionPercentage = 0.08
        // Second: amountOfSales = 0, commissionPercentage = 0.10
        // Third: amountOfSales = 4000.00, commissionPercentage = 0.25
        // Expected Output: Total commission amount is 1120.00
        
        // Create first sales person
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Create second sales person (zero sales)
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        
        // Create third sales person
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        // Add all sales persons to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        employees.add(salesPerson3);
        company.setEmployees(employees);
        
        // Calculate and verify total commission
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(1120.00, result, 0.001);
    }
}