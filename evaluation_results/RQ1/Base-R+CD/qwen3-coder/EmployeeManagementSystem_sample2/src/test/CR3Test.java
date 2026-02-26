// IMPORTANT: Do not include package declaration
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
    public void testCase1_singleSalespersonWithNonZeroSales() {
        // Create a salesperson with amountOfSales = 1000.00 and commissionPercentage = 0.10
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add the salesperson to the company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate total commission and verify it equals 100.00
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        assertEquals(100.00, totalCommission, 0.001);
    }
    
    @Test
    public void testCase2_zeroSalespersonsInCompany() {
        // Create a company with no salespeople
        List<Employee> employees = new ArrayList<>();
        // Add other types of employees to ensure the method only counts salespeople
        Manager manager = new Manager();
        manager.setSalary(5000);
        employees.add(manager);
        
        Worker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20);
        employees.add(worker);
        
        company.setEmployees(employees);
        
        // Calculate total commission and verify it equals 0
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        assertEquals(0.0, totalCommission, 0.001);
    }
    
    @Test
    public void testCase3_multipleSalespersonsWithNonZeroSales() {
        // Create first salesperson with amountOfSales = 2000.00 and commissionPercentage = 0.15
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        // Create second salesperson with amountOfSales = 3000.00 and commissionPercentage = 0.20
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        // Add both salespeople to the company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        company.setEmployees(employees);
        
        // Calculate total commission and verify it equals 900.00 (2000*0.15 + 3000*0.20 = 300 + 600 = 900)
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        assertEquals(900.00, totalCommission, 0.001);
    }
    
    @Test
    public void testCase4_singleSalespersonWithZeroSales() {
        // Create a salesperson with amountOfSales = 0 and commissionPercentage = 0.12
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add the salesperson to the company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate total commission and verify it equals 0 (0 * 0.12 = 0)
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        assertEquals(0.0, totalCommission, 0.001);
    }
    
    @Test
    public void testCase5_multipleSalespersonsWithMixedSales() {
        // Create first salesperson with amountOfSales = 1500.00 and commissionPercentage = 0.08
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Create second salesperson with amountOfSales = 0 and commissionPercentage = 0.10
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        
        // Create third salesperson with amountOfSales = 4000.00 and commissionPercentage = 0.25
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        // Add all salespeople to the company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        employees.add(salesPerson3);
        company.setEmployees(employees);
        
        // Calculate total commission and verify it equals 1120.00 (1500*0.08 + 0*0.10 + 4000*0.25 = 120 + 0 + 1000 = 1120)
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        assertEquals(1120.00, totalCommission, 0.001);
    }
}