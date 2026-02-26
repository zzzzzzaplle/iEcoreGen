import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Test Case 1: "Calculate Total Salary for Company with Single Worker"
        // Input: "A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00"
        // Expected Output: "The total salary of all employees in the company is 800.00 (40.00 * 20.00)."
        
        // Create and configure a worker
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        worker.setName("Worker 1");
        worker.setDepartment("Production");
        
        // Add worker to company
        company.getEmployees().add(worker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected result
        assertEquals(800.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Test Case 2: "Calculate Total Salary for Company with Single SalesPerson"
        // Input: "A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10"
        // Expected Output: "The total salary of all employees in the company is 3200.00."
        
        // Create and configure a salesperson
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        salesPerson.setName("Sales Person 1");
        salesPerson.setDepartment("Sales");
        
        // Add salesperson to company
        company.getEmployees().add(salesPerson);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected result
        assertEquals(3200.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Test Case 3: "Calculate Total Salary for Company with Single Manager"
        // Input: "A Company with 1 Manager. Manager details: salary = 5000.00"
        // Expected Output: "The total salary of all employees in the company is 5000.00."
        
        // Create and configure a manager
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        manager.setName("Manager 1");
        manager.setDepartment("Management");
        
        // Add manager to company
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected result
        assertEquals(5000.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Test Case 4: "Calculate Total Salary for Company with Worker, SalesPerson and Manager"
        // Input: "A Company with 1 Worker, 1 SalesPerson and 1 Manager. Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00. SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15. Manager details: salary = 4800.00"
        // Expected Output: "The total salary of all employees in the company is 8595.00."
        
        // Create and configure a worker
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        worker.setName("Worker 1");
        worker.setDepartment("Production");
        
        // Create and configure a salesperson
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        salesPerson.setName("Sales Person 1");
        salesPerson.setDepartment("Sales");
        
        // Create and configure a manager
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        manager.setName("Manager 1");
        manager.setDepartment("Management");
        
        // Add all employees to company
        company.getEmployees().add(worker);
        company.getEmployees().add(salesPerson);
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected result
        assertEquals(8595.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Test Case 5: "Calculate Total Salary for Company with Multiple Workers"
        // Input: "A Company with 2 Workers, 1 shift worker. First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00. Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00. Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00"
        // Expected Output: "The total salary of all employees in the company is 2528.00."
        
        // Create and configure first worker
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        worker1.setName("Worker 1");
        worker1.setDepartment("Production");
        
        // Create and configure second worker
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        worker2.setName("Worker 2");
        worker2.setDepartment("Production");
        
        // Create and configure shift worker
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        shiftWorker.setName("Shift Worker");
        shiftWorker.setDepartment("Production");
        
        // Add all workers to company
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        company.getEmployees().add(shiftWorker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected result
        assertEquals(2528.00, totalSalary, 0.001);
    }
}