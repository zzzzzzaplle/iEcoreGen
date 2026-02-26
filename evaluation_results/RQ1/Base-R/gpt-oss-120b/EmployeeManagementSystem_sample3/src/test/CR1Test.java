import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a fresh company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Test Case 1: Calculate Total Salary for Company with Single Worker
        // Input: A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00
        
        // Create a worker (using OffShiftWorker as base class)
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHours(40.0);
        worker.setHourlyRate(20.0);
        
        // Add worker to company
        company.addEmployee(worker);
        
        // Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Expected output: 40.00 * 20.00 = 800.00
        assertEquals(800.00, result, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Test Case 2: Calculate Total Salary for Company with Single SalesPerson
        // Input: A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, 
        // amountOfSales = 2000.00, commissionPercentage = 0.10
        
        // Create a sales person
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add sales person to company
        company.addEmployee(salesPerson);
        
        // Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Expected output: 3000.00 + (2000.00 * 0.10) = 3200.00
        assertEquals(3200.00, result, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Test Case 3: Calculate Total Salary for Company with Single Manager
        // Input: A Company with 1 Manager. Manager details: salary = 5000.00
        
        // Create a manager
        Manager manager = new Manager();
        manager.setFixedSalary(5000.00);
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Expected output: 5000.00
        assertEquals(5000.00, result, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Test Case 4: Calculate Total Salary for Company with Worker, SalesPerson and Manager
        // Input: A Company with 1 Worker, 1 SalesPerson and 1 Manager. 
        // Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00.
        // SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15.
        // Manager details: salary = 4800.00
        
        // Create worker
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHours(35.0);
        worker.setHourlyRate(22.0);
        
        // Create sales person
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create manager
        Manager manager = new Manager();
        manager.setFixedSalary(4800.00);
        
        // Add all employees to company
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Expected output: (35 * 22) + (2800 + 1500 * 0.15) + 4800 = 770 + 3025 + 4800 = 8595.00
        assertEquals(8595.00, result, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Test Case 5: Calculate Total Salary for Company with Multiple Workers
        // Input: A Company with 2 Workers, 1 shift worker. 
        // First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00.
        // Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00.
        // Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00
        
        // Create first worker (regular off-shift worker)
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHours(45.0);
        worker1.setHourlyRate(18.0);
        
        // Create second worker (regular off-shift worker)
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHours(38.0);
        worker2.setHourlyRate(21.0);
        
        // Create third worker (shift worker with holiday premium)
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(30.0);
        shiftWorker.setHourlyRate(24.0);
        shiftWorker.setHolidayPremium(200.0);
        
        // Add all workers to company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        // Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Expected output: (45 * 18) + (38 * 21) + (30 * 24 + 200) = 810 + 798 + (720 + 200) = 2528.00
        assertEquals(2528.00, result, 0.001);
    }
}