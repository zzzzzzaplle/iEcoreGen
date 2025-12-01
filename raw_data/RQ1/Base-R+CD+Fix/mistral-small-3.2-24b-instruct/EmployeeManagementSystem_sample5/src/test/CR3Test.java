import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize company and date format before each test
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleSalespersonWithNonZeroSales() throws Exception {
        // Test Case 1: Single Salesperson with Non-zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        // Expected Output: The total commission amount is 100.00
        
        // Create sales person with specified parameters
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        SalesPeople salesPerson = new SalesPeople("Sales", "John Doe", birthDate, "123-45-6789", 50000.0, 1000.00, 0.10);
        
        // Add sales person to company
        company.addEmployee(salesPerson);
        
        // Calculate total commission and verify result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_ZeroSalespersonsInCompany() {
        // Test Case 2: Zero Salespersons in Company
        // Input: A Company object with no SalesPerson objects
        // Expected Output: The total commission amount is 0
        
        // Company has no employees by default, so calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleSalespersonsWithNonZeroSales() throws Exception {
        // Test Case 3: Multiple Salespersons with Non-zero Sales
        // Input: A Company object with two SalesPerson objects. First has amountOfSales = 2000.00 and commissionPercentage = 0.15, 
        // second has amountOfSales = 3000.00 and commissionPercentage = 0.20.
        // Expected Output: The total commission amount is 900.00
        
        // Create first sales person
        Date birthDate1 = dateFormat.parse("1985-05-15 00:00:00");
        SalesPeople salesPerson1 = new SalesPeople("Sales", "Alice Smith", birthDate1, "987-65-4321", 60000.0, 2000.00, 0.15);
        
        // Create second sales person
        Date birthDate2 = dateFormat.parse("1988-08-20 00:00:00");
        SalesPeople salesPerson2 = new SalesPeople("Sales", "Bob Johnson", birthDate2, "456-78-9012", 55000.0, 3000.00, 0.20);
        
        // Add both sales persons to company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        
        // Calculate total commission and verify result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(900.00, result, 0.001); // 2000*0.15 + 3000*0.20 = 300 + 600 = 900
    }
    
    @Test
    public void testCase4_SingleSalespersonWithZeroSales() throws Exception {
        // Test Case 4: Single Salesperson with Zero Sales
        // Input: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        // Expected Output: The total commission amount is 0 * 0.12 = 0
        
        // Create sales person with zero sales
        Date birthDate = dateFormat.parse("1992-03-10 00:00:00");
        SalesPeople salesPerson = new SalesPeople("Sales", "Carol Davis", birthDate, "111-22-3333", 48000.0, 0.0, 0.12);
        
        // Add sales person to company
        company.addEmployee(salesPerson);
        
        // Calculate total commission and verify result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(0.0, result, 0.001); // 0 * 0.12 = 0
    }
    
    @Test
    public void testCase5_MultipleSalespersonsWithMixedSales() throws Exception {
        // Test Case 5: Multiple Salespersons with Mixed Sales
        // Input: A Company object with three SalesPerson objects. First has amountOfSales = 1500.00 and commissionPercentage = 0.08, 
        // second has amountOfSales = 0 and commissionPercentage = 0.10, third has amountOfSales = 4000.00 and commissionPercentage = 0.25.
        // Expected Output: The total commission amount is 1120.00
        
        // Create first sales person
        Date birthDate1 = dateFormat.parse("1987-11-05 00:00:00");
        SalesPeople salesPerson1 = new SalesPeople("Sales", "David Wilson", birthDate1, "222-33-4444", 52000.0, 1500.00, 0.08);
        
        // Create second sales person with zero sales
        Date birthDate2 = dateFormat.parse("1991-07-25 00:00:00");
        SalesPeople salesPerson2 = new SalesPeople("Sales", "Eva Brown", birthDate2, "333-44-5555", 47000.0, 0.0, 0.10);
        
        // Create third sales person
        Date birthDate3 = dateFormat.parse("1989-09-12 00:00:00");
        SalesPeople salesPerson3 = new SalesPeople("Sales", "Frank Miller", birthDate3, "444-55-6666", 58000.0, 4000.00, 0.25);
        
        // Add all sales persons to company
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        company.addEmployee(salesPerson3);
        
        // Calculate total commission and verify result
        double result = company.calculateTotalSalesPeopleCommission();
        assertEquals(1120.00, result, 0.001); // 1500*0.08 + 0*0.10 + 4000*0.25 = 120 + 0 + 1000 = 1120
    }
}