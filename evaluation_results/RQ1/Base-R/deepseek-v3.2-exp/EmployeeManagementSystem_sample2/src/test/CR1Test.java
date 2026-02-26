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
        // Initialize a fresh company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Setup: Create a company with 1 Worker
        // Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(40.0);
        worker.setHourlyRate(20.00);
        company.addEmployee(worker);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Verify: Expected output is 800.00 (40.00 * 20.00)
        assertEquals(800.00, result, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Setup: Create a company with 1 SalesPerson
        // SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        company.addEmployee(salesPerson);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Verify: Expected output is 3200.00 (3000.00 + 2000.00 * 0.10)
        assertEquals(3200.00, result, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Setup: Create a company with 1 Manager
        // Manager details: salary = 5000.00
        Manager manager = new Manager();
        manager.setFixedSalary(5000.00);
        company.addEmployee(manager);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Verify: Expected output is 5000.00
        assertEquals(5000.00, result, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Setup: Create a company with 1 Worker, 1 SalesPerson and 1 Manager
        // Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(35.0);
        worker.setHourlyRate(22.00);
        company.addEmployee(worker);
        
        // SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        company.addEmployee(salesPerson);
        
        // Manager details: salary = 4800.00
        Manager manager = new Manager();
        manager.setFixedSalary(4800.00);
        company.addEmployee(manager);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Verify: Expected output is 8595.00
        // Calculation: Worker: 35 * 22 = 770, SalesPerson: 2800 + (1500 * 0.15) = 3025, Manager: 4800
        // Total: 770 + 3025 + 4800 = 8595.00
        assertEquals(8595.00, result, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Setup: Create a company with 2 Workers, 1 shift worker
        // First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(45.0);
        worker1.setHourlyRate(18.00);
        company.addEmployee(worker1);
        
        // Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(38.0);
        worker2.setHourlyRate(21.00);
        company.addEmployee(worker2);
        
        // Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(30.0);
        shiftWorker.setHourlyRate(24.00);
        shiftWorker.setHolidayPremium(200.00);
        company.addEmployee(shiftWorker);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Verify: Expected output is 2528.00
        // Calculation: Worker1: 45 * 18 = 810, Worker2: 38 * 21 = 798, 
        // ShiftWorker: (30 * 24) + 200 = 720 + 200 = 920
        // Total: 810 + 798 + 920 = 2528.00
        assertEquals(2528.00, result, 0.001);
    }
}