import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(40.0);
        worker.setHourlyRate(20.0);
        
        company.addEmployee(worker);
        
        double expectedTotalSalary = 800.00; // 40.00 * 20.00
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 800.00 for single worker", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Test Case 2: Calculate Total Salary for Company with Single SalesPerson
        // Input: A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(3000.0);
        salesPerson.setAmountOfSales(2000.0);
        salesPerson.setCommissionPercentage(0.10);
        
        company.addEmployee(salesPerson);
        
        double expectedTotalSalary = 3200.00; // 3000.00 + (2000.00 * 0.10)
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 3200.00 for single sales person", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Test Case 3: Calculate Total Salary for Company with Single Manager
        // Input: A Company with 1 Manager. Manager details: salary = 5000.00
        Manager manager = new Manager();
        manager.setFixedSalary(5000.0);
        
        company.addEmployee(manager);
        
        double expectedTotalSalary = 5000.00;
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 5000.00 for single manager", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithMixedEmployees() {
        // Test Case 4: Calculate Total Salary for Company with Worker, SalesPerson and Manager
        // Input: A Company with 1 Worker, 1 SalesPerson and 1 Manager. 
        // Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00. 
        // SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15. 
        // Manager details: salary = 4800.00
        
        // Create and add Worker
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(35.0);
        worker.setHourlyRate(22.0);
        company.addEmployee(worker);
        
        // Create and add SalesPerson
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(2800.0);
        salesPerson.setAmountOfSales(1500.0);
        salesPerson.setCommissionPercentage(0.15);
        company.addEmployee(salesPerson);
        
        // Create and add Manager
        Manager manager = new Manager();
        manager.setFixedSalary(4800.0);
        company.addEmployee(manager);
        
        double expectedTotalSalary = 8595.00; // Worker: 770.00 + SalesPerson: 3025.00 + Manager: 4800.00
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 8595.00 for mixed employee types", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Test Case 5: Calculate Total Salary for Company with Multiple Workers
        // Input: A Company with 2 Workers, 1 shift worker. 
        // First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00. 
        // Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00. 
        // Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00
        
        // Create and add first Worker (regular)
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(45.0);
        worker1.setHourlyRate(18.0);
        worker1.setShiftWorker(false);
        company.addEmployee(worker1);
        
        // Create and add second Worker (regular)
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(38.0);
        worker2.setHourlyRate(21.0);
        worker2.setShiftWorker(false);
        company.addEmployee(worker2);
        
        // Create and add third Worker (shift worker with holiday premium)
        Worker shiftWorker = new Worker();
        shiftWorker.setWeeklyWorkingHours(30.0);
        shiftWorker.setHourlyRate(24.0);
        shiftWorker.setShiftWorker(true);
        shiftWorker.setHolidayPremium(200.0);
        company.addEmployee(shiftWorker);
        
        double expectedTotalSalary = 2528.00; // Worker1: 810.00 + Worker2: 798.00 + ShiftWorker: 920.00
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 2528.00 for multiple workers including shift worker", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
}