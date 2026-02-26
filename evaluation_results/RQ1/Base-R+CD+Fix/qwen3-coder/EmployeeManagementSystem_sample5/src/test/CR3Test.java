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
        // Create company with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        company.getEmployees().add(salesPerson);
        
        // Calculate total commission amount
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify the total commission amount is 100.00
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Create company with no SalesPerson objects
        // Company already has no employees from setUp()
        
        // Calculate total commission amount
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify the total commission amount is 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() {
        // Create company with two SalesPerson objects
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        
        company.getEmployees().add(salesPerson1);
        company.getEmployees().add(salesPerson2);
        
        // Calculate total commission amount
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify the total commission amount is 900.00
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() {
        // Create company with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(0);
        salesPerson.setCommissionPercentage(0.12);
        
        company.getEmployees().add(salesPerson);
        
        // Calculate total commission amount
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify the total commission amount is 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() {
        // Create company with three SalesPerson objects
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(0);
        salesPerson2.setCommissionPercentage(0.10);
        
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        
        company.getEmployees().add(salesPerson1);
        company.getEmployees().add(salesPerson2);
        company.getEmployees().add(salesPerson3);
        
        // Calculate total commission amount
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify the total commission amount is 1120.00
        assertEquals(1120.00, result, 0.001);
    }
}