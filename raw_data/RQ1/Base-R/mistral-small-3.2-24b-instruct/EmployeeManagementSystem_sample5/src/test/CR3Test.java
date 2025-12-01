import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleSalespersonWithNonZeroSales() {
        // Create a SalesPerson with amountOfSales = 1000.00 and commissionPercentage = 0.10
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add the SalesPerson to the company
        company.addEmployee(salesPerson);
        
        // Calculate total commission and verify expected result
        double totalCommission = company.calculateTotalCommission();
        assertEquals(100.00, totalCommission, 0.001);
    }
    
    @Test
    public void testCase2_zeroSalespersonsInCompany() {
        // Company has no SalesPerson objects, only other employee types
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(40);
        worker.setHourlyRate(15.0);
        worker.setDepartment(Department.PRODUCTION);
        
        Manager manager = new Manager();
        manager.setPosition("Team Lead");
        
        // Add non-SalesPerson employees to company
        company.addEmployee(worker);
        company.addEmployee(manager);
        
        // Calculate total commission and verify it's 0
        double totalCommission = company.calculateTotalCommission();
        assertEquals(0.0, totalCommission, 0.001);
    }
    
    @Test
    public void testCase3_multipleSalespersonsWithNonZeroSales() {
        // Create first SalesPerson with amountOfSales = 2000.00 and commissionPercentage = 0.15
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        // Create second SalesPerson with amountOfSales = 3000.00 and commissionPercentage = 0.20
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        // Add both SalesPerson objects to the company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        
        // Calculate total commission and verify expected result
        double totalCommission = company.calculateTotalCommission();
        assertEquals(900.00, totalCommission, 0.001);
    }
    
    @Test
    public void testCase4_singleSalespersonWithZeroSales() {
        // Create a SalesPerson with amountOfSales = 0 and commissionPercentage = 0.12
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add the SalesPerson to the company
        company.addEmployee(salesPerson);
        
        // Calculate total commission and verify it's 0
        double totalCommission = company.calculateTotalCommission();
        assertEquals(0.0, totalCommission, 0.001);
    }
    
    @Test
    public void testCase5_multipleSalespersonsWithMixedSales() {
        // Create first SalesPerson with amountOfSales = 1500.00 and commissionPercentage = 0.08
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Create second SalesPerson with amountOfSales = 0 and commissionPercentage = 0.10
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        
        // Create third SalesPerson with amountOfSales = 4000.00 and commissionPercentage = 0.25
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        // Add all three SalesPerson objects to the company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        company.addEmployee(salesPerson3);
        
        // Calculate total commission and verify expected result
        double totalCommission = company.calculateTotalCommission();
        assertEquals(1120.00, totalCommission, 0.001);
    }
}