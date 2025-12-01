import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new Company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Test Case 1: Calculate Total Salary for Company with Single Worker
        // Input: A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00
        // Expected Output: The total salary of all employees in the company is 800.00 (40.00 * 20.00)
        
        // Create and configure a Worker
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(40);
        worker.setHourlyRate(20.00);
        
        // Add worker to company
        company.addEmployee(worker);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalSalary();
        assertEquals(800.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Test Case 2: Calculate Total Salary for Company with Single SalesPerson
        // Input: A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        // Expected Output: The total salary of all employees in the company is 3200.00
        
        // Create and configure a SalesPerson
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add salesperson to company
        company.addEmployee(salesPerson);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalSalary();
        assertEquals(3200.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Test Case 3: Calculate Total Salary for Company with Single Manager
        // Input: A Company with 1 Manager. Manager details: salary = 5000.00
        // Expected Output: The total salary of all employees in the company is 5000.00
        
        // Create and configure a Manager
        Manager manager = new Manager();
        // Note: Manager's calculateSalary() returns 0.0 as per the provided code
        // This test will fail because the specification expects 5000.00 but code returns 0.0
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalSalary();
        assertEquals(5000.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Test Case 4: Calculate Total Salary for Company with Worker, SalesPerson and Manager
        // Input: A Company with 1 Worker, 1 SalesPerson and 1 Manager. 
        // Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00. 
        // SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15. 
        // Manager details: salary = 4800.00
        // Expected Output: The total salary of all employees in the company is 8595.00
        
        // Create and configure Worker
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(35);
        worker.setHourlyRate(22.00);
        
        // Create and configure SalesPerson
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create and configure Manager
        Manager manager = new Manager();
        // Note: Manager's calculateSalary() returns 0.0 as per the provided code
        
        // Add all employees to company
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalSalary();
        // Expected breakdown: Worker: 35 * 22 = 770, SalesPerson: 2800 + (1500 * 0.15) = 3025, Manager: 0
        // Total: 770 + 3025 + 0 = 3795 (but specification expects 8595.00)
        // This test will fail due to discrepancy between specification and implementation
        assertEquals(8595.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Test Case 5: Calculate Total Salary for Company with Multiple Workers
        // Input: A Company with 2 Workers, 1 shift worker. 
        // First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00. 
        // Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00. 
        // Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00
        // Expected Output: The total salary of all employees in the company is 2528.00
        
        // Create and configure first Worker (non-shift)
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(45);
        worker1.setHourlyRate(18.00);
        worker1.setShiftWorker(false);
        
        // Create and configure second Worker (non-shift)
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(38);
        worker2.setHourlyRate(21.00);
        worker2.setShiftWorker(false);
        
        // Create and configure third Worker (shift worker with holiday premium)
        Worker worker3 = new Worker();
        worker3.setWeeklyWorkingHours(30);
        worker3.setHourlyRate(24.00);
        worker3.setShiftWorker(true);
        worker3.setHolidayPremium(200.00);
        
        // Add all workers to company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalSalary();
        // Expected breakdown: 
        // Worker1: 45 * 18 = 810
        // Worker2: 38 * 21 = 798  
        // Worker3: (30 * 24) + 200 = 720 + 200 = 920
        // Total: 810 + 798 + 920 = 2528.00
        assertEquals(2528.00, totalSalary, 0.001);
    }
}