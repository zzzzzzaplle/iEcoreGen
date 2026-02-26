import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Test Case 1: "Calculate Total Salary for Company with Single Worker"
        // Input: "A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00"
        // Expected Output: "The total salary of all employees in the company is 800.00 (40.00 * 20.00)."
        
        // Create a worker and set properties
        NonShiftWorker worker = new NonShiftWorker();
        worker.setWeeklyWorkingHours(40.0);
        worker.setHourlyRates(20.0);
        
        // Add worker to company
        company.addEmployee(worker);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalSalary();
        assertEquals(800.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Test Case 2: "Calculate Total Salary for Company with Single SalesPerson"
        // Input: "A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10"
        // Expected Output: "The total salary of all employees in the company is 3200.00."
        
        // Create a salesperson and set properties
        Salesperson salesperson = new Salesperson();
        salesperson.setSalary(3000.00);
        salesperson.setAmountOfSales(2000.00);
        salesperson.setCommissionPercentage(0.10);
        
        // Add salesperson to company
        company.addEmployee(salesperson);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalSalary();
        assertEquals(3200.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Test Case 3: "Calculate Total Salary for Company with Single Manager"
        // Input: "A Company with 1 Manager. Manager details: salary = 5000.00"
        // Expected Output: "The total salary of all employees in the company is 5000.00."
        
        // Create a manager and set properties
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalSalary();
        assertEquals(5000.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Test Case 4: "Calculate Total Salary for Company with Worker, SalesPerson and Manager"
        // Input: "A Company with 1 Worker, 1 SalesPerson and 1 Manager. Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00. SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15. Manager details: salary = 4800.00"
        // Expected Output: "The total salary of all employees in the company is 8595.00."
        
        // Create and configure worker
        NonShiftWorker worker = new NonShiftWorker();
        worker.setWeeklyWorkingHours(35.0);
        worker.setHourlyRates(22.0);
        
        // Create and configure salesperson
        Salesperson salesperson = new Salesperson();
        salesperson.setSalary(2800.00);
        salesperson.setAmountOfSales(1500.00);
        salesperson.setCommissionPercentage(0.15);
        
        // Create and configure manager
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        // Add all employees to company
        company.addEmployee(worker);
        company.addEmployee(salesperson);
        company.addEmployee(manager);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalSalary();
        assertEquals(8595.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Test Case 5: "Calculate Total Salary for Company with Multiple Workers"
        // Input: "A Company with 2 Workers, 1 shift worker. First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00. Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00. Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00"
        // Expected Output: "The total salary of all employees in the company is 2528.00."
        
        // Create and configure first worker
        NonShiftWorker worker1 = new NonShiftWorker();
        worker1.setWeeklyWorkingHours(45.0);
        worker1.setHourlyRates(18.0);
        
        // Create and configure second worker
        NonShiftWorker worker2 = new NonShiftWorker();
        worker2.setWeeklyWorkingHours(38.0);
        worker2.setHourlyRates(21.0);
        
        // Create and configure shift worker
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(30.0);
        shiftWorker.setHourlyRates(24.0);
        shiftWorker.setHolidayPremiums(200.0);
        
        // Add all workers to company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalSalary();
        assertEquals(2528.00, totalSalary, 0.001);
    }
}