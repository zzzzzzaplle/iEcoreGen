import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Test Case 1: Calculate Total Salary for Company with Single Worker
        // Input: A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00
        // Expected Output: The total salary of all employees in the company is 800.00 (40.00 * 20.00).
        
        // Create a worker with specified details
        Worker worker = new Worker("IT", "John Doe", LocalDate.of(1990, 1, 1), 
                                  "123-45-6789", 40.0, 20.0, false);
        
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
        // Expected Output: The total salary of all employees in the company is 3200.00.
        
        // Create a salesperson with specified details
        SalesPerson salesPerson = new SalesPerson("Sales", "Jane Smith", LocalDate.of(1985, 5, 15),
                                                "987-65-4321", 3000.00, 2000.00, 0.10);
        
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
        // Expected Output: The total salary of all employees in the company is 5000.00.
        
        // Create a manager with specified details
        Manager manager = new Manager("Management", "Bob Johnson", LocalDate.of(1980, 3, 20),
                                     "456-78-9012", 5000.00);
        
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
        // Expected Output: The total salary of all employees in the company is 8595.00.
        
        // Create worker with specified details
        Worker worker = new Worker("IT", "Worker1", LocalDate.of(1992, 6, 10),
                                  "111-11-1111", 35.0, 22.0, false);
        
        // Create salesperson with specified details
        SalesPerson salesPerson = new SalesPerson("Sales", "SalesPerson1", LocalDate.of(1988, 8, 25),
                                                "222-22-2222", 2800.00, 1500.00, 0.15);
        
        // Create manager with specified details
        Manager manager = new Manager("Management", "Manager1", LocalDate.of(1975, 12, 5),
                                     "333-33-3333", 4800.00);
        
        // Add all employees to company
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Calculate total salary and verify expected result
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
        // Expected Output: The total salary of all employees in the company is 2528.00.
        
        // Note: The test specification mentions 2 workers but describes 3 workers. 
        // Following the exact description, I'll create 3 workers including the shift worker
        
        // Create first worker (non-shift)
        Worker worker1 = new Worker("IT", "Worker1", LocalDate.of(1991, 3, 15),
                                   "444-44-4444", 45.0, 18.0, false);
        
        // Create second worker (non-shift)
        Worker worker2 = new Worker("HR", "Worker2", LocalDate.of(1993, 7, 20),
                                   "555-55-5555", 38.0, 21.0, false);
        
        // Create third worker (shift worker in Delivery department for holiday premium)
        Worker shiftWorker = new Worker("Delivery", "ShiftWorker", LocalDate.of(1989, 11, 30),
                                      "666-66-6666", 30.0, 24.0, true);
        
        // Add all workers to company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalSalary();
        assertEquals(2528.00, totalSalary, 0.001);
    }
}