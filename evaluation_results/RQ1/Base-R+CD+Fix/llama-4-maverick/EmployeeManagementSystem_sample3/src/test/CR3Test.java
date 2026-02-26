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
        // Create a SalesPerson with amountOfSales = 1000.00 and commissionPercentage = 0.10
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add the SalesPerson to the company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals("Total commission should be 100.00 for single salesperson", 100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Create company with no SalesPerson objects
        List<Employee> employees = new ArrayList<>();
        company.setEmployees(employees);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals("Total commission should be 0 when no salespeople exist", 0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Create first SalesPerson with amountOfSales = 2000.00 and commissionPercentage = 0.15
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        // Create second SalesPerson with amountOfSales = 3000.00 and commissionPercentage = 0.20
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        // Add both SalesPersons to the company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        company.setEmployees(employees);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals("Total commission should be 900.00 for multiple salespeople", 900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Create a SalesPerson with amountOfSales = 0 and commissionPercentage = 0.12
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add the SalesPerson to the company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals("Total commission should be 0 when sales are zero", 0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Create first SalesPerson with amountOfSales = 1500.00 and commissionPercentage = 0.08
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Create second SalesPerson with amountOfSales = 0 and commissionPercentage = 0.10
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        
        // Create third SalesPerson with amountOfSales = 4000.00 and commissionPercentage = 0.25
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        // Add all three SalesPersons to the company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        employees.add(salesPerson3);
        company.setEmployees(employees);
        
        // Calculate total commission and verify expected result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals("Total commission should be 1120.00 for mixed sales scenarios", 1120.00, result, 0.001);
    }
}