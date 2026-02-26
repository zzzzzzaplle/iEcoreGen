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
        // Expected Output: The total salary of all employees in the company is 800.00 (40.00 * 20.00)
        
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(40.0);
        worker.setHourlyRates(20.0);
        
        company.addEmployee(worker);
        
        double expectedTotalSalary = 800.00;
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals(expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Test Case 2: Calculate Total Salary for Company with Single SalesPerson
        // Input: A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        // Expected Output: The total salary of all employees in the company is 3200.00
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        company.addEmployee(salesPerson);
        
        double expectedTotalSalary = 3200.00;
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals(expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Test Case 3: Calculate Total Salary for Company with Single Manager
        // Input: A Company with 1 Manager. Manager details: salary = 5000.00
        // Expected Output: The total salary of all employees in the company is 5000.00
        
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        company.addEmployee(manager);
        
        double expectedTotalSalary = 5000.00;
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals(expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Test Case 4: Calculate Total Salary for Company with Worker, SalesPerson and Manager
        // Input: A Company with 1 Worker, 1 SalesPerson and 1 Manager. 
        // Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00. 
        // SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15. 
        // Manager details: salary = 4800.00
        // Expected Output: The total salary of all employees in the company is 8595.00
        
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(35.0);
        worker.setHourlyRates(22.0);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        double expectedTotalSalary = 8595.00;
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals(expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Test Case 5: Calculate Total Salary for Company with Multiple Workers
        // Input: A Company with 2 Workers, 1 shift worker. 
        // First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00. 
        // Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00. 
        // Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00
        // Expected Output: The total salary of all employees in the company is 2528.00
        
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(45.0);
        worker1.setHourlyRates(18.0);
        
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(38.0);
        worker2.setHourlyRates(21.0);
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(30.0);
        shiftWorker.setHourlyRates(24.0);
        shiftWorker.setHolidayPremium(200.0);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        double expectedTotalSalary = 2528.00;
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals(expectedTotalSalary, actualTotalSalary, 0.001);
    }
}