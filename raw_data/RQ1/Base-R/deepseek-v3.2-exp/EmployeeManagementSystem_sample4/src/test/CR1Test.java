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
        // Test Case 1: Calculate Total Salary for Company with Single Worker
        // Input: A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00
        // Expected Output: The total salary of all employees in the company is 800.00 (40.00 * 20.00)
        
        // Create worker and set details
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(40.0);
        worker.setHourlyRate(20.0);
        
        // Add worker to company
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        company.setEmployees(employees);
        
        // Calculate total salary and verify result
        double totalSalary = company.calculateTotalSalary();
        assertEquals(800.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Test Case 2: Calculate Total Salary for Company with Single SalesPerson
        // Input: A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        // Expected Output: The total salary of all employees in the company is 3200.00
        
        // Create sales person and set details
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add sales person to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate total salary and verify result
        double totalSalary = company.calculateTotalSalary();
        assertEquals(3200.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Test Case 3: Calculate Total Salary for Company with Single Manager
        // Input: A Company with 1 Manager. Manager details: salary = 5000.00
        // Expected Output: The total salary of all employees in the company is 5000.00
        
        // Create manager and set details
        Manager manager = new Manager();
        manager.setFixedSalary(5000.00);
        
        // Add manager to company
        List<Employee> employees = new ArrayList<>();
        employees.add(manager);
        company.setEmployees(employees);
        
        // Calculate total salary and verify result
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
        
        // Create worker and set details
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(35.0);
        worker.setHourlyRate(22.0);
        
        // Create sales person and set details
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create manager and set details
        Manager manager = new Manager();
        manager.setFixedSalary(4800.00);
        
        // Add all employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        employees.add(salesPerson);
        employees.add(manager);
        company.setEmployees(employees);
        
        // Calculate total salary and verify result
        double totalSalary = company.calculateTotalSalary();
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
        
        // Create first worker (non-shift)
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(45.0);
        worker1.setHourlyRate(18.0);
        worker1.setShiftWorker(false);
        
        // Create second worker (non-shift)
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(38.0);
        worker2.setHourlyRate(21.0);
        worker2.setShiftWorker(false);
        
        // Create third worker (shift worker with holiday premiums)
        Worker worker3 = new Worker();
        worker3.setWeeklyWorkingHours(30.0);
        worker3.setHourlyRate(24.0);
        worker3.setShiftWorker(true);
        worker3.setHolidayPremiums(200.00);
        
        // Add all workers to company
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(worker3);
        company.setEmployees(employees);
        
        // Calculate total salary and verify result
        double totalSalary = company.calculateTotalSalary();
        assertEquals(2528.00, totalSalary, 0.001);
    }
}