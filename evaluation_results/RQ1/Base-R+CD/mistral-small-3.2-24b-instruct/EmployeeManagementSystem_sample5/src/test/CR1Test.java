import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Setup: Create a Worker with weeklyWorkingHour = 40, hourlyRates = 20.00
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        company.addEmployee(worker);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected result is 800.00 (40.00 * 20.00)
        assertEquals(800.00, result, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Setup: Create a SalesPerson with salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        company.addEmployee(salesPerson);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected result is 3200.00 (3000.00 + 2000.00 * 0.10)
        assertEquals(3200.00, result, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Setup: Create a Manager with salary = 5000.00
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        company.addEmployee(manager);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected result is 5000.00
        assertEquals(5000.00, result, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Setup: Create Worker with weeklyWorkingHour = 35, hourlyRates = 22.00
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        // Setup: Create SalesPerson with salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Setup: Create Manager with salary = 4800.00
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected result is 8595.00 (770.00 + 3025.00 + 4800.00)
        assertEquals(8595.00, result, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Setup: Create first Worker with weeklyWorkingHour = 45, hourlyRates = 18.00
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        // Setup: Create second Worker with weeklyWorkingHour = 38, hourlyRates = 21.00
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        // Setup: Create ShiftWorker with weeklyWorkingHour = 30, hourlyRates = 24.00, holidayPremium = 200.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected result is 2528.00 (810.00 + 798.00 + 720.00 + 200.00)
        assertEquals(2528.00, result, 0.001);
    }
}