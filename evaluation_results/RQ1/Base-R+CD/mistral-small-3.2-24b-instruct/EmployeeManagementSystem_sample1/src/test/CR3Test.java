import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
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
        company.getEmployees().add(salesPerson);
        
        // Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result is 100.00
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Company has no employees (default empty list)
        
        // Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result is 0
        assertEquals(0.0, result, 0.001);
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
        company.getEmployees().add(salesPerson1);
        company.getEmployees().add(salesPerson2);
        
        // Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result is 900.00 (2000*0.15 + 3000*0.20 = 300 + 600 = 900)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Create a SalesPerson with amountOfSales = 0 and commissionPercentage = 0.12
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        
        // Add the SalesPerson to the company
        company.getEmployees().add(salesPerson);
        
        // Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result is 0 (0 * 0.12 = 0)
        assertEquals(0.0, result, 0.001);
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
        company.getEmployees().add(salesPerson1);
        company.getEmployees().add(salesPerson2);
        company.getEmployees().add(salesPerson3);
        
        // Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result is 1120.00 (1500*0.08 + 0*0.10 + 4000*0.25 = 120 + 0 + 1000 = 1120)
        assertEquals(1120.00, result, 0.001);
    }
}