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
        // Setup: Create Company with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Execute: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify: The total commission amount is 100.00
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Setup: Create Company with no SalesPerson objects
        List<Employee> employees = new ArrayList<>();
        // Add non-sales employees to ensure they don't affect commission calculation
        Manager manager = new Manager();
        manager.setSalary(50000.00);
        employees.add(manager);
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(25.00);
        shiftWorker.setHolidayPremium(100.00);
        employees.add(shiftWorker);
        
        company.setEmployees(employees);
        
        // Execute: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify: The total commission amount is 0
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Setup: Create Company with two SalesPerson objects
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
        
        // Execute: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify: The total commission amount is 900.00
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Setup: Create Company with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(0.00);
        salesPerson.setCommissionPercentage(0.12);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Execute: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify: The total commission amount is 0 * 0.12 = 0
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Setup: Create Company with three SalesPerson objects
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(0.00);
        salesPerson2.setCommissionPercentage(0.10);
        
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        employees.add(salesPerson3);
        company.setEmployees(employees);
        
        // Execute: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify: The total commission amount is 1120.00
        assertEquals(1120.00, result, 0.001);
    }
}