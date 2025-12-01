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
    public void testCase1_singleSalespersonWithNonZeroSales() {
        // Create a Company object with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        Salesperson salesperson = new Salesperson();
        salesperson.setAmountOfSales(1000.00);
        salesperson.setCommissionPercentage(0.10);
        company.addEmployee(salesperson);
        
        // Calculate total commission amount
        double result = company.calculateTotalCommission();
        
        // Verify the total commission amount is 100.00
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_zeroSalespersonsInCompany() {
        // Create a Company object with no SalesPerson objects
        // Add other types of employees to ensure they are ignored
        NonShiftWorker worker = new NonShiftWorker();
        worker.setWeeklyWorkingHours(40);
        worker.setHourlyRates(25.0);
        company.addEmployee(worker);
        
        // Calculate total commission amount
        double result = company.calculateTotalCommission();
        
        // Verify the total commission amount is 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleSalespersonsWithNonZeroSales() {
        // Create a Company object with two SalesPerson objects
        Salesperson salesperson1 = new Salesperson();
        salesperson1.setAmountOfSales(2000.00);
        salesperson1.setCommissionPercentage(0.15);
        company.addEmployee(salesperson1);
        
        Salesperson salesperson2 = new Salesperson();
        salesperson2.setAmountOfSales(3000.00);
        salesperson2.setCommissionPercentage(0.20);
        company.addEmployee(salesperson2);
        
        // Calculate total commission amount
        double result = company.calculateTotalCommission();
        
        // Verify the total commission amount is 900.00
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_singleSalespersonWithZeroSales() {
        // Create a Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        Salesperson salesperson = new Salesperson();
        salesperson.setAmountOfSales(0.0);
        salesperson.setCommissionPercentage(0.12);
        company.addEmployee(salesperson);
        
        // Calculate total commission amount
        double result = company.calculateTotalCommission();
        
        // Verify the total commission amount is 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_multipleSalespersonsWithMixedSales() {
        // Create a Company object with three SalesPerson objects
        Salesperson salesperson1 = new Salesperson();
        salesperson1.setAmountOfSales(1500.00);
        salesperson1.setCommissionPercentage(0.08);
        company.addEmployee(salesperson1);
        
        Salesperson salesperson2 = new Salesperson();
        salesperson2.setAmountOfSales(0.0);
        salesperson2.setCommissionPercentage(0.10);
        company.addEmployee(salesperson2);
        
        Salesperson salesperson3 = new Salesperson();
        salesperson3.setAmountOfSales(4000.00);
        salesperson3.setCommissionPercentage(0.25);
        company.addEmployee(salesperson3);
        
        // Calculate total commission amount
        double result = company.calculateTotalCommission();
        
        // Verify the total commission amount is 1120.00
        assertEquals(1120.00, result, 0.001);
    }
}