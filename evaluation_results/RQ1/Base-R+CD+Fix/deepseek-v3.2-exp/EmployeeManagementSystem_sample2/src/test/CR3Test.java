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
    public void testCase1_SingleSalespersonWithNonZeroSales() {
        // Create a SalesPerson with amountOfSales = 1000.00 and commissionPercentage = 0.10
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add the salesperson to the company
        company.addEmployee(salesPerson);
        
        // Calculate total commission and verify it equals 100.00
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        assertEquals(100.00, totalCommission, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Create a company with no SalesPerson objects
        // Add some non-sales employees to ensure they don't affect the calculation
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        company.addEmployee(manager);
        
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(15.00);
        company.addEmployee(worker);
        
        // Calculate total commission and verify it equals 0
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        assertEquals(0.00, totalCommission, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Create first SalesPerson: amountOfSales = 2000.00, commissionPercentage = 0.15
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        // Create second SalesPerson: amountOfSales = 3000.00, commissionPercentage = 0.20
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        // Add both salespersons to the company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        
        // Calculate total commission and verify it equals 900.00
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        assertEquals(900.00, totalCommission, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Create a SalesPerson with amountOfSales = 0 and commissionPercentage = 0.12
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add the salesperson to the company
        company.addEmployee(salesPerson);
        
        // Calculate total commission and verify it equals 0
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        assertEquals(0.00, totalCommission, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Create first SalesPerson: amountOfSales = 1500.00, commissionPercentage = 0.08
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Create second SalesPerson: amountOfSales = 0, commissionPercentage = 0.10
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        
        // Create third SalesPerson: amountOfSales = 4000.00, commissionPercentage = 0.25
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        // Add all three salespersons to the company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        company.addEmployee(salesPerson3);
        
        // Calculate total commission and verify it equals 1120.00
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        assertEquals(1120.00, totalCommission, 0.001);
    }
}