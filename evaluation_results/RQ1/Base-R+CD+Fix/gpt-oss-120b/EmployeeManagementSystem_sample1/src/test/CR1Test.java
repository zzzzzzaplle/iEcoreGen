import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Setup: Create a company with 1 Worker
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        company.addEmployee(worker);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: The total salary should be 800.00 (40.00 * 20.00)
        assertEquals(800.00, result, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Setup: Create a company with 1 SalesPerson
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        company.addEmployee(salesPerson);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: The total salary should be 3200.00
        assertEquals(3200.00, result, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Setup: Create a company with 1 Manager
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        company.addEmployee(manager);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: The total salary should be 5000.00
        assertEquals(5000.00, result, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Setup: Create a company with 1 Worker, 1 SalesPerson and 1 Manager
        
        // Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        // SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Manager details: salary = 4800.00
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: The total salary should be 8595.00
        assertEquals(8595.00, result, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Setup: Create a company with 3 Workers (2 regular, 1 shift worker)
        
        // First Worker: weeklyWorkingHour = 45, hourlyRates = 18.00
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        // Second Worker: weeklyWorkingHour = 38, hourlyRates = 21.00
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        // Third Worker: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: The total salary should be 2528.00
        assertEquals(2528.00, result, 0.001);
    }
}