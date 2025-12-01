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
        // Input: A Company object with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        company.addEmployee(salesPerson);
        
        // Expected Output: The total commission amount is 100.00
        double result = company.calculateTotalCommission();
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Input: A Company object with no SalesPerson objects
        // Company is empty after setUp()
        
        // Expected Output: The total commission amount is 0
        double result = company.calculateTotalCommission();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Input: A Company object with two SalesPerson objects
        // First has amountOfSales = 2000.00 and commissionPercentage = 0.15
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        company.addEmployee(salesPerson1);
        
        // Second has amountOfSales = 3000.00 and commissionPercentage = 0.20
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        company.addEmployee(salesPerson2);
        
        // Expected Output: The total commission amount is 900.00
        double result = company.calculateTotalCommission();
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Input: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        
        company.addEmployee(salesPerson);
        
        // Expected Output: The total commission amount is 0 * 0.12 = 0
        double result = company.calculateTotalCommission();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Input: A Company object with three SalesPerson objects
        // First has amountOfSales = 1500.00 and commissionPercentage = 0.08
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        company.addEmployee(salesPerson1);
        
        // Second has amountOfSales = 0 and commissionPercentage = 0.10
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        company.addEmployee(salesPerson2);
        
        // Third has amountOfSales = 4000.00 and commissionPercentage = 0.25
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        company.addEmployee(salesPerson3);
        
        // Expected Output: The total commission amount is 1120.00
        double result = company.calculateTotalCommission();
        assertEquals(1120.00, result, 0.001);
    }
}