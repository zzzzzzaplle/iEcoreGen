import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleSalespersonWithNonZeroSales() {
        // Create a salesperson with amountOfSales = 1000.00 and commissionPercentage = 0.10
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add the salesperson to the company
        company.addEmployee(salesPerson);
        
        // Calculate total commission and verify result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Company has no salespersons, only other types of employees
        Manager manager = new Manager();
        manager.setDepartment("PRODUCTION");
        company.addEmployee(manager);
        
        ShiftWorker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.0);
        company.addEmployee(worker);
        
        // Calculate total commission and verify result (should be 0)
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Create first salesperson: amountOfSales = 2000.00, commissionPercentage = 0.15
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        // Create second salesperson: amountOfSales = 3000.00, commissionPercentage = 0.20
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        // Add both salespersons to the company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        
        // Calculate total commission and verify result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Create a salesperson with amountOfSales = 0 and commissionPercentage = 0.12
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add the salesperson to the company
        company.addEmployee(salesPerson);
        
        // Calculate total commission and verify result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Create first salesperson: amountOfSales = 1500.00, commissionPercentage = 0.08
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Create second salesperson: amountOfSales = 0, commissionPercentage = 0.10
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        
        // Create third salesperson: amountOfSales = 4000.00, commissionPercentage = 0.25
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        // Add all salespersons to the company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        company.addEmployee(salesPerson3);
        
        // Calculate total commission and verify result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(1120.00, result, 0.001);
    }
}