import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        
        NonShiftWorker worker = new NonShiftWorker();
        worker.setWeeklyWorkingHours(40.0);
        worker.setHourlyRate(20.0);
        
        company.addEmployee(worker);
        
        double expected = 800.00; // 40.00 * 20.00
        double actual = company.calculateTotalSalary();
        
        assertEquals("Total salary for single worker should be 800.00", 
                     expected, actual, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Test Case 2: Calculate Total Salary for Company with Single SalesPerson
        // Input: A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setSalaryBase(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        company.addEmployee(salesPerson);
        
        double expected = 3200.00; // 3000.00 + (2000.00 * 0.10)
        double actual = company.calculateTotalSalary();
        
        assertEquals("Total salary for single salesperson should be 3200.00", 
                     expected, actual, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Test Case 3: Calculate Total Salary for Company with Single Manager
        // Input: A Company with 1 Manager. Manager details: salary = 5000.00
        
        Manager manager = new Manager();
        manager.setSalaryBase(5000.00);
        
        company.addEmployee(manager);
        
        double expected = 5000.00;
        double actual = company.calculateTotalSalary();
        
        assertEquals("Total salary for single manager should be 5000.00", 
                     expected, actual, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Test Case 4: Calculate Total Salary for Company with Worker, SalesPerson and Manager
        // Input: A Company with 1 Worker, 1 SalesPerson and 1 Manager. 
        // Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00.
        // SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15.
        // Manager details: salary = 4800.00
        
        // Create and configure worker
        NonShiftWorker worker = new NonShiftWorker();
        worker.setWeeklyWorkingHours(35.0);
        worker.setHourlyRate(22.0);
        
        // Create and configure salesperson
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setSalaryBase(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create and configure manager
        Manager manager = new Manager();
        manager.setSalaryBase(4800.00);
        
        // Add all employees to company
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        double expected = 8595.00; // Worker: 35 * 22 = 770, SalesPerson: 2800 + (1500 * 0.15) = 3025, Manager: 4800
        double actual = company.calculateTotalSalary();
        
        assertEquals("Total salary for mixed employees should be 8595.00", 
                     expected, actual, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Test Case 5: Calculate Total Salary for Company with Multiple Workers
        // Input: A Company with 2 Workers, 1 shift worker. 
        // First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00.
        // Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00.
        // Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00
        
        // Create and configure first worker (non-shift)
        NonShiftWorker worker1 = new NonShiftWorker();
        worker1.setWeeklyWorkingHours(45.0);
        worker1.setHourlyRate(18.0);
        
        // Create and configure second worker (non-shift)
        NonShiftWorker worker2 = new NonShiftWorker();
        worker2.setWeeklyWorkingHours(38.0);
        worker2.setHourlyRate(21.0);
        
        // Create and configure shift worker
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(30.0);
        shiftWorker.setHourlyRate(24.0);
        shiftWorker.setHolidayPremium(200.0);
        shiftWorker.setDepartment(Department.DELIVERY);
        
        // Add all workers to company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        double expected = 2528.00; // Worker1: 45 * 18 = 810, Worker2: 38 * 21 = 798, ShiftWorker: (30 * 24) + 200 = 920
        double actual = company.calculateTotalSalary();
        
        assertEquals("Total salary for multiple workers should be 2528.00", 
                     expected, actual, 0.001);
    }
}