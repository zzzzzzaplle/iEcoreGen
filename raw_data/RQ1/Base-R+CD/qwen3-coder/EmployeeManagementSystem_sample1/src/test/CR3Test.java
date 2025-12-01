import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleSalespersonWithNonZeroSales() {
        // Arrange: Create a company with one salesperson
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Act: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Assert: Expected commission is 100.00 (1000.00 * 0.10)
        assertEquals(100.00, result, 0.01);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Arrange: Create a company with no salespeople
        List<Employee> employees = new ArrayList<>();
        // Add some non-sales employees to ensure we're testing the right scenario
        Manager manager = new Manager();
        manager.setSalary(50000);
        employees.add(manager);
        
        company.setEmployees(employees);
        
        // Act: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Assert: Expected commission is 0 since there are no salespeople
        assertEquals(0.0, result, 0.01);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Arrange: Create a company with two salespeople
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        company.setEmployees(employees);
        
        // Act: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Assert: Expected commission is 900.00 (2000*0.15 + 3000*0.20 = 300 + 600)
        assertEquals(900.00, result, 0.01);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Arrange: Create a company with one salesperson with zero sales
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Act: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Assert: Expected commission is 0 (0 * 0.12)
        assertEquals(0.0, result, 0.01);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Arrange: Create a company with three salespeople with mixed sales
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        employees.add(salesPerson3);
        company.setEmployees(employees);
        
        // Act: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Assert: Expected commission is 1120.00 (1500*0.08 + 0*0.10 + 4000*0.25 = 120 + 0 + 1000)
        assertEquals(1120.00, result, 0.01);
    }
}