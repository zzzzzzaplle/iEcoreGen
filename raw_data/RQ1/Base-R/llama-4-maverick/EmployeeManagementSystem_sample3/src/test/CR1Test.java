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
        // Input: A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00
        NonShiftWorker worker = new NonShiftWorker();
        worker.setWeeklyWorkingHours(40);
        worker.setHourlyRates(20.00);
        company.addEmployee(worker);
        
        // Expected Output: The total salary of all employees in the company is 800.00 (40.00 * 20.00)
        double expectedTotalSalary = 800.00;
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 800.00 for single worker", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Input: A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        company.addEmployee(salesPerson);
        
        // Expected Output: The total salary of all employees in the company is 3200.00
        double expectedTotalSalary = 3200.00;
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 3200.00 for single salesperson", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Input: A Company with 1 Manager. Manager details: salary = 5000.00
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        company.addEmployee(manager);
        
        // Expected Output: The total salary of all employees in the company is 5000.00
        double expectedTotalSalary = 5000.00;
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 5000.00 for single manager", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Input: A Company with 1 Worker, 1 SalesPerson and 1 Manager
        // Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00
        NonShiftWorker worker = new NonShiftWorker();
        worker.setWeeklyWorkingHours(35);
        worker.setHourlyRates(22.00);
        company.addEmployee(worker);
        
        // SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        company.addEmployee(salesPerson);
        
        // Manager details: salary = 4800.00
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        company.addEmployee(manager);
        
        // Expected Output: The total salary of all employees in the company is 8595.00
        double expectedTotalSalary = 8595.00;
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 8595.00 for worker, salesperson and manager", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Input: A Company with 2 Workers, 1 shift worker
        // First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00
        NonShiftWorker worker1 = new NonShiftWorker();
        worker1.setWeeklyWorkingHours(45);
        worker1.setHourlyRates(18.00);
        company.addEmployee(worker1);
        
        // Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00
        NonShiftWorker worker2 = new NonShiftWorker();
        worker2.setWeeklyWorkingHours(38);
        worker2.setHourlyRates(21.00);
        company.addEmployee(worker2);
        
        // Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremiums(200.00);
        company.addEmployee(shiftWorker);
        
        // Expected Output: The total salary of all employees in the company is 2528.00
        double expectedTotalSalary = 2528.00;
        double actualTotalSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 2528.00 for multiple workers including shift worker", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
}