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
        // Setup: Create a Company object with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        company.addEmployee(salesPerson);
        
        // Execute: Calculate total commission amount
        double result = company.totalCommissionAmount();
        
        // Verify: The total commission amount is 100.00
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Setup: A Company object with no SalesPerson objects
        // (company is empty after setUp)
        
        // Execute: Calculate total commission amount
        double result = company.totalCommissionAmount();
        
        // Verify: The total commission amount is 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Setup: A Company object with two SalesPerson objects
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        company.addEmployee(salesPerson1);
        
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        company.addEmployee(salesPerson2);
        
        // Execute: Calculate total commission amount
        double result = company.totalCommissionAmount();
        
        // Verify: The total commission amount is 900.00
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Setup: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        company.addEmployee(salesPerson);
        
        // Execute: Calculate total commission amount
        double result = company.totalCommissionAmount();
        
        // Verify: The total commission amount is 0 * 0.12 = 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Setup: A Company object with three SalesPerson objects
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        company.addEmployee(salesPerson1);
        
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        company.addEmployee(salesPerson2);
        
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        company.addEmployee(salesPerson3);
        
        // Execute: Calculate total commission amount
        double result = company.totalCommissionAmount();
        
        // Verify: The total commission amount is 1120.00
        assertEquals(1120.00, result, 0.001);
    }
}