import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleSalespersonWithNonZeroSales() throws Exception {
        // Test Case 1: Single Salesperson with Non-zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        // Expected Output: The total commission amount is 100.00
        
        // Create salesperson with specified parameters
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        SalesPeople salesPerson = new SalesPeople(
            DepartmentType.PRODUCTION.toString(),
            "John Doe",
            birthDate,
            "123-45-6789",
            50000.0, // base salary
            1000.00, // amountOfSales
            0.10     // commissionPercentage (10%)
        );
        
        // Add salesperson to company
        company.getEmployees().add(salesPerson);
        
        // Calculate total commission
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result
        assertEquals("Total commission should be 100.00 for 1000 sales with 10% commission", 
                     100.00, actualCommission, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Test Case 2: Zero Salespersons in Company
        // Input: A Company object with no SalesPerson objects
        // Expected Output: The total commission amount is 0
        
        // Company is already empty from setUp()
        // Calculate total commission
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result
        assertEquals("Total commission should be 0 when no salespeople exist", 
                     0.0, actualCommission, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() throws Exception {
        // Test Case 3: Multiple Salespersons with Non-zero Sales
        // Input: A Company object with two SalesPerson objects. 
        // First has amountOfSales = 2000.00 and commissionPercentage = 0.15
        // Second has amountOfSales = 3000.00 and commissionPercentage = 0.20
        // Expected Output: The total commission amount is 900.00
        
        Date birthDate = dateFormat.parse("1985-05-15 00:00:00");
        
        // Create first salesperson
        SalesPeople salesPerson1 = new SalesPeople(
            DepartmentType.CONTROL.toString(),
            "Alice Smith",
            birthDate,
            "987-65-4321",
            60000.0, // base salary
            2000.00, // amountOfSales
            0.15     // commissionPercentage (15%)
        );
        
        // Create second salesperson
        SalesPeople salesPerson2 = new SalesPeople(
            DepartmentType.DELIVERY.toString(),
            "Bob Johnson",
            birthDate,
            "456-78-9012",
            55000.0, // base salary
            3000.00, // amountOfSales
            0.20     // commissionPercentage (20%)
        );
        
        // Add salespeople to company
        company.getEmployees().add(salesPerson1);
        company.getEmployees().add(salesPerson2);
        
        // Calculate total commission
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result
        assertEquals("Total commission should be 900.00 for combined salespeople", 
                     900.00, actualCommission, 0.001);
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() throws Exception {
        // Test Case 4: Single Salesperson with Zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        // Expected Output: The total commission amount is 0 * 0.12 = 0
        
        Date birthDate = dateFormat.parse("1992-03-20 00:00:00");
        
        // Create salesperson with zero sales
        SalesPeople salesPerson = new SalesPeople(
            DepartmentType.PRODUCTION.toString(),
            "Carol Williams",
            birthDate,
            "234-56-7890",
            48000.0, // base salary
            0.0,     // amountOfSales (zero)
            0.12     // commissionPercentage (12%)
        );
        
        // Add salesperson to company
        company.getEmployees().add(salesPerson);
        
        // Calculate total commission
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result
        assertEquals("Total commission should be 0 when sales are zero", 
                     0.0, actualCommission, 0.001);
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() throws Exception {
        // Test Case 5: Multiple Salespersons with Mixed Sales
        // Input: A Company object with three SalesPerson objects.
        // First has amountOfSales = 1500.00 and commissionPercentage = 0.08
        // Second has amountOfSales = 0 and commissionPercentage = 0.10
        // Third has amountOfSales = 4000.00 and commissionPercentage = 0.25
        // Expected Output: The total commission amount is 1120.00
        
        Date birthDate = dateFormat.parse("1988-07-10 00:00:00");
        
        // Create first salesperson
        SalesPeople salesPerson1 = new SalesPeople(
            DepartmentType.CONTROL.toString(),
            "David Brown",
            birthDate,
            "345-67-8901",
            52000.0, // base salary
            1500.00, // amountOfSales
            0.08     // commissionPercentage (8%)
        );
        
        // Create second salesperson (zero sales)
        SalesPeople salesPerson2 = new SalesPeople(
            DepartmentType.DELIVERY.toString(),
            "Eva Garcia",
            birthDate,
            "567-89-0123",
            49000.0, // base salary
            0.0,     // amountOfSales (zero)
            0.10     // commissionPercentage (10%)
        );
        
        // Create third salesperson
        SalesPeople salesPerson3 = new SalesPeople(
            DepartmentType.PRODUCTION.toString(),
            "Frank Miller",
            birthDate,
            "678-90-1234",
            58000.0, // base salary
            4000.00, // amountOfSales
            0.25     // commissionPercentage (25%)
        );
        
        // Add all salespeople to company
        company.getEmployees().add(salesPerson1);
        company.getEmployees().add(salesPerson2);
        company.getEmployees().add(salesPerson3);
        
        // Calculate total commission
        double actualCommission = company.calculateTotalSalesPeopleCommission();
        
        // Verify the result
        assertEquals("Total commission should be 1120.00 for mixed salespeople", 
                     1120.00, actualCommission, 0.001);
    }
}